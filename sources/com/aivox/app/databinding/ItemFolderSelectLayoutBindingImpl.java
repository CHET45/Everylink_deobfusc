package com.aivox.app.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import com.aivox.common.model.FolderBean;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ItemFolderSelectLayoutBindingImpl extends ItemFolderSelectLayoutBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final LinearLayout mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemFolderSelectLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 4, sIncludes, sViewsWithIds));
    }

    private ItemFolderSelectLayoutBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (ImageView) objArr[2], (TextView) objArr[3]);
        this.mDirtyFlags = -1L;
        this.clSelectFolder.setTag(null);
        this.ivSelectFolder.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[1];
        this.mboundView1 = linearLayout;
        linearLayout.setTag(null);
        this.tvSelectFolderName.setTag(null);
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
        if (3 != i) {
            return false;
        }
        setModel((FolderBean) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemFolderSelectLayoutBinding
    public void setModel(FolderBean folderBean) {
        this.mModel = folderBean;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(3);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean zIsSelected;
        Drawable drawable;
        String name;
        Drawable drawable2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FolderBean folderBean = this.mModel;
        long j2 = j & 3;
        Drawable drawable3 = null;
        if (j2 != 0) {
            if (folderBean != null) {
                name = folderBean.getName();
                zIsSelected = folderBean.isSelected();
            } else {
                zIsSelected = false;
                name = null;
            }
            if (j2 != 0) {
                j |= zIsSelected ? 40L : 20L;
            }
            drawable = zIsSelected ? AppCompatResources.getDrawable(this.mboundView1.getContext(), C1034R.drawable.bg_record_item_selected) : null;
        } else {
            zIsSelected = false;
            drawable = null;
            name = null;
        }
        long j3 = 16 & j;
        if (j3 != 0) {
            boolean z = ViewDataBinding.safeUnbox(folderBean != null ? folderBean.getType() : null) == -1;
            if (j3 != 0) {
                j |= z ? 128L : 64L;
            }
            drawable2 = AppCompatResources.getDrawable(this.ivSelectFolder.getContext(), z ? C1034R.drawable.ic_move_out : C1034R.drawable.ic_folder_normal);
        } else {
            drawable2 = null;
        }
        long j4 = j & 3;
        if (j4 != 0) {
            drawable3 = zIsSelected ? AppCompatResources.getDrawable(this.ivSelectFolder.getContext(), C1034R.drawable.ic_folder_highlight) : drawable2;
        }
        if (j4 != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivSelectFolder, drawable3);
            ViewBindingAdapter.setBackground(this.mboundView1, drawable);
            TextViewBindingAdapter.setText(this.tvSelectFolderName, name);
        }
    }
}
