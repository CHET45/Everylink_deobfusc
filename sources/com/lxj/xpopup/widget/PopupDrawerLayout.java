package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.animator.ShadowBgAnimator;
import com.lxj.xpopup.enums.LayoutStatus;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PopupDrawerLayout extends FrameLayout {
    ShadowBgAnimator bgAnimator;
    ViewDragHelper.Callback callback;
    boolean canChildScrollLeft;
    float downX;
    float downY;
    ViewDragHelper dragHelper;
    public boolean enableDrag;
    public boolean enableShadow;
    float fraction;
    boolean hasLayout;
    public boolean isDismissOnTouchOutside;
    public boolean isDrawStatusBarShadow;
    boolean isIntercept;
    boolean isToLeft;
    private OnCloseListener listener;
    View mChild;
    View placeHolder;
    public PopupPosition position;
    LayoutStatus status;

    /* JADX INFO: renamed from: ty */
    float f863ty;

    /* JADX INFO: renamed from: x */
    float f864x;

    /* JADX INFO: renamed from: y */
    float f865y;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int i, float f, boolean z);

        void onOpen();
    }

    public PopupDrawerLayout(Context context) {
        this(context, null);
    }

    public PopupDrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PopupDrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.status = null;
        this.position = PopupPosition.Left;
        this.isDrawStatusBarShadow = false;
        this.fraction = 0.0f;
        this.enableShadow = true;
        this.enableDrag = true;
        this.hasLayout = false;
        this.isIntercept = false;
        ViewDragHelper.Callback callback = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(View view2) {
                return 1;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view2, int i2) {
                return (PopupDrawerLayout.this.dragHelper.continueSettling(true) || PopupDrawerLayout.this.status == LayoutStatus.Close) ? false : true;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view2, int i2, int i3) {
                return view2 == PopupDrawerLayout.this.placeHolder ? i2 : PopupDrawerLayout.this.fixLeft(i2);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view2, int i2, int i3, int i4, int i5) {
                super.onViewPositionChanged(view2, i2, i3, i4, i5);
                if (view2 == PopupDrawerLayout.this.placeHolder) {
                    PopupDrawerLayout.this.placeHolder.layout(0, 0, PopupDrawerLayout.this.placeHolder.getMeasuredWidth(), PopupDrawerLayout.this.placeHolder.getMeasuredHeight());
                    PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                    int iFixLeft = popupDrawerLayout.fixLeft(popupDrawerLayout.mChild.getLeft() + i4);
                    PopupDrawerLayout.this.mChild.layout(iFixLeft, PopupDrawerLayout.this.mChild.getTop(), PopupDrawerLayout.this.mChild.getMeasuredWidth() + iFixLeft, PopupDrawerLayout.this.mChild.getBottom());
                    calcFraction(iFixLeft, i4);
                    return;
                }
                calcFraction(i2, i4);
            }

            private void calcFraction(int i2, int i3) {
                if (PopupDrawerLayout.this.position == PopupPosition.Left) {
                    PopupDrawerLayout.this.fraction = ((r0.mChild.getMeasuredWidth() + i2) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (i2 == (-PopupDrawerLayout.this.mChild.getMeasuredWidth()) && PopupDrawerLayout.this.listener != null && PopupDrawerLayout.this.status != LayoutStatus.Close) {
                        PopupDrawerLayout.this.status = LayoutStatus.Close;
                        PopupDrawerLayout.this.listener.onClose();
                    }
                } else if (PopupDrawerLayout.this.position == PopupPosition.Right) {
                    PopupDrawerLayout.this.fraction = ((r0.getMeasuredWidth() - i2) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (i2 == PopupDrawerLayout.this.getMeasuredWidth() && PopupDrawerLayout.this.listener != null && PopupDrawerLayout.this.status != LayoutStatus.Close) {
                        PopupDrawerLayout.this.status = LayoutStatus.Close;
                        PopupDrawerLayout.this.listener.onClose();
                    }
                }
                if (PopupDrawerLayout.this.enableShadow) {
                    PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                    popupDrawerLayout.setBackgroundColor(popupDrawerLayout.bgAnimator.calculateBgColor(PopupDrawerLayout.this.fraction));
                }
                if (PopupDrawerLayout.this.listener != null) {
                    PopupDrawerLayout.this.listener.onDrag(i2, PopupDrawerLayout.this.fraction, i3 < 0);
                    if (PopupDrawerLayout.this.fraction != 1.0f || PopupDrawerLayout.this.status == LayoutStatus.Open) {
                        return;
                    }
                    PopupDrawerLayout.this.status = LayoutStatus.Open;
                    PopupDrawerLayout.this.listener.onOpen();
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view2, float f, float f2) {
                int measuredWidth;
                int measuredWidth2;
                super.onViewReleased(view2, f, f2);
                if (view2 == PopupDrawerLayout.this.placeHolder && f == 0.0f) {
                    if (PopupDrawerLayout.this.isDismissOnTouchOutside) {
                        PopupDrawerLayout.this.close();
                        return;
                    }
                    return;
                }
                if (view2 == PopupDrawerLayout.this.mChild && PopupDrawerLayout.this.isToLeft && !PopupDrawerLayout.this.canChildScrollLeft && f < -500.0f) {
                    PopupDrawerLayout.this.close();
                    return;
                }
                if (PopupDrawerLayout.this.position == PopupPosition.Left) {
                    if (f < -1000.0f) {
                        measuredWidth2 = PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    } else {
                        if (PopupDrawerLayout.this.mChild.getLeft() < (-PopupDrawerLayout.this.mChild.getMeasuredWidth()) / 2) {
                            measuredWidth2 = PopupDrawerLayout.this.mChild.getMeasuredWidth();
                        } else {
                            measuredWidth = 0;
                        }
                    }
                    measuredWidth = -measuredWidth2;
                } else if (f > 1000.0f) {
                    measuredWidth = PopupDrawerLayout.this.getMeasuredWidth();
                } else {
                    measuredWidth = view2.getLeft() < PopupDrawerLayout.this.getMeasuredWidth() - (PopupDrawerLayout.this.mChild.getMeasuredWidth() / 2) ? PopupDrawerLayout.this.getMeasuredWidth() - PopupDrawerLayout.this.mChild.getMeasuredWidth() : PopupDrawerLayout.this.getMeasuredWidth();
                }
                PopupDrawerLayout.this.dragHelper.smoothSlideViewTo(PopupDrawerLayout.this.mChild, measuredWidth, view2.getTop());
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        };
        this.callback = callback;
        this.isDismissOnTouchOutside = true;
        this.dragHelper = ViewDragHelper.create(this, callback);
    }

    public void setDrawerPosition(PopupPosition popupPosition) {
        this.position = popupPosition;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.placeHolder = getChildAt(0);
        this.mChild = getChildAt(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f863ty = getTranslationY();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.placeHolder.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if (!this.hasLayout) {
            if (this.position == PopupPosition.Left) {
                View view2 = this.mChild;
                view2.layout(-view2.getMeasuredWidth(), 0, 0, getMeasuredHeight());
            } else {
                this.mChild.layout(getMeasuredWidth(), 0, getMeasuredWidth() + this.mChild.getMeasuredWidth(), getMeasuredHeight());
            }
            this.hasLayout = true;
            return;
        }
        View view3 = this.mChild;
        view3.layout(view3.getLeft(), this.mChild.getTop(), this.mChild.getRight(), this.mChild.getMeasuredHeight());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005c  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.enableDrag
            if (r0 != 0) goto L9
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        L9:
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            r1 = 1
            boolean r0 = r0.continueSettling(r1)
            if (r0 != 0) goto La3
            com.lxj.xpopup.enums.LayoutStatus r0 = r5.status
            com.lxj.xpopup.enums.LayoutStatus r2 = com.lxj.xpopup.enums.LayoutStatus.Close
            if (r0 != r2) goto L1a
            goto La3
        L1a:
            float r0 = r6.getX()
            float r2 = r5.f864x
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r2 = 0
            if (r0 >= 0) goto L27
            r0 = r1
            goto L28
        L27:
            r0 = r2
        L28:
            r5.isToLeft = r0
            float r0 = r6.getX()
            r5.f864x = r0
            float r0 = r6.getY()
            r5.f865y = r0
            int r0 = r6.getAction()
            if (r0 == 0) goto L62
            if (r0 == r1) goto L5c
            r3 = 2
            if (r0 == r3) goto L45
            r2 = 3
            if (r0 == r2) goto L5c
            goto L6e
        L45:
            float r0 = r5.f864x
            float r3 = r5.downX
            float r0 = r0 - r3
            float r0 = java.lang.Math.abs(r0)
            float r3 = r5.f865y
            float r4 = r5.downY
            float r3 = r3 - r4
            float r3 = java.lang.Math.abs(r3)
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L6e
            return r2
        L5c:
            r0 = 0
            r5.f864x = r0
            r5.f865y = r0
            goto L6e
        L62:
            float r0 = r6.getX()
            r5.downX = r0
            float r0 = r6.getY()
            r5.downY = r0
        L6e:
            float r0 = r6.getX()
            float r2 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r2, r1)
            r5.canChildScrollLeft = r0
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            boolean r0 = r0.shouldInterceptTouchEvent(r6)
            r5.isIntercept = r0
            boolean r1 = r5.isToLeft
            if (r1 == 0) goto L8d
            boolean r1 = r5.canChildScrollLeft
            if (r1 != 0) goto L8d
            return r0
        L8d:
            float r0 = r6.getX()
            float r1 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r1)
            if (r0 != 0) goto L9e
            boolean r6 = r5.isIntercept
            return r6
        L9e:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        La3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.PopupDrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean canScroll(ViewGroup viewGroup, float f, float f2, int i) {
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            int[] iArr = new int[2];
            childAt.getLocationInWindow(iArr);
            int i3 = iArr[0];
            if (XPopupUtils.isInRect(f, f2, new Rect(i3, iArr[1], childAt.getWidth() + i3, iArr[1] + childAt.getHeight())) && (childAt instanceof ViewGroup)) {
                if (childAt instanceof ViewPager) {
                    ViewPager viewPager = (ViewPager) childAt;
                    if (i == 0) {
                        return viewPager.canScrollHorizontally(-1) || viewPager.canScrollHorizontally(1);
                    }
                    return viewPager.canScrollHorizontally(i);
                }
                if (childAt instanceof HorizontalScrollView) {
                    HorizontalScrollView horizontalScrollView = (HorizontalScrollView) childAt;
                    if (i == 0) {
                        return horizontalScrollView.canScrollHorizontally(-1) || horizontalScrollView.canScrollHorizontally(1);
                    }
                    return horizontalScrollView.canScrollHorizontally(i);
                }
                return canScroll((ViewGroup) childAt, f, f2, i);
            }
        }
        return false;
    }

    private boolean canScroll(ViewGroup viewGroup, float f, float f2) {
        return canScroll(viewGroup, f, f2, 0);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enableDrag) {
            return super.onTouchEvent(motionEvent);
        }
        if (this.dragHelper.continueSettling(true)) {
            return true;
        }
        this.dragHelper.processTouchEvent(motionEvent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int fixLeft(int i) {
        if (this.position == PopupPosition.Left) {
            if (i < (-this.mChild.getMeasuredWidth())) {
                i = -this.mChild.getMeasuredWidth();
            }
            if (i > 0) {
                return 0;
            }
            return i;
        }
        if (this.position != PopupPosition.Right) {
            return i;
        }
        if (i < getMeasuredWidth() - this.mChild.getMeasuredWidth()) {
            i = getMeasuredWidth() - this.mChild.getMeasuredWidth();
        }
        return i > getMeasuredWidth() ? getMeasuredWidth() : i;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.2
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout.this.dragHelper.smoothSlideViewTo(PopupDrawerLayout.this.mChild, PopupDrawerLayout.this.position == PopupPosition.Left ? 0 : PopupDrawerLayout.this.mChild.getLeft() - PopupDrawerLayout.this.mChild.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    public void close() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.3
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout.this.dragHelper.abort();
                PopupDrawerLayout.this.dragHelper.smoothSlideViewTo(PopupDrawerLayout.this.mChild, PopupDrawerLayout.this.position == PopupPosition.Left ? -PopupDrawerLayout.this.mChild.getMeasuredWidth() : PopupDrawerLayout.this.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    public void setBgAnimator(ShadowBgAnimator shadowBgAnimator) {
        this.bgAnimator = shadowBgAnimator;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }
}
