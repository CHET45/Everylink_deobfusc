package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityNoteMarkListBinding extends ViewDataBinding {
    public final RecyclerView rvMarks;
    public final HeadTitleLinearView titleView;

    protected ActivityNoteMarkListBinding(Object obj, View view2, int i, RecyclerView recyclerView, HeadTitleLinearView headTitleLinearView) {
        super(obj, view2, i);
        this.rvMarks = recyclerView;
        this.titleView = headTitleLinearView;
    }

    public static ActivityNoteMarkListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoteMarkListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityNoteMarkListBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_note_mark_list, viewGroup, z, obj);
    }

    public static ActivityNoteMarkListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoteMarkListBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityNoteMarkListBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_note_mark_list, null, false, obj);
    }

    public static ActivityNoteMarkListBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityNoteMarkListBinding bind(View view2, Object obj) {
        return (ActivityNoteMarkListBinding) bind(obj, view2, C0726R.layout.activity_note_mark_list);
    }
}
