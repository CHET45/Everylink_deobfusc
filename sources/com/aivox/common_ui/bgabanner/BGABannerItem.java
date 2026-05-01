package com.aivox.common_ui.bgabanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.aivox.common_ui.databinding.BgaBannerItemLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class BGABannerItem extends LinearLayout {
    BgaBannerItemLayoutBinding mBinding;

    public BGABannerItem(Context context) {
        this(context, null);
    }

    public BGABannerItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BGABannerItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        BgaBannerItemLayoutBinding bgaBannerItemLayoutBindingInflate = BgaBannerItemLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = bgaBannerItemLayoutBindingInflate;
        bgaBannerItemLayoutBindingInflate.containerBannerItem.setCornerRadiusDp(12.0f);
    }

    public ImageView getImageView() {
        return this.mBinding.ivBannerItem;
    }
}
