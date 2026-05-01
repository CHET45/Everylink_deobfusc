package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.aivox.common_ui.databinding.PlanItemLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class PlanItemView extends LinearLayout {
    private PlanItemLayoutBinding mBinding;

    public PlanItemView(Context context) {
        this(context, null);
    }

    public PlanItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlanItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        this.mBinding = PlanItemLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.PlanItemView);
        String string = typedArrayObtainStyledAttributes.getString(C1034R.styleable.PlanItemView_piv_title_name);
        String string2 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.PlanItemView_piv_title_value);
        String string3 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.PlanItemView_piv_title_unit);
        String string4 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.PlanItemView_piv_sub_title);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.PlanItemView_piv_show_sub_title, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.PlanItemView_piv_title_only, false);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.PlanItemView_piv_icon_start, C1034R.drawable.ic_vip_feature);
        this.mBinding.tvItemSubTitle.setVisibility(z ? 0 : 8);
        this.mBinding.tvItemTitleValue.setVisibility(z2 ? 8 : 0);
        this.mBinding.tvItemTitleUnit.setVisibility(z2 ? 8 : 0);
        this.mBinding.tvItemTitleHolder.setVisibility(z2 ? 8 : 0);
        this.mBinding.tvItemTitleName.setText(string);
        this.mBinding.tvItemTitleValue.setText(string2);
        this.mBinding.tvItemTitleUnit.setText(string3);
        this.mBinding.tvItemSubTitle.setText(string4);
        this.mBinding.ivItemIcon.setImageResource(resourceId);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setPiv_title_name(String str) {
        this.mBinding.tvItemTitleName.setText(str);
    }

    public void setPiv_sub_title(String str) {
        this.mBinding.tvItemSubTitle.setText(str);
    }

    public void setPiv_title_value(String str) {
        this.mBinding.tvItemTitleValue.setText(str);
    }

    public void setPiv_title_unit(String str) {
        this.mBinding.tvItemTitleUnit.setText(str);
    }

    public void setPiv_show_sub_title(boolean z) {
        this.mBinding.tvItemSubTitle.setVisibility(z ? 0 : 8);
    }

    public void setPiv_title_only(boolean z) {
        this.mBinding.tvItemTitleValue.setVisibility(z ? 8 : 0);
        this.mBinding.tvItemTitleUnit.setVisibility(z ? 8 : 0);
        this.mBinding.tvItemTitleHolder.setVisibility(z ? 8 : 0);
    }

    public void setPiv_icon_start(Drawable drawable) {
        this.mBinding.ivItemIcon.setImageDrawable(drawable);
    }
}
