package com.tencent.qcloud.network.sonar.traceroute;

import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.qcloud.network.sonar.Sonar;
import com.tencent.qcloud.network.sonar.SonarCallback;
import com.tencent.qcloud.network.sonar.SonarRequest;
import com.tencent.qcloud.network.sonar.SonarResult;
import com.tencent.qcloud.network.sonar.SonarType;
import com.tencent.qcloud.network.sonar.command.CommandStatus;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class TracerouteSonar implements Sonar<TracerouteResult> {
    protected final String TAG = getClass().getSimpleName();
    private final Config config = new Config();
    private boolean isUserStop = false;
    private SonarCallback.Step<TracerouteNodeResult> stepCallback;
    private TracerouteTask task;

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public SonarResult<TracerouteResult> start(SonarRequest sonarRequest) {
        if (!sonarRequest.isNetworkAvailable()) {
            return new SonarResult<>(SonarType.TRACEROUTE, new Exception(Sonar.ERROR_MSG_NO_NETWORK));
        }
        if (TextUtils.isEmpty(sonarRequest.getIp())) {
            return new SonarResult<>(SonarType.TRACEROUTE, new Exception(Sonar.ERROR_MSG_IP_IS_EMPTY));
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        SonarLog.m1874d(this.TAG, "run thread:" + Thread.currentThread().getId() + " name:" + Thread.currentThread().getName());
        this.isUserStop = false;
        ArrayList arrayList = new ArrayList();
        long jCurrentTimeMillis2 = System.currentTimeMillis() / 1000;
        SystemClock.elapsedRealtime();
        int i = 0;
        for (int i2 = 1; i2 <= this.config.maxHop && !this.isUserStop; i2++) {
            TracerouteTask tracerouteTask = new TracerouteTask(sonarRequest.getIp(), i2, this.config.countPerRoute, this.stepCallback);
            this.task = tracerouteTask;
            TracerouteNodeResult tracerouteNodeResultRun = tracerouteTask.run();
            SonarLog.m1874d(this.TAG, String.format("[thread]:%d, [trace node]:%s", Long.valueOf(Thread.currentThread().getId()), tracerouteNodeResultRun == null ? "null" : tracerouteNodeResultRun.toString()));
            if (tracerouteNodeResultRun != null) {
                arrayList.add(tracerouteNodeResultRun);
                if (tracerouteNodeResultRun.isFinalRoute()) {
                    break;
                }
                i = TextUtils.equals("*", tracerouteNodeResultRun.getRouteIp()) ? i + 1 : 0;
                if (i == 10) {
                    break;
                }
            }
        }
        TracerouteResult tracerouteResult = new TracerouteResult(sonarRequest.getIp(), jCurrentTimeMillis2, this.isUserStop ? CommandStatus.CMD_STATUS_USER_STOP : CommandStatus.CMD_STATUS_SUCCESSFUL, sonarRequest.getHost(), System.currentTimeMillis() - jCurrentTimeMillis);
        tracerouteResult.getTracerouteNodeResults().addAll(arrayList);
        return new SonarResult<>(SonarType.TRACEROUTE, tracerouteResult);
    }

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public void stop() {
        this.isUserStop = true;
        TracerouteTask tracerouteTask = this.task;
        if (tracerouteTask != null) {
            tracerouteTask.stop();
        }
    }

    public static class Config {
        private int maxHop = 32;
        private int countPerRoute = 3;

        public int getMaxHop() {
            return this.maxHop;
        }

        public Config setMaxHop(int i) {
            this.maxHop = Math.max(1, Math.min(i, 128));
            return this;
        }

        public int getCountPerRoute() {
            return this.countPerRoute;
        }

        public Config setCountPerRoute(int i) {
            this.countPerRoute = Math.max(1, Math.min(i, 3));
            return this;
        }
    }
}
