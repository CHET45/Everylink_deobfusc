package com.tencent.aai.audio.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FileUtils {
    private static String rootPath = "/tencent/tencent_aai/audio/";
    private static final String AUDIO_PCM_BASEPATH = "/" + rootPath + "";
    private static final String AUDIO_WAV_BASEPATH = "/" + rootPath + "";

    /* JADX INFO: renamed from: com.tencent.aai.audio.utils.FileUtils$a */
    public static class RunnableC2594a implements Runnable {

        /* JADX INFO: renamed from: a */
        public final /* synthetic */ List f927a;

        public RunnableC2594a(List list) {
            this.f927a = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!PcmToWav.mergePCMFilesToWAVFile(this.f927a, FileUtils.getWavFileAbsolutePath("merge.wav"))) {
                throw new IllegalStateException("mergePCMFilesToWAVFile fail");
            }
        }
    }

    public static String getPcmFileAbsolutePath(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("fileName isEmpty");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("SDcard no found");
        }
        if (!isSdcardExit()) {
            return "";
        }
        if (!str.endsWith(".pcm")) {
            str = str + ".pcm";
        }
        String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASEPATH;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str2 + str;
    }

    public static List<File> getPcmFiles() {
        ArrayList arrayList = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASEPATH);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    public static String getWavFileAbsolutePath(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("fileName can't be null");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("SDcard no found");
        }
        if (!isSdcardExit()) {
            return "";
        }
        if (!str.endsWith(PictureMimeType.WAV)) {
            str = str + PictureMimeType.WAV;
        }
        String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASEPATH;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str2 + str;
    }

    public static List<File> getWavFiles() {
        ArrayList arrayList = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASEPATH);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    public static boolean isSdcardExit() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static void mergePCMFilesToWAVFile(List<String> list) {
        new Thread(new RunnableC2594a(list)).start();
    }
}
