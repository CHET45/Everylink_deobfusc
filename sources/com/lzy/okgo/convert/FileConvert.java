package com.lzy.okgo.convert;

import android.os.Environment;
import android.text.TextUtils;
import com.aivox.base.util.FileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okgo.utils.OkLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class FileConvert implements Converter<File> {
    public static final String DM_TARGET_FOLDER = File.separator + FileUtils.Download + File.separator;
    private AbsCallback callback;
    private String destFileDir;
    private String destFileName;

    public FileConvert() {
        this(null);
    }

    public FileConvert(String str) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, str);
    }

    public FileConvert(String str, String str2) {
        this.destFileDir = str;
        this.destFileName = str2;
    }

    public void setCallback(AbsCallback absCallback) {
        this.callback = absCallback;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.convert.Converter
    public File convertSuccess(Response response) throws Exception {
        Throwable th;
        FileOutputStream fileOutputStream;
        InputStream inputStreamByteStream;
        InputStream inputStream;
        final long jContentLength;
        long j;
        long j2;
        long jCurrentTimeMillis;
        long j3;
        byte[] bArr;
        if (TextUtils.isEmpty(this.destFileDir)) {
            this.destFileDir = Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER;
        }
        if (TextUtils.isEmpty(this.destFileName)) {
            this.destFileName = HttpUtils.getNetFileName(response, response.request().url().toString());
        }
        File file = new File(this.destFileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, this.destFileName);
        if (file2.exists()) {
            file2.delete();
        }
        byte[] bArr2 = new byte[2048];
        InputStream inputStream2 = null;
        try {
            inputStreamByteStream = response.body().byteStream();
            try {
                jContentLength = response.body().contentLength();
                fileOutputStream = new FileOutputStream(file2);
                j = 0;
                j2 = 0;
                jCurrentTimeMillis = 0;
                j3 = 0;
            } catch (Throwable th2) {
                inputStream = inputStreamByteStream;
                th = th2;
                fileOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
        while (true) {
            try {
                int i = inputStreamByteStream.read(bArr2);
                if (i == -1) {
                    break;
                }
                File file3 = file2;
                inputStream = inputStreamByteStream;
                final long j4 = ((long) i) + j2;
                try {
                    fileOutputStream.write(bArr2, 0, i);
                    if (this.callback != null) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                        if (jCurrentTimeMillis2 >= OkGo.REFRESH_TIME || j4 == jContentLength) {
                            long j5 = jCurrentTimeMillis2 / 1000;
                            if (j5 == j) {
                                j5++;
                            }
                            final long j6 = (j4 - j3) / j5;
                            bArr = bArr2;
                            OkGo.getInstance().getDelivery().post(new Runnable() { // from class: com.lzy.okgo.convert.FileConvert.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AbsCallback absCallback = FileConvert.this.callback;
                                    long j7 = j4;
                                    long j8 = jContentLength;
                                    absCallback.downloadProgress(j7, j8, (j7 * 1.0f) / j8, j6);
                                }
                            });
                            jCurrentTimeMillis = System.currentTimeMillis();
                            j3 = j4;
                        } else {
                            bArr = bArr2;
                        }
                        j2 = j4;
                        bArr2 = bArr;
                    } else {
                        j2 = j4;
                    }
                    file2 = file3;
                    inputStreamByteStream = inputStream;
                    j = 0;
                } catch (Throwable th4) {
                    th = th4;
                }
                th = th4;
            } catch (Throwable th5) {
                th = th5;
                inputStream = inputStreamByteStream;
            }
            th = th;
            inputStream2 = inputStream;
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e) {
                    OkLogger.m864e(e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                    throw th;
                } catch (IOException e2) {
                    OkLogger.m864e(e2);
                    throw th;
                }
            }
            throw th;
        }
        File file4 = file2;
        inputStream = inputStreamByteStream;
        fileOutputStream.flush();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                OkLogger.m864e(e3);
            }
        }
        try {
            fileOutputStream.close();
        } catch (IOException e4) {
            OkLogger.m864e(e4);
        }
        return file4;
    }
}
