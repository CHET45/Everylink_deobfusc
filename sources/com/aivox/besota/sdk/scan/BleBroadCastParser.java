package com.aivox.besota.sdk.scan;

import android.bluetooth.le.ScanRecord;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface BleBroadCastParser {
    Map<String, String> parseBleScanRecordData(ScanRecord scanRecord);
}
