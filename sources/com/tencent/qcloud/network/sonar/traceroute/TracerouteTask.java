package com.tencent.qcloud.network.sonar.traceroute;

import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.qcloud.network.sonar.SonarCallback;
import com.tencent.qcloud.network.sonar.command.CommandStatus;
import com.tencent.qcloud.network.sonar.command.NetCommandTask;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

/* JADX INFO: loaded from: classes4.dex */
final class TracerouteTask extends NetCommandTask<TracerouteNodeResult> {
    private final int count;
    private final int hop;
    private SonarCallback.Step<TracerouteNodeResult> stepCallback;
    private final String targetIp;

    @Override // com.tencent.qcloud.network.sonar.command.CommandTask
    protected void parseInputInfo(String str) {
    }

    TracerouteTask(String str, int i, int i2, SonarCallback.Step<TracerouteNodeResult> step) {
        this.targetIp = str;
        this.hop = i;
        this.count = i2;
        this.stepCallback = step;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, com.tencent.qcloud.network.sonar.traceroute.TracerouteNodeResult] */
    @Override // com.tencent.qcloud.network.sonar.command.CommandTask
    public TracerouteNodeResult run() {
        float f;
        this.isRunning = true;
        this.command = String.format("ping -c 1 -W 1 -t %d %s", Integer.valueOf(this.hop), this.targetIp);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; this.isRunning && i < this.count; i++) {
            try {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                String strExecCommand = execCommand(this.command);
                int iElapsedRealtime = (int) (SystemClock.elapsedRealtime() - jElapsedRealtime);
                float f2 = COMMAND_ELAPSED_TIME;
                while (true) {
                    f = iElapsedRealtime - f2;
                    if (f >= ((double) iElapsedRealtime) * 0.1d || f2 <= COMMAND_ELAPSED_TIME * 0.1f) {
                        break;
                    }
                    f2 = (float) (((double) f2) * 0.8d);
                }
                SonarLog.m1874d(this.TAG, String.format("[traceroute delay]:%d [COMMAND_ELAPSED_TIME]:%f [tmpElapsed]%f", Integer.valueOf(iElapsedRealtime), Float.valueOf(COMMAND_ELAPSED_TIME), Float.valueOf(f2)));
                int i2 = (int) f;
                SingleNodeResult singleNodeInfoInput = parseSingleNodeInfoInput(strExecCommand);
                if (!singleNodeInfoInput.isFinalRoute() && singleNodeInfoInput.getStatus() == CommandStatus.CMD_STATUS_SUCCESSFUL) {
                    singleNodeInfoInput.setDelay(i2);
                }
                arrayList.add(singleNodeInfoInput);
            } catch (IOException e) {
                e = e;
                SonarLog.m1874d(this.TAG, String.format("traceroute[%d]: %s occur error: %s", Integer.valueOf(i), this.command, e.getMessage()));
            } catch (InterruptedException e2) {
                e = e2;
                SonarLog.m1874d(this.TAG, String.format("traceroute[%d]: %s occur error: %s", Integer.valueOf(i), this.command, e.getMessage()));
            }
        }
        this.resultData = new TracerouteNodeResult(this.targetIp, this.hop, arrayList);
        SonarCallback.Step<TracerouteNodeResult> step = this.stepCallback;
        if (step != null) {
            step.step((TracerouteNodeResult) this.resultData);
        }
        if (this.isRunning) {
            return (TracerouteNodeResult) this.resultData;
        }
        return null;
    }

    protected SingleNodeResult parseSingleNodeInfoInput(String str) {
        SonarLog.m1874d(this.TAG, "[hop]:" + this.hop + " [org data]:" + str);
        SingleNodeResult singleNodeResult = new SingleNodeResult(this.targetIp, this.hop);
        if (TextUtils.isEmpty(str)) {
            singleNodeResult.setStatus(CommandStatus.CMD_STATUS_NETWORK_ERROR);
            singleNodeResult.setDelay(0.0f);
            return singleNodeResult;
        }
        Matcher matcherMatcherRouteNode = matcherRouteNode(str);
        if (matcherMatcherRouteNode.find()) {
            singleNodeResult.setRouteIp(getIpFromMatcher(matcherMatcherRouteNode));
            singleNodeResult.setStatus(CommandStatus.CMD_STATUS_SUCCESSFUL);
        } else {
            Matcher matcherMatcherIp = matcherIp(str);
            if (matcherMatcherIp.find()) {
                singleNodeResult.setRouteIp(matcherMatcherIp.group());
                singleNodeResult.setStatus(CommandStatus.CMD_STATUS_SUCCESSFUL);
                singleNodeResult.setDelay(Float.parseFloat(getPingDelayFromMatcher(matcherTime(str))));
            } else {
                singleNodeResult.setStatus(CommandStatus.CMD_STATUS_FAILED);
                singleNodeResult.setDelay(0.0f);
            }
        }
        return singleNodeResult;
    }

    @Override // com.tencent.qcloud.network.sonar.command.CommandTask
    protected void parseErrorInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SonarLog.m1874d(this.TAG, "[hop]:" + this.hop + " [error data]:" + str);
    }

    @Override // com.tencent.qcloud.network.sonar.command.CommandTask
    protected void stop() {
        this.isRunning = false;
    }
}
