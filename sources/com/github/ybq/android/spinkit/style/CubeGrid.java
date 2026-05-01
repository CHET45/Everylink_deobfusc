package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.aivox.base.common.Constant;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class CubeGrid extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        int[] iArr = {200, Constant.EVENT.BLE_CONNECTED, Constant.EVENT.BLE_GLASS_ASK_AI, 100, 200, Constant.EVENT.BLE_CONNECTED, 0, 100, 200};
        GridItem[] gridItemArr = new GridItem[9];
        for (int i = 0; i < 9; i++) {
            GridItem gridItem = new GridItem();
            gridItemArr[i] = gridItem;
            gridItem.setAnimationDelay(iArr[i]);
        }
        return gridItemArr;
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iWidth = (int) (rectClipSquare.width() * 0.33f);
        int iHeight = (int) (rectClipSquare.height() * 0.33f);
        for (int i = 0; i < getChildCount(); i++) {
            int i2 = rectClipSquare.left + ((i % 3) * iWidth);
            int i3 = rectClipSquare.top + ((i / 3) * iHeight);
            getChildAt(i).setDrawBounds(i2, i3, i2 + iWidth, i3 + iHeight);
        }
    }

    private class GridItem extends RectSprite {
        private GridItem() {
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.35f, 0.7f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float fValueOf = Float.valueOf(1.0f);
            return spriteAnimatorBuilder.scale(fArr, fValueOf, Float.valueOf(0.0f), fValueOf, fValueOf).duration(1300L).easeInOut(fArr).build();
        }
    }
}
