package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityMainBinding extends ViewDataBinding {
    public final ConstraintLayout container;
    public final RadioButton footBarAudio;
    public final RadioButton footBarChat;
    public final RadioButton footBarFile;
    public final RadioButton footBarHome;
    public final RadioButton footBarMe;
    public final FrameLayout fragmentContainer;
    public final Group gpGuide;
    public final ImageView ivCloseX;
    public final ImageView ivHasMsgNotice;
    public final ImageView ivHasNews;
    public final ImageView ivHasSysNotice;
    public final ConstraintLayout layoutFooter;
    public final LinearLayout mainContentLayout;
    public final RadioGroup rgBottom;
    public final RelativeLayout rlCloseLayout;
    public final TextView tvLocalTag;
    public final View viewNext;
    public final View viewPrev;
    public final View viewSkip;
    public final ViewPager vpGuide;

    protected ActivityMainBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, RadioButton radioButton, RadioButton radioButton2, RadioButton radioButton3, RadioButton radioButton4, RadioButton radioButton5, FrameLayout frameLayout, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ConstraintLayout constraintLayout2, LinearLayout linearLayout, RadioGroup radioGroup, RelativeLayout relativeLayout, TextView textView, View view3, View view4, View view5, ViewPager viewPager) {
        super(obj, view2, i);
        this.container = constraintLayout;
        this.footBarAudio = radioButton;
        this.footBarChat = radioButton2;
        this.footBarFile = radioButton3;
        this.footBarHome = radioButton4;
        this.footBarMe = radioButton5;
        this.fragmentContainer = frameLayout;
        this.gpGuide = group;
        this.ivCloseX = imageView;
        this.ivHasMsgNotice = imageView2;
        this.ivHasNews = imageView3;
        this.ivHasSysNotice = imageView4;
        this.layoutFooter = constraintLayout2;
        this.mainContentLayout = linearLayout;
        this.rgBottom = radioGroup;
        this.rlCloseLayout = relativeLayout;
        this.tvLocalTag = textView;
        this.viewNext = view3;
        this.viewPrev = view4;
        this.viewSkip = view5;
        this.vpGuide = viewPager;
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_main, viewGroup, z, obj);
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_main, null, false, obj);
    }

    public static ActivityMainBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityMainBinding bind(View view2, Object obj) {
        return (ActivityMainBinding) bind(obj, view2, C0726R.layout.activity_main);
    }
}
