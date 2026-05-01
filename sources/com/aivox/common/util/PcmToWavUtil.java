package com.aivox.common.util;

import android.media.AudioRecord;
import com.aivox.common.socket.DeviceProtocol;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class PcmToWavUtil {
    private static final PCMFormat DEFAULT_AUDIO_FORMAT = PCMFormat.PCM_16BIT;
    private static final int DEFAULT_CHANNEL_CONFIG = 16;
    private static final int DEFAULT_CHANNEL_COUNT = 1;
    private static final int DEFAULT_SAMPLING_RATE = 16000;

    public interface IPcmConvertCallback {
        void onComplete();

        void onError(String str);

        void onStart();
    }

    public static void convertPcmToWav(String str, String str2, IPcmConvertCallback iPcmConvertCallback) {
        PCMFormat pCMFormat = DEFAULT_AUDIO_FORMAT;
        convertPcmToWav(str, str2, 16000, 1, pCMFormat.getAudioFormat() == PCMFormat.PCM_16BIT.getAudioFormat() ? 16 : 8, AudioRecord.getMinBufferSize(16000, 16, pCMFormat.getAudioFormat()), iPcmConvertCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void convertPcmToWav(java.lang.String r13, java.lang.String r14, int r15, int r16, int r17, int r18, com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.PcmToWavUtil.convertPcmToWav(java.lang.String, java.lang.String, int, int, int, int, com.aivox.common.util.PcmToWavUtil$IPcmConvertCallback):void");
    }

    private static void writeWaveFileHeader(FileOutputStream fileOutputStream, long j, long j2, int i, int i2, long j3) throws IOException {
        fileOutputStream.write(new byte[]{DeviceProtocol.MSG_ID_WIFI.ACK_RECORD_STOP, 73, 70, 70, (byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i2, 0, (byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255), (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), (byte) ((i2 * 16) / 8), 0, 16, 0, 100, 97, 116, 97, (byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)}, 0, 44);
    }
}
