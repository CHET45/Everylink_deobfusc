package com.tencent.qcloud.network.sonar.traceroute;

import com.tencent.qcloud.network.sonar.command.CommandStatus;
import com.tencent.qcloud.network.sonar.command.JsonSerializable;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class TracerouteResult implements JsonSerializable {
    private String host;
    private CommandStatus status;
    private String targetIp;
    private long timeConsuming;
    private long timestamp;
    private List<TracerouteNodeResult> tracerouteNodeResults = new ArrayList();

    public TracerouteResult(String str, long j, CommandStatus commandStatus, String str2, long j2) {
        this.targetIp = str;
        this.timestamp = j;
        this.status = commandStatus;
        this.host = str2;
        this.timeConsuming = j2;
    }

    public String getTargetIp() {
        return this.targetIp;
    }

    public List<TracerouteNodeResult> getTracerouteNodeResults() {
        return this.tracerouteNodeResults;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public CommandStatus getCommandStatus() {
        return this.status;
    }

    public long getTimeConsuming() {
        return this.timeConsuming;
    }

    public int getHopCount() {
        List<TracerouteNodeResult> list = this.tracerouteNodeResults;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public int getTotalDelay() {
        List<TracerouteNodeResult> list = this.tracerouteNodeResults;
        int iAverageDelay = 0;
        if (list != null) {
            for (TracerouteNodeResult tracerouteNodeResult : list) {
                if (tracerouteNodeResult != null && tracerouteNodeResult.averageDelay() > 0.0f) {
                    iAverageDelay += tracerouteNodeResult.averageDelay();
                }
            }
        }
        return iAverageDelay;
    }

    public float getLossRate() {
        List<TracerouteNodeResult> list = this.tracerouteNodeResults;
        if (list == null) {
            return 1.0f;
        }
        int i = 0;
        float fLossRate = 0.0f;
        for (TracerouteNodeResult tracerouteNodeResult : list) {
            if (tracerouteNodeResult != null) {
                i++;
                fLossRate += tracerouteNodeResult.lossRate();
            }
        }
        return fLossRate / i;
    }

    public String getNodeResultsString() {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        List<TracerouteNodeResult> list = this.tracerouteNodeResults;
        if (list != null && !list.isEmpty()) {
            for (TracerouteNodeResult tracerouteNodeResult : this.tracerouteNodeResults) {
                if (tracerouteNodeResult != null && tracerouteNodeResult.toJson().length() != 0) {
                    jSONArray.put(tracerouteNodeResult.toJson());
                }
            }
        }
        try {
            jSONObject.put("traceroute_node_results", jSONArray);
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    public String toString() {
        return toJson().toString();
    }

    @Override // com.tencent.qcloud.network.sonar.command.JsonSerializable
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        List<TracerouteNodeResult> list = this.tracerouteNodeResults;
        if (list != null && !list.isEmpty()) {
            for (TracerouteNodeResult tracerouteNodeResult : this.tracerouteNodeResults) {
                if (tracerouteNodeResult != null && tracerouteNodeResult.toJson().length() != 0) {
                    jSONArray.put(tracerouteNodeResult.toJson());
                }
            }
        }
        try {
            jSONObject.put("host", this.host);
            jSONObject.put("host_ip", this.targetIp);
            jSONObject.put("timestamp", this.timestamp);
            jSONObject.put("command_status", this.status.getName().toString());
            jSONObject.put("hopCount", getHopCount());
            jSONObject.put("totalDelay", getTotalDelay());
            jSONObject.put("lossRate", getLossRate());
            jSONObject.put("timeConsuming", this.timeConsuming);
            jSONObject.put("traceroute_node_results", jSONArray);
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }
}
