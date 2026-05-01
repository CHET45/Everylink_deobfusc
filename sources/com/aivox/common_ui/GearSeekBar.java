package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class GearSeekBar extends View {
    private int currentGear;
    private float effectiveWidth;
    private int gearCount;
    private OnGearChangedListener listener;
    private int progressColor;
    private Paint progressPaint;
    private final RectF progressRect;
    private float thumbCenterX;
    private int thumbColor;
    private Paint thumbPaint;
    private float thumbRadius;
    private int trackColor;
    private float trackCornerRadius;
    private float trackHeight;
    private Paint trackPaint;
    private final RectF trackRect;

    public interface OnGearChangedListener {
        void onGearChanged(int i);
    }

    public GearSeekBar(Context context) {
        super(context);
        this.trackColor = -3355444;
        this.progressColor = -16776961;
        this.trackHeight = 20.0f;
        this.trackCornerRadius = 10.0f;
        this.thumbColor = -16776961;
        this.thumbRadius = 40.0f;
        this.gearCount = 4;
        this.currentGear = 0;
        this.trackRect = new RectF();
        this.progressRect = new RectF();
        init(null);
    }

    public GearSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.trackColor = -3355444;
        this.progressColor = -16776961;
        this.trackHeight = 20.0f;
        this.trackCornerRadius = 10.0f;
        this.thumbColor = -16776961;
        this.thumbRadius = 40.0f;
        this.gearCount = 4;
        this.currentGear = 0;
        this.trackRect = new RectF();
        this.progressRect = new RectF();
        init(attributeSet);
    }

    public GearSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.trackColor = -3355444;
        this.progressColor = -16776961;
        this.trackHeight = 20.0f;
        this.trackCornerRadius = 10.0f;
        this.thumbColor = -16776961;
        this.thumbRadius = 40.0f;
        this.gearCount = 4;
        this.currentGear = 0;
        this.trackRect = new RectF();
        this.progressRect = new RectF();
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        this.trackPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.thumbPaint = new Paint(1);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1034R.styleable.GearSeekBar);
            this.trackColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.GearSeekBar_gsv_trackColor, Color.parseColor("#E0E0E0"));
            this.progressColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.GearSeekBar_gsv_progressColor, Color.parseColor("#007AFF"));
            this.trackHeight = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GearSeekBar_gsv_trackHeight, 12.0f);
            this.trackCornerRadius = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GearSeekBar_gsv_trackCornerRadius, 6.0f);
            this.thumbColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.GearSeekBar_gsv_thumbColor, Color.parseColor("#007AFF"));
            this.thumbRadius = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.GearSeekBar_gsv_thumbRadius, 24.0f);
            this.gearCount = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.GearSeekBar_gsv_gearCount, 4);
            this.currentGear = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.GearSeekBar_gsv_initialGear, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
        this.trackPaint.setColor(this.trackColor);
        this.progressPaint.setColor(this.progressColor);
        this.thumbPaint.setColor(this.thumbColor);
        if (this.gearCount < 2) {
            this.gearCount = 2;
        }
        setGear(this.currentGear, false);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(View.MeasureSpec.getSize(i), resolveSize(((int) (this.thumbRadius * 2.0f)) + getPaddingTop() + getPaddingBottom(), i2));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float paddingTop = (i2 - getPaddingTop()) - getPaddingBottom();
        float paddingTop2 = getPaddingTop();
        float f = this.trackHeight;
        float f2 = paddingTop2 + ((paddingTop - f) / 2.0f);
        float paddingLeft = getPaddingLeft() + this.thumbRadius;
        float paddingRight = (i - getPaddingRight()) - this.thumbRadius;
        this.effectiveWidth = paddingRight - paddingLeft;
        this.trackRect.set(paddingLeft, f2, paddingRight, f + f2);
        this.progressRect.set(this.trackRect);
        this.thumbCenterX = getXForGear(this.currentGear);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.trackRect;
        float f = this.trackCornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.trackPaint);
        this.progressRect.right = this.thumbCenterX;
        RectF rectF2 = this.progressRect;
        float f2 = this.trackCornerRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.progressPaint);
        canvas.drawCircle(this.thumbCenterX, getHeight() / 2.0f, this.thumbRadius, this.thumbPaint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                OnGearChangedListener onGearChangedListener = this.listener;
                if (onGearChangedListener != null) {
                    onGearChangedListener.onGearChanged(this.currentGear);
                }
                return true;
            }
            if (action != 2) {
                return super.onTouchEvent(motionEvent);
            }
        }
        updateGearFromTouchEvent(motionEvent.getX());
        return true;
    }

    private void updateGearFromTouchEvent(float f) {
        int closestGear = getClosestGear(f);
        if (closestGear != this.currentGear) {
            this.currentGear = closestGear;
            this.thumbCenterX = getXForGear(closestGear);
            invalidate();
        }
    }

    private int getClosestGear(float f) {
        float f2 = Float.MAX_VALUE;
        int i = 0;
        for (int i2 = 0; i2 < this.gearCount; i2++) {
            float fAbs = Math.abs(f - getXForGear(i2));
            if (fAbs < f2) {
                i = i2;
                f2 = fAbs;
            }
        }
        return i;
    }

    private float getXForGear(int i) {
        if (this.gearCount <= 1) {
            return getPaddingLeft() + this.thumbRadius;
        }
        return getPaddingLeft() + this.thumbRadius + (i * (this.effectiveWidth / (r0 - 1)));
    }

    public void setOnGearChangedListener(OnGearChangedListener onGearChangedListener) {
        this.listener = onGearChangedListener;
    }

    public void setGear(int i, boolean z) {
        OnGearChangedListener onGearChangedListener;
        int iMax = Math.max(0, Math.min(i, this.gearCount - 1));
        if (iMax != this.currentGear) {
            this.currentGear = iMax;
            this.thumbCenterX = getXForGear(iMax);
            invalidate();
            if (!z || (onGearChangedListener = this.listener) == null) {
                return;
            }
            onGearChangedListener.onGearChanged(this.currentGear);
        }
    }

    public int getGear() {
        return this.currentGear;
    }
}
