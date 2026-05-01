package com.aivox.common.listener;

/* JADX INFO: loaded from: classes.dex */
public interface AudioListener {
    public static final int STATE_BUFFERING = 1;
    public static final int STATE_COMPLETE = 4;
    public static final int STATE_ERROR = 7;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_PLAYING = 5;
    public static final int STATE_RESUME = 3;

    void audioInit();

    void audioPause();

    void audioPlay();

    void audioResume();

    void audioStop();
}
