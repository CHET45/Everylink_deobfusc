package com.aivox.account.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityNameInputBinding;
import com.aivox.base.C0874R;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.UserService;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NameInputActivity extends BaseFragmentActivity {
    private ActivityNameInputBinding mBinding;
    private SQLiteDataBaseManager manager;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityNameInputBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_name_input);
        this.manager = new SQLiteDataBaseManager(this);
        KeyboardUtils.showSoftInput(this.mBinding.etName);
        this.mBinding.etName.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.NameInputActivity.1
            int lastPosition = 0;
            String tempText = "";

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                this.lastPosition = NameInputActivity.this.mBinding.etName.getSelectionStart();
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                NameInputActivity.this.mBinding.etName.removeTextChangedListener(this);
                if (NameInputActivity.this.mBinding.etName.getLineCount() > 2) {
                    NameInputActivity.this.mBinding.etName.setText(this.tempText);
                    NameInputActivity.this.mBinding.etName.setSelection(this.lastPosition);
                } else {
                    this.tempText = NameInputActivity.this.mBinding.etName.getText().toString();
                }
                NameInputActivity.this.mBinding.btnContinue.setEnabled(editable.length() > 0);
                NameInputActivity.this.mBinding.tvNotice.setText(editable.length() > 0 ? C0874R.string.account_nickname_set_correct : C0874R.string.account_name_set_empty);
                NameInputActivity.this.mBinding.etName.addTextChangedListener(this);
            }
        });
        this.mBinding.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.NameInputActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2030lambda$initView$0$comaivoxaccountactivityNameInputActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-NameInputActivity, reason: not valid java name */
    /* synthetic */ void m2030lambda$initView$0$comaivoxaccountactivityNameInputActivity(View view2) {
        setNickname();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNickname() {
        this.mBinding.btnContinue.startLoading();
        new UserService(this).modifyUserName(this.mBinding.etName.getText().toString()).subscribe(new Consumer() { // from class: com.aivox.account.activity.NameInputActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m58xdd7ffdd6((UserInfo) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.NameInputActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m59x96f78b75((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setNickname$1$com-aivox-account-activity-NameInputActivity */
    /* synthetic */ void m58xdd7ffdd6(UserInfo userInfo) throws Exception {
        this.mBinding.btnContinue.stopLoading();
        SPUtil.put(SPUtil.NICKNAME, this.mBinding.etName.getText().toString());
        this.manager.updateUserInfo(userInfo.getUuid(), userInfo.getAvatar(), this.mBinding.etName.getText().toString(), userInfo.getBirthday(), userInfo.getGender());
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        finish();
    }

    /* JADX INFO: renamed from: lambda$setNickname$2$com-aivox-account-activity-NameInputActivity */
    /* synthetic */ void m59x96f78b75(Throwable th) throws Exception {
        this.mBinding.btnContinue.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.NameInputActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.setNickname();
                }
            });
        }
    }
}
