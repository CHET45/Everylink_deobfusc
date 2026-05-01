package com.aivox.common_ui;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* JADX INFO: loaded from: classes.dex */
public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTextWithBackgroundColor(String str, String str2, int i, int i2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        int iIndexOf = str.indexOf(str2);
        int length = str2.length() + iIndexOf;
        spannableStringBuilder.setSpan(new BackgroundColorSpan(i), iIndexOf, length, 33);
        spannableStringBuilder.setSpan(new StyleSpan(1), iIndexOf, length, 33);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(i2), iIndexOf, length, 33);
        setText(spannableStringBuilder);
    }
}
