package com.tencent.cloud.stream.tts.core.p032ws;

/* JADX INFO: loaded from: classes4.dex */
public class WebsocketProfile {
    private int connectTimeout;
    private int eventGroupThreadNum;
    private int handshakeTimeout;
    private boolean isCompression;

    public int getHandshakeTimeout() {
        return this.handshakeTimeout;
    }

    public void setHandshakeTimeout(int handshakeTimeout) {
        this.handshakeTimeout = handshakeTimeout;
    }

    public boolean isCompression() {
        return this.isCompression;
    }

    public void setCompression(boolean compression) {
        this.isCompression = compression;
    }

    public int getEventGroupThreadNum() {
        return this.eventGroupThreadNum;
    }

    public void setEventGroupThreadNum(int eventGroupThreadNum) {
        this.eventGroupThreadNum = eventGroupThreadNum;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public static WebsocketProfile defaultWebsocketProfile() {
        WebsocketProfile websocketProfile = new WebsocketProfile();
        websocketProfile.setCompression(false);
        websocketProfile.setHandshakeTimeout(5000);
        websocketProfile.setEventGroupThreadNum(0);
        websocketProfile.setConnectTimeout(5000);
        return websocketProfile;
    }
}
