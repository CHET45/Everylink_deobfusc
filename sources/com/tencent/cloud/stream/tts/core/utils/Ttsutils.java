package com.tencent.cloud.stream.tts.core.utils;

import android.content.Context;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public class Ttsutils {
    public static String responsePcm2Wav(Context context, int sampleRate, byte[] response, String sessionId) {
        return printAndSaveResponse(context, sampleRate, response, sessionId);
    }

    public static String printAndSaveResponse(Context context, int sampleRate, byte[] response, String sessionId) {
        if (response == null) {
            return null;
        }
        byte[] bArr = new byte[response.length + 44];
        PcmUtils.pcm2WavBytes(response, bArr, sampleRate, 1, sampleRate == 16000 ? 16 : 8);
        return saveWavFile(context, bArr, sessionId);
    }

    private static String saveWavFile(Context context, byte[] response, String fileName) {
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(fileName, 0);
            fileOutputStreamOpenFileOutput.write(response);
            fileOutputStreamOpenFileOutput.close();
            return new File(context.getFilesDir(), fileName + PictureMimeType.WAV).getAbsolutePath();
        } catch (IOException e) {
            AAILogger.m1854e("saveWavFile", "Failed save data to: " + fileName + ", error: " + e.getMessage());
            return fileName;
        }
    }

    public static int fill(InputStream in, byte[] buffer) throws IOException {
        int i;
        int length = buffer.length;
        int i2 = 0;
        do {
            i = in.read(buffer, i2, length - i2);
            if (i >= 0 && (i2 = i2 + i) == length) {
                return length;
            }
        } while (i != -1);
        return i2;
    }

    public static void saveResponseToFile(byte[] response, String filePath) {
        try {
            new File(filePath).getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
            fileOutputStream.write(response);
            fileOutputStream.close();
        } catch (IOException e) {
            AAILogger.m1854e("saveResponseToFile", "Failed save data to: " + filePath + ", error: " + e.getMessage());
        }
    }
}
