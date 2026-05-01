package com.aivox.app;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.databinding.ActivityAiChatEditBindingImpl;
import com.aivox.app.databinding.ActivityConversationHistoryBindingImpl;
import com.aivox.app.databinding.ActivityConversationRecordingBindingImpl;
import com.aivox.app.databinding.ActivityDeviceConnectedBindingImpl;
import com.aivox.app.databinding.ActivityDeviceFilesBindingImpl;
import com.aivox.app.databinding.ActivityDeviceHelpBindingImpl;
import com.aivox.app.databinding.ActivityDeviceInfoBindingImpl;
import com.aivox.app.databinding.ActivityDeviceScanBindingImpl;
import com.aivox.app.databinding.ActivityDeviceUpdateBindingImpl;
import com.aivox.app.databinding.ActivityEarPhoneConversationRecordingBindingImpl;
import com.aivox.app.databinding.ActivityExternalOpenBindingImpl;
import com.aivox.app.databinding.ActivityFolderSelectBindingImpl;
import com.aivox.app.databinding.ActivityFunctionGuideBindingImpl;
import com.aivox.app.databinding.ActivityGlassManualBindingImpl;
import com.aivox.app.databinding.ActivityGlassPreviewBindingImpl;
import com.aivox.app.databinding.ActivityMainBindingImpl;
import com.aivox.app.databinding.ActivityMyPhotoBrowseBindingImpl;
import com.aivox.app.databinding.ActivityNoteMarkListBindingImpl;
import com.aivox.app.databinding.ActivityNoticeDetailBindingImpl;
import com.aivox.app.databinding.ActivityNoticeListBindingImpl;
import com.aivox.app.databinding.ActivityRecordImportBindingImpl;
import com.aivox.app.databinding.ActivityRecordInfoBindingImpl;
import com.aivox.app.databinding.ActivityRecordUploadBindingImpl;
import com.aivox.app.databinding.ActivityRecordingBindingImpl;
import com.aivox.app.databinding.ActivitySearchAndMoveBindingImpl;
import com.aivox.app.databinding.ActivityWebBindingImpl;
import com.aivox.app.databinding.AiSummaryOperateViewBindingImpl;
import com.aivox.app.databinding.AudioInAppShareViewBindingImpl;
import com.aivox.app.databinding.AudioOperateAiViewBindingImpl;
import com.aivox.app.databinding.AudioOperateViewBindingImpl;
import com.aivox.app.databinding.AudioShareViewBindingImpl;
import com.aivox.app.databinding.FooterBindingImpl;
import com.aivox.app.databinding.FragmentCloudRecordBindingImpl;
import com.aivox.app.databinding.FragmentFileBindingImpl;
import com.aivox.app.databinding.FragmentGallaryBindingImpl;
import com.aivox.app.databinding.FragmentHomeBindingImpl;
import com.aivox.app.databinding.FragmentLocalRecordBindingImpl;
import com.aivox.app.databinding.FragmentMainAiBindingImpl;
import com.aivox.app.databinding.FragmentMainFileBindingImpl;
import com.aivox.app.databinding.FragmentMainHomeBindingImpl;
import com.aivox.app.databinding.FragmentMainMeBindingImpl;
import com.aivox.app.databinding.FragmentRecordTranscribeBindingImpl;
import com.aivox.app.databinding.FragmentRecordingTranscribeBindingImpl;
import com.aivox.app.databinding.IncludeAudioDetailHeaderBindingImpl;
import com.aivox.app.databinding.IncludeNullBindingImpl;
import com.aivox.app.databinding.IncludeRecommendShareBindingImpl;
import com.aivox.app.databinding.ItemAccoutBindingImpl;
import com.aivox.app.databinding.ItemAudioimportListLayoutBindingImpl;
import com.aivox.app.databinding.ItemBleDeviceBindingImpl;
import com.aivox.app.databinding.ItemConversationHistoryBindingImpl;
import com.aivox.app.databinding.ItemConversationLeftBindingImpl;
import com.aivox.app.databinding.ItemConversationRightBindingImpl;
import com.aivox.app.databinding.ItemFeedbackBindingBindingImpl;
import com.aivox.app.databinding.ItemFolderSelectLayoutBindingImpl;
import com.aivox.app.databinding.ItemHomeAiChatBindingImpl;
import com.aivox.app.databinding.ItemMembershipPackageBindingBindingImpl;
import com.aivox.app.databinding.ItemMsgBindingImpl;
import com.aivox.app.databinding.ItemRecordImgTagBindingImpl;
import com.aivox.app.databinding.ItemTranscribeBindingImpl;
import com.aivox.app.databinding.ItemTranscribeFloatingBindingImpl;
import com.aivox.app.databinding.ItemVipPriceBindingImpl;
import com.aivox.app.databinding.ViewConversationRecordBindingImpl;
import com.microsoft.azure.storage.table.TableConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYAICHATEDIT = 1;
    private static final int LAYOUT_ACTIVITYCONVERSATIONHISTORY = 2;
    private static final int LAYOUT_ACTIVITYCONVERSATIONRECORDING = 3;
    private static final int LAYOUT_ACTIVITYDEVICECONNECTED = 4;
    private static final int LAYOUT_ACTIVITYDEVICEFILES = 5;
    private static final int LAYOUT_ACTIVITYDEVICEHELP = 6;
    private static final int LAYOUT_ACTIVITYDEVICEINFO = 7;
    private static final int LAYOUT_ACTIVITYDEVICESCAN = 8;
    private static final int LAYOUT_ACTIVITYDEVICEUPDATE = 9;
    private static final int LAYOUT_ACTIVITYEARPHONECONVERSATIONRECORDING = 10;
    private static final int LAYOUT_ACTIVITYEXTERNALOPEN = 11;
    private static final int LAYOUT_ACTIVITYFOLDERSELECT = 12;
    private static final int LAYOUT_ACTIVITYFUNCTIONGUIDE = 13;
    private static final int LAYOUT_ACTIVITYGLASSMANUAL = 14;
    private static final int LAYOUT_ACTIVITYGLASSPREVIEW = 15;
    private static final int LAYOUT_ACTIVITYMAIN = 16;
    private static final int LAYOUT_ACTIVITYMYPHOTOBROWSE = 17;
    private static final int LAYOUT_ACTIVITYNOTEMARKLIST = 18;
    private static final int LAYOUT_ACTIVITYNOTICEDETAIL = 19;
    private static final int LAYOUT_ACTIVITYNOTICELIST = 20;
    private static final int LAYOUT_ACTIVITYRECORDIMPORT = 21;
    private static final int LAYOUT_ACTIVITYRECORDINFO = 22;
    private static final int LAYOUT_ACTIVITYRECORDING = 24;
    private static final int LAYOUT_ACTIVITYRECORDUPLOAD = 23;
    private static final int LAYOUT_ACTIVITYSEARCHANDMOVE = 25;
    private static final int LAYOUT_ACTIVITYWEB = 26;
    private static final int LAYOUT_AISUMMARYOPERATEVIEW = 27;
    private static final int LAYOUT_AUDIOINAPPSHAREVIEW = 28;
    private static final int LAYOUT_AUDIOOPERATEAIVIEW = 29;
    private static final int LAYOUT_AUDIOOPERATEVIEW = 30;
    private static final int LAYOUT_AUDIOSHAREVIEW = 31;
    private static final int LAYOUT_FOOTER = 32;
    private static final int LAYOUT_FRAGMENTCLOUDRECORD = 33;
    private static final int LAYOUT_FRAGMENTFILE = 34;
    private static final int LAYOUT_FRAGMENTGALLARY = 35;
    private static final int LAYOUT_FRAGMENTHOME = 36;
    private static final int LAYOUT_FRAGMENTLOCALRECORD = 37;
    private static final int LAYOUT_FRAGMENTMAINAI = 38;
    private static final int LAYOUT_FRAGMENTMAINFILE = 39;
    private static final int LAYOUT_FRAGMENTMAINHOME = 40;
    private static final int LAYOUT_FRAGMENTMAINME = 41;
    private static final int LAYOUT_FRAGMENTRECORDINGTRANSCRIBE = 43;
    private static final int LAYOUT_FRAGMENTRECORDTRANSCRIBE = 42;
    private static final int LAYOUT_INCLUDEAUDIODETAILHEADER = 44;
    private static final int LAYOUT_INCLUDENULL = 45;
    private static final int LAYOUT_INCLUDERECOMMENDSHARE = 46;
    private static final int LAYOUT_ITEMACCOUT = 47;
    private static final int LAYOUT_ITEMAUDIOIMPORTLISTLAYOUT = 48;
    private static final int LAYOUT_ITEMBLEDEVICE = 49;
    private static final int LAYOUT_ITEMCONVERSATIONHISTORY = 50;
    private static final int LAYOUT_ITEMCONVERSATIONLEFT = 51;
    private static final int LAYOUT_ITEMCONVERSATIONRIGHT = 52;
    private static final int LAYOUT_ITEMFEEDBACKBINDING = 53;
    private static final int LAYOUT_ITEMFOLDERSELECTLAYOUT = 54;
    private static final int LAYOUT_ITEMHOMEAICHAT = 55;
    private static final int LAYOUT_ITEMMEMBERSHIPPACKAGEBINDING = 56;
    private static final int LAYOUT_ITEMMSG = 57;
    private static final int LAYOUT_ITEMRECORDIMGTAG = 58;
    private static final int LAYOUT_ITEMTRANSCRIBE = 59;
    private static final int LAYOUT_ITEMTRANSCRIBEFLOATING = 60;
    private static final int LAYOUT_ITEMVIPPRICE = 61;
    private static final int LAYOUT_VIEWCONVERSATIONRECORD = 62;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(62);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(C0726R.layout.activity_ai_chat_edit, 1);
        sparseIntArray.put(C0726R.layout.activity_conversation_history, 2);
        sparseIntArray.put(C0726R.layout.activity_conversation_recording, 3);
        sparseIntArray.put(C0726R.layout.activity_device_connected, 4);
        sparseIntArray.put(C0726R.layout.activity_device_files, 5);
        sparseIntArray.put(C0726R.layout.activity_device_help, 6);
        sparseIntArray.put(C0726R.layout.activity_device_info, 7);
        sparseIntArray.put(C0726R.layout.activity_device_scan, 8);
        sparseIntArray.put(C0726R.layout.activity_device_update, 9);
        sparseIntArray.put(C0726R.layout.activity_ear_phone_conversation_recording, 10);
        sparseIntArray.put(C0726R.layout.activity_external_open, 11);
        sparseIntArray.put(C0726R.layout.activity_folder_select, 12);
        sparseIntArray.put(C0726R.layout.activity_function_guide, 13);
        sparseIntArray.put(C0726R.layout.activity_glass_manual, 14);
        sparseIntArray.put(C0726R.layout.activity_glass_preview, 15);
        sparseIntArray.put(C0726R.layout.activity_main, 16);
        sparseIntArray.put(C0726R.layout.activity_my_photo_browse, 17);
        sparseIntArray.put(C0726R.layout.activity_note_mark_list, 18);
        sparseIntArray.put(C0726R.layout.activity_notice_detail, 19);
        sparseIntArray.put(C0726R.layout.activity_notice_list, 20);
        sparseIntArray.put(C0726R.layout.activity_record_import, 21);
        sparseIntArray.put(C0726R.layout.activity_record_info, 22);
        sparseIntArray.put(C0726R.layout.activity_record_upload, 23);
        sparseIntArray.put(C0726R.layout.activity_recording, 24);
        sparseIntArray.put(C0726R.layout.activity_search_and_move, 25);
        sparseIntArray.put(C0726R.layout.activity_web, 26);
        sparseIntArray.put(C0726R.layout.ai_summary_operate_view, 27);
        sparseIntArray.put(C0726R.layout.audio_in_app_share_view, 28);
        sparseIntArray.put(C0726R.layout.audio_operate_ai_view, 29);
        sparseIntArray.put(C0726R.layout.audio_operate_view, 30);
        sparseIntArray.put(C0726R.layout.audio_share_view, 31);
        sparseIntArray.put(C0726R.layout.footer, 32);
        sparseIntArray.put(C0726R.layout.fragment_cloud_record, 33);
        sparseIntArray.put(C0726R.layout.fragment_file, 34);
        sparseIntArray.put(C0726R.layout.fragment_gallary, 35);
        sparseIntArray.put(C0726R.layout.fragment_home, 36);
        sparseIntArray.put(C0726R.layout.fragment_local_record, 37);
        sparseIntArray.put(C0726R.layout.fragment_main_ai, 38);
        sparseIntArray.put(C0726R.layout.fragment_main_file, 39);
        sparseIntArray.put(C0726R.layout.fragment_main_home, 40);
        sparseIntArray.put(C0726R.layout.fragment_main_me, 41);
        sparseIntArray.put(C0726R.layout.fragment_record_transcribe, 42);
        sparseIntArray.put(C0726R.layout.fragment_recording_transcribe, 43);
        sparseIntArray.put(C0726R.layout.include_audio_detail_header, 44);
        sparseIntArray.put(C0726R.layout.include_null, 45);
        sparseIntArray.put(C0726R.layout.include_recommend_share, 46);
        sparseIntArray.put(C0726R.layout.item_accout, 47);
        sparseIntArray.put(C0726R.layout.item_audioimport_list_layout, 48);
        sparseIntArray.put(C0726R.layout.item_ble_device, 49);
        sparseIntArray.put(C0726R.layout.item_conversation_history, 50);
        sparseIntArray.put(C0726R.layout.item_conversation_left, 51);
        sparseIntArray.put(C0726R.layout.item_conversation_right, 52);
        sparseIntArray.put(C0726R.layout.item_feedback_binding, 53);
        sparseIntArray.put(C0726R.layout.item_folder_select_layout, 54);
        sparseIntArray.put(C0726R.layout.item_home_ai_chat, 55);
        sparseIntArray.put(C0726R.layout.item_membership_package_binding, 56);
        sparseIntArray.put(C0726R.layout.item_msg, 57);
        sparseIntArray.put(C0726R.layout.item_record_img_tag, 58);
        sparseIntArray.put(C0726R.layout.item_transcribe, 59);
        sparseIntArray.put(C0726R.layout.item_transcribe_floating, 60);
        sparseIntArray.put(C0726R.layout.item_vip_price, 61);
        sparseIntArray.put(C0726R.layout.view_conversation_record, 62);
    }

    private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent dataBindingComponent, View view2, int i, Object obj) {
        switch (i) {
            case 1:
                if ("layout/activity_ai_chat_edit_0".equals(obj)) {
                    return new ActivityAiChatEditBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_ai_chat_edit is invalid. Received: " + obj);
            case 2:
                if ("layout/activity_conversation_history_0".equals(obj)) {
                    return new ActivityConversationHistoryBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_conversation_history is invalid. Received: " + obj);
            case 3:
                if ("layout/activity_conversation_recording_0".equals(obj)) {
                    return new ActivityConversationRecordingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_conversation_recording is invalid. Received: " + obj);
            case 4:
                if ("layout/activity_device_connected_0".equals(obj)) {
                    return new ActivityDeviceConnectedBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_connected is invalid. Received: " + obj);
            case 5:
                if ("layout/activity_device_files_0".equals(obj)) {
                    return new ActivityDeviceFilesBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_files is invalid. Received: " + obj);
            case 6:
                if ("layout/activity_device_help_0".equals(obj)) {
                    return new ActivityDeviceHelpBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_help is invalid. Received: " + obj);
            case 7:
                if ("layout/activity_device_info_0".equals(obj)) {
                    return new ActivityDeviceInfoBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_info is invalid. Received: " + obj);
            case 8:
                if ("layout/activity_device_scan_0".equals(obj)) {
                    return new ActivityDeviceScanBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_scan is invalid. Received: " + obj);
            case 9:
                if ("layout/activity_device_update_0".equals(obj)) {
                    return new ActivityDeviceUpdateBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_device_update is invalid. Received: " + obj);
            case 10:
                if ("layout/activity_ear_phone_conversation_recording_0".equals(obj)) {
                    return new ActivityEarPhoneConversationRecordingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_ear_phone_conversation_recording is invalid. Received: " + obj);
            case 11:
                if ("layout/activity_external_open_0".equals(obj)) {
                    return new ActivityExternalOpenBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_external_open is invalid. Received: " + obj);
            case 12:
                if ("layout/activity_folder_select_0".equals(obj)) {
                    return new ActivityFolderSelectBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_folder_select is invalid. Received: " + obj);
            case 13:
                if ("layout/activity_function_guide_0".equals(obj)) {
                    return new ActivityFunctionGuideBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_function_guide is invalid. Received: " + obj);
            case 14:
                if ("layout/activity_glass_manual_0".equals(obj)) {
                    return new ActivityGlassManualBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_glass_manual is invalid. Received: " + obj);
            case 15:
                if ("layout/activity_glass_preview_0".equals(obj)) {
                    return new ActivityGlassPreviewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_glass_preview is invalid. Received: " + obj);
            case 16:
                if ("layout/activity_main_0".equals(obj)) {
                    return new ActivityMainBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + obj);
            case 17:
                if ("layout/activity_my_photo_browse_0".equals(obj)) {
                    return new ActivityMyPhotoBrowseBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_my_photo_browse is invalid. Received: " + obj);
            case 18:
                if ("layout/activity_note_mark_list_0".equals(obj)) {
                    return new ActivityNoteMarkListBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_note_mark_list is invalid. Received: " + obj);
            case 19:
                if ("layout/activity_notice_detail_0".equals(obj)) {
                    return new ActivityNoticeDetailBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_notice_detail is invalid. Received: " + obj);
            case 20:
                if ("layout/activity_notice_list_0".equals(obj)) {
                    return new ActivityNoticeListBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_notice_list is invalid. Received: " + obj);
            case 21:
                if ("layout/activity_record_import_0".equals(obj)) {
                    return new ActivityRecordImportBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_record_import is invalid. Received: " + obj);
            case 22:
                if ("layout/activity_record_info_0".equals(obj)) {
                    return new ActivityRecordInfoBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_record_info is invalid. Received: " + obj);
            case 23:
                if ("layout/activity_record_upload_0".equals(obj)) {
                    return new ActivityRecordUploadBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_record_upload is invalid. Received: " + obj);
            case 24:
                if ("layout/activity_recording_0".equals(obj)) {
                    return new ActivityRecordingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_recording is invalid. Received: " + obj);
            case 25:
                if ("layout/activity_search_and_move_0".equals(obj)) {
                    return new ActivitySearchAndMoveBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_search_and_move is invalid. Received: " + obj);
            case 26:
                if ("layout/activity_web_0".equals(obj)) {
                    return new ActivityWebBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for activity_web is invalid. Received: " + obj);
            case 27:
                if ("layout/ai_summary_operate_view_0".equals(obj)) {
                    return new AiSummaryOperateViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for ai_summary_operate_view is invalid. Received: " + obj);
            case 28:
                if ("layout/audio_in_app_share_view_0".equals(obj)) {
                    return new AudioInAppShareViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for audio_in_app_share_view is invalid. Received: " + obj);
            case 29:
                if ("layout/audio_operate_ai_view_0".equals(obj)) {
                    return new AudioOperateAiViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for audio_operate_ai_view is invalid. Received: " + obj);
            case 30:
                if ("layout/audio_operate_view_0".equals(obj)) {
                    return new AudioOperateViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for audio_operate_view is invalid. Received: " + obj);
            case 31:
                if ("layout/audio_share_view_0".equals(obj)) {
                    return new AudioShareViewBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for audio_share_view is invalid. Received: " + obj);
            case 32:
                if ("layout/footer_0".equals(obj)) {
                    return new FooterBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for footer is invalid. Received: " + obj);
            case 33:
                if ("layout/fragment_cloud_record_0".equals(obj)) {
                    return new FragmentCloudRecordBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_cloud_record is invalid. Received: " + obj);
            case 34:
                if ("layout/fragment_file_0".equals(obj)) {
                    return new FragmentFileBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_file is invalid. Received: " + obj);
            case 35:
                if ("layout/fragment_gallary_0".equals(obj)) {
                    return new FragmentGallaryBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_gallary is invalid. Received: " + obj);
            case 36:
                if ("layout/fragment_home_0".equals(obj)) {
                    return new FragmentHomeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + obj);
            case 37:
                if ("layout/fragment_local_record_0".equals(obj)) {
                    return new FragmentLocalRecordBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_local_record is invalid. Received: " + obj);
            case 38:
                if ("layout/fragment_main_ai_0".equals(obj)) {
                    return new FragmentMainAiBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_main_ai is invalid. Received: " + obj);
            case 39:
                if ("layout/fragment_main_file_0".equals(obj)) {
                    return new FragmentMainFileBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_main_file is invalid. Received: " + obj);
            case 40:
                if ("layout/fragment_main_home_0".equals(obj)) {
                    return new FragmentMainHomeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_main_home is invalid. Received: " + obj);
            case 41:
                if ("layout/fragment_main_me_0".equals(obj)) {
                    return new FragmentMainMeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_main_me is invalid. Received: " + obj);
            case 42:
                if ("layout/fragment_record_transcribe_0".equals(obj)) {
                    return new FragmentRecordTranscribeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_record_transcribe is invalid. Received: " + obj);
            case 43:
                if ("layout/fragment_recording_transcribe_0".equals(obj)) {
                    return new FragmentRecordingTranscribeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for fragment_recording_transcribe is invalid. Received: " + obj);
            case 44:
                if ("layout/include_audio_detail_header_0".equals(obj)) {
                    return new IncludeAudioDetailHeaderBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for include_audio_detail_header is invalid. Received: " + obj);
            case 45:
                if ("layout/include_null_0".equals(obj)) {
                    return new IncludeNullBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for include_null is invalid. Received: " + obj);
            case 46:
                if ("layout/include_recommend_share_0".equals(obj)) {
                    return new IncludeRecommendShareBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for include_recommend_share is invalid. Received: " + obj);
            case 47:
                if ("layout/item_accout_0".equals(obj)) {
                    return new ItemAccoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_accout is invalid. Received: " + obj);
            case 48:
                if ("layout/item_audioimport_list_layout_0".equals(obj)) {
                    return new ItemAudioimportListLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_audioimport_list_layout is invalid. Received: " + obj);
            case 49:
                if ("layout/item_ble_device_0".equals(obj)) {
                    return new ItemBleDeviceBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_ble_device is invalid. Received: " + obj);
            case 50:
                if ("layout/item_conversation_history_0".equals(obj)) {
                    return new ItemConversationHistoryBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_conversation_history is invalid. Received: " + obj);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent dataBindingComponent, View view2, int i, Object obj) {
        switch (i) {
            case 51:
                if ("layout/item_conversation_left_0".equals(obj)) {
                    return new ItemConversationLeftBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_conversation_left is invalid. Received: " + obj);
            case 52:
                if ("layout/item_conversation_right_0".equals(obj)) {
                    return new ItemConversationRightBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_conversation_right is invalid. Received: " + obj);
            case 53:
                if ("layout/item_feedback_binding_0".equals(obj)) {
                    return new ItemFeedbackBindingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_feedback_binding is invalid. Received: " + obj);
            case 54:
                if ("layout/item_folder_select_layout_0".equals(obj)) {
                    return new ItemFolderSelectLayoutBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_folder_select_layout is invalid. Received: " + obj);
            case 55:
                if ("layout/item_home_ai_chat_0".equals(obj)) {
                    return new ItemHomeAiChatBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_home_ai_chat is invalid. Received: " + obj);
            case 56:
                if ("layout/item_membership_package_binding_0".equals(obj)) {
                    return new ItemMembershipPackageBindingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_membership_package_binding is invalid. Received: " + obj);
            case 57:
                if ("layout/item_msg_0".equals(obj)) {
                    return new ItemMsgBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_msg is invalid. Received: " + obj);
            case 58:
                if ("layout/item_record_img_tag_0".equals(obj)) {
                    return new ItemRecordImgTagBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_record_img_tag is invalid. Received: " + obj);
            case 59:
                if ("layout/item_transcribe_0".equals(obj)) {
                    return new ItemTranscribeBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_transcribe is invalid. Received: " + obj);
            case 60:
                if ("layout/item_transcribe_floating_0".equals(obj)) {
                    return new ItemTranscribeFloatingBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_transcribe_floating is invalid. Received: " + obj);
            case 61:
                if ("layout/item_vip_price_0".equals(obj)) {
                    return new ItemVipPriceBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for item_vip_price is invalid. Received: " + obj);
            case 62:
                if ("layout/view_conversation_record_0".equals(obj)) {
                    return new ViewConversationRecordBindingImpl(dataBindingComponent, view2);
                }
                throw new IllegalArgumentException("The tag for view_conversation_record is invalid. Received: " + obj);
            default:
                return null;
        }
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
        int i3 = (i2 - 1) / 50;
        if (i3 == 0) {
            return internalGetViewDataBinding0(dataBindingComponent, view2, i2, tag);
        }
        if (i3 != 1) {
            return null;
        }
        return internalGetViewDataBinding1(dataBindingComponent, view2, i2, tag);
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
        ArrayList arrayList = new ArrayList(8);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.aivox.account.DataBinderMapperImpl());
        arrayList.add(new com.aivox.base.DataBinderMapperImpl());
        arrayList.add(new com.aivox.besota.DataBinderMapperImpl());
        arrayList.add(new com.aivox.common.DataBinderMapperImpl());
        arrayList.add(new com.aivox.common_ui.DataBinderMapperImpl());
        arrayList.add(new com.aivox.jieliota.DataBinderMapperImpl());
        arrayList.add(new com.aivox.set.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(7);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "clickListener");
            sparseArray.put(2, "isFaceMode");
            sparseArray.put(3, "model");
            sparseArray.put(4, "rippersColor");
            sparseArray.put(5, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
            sparseArray.put(6, "xmlmodel");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(62);
            sKeys = map;
            map.put("layout/activity_ai_chat_edit_0", Integer.valueOf(C0726R.layout.activity_ai_chat_edit));
            map.put("layout/activity_conversation_history_0", Integer.valueOf(C0726R.layout.activity_conversation_history));
            map.put("layout/activity_conversation_recording_0", Integer.valueOf(C0726R.layout.activity_conversation_recording));
            map.put("layout/activity_device_connected_0", Integer.valueOf(C0726R.layout.activity_device_connected));
            map.put("layout/activity_device_files_0", Integer.valueOf(C0726R.layout.activity_device_files));
            map.put("layout/activity_device_help_0", Integer.valueOf(C0726R.layout.activity_device_help));
            map.put("layout/activity_device_info_0", Integer.valueOf(C0726R.layout.activity_device_info));
            map.put("layout/activity_device_scan_0", Integer.valueOf(C0726R.layout.activity_device_scan));
            map.put("layout/activity_device_update_0", Integer.valueOf(C0726R.layout.activity_device_update));
            map.put("layout/activity_ear_phone_conversation_recording_0", Integer.valueOf(C0726R.layout.activity_ear_phone_conversation_recording));
            map.put("layout/activity_external_open_0", Integer.valueOf(C0726R.layout.activity_external_open));
            map.put("layout/activity_folder_select_0", Integer.valueOf(C0726R.layout.activity_folder_select));
            map.put("layout/activity_function_guide_0", Integer.valueOf(C0726R.layout.activity_function_guide));
            map.put("layout/activity_glass_manual_0", Integer.valueOf(C0726R.layout.activity_glass_manual));
            map.put("layout/activity_glass_preview_0", Integer.valueOf(C0726R.layout.activity_glass_preview));
            map.put("layout/activity_main_0", Integer.valueOf(C0726R.layout.activity_main));
            map.put("layout/activity_my_photo_browse_0", Integer.valueOf(C0726R.layout.activity_my_photo_browse));
            map.put("layout/activity_note_mark_list_0", Integer.valueOf(C0726R.layout.activity_note_mark_list));
            map.put("layout/activity_notice_detail_0", Integer.valueOf(C0726R.layout.activity_notice_detail));
            map.put("layout/activity_notice_list_0", Integer.valueOf(C0726R.layout.activity_notice_list));
            map.put("layout/activity_record_import_0", Integer.valueOf(C0726R.layout.activity_record_import));
            map.put("layout/activity_record_info_0", Integer.valueOf(C0726R.layout.activity_record_info));
            map.put("layout/activity_record_upload_0", Integer.valueOf(C0726R.layout.activity_record_upload));
            map.put("layout/activity_recording_0", Integer.valueOf(C0726R.layout.activity_recording));
            map.put("layout/activity_search_and_move_0", Integer.valueOf(C0726R.layout.activity_search_and_move));
            map.put("layout/activity_web_0", Integer.valueOf(C0726R.layout.activity_web));
            map.put("layout/ai_summary_operate_view_0", Integer.valueOf(C0726R.layout.ai_summary_operate_view));
            map.put("layout/audio_in_app_share_view_0", Integer.valueOf(C0726R.layout.audio_in_app_share_view));
            map.put("layout/audio_operate_ai_view_0", Integer.valueOf(C0726R.layout.audio_operate_ai_view));
            map.put("layout/audio_operate_view_0", Integer.valueOf(C0726R.layout.audio_operate_view));
            map.put("layout/audio_share_view_0", Integer.valueOf(C0726R.layout.audio_share_view));
            map.put("layout/footer_0", Integer.valueOf(C0726R.layout.footer));
            map.put("layout/fragment_cloud_record_0", Integer.valueOf(C0726R.layout.fragment_cloud_record));
            map.put("layout/fragment_file_0", Integer.valueOf(C0726R.layout.fragment_file));
            map.put("layout/fragment_gallary_0", Integer.valueOf(C0726R.layout.fragment_gallary));
            map.put("layout/fragment_home_0", Integer.valueOf(C0726R.layout.fragment_home));
            map.put("layout/fragment_local_record_0", Integer.valueOf(C0726R.layout.fragment_local_record));
            map.put("layout/fragment_main_ai_0", Integer.valueOf(C0726R.layout.fragment_main_ai));
            map.put("layout/fragment_main_file_0", Integer.valueOf(C0726R.layout.fragment_main_file));
            map.put("layout/fragment_main_home_0", Integer.valueOf(C0726R.layout.fragment_main_home));
            map.put("layout/fragment_main_me_0", Integer.valueOf(C0726R.layout.fragment_main_me));
            map.put("layout/fragment_record_transcribe_0", Integer.valueOf(C0726R.layout.fragment_record_transcribe));
            map.put("layout/fragment_recording_transcribe_0", Integer.valueOf(C0726R.layout.fragment_recording_transcribe));
            map.put("layout/include_audio_detail_header_0", Integer.valueOf(C0726R.layout.include_audio_detail_header));
            map.put("layout/include_null_0", Integer.valueOf(C0726R.layout.include_null));
            map.put("layout/include_recommend_share_0", Integer.valueOf(C0726R.layout.include_recommend_share));
            map.put("layout/item_accout_0", Integer.valueOf(C0726R.layout.item_accout));
            map.put("layout/item_audioimport_list_layout_0", Integer.valueOf(C0726R.layout.item_audioimport_list_layout));
            map.put("layout/item_ble_device_0", Integer.valueOf(C0726R.layout.item_ble_device));
            map.put("layout/item_conversation_history_0", Integer.valueOf(C0726R.layout.item_conversation_history));
            map.put("layout/item_conversation_left_0", Integer.valueOf(C0726R.layout.item_conversation_left));
            map.put("layout/item_conversation_right_0", Integer.valueOf(C0726R.layout.item_conversation_right));
            map.put("layout/item_feedback_binding_0", Integer.valueOf(C0726R.layout.item_feedback_binding));
            map.put("layout/item_folder_select_layout_0", Integer.valueOf(C0726R.layout.item_folder_select_layout));
            map.put("layout/item_home_ai_chat_0", Integer.valueOf(C0726R.layout.item_home_ai_chat));
            map.put("layout/item_membership_package_binding_0", Integer.valueOf(C0726R.layout.item_membership_package_binding));
            map.put("layout/item_msg_0", Integer.valueOf(C0726R.layout.item_msg));
            map.put("layout/item_record_img_tag_0", Integer.valueOf(C0726R.layout.item_record_img_tag));
            map.put("layout/item_transcribe_0", Integer.valueOf(C0726R.layout.item_transcribe));
            map.put("layout/item_transcribe_floating_0", Integer.valueOf(C0726R.layout.item_transcribe_floating));
            map.put("layout/item_vip_price_0", Integer.valueOf(C0726R.layout.item_vip_price));
            map.put("layout/view_conversation_record_0", Integer.valueOf(C0726R.layout.view_conversation_record));
        }
    }
}
