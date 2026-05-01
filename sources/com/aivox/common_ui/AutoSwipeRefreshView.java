package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.base.util.BaseAppUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class AutoSwipeRefreshView extends SwipeRefreshLayout {
    public AutoSwipeRefreshView(Context context) {
        super(context);
    }

    public AutoSwipeRefreshView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void autoRefresh() {
        try {
            Field declaredField = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            declaredField.setAccessible(true);
            ((View) declaredField.get(this)).setVisibility(0);
            Method declaredMethod = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", Boolean.TYPE, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, true, true);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }
}
