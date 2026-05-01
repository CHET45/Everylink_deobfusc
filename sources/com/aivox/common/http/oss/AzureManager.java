package com.aivox.common.http.oss;

import android.content.Context;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AzureInfoBean;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.StorageUri;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import io.reactivex.functions.Consumer;
import java.net.URI;

/* JADX INFO: loaded from: classes.dex */
public class AzureManager {
    private AzureInfoBean mAzureBen;
    private final Context mContext;
    private final String mFilePath;
    private final OnUploadListener mListener;
    private final String mObjKey;
    private final int mPos;

    public AzureManager(Context context, int i, String str, OnUploadListener onUploadListener, String str2) {
        this.mContext = context;
        this.mPos = i;
        this.mFilePath = str;
        this.mListener = onUploadListener;
        this.mObjKey = str2;
    }

    public void doUpload() {
        String str = (String) SPUtil.get(SPUtil.AZURE_DATA_STR, "");
        if (BaseStringUtil.isNotEmpty(str)) {
            LogUtil.m335d("AZURE", "有缓存");
            try {
                AzureInfoBean azureInfoBean = (AzureInfoBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(str, Constant.DECRYPT_KEY), AzureInfoBean.class);
                this.mAzureBen = azureInfoBean;
                if (Long.parseLong(azureInfoBean.getExpiredTime()) - System.currentTimeMillis() < 7200000) {
                    LogUtil.m335d("AZURE", "缓存过期");
                    new AudioService(this.mContext).getAzureInfo().subscribe(new Consumer() { // from class: com.aivox.common.http.oss.AzureManager$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) throws Exception {
                            this.f$0.m2444lambda$doUpload$0$comaivoxcommonhttpossAzureManager((String) obj);
                        }
                    }, new Consumer() { // from class: com.aivox.common.http.oss.AzureManager$$ExternalSyntheticLambda1
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) throws Exception {
                            this.f$0.m2445lambda$doUpload$1$comaivoxcommonhttpossAzureManager((Throwable) obj);
                        }
                    });
                } else {
                    LogUtil.m335d("AZURE", "缓存有效");
                    uploadFile();
                }
                return;
            } catch (Exception e) {
                LogUtil.m335d("AZURE", "上传失败 -> " + this.mObjKey + "\nERROR : " + e.getLocalizedMessage());
                SPUtil.put(SPUtil.AZURE_DATA_STR, "");
                this.mListener.onFailure(this.mPos);
                e.printStackTrace();
                return;
            }
        }
        LogUtil.m335d("AZURE", "无缓存");
        new AudioService(this.mContext).getAzureInfo().subscribe(new Consumer() { // from class: com.aivox.common.http.oss.AzureManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2446lambda$doUpload$2$comaivoxcommonhttpossAzureManager((String) obj);
            }
        }, new Consumer() { // from class: com.aivox.common.http.oss.AzureManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2447lambda$doUpload$3$comaivoxcommonhttpossAzureManager((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doUpload$0$com-aivox-common-http-oss-AzureManager, reason: not valid java name */
    /* synthetic */ void m2444lambda$doUpload$0$comaivoxcommonhttpossAzureManager(String str) throws Exception {
        SPUtil.put(SPUtil.AZURE_DATA_STR, str);
        this.mAzureBen = (AzureInfoBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(str, Constant.DECRYPT_KEY), AzureInfoBean.class);
        uploadFile();
    }

    /* JADX INFO: renamed from: lambda$doUpload$1$com-aivox-common-http-oss-AzureManager, reason: not valid java name */
    /* synthetic */ void m2445lambda$doUpload$1$comaivoxcommonhttpossAzureManager(Throwable th) throws Exception {
        LogUtil.m335d("AZURE", "上传失败 -> " + this.mObjKey + "\nERROR : " + th.getLocalizedMessage());
        SPUtil.put(SPUtil.AZURE_DATA_STR, "");
        this.mListener.onFailure(this.mPos);
        th.printStackTrace();
    }

    /* JADX INFO: renamed from: lambda$doUpload$2$com-aivox-common-http-oss-AzureManager, reason: not valid java name */
    /* synthetic */ void m2446lambda$doUpload$2$comaivoxcommonhttpossAzureManager(String str) throws Exception {
        SPUtil.put(SPUtil.AZURE_DATA_STR, str);
        this.mAzureBen = (AzureInfoBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(str, Constant.DECRYPT_KEY), AzureInfoBean.class);
        uploadFile();
    }

    /* JADX INFO: renamed from: lambda$doUpload$3$com-aivox-common-http-oss-AzureManager, reason: not valid java name */
    /* synthetic */ void m2447lambda$doUpload$3$comaivoxcommonhttpossAzureManager(Throwable th) throws Exception {
        LogUtil.m335d("AZURE", "获取 token -> " + this.mObjKey + "\nERROR : " + th.getLocalizedMessage());
        SPUtil.put(SPUtil.AZURE_DATA_STR, "");
        this.mListener.onFailure(this.mPos);
        th.printStackTrace();
    }

    private void uploadFile() {
        new Thread(new Runnable() { // from class: com.aivox.common.http.oss.AzureManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LogUtil.m335d("AZURE", "上传开始 -> " + AzureManager.this.mObjKey);
                    AzureManager.this.mListener.onProgress(AzureManager.this.mPos, 0L, FileUtils.getFileSize(AzureManager.this.mFilePath), 0);
                    CloudBlockBlob blockBlobReference = new CloudBlobClient(new StorageUri(new URI("https://" + AzureManager.this.mAzureBen.getAccountName() + ".blob.core.windows.net")), new StorageCredentialsSharedAccessSignature(AzureManager.this.mAzureBen.getSasToken())).getContainerReference(AzureManager.this.mAzureBen.getContainerName()).getBlockBlobReference(AzureManager.this.mObjKey);
                    blockBlobReference.uploadFromFile(AzureManager.this.mFilePath);
                    LogUtil.m335d("AZURE", "上传结束 -> " + AzureManager.this.mObjKey);
                    AzureManager.this.mListener.onProgress(AzureManager.this.mPos, FileUtils.getFileSize(AzureManager.this.mFilePath), FileUtils.getFileSize(AzureManager.this.mFilePath), 100);
                    AzureManager.this.mListener.onSuccess(AzureManager.this.mPos, AzureManager.this.mFilePath, blockBlobReference.getUri().toString(), FileUtils.getFileSize(AzureManager.this.mFilePath));
                } catch (Exception e) {
                    LogUtil.m335d("AZURE", "上传失败 -> " + AzureManager.this.mObjKey + "\nERROR : " + e.getLocalizedMessage());
                    AzureManager.this.mListener.onFailure(AzureManager.this.mPos);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
