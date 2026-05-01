package com.aivox.set.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.aivox.set.databinding.TimeChargeNoticeViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class TimeChargeNoticeView extends LinearLayout {
    private TimeChargeNoticeViewLayoutBinding mBinding;

    public TimeChargeNoticeView(Context context) {
        this(context, null);
    }

    public TimeChargeNoticeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimeChargeNoticeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mBinding = TimeChargeNoticeViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setContent(String str, String str2) {
        this.mBinding.tvContent.setTextWithBackgroundColor(str, str2, Color.parseColor("#FFF1CC"), Color.parseColor("#333333"));
    }
}
