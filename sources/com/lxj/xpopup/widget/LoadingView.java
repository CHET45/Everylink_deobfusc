package com.lxj.xpopup.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LoadingView extends View {
    private ArgbEvaluator argbEvaluator;
    float avgAngle;
    float centerX;
    float centerY;
    private int endColor;
    float endX;
    private Runnable increaseTask;
    int lineCount;
    private Paint paint;
    private float radius;
    private float radiusOffset;
    private int startColor;
    float startX;
    private float stokeWidth;
    int time;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.stokeWidth = 2.0f;
        this.argbEvaluator = new ArgbEvaluator();
        this.startColor = Color.parseColor("#EEEEEE");
        this.endColor = Color.parseColor("#111111");
        this.lineCount = 10;
        this.avgAngle = 360.0f / 10;
        this.time = 0;
        this.increaseTask = new Runnable() { // from class: com.lxj.xpopup.widget.LoadingView.1
            @Override // java.lang.Runnable
            public void run() {
                LoadingView.this.time++;
                LoadingView loadingView = LoadingView.this;
                loadingView.postInvalidate(0, 0, loadingView.getMeasuredWidth(), LoadingView.this.getMeasuredHeight());
                LoadingView loadingView2 = LoadingView.this;
                loadingView2.postDelayed(loadingView2.increaseTask, 80L);
            }
        };
        this.paint = new Paint(1);
        float fDp2px = XPopupUtils.dp2px(context, this.stokeWidth);
        this.stokeWidth = fDp2px;
        this.paint.setStrokeWidth(fDp2px);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float measuredWidth = getMeasuredWidth() / 2;
        this.radius = measuredWidth;
        this.radiusOffset = measuredWidth / 2.5f;
        this.centerX = getMeasuredWidth() / 2;
        this.centerY = getMeasuredHeight() / 2;
        float fDp2px = XPopupUtils.dp2px(getContext(), 2.0f);
        this.stokeWidth = fDp2px;
        this.paint.setStrokeWidth(fDp2px);
        float f = this.centerX + this.radiusOffset;
        this.startX = f;
        this.endX = f + (this.radius / 3.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = this.lineCount - 1; i >= 0; i--) {
            int iAbs = Math.abs(this.time + i);
            this.paint.setColor(((Integer) this.argbEvaluator.evaluate((((iAbs % r2) + 1) * 1.0f) / this.lineCount, Integer.valueOf(this.startColor), Integer.valueOf(this.endColor))).intValue());
            float f = this.startX;
            float f2 = this.centerY;
            canvas.drawLine(f, f2, this.endX, f2, this.paint);
            canvas.drawCircle(this.startX, this.centerY, this.stokeWidth / 2.0f, this.paint);
            canvas.drawCircle(this.endX, this.centerY, this.stokeWidth / 2.0f, this.paint);
            canvas.rotate(this.avgAngle, this.centerX, this.centerY);
        }
    }

    public void start() {
        removeCallbacks(this.increaseTask);
        postDelayed(this.increaseTask, 80L);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.increaseTask);
    }
}
