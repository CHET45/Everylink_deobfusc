package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.common.model.Transcribe;

/* JADX INFO: loaded from: classes.dex */
public class ItemTranscribeFloatingBindingImpl extends ItemTranscribeFloatingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final Group mboundView3;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    public ItemTranscribeFloatingBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 4, sIncludes, sViewsWithIds));
    }

    private ItemTranscribeFloatingBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[0], (TextView) objArr[1], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        this.f132cl.setTag(null);
        Group group = (Group) objArr[3];
        this.mboundView3 = group;
        group.setTag(null);
        this.tvContent.setTag(null);
        this.tvTranslateResult.setTag(null);
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
        setXmlmodel((Transcribe) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemTranscribeFloatingBinding
    public void setXmlmodel(Transcribe transcribe) {
        this.mXmlmodel = transcribe;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        String str2;
        int colorFromResource;
        int i;
        String translate;
        String var;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Transcribe transcribe = this.mXmlmodel;
        long j2 = j & 3;
        boolean z = false;
        if (j2 != 0) {
            if (transcribe != null) {
                translate = transcribe.getTranslate();
                var = transcribe.getVar();
            } else {
                translate = null;
                var = null;
            }
            boolean zIsEmpty = BaseStringUtil.isEmpty(translate);
            boolean zIsEmpty2 = BaseStringUtil.isEmpty(var);
            if (j2 != 0) {
                j |= zIsEmpty ? 128L : 64L;
            }
            if ((j & 3) != 0) {
                j |= zIsEmpty2 ? 40L : 20L;
            }
            int i2 = zIsEmpty ? 8 : 0;
            colorFromResource = getColorFromResource(this.tvContent, zIsEmpty2 ? C0874R.color.txt_black : C0874R.color.home_txt);
            str2 = var;
            str = translate;
            i = i2;
            z = zIsEmpty2;
        } else {
            str = null;
            str2 = null;
            colorFromResource = 0;
            i = 0;
        }
        String onebest = ((32 & j) == 0 || transcribe == null) ? null : transcribe.getOnebest();
        long j3 = j & 3;
        String str3 = j3 != 0 ? z ? onebest : str2 : null;
        if (j3 != 0) {
            this.mboundView3.setVisibility(i);
            TextViewBindingAdapter.setText(this.tvContent, str3);
            this.tvContent.setTextColor(colorFromResource);
            TextViewBindingAdapter.setText(this.tvTranslateResult, str);
        }
    }
}
