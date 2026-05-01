package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes.dex */
public class NoScrollViewPager extends ViewPager {
    private boolean noScroll;
    private OnTouchHandleListener onTouchHandleListener;

    public interface OnTouchHandleListener {
        void onTouched();
    }

    public void setNoScroll(boolean z) {
        this.noScroll = z;
    }

    public NoScrollViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.noScroll = true;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.noScroll) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        OnTouchHandleListener onTouchHandleListener = this.onTouchHandleListener;
        if (onTouchHandleListener != null) {
            onTouchHandleListener.onTouched();
        }
        if (this.noScroll) {
            return false;
        }
        try {
            return super.onInterceptTouchEvent(motionEvent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setOnTouchHandleListener(OnTouchHandleListener onTouchHandleListener) {
        this.onTouchHandleListener = onTouchHandleListener;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i) {
        super.setCurrentItem(i, false);
    }
}
