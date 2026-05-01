package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class Wave extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        WaveItem[] waveItemArr = new WaveItem[5];
        for (int i = 0; i < 5; i++) {
            waveItemArr[i] = new WaveItem();
            waveItemArr[i].setAnimationDelay((i * 100) + 600);
        }
        return waveItemArr;
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iWidth = rectClipSquare.width() / getChildCount();
        int iWidth2 = ((rectClipSquare.width() / 5) * 3) / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int i2 = rectClipSquare.left + (i * iWidth) + (iWidth / 5);
            childAt.setDrawBounds(i2, rectClipSquare.top, i2 + iWidth2, rectClipSquare.bottom);
        }
    }

    private class WaveItem extends RectSprite {
        WaveItem() {
            setScaleY(0.4f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.2f, 0.4f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float fValueOf = Float.valueOf(0.4f);
            return spriteAnimatorBuilder.scaleY(fArr, fValueOf, Float.valueOf(1.0f), fValueOf, fValueOf).duration(1200L).easeInOut(fArr).build();
        }
    }
}
