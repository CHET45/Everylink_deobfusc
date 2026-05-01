package com.aivox.common_ui.wheelview.adapter;

import android.content.Context;
import com.aivox.common_ui.wheelview.WheelAdapter;

/* JADX INFO: loaded from: classes.dex */
public class AdapterWheel extends AbstractWheelTextAdapter {
    private WheelAdapter adapter;

    public AdapterWheel(Context context, WheelAdapter wheelAdapter) {
        super(context);
        this.adapter = wheelAdapter;
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    @Override // com.aivox.common_ui.wheelview.adapter.WheelViewAdapter
    public int getItemsCount() {
        return this.adapter.getItemsCount();
    }

    @Override // com.aivox.common_ui.wheelview.adapter.AbstractWheelTextAdapter
    protected CharSequence getItemText(int i) {
        return this.adapter.getItem(i);
    }
}
