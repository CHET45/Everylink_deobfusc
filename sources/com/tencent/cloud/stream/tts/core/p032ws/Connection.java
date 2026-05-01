package com.tencent.cloud.stream.tts.core.p032ws;

/* JADX INFO: loaded from: classes4.dex */
public interface Connection {
    void close(int code, String msg);

    boolean isActive();

    void sendBinary(final byte[] data);

    void sendPing();

    void sendText(final String text);
}
