package com.tencent.cos.xml.model.tag;

/* JADX INFO: loaded from: classes4.dex */
public class VersioningConfiguration {
    public String status;

    public String toString() {
        StringBuilder sb = new StringBuilder("{VersioningConfiguration:\nStatus:");
        sb.append(this.status).append("\n}");
        return sb.toString();
    }
}
