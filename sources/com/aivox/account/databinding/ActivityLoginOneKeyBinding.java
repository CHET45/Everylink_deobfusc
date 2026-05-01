package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityLoginOneKeyBinding extends ViewDataBinding {
    public final LoadingButton btnLogin;
    public final LoadingButton btnLoginEmail;
    public final LoadingButton btnLoginPhone;
    public final CheckBox cbEmail;
    public final ImageView ivGoogleLogin;

    /* JADX INFO: renamed from: ll */
    public final LinearLayout f112ll;
    public final TextView tvPolicy;
    public final TextView tvSlogan;

    protected ActivityLoginOneKeyBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoadingButton loadingButton2, LoadingButton loadingButton3, CheckBox checkBox, ImageView imageView, LinearLayout linearLayout, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnLogin = loadingButton;
        this.btnLoginEmail = loadingButton2;
        this.btnLoginPhone = loadingButton3;
        this.cbEmail = checkBox;
        this.ivGoogleLogin = imageView;
        this.f112ll = linearLayout;
        this.tvPolicy = textView;
        this.tvSlogan = textView2;
    }

    public static ActivityLoginOneKeyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLoginOneKeyBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityLoginOneKeyBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_login_one_key, viewGroup, z, obj);
    }

    public static ActivityLoginOneKeyBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLoginOneKeyBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityLoginOneKeyBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_login_one_key, null, false, obj);
    }

    public static ActivityLoginOneKeyBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityLoginOneKeyBinding bind(View view2, Object obj) {
        return (ActivityLoginOneKeyBinding) bind(obj, view2, C0707R.layout.activity_login_one_key);
    }
}
