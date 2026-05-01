package com.aivox.common.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common.C0958R;
import com.aivox.common_ui.DialogTitleView;
import com.aivox.common_ui.LangItemView;
import com.aivox.common_ui.LoadingButton;

/* JADX INFO: loaded from: classes.dex */
public class LanguageSelectViewLayoutBindingImpl extends LanguageSelectViewLayoutBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

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
        sparseIntArray.put(C0958R.id.dtv_title, 1);
        sparseIntArray.put(C0958R.id.cl_lang_switch, 2);
        sparseIntArray.put(C0958R.id.tv_lang_l, 3);
        sparseIntArray.put(C0958R.id.iv_arrow, 4);
        sparseIntArray.put(C0958R.id.tv_lang_r, 5);
        sparseIntArray.put(C0958R.id.tv_language_recent, 6);
        sparseIntArray.put(C0958R.id.ll_lang_recent_from, 7);
        sparseIntArray.put(C0958R.id.liv_from_recent_1, 8);
        sparseIntArray.put(C0958R.id.liv_from_recent_2, 9);
        sparseIntArray.put(C0958R.id.ll_lang_recent_to, 10);
        sparseIntArray.put(C0958R.id.liv_to_recent_1, 11);
        sparseIntArray.put(C0958R.id.liv_to_recent_2, 12);
        sparseIntArray.put(C0958R.id.tv_language_all, 13);
        sparseIntArray.put(C0958R.id.rv_lang_from, 14);
        sparseIntArray.put(C0958R.id.rv_lang_to, 15);
        sparseIntArray.put(C0958R.id.btn_continue, 16);
    }

    public LanguageSelectViewLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 17, sIncludes, sViewsWithIds));
    }

    private LanguageSelectViewLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[16], (ConstraintLayout) objArr[2], (DialogTitleView) objArr[1], (ImageView) objArr[4], (LangItemView) objArr[8], (LangItemView) objArr[9], (LangItemView) objArr[11], (LangItemView) objArr[12], (LinearLayout) objArr[7], (LinearLayout) objArr[10], (RecyclerView) objArr[14], (RecyclerView) objArr[15], (TextView) objArr[3], (TextView) objArr[5], (TextView) objArr[13], (TextView) objArr[6]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
