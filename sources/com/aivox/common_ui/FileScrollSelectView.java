package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.C0874R;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.FileScrollSelectViewLayoutBinding;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class FileScrollSelectView extends BaseDialogViewWrapper {

    public interface DateSelectListener {
        void onDateSelected(int i);
    }

    public FileScrollSelectView(Context context, final DateSelectListener dateSelectListener) {
        super(context);
        final FileScrollSelectViewLayoutBinding fileScrollSelectViewLayoutBindingInflate = FileScrollSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        fileScrollSelectViewLayoutBindingInflate.wvFile.setData(Arrays.asList(context.getString(C0874R.string.file_type_word), context.getString(C0874R.string.file_type_txt)));
        fileScrollSelectViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.FileScrollSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2502lambda$new$0$comaivoxcommon_uiFileScrollSelectView(dateSelectListener, fileScrollSelectViewLayoutBindingInflate, view2);
            }
        });
        fileScrollSelectViewLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.FileScrollSelectView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2503lambda$new$1$comaivoxcommon_uiFileScrollSelectView(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-FileScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2502lambda$new$0$comaivoxcommon_uiFileScrollSelectView(DateSelectListener dateSelectListener, FileScrollSelectViewLayoutBinding fileScrollSelectViewLayoutBinding, View view2) {
        dateSelectListener.onDateSelected(fileScrollSelectViewLayoutBinding.wvFile.getCurrentPosition());
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-FileScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2503lambda$new$1$comaivoxcommon_uiFileScrollSelectView(View view2) {
        this.mDialog.dismiss();
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
