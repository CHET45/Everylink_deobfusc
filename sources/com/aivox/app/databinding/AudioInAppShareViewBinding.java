package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.DialogTitleItemView;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.PowerfulEditText;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public abstract class AudioInAppShareViewBinding extends ViewDataBinding {
    public final LoadingButton btnContinue;
    public final DialogTitleItemView dtivItemTitle;
    public final DialogTitleItemView dtivTimeTitle;
    public final DialogTitleView dtvTitle;
    public final PowerfulEditText etSearch;
    public final HorizontalScrollView hsvValidTime;
    public final RoundedCornerBitmap ivUserAvatar;
    public final LinearLayout llSearch;
    public final LinearLayout llUserInfo;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final TextView tvDay1;
    public final TextView tvDay30;
    public final TextView tvDay7;
    public final TextView tvDay90;
    public final TextView tvUserAvatar;
    public final TextView tvUserName;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected AudioInAppShareViewBinding(Object obj, View view2, int i, LoadingButton loadingButton, DialogTitleItemView dialogTitleItemView, DialogTitleItemView dialogTitleItemView2, DialogTitleView dialogTitleView, PowerfulEditText powerfulEditText, HorizontalScrollView horizontalScrollView, RoundedCornerBitmap roundedCornerBitmap, LinearLayout linearLayout, LinearLayout linearLayout2, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        super(obj, view2, i);
        this.btnContinue = loadingButton;
        this.dtivItemTitle = dialogTitleItemView;
        this.dtivTimeTitle = dialogTitleItemView2;
        this.dtvTitle = dialogTitleView;
        this.etSearch = powerfulEditText;
        this.hsvValidTime = horizontalScrollView;
        this.ivUserAvatar = roundedCornerBitmap;
        this.llSearch = linearLayout;
        this.llUserInfo = linearLayout2;
        this.tvDay1 = textView;
        this.tvDay30 = textView2;
        this.tvDay7 = textView3;
        this.tvDay90 = textView4;
        this.tvUserAvatar = textView5;
        this.tvUserName = textView6;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static AudioInAppShareViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioInAppShareViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (AudioInAppShareViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_in_app_share_view, viewGroup, z, obj);
    }

    public static AudioInAppShareViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioInAppShareViewBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (AudioInAppShareViewBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.audio_in_app_share_view, null, false, obj);
    }

    public static AudioInAppShareViewBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static AudioInAppShareViewBinding bind(View view2, Object obj) {
        return (AudioInAppShareViewBinding) bind(obj, view2, C0726R.layout.audio_in_app_share_view);
    }
}
