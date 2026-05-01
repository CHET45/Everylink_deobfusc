package com.aivox.app.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityRecordingBinding;
import com.aivox.app.fragment.RecordingTranscribeFragment;
import com.aivox.app.util.TencentTtsManager;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.notify.RecordNotification;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.AudioUtils;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.BleAudioDataUpManager;
import com.aivox.common.ble.BleDataManager;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.BleDeviceAutoLinkHelper;
import com.aivox.common.ble.service.BleServiceUtils;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.ble.service.SppDeviceAutoLinkHelper;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.listener.RecordListener;
import com.aivox.common.model.AiTranscribe;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.LocalTransBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.model.UploadAudioBean;
import com.aivox.common.model.UploadAudioInfo;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.parse.WifiSendManagerForJson;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.socket.WebSocketManager;
import com.aivox.common.speech2text.CommonTransManager;
import com.aivox.common.speech2text.ICommonTransCallback;
import com.aivox.common.speech2text.MP3RecorderTencent;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.translate.SeqTransModel;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.GlideEngine;
import com.aivox.common.util.PhoneTtsManager;
import com.aivox.common.util.tts.TtsManager;
import com.aivox.common.util.whisper.WhisperService;
import com.aivox.common_ui.BottomEditDialogView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.greenrobot.eventbus.EventBus;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/* JADX INFO: loaded from: classes.dex */
public class RecordingActivity extends BaseFragmentActivity implements RecordListener, OnViewClickListener, ICommonTransCallback {
    private AudioManager audioManager;
    private BluetoothProfile bluetoothProfile;
    private Bundle bundle;
    private ServiceConnection conn;
    private String debugPath;
    private long duration;
    private String filePath;
    private boolean isInBackground;
    private boolean isNoNet;
    private boolean isRecordInit;
    private boolean isSocketBreak;
    private boolean isTransTimeExhaust;
    private boolean isUpLimit;
    private LocalFileDbManager localFIleDbManager;
    private CommonTransManager localTransManager;
    private int mAudioId;
    private AudioService mAudioService;
    private ActivityRecordingBinding mBinding;
    private RecordingHandler mHandler;
    private PhoneTtsManager mMicrosoftTtsManager;
    private TtsManager mTencentTtsManager;
    private int maxPicCount;
    private long pauseTime;
    private boolean picInsertEnable;
    private int realIndex;
    private RecordNotification recordNotification;
    private int recordStatus;
    private long resumeTime;
    private long sessionStartTime;
    private boolean showBreakNotify;
    private long startTime;
    private RecordingTranscribeFragment transcribeFragment;
    private UserInfo userInfo;
    private long videoLength;
    private WhisperService whisperService;
    private final List<Transcribe> transcribeList = new ArrayList();
    private int mTimeNumber = 0;
    private boolean mFirst = true;
    private boolean isFirstStart = false;
    private int currentId = -1;
    private int currentIndex = -1;
    private int totalImgCount = 0;
    private boolean isTranslatingTimeOut = false;
    private int isTrans = -1;
    private int clickInsertIndex = -1;
    private int mTranscribeType = 0;
    private int mFrom = MyEnum.TRANSLATE_LANGUAGE.EN.type;
    private int mTo = MyEnum.TRANSLATE_LANGUAGE.ZH.type;
    private boolean isBreakSaving = false;
    private boolean isBreakBySppBreak = false;
    private Transcribe nTranscribe = new Transcribe();
    private int lastED = 0;
    private String mAudioTitle = "";
    private boolean mIsTtsOn = true;
    private boolean mLocalTrans = false;
    private int localRecordState = 0;
    private boolean isWhisperReady = false;
    private boolean isWhisperStartRecord = false;
    private int currentImgCount = -1;
    private int nonUploadImgIndex = -1;
    private final List<AiTranscribe.ImageListBean> imgList = new ArrayList();
    private final List<AiTranscribe> aiTranscribeList = new ArrayList();
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private boolean isEarPhoneMode = false;
    private final boolean isUnLimitEarphone = false;
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    static /* synthetic */ void lambda$doSave$4(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$doSave$5(Throwable th) throws Exception {
    }

    static /* synthetic */ void lambda$onDestroy$25(Object obj) throws Exception {
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onComplete(String str) {
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onPrepare() {
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordDelete() {
    }

    static /* synthetic */ int access$2210(RecordingActivity recordingActivity) {
        int i = recordingActivity.currentImgCount;
        recordingActivity.currentImgCount = i - 1;
        return i;
    }

    private class RecordingHandler extends Handler {
        private RecordingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            LogUtil.m335d("recording__", "msg:" + message.what);
            if (message.what == 1001) {
                if (RecordingActivity.this.recordStatus == 1) {
                    RecordingActivity.this.mTimeNumber = ((int) (System.currentTimeMillis() - RecordingActivity.this.sessionStartTime)) / 1000;
                    RecordingActivity.this.mBinding.tvRecordTime.setText(DateUtil.numberToTime(RecordingActivity.this.mTimeNumber));
                    if (!BaseAppUtils.isKeyguardLocked(RecordingActivity.this.context)) {
                        RecordingActivity.this.recordNotification.update(DateUtil.numberToTime(RecordingActivity.this.mTimeNumber), RecordingActivity.this);
                    }
                }
                if (RecordingActivity.this.isSocketBreak && RecordingActivity.this.mTimeNumber % 5 == 0) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.network_connecting));
                }
                if (BaseAppUtils.getMainTransType(RecordingActivity.this.mTranscribeType) != 1 || RecordingActivity.this.mTimeNumber != 18000) {
                    RecordingActivity.this.mHandler.sendEmptyMessageDelayed(1001, 1000L);
                } else {
                    RecordingActivity.this.isTranslatingTimeOut = false;
                    RecordingActivity.this.toAutoSave();
                }
            }
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityRecordingBinding activityRecordingBinding = (ActivityRecordingBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_recording);
        this.mBinding = activityRecordingBinding;
        activityRecordingBinding.setClickListener(this);
        this.audioManager = (AudioManager) this.context.getSystemService("audio");
        this.mAudioService = new AudioService(this.context);
        SPUtil.put(SPUtil.HAS_BREAK_SAVE_AUDIO, false);
        getWindow().setFlags(128, 128);
        this.isFirstStart = true;
        SQLiteDataBaseManager sQLiteDataBaseManager = new SQLiteDataBaseManager(this);
        this.localFIleDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.userInfo = sQLiteDataBaseManager.getUserInfo();
        this.maxPicCount = DataHandle.getIns().getFunctionBean().getImageInsertLimit().intValue();
        this.picInsertEnable = DataHandle.getIns().getFunctionBean().getImageInsert().booleanValue();
        boolean zBooleanValue = ((Boolean) SPUtil.get(SPUtil.IS_TRANS_TIME_OUT, true)).booleanValue();
        this.isTransTimeExhaust = zBooleanValue;
        if (zBooleanValue) {
            refreshNotify(getString(C0874R.string.trans_time_exhaust));
            this.mBinding.tvOnlyRecording.setVisibility(0);
        }
        this.bundle = getIntent().getExtras();
        LogUtil.m338i("bundle===" + this.bundle);
        this.mTranscribeType = this.bundle.getInt(Constant.TRANSCRIBE_TYPE, 0);
        this.mFrom = this.bundle.getInt("from");
        this.mTo = this.bundle.getInt("to");
        if (this.mFrom == MyEnum.TRANSLATE_LANGUAGE.YUE_HK.type) {
            this.mFrom = MyEnum.TRANSLATE_LANGUAGE.YUE.type;
        }
        if (this.mTo == MyEnum.TRANSLATE_LANGUAGE.YUE_HK.type) {
            this.mTo = MyEnum.TRANSLATE_LANGUAGE.YUE.type;
        }
        this.mLocalTrans = DataHandle.getIns().isVip() && this.mFrom >= 10000;
        LogUtil.m336e("mTranscribeType===" + this.mTranscribeType);
        DataHandle.getIns().setCurTransType(this.mTranscribeType);
        if (this.recordStatus == 0) {
            this.mHandler = new RecordingHandler();
        }
        if (BleBtService.getInstance().isGlass()) {
            this.audioManager.startBluetoothSco();
        }
        if (DataHandle.getIns().isHasConnectedBle(true)) {
            this.isEarPhoneMode = true;
            BleServiceUtils.getInstance().setRecording(true);
        } else if (DataHandle.getIns().isHasConnectedSpp()) {
            int i = this.mTranscribeType;
            this.isEarPhoneMode = (i == 101 || i == 104 || i == 107) && !CommonServiceUtils.getInstance().getConnectedDeviceName().equals(MyEnum.DEVICE_MODEL.TRANSLATER_T3.name);
        } else {
            this.isEarPhoneMode = false;
        }
        this.mBinding.langSwitchView.setType(this.mTranscribeType);
        this.mBinding.langSwitchView.setLanguage(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mFrom).display, MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mTo).display);
        WifiSendManagerForJson.getInstance().setBaseData(this.mTranscribeType, this.isEarPhoneMode ? 2 : 0, "");
        if (this.mLocalTrans) {
            this.mBinding.ivInsertImg.setAlpha(0.4f);
            this.mBinding.ivAddMark.setAlpha(0.4f);
            loadModel();
            if (DataHandle.getIns().hasConnectedBluetooth(true)) {
                this.bluetoothAdapter.getProfileProxy(this, new BluetoothProfile.ServiceListener() { // from class: com.aivox.app.activity.RecordingActivity.1
                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public void onServiceDisconnected(int i2) {
                    }

                    @Override // android.bluetooth.BluetoothProfile.ServiceListener
                    public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
                        if (i2 == 2) {
                            RecordingActivity.this.bluetoothProfile = bluetoothProfile;
                        }
                    }
                }, 2);
            }
            if (this.conn == null) {
                this.conn = new ServiceConnection() { // from class: com.aivox.app.activity.RecordingActivity.2
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        RecordingActivity.this.whisperService = ((WhisperService.WhisperBinder) iBinder).getService();
                        RecordingActivity.this.loadModel();
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName componentName) {
                        RecordingActivity.this.whisperService = null;
                    }
                };
            }
            bindService(new Intent(getApplication(), (Class<?>) WhisperService.class), this.conn, 1);
            this.localRecordState = 0;
        }
        initFragment();
        initTtsManager();
        DataHandle.getIns().setTranscribeWsCanClosed(false);
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:autoStart");
        checkPermissionAndDoRecord();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void initLocalAudio() {
        if (this.whisperService != null && this.isWhisperReady && this.isWhisperStartRecord) {
            if (DataHandle.getIns().hasConnectedBluetooth(true) && ActivityCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") == 0) {
                Iterator<BluetoothDevice> it = this.bluetoothAdapter.getBondedDevices().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (this.bluetoothProfile.getConnectionState(it.next()) == 2) {
                        this.audioManager.startBluetoothSco();
                        break;
                    }
                }
            }
            createAudioInfo();
        }
    }

    private void initFragment() {
        this.transcribeFragment = RecordingTranscribeFragment.getInstance();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.add(this.mBinding.flContainer.getId(), this.transcribeFragment);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onProgress(final String str, final boolean z, final String str2, int i, int i2, final boolean z2) {
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2227lambda$onProgress$3$comaivoxappactivityRecordingActivity(str2, str, z, z2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:94:? A[RETURN, SYNTHETIC] */
    /* JADX INFO: renamed from: lambda$onProgress$3$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    /* synthetic */ void m2227lambda$onProgress$3$comaivoxappactivityRecordingActivity(java.lang.String r20, final java.lang.String r21, boolean r22, final boolean r23) {
        /*
            Method dump skipped, instruction units count: 698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.activity.RecordingActivity.m2227lambda$onProgress$3$comaivoxappactivityRecordingActivity(java.lang.String, java.lang.String, boolean, boolean):void");
    }

    /* JADX INFO: renamed from: lambda$onProgress$2$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2226lambda$onProgress$2$comaivoxappactivityRecordingActivity(String str, boolean z, final boolean z2, int i, String str2, int i2, String str3) {
        if (!z2) {
            sendPushText(i + "", ((int) (System.currentTimeMillis() - this.sessionStartTime)) + "", str, i, str2, z);
            this.lastED = (int) (System.currentTimeMillis() - this.sessionStartTime);
        }
        for (int i3 = 0; i3 < this.transcribeList.size(); i3++) {
            if (str3.equals(this.transcribeList.get(i3).getAudioId())) {
                this.transcribeList.get(i3).setTranslate(str2);
            }
        }
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2225lambda$onProgress$1$comaivoxappactivityRecordingActivity(z2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onProgress$1$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2225lambda$onProgress$1$comaivoxappactivityRecordingActivity(boolean z) {
        if (isActivityShow(RecordingActivity.class)) {
            this.transcribeFragment.setTranscribeList(this.transcribeList);
        }
        if (BleBtService.getInstance().isGlass() && !z && this.mIsTtsOn) {
            int size = this.transcribeList.size() - 1;
            if (this.mTencentTtsManager != null) {
                final String translate = this.transcribeList.get(size).getTranslate();
                this.singleThreadExecutor.execute(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2224lambda$onProgress$0$comaivoxappactivityRecordingActivity(translate);
                    }
                });
            } else {
                this.mMicrosoftTtsManager.speak(this.transcribeList.get(size).getTranslate());
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onProgress$0$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2224lambda$onProgress$0$comaivoxappactivityRecordingActivity(String str) {
        this.mTencentTtsManager.speak(str, false);
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onError(final String str, boolean z) {
        if (z) {
            runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    RecordingActivity.this.refreshNotify(str);
                    if (RecordingActivity.this.localTransManager != null) {
                        RecordingActivity.this.localTransManager.resumeAudio();
                    }
                }
            });
        }
    }

    private int listContainIndex(List<Transcribe> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAudioId().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private void refreshFontSize() {
        if (this.transcribeList.isEmpty()) {
            return;
        }
        this.transcribeFragment.setTranscribeList(this.transcribeList);
    }

    private void clickFontSizeSmallBtn() {
        refreshFontSize();
        this.transcribeFragment.setFontSize(13.0f);
    }

    private void clickFontSizeBigBtn() {
        refreshFontSize();
        this.transcribeFragment.setFontSize(17.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doStart() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:doStart");
        LogUtil.m334d("state=" + RecordingStateMachine.get().getStageNow());
        if (this.mLocalTrans) {
            changeLocalRecord(true);
        } else if (RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.RECORD_PAUSED || RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.IDLE) {
            checkPermissionAndDoRecord();
        }
    }

    private void doPause() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:doPause");
        LogUtil.m334d("state=" + RecordingStateMachine.get().getStageNow());
        if (this.mLocalTrans) {
            changeLocalRecord(false);
        } else if (RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.RECORD_ING) {
            recordPause();
        }
    }

    private void doSave() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:doSave");
        if (this.recordStatus == 0) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.record_not_start), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
            return;
        }
        if (this.mTimeNumber < 5) {
            doPause();
            DialogUtils.showDeleteDialog(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.audio_length_too_short), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda6
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2222lambda$doSave$6$comaivoxappactivityRecordingActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2223lambda$doSave$7$comaivoxappactivityRecordingActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, false, C0874R.string.delete, C0874R.string.tips_continue, 0);
            return;
        }
        if (this.mLocalTrans) {
            showSaveDialog();
            return;
        }
        recordPause();
        if (!NetUtil.isConnected(this)) {
            setRecordStatus(3);
            RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "RECORD_PAUSED");
            doBack();
        } else {
            showSaveDialog();
            LogUtil.m336e("-----------文件------->" + this.filePath);
        }
    }

    /* JADX INFO: renamed from: lambda$doSave$6$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2222lambda$doSave$6$comaivoxappactivityRecordingActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        setRecordStatus(3);
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "RECORD_PAUSED");
        CommonTransManager commonTransManager = this.localTransManager;
        if (commonTransManager != null) {
            commonTransManager.stopAudio();
        }
        FileUtils.localFileDelete(this.filePath);
        this.localFIleDbManager.deleteWhere(LocalFileEntityDao.Properties.LocalPath.m1944eq(this.filePath), LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()));
        this.mAudioService.deleteFile(DataHandle.getIns().getFileId() + "").doFinally(new Action() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda44
            @Override // io.reactivex.functions.Action
            public final void run() {
                this.f$0.doBack();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                RecordingActivity.lambda$doSave$4(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                RecordingActivity.lambda$doSave$5((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doSave$7$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2223lambda$doSave$7$comaivoxappactivityRecordingActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toAutoSave() {
        if (this.mLocalTrans) {
            changeLocalRecord(false);
            if (this.localRecordState == 2) {
                setRecordStatus(3);
                this.localRecordState = 1;
                return;
            }
        }
        if (this.recordStatus == 0) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.record_not_start), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
            return;
        }
        recordPause();
        DialogUtils.showLoadingDialog(this.context, "", false);
        new Thread(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2241lambda$toAutoSave$8$comaivoxappactivityRecordingActivity();
            }
        }).start();
        LogUtil.m336e("-----------文件------->" + this.filePath);
    }

    /* JADX INFO: renamed from: lambda$toAutoSave$8$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2241lambda$toAutoSave$8$comaivoxappactivityRecordingActivity() {
        try {
            Thread.sleep(100L);
            recordSave();
        } catch (InterruptedException e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTrans() {
        LogUtil.m338i("recordStatus=" + this.recordStatus);
        int i = this.recordStatus;
        if (i == 0 || i == 3) {
            if (i == 0) {
                this.startTime = DateUtil.getDateTimeNow();
            }
            setRecordStatus(1);
            if (!this.isRecordInit) {
                recordInit();
                this.isRecordInit = true;
            }
            sendDeviceCmd();
            LogUtil.m338i("ws_recordStatus:" + this.recordStatus);
            recordStart();
            return;
        }
        DialogUtils.showLoadingDialog(this, "", false);
        sendDeviceCmd();
        sendRecordResumeMsg();
    }

    private void checkPermissionAndDoRecord() {
        if (isDestroyed()) {
            return;
        }
        PermissionUtils.getIns(this, new C07984()).request("android.permission.RECORD_AUDIO", "android.permission.BLUETOOTH_CONNECT");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordingActivity$4 */
    class C07984 implements PermissionCallback {
        C07984() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                LogUtil.m338i("已获得录音权限");
                if (RecordingActivity.this.mLocalTrans) {
                    RecordingActivity.this.isWhisperStartRecord = true;
                    RecordingActivity.this.initLocalAudio();
                    return;
                } else {
                    WebSocketManager.startService(RecordingActivity.this);
                    RecordingActivity.this.startTrans();
                    return;
                }
            }
            DialogUtils.showDialogWithBtnIds(RecordingActivity.this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.no_microphone_permissions), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$4$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2242lambda$granted$0$comaivoxappactivityRecordingActivity$4(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$4$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    BaseAppUtils.openSettingView(context);
                }
            }, true, false, C0874R.string.cancel, C0874R.string.sure);
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-RecordingActivity$4, reason: not valid java name */
        /* synthetic */ void m2242lambda$granted$0$comaivoxappactivityRecordingActivity$4(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            RecordingActivity.this.finish();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.no_microphone_permissions));
            BaseAppUtils.openSettingView(RecordingActivity.this.context);
        }
    }

    private void recordInit() {
        int i;
        LogUtil.m336e("-----第一次开始----->");
        this.filePath = FileUtils.getAudioFilePath(this);
        File file = new File(this.filePath);
        if (!file.exists() && !file.mkdirs()) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.create_file_fail), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
            return;
        }
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:recordInit");
        this.filePath = FileUtils.getAudioFilePath(this) + DateUtil.getDateFromTimestamp(this.startTime, DateUtil.YYYY_MMDD_HHMM_SS) + "." + AudioType.WAV.getType();
        this.debugPath = FileUtils.getLogFilePath(this);
        boolean z = this.isEarPhoneMode;
        if ((z && this.mTranscribeType == 2) || (i = this.mTranscribeType) == 1) {
            this.localTransManager = new CommonTransManager(this, true);
        } else if (!z || i == 102 || i == 105) {
            this.localTransManager = new CommonTransManager(this, true);
        } else {
            this.localTransManager = new CommonTransManager((getTransType(true) ? MyEnum.TRANS_TYPE.TENCENT : MyEnum.TRANS_TYPE.NULL).type, (getTransType(false) ? MyEnum.TRANS_TYPE.TENCENT : MyEnum.TRANS_TYPE.NULL).type, this, true);
        }
    }

    private void sendDeviceCmd() {
        int i;
        boolean z = this.isEarPhoneMode;
        if ((z && this.mTranscribeType == 2) || (i = this.mTranscribeType) == 1 || !z || i == 102 || i == 105) {
            return;
        }
        if (i == 103 || i == 106) {
            CommonServiceUtils.getInstance().sendData(Constant.CmdUpReadyDown);
        } else if (i == 107) {
            CommonServiceUtils.getInstance().sendData(Constant.CmdUpReadyToPhoneRecord);
            BleAudioDataUpManager.getInstance().startEncoder(this.filePath);
        } else {
            CommonServiceUtils.getInstance().sendData(Constant.CmdUpReadyClean);
        }
    }

    public void setRecordStatus(int i) {
        this.recordStatus = i;
        SPUtil.put(SPUtil.RECORD_STATE, Integer.valueOf(i));
    }

    private void sendRecordStartMsg() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_ING, "ACK_PHONE_RECORD_START");
        SPUtil.put("canSendStream", true);
        DataHandle.getIns().setFileId(0);
        DataHandle.getIns().setFileName("");
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdRecordStart(this.mTranscribeType, this.mFrom, this.mTo, this.isEarPhoneMode ? 2 : 0, ""), DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_START);
    }

    private void sendRecordResumeMsg() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_RESUME_ING, "RECORD_RESUME_ING");
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_ING, "ACK_PHONE_RECORD_RESUME");
        LogUtil.m338i("ws_recordStatus:" + this.recordStatus);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.resumeTime = jCurrentTimeMillis;
        this.sessionStartTime += jCurrentTimeMillis - this.pauseTime;
        recordResume();
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordStart() {
        int i;
        if (!this.isEarPhoneMode || (i = this.mTranscribeType) == 105 || i == 102 || i == 107 || i == 1 || i == 2) {
            AudioUtils.getIns(this).reqAudioFocus();
            AudioUtils.getIns(this).changeToBluetoothSpeaker();
        }
        LogUtil.m338i("mFirst=" + this.mFirst);
        try {
            Constant.AudioStreamType audioStreamType = Constant.AudioStreamType.NORMAL;
            if (this.isEarPhoneMode) {
                int i2 = this.mTranscribeType;
                if (i2 == 105 || i2 == 102) {
                    audioStreamType = Constant.AudioStreamType.LEFT;
                } else if (i2 == 106 || i2 == 103) {
                    audioStreamType = Constant.AudioStreamType.RIGHT;
                } else if (DataHandle.getIns().isHasConnectedBle(true)) {
                    int i3 = this.mTranscribeType;
                    if (i3 == 1 || i3 == 2) {
                        audioStreamType = Constant.AudioStreamType.LEFT;
                    } else if (MyEnum.DEVICE_MODEL.getEarphone(CommonServiceUtils.getInstance().getConnectedDeviceName()).type != MyEnum.DEVICE_MODEL.OWS_YT09.type) {
                        audioStreamType = Constant.AudioStreamType.SINGLE_MIX;
                    }
                }
            }
            BleDataManager.getInstance().createFile(this.filePath, audioStreamType);
            this.localTransManager.startRecording(new MP3RecorderTencent(this.filePath));
            if (this.mFirst) {
                this.sessionStartTime = System.currentTimeMillis();
                this.recordNotification = new RecordNotification(this, RecordingActivity.class, this.bundle);
                EventBus.getDefault().post(new EventBean(101));
                this.mHandler.removeMessages(1001);
                this.mHandler.sendEmptyMessage(1001);
                this.mFirst = false;
            }
            setViewGone();
        } catch (Exception e) {
            LogUtil.m336e("录音异常：" + e.getLocalizedMessage());
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "recordStartError" + e);
            recordError();
            DialogUtils.showDialogWithDefBtnAndSingleListener(this, Integer.valueOf(C0874R.string.reminder), e.toString(), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda29
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i4, int i5, EditText editText) {
                    this.f$0.m2229lambda$recordStart$9$comaivoxappactivityRecordingActivity(context, dialogBuilder, dialog, i4, i5, editText);
                }
            }, false, false);
        }
    }

    /* JADX INFO: renamed from: lambda$recordStart$9$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2229lambda$recordStart$9$comaivoxappactivityRecordingActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doBack();
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordError() {
        LogUtil.m336e("--recordError--");
        setRecordStatus(0);
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordPause() {
        LogUtil.m338i("-----recordPause----" + this.recordStatus);
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:recordPause");
        if (this.recordStatus != 2) {
            this.pauseTime = System.currentTimeMillis();
            setRecordStatus(2);
            setViewGone();
            CommonTransManager commonTransManager = this.localTransManager;
            if (commonTransManager != null) {
                commonTransManager.pauseAudio();
            }
            DialogUtils.hideLoadingDialog();
            LogUtil.m338i("ws_recordStatus:" + this.recordStatus);
            this.pauseTime = System.currentTimeMillis();
        }
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordResume() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "RecordingActivity:recordResume");
        LogUtil.m338i("-----recordResume----");
        setRecordStatus(1);
        setViewGone();
        CommonTransManager commonTransManager = this.localTransManager;
        if (commonTransManager != null && !this.isTransTimeExhaust) {
            commonTransManager.resumeAudio();
        }
        EventBus.getDefault().post(new EventBean(102));
        DialogUtils.hideLoadingDialog();
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordStopOrSave() {
        RecordNotification recordNotification = this.recordNotification;
        if (recordNotification != null) {
            recordNotification.cancel();
        }
        this.mBinding.audioOverIv.setClickable(false);
        toEditRecord();
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordSave() {
        if (this.mLocalTrans) {
            changeLocalRecord(false);
            if (!this.transcribeList.isEmpty()) {
                if (this.transcribeList.size() > 1) {
                    this.aiTranscribeList.add(this.transcribeList.get(r2.size() - 2).toEntity(this.sessionStartTime));
                }
                List<AiTranscribe> list = this.aiTranscribeList;
                List<Transcribe> list2 = this.transcribeList;
                list.add(list2.get(list2.size() - 1).toEntity(this.sessionStartTime));
            }
            saveAllAsrContent();
            return;
        }
        DataHandle.getIns().setTranscribeWsCanClosed(true);
        if (!this.isBreakSaving && !RegexUtil.isFileName(this.mAudioTitle)) {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.record_info_dialog_meeting_subject_error));
        } else {
            EventBus.getDefault().post(new EventBean(104));
        }
    }

    @Override // com.aivox.common.listener.RecordListener
    public void recordPropertiesModify() {
        this.pauseTime = System.currentTimeMillis();
        recordPause();
    }

    private void setViewGone() {
        showRecordingGif(this.recordStatus == 1);
        if (this.recordStatus == 1) {
            this.mBinding.audioStartIv.setVisibility(8);
            this.mBinding.audioStopIv.setVisibility(0);
            this.mBinding.tvOnPause.setVisibility(8);
            CenterInside centerInside = new CenterInside();
            Glide.with((FragmentActivity) this).load(Integer.valueOf(C1034R.drawable.ic_anim_rec)).optionalTransform(centerInside).optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(centerInside)).into(this.mBinding.ivLeading);
            return;
        }
        this.mBinding.audioStartIv.setVisibility(0);
        this.mBinding.audioStopIv.setVisibility(8);
        this.mBinding.tvOnPause.setVisibility(0);
        this.mBinding.ivLeading.setImageResource(C1034R.drawable.bg_gray_round_light);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        LogUtil.m335d("recording__onEventMainThread", "msg:" + eventBean.getFrom());
        switch (eventBean.getFrom()) {
            case 46:
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkReconnect");
                this.isNoNet = false;
                refreshNotify();
                break;
            case 55:
                DialogUtils.showLoadingDialog(this, "保存录音中...");
                break;
            case Constant.EVENT.CONNECT_SERVICE_ERROR /* 59 */:
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkDisconnect");
                if (!NetUtil.isConnected(this)) {
                    this.isNoNet = true;
                    refreshNotify();
                }
                break;
            case Constant.EVENT.RECORDING_BREAK_BY_PHONE /* 71 */:
            case 304:
                if (isTwoWay()) {
                    this.isBreakSaving = true;
                    SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "recordingBreakByPhone");
                    toAutoSave();
                    break;
                }
                break;
            case Constant.EVENT.WS_CONNECT_ERROR /* 73 */:
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "wsConnectError");
                this.isSocketBreak = true;
                CommonTransManager commonTransManager = this.localTransManager;
                if (commonTransManager != null) {
                    commonTransManager.stopRecognize();
                }
                break;
            case 76:
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "transTimeOut");
                if (!this.isTransTimeExhaust) {
                    DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_exhaust), null, null, false, true, C0874R.string.know_and_continue, C0874R.string.know_and_continue);
                }
                SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, true);
                this.isTransTimeExhaust = true;
                refreshNotify(getString(C0874R.string.trans_failure_time_out));
                break;
            case 81:
                if (this.mLocalTrans) {
                    this.isWhisperReady = true;
                    initLocalAudio();
                }
                break;
            case 104:
                if (this.recordStatus != 3) {
                    DialogUtils.showLoadingDialog(this, "", false);
                    SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "stopRecording");
                    CommonTransManager commonTransManager2 = this.localTransManager;
                    if (commonTransManager2 != null) {
                        commonTransManager2.stopAudio();
                    }
                    this.mFirst = true;
                    setRecordStatus(3);
                } else if (this.isSocketBreak) {
                    EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_STOP));
                }
                break;
            case 106:
                recordSave();
                break;
            case 107:
                doStart();
                break;
            case Constant.EVENT.BLE_CONNECTED /* 300 */:
                if (this.isEarPhoneMode) {
                    DialogUtils.hideLoadingDialog();
                    doStart();
                    if (this.mTranscribeType == 107) {
                        CommonServiceUtils.getInstance().sendData(Constant.CmdUpReadyToPhoneRecord);
                        BleAudioDataUpManager.getInstance().startEncoder(this.filePath);
                    }
                    break;
                }
                break;
            case 301:
            case 308:
                if (this.isEarPhoneMode) {
                    this.isBreakSaving = true;
                    this.isBreakBySppBreak = true;
                    toAutoSave();
                    break;
                }
                break;
            case 306:
                if (this.isEarPhoneMode) {
                    doPause();
                    DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.earphone_spp_broken), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda35
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                            this.f$0.m231x96b64232(context, dialogBuilder, dialog, i, i2, editText);
                        }
                    }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda36
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                            this.f$0.m232x9e1b7751(context, dialogBuilder, dialog, i, i2, editText);
                        }
                    }, true, true, C0874R.string.earphone_spp_do_reconnect, C0874R.string.save);
                    if (this.mTranscribeType == 107) {
                        BleAudioDataUpManager.getInstance().stopAll();
                    }
                    break;
                }
                break;
            case 406:
                disconnectAnd2ReLogin();
                break;
            case DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_VERIFIED /* 30005 */:
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "wsConnectSuccess");
                this.isSocketBreak = false;
                if (this.isFirstStart) {
                    initWebSocket();
                    this.isFirstStart = false;
                    if (eventBean.getA() != 1) {
                        this.isTrans = 0;
                    }
                }
                break;
            case DeviceProtocol.MSG_ID_WIFI_JSON.ACK_CHECK_LIMIT /* 30009 */:
                LogUtil.m335d("ACK_CHECK_LIMIT", "isFirstStart:" + this.isFirstStart + eventBean.getA());
                DialogUtils.hideLoadingDialog();
                if (eventBean.getA() == 1) {
                    this.isUpLimit = false;
                    refreshNotify();
                    if (this.localTransManager != null && !this.isTransTimeExhaust) {
                        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "beginTransRecording");
                        SPUtil.put(SPUtil.ALI_TRANS_KEY + this.mFrom, SPUtil.get(SPUtil.ALI_TRANS_KEY, ""));
                        SPUtil.put(SPUtil.ALI_TRANS_TOKEN + this.mFrom, SPUtil.get(SPUtil.ALI_TRANS_TOKEN, ""));
                        this.localTransManager.startTrans(this.isUpLimit, this.mFrom, isTwoWayTrans() ? this.mTo : this.mFrom, this.debugPath, this);
                    } else {
                        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "justRecording");
                    }
                } else {
                    this.isUpLimit = true;
                    CommonTransManager commonTransManager3 = this.localTransManager;
                    if (commonTransManager3 != null) {
                        commonTransManager3.stopRecognize();
                    }
                    refreshNotify();
                }
                break;
            case DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_START /* 30021 */:
                DialogUtils.hideLoadingDialog();
                LogUtil.m338i("ws_recordStatus:" + this.recordStatus);
                this.mAudioTitle = DataHandle.getIns().getFileName();
                LocalFileEntity localFileEntity = new LocalFileEntity();
                localFileEntity.setLocalPath(this.filePath);
                localFileEntity.setVid(DataHandle.getIns().getFileId());
                localFileEntity.setTitle(DataHandle.getIns().getFileName());
                localFileEntity.setUid(this.userInfo.getUuid());
                localFileEntity.setIsBreak(true);
                this.localFIleDbManager.insertOrReplace(localFileEntity);
                break;
            case DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_STOP /* 30024 */:
                DialogUtils.hideLoadingDialog();
                DataHandle.getIns().setTranscribeWsCanClosed(true);
                recordStopOrSave();
                break;
        }
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$10$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m231x96b64232(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        if (BaseStringUtil.isEmpty((String) SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, "")) || !BleServiceUtils.getInstance().isBtOpen()) {
            return;
        }
        CommonServiceUtils.getInstance().disconnect();
        if (DataHandle.getIns().isHasConnectedBle(true)) {
            BleDeviceAutoLinkHelper.getInstance().startAutoLinkBle(this);
        } else {
            SppDeviceAutoLinkHelper.getInstance().startAutoLinkSpp(this);
        }
        DialogUtils.showLoadingDialog(this, getString(C0874R.string.earphone_spp_reconnectting));
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$11$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m232x9e1b7751(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doSave();
    }

    private void uploadImg(String str, int i) {
        if (FileUtils.isFileExist(str)) {
            CommonUploadManager.getInstance().startUpload(this.context, i, str, this.userInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new OnUploadListener() { // from class: com.aivox.app.activity.RecordingActivity.5
                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onProgress(int i2, long j, long j2, int i3) {
                    LogUtil.m338i("上传中:" + i2 + "  " + i3 + "% " + j + "/" + j2);
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onSuccess(int i2, String str2, String str3, long j) {
                    LogUtil.m338i("上传成功:" + i2 + "  " + str2 + "   " + str3);
                    RecordingActivity.access$2210(RecordingActivity.this);
                    RecordingActivity.this.sendInsertImg(str3, i2 + "", BaseStringUtil.bToKb(j));
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onFailure(int i2) {
                    if (RecordingActivity.this.currentImgCount > 0) {
                        RecordingActivity.access$2210(RecordingActivity.this);
                        if (RecordingActivity.this.currentImgCount == 0) {
                            RecordingActivity.this.clickInsertIndex = -1;
                        }
                    }
                    LogUtil.m338i("上传失败：" + i2);
                }
            }, Constant.TYPE_AUDIO_IMG);
        } else {
            LogUtil.m338i("文件不存在");
        }
    }

    private UploadAudioBean initUploadAudioBean(String str) {
        UploadAudioBean uploadAudioBean = new UploadAudioBean();
        uploadAudioBean.setTitle(this.mAudioTitle);
        UploadAudioInfo uploadAudioInfo = new UploadAudioInfo();
        uploadAudioInfo.setLocalPath(str);
        uploadAudioInfo.setAudioLength(FileUtils.getFileSizeKb(str));
        uploadAudioInfo.setAudioTimeDuration((int) FileUtils.getAudioFileVoiceTime(str));
        uploadAudioBean.setUploadAudioInfo(uploadAudioInfo);
        return uploadAudioBean;
    }

    private void toEditRecord() {
        this.duration = FileUtils.getAudioFileVoiceTime(this.filePath);
        this.videoLength = FileUtils.getFileSizeKb(this.filePath);
        long j = this.duration;
        if (j == 0) {
            j = this.mTimeNumber;
        }
        this.duration = j;
        if (currentAudioId() == 0) {
            reqAddRecord();
        } else {
            m2237lambda$reqEditRecord$22$comaivoxappactivityRecordingActivity(currentAudioId());
        }
    }

    private int currentAudioId() {
        return this.mLocalTrans ? Math.max(this.mAudioId, 0) : DataHandle.getIns().getFileId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqAddRecord() {
        DialogUtils.showLoadingDialog(this);
        this.mAudioService.addRecordFromLocal(initUploadAudioBean(this.filePath)).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.m227xe1480d09((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2231lambda$reqAddRecord$13$comaivoxappactivityRecordingActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqAddRecord$13$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2231lambda$reqAddRecord$13$comaivoxappactivityRecordingActivity(Throwable th) throws Exception {
        LogUtil.m338i("putAudio_thr:" + th.getLocalizedMessage());
        DialogUtils.hideLoadingDialog();
        this.mBinding.audioOverIv.setClickable(true);
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda8
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqAddRecord();
                }
            }, new AppUtils.ResponseCancelCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda9
                @Override // com.aivox.common.util.AppUtils.ResponseCancelCallback
                public final void cancelCallback() {
                    this.f$0.m2230lambda$reqAddRecord$12$comaivoxappactivityRecordingActivity();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$reqAddRecord$12$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2230lambda$reqAddRecord$12$comaivoxappactivityRecordingActivity() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "RECORD_PAUSED");
        doBack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getAudioInfoById, reason: merged with bridge method [inline-methods] */
    public void m227xe1480d09(final Integer num) {
        this.mAudioService.recordDetails(num.intValue()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m226xd9e2d7ea((AudioInfoBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m228xe8ad4228(num, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$14$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m226xd9e2d7ea(AudioInfoBean audioInfoBean) throws Exception {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "PULL_OSS_UPLOAD_PROGRESS");
        LocalFileEntity localFileEntity = new LocalFileEntity(audioInfoBean);
        localFileEntity.setIsBreak(false);
        this.localFIleDbManager.insertOrReplace(localFileEntity);
        doBack();
    }

    /* JADX INFO: renamed from: lambda$getAudioInfoById$16$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m228xe8ad4228(final Integer num, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda34
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m227xe1480d09(num);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqEditRecord, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m2237lambda$reqEditRecord$22$comaivoxappactivityRecordingActivity(final int i) {
        DialogUtils.showLoadingDialog(this.context);
        this.isTrans = !this.transcribeList.isEmpty() ? -1 : 0;
        if (this.mLocalTrans) {
            this.mAudioService.saveLocalRecordInfo(i, this.duration, this.videoLength, this.mAudioTitle, this.filePath, "", "", "").subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda24
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2232lambda$reqEditRecord$17$comaivoxappactivityRecordingActivity((AudioInfoBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda25
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2235lambda$reqEditRecord$20$comaivoxappactivityRecordingActivity(i, (Throwable) obj);
                }
            });
        } else {
            this.mAudioService.saveRecordInfo(i, this.mAudioTitle, "", "", this.duration, this.videoLength, this.filePath, this.mFrom).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda26
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2236lambda$reqEditRecord$21$comaivoxappactivityRecordingActivity((AudioInfoBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda27
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2239lambda$reqEditRecord$24$comaivoxappactivityRecordingActivity(i, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$17$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2232lambda$reqEditRecord$17$comaivoxappactivityRecordingActivity(AudioInfoBean audioInfoBean) throws Exception {
        this.mAudioId = audioInfoBean.getId();
        DialogUtils.hideLoadingDialog();
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "PULL_OSS_UPLOAD_PROGRESS");
        LocalFileEntity localFileEntity = new LocalFileEntity(audioInfoBean);
        localFileEntity.setIsBreak(false);
        this.localFIleDbManager.insertOrReplace(localFileEntity);
        if (audioInfoBean.getTitle().isEmpty() && audioInfoBean.getIsTrans() == 1) {
            DataHandle.getIns().setAiNamingAudioId(audioInfoBean.getId());
        }
        doBack();
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$20$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2235lambda$reqEditRecord$20$comaivoxappactivityRecordingActivity(final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        this.mBinding.audioOverIv.setClickable(true);
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda30
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2233lambda$reqEditRecord$18$comaivoxappactivityRecordingActivity(i);
                }
            }, new AppUtils.ResponseCancelCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda31
                @Override // com.aivox.common.util.AppUtils.ResponseCancelCallback
                public final void cancelCallback() {
                    this.f$0.m2234lambda$reqEditRecord$19$comaivoxappactivityRecordingActivity();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$19$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2234lambda$reqEditRecord$19$comaivoxappactivityRecordingActivity() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "RECORD_PAUSED");
        doBack();
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$21$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2236lambda$reqEditRecord$21$comaivoxappactivityRecordingActivity(AudioInfoBean audioInfoBean) throws Exception {
        this.mAudioId = audioInfoBean.getId();
        DialogUtils.hideLoadingDialog();
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "PULL_OSS_UPLOAD_PROGRESS");
        LocalFileEntity localFileEntity = new LocalFileEntity(audioInfoBean);
        localFileEntity.setIsBreak(false);
        this.localFIleDbManager.insertOrReplace(localFileEntity);
        if (audioInfoBean.getTitle().isEmpty() && audioInfoBean.getIsTrans() == 1) {
            DataHandle.getIns().setAiNamingAudioId(audioInfoBean.getId());
        }
        doBack();
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$24$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2239lambda$reqEditRecord$24$comaivoxappactivityRecordingActivity(final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        this.mBinding.audioOverIv.setClickable(true);
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda11
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2237lambda$reqEditRecord$22$comaivoxappactivityRecordingActivity(i);
                }
            }, new AppUtils.ResponseCancelCallback() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda22
                @Override // com.aivox.common.util.AppUtils.ResponseCancelCallback
                public final void cancelCallback() {
                    this.f$0.m2238lambda$reqEditRecord$23$comaivoxappactivityRecordingActivity();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$23$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2238lambda$reqEditRecord$23$comaivoxappactivityRecordingActivity() {
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.IDLE, "RECORD_PAUSED");
        doBack();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        LogUtil.m338i("Recording_onBackPressed");
        doBack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doBack() {
        LogUtil.m338i("Recording_doBack:" + this.recordStatus);
        RecordNotification recordNotification = this.recordNotification;
        if (recordNotification != null) {
            recordNotification.cancel();
        }
        int i = this.recordStatus;
        if (i == 1 || i == 2) {
            return;
        }
        if (this.isBreakSaving) {
            SPUtil.put(SPUtil.HAS_BREAK_SAVE_AUDIO, true);
            SPUtil.put(SPUtil.AUDIO_BREAK_BECAUSE_SPP_BREAK, Boolean.valueOf(this.isBreakBySppBreak));
            SPUtil.put(SPUtil.BREAK_AUDIO_TIMEOUT_TRANSLATING, Boolean.valueOf(this.isTranslatingTimeOut));
            SPUtil.put(SPUtil.BREAK_SAVE_AUDIO_ID, Integer.valueOf(DataHandle.getIns().getFileId()));
            EventBus.getDefault().postSticky(new EventBean(72, true));
        }
        this.mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().post(new EventBean(50));
        if (!this.isBreakSaving) {
            ARouterUtils.startWithActivity(this, MainAction.MAIN);
        }
        finishAndRemoveTask();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.m338i("recording_onNewIntent");
        setIntent(intent);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onCreate", true);
        super.onCreate(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onStart");
        super.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onResume");
        super.onResume();
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_popup_bar));
        this.isInBackground = false;
        refreshFontSize();
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("toSave", false)) {
            doSave();
        }
        LogUtil.m338i("Recording_onResume--" + extras);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        LogUtil.m338i("RecordingAct2_onPause:");
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onPause");
        if (this.isBreakSaving && !this.isInBackground) {
            toAutoSave();
        }
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_secondary));
        this.isInBackground = true;
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onStop");
        super.onStop();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "RecordingActivity:onDestroy");
        super.onDestroy();
        LogUtil.m338i("Recording_onDestroy" + WebSocketHandler.getInstance().getStatus());
        TtsManager ttsManager = this.mTencentTtsManager;
        if (ttsManager != null) {
            ttsManager.release();
        }
        PhoneTtsManager phoneTtsManager = this.mMicrosoftTtsManager;
        if (phoneTtsManager != null) {
            phoneTtsManager.releaseAll();
        }
        this.singleThreadExecutor.shutdownNow();
        WebSocketManager.stopService(this);
        SeqTransModel.getInstance().destroy();
        if (BaseStringUtil.isEmpty(this.mAudioTitle) && this.mAudioId != 0 && CollectionUtils.isNotEmpty(this.transcribeList)) {
            this.mAudioService.generateAiTitleById(this.mAudioId).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda37
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    RecordingActivity.lambda$onDestroy$25(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda38
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataHandle.getIns().setAiNamingAudioId(-1);
                }
            });
        }
        DataHandle.getIns().setFileId(0);
        DataHandle.getIns().setFileName("");
        if (WebSocketHandler.getInstance().getStatus() == ConnectStatus.Open && this.recordStatus == 1) {
            CommonTransManager commonTransManager = this.localTransManager;
            if (commonTransManager != null) {
                commonTransManager.stopAudio();
            }
            AppManager.getAppManager().finishActivity(ARouterUtils.getClass(MainAction.MAIN));
        }
        if (isUpTrans()) {
            AudioUtils.getIns(this).clearAudioFocus();
        }
        AudioManager audioManager = this.audioManager;
        if (audioManager != null) {
            audioManager.stopBluetoothSco();
            this.audioManager.setMode(0);
        }
        SPUtil.put(SPUtil.RECORD_STATE, 0);
        this.mHandler.removeCallbacksAndMessages(null);
        this.transcribeList.clear();
        if (this.isEarPhoneMode) {
            BleDataManager.getInstance().destroy();
        }
        BleAudioDataUpManager.getInstance().destroy();
        if (this.mTranscribeType == 107) {
            BleAudioDataUpManager.getInstance().stopAll();
        }
        WhisperService whisperService = this.whisperService;
        if (whisperService != null) {
            whisperService.release();
            this.whisperService = null;
            unbindService(this.conn);
        }
        if (DataHandle.getIns().isHasConnectedBle(true)) {
            BleServiceUtils.getInstance().setRecording(false);
        }
    }

    private void initWebSocket() {
        sendRecordStartMsg();
    }

    private void disconnectAnd2ReLogin() {
        if (this.whisperService != null) {
            changeLocalRecord(false);
            this.whisperService.release();
        }
        recordPause();
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.socket_disconnect_and_to_relogin), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda32
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m225x9e67434d(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, false);
    }

    /* JADX INFO: renamed from: lambda$disconnectAnd2ReLogin$27$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m225x9e67434d(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.logout(this);
    }

    private void showSaveDialog() {
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 1, 25, true, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.app.activity.RecordingActivity.6
            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onLeftBtnClick() {
                RecordingActivity.this.doStart();
            }

            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onSaveBtnClick(String str) {
                if (!BaseStringUtil.isEmpty(str)) {
                    RecordingActivity.this.mAudioTitle = str;
                } else {
                    RecordingActivity.this.mAudioTitle = DateUtil.getDateFromTimestamp(System.currentTimeMillis(), DateUtil.YYYY_MM_DD);
                }
                RecordingActivity.this.recordSave();
            }
        });
        bottomEditDialogView.setDialogContent(getString(C0874R.string.save), getString(C0874R.string.record_info_dialog_file_name), getString(C0874R.string.generate_by_date), this.mAudioTitle, "");
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }

    private void showMarkEditDialog() {
        if (this.currentIndex == -1 || this.isTransTimeExhaust || this.mLocalTrans) {
            return;
        }
        if (!DataHandle.getIns().isVip()) {
            AppUtils.showVipSubDialog(this.context);
            return;
        }
        int i = this.currentIndex;
        this.clickInsertIndex = i;
        if (this.transcribeList.get(i - 1).getAudioMark() != null) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.mark_note_existed));
            return;
        }
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 3, 1000, false, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.app.activity.RecordingActivity.7
            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onLeftBtnClick() {
            }

            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onSaveBtnClick(String str) {
                if (BaseStringUtil.isEmpty(str.trim())) {
                    return;
                }
                RecordingActivity.this.sendInsertMark(str);
            }
        });
        bottomEditDialogView.showTopBtn(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m234x9e810903(view2);
            }
        });
        bottomEditDialogView.setDialogContent(getString(C0874R.string.dialog_edit_notes_title), getString(C0874R.string.dialog_edit_notes_msg), getString(C0874R.string.dialog_edit_notes_hint), "", getString(C0874R.string.delete));
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
        this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m235xa5e63e22();
            }
        }, 500L);
    }

    /* JADX INFO: renamed from: lambda$showMarkEditDialog$28$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m234x9e810903(View view2) {
        this.transcribeFragment.resumeAutoScroll();
    }

    /* JADX INFO: renamed from: lambda$showMarkEditDialog$29$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m235xa5e63e22() {
        this.transcribeFragment.pauseAutoScroll();
    }

    private void showPicSelectPage() {
        if (!this.picInsertEnable) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.unable_to_insert_pic));
            return;
        }
        if (this.currentIndex == -1 || this.isTransTimeExhaust || this.mLocalTrans) {
            return;
        }
        if (this.maxPicCount - this.totalImgCount == 0) {
            if (DataHandle.getIns().isVip()) {
                ToastUtil.showLong(getString(C0874R.string.img_out_limit_num_for_vip, new Object[]{Integer.valueOf(this.maxPicCount)}));
                return;
            } else {
                AppUtils.showVipSubDialog(this.context);
                return;
            }
        }
        PermissionUtils.getIns(this, new C08028()).request("android.permission.READ_MEDIA_IMAGES");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordingActivity$8 */
    class C08028 implements PermissionCallback {
        C08028() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                RecordingActivity recordingActivity = RecordingActivity.this;
                recordingActivity.clickInsertIndex = recordingActivity.currentIndex;
                RecordingActivity recordingActivity2 = RecordingActivity.this;
                recordingActivity2.nonUploadImgIndex = recordingActivity2.currentIndex;
                RecordingActivity.this.imgList.clear();
                PictureSelector.create((AppCompatActivity) RecordingActivity.this).openGallery(SelectMimeType.ofImage()).setMaxSelectNum(RecordingActivity.this.maxPicCount - RecordingActivity.this.totalImgCount).setMinSelectNum(1).setImageSpanCount(4).isPreviewImage(true).isDisplayCamera(true).setImageEngine(GlideEngine.createGlideEngine()).setCompressEngine(new CompressFileEngine() { // from class: com.aivox.app.activity.RecordingActivity$8$$ExternalSyntheticLambda0
                    @Override // com.luck.picture.lib.engine.CompressFileEngine
                    public final void onStartCompress(Context context, ArrayList arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
                        this.f$0.m2243lambda$granted$0$comaivoxappactivityRecordingActivity$8(context, arrayList, onKeyValueResultCallbackListener);
                    }
                }).setSelectionMode(2).isSelectZoomAnim(true).forResult(188);
                return;
            }
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-RecordingActivity$8, reason: not valid java name */
        /* synthetic */ void m2243lambda$granted$0$comaivoxappactivityRecordingActivity$8(Context context, ArrayList arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
            Luban.with(context).load(arrayList).ignoreBy(50).setCompressListener(new OnNewCompressListener() { // from class: com.aivox.app.activity.RecordingActivity.8.1
                @Override // top.zibin.luban.OnNewCompressListener
                public void onStart() {
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onSuccess(String str, File file) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, file.getAbsolutePath());
                    }
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onError(String str, Throwable th) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, null);
                    }
                    if (RecordingActivity.this.currentImgCount > 0) {
                        RecordingActivity.access$2210(RecordingActivity.this);
                        if (RecordingActivity.this.currentImgCount == 0) {
                            RecordingActivity.this.clickInsertIndex = -1;
                        }
                    }
                }
            }).launch();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            BaseAppUtils.openSettingView(RecordingActivity.this.context);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtil.m338i("RecordingAct2_onActivityResult:" + i2);
        if (i == 188) {
            if (i2 == -1) {
                ArrayList<LocalMedia> arrayListObtainSelectorList = PictureSelector.obtainSelectorList(intent);
                this.totalImgCount += arrayListObtainSelectorList.size();
                this.currentImgCount = arrayListObtainSelectorList.size();
                for (int i3 = 0; i3 < arrayListObtainSelectorList.size(); i3++) {
                    LocalMedia localMedia = arrayListObtainSelectorList.get(i3);
                    String availablePath = localMedia.getAvailablePath();
                    LogUtil.m338i("getAvailablePath:" + localMedia.getAvailablePath());
                    if (isFinishing() || isDestroyed()) {
                        return;
                    }
                    LogUtil.m336e("压缩成功后调用，返回压缩后的图片文件");
                    LogUtil.m338i("file.size:" + FileUtils.getFileSize(availablePath));
                    LogUtil.m338i("path:" + availablePath);
                    uploadImg(availablePath, this.currentId);
                }
                return;
            }
            if (this.mLocalTrans) {
                this.clickInsertIndex = -1;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInsertImg(String str, String str2, int i) {
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdInsertImg(str, str2, i));
        this.transcribeList.get(this.clickInsertIndex - 1).setHasBindImg(true);
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2240lambda$sendInsertImg$30$comaivoxappactivityRecordingActivity();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sendInsertImg$30$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2240lambda$sendInsertImg$30$comaivoxappactivityRecordingActivity() {
        this.transcribeFragment.setTranscribeList(this.transcribeList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendInsertMark(String str) {
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdInsertMark(str, this.currentId + ""));
        ToastUtil.showShort(Integer.valueOf(C0874R.string.success));
        AudioMarkBean audioMarkBean = new AudioMarkBean();
        audioMarkBean.setId(-1);
        audioMarkBean.setContent(str);
        this.transcribeList.get(this.clickInsertIndex - 1).setAudioMark(audioMarkBean);
        this.transcribeFragment.setTranscribeList(this.transcribeList);
    }

    private void sendPushText(String str, String str2, String str3, int i, String str4, boolean z) {
        if (this.mLocalTrans) {
            return;
        }
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdPushText(str, str2, str3, i, str4, this.mTo, isTwoWay() ? z ? 0 : 1 : -1));
    }

    private void refreshNotify() {
        if (this.showBreakNotify) {
            return;
        }
        if (this.isUpLimit) {
            this.mBinding.llNotify.setVisibility(0);
            this.mBinding.llNotify.setBackgroundColor(Color.parseColor("#FFF1CC"));
            this.mBinding.tvNotify.setText(getString(C0874R.string.trans_service_full));
            this.mBinding.tvNotify.setTextColor(getColor(C0874R.color.black_11));
            this.mBinding.llNotify.setOnClickListener(null);
            return;
        }
        if (this.isNoNet) {
            this.mBinding.llNotify.setVisibility(0);
            this.mBinding.llNotify.setBackgroundColor(Color.parseColor("#FA5452"));
            this.mBinding.tvNotify.setText(getString(C0874R.string.weak_net_recording_tip));
            this.mBinding.tvNotify.setTextColor(getColor(C0874R.color.white));
            this.mBinding.llNotify.setOnClickListener(null);
            return;
        }
        this.mBinding.llNotify.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshNotify(String str) {
        this.showBreakNotify = true;
        if (this.isUpLimit) {
            return;
        }
        SpannableString spannableString = new SpannableString(str);
        this.mBinding.llNotify.setVisibility(0);
        this.mBinding.llNotify.setBackgroundColor(Color.parseColor("#FFECD5"));
        this.mBinding.tvNotify.setText(spannableString);
        this.mBinding.tvNotify.setTextColor(getColor(C0874R.color.black_11));
        this.mBinding.llNotify.setOnClickListener(null);
    }

    private void showRecordingGif(boolean z) {
        if (z) {
            ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivRecording, C1034R.mipmap.gif_recording, C1034R.mipmap.gif_recording);
        } else {
            this.mBinding.ivRecording.setImageResource(C1034R.mipmap.gif_recording);
        }
    }

    private boolean getTransType(boolean z) {
        if (z) {
            int i = this.mTranscribeType;
            return (i == 103 || i == 106) ? false : true;
        }
        int i2 = this.mTranscribeType;
        return (i2 == 102 || i2 == 105) ? false : true;
    }

    private boolean isTwoWay() {
        int i = this.mTranscribeType;
        return i == 101 || i == 104 || i == 107;
    }

    private boolean isTwoWayTrans() {
        int i = this.mTranscribeType;
        return i == 104 || i == 107;
    }

    private boolean isUpTrans() {
        int i = this.mTranscribeType;
        return i == 105 || i == 107;
    }

    private void initTtsManager() {
        if (this.mTranscribeType != 2) {
            return;
        }
        this.mBinding.ivTtsSwitch.setVisibility(0);
        this.mBinding.ivTtsSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m229x74ea11d9(view2);
            }
        });
        List listAsList = Arrays.asList(MyEnum.TRANSLATE_LANGUAGE.ZH.name, MyEnum.TRANSLATE_LANGUAGE.EN.name);
        final String str = MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mTo).name;
        LogUtil.m335d(this.TAG, "initTtsManager: " + str);
        Stream stream = listAsList.stream();
        Objects.requireNonNull(str);
        if (stream.anyMatch(new Predicate() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.startsWith((String) obj);
            }
        })) {
            this.singleThreadExecutor.execute(new Runnable() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m230x7c4f46f8(str);
                }
            });
        } else {
            this.mMicrosoftTtsManager = new PhoneTtsManager(this.mTo);
        }
    }

    /* JADX INFO: renamed from: lambda$initTtsManager$31$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m229x74ea11d9(View view2) {
        int i;
        this.mIsTtsOn = !this.mIsTtsOn;
        ImageView imageView = this.mBinding.ivTtsSwitch;
        if (this.mIsTtsOn) {
            i = C1034R.drawable.ic_tts_on;
        } else {
            i = C1034R.drawable.ic_tts_off;
        }
        imageView.setImageResource(i);
    }

    /* JADX INFO: renamed from: lambda$initTtsManager$32$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m230x7c4f46f8(String str) {
        TencentTtsManager tencentTtsManager = new TencentTtsManager();
        this.mTencentTtsManager = tencentTtsManager;
        tencentTtsManager.init(str);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        LogUtil.m338i("RecordingAct2_onSaveInstanceState:");
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public void onRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        LogUtil.m338i("RecordingAct2_onRestoreInstanceState:");
        super.onRestoreInstanceState(bundle, persistableBundle);
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.right_icon) {
            clickFontSizeSmallBtn();
            return;
        }
        if (id == C0726R.id.right_two_icon) {
            clickFontSizeBigBtn();
            return;
        }
        if (id == C0726R.id.audio_start_iv) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_BUTTON, "start");
            doStart();
            return;
        }
        if (id == C0726R.id.audio_stop_iv) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_BUTTON, "pause");
            DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.reminder_pause), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda28
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2228lambda$onViewClick$33$comaivoxappactivityRecordingActivity(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true, C0874R.string.continue_recording, C0874R.string.pause_recording);
            return;
        }
        if (id == C0726R.id.audio_over_iv) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_BUTTON, "save");
            doSave();
        } else if (id == C0726R.id.iv_insert_img) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_BUTTON, "insertImg");
            showPicSelectPage();
        } else if (id == C0726R.id.iv_add_mark) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_BUTTON, "insertMark");
            showMarkEditDialog();
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$33$com-aivox-app-activity-RecordingActivity, reason: not valid java name */
    /* synthetic */ void m2228lambda$onViewClick$33$comaivoxappactivityRecordingActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doPause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadModel() {
        changeLocalRecord(false);
        this.localRecordState = 0;
        WhisperService whisperService = this.whisperService;
        if (whisperService != null) {
            whisperService.loadModel(MyEnum.TRANSLATE_LANGUAGE.EN_LOCAL.name);
        }
        this.localRecordState = 1;
    }

    private void initLocalRecord(int i) {
        if (this.mLocalTrans) {
            this.filePath = FileUtils.getAudioFilePath(this);
            File file = new File(this.filePath);
            if (!file.exists() && !file.mkdirs()) {
                DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.create_file_fail), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
                return;
            }
            this.filePath = FileUtils.getAudioFilePath(this) + DateUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(), DateUtil.YYYY_MMDD_HHMM_SS) + "." + AudioType.WAV.getType();
            LocalFileEntity localFileEntity = new LocalFileEntity();
            localFileEntity.setLocalPath(this.filePath);
            if (i < 0) {
                int iIntValue = ((Integer) SPUtil.get(SPUtil.LOCAL_AI_AUDIO_ID, Integer.MIN_VALUE)).intValue() + 1;
                localFileEntity.setVid(iIntValue);
                SPUtil.put(SPUtil.LOCAL_AI_AUDIO_ID, Integer.valueOf(iIntValue));
                this.transcribeFragment.setAudioId(iIntValue);
            } else {
                this.mAudioId = i;
                localFileEntity.setVid(i);
                this.transcribeFragment.setAudioId(this.mAudioId);
            }
            localFileEntity.setTitle(this.mAudioTitle);
            localFileEntity.setUid(this.userInfo.getUuid());
            this.localFIleDbManager.insertOrReplace(localFileEntity);
            LogUtil.m335d("whisper", "filePath:" + this.filePath);
            if (this.whisperService.getCallback() != null) {
                this.whisperService.setCallback(null);
            }
            this.whisperService.setFilePath(this.filePath);
            this.localRecordState = 2;
            this.sessionStartTime = System.currentTimeMillis();
            if (this.recordNotification == null) {
                this.recordNotification = new RecordNotification(this, RecordingActivity.class, this.bundle);
            }
            if (!this.aiTranscribeList.isEmpty()) {
                this.aiTranscribeList.clear();
            }
            if (!this.transcribeList.isEmpty()) {
                this.transcribeList.clear();
            }
            this.transcribeFragment.setTranscribeList(this.transcribeList);
            this.whisperService.setCallback(this);
            changeLocalRecord(true);
        }
    }

    private void changeLocalRecord(boolean z) {
        if (this.mLocalTrans && this.localRecordState == 2) {
            if (z) {
                this.whisperService.startStream();
                if (this.pauseTime != 0) {
                    this.sessionStartTime += System.currentTimeMillis() - this.pauseTime;
                }
                setRecordStatus(1);
                Message message = new Message();
                message.what = 1001;
                this.mHandler.sendMessage(message);
                showRecordingGif(true);
            } else {
                this.whisperService.setStop(false);
                this.pauseTime = System.currentTimeMillis();
                this.mHandler.removeMessages(1001);
                setRecordStatus(2);
                this.whisperService.stopStream();
            }
            setViewGone();
        }
    }

    private void createAudioInfo() {
        this.mAudioService.createAudioInfo(1).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m223xfcc6267a((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m224x42b5b99((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createAudioInfo$34$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m223xfcc6267a(Integer num) throws Exception {
        if (num != null) {
            LogUtil.m335d("activity whisper", "audioID:" + num);
            DataHandle.getIns().setFileId(num.intValue());
            initLocalRecord(num.intValue());
            LocalFileEntity localFileEntity = new LocalFileEntity();
            localFileEntity.setLocalPath(this.filePath);
            localFileEntity.setVid(DataHandle.getIns().getFileId());
            localFileEntity.setTitle(DataHandle.getIns().getFileName());
            localFileEntity.setUid(this.userInfo.getUuid());
            localFileEntity.setIsBreak(true);
            this.localFIleDbManager.insertOrReplace(localFileEntity);
        }
    }

    /* JADX INFO: renamed from: lambda$createAudioInfo$35$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m224x42b5b99(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        }
        LogUtil.m337e("activity whisper", "createAudioInfo" + th.getMessage());
    }

    private void saveAllAsrContent() {
        if (this.transcribeList.isEmpty()) {
            return;
        }
        DialogUtils.showLoadingDialog(this.context);
        this.mAudioService.saveAsrContent(this.mAudioId, (LocalTransBean[]) this.transcribeList.stream().map(new Function() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda39
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RecordingActivity.lambda$saveAllAsrContent$36((Transcribe) obj);
            }
        }).toArray(new IntFunction() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda40
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return RecordingActivity.lambda$saveAllAsrContent$37(i);
            }
        })).doFinally(new Action() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda41
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda42
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m233x751cc28b(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordingActivity$$ExternalSyntheticLambda43
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LogUtil.m337e("activity whisper", "saveAllAsrContent:" + ((Throwable) obj).getMessage());
            }
        });
    }

    static /* synthetic */ LocalTransBean lambda$saveAllAsrContent$36(Transcribe transcribe) {
        return new LocalTransBean(transcribe.getBeginAt(), transcribe.getEndAt(), transcribe.getOnebest());
    }

    static /* synthetic */ LocalTransBean[] lambda$saveAllAsrContent$37(int i) {
        return new LocalTransBean[i];
    }

    /* JADX INFO: renamed from: lambda$saveAllAsrContent$38$com-aivox-app-activity-RecordingActivity */
    /* synthetic */ void m233x751cc28b(Object obj) throws Exception {
        this.nonUploadImgIndex = -1;
        this.currentImgCount = -1;
        this.imgList.clear();
        this.aiTranscribeList.clear();
        this.recordStatus = 3;
        recordStopOrSave();
    }
}
