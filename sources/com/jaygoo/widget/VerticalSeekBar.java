package com.jaygoo.widget;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes3.dex */
public class VerticalSeekBar extends SeekBar {
    private int indicatorTextOrientation;
    VerticalRangeSeekBar verticalSeekBar;

    public VerticalSeekBar(RangeSeekBar rangeSeekBar, AttributeSet attributeSet, boolean z) {
        super(rangeSeekBar, attributeSet, z);
        initAttrs(attributeSet);
        this.verticalSeekBar = (VerticalRangeSeekBar) rangeSeekBar;
    }

    private void initAttrs(AttributeSet attributeSet) {
        try {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1981R.styleable.VerticalRangeSeekBar);
            this.indicatorTextOrientation = typedArrayObtainStyledAttributes.getInt(C1981R.styleable.VerticalRangeSeekBar_rsb_indicator_text_orientation, 1);
            typedArrayObtainStyledAttributes.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.jaygoo.widget.SeekBar
    protected void onDrawIndicator(Canvas canvas, Paint paint, String str) {
        if (str == null) {
            return;
        }
        if (this.indicatorTextOrientation == 1) {
            drawVerticalIndicator(canvas, paint, str);
        } else {
            super.onDrawIndicator(canvas, paint, str);
        }
    }

    protected void drawVerticalIndicator(Canvas canvas, Paint paint, String str) {
        paint.setTextSize(getIndicatorTextSize());
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getIndicatorBackgroundColor());
        int i = 0;
        paint.getTextBounds(str, 0, str.length(), this.indicatorTextRect);
        int iHeight = this.indicatorTextRect.height() + getIndicatorPaddingLeft() + getIndicatorPaddingRight();
        if (getIndicatorWidth() > iHeight) {
            iHeight = getIndicatorWidth();
        }
        int iWidth = this.indicatorTextRect.width() + getIndicatorPaddingTop() + getIndicatorPaddingBottom();
        if (getIndicatorHeight() > iWidth) {
            iWidth = getIndicatorHeight();
        }
        this.indicatorRect.left = (this.scaleThumbWidth / 2) - (iHeight / 2);
        this.indicatorRect.top = ((this.bottom - iWidth) - this.scaleThumbHeight) - getIndicatorMargin();
        this.indicatorRect.right = this.indicatorRect.left + iHeight;
        this.indicatorRect.bottom = this.indicatorRect.top + iWidth;
        if (this.indicatorBitmap == null) {
            int i2 = this.scaleThumbWidth / 2;
            int i3 = this.indicatorRect.bottom;
            int indicatorArrowSize = i2 - getIndicatorArrowSize();
            int indicatorArrowSize2 = i3 - getIndicatorArrowSize();
            int indicatorArrowSize3 = getIndicatorArrowSize() + i2;
            this.indicatorArrowPath.reset();
            this.indicatorArrowPath.moveTo(i2, i3);
            float f = indicatorArrowSize;
            float f2 = indicatorArrowSize2;
            this.indicatorArrowPath.lineTo(f, f2);
            this.indicatorArrowPath.lineTo(indicatorArrowSize3, f2);
            this.indicatorArrowPath.close();
            canvas.drawPath(this.indicatorArrowPath, paint);
            this.indicatorRect.bottom -= getIndicatorArrowSize();
            this.indicatorRect.top -= getIndicatorArrowSize();
        }
        int iDp2px = Utils.dp2px(getContext(), 1.0f);
        int iWidth2 = (((this.indicatorRect.width() / 2) - ((int) (this.rangeSeekBar.getProgressWidth() * this.currPercent))) - this.rangeSeekBar.getProgressLeft()) + iDp2px;
        int iWidth3 = (((this.indicatorRect.width() / 2) - ((int) (this.rangeSeekBar.getProgressWidth() * (1.0f - this.currPercent)))) - this.rangeSeekBar.getProgressPaddingRight()) + iDp2px;
        if (iWidth2 > 0) {
            this.indicatorRect.left += iWidth2;
            this.indicatorRect.right += iWidth2;
        } else if (iWidth3 > 0) {
            this.indicatorRect.left -= iWidth3;
            this.indicatorRect.right -= iWidth3;
        }
        if (this.indicatorBitmap != null) {
            Utils.drawBitmap(canvas, paint, this.indicatorBitmap, this.indicatorRect);
        } else if (getIndicatorRadius() > 0.0f) {
            canvas.drawRoundRect(new RectF(this.indicatorRect), getIndicatorRadius(), getIndicatorRadius(), paint);
        } else {
            canvas.drawRect(this.indicatorRect, paint);
        }
        int iWidth4 = ((this.indicatorRect.left + ((this.indicatorRect.width() - this.indicatorTextRect.width()) / 2)) + getIndicatorPaddingLeft()) - getIndicatorPaddingRight();
        int iHeight2 = ((this.indicatorRect.bottom - ((this.indicatorRect.height() - this.indicatorTextRect.height()) / 2)) + getIndicatorPaddingTop()) - getIndicatorPaddingBottom();
        paint.setColor(getIndicatorTextColor());
        float f3 = iWidth4;
        float fWidth = (this.indicatorTextRect.width() / 2.0f) + f3;
        float f4 = iHeight2;
        float fHeight = f4 - (this.indicatorTextRect.height() / 2.0f);
        if (this.indicatorTextOrientation == 1) {
            if (this.verticalSeekBar.getOrientation() == 1) {
                i = 90;
            } else if (this.verticalSeekBar.getOrientation() == 2) {
                i = -90;
            }
        }
        if (i != 0) {
            canvas.rotate(i, fWidth, fHeight);
        }
        canvas.drawText(str, f3, f4, paint);
        if (i != 0) {
            canvas.rotate(-i, fWidth, fHeight);
        }
    }

    public int getIndicatorTextOrientation() {
        return this.indicatorTextOrientation;
    }

    public void setIndicatorTextOrientation(int i) {
        this.indicatorTextOrientation = i;
    }
}
