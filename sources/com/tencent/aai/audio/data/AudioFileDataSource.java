package com.tencent.aai.audio.data;

import com.tencent.aai.audio.utils.CharUtils;
import com.tencent.aai.exception.ClientException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class AudioFileDataSource implements PcmAudioDataSource {
    private File audioFile;
    private FileInputStream fileInputStream;
    private int interval = 38;

    public AudioFileDataSource(File file) {
        this.audioFile = file;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public boolean isSetSaveAudioRecordFiles() {
        return false;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public int read(short[] sArr, int i) {
        try {
            Thread.sleep(this.interval);
            int i2 = i * 2;
            byte[] bArr = new byte[i2];
            short[] sArrByteArray2ShortArray = null;
            try {
                int i3 = this.fileInputStream.read(bArr, 0, i2);
                if (i3 < 0 || (sArrByteArray2ShortArray = CharUtils.byteArray2ShortArray(bArr, i3)) == null) {
                    return -1;
                }
                System.arraycopy(sArrByteArray2ShortArray, 0, sArr, 0, sArrByteArray2ShortArray.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sArrByteArray2ShortArray.length;
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void start() throws ClientException {
        try {
            this.fileInputStream = new FileInputStream(this.audioFile);
        } catch (FileNotFoundException unused) {
            throw new ClientException(-1, "audio source file not exist");
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void stop() {
        FileInputStream fileInputStream = this.fileInputStream;
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
