package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.aivox.base.common.Constant;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class ThreeBounce extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Bounce(), new Bounce(), new Bounce()};
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        spriteArr[1].setAnimationDelay(160);
        spriteArr[2].setAnimationDelay(Constant.EVENT.BLE_SHOW_CONNECT_DIALOG);
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iWidth = rectClipSquare.width() / 8;
        int iCenterY = rectClipSquare.centerY() - iWidth;
        int iCenterY2 = rectClipSquare.centerY() + iWidth;
        for (int i = 0; i < getChildCount(); i++) {
            int iWidth2 = ((rectClipSquare.width() * i) / 3) + rectClipSquare.left;
            getChildAt(i).setDrawBounds(iWidth2, iCenterY, (iWidth * 2) + iWidth2, iCenterY2);
        }
    }

    private class Bounce extends CircleSprite {
        Bounce() {
            setScale(0.0f);
        }

        @Override // com.github.ybq.android.spinkit.sprite.CircleSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.4f, 0.8f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float fValueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fArr, fValueOf, Float.valueOf(1.0f), fValueOf, fValueOf).duration(1400L).easeInOut(fArr).build();
        }
    }
}
