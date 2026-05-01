package com.tencent.aai.audio.data;

import android.media.AudioRecord;
import android.os.Build;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ClientExceptionType;
import com.tencent.aai.log.AAILogger;

/* JADX INFO: loaded from: classes4.dex */
public class AudioRecordDataSource implements PcmAudioDataSource {
    private static boolean isSetSaveAudioRecordFiles = false;
    private static boolean recording = false;
    private String TAG = AudioRecordDataSource.class.getName();
    private int audioFormat;
    private AudioRecord audioRecord;
    private int audioSource;
    private int bufferSize;
    private int channel;
    private int sampleRate;

    public AudioRecordDataSource(boolean z) {
        isSetSaveAudioRecordFiles = z;
        this.audioSource = 1;
        this.sampleRate = 16000;
        this.channel = 16;
        this.audioFormat = 2;
        int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2) * 2;
        this.bufferSize = minBufferSize;
        if (minBufferSize < 0) {
            this.bufferSize = 0;
            AAILogger.error(this.TAG, "AudioRecord.getMinBufferSize error");
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public boolean isSetSaveAudioRecordFiles() {
        return isSetSaveAudioRecordFiles;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public int read(short[] sArr, int i) {
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord == null) {
            return -1;
        }
        return audioRecord.read(sArr, 0, i);
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void start() throws ClientException {
        AudioRecord audioRecord;
        if (recording) {
            throw new ClientException(ClientExceptionType.AUDIO_RECORD_MULTIPLE_START);
        }
        this.audioRecord = Build.VERSION.SDK_INT == 29 ? new AudioRecord(7, this.sampleRate, this.channel, this.audioFormat, this.bufferSize) : new AudioRecord(this.audioSource, this.sampleRate, this.channel, this.audioFormat, this.bufferSize);
        if (this.audioRecord.getState() != 1) {
            throw new ClientException(ClientExceptionType.AUDIO_RECORD_INIT_FAILED);
        }
        if (recording || (audioRecord = this.audioRecord) == null || audioRecord.getState() != 1) {
            throw new ClientException(ClientExceptionType.AUDIO_RECORD_START_FAILED);
        }
        recording = true;
        try {
            this.audioRecord.startRecording();
        } catch (IllegalStateException unused) {
            throw new ClientException(ClientExceptionType.AUDIO_RECORD_START_FAILED);
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void stop() {
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            audioRecord.stop();
            this.audioRecord.release();
        }
        this.audioRecord = null;
        recording = false;
    }
}
