package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public class ActivityMainBindingImpl extends ActivityMainBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.main_content_layout, 1);
        sparseIntArray.put(C0726R.id.fragment_container, 2);
        sparseIntArray.put(C0726R.id.tv_local_tag, 3);
        sparseIntArray.put(C0726R.id.layout_footer, 4);
        sparseIntArray.put(C0726R.id.rg_bottom, 5);
        sparseIntArray.put(C0726R.id.foot_bar_home, 6);
        sparseIntArray.put(C0726R.id.foot_bar_audio, 7);
        sparseIntArray.put(C0726R.id.foot_bar_chat, 8);
        sparseIntArray.put(C0726R.id.foot_bar_file, 9);
        sparseIntArray.put(C0726R.id.foot_bar_me, 10);
        sparseIntArray.put(C0726R.id.iv_has_news, 11);
        sparseIntArray.put(C0726R.id.iv_has_sys_notice, 12);
        sparseIntArray.put(C0726R.id.iv_has_msg_notice, 13);
        sparseIntArray.put(C0726R.id.rl_close_layout, 14);
        sparseIntArray.put(C0726R.id.iv_close_x, 15);
        sparseIntArray.put(C0726R.id.vp_guide, 16);
        sparseIntArray.put(C0726R.id.view_skip, 17);
        sparseIntArray.put(C0726R.id.view_prev, 18);
        sparseIntArray.put(C0726R.id.view_next, 19);
        sparseIntArray.put(C0726R.id.gp_guide, 20);
    }

    public ActivityMainBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 21, sIncludes, sViewsWithIds));
    }

    private ActivityMainBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (RadioButton) objArr[7], (RadioButton) objArr[8], (RadioButton) objArr[9], (RadioButton) objArr[6], (RadioButton) objArr[10], (FrameLayout) objArr[2], (Group) objArr[20], (ImageView) objArr[15], (ImageView) objArr[13], (ImageView) objArr[11], (ImageView) objArr[12], (ConstraintLayout) objArr[4], (LinearLayout) objArr[1], (RadioGroup) objArr[5], (RelativeLayout) objArr[14], (TextView) objArr[3], (View) objArr[19], (View) objArr[18], (View) objArr[17], (ViewPager) objArr[16]);
        this.mDirtyFlags = -1L;
        this.container.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
