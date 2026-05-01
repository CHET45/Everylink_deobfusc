package com.aivox.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.aivox.base.common.Constant;
import com.aivox.common.model.EventBean;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class LocaleChangeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
            EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_SWITCH_LANGUAGE));
        }
    }
}
