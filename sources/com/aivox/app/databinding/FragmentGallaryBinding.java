package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentGallaryBinding extends ViewDataBinding {
    public final ConstraintLayout clSyncProgress;
    public final CircularProgressIndicator cpi;
    public final GridLayout glFunction;
    public final ImageView ivCancel;
    public final ImageView ivFavorite;
    public final ImageView ivSettings;
    public final LinearLayout llDelete;
    public final LinearLayout llFavorite;
    public final LinearLayout llSave;
    public final LinearLayout llSync;
    public final LinearLayout llTab;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final RelativeLayout rlSync;
    public final RecyclerView rvGallery;
    public final TextView tvAll;
    public final TextView tvComplete;
    public final TextView tvFavorite;
    public final TextView tvFile;
    public final TextView tvFileCount;
    public final TextView tvHint;
    public final TextView tvPhoto;
    public final TextView tvProgress;
    public final TextView tvSelectAll;
    public final TextView tvSelectCount;
    public final TextView tvSync;
    public final TextView tvTitle;
    public final TextView tvVideo;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentGallaryBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, CircularProgressIndicator circularProgressIndicator, GridLayout gridLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, RelativeLayout relativeLayout, RecyclerView recyclerView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10, TextView textView11, TextView textView12, TextView textView13) {
        super(obj, view2, i);
        this.clSyncProgress = constraintLayout;
        this.cpi = circularProgressIndicator;
        this.glFunction = gridLayout;
        this.ivCancel = imageView;
        this.ivFavorite = imageView2;
        this.ivSettings = imageView3;
        this.llDelete = linearLayout;
        this.llFavorite = linearLayout2;
        this.llSave = linearLayout3;
        this.llSync = linearLayout4;
        this.llTab = linearLayout5;
        this.rlSync = relativeLayout;
        this.rvGallery = recyclerView;
        this.tvAll = textView;
        this.tvComplete = textView2;
        this.tvFavorite = textView3;
        this.tvFile = textView4;
        this.tvFileCount = textView5;
        this.tvHint = textView6;
        this.tvPhoto = textView7;
        this.tvProgress = textView8;
        this.tvSelectAll = textView9;
        this.tvSelectCount = textView10;
        this.tvSync = textView11;
        this.tvTitle = textView12;
        this.tvVideo = textView13;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentGallaryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentGallaryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentGallaryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_gallary, viewGroup, z, obj);
    }

    public static FragmentGallaryBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentGallaryBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentGallaryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_gallary, null, false, obj);
    }

    public static FragmentGallaryBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentGallaryBinding bind(View view2, Object obj) {
        return (FragmentGallaryBinding) bind(obj, view2, C0726R.layout.fragment_gallary);
    }
}
