package com.jieli.jl_bt_ota.thread;

import android.os.Handler;
import android.os.Looper;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.model.base.BaseError;

/* JADX INFO: loaded from: classes3.dex */
public class ReadFileThread extends Thread {

    /* JADX INFO: renamed from: a */
    private final String f699a;

    /* JADX INFO: renamed from: b */
    private final IActionCallback<byte[]> f700b;

    /* JADX INFO: renamed from: c */
    private final Handler f701c = new Handler(Looper.getMainLooper());

    public ReadFileThread(String str, IActionCallback<byte[]> iActionCallback) {
        this.f699a = str;
        this.f700b = iActionCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m724a(byte[] bArr) {
        IActionCallback<byte[]> iActionCallback = this.f700b;
        if (iActionCallback != null) {
            iActionCallback.onSuccess(bArr);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m726b(final byte[] bArr) {
        this.f701c.post(new Runnable() { // from class: com.jieli.jl_bt_ota.thread.ReadFileThread$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m724a(bArr);
            }
        });
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.f701c.removeCallbacksAndMessages(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v15, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r1v17, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0082 -> B:49:0x0085). Please report as a decompilation issue!!! */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "file path : "
            r0.<init>(r1)
            java.lang.String r1 = r6.f699a
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ReadFileThread"
            com.jieli.jl_bt_ota.util.JL_Log.m844d(r1, r0)
            java.lang.String r0 = r6.f699a
            boolean r0 = com.jieli.jl_bt_ota.util.FileUtil.checkFileExist(r0)
            if (r0 == 0) goto L98
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.io.IOException -> L52 java.lang.Throwable -> L69 java.io.FileNotFoundException -> L6b
            java.lang.String r2 = r6.f699a     // Catch: java.io.IOException -> L52 java.lang.Throwable -> L69 java.io.FileNotFoundException -> L6b
            r1.<init>(r2)     // Catch: java.io.IOException -> L52 java.lang.Throwable -> L69 java.io.FileNotFoundException -> L6b
            int r0 = r1.available()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            int r2 = r1.read(r0)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            if (r2 < 0) goto L3c
            byte[] r3 = new byte[r2]     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            r4 = 0
            java.lang.System.arraycopy(r0, r4, r3, r4, r2)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            r6.m726b(r3)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            goto L45
        L3c:
            r0 = 20485(0x5005, float:2.8706E-41)
            com.jieli.jl_bt_ota.model.base.BaseError r0 = com.jieli.jl_bt_ota.model.OTAError.buildError(r0)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
            r6.m725b(r0)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4b java.io.FileNotFoundException -> L50
        L45:
            r1.close()     // Catch: java.io.IOException -> L81
            goto L85
        L49:
            r0 = move-exception
            goto L8d
        L4b:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L53
        L50:
            r0 = move-exception
            goto L6f
        L52:
            r1 = move-exception
        L53:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L69
            r2 = 20486(0x5006, float:2.8707E-41)
            com.jieli.jl_bt_ota.model.base.BaseError r1 = com.jieli.jl_bt_ota.model.OTAError.buildError(r2, r1)     // Catch: java.lang.Throwable -> L69
            r6.m725b(r1)     // Catch: java.lang.Throwable -> L69
            if (r0 == 0) goto L85
            r0.close()     // Catch: java.io.IOException -> L81
            goto L85
        L69:
            r1 = move-exception
            goto L8a
        L6b:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L6f:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L86
            r0 = 20484(0x5004, float:2.8704E-41)
            com.jieli.jl_bt_ota.model.base.BaseError r0 = com.jieli.jl_bt_ota.model.OTAError.buildError(r0)     // Catch: java.lang.Throwable -> L86
            r6.m725b(r0)     // Catch: java.lang.Throwable -> L86
            if (r1 == 0) goto L85
            r1.close()     // Catch: java.io.IOException -> L81
            goto L85
        L81:
            r0 = move-exception
            r0.printStackTrace()
        L85:
            return
        L86:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L8a:
            r5 = r1
            r1 = r0
            r0 = r5
        L8d:
            if (r1 == 0) goto L97
            r1.close()     // Catch: java.io.IOException -> L93
            goto L97
        L93:
            r1 = move-exception
            r1.printStackTrace()
        L97:
            throw r0
        L98:
            r0 = 4097(0x1001, float:5.741E-42)
            java.lang.String r1 = "File path does not exist."
            com.jieli.jl_bt_ota.model.base.BaseError r0 = com.jieli.jl_bt_ota.model.OTAError.buildError(r0, r1)
            r6.m725b(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.thread.ReadFileThread.run():void");
    }

    /* JADX INFO: renamed from: b */
    private void m725b(final BaseError baseError) {
        if (baseError == null) {
            return;
        }
        this.f701c.post(new Runnable() { // from class: com.jieli.jl_bt_ota.thread.ReadFileThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m723a(baseError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m723a(BaseError baseError) {
        IActionCallback<byte[]> iActionCallback = this.f700b;
        if (iActionCallback != null) {
            iActionCallback.onError(baseError);
        }
    }
}
