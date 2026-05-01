package com.tencent.qcloud.network.sonar.traceroute;

import android.text.TextUtils;
import com.tencent.qcloud.network.sonar.command.CommandStatus;
import com.tencent.qcloud.network.sonar.command.NetCommandResult;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class SingleNodeResult extends NetCommandResult {
    protected float delay;
    private int hop;
    private boolean isFinalRoute;
    private String routeIp;

    protected SingleNodeResult(String str, int i) {
        super(str);
        this.hop = i;
        setRouteIp("*");
        this.delay = 0.0f;
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

    public void setDelay(float f) {
        this.delay = f;
    }

    public float getDelay() {
        return this.delay;
    }

    SingleNodeResult setHop(int i) {
        this.hop = i;
        return this;
    }

    SingleNodeResult setRouteIp(String str) {
        this.routeIp = str;
        this.isFinalRoute = TextUtils.equals(this.targetIp, str);
        return this;
    }

    SingleNodeResult setFinalRoute(boolean z) {
        this.isFinalRoute = z;
        return this;
    }

    SingleNodeResult setStatus(CommandStatus commandStatus) {
        this.status = commandStatus;
        return this;
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
            json.put("delay", String.format("%.2f", Float.valueOf(this.delay)));
            json.put("is_final_route", this.isFinalRoute);
        } catch (JSONException e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
