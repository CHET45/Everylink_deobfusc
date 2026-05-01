package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.LangSwitchView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityRecordingBinding extends ViewDataBinding {
    public final ImageView audioOverIv;
    public final ImageView audioStartIv;
    public final ImageView audioStopIv;
    public final LinearLayout clBottom;
    public final Toolbar commonToolbar;
    public final FrameLayout flContainer;
    public final ImageView ivAddMark;
    public final ImageView ivInsertImg;
    public final ImageView ivLeading;
    public final ImageView ivRecording;
    public final ImageView ivTtsSwitch;
    public final LangSwitchView langSwitchView;
    public final LinearLayout llNotify;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final ImageView rightIcon;
    public final ImageView rightTwoIcon;
    public final LinearLayout rlBtn;
    public final TextView tvNotify;
    public final TextView tvOnPause;
    public final TextView tvOnlyRecording;
    public final TextView tvRecordTime;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityRecordingBinding(Object obj, View view2, int i, ImageView imageView, ImageView imageView2, ImageView imageView3, LinearLayout linearLayout, Toolbar toolbar, FrameLayout frameLayout, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, LangSwitchView langSwitchView, LinearLayout linearLayout2, ImageView imageView9, ImageView imageView10, LinearLayout linearLayout3, TextView textView, TextView textView2, TextView textView3, TextView textView4) {
        super(obj, view2, i);
        this.audioOverIv = imageView;
        this.audioStartIv = imageView2;
        this.audioStopIv = imageView3;
        this.clBottom = linearLayout;
        this.commonToolbar = toolbar;
        this.flContainer = frameLayout;
        this.ivAddMark = imageView4;
        this.ivInsertImg = imageView5;
        this.ivLeading = imageView6;
        this.ivRecording = imageView7;
        this.ivTtsSwitch = imageView8;
        this.langSwitchView = langSwitchView;
        this.llNotify = linearLayout2;
        this.rightIcon = imageView9;
        this.rightTwoIcon = imageView10;
        this.rlBtn = linearLayout3;
        this.tvNotify = textView;
        this.tvOnPause = textView2;
        this.tvOnlyRecording = textView3;
        this.tvRecordTime = textView4;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_recording, viewGroup, z, obj);
    }

    public static ActivityRecordingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityRecordingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_recording, null, false, obj);
    }

    public static ActivityRecordingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordingBinding bind(View view2, Object obj) {
        return (ActivityRecordingBinding) bind(obj, view2, C0726R.layout.activity_recording);
    }
}
