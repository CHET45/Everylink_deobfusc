package com.aivox.account.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.account.databinding.IncludeSetGenderBinding;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class SetGenderView extends BaseDialogViewWrapper {

    public interface ClickListener {
        void selectGender(int i);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public SetGenderView(Context context) {
        super(context);
    }

    public SetGenderView(Context context, int i, final ClickListener clickListener) {
        super(context);
        final IncludeSetGenderBinding includeSetGenderBindingInflate = IncludeSetGenderBinding.inflate(LayoutInflater.from(context), this, true);
        includeSetGenderBindingInflate.tvMale.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.view.SetGenderView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2060lambda$new$0$comaivoxaccountviewSetGenderView(includeSetGenderBindingInflate, clickListener, view2);
            }
        });
        includeSetGenderBindingInflate.tvFemale.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.view.SetGenderView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2061lambda$new$1$comaivoxaccountviewSetGenderView(includeSetGenderBindingInflate, clickListener, view2);
            }
        });
        includeSetGenderBindingInflate.tvClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.view.SetGenderView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2062lambda$new$2$comaivoxaccountviewSetGenderView(clickListener, view2);
            }
        });
        if (i == 1) {
            includeSetGenderBindingInflate.tvMale.setBackgroundResource(C1034R.drawable.btn_gender_select);
            includeSetGenderBindingInflate.tvFemale.setBackgroundResource(0);
        } else if (i == 2) {
            includeSetGenderBindingInflate.tvMale.setBackgroundResource(0);
            includeSetGenderBindingInflate.tvFemale.setBackgroundResource(C1034R.drawable.btn_gender_select);
        } else {
            includeSetGenderBindingInflate.tvMale.setBackgroundResource(0);
            includeSetGenderBindingInflate.tvFemale.setBackgroundResource(0);
        }
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-account-view-SetGenderView, reason: not valid java name */
    /* synthetic */ void m2060lambda$new$0$comaivoxaccountviewSetGenderView(IncludeSetGenderBinding includeSetGenderBinding, ClickListener clickListener, View view2) {
        includeSetGenderBinding.tvMale.setBackgroundResource(C1034R.drawable.btn_gender_select);
        includeSetGenderBinding.tvFemale.setBackgroundResource(0);
        if (clickListener != null) {
            clickListener.selectGender(1);
        }
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-account-view-SetGenderView, reason: not valid java name */
    /* synthetic */ void m2061lambda$new$1$comaivoxaccountviewSetGenderView(IncludeSetGenderBinding includeSetGenderBinding, ClickListener clickListener, View view2) {
        includeSetGenderBinding.tvMale.setBackgroundResource(0);
        includeSetGenderBinding.tvFemale.setBackgroundResource(C1034R.drawable.btn_gender_select);
        if (clickListener != null) {
            clickListener.selectGender(2);
        }
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$2$com-aivox-account-view-SetGenderView, reason: not valid java name */
    /* synthetic */ void m2062lambda$new$2$comaivoxaccountviewSetGenderView(ClickListener clickListener, View view2) {
        if (clickListener != null) {
            clickListener.selectGender(-1);
        }
        this.mDialog.dismiss();
    }
}
