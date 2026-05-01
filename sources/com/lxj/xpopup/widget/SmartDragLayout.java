package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import com.aivox.base.common.Constant;
import com.lxj.xpopup.enums.LayoutStatus;

/* JADX INFO: loaded from: classes4.dex */
public class SmartDragLayout extends LinearLayout implements NestedScrollingParent {
    private View child;
    boolean dismissOnTouchOutside;
    int duration;
    boolean enableDrag;
    boolean isScrollUp;
    boolean isThreeDrag;
    boolean isUserClose;
    int lastHeight;
    private OnCloseListener listener;
    int maxY;
    int minY;
    OverScroller scroller;
    LayoutStatus status;
    float touchX;
    float touchY;
    VelocityTracker tracker;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int i, float f, boolean z);

        void onOpen();
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return 2;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view2, float f, float f2) {
        return false;
    }

    public SmartDragLayout(Context context) {
        this(context, null);
    }

    public SmartDragLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SmartDragLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.enableDrag = true;
        this.dismissOnTouchOutside = true;
        this.isUserClose = false;
        this.isThreeDrag = false;
        this.status = LayoutStatus.Close;
        this.duration = Constant.EVENT.BLE_GLASS_ASK_AI;
        if (this.enableDrag) {
            this.scroller = new OverScroller(context);
        }
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view2) {
        super.onViewAdded(view2);
        this.child = view2;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.maxY = this.child.getMeasuredHeight();
        this.minY = 0;
        int measuredWidth = (getMeasuredWidth() / 2) - (this.child.getMeasuredWidth() / 2);
        this.child.layout(measuredWidth, getMeasuredHeight(), this.child.getMeasuredWidth() + measuredWidth, getMeasuredHeight() + this.maxY);
        if (this.status == LayoutStatus.Open) {
            if (this.isThreeDrag) {
                scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
            } else {
                scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
            }
        }
        this.lastHeight = this.maxY;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.isUserClose = true;
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0057  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instruction units count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.SmartDragLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void finishScroll() {
        int scrollY;
        if (this.enableDrag) {
            int scrollY2 = (getScrollY() > (this.isScrollUp ? this.maxY - this.minY : (this.maxY - this.minY) * 2) / 3 ? this.maxY : this.minY) - getScrollY();
            if (this.isThreeDrag) {
                int i = this.maxY / 3;
                float f = i;
                float f2 = 2.5f * f;
                if (getScrollY() > f2) {
                    i = this.maxY;
                    scrollY = getScrollY();
                } else if (getScrollY() <= f2 && getScrollY() > f * 1.5f) {
                    i *= 2;
                    scrollY = getScrollY();
                } else if (getScrollY() > i) {
                    scrollY = getScrollY();
                } else {
                    i = this.minY;
                    scrollY = getScrollY();
                }
                scrollY2 = i - scrollY;
            }
            this.scroller.startScroll(getScrollX(), getScrollY(), 0, scrollY2, this.duration);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        int i3 = this.maxY;
        if (i2 > i3) {
            i2 = i3;
        }
        int i4 = this.minY;
        if (i2 < i4) {
            i2 = i4;
        }
        float f = ((i2 - i4) * 1.0f) / (i3 - i4);
        this.isScrollUp = i2 > getScrollY();
        if (this.listener != null) {
            if (this.isUserClose && f == 0.0f && this.status != LayoutStatus.Close) {
                this.status = LayoutStatus.Close;
                this.listener.onClose();
            } else if (f == 1.0f && this.status != LayoutStatus.Open) {
                this.status = LayoutStatus.Open;
                this.listener.onOpen();
            }
            this.listener.onDrag(i2, f, this.isScrollUp);
        }
        super.scrollTo(i, i2);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isScrollUp = false;
        this.isUserClose = false;
        setTranslationY(0.0f);
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.1
            @Override // java.lang.Runnable
            public void run() {
                int scrollY = SmartDragLayout.this.maxY - SmartDragLayout.this.getScrollY();
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                if (smartDragLayout.enableDrag && SmartDragLayout.this.isThreeDrag) {
                    scrollY /= 3;
                }
                smartDragLayout.smoothScroll(scrollY, true);
                SmartDragLayout.this.status = LayoutStatus.Opening;
            }
        });
    }

    public void close() {
        this.isUserClose = true;
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.2
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout.this.scroller.abortAnimation();
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                smartDragLayout.smoothScroll(smartDragLayout.minY - SmartDragLayout.this.getScrollY(), false);
                SmartDragLayout.this.status = LayoutStatus.Closing;
            }
        });
    }

    public void smoothScroll(final int i, final boolean z) {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.3
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout.this.scroller.startScroll(SmartDragLayout.this.getScrollX(), SmartDragLayout.this.getScrollY(), 0, i, (int) (z ? SmartDragLayout.this.duration : SmartDragLayout.this.duration * 0.8f));
                ViewCompat.postInvalidateOnAnimation(SmartDragLayout.this);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view2, View view3, int i) {
        return i == 2 && this.enableDrag;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view2, View view3, int i) {
        this.scroller.abortAnimation();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view2) {
        finishScroll();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view2, int i, int i2, int i3, int i4) {
        scrollTo(getScrollX(), getScrollY() + i4);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view2, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            int scrollY = getScrollY() + i2;
            if (scrollY < this.maxY) {
                iArr[1] = i2;
            }
            scrollTo(getScrollX(), scrollY);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view2, float f, float f2, boolean z) {
        if (getScrollY() <= this.minY || getScrollY() >= this.maxY || f2 >= -1500.0f || this.isThreeDrag) {
            return false;
        }
        close();
        return false;
    }

    public void isThreeDrag(boolean z) {
        this.isThreeDrag = z;
    }

    public void enableDrag(boolean z) {
        this.enableDrag = z;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public void dismissOnTouchOutside(boolean z) {
        this.dismissOnTouchOutside = z;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }
}
