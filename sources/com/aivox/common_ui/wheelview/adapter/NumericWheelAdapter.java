package com.aivox.common_ui.wheelview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class NumericWheelAdapter extends AbstractWheelTextAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private String format;
    private String label;
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(Context context) {
        this(context, 0, 9);
    }

    public NumericWheelAdapter(Context context, int i, int i2) {
        this(context, i, i2, null);
    }

    public NumericWheelAdapter(Context context, int i, int i2, String str) {
        super(context);
        this.minValue = i;
        this.maxValue = i2;
        this.format = str;
    }

    @Override // com.aivox.common_ui.wheelview.adapter.AbstractWheelTextAdapter
    public CharSequence getItemText(int i) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        int i2 = this.minValue + i;
        String str = this.format;
        return str != null ? String.format(str, Integer.valueOf(i2)) : Integer.toString(i2);
    }

    @Override // com.aivox.common_ui.wheelview.adapter.WheelViewAdapter
    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    @Override // com.aivox.common_ui.wheelview.adapter.AbstractWheelTextAdapter, com.aivox.common_ui.wheelview.adapter.WheelViewAdapter
    public View getItem(int i, View view2, ViewGroup viewGroup) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        if (view2 == null) {
            view2 = getView(this.itemResourceId, viewGroup);
        }
        TextView textView = getTextView(view2, this.itemTextResourceId);
        if (textView != null) {
            CharSequence itemText = getItemText(i);
            if (itemText == null) {
                itemText = "";
            }
            textView.setText(((Object) itemText) + this.label);
            if (this.itemResourceId == -1) {
                configureTextView(textView);
            }
        }
        return view2;
    }

    public void setLabel(String str) {
        this.label = str;
    }
}
