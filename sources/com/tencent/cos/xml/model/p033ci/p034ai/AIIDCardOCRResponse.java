package com.tencent.cos.xml.model.p033ci.p034ai;

/* JADX INFO: loaded from: classes4.dex */
public class AIIDCardOCRResponse {
    public AIIDCardOCRResponseAdvancedInfo advancedInfo;
    public AIIDCardOCRResponseIdInfo idInfo;

    public static class AIIDCardOCRResponseAdvancedInfo {
        public String borderCodeValue;
        public String idCard;
        public String portrait;
        public String quality;
        public String warnInfos;
    }

    public static class AIIDCardOCRResponseIdInfo {
        public String address;
        public String authority;
        public String birth;
        public String idNum;
        public String name;
        public String nation;
        public String sex;
        public String validDate;
    }
}
