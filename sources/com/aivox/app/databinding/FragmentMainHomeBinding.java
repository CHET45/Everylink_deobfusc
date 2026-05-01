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

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentMainHomeBinding extends ViewDataBinding {
    public final ConstraintLayout clHomeTopBar;
    public final ImageView ivDotMsg;
    public final ImageView ivDotSys;
    public final ImageView ivImport;
    public final ImageView ivNotice;
    public final ImageView ivSplashLogo;
    public final LinearLayout llBilingual;
    public final LinearLayout llDevices;
    public final LinearLayout llNoti;
    public final LinearLayout llTranscribe;
    public final LinearLayout llTranslate;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final RecyclerView rvRecent;
    public final TextView tvBilingual;
    public final TextView tvDeviceConnected;
    public final TextView tvDevices;
    public final TextView tvNoFiles;
    public final TextView tvNoti;
    public final TextView tvRecentFiles;
    public final TextView tvShorthand;
    public final TextView tvTranslate;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentMainHomeBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, RecyclerView recyclerView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8) {
        super(obj, view2, i);
        this.clHomeTopBar = constraintLayout;
        this.ivDotMsg = imageView;
        this.ivDotSys = imageView2;
        this.ivImport = imageView3;
        this.ivNotice = imageView4;
        this.ivSplashLogo = imageView5;
        this.llBilingual = linearLayout;
        this.llDevices = linearLayout2;
        this.llNoti = linearLayout3;
        this.llTranscribe = linearLayout4;
        this.llTranslate = linearLayout5;
        this.rvRecent = recyclerView;
        this.tvBilingual = textView;
        this.tvDeviceConnected = textView2;
        this.tvDevices = textView3;
        this.tvNoFiles = textView4;
        this.tvNoti = textView5;
        this.tvRecentFiles = textView6;
        this.tvShorthand = textView7;
        this.tvTranslate = textView8;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentMainHomeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainHomeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentMainHomeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_home, viewGroup, z, obj);
    }

    public static FragmentMainHomeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainHomeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentMainHomeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_home, null, false, obj);
    }

    public static FragmentMainHomeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainHomeBinding bind(View view2, Object obj) {
        return (FragmentMainHomeBinding) bind(obj, view2, C0726R.layout.fragment_main_home);
    }
}
