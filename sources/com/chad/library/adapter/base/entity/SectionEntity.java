package com.chad.library.adapter.base.entity;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SectionEntity<T> implements Serializable {
    public String header;
    public boolean isHeader;

    /* JADX INFO: renamed from: t */
    public T f423t;

    public SectionEntity(boolean z, String str) {
        this.isHeader = z;
        this.header = str;
        this.f423t = null;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.f423t = t;
    }
}
