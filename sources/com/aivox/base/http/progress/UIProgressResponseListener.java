package com.aivox.base.http.progress;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class UIProgressResponseListener implements ProgressResponseListener {
    private static final int RESPONSE_UPDATE = 2;
    private final Handler mHandler = new UIHandler(Looper.getMainLooper(), this);

    public abstract void onUIResponseProgress(long j, long j2, boolean z);

    private static class UIHandler extends Handler {
        private final WeakReference<UIProgressResponseListener> mUIProgressResponseListenerWeakReference;

        public UIHandler(Looper looper, UIProgressResponseListener uIProgressResponseListener) {
            super(looper);
            this.mUIProgressResponseListenerWeakReference = new WeakReference<>(uIProgressResponseListener);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 2) {
                UIProgressResponseListener uIProgressResponseListener = this.mUIProgressResponseListenerWeakReference.get();
                if (uIProgressResponseListener != null) {
                    ProgressModel progressModel = (ProgressModel) message.obj;
                    uIProgressResponseListener.onUIResponseProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    return;
                }
                return;
            }
            super.handleMessage(message);
        }
    }

    @Override // com.aivox.base.http.progress.ProgressResponseListener
    public void onResponseProgress(long j, long j2, boolean z) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = new ProgressModel(j, j2, z);
        messageObtain.what = 2;
        this.mHandler.sendMessage(messageObtain);
    }
}
