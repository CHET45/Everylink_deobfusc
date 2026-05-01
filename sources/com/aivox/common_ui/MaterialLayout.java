package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
public class MaterialLayout extends RelativeLayout {
    private static final int DEFAULT_ALPHA = 255;
    private static final int DEFAULT_ALPHA_STEP = 5;
    private static final int DEFAULT_DURATION = 200;
    private static final int DEFAULT_FRAME_RATE = 10;
    private static final int DEFAULT_RADIUS = 10;
    private static final float DEFAULT_SCALE = 0.8f;
    private int mAlphaStep;
    private int mBackupAlpha;
    private Point mCenterPoint;
    private float mCircleScale;
    private int mCirclelColor;
    private int mColorAlpha;
    private int mDuration;
    private int mFrameRate;
    private int mMaxRadius;
    private Paint mPaint;
    private int mRadius;
    private int mRadiusStep;
    private RectF mTargetRectf;
    private View mTargetView;

    public MaterialLayout(Context context) {
        this(context, null);
    }

    public MaterialLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFrameRate = 10;
        this.mDuration = 200;
        this.mPaint = new Paint();
        this.mCenterPoint = null;
        this.mRadius = 10;
        this.mMaxRadius = 10;
        this.mCirclelColor = -3355444;
        this.mRadiusStep = 1;
        this.mCircleScale = DEFAULT_SCALE;
        this.mColorAlpha = 255;
        this.mAlphaStep = 5;
        init(context, attributeSet);
    }

    public MaterialLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFrameRate = 10;
        this.mDuration = 200;
        this.mPaint = new Paint();
        this.mCenterPoint = null;
        this.mRadius = 10;
        this.mMaxRadius = 10;
        this.mCirclelColor = -3355444;
        this.mRadiusStep = 1;
        this.mCircleScale = DEFAULT_SCALE;
        this.mColorAlpha = 255;
        this.mAlphaStep = 5;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (isInEditMode()) {
            return;
        }
        if (attributeSet != null) {
            initTypedArray(context, attributeSet);
        }
        initPaint();
        setWillNotDraw(false);
        setDrawingCacheEnabled(true);
    }

    private void initTypedArray(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.MaterialLayout);
        this.mCirclelColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.MaterialLayout_ml_mycolor, -3355444);
        this.mDuration = typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.MaterialLayout_ml_duration, 200);
        this.mFrameRate = typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.MaterialLayout_ml_framerate, 10);
        this.mColorAlpha = typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.MaterialLayout_ml_alpha, 255);
        this.mCircleScale = typedArrayObtainStyledAttributes.getFloat(C1034R.styleable.MaterialLayout_ml_scale, DEFAULT_SCALE);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initPaint() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mCirclelColor);
        this.mPaint.setAlpha(this.mColorAlpha);
        this.mBackupAlpha = this.mColorAlpha;
    }

    private boolean isInFrame(View view2, float f, float f2) {
        initViewRect(view2);
        return this.mTargetRectf.contains(f, f2);
    }

    private void initViewRect(View view2) {
        int[] iArr = new int[2];
        view2.getLocationOnScreen(iArr);
        this.mTargetRectf = new RectF(iArr[0], iArr[1], r2 + view2.getWidth(), iArr[1] + view2.getHeight());
    }

    private void removeExtraHeight() {
        getLocationOnScreen(new int[2]);
        this.mTargetRectf.top -= r1[1];
        this.mTargetRectf.bottom -= r1[1];
        this.mCenterPoint = new Point(((int) (this.mTargetRectf.left + this.mTargetRectf.right)) / 2, (int) ((this.mTargetRectf.top + this.mTargetRectf.bottom) / 2.0f));
    }

    private View findTargetView(ViewGroup viewGroup, float f, float f2) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                return findTargetView((ViewGroup) childAt, f, f2);
            }
            if (isInFrame(childAt, f, f2)) {
                return childAt;
            }
        }
        return null;
    }

    private boolean isAnimEnd() {
        return this.mRadius >= this.mMaxRadius;
    }

    private void calculateMaxRadius(View view2) {
        int iMax = (int) ((Math.max(view2.getWidth(), view2.getHeight()) / 2) * this.mCircleScale);
        this.mMaxRadius = iMax;
        int i = this.mDuration / this.mFrameRate;
        this.mRadiusStep = (iMax - 10) / i;
        this.mAlphaStep = (this.mColorAlpha - 100) / i;
    }

    private void deliveryTouchDownEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            View viewFindTargetView = findTargetView(this, motionEvent.getRawX(), motionEvent.getRawY());
            this.mTargetView = viewFindTargetView;
            if (viewFindTargetView != null) {
                removeExtraHeight();
                calculateMaxRadius(this.mTargetView);
                invalidate();
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        deliveryTouchDownEvent(motionEvent);
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawRippleIfNecessary(canvas);
    }

    private void drawRippleIfNecessary(Canvas canvas) {
        if (isFoundTouchedSubView()) {
            this.mRadius += this.mRadiusStep;
            this.mColorAlpha -= this.mAlphaStep;
            canvas.clipRect(this.mTargetRectf);
            this.mPaint.setAlpha(this.mColorAlpha);
            canvas.drawCircle(this.mCenterPoint.x, this.mCenterPoint.y, this.mRadius, this.mPaint);
        }
        if (isAnimEnd()) {
            reset();
        } else {
            invalidateDelayed();
        }
    }

    private void invalidateDelayed() {
        postDelayed(new Runnable() { // from class: com.aivox.common_ui.MaterialLayout.1
            @Override // java.lang.Runnable
            public void run() {
                MaterialLayout.this.invalidate();
            }
        }, this.mFrameRate);
    }

    private boolean isFoundTouchedSubView() {
        return (this.mCenterPoint == null || this.mTargetView == null) ? false : true;
    }

    private void reset() {
        this.mCenterPoint = null;
        this.mTargetRectf = null;
        this.mRadius = 10;
        this.mColorAlpha = this.mBackupAlpha;
        this.mTargetView = null;
        invalidate();
    }
}
