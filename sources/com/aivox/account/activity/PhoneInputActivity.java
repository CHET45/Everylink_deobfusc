package com.aivox.account.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityPhoneInputBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class PhoneInputActivity extends BaseFragmentActivity {
    private ActivityPhoneInputBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityPhoneInputBinding activityPhoneInputBinding = (ActivityPhoneInputBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_phone_input);
        this.mBinding = activityPhoneInputBinding;
        activityPhoneInputBinding.etPhone.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.PhoneInputActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PhoneInputActivity.this.mBinding.btnSendCode.setEnabled(RegexUtil.isMobile(editable.toString()));
            }
        });
        this.mBinding.btnSendCode.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.PhoneInputActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2036lambda$initView$0$comaivoxaccountactivityPhoneInputActivity(view2);
            }
        });
        this.mBinding.tvPwdLogin.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.PhoneInputActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2037lambda$initView$1$comaivoxaccountactivityPhoneInputActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-PhoneInputActivity, reason: not valid java name */
    /* synthetic */ void m2036lambda$initView$0$comaivoxaccountactivityPhoneInputActivity(View view2) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PHONE, this.mBinding.etPhone.getText());
        ARouterUtils.startWithContext(this.context, AccountAction.SMS_CODE_INPUT, bundle);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-account-activity-PhoneInputActivity, reason: not valid java name */
    /* synthetic */ void m2037lambda$initView$1$comaivoxaccountactivityPhoneInputActivity(View view2) {
        if (this.mBinding.btnSendCode.isEnabled()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_PHONE, this.mBinding.etPhone.getText());
            bundle.putInt(Constant.KEY_PWD_INPUT_TYPE, 1);
            ARouterUtils.startWithContext(this.context, AccountAction.PWD_INPUT, bundle);
            return;
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.bindaccount_empty_account_phone));
    }
}
