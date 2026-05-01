package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleLayoutContainer;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;

/* JADX INFO: loaded from: classes3.dex */
public class Circle extends CircleLayoutContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        Dot[] dotArr = new Dot[12];
        for (int i = 0; i < 12; i++) {
            dotArr[i] = new Dot();
            dotArr[i].setAnimationDelay(i * 100);
        }
        return dotArr;
    }

    private class Dot extends CircleSprite {
        Dot() {
            setScale(0.0f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float fValueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fArr, fValueOf, Float.valueOf(1.0f), fValueOf).duration(1200L).easeInOut(fArr).build();
        }
    }
}
