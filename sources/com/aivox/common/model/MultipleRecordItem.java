package com.aivox.common.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* JADX INFO: loaded from: classes.dex */
public class MultipleRecordItem implements MultiItemEntity {
    public static final int AUDIO = 1;
    private AudioInfoBean audioInfoBean;
    private FolderBean folderBean;
    private final int type = 1;

    public MultipleRecordItem(AudioInfoBean audioInfoBean) {
        this.audioInfoBean = audioInfoBean;
    }

    public AudioInfoBean getAudioInfo() {
        return this.audioInfoBean;
    }

    public void setAudioInfo(AudioInfoBean audioInfoBean) {
        this.audioInfoBean = audioInfoBean;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }
}
