package com.aivox.common.statemachine;

import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.base.statemachine.BaseIStateMachine;
import com.aivox.base.util.LogUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SocketStateMachine extends AbstractStateMachine {
    private static final String _TAG = "websocket";
    private static SocketStateMachine mInstance;
    public WebSocketCallBack webSocketCallBack;

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventBeforeStateGo(String str, JSONObject jSONObject, int i) {
        return null;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine
    protected String TAG() {
        return _TAG;
    }

    public static SocketStateMachine get() {
        if (mInstance == null) {
            synchronized (SocketStateMachine.class) {
                if (mInstance == null) {
                    mInstance = new SocketStateMachine(null);
                }
            }
        }
        return mInstance;
    }

    SocketStateMachine(BaseIStateMachine baseIStateMachine) {
        super(baseIStateMachine);
    }

    public WebSocketCallBack getWebSocketCallBack() {
        return this.webSocketCallBack;
    }

    public void setWebSocketCallBack(WebSocketCallBack webSocketCallBack) {
        this.webSocketCallBack = webSocketCallBack;
    }

    public enum SocketStateCode implements BaseIStateCode {
        START(999, "Socket - 状态机启动"),
        END(9999, "Socket - 状态机结束"),
        IDLE(0, "Socket - 等待连接中"),
        CONNECT_AND_NOT_VERIFIED(1, "Socket - 已连接未校验"),
        CONNECT_AND_VERIFING(2, "Socket - 身份校验中"),
        CONNECT_AND_VERIFIED(3, "Socket - 身份已校验");

        private Integer code;
        private String msg;

        SocketStateCode(Integer num, String str) {
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
        LogUtil.m339i(TAG(), "--Socket statemachine 初始化--");
        this.stageNow = SocketStateCode.START;
        registerStateTable(Collections.singleton("START"), SocketStateCode.START, SocketStateCode.IDLE);
        registerStateTable(Collections.singleton("START"), SocketStateCode.IDLE, SocketStateCode.IDLE);
        registerStateTable(Collections.singleton("CONNECTING"), SocketStateCode.START, SocketStateCode.CONNECT_AND_NOT_VERIFIED);
        registerStateTable(Collections.singleton("CONNECTING"), SocketStateCode.IDLE, SocketStateCode.CONNECT_AND_NOT_VERIFIED);
        for (SocketStateCode socketStateCode : SocketStateCode.values()) {
            registerStateTable(Arrays.asList("ACK_ERROR", "ACK_TIMEOUT", "DISCONNECT", "STATEGO_ERROR"), socketStateCode, SocketStateCode.IDLE);
            registerStateTable(Collections.singleton("PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT"), socketStateCode, SocketStateCode.IDLE);
        }
        registerStateTable(Collections.singleton("CONNECT_AND_VERIFING"), SocketStateCode.CONNECT_AND_NOT_VERIFIED, SocketStateCode.CONNECT_AND_VERIFING);
        registerStateTable(Collections.singleton("ACK_PHONE_VERIFIED"), SocketStateCode.CONNECT_AND_VERIFING, SocketStateCode.CONNECT_AND_VERIFIED);
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void enter() {
        stateGo(SocketStateCode.IDLE, "START");
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void exit() {
        this.timeLast = this.timeNow;
        this.timeNow = System.currentTimeMillis();
        long j = this.timeNow;
        long j2 = this.timeLast;
        LogUtil.m339i(TAG(), "exit---" + toString("exit_arrow", SocketStateCode.IDLE));
        this.stageLast = this.stageNow;
        this.stageNow = SocketStateCode.IDLE;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public BaseIStateCode[] getAllStateCodes() {
        return SocketStateCode.values();
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventAfterStateGo(String str, JSONObject jSONObject, int i) {
        str.hashCode();
        switch (str) {
            case "STATEGO_ERROR":
            case "ACK_TIMEOUT":
            case "ACK_ERROR":
            case "DISCONNECT":
                StateEvent.getIns().reconnect();
                break;
            case "ACK_PHONE_VERIFIED":
                StateEvent.getIns().parsePhoneVerified(i == 1);
                break;
        }
        return null;
    }

    public void stateGo(SocketStateCode socketStateCode, String str) {
        stateGo(socketStateCode, str, null, 1);
    }
}
