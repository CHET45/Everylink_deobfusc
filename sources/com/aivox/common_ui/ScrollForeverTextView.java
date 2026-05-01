package com.aivox.common_ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* JADX INFO: loaded from: classes.dex */
public class ScrollForeverTextView extends AppCompatTextView {
    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    public ScrollForeverTextView(Context context) {
        this(context, null);
    }

    public ScrollForeverTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public ScrollForeverTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setSingleLine(true);
        setMaxLines(1);
    }
}
