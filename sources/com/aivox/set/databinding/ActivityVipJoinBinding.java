package com.aivox.set.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.VipPackView;
import com.aivox.set.C1106R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityVipJoinBinding extends ViewDataBinding {
    public final ConstraintLayout clLogo;
    public final ConstraintLayout clParent;
    public final CardView cvTrial;
    public final ImageView ivClose;
    public final ImageView ivLogo;
    public final ImageView ivTitle;
    public final LinearLayout llTopBtn;
    public final LinearLayout llVipSwitch;
    public final RelativeLayout rlContent;
    public final TextView tvAgreement;
    public final View viewCover;
    public final VipPackView vpvMonth;
    public final VipPackView vpvYear;

    protected ActivityVipJoinBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, CardView cardView, ImageView imageView, ImageView imageView2, ImageView imageView3, LinearLayout linearLayout, LinearLayout linearLayout2, RelativeLayout relativeLayout, TextView textView, View view3, VipPackView vipPackView, VipPackView vipPackView2) {
        super(obj, view2, i);
        this.clLogo = constraintLayout;
        this.clParent = constraintLayout2;
        this.cvTrial = cardView;
        this.ivClose = imageView;
        this.ivLogo = imageView2;
        this.ivTitle = imageView3;
        this.llTopBtn = linearLayout;
        this.llVipSwitch = linearLayout2;
        this.rlContent = relativeLayout;
        this.tvAgreement = textView;
        this.viewCover = view3;
        this.vpvMonth = vipPackView;
        this.vpvYear = vipPackView2;
    }

    public static ActivityVipJoinBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVipJoinBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityVipJoinBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_vip_join, viewGroup, z, obj);
    }

    public static ActivityVipJoinBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVipJoinBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityVipJoinBinding) ViewDataBinding.inflateInternal(layoutInflater, C1106R.layout.activity_vip_join, null, false, obj);
    }

    public static ActivityVipJoinBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityVipJoinBinding bind(View view2, Object obj) {
        return (ActivityVipJoinBinding) bind(obj, view2, C1106R.layout.activity_vip_join);
    }
}
