package com.aivox.account.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityNameInputBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final EditText etName;
    public final TextView tvMsg;
    public final TextView tvNotice;

    protected ActivityNameInputBinding(Object obj, View view2, int i, LoadingButton loadingButton, EditText editText, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.etName = editText;
        this.tvMsg = textView;
        this.tvNotice = textView2;
    }

    public static ActivityNameInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNameInputBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityNameInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_name_input, viewGroup, z, obj);
    }

    public static ActivityNameInputBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNameInputBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityNameInputBinding) ViewDataBinding.inflateInternal(layoutInflater, C0707R.layout.activity_name_input, null, false, obj);
    }

    public static ActivityNameInputBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNameInputBinding bind(View view2, Object obj) {
        return (ActivityNameInputBinding) bind(obj, view2, C0707R.layout.activity_name_input);
    }
}
