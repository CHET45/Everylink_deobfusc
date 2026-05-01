package com.aivox.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.aivox.base.util.LogUtil;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class SwitchButton extends View {
    private String ATTR_IS_OPENED;
    private String ATTR_SLIDE_BUTTON;
    private String ATTR_SWITCH_BACKGROUND1;
    private String ATTR_SWITCH_BACKGROUND2;
    private String NAMESPACE;
    boolean isOpen;
    int mCurrLeft;
    Bitmap mCurrSwitch;
    int mMaxLeft;
    private OnOpenedListener mOpenedListener;
    Paint mPaint;
    Bitmap mSlideButton;
    Bitmap mSwitchBackground1;
    Bitmap mSwitchBackground2;
    int moveX;
    int startX;

    public interface OnOpenedListener {
        void onChecked(View view2, boolean z);
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.NAMESPACE = "http://schemas.android.com/apk/res/com.aivox.app";
        this.ATTR_SLIDE_BUTTON = "slide_button";
        this.ATTR_SWITCH_BACKGROUND1 = "switch_background1";
        this.ATTR_SWITCH_BACKGROUND2 = "switch_background2";
        this.ATTR_IS_OPENED = "isOpened";
        this.isOpen = false;
        init(attributeSet);
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.NAMESPACE = "http://schemas.android.com/apk/res/com.aivox.app";
        this.ATTR_SLIDE_BUTTON = "slide_button";
        this.ATTR_SWITCH_BACKGROUND1 = "switch_background1";
        this.ATTR_SWITCH_BACKGROUND2 = "switch_background2";
        this.ATTR_IS_OPENED = "isOpened";
        this.isOpen = false;
        init(attributeSet);
    }

    public SwitchButton(Context context) {
        super(context);
        this.NAMESPACE = "http://schemas.android.com/apk/res/com.aivox.app";
        this.ATTR_SLIDE_BUTTON = "slide_button";
        this.ATTR_SWITCH_BACKGROUND1 = "switch_background1";
        this.ATTR_SWITCH_BACKGROUND2 = "switch_background2";
        this.ATTR_IS_OPENED = "isOpened";
        this.isOpen = false;
        init(null);
    }

    private void init(AttributeSet attributeSet) {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(-16776961);
        if (attributeSet != null) {
            int attributeResourceValue = attributeSet.getAttributeResourceValue(this.NAMESPACE, this.ATTR_SLIDE_BUTTON, -1);
            LogUtil.m338i("id1:" + attributeResourceValue);
            if (attributeResourceValue > -1) {
                this.mSlideButton = BitmapFactory.decodeResource(getResources(), attributeResourceValue);
            }
            int attributeResourceValue2 = attributeSet.getAttributeResourceValue(this.NAMESPACE, this.ATTR_SWITCH_BACKGROUND1, -1);
            LogUtil.m338i("id2:" + attributeResourceValue2);
            if (attributeResourceValue2 > -1) {
                this.mSwitchBackground1 = BitmapFactory.decodeResource(getResources(), attributeResourceValue2);
            }
            int attributeResourceValue3 = attributeSet.getAttributeResourceValue(this.NAMESPACE, this.ATTR_SWITCH_BACKGROUND2, -1);
            LogUtil.m338i("id3:" + attributeResourceValue3);
            if (attributeResourceValue3 > -1) {
                this.mSwitchBackground2 = BitmapFactory.decodeResource(getResources(), attributeResourceValue3);
            }
            if (this.mSlideButton == null || this.mSwitchBackground1 == null || this.mSwitchBackground2 == null) {
                this.mSwitchBackground1 = BitmapFactory.decodeResource(getResources(), C1034R.drawable.setbutton_bgon_2x);
                this.mSwitchBackground2 = BitmapFactory.decodeResource(getResources(), C1034R.drawable.setbutton_bg_2x);
                this.mSlideButton = BitmapFactory.decodeResource(getResources(), C1034R.drawable.setbutton_radio_2x);
            }
        }
        Bitmap bitmap = this.mSwitchBackground1;
        this.mCurrSwitch = bitmap;
        this.mMaxLeft = bitmap.getWidth() - this.mSlideButton.getWidth();
        initStatus(attributeSet);
    }

    private void initStatus(AttributeSet attributeSet) {
        if (attributeSet != null) {
            setStatus(attributeSet.getAttributeBooleanValue(this.NAMESPACE, this.ATTR_IS_OPENED, false));
        }
    }

    public void setStatus(boolean z) {
        if (z) {
            this.mCurrLeft = this.mMaxLeft;
            this.mCurrSwitch = this.mSwitchBackground1;
            this.isOpen = true;
        } else {
            this.mCurrLeft = 0;
            this.mCurrSwitch = this.mSwitchBackground2;
            this.isOpen = false;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(this.mSwitchBackground1.getWidth(), this.mSwitchBackground1.getHeight());
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.mCurrSwitch, 0.0f, 0.0f, this.mPaint);
        canvas.drawBitmap(this.mSlideButton, this.mCurrLeft, 0.0f, this.mPaint);
    }

    public void setOnCheckChangedListener(OnOpenedListener onOpenedListener) {
        this.mOpenedListener = onOpenedListener;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startX = (int) motionEvent.getX();
        } else if (action == 1) {
            if (this.moveX < 5) {
                setStatus(!this.isOpen);
            } else {
                setStatus(this.mCurrLeft >= this.mMaxLeft / 2);
            }
            OnOpenedListener onOpenedListener = this.mOpenedListener;
            if (onOpenedListener != null) {
                onOpenedListener.onChecked(this, this.isOpen);
            }
            this.moveX = 0;
        } else if (action == 2) {
            int x = (int) (motionEvent.getX() - this.startX);
            this.mCurrLeft += x;
            this.startX = (int) motionEvent.getX();
            this.moveX += Math.abs(x);
        }
        if (this.mCurrLeft < 0) {
            this.mCurrLeft = 0;
        }
        int i = this.mCurrLeft;
        int i2 = this.mMaxLeft;
        if (i > i2) {
            this.mCurrLeft = i2;
        }
        invalidate();
        return true;
    }
}
