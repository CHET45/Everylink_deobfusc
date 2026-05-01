package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;

/* JADX INFO: loaded from: classes.dex */
public class FragmentMainAiBindingImpl extends FragmentMainAiBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback65;
    private final View.OnClickListener mCallback66;
    private final View.OnClickListener mCallback67;
    private final View.OnClickListener mCallback68;
    private final View.OnClickListener mCallback69;
    private final View.OnClickListener mCallback70;
    private final View.OnClickListener mCallback71;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.sc_break, 8);
        sparseIntArray.put(C0726R.id.et_question, 9);
        sparseIntArray.put(C0726R.id.v_line, 10);
        sparseIntArray.put(C0726R.id.ll_content, 11);
        sparseIntArray.put(C0726R.id.ll_empty, 12);
        sparseIntArray.put(C0726R.id.rv_ai, 13);
        sparseIntArray.put(C0726R.id.group_input, 14);
    }

    public FragmentMainAiBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 15, sIncludes, sViewsWithIds));
    }

    private FragmentMainAiBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (CardView) objArr[6], (EditText) objArr[9], (Group) objArr[14], (ImageView) objArr[3], (ImageView) objArr[5], (ImageView) objArr[2], (ImageView) objArr[4], (ImageView) objArr[7], (LinearLayout) objArr[11], (LinearLayout) objArr[12], (RecyclerView) objArr[13], (SwitchCompat) objArr[8], (TextView) objArr[1], (View) objArr[10]);
        this.mDirtyFlags = -1L;
        this.cvUpload.setTag(null);
        this.ivDelete.setTag(null);
        this.ivImg.setTag(null);
        this.ivModel.setTag(null);
        this.ivSend.setTag(null);
        this.ivUpload.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(view2);
        this.mCallback69 = new OnClickListener(this, 5);
        this.mCallback67 = new OnClickListener(this, 3);
        this.mCallback68 = new OnClickListener(this, 4);
        this.mCallback65 = new OnClickListener(this, 1);
        this.mCallback66 = new OnClickListener(this, 2);
        this.mCallback70 = new OnClickListener(this, 6);
        this.mCallback71 = new OnClickListener(this, 7);
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
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.FragmentMainAiBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(1);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        OnViewClickListener onViewClickListener = this.mClickListener;
        if ((j & 2) != 0) {
            this.cvUpload.setOnClickListener(this.mCallback70);
            this.ivDelete.setOnClickListener(this.mCallback67);
            this.ivImg.setOnClickListener(this.mCallback69);
            this.ivModel.setOnClickListener(this.mCallback66);
            this.ivSend.setOnClickListener(this.mCallback68);
            this.ivUpload.setOnClickListener(this.mCallback71);
            this.tvTitle.setOnClickListener(this.mCallback65);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        switch (i) {
            case 1:
                OnViewClickListener onViewClickListener = this.mClickListener;
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(view2);
                }
                break;
            case 2:
                OnViewClickListener onViewClickListener2 = this.mClickListener;
                if (onViewClickListener2 != null) {
                    onViewClickListener2.onViewClick(view2);
                }
                break;
            case 3:
                OnViewClickListener onViewClickListener3 = this.mClickListener;
                if (onViewClickListener3 != null) {
                    onViewClickListener3.onViewClick(view2);
                }
                break;
            case 4:
                OnViewClickListener onViewClickListener4 = this.mClickListener;
                if (onViewClickListener4 != null) {
                    onViewClickListener4.onViewClick(view2);
                }
                break;
            case 5:
                OnViewClickListener onViewClickListener5 = this.mClickListener;
                if (onViewClickListener5 != null) {
                    onViewClickListener5.onViewClick(view2);
                }
                break;
            case 6:
                OnViewClickListener onViewClickListener6 = this.mClickListener;
                if (onViewClickListener6 != null) {
                    onViewClickListener6.onViewClick(view2);
                }
                break;
            case 7:
                OnViewClickListener onViewClickListener7 = this.mClickListener;
                if (onViewClickListener7 != null) {
                    onViewClickListener7.onViewClick(view2);
                }
                break;
        }
    }
}
