package com.aivox.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class DZStickyNavLayouts extends LinearLayout implements NestedScrollingParent {
    private static final int MAX_WIDTH = 500;
    private boolean isRunAnim;
    boolean isVertical;
    private View mChildView;
    private int mDrag;
    private View mFooterView;
    private View mHeaderView;

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view2, float f, float f2, boolean z) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view2, int i, int i2, int i3, int i4) {
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view2, View view3, int i) {
    }

    public DZStickyNavLayouts(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrag = 2;
        this.isVertical = false;
        setOrientation(0);
        View view2 = new View(context);
        this.mHeaderView = view2;
        view2.setBackgroundColor(-16091);
        View view3 = new View(context);
        this.mFooterView = view3;
        view3.setBackgroundColor(-49514);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mChildView = getChildAt(0);
        boolean z = this.isVertical;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(z ? -1 : 500, z ? 500 : -1);
        addView(this.mHeaderView, 0, layoutParams);
        addView(this.mFooterView, getChildCount(), layoutParams);
        boolean z2 = this.isVertical;
        scrollBy(z2 ? 0 : 500, z2 ? 500 : 0);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mChildView.getLayoutParams().width = getMeasuredWidth();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view2, View view3, int i) {
        return (view3 instanceof RecyclerView) && !this.isRunAnim;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view2) {
        startAnimation(new ProgressAnimation());
    }

    private class ProgressAnimation extends Animation {
        private float endProgress;
        private float startProgress;

        private ProgressAnimation() {
            this.startProgress = 0.0f;
            this.endProgress = 1.0f;
            DZStickyNavLayouts.this.isRunAnim = true;
        }

        @Override // android.view.animation.Animation
        protected void applyTransformation(float f, Transformation transformation) {
            float f2 = this.endProgress;
            float f3 = this.startProgress;
            float f4 = ((f2 - f3) * f) + f3;
            DZStickyNavLayouts.this.scrollBy((int) ((500 - r3.getScrollX()) * f4), 0);
            if (f4 == 1.0f) {
                DZStickyNavLayouts.this.isRunAnim = false;
            }
        }

        @Override // android.view.animation.Animation
        public void initialize(int i, int i2, int i3, int i4) {
            super.initialize(i, i2, i3, i4);
            setDuration(260L);
            setInterpolator(new AccelerateInterpolator());
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view2, int i, int i2, int[] iArr) {
        getParent().requestDisallowInterceptTouchEvent(true);
        boolean z = i > 0 && getScrollX() < 500 && !ViewCompat.canScrollHorizontally(view2, -1);
        boolean z2 = i < 0 && !ViewCompat.canScrollHorizontally(view2, -1);
        boolean z3 = i < 0 && getScrollX() > 500 && !ViewCompat.canScrollHorizontally(view2, 1);
        boolean z4 = i > 0 && !ViewCompat.canScrollHorizontally(view2, 1);
        if (z || z2 || z3 || z4) {
            scrollBy(i / this.mDrag, 0);
            iArr[0] = i;
        }
        if (i > 0 && getScrollX() > 500 && !ViewCompat.canScrollHorizontally(view2, -1)) {
            scrollTo(500, 0);
        }
        if (i >= 0 || getScrollX() >= 500 || ViewCompat.canScrollHorizontally(view2, 1)) {
            return;
        }
        scrollTo(500, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view2, float f, float f2) {
        return getScrollX() != 500;
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (i < 0) {
            i = 0;
        } else if (i > 1000) {
            i = 1000;
        }
        super.scrollTo(i, i2);
    }
}
