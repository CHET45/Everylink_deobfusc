package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import com.jieli.jl_bt_ota.interfaces.rcsp.ICmdSnGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* JADX INFO: loaded from: classes3.dex */
public class SnGenerator implements ICmdSnGenerator {

    /* JADX INFO: renamed from: b */
    private final Map<String, Integer> f778b = new HashMap();

    /* JADX INFO: renamed from: a */
    private int f777a = (new Random().nextInt(255) + 1) % 256;

    public synchronized int autoIncSN(BluetoothDevice bluetoothDevice) {
        int cmdSequence = getCmdSequence(bluetoothDevice);
        if (bluetoothDevice == null) {
            int i = cmdSequence + 1;
            this.f777a = i < 256 ? i : 0;
            return cmdSequence;
        }
        int i2 = cmdSequence + 1;
        if (i2 < 256) {
            i = i2;
        }
        this.f778b.put(bluetoothDevice.getAddress(), Integer.valueOf(i));
        return cmdSequence;
    }

    public void destroy() {
        this.f778b.clear();
    }

    public int getCmdSequence(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return this.f777a;
        }
        Integer num = this.f778b.get(bluetoothDevice.getAddress());
        return num == null ? this.f777a : num.intValue();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.rcsp.ICmdSnGenerator
    public int getRcspCmdSeq(BluetoothDevice bluetoothDevice) {
        return autoIncSN(bluetoothDevice);
    }

    public void removeCmdSequence(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || this.f778b.isEmpty()) {
            return;
        }
        this.f778b.remove(bluetoothDevice.getAddress());
    }
}
