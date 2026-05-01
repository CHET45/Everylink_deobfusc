package com.tencent.cloud.stream.tts.core.p032ws;

import android.util.Log;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerException;
import com.tencent.cloud.stream.tts.core.exception.SynthesizerExceptionType;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechClient {
    public static final String TAG = "SpeechClient";
    public static int connectMaxTryTimes = 3;
    public static int maxFramePayloadLength = 3276800;
    WebSocketClient client;

    public SpeechClient() {
        this(5000);
    }

    public SpeechClient(int connectTimeout) {
        WebsocketProfile websocketProfileDefaultWebsocketProfile = WebsocketProfile.defaultWebsocketProfile();
        websocketProfileDefaultWebsocketProfile.setConnectTimeout(connectTimeout);
        this.client = new WebSocketClient(websocketProfileDefaultWebsocketProfile);
    }

    public Connection connect(ConnectionProfile connectionProfile, ConnectionListener listener) throws Exception {
        for (int i = 0; i <= connectMaxTryTimes; i++) {
            try {
                return this.client.connect(connectionProfile, listener);
            } catch (Exception e) {
                if (i == 2) {
                    String str = String.format("failed to connect to server after %d tries,error msg is :%s", Integer.valueOf(i), e.getMessage());
                    Log.e(TAG, str);
                    throw new SynthesizerException(SynthesizerExceptionType.CONNECT_SERVER_FAIL, str);
                }
                Thread.sleep(100L);
                Log.w(TAG, String.format("failed to connect to server the %d time:%s ,try again ", Integer.valueOf(i), e.getMessage()));
            }
        }
        return null;
    }

    public void disconnect() {
        this.client.disconnect();
    }

    public void cancel() {
        this.client.cancel();
    }
}
