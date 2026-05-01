package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class COSOCRResponse {
    public float angel;
    public String language;
    public int pdfPageSize;
    public String requestId;
    public COSOCRResponseTextDetections textDetections;

    public static class COSOCRResponseItemPolygon {
        public int height;
        public int width;

        /* JADX INFO: renamed from: x */
        public int f1811x;

        /* JADX INFO: renamed from: y */
        public int f1812y;
    }

    public static class COSOCRResponsePolygon {

        /* JADX INFO: renamed from: x */
        public int f1813x;

        /* JADX INFO: renamed from: y */
        public int f1814y;
    }

    public static class COSOCRResponseTextDetections {
        public int confidence;
        public String detectedText;
        public List<COSOCRResponseItemPolygon> itemPolygon;
        public List<COSOCRResponsePolygon> polygon;
        public List<COSOCRResponseWordPolygon> wordPolygon;
        public List<COSOCRResponseWords> words;
    }

    public static class COSOCRResponseWordCoordPoint {
        public COSOCRResponseWordCoordinate wordCoordinate;
    }

    public static class COSOCRResponseWordCoordinate {

        /* JADX INFO: renamed from: x */
        public int f1815x;

        /* JADX INFO: renamed from: y */
        public int f1816y;
    }

    public static class COSOCRResponseWordPolygon {
        public COSOCRResponsePolygon leftBottom;
        public COSOCRResponsePolygon leftTop;
        public COSOCRResponsePolygon rightBottom;
        public COSOCRResponsePolygon rightTop;
    }

    public static class COSOCRResponseWords {
        public String character;
        public int confidence;
        public COSOCRResponseWordCoordPoint wordCoordPoint;
    }
}
