package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class HolderEditTextLayoutBinding extends ViewDataBinding {
    public final EditText etContent;
    public final TextView tvHolder;

    protected HolderEditTextLayoutBinding(Object obj, View view2, int i, EditText editText, TextView textView) {
        super(obj, view2, i);
        this.etContent = editText;
        this.tvHolder = textView;
    }

    public static HolderEditTextLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HolderEditTextLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (HolderEditTextLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.holder_edit_text_layout, viewGroup, z, obj);
    }

    public static HolderEditTextLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HolderEditTextLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (HolderEditTextLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.holder_edit_text_layout, null, false, obj);
    }

    public static HolderEditTextLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static HolderEditTextLayoutBinding bind(View view2, Object obj) {
        return (HolderEditTextLayoutBinding) bind(obj, view2, C1034R.layout.holder_edit_text_layout);
    }
}
