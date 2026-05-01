package com.tencent.aai.audio.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PcmToWav {
    private static void clearFiles(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            if (file.exists() && file.getName().toLowerCase().endsWith(".pcm") && !file.getName().toLowerCase().equals("mergeTemp.pcm")) {
                file.delete();
            }
        }
    }

    public static boolean makePCMFileToWAVFile(String str, String str2, boolean z) {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        int length = (int) file.length();
        WaveHeader waveHeader = new WaveHeader();
        waveHeader.fileLength = length + 36;
        waveHeader.FmtHdrLeth = 16;
        waveHeader.BitsPerSample = (short) 16;
        waveHeader.Channels = (short) 2;
        waveHeader.FormatTag = (short) 1;
        waveHeader.SamplesPerSec = 8000;
        short s = (short) 4;
        waveHeader.BlockAlign = s;
        waveHeader.AvgBytesPerSec = s * 8000;
        waveHeader.DataHdrLeth = length;
        try {
            byte[] header = waveHeader.getHeader();
            if (header.length != 44) {
                return false;
            }
            File file2 = new File(str2);
            if (file2.exists()) {
                file2.delete();
            }
            try {
                byte[] bArr = new byte[4096];
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str2));
                bufferedOutputStream.write(header, 0, header.length);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                while (bufferedInputStream.read(bArr) != -1) {
                    bufferedOutputStream.write(bArr);
                }
                bufferedInputStream.close();
                bufferedOutputStream.close();
                if (z) {
                    file.delete();
                }
                String str3 = "makePCMFileToWAVFile  success!" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date());
                return true;
            } catch (FileNotFoundException e) {
                e.getMessage();
                return false;
            } catch (IOException e2) {
                e2.getMessage();
                return false;
            }
        } catch (IOException e3) {
            e3.getMessage();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2, types: [int] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.io.BufferedOutputStream, java.io.OutputStream] */
    public static boolean mergePCMFilesToWAVFile(List<String> list, String str) throws Throwable {
        File[] fileArr = new File[list.size()];
        int size = list.size();
        int length = 0;
        for (int i = 0; i < size; i++) {
            File file = new File(list.get(i));
            fileArr[i] = file;
            length = (int) (((long) length) + file.length());
        }
        WaveHeader waveHeader = new WaveHeader();
        waveHeader.fileLength = length + 36;
        waveHeader.FmtHdrLeth = 16;
        waveHeader.BitsPerSample = (short) 16;
        waveHeader.Channels = (short) 2;
        waveHeader.FormatTag = (short) 1;
        waveHeader.SamplesPerSec = 8000;
        short s = (short) 4;
        waveHeader.BlockAlign = s;
        ?? bufferedOutputStream = s * 8000;
        waveHeader.AvgBytesPerSec = bufferedOutputStream;
        waveHeader.DataHdrLeth = length;
        try {
            byte[] header = waveHeader.getHeader();
            if (header.length != 44) {
                return false;
            }
            File file2 = new File(str);
            if (file2.exists()) {
                file2.delete();
            }
            BufferedInputStream bufferedInputStream = null;
            try {
                try {
                    byte[] bArr = new byte[4096];
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
                    try {
                        bufferedOutputStream.write(header, 0, header.length);
                        int i2 = 0;
                        while (i2 < size) {
                            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(fileArr[i2]));
                            while (bufferedInputStream2.read(bArr) != -1) {
                                try {
                                    bufferedOutputStream.write(bArr);
                                } catch (FileNotFoundException e) {
                                    e = e;
                                    bufferedInputStream = bufferedInputStream2;
                                    e.getMessage();
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (bufferedOutputStream != 0) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    return false;
                                } catch (IOException e4) {
                                    e = e4;
                                    bufferedInputStream = bufferedInputStream2;
                                    e.getMessage();
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                    if (bufferedOutputStream != 0) {
                                        try {
                                            bufferedOutputStream.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedInputStream = bufferedInputStream2;
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                    if (bufferedOutputStream == 0) {
                                        throw th;
                                    }
                                    try {
                                        bufferedOutputStream.close();
                                        throw th;
                                    } catch (IOException e8) {
                                        e8.printStackTrace();
                                        throw th;
                                    }
                                }
                            }
                            bufferedInputStream2.close();
                            i2++;
                            bufferedInputStream = bufferedInputStream2;
                        }
                        bufferedOutputStream.close();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e9) {
                                e9.printStackTrace();
                            }
                        }
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e10) {
                            e10.printStackTrace();
                        }
                        String str2 = "mergePCMFilesToWAVFile  success!" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date());
                        return true;
                    } catch (FileNotFoundException e11) {
                        e = e11;
                    } catch (IOException e12) {
                        e = e12;
                    }
                } catch (FileNotFoundException e13) {
                    e = e13;
                    bufferedOutputStream = 0;
                } catch (IOException e14) {
                    e = e14;
                    bufferedOutputStream = 0;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = 0;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException e15) {
            e15.getMessage();
            return false;
        }
    }
}
