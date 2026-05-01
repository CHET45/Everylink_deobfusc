package com.tencent.qcloud.core.http.interceptor;

import android.text.TextUtils;
import com.microsoft.azure.storage.StorageErrorCodeStrings;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.common.DomainSwitchException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskManager;
import com.tencent.qcloud.core.util.DomainSwitchUtils;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/* JADX INFO: loaded from: classes4.dex */
public class RetryInterceptor implements Interceptor {
    private static final int MIN_CLOCK_SKEWED_OFFSET = 600;
    private static volatile Map<String, HostReliable> hostReliables = new HashMap();
    private RetryStrategy.WeightAndReliableAddition additionComputer = new RetryStrategy.WeightAndReliableAddition();
    private RetryStrategy retryStrategy;

    private static class HostReliable {
        private static final int defaultReliable = 2;
        private final String host;
        private final int maxReliable;
        private final int minReliable;
        private int reliable;
        private final long resetPeriod;

        private HostReliable(String str) {
            this.maxReliable = 4;
            this.minReliable = 0;
            this.resetPeriod = 300000L;
            this.host = str;
            this.reliable = 2;
            new Timer(str + "reliable").schedule(new TimerTask() { // from class: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.HostReliable.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                }
            }, 300000L, 300000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void increaseReliable() {
            int i = this.reliable;
            if (i < 4) {
                this.reliable = i + 1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void decreaseReliable() {
            int i = this.reliable;
            if (i > 0) {
                this.reliable = i - 1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized int getReliable() {
            return this.reliable;
        }

        private synchronized void zeroReliable() {
            this.reliable = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void resetReliable() {
            this.reliable = 2;
        }
    }

    public RetryInterceptor(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        return processRequest(chain, request, (HttpTask) TaskManager.getInstance().get((String) request.tag()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x01dc, code lost:
    
        r1 = r6;
        decreaseHostAccess(r6.url().host());
        r24.retryStrategy.onTaskEnd(r15, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01ee, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0211, code lost:
    
        r21 = r7;
        r14 = r8;
        r15 = r9;
        r4 = r18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0345 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x024b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x012b  */
    /* JADX WARN: Type inference failed for: r0v44, types: [com.tencent.qcloud.core.task.RetryStrategy] */
    /* JADX WARN: Type inference failed for: r0v51, types: [com.tencent.qcloud.core.task.RetryStrategy] */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r14v9, types: [java.lang.Exception] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    okhttp3.Response processRequest(okhttp3.Interceptor.Chain r25, okhttp3.Request r26, com.tencent.qcloud.core.http.HttpTask r27) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 879
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.processRequest(okhttp3.Interceptor$Chain, okhttp3.Request, com.tencent.qcloud.core.http.HttpTask):okhttp3.Response");
    }

    private Response executeTaskOnce(Interceptor.Chain chain, Request request, HttpTask httpTask) throws IOException {
        try {
            if (httpTask.isCanceled()) {
                throw new IOException("CANCELED");
            }
            if (httpTask.isResponseFilePathConverter()) {
                long transferBodySize = httpTask.getTransferBodySize();
                if (transferBodySize > 0) {
                    request = buildNewRangeRequest(request, transferBodySize, httpTask);
                }
            }
            return processSingleRequest(chain, request);
        } catch (DomainSwitchException e) {
            throw e;
        } catch (ProtocolException e2) {
            if (e2.getMessage() != null && e2.getMessage().contains("HTTP 204 had non-zero Content-Length: ")) {
                return new Response.Builder().request(request).message(e2.toString()).code(204).protocol(Protocol.HTTP_1_1).build();
            }
            e2.printStackTrace();
            throw e2;
        } catch (IOException e3) {
            e3.printStackTrace();
            throw e3;
        }
    }

    private boolean isUserCancelled(IOException iOException) {
        return (iOException == null || iOException.getMessage() == null || !iOException.getMessage().toLowerCase(Locale.ROOT).equals("canceled")) ? false : true;
    }

    Response processSingleRequest(Interceptor.Chain chain, Request request) throws IOException {
        return chain.proceed(request);
    }

    String getClockSkewError(Response response, int i) {
        if (response == null || i != 403) {
            return null;
        }
        if (response.request().method().toUpperCase(Locale.ROOT).equals("HEAD")) {
            return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED;
        }
        ResponseBody responseBodyBody = response.body();
        if (responseBodyBody == null) {
            return null;
        }
        try {
            BufferedSource bufferedSourceSource = responseBodyBody.source();
            bufferedSourceSource.request(Long.MAX_VALUE);
            String string = bufferedSourceSource.buffer().clone().readString(Charset.forName("UTF-8"));
            Pattern patternCompile = Pattern.compile("<Code>(RequestTimeTooSkewed|AccessDenied)</Code>");
            Pattern patternCompile2 = Pattern.compile("<Message>Request has expired</Message>");
            Matcher matcher = patternCompile.matcher(string);
            Matcher matcher2 = patternCompile2.matcher(string);
            if (!matcher.find()) {
                return null;
            }
            String strGroup = matcher.group(1);
            if (QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED.equals(strGroup)) {
                return QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED;
            }
            if (!"AccessDenied".equals(strGroup)) {
                return null;
            }
            if (matcher2.find()) {
                return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED;
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    private void increaseHostReliable(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            hostReliable.increaseReliable();
        } else {
            hostReliables.put(str, new HostReliable(str));
        }
    }

    private void decreaseHostAccess(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            hostReliable.decreaseReliable();
        } else {
            hostReliables.put(str, new HostReliable(str));
        }
    }

    private int getHostReliable(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            return hostReliable.getReliable();
        }
        return 2;
    }

    public static void resetHostReliable(String str) {
        HostReliable hostReliable = hostReliables.get(str);
        if (hostReliable != null) {
            hostReliable.resetReliable();
        }
    }

    private boolean shouldRetry(Request request, Response response, int i, int i2, long j, IOException iOException, int i3) {
        if (isUserCancelled(iOException)) {
            return false;
        }
        int hostReliable = getHostReliable(request.url().host());
        int retryAddition = this.additionComputer.getRetryAddition(i2, hostReliable);
        COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, String.format(Locale.ENGLISH, "attempts = %d, weight = %d, reliable = %d, addition = %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(hostReliable), Integer.valueOf(retryAddition)));
        if (!this.retryStrategy.shouldRetry(i, System.nanoTime() - j, retryAddition) || !this.retryStrategy.getQCloudHttpRetryHandler().shouldRetry(request, response, iOException)) {
            return false;
        }
        if (iOException == null || !isRecoverable(iOException, request)) {
            return i3 >= 500 && i3 < 600;
        }
        return true;
    }

    private boolean checkDomainSwitchCondition(HttpTask httpTask, Request request, Response response, int i, IOException iOException) {
        if (httpTask == null || !httpTask.isDomainSwitch() || httpTask.isSelfSigner() || !DomainSwitchUtils.isMyqcloudUrl(request.url().host())) {
            return false;
        }
        if (iOException != null && iOException.getCause() != null && (iOException.getCause() instanceof QCloudServiceException) && !TextUtils.isEmpty(((QCloudServiceException) iOException.getCause()).getRequestId())) {
            return false;
        }
        if (response == null) {
            return true;
        }
        return TextUtils.isEmpty(response.header(Headers.REQUEST_ID)) && TextUtils.isEmpty(response.header(Headers.CI_REQUEST_ID));
    }

    private boolean isRecoverable(IOException iOException, Request request) {
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            return iOException instanceof SocketTimeoutException;
        }
        if (!(iOException.getCause() instanceof QCloudServiceException)) {
            return true;
        }
        QCloudServiceException qCloudServiceException = (QCloudServiceException) iOException.getCause();
        if (TextUtils.isEmpty(request.header(COSRequestHeaderKey.X_COS_COPY_SOURCE))) {
            return qCloudServiceException.getStatusCode() >= 500 && qCloudServiceException.getStatusCode() < 600;
        }
        return isCopyObjectRetryableError(qCloudServiceException.getErrorCode());
    }

    private boolean isCopyObjectRetryableError(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return StorageErrorCodeStrings.INTERNAL_ERROR.equals(str) || "SlowDown".equals(str) || "ServiceUnavailable".equals(str);
    }

    private String parseXmlTag(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                Matcher matcher = Pattern.compile("<" + str2 + ">([^<]*)</" + str2 + ">").matcher(str);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            } catch (Exception e) {
                COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "parseXmlTag failed for tag %s: %s", str2, e.getMessage());
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x002e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private okhttp3.Request buildNewRangeRequest(okhttp3.Request r11, long r12, com.tencent.qcloud.core.http.HttpTask r14) {
        /*
            r10 = this;
            java.lang.String r0 = "Authorization"
            java.lang.String r1 = "Range"
            java.lang.String r2 = r11.header(r1)
            java.lang.String r3 = ""
            r4 = -1
            if (r2 == 0) goto L3b
            java.lang.String r6 = "bytes="
            java.lang.String r2 = r2.replace(r6, r3)
            java.lang.String r6 = "-"
            java.lang.String[] r2 = r2.split(r6)
            int r6 = r2.length
            if (r6 <= 0) goto L29
            r6 = 0
            r6 = r2[r6]     // Catch: java.lang.NumberFormatException -> L25
            long r6 = java.lang.Long.parseLong(r6)     // Catch: java.lang.NumberFormatException -> L25
            goto L2a
        L25:
            r6 = move-exception
            r6.printStackTrace()
        L29:
            r6 = r4
        L2a:
            int r8 = r2.length
            r9 = 1
            if (r8 <= r9) goto L39
            r2 = r2[r9]     // Catch: java.lang.NumberFormatException -> L35
            long r8 = java.lang.Long.parseLong(r2)     // Catch: java.lang.NumberFormatException -> L35
            goto L3d
        L35:
            r2 = move-exception
            r2.printStackTrace()
        L39:
            r8 = r4
            goto L3d
        L3b:
            r6 = r4
            r8 = r6
        L3d:
            int r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r2 == 0) goto L42
            long r12 = r12 + r6
        L42:
            okhttp3.Request$Builder r2 = r11.newBuilder()
            okhttp3.Headers r11 = r11.headers()
            okhttp3.Headers$Builder r11 = r11.newBuilder()
            java.lang.Long r12 = java.lang.Long.valueOf(r12)
            int r13 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r13 != 0) goto L57
            goto L5b
        L57:
            java.lang.String r3 = java.lang.String.valueOf(r8)
        L5b:
            java.lang.Object[] r12 = new java.lang.Object[]{r12, r3}
            java.lang.String r13 = "bytes=%s-%s"
            java.lang.String r12 = java.lang.String.format(r13, r12)
            com.tencent.qcloud.core.http.QCloudHttpRequest r13 = r14.newRangeSignRequest(r12)     // Catch: java.lang.Exception -> L73
            r11.set(r1, r12)     // Catch: java.lang.Exception -> L73
            java.lang.String r12 = r13.header(r0)     // Catch: java.lang.Exception -> L73
            r11.set(r0, r12)     // Catch: java.lang.Exception -> L73
        L73:
            okhttp3.Headers r11 = r11.build()
            r2.headers(r11)
            okhttp3.Request r11 = r2.build()
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.interceptor.RetryInterceptor.buildNewRangeRequest(okhttp3.Request, long, com.tencent.qcloud.core.http.HttpTask):okhttp3.Request");
    }
}
