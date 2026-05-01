package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityEarPhoneConversationRecordingBinding extends ViewDataBinding {
    public final ConstraintLayout clConversation;
    public final ImageView ivSwitchLang;
    public final ImageView ivWave;
    public final ConstraintLayout llBottom;
    public final LinearLayout llWave;

    @Bindable
    protected OnViewClickListener mClickListener;

    @Bindable
    protected Boolean mIsFaceMode;
    public final RecyclerView recyclerview;
    public final HeadTitleLinearView titleView;
    public final TextView tvLangMySide;
    public final TextView tvLangOtherSide;
    public final TextView tvLeftRecording;
    public final TextView tvRightRecording;
    public final TextView tvSpeakHint;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    public abstract void setIsFaceMode(Boolean bool);

    protected ActivityEarPhoneConversationRecordingBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ConstraintLayout constraintLayout2, LinearLayout linearLayout, RecyclerView recyclerView, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        super(obj, view2, i);
        this.clConversation = constraintLayout;
        this.ivSwitchLang = imageView;
        this.ivWave = imageView2;
        this.llBottom = constraintLayout2;
        this.llWave = linearLayout;
        this.recyclerview = recyclerView;
        this.titleView = headTitleLinearView;
        this.tvLangMySide = textView;
        this.tvLangOtherSide = textView2;
        this.tvLeftRecording = textView3;
        this.tvRightRecording = textView4;
        this.tvSpeakHint = textView5;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public Boolean getIsFaceMode() {
        return this.mIsFaceMode;
    }

    public static ActivityEarPhoneConversationRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEarPhoneConversationRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityEarPhoneConversationRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_ear_phone_conversation_recording, viewGroup, z, obj);
    }

    public static ActivityEarPhoneConversationRecordingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEarPhoneConversationRecordingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityEarPhoneConversationRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_ear_phone_conversation_recording, null, false, obj);
    }

    public static ActivityEarPhoneConversationRecordingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityEarPhoneConversationRecordingBinding bind(View view2, Object obj) {
        return (ActivityEarPhoneConversationRecordingBinding) bind(obj, view2, C0726R.layout.activity_ear_phone_conversation_recording);
    }
}
