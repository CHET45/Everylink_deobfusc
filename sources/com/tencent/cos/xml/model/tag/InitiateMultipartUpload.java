package com.tencent.cos.xml.model.tag;

/* JADX INFO: loaded from: classes4.dex */
public class InitiateMultipartUpload {
    public String bucket;
    public String key;
    public String uploadId;

    public String toString() {
        StringBuilder sb = new StringBuilder("{InitiateMultipartUpload:\nBucket:");
        sb.append(this.bucket).append("\nKey:");
        sb.append(this.key).append("\nUploadId:");
        sb.append(this.uploadId).append("\n}");
        return sb.toString();
    }
}
