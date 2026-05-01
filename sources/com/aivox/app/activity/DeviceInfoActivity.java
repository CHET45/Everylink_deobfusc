package com.aivox.app.activity;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityDeviceInfoBinding;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.common.base.BaseFragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfoActivity extends BaseFragmentActivity {
    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        String string;
        ActivityDeviceInfoBinding activityDeviceInfoBinding = (ActivityDeviceInfoBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_device_info);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            string = "";
        } else {
            string = extras.getString(Constant.KEY_FLAG);
        }
        activityDeviceInfoBinding.titleView.setTitle(string);
        activityDeviceInfoBinding.ivDeviceIcon.setImageResource(MyEnum.DEVICE_MODEL.getDeviceIcon(string));
        activityDeviceInfoBinding.stvHowToPair.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceInfoActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2096lambda$initView$0$comaivoxappactivityDeviceInfoActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-DeviceInfoActivity, reason: not valid java name */
    /* synthetic */ void m2096lambda$initView$0$comaivoxappactivityDeviceInfoActivity(View view2) {
        ARouterUtils.startWithContext(this.context, MainAction.DEVICE_HELP);
    }
}
