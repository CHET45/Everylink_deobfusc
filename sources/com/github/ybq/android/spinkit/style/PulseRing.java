package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.alibaba.fastjson.asm.Opcodes;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.github.ybq.android.spinkit.sprite.RingSprite;

/* JADX INFO: loaded from: classes3.dex */
public class PulseRing extends RingSprite {
    public PulseRing() {
        setScale(0.0f);
    }

    @Override // com.github.ybq.android.spinkit.sprite.RingSprite, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 0.7f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        Float fValueOf = Float.valueOf(1.0f);
        return spriteAnimatorBuilder.scale(fArr, Float.valueOf(0.0f), fValueOf, fValueOf).alpha(fArr, 255, Integer.valueOf(Opcodes.GETSTATIC), 0).duration(1000L).interpolator(KeyFrameInterpolator.pathInterpolator(0.21f, 0.53f, 0.56f, 0.8f, fArr)).build();
    }
}
