package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;

/* JADX INFO: loaded from: classes.dex */
public abstract class AudioShareViewBinding extends ViewDataBinding {
    public final ConstraintLayout clAllowSave;
    public final ConstraintLayout clCircle;
    public final ConstraintLayout clContent;
    public final ConstraintLayout clDeleteAfterRead;
    public final ConstraintLayout clEncrypt;
    public final ConstraintLayout clLine;
    public final ConstraintLayout clQq;
    public final ConstraintLayout clShareUrl;
    public final ConstraintLayout clWechat;
    public final DialogTitleItemView dtivItemTitle;
    public final DialogTitleItemView dtivTimeTitle;
    public final DialogTitleView dtvTitle;
    public final TextView fileShareCircle;
    public final TextView fileShareLine;
    public final TextView fileShareQq;
    public final TextView fileShareWechat;
    public final Group groupContent;
    public final HorizontalScrollView hsvValidTime;
    public final ImageView ivAllowSave;
    public final ImageView ivCircle;
    public final ImageView ivDeleteAfterRead;
    public final ImageView ivEncrypt;
    public final ImageView ivLine;
    public final ImageView ivQq;
    public final ImageView ivShareUrl;
    public final ImageView ivWx;
    public final LinearLayout llCopyLink;
    public final LinearLayout llShare;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final TextView tvAllowSave;
    public final TextView tvCopyLink;
    public final TextView tvDay1;
    public final TextView tvDay30;
    public final TextView tvDay7;
    public final TextView tvDay90;
    public final TextView tvDeleteAfterRead;
    public final TextView tvEncrypt;
    public final TextView tvLink;
    public final TextView tvShare;
    public final TextView tvShareUrl;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected AudioShareViewBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, ConstraintLayout constraintLayout5, ConstraintLayout constraintLayout6, ConstraintLayout constraintLayout7, ConstraintLayout constraintLayout8, ConstraintLayout constraintLayout9, DialogTitleItemView dialogTitleItemView, DialogTitleItemView dialogTitleItemView2, DialogTitleView dialogTitleView, TextView textView, TextView textView2, TextView textView3, TextView textView4, Group group, HorizontalScrollView horizontalScrollView, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, LinearLayout linearLayout, LinearLayout linearLayout2, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10, TextView textView11, TextView textView12, TextView textView13, TextView textView14, TextView textView15) {
        super(obj, view2, i);
        this.clAllowSave = constraintLayout;
        this.clCircle = constraintLayout2;
        this.clContent = constraintLayout3;
        this.clDeleteAfterRead = constraintLayout4;
        this.clEncrypt = constraintLayout5;
        this.clLine = constraintLayout6;
        this.clQq = constraintLayout7;
        this.clShareUrl = constraintLayout8;
        this.clWechat = constraintLayout9;
        this.dtivItemTitle = dialogTitleItemView;
        this.dtivTimeTitle = dialogTitleItemView2;
        this.dtvTitle = dialogTitleView;
        this.fileShareCircle = textView;
        this.fileShareLine = textView2;
        this.fileShareQq = textView3;
        this.fileShareWechat = textView4;
        this.groupContent = group;
        this.hsvValidTime = horizontalScrollView;
        this.ivAllowSave = imageView;
        this.ivCircle = imageView2;
        this.ivDeleteAfterRead = imageView3;
        this.ivEncrypt = imageView4;
        this.ivLine = imageView5;
        this.ivQq = imageView6;
        this.ivShareUrl = imageView7;
        this.ivWx = imageView8;
        this.llCopyLink = linearLayout;
        this.llShare = linearLayout2;
        this.tvAllowSave = textView5;
        this.tvCopyLink = textView6;
        this.tvDay1 = textView7;
        this.tvDay30 = textView8;
        this.tvDay7 = textView9;
        this.tvDay90 = textView10;
        this.tvDeleteAfterRead = textView11;
        this.tvEncrypt = textView12;
        this.tvLink = textView13;
        this.tvShare = textView14;
        this.tvShareUrl = textView15;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static AudioShareViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioShareViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AudioShareViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_share_view, viewGroup, z, obj);
    }

    public static AudioShareViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioShareViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AudioShareViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_share_view, null, false, obj);
    }

    public static AudioShareViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioShareViewBinding bind(View view2, Object obj) {
        return (AudioShareViewBinding) bind(obj, view2, C0726R.layout.audio_share_view);
    }
}
