package com.aivox.common.util;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.util.tts.TtsManager;
import com.microsoft.cognitiveservices.speech.Connection;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.util.EventHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class MicrosoftTtsManager implements TtsManager {
    public static final int EVENT_CANCEL = 2;
    public static final int EVENT_COMPLETE = 1;
    public static final int EVENT_START = 0;
    private Connection connection;
    private AudioTrack mAudioTrack;
    private OnEventChangeListener mEventChangeListener;
    private ITextToSpeechListener mTtsListener;
    private SpeechSynthesizer synthesizer;
    private TtsManager.TtsListener ttsListener;
    private final String speechSubscriptionKey = "10aa9904c17e47ec8fd3320f00b54661";
    private final String serviceRegion = "eastus";
    private boolean isSpeaking = false;
    private boolean isInterapted = false;
    private int langType = MyEnum.TTS_LANGUAGE_TYPE.EN.type;
    private SpeechConfig speechConfig = SpeechConfig.fromSubscription("10aa9904c17e47ec8fd3320f00b54661", "eastus");
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface ITextToSpeechListener {
        void onSpeechError();

        void onSpeechFinish();

        void onSpeechStart();
    }

    public interface OnEventChangeListener {
        void onEventChanged(int i);
    }

    public MicrosoftTtsManager() {
        LogUtil.m335d("MicrosoftTtsManager", "MicrosoftTtsManager init");
        initSynthesizer();
    }

    private void initSynthesizer() {
        if (this.speechConfig == null) {
            this.speechConfig = SpeechConfig.fromSubscription("10aa9904c17e47ec8fd3320f00b54661", "eastus");
        }
        this.speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Raw16Khz16BitMonoPcm);
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(this.speechConfig, (AudioConfig) null);
        this.synthesizer = speechSynthesizer;
        Connection connectionFromSpeechSynthesizer = Connection.fromSpeechSynthesizer(speechSynthesizer);
        this.connection = connectionFromSpeechSynthesizer;
        connectionFromSpeechSynthesizer.openConnection(true);
        LogUtil.m335d("MicrosoftTtsManager", "TTS connection opened with keep-alive.");
    }

    private void stopAndReleaseAudioTrack() {
        AudioTrack audioTrack = this.mAudioTrack;
        if (audioTrack != null) {
            try {
                if (audioTrack.getPlayState() == 3) {
                    this.mAudioTrack.stop();
                }
                this.mAudioTrack.release();
            } catch (Exception unused) {
            }
            this.mAudioTrack = null;
        }
    }

    public void setListener(ITextToSpeechListener iTextToSpeechListener) {
        this.mTtsListener = iTextToSpeechListener;
    }

    public void speak(String str, int i, boolean z) {
        speak(str, i, z, Constant.AudioStreamType.NORMAL);
    }

    public void speak(final String str, int i, boolean z, final Constant.AudioStreamType audioStreamType) {
        this.isInterapted = false;
        LogUtil.m337e("TTS", "speak: " + str);
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        if (this.isSpeaking) {
            stopAll();
            if (!z) {
                return;
            }
        }
        if (this.speechConfig == null) {
            this.speechConfig = SpeechConfig.fromSubscription("10aa9904c17e47ec8fd3320f00b54661", "eastus");
        }
        this.speechConfig.setSpeechSynthesisLanguage(MyEnum.TTS_LANGUAGE_TYPE.getTtsLanguageName(i));
        if (this.synthesizer == null) {
            initSynthesizer();
        }
        this.synthesizer.SynthesisStarted.addEventListener(new EventHandler() { // from class: com.aivox.common.util.MicrosoftTtsManager$$ExternalSyntheticLambda0
            @Override // com.microsoft.cognitiveservices.speech.util.EventHandler
            public final void onEvent(Object obj, Object obj2) {
                this.f$0.m2472lambda$speak$0$comaivoxcommonutilMicrosoftTtsManager(obj, (SpeechSynthesisEventArgs) obj2);
            }
        });
        ExecutorService executorService = this.executor;
        if (executorService == null || executorService.isShutdown()) {
            this.executor = Executors.newSingleThreadExecutor();
        }
        this.executor.submit(new Runnable() { // from class: com.aivox.common.util.MicrosoftTtsManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2473lambda$speak$1$comaivoxcommonutilMicrosoftTtsManager(str, audioStreamType);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$speak$0$com-aivox-common-util-MicrosoftTtsManager, reason: not valid java name */
    /* synthetic */ void m2472lambda$speak$0$comaivoxcommonutilMicrosoftTtsManager(Object obj, SpeechSynthesisEventArgs speechSynthesisEventArgs) {
        this.isSpeaking = true;
        ITextToSpeechListener iTextToSpeechListener = this.mTtsListener;
        if (iTextToSpeechListener != null) {
            iTextToSpeechListener.onSpeechStart();
        }
        TtsManager.TtsListener ttsListener = this.ttsListener;
        if (ttsListener != null) {
            ttsListener.onTtsStart();
        }
        OnEventChangeListener onEventChangeListener = this.mEventChangeListener;
        if (onEventChangeListener != null) {
            onEventChangeListener.onEventChanged(0);
        }
    }

    /* JADX INFO: renamed from: lambda$speak$1$com-aivox-common-util-MicrosoftTtsManager, reason: not valid java name */
    /* synthetic */ void m2473lambda$speak$1$comaivoxcommonutilMicrosoftTtsManager(String str, Constant.AudioStreamType audioStreamType) {
        try {
            byte[] audioData = this.synthesizer.SpeakText(str).getAudioData();
            if (this.isInterapted) {
                return;
            }
            int length = audioData.length;
            int i = length / 2;
            stopAndReleaseAudioTrack();
            AudioTrack audioTrackBuild = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(1).build()).setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(16000).setChannelMask(4).build()).setBufferSizeInBytes(length).setTransferMode(0).build();
            this.mAudioTrack = audioTrackBuild;
            audioTrackBuild.write(audioData, 0, length);
            float f = 0.0f;
            float f2 = audioStreamType == Constant.AudioStreamType.LEFT ? 0.0f : 1.0f;
            if (audioStreamType != Constant.AudioStreamType.RIGHT) {
                f = 1.0f;
            }
            this.mAudioTrack.setStereoVolume(f2, f);
            this.mAudioTrack.setNotificationMarkerPosition(i - 1);
            this.mAudioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() { // from class: com.aivox.common.util.MicrosoftTtsManager.1
                @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
                public void onPeriodicNotification(AudioTrack audioTrack) {
                }

                @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
                public void onMarkerReached(AudioTrack audioTrack) {
                    MicrosoftTtsManager.this.isSpeaking = false;
                    if (MicrosoftTtsManager.this.mTtsListener != null) {
                        MicrosoftTtsManager.this.mTtsListener.onSpeechFinish();
                    }
                    if (MicrosoftTtsManager.this.ttsListener != null) {
                        MicrosoftTtsManager.this.ttsListener.onTtsStop();
                    }
                    if (MicrosoftTtsManager.this.mEventChangeListener != null) {
                        MicrosoftTtsManager.this.mEventChangeListener.onEventChanged(1);
                    }
                }
            });
            this.mAudioTrack.play();
        } catch (Exception e) {
            e.printStackTrace();
            this.isSpeaking = false;
            ITextToSpeechListener iTextToSpeechListener = this.mTtsListener;
            if (iTextToSpeechListener != null) {
                iTextToSpeechListener.onSpeechError();
            }
            TtsManager.TtsListener ttsListener = this.ttsListener;
            if (ttsListener != null) {
                ttsListener.onTtsError("播放错误");
            }
            OnEventChangeListener onEventChangeListener = this.mEventChangeListener;
            if (onEventChangeListener != null) {
                onEventChangeListener.onEventChanged(2);
            }
        }
    }

    public void setOnEventChangeListener(OnEventChangeListener onEventChangeListener) {
        this.mEventChangeListener = onEventChangeListener;
    }

    public void stopAll() {
        this.isInterapted = true;
        SpeechSynthesizer speechSynthesizer = this.synthesizer;
        if (speechSynthesizer != null) {
            speechSynthesizer.StopSpeakingAsync();
        }
        stopAndReleaseAudioTrack();
        this.isSpeaking = false;
        ITextToSpeechListener iTextToSpeechListener = this.mTtsListener;
        if (iTextToSpeechListener != null) {
            iTextToSpeechListener.onSpeechFinish();
        }
        OnEventChangeListener onEventChangeListener = this.mEventChangeListener;
        if (onEventChangeListener != null) {
            onEventChangeListener.onEventChanged(1);
        }
    }

    public synchronized void releaseAll() {
        stopAll();
        ExecutorService executorService = this.executor;
        if (executorService != null) {
            executorService.shutdownNow();
            this.executor = null;
        }
        SpeechSynthesizer speechSynthesizer = this.synthesizer;
        if (speechSynthesizer != null) {
            try {
                try {
                    Future<Void> futureStopSpeakingAsync = speechSynthesizer.StopSpeakingAsync();
                    if (futureStopSpeakingAsync != null) {
                        futureStopSpeakingAsync.get(3L, TimeUnit.SECONDS);
                    }
                    this.synthesizer.close();
                } catch (Throwable th) {
                    try {
                        this.synthesizer.close();
                    } catch (Exception unused) {
                    }
                    this.synthesizer = null;
                    throw th;
                }
            } catch (Exception e) {
                LogUtil.m337e("MicrosoftTtsManager", "Stop async wait error: " + e.getMessage());
                this.synthesizer.close();
            }
            this.synthesizer = null;
        }
        Connection connection = this.connection;
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception unused2) {
            }
            this.connection = null;
        }
        SpeechConfig speechConfig = this.speechConfig;
        if (speechConfig != null) {
            speechConfig.close();
            this.speechConfig = null;
        }
        stopAndReleaseAudioTrack();
        this.mTtsListener = null;
        this.ttsListener = null;
        this.mEventChangeListener = null;
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void init(String str) {
        if (str.contains("zh")) {
            if (str.contains("CN")) {
                this.langType = MyEnum.TTS_LANGUAGE_TYPE.ZH.type;
                return;
            } else if (str.contains("HK")) {
                this.langType = MyEnum.TTS_LANGUAGE_TYPE.YUE_HK.type;
                return;
            } else {
                this.langType = MyEnum.TTS_LANGUAGE_TYPE.CHT.type;
                return;
            }
        }
        if (str.startsWith("ja")) {
            this.langType = MyEnum.TTS_LANGUAGE_TYPE.JP.type;
            return;
        }
        if (str.startsWith("ko")) {
            this.langType = MyEnum.TTS_LANGUAGE_TYPE.KO.type;
            return;
        }
        if (str.startsWith("th")) {
            this.langType = MyEnum.TTS_LANGUAGE_TYPE.TH.type;
        } else if (str.startsWith("vi")) {
            this.langType = MyEnum.TTS_LANGUAGE_TYPE.VI.type;
        } else {
            this.langType = MyEnum.TTS_LANGUAGE_TYPE.EN.type;
        }
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void speak(String str, boolean z) {
        if (z) {
            stopAll();
        }
        speak(str, this.langType, true);
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void stop() {
        stopAll();
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void release() {
        releaseAll();
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void setTtsListener(TtsManager.TtsListener ttsListener) {
        this.ttsListener = ttsListener;
    }
}
