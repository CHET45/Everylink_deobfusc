package com.aivox.app.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityRecordUploadBinding;
import com.aivox.base.C0874R;
import com.aivox.base.http.HttpException;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.UploadAudioBean;
import com.aivox.common.model.UploadAudioInfo;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class RecordUploadActivity extends BaseFragmentActivity {
    private LocalFileDbManager localFileDbManager;
    private ObjectAnimator mAnimator;
    private ActivityRecordUploadBinding mBinding;
    private final CompositeDisposable mDis = new CompositeDisposable();
    private UploadAudioInfo uploadAudioInfo;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.localFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.mBinding = (ActivityRecordUploadBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_record_upload);
        AudioInfoBean audioInfoBean = (AudioInfoBean) getIntent().getExtras().getSerializable("data");
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this.mBinding.pbProgress, "progress", 100);
        this.mAnimator = objectAnimatorOfInt;
        objectAnimatorOfInt.setDuration(5000L);
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.m2218lambda$initView$0$comaivoxappactivityRecordUploadActivity(valueAnimator);
            }
        });
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2219lambda$initView$1$comaivoxappactivityRecordUploadActivity(view2);
            }
        });
        this.mBinding.btnFinish.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2220lambda$initView$2$comaivoxappactivityRecordUploadActivity(view2);
            }
        });
        if (audioInfoBean != null) {
            this.mBinding.tvFileName.setText(audioInfoBean.getTitle());
            this.mAnimator.start();
            m217xe668af94(audioInfoBean);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-RecordUploadActivity, reason: not valid java name */
    /* synthetic */ void m2218lambda$initView$0$comaivoxappactivityRecordUploadActivity(ValueAnimator valueAnimator) {
        this.mBinding.tvProgress.setText(String.format(getString(C0874R.string.record_loading), Integer.valueOf(((Integer) this.mAnimator.getAnimatedValue()).intValue())));
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-RecordUploadActivity, reason: not valid java name */
    /* synthetic */ void m2219lambda$initView$1$comaivoxappactivityRecordUploadActivity(View view2) {
        onBackPressed();
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-RecordUploadActivity, reason: not valid java name */
    /* synthetic */ void m2220lambda$initView$2$comaivoxappactivityRecordUploadActivity(View view2) {
        onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: addLocalRecord, reason: merged with bridge method [inline-methods] */
    public void m217xe668af94(final AudioInfoBean audioInfoBean) {
        String localPath = audioInfoBean.getLocalPath();
        UploadAudioInfo uploadAudioInfo = new UploadAudioInfo();
        this.uploadAudioInfo = uploadAudioInfo;
        uploadAudioInfo.setAudioUrl("");
        this.uploadAudioInfo.setLocalPath(localPath);
        this.uploadAudioInfo.setAudioLength(FileUtils.getFileSizeKb(localPath));
        this.uploadAudioInfo.setAudioTimeDuration((int) FileUtils.getAudioFileVoiceTime(localPath));
        this.mDis.add(new AudioService(this).addRecordFromLocal(initUploadAudioBean(audioInfoBean.getTitle())).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.m220x2aad2abc((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m218x81097215(audioInfoBean, (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$addLocalRecord$4$com-aivox-app-activity-RecordUploadActivity */
    /* synthetic */ void m218x81097215(final AudioInfoBean audioInfoBean, Throwable th) throws Exception {
        LogUtil.m338i("putAudio_thr:" + th.getLocalizedMessage());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m217xe668af94(audioInfoBean);
                }
            });
        }
    }

    private UploadAudioBean initUploadAudioBean(String str) {
        UploadAudioBean uploadAudioBean = new UploadAudioBean();
        uploadAudioBean.setTitle(str);
        uploadAudioBean.setIsTop(0);
        uploadAudioBean.setUploadAudioInfo(this.uploadAudioInfo);
        return uploadAudioBean;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getAudioInfoById, reason: merged with bridge method [inline-methods] */
    public void m220x2aad2abc(final Integer num) {
        this.mAnimator.pause();
        this.mDis.add(new AudioService(this).recordDetails(num.intValue()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m219x900c683b((AudioInfoBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m221xc54ded3d(num, (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$5$com-aivox-app-activity-RecordUploadActivity */
    /* synthetic */ void m219x900c683b(AudioInfoBean audioInfoBean) throws Exception {
        this.mAnimator.resume();
        this.localFileDbManager.insertOrReplace(new LocalFileEntity(audioInfoBean));
        EventBus.getDefault().post(new EventBean(50));
        this.mBinding.btnFinish.setEnabled(true);
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$7$com-aivox-app-activity-RecordUploadActivity */
    /* synthetic */ void m221xc54ded3d(final Integer num, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda4
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m220x2aad2abc(num);
                }
            });
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mBinding.pbProgress.getProgress() == 100) {
            super.onBackPressed();
        } else {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.upload_not_complete), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordUploadActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m222x8b67f0e0(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true, C0874R.string.cancel, C0874R.string.confirm);
        }
    }

    /* JADX INFO: renamed from: lambda$onBackPressed$8$com-aivox-app-activity-RecordUploadActivity */
    /* synthetic */ void m222x8b67f0e0(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        finish();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mDis.clear();
        this.mAnimator.cancel();
    }
}
