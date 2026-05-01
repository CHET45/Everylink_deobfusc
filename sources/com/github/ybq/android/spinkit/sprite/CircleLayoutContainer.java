package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CircleLayoutContainer extends SpriteContainer {
    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer
    public void drawChild(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int iSave = canvas.save();
            canvas.rotate((i * 360) / getChildCount(), getBounds().centerX(), getBounds().centerY());
            childAt.draw(canvas);
            canvas.restoreToCount(iSave);
        }
    }

    @Override // com.github.ybq.android.spinkit.sprite.SpriteContainer, com.github.ybq.android.spinkit.sprite.Sprite, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect rectClipSquare = clipSquare(rect);
        int iWidth = (int) (((((double) rectClipSquare.width()) * 3.141592653589793d) / 3.5999999046325684d) / ((double) getChildCount()));
        int iCenterX = rectClipSquare.centerX() - iWidth;
        int iCenterX2 = rectClipSquare.centerX() + iWidth;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setDrawBounds(iCenterX, rectClipSquare.top, iCenterX2, rectClipSquare.top + (iWidth * 2));
        }
    }
}
