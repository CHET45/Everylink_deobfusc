package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.Transcribe;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeAudioDetailHeaderBinding extends ViewDataBinding {
    public final ImageView ivCloud;

    @Bindable
    protected Transcribe mXmlmodel;
    public final TextView tvAudioAddress;
    public final TextView tvCreateAt;
    public final TextView tvDuration;
    public final TextView tvTitle;

    public abstract void setXmlmodel(Transcribe transcribe);

    protected IncludeAudioDetailHeaderBinding(Object obj, View view2, int i, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.ivCloud = imageView;
        this.tvAudioAddress = textView;
        this.tvCreateAt = textView2;
        this.tvDuration = textView3;
        this.tvTitle = textView4;
    }

    public Transcribe getXmlmodel() {
        return this.mXmlmodel;
    }

    public static IncludeAudioDetailHeaderBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeAudioDetailHeaderBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeAudioDetailHeaderBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_audio_detail_header, viewGroup, z, obj);
    }

    public static IncludeAudioDetailHeaderBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeAudioDetailHeaderBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeAudioDetailHeaderBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_audio_detail_header, null, false, obj);
    }

    public static IncludeAudioDetailHeaderBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeAudioDetailHeaderBinding bind(View view2, Object obj) {
        return (IncludeAudioDetailHeaderBinding) bind(obj, view2, C0726R.layout.include_audio_detail_header);
    }
}
