package com.aivox.app.media;

import android.media.MediaFormat;
import android.util.Log;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class VideoExtractor implements IExtractor {
    private final String TAG = "VideoExtractor";
    private AVExtractor mVideoExtractor;

    public VideoExtractor(String str) {
        Log.d("VideoExtractor", "VideoExtractor: ");
        this.mVideoExtractor = new AVExtractor(str);
    }

    @Override // com.aivox.app.media.IExtractor
    public MediaFormat getFormat() {
        Log.d("VideoExtractor", "getFormat: ");
        return this.mVideoExtractor.getVideoFormat();
    }

    @Override // com.aivox.app.media.IExtractor
    public int readBuffer(ByteBuffer byteBuffer) {
        Log.d("VideoExtractor", "readBuffer: ");
        return this.mVideoExtractor.readBuffer(byteBuffer);
    }

    @Override // com.aivox.app.media.IExtractor
    public long getCurrentTimestamp() {
        return this.mVideoExtractor.getCurrentTimestamp();
    }

    @Override // com.aivox.app.media.IExtractor
    public long seek(long j) {
        return this.mVideoExtractor.seek(j);
    }

    @Override // com.aivox.app.media.IExtractor
    public void setStartPos(long j) {
        this.mVideoExtractor.setStartPos(j);
    }

    @Override // com.aivox.app.media.IExtractor
    public void stop() {
        this.mVideoExtractor.stop();
    }

    @Override // com.aivox.app.media.IExtractor
    public int getTrack() {
        return this.mVideoExtractor.getVideoTrack();
    }
}
