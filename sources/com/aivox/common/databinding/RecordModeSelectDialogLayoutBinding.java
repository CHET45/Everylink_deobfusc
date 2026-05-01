package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common.C0958R;
import com.aivox.common_ui.DialogTitleView;

/* JADX INFO: loaded from: classes.dex */
public abstract class RecordModeSelectDialogLayoutBinding extends ViewDataBinding {
    public final DialogTitleView dtvTitle;
    public final RecyclerView rvMark;

    protected RecordModeSelectDialogLayoutBinding(Object obj, View view2, int i, DialogTitleView dialogTitleView, RecyclerView recyclerView) {
        super(obj, view2, i);
        this.dtvTitle = dialogTitleView;
        this.rvMark = recyclerView;
    }

    public static RecordModeSelectDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RecordModeSelectDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (RecordModeSelectDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.record_mode_select_dialog_layout, viewGroup, z, obj);
    }

    public static RecordModeSelectDialogLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RecordModeSelectDialogLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (RecordModeSelectDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.record_mode_select_dialog_layout, null, false, obj);
    }

    public static RecordModeSelectDialogLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RecordModeSelectDialogLayoutBinding bind(View view2, Object obj) {
        return (RecordModeSelectDialogLayoutBinding) bind(obj, view2, C0958R.layout.record_mode_select_dialog_layout);
    }
}
