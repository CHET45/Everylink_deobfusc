package com.tencent.cos.xml.model.tag;

/* JADX INFO: loaded from: classes4.dex */
public class LocationConstraint {
    public String location;

    public String toString() {
        StringBuilder sb = new StringBuilder("{LocationConstraint:\nLocation:");
        sb.append(this.location).append("\n}");
        return sb.toString();
    }
}
