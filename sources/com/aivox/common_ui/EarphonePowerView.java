package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.aivox.base.util.LogUtil;
import com.aivox.common_ui.databinding.EarphonePowerBinding;
import com.github.houbb.heaven.constant.PunctuationConst;

/* JADX INFO: loaded from: classes.dex */
public class EarphonePowerView extends LinearLayout {
    private EarphonePowerBinding mBinding;

    public EarphonePowerView(Context context) {
        this(context, null);
    }

    public EarphonePowerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EarphonePowerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void init(Context context, AttributeSet attributeSet) {
        this.mBinding = EarphonePowerBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.EarphonePowerView);
        setImg(typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.EarphonePowerView_power_type, 3), 100);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setPower(String str, int i) {
        if (str.length() > 3) {
            str = str.substring(0, 3);
        }
        int i2 = Integer.parseInt(str.replaceAll("^0+(?!$)", ""));
        LogUtil.m335d("blebtservice", "setPower:" + i2);
        this.mBinding.tvPower.setText(i2 + PunctuationConst.PERCENT);
        setImg(i, i2);
    }

    public void setImg(int i, int i2) {
        if (i == 1) {
            this.mBinding.ivPower.setImageResource(i2 < 20 ? C1034R.drawable.ic_earphone_l_low : C1034R.drawable.ic_earphone_l);
        } else if (i == 2) {
            this.mBinding.ivPower.setImageResource(i2 < 20 ? C1034R.drawable.ic_earphone_r_low : C1034R.drawable.ic_earphone_r);
        } else {
            if (i != 3) {
                return;
            }
            this.mBinding.ivPower.setImageResource(i2 < 20 ? C1034R.drawable.ic_earphone_b_low : C1034R.drawable.ic_earphone_b);
        }
    }
}
