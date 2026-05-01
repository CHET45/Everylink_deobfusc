package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common_ui.databinding.RemainTimeViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class RemainTimeView extends FrameLayout {
    private RemainTimeViewLayoutBinding mBinding;

    public RemainTimeView(Context context) {
        this(context, null);
    }

    public RemainTimeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RemainTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    public void initView(Context context) {
        this.mBinding = RemainTimeViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setRemainingTime(long j, long j2, long j3, long j4) {
        this.mBinding.tvRemainTime.setText(BaseStringUtil.getHourStr(j, ""));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBinding.viewTimeVip.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mBinding.viewTimeOther.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBinding.viewTimePurchase.getLayoutParams();
        layoutParams.weight = j2;
        layoutParams2.weight = j3;
        layoutParams3.weight = j4;
        this.mBinding.viewTimeVip.setLayoutParams(layoutParams);
        this.mBinding.viewTimeOther.setLayoutParams(layoutParams2);
        this.mBinding.viewTimePurchase.setLayoutParams(layoutParams3);
    }
}
