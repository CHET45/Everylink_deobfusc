package com.aivox.common_ui;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.base.C0874R;
import com.aivox.base.util.LogUtil;
import com.aivox.common_ui.databinding.VipFeatureViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class VipFeatureView extends FrameLayout {
    public VipFeatureView(Context context) {
        this(context, null);
    }

    public VipFeatureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VipFeatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        VipFeatureViewLayoutBinding vipFeatureViewLayoutBindingInflate = VipFeatureViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        LogUtil.m336e(context.getString(C0874R.string.vip_feature_time));
        vipFeatureViewLayoutBindingInflate.tvFeature1.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_time), 0));
        vipFeatureViewLayoutBindingInflate.tvFeature2.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_ai), 0));
        vipFeatureViewLayoutBindingInflate.tvFeature3.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_cloud), 0));
        vipFeatureViewLayoutBindingInflate.tvFeature4.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_img), 0));
        vipFeatureViewLayoutBindingInflate.tvFeature5.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_folder), 0));
        vipFeatureViewLayoutBindingInflate.tvFeature6.setText(Html.fromHtml(context.getString(C0874R.string.vip_feature_import), 0));
    }
}
