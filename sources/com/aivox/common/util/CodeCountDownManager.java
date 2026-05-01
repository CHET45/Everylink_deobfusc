package com.aivox.common.util;

import android.os.CountDownTimer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class CodeCountDownManager {
    private static boolean mIsTicking;
    private static final Set<CountDownListener> mListenerSet = new HashSet();
    private static CountDownTimer mTimer;

    public interface CountDownListener {
        void onFinish();

        void onTick(long j);
    }

    private CodeCountDownManager() {
    }

    private static final class MInstanceHolder {
        static final CodeCountDownManager mInstance = new CodeCountDownManager();

        private MInstanceHolder() {
        }
    }

    public static CodeCountDownManager getInstance() {
        mTimer = new CountDownTimer(60000L, 1000L) { // from class: com.aivox.common.util.CodeCountDownManager.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                boolean unused = CodeCountDownManager.mIsTicking = true;
                if (CodeCountDownManager.mListenerSet.isEmpty()) {
                    return;
                }
                Iterator it = CodeCountDownManager.mListenerSet.iterator();
                while (it.hasNext()) {
                    ((CountDownListener) it.next()).onTick(j / 1000);
                }
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                boolean unused = CodeCountDownManager.mIsTicking = false;
                if (CodeCountDownManager.mListenerSet.isEmpty()) {
                    return;
                }
                Iterator it = CodeCountDownManager.mListenerSet.iterator();
                while (it.hasNext()) {
                    ((CountDownListener) it.next()).onFinish();
                }
            }
        };
        return MInstanceHolder.mInstance;
    }

    public void registerAndStart(CountDownListener countDownListener) {
        mListenerSet.add(countDownListener);
        CountDownTimer countDownTimer = mTimer;
        if (countDownTimer == null || mIsTicking) {
            return;
        }
        countDownTimer.start();
    }

    public void unRegister(CountDownListener countDownListener) {
        mListenerSet.remove(countDownListener);
    }

    public boolean isTicking() {
        return mIsTicking;
    }

    public void stopTicking() {
        mIsTicking = false;
        mTimer.cancel();
    }
}
