package com.tencent.qcloud.network.sonar.command;

import com.tencent.qcloud.network.sonar.utils.SonarLog;
import com.tencent.qcloud.network.sonar.utils.Utils;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CommandTask<T> {
    protected static float COMMAND_ELAPSED_TIME;
    protected String command;
    protected Process process;
    protected T resultData;
    protected final String TAG = getClass().getSimpleName();
    protected boolean isRunning = false;
    protected InputStream dataInputStream = null;
    protected InputStream errorInputStream = null;

    protected abstract void parseErrorInfo(String str);

    protected abstract void parseInputInfo(String str);

    protected abstract T run();

    protected abstract void stop();

    protected Process createProcess(String str) throws IOException {
        return Runtime.getRuntime().exec(str);
    }

    protected String execCommand(String str) throws InterruptedException, IOException {
        String data;
        SonarLog.m1874d(this.TAG, "[command]:" + str);
        Process processCreateProcess = createProcess(str);
        this.process = processCreateProcess;
        SonarLog.m1874d(this.TAG, "[status]: " + processCreateProcess.waitFor());
        this.dataInputStream = this.process.getInputStream();
        this.errorInputStream = this.process.getErrorStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(this.dataInputStream);
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(this.errorInputStream);
        String data2 = "";
        try {
            try {
                data = readData(bufferedInputStream);
            } catch (IOException e) {
                e = e;
                data = "";
            }
            try {
                data2 = readData(bufferedInputStream2);
                Utils.closeAllCloseable(bufferedInputStream, this.dataInputStream, bufferedInputStream2, this.errorInputStream);
            } catch (IOException e2) {
                e = e2;
                if (SonarLog.openLog) {
                    e.printStackTrace();
                }
                Utils.closeAllCloseable(bufferedInputStream, this.dataInputStream, bufferedInputStream2, this.errorInputStream);
            }
            this.process.destroy();
            parseErrorInfo(data2);
            return data;
        } catch (Throwable th) {
            Utils.closeAllCloseable(bufferedInputStream, this.dataInputStream, bufferedInputStream2, this.errorInputStream);
            this.process.destroy();
            parseErrorInfo("");
            throw th;
        }
    }

    protected String readData(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        byte[] bArr = new byte[1024];
        byte[] bArrCopyOf = null;
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                break;
            }
            if (bArrCopyOf == null) {
                bArrCopyOf = Arrays.copyOf(bArr, i);
            } else {
                int length = bArrCopyOf.length;
                byte[] bArr2 = new byte[length + i];
                System.arraycopy(bArrCopyOf, 0, bArr2, 0, length);
                System.arraycopy(bArr, 0, bArr2, length, i);
                bArrCopyOf = bArr2;
            }
        }
        if (bArrCopyOf == null) {
            return null;
        }
        return new String(bArrCopyOf, Charset.forName("UTF-8"));
    }

    public T getResultData() {
        return this.resultData;
    }
}
