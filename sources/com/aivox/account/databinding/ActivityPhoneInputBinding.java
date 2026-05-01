package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.LoginEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityPhoneInputBinding extends ViewDataBinding {
    public final LoadingButton btnSendCode;
    public final LoginEditText etPhone;
    public final ConstraintLayout main;
    public final HeadTitleLinearView titleView;
    public final TextView tvPwdLogin;

    protected ActivityPhoneInputBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoginEditText loginEditText, ConstraintLayout constraintLayout, HeadTitleLinearView headTitleLinearView, TextView textView) {
        super(obj, view2, i);
        this.btnSendCode = loadingButton;
        this.etPhone = loginEditText;
        this.main = constraintLayout;
        this.titleView = headTitleLinearView;
        this.tvPwdLogin = textView;
    }

    public static ActivityPhoneInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPhoneInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityPhoneInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_phone_input, viewGroup, z, obj);
    }

    public static ActivityPhoneInputBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPhoneInputBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityPhoneInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_phone_input, null, false, obj);
    }

    public static ActivityPhoneInputBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPhoneInputBinding bind(View view2, Object obj) {
        return (ActivityPhoneInputBinding) bind(obj, view2, C0707R.layout.activity_phone_input);
    }
}
