package com.tencent.qcloud.core.http.interceptor;

import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.TaskExecutors;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class TrafficControlInterceptor implements Interceptor {
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
        private long boostModeExhaustedTime = System.nanoTime() + BOOST_MODE_DURATION;
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

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r9) throws java.io.IOException {
        /*
            r8 = this;
            okhttp3.Request r0 = r9.request()
            com.tencent.qcloud.core.task.TaskManager r1 = com.tencent.qcloud.core.task.TaskManager.getInstance()
            java.lang.Object r2 = r0.tag()
            java.lang.String r2 = (java.lang.String) r2
            com.tencent.qcloud.core.task.QCloudTask r1 = r1.get(r2)
            com.tencent.qcloud.core.http.HttpTask r1 = (com.tencent.qcloud.core.http.HttpTask) r1
            if (r1 == 0) goto Lbd
            boolean r2 = r1.isCanceled()
            if (r2 != 0) goto Lbd
            com.tencent.qcloud.core.http.interceptor.TrafficControlInterceptor$TrafficStrategy r2 = r8.getSuitableStrategy(r1)
            if (r2 == 0) goto L25
            r2.waitForPermit()
        L25:
            java.lang.String r3 = " %s begin to execute"
            java.lang.Object[] r4 = new java.lang.Object[]{r0}
            java.lang.String r5 = "QCloudHttp"
            com.tencent.qcloud.core.logger.COSLogger.iNetwork(r5, r3, r4)
            r3 = 0
            long r4 = java.lang.System.nanoTime()     // Catch: java.lang.Exception -> L6d java.io.IOException -> L74 com.tencent.qcloud.core.common.QCloudServiceException -> L77 com.tencent.qcloud.core.common.QCloudClientException -> L8d
            okhttp3.Response r9 = r8.processRequest(r9, r0)     // Catch: java.lang.Exception -> L6d java.io.IOException -> L74 com.tencent.qcloud.core.common.QCloudServiceException -> L77 com.tencent.qcloud.core.common.QCloudClientException -> L8d
            boolean r6 = r1.isDownloadTask()     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            if (r6 == 0) goto L42
            r1.convertResponse(r9)     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
        L42:
            if (r2 == 0) goto L60
            boolean r6 = r9.isSuccessful()     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            if (r6 == 0) goto L5d
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            long r6 = java.lang.System.nanoTime()     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            long r6 = r6 - r4
            long r3 = r3.toMillis(r6)     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            double r3 = r8.getAverageStreamingSpeed(r1, r3)     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            r2.reportSpeed(r0, r3)     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
            goto L60
        L5d:
            r2.reportException(r0, r3)     // Catch: java.lang.Exception -> L61 java.io.IOException -> L64 com.tencent.qcloud.core.common.QCloudServiceException -> L67 com.tencent.qcloud.core.common.QCloudClientException -> L6a
        L60:
            return r9
        L61:
            r1 = move-exception
            r3 = r9
            goto L6e
        L64:
            r1 = move-exception
            r3 = r9
            goto L75
        L67:
            r1 = move-exception
            r3 = r9
            goto L78
        L6a:
            r1 = move-exception
            r3 = r9
            goto L8e
        L6d:
            r1 = move-exception
        L6e:
            java.io.IOException r9 = new java.io.IOException
            r9.<init>(r1)
            goto La2
        L74:
            r1 = move-exception
        L75:
            r9 = r1
            goto La2
        L77:
            r1 = move-exception
        L78:
            java.lang.Throwable r9 = r1.getCause()
            boolean r9 = r9 instanceof java.io.IOException
            if (r9 == 0) goto L87
            java.lang.Throwable r9 = r1.getCause()
            java.io.IOException r9 = (java.io.IOException) r9
            goto La2
        L87:
            java.io.IOException r9 = new java.io.IOException
            r9.<init>(r1)
            goto La2
        L8d:
            r1 = move-exception
        L8e:
            java.lang.Throwable r9 = r1.getCause()
            boolean r9 = r9 instanceof java.io.IOException
            if (r9 == 0) goto L9d
            java.lang.Throwable r9 = r1.getCause()
            java.io.IOException r9 = (java.io.IOException) r9
            goto La2
        L9d:
            java.io.IOException r9 = new java.io.IOException
            r9.<init>(r1)
        La2:
            if (r2 == 0) goto Lb1
            boolean r1 = com.tencent.qcloud.core.http.HttpUtil.isNetworkTimeoutError(r9)
            if (r1 == 0) goto Lae
            r2.reportTimeOut(r0)
            goto Lb1
        Lae:
            r2.reportException(r0, r9)
        Lb1:
            if (r3 == 0) goto Lbc
            okhttp3.ResponseBody r0 = r3.body()
            if (r0 == 0) goto Lbc
            r3.close()
        Lbc:
            throw r9
        Lbd:
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r0 = "CANCELED"
            r9.<init>(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.TrafficControlInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }

    private Response processRequest(Interceptor.Chain chain, Request request) throws IOException {
        return chain.proceed(request);
    }
}
