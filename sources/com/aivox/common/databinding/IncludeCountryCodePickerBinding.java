package com.aivox.common.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.common.C0958R;

/* JADX INFO: loaded from: classes.dex */
public abstract class IncludeCountryCodePickerBinding extends ViewDataBinding {
    public final EditText etSearch;
    public final ImageView ivClose;
    public final View line;
    public final RecyclerView rvCountry;
    public final TextView tvTitle;

    protected IncludeCountryCodePickerBinding(Object obj, View view2, int i, EditText editText, ImageView imageView, View view3, RecyclerView recyclerView, TextView textView) {
        super(obj, view2, i);
        this.etSearch = editText;
        this.ivClose = imageView;
        this.line = view3;
        this.rvCountry = recyclerView;
        this.tvTitle = textView;
    }

    public static IncludeCountryCodePickerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        return inflate(layoutInflater, viewGroup, z, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeCountryCodePickerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z, Object obj) {
        return (IncludeCountryCodePickerBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.include_country_code_picker, viewGroup, z, obj);
    }

    public static IncludeCountryCodePickerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeCountryCodePickerBinding inflate(LayoutInflater layoutInflater, Object obj) {
        return (IncludeCountryCodePickerBinding) ViewDataBinding.inflateInternal(layoutInflater, C0958R.layout.include_country_code_picker, null, false, obj);
    }

    public static IncludeCountryCodePickerBinding bind(View view2) {
        return bind(view2, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static IncludeCountryCodePickerBinding bind(View view2, Object obj) {
        return (IncludeCountryCodePickerBinding) bind(obj, view2, C0958R.layout.include_country_code_picker);
    }
}
