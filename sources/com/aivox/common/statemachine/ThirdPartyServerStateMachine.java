package com.aivox.common.statemachine;

import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.base.statemachine.BaseIStateMachine;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.azure.core.implementation.logging.DefaultLogger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ThirdPartyServerStateMachine extends AbstractStateMachine {
    private static final String _TAG = "thirdpartyserver";
    private static ThirdPartyServerStateMachine mInstance;

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventBeforeStateGo(String str, JSONObject jSONObject, int i) {
        return null;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine
    protected String TAG() {
        return _TAG;
    }

    public static ThirdPartyServerStateMachine get() {
        if (mInstance == null) {
            synchronized (ThirdPartyServerStateMachine.class) {
                if (mInstance == null) {
                    mInstance = new ThirdPartyServerStateMachine(null);
                }
            }
        }
        return mInstance;
    }

    ThirdPartyServerStateMachine(BaseIStateMachine baseIStateMachine) {
        super(baseIStateMachine);
    }

    public enum ThirdPartyServerStateCode implements BaseIStateCode {
        START(999, "第三方服务-状态机启动"),
        END(9999, "第三方服务-状态机结束"),
        IDLE(0, "第三方服务-未连接"),
        CONNECTED(1, "第三方服务-已连接"),
        ERROR(2, "第三方服务-欠费异常"),
        DISCONNECT(3, "第三方服务-断开连接-重连中"),
        RECONNECT_SUCCESS(4, "第三方服务-重连成功"),
        RECONNECT_FAIL(5, "第三方服务-重连失败");

        private Integer code;
        private String msg;

        ThirdPartyServerStateCode(Integer num, String str) {
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
        LogUtil.m339i(TAG(), "--thirdpartyserver statemachine 初始化--");
        this.stageNow = ThirdPartyServerStateCode.START;
        registerStateTable(Collections.singleton("START"), ThirdPartyServerStateCode.START, ThirdPartyServerStateCode.IDLE);
        registerStateTable(Collections.singleton("START"), ThirdPartyServerStateCode.IDLE, ThirdPartyServerStateCode.IDLE);
        registerStateTable(Collections.singleton("CONNECTING"), ThirdPartyServerStateCode.IDLE, ThirdPartyServerStateCode.CONNECTED);
        registerStateTable(Collections.singleton(DefaultLogger.ERROR), ThirdPartyServerStateCode.CONNECTED, ThirdPartyServerStateCode.ERROR);
        registerStateTable(Collections.singleton("DISCONNECT"), ThirdPartyServerStateCode.CONNECTED, ThirdPartyServerStateCode.DISCONNECT);
        registerStateTable(Collections.singleton("RECONNECT_SUCCESS"), ThirdPartyServerStateCode.DISCONNECT, ThirdPartyServerStateCode.RECONNECT_SUCCESS);
        registerStateTable(Collections.singleton("RECONNECT_SUCCESS_2_CONNECTED"), ThirdPartyServerStateCode.RECONNECT_SUCCESS, ThirdPartyServerStateCode.CONNECTED);
        registerStateTable(Collections.singleton("RECONNECT_FAIL"), ThirdPartyServerStateCode.DISCONNECT, ThirdPartyServerStateCode.RECONNECT_FAIL);
        registerStateTable(Collections.singleton("RECONNECT_FAIL"), ThirdPartyServerStateCode.CONNECTED, ThirdPartyServerStateCode.RECONNECT_FAIL);
        for (ThirdPartyServerStateCode thirdPartyServerStateCode : ThirdPartyServerStateCode.values()) {
            registerStateTable(Arrays.asList("ACK_ERROR", "ACK_TIMEOUT", "DISCONNECT", "STATEGO_ERROR", "INIT"), thirdPartyServerStateCode, ThirdPartyServerStateCode.IDLE);
        }
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void enter() {
        stateGo(ThirdPartyServerStateCode.IDLE, "START");
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public void exit() {
        this.timeLast = this.timeNow;
        this.timeNow = System.currentTimeMillis();
        long j = this.timeNow;
        long j2 = this.timeLast;
        LogUtil.m339i(TAG(), "exit---" + toString("exit_arrow", ThirdPartyServerStateCode.IDLE));
        this.stageLast = this.stageNow;
        this.stageNow = ThirdPartyServerStateCode.IDLE;
    }

    @Override // com.aivox.common.statemachine.AbstractStateMachine, com.aivox.base.statemachine.BaseIStateMachine
    public BaseIStateCode[] getAllStateCodes() {
        return ThirdPartyServerStateCode.values();
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public Future<Boolean> executionEventAfterStateGo(String str, JSONObject jSONObject, int i) {
        str.hashCode();
        if (str.equals("RECONNECT_FAIL")) {
            stateGo(ThirdPartyServerStateCode.IDLE, "INIT");
            return null;
        }
        if (!str.equals("RECONNECT_SUCCESS")) {
            return null;
        }
        stateGo(ThirdPartyServerStateCode.CONNECTED, "RECONNECT_SUCCESS_2_CONNECTED");
        SPUtil.put(SPUtil.SEND_RECORD_DURATION, true);
        SPUtil.put("canSendStream", true);
        return null;
    }

    public void stateGo(ThirdPartyServerStateCode thirdPartyServerStateCode, String str) {
        stateGo(thirdPartyServerStateCode, str, null, 1);
    }
}
