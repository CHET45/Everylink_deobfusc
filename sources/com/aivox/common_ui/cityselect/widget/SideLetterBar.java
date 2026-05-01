package com.aivox.common_ui.cityselect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.common_ui.C1034R;
import com.github.houbb.heaven.util.lang.BoolUtil;

/* JADX INFO: loaded from: classes.dex */
public class SideLetterBar extends View {

    /* JADX INFO: renamed from: b */
    private static final String[] f274b = {"定位", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", BoolUtil.f541Y, "Z"};
    private int choose;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;
    private Paint paint;
    private boolean showBg;

    public interface OnLetterChangedListener {
        void onLetterChanged(String str);
    }

    public SideLetterBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.choose = -1;
        this.paint = new Paint();
        this.showBg = false;
    }

    public SideLetterBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.choose = -1;
        this.paint = new Paint();
        this.showBg = false;
    }

    public SideLetterBar(Context context) {
        super(context);
        this.choose = -1;
        this.paint = new Paint();
        this.showBg = false;
    }

    public void setOverlay(TextView textView) {
        this.overlay = textView;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        if (this.showBg) {
            canvas.drawColor(0);
        }
        int height = getHeight();
        int width = getWidth();
        int length = height / f274b.length;
        while (true) {
            String[] strArr = f274b;
            if (i >= strArr.length) {
                return;
            }
            this.paint.setTextSize(getResources().getDimension(C1034R.dimen.font_12sp));
            if (i == 0 || i == 1) {
                this.paint.setColor(Color.parseColor("#FFD24B"));
            } else {
                this.paint.setColor(Color.parseColor("#999999"));
            }
            this.paint.setAntiAlias(true);
            if (i == this.choose) {
                this.paint.setColor(Color.parseColor("#5c5c5c"));
                this.paint.setFakeBoldText(true);
            }
            canvas.drawText(strArr[i], (width / 2) - (this.paint.measureText(strArr[i]) / 2.0f), (length * i) + length, this.paint);
            this.paint.reset();
            i++;
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        int i = this.choose;
        OnLetterChangedListener onLetterChangedListener = this.onLetterChangedListener;
        String[] strArr = f274b;
        int height = (int) ((y / getHeight()) * strArr.length);
        if (action == 0) {
            this.showBg = true;
            if (i != height && onLetterChangedListener != null && height >= 0 && height < strArr.length) {
                onLetterChangedListener.onLetterChanged(strArr[height]);
                this.choose = height;
                invalidate();
                TextView textView = this.overlay;
                if (textView != null) {
                    textView.setVisibility(0);
                    this.overlay.setText(strArr[height]);
                }
            }
        } else if (action != 1) {
            if (action == 2 && i != height && onLetterChangedListener != null && height >= 0 && height < strArr.length) {
                onLetterChangedListener.onLetterChanged(strArr[height]);
                this.choose = height;
                invalidate();
                TextView textView2 = this.overlay;
                if (textView2 != null) {
                    textView2.setVisibility(0);
                    this.overlay.setText(strArr[height]);
                }
            }
        } else {
            this.showBg = false;
            this.choose = -1;
            invalidate();
            TextView textView3 = this.overlay;
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }
}
