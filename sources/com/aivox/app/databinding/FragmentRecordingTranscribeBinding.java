package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentRecordingTranscribeBinding extends ViewDataBinding {
    public final TextView btnToTranscribe;
    public final ConstraintLayout clBg;
    public final ImageView ivToBottom;
    public final RecyclerView recyclerview;
    public final TextView tvRecordPrepare;

    protected FragmentRecordingTranscribeBinding(Object obj, View view2, int i, TextView textView, ConstraintLayout constraintLayout, ImageView imageView, RecyclerView recyclerView, TextView textView2) {
        super(obj, view2, i);
        this.btnToTranscribe = textView;
        this.clBg = constraintLayout;
        this.ivToBottom = imageView;
        this.recyclerview = recyclerView;
        this.tvRecordPrepare = textView2;
    }

    public static FragmentRecordingTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordingTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentRecordingTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_recording_transcribe, viewGroup, z, obj);
    }

    public static FragmentRecordingTranscribeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordingTranscribeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentRecordingTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_recording_transcribe, null, false, obj);
    }

    public static FragmentRecordingTranscribeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordingTranscribeBinding bind(View view2, Object obj) {
        return (FragmentRecordingTranscribeBinding) bind(obj, view2, C0726R.layout.fragment_recording_transcribe);
    }
}
