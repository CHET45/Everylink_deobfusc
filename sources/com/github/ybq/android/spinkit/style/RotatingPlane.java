package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;

/* JADX INFO: loaded from: classes3.dex */
public class RotatingPlane extends RectSprite {
    @Override // com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        setDrawBounds(clipSquare(rect));
    }

    @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 0.5f, 1.0f};
        return new SpriteAnimatorBuilder(this).rotateX(fArr, 0, -180, -180).rotateY(fArr, 0, 0, -180).duration(1200L).easeInOut(fArr).build();
    }
}
