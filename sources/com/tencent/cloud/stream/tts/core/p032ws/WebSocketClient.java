package com.tencent.cloud.stream.tts.core.p032ws;

import android.util.Log;
import com.tencent.cloud.stream.tts.BuildConfig;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* JADX INFO: loaded from: classes4.dex */
public class WebSocketClient {
    public static final String TAG = "WebSocketClient";
    private CountDownLatch connectLatch;
    private WebsocketConnection connection;
    private ConnectionListener listener;
    private OkHttpClient okHttpClient;
    private WebsocketProfile websocketProfile;

    public WebSocketClient(WebsocketProfile profile) {
        this.websocketProfile = profile;
        this.okHttpClient = new OkHttpClient.Builder().connectTimeout(profile.getConnectTimeout(), TimeUnit.MILLISECONDS).build();
    }

    public Connection connect(ConnectionProfile connectionProfile, final ConnectionListener listener) throws Exception {
        this.listener = listener;
        this.connectLatch = new CountDownLatch(1);
        Request.Builder builder = new Request.Builder();
        builder.url(connectionProfile.getUrl());
        if (connectionProfile.getToken() != null) {
            builder.header(Constant.HEADER_TOKEN, connectionProfile.getToken());
        }
        builder.removeHeader("User-Agent").addHeader("User-Agent", String.format("Android-sdk-%s", BuildConfig.SDK_VERSION));
        this.okHttpClient.newWebSocket(builder.build(), new WebSocketListener() { // from class: com.tencent.cloud.stream.tts.core.ws.WebSocketClient.1
            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                ConnectionListener connectionListener = listener;
                if (connectionListener != null) {
                    connectionListener.onClose(code, reason);
                }
                Log.d(WebSocketClient.TAG, "websocket onClosed: " + reason);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                ConnectionListener connectionListener = listener;
                if (connectionListener != null) {
                    connectionListener.onClose(code, reason);
                }
                Log.d(WebSocketClient.TAG, "websocket onClosing: " + reason);
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                if (response != null) {
                    response.close();
                }
                if (WebSocketClient.this.connectLatch != null) {
                    WebSocketClient.this.connectLatch.countDown();
                }
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                ConnectionListener connectionListener = listener;
                if (connectionListener != null) {
                    connectionListener.onMessage(text);
                }
                Log.i(WebSocketClient.TAG, "websocket onMessage text: " + text);
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                if (listener != null) {
                    listener.onMessage(ByteBuffer.wrap(bytes.toByteArray()));
                }
                Log.d(WebSocketClient.TAG, "websocket onMessage bytes: " + bytes.size());
            }

            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                WebSocketClient.this.connection = new WebsocketConnection(webSocket);
                if (WebSocketClient.this.connectLatch != null) {
                    WebSocketClient.this.connectLatch.countDown();
                }
                Log.d(WebSocketClient.TAG, "websocket onOpen: " + response.message());
            }
        });
        this.connectLatch.await(this.websocketProfile.getConnectTimeout(), TimeUnit.MILLISECONDS);
        return this.connection;
    }

    public void disconnect() {
        if (this.connection != null) {
            Log.d(TAG, "close the websocket connection from disconnect.");
            this.connection.close(1000, "close the websocket connection.");
            this.connection = null;
        }
        ConnectionListener connectionListener = this.listener;
        if (connectionListener != null) {
            connectionListener.onClose(1000, "close the websocket connection.");
        }
    }

    public void cancel() {
        if (this.connection != null) {
            Log.d(TAG, "close the websocket connection from cancel.");
            this.connection.close(1000, "close the websocket connection.");
            this.connection = null;
        }
        ConnectionListener connectionListener = this.listener;
        if (connectionListener != null) {
            connectionListener.onCancel();
        }
    }
}
