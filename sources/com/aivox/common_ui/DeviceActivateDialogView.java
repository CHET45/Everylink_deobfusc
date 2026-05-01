package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.C0874R;
import com.aivox.base.common.MyEnum;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.DeviceActivateDialogLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class DeviceActivateDialogView extends BaseDialogViewWrapper {
    private boolean mActivateSuccess;
    private final DeviceActivateDialogLayoutBinding mBinding;

    public DeviceActivateDialogView(Context context, String str, String str2) {
        super(context);
        DeviceActivateDialogLayoutBinding deviceActivateDialogLayoutBindingInflate = DeviceActivateDialogLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = deviceActivateDialogLayoutBindingInflate;
        deviceActivateDialogLayoutBindingInflate.ivDeviceIcon.setImageResource(MyEnum.DEVICE_MODEL.getDeviceDotIcon(str));
        deviceActivateDialogLayoutBindingInflate.dtvTitle.setViewClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.DeviceActivateDialogView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2497lambda$new$0$comaivoxcommon_uiDeviceActivateDialogView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-DeviceActivateDialogView, reason: not valid java name */
    /* synthetic */ void m2497lambda$new$0$comaivoxcommon_uiDeviceActivateDialogView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void onActivateSuccess() {
        this.mActivateSuccess = true;
        this.mBinding.tvActivateSuccess.setVisibility(0);
        this.mBinding.tvActivateFailed.setVisibility(8);
        this.mBinding.tvActivateMsg.setText(C0874R.string.device_activate_msg_success);
    }

    public void onActivateFailed() {
        this.mActivateSuccess = false;
        this.mBinding.tvActivateSuccess.setVisibility(8);
        this.mBinding.tvActivateFailed.setVisibility(0);
        this.mBinding.tvActivateMsg.setText(C0874R.string.device_activate_msg_failed);
    }

    public boolean isActivateSuccess() {
        return this.mActivateSuccess;
    }
}
