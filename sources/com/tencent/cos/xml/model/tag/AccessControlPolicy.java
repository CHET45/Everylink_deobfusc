package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AccessControlPolicy {
    public AccessControlList accessControlList;
    public Owner owner;

    public String toString() {
        StringBuilder sb = new StringBuilder("{AccessControlPolicy:\n");
        Owner owner = this.owner;
        if (owner != null) {
            sb.append(owner.toString()).append("\n");
        }
        AccessControlList accessControlList = this.accessControlList;
        if (accessControlList != null) {
            sb.append(accessControlList.toString()).append("\n");
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Owner {
        public String displayName;

        /* JADX INFO: renamed from: id */
        public String f1832id;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Owner:\nId:");
            sb.append(this.f1832id).append("\nDisplayName:");
            sb.append(this.displayName).append("\n}");
            return sb.toString();
        }
    }

    public static class AccessControlList {
        public List<Grant> grants;

        public String toString() {
            StringBuilder sb = new StringBuilder("{AccessControlList:\n");
            List<Grant> list = this.grants;
            if (list != null) {
                for (Grant grant : list) {
                    if (grant != null) {
                        sb.append(grant.toString()).append("\n");
                    }
                }
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Grant {
        public Grantee grantee;
        public String permission;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Grant:\n");
            Grantee grantee = this.grantee;
            if (grantee != null) {
                sb.append(grantee.toString()).append("\n");
            }
            sb.append("Permission:").append(this.permission).append("\n}");
            return sb.toString();
        }
    }

    public static class Grantee {
        public String displayName;

        /* JADX INFO: renamed from: id */
        public String f1831id;
        public String uri;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Grantee:\nURI:");
            sb.append(this.uri).append("\nId:");
            sb.append(this.f1831id).append("\nDisplayName:");
            sb.append(this.displayName).append("\n}");
            return sb.toString();
        }
    }
}
