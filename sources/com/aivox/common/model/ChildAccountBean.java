package com.aivox.common.model;

import com.chad.library.adapter.base.entity.SectionEntity;

/* JADX INFO: loaded from: classes.dex */
public class ChildAccountBean extends SectionEntity<Identity> {
    public ChildAccountBean(boolean z, String str) {
        super(z, str);
    }

    public ChildAccountBean(Identity identity) {
        super(identity);
    }

    public String toString() {
        return "AudioListBean{isHeader=" + this.isHeader + ", t=" + this.f423t + ", header='" + this.header + "'}";
    }
}
