package com.aivox.common_ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class RoundCornerContainer extends FrameLayout {
    private float mRadius;
    private final Path path;
    private final RectF rect;

    public RoundCornerContainer(Context context) {
        super(context);
        this.path = new Path();
        this.rect = new RectF();
        this.mRadius = 32.0f;
        init();
    }

    public RoundCornerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.path = new Path();
        this.rect = new RectF();
        this.mRadius = 32.0f;
        init();
    }

    public RoundCornerContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.path = new Path();
        this.rect = new RectF();
        this.mRadius = 32.0f;
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.path.reset();
        this.rect.set(0.0f, 0.0f, getWidth(), getHeight());
        Path path = this.path;
        RectF rectF = this.rect;
        float f = this.mRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }

    public void setCornerRadiusDp(float f) {
        this.mRadius = SizeUtils.dp2px(f);
        this.path.reset();
        this.rect.set(0.0f, 0.0f, getWidth(), getHeight());
        Path path = this.path;
        RectF rectF = this.rect;
        float f2 = this.mRadius;
        path.addRoundRect(rectF, f2, f2, Path.Direction.CW);
        invalidate();
    }
}
