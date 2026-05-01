package com.aivox.base.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.util.DialogBuilder;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class NetUtil {
    private NetUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) BaseAppUtils.getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            context = BaseAppUtils.getContext();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    public static void isConnected(Context context, boolean z) {
        if (z) {
            if (isConnected(context)) {
                DialogUtils.dismissAll();
            } else {
                DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.network_disconnect), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.base.util.NetUtil$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        context2.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
                    }
                }, true, false, C0874R.string.sure, C0874R.string.settings);
            }
        }
    }

    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseAppUtils.getContext().getSystemService("connectivity");
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo().getType() == 1;
    }

    public static boolean is3G() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseAppUtils.getContext().getSystemService("connectivity");
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo().getType() == 0;
    }

    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    public static void ping() {
        try {
            Process processExec = Runtime.getRuntime().exec("/system/bin/ping -c 4 ttlnt.wedo-lnt.com");
            processExec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                } else if (line.contains("packet loss")) {
                    line.substring(line.indexOf("received") + 10, line.indexOf(PunctuationConst.PERCENT) + 1);
                }
            }
            String strSubstring = "";
            while (true) {
                String line2 = bufferedReader.readLine();
                if (line2 != null) {
                    if (line2.contains("avg")) {
                        LogUtil.m338i("延迟-str:" + line2);
                        int iIndexOf = line2.indexOf("/", 20);
                        strSubstring = line2.substring(iIndexOf + 1, line2.indexOf(".", iIndexOf));
                        LogUtil.m338i("延迟:" + strSubstring + "ms");
                    }
                } else {
                    strSubstring.equals("");
                    return;
                }
            }
        } catch (Exception e) {
            LogUtil.m336e("ping_e:" + e.toString());
        }
    }
}
