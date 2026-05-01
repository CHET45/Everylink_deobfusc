package com.aivox.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class RippleCircleView extends View {
    private RippleAnimationView rippleAnimationView;

    public RippleCircleView(Context context) {
        super(context);
    }

    public RippleCircleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RippleCircleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RippleCircleView(RippleAnimationView rippleAnimationView, Context context) {
        super(context);
        this.rippleAnimationView = rippleAnimationView;
        setVisibility(4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float fMin = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(fMin, fMin, fMin - this.rippleAnimationView.rippleStrokeWidth, this.rippleAnimationView.paint);
    }
}
