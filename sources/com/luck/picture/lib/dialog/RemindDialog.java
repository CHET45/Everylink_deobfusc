package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.luck.picture.lib.C2114R;

/* JADX INFO: loaded from: classes3.dex */
public class RemindDialog extends Dialog implements View.OnClickListener {
    private final TextView btnOk;
    private OnDialogClickListener listener;
    private final TextView tvContent;

    public interface OnDialogClickListener {
        void onClick(View view2);
    }

    public RemindDialog(Context context, String str) {
        super(context, C2114R.style.Picture_Theme_Dialog);
        setContentView(C2114R.layout.ps_remind_dialog);
        TextView textView = (TextView) findViewById(C2114R.id.btnOk);
        this.btnOk = textView;
        TextView textView2 = (TextView) findViewById(C2114R.id.tv_content);
        this.tvContent = textView2;
        textView2.setText(str);
        textView.setOnClickListener(this);
        setDialogSize();
    }

    @Deprecated
    public static Dialog showTipsDialog(Context context, String str) {
        return new RemindDialog(context, str);
    }

    public static RemindDialog buildDialog(Context context, String str) {
        return new RemindDialog(context, str);
    }

    public void setButtonText(String str) {
        this.btnOk.setText(str);
    }

    public void setButtonTextColor(int i) {
        this.btnOk.setTextColor(i);
    }

    public void setContent(String str) {
        this.tvContent.setText(str);
    }

    public void setContentTextColor(int i) {
        this.tvContent.setTextColor(i);
    }

    private void setDialogSize() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -2;
        attributes.height = -2;
        attributes.gravity = 17;
        getWindow().setWindowAnimations(C2114R.style.PictureThemeDialogWindowStyle);
        getWindow().setAttributes(attributes);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        if (view2.getId() == C2114R.id.btnOk) {
            OnDialogClickListener onDialogClickListener = this.listener;
            if (onDialogClickListener != null) {
                onDialogClickListener.onClick(view2);
            } else {
                dismiss();
            }
        }
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.listener = onDialogClickListener;
    }
}
