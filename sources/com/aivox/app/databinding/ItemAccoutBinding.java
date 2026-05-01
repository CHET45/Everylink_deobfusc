package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.Identity;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemAccoutBinding extends ViewDataBinding {

    /* JADX INFO: renamed from: iv */
    public final ImageView f123iv;
    public final RoundedCornerBitmap ivHead;

    @Bindable
    protected Identity mXmlmodel;

    /* JADX INFO: renamed from: tv */
    public final TextView f124tv;

    public abstract void setXmlmodel(Identity identity);

    protected ItemAccoutBinding(Object obj, View view2, int i, ImageView imageView, RoundedCornerBitmap roundedCornerBitmap, TextView textView) {
        super(obj, view2, i);
        this.f123iv = imageView;
        this.ivHead = roundedCornerBitmap;
        this.f124tv = textView;
    }

    public Identity getXmlmodel() {
        return this.mXmlmodel;
    }

    public static ItemAccoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAccoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ItemAccoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_accout, viewGroup, z, obj);
    }

    public static ItemAccoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAccoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ItemAccoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.item_accout, null, false, obj);
    }

    public static ItemAccoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAccoutBinding bind(View view2, Object obj) {
        return (ItemAccoutBinding) bind(obj, view2, C0726R.layout.item_accout);
    }
}
