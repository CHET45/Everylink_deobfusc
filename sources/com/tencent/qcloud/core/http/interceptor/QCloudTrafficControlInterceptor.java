package com.tencent.qcloud.core.http.interceptor;

import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.NetworkProxy;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.TaskExecutors;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudTrafficControlInterceptor {
    private TrafficStrategy uploadTrafficStrategy = new ModerateTrafficStrategy("UploadStrategy-", TaskExecutors.UPLOAD_THREAD_COUNT);
    private TrafficStrategy downloadTrafficStrategy = new AggressiveTrafficStrategy("DownloadStrategy-", TaskExecutors.DOWNLOAD_THREAD_COUNT);

    private static class ResizableSemaphore extends Semaphore {
        ResizableSemaphore(int i, boolean z) {
            super(i, z);
        }

        @Override // java.util.concurrent.Semaphore
        protected void reducePermits(int i) {
            super.reducePermits(i);
        }
    }

    private static abstract class TrafficStrategy {
        static final long BOOST_MODE_DURATION = TimeUnit.SECONDS.toNanos(3);
        static final int SINGLE_THREAD_SAFE_SPEED = 100;
        private long boostModeExhaustedTime;
        private AtomicInteger concurrent;
        private ResizableSemaphore controller;
        private final int maxConcurrent;
        private final String name;

        TrafficStrategy(String str, int i, int i2) {
            this.name = str;
            this.maxConcurrent = i2;
            this.controller = new ResizableSemaphore(i, true);
            this.concurrent = new AtomicInteger(i);
            COSLogger.dProcess(QCloudHttpClient.HTTP_LOG_TAG, str + " init concurrent is " + i);
        }

        void reportException(Request request, IOException iOException) {
            this.controller.release();
        }

        void reportTimeOut(Request request) {
            adjustConcurrent(1, true);
        }

        void reportSpeed(Request request, double d) {
            if (d > 0.0d) {
                COSLogger.dProcess(QCloudHttpClient.HTTP_LOG_TAG, this.name + " %s streaming speed is %1.3f KBps", request, Double.valueOf(d));
                int i = this.concurrent.get();
                if (d > 240.0d && i < this.maxConcurrent) {
                    this.boostModeExhaustedTime = System.nanoTime() + BOOST_MODE_DURATION;
                    adjustConcurrent(i + 1, true);
                    return;
                }
                if (d > 120.0d && this.boostModeExhaustedTime > 0) {
                    this.boostModeExhaustedTime = System.nanoTime() + BOOST_MODE_DURATION;
                    this.controller.release();
                    return;
                } else if (d > 0.0d && i > 1 && d < 70.0d) {
                    adjustConcurrent(i - 1, true);
                    return;
                } else {
                    this.controller.release();
                    return;
                }
            }
            this.controller.release();
        }

        void waitForPermit() {
            try {
                if (this.concurrent.get() > 1 && System.nanoTime() > this.boostModeExhaustedTime) {
                    adjustConcurrent(1, false);
                }
                this.controller.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void adjustConcurrent(int i, boolean z) {
            int i2 = i - this.concurrent.get();
            if (i2 != 0) {
                this.concurrent.set(i);
                if (i2 <= 0) {
                    this.controller.reducePermits(i2 * (-1));
                    if (z) {
                        this.controller.release();
                    }
                } else if (z) {
                    this.controller.release(i2 + 1);
                }
                COSLogger.dProcess(QCloudHttpClient.HTTP_LOG_TAG, this.name + "set concurrent to " + i);
            } else if (z) {
                this.controller.release();
            }
        }
    }

    private static class AggressiveTrafficStrategy extends TrafficStrategy {
        AggressiveTrafficStrategy(String str, int i) {
            super(str, i, i);
        }
    }

    private static class ModerateTrafficStrategy extends TrafficStrategy {
        ModerateTrafficStrategy(String str, int i) {
            super(str, 1, i);
        }
    }

    private TrafficStrategy getSuitableStrategy(HttpTask httpTask) {
        if (!httpTask.isEnableTraffic()) {
            return null;
        }
        if (httpTask.isDownloadTask()) {
            return this.downloadTrafficStrategy;
        }
        if (httpTask.isUploadTask()) {
            return this.uploadTrafficStrategy;
        }
        return null;
    }

    private double getAverageStreamingSpeed(HttpTask httpTask, long j) {
        if (j == 0) {
            return 0.0d;
        }
        return (httpTask.getTransferBodySize() / 1024.0d) / (j / 1000.0d);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(com.tencent.qcloud.core.http.NetworkProxy r8, okhttp3.Request r9) throws java.io.IOException {
        /*
            r7 = this;
            com.tencent.qcloud.core.task.TaskManager r0 = com.tencent.qcloud.core.task.TaskManager.getInstance()
            java.lang.Object r1 = r9.tag()
            java.lang.String r1 = (java.lang.String) r1
            com.tencent.qcloud.core.task.QCloudTask r0 = r0.get(r1)
            com.tencent.qcloud.core.http.HttpTask r0 = (com.tencent.qcloud.core.http.HttpTask) r0
            if (r0 == 0) goto Lb9
            boolean r1 = r0.isCanceled()
            if (r1 != 0) goto Lb9
            com.tencent.qcloud.core.http.interceptor.QCloudTrafficControlInterceptor$TrafficStrategy r1 = r7.getSuitableStrategy(r0)
            if (r1 == 0) goto L21
            r1.waitForPermit()
        L21:
            java.lang.String r2 = " %s begin to execute"
            java.lang.Object[] r3 = new java.lang.Object[]{r9}
            java.lang.String r4 = "QCloudHttp"
            com.tencent.qcloud.core.logger.COSLogger.iNetwork(r4, r2, r3)
            r2 = 0
            long r3 = java.lang.System.nanoTime()     // Catch: java.lang.Exception -> L69 java.io.IOException -> L70 com.tencent.qcloud.core.common.QCloudServiceException -> L73 com.tencent.qcloud.core.common.QCloudClientException -> L89
            okhttp3.Response r8 = r7.processRequest(r8, r9)     // Catch: java.lang.Exception -> L69 java.io.IOException -> L70 com.tencent.qcloud.core.common.QCloudServiceException -> L73 com.tencent.qcloud.core.common.QCloudClientException -> L89
            boolean r5 = r0.isDownloadTask()     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            if (r5 == 0) goto L3e
            r0.convertResponse(r8)     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
        L3e:
            if (r1 == 0) goto L5c
            boolean r5 = r8.isSuccessful()     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            if (r5 == 0) goto L59
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            long r5 = java.lang.System.nanoTime()     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            long r5 = r5 - r3
            long r2 = r2.toMillis(r5)     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            double r2 = r7.getAverageStreamingSpeed(r0, r2)     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            r1.reportSpeed(r9, r2)     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
            goto L5c
        L59:
            r1.reportException(r9, r2)     // Catch: java.lang.Exception -> L5d java.io.IOException -> L60 com.tencent.qcloud.core.common.QCloudServiceException -> L63 com.tencent.qcloud.core.common.QCloudClientException -> L66
        L5c:
            return r8
        L5d:
            r0 = move-exception
            r2 = r8
            goto L6a
        L60:
            r0 = move-exception
            r2 = r8
            goto L71
        L63:
            r0 = move-exception
            r2 = r8
            goto L74
        L66:
            r0 = move-exception
            r2 = r8
            goto L8a
        L69:
            r0 = move-exception
        L6a:
            java.io.IOException r8 = new java.io.IOException
            r8.<init>(r0)
            goto L9e
        L70:
            r0 = move-exception
        L71:
            r8 = r0
            goto L9e
        L73:
            r0 = move-exception
        L74:
            java.lang.Throwable r8 = r0.getCause()
            boolean r8 = r8 instanceof java.io.IOException
            if (r8 == 0) goto L83
            java.lang.Throwable r8 = r0.getCause()
            java.io.IOException r8 = (java.io.IOException) r8
            goto L9e
        L83:
            java.io.IOException r8 = new java.io.IOException
            r8.<init>(r0)
            goto L9e
        L89:
            r0 = move-exception
        L8a:
            java.lang.Throwable r8 = r0.getCause()
            boolean r8 = r8 instanceof java.io.IOException
            if (r8 == 0) goto L99
            java.lang.Throwable r8 = r0.getCause()
            java.io.IOException r8 = (java.io.IOException) r8
            goto L9e
        L99:
            java.io.IOException r8 = new java.io.IOException
            r8.<init>(r0)
        L9e:
            if (r1 == 0) goto Lad
            boolean r0 = com.tencent.qcloud.core.http.HttpUtil.isNetworkTimeoutError(r8)
            if (r0 == 0) goto Laa
            r1.reportTimeOut(r9)
            goto Lad
        Laa:
            r1.reportException(r9, r8)
        Lad:
            if (r2 == 0) goto Lb8
            okhttp3.ResponseBody r9 = r2.body()
            if (r9 == 0) goto Lb8
            r2.close()
        Lb8:
            throw r8
        Lb9:
            java.io.IOException r8 = new java.io.IOException
            java.lang.String r9 = "CANCELED"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.QCloudTrafficControlInterceptor.intercept(com.tencent.qcloud.core.http.NetworkProxy, okhttp3.Request):okhttp3.Response");
    }

    private Response processRequest(NetworkProxy networkProxy, Request request) throws IOException {
        return networkProxy.callHttpRequest(request);
    }
}
