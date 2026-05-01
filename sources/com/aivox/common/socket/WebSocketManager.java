package com.aivox.common.socket;

import android.content.Context;
import android.content.Intent;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketManager {
    public static void startService(Context context) {
        DialogUtils.showLoadingDialog(context);
        LogUtil.m338i("WebSocketManager_startService");
        if (BaseAppUtils.isServiceWork(context, WebSocketService.class.getName())) {
            WebSocketService.getInstance().initWebSocket();
            return;
        }
        Intent intent = new Intent(context, (Class<?>) WebSocketService.class);
        intent.putExtra("toActivity", ARouterUtils.getClass(MainAction.MAIN));
        intent.putExtra("loginActivity", ARouterUtils.getClass(AccountAction.ONE_KEY_LOGIN));
        intent.putExtra("channel_id", "websocket");
        context.startService(intent);
    }

    public static void stopService(Context context) {
        LogUtil.m338i("WebSocketManager_stopService");
        context.stopService(new Intent(context, (Class<?>) WebSocketService.class));
    }
}
