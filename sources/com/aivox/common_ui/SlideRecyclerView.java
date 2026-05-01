package com.aivox.common_ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class SlideRecyclerView extends RecyclerView {
    private static final int INVALID_CHILD_WIDTH = -1;
    private static final int INVALID_POSITION = -1;
    private static final int SNAP_VELOCITY = 60;
    private static final String TAG = "SlideRecyclerView";
    public boolean canSlide;
    private float mFirstX;
    private float mFirstY;
    private ViewGroup mFlingView;
    public boolean mIsSlide;
    private float mLastX;
    private int mMenuViewWidth;
    private int mPosition;
    private Scroller mScroller;
    private Rect mTouchFrame;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    float startX;

    public void setCanSlide(boolean z) {
        this.canSlide = z;
    }

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlideRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.canSlide = true;
        this.startX = 0.0f;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScroller = new Scroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startX = motionEvent.getX();
        } else if (action == 2) {
            if (motionEvent.getX() < this.startX) {
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0065, code lost:
    
        if (java.lang.Math.abs(r0 - r7.mFirstX) > java.lang.Math.abs(r1 - r7.mFirstY)) goto L15;
     */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instruction units count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common_ui.SlideRecyclerView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsSlide && this.mPosition != -1) {
            float x = motionEvent.getX();
            obtainVelocity(motionEvent);
            int action = motionEvent.getAction();
            if (action != 1) {
                if (action == 2 && this.mMenuViewWidth != -1) {
                    float f = this.mLastX - x;
                    if (this.mFlingView.getScrollX() + f <= this.mMenuViewWidth && this.mFlingView.getScrollX() + f > 0.0f) {
                        this.mFlingView.scrollBy((int) f, 0);
                    }
                    this.mLastX = x;
                }
            } else {
                if (this.mMenuViewWidth != -1) {
                    int scrollX = this.mFlingView.getScrollX();
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    if (this.mVelocityTracker.getXVelocity() < -60.0f) {
                        Scroller scroller = this.mScroller;
                        int i = this.mMenuViewWidth;
                        scroller.startScroll(scrollX, 0, i - scrollX, 0, Math.abs(i - scrollX));
                    } else if (this.mVelocityTracker.getXVelocity() >= 60.0f) {
                        this.mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                    } else {
                        int i2 = this.mMenuViewWidth;
                        if (scrollX >= i2 / 2) {
                            this.mScroller.startScroll(scrollX, 0, i2 - scrollX, 0, Math.abs(i2 - scrollX));
                        } else {
                            this.mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        }
                    }
                    invalidate();
                }
                this.mMenuViewWidth = -1;
                this.mIsSlide = false;
                this.mPosition = -1;
                releaseVelocity();
            }
            return true;
        }
        closeMenu();
        releaseVelocity();
        return super.onTouchEvent(motionEvent);
    }

    private void releaseVelocity() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void obtainVelocity(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    public int pointToPosition(int i, int i2) {
        int iFindFirstVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Rect rect = this.mTouchFrame;
        if (rect == null) {
            rect = new Rect();
            this.mTouchFrame = rect;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i, i2)) {
                    return iFindFirstVisibleItemPosition + childCount;
                }
            }
        }
        return -1;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingView.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            invalidate();
        }
    }

    public void closeMenu() {
        ViewGroup viewGroup = this.mFlingView;
        if (viewGroup == null || viewGroup.getScrollX() == 0) {
            return;
        }
        this.mFlingView.scrollTo(0, 0);
    }

    public void showMenu() {
        Scroller scroller = this.mScroller;
        int i = this.mMenuViewWidth;
        scroller.startScroll(0, 0, i, 0, Math.abs(i));
        invalidate();
    }

    public boolean isChildViewVisible() {
        ViewGroup viewGroup = this.mFlingView;
        return (viewGroup == null || viewGroup.getScrollX() == 0) ? false : true;
    }
}
