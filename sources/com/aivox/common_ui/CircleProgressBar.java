package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class CircleProgressBar extends View {
    private static final String COLOR_FFD3D3D5 = "#ffe3e3e5";
    private static final String COLOR_FFF2A670 = "#fff2a670";
    private static final int DEFAULT_LINE_COUNT = 45;
    private static final float DEFAULT_LINE_WIDTH = 4.0f;
    private static final int DEFAULT_MAX = 100;
    private static final float DEFAULT_PROGRESS_STROKE_WIDTH = 1.0f;
    private static final float DEFAULT_PROGRESS_TEXT_SIZE = 11.0f;
    private static final int DEFAULT_START_DEGREE = -90;
    public static final int LINE = 0;
    public static final int LINEAR = 0;
    private static final float LINEAR_START_DEGREE = 90.0f;
    private static final float MAX_DEGREE = 360.0f;
    public static final int RADIAL = 1;
    public static final int SOLID = 1;
    public static final int SOLID_LINE = 2;
    public static final int SWEEP = 2;
    private int mBlurRadius;
    private BlurMaskFilter.Blur mBlurStyle;
    private final RectF mBoundsRectF;
    private Paint.Cap mCap;
    private float mCenterX;
    private float mCenterY;
    private boolean mDrawBackgroundOutsideProgress;
    private int mLineCount;
    private float mLineWidth;
    private int mMax;
    private int mProgress;
    private int mProgressBackgroundColor;
    private final Paint mProgressBackgroundPaint;
    private int mProgressEndColor;
    private ProgressFormatter mProgressFormatter;
    private final Paint mProgressPaint;
    private final RectF mProgressRectF;
    private int mProgressStartColor;
    private float mProgressStrokeWidth;
    private int mProgressTextColor;
    private final Paint mProgressTextPaint;
    private final Rect mProgressTextRect;
    private float mProgressTextSize;
    private float mRadius;
    private int mShader;
    private int mStartDegree;
    private int mStyle;

    public interface ProgressFormatter {
        CharSequence format(int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface ShaderMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface Style {
    }

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgressRectF = new RectF();
        this.mBoundsRectF = new RectF();
        this.mProgressTextRect = new Rect();
        this.mProgressPaint = new Paint(1);
        this.mProgressBackgroundPaint = new Paint(1);
        this.mProgressTextPaint = new TextPaint(1);
        this.mMax = 100;
        this.mProgressFormatter = new DefaultProgressFormatter();
        initFromAttributes(context, attributeSet);
        initPaint();
    }

    private static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void initFromAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.CircleProgressBar);
        this.mLineCount = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_line_count, 45);
        this.mStyle = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_progress_style, 0);
        this.mShader = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_progress_shader, 0);
        this.mCap = typedArrayObtainStyledAttributes.hasValue(C1034R.styleable.CircleProgressBar_progress_stroke_cap) ? Paint.Cap.values()[typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_progress_stroke_cap, 0)] : Paint.Cap.BUTT;
        this.mLineWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1034R.styleable.CircleProgressBar_line_width, dip2px(getContext(), DEFAULT_LINE_WIDTH));
        this.mProgressTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1034R.styleable.CircleProgressBar_progress_text_size, dip2px(getContext(), DEFAULT_PROGRESS_TEXT_SIZE));
        this.mProgressStrokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1034R.styleable.CircleProgressBar_progress_stroke_width, dip2px(getContext(), 1.0f));
        this.mProgressStartColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircleProgressBar_progress_start_color, Color.parseColor(COLOR_FFF2A670));
        this.mProgressEndColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircleProgressBar_progress_end_color, Color.parseColor(COLOR_FFF2A670));
        this.mProgressTextColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircleProgressBar_progress_text_color, Color.parseColor(COLOR_FFF2A670));
        this.mProgressBackgroundColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.CircleProgressBar_progress_background_color, Color.parseColor(COLOR_FFD3D3D5));
        this.mStartDegree = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_progress_start_degree, DEFAULT_START_DEGREE);
        this.mDrawBackgroundOutsideProgress = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.CircleProgressBar_drawBackgroundOutsideProgress, false);
        this.mBlurRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(C1034R.styleable.CircleProgressBar_progress_blur_radius, 0);
        int i = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.CircleProgressBar_progress_blur_style, 0);
        if (i == 1) {
            this.mBlurStyle = BlurMaskFilter.Blur.SOLID;
        } else if (i == 2) {
            this.mBlurStyle = BlurMaskFilter.Blur.OUTER;
        } else if (i == 3) {
            this.mBlurStyle = BlurMaskFilter.Blur.INNER;
        } else {
            this.mBlurStyle = BlurMaskFilter.Blur.NORMAL;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initPaint() {
        this.mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mProgressTextPaint.setTextSize(this.mProgressTextSize);
        this.mProgressPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth(this.mProgressStrokeWidth);
        this.mProgressPaint.setColor(this.mProgressStartColor);
        this.mProgressPaint.setStrokeCap(this.mCap);
        updateMaskBlurFilter();
        this.mProgressBackgroundPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressBackgroundPaint.setStrokeWidth(this.mProgressStrokeWidth);
        this.mProgressBackgroundPaint.setColor(this.mProgressBackgroundColor);
        this.mProgressBackgroundPaint.setStrokeCap(this.mCap);
    }

    private void updateMaskBlurFilter() {
        if (this.mBlurStyle != null && this.mBlurRadius > 0) {
            setLayerType(1, this.mProgressPaint);
            this.mProgressPaint.setMaskFilter(new BlurMaskFilter(this.mBlurRadius, this.mBlurStyle));
        } else {
            this.mProgressPaint.setMaskFilter(null);
        }
    }

    private void updateProgressShader() {
        Shader linearGradient = null;
        if (this.mProgressStartColor != this.mProgressEndColor) {
            int i = this.mShader;
            if (i == 0) {
                linearGradient = new LinearGradient(this.mProgressRectF.left, this.mProgressRectF.top, this.mProgressRectF.left, this.mProgressRectF.bottom, this.mProgressStartColor, this.mProgressEndColor, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                matrix.setRotate(LINEAR_START_DEGREE, this.mCenterX, this.mCenterY);
                linearGradient.setLocalMatrix(matrix);
            } else if (i == 1) {
                linearGradient = new RadialGradient(this.mCenterX, this.mCenterY, this.mRadius, this.mProgressStartColor, this.mProgressEndColor, Shader.TileMode.CLAMP);
            } else if (i == 2) {
                float f = (float) (-((this.mCap == Paint.Cap.BUTT && this.mStyle == 2) ? 0.0d : Math.toDegrees((float) (((((double) this.mProgressStrokeWidth) / 3.141592653589793d) * 2.0d) / ((double) this.mRadius)))));
                linearGradient = new SweepGradient(this.mCenterX, this.mCenterY, new int[]{this.mProgressStartColor, this.mProgressEndColor}, new float[]{0.0f, 1.0f});
                Matrix matrix2 = new Matrix();
                matrix2.setRotate(f, this.mCenterX, this.mCenterY);
                linearGradient.setLocalMatrix(matrix2);
            }
            this.mProgressPaint.setShader(linearGradient);
            return;
        }
        this.mProgressPaint.setShader(null);
        this.mProgressPaint.setColor(this.mProgressStartColor);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(this.mStartDegree, this.mCenterX, this.mCenterY);
        drawProgress(canvas);
        canvas.restore();
        drawProgressText(canvas);
    }

    private void drawProgressText(Canvas canvas) {
        ProgressFormatter progressFormatter = this.mProgressFormatter;
        if (progressFormatter == null) {
            return;
        }
        CharSequence charSequence = progressFormatter.format(this.mProgress, this.mMax);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        this.mProgressTextPaint.setTextSize(this.mProgressTextSize);
        this.mProgressTextPaint.setColor(this.mProgressTextColor);
        this.mProgressTextPaint.getTextBounds(String.valueOf(charSequence), 0, charSequence.length(), this.mProgressTextRect);
        canvas.drawText(charSequence, 0, charSequence.length(), this.mCenterX, this.mCenterY + (this.mProgressTextRect.height() / 2), this.mProgressTextPaint);
    }

    private void drawProgress(Canvas canvas) {
        int i = this.mStyle;
        if (i == 1) {
            drawSolidProgress(canvas);
        } else if (i == 2) {
            drawSolidLineProgress(canvas);
        } else {
            drawLineProgress(canvas);
        }
    }

    private void drawLineProgress(Canvas canvas) {
        int i = this.mLineCount;
        float f = (float) (6.283185307179586d / ((double) i));
        float f2 = this.mRadius;
        float f3 = f2 - this.mLineWidth;
        int i2 = (int) ((this.mProgress / this.mMax) * i);
        for (int i3 = 0; i3 < this.mLineCount; i3++) {
            double d = i3 * (-f);
            float fCos = (((float) Math.cos(d)) * f3) + this.mCenterX;
            float fSin = this.mCenterY - (((float) Math.sin(d)) * f3);
            float fCos2 = this.mCenterX + (((float) Math.cos(d)) * f2);
            float fSin2 = this.mCenterY - (((float) Math.sin(d)) * f2);
            if (!this.mDrawBackgroundOutsideProgress || i3 >= i2) {
                canvas.drawLine(fCos, fSin, fCos2, fSin2, this.mProgressBackgroundPaint);
            }
            if (i3 < i2) {
                canvas.drawLine(fCos, fSin, fCos2, fSin2, this.mProgressPaint);
            }
        }
    }

    private void drawSolidProgress(Canvas canvas) {
        if (this.mDrawBackgroundOutsideProgress) {
            float f = (this.mProgress * MAX_DEGREE) / this.mMax;
            canvas.drawArc(this.mProgressRectF, f, MAX_DEGREE - f, true, this.mProgressBackgroundPaint);
        } else {
            canvas.drawArc(this.mProgressRectF, 0.0f, MAX_DEGREE, true, this.mProgressBackgroundPaint);
        }
        canvas.drawArc(this.mProgressRectF, 0.0f, (this.mProgress * MAX_DEGREE) / this.mMax, true, this.mProgressPaint);
    }

    private void drawSolidLineProgress(Canvas canvas) {
        if (this.mDrawBackgroundOutsideProgress) {
            float f = (this.mProgress * MAX_DEGREE) / this.mMax;
            canvas.drawArc(this.mProgressRectF, f, MAX_DEGREE - f, false, this.mProgressBackgroundPaint);
        } else {
            canvas.drawArc(this.mProgressRectF, 0.0f, MAX_DEGREE, false, this.mProgressBackgroundPaint);
        }
        canvas.drawArc(this.mProgressRectF, 0.0f, (this.mProgress * MAX_DEGREE) / this.mMax, false, this.mProgressPaint);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mBoundsRectF.left = getPaddingLeft();
        this.mBoundsRectF.top = getPaddingTop();
        this.mBoundsRectF.right = i - getPaddingRight();
        this.mBoundsRectF.bottom = i2 - getPaddingBottom();
        this.mCenterX = this.mBoundsRectF.centerX();
        this.mCenterY = this.mBoundsRectF.centerY();
        this.mRadius = Math.min(this.mBoundsRectF.width(), this.mBoundsRectF.height()) / 2.0f;
        this.mProgressRectF.set(this.mBoundsRectF);
        updateProgressShader();
        RectF rectF = this.mProgressRectF;
        float f = this.mProgressStrokeWidth;
        rectF.inset(f / 2.0f, f / 2.0f);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress);
    }

    public void setProgressFormatter(ProgressFormatter progressFormatter) {
        this.mProgressFormatter = progressFormatter;
        invalidate();
    }

    public void setProgressStrokeWidth(float f) {
        this.mProgressStrokeWidth = f;
        this.mProgressRectF.set(this.mBoundsRectF);
        updateProgressShader();
        RectF rectF = this.mProgressRectF;
        float f2 = this.mProgressStrokeWidth;
        rectF.inset(f2 / 2.0f, f2 / 2.0f);
        invalidate();
    }

    public void setProgressTextSize(float f) {
        this.mProgressTextSize = f;
        invalidate();
    }

    public void setProgressStartColor(int i) {
        this.mProgressStartColor = i;
        updateProgressShader();
        invalidate();
    }

    public void setProgressEndColor(int i) {
        this.mProgressEndColor = i;
        updateProgressShader();
        invalidate();
    }

    public void setProgressTextColor(int i) {
        this.mProgressTextColor = i;
        invalidate();
    }

    public void setProgressBackgroundColor(int i) {
        this.mProgressBackgroundColor = i;
        this.mProgressBackgroundPaint.setColor(i);
        invalidate();
    }

    public void setLineCount(int i) {
        this.mLineCount = i;
        invalidate();
    }

    public void setLineWidth(float f) {
        this.mLineWidth = f;
        invalidate();
    }

    public void setStyle(int i) {
        this.mStyle = i;
        this.mProgressPaint.setStyle(i == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mProgressBackgroundPaint.setStyle(this.mStyle == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        invalidate();
    }

    public void setBlurRadius(int i) {
        this.mBlurRadius = i;
        updateMaskBlurFilter();
        invalidate();
    }

    public void setBlurStyle(BlurMaskFilter.Blur blur) {
        this.mBlurStyle = blur;
        updateMaskBlurFilter();
        invalidate();
    }

    public void setShader(int i) {
        this.mShader = i;
        updateProgressShader();
        invalidate();
    }

    public void setCap(Paint.Cap cap) {
        this.mCap = cap;
        this.mProgressPaint.setStrokeCap(cap);
        this.mProgressBackgroundPaint.setStrokeCap(cap);
        invalidate();
    }

    public void setStartDegree(int i) {
        this.mStartDegree = i;
        invalidate();
    }

    public void setDrawBackgroundOutsideProgress(boolean z) {
        this.mDrawBackgroundOutsideProgress = z;
        invalidate();
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setProgress(int i) {
        this.mProgress = i;
        invalidate();
    }

    public int getMax() {
        return this.mMax;
    }

    public void setMax(int i) {
        this.mMax = i;
        invalidate();
    }

    private static final class DefaultProgressFormatter implements ProgressFormatter {
        private static final String DEFAULT_PATTERN = "%d%%";

        private DefaultProgressFormatter() {
        }

        @Override // com.aivox.common_ui.CircleProgressBar.ProgressFormatter
        public CharSequence format(int i, int i2) {
            return String.format(DEFAULT_PATTERN, Integer.valueOf((int) ((i / i2) * 100.0f)));
        }
    }

    private static final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.aivox.common_ui.CircleProgressBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int progress;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
        }
    }
}
