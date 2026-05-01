package com.aivox.set.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.img.imageloader.FileSizeUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.util.DataCleanManager;
import com.aivox.common_ui.C1034R;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityStorageSpaceBinding;

/* JADX INFO: loaded from: classes.dex */
public class StorageSpaceActivity extends BaseFragmentActivity implements OnViewClickListener {
    private ActivityStorageSpaceBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityStorageSpaceBinding activityStorageSpaceBinding = (ActivityStorageSpaceBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_storage_space);
        this.mBinding = activityStorageSpaceBinding;
        activityStorageSpaceBinding.setClickListener(this);
        reSetSize();
    }

    private void reSetSize() {
        try {
            double fileOrFilesSize = FileSizeUtil.getFileOrFilesSize(FileUtils.getAudioFilePath(this), 3) + FileSizeUtil.getFileOrFilesSize(FileUtils.getMemoFilePath(this), 3) + FileSizeUtil.getFileOrFilesSize(FileUtils.getAudioOtherFilePath(this), 3);
            this.mBinding.tvStorageSizeCache.setText(String.format("%.2f MB", Double.valueOf(DataCleanManager.getTotalCacheSize(this, 3))));
            this.mBinding.tvStorageSizeAudio.setText(String.format("%.2f MB", Double.valueOf(fileOrFilesSize)));
            boolean z = true;
            setAble(this.mBinding.tvClearCache, DataCleanManager.getTotalCacheSize(this, 3) != 0.0d);
            TextView textView = this.mBinding.tvClearAudio;
            if (fileOrFilesSize == 0.0d) {
                z = false;
            }
            setAble(textView, z);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAble(TextView textView, boolean z) {
        Resources resources;
        int i;
        textView.setClickable(z);
        if (z) {
            resources = getResources();
            i = C1034R.drawable.bg_highlight_c40;
        } else {
            resources = getResources();
            i = C1034R.drawable.bg_gary_c40;
        }
        textView.setBackground(resources.getDrawable(i));
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        int id = view2.getId();
        if (id == this.mBinding.tvClearCache.getId()) {
            DataCleanManager.clearAllCache(this);
            reSetSize();
        } else if (id == this.mBinding.tvClearAudio.getId()) {
            DialogUtils.showDeleteDialog(this, Integer.valueOf(C0874R.string.warning), Integer.valueOf(C0874R.string.set_storage_data_clean_noti), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.set.activity.StorageSpaceActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2555lambda$onViewClick$0$comaivoxsetactivityStorageSpaceActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, true, C0874R.string.set_storage_clean, C0874R.string.cancel, 0);
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$0$com-aivox-set-activity-StorageSpaceActivity, reason: not valid java name */
    /* synthetic */ void m2555lambda$onViewClick$0$comaivoxsetactivityStorageSpaceActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        FileUtils.deleteFile(FileUtils.getAudioFilePath(this));
        FileUtils.deleteFile(FileUtils.getMemoFilePath(this));
        FileUtils.deleteFile(FileUtils.getAudioOtherFilePath(this));
        reSetSize();
    }
}
