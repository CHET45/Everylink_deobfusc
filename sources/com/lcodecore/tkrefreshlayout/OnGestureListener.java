package com.lcodecore.tkrefreshlayout;

import android.view.MotionEvent;

/* JADX INFO: loaded from: classes3.dex */
public interface OnGestureListener {
    void onDown(MotionEvent motionEvent);

    void onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

    void onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

    void onUp(MotionEvent motionEvent, boolean z);
}
