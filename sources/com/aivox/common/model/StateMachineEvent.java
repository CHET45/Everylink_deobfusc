package com.aivox.common.model;

import com.aivox.base.statemachine.BaseIStateCode;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class StateMachineEvent {
    public String arrow;
    public JSONObject jsonObject;
    public int msgId;
    public BaseIStateCode next;
    public int status;

    public StateMachineEvent(BaseIStateCode baseIStateCode, String str, JSONObject jSONObject, int i) {
        this.msgId = this.msgId;
        this.next = baseIStateCode;
        this.arrow = str;
        this.jsonObject = jSONObject;
        this.status = i;
    }

    public StateMachineEvent(int i, JSONObject jSONObject, int i2) {
        this.msgId = i;
        this.jsonObject = jSONObject;
        this.status = i2;
    }

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int i) {
        this.msgId = i;
    }

    public BaseIStateCode getNext() {
        return this.next;
    }

    public void setNext(BaseIStateCode baseIStateCode) {
        this.next = baseIStateCode;
    }

    public String getArrow() {
        return this.arrow;
    }

    public void setArrow(String str) {
        this.arrow = str;
    }

    public JSONObject getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(JSONObject jSONObject) {
        this.jsonObject = jSONObject;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
