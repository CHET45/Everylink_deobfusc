package com.aivox.set.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.set.C1106R;
import com.zhy.view.flowlayout.TagFlowLayout;

/* JADX INFO: loaded from: classes.dex */
public class ActivityContactUsBindingImpl extends ActivityContactUsBinding {
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
        sparseIntArray.put(C1106R.id.title_view, 1);
        sparseIntArray.put(C1106R.id.tv_progress, 2);
        sparseIntArray.put(C1106R.id.progress, 3);
        sparseIntArray.put(C1106R.id.iv_submit_success, 4);
        sparseIntArray.put(C1106R.id.tv_step_msg, 5);
        sparseIntArray.put(C1106R.id.fl_container, 6);
        sparseIntArray.put(C1106R.id.ll_step_1, 7);
        sparseIntArray.put(C1106R.id.tv_account, 8);
        sparseIntArray.put(C1106R.id.tv_email, 9);
        sparseIntArray.put(C1106R.id.ll_email, 10);
        sparseIntArray.put(C1106R.id.iv_format_notice, 11);
        sparseIntArray.put(C1106R.id.et_email, 12);
        sparseIntArray.put(C1106R.id.ll_quick_set, 13);
        sparseIntArray.put(C1106R.id.cb_email, 14);
        sparseIntArray.put(C1106R.id.ll_step_2, 15);
        sparseIntArray.put(C1106R.id.tv_tags, 16);
        sparseIntArray.put(C1106R.id.tag_flow, 17);
        sparseIntArray.put(C1106R.id.sv_step_3, 18);
        sparseIntArray.put(C1106R.id.tv_msg, 19);
        sparseIntArray.put(C1106R.id.ll_msg, 20);
        sparseIntArray.put(C1106R.id.iv_msg_empty, 21);
        sparseIntArray.put(C1106R.id.et_msg, 22);
        sparseIntArray.put(C1106R.id.tv_text_count, 23);
        sparseIntArray.put(C1106R.id.tv_files, 24);
        sparseIntArray.put(C1106R.id.rv_files, 25);
        sparseIntArray.put(C1106R.id.ll_submit, 26);
        sparseIntArray.put(C1106R.id.cb_submit, 27);
        sparseIntArray.put(C1106R.id.tv_submit_success, 28);
        sparseIntArray.put(C1106R.id.tv_warning, 29);
        sparseIntArray.put(C1106R.id.btn_next, 30);
    }

    public ActivityContactUsBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 31, sIncludes, sViewsWithIds));
    }

    private ActivityContactUsBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[30], (CheckBox) objArr[14], (CheckBox) objArr[27], (EditText) objArr[12], (EditText) objArr[22], (FrameLayout) objArr[6], (ImageView) objArr[11], (ImageView) objArr[21], (ImageView) objArr[4], (LinearLayout) objArr[10], (LinearLayout) objArr[20], (LinearLayout) objArr[13], (LinearLayout) objArr[7], (LinearLayout) objArr[15], (LinearLayout) objArr[26], (LinearLayout) objArr[0], (ProgressBar) objArr[3], (RecyclerView) objArr[25], (ScrollView) objArr[18], (TagFlowLayout) objArr[17], (HeadTitleLinearView) objArr[1], (TextView) objArr[8], (TextView) objArr[9], (TextView) objArr[24], (TextView) objArr[19], (TextView) objArr[2], (TextView) objArr[5], (TextView) objArr[28], (TextView) objArr[16], (TextView) objArr[23], (TextView) objArr[29]);
        this.mDirtyFlags = -1L;
        this.main.setTag(null);
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
