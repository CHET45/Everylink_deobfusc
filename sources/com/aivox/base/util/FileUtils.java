package com.aivox.base.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.FileProvider;
import com.aivox.base.C0874R;
import com.aivox.base.util.share.Share2;
import com.aivox.base.util.share.ShareContentType;
import com.azure.core.implementation.logging.LoggingKeys;
import com.blankj.utilcode.util.FileUtils;
import com.microsoft.azure.storage.file.FileConstants;
import com.tencent.qcloud.core.util.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class FileUtils {
    public static final String AUDIO = "audio";
    public static final String Audio_From_Clip = "audioClip";
    public static final String Audio_From_Device = "audioDevice";
    public static final String Audio_From_Other = "audioOther";
    public static final String Audio_speech_to_text = "audioTxt";
    public static final String CRASH = "crash";
    private static final String DATA_PATH;
    public static final String Download = "download";
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    public static final String LOG = "log";
    public static final String MEMO = "memo";
    public static final String OTA = "ota";
    public static final String SDPATH;
    private static final String SD_PATH;
    private static final String SD_STATE;
    public static final String SEP;
    public static final String TTS = "tts";
    public static String WECHAT_APP_PACKAGE;
    public static String WECHAT_CIRCLE;
    public static String WECHAT_LAUNCHER_UI_CLASS;

    private FileUtils() {
        throw new Error("error");
    }

    static {
        String str = File.separator;
        SEP = str;
        SDPATH = Environment.getExternalStorageDirectory() + str;
        SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        DATA_PATH = Environment.getDataDirectory().getPath();
        SD_STATE = Environment.getExternalStorageState();
        WECHAT_APP_PACKAGE = "com.tencent.mm";
        WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI";
        WECHAT_CIRCLE = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    }

    public static StringBuffer getAppPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        StringBuffer stringBuffer = new StringBuffer();
        if (externalFilesDir != null) {
            stringBuffer.append(externalFilesDir.getAbsolutePath());
        }
        stringBuffer.append(SEP);
        stringBuffer.append(SPUtil.get(SPUtil.USER_ID, ""));
        File file = new File(stringBuffer.toString());
        if (!file.exists() && !file.mkdirs()) {
            DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), "创建文件失败", null, null, false, false, C0874R.string.sure, C0874R.string.sure);
        }
        return stringBuffer;
    }

    public static void moveOldData(Context context) {
        if (new File(getAudioFilePath(context)).exists()) {
            com.blankj.utilcode.util.FileUtils.move(getOldAudioFilePath(context).toString(), getAudioFilePath(context), new FileUtils.OnReplaceListener() { // from class: com.aivox.base.util.FileUtils.1
                @Override // com.blankj.utilcode.util.FileUtils.OnReplaceListener
                public boolean onReplace(File file, File file2) {
                    return false;
                }
            });
            com.blankj.utilcode.util.FileUtils.move(getOldMemoFilePath(context).toString(), getMemoFilePath(context), new FileUtils.OnReplaceListener() { // from class: com.aivox.base.util.FileUtils.2
                @Override // com.blankj.utilcode.util.FileUtils.OnReplaceListener
                public boolean onReplace(File file, File file2) {
                    return false;
                }
            });
            com.blankj.utilcode.util.FileUtils.move(getOldAudioOtherFilePath(context).toString(), getAudioOtherFilePath(context), new FileUtils.OnReplaceListener() { // from class: com.aivox.base.util.FileUtils.3
                @Override // com.blankj.utilcode.util.FileUtils.OnReplaceListener
                public boolean onReplace(File file, File file2) {
                    return false;
                }
            });
        }
    }

    public static StringBuffer getOldAppPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(externalFilesDir.getAbsolutePath());
        return stringBuffer;
    }

    public static String getOldAudioFilePath(Context context) {
        return getOldAppPath(context, "audio");
    }

    public static String getOldAudioOtherFilePath(Context context) {
        return getOldAppPath(context, Audio_From_Other);
    }

    public static String getOldMemoFilePath(Context context) {
        return getOldAppPath(context, MEMO);
    }

    public static String getAudioFilePath(Context context) {
        return getAppPath(context, "audio");
    }

    public static String getAudioOtherFilePath(Context context) {
        return getAppPath(context, Audio_From_Other);
    }

    public static String getLogFilePath(Context context) {
        return getAppPath(context, LOG);
    }

    public static String getOTAFilePath(Context context) {
        return getAppPath(context, OTA);
    }

    public static String getMemoFilePath(Context context) {
        return getAppPath(context, MEMO);
    }

    public static String getOldAppPath(Context context, String str) {
        StringBuffer oldAppPath = getOldAppPath(context);
        String str2 = SEP;
        oldAppPath.append(str2);
        oldAppPath.append(str);
        oldAppPath.append(str2);
        String string = oldAppPath.toString();
        LogUtil.m335d("FileUtils", "filePath" + string);
        File file = new File(string);
        if (!file.exists() && !file.mkdirs()) {
            DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), "创建文件失败", null, null, false, false, C0874R.string.sure, C0874R.string.sure);
        }
        return string;
    }

    public static String getAppPath(Context context, String str) {
        StringBuffer appPath = getAppPath(context);
        String str2 = SEP;
        appPath.append(str2);
        appPath.append(str);
        appPath.append(str2);
        String string = appPath.toString();
        LogUtil.m335d("FileUtils", "filePath:" + string);
        File file = new File(string);
        if (!file.exists() && !file.mkdirs()) {
            DialogUtils.showDialogWithBtnIds(context, Integer.valueOf(C0874R.string.reminder), "创建文件失败", null, null, false, false, C0874R.string.sure, C0874R.string.sure);
        }
        return string;
    }

    public static boolean hasSdcard() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String readFile(String str, String str2) throws Throwable {
        BufferedReader bufferedReader = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "utf-8";
        }
        File file = new File(str);
        StringBuilder sb = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), str2));
            while (true) {
                try {
                    String line = bufferedReader2.readLine();
                    if (line == null) {
                        break;
                    }
                    if (!sb.toString().equals("")) {
                        sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
                    }
                    sb.append(line);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            BaseAppUtils.printErrorMsg(e);
                        }
                    }
                    throw th;
                }
            }
            String string = sb.toString();
            try {
                bufferedReader2.close();
            } catch (IOException e2) {
                BaseAppUtils.printErrorMsg(e2);
            }
            return string;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String readFile(String str) throws IOException {
        return readFile(str, "utf-8");
    }

    public static List<String> readFileToList(String str) throws IOException {
        return readFileToList(str, "utf-8");
    }

    public static List<String> readFileToList(String str, String str2) throws Throwable {
        BufferedReader bufferedReader = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "utf-8";
        }
        File file = new File(str);
        ArrayList arrayList = new ArrayList();
        if (!file.isFile()) {
            return null;
        }
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), str2));
            while (true) {
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null) {
                        arrayList.add(line);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            BaseAppUtils.printErrorMsg(e);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            BaseAppUtils.printErrorMsg(e2);
                        }
                    }
                    throw th;
                }
            }
            bufferedReader2.close();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void dealAudioStream(Activity activity, ArrayList<Uri> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(UriToPathUtil.getFileAbsolutePath(activity, it.next()));
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            copyFile((String) it2.next(), getAppPath(activity, Audio_From_Other));
        }
    }

    public static boolean copyFile(String str, String str2) {
        if (str == null || BaseStringUtil.isEmpty(str)) {
            LogUtil.m338i("copyFileFrom为空");
            return false;
        }
        String str3 = str2 + str.substring(str.lastIndexOf("/") + 1);
        LogUtil.m338i("源文件地址:" + str);
        LogUtil.m338i("目标文件地址:" + str3);
        try {
            boolean zWriteFile = writeFile(str3, new FileInputStream(str));
            LogUtil.m338i("结果：" + zWriteFile);
            return zWriteFile;
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("e:" + e.toString());
            return false;
        }
    }

    public static String copyAndRenameFile(String str, String str2, String str3) {
        if (str == null || BaseStringUtil.isEmpty(str) || !isFileExist(str)) {
            LogUtil.m338i("copyFileFrom为空");
            return "";
        }
        String str4 = str2 + (str3 + getFileExtensionName(str));
        LogUtil.m338i("源文件地址:" + str);
        LogUtil.m338i("目标文件地址:" + str4);
        try {
            LogUtil.m338i("结果：" + writeFile(str4, new FileInputStream(str)));
            return str4;
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("e:" + e);
            return "";
        }
    }

    public static boolean writeFile(String str, String str2, boolean z) throws Throwable {
        FileWriter fileWriter;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        FileWriter fileWriter2 = null;
        try {
            createFile(str);
            fileWriter = new FileWriter(str, z);
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileWriter.write(str2);
            fileWriter.flush();
            try {
                fileWriter.close();
                return true;
            } catch (IOException e) {
                BaseAppUtils.printErrorMsg(e);
                return true;
            }
        } catch (Throwable th2) {
            th = th2;
            fileWriter2 = fileWriter;
            if (fileWriter2 != null) {
                try {
                    fileWriter2.close();
                } catch (IOException e2) {
                    BaseAppUtils.printErrorMsg(e2);
                }
            }
            throw th;
        }
    }

    public static boolean writeFile(String str, InputStream inputStream) throws IOException {
        return writeFile(str, inputStream, false);
    }

    public static boolean writeFile(String str, InputStream inputStream, boolean z) throws IOException {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("filePath is Empty");
        }
        if (inputStream == null) {
            throw new NullPointerException("InputStream is null");
        }
        return writeFile(new File(str), inputStream, z);
    }

    public static boolean writeFile(File file, InputStream inputStream) throws IOException {
        return writeFile(file, inputStream, false);
    }

    public static boolean writeFile(File file, InputStream inputStream, boolean z) throws Throwable {
        if (file == null) {
            throw new NullPointerException("file = null");
        }
        FileOutputStream fileOutputStream = null;
        try {
            createFile(file.getAbsolutePath());
            FileOutputStream fileOutputStream2 = new FileOutputStream(file, z);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i != -1) {
                        fileOutputStream2.write(bArr, 0, i);
                    } else {
                        fileOutputStream2.flush();
                        try {
                            fileOutputStream2.close();
                            inputStream.close();
                            return true;
                        } catch (IOException e) {
                            BaseAppUtils.printErrorMsg(e);
                            return true;
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                        inputStream.close();
                    } catch (IOException e2) {
                        BaseAppUtils.printErrorMsg(e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<File> getFilesByPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                arrayList.add(file);
            } else if (file.isDirectory()) {
                LogUtil.m338i("文件夹:" + file.getAbsolutePath());
                getFilesByPath(file.getAbsolutePath() + "/");
            }
        }
        return arrayList;
    }

    public static List<File> getFilesByPath(String str, List<File> list) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                if (!file.getAbsolutePath().contains("_L.") && !file.getAbsolutePath().contains("_F.")) {
                    list.add(file);
                }
            } else if (file.isDirectory()) {
                getFilesByPath(file.getAbsolutePath() + "/", list);
            }
        }
        return list;
    }

    public static List<String> getFileNameList(String str, FilenameFilter filenameFilter) {
        if (filenameFilter == null) {
            return getFileNameList(str);
        }
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        File[] fileArrListFiles = new File(str).listFiles(filenameFilter);
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                arrayList.add(file.getName());
            }
        }
        return arrayList;
    }

    public static List<String> getFileNameList(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                arrayList.add(file.getName());
            }
        }
        return arrayList;
    }

    public static List<String> getFileNameList(String str, final String str2) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        File[] fileArrListFiles = new File(str).listFiles(new FilenameFilter() { // from class: com.aivox.base.util.FileUtils.4
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return str3.indexOf(new StringBuilder(".").append(str2).toString()) > 0;
            }
        });
        if (fileArrListFiles == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                arrayList.add(file.getName());
            }
        }
        return arrayList;
    }

    public static String getFileExtension(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(".");
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        if (iLastIndexOf == -1 || iLastIndexOf2 >= iLastIndexOf) {
            return "";
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static boolean makeDirs(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return (file.exists() && file.isDirectory()) || file.mkdirs();
    }

    public static boolean makeDirs(File file) {
        if (file == null) {
            return false;
        }
        return (file.exists() && file.isDirectory()) || file.mkdirs();
    }

    public static boolean createFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return createFile(new File(str));
    }

    public static boolean createFile(File file) {
        if (file != null && makeDirs(getFolderName(file.getAbsolutePath())) && !file.exists()) {
            try {
                return file.createNewFile();
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
                LogUtil.m338i("thr_createFile:" + e.toString());
            }
        }
        return false;
    }

    public static String getFolderName(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf);
    }

    public static boolean isFolderExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isDirectory();
    }

    public static boolean isFileExist(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    public static String getFileNameWithoutExtension(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(".");
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        if (iLastIndexOf2 == -1) {
            return iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf);
        }
        if (iLastIndexOf == -1) {
            return str.substring(iLastIndexOf2 + 1);
        }
        if (iLastIndexOf2 < iLastIndexOf) {
            return str.substring(iLastIndexOf2 + 1, iLastIndexOf);
        }
        return str.substring(iLastIndexOf2 + 1);
    }

    public static String getFileName(String str) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? str : str.substring(iLastIndexOf + 1);
    }

    public static String getFileExtensionName(String str) {
        int iLastIndexOf = str.lastIndexOf(".");
        if (iLastIndexOf == -1) {
            return "";
        }
        return str.substring(iLastIndexOf);
    }

    public static long getFileSize(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return -1L;
    }

    public static long getFileSizeKb(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            return file.length() / 1024;
        }
        return 0L;
    }

    public static long getAudioFileVoiceTime(String str) {
        return getAudioFileVoiceTime(str, 0);
    }

    public static long getAudioFileVoiceTime(String str, int i) {
        long duration = 0;
        if (str == null || str.isEmpty()) {
            return 0L;
        }
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            LogUtil.m336e("AUDIO== mediaPlayer.setDataSource(filePath): " + str);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(str);
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
        } catch (IOException | IllegalStateException e) {
            LogUtil.m336e("AUDIO==获取时长失败，重试中" + i + "---" + e.toString());
            int i2 = i + 1;
            if (i2 < 3) {
                getAudioFileVoiceTime(str, i2);
            }
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        return duration / 1000;
    }

    public static String getAudioFileType(Context context, String str) {
        LogUtil.m338i("filepath:" + str + "\n uri:" + UriToPathUtil.toUri(context, str).toString());
        Cursor cursorQuery = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, "title_key");
        String string = "";
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"));
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return string;
    }

    public static long getAudioFileVoiceTime2(Context context, String str) {
        Uri uri = UriToPathUtil.toUri(context, str);
        LogUtil.m338i("filepath:" + str + "\n uri:" + uri.toString());
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name", "_size", TypedValues.TransitionType.S_DURATION}, null, null, null);
        long j = 0;
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_display_name"));
                long j2 = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_size"));
                long j3 = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(TypedValues.TransitionType.S_DURATION));
                LogUtil.m338i("duration:" + string + ";;;size:" + j2 + ";;mduration:" + j3);
                j = j3;
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return j;
    }

    public static String getModifiedTime_1(File file) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(file.lastModified());
        return calendar.getTime().toLocaleString();
    }

    public static String getModifiedTime_2(File file) {
        Calendar calendar = Calendar.getInstance();
        long jLastModified = file.lastModified();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTimeInMillis(jLastModified);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getFileSuffix(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public static void deleteFiles(List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            deleteFile(it.next());
        }
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return deleteFile(new File(str));
    }

    public static String getTranscribeFilePath(String str) {
        return str.substring(0, str.lastIndexOf("/") + 1) + getFileNameWithoutExtension(str) + "_trans.json";
    }

    public static String getLabelFilePath(String str) {
        return str.substring(0, str.lastIndexOf("/") + 1) + getFileNameWithoutExtension(str) + "_label.json";
    }

    public static String getFilePathL(String str) {
        return str.substring(0, str.lastIndexOf("/") + 1) + getFileNameWithoutExtension(str) + "_L.wav";
    }

    public static String getFilePathF(String str) {
        return str.substring(0, str.lastIndexOf("/") + 1) + getFileNameWithoutExtension(str) + "_F.wav";
    }

    public static void localFileDelate(String str) {
        if (deleteFile("")) {
            LogUtil.m338i("本地文件删除成功:" + getFileNameWithoutExtension(""));
        } else {
            LogUtil.m338i("本地文件删除失败");
        }
    }

    public static void localFileDelete(String str) {
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        String folderName = getFolderName(str);
        LogUtil.m338i("文件名：" + getFileNameWithoutExtension(str) + "；文件夹名：" + getFileName(folderName));
        if (getFileNameWithoutExtension(str).equals(getFileName(folderName))) {
            if (deleteFile(folderName)) {
                LogUtil.m338i("本地文件夹删除成功:" + folderName);
                return;
            } else {
                LogUtil.m338i("本地文件夹删除失败");
                return;
            }
        }
        if (deleteFile(str)) {
            LogUtil.m338i("本地文件删除成功:" + getFileNameWithoutExtension(str));
        } else {
            LogUtil.m338i("本地文件删除失败");
        }
    }

    public static boolean deleteFile(File file) {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return true;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                file2.delete();
            } else if (file2.isDirectory()) {
                deleteFile(file2.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public static void delete(String str, FilenameFilter filenameFilter) {
        File[] fileArrListFiles;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                if (filenameFilter != null) {
                    fileArrListFiles = file.listFiles(filenameFilter);
                } else {
                    fileArrListFiles = file.listFiles();
                }
                if (fileArrListFiles == null) {
                    return;
                }
                for (File file2 : fileArrListFiles) {
                    if (file2.isFile()) {
                        file2.delete();
                    }
                }
            }
        }
    }

    public static void shareAudio(Activity activity, List<String> list) {
        ArrayList<Uri> arrayList = new ArrayList<>();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            Uri uri = UriToPathUtil.toUri(activity, it.next());
            if (uri == null) {
                return;
            } else {
                arrayList.add(uri);
            }
        }
        new Share2.Builder(activity).setContentType("audio/*").setShareFileUris(arrayList).setTitle("Share File").build().shareBySystem_multiple();
    }

    public static void shareAudio(Activity activity, String str) {
        Uri uri = UriToPathUtil.toUri(activity, str);
        if (uri == null) {
            return;
        }
        LogUtil.m334d("url:" + uri.toString());
        new Share2.Builder(activity).setContentType("audio/*").setShareFileUri(uri).setTitle(FileConstants.SHARE_ELEMENT).build().shareBySystem();
    }

    public static void shareFile(Activity activity, String str) {
        Uri uri = UriToPathUtil.toUri(activity, str);
        if (uri == null) {
            return;
        }
        LogUtil.m338i("uri=" + uri.toString());
        new Share2.Builder(activity).setContentType(ShareContentType.FILE).setShareFileUri(uri).setTitle("Share File").build().shareBySystem();
    }

    public static String setRootDir() {
        String str;
        if (Environment.getExternalStorageState().equals("mounted")) {
            str = Environment.getExternalStorageDirectory() + "/";
        } else {
            str = Environment.getDataDirectory() + "/";
        }
        String str2 = str + "com.aivox.app/";
        createRootDir(str2);
        return str2;
    }

    public static boolean createRootDir(String str) {
        boolean zCreateNewFile;
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        try {
            zCreateNewFile = file.createNewFile();
        } catch (IOException e) {
            LogUtil.m336e("e-createRootDir:" + e.toString());
            zCreateNewFile = false;
        }
        LogUtil.m336e("createRootDir=" + zCreateNewFile);
        return zCreateNewFile;
    }

    public void sendCSVByEmail(String str, String str2) {
        File file = new File(str + str2);
        if (!file.exists()) {
            Toast.makeText(BaseAppUtils.getContext(), "CSV不存在", 0).show();
            return;
        }
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("subject", strSubstring);
            intent.putExtra(LoggingKeys.BODY_KEY, "  ");
            Uri uriForFile = FileProvider.getUriForFile(BaseAppUtils.getContext(), "com.aivox.app.fileprovider", file);
            intent.setFlags(1);
            intent.setFlags(2);
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            BaseAppUtils.getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(BaseAppUtils.getContext(), "系统没有邮件客户端！", 0).show();
        }
    }

    public static void shareImage(Activity activity, String str) {
        Uri uri = UriToPathUtil.toUri(activity, str);
        if (uri == null) {
            return;
        }
        new Share2.Builder(activity).setContentType("image/*").setShareFileUri(uri).setTitle("Share Image").build().shareBySystem();
    }

    public static void shareImageCircle(Activity activity, String str) {
        Uri uri = UriToPathUtil.toUri(activity, str);
        if (uri == null) {
            return;
        }
        new Share2.Builder(activity).setContentType("image/*").setShareFileUri(uri).setShareToComponent(WECHAT_APP_PACKAGE, WECHAT_CIRCLE).setTitle("Share Image To WeChat Circle").build().shareBySystem();
    }

    public static void shareTxt(Activity activity, String str) {
        new Share2.Builder(activity).setContentType("text/plain").setTextContent(str).setTitle("Share Text").build().shareBySystem();
    }
}
