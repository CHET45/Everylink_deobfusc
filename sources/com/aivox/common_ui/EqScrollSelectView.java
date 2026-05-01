package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.common.MyEnum;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.EqScrollSelectViewLayoutBinding;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class EqScrollSelectView extends BaseDialogViewWrapper {

    public interface DateSelectListener {
        void onDateSelected(int i);
    }

    public EqScrollSelectView(Context context, final DateSelectListener dateSelectListener) {
        super(context);
        final EqScrollSelectViewLayoutBinding eqScrollSelectViewLayoutBindingInflate = EqScrollSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        eqScrollSelectViewLayoutBindingInflate.wvEq.setData(Arrays.asList(context.getString(MyEnum.DEVICE_EFFECT.DYNAMIC.msgRes), context.getString(MyEnum.DEVICE_EFFECT.VOCAL.msgRes), context.getString(MyEnum.DEVICE_EFFECT.OFF.msgRes), context.getString(MyEnum.DEVICE_EFFECT.BASS.msgRes), context.getString(MyEnum.DEVICE_EFFECT.CLEAR.msgRes)));
        eqScrollSelectViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.EqScrollSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2500lambda$new$0$comaivoxcommon_uiEqScrollSelectView(dateSelectListener, eqScrollSelectViewLayoutBindingInflate, view2);
            }
        });
        eqScrollSelectViewLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.EqScrollSelectView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2501lambda$new$1$comaivoxcommon_uiEqScrollSelectView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-EqScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2500lambda$new$0$comaivoxcommon_uiEqScrollSelectView(DateSelectListener dateSelectListener, EqScrollSelectViewLayoutBinding eqScrollSelectViewLayoutBinding, View view2) {
        dateSelectListener.onDateSelected(eqScrollSelectViewLayoutBinding.wvEq.getCurrentPosition());
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-EqScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2501lambda$new$1$comaivoxcommon_uiEqScrollSelectView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
