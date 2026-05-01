package com.aivox.common_ui.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.RoundCornerContainer;

/* JADX INFO: loaded from: classes.dex */
public abstract class BgaBannerItemLayoutBinding extends ViewDataBinding {
    public final RoundCornerContainer containerBannerItem;
    public final ImageView ivBannerItem;

    protected BgaBannerItemLayoutBinding(Object obj, View view2, int i, RoundCornerContainer roundCornerContainer, ImageView imageView) {
        super(obj, view2, i);
        this.containerBannerItem = roundCornerContainer;
        this.ivBannerItem = imageView;
    }

    public static BgaBannerItemLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BgaBannerItemLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (BgaBannerItemLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.bga_banner_item_layout, viewGroup, z, obj);
    }

    public static BgaBannerItemLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BgaBannerItemLayoutBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (BgaBannerItemLayoutBinding) ViewDataBinding.inflateInternal(layoutInflater, C1034R.layout.bga_banner_item_layout, null, false, obj);
    }

    public static BgaBannerItemLayoutBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static BgaBannerItemLayoutBinding bind(View view2, Object obj) {
        return (BgaBannerItemLayoutBinding) bind(obj, view2, C1034R.layout.bga_banner_item_layout);
    }
}
