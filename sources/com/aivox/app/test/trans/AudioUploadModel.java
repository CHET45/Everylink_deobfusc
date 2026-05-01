package com.aivox.app.test.trans;

import android.content.Context;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.Constant;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.EventBean;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class AudioUploadModel {
    private AudioInfoBean mAudioInfoBean;
    private WeakReference<Context> mContext;
    private EventBean<Object> mEventBean;
    private boolean mNeedTrans;
    private boolean mUploading;

    /* synthetic */ AudioUploadModel(C08371 c08371) {
        this();
    }

    private AudioUploadModel() {
    }

    private static final class InstanceHolder {
        static final AudioUploadModel mInstance = new AudioUploadModel(null);

        private InstanceHolder() {
        }
    }

    public static AudioUploadModel getInstance() {
        return InstanceHolder.mInstance;
    }

    public void checkAndUpload(Context context, AudioInfoBean audioInfoBean, boolean z, boolean z2) {
        if (this.mUploading) {
            if (z) {
                ToastUtil.showLong(Integer.valueOf(C0874R.string.another_upload_on_progressing));
                return;
            }
            return;
        }
        this.mUploading = true;
        this.mNeedTrans = z2;
        this.mContext = new WeakReference<>(context);
        this.mAudioInfoBean = audioInfoBean;
        String localPath = audioInfoBean.getLocalPath();
        EventBean<Object> eventBean = new EventBean<>(200);
        this.mEventBean = eventBean;
        eventBean.setA(this.mAudioInfoBean.getId());
        if (!FileUtils.isFileExist(localPath)) {
            uploadFailed(this.mContext.get().getString(C0874R.string.toast_file_does_not_exist_or_deleted));
        } else {
            new UserService(this.mContext.get()).checkCloudSpace(FileUtils.getFileSizeKb(localPath)).subscribe(new Consumer() { // from class: com.aivox.app.test.trans.AudioUploadModel$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m326x73f1c899((Boolean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.test.trans.AudioUploadModel$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m327x5933375a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$checkAndUpload$0$com-aivox-app-test-trans-AudioUploadModel */
    /* synthetic */ void m326x73f1c899(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            upload2Oss();
        } else {
            uploadFailed(this.mContext.get().getString(C0874R.string.me_cloud_full_sysc_unable));
        }
    }

    /* JADX INFO: renamed from: lambda$checkAndUpload$1$com-aivox-app-test-trans-AudioUploadModel */
    /* synthetic */ void m327x5933375a(Throwable th) throws Exception {
        uploadFailed(th.getLocalizedMessage());
    }

    private void upload2Oss() {
        this.mEventBean.setFrom(200);
        EventBus.getDefault().post(this.mEventBean);
        CommonUploadManager.getInstance().startUpload(this.mContext.get(), 0, this.mAudioInfoBean.getLocalPath(), (String) SPUtil.get(SPUtil.USER_ID, "0"), DateUtil.getCurDate(DateUtil.YYYYMMDD), new C08371(), Constant.TYPE_AUDIO);
    }

    /* JADX INFO: renamed from: com.aivox.app.test.trans.AudioUploadModel$1 */
    class C08371 implements OnUploadListener {
        C08371() {
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onProgress(int i, long j, long j2, int i2) {
            LogUtil.m338i("onProgress:" + i + "  " + i2 + "% " + j + "/" + j2);
            AudioUploadModel.this.mEventBean.setFrom(Constant.EVENT.AUDIO_UPLOAD_PROGRESS);
            AudioUploadModel.this.mEventBean.setB(i2);
            EventBus.getDefault().post(AudioUploadModel.this.mEventBean);
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onSuccess(int i, String str, final String str2, long j) {
            final long fileSizeKb = FileUtils.getFileSizeKb(AudioUploadModel.this.mAudioInfoBean.getLocalPath());
            new AudioService((Context) AudioUploadModel.this.mContext.get()).syncFile(AudioUploadModel.this.mAudioInfoBean.getId(), str2, fileSizeKb).subscribe(new Consumer() { // from class: com.aivox.app.test.trans.AudioUploadModel$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2375lambda$onSuccess$0$comaivoxapptesttransAudioUploadModel$1(fileSizeKb, str2, obj);
                }
            }, new Consumer() { // from class: com.aivox.app.test.trans.AudioUploadModel$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2376lambda$onSuccess$1$comaivoxapptesttransAudioUploadModel$1(fileSizeKb, str2, (Throwable) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-test-trans-AudioUploadModel$1, reason: not valid java name */
        /* synthetic */ void m2375lambda$onSuccess$0$comaivoxapptesttransAudioUploadModel$1(long j, String str, Object obj) throws Exception {
            AudioUploadModel.this.mUploading = false;
            AudioUploadModel.this.mEventBean.setFrom(Constant.EVENT.AUDIO_UPLOAD_COMPLETE);
            AudioUploadModel.this.mEventBean.setD(j);
            AudioUploadModel.this.mEventBean.setS1(str);
            AudioUploadModel.this.mEventBean.setTrue(AudioUploadModel.this.mNeedTrans);
            EventBus.getDefault().post(AudioUploadModel.this.mEventBean);
        }

        /* JADX INFO: renamed from: lambda$onSuccess$1$com-aivox-app-test-trans-AudioUploadModel$1, reason: not valid java name */
        /* synthetic */ void m2376lambda$onSuccess$1$comaivoxapptesttransAudioUploadModel$1(long j, String str, Throwable th) throws Exception {
            if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.THE_AUDIO_ALREADY_SYNC.code) {
                AudioUploadModel.this.mUploading = false;
                AudioUploadModel.this.mEventBean.setFrom(Constant.EVENT.AUDIO_UPLOAD_COMPLETE);
                AudioUploadModel.this.mEventBean.setD(j);
                AudioUploadModel.this.mEventBean.setS1(str);
                AudioUploadModel.this.mEventBean.setTrue(AudioUploadModel.this.mNeedTrans);
                EventBus.getDefault().post(AudioUploadModel.this.mEventBean);
                return;
            }
            AudioUploadModel.this.uploadFailed(th.getLocalizedMessage());
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onFailure(int i) {
            AudioUploadModel audioUploadModel = AudioUploadModel.this;
            audioUploadModel.uploadFailed(((Context) audioUploadModel.mContext.get()).getString(C0874R.string.audio_sync_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadFailed(String str) {
        this.mUploading = false;
        this.mEventBean.setFrom(Constant.EVENT.AUDIO_UPLOAD_FAILED);
        this.mEventBean.setS1(str);
        EventBus.getDefault().post(this.mEventBean);
    }

    public void destroy() {
        this.mUploading = false;
    }
}
