package com.aivox.app.media;

import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public interface IExtractor {
    long getCurrentTimestamp();

    MediaFormat getFormat();

    int getTrack();

    int readBuffer(ByteBuffer byteBuffer);

    long seek(long j);

    void setStartPos(long j);

    void stop();
}
