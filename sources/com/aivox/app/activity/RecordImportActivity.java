package com.aivox.app.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.RecordImportAdapter;
import com.aivox.app.databinding.ActivityRecordImportBinding;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SDCardUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.PowerfulEditText;
import com.aivox.common_ui.SimpleDividerItemDecoration;
import com.blankj.utilcode.util.KeyboardUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecordImportActivity extends BaseFragmentActivity {
    private RecordImportAdapter adapter;
    private ActivityRecordImportBinding mBinding;
    private final List<AudioInfoBean> list = new ArrayList();
    private final List<AudioInfoBean> searchList = new ArrayList();

    static /* synthetic */ void lambda$initView$0(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$1(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$2(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$3(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$4(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$6(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$8(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$initView$9(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityRecordImportBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_record_import);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        this.mBinding.recyclerview.setLayoutManager(linearLayoutManager);
        this.mBinding.recyclerview.addItemDecoration(new SimpleDividerItemDecoration(this, getResources().getDrawable(C0874R.color.home_bgcolor), 5));
        this.adapter = new RecordImportAdapter(this, C0726R.layout.item_audioimport_list_layout);
        this.mBinding.recyclerview.setAdapter(this.adapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda4
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m2179lambda$initView$12$comaivoxappactivityRecordImportActivity(view2, i);
            }
        });
        this.mBinding.etSearchImport.addTextChangedListener(new TextWatcher() { // from class: com.aivox.app.activity.RecordImportActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                RecordImportActivity.this.refresh(editable.toString());
            }
        });
        this.mBinding.etSearchImport.setOnRightClickListener(new PowerfulEditText.OnRightClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda5
            @Override // com.aivox.common_ui.PowerfulEditText.OnRightClickListener
            public final void onClick(EditText editText) {
                this.f$0.m2180lambda$initView$13$comaivoxappactivityRecordImportActivity(editText);
            }
        });
        this.mBinding.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2181lambda$initView$14$comaivoxappactivityRecordImportActivity(view2);
            }
        });
        this.mBinding.tvSearchCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2182lambda$initView$15$comaivoxappactivityRecordImportActivity(view2);
            }
        });
        this.mBinding.tvDoImport.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2183lambda$initView$16$comaivoxappactivityRecordImportActivity(view2);
            }
        });
        requestPermission();
    }

    /* JADX INFO: renamed from: lambda$initView$12$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2179lambda$initView$12$comaivoxappactivityRecordImportActivity(View view2, final int i) {
        final String localPath;
        if (TextUtils.isEmpty(this.mBinding.etSearchImport.getText().toString())) {
            localPath = this.list.get(i).getLocalPath();
        } else {
            localPath = this.searchList.get(i).getLocalPath();
        }
        if (SDCardUtil.getFreeKbs() < FileUtils.getFileSizeKb(localPath)) {
            DialogUtils.showMsgNoBtn(this, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.storage_full), true);
            return;
        }
        if (DataHandle.getIns().isVip()) {
            if (FileUtils.getFileSizeKb(localPath) >= 512000) {
                DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_size_for_vip), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda9
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                        RecordImportActivity.lambda$initView$0(context, dialogBuilder, dialog, i2, i3, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda11
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                        RecordImportActivity.lambda$initView$1(context, dialogBuilder, dialog, i2, i3, editText);
                    }
                }, false, false);
                return;
            } else if (FileUtils.getAudioFileVoiceTime(localPath) >= 18000) {
                DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_for_vip), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda12
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                        RecordImportActivity.lambda$initView$2(context, dialogBuilder, dialog, i2, i3, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda13
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                        RecordImportActivity.lambda$initView$3(context, dialogBuilder, dialog, i2, i3, editText);
                    }
                }, false, false);
                return;
            }
        } else if (FileUtils.getFileSizeKb(localPath) >= 307200) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_size_for_free), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda14
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    RecordImportActivity.lambda$initView$4(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda15
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2184lambda$initView$5$comaivoxappactivityRecordImportActivity(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, true, false, C0874R.string.know_and_continue, C0874R.string.join_vip);
            return;
        } else if (FileUtils.getAudioFileVoiceTime(localPath) >= 3600) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_for_free), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda16
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    RecordImportActivity.lambda$initView$6(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda17
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2185lambda$initView$7$comaivoxappactivityRecordImportActivity(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, true, false, C0874R.string.know_and_continue, C0874R.string.join_vip);
            return;
        }
        if (FileUtils.getAudioFileVoiceTime(localPath) <= 5) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.tip_audio_length_short_5s), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    RecordImportActivity.lambda$initView$8(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    RecordImportActivity.lambda$initView$9(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, false, false);
        } else {
            DialogUtils.showLoadingDialog(this.context);
            new Thread(new Runnable() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2178lambda$initView$11$comaivoxappactivityRecordImportActivity(localPath, i);
                }
            }).start();
        }
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2184lambda$initView$5$comaivoxappactivityRecordImportActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this, false);
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2185lambda$initView$7$comaivoxappactivityRecordImportActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this, false);
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2178lambda$initView$11$comaivoxappactivityRecordImportActivity(String str, final int i) {
        final String strCopyAndRenameFile = FileUtils.copyAndRenameFile(str, FileUtils.getAppPath(this.context, FileUtils.Audio_From_Other), System.currentTimeMillis() + "");
        if (TextUtils.isEmpty(this.mBinding.etSearchImport.getText().toString())) {
            this.list.get(i).setLocalPath(strCopyAndRenameFile);
        } else {
            this.searchList.get(i).setLocalPath(strCopyAndRenameFile);
        }
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2177lambda$initView$10$comaivoxappactivityRecordImportActivity(strCopyAndRenameFile, i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2177lambda$initView$10$comaivoxappactivityRecordImportActivity(String str, int i) {
        if (BaseStringUtil.isNotEmpty(str)) {
            DialogUtils.hideLoadingDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", this.list.get(i));
            ARouterUtils.startWithContext(this.context, RecordAction.RECORD_UPLOAD, bundle);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$13$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2180lambda$initView$13$comaivoxappactivityRecordImportActivity(EditText editText) {
        this.mBinding.etSearchImport.setText("");
        refresh("");
        KeyboardUtils.hideSoftInput(this.mBinding.etSearchImport);
    }

    /* JADX INFO: renamed from: lambda$initView$14$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2181lambda$initView$14$comaivoxappactivityRecordImportActivity(View view2) {
        toSearch(true);
    }

    /* JADX INFO: renamed from: lambda$initView$15$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2182lambda$initView$15$comaivoxappactivityRecordImportActivity(View view2) {
        toSearch(false);
    }

    /* JADX INFO: renamed from: lambda$initView$16$com-aivox-app-activity-RecordImportActivity, reason: not valid java name */
    /* synthetic */ void m2183lambda$initView$16$comaivoxappactivityRecordImportActivity(View view2) {
        this.mBinding.svImportNotice.setVisibility(8);
        this.mBinding.ivSearch.setVisibility(0);
    }

    private void requestPermission() {
        PermissionUtils.getIns(this, new PermissionCallback() { // from class: com.aivox.app.activity.RecordImportActivity.2
            @Override // com.aivox.base.permission.PermissionCallback
            public void granted(boolean z) {
                if (z) {
                    RecordImportActivity.this.initData();
                } else {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
                }
            }

            @Override // com.aivox.base.permission.PermissionCallback
            public void requestError(Throwable th) {
                LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
                ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
                BaseAppUtils.openSettingView(RecordImportActivity.this.context);
            }
        }).request("android.permission.READ_MEDIA_AUDIO");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData() {
        this.list.clear();
        this.list.addAll(getMusics());
        if (this.list.size() > 1) {
            this.list.sort(new Comparator() { // from class: com.aivox.app.activity.RecordImportActivity$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Long.compare(DateUtil.getTimestampFromDate(DateUtil.utc2Local(((AudioInfoBean) obj2).getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.getTimestampFromDate(DateUtil.utc2Local(((AudioInfoBean) obj).getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS));
                }
            });
        }
        this.adapter.setmDate(this.list);
        this.adapter.isShowFooter(false);
        if (this.list.size() == 0) {
            this.adapter.isShowNull(true);
            this.mBinding.llEmpty.setVisibility(0);
        }
        LogUtil.m338i("总数_initData:" + this.adapter.getItemCount());
    }

    private void toSearch(boolean z) {
        if (z) {
            this.mBinding.llSearchLayout.setVisibility(0);
            this.mBinding.ivSearch.setVisibility(8);
            this.mBinding.tvSearchCancel.setVisibility(0);
            KeyboardUtils.showSoftInput(this.mBinding.etSearchImport);
            this.searchList.clear();
            this.adapter.setmDate(this.searchList);
            return;
        }
        this.mBinding.llSearchLayout.setVisibility(4);
        this.mBinding.ivSearch.setVisibility(0);
        this.mBinding.tvSearchCancel.setVisibility(8);
        this.mBinding.etSearchImport.setText("");
        KeyboardUtils.hideSoftInput(this.mBinding.etSearchImport);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh(String str) {
        if (TextUtils.isEmpty(str)) {
            this.adapter.setmDate(this.list);
        } else {
            this.searchList.clear();
            for (int i = 0; i < this.list.size(); i++) {
                if (this.list.get(i).getTitle() != null && this.list.get(i).getTitle().contains(str)) {
                    this.searchList.add(this.list.get(i));
                }
            }
            this.adapter.setmDate(this.searchList);
        }
        LogUtil.m338i("总数_refresh:" + this.adapter.getItemCount());
    }

    public List<AudioInfoBean> getMusics() {
        ContentResolver contentResolver = getContentResolver();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, "title_key");
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_display_name"));
                String string3 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("album"));
                String string4 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("artist"));
                String string5 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"));
                long j = cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_size"));
                int i = cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow(TypedValues.TransitionType.S_DURATION)) / 1000;
                LogUtil.m338i("----0----" + string);
                LogUtil.m338i("name:" + string2 + ";album:" + string3 + ";artist:" + string4 + ";size:" + j + ";duration:" + i + ";type:" + string5);
                if (i > 5 && string5 != null && !string5.contains("amr-wb")) {
                    arrayList.add(new AudioInfoBean(string2, new File(string).lastModified(), i, string, ""));
                }
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return arrayList;
    }
}
