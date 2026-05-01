package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleLayoutContainer;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;

/* JADX INFO: loaded from: classes3.dex */
public class FadingCircle extends CircleLayoutContainer {
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
            setAlpha(0);
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.39f, 0.4f, 1.0f};
            return new SpriteAnimatorBuilder(this).alpha(fArr, 0, 0, 255, 0).duration(1200L).easeInOut(fArr).build();
        }
    }
}
