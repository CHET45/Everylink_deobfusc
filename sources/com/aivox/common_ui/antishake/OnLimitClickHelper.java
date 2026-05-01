package com.aivox.common_ui.antishake;

import android.util.Log;
import android.view.View;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class OnLimitClickHelper implements OnLimitClickListener {
    public static final int CLICK_DELAY_TIME = 500;
    public static final int CLICK_DELAY_TIME_2 = 2000;
    private long lastClickTime = 0;
    private OnLimitClickListener onLimitClickListener;

    public OnLimitClickHelper(OnLimitClickListener onLimitClickListener) {
        this.onLimitClickListener = onLimitClickListener;
    }

    @Override // com.aivox.common_ui.antishake.OnLimitClickListener
    public void onClick(View view2) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        Log.i("tag", "点击1:" + timeInMillis);
        if (timeInMillis - this.lastClickTime > 500) {
            this.lastClickTime = timeInMillis;
            OnLimitClickListener onLimitClickListener = this.onLimitClickListener;
            if (onLimitClickListener != null) {
                onLimitClickListener.onClick(view2);
            }
        }
    }
}
