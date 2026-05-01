package com.bigkoo.pickerview.utils;

import com.bigkoo.pickerview.C1198R;

/* JADX INFO: loaded from: classes3.dex */
public class PickerViewAnimateUtil {
    private static final int INVALID = -1;

    public static int getAnimationResource(int i, boolean z) {
        if (i != 80) {
            return -1;
        }
        return z ? C1198R.anim.pickerview_slide_in_bottom : C1198R.anim.pickerview_slide_out_bottom;
    }
}
