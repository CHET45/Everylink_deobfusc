package com.aivox.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ScreenUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.receive.NetworkReceiver;
import com.aivox.common.socket.WebSocketManager;
import com.aivox.common.statemachine.SocketStateMachine;
import com.aivox.common.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import java.util.HashMap;
import java.util.Objects;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseFragmentActivity extends AppCompatActivity {
    public static boolean isForeground = false;
    static HashMap<Integer, CountDownTimer> timerHashMap = new HashMap<>();
    protected Context context;
    public Bundle savedInstanceState;
    protected final String TAG = getClass().getSimpleName();
    public boolean setPortrait = true;
    protected int curNetStatus = 1;

    static /* synthetic */ void lambda$networkDisconnect$2(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$socketReconnectError$0(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    private void storageLess(int i) {
    }

    public abstract void initView();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
        if (this.setPortrait) {
            try {
                setRequestedOrientation(1);
            } catch (Exception e) {
                LogUtil.m336e("禁用横屏" + e.getLocalizedMessage());
            }
        }
        this.savedInstanceState = bundle;
        this.context = this;
        initView();
        Toolbar toolbar = (Toolbar) findViewById(C0874R.id.common_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        AppManager.getAppManager().addActivity(this);
        ScreenUtil.getDisplayMetrics(this);
        registerNetworkReceiver();
        BarUtils.transparentStatusBar(this);
        if (getWindow().getDecorView().getBackground() == null) {
            getWindow().getDecorView().setBackgroundColor(getColor(C0874R.color.red));
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(context));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LogUtil.m338i("--onConfigurationChanged--");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isForeground = false;
        AppManager.getAppManager().finishActivity(this);
        try {
            unregisterReceiver(NetworkReceiver.getInstance());
        } catch (Exception e) {
            LogUtil.m337e(this.TAG, "unregisterReceiver NetworkReceiver failed: " + e.getMessage());
        }
        HashMap<Integer, CountDownTimer> map = timerHashMap;
        if (map == null || map.isEmpty()) {
            return;
        }
        for (CountDownTimer countDownTimer : timerHashMap.values()) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
        timerHashMap.clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBean) {
        int from = eventBean.getFrom();
        if (from == 51) {
            refreshDeviceConnectState(eventBean);
            return;
        }
        if (from == 52) {
            refreshThirdConnectState();
            return;
        }
        if (from == 59) {
            socketDisconnect();
            return;
        }
        if (from == 40008) {
            currentAccountIsLoggedOut();
        } else if (from == 62) {
            storageLess(eventBean.getA());
        } else {
            if (from != 63) {
                return;
            }
            pointLess(eventBean.getA());
        }
    }

    private Activity getActivity() {
        Activity activityCurrentActivity = AppManager.getAppManager().currentActivity();
        for (Activity activity : AppManager.getAppManager().getAllActivity()) {
            if (activityCurrentActivity == activity) {
                return activity;
            }
        }
        return null;
    }

    private void socketDisconnect() {
        LogUtil.m338i("网络:" + NetUtil.isNetworkConnected() + "---" + NetUtil.isConnected(this));
        if (NetUtil.isNetworkConnected()) {
            LogUtil.m336e("==长连接 - 断开==");
        }
    }

    private void socketReconnectSuccess() {
        LogUtil.m336e("==长连接 - 重连成功==");
        if (getActivity() != null) {
            ToastUtil.showTextToast2(this, Integer.valueOf(C0874R.string.socket_reconnect_success));
        }
    }

    private void socketReconnectError() {
        Activity activity = getActivity();
        if (activity != null) {
            DialogUtils.showDialogWithDefBtnAndSingleListener(activity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.socket_reconnect_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.base.BaseFragmentActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    BaseFragmentActivity.lambda$socketReconnectError$0(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
        }
    }

    private void currentAccountIsLoggedOut() {
        Activity activity = getActivity();
        if (activity != null) {
            EventBus.getDefault().post(new EventBean(104));
            WebSocketManager.stopService(this);
            AppUtils.showLogoutDialog(activity, Integer.valueOf(((Integer) SPUtil.get(SPUtil.RECORD_STATE, 0)).intValue() == 0 ? C0874R.string.socket_disconnect_and_to_relogin_no_recording : C0874R.string.socket_disconnect_and_to_relogin));
        }
    }

    private void pointLess(int i) {
        LogUtil.m338i("点券不足弹窗");
    }

    /* JADX WARN: Type inference failed for: r9v0, types: [com.aivox.common.base.BaseFragmentActivity$1] */
    private void startCmdCountDownTimer(final int i, int i2) {
        if (timerHashMap == null) {
            timerHashMap = new HashMap<>();
        }
        if (getActivity() == null || timerHashMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        LogUtil.m339i("websocket", "添加计时器key==" + i);
        timerHashMap.put(Integer.valueOf(i), new CountDownTimer(i2, 500L) { // from class: com.aivox.common.base.BaseFragmentActivity.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                LogUtil.m339i("websocket", i + "倒计时" + j);
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                LogUtil.m339i("websocket", i + "倒计时结束，未收到响应");
                BaseFragmentActivity.timerHashMap.remove(Integer.valueOf(i));
                SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "ACK_TIMEOUT");
            }
        }.start());
    }

    private void startCmdCountDownTimer(int i) {
        startCmdCountDownTimer(i, 5000);
    }

    private void stopCmdCountDownTimer(int i) {
        if (timerHashMap.size() != 0 && timerHashMap.containsKey(Integer.valueOf(i))) {
            ((CountDownTimer) Objects.requireNonNull(timerHashMap.get(Integer.valueOf(i)))).cancel();
            timerHashMap.remove(Integer.valueOf(i));
            LogUtil.m339i("websocket", i + "已移除");
        }
    }

    private void refreshDeviceConnectState(EventBean eventBean) {
        ToastUtil.showTextToast(this, DataHandle.getIns().getDeviceOnlineOrOffline() == 1 ? "设备已上线" : "设备已离线");
    }

    private void refreshThirdConnectState() {
        if (isRecordViewShow()) {
            return;
        }
        DialogUtils.hideLoadingDialog();
        ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.pop_recording));
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDeviceRecording", true);
        BaseAppUtils.startActivity(this, ARouterUtils.getClass(RecordAction.RECORD_ING), bundle);
    }

    public boolean isRecordViewShow() {
        return BaseAppUtils.isActivityInTheForeground(this, ARouterUtils.getClass(RecordAction.RECORD_ING).getName());
    }

    public boolean isActivityShow(Class cls) {
        return BaseAppUtils.isActivityInTheForeground(this, cls.getName()) && !BaseAppUtils.isKeyguardLocked(this);
    }

    protected void gone(View... viewArr) {
        if (viewArr == null || viewArr.length <= 0) {
            return;
        }
        for (View view2 : viewArr) {
            if (view2 != null) {
                view2.setVisibility(8);
            }
        }
    }

    protected void visible(View... viewArr) {
        if (viewArr == null || viewArr.length <= 0) {
            return;
        }
        for (View view2 : viewArr) {
            if (view2 != null) {
                view2.setVisibility(0);
            }
        }
    }

    private void registerNetworkReceiver() {
        registerReceiver(NetworkReceiver.getInstance(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        NetworkReceiver.getInstance().setNetworkChangeListener(new NetworkReceiver.NetworkChangeListener() { // from class: com.aivox.common.base.BaseFragmentActivity$$ExternalSyntheticLambda1
            @Override // com.aivox.common.receive.NetworkReceiver.NetworkChangeListener
            public final void onChange(int i, int i2, int i3, int i4, int i5) {
                this.f$0.m343x24a7c26(i, i2, i3, i4, i5);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$registerNetworkReceiver$1$com-aivox-common-base-BaseFragmentActivity */
    /* synthetic */ void m343x24a7c26(int i, int i2, int i3, int i4, int i5) {
        LogUtil.m336e("network changed---" + i4 + "--" + i5);
        this.curNetStatus = i5;
        EventBus.getDefault().post(new EventBean(45));
        if (i5 != i4) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "netChanged -> from " + i4 + " to " + i5);
            if (i5 == i3) {
                networkDisconnect();
            } else if (i4 == i3) {
                networkConnect();
            }
        }
    }

    private void networkConnect() {
        ToastUtil.showTextToast(this.context, Integer.valueOf(C0874R.string.socket_reconnect_success));
        LogUtil.m338i("网络已恢复，socket发起重连");
        if (SocketStateMachine.get().getStageNow() == SocketStateMachine.SocketStateCode.IDLE) {
            EventBus.getDefault().post(new EventBean(46));
        }
    }

    private void networkDisconnect() {
        if (BaseStringUtil.isEmpty((String) SPUtil.get(SPUtil.TOKEN, ""))) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.network_disconnect), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.base.BaseFragmentActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    BaseFragmentActivity.lambda$networkDisconnect$2(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.base.BaseFragmentActivity$$ExternalSyntheticLambda3
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    BaseAppUtils.jump2OpenWifi(context);
                }
            }, false, false, C0874R.string.sure, C0874R.string.settings);
            return;
        }
        if (SocketStateMachine.get().getStageNow() != SocketStateMachine.SocketStateCode.IDLE) {
            SocketStateMachine.get().stateGo(SocketStateMachine.SocketStateCode.IDLE, "DISCONNECT");
        }
        EventBus.getDefault().post(new EventBean(59));
    }
}
