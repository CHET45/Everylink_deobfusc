package com.aivox.common_ui;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class SoftKeyBoardListener {
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private View rootView;
    int rootViewVisibleHeight;

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardHide(int i);

        void keyBoardShow(int i);
    }

    public SoftKeyBoardListener(Activity activity) {
        new SoftKeyBoardListener(activity.getWindow());
    }

    public SoftKeyBoardListener(Window window) {
        View decorView = window.getDecorView();
        this.rootView = decorView;
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aivox.common_ui.SoftKeyBoardListener.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                Rect rect = new Rect();
                SoftKeyBoardListener.this.rootView.getWindowVisibleDisplayFrame(rect);
                int iHeight = rect.height();
                if (SoftKeyBoardListener.this.rootViewVisibleHeight == 0) {
                    SoftKeyBoardListener.this.rootViewVisibleHeight = iHeight;
                    return;
                }
                if (SoftKeyBoardListener.this.rootViewVisibleHeight == iHeight) {
                    return;
                }
                if (SoftKeyBoardListener.this.rootViewVisibleHeight - iHeight > 200) {
                    if (SoftKeyBoardListener.this.onSoftKeyBoardChangeListener != null) {
                        SoftKeyBoardListener.this.onSoftKeyBoardChangeListener.keyBoardShow(SoftKeyBoardListener.this.rootViewVisibleHeight - iHeight);
                    }
                    SoftKeyBoardListener.this.rootViewVisibleHeight = iHeight;
                } else if (iHeight - SoftKeyBoardListener.this.rootViewVisibleHeight > 200) {
                    if (SoftKeyBoardListener.this.onSoftKeyBoardChangeListener != null) {
                        SoftKeyBoardListener.this.onSoftKeyBoardChangeListener.keyBoardHide(iHeight - SoftKeyBoardListener.this.rootViewVisibleHeight);
                    }
                    SoftKeyBoardListener.this.rootViewVisibleHeight = iHeight;
                }
            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        new SoftKeyBoardListener(activity).setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }

    public static void setListener(Window window, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        new SoftKeyBoardListener(window).setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }
}
