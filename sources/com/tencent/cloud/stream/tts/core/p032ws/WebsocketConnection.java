package com.tencent.cloud.stream.tts.core.p032ws;

import okhttp3.WebSocket;

/* JADX INFO: loaded from: classes4.dex */
public class WebsocketConnection implements Connection {
    WebSocket socket;

    @Override // com.tencent.cloud.stream.tts.core.p032ws.Connection
    public void sendBinary(byte[] data) {
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.Connection
    public void sendPing() {
    }

    public WebsocketConnection(WebSocket socket) {
        this.socket = socket;
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.Connection
    public boolean isActive() {
        return this.socket != null;
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.Connection
    public void close(int code, String msg) {
        WebSocket webSocket = this.socket;
        if (webSocket != null) {
            webSocket.close(code, msg);
            this.socket.cancel();
            this.socket = null;
        }
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.Connection
    public void sendText(final String text) {
        WebSocket webSocket = this.socket;
        if (webSocket != null) {
            webSocket.send(text);
        }
    }
}
