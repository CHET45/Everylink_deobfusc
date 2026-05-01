package com.tencent.cos.xml.model.tag;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ListAllMyBuckets {
    public List<Bucket> buckets;
    public Owner owner;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListAllMyBuckets:\n");
        Owner owner = this.owner;
        if (owner != null) {
            sb.append(owner.toString()).append("\n");
        }
        sb.append("Buckets:\n");
        for (Bucket bucket : this.buckets) {
            if (bucket != null) {
                sb.append(bucket.toString()).append("\n");
            }
        }
        sb.append("}\n}");
        return sb.toString();
    }

    public static class Owner {
        public String disPlayName;

        /* JADX INFO: renamed from: id */
        public String f1836id;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nID:");
            sb.append(this.f1836id).append("\nDisPlayName:");
            sb.append(this.disPlayName).append("\n}");
            return sb.toString();
        }
    }

    public static class Bucket {
        public String createDate;
        public String location;
        public String name;
        public String type;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Bucket:\nName:");
            sb.append(this.name).append("\nLocation:");
            sb.append(this.location).append("\nCreateDate:");
            sb.append(this.createDate).append("\n}");
            return sb.toString();
        }
    }
}
