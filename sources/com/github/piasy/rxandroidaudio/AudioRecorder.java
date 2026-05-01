package com.github.piasy.rxandroidaudio;

import android.media.MediaRecorder;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes3.dex */
public final class AudioRecorder {
    public static final int DEFAULT_BIT_RATE = 44100;
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    public static final int ERROR_INTERNAL = 2;
    public static final int ERROR_NOT_PREPARED = 3;
    public static final int ERROR_SDCARD_ACCESS = 1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARED = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STOP_AUDIO_RECORD_DELAY_MILLIS = 300;
    private static final String TAG = "AudioRecorder";
    private OnErrorListener mOnErrorListener;
    private MediaRecorder mRecorder;
    private long mSampleStart;
    private boolean mStarted;
    private int mState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public interface OnErrorListener {
        void onError(int i);
    }

    private AudioRecorder() {
        this.mState = 0;
        this.mSampleStart = 0L;
        this.mStarted = false;
    }

    public static AudioRecorder getInstance() {
        return RxAndroidAudioHolder.INSTANCE;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public synchronized int getMaxAmplitude() {
        if (this.mState != 2) {
            return 0;
        }
        return this.mRecorder.getMaxAmplitude();
    }

    public int progress() {
        if (this.mState == 2) {
            return (int) ((System.currentTimeMillis() - this.mSampleStart) / 1000);
        }
        return 0;
    }

    public synchronized boolean startRecord(int i, int i2, int i3, int i4, int i5, File file) {
        stopRecord();
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mRecorder = mediaRecorder;
        mediaRecorder.setAudioSource(i);
        this.mRecorder.setOutputFormat(i2);
        this.mRecorder.setAudioSamplingRate(i4);
        this.mRecorder.setAudioEncodingBitRate(i5);
        this.mRecorder.setAudioEncoder(i3);
        this.mRecorder.setOutputFile(file.getAbsolutePath());
        try {
            this.mRecorder.prepare();
            try {
                this.mRecorder.start();
                this.mStarted = true;
                this.mSampleStart = System.currentTimeMillis();
                this.mState = 2;
                return true;
            } catch (RuntimeException e) {
                Log.w(TAG, "startRecord fail, start fail: " + e.getMessage());
                setError(2);
                this.mRecorder.reset();
                this.mRecorder.release();
                this.mRecorder = null;
                this.mStarted = false;
                return false;
            }
        } catch (IOException | RuntimeException e2) {
            Log.w(TAG, "startRecord fail, prepare fail: " + e2.getMessage());
            setError(2);
            this.mRecorder.reset();
            this.mRecorder.release();
            this.mRecorder = null;
            return false;
        }
    }

    public synchronized boolean prepareRecord(int i, int i2, int i3, File file) {
        return prepareRecord(i, i2, i3, 44100, 44100, file);
    }

    public synchronized boolean prepareRecord(int i, int i2, int i3, int i4, int i5, File file) {
        stopRecord();
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mRecorder = mediaRecorder;
        mediaRecorder.setAudioSource(i);
        this.mRecorder.setOutputFormat(i2);
        this.mRecorder.setAudioSamplingRate(i4);
        this.mRecorder.setAudioEncodingBitRate(i5);
        this.mRecorder.setAudioEncoder(i3);
        this.mRecorder.setOutputFile(file.getAbsolutePath());
        try {
            this.mRecorder.prepare();
            this.mState = 1;
        } catch (IOException e) {
            Log.w(TAG, "startRecord fail, prepare fail: " + e.getMessage());
            setError(2);
            this.mRecorder.reset();
            this.mRecorder.release();
            this.mRecorder = null;
            return false;
        }
        return true;
    }

    public synchronized boolean startRecord() {
        MediaRecorder mediaRecorder = this.mRecorder;
        if (mediaRecorder == null || this.mState != 1) {
            setError(3);
            return false;
        }
        try {
            mediaRecorder.start();
            this.mStarted = true;
            this.mSampleStart = System.currentTimeMillis();
            this.mState = 2;
            return true;
        } catch (RuntimeException e) {
            Log.w(TAG, "startRecord fail, start fail: " + e.getMessage());
            setError(2);
            this.mRecorder.reset();
            this.mRecorder.release();
            this.mRecorder = null;
            this.mStarted = false;
            return false;
        }
    }

    public synchronized int stopRecord() {
        int iCurrentTimeMillis = -1;
        if (this.mRecorder == null) {
            this.mState = 0;
            return -1;
        }
        if (this.mState == 2) {
            try {
                Thread.sleep(300L);
                this.mRecorder.stop();
                this.mStarted = false;
                iCurrentTimeMillis = (int) ((System.currentTimeMillis() - this.mSampleStart) / 1000);
            } catch (InterruptedException e) {
                Log.w(TAG, "stopRecord fail, stop fail(InterruptedException): " + e.getMessage());
            } catch (RuntimeException e2) {
                Log.w(TAG, "stopRecord fail, stop fail(no audio data recorded): " + e2.getMessage());
            }
            this.mRecorder.reset();
            this.mRecorder.release();
            this.mRecorder = null;
            this.mState = 0;
            return iCurrentTimeMillis;
        }
        try {
            this.mRecorder.reset();
        } catch (RuntimeException e3) {
            Log.w(TAG, "stopRecord fail, reset fail " + e3.getMessage());
        }
        this.mRecorder.release();
        this.mRecorder = null;
        this.mState = 0;
        return iCurrentTimeMillis;
    }

    private void setError(int i) {
        OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(i);
        }
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    private static class RxAndroidAudioHolder {
        private static final AudioRecorder INSTANCE = new AudioRecorder();

        private RxAndroidAudioHolder() {
        }
    }
}
