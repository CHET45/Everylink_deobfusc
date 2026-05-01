package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class CORSConfiguration {
    public List<CORSRule> corsRules;

    public String toString() {
        StringBuilder sb = new StringBuilder("{CORSConfiguration:\n");
        List<CORSRule> list = this.corsRules;
        if (list != null) {
            for (CORSRule cORSRule : list) {
                if (cORSRule != null) {
                    sb.append(cORSRule.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class CORSRule {
        public List<String> allowedHeader;
        public List<String> allowedMethod;
        public String allowedOrigin;
        public List<String> exposeHeader;

        /* JADX INFO: renamed from: id */
        public String f1833id;
        public int maxAgeSeconds;

        public String toString() {
            StringBuilder sb = new StringBuilder("{CORSRule:\nID:");
            sb.append(this.f1833id).append("\nAllowedOrigin:");
            sb.append(this.allowedOrigin).append("\n");
            List<String> list = this.allowedMethod;
            if (list != null) {
                for (String str : list) {
                    if (str != null) {
                        sb.append("AllowedMethod:").append(str).append("\n");
                    }
                }
            }
            List<String> list2 = this.allowedHeader;
            if (list2 != null) {
                for (String str2 : list2) {
                    if (str2 != null) {
                        sb.append("AllowedHeader:").append(str2).append("\n");
                    }
                }
            }
            List<String> list3 = this.exposeHeader;
            if (list3 != null) {
                for (String str3 : list3) {
                    if (str3 != null) {
                        sb.append("ExposeHeader:").append(str3).append("\n");
                    }
                }
            }
            sb.append("MaxAgeSeconds:").append(this.maxAgeSeconds).append("\n}");
            return sb.toString();
        }
    }
}
