package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ListParts {
    public String bucket;
    public String encodingType;
    public Initiator initiator;
    public boolean isTruncated;
    public String key;
    public String maxParts;
    public String nextPartNumberMarker;
    public Owner owner;
    public String partNumberMarker;
    public List<Part> parts;
    public String storageClass;
    public String uploadId;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListParts:\nBucket:");
        sb.append(this.bucket).append("\nEncoding-Type:");
        sb.append(this.encodingType).append("\nKey:");
        sb.append(this.key).append("\nUploadId:");
        sb.append(this.uploadId).append("\n");
        Owner owner = this.owner;
        if (owner != null) {
            sb.append(owner.toString()).append("\n");
        }
        sb.append("PartNumberMarker:").append(this.partNumberMarker).append("\n");
        Initiator initiator = this.initiator;
        if (initiator != null) {
            sb.append(initiator.toString()).append("\n");
        }
        sb.append("StorageClass:").append(this.storageClass).append("\nNextPartNumberMarker:");
        sb.append(this.nextPartNumberMarker).append("\nMaxParts:");
        sb.append(this.maxParts).append("\nIsTruncated:");
        sb.append(this.isTruncated).append("\n");
        List<Part> list = this.parts;
        if (list != null) {
            for (Part part : list) {
                if (part != null) {
                    sb.append(part.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Owner {
        public String disPlayName;

        /* JADX INFO: renamed from: id */
        public String f1841id;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nId:");
            sb.append(this.f1841id).append("\nDisPlayName:");
            sb.append(this.disPlayName).append("\n}");
            return sb.toString();
        }
    }

    public static class Initiator {
        public String disPlayName;

        /* JADX INFO: renamed from: id */
        public String f1840id;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Initiator:\nId:");
            sb.append(this.f1840id).append("\nDisPlayName:");
            sb.append(this.disPlayName).append("\n}");
            return sb.toString();
        }
    }

    public static class Part {
        public String eTag;
        public String lastModified;
        public String partNumber;
        public String size;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Part:\nPartNumber:");
            sb.append(this.partNumber).append("\nLastModified:");
            sb.append(this.lastModified).append("\nETag:");
            sb.append(this.eTag).append("\nSize:");
            sb.append(this.size).append("\n}");
            return sb.toString();
        }
    }
}
