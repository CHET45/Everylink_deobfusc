package com.tencent.cloud.stream.tts.core.utils;

import com.aivox.common.socket.DeviceProtocol;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class PcmUtils {
    public static void convert2Wav(String inPcmFilePath, String outWavFilePath, int sampleRate, int channels, int bitNum) throws Throwable {
        convert2Wav(new File(inPcmFilePath), new File(outWavFilePath), sampleRate, channels, bitNum);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:40:0x006b -> B:59:0x006f). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void convert2Wav(java.io.File r13, java.io.File r14, int r15, int r16, int r17) throws java.lang.Throwable {
        /*
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            int r1 = r15 * r16
            int r1 = r1 * r17
            r2 = 0
            int r1 = r1 / 8
            long r8 = (long) r1     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L54
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L54
            r1 = r13
            r10.<init>(r13)     // Catch: java.lang.Throwable -> L50 java.lang.Exception -> L54
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            r12 = 0
            r1 = r14
            r11.<init>(r14, r12)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            java.nio.channels.FileChannel r1 = r10.getChannel()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            long r2 = r1.size()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            r4 = 36
            long r4 = r4 + r2
            r1 = r11
            r6 = r15
            r7 = r16
            writeWaveFileHeader(r1, r2, r4, r6, r7, r8)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
        L2b:
            int r1 = r10.read(r0)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            if (r1 <= 0) goto L35
            r11.write(r0, r12, r1)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L45
            goto L2b
        L35:
            r10.close()     // Catch: java.io.IOException -> L39
            goto L3e
        L39:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L3e:
            r11.close()     // Catch: java.io.IOException -> L6a
            goto L6f
        L42:
            r0 = move-exception
            r1 = r0
            goto L4a
        L45:
            r0 = move-exception
            goto L4e
        L47:
            r0 = move-exception
            r1 = r0
            r11 = r2
        L4a:
            r2 = r10
            goto L72
        L4c:
            r0 = move-exception
            r11 = r2
        L4e:
            r2 = r10
            goto L56
        L50:
            r0 = move-exception
            r1 = r0
            r11 = r2
            goto L72
        L54:
            r0 = move-exception
            r11 = r2
        L56:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L70
            if (r2 == 0) goto L64
            r2.close()     // Catch: java.io.IOException -> L5f
            goto L64
        L5f:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L64:
            if (r11 == 0) goto L6f
            r11.close()     // Catch: java.io.IOException -> L6a
            goto L6f
        L6a:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L6f:
            return
        L70:
            r0 = move-exception
            r1 = r0
        L72:
            if (r2 == 0) goto L7d
            r2.close()     // Catch: java.io.IOException -> L78
            goto L7d
        L78:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L7d:
            if (r11 == 0) goto L88
            r11.close()     // Catch: java.io.IOException -> L83
            goto L88
        L83:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L88:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cloud.stream.tts.core.utils.PcmUtils.convert2Wav(java.io.File, java.io.File, int, int, int):void");
    }

    public static void pcm2WavBytes(byte[] pcm, byte[] wav, int sampleRate, int channels, int bitNum) {
        pcm2Wav(pcm, wav, sampleRate, channels, bitNum);
    }

    public static void pcm2Wav(byte[] pcm, byte[] wav, int sampleRate, int channels, int bitNum) {
        byte[] bArr = new byte[44];
        long length = pcm.length;
        writeWaveBytesHeader(bArr, length, 36 + length, sampleRate, channels, ((sampleRate * channels) * bitNum) / 8);
        System.arraycopy(bArr, 0, wav, 0, 44);
        System.arraycopy(pcm, 0, wav, 44, pcm.length);
    }

    private static void writeWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, int sampleRate, int channels, long byteRate) throws IOException {
        byte[] bArr = new byte[44];
        writeWaveBytesHeader(bArr, totalAudioLen, totalDataLen, sampleRate, channels, byteRate);
        out.write(bArr, 0, 44);
    }

    private static void writeWaveBytesHeader(byte[] header, long totalAudioLen, long totalDataLen, int sampleRate, int channels, long byteRate) {
        header[0] = DeviceProtocol.MSG_ID_WIFI.ACK_RECORD_STOP;
        header[1] = 73;
        header[2] = 70;
        header[3] = 70;
        header[4] = (byte) (totalDataLen & 255);
        header[5] = (byte) ((totalDataLen >> 8) & 255);
        header[6] = (byte) ((totalDataLen >> 16) & 255);
        header[7] = (byte) ((totalDataLen >> 24) & 255);
        header[8] = 87;
        header[9] = 65;
        header[10] = 86;
        header[11] = 69;
        header[12] = 102;
        header[13] = 109;
        header[14] = 116;
        header[15] = 32;
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (sampleRate & 255);
        header[25] = (byte) ((sampleRate >> 8) & 255);
        header[26] = (byte) ((sampleRate >> 16) & 255);
        header[27] = (byte) ((sampleRate >> 24) & 255);
        header[28] = (byte) (byteRate & 255);
        header[29] = (byte) ((byteRate >> 8) & 255);
        header[30] = (byte) ((byteRate >> 16) & 255);
        header[31] = (byte) ((byteRate >> 24) & 255);
        header[32] = (byte) ((channels * 16) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 100;
        header[37] = 97;
        header[38] = 116;
        header[39] = 97;
        header[40] = (byte) (totalAudioLen & 255);
        header[41] = (byte) ((totalAudioLen >> 8) & 255);
        header[42] = (byte) ((totalAudioLen >> 16) & 255);
        header[43] = (byte) ((totalAudioLen >> 24) & 255);
    }
}
