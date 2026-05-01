package com.aivox.besota.sdk.message;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class A2DPConnection implements Serializable {
    private List<String> addressList;
    private boolean connected;

    public boolean isConnected() {
        return this.connected;
    }

    public void setConnected(boolean z) {
        this.connected = z;
    }

    public List<String> getAddressList() {
        return this.addressList;
    }

    public void setAddressList(List<String> list) {
        this.addressList = list;
    }

    public String toString() {
        return "A2DPConnection{connected=" + this.connected + '}';
    }
}
