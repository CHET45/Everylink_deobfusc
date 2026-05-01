package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import com.lxj.xpopup.interfaces.OnClickOutsideListener;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PartShadowContainer extends FrameLayout {
    public boolean isDismissOnTouchOutside;
    private OnClickOutsideListener listener;

    /* JADX INFO: renamed from: x */
    private float f860x;

    /* JADX INFO: renamed from: y */
    private float f861y;

    public PartShadowContainer(Context context) {
        super(context);
        this.isDismissOnTouchOutside = true;
    }

    public PartShadowContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PartShadowContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isDismissOnTouchOutside = true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnClickOutsideListener onClickOutsideListener;
        View childAt = getChildAt(0);
        int[] iArr = new int[2];
        childAt.getLocationInWindow(iArr);
        int i = iArr[0];
        if (!XPopupUtils.isInRect(motionEvent.getRawX(), motionEvent.getRawY(), new Rect(i, iArr[1], childAt.getMeasuredWidth() + i, iArr[1] + childAt.getMeasuredHeight()))) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f860x = motionEvent.getX();
                this.f861y = motionEvent.getY();
            } else if (action == 1 || action == 2 || action == 3) {
                if (((float) Math.sqrt(Math.pow(motionEvent.getX() - this.f860x, 2.0d) + Math.pow(motionEvent.getY() - this.f861y, 2.0d))) < ViewConfiguration.get(getContext()).getScaledTouchSlop() && this.isDismissOnTouchOutside && (onClickOutsideListener = this.listener) != null) {
                    onClickOutsideListener.onClickOutside();
                }
                this.f860x = 0.0f;
                this.f861y = 0.0f;
            }
        }
        return true;
    }

    public void setOnClickOutsideListener(OnClickOutsideListener onClickOutsideListener) {
        this.listener = onClickOutsideListener;
    }
}
