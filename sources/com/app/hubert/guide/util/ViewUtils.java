package com.app.hubert.guide.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes.dex */
public class ViewUtils {
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

    public static Rect getLocationInView(View view2, View view3) {
        if (view3 == null || view2 == null) {
            throw new IllegalArgumentException("parent and child can not be null .");
        }
        Context context = view3.getContext();
        View decorView = context instanceof Activity ? ((Activity) context).getWindow().getDecorView() : null;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        if (view3 == view2) {
            view3.getHitRect(rect);
            return rect;
        }
        View view4 = view3;
        while (view4 != decorView && view4 != view2) {
            view4.getHitRect(rect2);
            if (!view4.getClass().equals(FRAGMENT_CON)) {
                rect.left += rect2.left;
                rect.top += rect2.top;
            }
            view4 = (View) view4.getParent();
            if (view4 == null) {
                throw new IllegalArgumentException("the view is not showing in the window!");
            }
            if (view4.getParent() != null && (view4.getParent() instanceof ViewPager)) {
                view4 = (View) view4.getParent();
            }
        }
        rect.right = rect.left + view3.getMeasuredWidth();
        rect.bottom = rect.top + view3.getMeasuredHeight();
        return rect;
    }
}
