package com.aivox.common.statemachine;

import android.content.Context;
import com.aivox.base.statemachine.BaseIStateCode;
import com.aivox.base.statemachine.BaseIStateMachine;
import com.aivox.base.statemachine.StateMachineEvent;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.MapUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractStateMachine implements BaseIStateMachine {
    Context context;
    private final int level;
    protected final BaseIStateMachine parent;
    protected BaseIStateCode stageLast;
    protected BaseIStateCode stageNow;
    protected final Map<BaseIStateCode, Map<BaseIStateCode, Set<String>>> stateTable = new HashMap();
    protected long timeLast;
    protected long timeNow;

    private void uploadStateMachineMonitor(String str, String str2, long j) {
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public abstract void InitializeTable();

    protected abstract String TAG();

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public abstract void enter();

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public abstract void exit();

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public abstract BaseIStateCode[] getAllStateCodes();

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public int getLevel() {
        return this.level;
    }

    public AbstractStateMachine(BaseIStateMachine baseIStateMachine) {
        if (baseIStateMachine == null) {
            this.level = 0;
        } else {
            this.level = baseIStateMachine.getLevel() + 1;
        }
        this.parent = baseIStateMachine;
        this.stageNow = null;
        this.stageLast = null;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.timeNow = jCurrentTimeMillis;
        this.timeLast = jCurrentTimeMillis;
        InitializeTable();
    }

    private String prefixLevel() {
        StringBuffer stringBuffer = new StringBuffer();
        BaseIStateMachine baseIStateMachine = this.parent;
        if (baseIStateMachine != null) {
            stringBuffer.append(baseIStateMachine.toString());
            stringBuffer.append('\n');
        }
        for (int i = 0; i < getLevel(); i++) {
            stringBuffer.append('\t');
        }
        return stringBuffer.toString();
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public String toString() {
        if (this.stageLast != null) {
            StringBuilder sbAppend = new StringBuilder().append(prefixLevel()).append("(prev:").append(this.stageLast.getMsg()).append(")");
            BaseIStateCode baseIStateCode = this.stageNow;
            return sbAppend.append(baseIStateCode != null ? baseIStateCode.getMsg() : "null").toString();
        }
        StringBuilder sbAppend2 = new StringBuilder().append(prefixLevel()).append("(prev:null)");
        BaseIStateCode baseIStateCode2 = this.stageNow;
        return sbAppend2.append(baseIStateCode2 != null ? baseIStateCode2.getMsg() : "null").toString();
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public String toString(String str, BaseIStateCode baseIStateCode) {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.timeLast;
        this.timeNow = System.currentTimeMillis();
        return toString() + "===" + str + "==>" + baseIStateCode.getMsg() + "===状态持续了：" + jCurrentTimeMillis;
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public BaseIStateCode getStageNow() {
        return this.stageNow;
    }

    public BaseIStateCode getStageLast() {
        return this.stageLast;
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public void registerStateTable(Collection<String> collection, BaseIStateCode baseIStateCode, BaseIStateCode baseIStateCode2) {
        Map map = (Map) MapUtil.getOrDefault(this.stateTable, baseIStateCode, new HashMap());
        Set set = (Set) MapUtil.getOrDefault(map, baseIStateCode2, new HashSet(collection));
        if (map.containsKey(baseIStateCode2)) {
            set.addAll(collection);
        } else {
            MapUtil.putIfAbsent(map, baseIStateCode2, set);
        }
        MapUtil.putIfAbsent(this.stateTable, baseIStateCode, map);
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public void stateSwitchGo(int i, JSONObject jSONObject, int i2) {
        EventBus.getDefault().post(new StateMachineEvent(i, jSONObject, i2));
    }

    @Override // com.aivox.base.statemachine.BaseIStateMachine
    public void stateGo(BaseIStateCode baseIStateCode, String str, JSONObject jSONObject, int i) {
        try {
            if (!this.stateTable.containsKey(this.stageNow)) {
                LogUtil.m337e(TAG(), "状态异常: " + toString() + ",当前状态不存在");
                throw new StateMachineException(this.stageNow, str, baseIStateCode, "当前状态不存在");
            }
            Map map = (Map) Objects.requireNonNull(this.stateTable.get(this.stageNow));
            if (!map.containsKey(baseIStateCode)) {
                LogUtil.m337e(TAG(), "状态异常:" + toString(str, baseIStateCode) + ",下一状态不存在");
                throw new StateMachineException(this.stageNow, str, baseIStateCode, "下一状态不存在");
            }
            if (!((Set) Objects.requireNonNull((Set) map.get(baseIStateCode))).contains(str) && !str.equals("exit_arrow")) {
                LogUtil.m337e(TAG(), "状态异常:" + toString(str, baseIStateCode) + ",不允许有该事件跳转");
                throw new StateMachineException(this.stageNow, str, baseIStateCode, "不允许有该事件跳转");
            }
            executionEventBeforeStateGo(str, jSONObject, i);
            this.timeLast = this.timeNow;
            this.timeNow = System.currentTimeMillis();
            LogUtil.m339i(TAG(), toString(str, baseIStateCode));
            this.stageLast = this.stageNow;
            this.stageNow = baseIStateCode;
            executionEventAfterStateGo(str, jSONObject, i);
        } catch (StateMachineException e) {
            LogUtil.m337e(TAG(), toString(str, baseIStateCode) + ";e==>" + e.toString());
        } catch (Exception e2) {
            BaseAppUtils.printErrorMsg(e2);
            LogUtil.m337e(TAG(), toString(str, baseIStateCode) + ";e==>" + e2.toString());
        }
    }
}
