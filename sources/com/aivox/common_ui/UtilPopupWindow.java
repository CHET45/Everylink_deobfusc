package com.aivox.common_ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
public class UtilPopupWindow {
    private PopupWindowCallback callback;

    public interface PopupWindowCallback {
        void setContentViewlayout(View view2);
    }

    public void setPopupWindowCallback(PopupWindowCallback popupWindowCallback) {
        this.callback = popupWindowCallback;
    }

    public void setPopupWindow(Context context, int i, View view2, View view3, final int i2) {
        PopupWindowCallback popupWindowCallback = this.callback;
        if (popupWindowCallback != null) {
            popupWindowCallback.setContentViewlayout(view2);
        }
        PopupWindow popupWindow = new PopupWindow(view2, -1, i);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ViewCompat.MEASURED_SIZE_MASK));
        final Activity activity = (Activity) context;
        final WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = 0.85f;
        activity.getWindow().setAttributes(attributes);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.aivox.common_ui.UtilPopupWindow.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                if (i2 == 2) {
                    attributes.alpha = 0.85f;
                    activity.getWindow().setAttributes(attributes);
                } else {
                    attributes.alpha = 1.0f;
                    activity.getWindow().setAttributes(attributes);
                }
            }
        });
        popupWindow.showAtLocation(view3, 80, 0, 0);
    }
}
