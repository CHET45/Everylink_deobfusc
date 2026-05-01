package com.aivox.common_ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class PickerView extends View {
    public static final float MARGIN_ALPHA = 2.8f;
    public static final float SPEED = 2.0f;
    public static final String TAG = "PickerView";
    private boolean isInit;
    private int mColorText;
    private int mCurrentSelected;
    private List<String> mDataList;
    private float mLastDownY;
    private float mMaxTextAlpha;
    private float mMaxTextSize;
    private float mMinTextAlpha;
    private float mMinTextSize;
    private float mMoveLen;
    private Paint mPaint;
    private onSelectListener mSelectListener;
    private MyTimerTask mTask;
    private int mViewHeight;
    private int mViewWidth;
    private Timer timer;
    Handler updateHandler;

    public interface onSelectListener {
        void onSelect(int i, String str);
    }

    public PickerView(Context context) {
        super(context);
        this.mMaxTextSize = 80.0f;
        this.mMinTextSize = 40.0f;
        this.mMaxTextAlpha = 255.0f;
        this.mMinTextAlpha = 120.0f;
        this.mColorText = 3355443;
        this.mMoveLen = 0.0f;
        this.isInit = false;
        this.updateHandler = new Handler() { // from class: com.aivox.common_ui.PickerView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (Math.abs(PickerView.this.mMoveLen) < 2.0f) {
                    PickerView.this.mMoveLen = 0.0f;
                    if (PickerView.this.mTask != null) {
                        PickerView.this.mTask.cancel();
                        PickerView.this.mTask = null;
                        PickerView.this.performSelect();
                    }
                } else {
                    PickerView.this.mMoveLen -= (PickerView.this.mMoveLen / Math.abs(PickerView.this.mMoveLen)) * 2.0f;
                }
                PickerView.this.invalidate();
            }
        };
        init();
    }

    public PickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxTextSize = 80.0f;
        this.mMinTextSize = 40.0f;
        this.mMaxTextAlpha = 255.0f;
        this.mMinTextAlpha = 120.0f;
        this.mColorText = 3355443;
        this.mMoveLen = 0.0f;
        this.isInit = false;
        this.updateHandler = new Handler() { // from class: com.aivox.common_ui.PickerView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (Math.abs(PickerView.this.mMoveLen) < 2.0f) {
                    PickerView.this.mMoveLen = 0.0f;
                    if (PickerView.this.mTask != null) {
                        PickerView.this.mTask.cancel();
                        PickerView.this.mTask = null;
                        PickerView.this.performSelect();
                    }
                } else {
                    PickerView.this.mMoveLen -= (PickerView.this.mMoveLen / Math.abs(PickerView.this.mMoveLen)) * 2.0f;
                }
                PickerView.this.invalidate();
            }
        };
        init();
    }

    public void setOnSelectListener(onSelectListener onselectlistener) {
        this.mSelectListener = onselectlistener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performSelect() {
        onSelectListener onselectlistener = this.mSelectListener;
        if (onselectlistener != null) {
            int i = this.mCurrentSelected;
            onselectlistener.onSelect(i, this.mDataList.get(i));
        }
    }

    public void setData(List<String> list) {
        this.mDataList = list;
        this.mCurrentSelected = list.size() / 2;
        invalidate();
    }

    public void setSelected(int i) {
        this.mCurrentSelected = i;
    }

    private void moveHeadToTail() {
        String str = this.mDataList.get(0);
        this.mDataList.remove(0);
        this.mDataList.add(str);
    }

    private void moveTailToHead() {
        String str = this.mDataList.get(r0.size() - 1);
        Log.i("tag", "size:" + this.mDataList.size());
        this.mDataList.remove(r1.size() - 1);
        this.mDataList.add(0, str);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mViewHeight = getMeasuredHeight();
        this.mViewWidth = getMeasuredWidth();
        float f = this.mViewHeight / 4.0f;
        this.mMaxTextSize = f;
        this.mMinTextSize = f / 2.0f;
        this.isInit = true;
        invalidate();
    }

    private void init() {
        this.timer = new Timer();
        this.mDataList = new ArrayList();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mColorText);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isInit) {
            drawData(canvas);
        }
    }

    private void drawData(Canvas canvas) {
        float fParabola = parabola(this.mViewHeight / 4.0f, this.mMoveLen);
        float f = this.mMaxTextSize;
        float f2 = this.mMinTextSize;
        this.mPaint.setTextSize(((f - f2) * fParabola) + f2);
        Paint paint = this.mPaint;
        float f3 = this.mMaxTextAlpha;
        float f4 = this.mMinTextAlpha;
        paint.setAlpha((int) (((f3 - f4) * fParabola) + f4));
        float f5 = (float) (((double) this.mViewWidth) / 2.0d);
        float f6 = (float) ((((double) this.mViewHeight) / 2.0d) + ((double) this.mMoveLen));
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        canvas.drawText(this.mDataList.get(this.mCurrentSelected), f5, (float) (((double) f6) - ((((double) fontMetricsInt.bottom) / 2.0d) + (((double) fontMetricsInt.top) / 2.0d))), this.mPaint);
        for (int i = 1; this.mCurrentSelected - i >= 0; i++) {
            drawOtherText(canvas, i, -1);
        }
        for (int i2 = 1; this.mCurrentSelected + i2 < this.mDataList.size(); i2++) {
            drawOtherText(canvas, i2, 1);
        }
    }

    private void drawOtherText(Canvas canvas, int i, int i2) {
        float f = i2;
        float f2 = (this.mMinTextSize * 2.8f * i) + (this.mMoveLen * f);
        float fParabola = parabola(this.mViewHeight / 4.0f, f2);
        float f3 = this.mMaxTextSize;
        float f4 = this.mMinTextSize;
        this.mPaint.setTextSize(((f3 - f4) * fParabola) + f4);
        Paint paint = this.mPaint;
        float f5 = this.mMaxTextAlpha;
        float f6 = this.mMinTextAlpha;
        paint.setAlpha((int) (((f5 - f6) * fParabola) + f6));
        float f7 = (float) ((((double) this.mViewHeight) / 2.0d) + ((double) (f * f2)));
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        canvas.drawText(this.mDataList.get(this.mCurrentSelected + (i2 * i)), (float) (((double) this.mViewWidth) / 2.0d), (float) (((double) f7) - ((((double) fontMetricsInt.bottom) / 2.0d) + (((double) fontMetricsInt.top) / 2.0d))), this.mPaint);
    }

    private float parabola(float f, float f2) {
        float fPow = (float) (1.0d - Math.pow(f2 / f, 2.0d));
        if (fPow < 0.0f) {
            return 0.0f;
        }
        return fPow;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            doDown(motionEvent);
        } else if (actionMasked == 1) {
            doUp(motionEvent);
        } else if (actionMasked == 2) {
            doMove(motionEvent);
        }
        return true;
    }

    private void doDown(MotionEvent motionEvent) {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        this.mLastDownY = motionEvent.getY();
    }

    private void doMove(MotionEvent motionEvent) {
        float y = this.mMoveLen + (motionEvent.getY() - this.mLastDownY);
        this.mMoveLen = y;
        float f = this.mMinTextSize;
        if (y > (f * 2.8f) / 2.0f) {
            moveTailToHead();
            this.mMoveLen -= this.mMinTextSize * 2.8f;
        } else if (y < (f * (-2.8f)) / 2.0f) {
            moveHeadToTail();
            this.mMoveLen += this.mMinTextSize * 2.8f;
        }
        this.mLastDownY = motionEvent.getY();
        invalidate();
    }

    private void doUp(MotionEvent motionEvent) {
        if (Math.abs(this.mMoveLen) < 1.0E-4d) {
            this.mMoveLen = 0.0f;
            return;
        }
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        MyTimerTask myTimerTask2 = new MyTimerTask(this.updateHandler);
        this.mTask = myTimerTask2;
        this.timer.schedule(myTimerTask2, 0L, 10L);
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Handler handler = this.handler;
            handler.sendMessage(handler.obtainMessage());
        }
    }
}
