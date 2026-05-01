package com.aivox.base.http;

import com.aivox.base.util.BaseAppUtils;

/* JADX INFO: loaded from: classes.dex */
public class HttpUtils {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String str);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.aivox.base.http.HttpUtils$1] */
    public static void doGetAsyn(final String str, final CallBack callBack) {
        new Thread() { // from class: com.aivox.base.http.HttpUtils.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                try {
                    String strDoGet = HttpUtils.doGet(str);
                    CallBack callBack2 = callBack;
                    if (callBack2 != null) {
                        callBack2.onRequestComplete(strDoGet);
                    }
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                }
            }
        }.start();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.aivox.base.http.HttpUtils$2] */
    public static void doPostAsyn(final String str, final String str2, final CallBack callBack) throws Exception {
        new Thread() { // from class: com.aivox.base.http.HttpUtils.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                try {
                    String strDoPost = HttpUtils.doPost(str, str2);
                    CallBack callBack2 = callBack;
                    if (callBack2 != null) {
                        callBack2.onRequestComplete(strDoPost);
                    }
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                }
            }
        }.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String doGet(java.lang.String r7) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.http.HttpUtils.doGet(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ac A[Catch: IOException -> 0x008c, TRY_ENTER, TryCatch #8 {IOException -> 0x008c, blocks: (B:16:0x0088, B:19:0x008e, B:38:0x00ac, B:40:0x00b1), top: B:57:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b1 A[Catch: IOException -> 0x008c, TRY_LEAVE, TryCatch #8 {IOException -> 0x008c, blocks: (B:16:0x0088, B:19:0x008e, B:38:0x00ac, B:40:0x00b1), top: B:57:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d2 A[Catch: IOException -> 0x00ce, TRY_LEAVE, TryCatch #7 {IOException -> 0x00ce, blocks: (B:45:0x00ca, B:49:0x00d2), top: B:55:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ca A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String doPost(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.http.HttpUtils.doPost(java.lang.String, java.lang.String):java.lang.String");
    }
}
