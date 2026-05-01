package com.aivox.app.activity;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityFunctionGuideBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.common.base.BaseFragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class FunctionGuideActivity extends BaseFragmentActivity {
    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityFunctionGuideBinding activityFunctionGuideBinding = (ActivityFunctionGuideBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_function_guide);
        Bundle extras = getIntent().getExtras();
        int i = extras != null ? extras.getInt(Constant.KEY_FLAG) : 0;
        activityFunctionGuideBinding.titleView.setTitle(i == 0 ? C0874R.string.title_notes : C0874R.string.title_abridgement);
        activityFunctionGuideBinding.setType(Integer.valueOf(i));
    }
}
