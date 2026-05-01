package com.tencent.cos.xml.model.p033ci.audit;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class CreateStrategy {
    public Labels labels;
    public String name;
    public List<String> textLibs;

    public static class LabelInfo {
        public List<String> abuse;
        public List<String> ads;
        public List<String> illegal;
        public List<String> politics;
        public List<String> porn;
        public List<String> terrorism;
    }

    public static class Labels {
        public LabelInfo audio;
        public LabelInfo image;
        public LabelInfo text;
    }
}
