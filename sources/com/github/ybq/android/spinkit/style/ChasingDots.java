package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;
import com.aivox.base.util.MyAnimationUtil;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class ChasingDots extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Dot(), new Dot()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(1000);
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite
    public ValueAnimator onCreateAnimation() {
        return new SpriteAnimatorBuilder(this).rotate(new float[]{0.0f, 1.0f}, 0, 360).duration(MyAnimationUtil.ANI_TIME_2000).interpolator(new LinearInterpolator()).build();
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iWidth = (int) (rectClipSquare.width() * 0.6f);
        getChildAt(0).setDrawBounds(rectClipSquare.right - iWidth, rectClipSquare.top, rectClipSquare.right, rectClipSquare.top + iWidth);
        getChildAt(1).setDrawBounds(rectClipSquare.right - iWidth, rectClipSquare.bottom - iWidth, rectClipSquare.right, rectClipSquare.bottom);
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
            return spriteAnimatorBuilder.scale(fArr, fValueOf, Float.valueOf(1.0f), fValueOf).duration(MyAnimationUtil.ANI_TIME_2000).easeInOut(fArr).build();
        }
    }
}
