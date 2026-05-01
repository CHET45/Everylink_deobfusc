package com.tencent.qcloud.network.sonar.command;

import androidx.core.app.NotificationCompat;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CommandResult implements JsonSerializable {
    protected CommandStatus status;

    public CommandStatus getStatus() {
        return this.status;
    }

    @Override // com.tencent.qcloud.network.sonar.command.JsonSerializable
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            CommandStatus commandStatus = this.status;
            jSONObject.put(NotificationCompat.CATEGORY_STATUS, commandStatus == null ? null : commandStatus.name());
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }
}
