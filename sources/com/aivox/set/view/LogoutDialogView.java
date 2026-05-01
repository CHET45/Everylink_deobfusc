package com.aivox.set.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.set.databinding.LogoutDialogViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class LogoutDialogView extends BaseDialogViewWrapper {
    private LogoutInterface mInterface;

    public interface LogoutInterface {
        void doLogout();
    }

    public LogoutDialogView(Context context) {
        super(context);
        LogoutDialogViewLayoutBinding logoutDialogViewLayoutBindingInflate = LogoutDialogViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        logoutDialogViewLayoutBindingInflate.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.view.LogoutDialogView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2578lambda$new$0$comaivoxsetviewLogoutDialogView(view2);
            }
        });
        logoutDialogViewLayoutBindingInflate.tvDevice.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.view.LogoutDialogView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2579lambda$new$1$comaivoxsetviewLogoutDialogView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-set-view-LogoutDialogView, reason: not valid java name */
    /* synthetic */ void m2578lambda$new$0$comaivoxsetviewLogoutDialogView(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-set-view-LogoutDialogView, reason: not valid java name */
    /* synthetic */ void m2579lambda$new$1$comaivoxsetviewLogoutDialogView(View view2) {
        this.mDialog.dismiss();
        LogoutInterface logoutInterface = this.mInterface;
        if (logoutInterface != null) {
            logoutInterface.doLogout();
        }
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public void setInterface(LogoutInterface logoutInterface) {
        this.mInterface = logoutInterface;
    }
}
