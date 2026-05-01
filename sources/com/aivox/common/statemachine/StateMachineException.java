package com.aivox.common.statemachine;

import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.statemachine.SocketStateMachine;
import com.google.android.material.timepicker.TimeModel;

/* JADX INFO: loaded from: classes.dex */
public class StateMachineException extends Exception {
    private String action;
    private BaseIStateCode from;

    /* JADX INFO: renamed from: to */
    private BaseIStateCode f265to;

    public StateMachineException() {
    }

    public StateMachineException(BaseIStateCode baseIStateCode, int i, BaseIStateCode baseIStateCode2, String str) {
        super(str);
        setReason(baseIStateCode, String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(i)), baseIStateCode2);
    }

    public StateMachineException(BaseIStateCode baseIStateCode, String str, BaseIStateCode baseIStateCode2, String str2) {
        super(str2);
        stateGo();
        setReason(baseIStateCode, str, baseIStateCode2);
    }

    public StateMachineException(BaseIStateCode baseIStateCode, String str, BaseIStateCode baseIStateCode2, String str2, Throwable th) {
        super(str2, th);
        setReason(baseIStateCode, str, baseIStateCode2);
    }

    public String getReason() {
        return String.format("Exception {} to {} when doing {}", this.from, this.f265to, this.action);
    }

    public void setReason(BaseIStateCode baseIStateCode, String str, BaseIStateCode baseIStateCode2) {
        this.from = baseIStateCode;
        this.f265to = baseIStateCode2;
        this.action = str;
    }

    private void stateGo() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "STATEGO_ERROR");
        SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "STATEGO_ERROR");
    }
}
