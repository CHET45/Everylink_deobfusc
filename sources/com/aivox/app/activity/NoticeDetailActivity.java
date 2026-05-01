package com.aivox.app.activity;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityNoticeDetailBinding;
import com.aivox.base.common.Constant;
import com.aivox.common.base.BaseFragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDetailActivity extends BaseFragmentActivity {
    private ActivityNoticeDetailBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityNoticeDetailBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_notice_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString(Constant.KEY_TITLE);
            String string2 = extras.getString("data");
            this.mBinding.tvNoticeTitle.setText(string);
            this.mBinding.tvNoticeContent.setText(string2);
        }
    }
}
