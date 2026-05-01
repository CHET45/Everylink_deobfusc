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
public abstract class ItemConversationHistoryBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f126cl;
    public final ImageView lineContent;
    public final TextView tvContent;
    public final TextView tvTranslation;

    protected ItemConversationHistoryBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ImageView imageView, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.f126cl = constraintLayout;
        this.lineContent = imageView;
        this.tvContent = textView;
        this.tvTranslation = textView2;
    }

    public static ItemConversationHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemConversationHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_history, viewGroup, z, obj);
    }

    public static ItemConversationHistoryBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationHistoryBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemConversationHistoryBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_history, null, false, obj);
    }

    public static ItemConversationHistoryBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationHistoryBinding bind(View view2, Object obj) {
        return (ItemConversationHistoryBinding) bind(obj, view2, C0726R.layout.item_conversation_history);
    }
}
