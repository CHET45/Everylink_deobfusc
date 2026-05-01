package com.aivox.account.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityUserInfoBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AuthService;
import com.aivox.common.http.UserService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.GlideEngine;
import com.aivox.common.util.ImageFileCropEngine;
import com.aivox.common_ui.BottomEditDialogView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DateScrollSelectView;
import com.aivox.common_ui.GenderScrollSelectView;
import com.aivox.common_ui.SettingTileView;
import com.aivox.common_ui.antishake.AntiShake;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/* JADX INFO: loaded from: classes.dex */
public class UserInfoActivity extends BaseFragmentActivity implements OnViewClickListener {
    private String birthday;
    private int gender;
    private String imageUrl;
    private ActivityUserInfoBinding mBinding;
    String mIconPath;
    private SQLiteDataBaseManager manager;
    private UserInfo userInfo;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityUserInfoBinding activityUserInfoBinding = (ActivityUserInfoBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_user_info);
        this.mBinding = activityUserInfoBinding;
        activityUserInfoBinding.setClickListener(this);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2051lambda$initView$0$comaivoxaccountactivityUserInfoActivity(view2);
            }
        });
        refresh();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2051lambda$initView$0$comaivoxaccountactivityUserInfoActivity(View view2) {
        onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        reqModifyUserinfo();
    }

    private void refresh() {
        String displayDateStr;
        SQLiteDataBaseManager sQLiteDataBaseManager = new SQLiteDataBaseManager(this);
        this.manager = sQLiteDataBaseManager;
        UserInfo userInfo = sQLiteDataBaseManager.getUserInfo();
        this.userInfo = userInfo;
        this.birthday = userInfo.getBirthday();
        if (BaseStringUtil.isNotEmpty(this.userInfo.getAvatar())) {
            setImageUrl(this.userInfo.getAvatar());
            ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivHead, this.userInfo.getAvatar(), C1034R.drawable.bg_purple_round);
        }
        this.mBinding.stvNickname.setSubTitle(this.userInfo.getNickname());
        this.mBinding.tvHead.setText(this.userInfo.getNickname());
        SettingTileView settingTileView = this.mBinding.stvBirthday;
        if (BaseStringUtil.isEmpty(this.birthday)) {
            displayDateStr = getString(C0874R.string.user_info_birthday_select);
        } else {
            displayDateStr = DateUtil.getDisplayDateStr(this.birthday, this.context);
        }
        settingTileView.setSubTitle(displayDateStr);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2054lambda$refresh$1$comaivoxaccountactivityUserInfoActivity(view2);
            }
        });
        if (BaseStringUtil.isEmpty(this.userInfo.getEmail())) {
            this.mBinding.stvPhone.setSubTitle(this.userInfo.getPhone());
        } else {
            this.mBinding.stvPhone.setSubTitle(this.userInfo.getEmail());
        }
        this.mBinding.stvPwd.setSubTitle(getString(DataHandle.getIns().hasSetPwd() ? C0874R.string.pwd_set : C0874R.string.pwd_not_set));
        setGender(this.userInfo.getGender());
    }

    /* JADX INFO: renamed from: lambda$refresh$1$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2054lambda$refresh$1$comaivoxaccountactivityUserInfoActivity(View view2) {
        reqModifyUserinfo();
    }

    private void showDatePopup() {
        DialogUtils.showBottomSheetDialog(this.context, new DateScrollSelectView(this.context, new DateScrollSelectView.DateSelectListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common_ui.DateScrollSelectView.DateSelectListener
            public final void onDateSelected(String str) {
                this.f$0.m67xd5c476d2(str);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$showDatePopup$2$com-aivox-account-activity-UserInfoActivity */
    /* synthetic */ void m67xd5c476d2(String str) {
        String[] strArrSplit = str.split("-");
        this.birthday = strArrSplit[2] + "-" + strArrSplit[0] + "-" + strArrSplit[1];
        this.mBinding.stvBirthday.setSubTitle(DateUtil.getDisplayDateStr(str, this.context));
    }

    public void setGender(int i) {
        String string;
        this.gender = i;
        if (i == 1) {
            string = getString(C0874R.string.user_info_gender_male);
        } else if (i == 2) {
            string = getString(C0874R.string.user_info_gender_female);
        } else if (i == 3) {
            string = getString(C0874R.string.user_info_gender_other);
        } else {
            string = getString(C0874R.string.user_info_gender_select);
        }
        this.mBinding.stvGender.setSubTitle(string);
    }

    private void showGenderPopup() {
        DialogUtils.showBottomSheetDialog(this.context, new GenderScrollSelectView(this.context, new GenderScrollSelectView.DateSelectListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda1
            @Override // com.aivox.common_ui.GenderScrollSelectView.DateSelectListener
            public final void onDateSelected(int i) {
                this.f$0.setGender(i);
            }
        }));
    }

    /* JADX INFO: renamed from: com.aivox.account.activity.UserInfoActivity$1 */
    class C07221 implements PermissionCallback {
        C07221() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                PictureSelector.create((AppCompatActivity) UserInfoActivity.this).openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1).setMinSelectNum(1).setImageSpanCount(4).isPreviewImage(true).isDisplayCamera(true).setCropEngine(new ImageFileCropEngine()).setImageEngine(GlideEngine.createGlideEngine()).setCompressEngine(new CompressFileEngine() { // from class: com.aivox.account.activity.UserInfoActivity$1$$ExternalSyntheticLambda0
                    @Override // com.luck.picture.lib.engine.CompressFileEngine
                    public final void onStartCompress(Context context, ArrayList arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
                        this.f$0.m2057lambda$granted$0$comaivoxaccountactivityUserInfoActivity$1(context, arrayList, onKeyValueResultCallbackListener);
                    }
                }).setSelectionMode(1).isSelectZoomAnim(true).forResult(188);
            } else {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            }
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-account-activity-UserInfoActivity$1, reason: not valid java name */
        /* synthetic */ void m2057lambda$granted$0$comaivoxaccountactivityUserInfoActivity$1(Context context, ArrayList arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
            Luban.with(context).load(arrayList).ignoreBy(50).setCompressListener(new OnNewCompressListener() { // from class: com.aivox.account.activity.UserInfoActivity.1.1
                @Override // top.zibin.luban.OnNewCompressListener
                public void onStart() {
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onSuccess(String str, File file) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, file.getAbsolutePath());
                    }
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onError(String str, Throwable th) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, null);
                    }
                }
            }).launch();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            BaseAppUtils.openSettingView(UserInfoActivity.this.context);
        }
    }

    private void setPhotoIcon() {
        PermissionUtils.getIns(this, new C07221()).request("android.permission.READ_MEDIA_IMAGES");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            ArrayList<LocalMedia> arrayListObtainSelectorList = PictureSelector.obtainSelectorList(intent);
            if (arrayListObtainSelectorList.size() != 0) {
                this.mIconPath = arrayListObtainSelectorList.get(0).getAvailablePath();
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                LogUtil.m336e("压缩成功后调用，返回压缩后的图片文件");
                LogUtil.m338i("file.size:" + FileUtils.getFileSize(this.mIconPath));
                LogUtil.m338i("path:" + this.mIconPath);
                ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivHead, this.mIconPath, C1034R.mipmap.home_iv_company_head);
                upload2OSS(this.mIconPath);
            }
        }
    }

    private void toRefreshUserinfo(String str, String str2) {
        SPUtil.put(SPUtil.NICKNAME, str);
    }

    private void reqModifyUserinfo() {
        DialogUtils.showLoadingDialog(this.context);
        new UserService(this).modifyUserInfo(this.imageUrl, this.mBinding.stvNickname.getSubTitle(), this.birthday, this.gender).subscribe(new Consumer() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m65x7c461ac3((UserInfo) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m66x16e6dd44((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqModifyUserinfo$3$com-aivox-account-activity-UserInfoActivity */
    /* synthetic */ void m65x7c461ac3(UserInfo userInfo) throws Exception {
        DialogUtils.hideLoadingDialog();
        this.manager.updateUserInfo(userInfo.getUuid(), this.imageUrl, this.mBinding.stvNickname.getSubTitle(), this.birthday, this.gender);
        toRefreshUserinfo(this.mBinding.stvNickname.getSubTitle(), this.imageUrl);
        finish();
    }

    /* JADX INFO: renamed from: lambda$reqModifyUserinfo$4$com-aivox-account-activity-UserInfoActivity */
    /* synthetic */ void m66x16e6dd44(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        finish();
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upload2OSS(String str) {
        if (FileUtils.isFileExist(str)) {
            CommonUploadManager.getInstance().startUpload(this.context, 0, str, this.userInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new C07232(str), Constant.TYPE_USER_AVATAR);
        } else {
            LogUtil.m338i("文件不存在");
        }
    }

    /* JADX INFO: renamed from: com.aivox.account.activity.UserInfoActivity$2 */
    class C07232 implements OnUploadListener {
        final /* synthetic */ String val$filePath;

        C07232(String str) {
            this.val$filePath = str;
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onProgress(int i, long j, long j2, int i2) {
            LogUtil.m338i("上传中:" + i + "  " + i2 + "% " + j + "/" + j2);
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onSuccess(int i, String str, String str2, long j) {
            LogUtil.m338i("上传成功:" + i + "  " + str + "   " + str2);
            LogUtil.m338i("全部文件上传成功");
            UserInfoActivity.this.setImageUrl(str2);
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onFailure(int i) {
            LogUtil.m338i("上传失败：" + i);
            UserInfoActivity userInfoActivity = UserInfoActivity.this;
            final String str = this.val$filePath;
            userInfoActivity.runOnUiThread(new Runnable() { // from class: com.aivox.account.activity.UserInfoActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2059lambda$onFailure$1$comaivoxaccountactivityUserInfoActivity$2(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onFailure$1$com-aivox-account-activity-UserInfoActivity$2, reason: not valid java name */
        /* synthetic */ void m2059lambda$onFailure$1$comaivoxaccountactivityUserInfoActivity$2(final String str) {
            DialogUtils.showDialogWithDefBtnAndSingleListener(UserInfoActivity.this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.audio_sync_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.UserInfoActivity$2$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2058lambda$onFailure$0$comaivoxaccountactivityUserInfoActivity$2(str, context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, true);
        }

        /* JADX INFO: renamed from: lambda$onFailure$0$com-aivox-account-activity-UserInfoActivity$2, reason: not valid java name */
        /* synthetic */ void m2058lambda$onFailure$0$comaivoxaccountactivityUserInfoActivity$2(String str, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            UserInfoActivity.this.upload2OSS(str);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        int id = view2.getId();
        if (id == C0707R.id.cl_head) {
            setPhotoIcon();
            return;
        }
        if (id == C0707R.id.stv_nickname) {
            showNameSet();
            return;
        }
        if (id == C0707R.id.stv_birthday) {
            showDatePopup();
            return;
        }
        if (id == C0707R.id.stv_gender) {
            showGenderPopup();
            return;
        }
        if (id == C0707R.id.stv_pwd) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY_PWD_INPUT_TYPE, DataHandle.getIns().hasSetPwd() ? 2 : 0);
            if (BaseStringUtil.isEmpty(this.userInfo.getEmail())) {
                bundle.putString(Constant.KEY_PHONE, this.userInfo.getPhone());
            } else {
                bundle.putString("email", this.userInfo.getEmail());
            }
            ARouterUtils.startWithContext(this.context, AccountAction.PWD_INPUT, bundle);
            return;
        }
        if (id == C0707R.id.stv_phone) {
            return;
        }
        if (id == C0707R.id.tv_delete_account) {
            DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.delete_account), Integer.valueOf(C0874R.string.delete_account_notice), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda5
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2052lambda$onViewClick$5$comaivoxaccountactivityUserInfoActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
        } else if (id == C0707R.id.tv_sign_out) {
            DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.set_exit_notic), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda6
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2053lambda$onViewClick$6$comaivoxaccountactivityUserInfoActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, true, C0874R.string.set_exit, C0874R.string.cancel, 0);
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$5$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2052lambda$onViewClick$5$comaivoxaccountactivityUserInfoActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doDeleteAccount();
    }

    /* JADX INFO: renamed from: lambda$onViewClick$6$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2053lambda$onViewClick$6$comaivoxaccountactivityUserInfoActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        reqLogout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqLogout() {
        DialogUtils.showLoadingDialog(this.context);
        new AuthService(this).logout().subscribe(new Consumer() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2055lambda$reqLogout$7$comaivoxaccountactivityUserInfoActivity(obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2056lambda$reqLogout$8$comaivoxaccountactivityUserInfoActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqLogout$7$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2055lambda$reqLogout$7$comaivoxaccountactivityUserInfoActivity(Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        AppUtils.logout(this);
    }

    /* JADX INFO: renamed from: lambda$reqLogout$8$com-aivox-account-activity-UserInfoActivity, reason: not valid java name */
    /* synthetic */ void m2056lambda$reqLogout$8$comaivoxaccountactivityUserInfoActivity(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.UserInfoActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqLogout();
                }
            });
        }
    }

    private void doDeleteAccount() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_CODE_INPUT_TYPE, 4);
        bundle.putString("email", (String) SPUtil.get(SPUtil.LOGIN_ACCOUNT, ""));
        ARouterUtils.startWithActivity(this, AccountAction.CODE_INPUT, bundle);
    }

    private void showNameSet() {
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 1, 12, false, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.account.activity.UserInfoActivity.3
            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onLeftBtnClick() {
            }

            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onSaveBtnClick(String str) {
                UserInfoActivity.this.mBinding.stvNickname.setSubTitle(str);
                UserInfoActivity.this.mBinding.tvHead.setText(str);
            }
        });
        bottomEditDialogView.setDialogContent(getString(C0874R.string.set_nickname), getString(C0874R.string.set_nickname), "", this.mBinding.stvNickname.getSubTitle(), "");
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }
}
