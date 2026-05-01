package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.base.C0874R;
import com.aivox.common_ui.databinding.VipPackViewLayoutBinding;
import com.github.houbb.heaven.constant.PunctuationConst;

/* JADX INFO: loaded from: classes.dex */
public class VipPackView extends FrameLayout {
    public static final int PACK_TYPE_MONTH = 0;
    public static final int PACK_TYPE_YEAR = 1;
    private final VipPackViewLayoutBinding mBinding;
    private final Context mContext;

    public VipPackView(Context context) {
        this(context, null);
    }

    public VipPackView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VipPackView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        this.mBinding = VipPackViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setData(int i, double d) {
        if (i == 0) {
            this.mBinding.tvPackName.setText(C0874R.string.vip_pack_monthly);
            this.mBinding.tvPackPrice.setText(PunctuationConst.DOLLAR + this.mContext.getString(C0874R.string.price_holder_monthly, d + ""));
            this.mBinding.cvLabel.setVisibility(0);
            this.mBinding.clContent.setBackgroundColor(this.mContext.getColor(C0874R.color.bg_btn_highlight));
            return;
        }
        this.mBinding.tvPackName.setText(C0874R.string.vip_pack_yearly);
        this.mBinding.tvPackPrice.setText(PunctuationConst.DOLLAR + this.mContext.getString(C0874R.string.price_holder_annually, d + ""));
        this.mBinding.cvLabel.setVisibility(4);
        this.mBinding.clContent.setBackgroundColor(this.mContext.getColor(C0874R.color.bg_btn_bright));
        this.mBinding.tvPackName.setTextSize(2, 17.0f);
    }

    public void setTrialData(String str) {
        this.mBinding.tvPackName.setText(C0874R.string.vip_pack_monthly);
        this.mBinding.tvPackPrice.setText(this.mContext.getString(C0874R.string.price_holder_monthly, str));
        this.mBinding.tvLabel.setVisibility(0);
        this.mBinding.tvLabel.setText(C0874R.string.pro_label_trial);
        this.mBinding.clContent.setBackgroundColor(this.mContext.getColor(C0874R.color.bg_btn_highlight));
    }
}
