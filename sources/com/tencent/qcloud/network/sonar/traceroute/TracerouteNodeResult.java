package com.tencent.qcloud.network.sonar.traceroute;

import android.text.TextUtils;
import com.tencent.qcloud.network.sonar.command.CommandStatus;
import com.tencent.qcloud.network.sonar.command.NetCommandResult;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class TracerouteNodeResult extends NetCommandResult {
    private int hop;
    private boolean isFinalRoute;
    private String routeIp;
    private List<SingleNodeResult> singleNodeList;

    protected TracerouteNodeResult(String str, int i, List<SingleNodeResult> list) {
        super(str);
        this.hop = i;
        this.isFinalRoute = false;
        this.routeIp = "*";
        setSingleNodeList(list);
    }

    public int getHop() {
        return this.hop;
    }

    public String getRouteIp() {
        return this.routeIp;
    }

    public boolean isFinalRoute() {
        return this.isFinalRoute;
    }

    TracerouteNodeResult setHop(int i) {
        this.hop = i;
        return this;
    }

    TracerouteNodeResult setRouteIp(String str) {
        this.routeIp = str;
        this.isFinalRoute = TextUtils.equals(this.targetIp, str);
        return this;
    }

    TracerouteNodeResult setFinalRoute(boolean z) {
        this.isFinalRoute = z;
        return this;
    }

    public List<SingleNodeResult> getSingleNodeList() {
        return this.singleNodeList;
    }

    void setSingleNodeList(List<SingleNodeResult> list) {
        this.singleNodeList = list;
        if (list == null) {
            return;
        }
        for (SingleNodeResult singleNodeResult : list) {
            if (!TextUtils.equals("*", singleNodeResult.getRouteIp())) {
                setRouteIp(singleNodeResult.getRouteIp());
                return;
            }
        }
    }

    public int averageDelay() {
        List<SingleNodeResult> list = this.singleNodeList;
        int i = 0;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        float f = 0.0f;
        for (SingleNodeResult singleNodeResult : this.singleNodeList) {
            if (singleNodeResult != null && singleNodeResult.delay > 0.0f) {
                i++;
                f += singleNodeResult.delay;
            }
        }
        return Math.round(f / i);
    }

    public float lossRate() {
        List<SingleNodeResult> list = this.singleNodeList;
        if (list == null || list.isEmpty()) {
            return 1.0f;
        }
        float size = this.singleNodeList.size();
        int i = 0;
        for (SingleNodeResult singleNodeResult : this.singleNodeList) {
            if (singleNodeResult == null || singleNodeResult.getStatus() != CommandStatus.CMD_STATUS_SUCCESSFUL || singleNodeResult.delay == 0.0f) {
                i++;
            }
        }
        return i / size;
    }

    public String toString() {
        return toJson().toString();
    }

    @Override // com.tencent.qcloud.network.sonar.command.NetCommandResult, com.tencent.qcloud.network.sonar.command.CommandResult, com.tencent.qcloud.network.sonar.command.JsonSerializable
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        try {
            json.put("hop", this.hop);
            json.put("route_ip", this.routeIp);
            json.put("avg_delay", averageDelay());
            json.put("loss", String.format("%.2f", Float.valueOf(lossRate())));
            json.put("is_final_route", this.isFinalRoute);
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
