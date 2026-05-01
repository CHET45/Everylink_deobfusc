package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.DeviceConnectDialogLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class DeviceConnectDialogView extends BaseDialogViewWrapper {
    private final DeviceConnectDialogLayoutBinding mBinding;
    private final MyClickListener mListener;

    public interface MyClickListener {
        void onBtnClick(String str, String str2);
    }

    public DeviceConnectDialogView(Context context, final String str, final String str2, MyClickListener myClickListener) {
        super(context);
        DeviceConnectDialogLayoutBinding deviceConnectDialogLayoutBindingInflate = DeviceConnectDialogLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = deviceConnectDialogLayoutBindingInflate;
        this.mListener = myClickListener;
        deviceConnectDialogLayoutBindingInflate.dtvTitle.setTitle(str);
        deviceConnectDialogLayoutBindingInflate.ivDeviceIcon.setImageResource(MyEnum.DEVICE_MODEL.getDeviceIcon(str));
        deviceConnectDialogLayoutBindingInflate.btnConnect.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.DeviceConnectDialogView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2498lambda$new$0$comaivoxcommon_uiDeviceConnectDialogView(str, str2, view2);
            }
        });
        deviceConnectDialogLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.DeviceConnectDialogView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2499lambda$new$1$comaivoxcommon_uiDeviceConnectDialogView(view2);
            }
        });
        if (MyEnum.DEVICE_MODEL.TRANSLATER_T3.name.equals(str)) {
            deviceConnectDialogLayoutBindingInflate.epLeft.setVisibility(4);
            deviceConnectDialogLayoutBindingInflate.epRight.setVisibility(4);
        }
        if (MyEnum.DEVICE_MODEL.HAPPYLEMON_HL3.name.equals(str)) {
            deviceConnectDialogLayoutBindingInflate.epPack.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-DeviceConnectDialogView, reason: not valid java name */
    /* synthetic */ void m2498lambda$new$0$comaivoxcommon_uiDeviceConnectDialogView(String str, String str2, View view2) {
        if (this.mListener != null) {
            this.mBinding.btnConnect.startLoading();
            this.mListener.onBtnClick(str, str2);
        }
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-DeviceConnectDialogView, reason: not valid java name */
    /* synthetic */ void m2499lambda$new$1$comaivoxcommon_uiDeviceConnectDialogView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void onConnectedSuccess(String str, String str2, String str3) {
        this.mBinding.tvConnectSuccess.setVisibility(0);
        this.mBinding.llBattery.setVisibility(0);
        this.mBinding.btnConnect.stopLoading();
        this.mBinding.btnConnect.setVisibility(8);
        this.mBinding.epLeft.setPower(str, 1);
        this.mBinding.epRight.setPower(str2, 2);
        if (BaseStringUtil.isEmpty(str3)) {
            this.mBinding.epPack.setVisibility(8);
        } else {
            this.mBinding.epPack.setVisibility(0);
            this.mBinding.epPack.setPower(str3, 3);
        }
    }

    public void onConnectedFailed() {
        this.mBinding.tvConnectFailed.setVisibility(0);
        this.mBinding.btnConnect.stopLoading();
    }
}
