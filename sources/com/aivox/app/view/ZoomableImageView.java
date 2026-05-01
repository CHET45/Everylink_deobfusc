package com.aivox.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.aivox.app.listener.OnImageTouchedListener;
import com.aivox.base.util.BaseAppUtils;

/* JADX INFO: loaded from: classes.dex */
public class ZoomableImageView extends View {
    static final int sAnimationDelay = 500;
    static final int sPaintDelay = 250;
    static final float sPanRate = 7.0f;
    static final float sScaleRate = 1.25f;
    private Matrix mBaseMatrix;
    private Bitmap mBitmap;
    private Matrix mDisplayMatrix;
    private Runnable mFling;
    private GestureDetector mGestureDetector;
    private OnImageTouchedListener mImageTouchedListener;
    private double mLastDraw;
    private Matrix mMatrix;
    private float[] mMatrixValues;
    private float mMaxZoom;
    private Runnable mOnLayoutRunnable;
    private Paint mPaint;
    private Runnable mRefresh;
    private ScaleGestureDetector mScaleDetector;
    private Matrix mSuppMatrix;
    private int mThisHeight;
    private int mThisWidth;

    /* JADX INFO: Access modifiers changed from: private */
    public float easeOut(float f, float f2, float f3, float f4) {
        float f5 = (f / f4) - 1.0f;
        return (f3 * ((f5 * f5 * f5) + 1.0f)) + f2;
    }

    public ZoomableImageView(Context context) {
        super(context);
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mDisplayMatrix = new Matrix();
        this.mMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mOnLayoutRunnable = null;
        this.mRefresh = null;
        this.mFling = null;
        this.mLastDraw = 0.0d;
        init(context);
    }

    public void setOnImageTouchedListener(OnImageTouchedListener onImageTouchedListener) {
        this.mImageTouchedListener = onImageTouchedListener;
    }

    public ZoomableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mDisplayMatrix = new Matrix();
        this.mMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mOnLayoutRunnable = null;
        this.mRefresh = null;
        this.mFling = null;
        this.mLastDraw = 0.0d;
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setDither(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.mRefresh = new Runnable() { // from class: com.aivox.app.view.ZoomableImageView.1
            @Override // java.lang.Runnable
            public void run() {
                ZoomableImageView.this.postInvalidate();
            }
        };
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    public Bitmap getImageBitmap() {
        return this.mBitmap;
    }

    public void clear() {
        if (this.mBitmap != null) {
            this.mBitmap = null;
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mThisWidth = i3 - i;
        this.mThisHeight = i4 - i2;
        Runnable runnable = this.mOnLayoutRunnable;
        if (runnable != null) {
            this.mOnLayoutRunnable = null;
            runnable.run();
        }
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            setBaseMatrix(bitmap, this.mBaseMatrix);
            setImageMatrix(getImageViewMatrix());
        }
    }

    private static void translatePoint(Matrix matrix, float[] fArr) {
        matrix.mapPoints(fArr);
    }

    public void setImageMatrix(Matrix matrix) {
        if (matrix != null && matrix.isIdentity()) {
            matrix = null;
        }
        if ((matrix != null || this.mMatrix.isIdentity()) && (matrix == null || this.mMatrix.equals(matrix))) {
            return;
        }
        this.mMatrix.set(matrix);
        invalidate();
    }

    public void setImageBitmap(final Bitmap bitmap) {
        if (getWidth() <= 0) {
            this.mOnLayoutRunnable = new Runnable() { // from class: com.aivox.app.view.ZoomableImageView.2
                @Override // java.lang.Runnable
                public void run() {
                    ZoomableImageView.this.setImageBitmap(bitmap);
                }
            };
            return;
        }
        if (bitmap != null) {
            setBaseMatrix(bitmap, this.mBaseMatrix);
            this.mBitmap = bitmap;
        } else {
            this.mBaseMatrix.reset();
            this.mBitmap = bitmap;
        }
        this.mSuppMatrix.reset();
        setImageMatrix(getImageViewMatrix());
        this.mMaxZoom = maxZoom();
        zoomTo(zoomDefault());
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void center(boolean r10, boolean r11, boolean r12) {
        /*
            r9 = this;
            android.graphics.Bitmap r0 = r9.mBitmap
            if (r0 != 0) goto L5
            return
        L5:
            android.graphics.Matrix r0 = r9.getImageViewMatrix()
            r1 = 2
            float[] r2 = new float[r1]
            r2 = {x00a4: FILL_ARRAY_DATA , data: [0, 0} // fill-array
            android.graphics.Bitmap r3 = r9.mBitmap
            int r3 = r3.getWidth()
            float r3 = (float) r3
            android.graphics.Bitmap r4 = r9.mBitmap
            int r4 = r4.getHeight()
            float r4 = (float) r4
            float[] r1 = new float[r1]
            r5 = 0
            r1[r5] = r3
            r3 = 1
            r1[r3] = r4
            translatePoint(r0, r2)
            translatePoint(r0, r1)
            r0 = r1[r3]
            r4 = r2[r3]
            float r0 = r0 - r4
            r4 = r1[r5]
            r6 = r2[r5]
            float r4 = r4 - r6
            r6 = 1073741824(0x40000000, float:2.0)
            r7 = 0
            if (r10 == 0) goto L5f
            int r10 = r9.getHeight()
            float r10 = (float) r10
            int r8 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r8 >= 0) goto L49
            float r10 = r10 - r0
            float r10 = r10 / r6
            r0 = r2[r3]
        L47:
            float r10 = r10 - r0
            goto L60
        L49:
            r0 = r2[r3]
            int r8 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r8 <= 0) goto L51
            float r10 = -r0
            goto L60
        L51:
            r0 = r1[r3]
            int r10 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r10 >= 0) goto L5f
            int r10 = r9.getHeight()
            float r10 = (float) r10
            r0 = r1[r3]
            goto L47
        L5f:
            r10 = r7
        L60:
            if (r11 == 0) goto L80
            int r11 = r9.getWidth()
            float r11 = (float) r11
            int r0 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r0 >= 0) goto L71
            float r11 = r11 - r4
            float r11 = r11 / r6
            r0 = r2[r5]
        L6f:
            float r11 = r11 - r0
            goto L81
        L71:
            r0 = r2[r5]
            int r2 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r2 <= 0) goto L79
            float r11 = -r0
            goto L81
        L79:
            r0 = r1[r5]
            int r1 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r1 >= 0) goto L80
            goto L6f
        L80:
            r11 = r7
        L81:
            r9.postTranslate(r11, r10)
            if (r12 == 0) goto L9c
            android.view.animation.TranslateAnimation r12 = new android.view.animation.TranslateAnimation
            float r11 = -r11
            float r10 = -r10
            r12.<init>(r11, r7, r10, r7)
            long r10 = android.os.SystemClock.elapsedRealtime()
            r12.setStartTime(r10)
            r10 = 250(0xfa, double:1.235E-321)
            r12.setDuration(r10)
            r9.setAnimation(r12)
        L9c:
            android.graphics.Matrix r10 = r9.getImageViewMatrix()
            r9.setImageMatrix(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.view.ZoomableImageView.center(boolean, boolean, boolean):void");
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    protected float getScale(Matrix matrix) {
        if (this.mBitmap != null) {
            return getValue(matrix, 0);
        }
        return 1.0f;
    }

    public float getScale() {
        return getScale(this.mSuppMatrix);
    }

    private void setBaseMatrix(Bitmap bitmap, Matrix matrix) {
        float width = getWidth();
        float height = getHeight();
        matrix.reset();
        float fMin = Math.min(width / bitmap.getWidth(), 1.0f);
        float fMin2 = Math.min(height / bitmap.getHeight(), 1.0f);
        if (fMin > fMin2) {
            fMin = fMin2;
        }
        matrix.setScale(fMin, fMin);
        matrix.postTranslate((width - (bitmap.getWidth() * fMin)) / 2.0f, (height - (bitmap.getHeight() * fMin)) / 2.0f);
    }

    protected Matrix getImageViewMatrix() {
        this.mDisplayMatrix.set(this.mBaseMatrix);
        this.mDisplayMatrix.postConcat(this.mSuppMatrix);
        return this.mDisplayMatrix;
    }

    protected float maxZoom() {
        if (this.mBitmap == null) {
            return 1.0f;
        }
        return Math.max(r0.getWidth() / this.mThisWidth, this.mBitmap.getHeight() / this.mThisHeight) * 16.0f;
    }

    public float zoomDefault() {
        if (this.mBitmap == null) {
            return 1.0f;
        }
        return Math.max(Math.min(this.mThisWidth / r0.getWidth(), this.mThisHeight / this.mBitmap.getHeight()), 1.0f);
    }

    protected void zoomTo(float f, float f2, float f3) {
        float f4 = this.mMaxZoom;
        if (f > f4) {
            f = f4;
        }
        float scale = f / getScale();
        this.mSuppMatrix.postScale(scale, scale, f2, f3);
        setImageMatrix(getImageViewMatrix());
        center(true, true, false);
    }

    protected void zoomTo(float f, final float f2, final float f3, final float f4) {
        final float scale = (f - getScale()) / f4;
        final float scale2 = getScale();
        final long jCurrentTimeMillis = System.currentTimeMillis();
        post(new Runnable() { // from class: com.aivox.app.view.ZoomableImageView.3
            @Override // java.lang.Runnable
            public void run() {
                float fMin = Math.min(f4, System.currentTimeMillis() - jCurrentTimeMillis);
                ZoomableImageView.this.zoomTo(scale2 + (scale * fMin), f2, f3);
                if (fMin < f4) {
                    ZoomableImageView.this.post(this);
                }
            }
        });
    }

    public void zoomTo(float f) {
        zoomTo(f, getWidth() / 2.0f, getHeight() / 2.0f);
    }

    protected void zoomIn() {
        zoomIn(sScaleRate);
    }

    protected void zoomOut() {
        zoomOut(sScaleRate);
    }

    protected void zoomIn(float f) {
        if (getScale() < this.mMaxZoom && this.mBitmap != null) {
            this.mSuppMatrix.postScale(f, f, getWidth() / 2.0f, getHeight() / 2.0f);
            setImageMatrix(getImageViewMatrix());
        }
    }

    protected void zoomOut(float f) {
        if (this.mBitmap == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();
        Matrix matrix = new Matrix(this.mSuppMatrix);
        float f2 = width / 2.0f;
        float f3 = height / 2.0f;
        matrix.postScale(0.8f, 0.8f, f2, f3);
        if (getScale(matrix) < 1.0f) {
            this.mSuppMatrix.setScale(1.0f, 1.0f, f2, f3);
        } else {
            float f4 = 1.0f / f;
            this.mSuppMatrix.postScale(f4, f4, f2, f3);
        }
        setImageMatrix(getImageViewMatrix());
        center(true, true, false);
    }

    protected void postTranslate(float f, float f2) {
        this.mSuppMatrix.postTranslate(f, f2);
    }

    protected void scrollBy(final float f, final float f2, final float f3) {
        final long jCurrentTimeMillis = System.currentTimeMillis();
        Runnable runnable = new Runnable() { // from class: com.aivox.app.view.ZoomableImageView.4
            float old_x = 0.0f;
            float old_y = 0.0f;

            @Override // java.lang.Runnable
            public void run() {
                float fMin = Math.min(f3, System.currentTimeMillis() - jCurrentTimeMillis);
                float fEaseOut = ZoomableImageView.this.easeOut(fMin, 0.0f, f, f3);
                float fEaseOut2 = ZoomableImageView.this.easeOut(fMin, 0.0f, f2, f3);
                ZoomableImageView.this.postTranslate(fEaseOut - this.old_x, fEaseOut2 - this.old_y);
                ZoomableImageView.this.center(true, true, false);
                this.old_x = fEaseOut;
                this.old_y = fEaseOut2;
                if (fMin < f3) {
                    ZoomableImageView.this.post(this);
                }
            }
        };
        this.mFling = runnable;
        post(runnable);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        canvas.drawBitmap(this.mBitmap, this.mMatrix, null);
    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (scaleGestureDetector == null || !scaleGestureDetector.isInProgress()) {
                return false;
            }
            try {
                ZoomableImageView.this.zoomTo(Math.min(ZoomableImageView.this.maxZoom(), Math.max(ZoomableImageView.this.getScale() * scaleGestureDetector.getScaleFactor(), 1.0f)), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                ZoomableImageView.this.invalidate();
                return true;
            } catch (IllegalArgumentException e) {
                BaseAppUtils.printErrorMsg(e);
                return false;
            }
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private MyGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (ZoomableImageView.this.mImageTouchedListener != null) {
                ZoomableImageView.this.mImageTouchedListener.onImageTouched();
                return false;
            }
            return super.onSingleTapConfirmed(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if ((motionEvent != null && motionEvent.getPointerCount() > 1) || ((motionEvent2 != null && motionEvent2.getPointerCount() > 1) || (ZoomableImageView.this.mScaleDetector != null && ZoomableImageView.this.mScaleDetector.isInProgress()))) {
                return false;
            }
            if (ZoomableImageView.this.getScale() > ZoomableImageView.this.zoomDefault()) {
                ZoomableImageView zoomableImageView = ZoomableImageView.this;
                zoomableImageView.removeCallbacks(zoomableImageView.mFling);
                ZoomableImageView.this.postTranslate(-f, -f2);
                ZoomableImageView.this.center(true, true, false);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (ZoomableImageView.this.getScale() > ZoomableImageView.this.zoomDefault()) {
                ZoomableImageView zoomableImageView = ZoomableImageView.this;
                zoomableImageView.zoomTo(zoomableImageView.zoomDefault());
                return true;
            }
            ZoomableImageView zoomableImageView2 = ZoomableImageView.this;
            zoomableImageView2.zoomTo(zoomableImageView2.zoomDefault() * 3.0f, motionEvent.getX(), motionEvent.getY(), 200.0f);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if ((motionEvent != null && motionEvent.getPointerCount() > 1) || ((motionEvent2 != null && motionEvent2.getPointerCount() > 1) || ZoomableImageView.this.mScaleDetector.isInProgress())) {
                return false;
            }
            try {
                float x = motionEvent2.getX() - motionEvent.getX();
                float y = motionEvent2.getY() - motionEvent.getY();
                if (Math.abs(f) > 800.0f || Math.abs(f2) > 800.0f) {
                    ZoomableImageView.this.scrollBy(x / 2.0f, y / 2.0f, 300.0f);
                    ZoomableImageView.this.invalidate();
                }
            } catch (NullPointerException e) {
                BaseAppUtils.printErrorMsg(e);
            }
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mBitmap == null) {
            return true;
        }
        this.mScaleDetector.onTouchEvent(motionEvent);
        if (this.mScaleDetector.isInProgress()) {
            return true;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
