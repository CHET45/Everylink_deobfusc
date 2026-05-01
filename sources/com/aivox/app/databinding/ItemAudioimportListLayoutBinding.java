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
import com.aivox.common.model.AudioInfoBean;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemAudioimportListLayoutBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: cl */
    public final ConstraintLayout f125cl;
    public final ConstraintLayout clTop;
    public final TextView itemInfoDuration;
    public final TextView itemInfoTv;
    public final TextView itemName;

    @Bindable
    protected AudioInfoBean mXmlmodel;

    public abstract void setXmlmodel(AudioInfoBean audioInfoBean);

    protected ItemAudioimportListLayoutBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, TextView textView, TextView textView2, TextView textView3) {
        super(obj, view2, i);
        this.f125cl = constraintLayout;
        this.clTop = constraintLayout2;
        this.itemInfoDuration = textView;
        this.itemInfoTv = textView2;
        this.itemName = textView3;
    }

    public AudioInfoBean getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemAudioimportListLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAudioimportListLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemAudioimportListLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_audioimport_list_layout, viewGroup, z, obj);
    }

    public static ItemAudioimportListLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAudioimportListLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemAudioimportListLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_audioimport_list_layout, null, false, obj);
    }

    public static ItemAudioimportListLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAudioimportListLayoutBinding bind(View view2, Object obj) {
        return (ItemAudioimportListLayoutBinding) bind(obj, view2, C0726R.layout.item_audioimport_list_layout);
    }
}
