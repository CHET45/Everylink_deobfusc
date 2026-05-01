package com.aivox.common_ui.bgabanner;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.bgabanner.BGAViewPager;
import com.aivox.common_ui.bgabanner.transformer.TransitionEffect;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.videolan.libvlc.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
public class BGABanner extends RelativeLayout implements BGAViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {
    private static final int LWC = -2;
    private static final int NO_PLACEHOLDER_DRAWABLE = -1;
    private static final int RMP = -1;
    private static final int RWC = -2;
    private static final int VEL_THRESHOLD = 400;
    private static final ImageView.ScaleType[] sScaleTypeArray = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    private Adapter mAdapter;
    private boolean mAllowUserScrollable;
    private boolean mAutoPlayAble;
    private int mAutoPlayInterval;
    private AutoPlayTask mAutoPlayTask;
    private int mContentBottomMargin;
    private Delegate mDelegate;
    private View mEnterView;
    private GuideDelegate mGuideDelegate;
    private BGAOnNoDoubleClickListener mGuideOnNoDoubleClickListener;
    private List<View> mHackyViews;
    private boolean mIsFirstInvisible;
    private boolean mIsNeedShowIndicatorOnOnlyOnePage;
    private boolean mIsNumberIndicator;
    private List<? extends Object> mModels;
    private Drawable mNumberIndicatorBackground;
    private int mNumberIndicatorTextColor;
    private int mNumberIndicatorTextSize;
    private TextView mNumberIndicatorTv;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mOverScrollMode;
    private int mPageChangeDuration;
    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;
    private int mPlaceholderDrawableResId;
    private ImageView mPlaceholderIv;
    private ImageView.ScaleType mPlaceholderScaleType;
    private Drawable mPointContainerBackgroundDrawable;
    private int mPointContainerLeftRightPadding;
    private int mPointDrawableResId;
    private int mPointGravity;
    private int mPointLeftRightMargin;
    private LinearLayout mPointRealContainerLl;
    private int mPointTopBottomMargin;
    private boolean mShowPointIndicator;
    private View mSkipView;
    private int mTipTextColor;
    private int mTipTextSize;
    private TextView mTipTv;
    private List<String> mTips;
    private TransitionEffect mTransitionEffect;
    private BGAViewPager mViewPager;
    private List<View> mViews;

    public interface Adapter<V extends View, M> {
        void fillBannerItem(BGABanner bGABanner, V v, M m, int i);
    }

    public interface Delegate<V extends View, M> {
        void onBannerItemClick(BGABanner bGABanner, V v, M m, int i);
    }

    public interface GuideDelegate {
        void onClickEnterOrSkip();
    }

    public BGABanner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BGABanner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAutoPlayAble = true;
        this.mAutoPlayInterval = 3000;
        this.mPageChangeDuration = MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING;
        this.mPointGravity = 81;
        this.mTipTextColor = -1;
        this.mPointDrawableResId = C1034R.drawable.bga_banner_selector_point_solid;
        this.mPlaceholderScaleType = ImageView.ScaleType.CENTER_CROP;
        this.mPlaceholderDrawableResId = -1;
        this.mOverScrollMode = 2;
        this.mIsNumberIndicator = false;
        this.mShowPointIndicator = false;
        this.mNumberIndicatorTextColor = -1;
        this.mAllowUserScrollable = true;
        this.mIsFirstInvisible = true;
        this.mGuideOnNoDoubleClickListener = new BGAOnNoDoubleClickListener() { // from class: com.aivox.common_ui.bgabanner.BGABanner.1
            @Override // com.aivox.common_ui.bgabanner.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view2) {
                if (BGABanner.this.mGuideDelegate != null) {
                    BGABanner.this.mGuideDelegate.onClickEnterOrSkip();
                }
            }
        };
        initDefaultAttrs(context);
        initCustomAttrs(context, attributeSet);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        this.mAutoPlayTask = new AutoPlayTask();
        this.mPointLeftRightMargin = BGABannerUtil.dp2px(context, 3.0f);
        this.mPointTopBottomMargin = BGABannerUtil.dp2px(context, 6.0f);
        this.mPointContainerLeftRightPadding = BGABannerUtil.dp2px(context, 10.0f);
        this.mTipTextSize = BGABannerUtil.sp2px(context, 10.0f);
        this.mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        this.mTransitionEffect = TransitionEffect.Default;
        this.mNumberIndicatorTextSize = BGABannerUtil.sp2px(context, 10.0f);
        this.mContentBottomMargin = 0;
    }

    private void initCustomAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.BGABanner);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            initCustomAttr(typedArrayObtainStyledAttributes.getIndex(i), typedArrayObtainStyledAttributes);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initCustomAttr(int i, TypedArray typedArray) {
        if (i == C1034R.styleable.BGABanner_banner_pointDrawable) {
            this.mPointDrawableResId = typedArray.getResourceId(i, C1034R.drawable.bga_banner_selector_point_solid);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointContainerBackground) {
            this.mPointContainerBackgroundDrawable = typedArray.getDrawable(i);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointLeftRightMargin) {
            this.mPointLeftRightMargin = typedArray.getDimensionPixelSize(i, this.mPointLeftRightMargin);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointContainerLeftRightPadding) {
            this.mPointContainerLeftRightPadding = typedArray.getDimensionPixelSize(i, this.mPointContainerLeftRightPadding);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointTopBottomMargin) {
            this.mPointTopBottomMargin = typedArray.getDimensionPixelSize(i, this.mPointTopBottomMargin);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_indicatorGravity) {
            this.mPointGravity = typedArray.getInt(i, this.mPointGravity);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointAutoPlayAble) {
            this.mAutoPlayAble = typedArray.getBoolean(i, this.mAutoPlayAble);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pointAutoPlayInterval) {
            this.mAutoPlayInterval = typedArray.getInteger(i, this.mAutoPlayInterval);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_pageChangeDuration) {
            this.mPageChangeDuration = typedArray.getInteger(i, this.mPageChangeDuration);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_transitionEffect) {
            this.mTransitionEffect = TransitionEffect.values()[typedArray.getInt(i, TransitionEffect.Accordion.ordinal())];
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_tipTextColor) {
            this.mTipTextColor = typedArray.getColor(i, this.mTipTextColor);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_tipTextSize) {
            this.mTipTextSize = typedArray.getDimensionPixelSize(i, this.mTipTextSize);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_placeholderDrawable) {
            this.mPlaceholderDrawableResId = typedArray.getResourceId(i, this.mPlaceholderDrawableResId);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_isNumberIndicator) {
            this.mIsNumberIndicator = typedArray.getBoolean(i, this.mIsNumberIndicator);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_numberIndicatorTextColor) {
            this.mNumberIndicatorTextColor = typedArray.getColor(i, this.mNumberIndicatorTextColor);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_numberIndicatorTextSize) {
            this.mNumberIndicatorTextSize = typedArray.getDimensionPixelSize(i, this.mNumberIndicatorTextSize);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_numberIndicatorBackground) {
            this.mNumberIndicatorBackground = typedArray.getDrawable(i);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_isNeedShowIndicatorOnOnlyOnePage) {
            this.mIsNeedShowIndicatorOnOnlyOnePage = typedArray.getBoolean(i, this.mIsNeedShowIndicatorOnOnlyOnePage);
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_contentBottomMargin) {
            this.mContentBottomMargin = typedArray.getDimensionPixelSize(i, this.mContentBottomMargin);
            return;
        }
        if (i == C1034R.styleable.BGABanner_android_scaleType) {
            int i2 = typedArray.getInt(i, -1);
            if (i2 >= 0) {
                ImageView.ScaleType[] scaleTypeArr = sScaleTypeArray;
                if (i2 < scaleTypeArr.length) {
                    this.mPlaceholderScaleType = scaleTypeArr[i2];
                    return;
                }
                return;
            }
            return;
        }
        if (i == C1034R.styleable.BGABanner_banner_showPointIndicator) {
            this.mShowPointIndicator = typedArray.getBoolean(i, this.mShowPointIndicator);
        }
    }

    private void initView(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        if (this.mShowPointIndicator) {
            relativeLayout.setBackground(this.mPointContainerBackgroundDrawable);
        } else {
            relativeLayout.setBackgroundColor(0);
        }
        int i = this.mPointContainerLeftRightPadding;
        int i2 = this.mPointTopBottomMargin;
        relativeLayout.setPadding(i, i2, i, i2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        if ((this.mPointGravity & 112) == 48) {
            layoutParams.addRule(10);
        } else {
            layoutParams.addRule(12);
        }
        addView(relativeLayout, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        if (this.mIsNumberIndicator) {
            TextView textView = new TextView(context);
            this.mNumberIndicatorTv = textView;
            textView.setId(C1034R.id.banner_indicatorId);
            this.mNumberIndicatorTv.setGravity(16);
            this.mNumberIndicatorTv.setSingleLine(true);
            this.mNumberIndicatorTv.setEllipsize(TextUtils.TruncateAt.END);
            this.mNumberIndicatorTv.setTextColor(this.mNumberIndicatorTextColor);
            this.mNumberIndicatorTv.setTextSize(0, this.mNumberIndicatorTextSize);
            this.mNumberIndicatorTv.setVisibility(4);
            if (this.mNumberIndicatorBackground != null) {
                this.mNumberIndicatorTv.setBackground(this.mNumberIndicatorBackground);
            }
            relativeLayout.addView(this.mNumberIndicatorTv, layoutParams2);
        } else {
            LinearLayout linearLayout = new LinearLayout(context);
            this.mPointRealContainerLl = linearLayout;
            linearLayout.setId(C1034R.id.banner_indicatorId);
            this.mPointRealContainerLl.setOrientation(0);
            this.mPointRealContainerLl.setGravity(16);
            relativeLayout.addView(this.mPointRealContainerLl, layoutParams2);
            if (!this.mShowPointIndicator) {
                this.mPointRealContainerLl.setVisibility(8);
            }
        }
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(15);
        TextView textView2 = new TextView(context);
        this.mTipTv = textView2;
        textView2.setGravity(16);
        this.mTipTv.setSingleLine(true);
        this.mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        this.mTipTv.setTextColor(this.mTipTextColor);
        this.mTipTv.setTextSize(0, this.mTipTextSize);
        relativeLayout.addView(this.mTipTv, layoutParams3);
        int i3 = this.mPointGravity & 7;
        if (i3 == 3) {
            layoutParams2.addRule(9);
            layoutParams3.addRule(1, C1034R.id.banner_indicatorId);
            this.mTipTv.setGravity(21);
        } else if (i3 == 5) {
            layoutParams2.addRule(11);
            layoutParams3.addRule(0, C1034R.id.banner_indicatorId);
        } else {
            layoutParams2.addRule(14);
            layoutParams3.addRule(0, C1034R.id.banner_indicatorId);
        }
        showPlaceholder();
    }

    public void showPlaceholder() {
        if (this.mPlaceholderIv != null || this.mPlaceholderDrawableResId == -1) {
            return;
        }
        ImageView itemImageView = BGABannerUtil.getItemImageView(getContext(), this.mPlaceholderDrawableResId);
        this.mPlaceholderIv = itemImageView;
        itemImageView.setScaleType(this.mPlaceholderScaleType);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 0, 0, this.mContentBottomMargin);
        addView(this.mPlaceholderIv, layoutParams);
    }

    public void setPageChangeDuration(int i) {
        if (i < 0 || i > 2000) {
            return;
        }
        this.mPageChangeDuration = i;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setPageChangeDuration(i);
        }
    }

    public void setAutoPlayAble(boolean z) {
        this.mAutoPlayAble = z;
        stopAutoPlay();
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager == null || bGAViewPager.getAdapter() == null) {
            return;
        }
        this.mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void setAutoPlayInterval(int i) {
        this.mAutoPlayInterval = i;
    }

    public void setData(List<View> list, List<? extends Object> list2, List<String> list3) {
        if (list == null || list.size() < 1) {
            this.mAutoPlayAble = false;
            list = new ArrayList<>();
            list2 = new ArrayList<>();
            list3 = new ArrayList<>();
        }
        if (this.mAutoPlayAble && list.size() < 3 && this.mHackyViews == null) {
            this.mAutoPlayAble = false;
        }
        this.mModels = list2;
        this.mViews = list;
        this.mTips = list3;
        initIndicator();
        initViewPager();
        removePlaceholder();
    }

    public void setData(int i, List<? extends Object> list, List<String> list2) {
        this.mViews = new ArrayList();
        if (list == null) {
            list = new ArrayList<>();
            list2 = new ArrayList<>();
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.mViews.add(View.inflate(getContext(), i, null));
        }
        if (this.mAutoPlayAble && this.mViews.size() < 3) {
            ArrayList arrayList = new ArrayList(this.mViews);
            this.mHackyViews = arrayList;
            arrayList.add(View.inflate(getContext(), i, null));
            if (this.mHackyViews.size() == 2) {
                this.mHackyViews.add(View.inflate(getContext(), i, null));
            }
        }
        setData(this.mViews, list, list2);
    }

    public void setData(List<? extends Object> list, List<String> list2) {
        setData(C1034R.layout.bga_banner_item_image, list, list2);
    }

    public void setData(List<View> list) {
        setData(list, (List<? extends Object>) null, (List<String>) null);
    }

    public void setData(int... iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            arrayList.add(BGABannerUtil.getItemImageView(getContext(), i));
        }
        setData(arrayList);
    }

    public void setAllowUserScrollable(boolean z) {
        this.mAllowUserScrollable = z;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setAllowUserScrollable(z);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setEnterSkipViewId(int i, int i2) {
        if (i != 0) {
            this.mEnterView = ((Activity) getContext()).findViewById(i);
        }
        if (i2 != 0) {
            this.mSkipView = ((Activity) getContext()).findViewById(i2);
        }
    }

    public void setEnterSkipViewIdAndDelegate(int i, int i2, GuideDelegate guideDelegate) {
        if (guideDelegate != null) {
            this.mGuideDelegate = guideDelegate;
            if (i != 0) {
                View viewFindViewById = ((Activity) getContext()).findViewById(i);
                this.mEnterView = viewFindViewById;
                viewFindViewById.setOnClickListener(this.mGuideOnNoDoubleClickListener);
            }
            if (i2 != 0) {
                View viewFindViewById2 = ((Activity) getContext()).findViewById(i2);
                this.mSkipView = viewFindViewById2;
                viewFindViewById2.setOnClickListener(this.mGuideOnNoDoubleClickListener);
            }
        }
    }

    public int getCurrentItem() {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager == null || this.mViews == null) {
            return 0;
        }
        return bGAViewPager.getCurrentItem() % this.mViews.size();
    }

    public int getItemCount() {
        List<View> list = this.mViews;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<? extends View> getViews() {
        return this.mViews;
    }

    public <VT extends View> VT getItemView(int i) {
        List<View> list = this.mViews;
        if (list == null) {
            return null;
        }
        return (VT) list.get(i);
    }

    public ImageView getItemImageView(int i) {
        return (ImageView) getItemView(i);
    }

    public List<String> getTips() {
        return this.mTips;
    }

    public BGAViewPager getViewPager() {
        return this.mViewPager;
    }

    @Override // android.view.View
    public void setOverScrollMode(int i) {
        this.mOverScrollMode = i;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setOverScrollMode(i);
        }
    }

    private void initIndicator() {
        LinearLayout linearLayout = this.mPointRealContainerLl;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            boolean z = this.mIsNeedShowIndicatorOnOnlyOnePage;
            if (z || (!z && this.mViews.size() > 1)) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                int i = this.mPointLeftRightMargin;
                int i2 = this.mPointTopBottomMargin;
                layoutParams.setMargins(i, i2, i, i2);
                for (int i3 = 0; i3 < this.mViews.size(); i3++) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(this.mPointDrawableResId);
                    this.mPointRealContainerLl.addView(imageView);
                }
            }
        }
        if (this.mNumberIndicatorTv != null) {
            boolean z2 = this.mIsNeedShowIndicatorOnOnlyOnePage;
            if (z2 || (!z2 && this.mViews.size() > 1)) {
                this.mNumberIndicatorTv.setVisibility(0);
            } else {
                this.mNumberIndicatorTv.setVisibility(4);
            }
        }
    }

    private void initViewPager() {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null && equals(bGAViewPager.getParent())) {
            removeView(this.mViewPager);
            this.mViewPager = null;
        }
        BGAViewPager bGAViewPager2 = new BGAViewPager(getContext());
        this.mViewPager = bGAViewPager2;
        bGAViewPager2.setOffscreenPageLimit(1);
        this.mViewPager.setAdapter(new PageAdapter());
        this.mViewPager.addOnPageChangeListener(this);
        this.mViewPager.setOverScrollMode(this.mOverScrollMode);
        this.mViewPager.setAllowUserScrollable(this.mAllowUserScrollable);
        setPageChangeDuration(this.mPageChangeDuration);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 0, 0, this.mContentBottomMargin);
        addView(this.mViewPager, 0, layoutParams);
        if (this.mEnterView != null || this.mSkipView != null) {
            this.mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { // from class: com.aivox.common_ui.bgabanner.BGABanner.2
                @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                    if (i == BGABanner.this.getItemCount() - 2) {
                        if (BGABanner.this.mEnterView != null) {
                            ViewCompat.setAlpha(BGABanner.this.mEnterView, f);
                        }
                        if (BGABanner.this.mSkipView != null) {
                            ViewCompat.setAlpha(BGABanner.this.mSkipView, 1.0f - f);
                        }
                        if (f > 0.5f) {
                            if (BGABanner.this.mEnterView != null) {
                                BGABanner.this.mEnterView.setVisibility(0);
                            }
                            if (BGABanner.this.mSkipView != null) {
                                BGABanner.this.mSkipView.setVisibility(8);
                                return;
                            }
                            return;
                        }
                        if (BGABanner.this.mEnterView != null) {
                            BGABanner.this.mEnterView.setVisibility(8);
                        }
                        if (BGABanner.this.mSkipView != null) {
                            BGABanner.this.mSkipView.setVisibility(0);
                            return;
                        }
                        return;
                    }
                    if (i == BGABanner.this.getItemCount() - 1) {
                        if (BGABanner.this.mSkipView != null) {
                            BGABanner.this.mSkipView.setVisibility(8);
                        }
                        if (BGABanner.this.mEnterView != null) {
                            BGABanner.this.mEnterView.setVisibility(0);
                            ViewCompat.setAlpha(BGABanner.this.mEnterView, 1.0f);
                            return;
                        }
                        return;
                    }
                    if (BGABanner.this.mSkipView != null) {
                        BGABanner.this.mSkipView.setVisibility(0);
                        ViewCompat.setAlpha(BGABanner.this.mSkipView, 1.0f);
                    }
                    if (BGABanner.this.mEnterView != null) {
                        BGABanner.this.mEnterView.setVisibility(8);
                    }
                }
            });
        }
        if (this.mAutoPlayAble) {
            this.mViewPager.setAutoPlayDelegate(this);
            this.mViewPager.setCurrentItem(LockFreeTaskQueueCore.MAX_CAPACITY_MASK - (LockFreeTaskQueueCore.MAX_CAPACITY_MASK % this.mViews.size()));
            startAutoPlay();
            return;
        }
        switchToPoint(0);
    }

    public void removePlaceholder() {
        ImageView imageView = this.mPlaceholderIv;
        if (imageView == null || !equals(imageView.getParent())) {
            return;
        }
        removeView(this.mPlaceholderIv);
        this.mPlaceholderIv = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mAutoPlayAble) {
            int action = motionEvent.getAction();
            if (action == 0) {
                stopAutoPlay();
            } else if (action == 1 || action == 3) {
                startAutoPlay();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setIsNeedShowIndicatorOnOnlyOnePage(boolean z) {
        this.mIsNeedShowIndicatorOnOnlyOnePage = z;
    }

    public void setCurrentItem(int i) {
        if (this.mViewPager == null || this.mViews == null) {
            return;
        }
        if (i > getItemCount() - 1) {
            return;
        }
        if (this.mAutoPlayAble) {
            int currentItem = this.mViewPager.getCurrentItem();
            int size = i - (currentItem % this.mViews.size());
            if (size < 0) {
                for (int i2 = -1; i2 >= size; i2--) {
                    this.mViewPager.setCurrentItem(currentItem + i2, false);
                }
            } else if (size > 0) {
                for (int i3 = 1; i3 <= size; i3++) {
                    this.mViewPager.setCurrentItem(currentItem + i3, false);
                }
            }
            startAutoPlay();
            return;
        }
        this.mViewPager.setCurrentItem(i, false);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view2, int i) {
        super.onVisibilityChanged(view2, i);
        if (i == 0) {
            startAutoPlay();
        } else if (i == 4 || i == 8) {
            onInvisibleToUser();
        }
    }

    private void onInvisibleToUser() {
        stopAutoPlay();
        if (!this.mIsFirstInvisible && this.mAutoPlayAble && this.mViewPager != null && getItemCount() > 0) {
            switchToNextPage();
        }
        this.mIsFirstInvisible = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onInvisibleToUser();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoPlay();
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (this.mAutoPlayAble) {
            postDelayed(this.mAutoPlayTask, this.mAutoPlayInterval);
        }
    }

    public void stopAutoPlay() {
        AutoPlayTask autoPlayTask = this.mAutoPlayTask;
        if (autoPlayTask != null) {
            removeCallbacks(autoPlayTask);
        }
    }

    private void switchToPoint(int i) {
        boolean z;
        LinearLayout linearLayout;
        boolean z2;
        if (this.mTipTv != null) {
            List<String> list = this.mTips;
            if (list == null || list.size() < 1 || i >= this.mTips.size()) {
                this.mTipTv.setVisibility(8);
            } else {
                this.mTipTv.setVisibility(0);
                this.mTipTv.setText(this.mTips.get(i));
            }
        }
        if (this.mPointRealContainerLl != null) {
            List<View> list2 = this.mViews;
            if (list2 != null && list2.size() > 0 && i < this.mViews.size() && ((z2 = this.mIsNeedShowIndicatorOnOnlyOnePage) || (!z2 && this.mViews.size() > 1))) {
                this.mPointRealContainerLl.setVisibility(0);
                int i2 = 0;
                while (i2 < this.mPointRealContainerLl.getChildCount()) {
                    this.mPointRealContainerLl.getChildAt(i2).setEnabled(i2 == i);
                    this.mPointRealContainerLl.getChildAt(i2).requestLayout();
                    i2++;
                }
            } else {
                this.mPointRealContainerLl.setVisibility(8);
            }
        }
        if (!this.mShowPointIndicator && (linearLayout = this.mPointRealContainerLl) != null) {
            linearLayout.setVisibility(8);
        }
        if (this.mNumberIndicatorTv != null) {
            List<View> list3 = this.mViews;
            if (list3 != null && list3.size() > 0 && i < this.mViews.size() && ((z = this.mIsNeedShowIndicatorOnOnlyOnePage) || (!z && this.mViews.size() > 1))) {
                this.mNumberIndicatorTv.setVisibility(0);
                this.mNumberIndicatorTv.setText((i + 1) + "/" + this.mViews.size());
            } else {
                this.mNumberIndicatorTv.setVisibility(8);
            }
        }
    }

    public void setTransitionEffect(TransitionEffect transitionEffect) {
        this.mTransitionEffect = transitionEffect;
        if (this.mViewPager != null) {
            initViewPager();
            List<View> list = this.mHackyViews;
            if (list == null) {
                BGABannerUtil.resetPageTransformer(this.mViews);
            } else {
                BGABannerUtil.resetPageTransformer(list);
            }
        }
    }

    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        BGAViewPager bGAViewPager;
        if (pageTransformer == null || (bGAViewPager = this.mViewPager) == null) {
            return;
        }
        bGAViewPager.setPageTransformer(true, pageTransformer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToNextPage() {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setCurrentItem(bGAViewPager.getCurrentItem() + 1);
        }
    }

    @Override // com.aivox.common_ui.bgabanner.BGAViewPager.AutoPlayDelegate
    public void handleAutoPlayActionUpOrCancel(float f) {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            if (this.mPageScrollPosition < bGAViewPager.getCurrentItem()) {
                if (f > 400.0f || (this.mPageScrollPositionOffset < 0.7f && f > -400.0f)) {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
                    return;
                } else {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
                    return;
                }
            }
            if (f < -400.0f || (this.mPageScrollPositionOffset > 0.3f && f < 400.0f)) {
                this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
            } else {
                this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
            }
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        int size = i % this.mViews.size();
        switchToPoint(size);
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(size);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        this.mPageScrollPosition = i;
        this.mPageScrollPositionOffset = f;
        if (this.mTipTv != null) {
            List<String> list = this.mTips;
            if (list != null && list.size() > 0) {
                this.mTipTv.setVisibility(0);
                int size = i % this.mTips.size();
                int size2 = (i + 1) % this.mTips.size();
                if (size2 < this.mTips.size() && size < this.mTips.size()) {
                    if (f > 0.5d) {
                        this.mTipTv.setText(this.mTips.get(size2));
                        ViewCompat.setAlpha(this.mTipTv, f);
                    } else {
                        ViewCompat.setAlpha(this.mTipTv, 1.0f - f);
                        this.mTipTv.setText(this.mTips.get(size));
                    }
                }
            } else {
                this.mTipTv.setVisibility(8);
            }
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i % this.mViews.size(), f, i2);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    public void setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    private class PageAdapter extends PagerAdapter {
        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view2, Object obj) {
            return view2 == obj;
        }

        private PageAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            if (BGABanner.this.mViews == null) {
                return 0;
            }
            if (BGABanner.this.mAutoPlayAble) {
                return Integer.MAX_VALUE;
            }
            return BGABanner.this.mViews.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            int size = i % BGABanner.this.mViews.size();
            View view2 = BGABanner.this.mHackyViews == null ? (View) BGABanner.this.mViews.get(size) : (View) BGABanner.this.mHackyViews.get(i % BGABanner.this.mHackyViews.size());
            if (BGABanner.this.mDelegate != null) {
                view2.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.aivox.common_ui.bgabanner.BGABanner.PageAdapter.1
                    @Override // com.aivox.common_ui.bgabanner.BGAOnNoDoubleClickListener
                    public void onNoDoubleClick(View view3) {
                        int currentItem = BGABanner.this.mViewPager.getCurrentItem() % BGABanner.this.mViews.size();
                        BGABanner.this.mDelegate.onBannerItemClick(BGABanner.this, view3, BGABanner.this.mModels == null ? null : BGABanner.this.mModels.get(currentItem), currentItem);
                    }
                });
            }
            if (BGABanner.this.mAdapter != null) {
                Adapter adapter = BGABanner.this.mAdapter;
                BGABanner bGABanner = BGABanner.this;
                adapter.fillBannerItem(bGABanner, view2, bGABanner.mModels == null ? null : BGABanner.this.mModels.get(size), size);
            }
            ViewParent parent = view2.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(view2);
            }
            viewGroup.addView(view2);
            return view2;
        }
    }

    private static class AutoPlayTask implements Runnable {
        private final WeakReference<BGABanner> mBanner;

        private AutoPlayTask(BGABanner bGABanner) {
            this.mBanner = new WeakReference<>(bGABanner);
        }

        @Override // java.lang.Runnable
        public void run() {
            BGABanner bGABanner = this.mBanner.get();
            if (bGABanner != null) {
                bGABanner.switchToNextPage();
                bGABanner.startAutoPlay();
            }
        }
    }
}
