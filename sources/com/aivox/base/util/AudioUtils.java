package com.aivox.base.util;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public class AudioUtils {
    private static final String HEADSET_STATE_PATH = "/sys/class/switch/h2w/state";
    private static AudioUtils audioUtils;
    private static AudioManager mAudioManager;
    private static Context mContext;
    AudioFocusRequest audioFocusRequest;
    boolean isWiredHeadssetOn;
    int mConnectIndex = 0;

    public void changeToBluetoothSpeaker() {
    }

    public void changeToSpeaker() {
    }

    public static AudioUtils getIns(Context context) {
        if (audioUtils == null) {
            mContext = context;
            audioUtils = new AudioUtils();
            mAudioManager = (AudioManager) context.getSystemService("audio");
        }
        return audioUtils;
    }

    private boolean isBluetoothConnected() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled() && defaultAdapter.getProfileConnectionState(1) == 2;
    }

    public void changeToSpeaker2() {
        mAudioManager.setMode(0);
        mAudioManager.setSpeakerphoneOn(true);
    }

    public void changeToHeadset() {
        mAudioManager.setMode(3);
        mAudioManager.setSpeakerphoneOn(false);
    }

    public void changeToReceiver() {
        mAudioManager.setSpeakerphoneOn(false);
    }

    public void openSco() {
        if (isBluetoothConnected()) {
            if (!mAudioManager.isBluetoothScoAvailableOffCall()) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.bluetooth_record_not_supported));
                return;
            }
            this.mConnectIndex = 0;
            mAudioManager.startBluetoothSco();
            LogUtil.m338i("---startBluetoothSco---");
            mContext.registerReceiver(new BroadcastReceiver() { // from class: com.aivox.base.util.AudioUtils.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    int intExtra = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1);
                    LogUtil.m338i("---state---" + intExtra);
                    if (1 == intExtra) {
                        AudioUtils.mAudioManager.setBluetoothScoOn(true);
                        AudioUtils.mContext.unregisterReceiver(this);
                        return;
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        BaseAppUtils.printErrorMsg(e);
                        ToastUtil.showShort(e.getLocalizedMessage());
                    }
                    LogUtil.m338i("---重试---");
                    if (AudioUtils.this.mConnectIndex < 5) {
                        AudioUtils.mAudioManager.startBluetoothSco();
                    } else {
                        AudioUtils.mContext.unregisterReceiver(this);
                    }
                    AudioUtils.this.mConnectIndex++;
                }
            }, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        }
    }

    public void closeSco() {
        if (mAudioManager.isBluetoothScoOn()) {
            mAudioManager.setBluetoothScoOn(false);
            mAudioManager.stopBluetoothSco();
        }
    }

    public boolean isHeadsetExists() {
        this.isWiredHeadssetOn = mAudioManager.isWiredHeadsetOn();
        LogUtil.m338i("耳机：" + this.isWiredHeadssetOn);
        return this.isWiredHeadssetOn;
    }

    public void reqAudioFocus() {
        if (isBluetoothConnected()) {
            if (this.audioFocusRequest == null) {
                this.audioFocusRequest = new AudioFocusRequest.Builder(1).setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() { // from class: com.aivox.base.util.AudioUtils.2
                    @Override // android.media.AudioManager.OnAudioFocusChangeListener
                    public void onAudioFocusChange(int i) {
                    }
                }).build();
            }
            mAudioManager.abandonAudioFocusRequest(this.audioFocusRequest);
            LogUtil.m334d("requestAudioFocus result" + mAudioManager.requestAudioFocus(this.audioFocusRequest));
        }
    }

    public void clearAudioFocus() {
        mAudioManager.abandonAudioFocus(null);
    }
}
