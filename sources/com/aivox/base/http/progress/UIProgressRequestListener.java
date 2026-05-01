package com.aivox.base.http.progress;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class UIProgressRequestListener implements ProgressRequestListener {
    private static final int REQUEST_UPDATE = 1;
    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), this);

    public abstract void onUIRequestProgress(long j, long j2, boolean z);

    private static class UIHandler extends Handler {
        private final WeakReference<UIProgressRequestListener> mUIProgressRequestListenerWeakReference;

        public UIHandler(Looper looper, UIProgressRequestListener uIProgressRequestListener) {
            super(looper);
            this.mUIProgressRequestListenerWeakReference = new WeakReference<>(uIProgressRequestListener);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                UIProgressRequestListener uIProgressRequestListener = this.mUIProgressRequestListenerWeakReference.get();
                if (uIProgressRequestListener != null) {
                    ProgressModel progressModel = (ProgressModel) message.obj;
                    uIProgressRequestListener.onUIRequestProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    return;
                }
                return;
            }
            super.handleMessage(message);
        }
    }

    @Override // com.aivox.base.http.progress.ProgressRequestListener
    public void onRequestProgress(long j, long j2, boolean z) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = new ProgressModel(j, j2, z);
        messageObtain.what = 1;
        this.mHandler.sendMessage(messageObtain);
    }
}
