package com.github.piasy.rxandroidaudio;

import android.media.AudioTrack;
import android.util.Log;

/* JADX INFO: loaded from: classes3.dex */
public final class StreamAudioPlayer {
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final String TAG = "StreamAudioPlayer";
    private AudioTrack mAudioTrack;

    private StreamAudioPlayer() {
    }

    private static final class StreamAudioPlayerHolder {
        private static final StreamAudioPlayer INSTANCE = new StreamAudioPlayer();

        private StreamAudioPlayerHolder() {
        }
    }

    public static StreamAudioPlayer getInstance() {
        return StreamAudioPlayerHolder.INSTANCE;
    }

    public synchronized void init() {
        init(44100, 4, 2, 2048);
    }

    public synchronized void init(int i, int i2, int i3, int i4) {
        AudioTrack audioTrack = this.mAudioTrack;
        if (audioTrack != null) {
            audioTrack.release();
            this.mAudioTrack = null;
        }
        AudioTrack audioTrack2 = new AudioTrack(3, i, i2, i3, Math.max(AudioTrack.getMinBufferSize(i, i2, i3), i4), 1);
        this.mAudioTrack = audioTrack2;
        audioTrack2.play();
    }

    public synchronized boolean play(byte[] bArr, int i) {
        AudioTrack audioTrack = this.mAudioTrack;
        if (audioTrack != null) {
            try {
                int iWrite = audioTrack.write(bArr, 0, i);
                if (iWrite == -6) {
                    Log.w(TAG, "play fail: ERROR_DEAD_OBJECT");
                    return false;
                }
                if (iWrite == -3) {
                    Log.w(TAG, "play fail: ERROR_INVALID_OPERATION");
                    return false;
                }
                if (iWrite != -2) {
                    return true;
                }
                Log.w(TAG, "play fail: ERROR_BAD_VALUE");
                return false;
            } catch (IllegalStateException e) {
                Log.w(TAG, "play fail: " + e.getMessage());
                return false;
            }
        }
        Log.w(TAG, "play fail: null mAudioTrack");
        return false;
    }

    public synchronized void release() {
        AudioTrack audioTrack = this.mAudioTrack;
        if (audioTrack != null) {
            audioTrack.release();
            this.mAudioTrack = null;
        }
    }
}
