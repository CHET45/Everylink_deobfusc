package com.aivox.common_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common_ui.databinding.HomeEnterViewLayoutBinding;

/* JADX INFO: loaded from: classes.dex */
public class HomeEnterView extends LinearLayout {
    private HomeEnterViewLayoutBinding mBinding;
    private ClickListener mListener;
    private int mType;

    public interface ClickListener {
        void onClick(int i);
    }

    public HomeEnterView(Context context) {
        this(context, null);
    }

    public HomeEnterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HomeEnterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        this.mBinding = HomeEnterViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.HomeEnterView);
        String string = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HomeEnterView_hev_name);
        String string2 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HomeEnterView_hev_state_msg);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.HomeEnterView_hev_icon, C1034R.drawable.ic_home_shorthand);
        this.mType = typedArrayObtainStyledAttributes.getInt(C1034R.styleable.HomeEnterView_hev_type, 1);
        this.mBinding.tvEnterName.setText(string);
        this.mBinding.tvEnterState.setText(string2);
        this.mBinding.ivEnterIcon.setImageResource(resourceId);
        this.mBinding.tvEnterState.setVisibility(BaseStringUtil.isNotEmpty(string2) ? 0 : 4);
        this.mBinding.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.HomeEnterView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2507lambda$initView$0$comaivoxcommon_uiHomeEnterView(view2);
            }
        });
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-common_ui-HomeEnterView, reason: not valid java name */
    /* synthetic */ void m2507lambda$initView$0$comaivoxcommon_uiHomeEnterView(View view2) {
        ClickListener clickListener = this.mListener;
        if (clickListener != null) {
            clickListener.onClick(this.mType);
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.mListener = clickListener;
    }
}
