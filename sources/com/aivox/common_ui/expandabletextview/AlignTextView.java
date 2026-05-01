package com.aivox.common_ui.expandabletextview;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.aivox.common_ui.C1034R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlignTextView extends AppCompatTextView {
    private Align align;
    private boolean firstCalc;
    private float lineSpacingAdd;
    private float lineSpacingMultiplier;
    private List<String> lines;
    private int originalHeight;
    private int originalLineCount;
    private int originalPaddingBottom;
    private boolean setPaddingFromMe;
    private List<Integer> tailLines;
    private float textHeight;
    private float textLineSpaceExtra;
    private int width;

    public enum Align {
        ALIGN_LEFT,
        ALIGN_CENTER,
        ALIGN_RIGHT
    }

    public AlignTextView(Context context) {
        super(context);
        this.textLineSpaceExtra = 0.0f;
        this.lines = new ArrayList();
        this.tailLines = new ArrayList();
        this.align = Align.ALIGN_LEFT;
        this.firstCalc = true;
        this.lineSpacingMultiplier = 1.0f;
        this.lineSpacingAdd = 0.0f;
        this.originalHeight = 0;
        this.originalLineCount = 0;
        this.originalPaddingBottom = 0;
        this.setPaddingFromMe = false;
        setTextIsSelectable(false);
    }

    public AlignTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textLineSpaceExtra = 0.0f;
        this.lines = new ArrayList();
        this.tailLines = new ArrayList();
        this.align = Align.ALIGN_LEFT;
        this.firstCalc = true;
        this.lineSpacingMultiplier = 1.0f;
        this.lineSpacingAdd = 0.0f;
        this.originalHeight = 0;
        this.originalLineCount = 0;
        this.originalPaddingBottom = 0;
        this.setPaddingFromMe = false;
        setTextIsSelectable(false);
        this.lineSpacingMultiplier = attributeSet.getAttributeFloatValue("http://schemas.android.com/apk/res/android", "lineSpacingMultiplier", 1.0f);
        this.lineSpacingAdd = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.lineSpacingExtra}).getDimensionPixelSize(0, 0);
        this.originalPaddingBottom = getPaddingBottom();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.AlignTextView);
        int i = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.AlignTextView_align, 0);
        if (i == 1) {
            this.align = Align.ALIGN_CENTER;
        } else if (i == 2) {
            this.align = Align.ALIGN_RIGHT;
        } else {
            this.align = Align.ALIGN_LEFT;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.firstCalc) {
            this.width = getMeasuredWidth();
            String string = getText().toString();
            TextPaint paint = getPaint();
            this.lines.clear();
            this.tailLines.clear();
            for (String str : string.split("\\n")) {
                calc(paint, str);
            }
            measureTextViewHeight(string, paint.getTextSize(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            float f = (this.originalHeight * 1.0f) / this.originalLineCount;
            this.textHeight = f;
            float f2 = ((this.lineSpacingMultiplier - 1.0f) * f) + this.lineSpacingAdd;
            this.textLineSpaceExtra = f2;
            this.setPaddingFromMe = true;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), this.originalPaddingBottom + ((int) ((f2 + f) * (this.lines.size() - this.originalLineCount))));
            this.firstCalc = false;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        this.width = getMeasuredWidth();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textSize = getTextSize() - (((fontMetrics.bottom - fontMetrics.descent) + fontMetrics.ascent) - fontMetrics.top);
        float f = 2.0f;
        if ((getGravity() & 4096) == 0) {
            textSize += (this.textHeight - textSize) / 2.0f;
        }
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        this.width = (this.width - paddingLeft) - getPaddingRight();
        int i = 0;
        int i2 = 0;
        while (i2 < this.lines.size()) {
            float f2 = i2;
            float f3 = (this.textHeight * f2) + textSize;
            String str = this.lines.get(i2);
            float f4 = paddingLeft;
            float fMeasureText = this.width - paint.measureText(str);
            float length = fMeasureText / (str.length() - 1);
            if (this.tailLines.contains(Integer.valueOf(i2))) {
                if (this.align == Align.ALIGN_CENTER) {
                    fMeasureText /= f;
                } else {
                    if (this.align == Align.ALIGN_RIGHT) {
                    }
                    length = 0.0f;
                }
                f4 += fMeasureText;
                length = 0.0f;
            }
            int i3 = i;
            while (i3 < str.length()) {
                int i4 = i3 + 1;
                canvas.drawText(str.substring(i3, i4), paint.measureText(str.substring(i, i3)) + (i3 * length) + f4, paddingTop + f3 + (this.textLineSpaceExtra * f2), paint);
                i3 = i4;
                i = 0;
            }
            i2++;
            f = 2.0f;
            i = 0;
        }
    }

    public void setAlign(Align align) {
        this.align = align;
        invalidate();
    }

    private void calc(Paint paint, String str) {
        if (str.length() == 0) {
            this.lines.add("\n");
            return;
        }
        int iMeasureText = (int) (this.width / paint.measureText("中"));
        int i = iMeasureText + 1;
        int i2 = 0;
        StringBuilder sb = new StringBuilder(str.substring(0, Math.min(i, str.length())));
        while (true) {
            if (i >= str.length()) {
                break;
            }
            if (paint.measureText(str.substring(i2, i + 1)) > this.width) {
                this.lines.add(sb.toString());
                sb = new StringBuilder();
                if (str.length() - i > iMeasureText) {
                    int i3 = i + iMeasureText;
                    sb.append(str.substring(i, i3));
                    i2 = i;
                    i = i3 - 1;
                } else {
                    this.lines.add(str.substring(i));
                    break;
                }
            } else {
                sb.append(str.charAt(i));
            }
            i++;
        }
        if (sb.length() > 0) {
            this.lines.add(sb.toString());
        }
        this.tailLines.add(Integer.valueOf(this.lines.size() - 1));
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        this.firstCalc = true;
        super.setText(charSequence, bufferType);
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        if (!this.setPaddingFromMe) {
            this.originalPaddingBottom = i4;
        }
        this.setPaddingFromMe = false;
        super.setPadding(i, i2, i3, i4);
    }

    private void measureTextViewHeight(String str, float f, int i) {
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setTextSize(0, f);
        textView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        this.originalLineCount = textView.getLineCount();
        this.originalHeight = textView.getMeasuredHeight();
    }
}
