package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.PowerfulEditText;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityRecordInfoBinding extends ViewDataBinding {
    public final ConstraintLayout clAiSummary;
    public final ConstraintLayout clBottom;
    public final ConstraintLayout clMain;
    public final ConstraintLayout clMainIn;
    public final ConstraintLayout clSearch;
    public final ConstraintLayout clToolbar;
    public final EditText etAiTalk;
    public final PowerfulEditText etSearch;
    public final FrameLayout fragmentContainer;
    public final Group gpGuide;
    public final ImageView imgBack;
    public final ImageView ivAiTalkSend;
    public final ImageView ivBottomMore;
    public final ImageView ivLoading;
    public final ImageView ivNoteMark;
    public final ImageView ivOperateAi;
    public final ImageView ivSearch;
    public final ImageView ivSpeed;
    public final LinearLayout llAiTalk;
    public final LinearLayout llBtmNotify;
    public final LinearLayout llNoti;
    public final LinearLayout llSearch;
    public final LinearLayout llTime;
    public final LinearLayout llyBack;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final ImageView playStartIv;
    public final ImageView playStopIv;
    public final ConstraintLayout rlEdit;
    public final RelativeLayout rlNoteMark;
    public final ConstraintLayout rlTop;
    public final RecyclerView rvScenes;
    public final SeekBar sbProgress;
    public final TextView tvCancel;
    public final TextView tvEditTitle;
    public final TextView tvMsg;
    public final TextView tvNoteMark;
    public final TextView tvNoti;
    public final TextView tvRecordTimeCur;
    public final TextView tvRecordTimeTotal;
    public final TextView tvSave;
    public final TextView tvSearchCancel;
    public final View viewNext;
    public final View viewPrev;
    public final View viewSkip;
    public final ViewPager vpGuide;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected ActivityRecordInfoBinding(Object obj, View view2, int i, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, ConstraintLayout constraintLayout5, ConstraintLayout constraintLayout6, EditText editText, PowerfulEditText powerfulEditText, FrameLayout frameLayout, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, ImageView imageView9, ImageView imageView10, ConstraintLayout constraintLayout7, RelativeLayout relativeLayout, ConstraintLayout constraintLayout8, RecyclerView recyclerView, SeekBar seekBar, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, View view3, View view4, View view5, ViewPager viewPager) {
        super(obj, view2, i);
        this.clAiSummary = constraintLayout;
        this.clBottom = constraintLayout2;
        this.clMain = constraintLayout3;
        this.clMainIn = constraintLayout4;
        this.clSearch = constraintLayout5;
        this.clToolbar = constraintLayout6;
        this.etAiTalk = editText;
        this.etSearch = powerfulEditText;
        this.fragmentContainer = frameLayout;
        this.gpGuide = group;
        this.imgBack = imageView;
        this.ivAiTalkSend = imageView2;
        this.ivBottomMore = imageView3;
        this.ivLoading = imageView4;
        this.ivNoteMark = imageView5;
        this.ivOperateAi = imageView6;
        this.ivSearch = imageView7;
        this.ivSpeed = imageView8;
        this.llAiTalk = linearLayout;
        this.llBtmNotify = linearLayout2;
        this.llNoti = linearLayout3;
        this.llSearch = linearLayout4;
        this.llTime = linearLayout5;
        this.llyBack = linearLayout6;
        this.playStartIv = imageView9;
        this.playStopIv = imageView10;
        this.rlEdit = constraintLayout7;
        this.rlNoteMark = relativeLayout;
        this.rlTop = constraintLayout8;
        this.rvScenes = recyclerView;
        this.sbProgress = seekBar;
        this.tvCancel = textView;
        this.tvEditTitle = textView2;
        this.tvMsg = textView3;
        this.tvNoteMark = textView4;
        this.tvNoti = textView5;
        this.tvRecordTimeCur = textView6;
        this.tvRecordTimeTotal = textView7;
        this.tvSave = textView8;
        this.tvSearchCancel = textView9;
        this.viewNext = view3;
        this.viewPrev = view4;
        this.viewSkip = view5;
        this.vpGuide = viewPager;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static ActivityRecordInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityRecordInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_info, viewGroup, z, obj);
    }

    public static ActivityRecordInfoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordInfoBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityRecordInfoBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_record_info, null, false, obj);
    }

    public static ActivityRecordInfoBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityRecordInfoBinding bind(View view2, Object obj) {
        return (ActivityRecordInfoBinding) bind(obj, view2, C0726R.layout.activity_record_info);
    }
}
