package com.aivox.common.socket;

import android.content.Context;
import android.os.Build;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.C0874R;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.model.DataHandle;
import com.aivox.common.parse.ParseManagerForByte;
import com.aivox.common.statemachine.StateMachineManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.StringUtil;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketHandler extends WebSocketListener {
    public static final int ACK_TIMEOUT = 5000;
    public static final int ACK_TIMEOUT_FOR_AUDIO_SAVE = 15000;
    private static WebSocketHandler INST = null;
    private static final String TAG = "WebSocketHandler";
    private final OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
    private WebSocketCallBack mSocketIOCallBack;
    private ConnectStatus status;
    private WebSocket webSocket;
    private String wsUrl;

    private WebSocketHandler(String str) {
        LogUtil.m339i("websocket", "web_url:" + str);
        this.wsUrl = str;
    }

    public static WebSocketHandler getInstance() {
        if (INST == null) {
            synchronized (WebSocketHandler.class) {
                INST = new WebSocketHandler(StringUtil.getWebsocketUrl());
            }
        }
        return INST;
    }

    public static WebSocketHandler getInstance(String str) {
        if (INST == null) {
            synchronized (WebSocketHandler.class) {
                INST = new WebSocketHandler(str);
            }
        }
        return INST;
    }

    public ConnectStatus getStatus() {
        return this.status;
    }

    public void connect() {
        Request requestBuild = new Request.Builder().url(this.wsUrl).addHeader(SPUtil.TOKEN, (String) SPUtil.get(SPUtil.TOKEN, "")).addHeader("refresh-token", (String) SPUtil.get(SPUtil.REFRESH_TOKEN, "")).addHeader("MobileType", "1").addHeader("SysVersion", Build.VERSION.RELEASE).addHeader("AppVersion", BaseAppUtils.getVersionName()).addHeader("zoneId", DateUtil.getCurrentTimeZone()).addHeader(ExifInterface.TAG_MODEL, Build.BRAND).addHeader("Headset-Version", BaseStringUtil.isEmpty(DataHandle.getIns().getHeadsetVersion()) ? "" : DataHandle.getIns().getHeadsetVersion()).addHeader("Device", DataHandle.getIns().hasConnectedBluetooth(false) ? CommonServiceUtils.getInstance().getConnectedDeviceName() : "").build();
        LogUtil.m337e(TAG, requestBuild.headers().toString());
        this.webSocket = this.client.newWebSocket(requestBuild, this);
        this.status = ConnectStatus.Connecting;
        LogUtil.m339i("websocket", "websocket_connect");
    }

    public void reConnect() {
        WebSocket webSocket = this.webSocket;
        if (webSocket != null) {
            this.webSocket = this.client.newWebSocket(webSocket.request(), this);
        } else {
            LogUtil.m339i("websocket", "websocket_reconnect=null");
            connect();
        }
    }

    public void send(String str) {
        try {
            int iOptInt = new JSONObject(str).optInt("msgId");
            if (iOptInt != 20010 && iOptInt != 9) {
                LogUtil.m339i("websocket", "WebSocket_send： " + str);
            }
        } catch (JSONException e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m337e("websocket", "WebSocket_send： " + e.getLocalizedMessage());
        }
        WebSocket webSocket = this.webSocket;
        if (webSocket != null) {
            webSocket.send(str);
        } else {
            LogUtil.m339i("websocket", "websocket_send==null");
        }
    }

    public void send(byte[] bArr) {
        if (this.webSocket != null) {
            String strBytes2Hex = DataUtil.bytes2Hex(bArr);
            LogUtil.m339i("websocket", "WebSocket_准备发送数据：" + strBytes2Hex + ";" + strBytes2Hex.length());
            this.webSocket.send(ByteString.m1928of(bArr));
            return;
        }
        LogUtil.m339i("websocket", "websocket_sendByte=null");
    }

    public void send(String str, int i) {
        send(str);
    }

    public void send(Context context, byte[] bArr, int i) {
        send(bArr);
    }

    public void cancel() {
        LogUtil.m339i("websocket", "websocket_cancel");
        WebSocket webSocket = this.webSocket;
        if (webSocket != null) {
            webSocket.cancel();
        }
    }

    public void close() {
        LogUtil.m339i("websocket", "websocket_close");
        WebSocket webSocket = this.webSocket;
        if (webSocket != null) {
            webSocket.close(1000, null);
            INST = null;
            LogUtil.m338i("=websocket closed=");
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        LogUtil.m339i("websocket", "WebSocket_onOpen");
        this.status = ConnectStatus.Open;
        WebSocketCallBack webSocketCallBack = this.mSocketIOCallBack;
        if (webSocketCallBack != null) {
            webSocketCallBack.onOpen();
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, String str) {
        super.onMessage(webSocket, str);
        if (!str.contains("心跳包")) {
            LogUtil.m339i("websocket", "WebSocket_收到字符串数据: " + str);
        }
        if (!str.isEmpty() && String.valueOf(str.charAt(0)).equals("{") && String.valueOf(str.charAt(str.length() - 1)).equals(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX)) {
            StateMachineManager.get().parseWebsocketMessage(str, this.mSocketIOCallBack);
        } else {
            ParseManagerForByte.getInstance().parseWIFI(DataUtil.hexToByteArray(str));
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onMessage(WebSocket webSocket, ByteString byteString) {
        super.onMessage(webSocket, byteString);
        LogUtil.m339i("websocket", "WebSocket_收到字节数组: " + byteString + "\n" + byteString.hex());
        byte[] bArrHexToByteArray = DataUtil.hexToByteArray(byteString.hex());
        LogUtil.m339i("websocket", "result:" + DataUtil.getStrFromBytes(bArrHexToByteArray, bArrHexToByteArray.length, 0));
        ParseManagerForByte.getInstance().parseWIFI(byteString.toByteArray());
    }

    @Override // okhttp3.WebSocketListener
    public void onClosing(WebSocket webSocket, int i, String str) {
        super.onClosing(webSocket, i, str);
        this.status = ConnectStatus.Closing;
        LogUtil.m339i("websocket", "WebSocket_onClosing_" + i + PunctuationConst.UNDERLINE + str);
        WebSocketCallBack webSocketCallBack = this.mSocketIOCallBack;
        if (webSocketCallBack != null) {
            webSocketCallBack.onClosing();
        }
    }

    @Override // okhttp3.WebSocketListener
    public void onClosed(WebSocket webSocket, int i, String str) {
        super.onClosed(webSocket, i, str);
        this.status = ConnectStatus.Closed;
        LogUtil.m339i("websocket", "WebSocket_onClosed_" + i + PunctuationConst.UNDERLINE + str);
    }

    @Override // okhttp3.WebSocketListener
    public void onFailure(WebSocket webSocket, Throwable th, Response response) {
        super.onFailure(webSocket, th, response);
        this.status = ConnectStatus.Canceled;
        LogUtil.m339i("websocket", "WebSocket_onFailure: " + th);
        WebSocketCallBack webSocketCallBack = this.mSocketIOCallBack;
        if (webSocketCallBack != null) {
            webSocketCallBack.onConnectError(th);
        }
        if (response == null || response.code() != 403) {
            return;
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.socket.WebSocketHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WebSocketHandler.lambda$onFailure$0();
            }
        });
    }

    static /* synthetic */ void lambda$onFailure$0() {
        ToastUtil.showShort(Integer.valueOf(C0874R.string.socket_phone_verified_fail));
        AppUtils.logout(Utils.getApp());
    }

    public void setSocketIOCallBack(WebSocketCallBack webSocketCallBack) {
        this.mSocketIOCallBack = webSocketCallBack;
    }

    public void removeSocketIOCallBack() {
        this.mSocketIOCallBack = null;
    }
}
