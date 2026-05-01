package com.tencent.qcloud.core.logger.channel;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import com.tencent.qcloud.core.logger.LogEntity;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class FileChannel extends BaseLogChannel {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final long BUFFER_SIZE = 32768;
    private static final String LOG_DIR = "QCloudLogs";
    private static final long LOG_FLUSH_DURATION = 10000;
    private static final int MAX_FILE_COUNT = 30;
    private static final int MAX_FILE_SIZE = 3145728;
    private static final int MSG_FLUSH_ALL = 0;
    private static final int MSG_FLUSH_CONTENT = 1;
    private static FileChannel instance;
    private static final byte[] object = new byte[0];
    private final Handler handler;
    private File latestLogFile;
    private final File logRootDir;
    private final List<LogEntity> bufferRecord = Collections.synchronizedList(new ArrayList());
    private volatile long mBufferSize = 0;
    private byte[] encryptionKey = null;
    private byte[] ivParameter = null;

    public static FileChannel getInstance(Context context) {
        synchronized (FileChannel.class) {
            if (instance == null) {
                instance = new FileChannel(context);
            }
        }
        return instance;
    }

    private FileChannel(Context context) {
        this.logRootDir = new File(context.getExternalCacheDir() + File.separator + LOG_DIR);
        HandlerThread handlerThread = new HandlerThread("log_handlerThread", 1);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) { // from class: com.tencent.qcloud.core.logger.channel.FileChannel.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    FileChannel.this.flush();
                    sendEmptyMessageDelayed(0, FileChannel.LOG_FLUSH_DURATION);
                } else {
                    if (i != 1) {
                        return;
                    }
                    FileChannel.this.input();
                }
            }
        };
        this.handler = handler;
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = 0;
        handler.sendMessage(messageObtainMessage);
    }

    @Override // com.tencent.qcloud.core.logger.channel.BaseLogChannel
    public synchronized void log(LogEntity logEntity) {
        if (isLoggable(logEntity)) {
            this.bufferRecord.add(logEntity);
            this.mBufferSize += logEntity.getLength();
            this.handler.removeMessages(1);
            this.handler.sendEmptyMessageDelayed(1, 500L);
        }
    }

    public boolean isLoggable(LogEntity logEntity) {
        if (!isEnabled() || logEntity == null) {
            return false;
        }
        return logEntity.getLevel().isLoggable(getMinLevel());
    }

    private String formatDateString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss", Locale.getDefault()).format(Long.valueOf(j));
    }

    private boolean isSameDay(String str, long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return simpleDateFormat2.format(simpleDateFormat.parse(str)).equals(simpleDateFormat2.format(Long.valueOf(j)));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File[] getLogFilesDesc(int i) {
        if (this.logRootDir.listFiles() == null || this.logRootDir.listFiles().length <= 0) {
            return null;
        }
        File[] fileArrListFiles = this.logRootDir.listFiles();
        Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.tencent.qcloud.core.logger.channel.FileChannel.2
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
            }
        });
        int iMin = Math.min(i, fileArrListFiles.length);
        File[] fileArr = new File[iMin];
        System.arraycopy(fileArrListFiles, 0, fileArr, 0, iMin);
        return fileArr;
    }

    public String getLogRootDir() {
        return this.logRootDir.getAbsolutePath();
    }

    private File getLogFile(long j) {
        File[] fileArrListFiles = this.logRootDir.listFiles();
        if (this.latestLogFile == null) {
            if (!this.logRootDir.exists() && !this.logRootDir.mkdirs()) {
                return null;
            }
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.tencent.qcloud.core.logger.channel.FileChannel.3
                    @Override // java.util.Comparator
                    public int compare(File file, File file2) {
                        return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file.lastModified()));
                    }
                });
                for (File file : fileArrListFiles) {
                    boolean zContains = file.getName().contains("_encrypt");
                    byte[] bArr = this.encryptionKey;
                    if ((bArr != null && zContains) || (bArr == null && !zContains)) {
                        this.latestLogFile = file;
                        break;
                    }
                }
            }
        }
        File file2 = this.latestLogFile;
        if (file2 != null && file2.length() < 3145728) {
            String name = this.latestLogFile.getName();
            if (isSameDay(name.replace("_encrypt.log", "").replace(".log", ""), j)) {
                boolean zContains2 = name.contains("_encrypt");
                byte[] bArr2 = this.encryptionKey;
                if ((bArr2 != null && zContains2) || (bArr2 == null && !zContains2)) {
                    return this.latestLogFile;
                }
            }
        }
        this.latestLogFile = new File(this.logRootDir + File.separator + (formatDateString(j) + (this.encryptionKey == null ? "" : "_encrypt") + ".log"));
        cleanFilesIfNecessary(fileArrListFiles);
        return this.latestLogFile;
    }

    private void cleanFilesIfNecessary(File[] fileArr) {
        if (fileArr == null || fileArr.length < 30) {
            return;
        }
        fileArr[fileArr.length - 1].delete();
    }

    private void write(List<LogEntity> list) {
        synchronized (object) {
            if (list == null) {
                return;
            }
            DataOutputStream dataOutputStream = null;
            try {
                try {
                    File logFile = getLogFile(System.currentTimeMillis());
                    if (logFile != null) {
                        DataOutputStream dataOutputStream2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(logFile, true), 8192));
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                byte[] bytes = list.get(i).toString().getBytes("UTF-8");
                                if (this.encryptionKey != null) {
                                    try {
                                        appendEncryptedLog(dataOutputStream2, bytes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    dataOutputStream2.write(bytes);
                                }
                            } catch (FileNotFoundException e2) {
                                e = e2;
                                dataOutputStream = dataOutputStream2;
                                e.printStackTrace();
                                if (dataOutputStream != null) {
                                    try {
                                        dataOutputStream.close();
                                    } catch (IOException e3) {
                                        e = e3;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException e4) {
                                e = e4;
                                dataOutputStream = dataOutputStream2;
                                e.printStackTrace();
                                if (dataOutputStream != null) {
                                    try {
                                        dataOutputStream.close();
                                    } catch (IOException e5) {
                                        e = e5;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                dataOutputStream = dataOutputStream2;
                                if (dataOutputStream != null) {
                                    try {
                                        dataOutputStream.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        dataOutputStream2.flush();
                        dataOutputStream = dataOutputStream2;
                    }
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException e7) {
                            e = e7;
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e8) {
                    e = e8;
                } catch (IOException e9) {
                    e = e9;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void flush() {
        if (this.mBufferSize <= 0) {
            return;
        }
        write(this.bufferRecord);
        this.bufferRecord.clear();
        this.mBufferSize = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void input() {
        if (this.mBufferSize > 32768) {
            flush();
        }
    }

    public void setEncryptionKey(byte[] bArr, byte[] bArr2) {
        this.encryptionKey = bArr != null ? (byte[]) bArr.clone() : null;
        this.ivParameter = bArr2 != null ? (byte[]) bArr2.clone() : null;
    }

    public void appendEncryptedLog(DataOutputStream dataOutputStream, byte[] bArr) throws Exception {
        byte[] bArrEncryptSingle = encryptSingle(bArr, new SecretKeySpec(this.encryptionKey, JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM), new IvParameterSpec(this.ivParameter));
        dataOutputStream.writeInt(bArrEncryptSingle.length);
        dataOutputStream.write(bArrEncryptSingle);
    }

    private byte[] encryptSingle(byte[] bArr, SecretKeySpec secretKeySpec, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(bArr);
    }
}
