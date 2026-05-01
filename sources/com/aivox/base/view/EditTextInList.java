package com.aivox.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;

/* JADX INFO: loaded from: classes.dex */
public class EditTextInList extends AppCompatEditText {
    public EditTextInList(Context context) {
        super(context);
    }

    public EditTextInList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditTextInList(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }
}
