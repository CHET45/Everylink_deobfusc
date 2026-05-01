package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.AutoSwipeRefreshView;
import com.aivox.common_ui.HomeEnterView;
import com.google.android.material.appbar.AppBarLayout;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentHomeBinding extends ViewDataBinding {
    public final AppBarLayout appbar;
    public final ConstraintLayout clAsr;
    public final CoordinatorLayout clDefault;
    public final ConstraintLayout clManual;
    public final ConstraintLayout clRtsp;
    public final ConstraintLayout clTranslate;
    public final GridLayout glEnter;
    public final GridLayout glGlass;
    public final HomeEnterView hevBilingual;
    public final HomeEnterView hevDevice;
    public final HomeEnterView hevDeviceConnect;
    public final HomeEnterView hevShorthand;
    public final HomeEnterView hevTranslate;
    public final ImageView ivAsr;
    public final ImageView ivBattery;
    public final ImageView ivDevice;
    public final ImageView ivDeviceIcon;
    public final ImageView ivHomeEnterBilingual;
    public final ImageView ivHomeEnterDevice;
    public final ImageView ivHomeEnterShorthand;
    public final ImageView ivHomeEnterTranslate;
    public final ImageView ivManual;
    public final ImageView ivRtsp;
    public final ImageView ivSearch;
    public final ImageView ivTranslate;
    public final LinearLayout llAudioList;
    public final ConstraintLayout llDevice;
    public final LinearLayout llImageRecognition;
    public final LinearLayout llPhotoCapture;
    public final LinearLayout llTitle;
    public final LinearLayout llTitleEnter;
    public final LinearLayout llUpdate;
    public final LinearLayout llVideoRecording;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final AutoSwipeRefreshView refreshView;
    public final RecyclerView rvAudioList;
    public final ScrollView svGlass;
    public final TextView tvBasicFunctions;
    public final TextView tvBattery;
    public final TextView tvCommonFunctions;
    public final TextView tvDeviceName;
    public final TextView tvHomeTitle;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentHomeBinding(Object obj, View view2, int i, AppBarLayout appBarLayout, ConstraintLayout constraintLayout, CoordinatorLayout coordinatorLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, GridLayout gridLayout, GridLayout gridLayout2, HomeEnterView homeEnterView, HomeEnterView homeEnterView2, HomeEnterView homeEnterView3, HomeEnterView homeEnterView4, HomeEnterView homeEnterView5, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, ImageView imageView9, ImageView imageView10, ImageView imageView11, ImageView imageView12, LinearLayout linearLayout, ConstraintLayout constraintLayout5, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, LinearLayout linearLayout7, AutoSwipeRefreshView autoSwipeRefreshView, RecyclerView recyclerView, ScrollView scrollView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5) {
        super(obj, view2, i);
        this.appbar = appBarLayout;
        this.clAsr = constraintLayout;
        this.clDefault = coordinatorLayout;
        this.clManual = constraintLayout2;
        this.clRtsp = constraintLayout3;
        this.clTranslate = constraintLayout4;
        this.glEnter = gridLayout;
        this.glGlass = gridLayout2;
        this.hevBilingual = homeEnterView;
        this.hevDevice = homeEnterView2;
        this.hevDeviceConnect = homeEnterView3;
        this.hevShorthand = homeEnterView4;
        this.hevTranslate = homeEnterView5;
        this.ivAsr = imageView;
        this.ivBattery = imageView2;
        this.ivDevice = imageView3;
        this.ivDeviceIcon = imageView4;
        this.ivHomeEnterBilingual = imageView5;
        this.ivHomeEnterDevice = imageView6;
        this.ivHomeEnterShorthand = imageView7;
        this.ivHomeEnterTranslate = imageView8;
        this.ivManual = imageView9;
        this.ivRtsp = imageView10;
        this.ivSearch = imageView11;
        this.ivTranslate = imageView12;
        this.llAudioList = linearLayout;
        this.llDevice = constraintLayout5;
        this.llImageRecognition = linearLayout2;
        this.llPhotoCapture = linearLayout3;
        this.llTitle = linearLayout4;
        this.llTitleEnter = linearLayout5;
        this.llUpdate = linearLayout6;
        this.llVideoRecording = linearLayout7;
        this.refreshView = autoSwipeRefreshView;
        this.rvAudioList = recyclerView;
        this.svGlass = scrollView;
        this.tvBasicFunctions = textView;
        this.tvBattery = textView2;
        this.tvCommonFunctions = textView3;
        this.tvDeviceName = textView4;
        this.tvHomeTitle = textView5;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentHomeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentHomeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentHomeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_home, viewGroup, z, obj);
    }

    public static FragmentHomeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentHomeBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentHomeBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_home, null, false, obj);
    }

    public static FragmentHomeBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentHomeBinding bind(View view2, Object obj) {
        return (FragmentHomeBinding) bind(obj, view2, C0726R.layout.fragment_home);
    }
}
