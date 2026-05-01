package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.FolderBean;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemFolderSelectLayoutBinding extends ViewDataBinding {
    public final ConstraintLayout clSelectFolder;
    public final ImageView ivSelectFolder;

    @Bindable
    protected FolderBean mModel;
    public final TextView tvSelectFolderName;

    public abstract void setModel(FolderBean folderBean);

    protected ItemFolderSelectLayoutBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, TextView textView) {
        super(obj, view2, i);
        this.clSelectFolder = constraintLayout;
        this.ivSelectFolder = imageView;
        this.tvSelectFolderName = textView;
    }

    public FolderBean getModel() {
        return this.mModel;
    }

    public static ItemFolderSelectLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFolderSelectLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemFolderSelectLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_folder_select_layout, viewGroup, z, obj);
    }

    public static ItemFolderSelectLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFolderSelectLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemFolderSelectLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_folder_select_layout, null, false, obj);
    }

    public static ItemFolderSelectLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFolderSelectLayoutBinding bind(View view2, Object obj) {
        return (ItemFolderSelectLayoutBinding) bind(obj, view2, C0726R.layout.item_folder_select_layout);
    }
}
