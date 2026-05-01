package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ListMultipartUploads {
    public String bucket;
    public List<CommonPrefixes> commonPrefixes;
    public String delimiter;
    public String encodingType;
    public boolean isTruncated;
    public String keyMarker;
    public String maxUploads;
    public String nextKeyMarker;
    public String nextUploadIdMarker;
    public String prefix;
    public String uploadIdMarker;
    public List<Upload> uploads;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListMultipartUploads:\nBucket:");
        sb.append(this.bucket).append("\nEncoding-Type:");
        sb.append(this.encodingType).append("\nKeyMarker:");
        sb.append(this.keyMarker).append("\nUploadIdMarker:");
        sb.append(this.uploadIdMarker).append("\nNextKeyMarker:");
        sb.append(this.nextKeyMarker).append("\nNextUploadIdMarker:");
        sb.append(this.nextUploadIdMarker).append("\nMaxUploads:");
        sb.append(this.maxUploads).append("\nIsTruncated:");
        sb.append(this.isTruncated).append("\nPrefix:");
        sb.append(this.prefix).append("\nDelimiter:");
        sb.append(this.delimiter).append("\n");
        List<Upload> list = this.uploads;
        if (list != null) {
            for (Upload upload : list) {
                if (upload != null) {
                    sb.append(upload.toString()).append("\n");
                }
            }
        }
        List<CommonPrefixes> list2 = this.commonPrefixes;
        if (list2 != null) {
            for (CommonPrefixes commonPrefixes : list2) {
                if (commonPrefixes != null) {
                    sb.append(commonPrefixes.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Upload {
        public String initiated;
        public Initiator initiator;
        public String key;
        public Owner owner;
        public String storageClass;
        public String uploadID;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Upload:\nKey:");
            sb.append(this.key).append("\nUploadID:");
            sb.append(this.uploadID).append("\nStorageClass:");
            sb.append(this.storageClass).append("\n");
            Initiator initiator = this.initiator;
            if (initiator != null) {
                sb.append(initiator.toString()).append("\n");
            }
            Owner owner = this.owner;
            if (owner != null) {
                sb.append(owner.toString()).append("\n");
            }
            sb.append("Initiated:").append(this.initiated).append("\n}");
            return sb.toString();
        }
    }

    public static class CommonPrefixes {
        public String prefix;

        public String toString() {
            StringBuilder sb = new StringBuilder("{CommonPrefixes:\nPrefix:");
            sb.append(this.prefix).append("\n}");
            return sb.toString();
        }
    }

    public static class Initiator {
        public String displayName;

        /* JADX INFO: renamed from: id */
        public String f1838id;
        public String uin;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Initiator:\nUin:");
            sb.append(this.uin).append("\nId:");
            sb.append(this.f1838id).append("\nDisplayName:");
            sb.append(this.displayName).append("\n}");
            return sb.toString();
        }
    }

    public static class Owner {
        public String displayName;

        /* JADX INFO: renamed from: id */
        public String f1839id;
        public String uid;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nUid:");
            sb.append(this.uid).append("\nId:");
            sb.append(this.f1839id).append("\nDisplayName:");
            sb.append(this.displayName).append("\n}");
            return sb.toString();
        }
    }
}
