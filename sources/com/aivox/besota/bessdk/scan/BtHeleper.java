package com.aivox.besota.bessdk.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class BtHeleper {
    public static BluetoothManager getBluetoothManager(Context context) {
        return (BluetoothManager) context.getSystemService("bluetooth");
    }

    public static BluetoothAdapter getBluetoothAdapter(Context context) {
        return getBluetoothManager(context).getAdapter();
    }
}
