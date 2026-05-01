package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteResult {
    public List<Deleted> deletedList;
    public List<Error> errorList;

    public String toString() {
        StringBuilder sb = new StringBuilder("{DeleteResult:\n");
        List<Deleted> list = this.deletedList;
        if (list != null) {
            for (Deleted deleted : list) {
                if (deleted != null) {
                    sb.append(deleted.toString()).append("\n");
                }
            }
        }
        List<Error> list2 = this.errorList;
        if (list2 != null) {
            for (Error error : list2) {
                if (error != null) {
                    sb.append(error.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Deleted {
        public boolean deleteMarker;
        public String deleteMarkerVersionId;
        public String key;
        public String versionId;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Deleted:\nKey:");
            sb.append(this.key).append("\nVersionId:");
            sb.append(this.versionId).append("\nDeleteMarker:");
            sb.append(this.deleteMarker).append("\nDeleteMarkerVersionId:");
            sb.append(this.deleteMarkerVersionId).append("\n}");
            return sb.toString();
        }
    }

    public static class Error {
        public String code;
        public String key;
        public String message;
        public String versionId;

        public String toString() {
            StringBuilder sb = new StringBuilder("{CosError:\nKey:");
            sb.append(this.key).append("\nCode:");
            sb.append(this.code).append("\nMessage:");
            sb.append(this.message).append("\nVersionId:");
            sb.append(this.versionId).append("\n}");
            return sb.toString();
        }
    }
}
