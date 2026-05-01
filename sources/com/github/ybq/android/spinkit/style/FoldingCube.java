package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.animation.LinearInterpolator;
import com.aivox.base.common.Constant;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

/* JADX INFO: loaded from: classes3.dex */
public class FoldingCube extends SpriteContainer {
    private boolean wrapContent = false;

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public Sprite[] onCreateChild() {
        Cube[] cubeArr = new Cube[4];
        for (int i = 0; i < 4; i++) {
            cubeArr[i] = new Cube();
            cubeArr[i].setAnimationDelay(i * Constant.EVENT.BLE_CONNECTED);
        }
        return cubeArr;
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iMin = Math.min(rectClipSquare.width(), rectClipSquare.height());
        if (this.wrapContent) {
            iMin = (int) Math.sqrt((iMin * iMin) / 2);
            int iWidth = (rectClipSquare.width() - iMin) / 2;
            int iHeight = (rectClipSquare.height() - iMin) / 2;
            rectClipSquare = new Rect(rectClipSquare.left + iWidth, rectClipSquare.top + iHeight, rectClipSquare.right - iWidth, rectClipSquare.bottom - iHeight);
        }
        int i = iMin / 2;
        int i2 = rectClipSquare.left + i + 1;
        int i3 = rectClipSquare.top + i + 1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            Sprite childAt = getChildAt(i4);
            childAt.setDrawBounds(rectClipSquare.left, rectClipSquare.top, i2, i3);
            childAt.setPivotX(childAt.getDrawBounds().right);
            childAt.setPivotY(childAt.getDrawBounds().bottom);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void drawChild(Canvas canvas) {
        Rect rectClipSquare = clipSquare(getBounds());
        for (int i = 0; i < getChildCount(); i++) {
            int iSave = canvas.save();
            canvas.rotate((i * 90) + 45, rectClipSquare.centerX(), rectClipSquare.centerY());
            getChildAt(i).draw(canvas);
            canvas.restoreToCount(iSave);
        }
    }

    private class Cube extends RectSprite {
        Cube() {
            setAlpha(0);
            setRotateX(-180);
        }

        @Override // com.github.ybq.android.spinkit.sprite.RectSprite, com.github.ybq.android.spinkit.sprite.Sprite
        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.1f, 0.25f, 0.75f, 0.9f, 1.0f};
            return new SpriteAnimatorBuilder(this).alpha(fArr, 0, 0, 255, 255, 0, 0).rotateX(fArr, -180, -180, 0, 0, 0, 0).rotateY(fArr, 0, 0, 0, 0, 180, 180).duration(2400L).interpolator(new LinearInterpolator()).build();
        }
    }
}
