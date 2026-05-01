package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class AiSummaryOperateViewBinding extends ViewDataBinding {
    public final ConstraintLayout llContainer;
    public final TextView tvSummarySelect;

    protected AiSummaryOperateViewBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, TextView textView) {
        super(obj, view2, i);
        this.llContainer = constraintLayout;
        this.tvSummarySelect = textView;
    }

    public static AiSummaryOperateViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AiSummaryOperateViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AiSummaryOperateViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.ai_summary_operate_view, viewGroup, z, obj);
    }

    public static AiSummaryOperateViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AiSummaryOperateViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AiSummaryOperateViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.ai_summary_operate_view, null, false, obj);
    }

    public static AiSummaryOperateViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AiSummaryOperateViewBinding bind(View view2, Object obj) {
        return (AiSummaryOperateViewBinding) bind(obj, view2, C0726R.layout.ai_summary_operate_view);
    }
}
