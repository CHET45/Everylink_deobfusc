package com.lxj.xpopup.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.photoview.PhotoView;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BlankView;
import com.lxj.xpopup.widget.HackyViewPager;
import com.lxj.xpopup.widget.PhotoViewContainer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ImageViewerPopupView extends BasePopupView implements OnDragChangeListener, View.OnClickListener {
    protected ArgbEvaluator argbEvaluator;
    protected int bgColor;
    protected FrameLayout container;
    protected View customView;
    protected XPopupImageLoader imageLoader;
    protected boolean isInfinite;
    protected boolean isShowIndicator;
    protected boolean isShowPlaceholder;
    protected boolean isShowSaveBtn;
    public OnImageViewerLongPressListener longPressListener;
    protected HackyViewPager pager;
    protected PhotoViewContainer photoViewContainer;
    protected int placeholderColor;
    protected int placeholderRadius;
    protected int placeholderStrokeColor;
    protected BlankView placeholderView;
    protected int position;
    protected Rect rect;
    protected PhotoView snapshotView;
    protected ImageView srcView;
    protected OnSrcViewUpdateListener srcViewUpdateListener;
    protected TextView tv_pager_indicator;
    protected TextView tv_save;
    protected List<Object> urls;

    public ImageViewerPopupView(Context context) {
        super(context);
        this.argbEvaluator = new ArgbEvaluator();
        this.urls = new ArrayList();
        this.rect = null;
        this.isShowPlaceholder = true;
        this.placeholderColor = Color.parseColor("#f1f1f1");
        this.placeholderStrokeColor = -1;
        this.placeholderRadius = -1;
        this.isShowSaveBtn = true;
        this.isShowIndicator = true;
        this.isInfinite = false;
        this.bgColor = Color.rgb(32, 36, 46);
        this.container = (FrameLayout) findViewById(C2213R.id.container);
        if (getImplLayoutId() > 0) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.container, false);
            this.customView = viewInflate;
            viewInflate.setVisibility(4);
            this.customView.setAlpha(0.0f);
            this.container.addView(this.customView);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getInnerLayoutId() {
        return C2213R.layout._xpopup_image_viewer_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        this.tv_pager_indicator = (TextView) findViewById(C2213R.id.tv_pager_indicator);
        this.tv_save = (TextView) findViewById(C2213R.id.tv_save);
        this.placeholderView = (BlankView) findViewById(C2213R.id.placeholderView);
        PhotoViewContainer photoViewContainer = (PhotoViewContainer) findViewById(C2213R.id.photoViewContainer);
        this.photoViewContainer = photoViewContainer;
        photoViewContainer.setOnDragChangeListener(this);
        this.pager = (HackyViewPager) findViewById(C2213R.id.pager);
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter();
        this.pager.setAdapter(photoViewAdapter);
        this.pager.setCurrentItem(this.position);
        this.pager.setVisibility(4);
        addOrUpdateSnapshot();
        this.pager.setOffscreenPageLimit(2);
        this.pager.addOnPageChangeListener(photoViewAdapter);
        if (!this.isShowIndicator) {
            this.tv_pager_indicator.setVisibility(8);
        }
        if (!this.isShowSaveBtn) {
            this.tv_save.setVisibility(8);
        } else {
            this.tv_save.setOnClickListener(this);
        }
    }

    private void setupPlaceholder() {
        this.placeholderView.setVisibility(this.isShowPlaceholder ? 0 : 4);
        if (this.isShowPlaceholder) {
            int i = this.placeholderColor;
            if (i != -1) {
                this.placeholderView.color = i;
            }
            int i2 = this.placeholderRadius;
            if (i2 != -1) {
                this.placeholderView.radius = i2;
            }
            int i3 = this.placeholderStrokeColor;
            if (i3 != -1) {
                this.placeholderView.strokeColor = i3;
            }
            XPopupUtils.setWidthHeight(this.placeholderView, this.rect.width(), this.rect.height());
            this.placeholderView.setTranslationX(this.rect.left);
            this.placeholderView.setTranslationY(this.rect.top);
            this.placeholderView.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPagerIndicator() {
        if (this.urls.size() > 1) {
            this.tv_pager_indicator.setText((getRealPosition() + 1) + "/" + this.urls.size());
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setVisibility(0);
        }
    }

    private void addOrUpdateSnapshot() {
        if (this.srcView == null) {
            return;
        }
        if (this.snapshotView == null) {
            PhotoView photoView = new PhotoView(getContext());
            this.snapshotView = photoView;
            photoView.setEnabled(false);
            this.photoViewContainer.addView(this.snapshotView);
            this.snapshotView.setScaleType(this.srcView.getScaleType());
            this.snapshotView.setTranslationX(this.rect.left);
            this.snapshotView.setTranslationY(this.rect.top);
            XPopupUtils.setWidthHeight(this.snapshotView, this.rect.width(), this.rect.height());
        }
        int realPosition = getRealPosition();
        this.snapshotView.setTag(Integer.valueOf(realPosition));
        ImageView imageView = this.srcView;
        if (imageView != null && imageView.getDrawable() != null) {
            try {
                this.snapshotView.setImageDrawable(this.srcView.getDrawable().getConstantState().newDrawable());
            } catch (Exception unused) {
            }
        }
        setupPlaceholder();
        XPopupImageLoader xPopupImageLoader = this.imageLoader;
        if (xPopupImageLoader != null) {
            xPopupImageLoader.loadSnapshot(this.urls.get(realPosition), this.snapshotView);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(this.bgColor);
            this.pager.setVisibility(0);
            showPagerIndicator();
            this.photoViewContainer.isReleasing = false;
            doAfterShow();
            return;
        }
        this.photoViewContainer.isReleasing = true;
        View view2 = this.customView;
        if (view2 != null) {
            view2.setVisibility(0);
        }
        this.snapshotView.setVisibility(0);
        doAfterShow();
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.1.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        ImageViewerPopupView.this.pager.setVisibility(0);
                        ImageViewerPopupView.this.snapshotView.setVisibility(4);
                        ImageViewerPopupView.this.showPagerIndicator();
                        ImageViewerPopupView.this.photoViewContainer.isReleasing = false;
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setTranslationY(0.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationX(0.0f);
                ImageViewerPopupView.this.snapshotView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                XPopupUtils.setWidthHeight(ImageViewerPopupView.this.snapshotView, ImageViewerPopupView.this.photoViewContainer.getWidth(), ImageViewerPopupView.this.photoViewContainer.getHeight());
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                imageViewerPopupView.animateShadowBg(imageViewerPopupView.bgColor);
                if (ImageViewerPopupView.this.customView != null) {
                    ImageViewerPopupView.this.customView.animate().alpha(1.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).start();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateShadowBg(final int i) {
        final int color = ((ColorDrawable) this.photoViewContainer.getBackground()).getColor();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ImageViewerPopupView.this.photoViewContainer.setBackgroundColor(((Integer) ImageViewerPopupView.this.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(color), Integer.valueOf(i))).intValue());
            }
        });
        valueAnimatorOfFloat.setDuration(getAnimationDuration()).setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        if (this.srcView == null) {
            this.photoViewContainer.setBackgroundColor(0);
            doAfterDismiss();
            this.pager.setVisibility(4);
            this.placeholderView.setVisibility(4);
            return;
        }
        this.tv_pager_indicator.setVisibility(4);
        this.tv_save.setVisibility(4);
        this.pager.setVisibility(4);
        this.photoViewContainer.isReleasing = true;
        this.snapshotView.setVisibility(0);
        doAfterDismiss();
        this.snapshotView.post(new Runnable() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                TransitionManager.beginDelayedTransition((ViewGroup) ImageViewerPopupView.this.snapshotView.getParent(), new TransitionSet().setDuration(ImageViewerPopupView.this.getAnimationDuration()).addTransition(new ChangeBounds()).addTransition(new ChangeTransform()).addTransition(new ChangeImageTransform()).setInterpolator((TimeInterpolator) new FastOutSlowInInterpolator()).addListener((Transition.TransitionListener) new TransitionListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.1
                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(Transition transition) {
                        ImageViewerPopupView.this.pager.setVisibility(4);
                        ImageViewerPopupView.this.snapshotView.setVisibility(0);
                        ImageViewerPopupView.this.pager.setScaleX(1.0f);
                        ImageViewerPopupView.this.pager.setScaleY(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                        ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                        ImageViewerPopupView.this.placeholderView.setVisibility(4);
                    }
                }));
                ImageViewerPopupView.this.snapshotView.setScaleX(1.0f);
                ImageViewerPopupView.this.snapshotView.setScaleY(1.0f);
                ImageViewerPopupView.this.snapshotView.setTranslationY(ImageViewerPopupView.this.rect.top);
                ImageViewerPopupView.this.snapshotView.setTranslationX(ImageViewerPopupView.this.rect.left);
                ImageViewerPopupView.this.snapshotView.setScaleType(ImageViewerPopupView.this.srcView.getScaleType());
                XPopupUtils.setWidthHeight(ImageViewerPopupView.this.snapshotView, ImageViewerPopupView.this.rect.width(), ImageViewerPopupView.this.rect.height());
                ImageViewerPopupView.this.animateShadowBg(0);
                if (ImageViewerPopupView.this.customView != null) {
                    ImageViewerPopupView.this.customView.animate().alpha(0.0f).setDuration(ImageViewerPopupView.this.getAnimationDuration()).setListener(new AnimatorListenerAdapter() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.3.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            if (ImageViewerPopupView.this.customView != null) {
                                ImageViewerPopupView.this.customView.setVisibility(4);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        if (this.popupStatus != PopupStatus.Show) {
            return;
        }
        this.popupStatus = PopupStatus.Dismissing;
        doDismissAnimation();
    }

    public ImageViewerPopupView setImageUrls(List<Object> list) {
        this.urls = list;
        return this;
    }

    public ImageViewerPopupView setSrcViewUpdateListener(OnSrcViewUpdateListener onSrcViewUpdateListener) {
        this.srcViewUpdateListener = onSrcViewUpdateListener;
        return this;
    }

    public ImageViewerPopupView setXPopupImageLoader(XPopupImageLoader xPopupImageLoader) {
        this.imageLoader = xPopupImageLoader;
        return this;
    }

    public ImageViewerPopupView isShowPlaceholder(boolean z) {
        this.isShowPlaceholder = z;
        return this;
    }

    public ImageViewerPopupView isShowIndicator(boolean z) {
        this.isShowIndicator = z;
        return this;
    }

    public ImageViewerPopupView isShowSaveButton(boolean z) {
        this.isShowSaveBtn = z;
        return this;
    }

    public ImageViewerPopupView isInfinite(boolean z) {
        this.isInfinite = z;
        return this;
    }

    public ImageViewerPopupView setPlaceholderColor(int i) {
        this.placeholderColor = i;
        return this;
    }

    public ImageViewerPopupView setPlaceholderRadius(int i) {
        this.placeholderRadius = i;
        return this;
    }

    public ImageViewerPopupView setPlaceholderStrokeColor(int i) {
        this.placeholderStrokeColor = i;
        return this;
    }

    public ImageViewerPopupView setBgColor(int i) {
        this.bgColor = i;
        return this;
    }

    public ImageViewerPopupView setLongPressListener(OnImageViewerLongPressListener onImageViewerLongPressListener) {
        this.longPressListener = onImageViewerLongPressListener;
        return this;
    }

    public ImageViewerPopupView setSingleSrcView(ImageView imageView, Object obj) {
        if (this.urls == null) {
            this.urls = new ArrayList();
        }
        this.urls.clear();
        this.urls.add(obj);
        setSrcView(imageView, 0);
        return this;
    }

    public ImageViewerPopupView setSrcView(ImageView imageView, int i) {
        this.srcView = imageView;
        this.position = i;
        if (imageView != null) {
            int[] iArr = new int[2];
            imageView.getLocationInWindow(iArr);
            if (XPopupUtils.isLayoutRtl(getContext())) {
                int i2 = -((XPopupUtils.getWindowWidth(getContext()) - iArr[0]) - imageView.getWidth());
                this.rect = new Rect(i2, iArr[1], imageView.getWidth() + i2, iArr[1] + imageView.getHeight());
            } else {
                int i3 = iArr[0];
                this.rect = new Rect(i3, iArr[1], imageView.getWidth() + i3, iArr[1] + imageView.getHeight());
            }
        }
        return this;
    }

    public void updateSrcView(ImageView imageView) {
        setSrcView(imageView, this.position);
        addOrUpdateSnapshot();
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onRelease() {
        dismiss();
    }

    @Override // com.lxj.xpopup.interfaces.OnDragChangeListener
    public void onDragChange(int i, float f, float f2) {
        float f3 = 1.0f - f2;
        this.tv_pager_indicator.setAlpha(f3);
        View view2 = this.customView;
        if (view2 != null) {
            view2.setAlpha(f3);
        }
        if (this.isShowSaveBtn) {
            this.tv_save.setAlpha(f3);
        }
        this.photoViewContainer.setBackgroundColor(((Integer) this.argbEvaluator.evaluate(f2 * 0.8f, Integer.valueOf(this.bgColor), 0)).intValue());
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onDismiss() {
        super.onDismiss();
        this.srcView = null;
        this.srcViewUpdateListener = null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        if (view2 == this.tv_save) {
            save();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void destroy() {
        super.destroy();
        HackyViewPager hackyViewPager = this.pager;
        hackyViewPager.removeOnPageChangeListener((PhotoViewAdapter) hackyViewPager.getAdapter());
        this.imageLoader = null;
    }

    protected int getRealPosition() {
        return this.isInfinite ? this.position % this.urls.size() : this.position;
    }

    protected void save() {
        XPermission.create(getContext(), "STORAGE").callback(new XPermission.SimpleCallback() { // from class: com.lxj.xpopup.core.ImageViewerPopupView.4
            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onDenied() {
            }

            @Override // com.lxj.xpopup.util.XPermission.SimpleCallback
            public void onGranted() {
                XPopupUtils.saveBmpToAlbum(ImageViewerPopupView.this.getContext(), ImageViewerPopupView.this.imageLoader, ImageViewerPopupView.this.urls.get(ImageViewerPopupView.this.getRealPosition()));
            }
        }).request();
    }

    public class PhotoViewAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view2, Object obj) {
            return obj == view2;
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public PhotoViewAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            if (ImageViewerPopupView.this.isInfinite) {
                return 100000;
            }
            return ImageViewerPopupView.this.urls.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            if (ImageViewerPopupView.this.isInfinite) {
                i %= ImageViewerPopupView.this.urls.size();
            }
            int i2 = i;
            FrameLayout frameLayoutBuildContainer = buildContainer(viewGroup.getContext());
            ProgressBar progressBarBuildProgressBar = buildProgressBar(viewGroup.getContext());
            XPopupImageLoader xPopupImageLoader = ImageViewerPopupView.this.imageLoader;
            Object obj = ImageViewerPopupView.this.urls.get(i2);
            ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
            frameLayoutBuildContainer.addView(xPopupImageLoader.loadImage(i2, obj, imageViewerPopupView, imageViewerPopupView.snapshotView, progressBarBuildProgressBar), new FrameLayout.LayoutParams(-1, -1));
            frameLayoutBuildContainer.addView(progressBarBuildProgressBar);
            viewGroup.addView(frameLayoutBuildContainer);
            return frameLayoutBuildContainer;
        }

        private FrameLayout buildContainer(Context context) {
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            return frameLayout;
        }

        private ProgressBar buildProgressBar(Context context) {
            ProgressBar progressBar = new ProgressBar(context);
            progressBar.setIndeterminate(true);
            int iDp2px = XPopupUtils.dp2px(ImageViewerPopupView.this.container.getContext(), 40.0f);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iDp2px, iDp2px);
            layoutParams.gravity = 17;
            progressBar.setLayoutParams(layoutParams);
            progressBar.setVisibility(8);
            return progressBar;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            ImageViewerPopupView.this.position = i;
            ImageViewerPopupView.this.showPagerIndicator();
            if (ImageViewerPopupView.this.srcViewUpdateListener != null) {
                OnSrcViewUpdateListener onSrcViewUpdateListener = ImageViewerPopupView.this.srcViewUpdateListener;
                ImageViewerPopupView imageViewerPopupView = ImageViewerPopupView.this;
                onSrcViewUpdateListener.onSrcViewUpdate(imageViewerPopupView, imageViewerPopupView.getRealPosition());
            }
        }
    }
}
