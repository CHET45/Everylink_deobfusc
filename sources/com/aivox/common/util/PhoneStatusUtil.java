package com.aivox.common.util;

import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.Build;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.model.EventBean;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class PhoneStatusUtil {
    private static PhoneStatusUtil ins;
    AudioDeviceCallback audioDeviceCallback = new AudioDeviceCallback() { // from class: com.aivox.common.util.PhoneStatusUtil.1
        @Override // android.media.AudioDeviceCallback
        public void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
            super.onAudioDevicesAdded(audioDeviceInfoArr);
            if (audioDeviceInfoArr.length > 0) {
                for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                    LogUtil.m336e("AUDIO==onAudioDevicesAdded:" + audioDeviceInfo.toString());
                }
            }
        }

        @Override // android.media.AudioDeviceCallback
        public void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
            super.onAudioDevicesRemoved(audioDeviceInfoArr);
            if (audioDeviceInfoArr.length > 0) {
                for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                    LogUtil.m336e("AUDIO==onAudioDevicesRemoved:" + audioDeviceInfo.toString());
                }
            }
        }
    };
    AudioManager.AudioPlaybackCallback audioPlaybackCallback = new AudioManager.AudioPlaybackCallback() { // from class: com.aivox.common.util.PhoneStatusUtil.2
        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) {
            super.onPlaybackConfigChanged(list);
            if (list.size() == 0) {
                LogUtil.m336e("AUDIO===AudioPlaybackConfiguration:size==0");
                return;
            }
            Iterator<AudioPlaybackConfiguration> it = list.iterator();
            while (it.hasNext()) {
                LogUtil.m336e("AUDIO===AudioPlaybackConfiguration:" + it.next().getAudioAttributes().toString());
            }
        }
    };
    AudioManager.AudioRecordingCallback audioRecordingCallback = new AudioManager.AudioRecordingCallback() { // from class: com.aivox.common.util.PhoneStatusUtil.3
        @Override // android.media.AudioManager.AudioRecordingCallback
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
            super.onRecordingConfigChanged(list);
            int size = list.size();
            LogUtil.m336e("AUDIO==configs.size:" + size);
            if (size == 0) {
                return;
            }
            for (int i = 0; i < size; i++) {
                AudioRecordingConfiguration audioRecordingConfiguration = list.get(i);
                int clientAudioSource = audioRecordingConfiguration.getClientAudioSource();
                PhoneStatusUtil.this.configToString(audioRecordingConfiguration);
                LogUtil.m336e("AUDIO===source:" + clientAudioSource);
            }
        }
    };
    AudioManager mAudioManager;

    public static PhoneStatusUtil getIns() {
        if (ins == null) {
            ins = new PhoneStatusUtil();
        }
        return ins;
    }

    public AudioManager getAudioManager() {
        return this.mAudioManager;
    }

    public void unregisterAudioRecordingCallback() {
        if (this.mAudioManager != null) {
            this.mAudioManager.unregisterAudioRecordingCallback(this.audioRecordingCallback);
            this.mAudioManager.unregisterAudioDeviceCallback(this.audioDeviceCallback);
            this.mAudioManager.unregisterAudioPlaybackCallback(this.audioPlaybackCallback);
        }
    }

    public void registerAudioRecordingCallback() {
        this.mAudioManager = (AudioManager) BaseAppUtils.getContext().getSystemService("audio");
        LogUtil.m336e("AUDIO====initAudio__mode=" + this.mAudioManager.getMode());
        this.mAudioManager.getMode();
        this.mAudioManager.registerAudioDeviceCallback(this.audioDeviceCallback, null);
        this.mAudioManager.registerAudioPlaybackCallback(this.audioPlaybackCallback, null);
        if (Build.VERSION.SDK_INT >= 29) {
            this.mAudioManager.registerAudioRecordingCallback(this.audioRecordingCallback, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configToString(AudioRecordingConfiguration audioRecordingConfiguration) {
        if (Build.VERSION.SDK_INT < 29 || audioRecordingConfiguration == null || audioRecordingConfiguration.getAudioDevice() == null) {
            return;
        }
        LogUtil.m336e("AUDIO====静默状态:" + audioRecordingConfiguration.isClientSilenced() + ";device:" + (audioRecordingConfiguration.getAudioDevice() == null ? "null" : audioRecordingConfiguration.getAudioDevice().toString()) + ";effect:" + audioRecordingConfiguration.getEffects() + ";format:" + audioRecordingConfiguration.getFormat().toString());
        if (audioRecordingConfiguration.isClientSilenced()) {
            EventBus.getDefault().post(new EventBean(Constant.EVENT.RECORD_PAUSE_ANSWER_PHONE));
        }
    }
}
