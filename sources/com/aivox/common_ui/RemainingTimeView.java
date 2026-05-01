package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.common_ui.databinding.RemainingTimeViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class RemainingTimeView extends FrameLayout {
    public static final long TIME_30_HOUR = 108000;
    public static final long TIME_3_HOUR = 10800;
    public static final int TIME_TYPE_COMM = 2;
    public static final int TIME_TYPE_FILE = 1;
    public static final int TIME_TYPE_REAL = 0;
    private RemainingTimeViewLayoutBinding mBinding;

    public RemainingTimeView(Context context) {
        this(context, null);
    }

    public RemainingTimeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RemainingTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        RemainingTimeViewLayoutBinding remainingTimeViewLayoutBindingInflate = RemainingTimeViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = remainingTimeViewLayoutBindingInflate;
        remainingTimeViewLayoutBindingInflate.cpv.setMax(Constant.EVENT.BLE_CONNECTED);
    }

    public void setType(int i) {
        if (i == 0) {
            this.mBinding.tvTimeType.setText(getResources().getString(C0874R.string.real_time_holder));
        } else if (i == 1) {
            this.mBinding.tvTimeType.setText(getResources().getString(C0874R.string.file_time_holder));
        } else {
            if (i != 2) {
                return;
            }
            this.mBinding.tvTimeType.setText(getResources().getString(C0874R.string.comm_time_holder));
        }
    }
}
