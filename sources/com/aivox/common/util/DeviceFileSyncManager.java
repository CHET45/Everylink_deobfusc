package com.aivox.common.util;

import android.content.Context;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.UploadAudioBean;
import com.aivox.common.model.UploadAudioInfo;
import com.aivox.common.p003db.DeviceFileEntityDao;
import com.aivox.common.p003db.entity.DeviceFileEntity;
import com.aivox.common.p003db.maneger.DeviceFileDbManager;
import com.blankj.utilcode.util.CollectionUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFileSyncManager {
    private DeviceFileDbManager mDbManager;
    private Disposable mDis;
    private final List<DeviceFileEntity> mFileList;
    private boolean mIsSyncing;

    private DeviceFileSyncManager() {
        this.mIsSyncing = false;
        this.mFileList = new ArrayList();
        this.mDbManager = DeviceFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
    }

    private static final class MInstanceHolder {
        static final DeviceFileSyncManager instance = new DeviceFileSyncManager();

        private MInstanceHolder() {
        }
    }

    public static DeviceFileSyncManager getInstance() {
        return MInstanceHolder.instance;
    }

    public void startSync(Context context) {
        this.mDbManager = DeviceFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        if (this.mIsSyncing) {
            return;
        }
        this.mIsSyncing = true;
        this.mFileList.clear();
        this.mFileList.addAll(this.mDbManager.queryFileList(DeviceFileEntityDao.Properties.Uid.m1944eq((String) SPUtil.get(SPUtil.USER_ID, "0")), DeviceFileEntityDao.Properties.Synced.m1944eq(false), DeviceFileEntityDao.Properties.LocalPath.isNotNull()));
        LogUtil.m336e("待同步文件数量：" + this.mFileList.size());
        if (CollectionUtils.isEmpty(this.mFileList)) {
            this.mIsSyncing = false;
        } else {
            doSync(context, true);
        }
    }

    private void doSync(final Context context, final boolean z) {
        this.mDis = new AudioService(context).addRecordFromLocal(initUploadAudioBean(this.mFileList.get(0))).subscribe(new Consumer() { // from class: com.aivox.common.util.DeviceFileSyncManager$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2470lambda$doSync$0$comaivoxcommonutilDeviceFileSyncManager(z, context, (Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.common.util.DeviceFileSyncManager$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2471lambda$doSync$1$comaivoxcommonutilDeviceFileSyncManager(context, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doSync$0$com-aivox-common-util-DeviceFileSyncManager, reason: not valid java name */
    /* synthetic */ void m2470lambda$doSync$0$comaivoxcommonutilDeviceFileSyncManager(boolean z, Context context, Integer num) throws Exception {
        if (z) {
            EventBus.getDefault().post(new EventBean(500));
        }
        this.mDbManager.updateSyncStatus(true, DeviceFileEntityDao.Properties.f204Id.m1944eq(this.mFileList.get(0).getId()), new WhereCondition[0]);
        this.mFileList.remove(0);
        if (CollectionUtils.isNotEmpty(this.mFileList)) {
            doSync(context, false);
        } else {
            this.mIsSyncing = false;
        }
    }

    /* JADX INFO: renamed from: lambda$doSync$1$com-aivox-common-util-DeviceFileSyncManager, reason: not valid java name */
    /* synthetic */ void m2471lambda$doSync$1$comaivoxcommonutilDeviceFileSyncManager(Context context, Throwable th) throws Exception {
        AppUtils.checkHttpCode(context);
        this.mIsSyncing = false;
    }

    private UploadAudioBean initUploadAudioBean(DeviceFileEntity deviceFileEntity) {
        UploadAudioInfo uploadAudioInfo = new UploadAudioInfo();
        uploadAudioInfo.setAudioUrl("");
        uploadAudioInfo.setLocalPath(deviceFileEntity.getLocalPath());
        uploadAudioInfo.setAudioLength(FileUtils.getFileSizeKb(deviceFileEntity.getLocalPath()));
        uploadAudioInfo.setAudioTimeDuration((int) FileUtils.getAudioFileVoiceTime(deviceFileEntity.getLocalPath()));
        UploadAudioBean uploadAudioBean = new UploadAudioBean();
        uploadAudioBean.setTitle(deviceFileEntity.getName());
        uploadAudioBean.setIsTop(0);
        uploadAudioBean.setUploadAudioInfo(uploadAudioInfo);
        uploadAudioBean.setIsDevice(true);
        return uploadAudioBean;
    }

    public void release() {
        this.mIsSyncing = false;
        Disposable disposable = this.mDis;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
