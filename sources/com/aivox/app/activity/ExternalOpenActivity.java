package com.aivox.app.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityExternalOpenBinding;
import com.aivox.base.C0874R;
import com.aivox.base.http.HttpException;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SDCardUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.base.util.UriToPathUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.UploadAudioBean;
import com.aivox.common.model.UploadAudioInfo;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.net.URISyntaxException;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class ExternalOpenActivity extends BaseFragmentActivity {
    private String fileName;
    private LocalFileDbManager localFileDbManager;
    private ActivityExternalOpenBinding mBinding;
    private SQLiteDataBaseManager manager;
    private String newPath;
    private String oldPath;
    private UploadAudioInfo uploadAudioInfo;
    private UserInfo userInfo;

    static /* synthetic */ void lambda$startAct$10(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$11(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$12(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$14(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$16(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$17(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$18(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$19(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$8(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$startAct$9(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityExternalOpenBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_external_open);
        this.manager = new SQLiteDataBaseManager(this.context);
        this.localFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.userInfo = this.manager.getUserInfo();
        PermissionUtils.getIns(this, new C07591()).request("android.permission.READ_MEDIA_AUDIO");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.ExternalOpenActivity$1 */
    class C07591 implements PermissionCallback {
        C07591() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                Intent intent = ExternalOpenActivity.this.getIntent();
                String action = intent.getAction();
                if ("android.intent.action.SEND".equals(action) || "android.intent.action.VIEW".equals(action)) {
                    if (intent.getData() == null && intent.getClipData() == null) {
                        return;
                    }
                    Uri[] uriArrProcessData = ExternalOpenActivity.this.processData(intent);
                    if (uriArrProcessData != null && uriArrProcessData.length > 0) {
                        ExternalOpenActivity externalOpenActivity = ExternalOpenActivity.this;
                        externalOpenActivity.oldPath = UriToPathUtil.getFileAbsolutePath(externalOpenActivity, uriArrProcessData[0]);
                    }
                    if (BaseStringUtil.isEmpty(ExternalOpenActivity.this.oldPath)) {
                        return;
                    }
                    ExternalOpenActivity externalOpenActivity2 = ExternalOpenActivity.this;
                    externalOpenActivity2.fileName = externalOpenActivity2.oldPath.substring(ExternalOpenActivity.this.oldPath.lastIndexOf("/") + 1);
                    ExternalOpenActivity.this.mBinding.tvName.setText(ExternalOpenActivity.this.fileName);
                    if (SDCardUtil.getFreeKbs() < FileUtils.getFileSizeKb(ExternalOpenActivity.this.oldPath)) {
                        DialogUtils.showMsgNoBtn(ExternalOpenActivity.this, Integer.valueOf(C0874R.string.reminder), ExternalOpenActivity.this.getString(C0874R.string.storage_full), true);
                        ExternalOpenActivity.this.mBinding.tvImport.setVisibility(8);
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= 29) {
                        String str = System.currentTimeMillis() + "." + FileUtils.getFileExtension(ExternalOpenActivity.this.oldPath);
                        com.blankj.utilcode.util.FileUtils.rename(new File(ExternalOpenActivity.this.oldPath), str);
                        ExternalOpenActivity.this.newPath = new File(ExternalOpenActivity.this.oldPath).getParent() + File.separator + str;
                    } else {
                        ExternalOpenActivity externalOpenActivity3 = ExternalOpenActivity.this;
                        externalOpenActivity3.newPath = FileUtils.copyAndRenameFile(externalOpenActivity3.oldPath, FileUtils.getAppPath(ExternalOpenActivity.this, FileUtils.Audio_From_Other), System.currentTimeMillis() + "");
                    }
                    ExternalOpenActivity.this.mBinding.tvAudioTime.setText(DateUtil.numberToTime((int) FileUtils.getAudioFileVoiceTime(ExternalOpenActivity.this.newPath)));
                    ExternalOpenActivity.this.mBinding.tvAudioSize.setText(BaseStringUtil.kbToMbFormat(FileUtils.getFileSizeKb(ExternalOpenActivity.this.newPath)));
                    ExternalOpenActivity.this.mBinding.tvImport.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$1$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            this.f$0.m2117lambda$granted$0$comaivoxappactivityExternalOpenActivity$1(view2);
                        }
                    });
                    return;
                }
                return;
            }
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-ExternalOpenActivity$1, reason: not valid java name */
        /* synthetic */ void m2117lambda$granted$0$comaivoxappactivityExternalOpenActivity$1(View view2) {
            ExternalOpenActivity externalOpenActivity = ExternalOpenActivity.this;
            externalOpenActivity.startAct(externalOpenActivity.newPath);
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            BaseAppUtils.openSettingView(ExternalOpenActivity.this.context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Uri[] processData(Intent intent) {
        Uri[] uriArr = null;
        if (intent == null) {
            return null;
        }
        String dataString = intent.getDataString();
        if (BaseStringUtil.isNotEmpty(dataString)) {
            return new Uri[]{Uri.parse(dataString)};
        }
        ClipData clipData = intent.getClipData();
        if (clipData != null && clipData.getItemCount() > 0) {
            uriArr = new Uri[clipData.getItemCount()];
            for (int i = 0; i < clipData.getItemCount(); i++) {
                uriArr[i] = clipData.getItemAt(i).getUri();
            }
        }
        return uriArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: startImport, reason: merged with bridge method [inline-methods] */
    public void m2115lambda$startImport$1$comaivoxappactivityExternalOpenActivity(final String str, final String str2) {
        DialogUtils.showLoadingDialog(this);
        this.mBinding.tvImport.setClickable(false);
        new UserService(this).checkCloudSpace(FileUtils.getFileSizeKb(str2)).subscribe(new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2114lambda$startImport$0$comaivoxappactivityExternalOpenActivity(str2, str, (Boolean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2116lambda$startImport$2$comaivoxappactivityExternalOpenActivity(str, str2, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startImport$0$com-aivox-app-activity-ExternalOpenActivity, reason: not valid java name */
    /* synthetic */ void m2114lambda$startImport$0$comaivoxappactivityExternalOpenActivity(String str, String str2, Boolean bool) throws Exception {
        initUploadAudioInfo(str);
        m141x9f042030(str2);
        DialogUtils.hideLoadingDialog();
    }

    /* JADX INFO: renamed from: lambda$startImport$2$com-aivox-app-activity-ExternalOpenActivity, reason: not valid java name */
    /* synthetic */ void m2116lambda$startImport$2$comaivoxappactivityExternalOpenActivity(final String str, final String str2, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda9
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2115lambda$startImport$1$comaivoxappactivityExternalOpenActivity(str, str2);
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.userInfo = this.manager.getUserInfo();
    }

    private void initUploadAudioInfo(String str) {
        UploadAudioInfo uploadAudioInfo = new UploadAudioInfo();
        this.uploadAudioInfo = uploadAudioInfo;
        uploadAudioInfo.setAudioUrl("");
        this.uploadAudioInfo.setLocalPath(str);
        this.uploadAudioInfo.setAudioLength(FileUtils.getFileSizeKb(str));
        this.uploadAudioInfo.setAudioTimeDuration((int) FileUtils.getAudioFileVoiceTime(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqAddRecord, reason: merged with bridge method [inline-methods] */
    public void m141x9f042030(final String str) {
        DialogUtils.showLoadingDialog(this);
        new AudioService(this).addRecordFromLocal(initUploadAudioBean(str)).subscribe(new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.m139x42aa72bf((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m142x39a4e2b1(str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqAddRecord$4$com-aivox-app-activity-ExternalOpenActivity */
    /* synthetic */ void m142x39a4e2b1(final String str, Throwable th) throws Exception {
        LogUtil.m338i("putAudio_thr:" + th.getLocalizedMessage());
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda11
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m141x9f042030(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getAudioInfoById, reason: merged with bridge method [inline-methods] */
    public void m139x42aa72bf(final Integer num) {
        new AudioService(this).recordDetails(num.intValue()).subscribe(new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m138xa809b03e((AudioInfoBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m140xdd4b3540(num, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$5$com-aivox-app-activity-ExternalOpenActivity */
    /* synthetic */ void m138xa809b03e(AudioInfoBean audioInfoBean) throws Exception {
        this.localFileDbManager.insertOrReplace(new LocalFileEntity(audioInfoBean));
        EventBus.getDefault().post(new EventBean(50));
        ARouterUtils.startWithActivity(this, BaseAppUtils.isLogin() ? MainAction.MAIN : AccountAction.ONE_KEY_LOGIN);
        finish();
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$7$com-aivox-app-activity-ExternalOpenActivity */
    /* synthetic */ void m140xdd4b3540(final Integer num, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m139x42aa72bf(num);
                }
            });
        }
    }

    private void localFileDelete(String str) {
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        FileUtils.localFileDelete(str);
        this.localFileDbManager.deleteWhere(LocalFileEntityDao.Properties.LocalPath.m1944eq(str), LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()));
    }

    private UploadAudioBean initUploadAudioBean(String str) {
        UploadAudioBean uploadAudioBean = new UploadAudioBean();
        uploadAudioBean.setTitle(str);
        uploadAudioBean.setIsTop(0);
        uploadAudioBean.setUploadAudioInfo(this.uploadAudioInfo);
        return uploadAudioBean;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAct(String str) {
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            ARouterUtils.startWithActivity(this, AccountAction.ONE_KEY_LOGIN);
            finish();
            return;
        }
        if (!userInfo.isVip() || DateUtil.isTimePassed(this.userInfo.getVipExpire())) {
            if (FileUtils.getFileSizeKb(str) >= 307200) {
                DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_size_for_free), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda3
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        ExternalOpenActivity.lambda$startAct$12(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda4
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m2112lambda$startAct$13$comaivoxappactivityExternalOpenActivity(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, false, C0874R.string.know_and_continue, C0874R.string.join_vip);
                return;
            } else if (FileUtils.getAudioFileVoiceTime(str) >= 3600) {
                DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_for_free), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda5
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        ExternalOpenActivity.lambda$startAct$14(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda6
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m2113lambda$startAct$15$comaivoxappactivityExternalOpenActivity(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, false, C0874R.string.know_and_continue, C0874R.string.join_vip);
                return;
            }
        } else if (FileUtils.getFileSizeKb(str) >= 512000) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_size_for_vip), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda17
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$8(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda20
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$9(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
            return;
        } else if (FileUtils.getAudioFileVoiceTime(str) >= 18000) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_for_vip), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$10(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$11(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
            return;
        }
        if (FileUtils.getAudioFileVoiceTime(str) <= 5) {
            DialogUtils.showDialogWithDefBtn(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_short_5s), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$16(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda8
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$17(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
        } else if (FileUtils.getAudioFileType(this, str).contains("amr-wb")) {
            DialogUtils.showDialogWithDefBtn(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.file_formant_not_allow), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda18
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$18(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ExternalOpenActivity$$ExternalSyntheticLambda19
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    ExternalOpenActivity.lambda$startAct$19(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
        } else {
            m2115lambda$startImport$1$comaivoxappactivityExternalOpenActivity(this.fileName, str);
        }
    }

    /* JADX INFO: renamed from: lambda$startAct$13$com-aivox-app-activity-ExternalOpenActivity, reason: not valid java name */
    /* synthetic */ void m2112lambda$startAct$13$comaivoxappactivityExternalOpenActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this, false);
    }

    /* JADX INFO: renamed from: lambda$startAct$15$com-aivox-app-activity-ExternalOpenActivity, reason: not valid java name */
    /* synthetic */ void m2113lambda$startAct$15$comaivoxappactivityExternalOpenActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this, false);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            try {
                Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name", "_size", TypedValues.TransitionType.S_DURATION}, null, null, null);
                if (cursorQuery.moveToFirst()) {
                    return cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
                }
            } catch (Exception unused) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return uri.toString();
    }
}
