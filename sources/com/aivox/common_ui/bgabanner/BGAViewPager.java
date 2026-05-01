package com.aivox.common_ui.bgabanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import com.aivox.base.util.BaseAppUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class BGAViewPager extends ViewPager {
    private boolean mAllowUserScrollable;
    private AutoPlayDelegate mAutoPlayDelegate;

    public interface AutoPlayDelegate {
        void handleAutoPlayActionUpOrCancel(float f);
    }

    public BGAViewPager(Context context) {
        super(context);
        this.mAllowUserScrollable = true;
    }

    public BGAViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAllowUserScrollable = true;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setPageTransformer(boolean z, ViewPager.PageTransformer pageTransformer) {
        boolean z2 = pageTransformer != null;
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mPageTransformer");
            declaredField.setAccessible(true);
            boolean z3 = z2 != (((ViewPager.PageTransformer) declaredField.get(this)) != null);
            declaredField.set(this, pageTransformer);
            Method declaredMethod = ViewPager.class.getDeclaredMethod("setChildrenDrawingOrderEnabledCompat", Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, Boolean.valueOf(z2));
            Field declaredField2 = ViewPager.class.getDeclaredField("mDrawingOrder");
            declaredField2.setAccessible(true);
            if (z2) {
                declaredField2.setInt(this, z ? 2 : 1);
            } else {
                declaredField2.setInt(this, 0);
            }
            if (z3) {
                Method declaredMethod2 = ViewPager.class.getDeclaredMethod("populate", new Class[0]);
                declaredMethod2.setAccessible(true);
                declaredMethod2.invoke(this, new Object[0]);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public void setPageChangeDuration(int i) {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this, new BGABannerScroller(getContext(), i));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public void setBannerCurrentItemInternal(int i, boolean z) {
        try {
            Method declaredMethod = ViewPager.class.getDeclaredMethod("setCurrentItemInternal", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, Integer.valueOf(i), Boolean.valueOf(z), true);
            ViewCompat.postInvalidateOnAnimation(this);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public void setAllowUserScrollable(boolean z) {
        this.mAllowUserScrollable = z;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mAllowUserScrollable) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mAllowUserScrollable) {
            return false;
        }
        if (this.mAutoPlayDelegate != null && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1)) {
            this.mAutoPlayDelegate.handleAutoPlayActionUpOrCancel(getXVelocity());
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    private float getXVelocity() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mVelocityTracker");
            declaredField.setAccessible(true);
            VelocityTracker velocityTracker = (VelocityTracker) declaredField.get(this);
            Field declaredField2 = ViewPager.class.getDeclaredField("mActivePointerId");
            declaredField2.setAccessible(true);
            ViewPager.class.getDeclaredField("mMaximumVelocity").setAccessible(true);
            velocityTracker.computeCurrentVelocity(1000, r0.getInt(this));
            return VelocityTrackerCompat.getXVelocity(velocityTracker, declaredField2.getInt(this));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0.0f;
        }
    }

    public void setAutoPlayDelegate(AutoPlayDelegate autoPlayDelegate) {
        this.mAutoPlayDelegate = autoPlayDelegate;
    }
}
