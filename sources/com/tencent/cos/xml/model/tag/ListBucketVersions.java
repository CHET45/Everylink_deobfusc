package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ListBucketVersions {
    public boolean isTruncated;
    public String keyMarker;
    public long maxKeys;
    public String name;
    public String nextKeyMarker;
    public String nextVersionIdMarker;
    public List<ObjectVersion> objectVersionList;
    public String prefix;
    public String versionIdMarker;

    public static class ObjectVersion {
        public boolean isLatest;
        public String key;
        public String lastModified;
        public Owner owner;
        public String versionId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListVersionsResult:\nName:");
        sb.append(this.name).append("\nPrefix:");
        sb.append(this.prefix).append("\nKeyMarker:");
        sb.append(this.keyMarker).append("\nVersionIdMarker:");
        sb.append(this.versionIdMarker).append("\nMaxKeys:");
        sb.append(this.maxKeys).append("\nIsTruncated:");
        sb.append(this.isTruncated).append("\nNextKeyMarker:");
        sb.append(this.nextKeyMarker).append("\nNextVersionIdMarker:");
        sb.append(this.nextVersionIdMarker).append("\n");
        List<ObjectVersion> list = this.objectVersionList;
        if (list != null) {
            Iterator<ObjectVersion> it = list.iterator();
            while (it.hasNext()) {
                sb.append(it.next().toString()).append("\n");
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class DeleteMarker extends ObjectVersion {
        public String toString() {
            StringBuilder sb = new StringBuilder("{DeleteMarker:\nKey:");
            sb.append(this.key).append("\nVersionId:");
            sb.append(this.versionId).append("\nIsLatest:");
            sb.append(this.isLatest).append("\nLastModified:");
            sb.append(this.lastModified).append("\n");
            if (this.owner != null) {
                sb.append(this.owner.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Owner {
        public String uid;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nUid:");
            sb.append(this.uid).append("\n}");
            return sb.toString();
        }
    }

    public static class Version extends ObjectVersion {
        public String eTag;
        public long size;
        public String storageClass;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Version:\nKey:");
            sb.append(this.key).append("\nVersionId:");
            sb.append(this.versionId).append("\nIsLatest:");
            sb.append(this.isLatest).append("\nLastModified:");
            sb.append(this.lastModified).append("\nETag:");
            sb.append(this.eTag).append("\nSize:");
            sb.append(this.size).append("\nStorageClass:");
            sb.append(this.storageClass).append("\n");
            if (this.owner != null) {
                sb.append(this.owner.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }
}
