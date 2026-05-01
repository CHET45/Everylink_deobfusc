package com.github.ybq.android.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/* JADX INFO: loaded from: classes3.dex */
public class RectSprite extends ShapeSprite {
    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    @Override // com.github.ybq.android.spinkit.sprite.ShapeSprite
    public void drawShape(Canvas canvas, Paint paint) {
        if (getDrawBounds() != null) {
            canvas.drawRect(getDrawBounds(), paint);
        }
    }
}
