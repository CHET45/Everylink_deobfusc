package com.aivox.common_ui;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.common_ui.databinding.AudioSaveViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.BgaBannerItemLayoutBindingImpl;
import com.aivox.common_ui.databinding.BottomEditDialogLayoutBindingImpl;
import com.aivox.common_ui.databinding.DateScrollSelectViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.DeviceActivateDialogLayoutBindingImpl;
import com.aivox.common_ui.databinding.DeviceConnectDialogLayoutBindingImpl;
import com.aivox.common_ui.databinding.DialogEditItemViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.DialogTitleItemViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.DialogTitleViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.EarphonePowerBindingImpl;
import com.aivox.common_ui.databinding.EqScrollSelectViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.FileScrollSelectViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.GenderScrollSelectViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.HolderEditTextLayoutBindingImpl;
import com.aivox.common_ui.databinding.HomeEnterViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.LangItemViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.LangSwitchViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.LoadingButtonLayoutBindingImpl;
import com.aivox.common_ui.databinding.LoginEditTextLayoutBindingImpl;
import com.aivox.common_ui.databinding.PlanItemLayoutBindingImpl;
import com.aivox.common_ui.databinding.PwdForgetViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.RemainTimeItemViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.RemainTimeViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.RemainingTimeViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.SettingTileGroupViewBindingImpl;
import com.aivox.common_ui.databinding.SettingTileLayoutBindingImpl;
import com.aivox.common_ui.databinding.SimpleNoticeBtmViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.SpeakerNumSelectViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.VipFeatureViewLayoutBindingImpl;
import com.aivox.common_ui.databinding.VipPackViewLayoutBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_AUDIOSAVEVIEWLAYOUT = 1;
    private static final int LAYOUT_BGABANNERITEMLAYOUT = 2;
    private static final int LAYOUT_BOTTOMEDITDIALOGLAYOUT = 3;
    private static final int LAYOUT_DATESCROLLSELECTVIEWLAYOUT = 4;
    private static final int LAYOUT_DEVICEACTIVATEDIALOGLAYOUT = 5;
    private static final int LAYOUT_DEVICECONNECTDIALOGLAYOUT = 6;
    private static final int LAYOUT_DIALOGEDITITEMVIEWLAYOUT = 7;
    private static final int LAYOUT_DIALOGTITLEITEMVIEWLAYOUT = 8;
    private static final int LAYOUT_DIALOGTITLEVIEWLAYOUT = 9;
    private static final int LAYOUT_EARPHONEPOWER = 10;
    private static final int LAYOUT_EQSCROLLSELECTVIEWLAYOUT = 11;
    private static final int LAYOUT_FILESCROLLSELECTVIEWLAYOUT = 12;
    private static final int LAYOUT_GENDERSCROLLSELECTVIEWLAYOUT = 13;
    private static final int LAYOUT_HOLDEREDITTEXTLAYOUT = 14;
    private static final int LAYOUT_HOMEENTERVIEWLAYOUT = 15;
    private static final int LAYOUT_LANGITEMVIEWLAYOUT = 16;
    private static final int LAYOUT_LANGSWITCHVIEWLAYOUT = 17;
    private static final int LAYOUT_LOADINGBUTTONLAYOUT = 18;
    private static final int LAYOUT_LOGINEDITTEXTLAYOUT = 19;
    private static final int LAYOUT_PLANITEMLAYOUT = 20;
    private static final int LAYOUT_PWDFORGETVIEWLAYOUT = 21;
    private static final int LAYOUT_REMAININGTIMEVIEWLAYOUT = 24;
    private static final int LAYOUT_REMAINTIMEITEMVIEWLAYOUT = 22;
    private static final int LAYOUT_REMAINTIMEVIEWLAYOUT = 23;
    private static final int LAYOUT_SETTINGTILEGROUPVIEW = 25;
    private static final int LAYOUT_SETTINGTILELAYOUT = 26;
    private static final int LAYOUT_SIMPLENOTICEBTMVIEWLAYOUT = 27;
    private static final int LAYOUT_SPEAKERNUMSELECTVIEWLAYOUT = 28;
    private static final int LAYOUT_VIPFEATUREVIEWLAYOUT = 29;
    private static final int LAYOUT_VIPPACKVIEWLAYOUT = 30;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(30);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C1034R.layout.audio_save_view_layout, 1);
        sparseIntArray.put(C1034R.layout.bga_banner_item_layout, 2);
        sparseIntArray.put(C1034R.layout.bottom_edit_dialog_layout, 3);
        sparseIntArray.put(C1034R.layout.date_scroll_select_view_layout, 4);
        sparseIntArray.put(C1034R.layout.device_activate_dialog_layout, 5);
        sparseIntArray.put(C1034R.layout.device_connect_dialog_layout, 6);
        sparseIntArray.put(C1034R.layout.dialog_edit_item_view_layout, 7);
        sparseIntArray.put(C1034R.layout.dialog_title_item_view_layout, 8);
        sparseIntArray.put(C1034R.layout.dialog_title_view_layout, 9);
        sparseIntArray.put(C1034R.layout.earphone_power, 10);
        sparseIntArray.put(C1034R.layout.eq_scroll_select_view_layout, 11);
        sparseIntArray.put(C1034R.layout.file_scroll_select_view_layout, 12);
        sparseIntArray.put(C1034R.layout.gender_scroll_select_view_layout, 13);
        sparseIntArray.put(C1034R.layout.holder_edit_text_layout, 14);
        sparseIntArray.put(C1034R.layout.home_enter_view_layout, 15);
        sparseIntArray.put(C1034R.layout.lang_item_view_layout, 16);
        sparseIntArray.put(C1034R.layout.lang_switch_view_layout, 17);
        sparseIntArray.put(C1034R.layout.loading_button_layout, 18);
        sparseIntArray.put(C1034R.layout.login_edit_text_layout, 19);
        sparseIntArray.put(C1034R.layout.plan_item_layout, 20);
        sparseIntArray.put(C1034R.layout.pwd_forget_view_layout, 21);
        sparseIntArray.put(C1034R.layout.remain_time_item_view_layout, 22);
        sparseIntArray.put(C1034R.layout.remain_time_view_layout, 23);
        sparseIntArray.put(C1034R.layout.remaining_time_view_layout, 24);
        sparseIntArray.put(C1034R.layout.setting_tile_group_view, 25);
        sparseIntArray.put(C1034R.layout.setting_tile_layout, 26);
        sparseIntArray.put(C1034R.layout.simple_notice_btm_view_layout, 27);
        sparseIntArray.put(C1034R.layout.speaker_num_select_view_layout, 28);
        sparseIntArray.put(C1034R.layout.vip_feature_view_layout, 29);
        sparseIntArray.put(C1034R.layout.vip_pack_view_layout, 30);
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
                if ("layout/audio_save_view_layout_0".equals(tag)) {
                    return new AudioSaveViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for audio_save_view_layout is invalid. Received: " + tag);
            case 2:
                if ("layout/bga_banner_item_layout_0".equals(tag)) {
                    return new BgaBannerItemLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for bga_banner_item_layout is invalid. Received: " + tag);
            case 3:
                if ("layout/bottom_edit_dialog_layout_0".equals(tag)) {
                    return new BottomEditDialogLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for bottom_edit_dialog_layout is invalid. Received: " + tag);
            case 4:
                if ("layout/date_scroll_select_view_layout_0".equals(tag)) {
                    return new DateScrollSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for date_scroll_select_view_layout is invalid. Received: " + tag);
            case 5:
                if ("layout/device_activate_dialog_layout_0".equals(tag)) {
                    return new DeviceActivateDialogLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for device_activate_dialog_layout is invalid. Received: " + tag);
            case 6:
                if ("layout/device_connect_dialog_layout_0".equals(tag)) {
                    return new DeviceConnectDialogLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for device_connect_dialog_layout is invalid. Received: " + tag);
            case 7:
                if ("layout/dialog_edit_item_view_layout_0".equals(tag)) {
                    return new DialogEditItemViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for dialog_edit_item_view_layout is invalid. Received: " + tag);
            case 8:
                if ("layout/dialog_title_item_view_layout_0".equals(tag)) {
                    return new DialogTitleItemViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for dialog_title_item_view_layout is invalid. Received: " + tag);
            case 9:
                if ("layout/dialog_title_view_layout_0".equals(tag)) {
                    return new DialogTitleViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for dialog_title_view_layout is invalid. Received: " + tag);
            case 10:
                if ("layout/earphone_power_0".equals(tag)) {
                    return new EarphonePowerBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for earphone_power is invalid. Received: " + tag);
            case 11:
                if ("layout/eq_scroll_select_view_layout_0".equals(tag)) {
                    return new EqScrollSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for eq_scroll_select_view_layout is invalid. Received: " + tag);
            case 12:
                if ("layout/file_scroll_select_view_layout_0".equals(tag)) {
                    return new FileScrollSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for file_scroll_select_view_layout is invalid. Received: " + tag);
            case 13:
                if ("layout/gender_scroll_select_view_layout_0".equals(tag)) {
                    return new GenderScrollSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for gender_scroll_select_view_layout is invalid. Received: " + tag);
            case 14:
                if ("layout/holder_edit_text_layout_0".equals(tag)) {
                    return new HolderEditTextLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for holder_edit_text_layout is invalid. Received: " + tag);
            case 15:
                if ("layout/home_enter_view_layout_0".equals(tag)) {
                    return new HomeEnterViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for home_enter_view_layout is invalid. Received: " + tag);
            case 16:
                if ("layout/lang_item_view_layout_0".equals(tag)) {
                    return new LangItemViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for lang_item_view_layout is invalid. Received: " + tag);
            case 17:
                if ("layout/lang_switch_view_layout_0".equals(tag)) {
                    return new LangSwitchViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for lang_switch_view_layout is invalid. Received: " + tag);
            case 18:
                if ("layout/loading_button_layout_0".equals(tag)) {
                    return new LoadingButtonLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for loading_button_layout is invalid. Received: " + tag);
            case 19:
                if ("layout/login_edit_text_layout_0".equals(tag)) {
                    return new LoginEditTextLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for login_edit_text_layout is invalid. Received: " + tag);
            case 20:
                if ("layout/plan_item_layout_0".equals(tag)) {
                    return new PlanItemLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for plan_item_layout is invalid. Received: " + tag);
            case 21:
                if ("layout/pwd_forget_view_layout_0".equals(tag)) {
                    return new PwdForgetViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for pwd_forget_view_layout is invalid. Received: " + tag);
            case 22:
                if ("layout/remain_time_item_view_layout_0".equals(tag)) {
                    return new RemainTimeItemViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for remain_time_item_view_layout is invalid. Received: " + tag);
            case 23:
                if ("layout/remain_time_view_layout_0".equals(tag)) {
                    return new RemainTimeViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for remain_time_view_layout is invalid. Received: " + tag);
            case 24:
                if ("layout/remaining_time_view_layout_0".equals(tag)) {
                    return new RemainingTimeViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for remaining_time_view_layout is invalid. Received: " + tag);
            case 25:
                if ("layout/setting_tile_group_view_0".equals(tag)) {
                    return new SettingTileGroupViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for setting_tile_group_view is invalid. Received: " + tag);
            case 26:
                if ("layout/setting_tile_layout_0".equals(tag)) {
                    return new SettingTileLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for setting_tile_layout is invalid. Received: " + tag);
            case 27:
                if ("layout/simple_notice_btm_view_layout_0".equals(tag)) {
                    return new SimpleNoticeBtmViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for simple_notice_btm_view_layout is invalid. Received: " + tag);
            case 28:
                if ("layout/speaker_num_select_view_layout_0".equals(tag)) {
                    return new SpeakerNumSelectViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for speaker_num_select_view_layout is invalid. Received: " + tag);
            case 29:
                if ("layout/vip_feature_view_layout_0".equals(tag)) {
                    return new VipFeatureViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for vip_feature_view_layout is invalid. Received: " + tag);
            case 30:
                if ("layout/vip_pack_view_layout_0".equals(tag)) {
                    return new VipPackViewLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for vip_pack_view_layout is invalid. Received: " + tag);
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
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.aivox.base.DataBinderMapperImpl());
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
            HashMap<String, Integer> map = new HashMap<>(30);
            sKeys = map;
            map.put("layout/audio_save_view_layout_0", Integer.valueOf(C1034R.layout.audio_save_view_layout));
            map.put("layout/bga_banner_item_layout_0", Integer.valueOf(C1034R.layout.bga_banner_item_layout));
            map.put("layout/bottom_edit_dialog_layout_0", Integer.valueOf(C1034R.layout.bottom_edit_dialog_layout));
            map.put("layout/date_scroll_select_view_layout_0", Integer.valueOf(C1034R.layout.date_scroll_select_view_layout));
            map.put("layout/device_activate_dialog_layout_0", Integer.valueOf(C1034R.layout.device_activate_dialog_layout));
            map.put("layout/device_connect_dialog_layout_0", Integer.valueOf(C1034R.layout.device_connect_dialog_layout));
            map.put("layout/dialog_edit_item_view_layout_0", Integer.valueOf(C1034R.layout.dialog_edit_item_view_layout));
            map.put("layout/dialog_title_item_view_layout_0", Integer.valueOf(C1034R.layout.dialog_title_item_view_layout));
            map.put("layout/dialog_title_view_layout_0", Integer.valueOf(C1034R.layout.dialog_title_view_layout));
            map.put("layout/earphone_power_0", Integer.valueOf(C1034R.layout.earphone_power));
            map.put("layout/eq_scroll_select_view_layout_0", Integer.valueOf(C1034R.layout.eq_scroll_select_view_layout));
            map.put("layout/file_scroll_select_view_layout_0", Integer.valueOf(C1034R.layout.file_scroll_select_view_layout));
            map.put("layout/gender_scroll_select_view_layout_0", Integer.valueOf(C1034R.layout.gender_scroll_select_view_layout));
            map.put("layout/holder_edit_text_layout_0", Integer.valueOf(C1034R.layout.holder_edit_text_layout));
            map.put("layout/home_enter_view_layout_0", Integer.valueOf(C1034R.layout.home_enter_view_layout));
            map.put("layout/lang_item_view_layout_0", Integer.valueOf(C1034R.layout.lang_item_view_layout));
            map.put("layout/lang_switch_view_layout_0", Integer.valueOf(C1034R.layout.lang_switch_view_layout));
            map.put("layout/loading_button_layout_0", Integer.valueOf(C1034R.layout.loading_button_layout));
            map.put("layout/login_edit_text_layout_0", Integer.valueOf(C1034R.layout.login_edit_text_layout));
            map.put("layout/plan_item_layout_0", Integer.valueOf(C1034R.layout.plan_item_layout));
            map.put("layout/pwd_forget_view_layout_0", Integer.valueOf(C1034R.layout.pwd_forget_view_layout));
            map.put("layout/remain_time_item_view_layout_0", Integer.valueOf(C1034R.layout.remain_time_item_view_layout));
            map.put("layout/remain_time_view_layout_0", Integer.valueOf(C1034R.layout.remain_time_view_layout));
            map.put("layout/remaining_time_view_layout_0", Integer.valueOf(C1034R.layout.remaining_time_view_layout));
            map.put("layout/setting_tile_group_view_0", Integer.valueOf(C1034R.layout.setting_tile_group_view));
            map.put("layout/setting_tile_layout_0", Integer.valueOf(C1034R.layout.setting_tile_layout));
            map.put("layout/simple_notice_btm_view_layout_0", Integer.valueOf(C1034R.layout.simple_notice_btm_view_layout));
            map.put("layout/speaker_num_select_view_layout_0", Integer.valueOf(C1034R.layout.speaker_num_select_view_layout));
            map.put("layout/vip_feature_view_layout_0", Integer.valueOf(C1034R.layout.vip_feature_view_layout));
            map.put("layout/vip_pack_view_layout_0", Integer.valueOf(C1034R.layout.vip_pack_view_layout));
        }
    }
}
