package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.aivox.common_ui.databinding.SettingTileGroupViewBinding;

/* JADX INFO: loaded from: classes.dex */
public class SettingTileGroupView extends FrameLayout {
    public SettingTileGroupView(Context context) {
        this(context, null);
    }

    public SettingTileGroupView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SettingTileGroupView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SettingTileGroupViewBinding settingTileGroupViewBindingInflate = SettingTileGroupViewBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.SettingTileGroupView);
        settingTileGroupViewBindingInflate.tvTitle.setText(typedArrayObtainStyledAttributes.getString(C1034R.styleable.SettingTileGroupView_tile_group_title));
        typedArrayObtainStyledAttributes.recycle();
    }
}
