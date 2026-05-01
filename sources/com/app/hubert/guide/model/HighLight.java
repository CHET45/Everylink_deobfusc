package com.app.hubert.guide.model;

import android.graphics.RectF;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface HighLight {

    public enum Shape {
        CIRCLE,
        RECTANGLE,
        OVAL,
        ROUND_RECTANGLE
    }

    HighlightOptions getOptions();

    float getRadius();

    RectF getRectF(View view2);

    int getRound();

    Shape getShape();
}
