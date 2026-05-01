package com.aivox.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Network;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.aivox.base.C0874R;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.download.DownloadUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.GlassFile;
import com.aivox.common.model.GlassState;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.aivox.common.p003db.maneger.GlassImageDbManager;
import com.aivox.common.util.WifiConnector;
import com.blankj.utilcode.util.GsonUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.DateUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class SyncManager {
    private static final String TAG = "SyncManager";
    private final AudioService audioService;
    private final Context context;
    private String currentDownloadPath;
    private DownloadUtil currentDownloadUtil;
    private GlassFile currentDownloadingFile;
    private long lastUpdateTime;
    private final SyncListener listener;
    private final String uid;
    private WifiConnector wifiConnector;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final AtomicBoolean isCancelled = new AtomicBoolean(false);
    private final AtomicInteger downloadFailureCount = new AtomicInteger(0);
    private final AtomicInteger processFileIndex = new AtomicInteger(0);
    private final List<String> deleteList = Collections.synchronizedList(new ArrayList());
    private boolean isWifiConnected = false;
    private GlassState glassState = new GlassState();
    private SyncState currentState = SyncState.IDLE;
    private int fileCount = 0;
    private final GlassImageDbManager glassImageDbManager = GlassImageDbManager.getInstance(AppApplication.getIns().getDaoSession());

    public interface SyncListener {
        void onDownloadProgress(int i);

        void onFileCountUpdate(int i);

        void onImageListUpdate();

        void onSyncFinished(String str, boolean z);

        void onSyncProgressText(String str);

        void onSyncStateChanged(SyncState syncState);
    }

    public enum SyncState {
        IDLE,
        CONNECTING_WIFI,
        FETCHING_FILE_LIST,
        DOWNLOADING_FILES,
        DELETING_FILES
    }

    public GlassState getGlassState() {
        return this.glassState;
    }

    public SyncManager(Context context, String str, SyncListener syncListener) {
        this.context = context.getApplicationContext();
        this.uid = str;
        this.listener = syncListener;
        this.audioService = new AudioService(context);
    }

    public void startSync() {
        if (this.currentState != SyncState.IDLE) {
            LogUtil.m339i(TAG, "Attempt to start sync in non-IDLE state: " + this.currentState);
            return;
        }
        this.isCancelled.set(false);
        updateSyncState(SyncState.CONNECTING_WIFI);
        this.deleteList.clear();
        this.downloadFailureCount.set(0);
        this.processFileIndex.set(0);
    }

    public void cancelSync() {
        if (this.currentState == SyncState.IDLE) {
            return;
        }
        LogUtil.m335d(TAG, "Cancelling sync...");
        this.isCancelled.set(true);
        this.disposables.clear();
        if (this.currentDownloadUtil != null) {
            StringBuilder sb = new StringBuilder("Cancelling active file download: ");
            GlassFile glassFile = this.currentDownloadingFile;
            LogUtil.m335d(TAG, sb.append(glassFile != null ? glassFile.getName() : "unknown").toString());
            try {
                this.currentDownloadUtil.destroy();
                if (this.currentDownloadPath != null && this.currentDownloadingFile != null) {
                    File file = new File(this.currentDownloadPath, this.currentDownloadingFile.getName());
                    if (file.exists()) {
                        FileUtils.deleteFile(file.getPath());
                    }
                }
            } catch (Exception e) {
                LogUtil.m337e(TAG, "Error cancelling download:" + e);
            }
            this.currentDownloadUtil = null;
            this.currentDownloadingFile = null;
        }
        if (!this.deleteList.isEmpty() && this.isWifiConnected) {
            LogUtil.m339i(TAG, "Cancel requested, but executing delete for " + this.deleteList.size() + " downloaded files.");
            updateSyncState(SyncState.DELETING_FILES);
            glassDeleteFile();
            return;
        }
        shutdownSync(this.context.getString(C0874R.string.cancel), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdownSync(String str, boolean z) {
        LogUtil.m335d(TAG, "shutdownSync: " + str);
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.cleanup();
            this.wifiConnector = null;
        }
        this.isWifiConnected = false;
        this.disposables.clear();
        updateSyncState(SyncState.IDLE);
        this.glassState = new GlassState();
        SyncListener syncListener = this.listener;
        if (syncListener != null) {
            syncListener.onSyncFinished(str, z);
        }
    }

    public void setWifiInfo(String str, String str2) {
        LogUtil.m335d(TAG, "Received WiFi info: " + str + ", " + str2);
        if (this.currentState == SyncState.CONNECTING_WIFI) {
            connectWifi(str, str2);
        }
    }

    public void setFileApi(String str) {
        this.glassState.setFileApi(str);
        if ((this.currentState == SyncState.CONNECTING_WIFI || this.currentState == SyncState.FETCHING_FILE_LIST) && this.isWifiConnected && !TextUtils.isEmpty(this.glassState.getFileApi())) {
            glassGetFileList(this.glassState.getFileApi());
        }
    }

    public void setIp(String str) {
        this.glassState.setIp(str);
    }

    public SyncState getCurrentState() {
        return this.currentState;
    }

    public boolean isSyncing() {
        return this.currentState != SyncState.IDLE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSyncState(SyncState syncState) {
        this.currentState = syncState;
        SyncListener syncListener = this.listener;
        if (syncListener != null) {
            syncListener.onSyncStateChanged(syncState);
        }
    }

    private void connectWifi(String str, String str2) {
        LogUtil.m335d(TAG, "Connecting to WiFi: " + str);
        if (this.wifiConnector == null) {
            this.wifiConnector = new WifiConnector(this.context);
        }
        this.wifiConnector.setWifiConnectionListener(new C08391());
        this.wifiConnector.connectToWifi(str, str2);
    }

    /* JADX INFO: renamed from: com.aivox.app.util.SyncManager$1 */
    class C08391 implements WifiConnector.WifiConnectionListener {
        C08391() {
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnecting(String str) {
            if (SyncManager.this.listener != null) {
                SyncManager.this.listener.onSyncProgressText(SyncManager.this.context.getString(C0874R.string.connecting_wifi));
            }
            SyncManager.this.disposables.add(Completable.timer(30L, TimeUnit.SECONDS, Schedulers.m1898io()).observeOn(Schedulers.m1898io()).subscribe(new Action() { // from class: com.aivox.app.util.SyncManager$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2385lambda$onWifiConnecting$0$comaivoxapputilSyncManager$1();
                }
            }));
        }

        /* JADX INFO: renamed from: lambda$onWifiConnecting$0$com-aivox-app-util-SyncManager$1, reason: not valid java name */
        /* synthetic */ void m2385lambda$onWifiConnecting$0$comaivoxapputilSyncManager$1() throws Exception {
            if (SyncManager.this.isWifiConnected || SyncManager.this.currentState != SyncState.CONNECTING_WIFI) {
                return;
            }
            LogUtil.m337e(SyncManager.TAG, "WiFi连接超时");
            SyncManager syncManager = SyncManager.this;
            syncManager.shutdownSync(syncManager.context.getString(C0874R.string.wifi_connection_timeout), false);
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnected(String str, Network network) {
            SyncManager.this.isWifiConnected = true;
            LogUtil.m335d(SyncManager.TAG, "WiFi已连接");
            if (SyncManager.this.currentState == SyncState.CONNECTING_WIFI) {
                if (TextUtils.isEmpty(SyncManager.this.glassState.getFileApi())) {
                    SyncManager.this.updateSyncState(SyncState.FETCHING_FILE_LIST);
                } else {
                    SyncManager syncManager = SyncManager.this;
                    syncManager.glassGetFileList(syncManager.glassState.getFileApi());
                }
            }
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnectionFailed(String str, String str2) {
            LogUtil.m337e(SyncManager.TAG, "WiFi连接失败: " + str2);
            if (SyncManager.this.currentState == SyncState.CONNECTING_WIFI || SyncManager.this.currentState == SyncState.FETCHING_FILE_LIST) {
                SyncManager syncManager = SyncManager.this;
                syncManager.shutdownSync(syncManager.context.getString(C0874R.string.wifi_connection_failed), false);
            }
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiDisconnected(String str, String str2) {
            LogUtil.m337e(SyncManager.TAG, "WiFi已断开: " + str2);
            SyncManager.this.isWifiConnected = false;
            if (SyncManager.this.currentState != SyncState.IDLE) {
                SyncManager syncManager = SyncManager.this;
                syncManager.shutdownSync(syncManager.context.getString(C0874R.string.wifi_disconnected), false);
            }
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiEnabledRequired() {
            LogUtil.m335d(SyncManager.TAG, "WiFi已启用");
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onPermissionsRequired(List<String> list) {
            SyncManager.this.isWifiConnected = false;
            LogUtil.m335d(SyncManager.TAG, "权限不足");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void glassGetFileList(String str) {
        if (this.isCancelled.get()) {
            return;
        }
        updateSyncState(SyncState.FETCHING_FILE_LIST);
        this.disposables.add(this.audioService.glassApi(str).subscribeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m2381lambda$glassGetFileList$1$comaivoxapputilSyncManager((String) obj);
            }
        }).observeOn(Schedulers.m1898io()).subscribe(new Action() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2382lambda$glassGetFileList$2$comaivoxapputilSyncManager();
            }
        }, new Consumer() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2383lambda$glassGetFileList$3$comaivoxapputilSyncManager((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$glassGetFileList$1$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ CompletableSource m2381lambda$glassGetFileList$1$comaivoxapputilSyncManager(String str) throws Exception {
        if (this.isCancelled.get()) {
            return Completable.complete();
        }
        LogUtil.m339i(TAG, "glassApi result:" + str);
        JsonObject asJsonObject = JsonParser.parseString(str).getAsJsonObject();
        if (asJsonObject.get("files").getAsJsonArray().size() == 0) {
            return Completable.complete();
        }
        List list = (List) GsonUtils.fromJson(asJsonObject.get("files").toString(), new TypeToken<List<GlassFile>>() { // from class: com.aivox.app.util.SyncManager.2
        }.getType());
        this.fileCount = list.size();
        this.processFileIndex.set(0);
        updateSyncState(SyncState.DOWNLOADING_FILES);
        return Observable.fromIterable(list).delay(100L, TimeUnit.MILLISECONDS).concatMapCompletable(new Function() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.processFileDownload((GlassFile) obj);
            }
        }).doOnComplete(new Action() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2380lambda$glassGetFileList$0$comaivoxapputilSyncManager();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$glassGetFileList$0$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2380lambda$glassGetFileList$0$comaivoxapputilSyncManager() throws Exception {
        if (this.isCancelled.get()) {
            return;
        }
        LogUtil.m335d(TAG, "所有下载任务处理完毕，开始执行删除...");
        updateSyncState(SyncState.DELETING_FILES);
        glassDeleteFile();
    }

    /* JADX INFO: renamed from: lambda$glassGetFileList$2$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2382lambda$glassGetFileList$2$comaivoxapputilSyncManager() throws Exception {
        if (this.fileCount == 0) {
            shutdownSync(this.context.getString(C0874R.string.audio_sync_success), true);
        }
    }

    /* JADX INFO: renamed from: lambda$glassGetFileList$3$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2383lambda$glassGetFileList$3$comaivoxapputilSyncManager(Throwable th) throws Exception {
        if (this.isCancelled.get()) {
            return;
        }
        LogUtil.m337e(TAG, "glassApi error:" + th.getLocalizedMessage());
        shutdownSync(this.context.getString(C0874R.string.audio_sync_fail), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Completable processFileDownload(final GlassFile glassFile) {
        if (this.isCancelled.get()) {
            return Completable.complete();
        }
        this.processFileIndex.incrementAndGet();
        return Completable.create(new CompletableOnSubscribe() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda7
            @Override // io.reactivex.CompletableOnSubscribe
            public final void subscribe(CompletableEmitter completableEmitter) throws Exception {
                this.f$0.m2384lambda$processFileDownload$4$comaivoxapputilSyncManager(glassFile, completableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$processFileDownload$4$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2384lambda$processFileDownload$4$comaivoxapputilSyncManager(GlassFile glassFile, CompletableEmitter completableEmitter) throws Exception {
        if (this.isCancelled.get()) {
            completableEmitter.onComplete();
            return;
        }
        try {
            String url = glassFile.getUrl();
            String str = !url.startsWith("http://") ? "http://" + this.glassState.getIp() + url : url;
            String appPath = glassFile.getName().endsWith(".jpg") ? FileUtils.getAppPath(this.context, "glassImage") : glassFile.getName().endsWith(".mp4") ? FileUtils.getAppPath(this.context, "glassVideo") : FileUtils.getAppPath(this.context, "glassOther");
            File file = new File(appPath, glassFile.getName());
            if (file.exists()) {
                LogUtil.m335d(TAG, "文件已存在，删除: " + file.getName());
                FileUtils.deleteFile(file.getPath());
            }
            if (this.currentDownloadUtil == null) {
                this.currentDownloadUtil = new DownloadUtil();
            }
            this.currentDownloadingFile = glassFile;
            this.currentDownloadPath = appPath;
            this.currentDownloadUtil.download(str, appPath, glassFile.getName(), new C08413(completableEmitter, url, str, appPath));
        } catch (Exception e) {
            LogUtil.m337e(TAG, "processFileDownload 内部错误:" + e);
            this.downloadFailureCount.incrementAndGet();
            completableEmitter.onComplete();
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.util.SyncManager$3 */
    class C08413 implements DownloadUtil.OnDownloadListener {
        final /* synthetic */ CompletableEmitter val$emitter;
        final /* synthetic */ String val$finalPath;
        final /* synthetic */ String val$finalUrl;
        final /* synthetic */ String val$relativeUrl;

        C08413(CompletableEmitter completableEmitter, String str, String str2, String str3) {
            this.val$emitter = completableEmitter;
            this.val$relativeUrl = str;
            this.val$finalUrl = str2;
            this.val$finalPath = str3;
        }

        @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
        public void onDownloadSuccess(File file) {
            if (SyncManager.this.isCancelled.get()) {
                FileUtils.deleteFile(file.getPath());
                this.val$emitter.onComplete();
                return;
            }
            LogUtil.m335d(SyncManager.TAG, "下载成功: " + file.getAbsolutePath());
            try {
                try {
                    if (file.getAbsolutePath().endsWith(".jpg")) {
                        SyncManager.this.insertImageToDb(file);
                    } else if (file.getAbsolutePath().endsWith(".mp4")) {
                        SyncManager.this.takeVideoCover(file);
                    }
                    SyncManager.this.deleteList.add(this.val$relativeUrl);
                    this.val$emitter.onComplete();
                } catch (Exception e) {
                    LogUtil.m337e(SyncManager.TAG, "处理文件失败:" + e);
                    SyncManager.this.downloadFailureCount.incrementAndGet();
                    FileUtils.deleteFile(file.getPath());
                    this.val$emitter.onComplete();
                }
            } finally {
                SyncManager.this.currentDownloadingFile = null;
            }
        }

        @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
        public void onDownloading(long j, long j2) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - SyncManager.this.lastUpdateTime > 200) {
                SyncManager.this.lastUpdateTime = jCurrentTimeMillis;
                final int i = (int) ((j * 100) / j2);
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.aivox.app.util.SyncManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2386lambda$onDownloading$0$comaivoxapputilSyncManager$3(i);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onDownloading$0$com-aivox-app-util-SyncManager$3, reason: not valid java name */
        /* synthetic */ void m2386lambda$onDownloading$0$comaivoxapputilSyncManager$3(int i) {
            if (SyncManager.this.listener == null || SyncManager.this.isCancelled.get()) {
                return;
            }
            SyncManager.this.listener.onDownloadProgress(i);
            SyncManager.this.listener.onSyncProgressText(SyncManager.this.context.getString(C0874R.string.importing) + " " + SyncManager.this.processFileIndex.get() + "/" + SyncManager.this.fileCount);
        }

        @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
        public void onDownloadFailed(Exception exc) {
            if (!SyncManager.this.isCancelled.get()) {
                LogUtil.m337e(SyncManager.TAG, "下载失败: " + this.val$finalUrl);
                SyncManager.this.downloadFailureCount.incrementAndGet();
                File file = new File(this.val$finalPath);
                if (file.exists()) {
                    file.delete();
                }
            }
            SyncManager.this.currentDownloadingFile = null;
            this.val$emitter.onComplete();
        }
    }

    private void glassDeleteFile() {
        final int i = this.downloadFailureCount.get();
        final boolean z = this.isCancelled.get();
        if (!this.deleteList.isEmpty()) {
            this.disposables.add(this.audioService.glassDeleteApi("http://" + this.glassState.getIp() + "/api/glass/file-delete", (String[]) this.deleteList.toArray(new String[0])).subscribeOn(Schedulers.m1898io()).observeOn(Schedulers.m1898io()).subscribe(new Consumer() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2378lambda$glassDeleteFile$5$comaivoxapputilSyncManager(z, i, (String) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.util.SyncManager$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2379lambda$glassDeleteFile$6$comaivoxapputilSyncManager(z, (Throwable) obj);
                }
            }));
        } else if (z) {
            shutdownSync(this.context.getString(C0874R.string.cancel), false);
        } else if (i > 0) {
            shutdownSync(this.context.getString(C0874R.string.audio_sync_fail), false);
        } else {
            shutdownSync(this.context.getString(C0874R.string.audio_sync_success), true);
        }
    }

    /* JADX INFO: renamed from: lambda$glassDeleteFile$5$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2378lambda$glassDeleteFile$5$comaivoxapputilSyncManager(boolean z, int i, String str) throws Exception {
        LogUtil.m339i(TAG, "glassDeleteFile result:" + str);
        if (z) {
            shutdownSync(this.context.getString(C0874R.string.cancel), false);
            return;
        }
        if (i > 0) {
            SyncListener syncListener = this.listener;
            if (syncListener != null) {
                syncListener.onFileCountUpdate(i);
            }
            shutdownSync(this.context.getString(C0874R.string.audio_sync_fail), false);
            return;
        }
        SyncListener syncListener2 = this.listener;
        if (syncListener2 != null) {
            syncListener2.onFileCountUpdate(0);
        }
        shutdownSync(this.context.getString(C0874R.string.audio_sync_success), true);
    }

    /* JADX INFO: renamed from: lambda$glassDeleteFile$6$com-aivox-app-util-SyncManager, reason: not valid java name */
    /* synthetic */ void m2379lambda$glassDeleteFile$6$comaivoxapputilSyncManager(boolean z, Throwable th) throws Exception {
        LogUtil.m337e(TAG, "glassDeleteFile error:" + th);
        if (z) {
            shutdownSync(this.context.getString(C0874R.string.cancel), false);
            return;
        }
        SyncListener syncListener = this.listener;
        if (syncListener != null) {
            syncListener.onFileCountUpdate(0);
        }
        shutdownSync(this.context.getString(C0874R.string.audio_sync_fail), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void insertImageToDb(java.io.File r7) {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "insertImageToDb:"
            r0.<init>(r1)
            java.lang.String r1 = r7.getAbsolutePath()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "SyncManager"
            com.aivox.base.util.LogUtil.m335d(r1, r0)
            java.lang.String r0 = r7.getName()
            int r1 = r0.length()     // Catch: java.lang.Exception -> L4d
            r2 = 8
            if (r1 < r2) goto L4a
            r1 = 0
            java.lang.String r1 = r0.substring(r1, r2)     // Catch: java.lang.Exception -> L4d
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat     // Catch: java.lang.Exception -> L4d
            java.lang.String r3 = com.aivox.base.util.DateUtil.YYYYMMDD     // Catch: java.lang.Exception -> L4d
            java.util.Locale r4 = java.util.Locale.getDefault()     // Catch: java.lang.Exception -> L4d
            r2.<init>(r3, r4)     // Catch: java.lang.Exception -> L4d
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch: java.lang.Exception -> L4d
            java.lang.String r4 = com.aivox.base.util.DateUtil.YYYYMMDD2     // Catch: java.lang.Exception -> L4d
            java.util.Locale r5 = java.util.Locale.getDefault()     // Catch: java.lang.Exception -> L4d
            r3.<init>(r4, r5)     // Catch: java.lang.Exception -> L4d
            java.util.Date r1 = r2.parse(r1)     // Catch: java.lang.Exception -> L4d
            if (r1 == 0) goto L4a
            java.lang.String r1 = r3.format(r1)     // Catch: java.lang.Exception -> L4d
            goto L61
        L4a:
            java.lang.String r1 = ""
            goto L61
        L4d:
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.lang.String r2 = com.aivox.base.util.DateUtil.YYYYMMDD2
            java.util.Locale r3 = java.util.Locale.getDefault()
            r1.<init>(r2, r3)
            java.util.Date r2 = new java.util.Date
            r2.<init>()
            java.lang.String r1 = r1.format(r2)
        L61:
            com.aivox.common.db.entity.GlassImageEntity r2 = new com.aivox.common.db.entity.GlassImageEntity
            r2.<init>()
            java.lang.String r3 = r6.uid
            r2.setUid(r3)
            java.lang.String r7 = r7.getAbsolutePath()
            r2.setImagePath(r7)
            r2.setImageName(r0)
            r2.setCreateTime(r1)
            com.aivox.common.db.maneger.GlassImageDbManager r7 = r6.glassImageDbManager
            r7.insert(r2)
            com.aivox.app.util.SyncManager$SyncListener r7 = r6.listener
            if (r7 == 0) goto L84
            r7.onImageListUpdate()
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.util.SyncManager.insertImageToDb(java.io.File):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeVideoCover(File file) {
        StringBuilder sb;
        LogUtil.m335d(TAG, "takeVideoCover:" + file.getAbsolutePath());
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L, 2);
                if (frameAtTime != null) {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getAbsolutePath().replace(".mp4", ".jpg")));
                    frameAtTime.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    frameAtTime.recycle();
                    GlassImageEntity glassImageEntity = new GlassImageEntity();
                    glassImageEntity.setUid(this.uid);
                    glassImageEntity.setImagePath(file.getAbsolutePath());
                    glassImageEntity.setImageName(file.getName());
                    String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
                    if (strExtractMetadata != null) {
                        glassImageEntity.setDuration(Long.parseLong(strExtractMetadata));
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.PURE_DATE_FORMAT, Locale.getDefault());
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(com.aivox.base.util.DateUtil.YYYYMMDD2, Locale.getDefault());
                    try {
                        if (file.getName().contains(PunctuationConst.UNDERLINE)) {
                            glassImageEntity.setCreateTime(simpleDateFormat2.format(Long.valueOf(simpleDateFormat.parse(file.getName().split("/")[0]).getTime())));
                        } else {
                            glassImageEntity.setCreateTime(simpleDateFormat2.format(new Date()));
                        }
                    } catch (ParseException unused) {
                        glassImageEntity.setCreateTime(simpleDateFormat2.format(new Date()));
                    }
                    this.glassImageDbManager.insert(glassImageEntity);
                    SyncListener syncListener = this.listener;
                    if (syncListener != null) {
                        syncListener.onImageListUpdate();
                    }
                }
                try {
                    mediaMetadataRetriever.release();
                } catch (IOException e) {
                    e = e;
                    sb = new StringBuilder("retriever release error:");
                    LogUtil.m337e(TAG, sb.append(e).toString());
                }
            } catch (Exception e2) {
                LogUtil.m337e(TAG, "takeVideoCover error:" + e2);
                try {
                    mediaMetadataRetriever.release();
                } catch (IOException e3) {
                    e = e3;
                    sb = new StringBuilder("retriever release error:");
                    LogUtil.m337e(TAG, sb.append(e).toString());
                }
            }
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (IOException e4) {
                LogUtil.m337e(TAG, "retriever release error:" + e4);
            }
            throw th;
        }
    }

    public void release() {
        cancelSync();
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.cleanup();
            this.wifiConnector = null;
        }
    }
}
