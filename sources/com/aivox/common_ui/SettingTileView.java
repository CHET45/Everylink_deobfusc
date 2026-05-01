package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common_ui.databinding.SettingTileLayoutBinding;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class SettingTileView extends LinearLayout {
    private SettingTileLayoutBinding mBinding;

    public SettingTileView(Context context) {
        this(context, null);
    }

    public SettingTileView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SettingTileView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public void init(Context context, AttributeSet attributeSet) {
        this.mBinding = SettingTileLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.SettingTileView);
        String string = typedArrayObtainStyledAttributes.getString(C1034R.styleable.SettingTileView_tile_title);
        String string2 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.SettingTileView_tile_sub_title);
        String string3 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.SettingTileView_tile_msg);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.SettingTileView_tile_title_leading, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.SettingTileView_tile_title_icon, 0);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.SettingTileView_tile_right_icon, C1034R.drawable.ic_arrow_setting_tiny);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.SettingTileView_tile_content_padding_h, SizeUtils.dp2px(20.0f));
        int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.SettingTileView_tile_content_padding_v, SizeUtils.dp2px(0.0f));
        int dimension3 = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.SettingTileView_tile_divider_padding_left, SizeUtils.dp2px(20.0f));
        int dimension4 = (int) typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.SettingTileView_tile_divider_padding_right, SizeUtils.dp2px(20.0f));
        int color = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.SettingTileView_tile_title_color, getResources().getColor(C0874R.color.txt_color_primary));
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.SettingTileView_tile_show_title_dot, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.SettingTileView_tile_show_msg_dot, false);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.SettingTileView_tile_show_divider, false);
        boolean z4 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.SettingTileView_tile_show_right_icon, true);
        boolean z5 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.SettingTileView_tile_show_right_switch, false);
        this.mBinding.tvTitle.setTextColor(color);
        if (BaseStringUtil.isNotEmpty(string2)) {
            this.mBinding.tvTitle.setTextSize(15.0f);
        }
        setString(this.mBinding.tvTitle, string);
        setString(this.mBinding.tvSubTitle, string2);
        setString(this.mBinding.tvMsg, string3);
        setRes(this.mBinding.ivTitleLeading, resourceId);
        setRes(this.mBinding.ivRightIcon, resourceId3);
        setRes(this.mBinding.ivTitleIcon, resourceId2);
        setVisibility(this.mBinding.ivTitleLeading, resourceId != 0);
        setVisibility(this.mBinding.ivRightIcon, resourceId3 != 0);
        setVisibility(this.mBinding.ivTitleIcon, resourceId2 != 0);
        setVisibility(this.mBinding.ivTitleDot, z);
        setVisibility(this.mBinding.ivMsgDot, z2);
        setVisibility(this.mBinding.divider, z3);
        setVisibility(this.mBinding.ivRightIcon, z4);
        setVisibility(this.mBinding.tvSubTitle, !TextUtils.isEmpty(string2));
        setVisibility(this.mBinding.tvMsg, !TextUtils.isEmpty(string3));
        setVisibility(this.mBinding.switchRight, z5);
        this.mBinding.clContent.setPadding(dimension, dimension2, dimension, dimension2);
        this.mBinding.divider.setPadding(dimension3, 0, dimension4, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void setString(TextView textView, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        textView.setText(str);
    }

    private void setRes(ImageView imageView, int i) {
        if (i != 0) {
            imageView.setImageResource(i);
        }
    }

    private void setVisibility(View view2, boolean z) {
        view2.setVisibility(z ? 0 : 8);
    }

    public void changeTitleDotVisibility(boolean z) {
        setVisibility(this.mBinding.ivTitleDot, z);
    }

    public void changeMsgDotVisibility(boolean z) {
        setVisibility(this.mBinding.ivMsgDot, z);
    }

    public void setMsg(String str) {
        setString(this.mBinding.tvMsg, str);
        setVisibility(this.mBinding.tvMsg, !TextUtils.isEmpty(str));
    }

    public void setSubTitle(String str) {
        setString(this.mBinding.tvSubTitle, str);
        setVisibility(this.mBinding.tvSubTitle, !TextUtils.isEmpty(str));
        if (BaseStringUtil.isNotEmpty(str)) {
            this.mBinding.tvTitle.setTextSize(15.0f);
        }
    }

    public String getSubTitle() {
        return this.mBinding.tvSubTitle.getText().toString();
    }

    public void enableSwitch() {
        this.mBinding.switchRight.setClickable(true);
    }

    public void setChecked(boolean z) {
        this.mBinding.switchRight.setChecked(z);
    }

    public boolean isChecked() {
        return this.mBinding.switchRight.isChecked();
    }
}
