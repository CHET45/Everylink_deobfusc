package com.tencent.cos.xml.model.tag;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class RefererConfiguration {
    public List<Domain> domainList;
    public String emptyReferConfiguration;
    public String refererType;
    public String status;

    public boolean getEnabled() {
        return "Enabled".equals(this.status);
    }

    public void setEnabled(boolean z) {
        if (z) {
            this.status = "Enabled";
        } else {
            this.status = "Disabled";
        }
    }

    public RefererType getRefererType() {
        return RefererType.fromString(this.refererType);
    }

    public void setRefererType(RefererType refererType) {
        this.refererType = refererType.getTypeStr();
    }

    public boolean getAllowEmptyRefer() {
        return "Allow".equals(this.emptyReferConfiguration);
    }

    public void setAllowEmptyRefer(boolean z) {
        if (z) {
            this.emptyReferConfiguration = "Allow";
        } else {
            this.emptyReferConfiguration = "Deny";
        }
    }

    public static class Domain {
        public String domain;

        public Domain() {
        }

        public Domain(String str) {
            this.domain = str;
        }
    }

    public enum RefererType {
        Black("Black-List"),
        White("White-List");

        private final String type;

        RefererType(String str) {
            this.type = str;
        }

        public String getTypeStr() {
            return this.type;
        }

        public static RefererType fromString(String str) {
            for (RefererType refererType : values()) {
                if (refererType.getTypeStr().equalsIgnoreCase(str)) {
                    return refererType;
                }
            }
            return null;
        }
    }
}
