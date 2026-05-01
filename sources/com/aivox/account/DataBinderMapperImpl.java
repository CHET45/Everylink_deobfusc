package com.aivox.account;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.account.databinding.ActivityCodeInputBindingImpl;
import com.aivox.account.databinding.ActivityEmailInputBindingImpl;
import com.aivox.account.databinding.ActivityLoginOneKeyBindingImpl;
import com.aivox.account.databinding.ActivityNameInputBindingImpl;
import com.aivox.account.databinding.ActivityPhoneInputBindingImpl;
import com.aivox.account.databinding.ActivityPwdInputBindingImpl;
import com.aivox.account.databinding.ActivitySmsCodeBindingImpl;
import com.aivox.account.databinding.ActivityUserInfoBindingImpl;
import com.aivox.account.databinding.IncludeSetGenderBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYCODEINPUT = 1;
    private static final int LAYOUT_ACTIVITYEMAILINPUT = 2;
    private static final int LAYOUT_ACTIVITYLOGINONEKEY = 3;
    private static final int LAYOUT_ACTIVITYNAMEINPUT = 4;
    private static final int LAYOUT_ACTIVITYPHONEINPUT = 5;
    private static final int LAYOUT_ACTIVITYPWDINPUT = 6;
    private static final int LAYOUT_ACTIVITYSMSCODE = 7;
    private static final int LAYOUT_ACTIVITYUSERINFO = 8;
    private static final int LAYOUT_INCLUDESETGENDER = 9;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(9);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C0707R.layout.activity_code_input, 1);
        sparseIntArray.put(C0707R.layout.activity_email_input, 2);
        sparseIntArray.put(C0707R.layout.activity_login_one_key, 3);
        sparseIntArray.put(C0707R.layout.activity_name_input, 4);
        sparseIntArray.put(C0707R.layout.activity_phone_input, 5);
        sparseIntArray.put(C0707R.layout.activity_pwd_input, 6);
        sparseIntArray.put(C0707R.layout.activity_sms_code, 7);
        sparseIntArray.put(C0707R.layout.activity_user_info, 8);
        sparseIntArray.put(C0707R.layout.include_set_gender, 9);
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
                if ("layout/activity_code_input_0".equals(tag)) {
                    return new ActivityCodeInputBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_code_input is invalid. Received: " + tag);
            case 2:
                if ("layout/activity_email_input_0".equals(tag)) {
                    return new ActivityEmailInputBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_email_input is invalid. Received: " + tag);
            case 3:
                if ("layout/activity_login_one_key_0".equals(tag)) {
                    return new ActivityLoginOneKeyBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_login_one_key is invalid. Received: " + tag);
            case 4:
                if ("layout/activity_name_input_0".equals(tag)) {
                    return new ActivityNameInputBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_name_input is invalid. Received: " + tag);
            case 5:
                if ("layout/activity_phone_input_0".equals(tag)) {
                    return new ActivityPhoneInputBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_phone_input is invalid. Received: " + tag);
            case 6:
                if ("layout/activity_pwd_input_0".equals(tag)) {
                    return new ActivityPwdInputBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_pwd_input is invalid. Received: " + tag);
            case 7:
                if ("layout/activity_sms_code_0".equals(tag)) {
                    return new ActivitySmsCodeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_sms_code is invalid. Received: " + tag);
            case 8:
                if ("layout/activity_user_info_0".equals(tag)) {
                    return new ActivityUserInfoBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_user_info is invalid. Received: " + tag);
            case 9:
                if ("layout/include_set_gender_0".equals(tag)) {
                    return new IncludeSetGenderBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for include_set_gender is invalid. Received: " + tag);
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
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.aivox.base.DataBinderMapperImpl());
        arrayList.add(new com.aivox.common.DataBinderMapperImpl());
        arrayList.add(new com.aivox.common_ui.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(3);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "clickListener");
            sparseArray.put(2, "model");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(9);
            sKeys = map;
            map.put("layout/activity_code_input_0", Integer.valueOf(C0707R.layout.activity_code_input));
            map.put("layout/activity_email_input_0", Integer.valueOf(C0707R.layout.activity_email_input));
            map.put("layout/activity_login_one_key_0", Integer.valueOf(C0707R.layout.activity_login_one_key));
            map.put("layout/activity_name_input_0", Integer.valueOf(C0707R.layout.activity_name_input));
            map.put("layout/activity_phone_input_0", Integer.valueOf(C0707R.layout.activity_phone_input));
            map.put("layout/activity_pwd_input_0", Integer.valueOf(C0707R.layout.activity_pwd_input));
            map.put("layout/activity_sms_code_0", Integer.valueOf(C0707R.layout.activity_sms_code));
            map.put("layout/activity_user_info_0", Integer.valueOf(C0707R.layout.activity_user_info));
            map.put("layout/include_set_gender_0", Integer.valueOf(C0707R.layout.include_set_gender));
        }
    }
}
