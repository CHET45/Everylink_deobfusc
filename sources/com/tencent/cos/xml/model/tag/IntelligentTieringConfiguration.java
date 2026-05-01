package com.tencent.cos.xml.model.tag;

/* JADX INFO: loaded from: classes4.dex */
public class IntelligentTieringConfiguration {
    public String status;
    public Transition transition;

    public static class Transition {
        public int days;
        public int requestFrequent;
    }

    public IntelligentTieringConfiguration(String str, int i) {
        this.status = str;
        Transition transition = new Transition();
        this.transition = transition;
        transition.days = i;
        this.transition.requestFrequent = 1;
    }

    public IntelligentTieringConfiguration() {
    }
}
