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
public abstract class ActivityPwdInputBinding extends ViewDataBinding {
    public final LoadingButton btnLogin;
    public final LoginEditText etPwd;
    public final LoginEditText etPwdConfirm;
    public final ConstraintLayout main;
    public final HeadTitleLinearView titleView;
    public final TextView tvCodeLogin;
    public final TextView tvNotice;

    protected ActivityPwdInputBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoginEditText loginEditText, LoginEditText loginEditText2, ConstraintLayout constraintLayout, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnLogin = loadingButton;
        this.etPwd = loginEditText;
        this.etPwdConfirm = loginEditText2;
        this.main = constraintLayout;
        this.titleView = headTitleLinearView;
        this.tvCodeLogin = textView;
        this.tvNotice = textView2;
    }

    public static ActivityPwdInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPwdInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityPwdInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_pwd_input, viewGroup, z, obj);
    }

    public static ActivityPwdInputBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPwdInputBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityPwdInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_pwd_input, null, false, obj);
    }

    public static ActivityPwdInputBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityPwdInputBinding bind(View view2, Object obj) {
        return (ActivityPwdInputBinding) bind(obj, view2, C0707R.layout.activity_pwd_input);
    }
}
