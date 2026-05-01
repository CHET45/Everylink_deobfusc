package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.OnViewClickListener;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentMainAiBinding extends ViewDataBinding {
    public final CardView cvUpload;
    public final EditText etQuestion;
    public final Group groupInput;
    public final ImageView ivDelete;
    public final ImageView ivImg;
    public final ImageView ivModel;
    public final ImageView ivSend;
    public final ImageView ivUpload;
    public final LinearLayout llContent;
    public final LinearLayout llEmpty;

    @Bindable
    protected OnViewClickListener mClickListener;
    public final RecyclerView rvAi;
    public final SwitchCompat scBreak;
    public final TextView tvTitle;
    public final View vLine;

    public abstract void setClickListener(OnViewClickListener onViewClickListener);

    protected FragmentMainAiBinding(Object obj, View view2, int i, CardView cardView, EditText editText, Group group, ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, LinearLayout linearLayout2, RecyclerView recyclerView, SwitchCompat switchCompat, TextView textView, View view3) {
        super(obj, view2, i);
        this.cvUpload = cardView;
        this.etQuestion = editText;
        this.groupInput = group;
        this.ivDelete = imageView;
        this.ivImg = imageView2;
        this.ivModel = imageView3;
        this.ivSend = imageView4;
        this.ivUpload = imageView5;
        this.llContent = linearLayout;
        this.llEmpty = linearLayout2;
        this.rvAi = recyclerView;
        this.scBreak = switchCompat;
        this.tvTitle = textView;
        this.vLine = view3;
    }

    public OnViewClickListener getClickListener() {
        return this.mClickListener;
    }

    public static FragmentMainAiBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainAiBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (FragmentMainAiBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_ai, viewGroup, z, obj);
    }

    public static FragmentMainAiBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainAiBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (FragmentMainAiBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.fragment_main_ai, null, false, obj);
    }

    public static FragmentMainAiBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentMainAiBinding bind(View view2, Object obj) {
        return (FragmentMainAiBinding) bind(obj, view2, C0726R.layout.fragment_main_ai);
    }
}
