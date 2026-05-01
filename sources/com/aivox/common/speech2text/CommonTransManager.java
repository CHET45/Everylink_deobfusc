package com.aivox.common.speech2text;

import android.app.Activity;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class CommonTransManager {
    int downType;
    LocalTransManager localTransManager;
    LocalTransManager tencentTransDown;
    LocalTransManager tencentTransUp;
    int upType;
    int tranStatus = MyEnum.TRANS_MODEL_STATUS.NOT_START.type;
    boolean isBleMode = true;

    public int getTranStatus() {
        return this.tranStatus;
    }

    public void startRecording(BaseAudioDataSource baseAudioDataSource) {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.RECORDING_WITHOUT_TRANS.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.startRecording(baseAudioDataSource);
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.startRecording(baseAudioDataSource);
                return;
            }
            return;
        }
        this.localTransManager.startRecording(baseAudioDataSource);
    }

    public void startTrans(boolean z, int i, String str, ICommonTransCallback iCommonTransCallback) {
        LogUtil.m334d("CommonTransManager startTrans" + hashCode() + " from: " + i);
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.start(z, i, str, iCommonTransCallback);
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.start(z, i, str, iCommonTransCallback);
                return;
            }
            return;
        }
        this.localTransManager.start(z, i, str, iCommonTransCallback);
    }

    public void startTrans(boolean z, int i, int i2, String str, ICommonTransCallback iCommonTransCallback) {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.start(z, i, str, iCommonTransCallback);
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.start(z, i2, str, iCommonTransCallback);
                return;
            }
            return;
        }
        this.localTransManager.start(z, i, str, iCommonTransCallback);
    }

    public CommonTransManager(Activity activity, boolean z) {
        this.localTransManager = new LocalTransManager(activity, z);
    }

    public CommonTransManager(int i, int i2, Activity activity, boolean z) {
        this.upType = i;
        this.downType = i2;
        if (i == MyEnum.TRANS_TYPE.TENCENT.type) {
            this.tencentTransUp = new LocalTransManager(activity, z, true);
        }
        if (i2 == MyEnum.TRANS_TYPE.TENCENT.type) {
            this.tencentTransDown = new LocalTransManager(activity, z, false);
        }
    }

    public void pauseAudio() {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.RECORD_PAUSED.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.pauseAudio();
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.pauseAudio();
                return;
            }
            return;
        }
        this.localTransManager.pauseAudio();
    }

    public void resumeAudio() {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.resumeAudio();
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.resumeAudio();
                return;
            }
            return;
        }
        this.localTransManager.resumeAudio();
    }

    public void stopAudio() {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.FINISHED.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.stopAudio();
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.stopAudio();
                return;
            }
            return;
        }
        this.localTransManager.stopAudio();
    }

    public void stopRecognize() {
        this.tranStatus = MyEnum.TRANS_MODEL_STATUS.RECORDING_WITHOUT_TRANS.type;
        if (this.isBleMode) {
            if (this.upType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransUp.stopRecognize();
            }
            if (this.downType == MyEnum.TRANS_TYPE.TENCENT.type) {
                this.tencentTransDown.stopRecognize();
                return;
            }
            return;
        }
        this.localTransManager.stopRecognize();
    }
}
