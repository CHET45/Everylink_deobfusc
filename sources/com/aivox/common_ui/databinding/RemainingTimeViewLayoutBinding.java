package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.CircleProgressBar;

/* JADX INFO: loaded from: classes.dex */
public abstract class RemainingTimeViewLayoutBinding extends ViewDataBinding {
    public final CircleProgressBar cpv;
    public final TextView tvCurProgress;
    public final TextView tvPlusSymbol;
    public final TextView tvTimeType;

    protected RemainingTimeViewLayoutBinding(Object obj, View view2, int i, CircleProgressBar circleProgressBar, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.cpv = circleProgressBar;
        this.tvCurProgress = textView;
        this.tvPlusSymbol = textView2;
        this.tvTimeType = textView3;
    }

    public static RemainingTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainingTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (RemainingTimeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remaining_time_view_layout, viewGroup, z, obj);
    }

    public static RemainingTimeViewLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainingTimeViewLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (RemainingTimeViewLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.remaining_time_view_layout, null, false, obj);
    }

    public static RemainingTimeViewLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static RemainingTimeViewLayoutBinding bind(View view2, Object obj) {
        return (RemainingTimeViewLayoutBinding) bind(obj, view2, C1034R.layout.remaining_time_view_layout);
    }
}
