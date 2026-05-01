package com.tencent.cos.xml.model.tag;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ListBucket {
    public List<CommonPrefixes> commonPrefixesList;
    public List<Contents> contentsList;
    public String delimiter;
    public String encodingType;
    public boolean isTruncated;
    public String marker;
    public int maxKeys;
    public String name;
    public String nextMarker;
    public String prefix;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListBucket:\nName:");
        sb.append(this.name).append("\nEncoding-Type:");
        sb.append(this.encodingType).append("\nPrefix:");
        sb.append(this.prefix).append("\nMarker:");
        sb.append(this.marker).append("\nMaxKeys:");
        sb.append(this.maxKeys).append("\nIsTruncated:");
        sb.append(this.isTruncated).append("\nNextMarker:");
        sb.append(this.nextMarker).append("\n");
        List<Contents> list = this.contentsList;
        if (list != null) {
            for (Contents contents : list) {
                if (contents != null) {
                    sb.append(contents.toString()).append("\n");
                }
            }
        }
        List<CommonPrefixes> list2 = this.commonPrefixesList;
        if (list2 != null) {
            for (CommonPrefixes commonPrefixes : list2) {
                if (commonPrefixes != null) {
                    sb.append(commonPrefixes.toString()).append("\n");
                }
            }
        }
        sb.append("Delimiter:").append(this.delimiter).append("\n}");
        return sb.toString();
    }

    public static class Contents {
        public String eTag;
        public String key;
        public String lastModified;
        public Owner owner;
        public long size;
        public String storageClass;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Contents:\nKey:");
            sb.append(this.key).append("\nLastModified:");
            sb.append(this.lastModified).append("\nETag:");
            sb.append(this.eTag).append("\nSize:");
            sb.append(this.size).append("\n");
            Owner owner = this.owner;
            if (owner != null) {
                sb.append(owner.toString()).append("\n");
            }
            sb.append("StorageClass:").append(this.storageClass).append("\n}");
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

    public static class Owner {

        /* JADX INFO: renamed from: id */
        public String f1837id;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nId:");
            sb.append(this.f1837id).append("\n}");
            return sb.toString();
        }
    }
}
