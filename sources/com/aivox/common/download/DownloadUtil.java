package com.aivox.common.download;

import com.aivox.base.util.LogUtil;
import com.blankj.utilcode.util.GsonUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class DownloadUtil {
    private Call callAsyn;
    private Call callSync;
    private FileOutputStream fos;

    /* JADX INFO: renamed from: is */
    private InputStream f215is;
    private boolean isDestroy;
    private File mDestFile;
    private OkHttpClient okHttpClient = OkHttpClientHolder.INSTANCE.getOkHttpClientDownload();
    private OnDownloadListener onDownloadListener;

    public interface OnDownloadListener {
        void onDownloadFailed(Exception exc);

        void onDownloadSuccess(File file);

        void onDownloading(long j, long j2);
    }

    public void destroy() {
        this.isDestroy = true;
        Call call = this.callAsyn;
        if (call != null && !call.isCanceled()) {
            this.callAsyn.cancel();
        }
        Call call2 = this.callSync;
        if (call2 != null && !call2.isCanceled()) {
            this.callSync.cancel();
        }
        this.okHttpClient = null;
    }

    public OkHttpClient getOkHttpClient() {
        return this.okHttpClient;
    }

    public void downloadSync(String str, String str2, String str3, OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
        this.isDestroy = false;
        Call callNewCall = this.okHttpClient.newCall(new Request.Builder().url(str).build());
        this.callSync = callNewCall;
        try {
            readAndSave(str2, str3, callNewCall.execute(), onDownloadListener);
        } catch (IOException e) {
            e.printStackTrace();
            onDownloadListener.onDownloadFailed(e);
            if (this.callSync.isCanceled()) {
                return;
            }
            this.callSync.cancel();
        }
    }

    public void download(String str, String str2, String str3, OnDownloadListener onDownloadListener) {
        downloadAndSetHeader(str, null, str2, str3, onDownloadListener);
    }

    public void downloadAndSetHeader(String str, HashMap<String, Object> map, final String str2, final String str3, final OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
        this.isDestroy = false;
        Request.Builder builderUrl = new Request.Builder().url(str);
        if (map != null && map.size() > 0) {
            for (String str4 : map.keySet()) {
                builderUrl.addHeader(str4, String.valueOf(map.get(str4)));
            }
            LogUtil.m338i("header：" + GsonUtils.toJson(map));
        }
        Call callNewCall = this.okHttpClient.newCall(builderUrl.build());
        this.callAsyn = callNewCall;
        callNewCall.enqueue(new Callback() { // from class: com.aivox.common.download.DownloadUtil.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                DownloadUtil.this.doError(iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                DownloadUtil.this.readAndSave(str2, str3, response, onDownloadListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0120, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readAndSave(java.lang.String r6, java.lang.String r7, okhttp3.Response r8, com.aivox.common.download.DownloadUtil.OnDownloadListener r9) {
        /*
            Method dump skipped, instruction units count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.download.DownloadUtil.readAndSave(java.lang.String, java.lang.String, okhttp3.Response, com.aivox.common.download.DownloadUtil$OnDownloadListener):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doError(Exception exc) {
        this.onDownloadListener.onDownloadFailed(exc);
        File file = this.mDestFile;
        if (file != null && file.exists() && this.mDestFile.isFile()) {
            LogUtil.m336e("下载异常:" + exc.getLocalizedMessage() + ", 删除文件：" + this.mDestFile.delete() + ", 路径: " + this.mDestFile.getAbsolutePath());
        }
        try {
            FileOutputStream fileOutputStream = this.fos;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            InputStream inputStream = this.f215is;
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception unused) {
            exc.printStackTrace();
        }
    }
}
