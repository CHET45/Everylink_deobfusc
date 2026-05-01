package com.aivox.base.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.aivox.base.util.LogUtil;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class LockChangeObserver {
    private static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    private static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    private static final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";
    private Context mContext;
    private LockChangeListener mLockChangeListener;
    private LockReceiver mLockReceiver;

    public interface LockChangeListener {
        void isLocked(boolean z);
    }

    public LockChangeObserver(Context context) {
        this.mContext = context;
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SCREEN_OFF);
        intentFilter.addAction(ACTION_SCREEN_ON);
        intentFilter.addAction(ACTION_USER_PRESENT);
        LockReceiver lockReceiver = new LockReceiver(this);
        this.mLockReceiver = lockReceiver;
        this.mContext.registerReceiver(lockReceiver, intentFilter);
    }

    public void unRegisterReceiver() {
        LockReceiver lockReceiver = this.mLockReceiver;
        if (lockReceiver != null) {
            this.mContext.unregisterReceiver(lockReceiver);
        }
        this.mLockChangeListener = null;
    }

    public void setLockChangeListener(LockChangeListener lockChangeListener) {
        this.mLockChangeListener = lockChangeListener;
    }

    private static class LockReceiver extends BroadcastReceiver {
        private WeakReference<LockChangeObserver> mObserver;

        LockReceiver(LockChangeObserver lockChangeObserver) {
            this.mObserver = new WeakReference<>(lockChangeObserver);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            WeakReference<LockChangeObserver> weakReference = this.mObserver;
            if (weakReference == null || weakReference.get().mLockChangeListener == null) {
                return;
            }
            LockChangeListener lockChangeListener = this.mObserver.get().mLockChangeListener;
            LogUtil.m338i("广播：" + intent.getAction());
            String action = intent.getAction();
            action.hashCode();
            if (action.equals(LockChangeObserver.ACTION_SCREEN_OFF)) {
                LogUtil.m334d("锁屏");
                lockChangeListener.isLocked(true);
            } else if (action.equals(LockChangeObserver.ACTION_USER_PRESENT)) {
                LogUtil.m334d("解锁");
                lockChangeListener.isLocked(false);
            }
        }
    }
}
