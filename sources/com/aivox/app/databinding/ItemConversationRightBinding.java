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
import com.aivox.common.model.Transcribe;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemConversationRightBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f128cl;
    public final ImageView ivAvatar;
    public final ImageView ivToAudio;
    public final ImageView lineContent;

    @Bindable
    protected Transcribe mXmlmodel;
    public final TextView tvContent;
    public final TextView tvTime;
    public final TextView tvTranslateResult;

    public abstract void setXmlmodel(Transcribe transcribe);

    protected ItemConversationRightBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.f128cl = constraintLayout;
        this.ivAvatar = imageView;
        this.ivToAudio = imageView2;
        this.lineContent = imageView3;
        this.tvContent = textView;
        this.tvTime = textView2;
        this.tvTranslateResult = textView3;
    }

    public Transcribe getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemConversationRightBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationRightBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemConversationRightBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_right, viewGroup, z, obj);
    }

    public static ItemConversationRightBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationRightBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemConversationRightBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_right, null, false, obj);
    }

    public static ItemConversationRightBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationRightBinding bind(View view2, Object obj) {
        return (ItemConversationRightBinding) bind(obj, view2, C0726R.layout.item_conversation_right);
    }
}
