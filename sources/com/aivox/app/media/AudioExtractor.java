package com.aivox.app.media;

import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class AudioExtractor implements IExtractor {
    private AVExtractor mAudioExtractor;

    public AudioExtractor(String str) {
        this.mAudioExtractor = new AVExtractor(str);
    }

    @Override // com.aivox.app.media.IExtractor
    public MediaFormat getFormat() {
        return this.mAudioExtractor.getAudioFormat();
    }

    @Override // com.aivox.app.media.IExtractor
    public int readBuffer(ByteBuffer byteBuffer) {
        return this.mAudioExtractor.readBuffer(byteBuffer);
    }

    @Override // com.aivox.app.media.IExtractor
    public long getCurrentTimestamp() {
        return this.mAudioExtractor.getCurrentTimestamp();
    }

    @Override // com.aivox.app.media.IExtractor
    public long seek(long j) {
        return this.mAudioExtractor.seek(j);
    }

    @Override // com.aivox.app.media.IExtractor
    public void setStartPos(long j) {
        this.mAudioExtractor.setStartPos(j);
    }

    @Override // com.aivox.app.media.IExtractor
    public void stop() {
        this.mAudioExtractor.stop();
    }

    @Override // com.aivox.app.media.IExtractor
    public int getTrack() {
        return this.mAudioExtractor.getAudioTrack();
    }
}
