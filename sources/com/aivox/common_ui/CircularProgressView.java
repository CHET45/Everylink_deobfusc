package com.aivox.common_ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class CircularProgressView extends View {
    private Paint mBackPaint;
    private Paint mBgPaint;
    private int[] mColorArray;
    private Paint mProgPaint;
    private int mProgress;
    private RectF mRectF;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircularProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.CircularProgressView);
        Paint paint = new Paint();
        this.mBackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mBackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBackPaint.setAntiAlias(true);
        this.mBackPaint.setDither(true);
        this.mBackPaint.setStrokeWidth(typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.CircularProgressView_backWidth, 5.0f));
        this.mBackPaint.setColor(typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircularProgressView_backColor, -3355444));
        Paint paint2 = new Paint();
        this.mProgPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mProgPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mProgPaint.setAntiAlias(true);
        this.mProgPaint.setDither(true);
        this.mProgPaint.setStrokeWidth(typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.CircularProgressView_progWidth, 10.0f));
        this.mProgPaint.setColor(typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircularProgressView_progColor, -16776961));
        Paint paint3 = new Paint();
        this.mBgPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        this.mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBgPaint.setAntiAlias(true);
        this.mBgPaint.setDither(true);
        this.mBgPaint.setStrokeWidth(getWidth() - typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.CircularProgressView_progWidth, 10.0f));
        this.mBgPaint.setColor(typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircularProgressView_bgColor, -1));
        int color = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircularProgressView_progStartColor, -1);
        int color2 = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircularProgressView_progFirstColor, -1);
        if (color != -1 && color2 != -1) {
            this.mColorArray = new int[]{color, color2};
        } else {
            this.mColorArray = null;
        }
        this.mProgress = typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.CircularProgressView_progress, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int strokeWidth = (int) ((measuredWidth > measuredHeight ? measuredHeight : measuredWidth) - (this.mBackPaint.getStrokeWidth() > this.mProgPaint.getStrokeWidth() ? this.mBackPaint : this.mProgPaint).getStrokeWidth());
        this.mRectF = new RectF(getPaddingLeft() + ((measuredWidth - strokeWidth) / 2), getPaddingTop() + ((measuredHeight - strokeWidth) / 2), r1 + strokeWidth, r9 + strokeWidth);
        int[] iArr = this.mColorArray;
        if (iArr == null || iArr.length <= 1) {
            return;
        }
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, this.mBackPaint);
        canvas.drawArc(this.mRectF, 275.0f, (this.mProgress * 360) / 100, false, this.mProgPaint);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, this.mBgPaint);
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setProgress(int i) {
        this.mProgress = i;
        invalidate();
    }

    public void setProgress(int i, long j) {
        if (j <= 0) {
            setProgress(i);
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mProgress, i);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aivox.common_ui.CircularProgressView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircularProgressView.this.mProgress = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                CircularProgressView.this.invalidate();
            }
        });
        valueAnimatorOfInt.setInterpolator(new OvershootInterpolator());
        valueAnimatorOfInt.setDuration(j);
        valueAnimatorOfInt.start();
    }

    public void setBackWidth(int i) {
        this.mBackPaint.setStrokeWidth(i);
        invalidate();
    }

    public void setBackColor(int i) {
        this.mBackPaint.setColor(ContextCompat.getColor(getContext(), i));
        invalidate();
    }

    public void setProgWidth(int i) {
        this.mProgPaint.setStrokeWidth(i);
        invalidate();
    }

    public void setProgColor(int i) {
        this.mProgPaint.setColor(ContextCompat.getColor(getContext(), i));
        this.mProgPaint.setShader(null);
        invalidate();
    }

    public void setProgColor(int i, int i2) {
        this.mColorArray = new int[]{ContextCompat.getColor(getContext(), i), ContextCompat.getColor(getContext(), i2)};
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
        invalidate();
    }

    public void setProgColor(int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            return;
        }
        this.mColorArray = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            this.mColorArray[i] = ContextCompat.getColor(getContext(), iArr[i]);
        }
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
        invalidate();
    }
}
