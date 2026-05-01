package com.clj.fastble.bluetooth;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.utils.BleLruHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MultipleBluetoothController {
    private final BleLruHashMap<String, BleBluetooth> bleLruHashMap = new BleLruHashMap<>(BleManager.getInstance().getMaxConnectCount());
    private final HashMap<String, BleBluetooth> bleTempHashMap = new HashMap<>();

    public synchronized BleBluetooth buildConnectingBle(BleDevice bleDevice) {
        BleBluetooth bleBluetooth;
        bleBluetooth = new BleBluetooth(bleDevice);
        if (!this.bleTempHashMap.containsKey(bleBluetooth.getDeviceKey())) {
            this.bleTempHashMap.put(bleBluetooth.getDeviceKey(), bleBluetooth);
        }
        return bleBluetooth;
    }

    public synchronized void removeConnectingBle(BleBluetooth bleBluetooth) {
        if (bleBluetooth == null) {
            return;
        }
        if (this.bleTempHashMap.containsKey(bleBluetooth.getDeviceKey())) {
            this.bleTempHashMap.remove(bleBluetooth.getDeviceKey());
        }
    }

    public synchronized void addBleBluetooth(BleBluetooth bleBluetooth) {
        if (bleBluetooth == null) {
            return;
        }
        if (!this.bleLruHashMap.containsKey(bleBluetooth.getDeviceKey())) {
            this.bleLruHashMap.put(bleBluetooth.getDeviceKey(), bleBluetooth);
        }
    }

    public synchronized void removeBleBluetooth(BleBluetooth bleBluetooth) {
        if (bleBluetooth == null) {
            return;
        }
        if (this.bleLruHashMap.containsKey(bleBluetooth.getDeviceKey())) {
            this.bleLruHashMap.remove(bleBluetooth.getDeviceKey());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isContainDevice(com.clj.fastble.data.BleDevice r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 == 0) goto L14
            com.clj.fastble.utils.BleLruHashMap<java.lang.String, com.clj.fastble.bluetooth.BleBluetooth> r0 = r1.bleLruHashMap     // Catch: java.lang.Throwable -> L11
            java.lang.String r2 = r2.getKey()     // Catch: java.lang.Throwable -> L11
            boolean r2 = r0.containsKey(r2)     // Catch: java.lang.Throwable -> L11
            if (r2 == 0) goto L14
            r2 = 1
            goto L15
        L11:
            r2 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L11
            throw r2
        L14:
            r2 = 0
        L15:
            monitor-exit(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.clj.fastble.bluetooth.MultipleBluetoothController.isContainDevice(com.clj.fastble.data.BleDevice):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isContainDevice(android.bluetooth.BluetoothDevice r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L29
            com.clj.fastble.utils.BleLruHashMap<java.lang.String, com.clj.fastble.bluetooth.BleBluetooth> r0 = r3.bleLruHashMap     // Catch: java.lang.Throwable -> L26
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L26
            r1.<init>()     // Catch: java.lang.Throwable -> L26
            java.lang.String r2 = r4.getName()     // Catch: java.lang.Throwable -> L26
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L26
            java.lang.String r4 = r4.getAddress()     // Catch: java.lang.Throwable -> L26
            java.lang.StringBuilder r4 = r1.append(r4)     // Catch: java.lang.Throwable -> L26
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L26
            boolean r4 = r0.containsKey(r4)     // Catch: java.lang.Throwable -> L26
            if (r4 == 0) goto L29
            r4 = 1
            goto L2a
        L26:
            r4 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L26
            throw r4
        L29:
            r4 = 0
        L2a:
            monitor-exit(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.clj.fastble.bluetooth.MultipleBluetoothController.isContainDevice(android.bluetooth.BluetoothDevice):boolean");
    }

    public synchronized BleBluetooth getBleBluetooth(BleDevice bleDevice) {
        if (bleDevice != null) {
            if (this.bleLruHashMap.containsKey(bleDevice.getKey())) {
                return this.bleLruHashMap.get(bleDevice.getKey());
            }
        }
        return null;
    }

    public synchronized void disconnect(BleDevice bleDevice) {
        if (isContainDevice(bleDevice)) {
            getBleBluetooth(bleDevice).disconnect();
        }
    }

    public synchronized void disconnectAllDevice() {
        Iterator<Map.Entry<String, BleBluetooth>> it = this.bleLruHashMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().disconnect();
        }
        this.bleLruHashMap.clear();
    }

    public synchronized void destroy() {
        Iterator<Map.Entry<String, BleBluetooth>> it = this.bleLruHashMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().destroy();
        }
        this.bleLruHashMap.clear();
        Iterator<Map.Entry<String, BleBluetooth>> it2 = this.bleTempHashMap.entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().destroy();
        }
        this.bleTempHashMap.clear();
    }

    public synchronized List<BleBluetooth> getBleBluetoothList() {
        ArrayList arrayList;
        arrayList = new ArrayList(this.bleLruHashMap.values());
        Collections.sort(arrayList, new Comparator<BleBluetooth>() { // from class: com.clj.fastble.bluetooth.MultipleBluetoothController.1
            @Override // java.util.Comparator
            public int compare(BleBluetooth bleBluetooth, BleBluetooth bleBluetooth2) {
                return bleBluetooth.getDeviceKey().compareToIgnoreCase(bleBluetooth2.getDeviceKey());
            }
        });
        return arrayList;
    }

    public synchronized List<BleDevice> getDeviceList() {
        ArrayList arrayList;
        refreshConnectedDevice();
        arrayList = new ArrayList();
        for (BleBluetooth bleBluetooth : getBleBluetoothList()) {
            if (bleBluetooth != null) {
                arrayList.add(bleBluetooth.getDevice());
            }
        }
        return arrayList;
    }

    public void refreshConnectedDevice() {
        List<BleBluetooth> bleBluetoothList = getBleBluetoothList();
        for (int i = 0; bleBluetoothList != null && i < bleBluetoothList.size(); i++) {
            BleBluetooth bleBluetooth = bleBluetoothList.get(i);
            if (!BleManager.getInstance().isConnected(bleBluetooth.getDevice())) {
                removeBleBluetooth(bleBluetooth);
            }
        }
    }
}
