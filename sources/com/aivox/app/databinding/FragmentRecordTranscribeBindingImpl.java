package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.google.android.material.appbar.AppBarLayout;

/* JADX INFO: loaded from: classes.dex */
public class FragmentRecordTranscribeBindingImpl extends FragmentRecordTranscribeBinding {
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
        sparseIntArray.put(C0726R.id.appbar, 1);
        sparseIntArray.put(C0726R.id.cl_audio_title, 2);
        sparseIntArray.put(C0726R.id.tv_record_title, 3);
        sparseIntArray.put(C0726R.id.tv_record_date, 4);
        sparseIntArray.put(C0726R.id.tv_record_time, 5);
        sparseIntArray.put(C0726R.id.tv_record_location, 6);
        sparseIntArray.put(C0726R.id.view_cover_title, 7);
        sparseIntArray.put(C0726R.id.ll_switch, 8);
        sparseIntArray.put(C0726R.id.tv_switch_content, 9);
        sparseIntArray.put(C0726R.id.tv_switch_summary, 10);
        sparseIntArray.put(C0726R.id.view_cover_switch, 11);
        sparseIntArray.put(C0726R.id.rv_trans_list, 12);
        sparseIntArray.put(C0726R.id.rl_chat_layout, 13);
        sparseIntArray.put(C0726R.id.tv_chat_msg, 14);
        sparseIntArray.put(C0726R.id.rv_chat_list, 15);
        sparseIntArray.put(C0726R.id.btn_to_transcribe, 16);
        sparseIntArray.put(C0726R.id.iv_to_bottom, 17);
        sparseIntArray.put(C0726R.id.rv_search_list, 18);
    }

    public FragmentRecordTranscribeBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 19, sIncludes, sViewsWithIds));
    }

    private FragmentRecordTranscribeBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (AppBarLayout) objArr[1], (TextView) objArr[16], (ConstraintLayout) objArr[2], (ConstraintLayout) objArr[0], (ImageView) objArr[17], (LinearLayout) objArr[8], (RelativeLayout) objArr[13], (RecyclerView) objArr[15], (RecyclerView) objArr[18], (RecyclerView) objArr[12], (TextView) objArr[14], (TextView) objArr[4], (TextView) objArr[6], (TextView) objArr[5], (TextView) objArr[3], (TextView) objArr[9], (TextView) objArr[10], (View) objArr[11], (View) objArr[7]);
        this.mDirtyFlags = -1L;
        this.clBg.setTag(null);
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
