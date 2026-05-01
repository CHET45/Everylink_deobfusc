package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class WanderingCubes extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Cube(0), new Cube(3)};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        Rect rectClipSquare = clipSquare(rect);
        super.onBoundsChange(rectClipSquare);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setDrawBounds(rectClipSquare.left, rectClipSquare.top, rectClipSquare.left + (rectClipSquare.width() / 4), rectClipSquare.top + (rectClipSquare.height() / 4));
        }
    }

    private class Cube extends RectSprite {
        int startFrame;

        public Cube(int i) {
            this.startFrame = i;
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.25f, 0.5f, 0.51f, 0.75f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilderRotate = new SpriteAnimatorBuilder(this).rotate(fArr, 0, -90, -179, -180, -270, -360);
            Float fValueOf = Float.valueOf(0.0f);
            Float fValueOf2 = Float.valueOf(0.75f);
            SpriteAnimatorBuilder spriteAnimatorBuilderTranslateYPercentage = spriteAnimatorBuilderRotate.translateXPercentage(fArr, fValueOf, fValueOf2, fValueOf2, fValueOf2, fValueOf, fValueOf).translateYPercentage(fArr, fValueOf, fValueOf, fValueOf2, fValueOf2, fValueOf2, fValueOf);
            Float fValueOf3 = Float.valueOf(1.0f);
            Float fValueOf4 = Float.valueOf(0.5f);
            SpriteAnimatorBuilder spriteAnimatorBuilderEaseInOut = spriteAnimatorBuilderTranslateYPercentage.scale(fArr, fValueOf3, fValueOf4, fValueOf3, fValueOf3, fValueOf4, fValueOf3).duration(1800L).easeInOut(fArr);
            spriteAnimatorBuilderEaseInOut.startFrame(this.startFrame);
            return spriteAnimatorBuilderEaseInOut.build();
        }
    }
}
