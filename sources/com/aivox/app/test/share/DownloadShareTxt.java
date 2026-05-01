package com.aivox.app.test.share;

import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.aivox.base.C0874R;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.download.DownloadUtil;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.util.StringUtil;
import com.lzy.okgo.model.HttpHeaders;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DownloadShareTxt implements LifecycleObserver {
    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_ERROR = 3;
    private static final int DOWNLOAD_SUCCESS = 2;
    public static final int TYPE_TXT = 1;
    public static final int TYPE_WORD = 0;
    private IDownloadShareListener iDownloadShareListener;
    private boolean isProcessing;
    private FragmentActivity mAc;
    private DownloadUtil mDownloadUtil;
    private int mErrorCount;
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.aivox.app.test.share.DownloadShareTxt.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                int i2 = message.arg1;
                if (DownloadShareTxt.this.iDownloadShareListener != null) {
                    DownloadShareTxt.this.iDownloadShareListener.onProgress(i2);
                }
            } else if (i != 2) {
                if (i == 3 && DownloadShareTxt.this.mAc != null) {
                    String str = DownloadShareTxt.this.mAc.getString(C0874R.string.try_again) + ": " + ((String) message.obj);
                    if (DownloadShareTxt.this.iDownloadShareListener != null) {
                        DownloadShareTxt.this.iDownloadShareListener.onError(str);
                    }
                    DownloadShareTxt.this.isProcessing = false;
                }
            } else {
                File file = (File) message.obj;
                if (DownloadShareTxt.this.iDownloadShareListener != null) {
                    DownloadShareTxt.this.iDownloadShareListener.onSuccess(file);
                }
                DownloadShareTxt.this.isProcessing = false;
            }
            return false;
        }
    });

    public interface IDownloadShareListener {
        void onError(String str);

        void onProgress(int i);

        void onSuccess(File file);
    }

    static /* synthetic */ int access$108(DownloadShareTxt downloadShareTxt) {
        int i = downloadShareTxt.mErrorCount;
        downloadShareTxt.mErrorCount = i + 1;
        return i;
    }

    private void destroy() {
        DownloadUtil downloadUtil = this.mDownloadUtil;
        if (downloadUtil != null) {
            downloadUtil.destroy();
            this.mDownloadUtil = null;
        }
        FragmentActivity fragmentActivity = this.mAc;
        if (fragmentActivity != null) {
            fragmentActivity.getLifecycle().removeObserver(this);
            this.mAc = null;
        }
        this.isProcessing = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        LogUtil.m338i("lifecycle onDestroy");
        destroy();
    }

    public void download(FragmentActivity fragmentActivity, AudioInfoBean audioInfoBean, int i, IDownloadShareListener iDownloadShareListener) {
        this.iDownloadShareListener = iDownloadShareListener;
        this.mAc = fragmentActivity;
        if (this.isProcessing) {
            if (iDownloadShareListener != null) {
                iDownloadShareListener.onError(fragmentActivity.getString(C0874R.string.please_wait));
                return;
            }
            return;
        }
        this.isProcessing = true;
        fragmentActivity.getLifecycle().addObserver(this);
        String str = StringUtil.getHttpUrl() + "/service-audio/export-document?audioId=" + audioInfoBean.getId() + "&exportType=" + i;
        LogUtil.m337e("EXPORT", str);
        String title = audioInfoBean.getTitle();
        if (i == 0) {
            title = title + ".docx";
        } else if (i == 1) {
            title = title + ".txt";
        }
        String str2 = title;
        if (this.mDownloadUtil == null) {
            this.mDownloadUtil = new DownloadUtil();
        }
        this.mDownloadUtil.downloadAndSetHeader(str, getHeader(), fragmentActivity.getExternalCacheDir().getAbsolutePath(), str2, new DownloadUtil.OnDownloadListener() { // from class: com.aivox.app.test.share.DownloadShareTxt.1
            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloadSuccess(File file) {
                Message message = new Message();
                message.what = 2;
                message.obj = file;
                DownloadShareTxt.this.mHandler.sendMessage(message);
            }

            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloading(long j, long j2) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = (int) ((j / j2) * 100.0f);
                DownloadShareTxt.this.mHandler.sendMessage(message);
            }

            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloadFailed(Exception exc) {
                Message message = new Message();
                message.what = 3;
                message.obj = exc.getLocalizedMessage();
                DownloadShareTxt.this.mHandler.sendMessage(message);
                DownloadShareTxt.access$108(DownloadShareTxt.this);
                if (DownloadShareTxt.this.mErrorCount > 3) {
                    BaseAppUtils.printErrorMsg(exc, "下载文档多次异常,errorCount=" + DownloadShareTxt.this.mErrorCount);
                    DownloadShareTxt.this.mErrorCount = 0;
                }
            }
        });
    }

    private HashMap<String, Object> getHeader() {
        String str = (String) SPUtil.get(SPUtil.TOKEN, "");
        HashMap<String, Object> map = new HashMap<>();
        map.put(SPUtil.TOKEN, str);
        map.put(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, getLanguage());
        map.put("MobileType", "1");
        return map;
    }

    private String getLanguage() {
        Locale languageLocale = MultiLanguageUtil.getInstance().getLanguageLocale();
        String language = languageLocale.getLanguage();
        if (!language.equalsIgnoreCase("zh") || languageLocale.getCountry().equalsIgnoreCase("cn")) {
            return (language.equalsIgnoreCase("zh") || language.equalsIgnoreCase("en") || language.equalsIgnoreCase("jp")) ? language : "zh";
        }
        return "tc";
    }
}
