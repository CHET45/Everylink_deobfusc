package com.aivox.app.util.wav;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.aivox.app.test.denoise.UrlUtil;
import com.aivox.base.C0874R;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.common.download.OkHttpClientHolder;
import com.github.houbb.heaven.constant.FileOptionConst;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class IOSWavManager2 {
    private static final int BUFF_SIZE = 2048;
    private static final String IOS_WAV_DIR = "ios_wav";
    public static final int IOS_WAV_HEAD_SIZE = 4096;
    public static final String IOS_WAV_JUNK = "JUNK";
    private Call callDownload;
    private Call callHead;
    private FileInputStream fileInputStreamTemp;
    private FileOutputStream fileOutputStreamDest;
    private FileOutputStream fileOutputStreamDownload;
    private File fileTemp;
    private boolean gotoMainThread;
    private InputStream inputStreamDownLoad;
    private InputStream inputStreamHead;
    private boolean isDestroyed;
    private boolean isRunning;
    private boolean isStopProgram;
    private Activity mActivity;
    private final OkHttpClient okHttpClient = OkHttpClientHolder.INSTANCE.getOkHttpClientDownload();
    private String oriUrl;
    private File wavFile;

    public interface IIOSWavCallback {
        void onComplete(File file);

        void onError(String str);

        void onPrepare();

        void onProgress(String str, long j, long j2);
    }

    public void destroy() {
        this.isStopProgram = true;
        close();
    }

    public static File getIOSWavDir(Context context) {
        File file = new File(FileUtils.getAppPath(context).toString(), IOS_WAV_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public void handleByThread(final Activity activity, final String str, final IIOSWavCallback iIOSWavCallback) {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.util.wav.IOSWavManager2.1
            @Override // java.lang.Runnable
            public void run() {
                IOSWavManager2.this.handle(activity, true, str, false, iIOSWavCallback);
            }
        });
    }

    public void handle(Activity activity, boolean z, String str, boolean z2, IIOSWavCallback iIOSWavCallback) {
        this.oriUrl = str;
        this.mActivity = activity;
        this.gotoMainThread = z;
        doPrepare(iIOSWavCallback);
        if (this.isRunning) {
            LogUtil.m336e("已经在解析了，不能再次执行");
            return;
        }
        this.isRunning = true;
        this.isDestroyed = false;
        this.isStopProgram = false;
        if (TextUtils.isEmpty(str)) {
            doError(activity.getString(C0874R.string.ios_wav_url_is_empty), iIOSWavCallback);
        } else if (!str.contains("http") && !str.contains("https")) {
            doError(activity.getString(C0874R.string.ios_wav_local_path), iIOSWavCallback);
        } else {
            doDownload(activity, str, z2, iIOSWavCallback);
        }
    }

    private void doDownload(Context context, String str, boolean z, IIOSWavCallback iIOSWavCallback) {
        if (!z) {
            this.oriUrl = str;
            doError(context.getString(C0874R.string.ios_wav_ignore_brand), iIOSWavCallback);
            return;
        }
        try {
            File file = new File(getIOSWavDir(context), UrlUtil.getFileNameFromUrl(this.oriUrl));
            this.wavFile = file;
            if (file.exists()) {
                doComplete(this.wavFile, iIOSWavCallback);
                return;
            }
            Call callNewCall = this.okHttpClient.newCall(new Request.Builder().url(str).build());
            this.callHead = callNewCall;
            try {
                Response responseExecute = callNewCall.execute();
                doProgress(context.getString(C0874R.string.ios_wav_running), 1L, 2L, iIOSWavCallback);
                if (responseExecute.body() == null) {
                    throw new Exception("head response is null");
                }
                this.inputStreamHead = responseExecute.body().byteStream();
                String iOSHeaderTag2 = readIOSHeaderTag2(new WavFileHeader(), this.inputStreamHead);
                if (!TextUtils.isEmpty(iOSHeaderTag2) && iOSHeaderTag2.equals(IOS_WAV_JUNK)) {
                    doProgress(context.getString(C0874R.string.ios_wav_running), 2L, 2L, iIOSWavCallback);
                    this.inputStreamHead.close();
                    this.callHead.cancel();
                    LogUtil.m338i("this is ios wav");
                    Call callNewCall2 = this.okHttpClient.newCall(new Request.Builder().url(str).build());
                    this.callDownload = callNewCall2;
                    try {
                        Response responseExecute2 = callNewCall2.execute();
                        if (responseExecute2.body() == null) {
                            throw new Exception("download response is null");
                        }
                        this.inputStreamDownLoad = responseExecute2.body().byteStream();
                        this.fileTemp = new File(getIOSWavDir(context).toString(), "temp");
                        this.fileOutputStreamDownload = new FileOutputStream(this.fileTemp);
                        long jContentLength = responseExecute2.body().contentLength();
                        byte[] bArr = new byte[2048];
                        String string = context.getString(C0874R.string.denoise_download_running);
                        long j = 0;
                        while (true) {
                            int i = this.inputStreamDownLoad.read(bArr);
                            int i2 = 0;
                            int i3 = -1;
                            if (i != -1) {
                                if (this.isStopProgram) {
                                    doError(context.getString(C0874R.string.denoise_destroy), iIOSWavCallback);
                                    return;
                                }
                                this.fileOutputStreamDownload.write(bArr, 0, i);
                                long j2 = j + ((long) i);
                                doProgress(string, j2, jContentLength, iIOSWavCallback);
                                j = j2;
                            } else {
                                this.fileOutputStreamDownload.flush();
                                this.fileOutputStreamDownload.close();
                                this.inputStreamDownLoad.close();
                                this.callDownload.cancel();
                                LogUtil.m338i("开始更改头部: " + this.fileTemp.getAbsolutePath());
                                try {
                                    this.fileInputStreamTemp = new FileInputStream(this.fileTemp);
                                    WavFileHeader wavFileHeader = new WavFileHeader();
                                    readIOSHeaderTag2(wavFileHeader, this.fileInputStreamTemp);
                                    readIOSHeaderRest2(wavFileHeader, this.fileInputStreamTemp);
                                    FileOutputStream fileOutputStream = new FileOutputStream(this.wavFile);
                                    this.fileOutputStreamDest = fileOutputStream;
                                    writeHeader2(fileOutputStream, wavFileHeader);
                                    LogUtil.m338i("写入头文件：" + wavFileHeader.toString());
                                    byte[] bArr2 = new byte[2048];
                                    long jAvailable = this.fileInputStreamTemp.available();
                                    String string2 = context.getString(C0874R.string.ios_wav_modify_wav_header);
                                    long j3 = 0;
                                    while (true) {
                                        int i4 = this.fileInputStreamTemp.read(bArr2);
                                        if (i4 != i3) {
                                            if (this.isStopProgram) {
                                                doError(context.getString(C0874R.string.denoise_destroy), iIOSWavCallback);
                                                return;
                                            }
                                            this.fileOutputStreamDest.write(bArr2, i2, i4);
                                            long j4 = j3 + ((long) i4);
                                            doProgress(string2, j4, jAvailable, iIOSWavCallback);
                                            j3 = j4;
                                            i3 = i3;
                                            i2 = i2;
                                        } else {
                                            this.fileOutputStreamDest.flush();
                                            this.fileOutputStreamDest.close();
                                            this.fileInputStreamTemp.close();
                                            modifyHeaderSize(this.fileTemp, j3);
                                            LogUtil.m338i("修改入头文件size数据,sum: " + j3);
                                            doComplete(this.wavFile, iIOSWavCallback);
                                            close();
                                            return;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LogUtil.m336e(e.getLocalizedMessage());
                                    doError(e.getLocalizedMessage(), iIOSWavCallback);
                                    return;
                                }
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        LogUtil.m336e(e2.getLocalizedMessage());
                        doError(e2.getLocalizedMessage(), iIOSWavCallback);
                        return;
                    }
                }
                doError("the audio is not ios wav", iIOSWavCallback);
            } catch (Exception e3) {
                e3.printStackTrace();
                LogUtil.m336e(e3.getLocalizedMessage());
                doError(e3.getLocalizedMessage(), iIOSWavCallback);
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            LogUtil.m336e("解析文件名错误: " + e4.getLocalizedMessage());
            doError(context.getString(C0874R.string.denoise_parse_file_name_error) + ": " + e4.getLocalizedMessage(), iIOSWavCallback);
        }
    }

    private void modifyHeaderSize(File file, long j) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ_WRITE);
        randomAccessFile.seek(4L);
        randomAccessFile.write(intToByteArray((int) (36 + j)), 0, 4);
        randomAccessFile.seek(40L);
        randomAccessFile.write(intToByteArray((int) j), 0, 4);
        randomAccessFile.close();
    }

    private void doPrepare(final IIOSWavCallback iIOSWavCallback) {
        if (this.gotoMainThread) {
            Activity activity = this.mActivity;
            if (activity == null || activity.isFinishing() || this.mActivity.isDestroyed()) {
                return;
            }
            this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.util.wav.IOSWavManager2.2
                @Override // java.lang.Runnable
                public void run() {
                    iIOSWavCallback.onPrepare();
                }
            });
            return;
        }
        iIOSWavCallback.onPrepare();
    }

    private void doProgress(final String str, final long j, final long j2, final IIOSWavCallback iIOSWavCallback) {
        if (this.isDestroyed) {
            return;
        }
        if (this.gotoMainThread) {
            Activity activity = this.mActivity;
            if (activity == null || activity.isFinishing() || this.mActivity.isDestroyed()) {
                return;
            }
            this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.util.wav.IOSWavManager2.3
                @Override // java.lang.Runnable
                public void run() {
                    iIOSWavCallback.onProgress(str, j, j2);
                }
            });
            return;
        }
        iIOSWavCallback.onProgress(str, j, j2);
    }

    private void doError(String str, final IIOSWavCallback iIOSWavCallback) {
        LogUtil.m336e(SaveLogHelper.LOG_TAG_ERROR + str);
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        this.isRunning = false;
        File file = this.wavFile;
        if (file != null && file.exists() && this.wavFile.isFile()) {
            this.wavFile.delete();
        }
        close();
        if (this.gotoMainThread) {
            Activity activity = this.mActivity;
            if (activity != null && !activity.isFinishing() && !this.mActivity.isDestroyed()) {
                this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.util.wav.IOSWavManager2.4
                    @Override // java.lang.Runnable
                    public void run() {
                        iIOSWavCallback.onError(IOSWavManager2.this.oriUrl);
                    }
                });
            }
        } else {
            iIOSWavCallback.onError(this.oriUrl);
        }
        this.mActivity = null;
    }

    private void close() {
        try {
            File file = this.fileTemp;
            if (file != null && file.exists() && this.fileTemp.isFile()) {
                this.fileTemp.delete();
            }
            FileOutputStream fileOutputStream = this.fileOutputStreamDest;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            FileInputStream fileInputStream = this.fileInputStreamTemp;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            FileOutputStream fileOutputStream2 = this.fileOutputStreamDownload;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            InputStream inputStream = this.inputStreamDownLoad;
            if (inputStream != null) {
                inputStream.close();
            }
            Call call = this.callDownload;
            if (call != null) {
                call.cancel();
            }
            InputStream inputStream2 = this.inputStreamHead;
            if (inputStream2 != null) {
                inputStream2.close();
            }
            Call call2 = this.callHead;
            if (call2 != null) {
                call2.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doComplete(final File file, final IIOSWavCallback iIOSWavCallback) {
        if (this.isDestroyed) {
            return;
        }
        this.isDestroyed = true;
        this.isRunning = false;
        if (this.gotoMainThread) {
            Activity activity = this.mActivity;
            if (activity != null && !activity.isFinishing() && !this.mActivity.isDestroyed()) {
                this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.util.wav.IOSWavManager2.5
                    @Override // java.lang.Runnable
                    public void run() {
                        iIOSWavCallback.onComplete(file);
                    }
                });
            }
            this.mActivity = null;
            return;
        }
        iIOSWavCallback.onComplete(file);
        this.mActivity = null;
    }

    private static void writeHeader2(FileOutputStream fileOutputStream, WavFileHeader wavFileHeader) throws IOException {
        fileOutputStream.write(wavFileHeader.mChunkID.getBytes(), 0, 4);
        fileOutputStream.write(intToByteArray(wavFileHeader.mChunkSize), 0, 4);
        fileOutputStream.write(wavFileHeader.mFormat.getBytes(), 0, 4);
        fileOutputStream.write(wavFileHeader.mSubChunk1ID.getBytes(), 0, 4);
        fileOutputStream.write(intToByteArray(wavFileHeader.mSubChunk1Size), 0, 4);
        fileOutputStream.write(shortToByteArray(wavFileHeader.mAudioFormat), 0, 2);
        fileOutputStream.write(shortToByteArray(wavFileHeader.mNumChannel), 0, 2);
        fileOutputStream.write(intToByteArray(wavFileHeader.mSampleRate), 0, 4);
        fileOutputStream.write(intToByteArray(wavFileHeader.mByteRate), 0, 4);
        fileOutputStream.write(shortToByteArray(wavFileHeader.mBlockAlign), 0, 2);
        fileOutputStream.write(shortToByteArray(wavFileHeader.mBitsPerSample), 0, 2);
        fileOutputStream.write(wavFileHeader.mSubChunk2ID.getBytes(), 0, 4);
        fileOutputStream.write(intToByteArray(wavFileHeader.mSubChunk2Size), 0, 4);
    }

    public static String readIOSHeaderTag2(WavFileHeader wavFileHeader, InputStream inputStream) throws Exception {
        byte[] bArr = new byte[4];
        wavFileHeader.mChunkID = "" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream));
        LogUtil.m334d("Read file chunkID:" + wavFileHeader.mChunkID);
        inputStream.read(bArr);
        wavFileHeader.mChunkSize = byteArrayToInt(bArr);
        LogUtil.m334d("Read file chunkSize:" + wavFileHeader.mChunkSize);
        wavFileHeader.mFormat = "" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream));
        LogUtil.m334d("Read file format:" + wavFileHeader.mFormat);
        return "" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream));
    }

    private static byte readByteFromInputStream(InputStream inputStream) throws Exception {
        byte[] bArr = new byte[1];
        inputStream.read(bArr);
        return bArr[0];
    }

    public static void readIOSHeaderRest2(WavFileHeader wavFileHeader, InputStream inputStream) throws Exception {
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[2];
        inputStream.read(bArr);
        int iByteArrayToInt = byteArrayToInt(bArr);
        LogUtil.m338i("junkSize: " + iByteArrayToInt);
        inputStream.read(new byte[iByteArrayToInt]);
        wavFileHeader.mSubChunk1ID = "" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream));
        LogUtil.m334d("Read fmt chunkID:" + wavFileHeader.mSubChunk1ID);
        inputStream.read(bArr);
        wavFileHeader.mSubChunk1Size = byteArrayToInt(bArr);
        LogUtil.m334d("Read fmt chunkSize:" + wavFileHeader.mSubChunk1Size);
        inputStream.read(bArr2);
        wavFileHeader.mAudioFormat = byteArrayToShort(bArr2);
        LogUtil.m334d("Read audioFormat:" + ((int) wavFileHeader.mAudioFormat));
        inputStream.read(bArr2);
        wavFileHeader.mNumChannel = byteArrayToShort(bArr2);
        LogUtil.m334d("Read channel number:" + ((int) wavFileHeader.mNumChannel));
        inputStream.read(bArr);
        wavFileHeader.mSampleRate = byteArrayToInt(bArr);
        LogUtil.m334d("Read samplerate:" + wavFileHeader.mSampleRate);
        inputStream.read(bArr);
        wavFileHeader.mByteRate = byteArrayToInt(bArr);
        LogUtil.m334d("Read byterate:" + wavFileHeader.mByteRate);
        inputStream.read(bArr2);
        wavFileHeader.mBlockAlign = byteArrayToShort(bArr2);
        LogUtil.m334d("Read blockalign:" + ((int) wavFileHeader.mBlockAlign));
        inputStream.read(bArr2);
        wavFileHeader.mBitsPerSample = byteArrayToShort(bArr2);
        LogUtil.m334d("Read bitspersample:" + ((int) wavFileHeader.mBitsPerSample));
        LogUtil.m338i("Read FLLR :" + ("" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream))));
        inputStream.read(bArr);
        int iByteArrayToInt2 = byteArrayToInt(bArr);
        LogUtil.m338i("Read FLLRSize: " + iByteArrayToInt2);
        inputStream.read(new byte[iByteArrayToInt2]);
        wavFileHeader.mSubChunk2ID = "" + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream)) + ((char) readByteFromInputStream(inputStream));
        LogUtil.m334d("Read data chunkID:" + wavFileHeader.mSubChunk2ID);
        if (!wavFileHeader.mSubChunk2ID.equals("data")) {
            LogUtil.m336e("header.mSubChunk1ID 乱码，重新赋值为data");
            wavFileHeader.mSubChunk2ID = "data";
        }
        inputStream.read(bArr);
        wavFileHeader.mSubChunk2Size = byteArrayToInt(bArr);
        LogUtil.m334d("Read data chunkSize:" + wavFileHeader.mSubChunk2Size);
        LogUtil.m334d("Read wav head success !");
    }

    public static short byteArrayToShort(byte[] bArr) {
        return ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static int byteArrayToInt(byte[] bArr) {
        return ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static byte[] intToByteArray(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
    }

    public static byte[] shortToByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array();
    }
}
