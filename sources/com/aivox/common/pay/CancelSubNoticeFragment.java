package com.aivox.common.pay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aivox.base.app.AppManager;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.common.base.BaseBottomSheetFragment;
import com.aivox.common.databinding.FragmentCancelSubNoticeBinding;

/* JADX INFO: loaded from: classes.dex */
public class CancelSubNoticeFragment extends BaseBottomSheetFragment {
    private CancelSubNoticeFragment() {
    }

    public static CancelSubNoticeFragment newInstance() {
        return new CancelSubNoticeFragment();
    }

    @Override // com.aivox.common.base.BaseBottomSheetFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FragmentCancelSubNoticeBinding fragmentCancelSubNoticeBindingInflate = FragmentCancelSubNoticeBinding.inflate(layoutInflater, viewGroup, false);
        fragmentCancelSubNoticeBindingInflate.btnGoHome.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.pay.CancelSubNoticeFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m348x5ab3ce12(view2);
            }
        });
        return fragmentCancelSubNoticeBindingInflate.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-common-pay-CancelSubNoticeFragment */
    /* synthetic */ void m348x5ab3ce12(View view2) {
        dismiss();
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }
}
