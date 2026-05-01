package com.aivox.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityAiChatEditBinding extends ViewDataBinding {
    public final EditText etContent;
    public final TextView tvEditCancel;
    public final TextView tvEditSave;

    protected ActivityAiChatEditBinding(Object obj, View view2, int i, EditText editText, TextView textView, TextView textView2) {
        super(obj, view2, i);
        this.etContent = editText;
        this.tvEditCancel = textView;
        this.tvEditSave = textView2;
    }

    public static ActivityAiChatEditBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAiChatEditBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (ActivityAiChatEditBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_ai_chat_edit, viewGroup, z, obj);
    }

    public static ActivityAiChatEditBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAiChatEditBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (ActivityAiChatEditBinding) ViewDataBinding.inflateInternal(layoutInflater, C0726R.layout.activity_ai_chat_edit, null, false, obj);
    }

    public static ActivityAiChatEditBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityAiChatEditBinding bind(View view2, Object obj) {
        return (ActivityAiChatEditBinding) bind(obj, view2, C0726R.layout.activity_ai_chat_edit);
    }
}
