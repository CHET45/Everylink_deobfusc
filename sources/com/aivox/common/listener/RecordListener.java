package com.aivox.common.listener;

/* JADX INFO: loaded from: classes.dex */
public interface RecordListener {
    void recordDelete();

    void recordError();

    void recordPause();

    void recordPropertiesModify();

    void recordResume();

    void recordSave();

    void recordStart();

    void recordStopOrSave();
}
