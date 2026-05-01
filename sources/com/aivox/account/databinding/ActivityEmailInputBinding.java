package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.LoginEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityEmailInputBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final LoginEditText etEmail;
    public final HeadTitleLinearView title;

    protected ActivityEmailInputBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoginEditText loginEditText, HeadTitleLinearView headTitleLinearView) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.etEmail = loginEditText;
        this.title = headTitleLinearView;
    }

    public static ActivityEmailInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEmailInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityEmailInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_email_input, viewGroup, z, obj);
    }

    public static ActivityEmailInputBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEmailInputBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityEmailInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_email_input, null, false, obj);
    }

    public static ActivityEmailInputBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEmailInputBinding bind(View view2, Object obj) {
        return (ActivityEmailInputBinding) bind(obj, view2, C0707R.layout.activity_email_input);
    }
}
