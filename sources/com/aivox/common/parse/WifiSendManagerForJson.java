package com.aivox.common.parse;

import com.aivox.base.common.Constant;
import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.alibaba.fastjson.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WifiSendManagerForJson {
    private static WifiSendManagerForJson sendManager;
    private int from;
    private String headsetRecording;
    private int recordType;

    /* JADX INFO: renamed from: to */
    private int f264to;
    private int transcribeType;

    public static WifiSendManagerForJson getInstance() {
        if (sendManager == null) {
            sendManager = new WifiSendManagerForJson();
        }
        return sendManager;
    }

    private JSONObject wsRequestHead(int i) {
        JSONObject jSONObject = new JSONObject();
        Long lValueOf = Long.valueOf(System.currentTimeMillis());
        SPUtil.put("time", lValueOf);
        jSONObject.put("time", (Object) lValueOf);
        jSONObject.put("msgId", (Object) Integer.valueOf(i));
        jSONObject.put("uid", SPUtil.get(SPUtil.USER_ID, "0"));
        if (DataHandle.getIns().getFileId() != 0) {
            jSONObject.put("audioId", (Object) Integer.valueOf(DataHandle.getIns().getFileId()));
            jSONObject.put("videoId", (Object) Integer.valueOf(DataHandle.getIns().getFileId()));
        }
        return jSONObject;
    }

    public String wsPing() {
        return wsRequestHead(9).toString();
    }

    public String wsPhoneVerified() {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_PHONE_VERIFIED);
        jSONObjectWsRequestHead.put("connectType", (Object) 1);
        jSONObjectWsRequestHead.put("isReconnect", (Object) 0);
        String str = "";
        jSONObjectWsRequestHead.put("sessionId", SPUtil.get(SPUtil.SESSION_ID, ""));
        jSONObjectWsRequestHead.put("recordType", (Object) Integer.valueOf(this.recordType));
        int i = this.transcribeType;
        if (i != 0) {
            jSONObjectWsRequestHead.put(Constant.TRANSCRIBE_TYPE, (Object) Integer.valueOf(i));
        }
        if (!BaseStringUtil.isEmpty(this.headsetRecording)) {
            jSONObjectWsRequestHead.put("headsetRecording", (Object) this.headsetRecording);
        }
        BaseIStateCode stageNow = RecordingStateMachine.get().getStageNow();
        LogUtil.m336e("身份校验时录音状态===" + stageNow);
        if (stageNow != RecordingStateMachine.RecordingStateCode.IDLE) {
            if (stageNow == RecordingStateMachine.RecordingStateCode.RECORD_ING) {
                str = "a5";
            } else if (stageNow == RecordingStateMachine.RecordingStateCode.RECORD_PAUSED) {
                str = "a7";
            } else if (stageNow == RecordingStateMachine.RecordingStateCode.RECORD_STOP_ING) {
                str = "a9";
            }
            jSONObjectWsRequestHead.put("appStatusCode", (Object) str);
            jSONObjectWsRequestHead.put("fileId", (Object) Integer.valueOf(DataHandle.getIns().getFileId()));
        }
        return jSONObjectWsRequestHead.toString();
    }

    public void setBaseData(int i, int i2, String str) {
        this.transcribeType = i;
        this.recordType = i2;
        this.headsetRecording = str;
    }

    public String wsCmdRecordStart(int i, int i2, int i3, int i4, String str) {
        this.from = i2;
        this.f264to = i3;
        this.recordType = i4;
        this.headsetRecording = str;
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_PHONE_RECORD_START);
        jSONObjectWsRequestHead.put(Constant.TRANSCRIBE_TYPE, (Object) Integer.valueOf(i == 107 ? 104 : i));
        jSONObjectWsRequestHead.put("isNew", (Object) 1);
        jSONObjectWsRequestHead.put("from", (Object) Integer.valueOf(i2));
        if (i == 2) {
            jSONObjectWsRequestHead.put("to", (Object) Integer.valueOf(i3));
        }
        jSONObjectWsRequestHead.put("recordType", (Object) Integer.valueOf(i4));
        jSONObjectWsRequestHead.put("speaker", (Object) Integer.valueOf(i4 == 1 ? 0 : -1));
        if (!BaseStringUtil.isEmpty(str)) {
            jSONObjectWsRequestHead.put("headsetRecording", (Object) str);
        }
        return jSONObjectWsRequestHead.toString();
    }

    public String wsCmdInsertImg(String str, String str2, int i) {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_INSERT_IMG);
        jSONObjectWsRequestHead.put("imageUrl", (Object) str);
        jSONObjectWsRequestHead.put("beginAt", (Object) str2);
        jSONObjectWsRequestHead.put("imageSize", (Object) Integer.valueOf(i));
        return jSONObjectWsRequestHead.toString();
    }

    public String wsCmdInsertMark(String str, String str2) {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_INSERT_MARK);
        jSONObjectWsRequestHead.put("content", (Object) str);
        jSONObjectWsRequestHead.put("beginAt", (Object) str2);
        return jSONObjectWsRequestHead.toString();
    }

    public String wsCmdLimit() {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_CHECK_LIMIT);
        jSONObjectWsRequestHead.put("from", (Object) Integer.valueOf(this.from));
        if (this.transcribeType == 104) {
            jSONObjectWsRequestHead.put("to", (Object) Integer.valueOf(this.f264to));
        }
        return jSONObjectWsRequestHead.toString();
    }

    public String wsStartRecording() {
        return wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_START_RECORDING).toString();
    }

    public String wsStopRecording() {
        return wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_STOP_RECORDING).toString();
    }

    public String wsCmdRemoveLimit() {
        return wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_REMOVE_LIMIT).toString();
    }

    public String wsCmdPushText(String str, String str2, String str3, int i, String str4, int i2, int i3) {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_PUSH_TEXT);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("beginAt", (Object) str);
        jSONObject.put("endAt", (Object) str2);
        jSONObject.put("onebest", (Object) str3);
        jSONObject.put(Constant.KEY_INDEX, (Object) Integer.valueOf(i));
        jSONObject.put("translate", (Object) str4);
        jSONObject.put("toLang", (Object) Integer.valueOf(i2));
        if (i3 != -1) {
            jSONObject.put("speaker", (Object) Integer.valueOf(i3));
        }
        if (i3 != -1) {
            jSONObject.put("speakerName", (Object) (i3 == 0 ? "我" : "他"));
        }
        jSONObjectWsRequestHead.put("videoTrans", (Object) jSONObject);
        return jSONObjectWsRequestHead.toString();
    }

    public String wsRecordStop() {
        JSONObject jSONObjectWsRequestHead = wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_DEVICE_RECORD_STOP);
        jSONObjectWsRequestHead.put("fileId", (Object) 0);
        jSONObjectWsRequestHead.put("fileName", (Object) 0);
        return jSONObjectWsRequestHead.toString();
    }

    public String wsRequestDeviceInfo() {
        return wsRequestHead(DeviceProtocol.MSG_ID_WIFI_JSON.CMD_DEVICE_INFO).toString();
    }
}
