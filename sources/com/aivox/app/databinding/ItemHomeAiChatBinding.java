package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemHomeAiChatBinding extends ViewDataBinding {
    public final ConstraintLayout clAnswer;
    public final ImageView ivBotAvatar;
    public final ImageView ivCopy;
    public final ImageView ivDelete;
    public final ImageView ivImage;
    public final ImageView ivRegenerate;
    public final TextView tvAnswer;
    public final TextView tvName;
    public final TextView tvQuestion;
    public final View vLine;

    protected ItemHomeAiChatBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, TextView textView, TextView textView2, TextView textView3, View view3) {
        super(obj, view2, i);
        this.clAnswer = constraintLayout;
        this.ivBotAvatar = imageView;
        this.ivCopy = imageView2;
        this.ivDelete = imageView3;
        this.ivImage = imageView4;
        this.ivRegenerate = imageView5;
        this.tvAnswer = textView;
        this.tvName = textView2;
        this.tvQuestion = textView3;
        this.vLine = view3;
    }

    public static ItemHomeAiChatBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeAiChatBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemHomeAiChatBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_home_ai_chat, viewGroup, z, obj);
    }

    public static ItemHomeAiChatBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeAiChatBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemHomeAiChatBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_home_ai_chat, null, false, obj);
    }

    public static ItemHomeAiChatBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemHomeAiChatBinding bind(View view2, Object obj) {
        return (ItemHomeAiChatBinding) bind(obj, view2, C0726R.layout.item_home_ai_chat);
    }
}
