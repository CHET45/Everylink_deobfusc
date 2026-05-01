package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.Transcribe;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemTranscribeFloatingBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f132cl;

    @Bindable
    protected Transcribe mXmlmodel;
    public final TextView tvContent;
    public final TextView tvTranslateResult;

    public abstract void setXmlmodel(Transcribe transcribe);

    protected ItemTranscribeFloatingBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.f132cl = constraintLayout;
        this.tvContent = textView;
        this.tvTranslateResult = textView2;
    }

    public Transcribe getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemTranscribeFloatingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeFloatingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemTranscribeFloatingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_transcribe_floating, viewGroup, z, obj);
    }

    public static ItemTranscribeFloatingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeFloatingBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemTranscribeFloatingBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_transcribe_floating, null, false, obj);
    }

    public static ItemTranscribeFloatingBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemTranscribeFloatingBinding bind(View view2, Object obj) {
        return (ItemTranscribeFloatingBinding) bind(obj, view2, C0726R.layout.item_transcribe_floating);
    }
}
