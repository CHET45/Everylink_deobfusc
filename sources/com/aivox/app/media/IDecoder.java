package com.aivox.app.media;

import android.media.MediaFormat;

/* JADX INFO: loaded from: classes.dex */
public interface IDecoder extends Runnable {
    long getDuration();

    String getFilePath();

    int getHeight();

    MediaFormat getMediaFormat();

    int getRotationAngle();

    int getTrack();

    int getWidth();

    void goOn();

    Boolean isDecoding();

    Boolean isPause();

    Boolean isSeeking();

    Boolean isStop();

    void pause();

    void setStateListener();

    void stop();
}
