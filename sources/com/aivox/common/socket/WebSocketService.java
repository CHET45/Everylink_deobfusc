package com.aivox.common.socket;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import com.aivox.base.C0874R;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.parse.WifiSendManagerForJson;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.statemachine.SocketStateMachine;
import com.blankj.utilcode.util.NetworkUtils;
import com.microsoft.azure.storage.table.TableConstants;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int HANDLER_WS_RECONNECT = 3003;
    private static final int HEART_BEAT_RATE = 5000;
    private static WebSocketService service;
    boolean awaitingPong;
    int receivePongCount;
    int sentPingCount;
    int reconnectCount = 1;
    private long sendTime = 0;
    int failedPing = 0;
    private String channelId = "WebSocketService";
    boolean isThirdConnect = false;
    Handler mHandler = new Handler() { // from class: com.aivox.common.socket.WebSocketService.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 3003) {
                LogUtil.m338i("HANDLER_WS_RECONNECT:" + BaseAppUtils.isLogin() + "-" + NetworkUtils.isConnected() + "-" + (WebSocketHandler.getInstance().getStatus() != ConnectStatus.Open));
                if (BaseAppUtils.isLogin() && NetworkUtils.isConnected()) {
                    DataHandle.getIns().setDeviceOnlineOrOffline(0);
                    DataHandle.getIns().setDeviceInfo(null);
                    DataHandle.getIns().setDeviceRecordState(0);
                    if (BaseStringUtil.expString(2, Integer.valueOf(WebSocketService.this.reconnectCount)) <= 32) {
                        LogUtil.m339i("websocket", "第" + WebSocketService.this.reconnectCount + "次重连--->距上次：" + BaseStringUtil.expString(2, Integer.valueOf(WebSocketService.this.reconnectCount)) + "秒");
                        WebSocketService.this.connectWs(true);
                        WebSocketService.this.reconnectCount++;
                        return;
                    }
                    WebSocketService.this.reconnectCount = 1;
                    WebSocketService.this.closeWebSocket();
                    return;
                }
                return;
            }
            if (i != 5000) {
                if (i != 30006) {
                    return;
                }
                EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.PULL_TRANSCRIBE_INFO, (String) message.obj));
                return;
            }
            if (WebSocketHandler.getInstance().getStatus() == ConnectStatus.Open) {
                if (WebSocketService.this.awaitingPong) {
                    WebSocketService.this.failedPing++;
                } else {
                    WebSocketService.this.failedPing = 0;
                }
                LogUtil.m334d("== 发送心跳包 ==" + WebSocketService.this.failedPing + "-" + WebSocketService.this.sentPingCount + "-" + WebSocketService.this.receivePongCount);
                WebSocketService.this.sentPingCount++;
                WebSocketService.this.awaitingPong = true;
                if (WebSocketService.this.failedPing != 0) {
                    LogUtil.m336e("sent ping but didn't receive pong " + WebSocketService.this.failedPing + " times after " + (WebSocketService.this.sentPingCount - 1) + " successful ping/pongs)");
                }
                if (WebSocketService.this.failedPing == 5) {
                    EventBus.getDefault().post(new EventBean(59));
                }
                WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsPing(), 10);
                WebSocketService.this.mHandler.sendEmptyMessageDelayed(5000, 5000L);
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static WebSocketService getInstance() {
        if (service == null) {
            service = new WebSocketService();
        }
        return service;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtil.m339i("websocket", "WebSocketService---onCreate" + hashCode());
        EventBus.getDefault().register(this);
        service = this;
        initWebSocket();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtil.m336e("--onStartCommand--");
        if (intent != null) {
            intent.getIntExtra(TableConstants.ErrorConstants.ERROR_CODE, 0);
        }
        setServiceForeground(intent);
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        LogUtil.m339i("websocket", "WebSocketService---onDestroy");
        this.mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        closeWebSocket();
    }

    public void setServiceForeground(Intent intent) {
        NotificationChannel notificationChannel = new NotificationChannel(this.channelId, getString(C0874R.string.app_name), 3);
        Intent intent2 = new Intent(this, (Class<?>) WebSocketService.class);
        intent2.putExtra(TableConstants.ErrorConstants.ERROR_CODE, 123);
        PendingIntent service2 = PendingIntent.getService(this, 1001, intent2, 67108864);
        ((NotificationManager) getSystemService("notification")).createNotificationChannel(notificationChannel);
        startForeground(1, new NotificationCompat.Builder(this, this.channelId).setOngoing(true).setSmallIcon(C0874R.mipmap.icon_logo).setContentTitle(getString(C0874R.string.aivox_is_running_in_the_background)).setPriority(4).setCategory(NotificationCompat.CATEGORY_SERVICE).setChannelId(this.channelId).setContentIntent(service2).build());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBean) {
        int from = eventBean.getFrom();
        if (from == 46 || from == 59) {
            if (NetUtil.isConnected(service)) {
                LogUtil.m338i("发起重连。。。");
                this.mHandler.sendEmptyMessageDelayed(3003, BaseStringUtil.expString(2, Integer.valueOf(this.reconnectCount)) * 1000);
                return;
            }
            return;
        }
        if (from == 67) {
            this.receivePongCount++;
            this.awaitingPong = false;
        } else {
            if (from != 30005) {
                return;
            }
            this.reconnectCount = 1;
            this.mHandler.removeMessages(3003);
        }
    }

    public void initWebSocket() {
        WebSocketHandler.getInstance().setSocketIOCallBack(new WebSocketCallBack() { // from class: com.aivox.common.socket.WebSocketService.1
            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onMessage(int i) {
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onOpen() {
                WebSocketService.this.sentPingCount = 0;
                WebSocketService.this.receivePongCount = 0;
                WebSocketService.this.failedPing = 0;
                WebSocketService.this.awaitingPong = false;
                WebSocketService.this.mHandler.sendEmptyMessageDelayed(5000, 5000L);
                WebSocketService.this.requestFirstConnect();
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onMessage(int i, String str) {
                Message message = new Message();
                message.what = i;
                message.obj = str;
                WebSocketService.this.mHandler.sendMessageAtFrontOfQueue(message);
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onMessage(byte[] bArr) {
                LogUtil.m339i("websocket", "WebSocketService_onMessage:" + DataUtil.bytes2Hex(bArr));
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onMessage(String str) {
                LogUtil.m339i("websocket", "WebSocketService_onMessage_text:" + str);
                try {
                    if (new JSONObject(str).optInt(TableConstants.ErrorConstants.ERROR_CODE) == 406) {
                        WebSocketService.this.mHandler.sendEmptyMessage(406);
                    }
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                    LogUtil.m339i("websocket", "websocket_e:" + e.getLocalizedMessage());
                }
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onClosing() {
                if (BaseStringUtil.isNotEmpty((String) SPUtil.get(SPUtil.TOKEN, "")) && BaseAppUtils.isAppRunning(WebSocketService.service)) {
                    WebSocketService.this.stateGo();
                }
            }

            @Override // com.aivox.base.http.socket.WebSocketCallBack
            public void onConnectError(Throwable th) {
                LogUtil.m339i("websocket", "WebSocketService——onConnectError");
                EventBus.getDefault().post(new EventBean(73));
                if (!(th instanceof IOException)) {
                    BaseAppUtils.printErrorMsg(th, "Socket");
                }
                WebSocketService.this.stateGo();
            }
        });
        if (WebSocketHandler.getInstance().getStatus() != ConnectStatus.Open) {
            connectWs(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stateGo() {
        this.mHandler.removeMessages(5000);
        SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "DISCONNECT");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectWs(boolean z) {
        if (z) {
            WebSocketHandler.getInstance().reConnect();
        } else {
            WebSocketHandler.getInstance().connect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestFirstConnect() {
        SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.CONNECT_AND_NOT_VERIFIED, "CONNECTING");
        SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.CONNECT_AND_VERIFING, "CONNECT_AND_VERIFING");
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsPhoneVerified(), DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_VERIFIED);
    }

    private void requestDeviceInfo() {
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsRequestDeviceInfo(), DeviceProtocol.MSG_ID_WIFI_JSON.ACK_DEVICE_INFO);
    }

    public void closeWebSocket() {
        LogUtil.m339i("websocket", "WebSocketService___CloseWebSocket");
        if (WebSocketHandler.getInstance() == null || WebSocketHandler.getInstance().getStatus() != ConnectStatus.Open) {
            return;
        }
        SocketStateMachine.get().exit();
        RecordingStateMachine.get().exit();
        WebSocketHandler.getInstance().close();
        this.mHandler.removeMessages(3003);
    }

    public boolean isRecordViewShow() {
        return BaseAppUtils.isActivityInTheForeground(this, ARouterUtils.getClass(RecordAction.RECORD_ING).getName());
    }
}
