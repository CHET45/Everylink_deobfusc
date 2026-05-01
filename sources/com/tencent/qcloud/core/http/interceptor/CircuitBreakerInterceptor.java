package com.tencent.qcloud.core.http.interceptor;

import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.TaskManager;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class CircuitBreakerInterceptor implements Interceptor {
    private static final int THRESHOLD_STATE_SWITCH_FOR_CONTINUOUS_FAIL = 5;
    private static final int THRESHOLD_STATE_SWITCH_FOR_CONTINUOUS_SUCCESS = 2;
    private static final long TIMEOUT_FOR_OPEN_STATE = 10000;
    private static final long TIMEOUT_FOR_RESET_ALL = 60000;
    private long entryOpenStateTimestamp;
    private long recentErrorTimestamp;
    private AtomicInteger failedCount = new AtomicInteger(0);
    private AtomicInteger successCount = new AtomicInteger(0);
    private State state = State.CLOSED;
    private FootprintWriter footprintWriter = new FootprintWriter();

    enum State {
        OPEN,
        CLOSED,
        HALF_OPENED
    }

    private static class FootprintWriter {
        private Set<String> tasks;

        private FootprintWriter() {
            this.tasks = new HashSet();
        }

        boolean noRecords(HttpTask httpTask) {
            return !this.tasks.contains(getResourceId(httpTask));
        }

        void remember(HttpTask httpTask) {
            this.tasks.add(getResourceId(httpTask));
        }

        String getResourceId(HttpTask httpTask) {
            HttpRequest httpRequestRequest = httpTask.request();
            return httpRequestRequest.method() + httpRequestRequest.url().getHost() + "/" + httpRequestRequest.url().getPath();
        }
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        boolean zNoRecords;
        Request request = chain.request();
        HttpTask httpTask = (HttpTask) TaskManager.getInstance().get((String) request.tag());
        if (httpTask == null || httpTask.isCanceled()) {
            throw new IOException("CANCELED");
        }
        synchronized (CircuitBreakerInterceptor.class) {
            if (this.state == State.OPEN && TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.entryOpenStateTimestamp) > TIMEOUT_FOR_OPEN_STATE) {
                this.state = State.HALF_OPENED;
            }
            if (this.recentErrorTimestamp > 0 && TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.recentErrorTimestamp) > TIMEOUT_FOR_RESET_ALL) {
                this.state = State.CLOSED;
                this.successCount.set(0);
                this.failedCount.set(0);
                this.recentErrorTimestamp = 0L;
            }
            zNoRecords = this.footprintWriter.noRecords(httpTask);
            if (zNoRecords) {
                this.footprintWriter.remember(httpTask);
            }
        }
        if (this.state == State.OPEN && ((httpTask.isDownloadTask() || httpTask.isUploadTask()) && !zNoRecords)) {
            COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker deny %s", request);
            throw new CircuitBreakerDeniedException("too many continuous errors.");
        }
        try {
            Response responseProceed = chain.proceed(request);
            synchronized (CircuitBreakerInterceptor.class) {
                if (this.state == State.HALF_OPENED && this.successCount.incrementAndGet() >= 2) {
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker is CLOSED.");
                    this.state = State.CLOSED;
                    this.failedCount.set(0);
                } else if (this.state == State.OPEN) {
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker is HALF_OPENED.");
                    this.state = State.HALF_OPENED;
                    this.successCount.set(1);
                } else if (this.state == State.CLOSED) {
                    int i = this.failedCount.get();
                    if (i > 0) {
                        this.failedCount.set(Math.max(i - 2, 0));
                    }
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker get success");
                }
            }
            return responseProceed;
        } catch (IOException e) {
            synchronized (CircuitBreakerInterceptor.class) {
                this.recentErrorTimestamp = System.nanoTime();
                if (this.state == State.CLOSED && this.failedCount.incrementAndGet() >= 5) {
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker is OPEN.");
                    this.state = State.OPEN;
                    this.entryOpenStateTimestamp = System.nanoTime();
                } else if (this.state == State.HALF_OPENED) {
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker is OPEN.");
                    this.state = State.OPEN;
                    this.entryOpenStateTimestamp = System.nanoTime();
                } else {
                    COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "CircuitBreaker get fail: %d", Integer.valueOf(this.failedCount.get()));
                }
                throw e;
            }
        }
    }
}
