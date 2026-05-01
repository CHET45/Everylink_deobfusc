package com.tencent.qcloud.network.sonar.command;

import com.tencent.qcloud.network.sonar.utils.SonarLog;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NetCommandResult extends CommandResult {
    protected String targetIp;

    protected NetCommandResult(String str) {
        this.targetIp = str;
    }

    public String getTargetIp() {
        return this.targetIp;
    }

    protected NetCommandResult setTargetIp(String str) {
        this.targetIp = str;
        return this;
    }

    @Override // com.tencent.qcloud.network.sonar.command.CommandResult, com.tencent.qcloud.network.sonar.command.JsonSerializable
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        try {
            json.put("targetIp", this.targetIp);
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
