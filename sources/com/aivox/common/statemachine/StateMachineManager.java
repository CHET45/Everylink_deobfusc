package com.aivox.common.statemachine;

import androidx.core.app.NotificationCompat;
import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.EventBean;
import com.aivox.common.parse.WifiSendManagerForJson;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.statemachine.SocketStateMachine;
import com.microsoft.azure.storage.table.TableConstants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class StateMachineManager {
    public static StateMachineManager ins;

    private void sendSaveDelay(int i, long j, long j2, long j3) {
    }

    public static StateMachineManager get() {
        if (ins == null) {
            synchronized (RecordingStateMachine.class) {
                if (ins == null) {
                    ins = new StateMachineManager();
                }
            }
        }
        return ins;
    }

    public void parseWebsocketMessage(String str, WebSocketCallBack webSocketCallBack) {
        SocketStateMachine.get().setWebSocketCallBack(webSocketCallBack);
        RecordingStateMachine.get().setWebSocketCallBack(webSocketCallBack);
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject("data");
            int iOptInt = jSONObjectOptJSONObject.optInt("msgId");
            int iOptInt2 = jSONObjectOptJSONObject.optInt(NotificationCompat.CATEGORY_STATUS, 1);
            if (iOptInt == 40015) {
                WebSocketHandler.getInstance().cancel();
            } else {
                saveDelay(jSONObjectOptJSONObject);
                stateGoByMsgId(iOptInt, jSONObjectOptJSONObject, iOptInt2);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m336e("parseWifi:" + e.getLocalizedMessage());
            SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "ACK_ERROR");
        }
    }

    private void saveDelay(JSONObject jSONObject) {
        long jLongValue = ((Long) SPUtil.get("time", 0L)).longValue();
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jOptLong = jSONObject.optLong("time");
        int iOptInt = jSONObject.optInt("delayId", 0);
        if (iOptInt != 0) {
            sendSaveDelay(iOptInt, jCurrentTimeMillis, jCurrentTimeMillis - jOptLong, jCurrentTimeMillis - jLongValue);
        }
    }

    private void stateGoByMsgId(int i, JSONObject jSONObject, int i2) {
        if (i2 == 0) {
            SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "ACK_ERROR", jSONObject, i2);
            return;
        }
        if (i == 10) {
            LogUtil.m334d("== 收到心跳包 ==");
            EventBus.getDefault().post(new EventBean(67));
            return;
        }
        if (i == 30009) {
            StateEvent.getIns().parseLimitAndKey(jSONObject, i2);
            return;
        }
        if (i == 40002) {
            if (jSONObject.optInt(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE) != 4 || RecordingStateMachine.get().getStageNow() != RecordingStateMachine.RecordingStateCode.RECORD_ING) {
                RecordingStateMachine.get().executionEventAfterStateGo("PULL_POINT_LESS", jSONObject, i2);
                return;
            } else {
                RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_SAVE_ING, "PULL_POINT_LESS", jSONObject, i2);
                return;
            }
        }
        if (i == 40008) {
            RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT", jSONObject, i2);
            return;
        }
        if (i == 30005) {
            SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.CONNECT_AND_VERIFIED, "ACK_PHONE_VERIFIED", jSONObject, i2);
            return;
        }
        if (i == 30006) {
            RecordingStateMachine.get().executionEventAfterStateGo("PULL_TRANSCRIBE_INFO", jSONObject, i2);
            return;
        }
        if (i == 30021) {
            SPUtil.put(SPUtil.SEND_RECORD_DURATION, true);
            if (!((Boolean) SPUtil.get(SPUtil.IS_TRANS_TIME_OUT, true)).booleanValue()) {
                WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdLimit());
            } else {
                EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_CHECK_LIMIT, i2));
            }
            StateEvent.getIns().parsePhoneRecordStart(i2 == 1, jSONObject);
            return;
        }
        if (i == 30022 && SocketStateMachine.get().getStageNow() == SocketStateMachine.SocketStateCode.CONNECT_AND_VERIFING) {
            SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.CONNECT_AND_VERIFIED, "ACK_PHONE_VERIFIED", jSONObject, i2);
            if (!((Boolean) SPUtil.get(SPUtil.IS_TRANS_TIME_OUT, true)).booleanValue()) {
                WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdLimit());
            } else {
                EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_CHECK_LIMIT, i2));
            }
        }
    }
}
