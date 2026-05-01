package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityCodeInputBinding extends ViewDataBinding {
    public final LoadingButton btnNext;
    public final EditText etCode;
    public final HeadTitleLinearView title;
    public final TextView tvFakeHint;
    public final TextView tvMsg;
    public final TextView tvNotice;
    public final TextView tvSubMsg;

    protected ActivityCodeInputBinding(Object obj, View view2, int i, LoadingButton loadingButton, EditText editText, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.btnNext = loadingButton;
        this.etCode = editText;
        this.title = headTitleLinearView;
        this.tvFakeHint = textView;
        this.tvMsg = textView2;
        this.tvNotice = textView3;
        this.tvSubMsg = textView4;
    }

    public static ActivityCodeInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityCodeInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityCodeInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_code_input, viewGroup, z, obj);
    }

    public static ActivityCodeInputBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityCodeInputBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityCodeInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_code_input, null, false, obj);
    }

    public static ActivityCodeInputBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityCodeInputBinding bind(View view2, Object obj) {
        return (ActivityCodeInputBinding) bind(obj, view2, C0707R.layout.activity_code_input);
    }
}
