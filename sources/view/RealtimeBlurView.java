package view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import com.lyd.p014jk.blurviewlibrary.C2318R;
import util.FastBlur;

/* JADX INFO: loaded from: classes5.dex */
public class RealtimeBlurView extends View {
    private static final String TAG = "RealtimeBlurView2";
    private BlurPreDrawListener mBlurPreDrawListener;
    private float mBlurRadius;
    private Bitmap mBlurredBitmap;
    private Bitmap mBlurringBitmap;
    private Canvas mBlurringCanvas;
    private int mOverlayColor;
    private Paint mPaint;
    private Rect mRectDst;
    private Rect mRectSrc;
    private float mRoundCornerRadius;
    private float mScaleFractor;
    private View mTargetView;

    protected boolean canBlur() {
        return true;
    }

    public RealtimeBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2318R.styleable.RealtimeBlurView);
        this.mBlurRadius = typedArrayObtainStyledAttributes.getFloat(C2318R.styleable.RealtimeBlurView_realtimeBlurRadius, 8.0f);
        this.mOverlayColor = typedArrayObtainStyledAttributes.getColor(C2318R.styleable.RealtimeBlurView_realtimeOverlayColor, 67108864);
        this.mScaleFractor = typedArrayObtainStyledAttributes.getFloat(C2318R.styleable.RealtimeBlurView_realtimeDownsampleFactor, 12.0f);
        this.mRoundCornerRadius = typedArrayObtainStyledAttributes.getDimension(C2318R.styleable.RealtimeBlurView_realtimeBlurRoundCornerRadius, TypedValue.applyDimension(1, 0.0f, context.getResources().getDisplayMetrics()));
        typedArrayObtainStyledAttributes.recycle();
        this.mPaint = new Paint();
        this.mRectSrc = new Rect();
        this.mRectDst = new Rect();
        this.mPaint.setColor(this.mOverlayColor);
        this.mBlurPreDrawListener = new BlurPreDrawListener();
    }

    public RealtimeBlurView bindView(View view2) {
        this.mTargetView = view2;
        post(new Runnable() { // from class: view.RealtimeBlurView.1
            @Override // java.lang.Runnable
            public void run() {
                RealtimeBlurView.this.mTargetView.getViewTreeObserver().addOnPreDrawListener(RealtimeBlurView.this.mBlurPreDrawListener);
            }
        });
        return this;
    }

    private class BlurPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        private BlurPreDrawListener() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (RealtimeBlurView.this.canBlur() && RealtimeBlurView.this.prepare()) {
                int[] iArr = new int[2];
                RealtimeBlurView.this.mTargetView.getLocationOnScreen(iArr);
                int i = -iArr[0];
                int i2 = -iArr[1];
                RealtimeBlurView.this.getLocationOnScreen(iArr);
                int i3 = i + iArr[0];
                int i4 = i2 + iArr[1];
                int iSave = RealtimeBlurView.this.mBlurringCanvas.save();
                try {
                    RealtimeBlurView.this.mBlurringCanvas.scale(1.0f / RealtimeBlurView.this.mScaleFractor, 1.0f / RealtimeBlurView.this.mScaleFractor);
                    RealtimeBlurView.this.mBlurringCanvas.translate(-i3, -i4);
                    RealtimeBlurView.this.mTargetView.draw(RealtimeBlurView.this.mBlurringCanvas);
                } catch (RuntimeException unused) {
                } catch (Throwable th) {
                    RealtimeBlurView.this.mBlurringCanvas.restoreToCount(iSave);
                    throw th;
                }
                RealtimeBlurView.this.mBlurringCanvas.restoreToCount(iSave);
                RealtimeBlurView realtimeBlurView = RealtimeBlurView.this;
                realtimeBlurView.mBlurredBitmap = FastBlur.doBlur(realtimeBlurView.mBlurringBitmap, (int) RealtimeBlurView.this.mBlurRadius, true);
            }
            return true;
        }
    }

    protected boolean prepare() {
        if (this.mBlurRadius == 0.0f) {
            return false;
        }
        if (this.mBlurringBitmap == null) {
            this.mBlurringBitmap = Bitmap.createBitmap((int) (getMeasuredWidth() / this.mScaleFractor), (int) (getMeasuredHeight() / this.mScaleFractor), Bitmap.Config.RGB_565);
        }
        if (this.mBlurringBitmap == null) {
            return false;
        }
        if (this.mBlurringCanvas == null) {
            this.mBlurringCanvas = new Canvas(this.mBlurringBitmap);
        }
        if (this.mBlurredBitmap == null) {
            this.mBlurredBitmap = Bitmap.createBitmap((int) (getMeasuredWidth() / this.mScaleFractor), (int) (getMeasuredHeight() / this.mScaleFractor), Bitmap.Config.RGB_565);
        }
        return this.mBlurredBitmap != null;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mBlurredBitmap;
        if (bitmap == null) {
            return;
        }
        drawBlurredBitmap(canvas, bitmap, this.mOverlayColor, this.mRoundCornerRadius);
    }

    protected void drawBlurredBitmap(Canvas canvas, Bitmap bitmap, int i, float f) {
        if (bitmap != null) {
            this.mRectSrc.right = bitmap.getWidth();
            this.mRectSrc.bottom = bitmap.getHeight();
            this.mRectDst.right = getWidth();
            this.mRectDst.bottom = getHeight();
            canvas.drawBitmap(bitmap, this.mRectSrc, this.mRectDst, (Paint) null);
        }
        this.mPaint.setColor(i);
        canvas.drawRect(this.mRectDst, this.mPaint);
    }

    protected View getTargetView() {
        Context context = getContext();
        for (int i = 0; i < 4 && context != null && !(context instanceof Activity) && (context instanceof ContextWrapper); i++) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return ((Activity) context).getWindow().getDecorView();
        }
        return null;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        View view2;
        this.mTargetView = getTargetView();
        super.onAttachedToWindow();
        if (this.mBlurPreDrawListener == null || (view2 = this.mTargetView) == null) {
            return;
        }
        view2.getViewTreeObserver().addOnPreDrawListener(this.mBlurPreDrawListener);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        View view2;
        super.onDetachedFromWindow();
        if (this.mBlurPreDrawListener == null || (view2 = this.mTargetView) == null) {
            return;
        }
        view2.getViewTreeObserver().removeOnPreDrawListener(this.mBlurPreDrawListener);
    }
}
