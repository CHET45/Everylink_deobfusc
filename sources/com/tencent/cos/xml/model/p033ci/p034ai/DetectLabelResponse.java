package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DetectLabelResponse {
    public DetectLabelResponseLabels albumLabels;
    public DetectLabelResponseLabels cameraLabels;
    public DetectLabelResponseLabels newsLabels;
    public DetectLabelResponseLabels webLabels;

    public static class DetectLabelResponseLabels {
        public List<DetectLabelResponseLabelsItem> labels;
    }

    public static class DetectLabelResponseLabelsItem {
        public int confidence;
        public String firstCategory;
        public String name;
        public String secondCategory;
    }
}
