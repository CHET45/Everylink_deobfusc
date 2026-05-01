package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.GearSeekBar;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityFontSizeBinding extends ViewDataBinding {
    public final LoadingButton btnSave;
    public final ConstraintLayout clBottom;
    public final LinearLayout clTotal;
    public final GearSeekBar gsbFontSize;
    public final HeadTitleLinearView titleView;
    public final TextView tvContent1;
    public final TextView tvContent2;
    public final TextView tvContent3;

    protected ActivityFontSizeBinding(Object obj, View view2, int i, LoadingButton loadingButton, ConstraintLayout constraintLayout, LinearLayout linearLayout, GearSeekBar gearSeekBar, HeadTitleLinearView headTitleLinearView, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.btnSave = loadingButton;
        this.clBottom = constraintLayout;
        this.clTotal = linearLayout;
        this.gsbFontSize = gearSeekBar;
        this.titleView = headTitleLinearView;
        this.tvContent1 = textView;
        this.tvContent2 = textView2;
        this.tvContent3 = textView3;
    }

    public static ActivityFontSizeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFontSizeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityFontSizeBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_font_size, viewGroup, z, obj);
    }

    public static ActivityFontSizeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFontSizeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityFontSizeBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_font_size, null, false, obj);
    }

    public static ActivityFontSizeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityFontSizeBinding bind(View view2, Object obj) {
        return (ActivityFontSizeBinding) bind(obj, view2, C1106R.layout.activity_font_size);
    }
}
