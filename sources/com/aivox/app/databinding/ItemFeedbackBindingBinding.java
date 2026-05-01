package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.FeedbackSelect;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemFeedbackBindingBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f129cl;
    public final ImageView img;
    public final ImageView ivDel;

    @Bindable
    protected FeedbackSelect mXmlmodel;

    public abstract void setXmlmodel(FeedbackSelect feedbackSelect);

    protected ItemFeedbackBindingBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2) {
        super(obj, view2, i);
        this.f129cl = constraintLayout;
        this.img = imageView;
        this.ivDel = imageView2;
    }

    public FeedbackSelect getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemFeedbackBindingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFeedbackBindingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemFeedbackBindingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_feedback_binding, viewGroup, z, obj);
    }

    public static ItemFeedbackBindingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFeedbackBindingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemFeedbackBindingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_feedback_binding, null, false, obj);
    }

    public static ItemFeedbackBindingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemFeedbackBindingBinding bind(View view2, Object obj) {
        return (ItemFeedbackBindingBinding) bind(obj, view2, C0726R.layout.item_feedback_binding);
    }
}
