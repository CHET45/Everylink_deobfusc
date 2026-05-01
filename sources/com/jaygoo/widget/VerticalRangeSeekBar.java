package com.jaygoo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
public class VerticalRangeSeekBar extends RangeSeekBar {
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int TEXT_DIRECTION_HORIZONTAL = 2;
    public static final int TEXT_DIRECTION_VERTICAL = 1;
    private int maxTickMarkWidth;
    private int orientation;
    private int tickMarkDirection;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionDef {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextDirectionDef {
    }

    public VerticalRangeSeekBar(Context context) {
        this(context, null);
    }

    public VerticalRangeSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.orientation = 1;
        this.tickMarkDirection = 1;
        initAttrs(attributeSet);
        initSeekBar(attributeSet);
    }

    private void initAttrs(AttributeSet attributeSet) {
        try {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1981R.styleable.VerticalRangeSeekBar);
            this.orientation = typedArrayObtainStyledAttributes.getInt(C1981R.styleable.VerticalRangeSeekBar_rsb_orientation, 1);
            this.tickMarkDirection = typedArrayObtainStyledAttributes.getInt(C1981R.styleable.VerticalRangeSeekBar_rsb_tick_mark_orientation, 1);
            typedArrayObtainStyledAttributes.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initSeekBar(AttributeSet attributeSet) {
        this.leftSB = new VerticalSeekBar(this, attributeSet, true);
        this.rightSB = new VerticalSeekBar(this, attributeSet, false);
        this.rightSB.setVisible(getSeekBarMode() != 1);
    }

    @Override // com.jaygoo.widget.RangeSeekBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
    }

    @Override // com.jaygoo.widget.RangeSeekBar, android.view.View
    protected void onMeasure(int i, int i2) {
        int rawHeight;
        int iMakeMeasureSpec;
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode == 1073741824) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        } else if (mode == Integer.MIN_VALUE && (getParent() instanceof ViewGroup) && size == -1) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(((ViewGroup) getParent()).getMeasuredHeight(), Integer.MIN_VALUE);
        } else {
            if (getGravity() == 2) {
                rawHeight = (getProgressTop() * 2) + getProgressHeight();
            } else {
                rawHeight = (int) getRawHeight();
            }
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(rawHeight, 1073741824);
        }
        super.onMeasure(iMakeMeasureSpec, i2);
    }

    @Override // com.jaygoo.widget.RangeSeekBar, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.orientation == 1) {
            canvas.rotate(-90.0f);
            canvas.translate(-getHeight(), 0.0f);
        } else {
            canvas.rotate(90.0f);
            canvas.translate(0.0f, -getWidth());
        }
        super.onDraw(canvas);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x011c A[SYNTHETIC] */
    @Override // com.jaygoo.widget.RangeSeekBar
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDrawTickMark(android.graphics.Canvas r13, android.graphics.Paint r14) {
        /*
            Method dump skipped, instruction units count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jaygoo.widget.VerticalRangeSeekBar.onDrawTickMark(android.graphics.Canvas, android.graphics.Paint):void");
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    protected int getTickMarkRawHeight() {
        int tickMarkTextMargin;
        int i;
        if (this.maxTickMarkWidth > 0) {
            tickMarkTextMargin = getTickMarkTextMargin();
            i = this.maxTickMarkWidth;
        } else {
            if (getTickMarkTextArray() == null || getTickMarkTextArray().length <= 0) {
                return 0;
            }
            int length = getTickMarkTextArray().length;
            this.maxTickMarkWidth = Utils.measureText(String.valueOf(getTickMarkTextArray()[0]), getTickMarkTextSize()).width();
            for (int i2 = 1; i2 < length; i2++) {
                int iWidth = Utils.measureText(String.valueOf(getTickMarkTextArray()[i2]), getTickMarkTextSize()).width();
                if (this.maxTickMarkWidth < iWidth) {
                    this.maxTickMarkWidth = iWidth;
                }
            }
            tickMarkTextMargin = getTickMarkTextMargin();
            i = this.maxTickMarkWidth;
        }
        return tickMarkTextMargin + i;
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    public void setTickMarkTextSize(int i) {
        super.setTickMarkTextSize(i);
        this.maxTickMarkWidth = 0;
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    public void setTickMarkTextArray(CharSequence[] charSequenceArr) {
        super.setTickMarkTextArray(charSequenceArr);
        this.maxTickMarkWidth = 0;
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    protected float getEventX(MotionEvent motionEvent) {
        if (this.orientation == 1) {
            return getHeight() - motionEvent.getY();
        }
        return motionEvent.getY();
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    protected float getEventY(MotionEvent motionEvent) {
        if (this.orientation == 1) {
            return motionEvent.getX();
        }
        return (-motionEvent.getX()) + getWidth();
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    public VerticalSeekBar getLeftSeekBar() {
        return (VerticalSeekBar) this.leftSB;
    }

    @Override // com.jaygoo.widget.RangeSeekBar
    public VerticalSeekBar getRightSeekBar() {
        return (VerticalSeekBar) this.rightSB;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public int getTickMarkDirection() {
        return this.tickMarkDirection;
    }

    public void setTickMarkDirection(int i) {
        this.tickMarkDirection = i;
    }
}
