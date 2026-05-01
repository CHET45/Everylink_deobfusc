package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.C0874R;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.PwdForgetViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class PwdForgetView extends BaseDialogViewWrapper {

    public interface BtnTapListener {
        void onRetryTap();

        void onSendTap();
    }

    public PwdForgetView(Context context, String str, final BtnTapListener btnTapListener) {
        super(context);
        PwdForgetViewLayoutBinding pwdForgetViewLayoutBindingInflate = PwdForgetViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        pwdForgetViewLayoutBindingInflate.btnSend.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.PwdForgetView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2511lambda$new$0$comaivoxcommon_uiPwdForgetView(btnTapListener, view2);
            }
        });
        pwdForgetViewLayoutBindingInflate.btnTryAgain.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.PwdForgetView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2512lambda$new$1$comaivoxcommon_uiPwdForgetView(btnTapListener, view2);
            }
        });
        String string = context.getString(C0874R.string.account_send_code_holder, str);
        SpannableString spannableString = new SpannableString(string);
        int iIndexOf = string.indexOf(str);
        spannableString.setSpan(new StyleSpan(1), iIndexOf, str.length() + iIndexOf, 33);
        pwdForgetViewLayoutBindingInflate.tvDialogMsg.setText(spannableString);
        pwdForgetViewLayoutBindingInflate.titleView.setViewClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.PwdForgetView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2513lambda$new$2$comaivoxcommon_uiPwdForgetView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-PwdForgetView, reason: not valid java name */
    /* synthetic */ void m2511lambda$new$0$comaivoxcommon_uiPwdForgetView(BtnTapListener btnTapListener, View view2) {
        this.mDialog.dismiss();
        btnTapListener.onSendTap();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-PwdForgetView, reason: not valid java name */
    /* synthetic */ void m2512lambda$new$1$comaivoxcommon_uiPwdForgetView(BtnTapListener btnTapListener, View view2) {
        this.mDialog.dismiss();
        btnTapListener.onRetryTap();
    }

    /* JADX INFO: renamed from: lambda$new$2$com-aivox-common_ui-PwdForgetView, reason: not valid java name */
    /* synthetic */ void m2513lambda$new$2$comaivoxcommon_uiPwdForgetView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
