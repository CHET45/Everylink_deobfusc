package com.aivox.base.statemachine;

import java.util.Collection;
import java.util.concurrent.Future;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public interface BaseIStateMachine {
    void InitializeTable();

    void enter();

    Future<Boolean> executionEventAfterStateGo(String str, JSONObject jSONObject, int i);

    Future<Boolean> executionEventBeforeStateGo(String str, JSONObject jSONObject, int i);

    void exit();

    BaseIStateCode[] getAllStateCodes();

    int getLevel();

    BaseIStateCode getStageNow();

    void registerStateTable(Collection<String> collection, BaseIStateCode baseIStateCode, BaseIStateCode baseIStateCode2);

    void stateGo(BaseIStateCode baseIStateCode, String str, JSONObject jSONObject, int i);

    void stateSwitchGo(int i, JSONObject jSONObject, int i2);

    String toString();

    String toString(String str, BaseIStateCode baseIStateCode);
}
