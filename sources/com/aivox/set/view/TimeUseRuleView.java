package com.aivox.set.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ViewTimeUseRuleBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/* JADX INFO: loaded from: classes.dex */
public class TimeUseRuleView extends ConstraintLayout {
    BottomSheetDialog dialog;
    private ViewTimeUseRuleBinding mBinding;

    public TimeUseRuleView(Context context) {
        super(context);
    }

    public TimeUseRuleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ViewTimeUseRuleBinding viewTimeUseRuleBindingInflate = ViewTimeUseRuleBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = viewTimeUseRuleBindingInflate;
        viewTimeUseRuleBindingInflate.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.view.TimeUseRuleView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2580lambda$new$0$comaivoxsetviewTimeUseRuleView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-set-view-TimeUseRuleView, reason: not valid java name */
    /* synthetic */ void m2580lambda$new$0$comaivoxsetviewTimeUseRuleView(View view2) {
        BottomSheetDialog bottomSheetDialog;
        if (!AntiShake.check(view2) && view2.getId() == C1106R.id.iv_close && (bottomSheetDialog = this.dialog) != null && bottomSheetDialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public void setDialog(BottomSheetDialog bottomSheetDialog) {
        this.dialog = bottomSheetDialog;
    }

    public void setCloseClick(View.OnClickListener onClickListener) {
        this.mBinding.ivClose.setOnClickListener(onClickListener);
    }
}
