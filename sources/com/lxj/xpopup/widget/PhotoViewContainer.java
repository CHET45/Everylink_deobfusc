package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.interfaces.OnDragChangeListener;
import com.lxj.xpopup.photoview.PhotoView;

/* JADX INFO: loaded from: classes4.dex */
public class PhotoViewContainer extends FrameLayout {
    private static final String TAG = "PhotoViewContainer";
    private int HideTopThreshold;

    /* JADX INFO: renamed from: cb */
    ViewDragHelper.Callback f862cb;
    private OnDragChangeListener dragChangeListener;
    private ViewDragHelper dragHelper;
    public boolean isReleasing;
    boolean isVertical;
    private int maxOffset;
    private float touchX;
    private float touchY;
    public ViewPager viewPager;

    public PhotoViewContainer(Context context) {
        this(context, null);
    }

    public PhotoViewContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PhotoViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.HideTopThreshold = 80;
        this.isReleasing = false;
        this.isVertical = false;
        this.f862cb = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PhotoViewContainer.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view2) {
                return 1;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view2, int i2) {
                return !PhotoViewContainer.this.isReleasing;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view2, int i2, int i3) {
                int top2 = PhotoViewContainer.this.viewPager.getTop() + (i3 / 2);
                return top2 >= 0 ? Math.min(top2, PhotoViewContainer.this.maxOffset) : -Math.min(-top2, PhotoViewContainer.this.maxOffset);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view2, int i2, int i3, int i4, int i5) {
                super.onViewPositionChanged(view2, i2, i3, i4, i5);
                if (view2 != PhotoViewContainer.this.viewPager) {
                    PhotoViewContainer.this.viewPager.offsetTopAndBottom(i5);
                }
                float fAbs = (Math.abs(i3) * 1.0f) / PhotoViewContainer.this.maxOffset;
                float f = 1.0f - (0.2f * fAbs);
                PhotoViewContainer.this.viewPager.setScaleX(f);
                PhotoViewContainer.this.viewPager.setScaleY(f);
                view2.setScaleX(f);
                view2.setScaleY(f);
                if (PhotoViewContainer.this.dragChangeListener != null) {
                    PhotoViewContainer.this.dragChangeListener.onDragChange(i5, f, fAbs);
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view2, float f, float f2) {
                super.onViewReleased(view2, f, f2);
                if (Math.abs(view2.getTop()) > PhotoViewContainer.this.HideTopThreshold) {
                    if (PhotoViewContainer.this.dragChangeListener != null) {
                        PhotoViewContainer.this.dragChangeListener.onRelease();
                    }
                } else {
                    PhotoViewContainer.this.dragHelper.smoothSlideViewTo(PhotoViewContainer.this.viewPager, 0, 0);
                    PhotoViewContainer.this.dragHelper.smoothSlideViewTo(view2, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(PhotoViewContainer.this);
                }
            }
        };
        init();
    }

    private void init() {
        this.HideTopThreshold = dip2px(this.HideTopThreshold);
        this.dragHelper = ViewDragHelper.create(this, this.f862cb);
        setBackgroundColor(0);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.viewPager = (ViewPager) getChildAt(0);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.maxOffset = getHeight() / 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004c A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:6:0x000c, B:15:0x001c, B:19:0x003d, B:20:0x004c, B:21:0x0054), top: B:26:0x000c }] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getPointerCount()
            r1 = 1
            if (r0 <= r1) goto Lc
            boolean r6 = super.dispatchTouchEvent(r6)
            return r6
        Lc:
            int r0 = r6.getAction()     // Catch: java.lang.Exception -> L60
            if (r0 == 0) goto L54
            r2 = 0
            if (r0 == r1) goto L4c
            r3 = 2
            if (r0 == r3) goto L1c
            r1 = 3
            if (r0 == r1) goto L4c
            goto L60
        L1c:
            float r0 = r6.getX()     // Catch: java.lang.Exception -> L60
            float r3 = r5.touchX     // Catch: java.lang.Exception -> L60
            float r0 = r0 - r3
            float r3 = r6.getY()     // Catch: java.lang.Exception -> L60
            float r4 = r5.touchY     // Catch: java.lang.Exception -> L60
            float r3 = r3 - r4
            androidx.viewpager.widget.ViewPager r4 = r5.viewPager     // Catch: java.lang.Exception -> L60
            r4.dispatchTouchEvent(r6)     // Catch: java.lang.Exception -> L60
            float r3 = java.lang.Math.abs(r3)     // Catch: java.lang.Exception -> L60
            float r0 = java.lang.Math.abs(r0)     // Catch: java.lang.Exception -> L60
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L3c
            goto L3d
        L3c:
            r1 = r2
        L3d:
            r5.isVertical = r1     // Catch: java.lang.Exception -> L60
            float r0 = r6.getX()     // Catch: java.lang.Exception -> L60
            r5.touchX = r0     // Catch: java.lang.Exception -> L60
            float r0 = r6.getY()     // Catch: java.lang.Exception -> L60
            r5.touchY = r0     // Catch: java.lang.Exception -> L60
            goto L60
        L4c:
            r0 = 0
            r5.touchX = r0     // Catch: java.lang.Exception -> L60
            r5.touchY = r0     // Catch: java.lang.Exception -> L60
            r5.isVertical = r2     // Catch: java.lang.Exception -> L60
            goto L60
        L54:
            float r0 = r6.getX()     // Catch: java.lang.Exception -> L60
            r5.touchX = r0     // Catch: java.lang.Exception -> L60
            float r0 = r6.getY()     // Catch: java.lang.Exception -> L60
            r5.touchY = r0     // Catch: java.lang.Exception -> L60
        L60:
            boolean r6 = super.dispatchTouchEvent(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.PhotoViewContainer.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean isTopOrBottomEnd() {
        View currentImageView = getCurrentImageView();
        if (currentImageView instanceof PhotoView) {
            PhotoView photoView = (PhotoView) currentImageView;
            return photoView.attacher.isTopEnd || photoView.attacher.isBottomEnd;
        }
        return false;
    }

    private View getCurrentImageView() {
        ViewPager viewPager = this.viewPager;
        FrameLayout frameLayout = (FrameLayout) viewPager.getChildAt(viewPager.getCurrentItem());
        if (frameLayout == null) {
            return null;
        }
        return frameLayout.getChildAt(0);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zShouldInterceptTouchEvent = this.dragHelper.shouldInterceptTouchEvent(motionEvent);
        if (motionEvent.getPointerCount() > 1 && motionEvent.getAction() == 2) {
            return false;
        }
        if (isTopOrBottomEnd() && this.isVertical) {
            return true;
        }
        return zShouldInterceptTouchEvent && this.isVertical;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        try {
            this.dragHelper.processTouchEvent(motionEvent);
        } catch (Exception unused) {
        }
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.dragHelper.continueSettling(false)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public int dip2px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setOnDragChangeListener(OnDragChangeListener onDragChangeListener) {
        this.dragChangeListener = onDragChangeListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isReleasing = false;
    }
}
