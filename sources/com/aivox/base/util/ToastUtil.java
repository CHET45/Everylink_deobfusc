package com.aivox.base.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.aivox.base.C0874R;

/* JADX INFO: loaded from: classes.dex */
public class ToastUtil {
    public static boolean isShow = true;
    public static Toast sToast;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void showShort(Object obj) {
        if (isShow) {
            Toast toast = sToast;
            if (toast != null) {
                toast.cancel();
                sToast = null;
            }
            if (obj instanceof String) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), (String) obj, 0);
            } else if (obj instanceof Integer) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), ((Integer) obj).intValue(), 0);
            }
            Toast toast2 = sToast;
            if (toast2 != null) {
                toast2.show();
            }
        }
    }

    public static void showLong(Object obj) {
        if (isShow) {
            Toast toast = sToast;
            if (toast != null) {
                toast.cancel();
                sToast = null;
            }
            if (obj instanceof String) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), (String) obj, 1);
            } else if (obj instanceof Integer) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), ((Integer) obj).intValue(), 1);
            }
            Toast toast2 = sToast;
            if (toast2 != null) {
                toast2.show();
            }
        }
    }

    public static void show(Object obj, int i) {
        if (isShow) {
            Toast toast = sToast;
            if (toast != null) {
                toast.cancel();
                sToast = null;
            }
            if (obj instanceof String) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), (String) obj, i);
            } else if (obj instanceof Integer) {
                sToast = Toast.makeText(BaseAppUtils.getContext(), ((Integer) obj).intValue(), i);
            }
            Toast toast2 = sToast;
            if (toast2 != null) {
                toast2.show();
            }
        }
    }

    public static void showImageToast(Context context, String str) {
        View viewInflate = LayoutInflater.from(context).inflate(C0874R.layout.toast_image_layout, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(C0874R.id.f139tv)).setText(str);
        Toast toast = new Toast(context);
        toast.setDuration(0);
        toast.setView(viewInflate);
        toast.show();
    }

    public static void showTextToast(Context context, Object obj) {
        showShort(obj);
    }

    public static void showTextToast2(Context context, Object obj) {
        View viewInflate = LayoutInflater.from(context).inflate(C0874R.layout.toast_text_layout, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(C0874R.id.tv_message)).setText(BaseStringUtil.parseParam(obj, context));
        if (sToast == null) {
            Toast toast = new Toast(context);
            sToast = toast;
            toast.setDuration(0);
        }
        sToast.setView(viewInflate);
        sToast.show();
    }

    public static void notOpen(Context context) {
        View viewInflate = LayoutInflater.from(context).inflate(C0874R.layout.toast_text_layout, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(C0874R.id.tv_message)).setText("该功能暂未开放，敬请期待");
        Toast toast = new Toast(context);
        toast.setDuration(0);
        toast.setView(viewInflate);
        toast.show();
    }
}
