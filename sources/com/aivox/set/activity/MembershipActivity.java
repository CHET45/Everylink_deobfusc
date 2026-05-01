package com.aivox.set.activity;

import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.SetAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityMembershipBinding;

/* JADX INFO: loaded from: classes.dex */
public class MembershipActivity extends BaseFragmentActivity implements OnViewClickListener {
    private ActivityMembershipBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityMembershipBinding activityMembershipBinding = (ActivityMembershipBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_membership);
        this.mBinding = activityMembershipBinding;
        activityMembershipBinding.setClickListener(this);
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        int id = view2.getId();
        if (id == this.mBinding.stvHistory.getId()) {
            ARouterUtils.startWithActivity(this, SetAction.SET_PURCHASE_HISTORY);
        } else if (id == this.mBinding.stvUpgrade.getId()) {
            BaseAppUtils.startActivityForWeb(this, getString(C0874R.string.h5_path_coupon), ARouterUtils.getClass(MainAction.WEB));
        }
    }
}
