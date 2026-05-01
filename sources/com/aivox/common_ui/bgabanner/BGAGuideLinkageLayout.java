package com.aivox.common_ui.bgabanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.aivox.base.util.BaseAppUtils;

/* JADX INFO: loaded from: classes.dex */
public class BGAGuideLinkageLayout extends FrameLayout {
    public BGAGuideLinkageLayout(Context context) {
        super(context);
    }

    public BGAGuideLinkageLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BGAGuideLinkageLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        for (int i = 0; i < getChildCount(); i++) {
            try {
                getChildAt(i).dispatchTouchEvent(motionEvent);
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }
        return true;
    }
}
