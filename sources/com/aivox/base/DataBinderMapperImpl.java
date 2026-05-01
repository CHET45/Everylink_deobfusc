package com.aivox.base;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.base.databinding.FooterBindingImpl;
import com.aivox.base.databinding.IncludeNullBindingImpl;
import com.aivox.base.databinding.IncludeNullViewBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_FOOTER = 1;
    private static final int LAYOUT_INCLUDENULL = 2;
    private static final int LAYOUT_INCLUDENULLVIEW = 3;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C0874R.layout.footer, 1);
        sparseIntArray.put(C0874R.layout.include_null, 2);
        sparseIntArray.put(C0874R.layout.include_null_view, 3);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view2, int i) {
        int i2 = INTERNAL_LAYOUT_ID_LOOKUP.get(i);
        if (i2 <= 0) {
            return null;
        }
        Object tag = view2.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        }
        if (i2 == 1) {
            if ("layout/footer_0".equals(tag)) {
                return new FooterBindingImpl(dataBindingComponent, view2);
            }
            throw new IllegalArgumentException("The tag for footer is invalid. Received: " + tag);
        }
        if (i2 == 2) {
            if ("layout/include_null_0".equals(tag)) {
                return new IncludeNullBindingImpl(dataBindingComponent, view2);
            }
            throw new IllegalArgumentException("The tag for include_null is invalid. Received: " + tag);
        }
        if (i2 != 3) {
            return null;
        }
        if ("layout/include_null_view_0".equals(tag)) {
            return new IncludeNullViewBindingImpl(dataBindingComponent, view2);
        }
        throw new IllegalArgumentException("The tag for include_null_view is invalid. Received: " + tag);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Integer num;
        if (str == null || (num = InnerLayoutIdLookup.sKeys.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return InnerBrLookup.sKeys.get(i);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(1);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(3);
            sKeys = map;
            map.put("layout/footer_0", Integer.valueOf(C0874R.layout.footer));
            map.put("layout/include_null_0", Integer.valueOf(C0874R.layout.include_null));
            map.put("layout/include_null_view_0", Integer.valueOf(C0874R.layout.include_null_view));
        }
    }
}
