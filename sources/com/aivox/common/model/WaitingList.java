package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WaitingList {
    List<AudioInfoBean> audioInfoBeans;

    public WaitingList(List<AudioInfoBean> list) {
        new ArrayList();
        this.audioInfoBeans = list;
    }

    public List<AudioInfoBean> getAudioInfos() {
        return this.audioInfoBeans;
    }

    public void setAudioInfos(List<AudioInfoBean> list) {
        this.audioInfoBeans = list;
    }
}
