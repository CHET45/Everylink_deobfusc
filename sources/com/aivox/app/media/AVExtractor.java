package com.aivox.app.media;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import com.aivox.base.util.BaseAppUtils;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class AVExtractor {
    private MediaExtractor mExtractor;
    private final String TAG = "AVExtractor";
    private int mAudioTrack = -1;
    private int mVideoTrack = -1;
    private long mCurSampleTime = 0;
    private int mCurSampleFlag = 0;
    private long mStartPos = 0;

    public AVExtractor(String str) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        this.mExtractor = mediaExtractor;
        try {
            mediaExtractor.setDataSource(str);
        } catch (IOException e) {
            Log.e("AVExtractor", "AVExtractor setDataSource error: " + e.getMessage());
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public MediaFormat getVideoFormat() {
        Log.d("AVExtractor", "getVideoFormat TrackCount: " + this.mExtractor.getTrackCount());
        int i = 0;
        while (true) {
            if (i >= this.mExtractor.getTrackCount()) {
                break;
            }
            if (this.mExtractor.getTrackFormat(i).getString("mime").startsWith("video/")) {
                this.mVideoTrack = i;
                break;
            }
            i++;
        }
        int i2 = this.mVideoTrack;
        if (i2 >= 0) {
            return this.mExtractor.getTrackFormat(i2);
        }
        return null;
    }

    public MediaFormat getAudioFormat() {
        Log.d("AVExtractor", "getAudioFormat TrackCount: " + this.mExtractor.getTrackCount());
        int i = 0;
        while (true) {
            if (i >= this.mExtractor.getTrackCount()) {
                break;
            }
            if (this.mExtractor.getTrackFormat(i).getString("mime").startsWith("audio/")) {
                this.mAudioTrack = i;
                break;
            }
            i++;
        }
        int i2 = this.mAudioTrack;
        if (i2 >= 0) {
            return this.mExtractor.getTrackFormat(i2);
        }
        return null;
    }

    int readBuffer(ByteBuffer byteBuffer) {
        byteBuffer.clear();
        selectSourceTrack();
        int sampleData = this.mExtractor.readSampleData(byteBuffer, 0);
        if (sampleData < 0) {
            return -1;
        }
        this.mCurSampleTime = this.mExtractor.getSampleTime();
        this.mCurSampleFlag = this.mExtractor.getSampleFlags();
        this.mExtractor.advance();
        return sampleData;
    }

    private void selectSourceTrack() {
        Log.d("AVExtractor", "selectSourceTrack mVideoTrack : " + this.mVideoTrack + " mAudioTrack : " + this.mAudioTrack);
        int i = this.mVideoTrack;
        if (i >= 0) {
            this.mExtractor.selectTrack(i);
            return;
        }
        int i2 = this.mAudioTrack;
        if (i2 >= 0) {
            this.mExtractor.selectTrack(i2);
        }
    }

    public long seek(long j) {
        this.mExtractor.seekTo(j, 0);
        return this.mExtractor.getSampleTime();
    }

    public void stop() {
        this.mExtractor.release();
        this.mExtractor = null;
    }

    public int getVideoTrack() {
        return this.mVideoTrack;
    }

    public int getAudioTrack() {
        return this.mAudioTrack;
    }

    public void setStartPos(long j) {
        this.mStartPos = j;
    }

    public long getCurrentTimestamp() {
        return this.mCurSampleTime;
    }

    public long getSampleFlag() {
        return this.mCurSampleFlag;
    }
}
