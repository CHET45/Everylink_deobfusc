package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class BottomEditDialogLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnLeft;
    public final LoadingButton btnSave;
    public final ConstraintLayout clAudioSave;
    public final DialogTitleItemView dtivItemTitle;
    public final DialogTitleItemView dtivItemType;
    public final DialogTitleView dtvTitle;
    public final EditText etContent;
    public final HorizontalScrollView hsvTypeItem;
    public final ImageView ivContentClear;
    public final ImageView ivTop;
    public final LinearLayout llTypeItem;

    protected BottomEditDialogLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, LoadingButton loadingButton2, ConstraintLayout constraintLayout, DialogTitleItemView dialogTitleItemView, DialogTitleItemView dialogTitleItemView2, DialogTitleView dialogTitleView, EditText editText, HorizontalScrollView horizontalScrollView, ImageView imageView, ImageView imageView2, LinearLayout linearLayout) {
        super(obj, view2, i);
        this.btnLeft = loadingButton;
        this.btnSave = loadingButton2;
        this.clAudioSave = constraintLayout;
        this.dtivItemTitle = dialogTitleItemView;
        this.dtivItemType = dialogTitleItemView2;
        this.dtvTitle = dialogTitleView;
        this.etContent = editText;
        this.hsvTypeItem = horizontalScrollView;
        this.ivContentClear = imageView;
        this.ivTop = imageView2;
        this.llTypeItem = linearLayout;
    }

    public static BottomEditDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomEditDialogLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (BottomEditDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.bottom_edit_dialog_layout, viewGroup, z, obj);
    }

    public static BottomEditDialogLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomEditDialogLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (BottomEditDialogLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.bottom_edit_dialog_layout, null, false, obj);
    }

    public static BottomEditDialogLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BottomEditDialogLayoutBinding bind(View view2, Object obj) {
        return (BottomEditDialogLayoutBinding) bind(obj, view2, C1034R.layout.bottom_edit_dialog_layout);
    }
}
