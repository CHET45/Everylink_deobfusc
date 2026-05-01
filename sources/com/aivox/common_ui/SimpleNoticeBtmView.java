package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.SimpleNoticeBtmViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class SimpleNoticeBtmView extends BaseDialogViewWrapper {

    public interface ButtonClickListener {
        void onBtnClick();
    }

    public SimpleNoticeBtmView(Context context, int i, int i2, int i3, final ButtonClickListener buttonClickListener) {
        super(context);
        SimpleNoticeBtmViewLayoutBinding simpleNoticeBtmViewLayoutBindingInflate = SimpleNoticeBtmViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        simpleNoticeBtmViewLayoutBindingInflate.tvDialogTitle.setText(i);
        simpleNoticeBtmViewLayoutBindingInflate.tvDialogMsg.setText(i2);
        simpleNoticeBtmViewLayoutBindingInflate.btnContinue.setText(context.getString(i3));
        simpleNoticeBtmViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.SimpleNoticeBtmView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                buttonClickListener.onBtnClick();
            }
        });
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
