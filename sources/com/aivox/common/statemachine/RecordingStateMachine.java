package com.aivox.common.statemachine;

import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.base.statemachine.BaseIStateMachine;
import com.aivox.base.util.LogUtil;
import com.microsoft.azure.storage.table.TableConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RecordingStateMachine extends AbstractStateMachine {
    private static final String _TAG = "recording";
    private static RecordingStateMachine mInstance;
    public WebSocketCallBack webSocketCallBack;

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventBeforeStateGo(String str, JSONObject jSONObject, int i) {
        return null;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine
    protected String TAG() {
        return _TAG;
    }

    public static RecordingStateMachine get() {
        if (mInstance == null) {
            synchronized (RecordingStateMachine.class) {
                if (mInstance == null) {
                    mInstance = new RecordingStateMachine(null);
                }
            }
        }
        return mInstance;
    }

    RecordingStateMachine(BaseIStateMachine baseIStateMachine) {
        super(baseIStateMachine);
    }

    public WebSocketCallBack getWebSocketCallBack() {
        return this.webSocketCallBack;
    }

    public void setWebSocketCallBack(WebSocketCallBack webSocketCallBack) {
        this.webSocketCallBack = webSocketCallBack;
    }

    public enum RecordingStateCode implements BaseIStateCode {
        START(999, "状态机启动"),
        END(9999, "状态机结束"),
        IDLE(0, "未录音"),
        RECORD_ING(6, "录音中"),
        RECORD_PAUSED(8, "录音暂停中"),
        RECORD_RESUME_ING(9, "准备继续录音中"),
        RECORD_STOP_ING(10, "结束录音中"),
        RECORD_SAVE_ING(11, "保存录音中");

        private Integer code;
        private String msg;

        RecordingStateCode(Integer num, String str) {
            this.code = num;
            this.msg = str;
        }

        @Override // com.aivox.base.statemachine.BaseIStateCode
        public Integer getCode() {
            return this.code;
        }

        @Override // com.aivox.base.statemachine.BaseIStateCode
        public void setCode(Integer num) {
            this.code = num;
        }

        @Override // com.aivox.base.statemachine.BaseIStateCode
        public String getMsg() {
            return this.msg;
        }

        @Override // com.aivox.base.statemachine.BaseIStateCode
        public void setMsg(String str) {
            this.msg = str;
        }
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void InitializeTable() {
        LogUtil.m339i(TAG(), "--Recording statemachine 初始化--");
        this.stageNow = RecordingStateCode.START;
        registerStateTable(Collections.singleton("START"), RecordingStateCode.START, RecordingStateCode.IDLE);
        registerStateTable(Collections.singleton("START"), RecordingStateCode.IDLE, RecordingStateCode.IDLE);
        for (RecordingStateCode recordingStateCode : RecordingStateCode.values()) {
            registerStateTable(Arrays.asList("ACK_ERROR", "ACK_TIMEOUT", "STATEGO_ERROR"), recordingStateCode, RecordingStateCode.IDLE);
            registerStateTable(Collections.singleton("PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT"), recordingStateCode, RecordingStateCode.IDLE);
        }
        registerStateTable(Collections.singleton("ACK_PHONE_RECORD_START"), RecordingStateCode.IDLE, RecordingStateCode.RECORD_ING);
        registerStateTable(Collections.singleton("RECORD_STOP_ING"), RecordingStateCode.RECORD_ING, RecordingStateCode.RECORD_STOP_ING);
        registerStateTable(Collections.singleton("ACK_PHONE_RECORD_PAUSE"), RecordingStateCode.RECORD_ING, RecordingStateCode.RECORD_PAUSED);
        registerStateTable(Collections.singleton("ACK_PHONE_RECORD_PAUSE"), RecordingStateCode.RECORD_PAUSED, RecordingStateCode.RECORD_PAUSED);
        registerStateTable(Collections.singletonList("RECORD_RESUME_ING"), RecordingStateCode.RECORD_PAUSED, RecordingStateCode.RECORD_RESUME_ING);
        registerStateTable(Collections.singletonList("ACK_PHONE_RECORD_RESUME"), RecordingStateCode.RECORD_RESUME_ING, RecordingStateCode.RECORD_ING);
        registerStateTable(Collections.singletonList("RECORD_STOP_ING"), RecordingStateCode.RECORD_PAUSED, RecordingStateCode.RECORD_STOP_ING);
        registerStateTable(Collections.singletonList("ACK_PHONE_RECORD_STOP"), RecordingStateCode.RECORD_STOP_ING, RecordingStateCode.RECORD_SAVE_ING);
        registerStateTable(Collections.singletonList("RECORD_STOP_ING"), RecordingStateCode.RECORD_STOP_ING, RecordingStateCode.IDLE);
        registerStateTable(Collections.singletonList("RECORD_STOP_ING"), RecordingStateCode.RECORD_SAVE_ING, RecordingStateCode.IDLE);
        registerStateTable(Collections.singletonList("PULL_OSS_UPLOAD_PROGRESS"), RecordingStateCode.RECORD_SAVE_ING, RecordingStateCode.IDLE);
        registerStateTable(Collections.singletonList("PULL_POINT_LESS"), RecordingStateCode.RECORD_ING, RecordingStateCode.RECORD_SAVE_ING);
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void enter() {
        stateGo(RecordingStateCode.IDLE, "START");
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void exit() {
        this.timeLast = this.timeNow;
        this.timeNow = System.currentTimeMillis();
        long j = this.timeNow;
        long j2 = this.timeLast;
        LogUtil.m339i(TAG(), "exit---" + toString("exit_arrow", RecordingStateCode.IDLE));
        this.stageLast = this.stageNow;
        this.stageNow = RecordingStateCode.IDLE;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public BaseIStateCode[] getAllStateCodes() {
        return RecordingStateCode.values();
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventAfterStateGo(String str, JSONObject jSONObject, int i) {
        str.hashCode();
        switch (str) {
            case "STATEGO_ERROR":
            case "ACK_TIMEOUT":
            case "ACK_ERROR":
                StateEvent.getIns().reconnect();
                break;
            case "PULL_POINT_LESS":
                StateEvent.getIns().parsePointLess(jSONObject.optInt(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE));
                break;
            case "PULL_TRANSCRIBE_INFO":
                if (jSONObject != null) {
                    StateEvent.getIns().parseTranscribe(jSONObject, getWebSocketCallBack());
                    break;
                }
                break;
            case "PULL_STORAGE_LESS":
                StateEvent.getIns().parseStorageLess();
                break;
            case "PULL_TRANSCRIBE_SERVICE_ARREARS":
                StateEvent.getIns().parseTranscribeServiceArrears();
                break;
            case "ACK_PHONE_RECORD_STOP":
                StateEvent.getIns().parsePhoneRecordStop(i == 1);
                break;
            case "PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT":
                StateEvent.getIns().parseCurrentAccountIsLoggedOut();
                break;
        }
        return null;
    }

    public void stateGo(RecordingStateCode recordingStateCode, String str) {
        stateGo(recordingStateCode, str, null, 1);
    }
}
