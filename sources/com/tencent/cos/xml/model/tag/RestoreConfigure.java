package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;

/* JADX INFO: loaded from: classes4.dex */
public class RestoreConfigure {
    public CASJobParameters casJobParameters;
    public int days;

    public String toString() {
        StringBuilder sb = new StringBuilder("{RestoreRequest:\nDays:");
        sb.append(this.days).append("\n");
        CASJobParameters cASJobParameters = this.casJobParameters;
        if (cASJobParameters != null) {
            sb.append(cASJobParameters.toString()).append("\n");
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class CASJobParameters {
        public String tier = Tier.Standard.getTier();

        public String toString() {
            StringBuilder sb = new StringBuilder("{CASJobParameters:\nTier:");
            sb.append(this.tier).append("\n}");
            return sb.toString();
        }
    }

    public enum Tier {
        Expedited("Expedited"),
        Standard("Standard"),
        Bulk("Bulk");

        private String tier;

        Tier(String str) {
            this.tier = str;
        }

        public String getTier() {
            return this.tier;
        }
    }
}
