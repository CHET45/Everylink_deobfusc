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
public abstract class DialogEditItemViewLayoutBinding extends ViewDataBinding {
    public final EditText etItemContent;
    public final TextView tvItemTitle;

    protected DialogEditItemViewLayoutBinding(Object obj, View view2, int i, EditText editText, TextView textView) {
        super(obj, view2, i);
        this.etItemContent = editText;
        this.tvItemTitle = textView;
    }

    public static DialogEditItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditItemViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (DialogEditItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_edit_item_view_layout, viewGroup, z, obj);
    }

    public static DialogEditItemViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditItemViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (DialogEditItemViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.dialog_edit_item_view_layout, null, false, obj);
    }

    public static DialogEditItemViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static DialogEditItemViewLayoutBinding bind(View view2, Object obj) {
        return (DialogEditItemViewLayoutBinding) bind(obj, view2, C1034R.layout.dialog_edit_item_view_layout);
    }
}
