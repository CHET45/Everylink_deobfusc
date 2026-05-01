package com.aivox.base.util;

import android.media.MediaExtractor;
import android.media.MediaPlayer;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class MusicUtil {
    private static final int SAMPLE_SIZE = 204800;

    public static boolean clip(String str, String str2, int i, int i2) {
        String fileExtension = FileUtils.getFileExtension(str);
        if (fileExtension.equalsIgnoreCase("wav")) {
            return clipWav(str, str2, i, i2);
        }
        if (fileExtension.equalsIgnoreCase("mp3")) {
            return clipMp3(str, str2, i, i2);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0076 A[Catch: IOException -> 0x007a, TRY_ENTER, TRY_LEAVE, TryCatch #1 {IOException -> 0x007a, blocks: (B:20:0x004b, B:42:0x0076), top: B:60:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean clipMp3(java.lang.String r8, java.lang.String r9, int r10, int r11) throws java.lang.Throwable {
        /*
            r0 = 0
            android.media.MediaExtractor r1 = new android.media.MediaExtractor     // Catch: java.lang.Throwable -> L67 java.io.IOException -> L6a
            r1.<init>()     // Catch: java.lang.Throwable -> L67 java.io.IOException -> L6a
            r1.setDataSource(r8)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            int r8 = getAudioTrack(r1)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            r2 = 0
            if (r8 >= 0) goto L14
            r1.release()
            return r2
        L14:
            r1.selectTrack(r8)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            java.io.BufferedOutputStream r8 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            r9 = 204800(0x32000, float:2.86986E-40)
            r8.<init>(r3, r9)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L63
            int r10 = r10 * 1000
            int r11 = r11 * 1000
            long r3 = (long) r10
            r1.seekTo(r3, r2)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
        L2c:
            java.nio.ByteBuffer r10 = java.nio.ByteBuffer.allocate(r9)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            int r0 = r1.readSampleData(r10, r2)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            long r3 = r1.getSampleTime()     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            long r5 = (long) r11
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L46
            long r3 = r3 - r5
            r5 = 1000000(0xf4240, double:4.940656E-318)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L46
            goto L48
        L46:
            if (r0 > 0) goto L4f
        L48:
            r1.release()
            r8.close()     // Catch: java.io.IOException -> L7a
            goto L7e
        L4f:
            byte[] r3 = new byte[r0]     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r10.get(r3, r2, r0)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r8.write(r3)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r1.advance()     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            goto L2c
        L5b:
            r9 = move-exception
            goto L61
        L5d:
            r9 = move-exception
            goto L65
        L5f:
            r9 = move-exception
            r8 = r0
        L61:
            r0 = r1
            goto L81
        L63:
            r9 = move-exception
            r8 = r0
        L65:
            r0 = r1
            goto L6c
        L67:
            r9 = move-exception
            r8 = r0
            goto L81
        L6a:
            r9 = move-exception
            r8 = r0
        L6c:
            com.aivox.base.util.BaseAppUtils.printErrorMsg(r9)     // Catch: java.lang.Throwable -> L80
            if (r0 == 0) goto L74
            r0.release()
        L74:
            if (r8 == 0) goto L7e
            r8.close()     // Catch: java.io.IOException -> L7a
            goto L7e
        L7a:
            r8 = move-exception
            com.aivox.base.util.BaseAppUtils.printErrorMsg(r8)
        L7e:
            r8 = 1
            return r8
        L80:
            r9 = move-exception
        L81:
            if (r0 == 0) goto L86
            r0.release()
        L86:
            if (r8 == 0) goto L90
            r8.close()     // Catch: java.io.IOException -> L8c
            goto L90
        L8c:
            r8 = move-exception
            com.aivox.base.util.BaseAppUtils.printErrorMsg(r8)
        L90:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.MusicUtil.clipMp3(java.lang.String, java.lang.String, int, int):boolean");
    }

    private static int getAudioTrack(MediaExtractor mediaExtractor) {
        for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
            if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("audio")) {
                return i;
            }
        }
        return -1;
    }

    public static boolean clipWav(String str, String str2, int i, int i2) {
        try {
            if (str.toLowerCase().endsWith(PictureMimeType.WAV) && str2.toLowerCase().endsWith(PictureMimeType.WAV)) {
                File file = new File(str);
                if (!file.exists()) {
                    return false;
                }
                long wavLength = getWavLength(file);
                if (i >= 0 && i2 > 0) {
                    long j = i;
                    if (j < wavLength && i2 <= wavLength && i < i2) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        long length = file.length() - 44;
                        int i3 = Integer.parseInt(String.valueOf((length / wavLength) * ((long) (i2 - i))));
                        int i4 = Integer.parseInt(String.valueOf((length / wavLength) * j));
                        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
                        byteBufferAllocate.putInt(i3 + 36);
                        byte[] bArrArray = byteBufferAllocate.array();
                        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(4);
                        byteBufferAllocate2.putInt(i3);
                        byte[] bArrArray2 = byteBufferAllocate2.array();
                        byte[] bArrReverse = reverse(bArrArray);
                        byte[] bArrReverse2 = reverse(bArrArray2);
                        byte[] bArr = new byte[44];
                        fileInputStream.read(bArr, 0, 44);
                        for (int i5 = 0; i5 < 4; i5++) {
                            bArr[i5 + 4] = bArrReverse[i5];
                            bArr[i5 + 40] = bArrReverse2[i5];
                        }
                        byte[] bArr2 = new byte[i3 + 44];
                        for (int i6 = 0; i6 < 44; i6++) {
                            bArr2[i6] = bArr[i6];
                        }
                        fileInputStream.read(new byte[i4], 0, i4);
                        fileInputStream.read(bArr2, 44, i3);
                        fileInputStream.close();
                        File file2 = new File(str2);
                        if (file2.exists()) {
                            file2.delete();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        fileOutputStream.write(bArr2);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    public static long getWavLength(File file) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
        long duration = mediaPlayer.getDuration();
        LogUtil.m338i("### duration: " + duration);
        mediaPlayer.release();
        return duration;
    }

    public static byte[] reverse(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < length / 2; i++) {
            byte b = bArr[i];
            int i2 = (length - 1) - i;
            bArr[i] = bArr[i2];
            bArr[i2] = b;
        }
        return bArr;
    }
}
