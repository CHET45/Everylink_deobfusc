package com.aivox.common.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;

/* JADX INFO: loaded from: classes.dex */
public class NetworkReceiver extends BroadcastReceiver {
    private static NetworkReceiver networkReceiver;
    private NetworkChangeListener networkChangeListener;
    public final int wifi = 2;
    public final int mobile = 1;
    public final int none = 0;
    public int oldState = 0;
    public int newState = 0;

    public interface NetworkChangeListener {
        void onChange(int i, int i2, int i3, int i4, int i5);
    }

    public static NetworkReceiver getInstance() {
        if (networkReceiver == null) {
            networkReceiver = new NetworkReceiver();
        }
        return networkReceiver;
    }

    public void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }

    private void setChange(int i) {
        this.networkChangeListener.onChange(2, 1, 0, this.oldState, i);
        this.oldState = i;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (NetUtil.isConnected(context)) {
            int i = NetUtil.isWifi() ? 2 : 1;
            this.newState = i;
            int i2 = this.oldState;
            if (i != i2 && i2 == 0) {
                LogUtil.m338i("网络已恢复，发起重连");
            }
        } else if (this.oldState != 0) {
            this.newState = 0;
        }
        setChange(this.newState);
    }
}
