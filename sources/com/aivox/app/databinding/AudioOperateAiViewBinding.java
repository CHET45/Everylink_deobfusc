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
public abstract class AudioOperateAiViewBinding extends ViewDataBinding {
    public final View lineSpeaker;
    public final LinearLayout llContainer;
    public final TextView tvAiSpeaker;
    public final TextView tvTranscribe;
    public final TextView tvTranslate;

    protected AudioOperateAiViewBinding(Object obj, View view2, int i, View view3, LinearLayout linearLayout, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.lineSpeaker = view3;
        this.llContainer = linearLayout;
        this.tvAiSpeaker = textView;
        this.tvTranscribe = textView2;
        this.tvTranslate = textView3;
    }

    public static AudioOperateAiViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateAiViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AudioOperateAiViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_operate_ai_view, viewGroup, z, obj);
    }

    public static AudioOperateAiViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateAiViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AudioOperateAiViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_operate_ai_view, null, false, obj);
    }

    public static AudioOperateAiViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioOperateAiViewBinding bind(View view2, Object obj) {
        return (AudioOperateAiViewBinding) bind(obj, view2, C0726R.layout.audio_operate_ai_view);
    }
}
