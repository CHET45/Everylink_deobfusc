package com.aivox.common;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common.databinding.FragmentCancelSubNoticeBindingImpl;
import com.aivox.common.databinding.FragmentTimePurchaseBindingImpl;
import com.aivox.common.databinding.IncludeCountryCodePickerBindingImpl;
import com.aivox.common.databinding.ItemRecordModeSelectBindingImpl;
import com.aivox.common.databinding.LangSelectItemBindingImpl;
import com.aivox.common.databinding.LanguageSelectItemBindingImpl;
import com.aivox.common.databinding.LanguageSelectViewLayoutBindingImpl;
import com.aivox.common.databinding.RecordModeSelectDialogLayoutBindingImpl;
import com.aivox.common.databinding.ViewUploadProgressYellowBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_FRAGMENTCANCELSUBNOTICE = 1;
    private static final int LAYOUT_FRAGMENTTIMEPURCHASE = 2;
    private static final int LAYOUT_INCLUDECOUNTRYCODEPICKER = 3;
    private static final int LAYOUT_ITEMRECORDMODESELECT = 4;
    private static final int LAYOUT_LANGSELECTITEM = 5;
    private static final int LAYOUT_LANGUAGESELECTITEM = 6;
    private static final int LAYOUT_LANGUAGESELECTVIEWLAYOUT = 7;
    private static final int LAYOUT_RECORDMODESELECTDIALOGLAYOUT = 8;
    private static final int LAYOUT_VIEWUPLOADPROGRESSYELLOW = 9;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(9);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C0958R.layout.fragment_cancel_sub_notice, 1);
        sparseIntArray.put(C0958R.layout.fragment_time_purchase, 2);
        sparseIntArray.put(C0958R.layout.include_country_code_picker, 3);
        sparseIntArray.put(C0958R.layout.item_record_mode_select, 4);
        sparseIntArray.put(C0958R.layout.lang_select_item, 5);
        sparseIntArray.put(C0958R.layout.language_select_item, 6);
        sparseIntArray.put(C0958R.layout.language_select_view_layout, 7);
        sparseIntArray.put(C0958R.layout.record_mode_select_dialog_layout, 8);
        sparseIntArray.put(C0958R.layout.view_upload_progress_yellow, 9);
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
        switch (i2) {
            case 1:
                if ("layout/fragment_cancel_sub_notice_0".equals(tag)) {
                    return new FragmentCancelSubNoticeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_cancel_sub_notice is invalid. Received: " + tag);
            case 2:
                if ("layout/fragment_time_purchase_0".equals(tag)) {
                    return new FragmentTimePurchaseBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_time_purchase is invalid. Received: " + tag);
            case 3:
                if ("layout/include_country_code_picker_0".equals(tag)) {
                    return new IncludeCountryCodePickerBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for include_country_code_picker is invalid. Received: " + tag);
            case 4:
                if ("layout/item_record_mode_select_0".equals(tag)) {
                    return new ItemRecordModeSelectBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_record_mode_select is invalid. Received: " + tag);
            case 5:
                if ("layout/lang_select_item_0".equals(tag)) {
                    return new LangSelectItemBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for lang_select_item is invalid. Received: " + tag);
            case 6:
                if ("layout/language_select_item_0".equals(tag)) {
                    return new LanguageSelectItemBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for language_select_item is invalid. Received: " + tag);
            case 7:
                if ("layout/language_select_view_layout_0".equals(tag)) {
                    return new LanguageSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for language_select_view_layout is invalid. Received: " + tag);
            case 8:
                if ("layout/record_mode_select_dialog_layout_0".equals(tag)) {
                    return new RecordModeSelectDialogLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for record_mode_select_dialog_layout is invalid. Received: " + tag);
            case 9:
                if ("layout/view_upload_progress_yellow_0".equals(tag)) {
                    return new ViewUploadProgressYellowBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for view_upload_progress_yellow is invalid. Received: " + tag);
            default:
                return null;
        }
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
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.aivox.base.DataBinderMapperImpl());
        arrayList.add(new com.aivox.common_ui.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(2);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "model");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(9);
            sKeys = map;
            map.put("layout/fragment_cancel_sub_notice_0", Integer.valueOf(C0958R.layout.fragment_cancel_sub_notice));
            map.put("layout/fragment_time_purchase_0", Integer.valueOf(C0958R.layout.fragment_time_purchase));
            map.put("layout/include_country_code_picker_0", Integer.valueOf(C0958R.layout.include_country_code_picker));
            map.put("layout/item_record_mode_select_0", Integer.valueOf(C0958R.layout.item_record_mode_select));
            map.put("layout/lang_select_item_0", Integer.valueOf(C0958R.layout.lang_select_item));
            map.put("layout/language_select_item_0", Integer.valueOf(C0958R.layout.language_select_item));
            map.put("layout/language_select_view_layout_0", Integer.valueOf(C0958R.layout.language_select_view_layout));
            map.put("layout/record_mode_select_dialog_layout_0", Integer.valueOf(C0958R.layout.record_mode_select_dialog_layout));
            map.put("layout/view_upload_progress_yellow_0", Integer.valueOf(C0958R.layout.view_upload_progress_yellow));
        }
    }
}
