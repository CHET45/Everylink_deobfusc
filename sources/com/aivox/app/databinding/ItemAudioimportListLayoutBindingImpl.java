package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.app.C0726R;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.AudioInfoBean;

/* JADX INFO: loaded from: classes.dex */
public class ItemAudioimportListLayoutBindingImpl extends ItemAudioimportListLayoutBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.cl_top, 4);
    }

    public ItemAudioimportListLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 5, sIncludes, sViewsWithIds));
    }

    private ItemAudioimportListLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (ConstraintLayout) objArr[4], (TextView) objArr[3], (TextView) objArr[2], (TextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.f125cl.setTag(null);
        this.itemInfoDuration.setTag(null);
        this.itemInfoTv.setTag(null);
        this.itemName.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
    public boolean setVariable(int i, Object obj) {
        if (6 != i) {
            return false;
        }
        setXmlmodel((AudioInfoBean) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemAudioimportListLayoutBinding
    public void setXmlmodel(AudioInfoBean audioInfoBean) {
        this.mXmlmodel = audioInfoBean;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String strUtc2Local;
        String title;
        String startTime;
        int fileTime;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        AudioInfoBean audioInfoBean = this.mXmlmodel;
        long j2 = j & 3;
        String strNumberToTime = null;
        if (j2 != 0) {
            String str = DateUtil.MM_DD_HH_MM;
            if (audioInfoBean != null) {
                fileTime = audioInfoBean.getFileTime();
                title = audioInfoBean.getTitle();
                startTime = audioInfoBean.getStartTime();
            } else {
                startTime = null;
                fileTime = 0;
                title = null;
            }
            strNumberToTime = DateUtil.numberToTime(fileTime);
            strUtc2Local = DateUtil.utc2Local(startTime, str);
        } else {
            strUtc2Local = null;
            title = null;
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.itemInfoDuration, strNumberToTime);
            TextViewBindingAdapter.setText(this.itemInfoTv, strUtc2Local);
            TextViewBindingAdapter.setText(this.itemName, title);
        }
    }
}
