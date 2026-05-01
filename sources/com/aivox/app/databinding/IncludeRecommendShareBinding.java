package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeRecommendShareBinding extends ViewDataBinding {
    public final TextView btnClose;
    public final ConstraintLayout cl1Share;
    public final ConstraintLayout clCircle;
    public final ConstraintLayout clQq;
    public final ConstraintLayout clShareUrl;
    public final ConstraintLayout clWechat;
    public final TextView fileShareCircle;
    public final TextView fileShareQq;
    public final TextView fileShareWechat;
    public final ImageView ivCircle;
    public final ImageView ivQq;
    public final ImageView ivShareUrl;
    public final ImageView ivWechat;
    public final View lineShare;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final TextView tvShareTitle;
    public final TextView tvShareUrl;
    public final TextView tvToshare;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected IncludeRecommendShareBinding(Object obj, View view2, int i, TextView textView, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, ConstraintLayout constraintLayout5, TextView textView2, TextView textView3, TextView textView4, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view3, TextView textView5, TextView textView6, TextView textView7) {
        super(obj, view2, i);
        this.btnClose = textView;
        this.cl1Share = constraintLayout;
        this.clCircle = constraintLayout2;
        this.clQq = constraintLayout3;
        this.clShareUrl = constraintLayout4;
        this.clWechat = constraintLayout5;
        this.fileShareCircle = textView2;
        this.fileShareQq = textView3;
        this.fileShareWechat = textView4;
        this.ivCircle = imageView;
        this.ivQq = imageView2;
        this.ivShareUrl = imageView3;
        this.ivWechat = imageView4;
        this.lineShare = view3;
        this.tvShareTitle = textView5;
        this.tvShareUrl = textView6;
        this.tvToshare = textView7;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static IncludeRecommendShareBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeRecommendShareBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeRecommendShareBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_recommend_share, viewGroup, z, obj);
    }

    public static IncludeRecommendShareBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeRecommendShareBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeRecommendShareBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.include_recommend_share, null, false, obj);
    }

    public static IncludeRecommendShareBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeRecommendShareBinding bind(View view2, Object obj) {
        return (IncludeRecommendShareBinding) bind(obj, view2, C0726R.layout.include_recommend_share);
    }
}
