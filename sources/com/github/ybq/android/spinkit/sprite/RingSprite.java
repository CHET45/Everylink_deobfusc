package com.github.ybq.android.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/* JADX INFO: loaded from: classes3.dex */
public class RingSprite extends ShapeSprite {
    @Override // com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    @Override // com.github.ybq.android.spinkit.sprite.ShapeSprite
    public void drawShape(Canvas canvas, Paint paint) {
        if (getDrawBounds() != null) {
            paint.setStyle(Paint.Style.STROKE);
            int iMin = Math.min(getDrawBounds().width(), getDrawBounds().height()) / 2;
            paint.setStrokeWidth(iMin / 12);
            canvas.drawCircle(getDrawBounds().centerX(), getDrawBounds().centerY(), iMin, paint);
        }
    }
}
