package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.C0874R;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.GenderScrollSelectViewLayoutBinding;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class GenderScrollSelectView extends BaseDialogViewWrapper {

    public interface DateSelectListener {
        void onDateSelected(int i);
    }

    public GenderScrollSelectView(Context context, final DateSelectListener dateSelectListener) {
        super(context);
        final GenderScrollSelectViewLayoutBinding genderScrollSelectViewLayoutBindingInflate = GenderScrollSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        genderScrollSelectViewLayoutBindingInflate.wvGender.setData(Arrays.asList(context.getString(C0874R.string.user_info_gender_male), context.getString(C0874R.string.user_info_gender_female), context.getString(C0874R.string.user_info_gender_other)));
        genderScrollSelectViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.GenderScrollSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2504lambda$new$0$comaivoxcommon_uiGenderScrollSelectView(dateSelectListener, genderScrollSelectViewLayoutBindingInflate, view2);
            }
        });
        genderScrollSelectViewLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.GenderScrollSelectView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2505lambda$new$1$comaivoxcommon_uiGenderScrollSelectView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-GenderScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2504lambda$new$0$comaivoxcommon_uiGenderScrollSelectView(DateSelectListener dateSelectListener, GenderScrollSelectViewLayoutBinding genderScrollSelectViewLayoutBinding, View view2) {
        dateSelectListener.onDateSelected(genderScrollSelectViewLayoutBinding.wvGender.getCurrentPosition() + 1);
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-GenderScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2505lambda$new$1$comaivoxcommon_uiGenderScrollSelectView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
