package com.aivox.account.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityEmailInputBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.RegexUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AuthService;
import com.aivox.common.util.AppUtils;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class EmailInputActivity extends BaseFragmentActivity {
    public static final int EMAIL_TYPE_REGISTER = 0;
    public static final int EMAIL_TYPE_RESET = 1;
    private ActivityEmailInputBinding mBinding;
    private int mEmailInputType;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityEmailInputBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_email_input);
        this.mEmailInputType = getIntent().getExtras().getInt(Constant.KEY_EMAIL_INPUT_TYPE, 0);
        this.mBinding.etEmail.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.EmailInputActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                boolean zIsEmail = RegexUtil.isEmail(editable.toString());
                EmailInputActivity.this.mBinding.btnContinue.setEnabled(zIsEmail);
                EmailInputActivity.this.mBinding.etEmail.changeStatus(zIsEmail ? 0 : 2);
                if (editable.toString().isEmpty()) {
                    EmailInputActivity.this.mBinding.etEmail.changeStatus(0);
                }
            }
        });
        this.mBinding.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.EmailInputActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2028lambda$initView$0$comaivoxaccountactivityEmailInputActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-EmailInputActivity, reason: not valid java name */
    /* synthetic */ void m2028lambda$initView$0$comaivoxaccountactivityEmailInputActivity(View view2) {
        checkEmail();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEmail() {
        this.mBinding.btnContinue.startLoading();
        new AuthService(this.context).checkEmailExist(this.mBinding.etEmail.getText()).subscribe(new Consumer() { // from class: com.aivox.account.activity.EmailInputActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m56xc444ccf3((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.EmailInputActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m57x39bef334((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$checkEmail$1$com-aivox-account-activity-EmailInputActivity */
    /* synthetic */ void m56xc444ccf3(Integer num) throws Exception {
        Bundle bundle = new Bundle();
        int i = this.mEmailInputType;
        String str = AccountAction.CODE_INPUT;
        if (i == 0) {
            bundle.putInt(Constant.KEY_PWD_INPUT_TYPE, num.intValue() == 0 ? 0 : 1);
            bundle.putInt(Constant.KEY_CODE_INPUT_TYPE, 0);
            bundle.putString("email", this.mBinding.etEmail.getText());
            if (num.intValue() != 0) {
                str = AccountAction.PWD_INPUT;
            }
            ARouterUtils.startWithActivity(this, str, bundle);
        } else if (num.intValue() == 1) {
            DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.email_has_been_registered), null, false, true);
            this.mBinding.btnContinue.stopLoading();
            return;
        } else {
            bundle.putInt(Constant.KEY_CODE_INPUT_TYPE, 3);
            bundle.putString("email", this.mBinding.etEmail.getText());
            ARouterUtils.startWithActivity(this, AccountAction.CODE_INPUT, bundle);
        }
        this.mBinding.btnContinue.stopLoading();
    }

    /* JADX INFO: renamed from: lambda$checkEmail$2$com-aivox-account-activity-EmailInputActivity */
    /* synthetic */ void m57x39bef334(Throwable th) throws Exception {
        this.mBinding.btnContinue.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.EmailInputActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.checkEmail();
                }
            });
        }
    }
}
