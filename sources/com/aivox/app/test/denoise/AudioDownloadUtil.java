package com.aivox.app.test.denoise;

import android.content.Context;
import com.aivox.base.C0874R;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.download.DownloadUtil;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class AudioDownloadUtil {
    private static final String DOWNLOAD_DIR = "download";
    private DownloadUtil downloadUtil;

    public interface IAudioDownloadListener {
        void onComplete(File file);

        void onError(String str);

        void onPrepare(String str);

        void onProgress(long j, long j2);
    }

    private File getAudioDownloadDir(Context context) {
        return new File(FileUtils.getAppPath(context).toString(), "download");
    }

    public void destroy() {
        DownloadUtil downloadUtil = this.downloadUtil;
        if (downloadUtil != null) {
            downloadUtil.destroy();
        }
    }

    public void start(Context context, String str, final IAudioDownloadListener iAudioDownloadListener) {
        try {
            String fileNameFromUrl = UrlUtil.getFileNameFromUrl(str);
            if (fileNameFromUrl.contains("file_name=")) {
                fileNameFromUrl = fileNameFromUrl.split("file_name=")[1];
            }
            final File file = new File(getAudioDownloadDir(context), fileNameFromUrl);
            if (file.exists()) {
                iAudioDownloadListener.onComplete(file);
                return;
            }
            iAudioDownloadListener.onPrepare(str);
            DownloadUtil downloadUtil = new DownloadUtil();
            this.downloadUtil = downloadUtil;
            downloadUtil.downloadSync(str, getAudioDownloadDir(context).getAbsolutePath(), fileNameFromUrl, new DownloadUtil.OnDownloadListener() { // from class: com.aivox.app.test.denoise.AudioDownloadUtil.1
                @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
                public void onDownloadSuccess(File file2) {
                    iAudioDownloadListener.onComplete(file2);
                }

                @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
                public void onDownloading(long j, long j2) {
                    iAudioDownloadListener.onProgress(j, j2);
                }

                @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
                public void onDownloadFailed(Exception exc) {
                    exc.printStackTrace();
                    iAudioDownloadListener.onError(exc.getLocalizedMessage());
                    if (file.exists() && file.isFile()) {
                        file.delete();
                        LogUtil.m338i("删除文件：" + file.getAbsolutePath());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.m336e("解析文件名失败，error:" + e.getLocalizedMessage() + ", url: " + str);
            iAudioDownloadListener.onError(context.getString(C0874R.string.denoise_parse_file_name_error) + ": " + e.getLocalizedMessage());
        }
    }
}
