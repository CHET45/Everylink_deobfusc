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
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemConversationLeftBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f127cl;
    public final RoundedCornerBitmap ivAvatar;
    public final ImageView ivToAudio;
    public final ImageView lineContent;

    @Bindable
    protected Transcribe mXmlmodel;
    public final TextView tvContent;
    public final TextView tvTime;
    public final TextView tvTranslateResult;

    public abstract void setXmlmodel(Transcribe transcribe);

    protected ItemConversationLeftBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, RoundedCornerBitmap roundedCornerBitmap, ImageView imageView, ImageView imageView2, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.f127cl = constraintLayout;
        this.ivAvatar = roundedCornerBitmap;
        this.ivToAudio = imageView;
        this.lineContent = imageView2;
        this.tvContent = textView;
        this.tvTime = textView2;
        this.tvTranslateResult = textView3;
    }

    public Transcribe getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemConversationLeftBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationLeftBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemConversationLeftBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_left, viewGroup, z, obj);
    }

    public static ItemConversationLeftBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationLeftBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemConversationLeftBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_conversation_left, null, false, obj);
    }

    public static ItemConversationLeftBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemConversationLeftBinding bind(View view2, Object obj) {
        return (ItemConversationLeftBinding) bind(obj, view2, C0726R.layout.item_conversation_left);
    }
}
