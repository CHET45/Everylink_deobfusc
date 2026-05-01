package com.lxj.xpopupext.listener;

import android.view.View;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public interface TimePickerListener {
    void onTimeChanged(Date date);

    void onTimeConfirm(Date date, View view2);
}
