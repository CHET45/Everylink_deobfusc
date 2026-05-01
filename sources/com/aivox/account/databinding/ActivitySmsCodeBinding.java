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
import com.example.gjylibrary.GjySerialnumberLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivitySmsCodeBinding extends ViewDataBinding {
    public final GjySerialnumberLayout etCode;
    public final ConstraintLayout main;
    public final HeadTitleLinearView titleView;
    public final TextView tvCountDown;
    public final TextView tvPhoneNum;

    protected ActivitySmsCodeBinding(Object obj, View view2, int i, GjySerialnumberLayout gjySerialnumberLayout, ConstraintLayout constraintLayout, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.etCode = gjySerialnumberLayout;
        this.main = constraintLayout;
        this.titleView = headTitleLinearView;
        this.tvCountDown = textView;
        this.tvPhoneNum = textView2;
    }

    public static ActivitySmsCodeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySmsCodeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivitySmsCodeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_sms_code, viewGroup, z, obj);
    }

    public static ActivitySmsCodeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySmsCodeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivitySmsCodeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_sms_code, null, false, obj);
    }

    public static ActivitySmsCodeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySmsCodeBinding bind(View view2, Object obj) {
        return (ActivitySmsCodeBinding) bind(obj, view2, C0707R.layout.activity_sms_code);
    }
}
