package com.aivox.common.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.base.C0874R;
import com.aivox.base.common.MyEnum;
import com.aivox.common.C0957BR;
import com.aivox.common.model.LanguageSelectBean;

/* JADX INFO: loaded from: classes.dex */
public class LanguageSelectItemBindingImpl extends LanguageSelectItemBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public LanguageSelectItemBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 3, sIncludes, sViewsWithIds));
    }

    private LanguageSelectItemBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[2], (TextView) objArr[1]);
        this.mDirtyFlags = -1L;
        this.ivLangSelected.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvLang.setTag(null);
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
        if (C0957BR.model != i) {
            return false;
        }
        setModel((LanguageSelectBean) obj);
        return true;
    }

    @Override // com.aivox.common.databinding.LanguageSelectItemBinding
    public void setModel(LanguageSelectBean languageSelectBean) {
        this.mModel = languageSelectBean;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(C0957BR.model);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int colorFromResource;
        MyEnum.TRANSLATE_LANGUAGE language;
        boolean zIsSelected;
        boolean zIsEnable;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        LanguageSelectBean languageSelectBean = this.mModel;
        long j2 = j & 3;
        String str = null;
        if (j2 != 0) {
            if (languageSelectBean != null) {
                zIsEnable = languageSelectBean.isEnable();
                language = languageSelectBean.getLanguage();
                zIsSelected = languageSelectBean.isSelected();
            } else {
                language = null;
                zIsSelected = false;
                zIsEnable = false;
            }
            if (j2 != 0) {
                j |= zIsEnable ? 8L : 4L;
            }
            if ((j & 3) != 0) {
                j |= zIsSelected ? 32L : 16L;
            }
            colorFromResource = getColorFromResource(this.tvLang, zIsEnable ? C0874R.color.black_11 : C0874R.color.color_cc);
            i = zIsSelected ? 0 : 8;
            if (language != null) {
                str = language.desc;
            }
        } else {
            colorFromResource = 0;
        }
        if ((j & 3) != 0) {
            this.ivLangSelected.setVisibility(i);
            this.tvLang.setTextColor(colorFromResource);
            TextViewBindingAdapter.setText(this.tvLang, str);
        }
    }
}
