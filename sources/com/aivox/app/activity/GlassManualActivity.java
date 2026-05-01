package com.aivox.app.activity;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityGlassManualBinding;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.alibaba.android.arouter.utils.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class GlassManualActivity extends BaseFragmentActivity implements OnViewClickListener {
    private ActivityGlassManualBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        String[] stringArray;
        ActivityGlassManualBinding activityGlassManualBinding = (ActivityGlassManualBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_glass_manual);
        this.mBinding = activityGlassManualBinding;
        activityGlassManualBinding.setClickListener(this);
        LogUtil.m335d("language", MultiLanguageUtil.getInstance().getLanguageLocale().toString());
        if (MultiLanguageUtil.getInstance().getLanguageLocale().toString().contains("zh_CN")) {
            stringArray = getResources().getStringArray(C0874R.array.wakeup_cmd_zh);
        } else {
            stringArray = getResources().getStringArray(C0874R.array.wakeup_cmd_zh_t);
        }
        String[] stringArray2 = getResources().getStringArray(C0874R.array.wakeup_cmd_en);
        for (int i = 0; i < stringArray.length; i++) {
            View viewInflate = LayoutInflater.from(this).inflate(C0726R.layout.item_glass_wakeup, (ViewGroup) null);
            ((TextView) viewInflate.findViewById(C0726R.id.tv_left)).setText(stringArray[i]);
            if (!TextUtils.isEmpty(stringArray2[i])) {
                ((TextView) viewInflate.findViewById(C0726R.id.tv_right)).setText(stringArray2[i]);
            }
            this.mBinding.llWakeup.addView(viewInflate);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        if (view2.getId() == this.mBinding.tvGuide.getId()) {
            this.mBinding.tvGuide.setTypeface(Typeface.defaultFromStyle(1));
            this.mBinding.tvGuide.setBackgroundResource(C1034R.drawable.bg_white_c10);
            this.mBinding.tvWakeup.setTypeface(Typeface.DEFAULT);
            this.mBinding.tvWakeup.setBackgroundResource(C1034R.drawable.bg_gray_c12);
            this.mBinding.clGuide.setVisibility(0);
            this.mBinding.llWakeup.setVisibility(8);
            this.mBinding.tvManual.setVisibility(0);
            return;
        }
        if (view2.getId() == this.mBinding.tvWakeup.getId()) {
            this.mBinding.tvGuide.setTypeface(Typeface.DEFAULT);
            this.mBinding.tvGuide.setBackgroundResource(C1034R.drawable.bg_gray_c12);
            this.mBinding.tvWakeup.setTypeface(Typeface.defaultFromStyle(1));
            this.mBinding.tvWakeup.setBackgroundResource(C1034R.drawable.bg_white_c10);
            this.mBinding.clGuide.setVisibility(8);
            this.mBinding.llWakeup.setVisibility(0);
            this.mBinding.tvManual.setVisibility(8);
            return;
        }
        if (view2.getId() == this.mBinding.tvManual.getId()) {
            BaseAppUtils.startActivityForWeb(this, "https://app.smalink.co/device/", ARouterUtils.getClass(MainAction.WEB));
        }
    }
}
