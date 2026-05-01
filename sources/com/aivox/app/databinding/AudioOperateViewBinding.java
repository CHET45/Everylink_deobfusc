package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class AudioOperateViewBinding extends ViewDataBinding {
    public final LinearLayout llContainer;
    public final TextView tvCopyLink;
    public final TextView tvDelete;
    public final TextView tvEditFile;
    public final TextView tvExportAudio;
    public final TextView tvExportFile;
    public final TextView tvShareToUser;

    protected AudioOperateViewBinding(Object obj, View view2, int i, LinearLayout linearLayout, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.llContainer = linearLayout;
        this.tvCopyLink = textView;
        this.tvDelete = textView2;
        this.tvEditFile = textView3;
        this.tvExportAudio = textView4;
        this.tvExportFile = textView5;
        this.tvShareToUser = textView6;
    }

    public static AudioOperateViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AudioOperateViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_operate_view, viewGroup, z, obj);
    }

    public static AudioOperateViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AudioOperateViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_operate_view, null, false, obj);
    }

    public static AudioOperateViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateViewBinding bind(View view2, Object obj) {
        return (AudioOperateViewBinding) bind(obj, view2, C0726R.layout.audio_operate_view);
    }
}
