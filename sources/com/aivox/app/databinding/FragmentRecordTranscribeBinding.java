package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.google.android.material.appbar.AppBarLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentRecordTranscribeBinding extends ViewDataBinding {
    public final AppBarLayout appbar;
    public final TextView btnToTranscribe;
    public final ConstraintLayout clAudioTitle;
    public final ConstraintLayout clBg;
    public final ImageView ivToBottom;
    public final LinearLayout llSwitch;
    public final RelativeLayout rlChatLayout;
    public final RecyclerView rvChatList;
    public final RecyclerView rvSearchList;
    public final RecyclerView rvTransList;
    public final TextView tvChatMsg;
    public final TextView tvRecordDate;
    public final TextView tvRecordLocation;
    public final TextView tvRecordTime;
    public final TextView tvRecordTitle;
    public final TextView tvSwitchContent;
    public final TextView tvSwitchSummary;
    public final View viewCoverSwitch;
    public final View viewCoverTitle;

    protected FragmentRecordTranscribeBinding(Object obj, View view2, int i, AppBarLayout appBarLayout, TextView textView, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ImageView imageView, LinearLayout linearLayout, RelativeLayout relativeLayout, RecyclerView recyclerView, RecyclerView recyclerView2, RecyclerView recyclerView3, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, View view3, View view4) {
        super(obj, view2, i);
        this.appbar = appBarLayout;
        this.btnToTranscribe = textView;
        this.clAudioTitle = constraintLayout;
        this.clBg = constraintLayout2;
        this.ivToBottom = imageView;
        this.llSwitch = linearLayout;
        this.rlChatLayout = relativeLayout;
        this.rvChatList = recyclerView;
        this.rvSearchList = recyclerView2;
        this.rvTransList = recyclerView3;
        this.tvChatMsg = textView2;
        this.tvRecordDate = textView3;
        this.tvRecordLocation = textView4;
        this.tvRecordTime = textView5;
        this.tvRecordTitle = textView6;
        this.tvSwitchContent = textView7;
        this.tvSwitchSummary = textView8;
        this.viewCoverSwitch = view3;
        this.viewCoverTitle = view4;
    }

    public static FragmentRecordTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordTranscribeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentRecordTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_record_transcribe, viewGroup, z, obj);
    }

    public static FragmentRecordTranscribeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordTranscribeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentRecordTranscribeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_record_transcribe, null, false, obj);
    }

    public static FragmentRecordTranscribeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentRecordTranscribeBinding bind(View view2, Object obj) {
        return (FragmentRecordTranscribeBinding) bind(obj, view2, C0726R.layout.fragment_record_transcribe);
    }
}
