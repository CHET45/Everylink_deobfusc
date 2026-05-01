package com.tencent.cloud.stream.tts.core.p032ws;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public interface ConnectionListener {
    void onCancel();

    void onClose(int closeCode, String reason);

    void onMessage(String message);

    void onMessage(ByteBuffer message);

    void onOpen();
}
