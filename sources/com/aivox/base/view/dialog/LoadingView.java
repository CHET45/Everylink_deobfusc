package com.aivox.base.view.dialog;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aivox.base.C0874R;
import com.wang.avi.AVLoadingIndicatorView;

/* JADX INFO: loaded from: classes.dex */
public class LoadingView {
    public static Dialog showLoading(Context context, String str) {
        View viewInflate = LayoutInflater.from(context).inflate(C0874R.layout.layout_loading_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(C0874R.id.id_tv_loading_dialog_text);
        AVLoadingIndicatorView aVLoadingIndicatorView = (AVLoadingIndicatorView) viewInflate.findViewById(C0874R.id.AVLoadingIndicatorView);
        textView.setText(str);
        final Dialog dialog = new Dialog(context, C0874R.style.loading_dialog_style);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(viewInflate, new LinearLayout.LayoutParams(-1, -1));
        dialog.show();
        aVLoadingIndicatorView.smoothToShow();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.aivox.base.view.dialog.LoadingView.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                dialog.hide();
                return true;
            }
        });
        return dialog;
    }

    public static void dismissLoading(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.dismiss();
    }
}
