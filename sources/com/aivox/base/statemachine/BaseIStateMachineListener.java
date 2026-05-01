package com.aivox.base.statemachine;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface BaseIStateMachineListener {
    void afterStateGo(BaseIStateCode baseIStateCode, String str, JSONObject jSONObject, int i);

    void beforeEnter();

    void beforeStateGo(BaseIStateCode baseIStateCode, String str, JSONObject jSONObject, int i);

    void exit();

    void gotStateSwitchGo(int i, JSONObject jSONObject, int i2);
}
