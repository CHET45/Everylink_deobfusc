package com.aivox.common.ble.model;

import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BleAdvertisedData {
    private String mName;
    private List<UUID> mUuids;

    public BleAdvertisedData(List<UUID> list, String str) {
        this.mUuids = list;
        this.mName = str;
    }

    public List<UUID> getUuids() {
        return this.mUuids;
    }

    public String getName() {
        return this.mName;
    }
}
