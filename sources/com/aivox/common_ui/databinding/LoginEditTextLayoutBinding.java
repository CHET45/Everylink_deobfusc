package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public abstract class LoginEditTextLayoutBinding extends ViewDataBinding {
    public final EditText etContent;
    public final ImageView ivDisplay;
    public final ImageView ivStatus;
    public final LinearLayout llContent;

    protected LoginEditTextLayoutBinding(Object obj, View view2, int i, EditText editText, ImageView imageView, ImageView imageView2, LinearLayout linearLayout) {
        super(obj, view2, i);
        this.etContent = editText;
        this.ivDisplay = imageView;
        this.ivStatus = imageView2;
        this.llContent = linearLayout;
    }

    public static LoginEditTextLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoginEditTextLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (LoginEditTextLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.login_edit_text_layout, viewGroup, z, obj);
    }

    public static LoginEditTextLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoginEditTextLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (LoginEditTextLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.login_edit_text_layout, null, false, obj);
    }

    public static LoginEditTextLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LoginEditTextLayoutBinding bind(View view2, Object obj) {
        return (LoginEditTextLayoutBinding) bind(obj, view2, C1034R.layout.login_edit_text_layout);
    }
}
