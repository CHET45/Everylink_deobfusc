package com.aivox.common.speech2text;

import android.content.Context;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.jieliota.util.OtaConstant;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class WindEar {
    private static final int AUDIO_ENCODING = 2;
    private static final int AUDIO_FREQUENCY = 16000;
    private static final int PLAY_AUDIO_BUFFER_TIMES = 1;
    private static final int PLAY_CHANNEL_CONFIG = 4;
    private static final int RECORD_AUDIO_BUFFER_TIMES = 1;
    private static final int RECORD_CHANNEL_CONFIG = 16;
    private static final String TAG = "tag";
    private static final String TMP_FOLDER_NAME = "AnWindEar";
    private static String cachePCMFolder;
    private static WindEar instance = new WindEar();
    private static String wavFolderPath;
    private AudioRecordThread aRecordThread;
    private OnState onStateListener;
    private volatile WindState state = WindState.IDLE;
    private File tmpPCMFile = null;
    private File tmpWavFile = null;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface OnState {
        void onStateChanged(WindState windState);
    }

    public enum WindState {
        ERROR,
        IDLE,
        RECORDING,
        STOP_RECORD,
        PLAYING,
        STOP_PLAY
    }

    private WindEar() {
    }

    public static WindEar getInstance() {
        if (instance == null) {
            instance = new WindEar();
        }
        return instance;
    }

    public void setOnStateListener(OnState onState) {
        this.onStateListener = onState;
    }

    public static void init(Context context) {
        cachePCMFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + TMP_FOLDER_NAME;
        File file = new File(cachePCMFolder);
        if (!file.exists()) {
            Log.d(TAG, String.format(Locale.CHINA, "PCM目录:%s -> %b", cachePCMFolder, Boolean.valueOf(file.mkdirs())));
        } else {
            for (File file2 : file.listFiles()) {
                Log.d(TAG, String.format(Locale.CHINA, "删除PCM文件:%s %b", file2.getName(), Boolean.valueOf(file2.delete())));
            }
            Log.d(TAG, String.format(Locale.CHINA, "PCM目录:%s", cachePCMFolder));
        }
        wavFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + TMP_FOLDER_NAME;
        File file3 = new File(wavFolderPath);
        if (!file3.exists()) {
            Log.d(TAG, String.format(Locale.CHINA, "wav目录:%s -> %b", wavFolderPath, Boolean.valueOf(file3.mkdirs())));
        } else {
            Log.d(TAG, String.format(Locale.CHINA, "wav目录:%s", wavFolderPath));
        }
    }

    public synchronized void startRecord(boolean z) {
        if (!this.state.equals(WindState.IDLE)) {
            Log.w(TAG, "无法开始录制，当前状态为 " + this.state);
            return;
        }
        try {
            this.tmpPCMFile = File.createTempFile("recording", ".pcm", new File(cachePCMFolder));
            if (z) {
                this.tmpWavFile = new File(wavFolderPath + File.separator + FileOptionConst.READ + new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA).format(new Date()) + PictureMimeType.WAV);
            }
            Log.d(TAG, "tmp file " + this.tmpPCMFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioRecordThread audioRecordThread = this.aRecordThread;
        if (audioRecordThread != null) {
            audioRecordThread.interrupt();
            this.aRecordThread = null;
        }
        AudioRecordThread audioRecordThread2 = new AudioRecordThread(z);
        this.aRecordThread = audioRecordThread2;
        audioRecordThread2.start();
    }

    public synchronized void stopRecord() {
        if (this.state.equals(WindState.RECORDING)) {
            this.state = WindState.STOP_RECORD;
            notifyState(this.state);
        }
    }

    public synchronized void startPlayPCM() {
        if (isIdle()) {
            new AudioTrackPlayThread(this.tmpPCMFile).start();
        }
    }

    public synchronized void startPlayWav() {
        if (isIdle()) {
            new AudioTrackPlayThread(this.tmpWavFile).start();
        }
    }

    public synchronized void stopPlay() {
        if (this.state.equals(WindState.PLAYING)) {
            this.state = WindState.STOP_PLAY;
        }
    }

    public synchronized boolean isIdle() {
        return WindState.IDLE.equals(this.state);
    }

    private class AudioRecordThread extends Thread {
        AudioRecord aRecord;
        int bufferSize;
        boolean createWav;

        AudioRecordThread(boolean z) {
            this.bufferSize = 10240;
            this.createWav = z;
            this.bufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
            Log.d(WindEar.TAG, "record buffer size = " + this.bufferSize);
            this.aRecord = new AudioRecord(1, 16000, 16, 2, this.bufferSize);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            WindEar.this.state = WindState.RECORDING;
            WindEar windEar = WindEar.this;
            windEar.notifyState(windEar.state);
            Log.d(WindEar.TAG, "录制开始");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(WindEar.this.tmpPCMFile);
                FileOutputStream fileOutputStream2 = new FileOutputStream(WindEar.this.tmpWavFile);
                if (this.createWav) {
                    WindEar.this.writeWavFileHeader(fileOutputStream2, this.bufferSize, OtaConstant.SCAN_TIMEOUT, this.aRecord.getChannelCount());
                }
                this.aRecord.startRecording();
                int i = this.bufferSize;
                byte[] bArr = new byte[i];
                while (WindEar.this.state.equals(WindState.RECORDING) && !isInterrupted()) {
                    int i2 = this.aRecord.read(bArr, 0, i);
                    fileOutputStream.write(bArr, 0, i2);
                    fileOutputStream.flush();
                    if (this.createWav) {
                        fileOutputStream2.write(bArr, 0, i2);
                        fileOutputStream2.flush();
                    }
                }
                this.aRecord.stop();
                fileOutputStream.close();
                fileOutputStream2.close();
                if (this.createWav) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(WindEar.this.tmpWavFile, FileOptionConst.READ_WRITE);
                    WindEar windEar2 = WindEar.this;
                    byte[] bArrGenerateWavFileHeader = windEar2.generateWavFileHeader(windEar2.tmpPCMFile.length(), OtaConstant.SCAN_TIMEOUT, this.aRecord.getChannelCount());
                    Log.d(WindEar.TAG, "header: " + WindEar.getHexString(bArrGenerateWavFileHeader));
                    randomAccessFile.seek(0L);
                    randomAccessFile.write(bArrGenerateWavFileHeader);
                    randomAccessFile.close();
                    Log.d(WindEar.TAG, "tmpWavFile.length: " + WindEar.this.tmpWavFile.length());
                }
                Log.i(WindEar.TAG, "audio tmp PCM file len: " + WindEar.this.tmpPCMFile.length());
            } catch (Exception e) {
                Log.e(WindEar.TAG, "AudioRecordThread:", e);
                WindEar.this.notifyState(WindState.ERROR);
            }
            WindEar windEar3 = WindEar.this;
            windEar3.notifyState(windEar3.state);
            WindEar.this.state = WindState.IDLE;
            WindEar windEar4 = WindEar.this;
            windEar4.notifyState(windEar4.state);
            Log.d(WindEar.TAG, "录制结束");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toHexString(b)).append(PunctuationConst.COMMA);
        }
        return sb.toString();
    }

    private class AudioTrackPlayThread extends Thread {
        File audioFile;
        int bufferSize = 10240;
        AudioTrack track;

        AudioTrackPlayThread(File file) {
            this.audioFile = null;
            setPriority(10);
            this.audioFile = file;
            this.track = new AudioTrack(3, 16000, 4, 2, AudioTrack.getMinBufferSize(16000, 4, 2), 1);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            WindEar.this.state = WindState.PLAYING;
            WindEar windEar = WindEar.this;
            windEar.notifyState(windEar.state);
            try {
                FileInputStream fileInputStream = new FileInputStream(this.audioFile);
                this.track.play();
                int i = this.bufferSize;
                byte[] bArr = new byte[i];
                while (WindEar.this.state.equals(WindState.PLAYING) && fileInputStream.read(bArr) >= 0) {
                    this.track.write(bArr, 0, i);
                }
                this.track.stop();
                this.track.release();
            } catch (Exception e) {
                Log.e(WindEar.TAG, "AudioTrackPlayThread:", e);
                WindEar.this.notifyState(WindState.ERROR);
            }
            WindEar.this.state = WindState.STOP_PLAY;
            WindEar windEar2 = WindEar.this;
            windEar2.notifyState(windEar2.state);
            WindEar.this.state = WindState.IDLE;
            WindEar windEar3 = WindEar.this;
            windEar3.notifyState(windEar3.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyState(final WindState windState) {
        if (this.onStateListener != null) {
            this.mainHandler.post(new Runnable() { // from class: com.aivox.common.speech2text.WindEar.1
                @Override // java.lang.Runnable
                public void run() {
                    WindEar.this.onStateListener.onStateChanged(windState);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeWavFileHeader(FileOutputStream fileOutputStream, long j, long j2, int i) throws IOException {
        byte[] bArrGenerateWavFileHeader = generateWavFileHeader(j, j2, i);
        fileOutputStream.write(bArrGenerateWavFileHeader, 0, bArrGenerateWavFileHeader.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] generateWavFileHeader(long j, long j2, int i) {
        long j3 = j + 36;
        long j4 = 2 * j2 * ((long) i);
        Log.e(TAG, "longSampleRate:" + j2 + ";channels:" + i);
        return new byte[]{DeviceProtocol.MSG_ID_WIFI.ACK_RECORD_STOP, 73, 70, 70, (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i, 0, (byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), (byte) (j4 & 255), (byte) ((j4 >> 8) & 255), (byte) ((j4 >> 16) & 255), (byte) ((j4 >> 24) & 255), 4, 0, 16, 0, 100, 97, 116, 97, (byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)};
    }
}
