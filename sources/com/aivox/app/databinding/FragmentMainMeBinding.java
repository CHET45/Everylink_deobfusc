package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.RoundedCornerBitmap;
import com.aivox.common_ui.SettingTileView;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentMainMeBinding extends ViewDataBinding {
    public final ConstraintLayout clDeviceFeature;
    public final ConstraintLayout clHead;
    public final ConstraintLayout clTimeLeft;
    public final ImageView ivArrow;
    public final ImageView ivDeviceInfinity;
    public final RoundedCornerBitmap ivHead;
    public final LinearLayout llLeftTimeTitle;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final ProgressBar pbTime;
    public final RecyclerView rvDeviceRights;
    public final SettingTileView stvAboutUs;
    public final SettingTileView stvActivityH5;
    public final SettingTileView stvBenefitsCode;
    public final SettingTileView stvCheckUpdate;
    public final SettingTileView stvContactUs;
    public final SettingTileView stvCooperation;
    public final SettingTileView stvGuide;
    public final SettingTileView stvMembership;
    public final SettingTileView stvMyCard;
    public final SettingTileView stvNewConnect;
    public final SettingTileView stvRecommend;
    public final SettingTileView stvStorageUsage;
    public final SettingTileView stvTranscribe;
    public final SettingTileView stvTranslate;
    public final SettingTileView stvTxtPrivacy;
    public final SettingTileView stvTxtSize;
    public final SettingTileView stvVipFeature;
    public final TextView tvDeviceExpDate;
    public final TextView tvDeviceLeftExpMsg;
    public final TextView tvDeviceLeftTime;
    public final TextView tvDeviceLeftTimeMsg;
    public final TextView tvDeviceName;
    public final TextView tvDoPurchase;
    public final TextView tvHead;
    public final TextView tvName;
    public final TextView tvTimeLeft;
    public final TextView tvTimeLeftMsg;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentMainMeBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ImageView imageView, ImageView imageView2, RoundedCornerBitmap roundedCornerBitmap, LinearLayout linearLayout, ProgressBar progressBar, RecyclerView recyclerView, SettingTileView settingTileView, SettingTileView settingTileView2, SettingTileView settingTileView3, SettingTileView settingTileView4, SettingTileView settingTileView5, SettingTileView settingTileView6, SettingTileView settingTileView7, SettingTileView settingTileView8, SettingTileView settingTileView9, SettingTileView settingTileView10, SettingTileView settingTileView11, SettingTileView settingTileView12, SettingTileView settingTileView13, SettingTileView settingTileView14, SettingTileView settingTileView15, SettingTileView settingTileView16, SettingTileView settingTileView17, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10) {
        super(obj, view2, i);
        this.clDeviceFeature = constraintLayout;
        this.clHead = constraintLayout2;
        this.clTimeLeft = constraintLayout3;
        this.ivArrow = imageView;
        this.ivDeviceInfinity = imageView2;
        this.ivHead = roundedCornerBitmap;
        this.llLeftTimeTitle = linearLayout;
        this.pbTime = progressBar;
        this.rvDeviceRights = recyclerView;
        this.stvAboutUs = settingTileView;
        this.stvActivityH5 = settingTileView2;
        this.stvBenefitsCode = settingTileView3;
        this.stvCheckUpdate = settingTileView4;
        this.stvContactUs = settingTileView5;
        this.stvCooperation = settingTileView6;
        this.stvGuide = settingTileView7;
        this.stvMembership = settingTileView8;
        this.stvMyCard = settingTileView9;
        this.stvNewConnect = settingTileView10;
        this.stvRecommend = settingTileView11;
        this.stvStorageUsage = settingTileView12;
        this.stvTranscribe = settingTileView13;
        this.stvTranslate = settingTileView14;
        this.stvTxtPrivacy = settingTileView15;
        this.stvTxtSize = settingTileView16;
        this.stvVipFeature = settingTileView17;
        this.tvDeviceExpDate = textView;
        this.tvDeviceLeftExpMsg = textView2;
        this.tvDeviceLeftTime = textView3;
        this.tvDeviceLeftTimeMsg = textView4;
        this.tvDeviceName = textView5;
        this.tvDoPurchase = textView6;
        this.tvHead = textView7;
        this.tvName = textView8;
        this.tvTimeLeft = textView9;
        this.tvTimeLeftMsg = textView10;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentMainMeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainMeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentMainMeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_me, viewGroup, z, obj);
    }

    public static FragmentMainMeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainMeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentMainMeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_me, null, false, obj);
    }

    public static FragmentMainMeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainMeBinding bind(View view2, Object obj) {
        return (FragmentMainMeBinding) bind(obj, view2, C0726R.layout.fragment_main_me);
    }
}
