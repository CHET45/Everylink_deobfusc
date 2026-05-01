package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import com.google.android.gms.common.ConnectionResult;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes.dex */
public class RulerView extends View {
    final String TAG;
    private int mBeginRange;
    private int mEndRange;
    private int mGravity;
    private int mIndicateColor;
    private int mIndicateColor2;
    private Rect mIndicateLoc;
    private int mIndicatePadding;
    private Paint mIndicatePaint;
    private float mIndicateScale;
    private int mIndicateWidth;
    private int mInnerWidth;
    private boolean mIsAutoAlign;
    private boolean mIsDragged;
    private boolean mIsWithText;
    private int mLastMotionX;
    private OnScaleListener mListener;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private OverScroller mOverScroller;
    private int mTextColor;
    private Paint mTextPaint;
    private float mTextSize;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public interface OnScaleListener {
        void onScaleChanged(int i);
    }

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RulerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "RulerView";
        this.mIsAutoAlign = true;
        this.mIsWithText = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.RulerView);
        this.mIndicateColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.RulerView_ruler_indicateColor, ViewCompat.MEASURED_STATE_MASK);
        this.mIndicateColor2 = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.RulerView_ruler_indicateColor2, ViewCompat.MEASURED_STATE_MASK);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.RulerView_ruler_textColor, -7829368);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.RulerView_ruler_textSize, 18.0f);
        this.mBeginRange = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.RulerView_ruler_begin, 0);
        this.mEndRange = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.RulerView_ruler_end, 100);
        this.mIndicateWidth = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.RulerView_ruler_indicateWidth, 5.0f);
        this.mIndicatePadding = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.RulerView_ruler_indicatePadding, 15.0f);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.gravity});
        this.mGravity = typedArrayObtainStyledAttributes2.getInt(typedArrayObtainStyledAttributes2.getIndex(0), 80);
        typedArrayObtainStyledAttributes2.recycle();
        this.mIndicateScale = 0.7f;
        initValue();
    }

    private void initValue() {
        this.mOverScroller = new OverScroller(getContext());
        setOverScrollMode(0);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        Paint paint = new Paint();
        this.mIndicatePaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.mTextPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mInnerWidth = (this.mEndRange - this.mBeginRange) * getIndicateWidth();
        this.mIndicateLoc = new Rect();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int iSave = canvas.save();
        int i = this.mBeginRange;
        int i2 = 0;
        while (i <= this.mEndRange) {
            drawIndicate(canvas, i2);
            if (this.mIsWithText) {
                drawText(canvas, i2, numberToTime(i));
            }
            i++;
            i2++;
        }
        canvas.restoreToCount(iSave);
    }

    public static String numberToTime(int i) {
        return new DecimalFormat("00").format(i / 3600) + ":" + new DecimalFormat("00").format((i % 3600) / 60) + ":" + new DecimalFormat("00").format(i % 60);
    }

    private void drawIndicate(Canvas canvas, int i) {
        computeIndicateLoc(this.mIndicateLoc, i);
        int i2 = this.mIndicateLoc.left + this.mIndicatePadding;
        int i3 = this.mIndicateLoc.right - this.mIndicatePadding;
        int i4 = this.mIndicateLoc.top;
        int i5 = this.mIndicateLoc.bottom;
        if (i % 5 != 0) {
            int i6 = i5 - i4;
            if (isAlignTop()) {
                i5 = (int) (i4 + (i6 * this.mIndicateScale));
            } else {
                i4 = (int) (i5 - (i6 * this.mIndicateScale));
            }
            this.mIndicatePaint.setColor(this.mIndicateColor2);
        } else {
            this.mIndicatePaint.setColor(this.mIndicateColor);
        }
        canvas.drawRect(i2, i4, i3, i5, this.mIndicatePaint);
    }

    private void drawText(Canvas canvas, int i, String str) {
        if (i % 5 != 0) {
            return;
        }
        computeIndicateLoc(this.mIndicateLoc, i);
        int iComputeTextHeight = computeTextHeight();
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        int i2 = (this.mIndicateLoc.left + this.mIndicateLoc.right) / 2;
        int i3 = this.mIndicateLoc.bottom + iComputeTextHeight;
        if (!isAlignTop()) {
            int i4 = this.mIndicateLoc.top;
            this.mTextPaint.getTextBounds(str, 0, str.length(), this.mIndicateLoc);
            i3 = (this.mIndicateLoc.top / 2) + i4;
        }
        canvas.drawText(str, i2, i3, this.mTextPaint);
    }

    private void computeIndicateLoc(Rect rect, int i) {
        if (rect == null) {
            return;
        }
        int height = getHeight();
        int indicateWidth = getIndicateWidth();
        int i2 = i * indicateWidth;
        int i3 = indicateWidth + i2;
        int paddingTop = getPaddingTop();
        int paddingBottom = height - getPaddingBottom();
        if (this.mIsWithText) {
            int iComputeTextHeight = computeTextHeight();
            if (isAlignTop()) {
                paddingBottom -= iComputeTextHeight;
            } else {
                paddingTop += iComputeTextHeight;
            }
        }
        int startOffsets = getStartOffsets();
        rect.set(i2 + startOffsets, paddingTop, i3 + startOffsets, paddingBottom);
    }

    private int getStartOffsets() {
        if (!this.mIsWithText) {
            return 0;
        }
        String strValueOf = String.valueOf(this.mBeginRange);
        return ((int) this.mTextPaint.measureText(strValueOf, 0, strValueOf.length())) / 2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action == 0) {
            boolean zIsFinished = this.mOverScroller.isFinished();
            this.mIsDragged = !zIsFinished;
            if (!zIsFinished && getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mOverScroller.isFinished()) {
                this.mOverScroller.abortAnimation();
            }
            this.mLastMotionX = (int) motionEvent.getX();
            return true;
        }
        if (action == 1) {
            if (this.mIsDragged) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int xVelocity = (int) this.mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > this.mMinimumVelocity) {
                    fling(-xVelocity);
                } else {
                    sprintBack();
                }
            }
            this.mIsDragged = false;
            recycleVelocityTracker();
        } else if (action == 2) {
            int x = (int) motionEvent.getX();
            int i = this.mLastMotionX - x;
            if (!this.mIsDragged && Math.abs(i) > this.mTouchSlop) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                this.mIsDragged = true;
                i = i > 0 ? i - this.mTouchSlop : i + this.mTouchSlop;
            }
            if (this.mIsDragged) {
                this.mLastMotionX = x;
                if (getScrollX() <= 0 || getScrollX() >= getMaximumScroll()) {
                    i = (int) (((double) i) * 0.7d);
                }
                if (overScrollBy(i, 0, getScrollX(), getScrollY(), getMaximumScroll(), 0, getWidth(), 0, true)) {
                    this.mVelocityTracker.clear();
                }
            }
        } else if (action == 3) {
            if (this.mIsDragged && this.mOverScroller.isFinished()) {
                sprintBack();
            }
            this.mIsDragged = false;
            recycleVelocityTracker();
        }
        return true;
    }

    public void setmEndRange(int i) {
        this.mEndRange = i;
        refreshValues();
    }

    public int getmEndRange() {
        return this.mEndRange;
    }

    public int getmInnerWidth() {
        return this.mInnerWidth;
    }

    private void refreshValues() {
        this.mInnerWidth = (this.mEndRange - this.mBeginRange) * getIndicateWidth();
        invalidateView();
    }

    private int getIndicateWidth() {
        int i = this.mIndicateWidth;
        int i2 = this.mIndicatePadding;
        return i + i2 + i2;
    }

    private int getMinimumScroll() {
        return ((-(getWidth() - getIndicateWidth())) / 2) + getStartOffsets();
    }

    private int getMaximumScroll() {
        return this.mInnerWidth + getMinimumScroll();
    }

    private void adjustIndicate() {
        if (!this.mOverScroller.isFinished()) {
            this.mOverScroller.abortAnimation();
        }
        int scrollByPosition = getScrollByPosition(computeSelectedPosition()) - getScrollX();
        if (scrollByPosition != 0) {
            this.mOverScroller.startScroll(getScrollX(), getScrollY(), scrollByPosition, 0);
            invalidateView();
        }
    }

    public void fling(int i) {
        this.mOverScroller.fling(getScrollX(), getScrollY(), i, 0, getMinimumScroll(), getMaximumScroll(), 0, 0, getWidth() / 2, 0);
        invalidateView();
    }

    public void sprintBack() {
        this.mOverScroller.springBack(getScrollX(), getScrollY(), getMinimumScroll(), getMaximumScroll(), 0, 0);
        invalidateView();
    }

    public void setOnScaleListener(OnScaleListener onScaleListener) {
        if (onScaleListener != null) {
            this.mListener = onScaleListener;
        }
    }

    private int getScrollByPosition(int i) {
        computeIndicateLoc(this.mIndicateLoc, i);
        return (this.mIndicateLoc.left - getStartOffsets()) + getMinimumScroll();
    }

    public int computeSelectedPosition() {
        return Math.max(0, Math.min(this.mInnerWidth, (getScrollX() - getMinimumScroll()) + (getIndicateWidth() / 2))) / getIndicateWidth();
    }

    public void smoothScrollTo(int i) {
        Log.i("tag", "smoothScrollTo:" + this.mEndRange);
        if (i < 0 || this.mBeginRange + i > this.mEndRange) {
            return;
        }
        if (!this.mOverScroller.isFinished()) {
            this.mOverScroller.abortAnimation();
        }
        int scrollByPosition = getScrollByPosition(i);
        Log.i("tag", "padding:" + getmIndicatePadding() + ";oldX:" + getScrollX() + ";newX:" + (scrollByPosition - getScrollX()));
        this.mOverScroller.startScroll(getScrollX(), getScrollY(), scrollByPosition - getScrollX(), 0, ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
        invalidateView();
    }

    public void smoothScrollToValue(int i) {
        smoothScrollTo(i - this.mBeginRange);
    }

    private void onScaleChanged(int i) {
        OnScaleListener onScaleListener = this.mListener;
        if (onScaleListener != null) {
            onScaleListener.onScaleChanged(i);
        }
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (!this.mOverScroller.isFinished()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            setScrollX(i);
            onScrollChanged(i, i2, scrollX, scrollY);
        } else {
            super.scrollTo(i, i2);
        }
        if (this.mListener != null) {
            onScaleChanged(computeSelectedPosition() + this.mBeginRange);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    private int computeTextHeight() {
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    private boolean isAlignTop() {
        return (this.mGravity & 48) == 48;
    }

    public void setGravity(int i) {
        this.mGravity = i;
        invalidateView();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mOverScroller.computeScrollOffset()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            overScrollBy(this.mOverScroller.getCurrX() - scrollX, this.mOverScroller.getCurrY() - scrollY, scrollX, scrollY, getMaximumScroll(), 0, getWidth(), 0, false);
            invalidateView();
            return;
        }
        if (this.mIsDragged || !this.mIsAutoAlign) {
            return;
        }
        adjustIndicate();
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        return getMaximumScroll();
    }

    public void invalidateView() {
        postInvalidateOnAnimation();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void setIndicateWidth(int i) {
        this.mIndicateWidth = i;
        refreshValues();
    }

    public int getmIndicatePadding() {
        return this.mIndicatePadding;
    }

    public void setIndicatePadding(int i) {
        this.mIndicatePadding = i;
        refreshValues();
    }

    public void setWithText(boolean z) {
        this.mIsWithText = z;
        refreshValues();
    }

    public void setAutoAlign(boolean z) {
        this.mIsAutoAlign = z;
        refreshValues();
    }

    public boolean isWithText() {
        return this.mIsWithText;
    }

    public boolean isAutoAlign() {
        return this.mIsAutoAlign;
    }
}
