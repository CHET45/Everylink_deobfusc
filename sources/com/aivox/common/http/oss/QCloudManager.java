package com.aivox.common.http.oss;

import android.content.Context;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.OssAccessBean;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.InitiateMultipartUpload;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.InitMultipleUploadListener;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudLifecycleCredentials;
import com.tencent.qcloud.core.auth.SessionQCloudCredentials;
import com.tencent.qcloud.core.common.QCloudClientException;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class QCloudManager {
    static String bucket = "smalink-1302668744";
    private static QCloudManager mInstance = null;
    private static String region = "ap-shanghai";
    long ossTotalSize;

    static /* synthetic */ void lambda$upload$4(TransferState transferState) {
    }

    public static QCloudManager getInstance() {
        if (mInstance == null) {
            synchronized (QCloudManager.class) {
                mInstance = new QCloudManager();
            }
        }
        return mInstance;
    }

    public void multipartUpload2(final Context context, final int i, final String str, final String str2, final String str3, final OnUploadListener onUploadListener, final int i2, final String str4) {
        if (DateUtil.UTC2Timestamp((String) SPUtil.get(SPUtil.QCLOUD_EXP, ""), DateUtil.YYYY_MM_DD_T_HH_MM_SS) < System.currentTimeMillis() + 3600000) {
            new AudioService(context).getTencentCosKey().subscribe(new Consumer() { // from class: com.aivox.common.http.oss.QCloudManager$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m346x145e8b50(context, i, str, str2, str3, onUploadListener, i2, str4, (String) obj);
                }
            }, new Consumer() { // from class: com.aivox.common.http.oss.QCloudManager$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m347xa0feb651(context, i, str, str2, str3, onUploadListener, i2, str4, (Throwable) obj);
                }
            });
        } else {
            upload(context, i, str, str2, str3, onUploadListener, i2, str4);
        }
    }

    /* JADX INFO: renamed from: lambda$multipartUpload2$0$com-aivox-common-http-oss-QCloudManager */
    /* synthetic */ void m346x145e8b50(Context context, int i, String str, String str2, String str3, OnUploadListener onUploadListener, int i2, String str4, String str5) throws Exception {
        if (BaseStringUtil.isEmpty(SerAESUtil.decrypt(str5, Constant.DECRYPT_KEY))) {
            return;
        }
        OssAccessBean ossAccessBean = (OssAccessBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(str5, Constant.DECRYPT_KEY), OssAccessBean.class);
        SPUtil.put(SPUtil.QCLOUD_ID, ossAccessBean.getTmpSecretId());
        SPUtil.put(SPUtil.QCLOUD_SK, ossAccessBean.getTmpSecretKey());
        SPUtil.put(SPUtil.QCLOUD_TOKEN, ossAccessBean.getSessionToken());
        SPUtil.put(SPUtil.QCLOUD_EXP, ossAccessBean.getExpiration());
        SPUtil.put(SPUtil.QCLOUD_START, Long.valueOf(ossAccessBean.getStartTime()));
        SPUtil.put(SPUtil.QCLOUD_BUCK_NAME, ossAccessBean.getBuckName());
        SPUtil.put(SPUtil.QCLOUD_REGION, ossAccessBean.getRegion());
        upload(context, i, str, str2, str3, onUploadListener, i2, str4);
    }

    /* JADX INFO: renamed from: lambda$multipartUpload2$1$com-aivox-common-http-oss-QCloudManager */
    /* synthetic */ void m347xa0feb651(Context context, int i, String str, String str2, String str3, OnUploadListener onUploadListener, int i2, String str4, Throwable th) throws Exception {
        upload(context, i, str, str2, str3, onUploadListener, i2, str4);
    }

    private void upload(Context context, final int i, final String str, String str2, String str3, final OnUploadListener onUploadListener, int i2, String str4) {
        COSXMLUploadTask cOSXMLUploadTaskUpload = new TransferManager(new CosXmlService(context, new CosXmlServiceConfig.Builder().setRegion((String) SPUtil.get(SPUtil.QCLOUD_REGION, region)).isHttps(true).builder(), new MySessionCredentialProvider()), new TransferConfig.Builder().build()).upload((String) SPUtil.get(SPUtil.QCLOUD_BUCK_NAME, bucket), str4, str, (String) null);
        cOSXMLUploadTaskUpload.setInitMultipleUploadListener(new InitMultipleUploadListener() { // from class: com.aivox.common.http.oss.QCloudManager$$ExternalSyntheticLambda0
            @Override // com.tencent.cos.xml.transfer.InitMultipleUploadListener
            public final void onSuccess(InitiateMultipartUpload initiateMultipartUpload) {
                initiateMultipartUpload.uploadId;
            }
        });
        cOSXMLUploadTaskUpload.setCosXmlProgressListener(new CosXmlProgressListener() { // from class: com.aivox.common.http.oss.QCloudManager$$ExternalSyntheticLambda1
            @Override // com.tencent.qcloud.core.common.QCloudProgressListener
            public final void onProgress(long j, long j2) {
                this.f$0.m2448lambda$upload$3$comaivoxcommonhttpossQCloudManager(onUploadListener, i, j, j2);
            }
        });
        cOSXMLUploadTaskUpload.setCosXmlResultListener(new CosXmlResultListener() { // from class: com.aivox.common.http.oss.QCloudManager.1
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                OnUploadListener onUploadListener2 = onUploadListener;
                if (onUploadListener2 != null) {
                    onUploadListener2.onSuccess(i, str, cosXmlResult.accessUrl, QCloudManager.this.ossTotalSize);
                }
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                OnUploadListener onUploadListener2 = onUploadListener;
                if (onUploadListener2 != null) {
                    onUploadListener2.onFailure(i);
                }
                if (cosXmlClientException != null) {
                    cosXmlClientException.printStackTrace();
                    LogUtil.m334d(cosXmlClientException.toString());
                } else {
                    cosXmlServiceException.printStackTrace();
                    LogUtil.m334d(cosXmlServiceException.toString());
                }
            }
        });
        cOSXMLUploadTaskUpload.setTransferStateListener(new TransferStateListener() { // from class: com.aivox.common.http.oss.QCloudManager$$ExternalSyntheticLambda2
            @Override // com.tencent.cos.xml.transfer.TransferStateListener
            public final void onStateChanged(TransferState transferState) {
                QCloudManager.lambda$upload$4(transferState);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$upload$3$com-aivox-common-http-oss-QCloudManager, reason: not valid java name */
    /* synthetic */ void m2448lambda$upload$3$comaivoxcommonhttpossQCloudManager(OnUploadListener onUploadListener, int i, long j, long j2) {
        if (onUploadListener != null) {
            onUploadListener.onProgress(i, j, j2, (int) ((100 * j) / j2));
        }
        this.ossTotalSize = j2;
    }

    public static class MySessionCredentialProvider extends BasicLifecycleCredentialProvider {
        @Override // com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider
        protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {
            return new SessionQCloudCredentials((String) SPUtil.get(SPUtil.QCLOUD_ID, ""), (String) SPUtil.get(SPUtil.QCLOUD_SK, ""), (String) SPUtil.get(SPUtil.QCLOUD_TOKEN, ""), ((Long) SPUtil.get(SPUtil.QCLOUD_START, Long.valueOf(System.currentTimeMillis()))).longValue() / 1000, DateUtil.UTC2Timestamp((String) SPUtil.get(SPUtil.QCLOUD_EXP, ""), DateUtil.YYYY_MM_DD_T_HH_MM_SS));
        }
    }
}
