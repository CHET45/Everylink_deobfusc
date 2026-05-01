package com.aivox.set;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.set.databinding.ActivityContactUsBindingImpl;
import com.aivox.set.databinding.ActivityFontSizeBindingImpl;
import com.aivox.set.databinding.ActivityMembershipBindingImpl;
import com.aivox.set.databinding.ActivityPurchaseHistoryBindingImpl;
import com.aivox.set.databinding.ActivityStorageSpaceBindingImpl;
import com.aivox.set.databinding.ActivityTimeDetailBindingImpl;
import com.aivox.set.databinding.ActivityTutorialsBindingImpl;
import com.aivox.set.databinding.ActivityVipJoinBindingImpl;
import com.aivox.set.databinding.ItemBenefitsDetailBindingImpl;
import com.aivox.set.databinding.ItemVipPackageBuyBindingImpl;
import com.aivox.set.databinding.LogoutDialogViewLayoutBindingImpl;
import com.aivox.set.databinding.TimeChargeNoticeViewLayoutBindingImpl;
import com.aivox.set.databinding.ViewTimeUseRuleBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYCONTACTUS = 1;
    private static final int LAYOUT_ACTIVITYFONTSIZE = 2;
    private static final int LAYOUT_ACTIVITYMEMBERSHIP = 3;
    private static final int LAYOUT_ACTIVITYPURCHASEHISTORY = 4;
    private static final int LAYOUT_ACTIVITYSTORAGESPACE = 5;
    private static final int LAYOUT_ACTIVITYTIMEDETAIL = 6;
    private static final int LAYOUT_ACTIVITYTUTORIALS = 7;
    private static final int LAYOUT_ACTIVITYVIPJOIN = 8;
    private static final int LAYOUT_ITEMBENEFITSDETAIL = 9;
    private static final int LAYOUT_ITEMVIPPACKAGEBUY = 10;
    private static final int LAYOUT_LOGOUTDIALOGVIEWLAYOUT = 11;
    private static final int LAYOUT_TIMECHARGENOTICEVIEWLAYOUT = 12;
    private static final int LAYOUT_VIEWTIMEUSERULE = 13;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(13);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C1106R.layout.activity_contact_us, 1);
        sparseIntArray.put(C1106R.layout.activity_font_size, 2);
        sparseIntArray.put(C1106R.layout.activity_membership, 3);
        sparseIntArray.put(C1106R.layout.activity_purchase_history, 4);
        sparseIntArray.put(C1106R.layout.activity_storage_space, 5);
        sparseIntArray.put(C1106R.layout.activity_time_detail, 6);
        sparseIntArray.put(C1106R.layout.activity_tutorials, 7);
        sparseIntArray.put(C1106R.layout.activity_vip_join, 8);
        sparseIntArray.put(C1106R.layout.item_benefits_detail, 9);
        sparseIntArray.put(C1106R.layout.item_vip_package_buy, 10);
        sparseIntArray.put(C1106R.layout.logout_dialog_view_layout, 11);
        sparseIntArray.put(C1106R.layout.time_charge_notice_view_layout, 12);
        sparseIntArray.put(C1106R.layout.view_time_use_rule, 13);
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
                if ("layout/activity_contact_us_0".equals(tag)) {
                    return new ActivityContactUsBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_contact_us is invalid. Received: " + tag);
            case 2:
                if ("layout/activity_font_size_0".equals(tag)) {
                    return new ActivityFontSizeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_font_size is invalid. Received: " + tag);
            case 3:
                if ("layout/activity_membership_0".equals(tag)) {
                    return new ActivityMembershipBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_membership is invalid. Received: " + tag);
            case 4:
                if ("layout/activity_purchase_history_0".equals(tag)) {
                    return new ActivityPurchaseHistoryBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_purchase_history is invalid. Received: " + tag);
            case 5:
                if ("layout/activity_storage_space_0".equals(tag)) {
                    return new ActivityStorageSpaceBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_storage_space is invalid. Received: " + tag);
            case 6:
                if ("layout/activity_time_detail_0".equals(tag)) {
                    return new ActivityTimeDetailBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_time_detail is invalid. Received: " + tag);
            case 7:
                if ("layout/activity_tutorials_0".equals(tag)) {
                    return new ActivityTutorialsBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_tutorials is invalid. Received: " + tag);
            case 8:
                if ("layout/activity_vip_join_0".equals(tag)) {
                    return new ActivityVipJoinBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_vip_join is invalid. Received: " + tag);
            case 9:
                if ("layout/item_benefits_detail_0".equals(tag)) {
                    return new ItemBenefitsDetailBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_benefits_detail is invalid. Received: " + tag);
            case 10:
                if ("layout/item_vip_package_buy_0".equals(tag)) {
                    return new ItemVipPackageBuyBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_vip_package_buy is invalid. Received: " + tag);
            case 11:
                if ("layout/logout_dialog_view_layout_0".equals(tag)) {
                    return new LogoutDialogViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for logout_dialog_view_layout is invalid. Received: " + tag);
            case 12:
                if ("layout/time_charge_notice_view_layout_0".equals(tag)) {
                    return new TimeChargeNoticeViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for time_charge_notice_view_layout is invalid. Received: " + tag);
            case 13:
                if ("layout/view_time_use_rule_0".equals(tag)) {
                    return new ViewTimeUseRuleBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for view_time_use_rule is invalid. Received: " + tag);
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
            SparseArray<String> sparseArray = new SparseArray<>(4);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "clickListener");
            sparseArray.put(2, "model");
            sparseArray.put(3, "xmlmodel");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(13);
            sKeys = map;
            map.put("layout/activity_contact_us_0", Integer.valueOf(C1106R.layout.activity_contact_us));
            map.put("layout/activity_font_size_0", Integer.valueOf(C1106R.layout.activity_font_size));
            map.put("layout/activity_membership_0", Integer.valueOf(C1106R.layout.activity_membership));
            map.put("layout/activity_purchase_history_0", Integer.valueOf(C1106R.layout.activity_purchase_history));
            map.put("layout/activity_storage_space_0", Integer.valueOf(C1106R.layout.activity_storage_space));
            map.put("layout/activity_time_detail_0", Integer.valueOf(C1106R.layout.activity_time_detail));
            map.put("layout/activity_tutorials_0", Integer.valueOf(C1106R.layout.activity_tutorials));
            map.put("layout/activity_vip_join_0", Integer.valueOf(C1106R.layout.activity_vip_join));
            map.put("layout/item_benefits_detail_0", Integer.valueOf(C1106R.layout.item_benefits_detail));
            map.put("layout/item_vip_package_buy_0", Integer.valueOf(C1106R.layout.item_vip_package_buy));
            map.put("layout/logout_dialog_view_layout_0", Integer.valueOf(C1106R.layout.logout_dialog_view_layout));
            map.put("layout/time_charge_notice_view_layout_0", Integer.valueOf(C1106R.layout.time_charge_notice_view_layout));
            map.put("layout/view_time_use_rule_0", Integer.valueOf(C1106R.layout.view_time_use_rule));
        }
    }
}
