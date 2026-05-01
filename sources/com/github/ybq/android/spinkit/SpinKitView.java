package com.github.ybq.android.spinkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.sprite.Sprite;

/* JADX INFO: loaded from: classes3.dex */
public class SpinKitView extends ProgressBar {
    private int mColor;
    private Sprite mSprite;
    private Style mStyle;

    public SpinKitView(Context context) {
        this(context, null);
    }

    public SpinKitView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1541R.attr.SpinKitViewStyle);
    }

    public SpinKitView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, C1541R.style.SpinKitView);
    }

    public SpinKitView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1541R.styleable.SpinKitView, i, i2);
        this.mStyle = Style.values()[typedArrayObtainStyledAttributes.getInt(C1541R.styleable.SpinKitView_SpinKit_Style, 0)];
        this.mColor = typedArrayObtainStyledAttributes.getColor(C1541R.styleable.SpinKitView_SpinKit_Color, -1);
        typedArrayObtainStyledAttributes.recycle();
        init();
        setIndeterminate(true);
    }

    private void init() {
        Sprite spriteCreate = SpriteFactory.create(this.mStyle);
        spriteCreate.setColor(this.mColor);
        setIndeterminateDrawable(spriteCreate);
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawable(Drawable drawable) {
        if (!(drawable instanceof Sprite)) {
            throw new IllegalArgumentException("this d must be instanceof Sprite");
        }
        setIndeterminateDrawable((Sprite) drawable);
    }

    public void setIndeterminateDrawable(Sprite sprite) {
        super.setIndeterminateDrawable((Drawable) sprite);
        this.mSprite = sprite;
        if (sprite.getColor() == 0) {
            this.mSprite.setColor(this.mColor);
        }
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        if (getVisibility() == 0) {
            this.mSprite.start();
        }
    }

    @Override // android.widget.ProgressBar
    public Sprite getIndeterminateDrawable() {
        return this.mSprite;
    }

    public void setColor(int i) {
        this.mColor = i;
        Sprite sprite = this.mSprite;
        if (sprite != null) {
            sprite.setColor(i);
        }
        invalidate();
    }

    @Override // android.view.View
    public void unscheduleDrawable(Drawable drawable) {
        super.unscheduleDrawable(drawable);
        if (drawable instanceof Sprite) {
            ((Sprite) drawable).stop();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && this.mSprite != null && getVisibility() == 0) {
            this.mSprite.start();
        }
    }

    @Override // android.view.View
    public void onScreenStateChanged(int i) {
        Sprite sprite;
        super.onScreenStateChanged(i);
        if (i != 0 || (sprite = this.mSprite) == null) {
            return;
        }
        sprite.stop();
    }
}
