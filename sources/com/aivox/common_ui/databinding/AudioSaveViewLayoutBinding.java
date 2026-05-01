package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public abstract class AudioSaveViewLayoutBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final DialogTitleItemView dtivFile;
    public final DialogTitleView dtvSave;
    public final EditText etFilename;

    protected AudioSaveViewLayoutBinding(Object obj, View view2, int i, LoadingButton loadingButton, DialogTitleItemView dialogTitleItemView, DialogTitleView dialogTitleView, EditText editText) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.dtivFile = dialogTitleItemView;
        this.dtvSave = dialogTitleView;
        this.etFilename = editText;
    }

    public static AudioSaveViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioSaveViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AudioSaveViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.audio_save_view_layout, viewGroup, z, obj);
    }

    public static AudioSaveViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioSaveViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AudioSaveViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.audio_save_view_layout, null, false, obj);
    }

    public static AudioSaveViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioSaveViewLayoutBinding bind(View view2, Object obj) {
        return (AudioSaveViewLayoutBinding) bind(obj, view2, C1034R.layout.audio_save_view_layout);
    }
}
