package com.tencent.cos.xml.model.tag;

/* JADX INFO: loaded from: classes4.dex */
public class CopyObject {
    public String eTag;
    public String lastModified;

    public String toString() {
        StringBuilder sb = new StringBuilder("{CopyObject:\nETag:");
        sb.append(this.eTag).append("\nLastModified:");
        sb.append(this.lastModified).append("\n}");
        return sb.toString();
    }
}
