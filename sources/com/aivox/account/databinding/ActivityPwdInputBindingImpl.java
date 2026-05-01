package com.aivox.account.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.C0707R;
import com.aivox.common_ui.HeadTitleLinearView;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.LoginEditText;

/* JADX INFO: loaded from: classes.dex */
public class ActivityPwdInputBindingImpl extends ActivityPwdInputBinding {
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
        sparseIntArray.put(C0707R.id.title_view, 1);
        sparseIntArray.put(C0707R.id.et_pwd, 2);
        sparseIntArray.put(C0707R.id.et_pwd_confirm, 3);
        sparseIntArray.put(C0707R.id.tv_notice, 4);
        sparseIntArray.put(C0707R.id.btn_login, 5);
        sparseIntArray.put(C0707R.id.tv_code_login, 6);
    }

    public ActivityPwdInputBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 7, sIncludes, sViewsWithIds));
    }

    private ActivityPwdInputBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (LoadingButton) objArr[5], (LoginEditText) objArr[2], (LoginEditText) objArr[3], (ConstraintLayout) objArr[0], (HeadTitleLinearView) objArr[1], (TextView) objArr[6], (TextView) objArr[4]);
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
