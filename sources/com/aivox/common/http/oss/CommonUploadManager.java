package com.aivox.common.http.oss;

import android.content.Context;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.util.FileUtils;
import com.luck.picture.lib.config.PictureMimeType;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class CommonUploadManager {
    public static String dir = "";
    private static CommonUploadManager mInstance;

    public static CommonUploadManager getInstance() {
        if (mInstance == null) {
            synchronized (CommonUploadManager.class) {
                mInstance = new CommonUploadManager();
            }
        }
        return mInstance;
    }

    public void startUpload(Context context, int i, String str, String str2, String str3, OnUploadListener onUploadListener, int i2) {
        if (BaseGlobalConfig.isMainland()) {
            QCloudManager.getInstance().multipartUpload2(context, i, str, str2, str3, onUploadListener, i2, getDir(str2, str3, str, i2));
        } else {
            new AzureManager(context, i, str, onUploadListener, getDir(str2, str3, str, i2)).doUpload();
        }
    }

    public String getDir(String str, String str2, String str3, int i) {
        if (i == Constant.TYPE_AUDIO) {
            dir = "audio";
        } else if (i == Constant.TYPE_USER_AVATAR) {
            dir = "avatar";
        } else if (i == Constant.TYPE_AUDIO_IMG) {
            dir = PictureMimeType.MIME_TYPE_PREFIX_IMAGE;
        } else if (i == Constant.TYPE_LOG) {
            dir = FileUtils.LOG;
        } else if (i == Constant.TYPE_AI_CHAT) {
            dir = "ai-chat";
        } else {
            dir = FileUtils.MEMO;
        }
        String str4 = dir + "/" + str + "/" + str2 + "/" + UUID.randomUUID().toString().substring(0, 8).replace("-", "") + FileUtils.getFileName(str3);
        dir = str4;
        return str4;
    }
}
