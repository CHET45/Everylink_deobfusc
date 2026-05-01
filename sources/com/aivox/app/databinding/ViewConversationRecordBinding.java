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
import com.aivox.app.view.RippleAnimationView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewConversationRecordBinding extends ViewDataBinding {
    public final ConstraintLayout clParent;
    public final ImageView ivMic;
    public final ImageView ivPause;

    @Bindable
    protected Integer mRippersColor;
    public final RippleAnimationView ripple;
    public final TextView tvTime;

    public abstract void setRippersColor(Integer num);

    protected ViewConversationRecordBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, RippleAnimationView rippleAnimationView, TextView textView) {
        super(obj, view2, i);
        this.clParent = constraintLayout;
        this.ivMic = imageView;
        this.ivPause = imageView2;
        this.ripple = rippleAnimationView;
        this.tvTime = textView;
    }

    public Integer getRippersColor() {
        return this.mRippersColor;
    }

    public static ViewConversationRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewConversationRecordBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ViewConversationRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.view_conversation_record, viewGroup, z, obj);
    }

    public static ViewConversationRecordBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewConversationRecordBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ViewConversationRecordBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.view_conversation_record, null, false, obj);
    }

    public static ViewConversationRecordBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ViewConversationRecordBinding bind(View view2, Object obj) {
        return (ViewConversationRecordBinding) bind(obj, view2, C0726R.layout.view_conversation_record);
    }
}
