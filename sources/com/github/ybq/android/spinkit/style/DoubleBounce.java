package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.aivox.base.util.MyAnimationUtil;
import com.alibaba.fastjson.asm.Opcodes;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class DoubleBounce extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Bounce(), new Bounce()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(1000);
    }

    private class Bounce extends CircleSprite {
        Bounce() {
            setAlpha(Opcodes.IFEQ);
            setScale(0.0f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float fValueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fArr, fValueOf, Float.valueOf(1.0f), fValueOf).duration(MyAnimationUtil.ANI_TIME_2000).easeInOut(fArr).build();
        }
    }
}
