package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.util.CommonUtil;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class CommandCache {

    /* JADX INFO: renamed from: a */
    private final HashMap<String, CommandBase> f705a = new HashMap<>();

    public static String getCacheCommandKey(BluetoothDevice bluetoothDevice, int i, int i2) {
        return getCacheCommandKey(bluetoothDevice == null ? "00:00:00:00:00:00" : bluetoothDevice.getAddress(), i, i2);
    }

    public CommandBase getCommand(BluetoothDevice bluetoothDevice, int i, int i2) {
        return this.f705a.get(getCacheCommandKey(bluetoothDevice, i, i2));
    }

    public void putCommandBase(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
        if (commandBase != null) {
            if (commandBase.getType() == 2 || commandBase.getType() == 3) {
                this.f705a.put(getCacheCommandKey(bluetoothDevice, commandBase.getId(), commandBase.getOpCodeSn()), commandBase);
            }
        }
    }

    public void release() {
        this.f705a.clear();
    }

    public void removeCommandBase(BluetoothDevice bluetoothDevice, BasePacket basePacket) {
        if (basePacket != null) {
            removeCommandBase(bluetoothDevice, basePacket.getOpCode(), basePacket.getOpCodeSn());
        }
    }

    public void removeCommandBase(BluetoothDevice bluetoothDevice, int i, int i2) {
        this.f705a.remove(getCacheCommandKey(bluetoothDevice, i, i2));
    }

    public static String getCacheCommandKey(String str, int i, int i2) {
        return CommonUtil.formatString("%s-%s-%s", str, Integer.valueOf(i), Integer.valueOf(i2));
    }
}
