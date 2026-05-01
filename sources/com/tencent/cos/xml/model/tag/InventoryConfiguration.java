package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class InventoryConfiguration {
    public static final String SCHEDULE_FREQUENCY_DAILY = "Daily";
    public static final String SCHEDULE_FREQUENCY_WEEKLY = "Weekly";
    public Destination destination;
    public Filter filter;

    /* JADX INFO: renamed from: id */
    public String f1834id;
    public String includedObjectVersions;
    public boolean isEnabled;
    public OptionalFields optionalFields;
    public Schedule schedule;

    public String toString() {
        StringBuilder sb = new StringBuilder("{InventoryConfiguration:\nId");
        sb.append(this.f1834id).append("\nIsEnabled:");
        sb.append(this.isEnabled).append("\n");
        Destination destination = this.destination;
        if (destination != null) {
            sb.append(destination.toString()).append("\n");
        }
        Schedule schedule = this.schedule;
        if (schedule != null) {
            sb.append(schedule.toString()).append("\n");
        }
        Filter filter = this.filter;
        if (filter != null) {
            sb.append(filter.toString()).append("\n");
        }
        sb.append("IncludedObjectVersions:").append(this.includedObjectVersions).append("\n");
        OptionalFields optionalFields = this.optionalFields;
        if (optionalFields != null) {
            sb.append(optionalFields.toString()).append("\n");
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Filter {
        public String prefix;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Filter:\nPrefix:");
            sb.append(this.prefix).append("\n}");
            return sb.toString();
        }
    }

    public static class OptionalFields {
        public Set<String> fields;

        public String toString() {
            StringBuilder sb = new StringBuilder("{OptionalFields:\n");
            Set<String> set = this.fields;
            if (set != null) {
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    sb.append("Field:").append(it.next()).append("\n");
                }
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Schedule {
        public String frequency;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Schedule:\nFrequency:");
            sb.append(this.frequency).append("\n}");
            return sb.toString();
        }
    }

    public static class Destination {
        public COSBucketDestination cosBucketDestination;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Destination:\n");
            COSBucketDestination cOSBucketDestination = this.cosBucketDestination;
            if (cOSBucketDestination != null) {
                sb.append(cOSBucketDestination.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class COSBucketDestination {
        public String accountId;
        public String bucket;
        public Encryption encryption;
        public String format;
        public String prefix;

        public void setBucket(String str, String str2) {
            if (str == null || str2 == null) {
                return;
            }
            this.bucket = String.format("qcs::cos:%s::%s", str, str2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{COSBucketDestination:\nFormat:");
            sb.append(this.format).append("\nAccountId:");
            sb.append(this.accountId).append("\nBucket:");
            sb.append(this.bucket).append("\nPrefix:");
            sb.append(this.prefix).append("\n");
            Encryption encryption = this.encryption;
            if (encryption != null) {
                sb.append(encryption.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Encryption {
        public String sSECOS;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Encryption:\nSSE-COS:");
            sb.append(this.sSECOS).append("\n}");
            return sb.toString();
        }
    }

    public enum Field {
        SIZE(BlobConstants.SIZE_ELEMENT),
        LastModified_Date("LastModifiedDate"),
        StroageClass("StorageClass"),
        ETAG(Constants.ETAG_ELEMENT),
        IS_MULTIPARTUPLOADed("IsMultipartUploaded"),
        REPLICATION_STATUS("ReplicationStatus");

        String value;

        Field(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum IncludedObjectVersions {
        ALL("All"),
        CURRENT("Current");

        private String desc;

        IncludedObjectVersions(String str) {
            this.desc = str;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public enum Frequency {
        DAILY(InventoryConfiguration.SCHEDULE_FREQUENCY_DAILY);

        String value;

        Frequency(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }
}
