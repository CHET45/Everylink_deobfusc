package com.aivox.app.util;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.util.tts.TtsManager;
import com.tencent.cloud.stream.tts.FlowingSpeechSynthesizer;
import com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener;
import com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerRequest;
import com.tencent.cloud.stream.tts.SpeechSynthesizerResponse;
import com.tencent.cloud.stream.tts.core.p032ws.Credential;
import com.tencent.cloud.stream.tts.core.p032ws.SpeechClient;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class TencentTtsManager implements TtsManager {
    private static final ByteBuffer END_OF_SYNTHESIS_MARKER = ByteBuffer.allocate(0);
    private volatile AudioPlayerThread mAudioPlayerThread;
    private TtsManager.TtsListener mTtsListener;
    private volatile FlowingSpeechSynthesizer synthesizer;
    private final String TAG = "TencentTtsManager";
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private volatile TtsState mState = TtsState.IDLE;
    private final int APP_ID = 1302668744;
    private final String SECRET_ID = "AKIDHQKtBt8eeT8tgE7CgndCYidKp5LLubAT";
    private final String SECRET_KEY = "EExrVu65pHWUSAdsGPJwJPFqbz84khOC";
    private boolean needBreak = false;
    private final SpeechClient proxy = new SpeechClient();
    private final Credential credential = new Credential(String.valueOf(1302668744), "AKIDHQKtBt8eeT8tgE7CgndCYidKp5LLubAT", "EExrVu65pHWUSAdsGPJwJPFqbz84khOC", null);

    private enum TtsState {
        IDLE,
        PREPARING,
        PLAYING
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void init(String str) {
        LogUtil.m335d("TencentTtsManager", "Initializing new TTS session with language: " + str);
        if (this.mState == TtsState.PLAYING) {
            stop();
        }
        initTtsCore();
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void speak(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.needBreak = z;
        try {
            if (this.synthesizer != null) {
                if (this.mState == TtsState.PLAYING && z) {
                    stop();
                }
                this.mState = TtsState.PREPARING;
                this.synthesizer.process(str + "。");
                return;
            }
            init("en");
            if (this.synthesizer != null) {
                this.mState = TtsState.PREPARING;
                this.synthesizer.process(str);
            }
        } catch (Exception e) {
            LogUtil.m337e("TencentTtsManager", "Speak process failed:" + e.getLocalizedMessage());
            stop();
            TtsManager.TtsListener ttsListener = this.mTtsListener;
            if (ttsListener != null) {
                ttsListener.onTtsError("Speak process failed: " + e.getLocalizedMessage());
            }
        }
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void stop() {
        LogUtil.m335d("TencentTtsManager", "Stopping TTS session...");
        if (this.synthesizer != null) {
            try {
                this.synthesizer.stop();
            } catch (Exception unused) {
            }
            stopAudioPlayerThread();
            LogUtil.m335d("TencentTtsManager", "stop complete. ttsState=" + this.mState);
        }
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void release() {
        LogUtil.m335d("TencentTtsManager", "Releasing TencentTtsManager...");
        if (this.synthesizer != null) {
            stop();
            this.synthesizer.cancel();
            this.synthesizer = null;
        }
        this.mMainHandler.removeCallbacksAndMessages(null);
        this.mTtsListener = null;
    }

    @Override // com.aivox.common.util.tts.TtsManager
    public void setTtsListener(TtsManager.TtsListener ttsListener) {
        this.mTtsListener = ttsListener;
    }

    private void initTtsCore() {
        try {
            this.mAudioPlayerThread = new AudioPlayerThread();
            this.mAudioPlayerThread.start();
            this.synthesizer = new FlowingSpeechSynthesizer(this.proxy, this.credential, createTtsRequest(), createTtsListener());
            this.synthesizer.start();
            LogUtil.m335d("TencentTtsManager", "synthesizer.start()");
            this.mState = TtsState.PREPARING;
            LogUtil.m335d("TencentTtsManager", "TTS session initialized. State=" + this.mState);
        } catch (Exception e) {
            LogUtil.m337e("TencentTtsManager", "initTtsCore failed:" + e.getLocalizedMessage());
            stop();
            TtsManager.TtsListener ttsListener = this.mTtsListener;
            if (ttsListener != null) {
                ttsListener.onTtsError("TTS core initialization failed: " + e.getLocalizedMessage());
            }
        }
    }

    private FlowingSpeechSynthesizerRequest createTtsRequest() {
        FlowingSpeechSynthesizerRequest flowingSpeechSynthesizerRequest = new FlowingSpeechSynthesizerRequest();
        flowingSpeechSynthesizerRequest.setVolume(Float.valueOf(0.0f));
        flowingSpeechSynthesizerRequest.setSpeed(Float.valueOf(1.0f));
        flowingSpeechSynthesizerRequest.setCodec("pcm");
        flowingSpeechSynthesizerRequest.setSampleRate(16000);
        flowingSpeechSynthesizerRequest.setVoiceType(602003);
        flowingSpeechSynthesizerRequest.setEnableSubtitle(true);
        flowingSpeechSynthesizerRequest.setEmotionCategory("neutral");
        flowingSpeechSynthesizerRequest.setEmotionIntensity(100);
        flowingSpeechSynthesizerRequest.setSessionId(SPUtil.get(SPUtil.USER_ID, "") + "_tts_" + System.currentTimeMillis());
        return flowingSpeechSynthesizerRequest;
    }

    /* JADX INFO: renamed from: com.aivox.app.util.TencentTtsManager$1 */
    class C08441 extends FlowingSpeechSynthesizerListener {
        C08441() {
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onSynthesisStart(SpeechSynthesizerResponse speechSynthesizerResponse) {
            LogUtil.m335d(TAG, "onSynthesisStart: " + speechSynthesizerResponse.getMessage());
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onSynthesisEnd(SpeechSynthesizerResponse speechSynthesizerResponse) {
            LogUtil.m335d(TAG, "onSynthesisEnd: " + speechSynthesizerResponse.getMessage());
            if (TencentTtsManager.this.mAudioPlayerThread != null) {
                try {
                    TencentTtsManager.this.mAudioPlayerThread.putAudioData(TencentTtsManager.END_OF_SYNTHESIS_MARKER);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onAudioResult(ByteBuffer byteBuffer) {
            if (TencentTtsManager.this.mAudioPlayerThread == null) {
                return;
            }
            try {
                if (byteBuffer.hasRemaining()) {
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.remaining());
                    byteBufferAllocate.put(byteBuffer);
                    byteBufferAllocate.flip();
                    TencentTtsManager.this.mAudioPlayerThread.putAudioData(byteBufferAllocate);
                }
            } catch (InterruptedException unused) {
                LogUtil.m337e(TAG, "AudioPlayerThread interrupted while putting data.");
                Thread.currentThread().interrupt();
            }
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onTextResult(SpeechSynthesizerResponse speechSynthesizerResponse) {
            LogUtil.m335d(TAG, "onTextResult: " + speechSynthesizerResponse.getMessage());
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onSynthesisCancel() {
            LogUtil.m335d(TAG, "onSynthesisCancel");
        }

        @Override // com.tencent.cloud.stream.tts.FlowingSpeechSynthesizerListener
        public void onSynthesisFail(SpeechSynthesizerResponse speechSynthesizerResponse) {
            LogUtil.m337e(TAG, "onSynthesisFail: " + speechSynthesizerResponse.getMessage());
            TencentTtsManager.this.mMainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentTtsManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2394lambda$onSynthesisFail$0$comaivoxapputilTencentTtsManager$1();
                }
            });
            if (TencentTtsManager.this.mTtsListener != null) {
                TencentTtsManager.this.mTtsListener.onTtsError(speechSynthesizerResponse.getMessage());
            }
        }

        /* JADX INFO: renamed from: lambda$onSynthesisFail$0$com-aivox-app-util-TencentTtsManager$1, reason: not valid java name */
        /* synthetic */ void m2394lambda$onSynthesisFail$0$comaivoxapputilTencentTtsManager$1() {
            TencentTtsManager.this.stop();
        }
    }

    private FlowingSpeechSynthesizerListener createTtsListener() {
        return new C08441();
    }

    private void stopAudioPlayerThread() {
        AudioPlayerThread audioPlayerThread = this.mAudioPlayerThread;
        this.mAudioPlayerThread = null;
        if (audioPlayerThread != null) {
            audioPlayerThread.stopGracefully();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTtsStop() {
        this.mMainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentTtsManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2393lambda$notifyTtsStop$0$comaivoxapputilTencentTtsManager();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$notifyTtsStop$0$com-aivox-app-util-TencentTtsManager, reason: not valid java name */
    /* synthetic */ void m2393lambda$notifyTtsStop$0$comaivoxapputilTencentTtsManager() {
        if (this.mState != TtsState.IDLE) {
            LogUtil.m335d("TencentTtsManager", "State changed to IDLE (Stop/Timeout)");
            this.mState = TtsState.IDLE;
            TtsManager.TtsListener ttsListener = this.mTtsListener;
            if (ttsListener != null) {
                ttsListener.onTtsStop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AudioPlayerThread extends Thread {
        private final BlockingQueue<ByteBuffer> mAudioQueue;
        private AudioTrack mAudioTrack;
        private volatile boolean mIsRunning;
        private long mTotalBytesWritten;

        public AudioPlayerThread() {
            super("TencentTts-AudioPlayer");
            this.mAudioQueue = new LinkedBlockingQueue();
            this.mIsRunning = true;
            this.mTotalBytesWritten = 0L;
        }

        public void putAudioData(ByteBuffer byteBuffer) throws InterruptedException {
            if (this.mIsRunning) {
                this.mAudioQueue.put(byteBuffer);
            }
        }

        public void stopGracefully() {
            this.mIsRunning = false;
            this.mAudioQueue.clear();
            this.mAudioQueue.offer(TencentTtsManager.END_OF_SYNTHESIS_MARKER);
            interrupt();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            ByteBuffer byteBufferTake;
            AudioTrack audioTrack;
            AudioTrack audioTrackBuild = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(1).build()).setAudioFormat(new AudioFormat.Builder().setSampleRate(16000).setEncoding(2).setChannelMask(4).build()).setBufferSizeInBytes(AudioTrack.getMinBufferSize(16000, 4, 2) * 4).setTransferMode(1).build();
            this.mAudioTrack = audioTrackBuild;
            try {
                try {
                    audioTrackBuild.play();
                    loop0: while (true) {
                        boolean z = false;
                        while (this.mIsRunning) {
                            if (TencentTtsManager.this.needBreak) {
                                byteBufferTake = this.mAudioQueue.poll(50L, TimeUnit.MILLISECONDS);
                            } else {
                                byteBufferTake = this.mAudioQueue.take();
                            }
                            if (byteBufferTake == null) {
                                if (TencentTtsManager.this.mState == TtsState.PLAYING && (audioTrack = this.mAudioTrack) != null) {
                                    long j = this.mTotalBytesWritten / 2;
                                    long playbackHeadPosition = ((long) audioTrack.getPlaybackHeadPosition()) & 4294967295L;
                                    if (playbackHeadPosition >= j && !z) {
                                        LogUtil.m335d("TencentTtsManager", "AudioTrack buffer empty (Physical End). written=" + j + ", played=" + playbackHeadPosition);
                                        TencentTtsManager.this.notifyTtsStop();
                                        z = true;
                                    }
                                }
                            } else if (TencentTtsManager.this.needBreak && byteBufferTake == TencentTtsManager.END_OF_SYNTHESIS_MARKER) {
                                handlePlaybackComplete();
                            } else if (byteBufferTake.hasRemaining() && this.mIsRunning) {
                                if (TencentTtsManager.this.mState == TtsState.PREPARING || TencentTtsManager.this.mState == TtsState.IDLE) {
                                    notifyTtsStart();
                                }
                                int iWrite = this.mAudioTrack.write(byteBufferTake, byteBufferTake.remaining(), 0);
                                if (iWrite > 0) {
                                    this.mTotalBytesWritten += (long) iWrite;
                                } else if (iWrite < 0) {
                                    LogUtil.m337e("TencentTtsManager", "AudioTrack write error: " + iWrite);
                                }
                            }
                        }
                        break loop0;
                    }
                } catch (InterruptedException unused) {
                    LogUtil.m335d("TencentTtsManager", "AudioPlayerThread interrupted/stopped.");
                } catch (Exception e) {
                    LogUtil.m337e("TencentTtsManager", "AudioPlayerThread error: " + e.getMessage());
                }
            } finally {
                releaseAudioTrack();
            }
        }

        private void notifyTtsStart() {
            TencentTtsManager.this.mMainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentTtsManager$AudioPlayerThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m329xa58816a2();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$notifyTtsStart$0$com-aivox-app-util-TencentTtsManager$AudioPlayerThread */
        /* synthetic */ void m329xa58816a2() {
            if (TencentTtsManager.this.mState != TtsState.PLAYING) {
                TencentTtsManager.this.mState = TtsState.PLAYING;
                if (TencentTtsManager.this.mTtsListener != null) {
                    TencentTtsManager.this.mTtsListener.onTtsStart();
                }
            }
        }

        private void handlePlaybackComplete() {
            LogUtil.m335d("TencentTtsManager", "Audio playback finished (Buffer drained).");
            TencentTtsManager.this.mMainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentTtsManager$AudioPlayerThread$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m328x223708dd();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$handlePlaybackComplete$1$com-aivox-app-util-TencentTtsManager$AudioPlayerThread */
        /* synthetic */ void m328x223708dd() {
            TencentTtsManager.this.mState = TtsState.IDLE;
            if (TencentTtsManager.this.mTtsListener != null) {
                TencentTtsManager.this.mTtsListener.onTtsStop();
            }
        }

        private void releaseAudioTrack() {
            AudioTrack audioTrack = this.mAudioTrack;
            if (audioTrack != null) {
                try {
                    audioTrack.stop();
                    this.mAudioTrack.release();
                } catch (Exception unused) {
                }
                this.mAudioTrack = null;
            }
            LogUtil.m335d("TencentTtsManager", "AudioTrack released.");
        }
    }
}
