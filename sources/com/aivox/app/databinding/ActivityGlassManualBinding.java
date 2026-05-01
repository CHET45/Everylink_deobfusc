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
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityGlassManualBinding extends ViewDataBinding {
    public final ConstraintLayout clGuide;
    public final ImageView ivFull;
    public final LinearLayout llLeft;
    public final LinearLayout llRight;
    public final LinearLayout llTab;
    public final LinearLayout llWakeup;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final HeadTitleLinearView titleView;
    public final TextView tvGuide;
    public final TextView tvManual;
    public final TextView tvWakeup;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityGlassManualBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.clGuide = constraintLayout;
        this.ivFull = imageView;
        this.llLeft = linearLayout;
        this.llRight = linearLayout2;
        this.llTab = linearLayout3;
        this.llWakeup = linearLayout4;
        this.titleView = headTitleLinearView;
        this.tvGuide = textView;
        this.tvManual = textView2;
        this.tvWakeup = textView3;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityGlassManualBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassManualBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityGlassManualBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_glass_manual, viewGroup, z, obj);
    }

    public static ActivityGlassManualBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassManualBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityGlassManualBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_glass_manual, null, false, obj);
    }

    public static ActivityGlassManualBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGlassManualBinding bind(View view2, Object obj) {
        return (ActivityGlassManualBinding) bind(obj, view2, C0726R.layout.activity_glass_manual);
    }
}
