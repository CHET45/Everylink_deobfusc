package com.aivox.app.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.BleDeviceAdapter;
import com.aivox.app.databinding.ActivityDeviceScanBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.listener.BLeBtConnectListener;
import com.aivox.common.ble.listener.SppBtConnectListener;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.BleDeviceAutoLinkHelper;
import com.aivox.common.ble.service.BleServiceUtils;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.LocalDeviceBean;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.NoScrollGridLayoutManager;
import com.azure.core.util.polling.implementation.PollingConstants;
import com.blankj.utilcode.util.CollectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class DeviceScanActivity extends BaseFragmentActivity {
    private static final String TAG = "DeviceScanActivity";
    private BleDeviceAdapter adapter;
    private Animation animation;
    private boolean isConnecting;
    private boolean isScan;
    private ActivityDeviceScanBinding mBinding;
    private BleDeviceAdapter mOtherDeviceAdapter;
    private final List<LocalDeviceBean> mList = new ArrayList();
    private final List<LocalDeviceBean> mOtherList = new ArrayList();
    private int mOtherDeviceCount = 6;
    private final Handler mHandler = new Handler() { // from class: com.aivox.app.activity.DeviceScanActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 5000) {
                return;
            }
            DeviceScanActivity.this.getBondedDevice();
        }
    };

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.isScan = true;
        ActivityDeviceScanBinding activityDeviceScanBinding = (ActivityDeviceScanBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_device_scan);
        this.mBinding = activityDeviceScanBinding;
        activityDeviceScanBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2097lambda$initView$0$comaivoxappactivityDeviceScanActivity(view2);
            }
        });
        this.mBinding.titleView.setRightIconListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2098lambda$initView$1$comaivoxappactivityDeviceScanActivity(view2);
            }
        });
        BleDeviceAutoLinkHelper.getInstance().destroy();
        this.mBinding.rcvDevices.setLayoutManager(new NoScrollGridLayoutManager((Context) this, 2, 1, false));
        this.adapter = new BleDeviceAdapter(this, C0726R.layout.item_ble_device);
        this.mOtherDeviceAdapter = new BleDeviceAdapter(this, C0726R.layout.item_ble_device);
        this.mBinding.rcvDevices.setAdapter(this.adapter);
        this.animation = AnimationUtils.loadAnimation(this, C1034R.anim.rotating);
        this.mBinding.ivLoading.startAnimation(this.animation);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda4
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m2099lambda$initView$2$comaivoxappactivityDeviceScanActivity(view2, i);
            }
        });
        this.mBinding.tvConnect.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2100lambda$initView$3$comaivoxappactivityDeviceScanActivity(view2);
            }
        });
        for (int i = 0; i < this.mOtherDeviceCount; i++) {
            this.mOtherList.add(new LocalDeviceBean((Boolean) true, MyEnum.DEVICE_MODEL.AIVOX_R7));
        }
        this.mBinding.rcvMoreDevices.setAdapter(this.mOtherDeviceAdapter);
        this.mBinding.rcvMoreDevices.setLayoutManager(new NoScrollGridLayoutManager((Context) this, 2, 1, false));
        this.mOtherDeviceAdapter.setmDate(this.mOtherList);
        this.mOtherDeviceAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda6
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i2) {
                this.f$0.m2101lambda$initView$4$comaivoxappactivityDeviceScanActivity(view2, i2);
            }
        });
        CommonServiceUtils.getInstance().startSppService(this);
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2102lambda$initView$5$comaivoxappactivityDeviceScanActivity();
            }
        }, 1000L);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2097lambda$initView$0$comaivoxappactivityDeviceScanActivity(View view2) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2098lambda$initView$1$comaivoxappactivityDeviceScanActivity(View view2) {
        ARouterUtils.startWithContext(this.context, MainAction.DEVICE_HELP);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2099lambda$initView$2$comaivoxappactivityDeviceScanActivity(View view2, int i) {
        if (!BleBtService.getInstance().getBluetoothAdapter().isEnabled()) {
            openBleDialog();
            return;
        }
        DialogUtils.showLoadingDialog(this.context);
        if (DataHandle.getIns().hasConnectedBluetooth(false)) {
            DialogUtils.hideLoadingDialog();
            if (CollectionUtils.isNotEmpty(this.mList)) {
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CONNECTED, this.mList.get(0).getBluetoothDevice().getName()));
                this.mBinding.llBtmNotify.setVisibility(8);
                return;
            }
            return;
        }
        if (this.mList.get(i).getIsBleDevice().booleanValue()) {
            BleServiceUtils.getInstance().connectDevice(new DeviceBean(this.mList.get(i).getBluetoothDevice().getName(), this.mList.get(i).getBluetoothDevice().getAddress()));
            SPUtil.put(SPUtil.LAST_BLE_DEVICE_NAME, this.mList.get(i).getBleDevice().name);
            SPUtil.put(SPUtil.LAST_BLUETOOTH_DEVICE_NAME, this.mList.get(i).getBluetoothDevice().getName());
        } else {
            CommonServiceUtils.getInstance().connect(this.mList.get(i).getBluetoothDevice().getAddress());
        }
        this.isConnecting = true;
        this.isScan = false;
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2100lambda$initView$3$comaivoxappactivityDeviceScanActivity(View view2) {
        getBondedDevice();
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2101lambda$initView$4$comaivoxappactivityDeviceScanActivity(View view2, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_FLAG, this.mOtherList.get(i).getBluetoothDevice().getName());
        ARouterUtils.startWithActivity(this, MainAction.DEVICE_INFO, bundle);
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2102lambda$initView$5$comaivoxappactivityDeviceScanActivity() {
        CommonServiceUtils.getInstance().setSppListening(new SppBtConnectListener() { // from class: com.aivox.app.activity.DeviceScanActivity.2
            @Override // com.aivox.common.ble.listener.SppBtConnectListener
            public void onConnectSuccess(BluetoothDevice bluetoothDevice) {
                LogUtil.m335d(DeviceScanActivity.TAG, "onConnectSuccess" + bluetoothDevice.getAddress());
                DialogUtils.hideLoadingDialog();
                DataHandle.getIns().setHasConnectedBle(false);
                CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CONNECTED, bluetoothDevice.getName()));
                CommonServiceUtils.getInstance().stopBleService(DeviceScanActivity.this.context);
            }

            @Override // com.aivox.common.ble.listener.SppBtConnectListener
            public void onConnectFailed(String str) {
                LogUtil.m335d(DeviceScanActivity.TAG, "onConnectFailed: " + str);
                DialogUtils.hideLoadingDialog();
                CommonServiceUtils.getInstance().disconnect(DeviceScanActivity.this.context);
            }
        });
        if (isFinishing()) {
            return;
        }
        getBondedDevice();
        startScanBleDevice();
    }

    private void startScanBleDevice() {
        if (!((LocationManager) getSystemService(PollingConstants.LOCATION_LOWER_CASE)).isProviderEnabled("gps")) {
            DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.open_gps_notice), null, null, false, false, C0874R.string.cancel, C0874R.string.sure);
            return;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        BleServiceUtils.getInstance().setListening(new C07423());
        BleServiceUtils.getInstance().openBle(this);
        CommonServiceUtils.getInstance().startBleService(this);
        BleServiceUtils.getInstance().scanLeDevice(true);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceScanActivity$3 */
    class C07423 implements BLeBtConnectListener {
        C07423() {
        }

        @Override // com.aivox.common.ble.listener.BLeBtConnectListener
        public void openBle() {
            LogUtil.m335d(DeviceScanActivity.TAG, "openBle");
        }

        @Override // com.aivox.common.ble.listener.BLeBtConnectListener
        public void refreshDeviceList(BluetoothDevice bluetoothDevice, int i, MyEnum.BLE_DEVICE_MODEL ble_device_model) {
            if (BaseStringUtil.isEmpty(bluetoothDevice.getName())) {
                return;
            }
            LogUtil.m335d(DeviceScanActivity.TAG, "refreshDeviceList ble name:" + bluetoothDevice.getName() + " -> " + bluetoothDevice.getAddress());
            int i2 = 0;
            while (true) {
                if (i2 < DeviceScanActivity.this.mList.size()) {
                    if (((LocalDeviceBean) DeviceScanActivity.this.mList.get(i2)).getBluetoothDevice().getAddress().equals(bluetoothDevice.getAddress())) {
                        ((LocalDeviceBean) DeviceScanActivity.this.mList.get(i2)).setRssi(i);
                        break;
                    }
                    i2++;
                } else {
                    LocalDeviceBean localDeviceBean = new LocalDeviceBean(false, bluetoothDevice, ble_device_model);
                    localDeviceBean.setRssi(i);
                    DeviceScanActivity.this.mList.add(localDeviceBean);
                    break;
                }
            }
            DialogUtils.hideLoadingDialog();
            DeviceScanActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceScanActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m112xccb248db();
                }
            });
            new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.DeviceScanActivity$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BleServiceUtils.getInstance().scanLeDevice(false);
                }
            }, 60000L);
        }

        /* JADX INFO: renamed from: lambda$refreshDeviceList$0$com-aivox-app-activity-DeviceScanActivity$3 */
        /* synthetic */ void m112xccb248db() {
            if (DeviceScanActivity.this.mList.size() != 0) {
                DeviceScanActivity.this.adapter.setmDate(DeviceScanActivity.this.mList);
                if (DeviceScanActivity.this.mBinding.llConnectFail.getVisibility() == 0) {
                    DeviceScanActivity.this.mBinding.llConnectFail.setVisibility(8);
                    DeviceScanActivity.this.mBinding.rcvDevices.setVisibility(0);
                }
            }
        }

        @Override // com.aivox.common.ble.listener.BLeBtConnectListener
        public void connectSuccess() {
            LogUtil.m335d(DeviceScanActivity.TAG, "onConnectSuccess");
            DialogUtils.hideLoadingDialog();
            DataHandle.getIns().setHasConnectedBle(true);
            EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CONNECTED));
            CommonServiceUtils.getInstance().stopSppService(DeviceScanActivity.this);
            CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
            BleServiceUtils.getInstance().scanLeDevice(false);
        }

        @Override // com.aivox.common.ble.listener.BLeBtConnectListener
        public void connectFailed(String str) {
            LogUtil.m335d(DeviceScanActivity.TAG, "onConnectFailed: " + str);
            DialogUtils.hideLoadingDialog();
            BleServiceUtils.getInstance().scanLeDevice(false);
            ToastUtil.showShort(Integer.valueOf(C0874R.string.connect_failed));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBondedDevice() {
        boolean zBooleanValue;
        this.mList.clear();
        for (BluetoothDevice bluetoothDevice : CommonServiceUtils.getInstance().getBondedDevices()) {
            try {
                zBooleanValue = ((Boolean) bluetoothDevice.getClass().getMethod("isConnected", new Class[0]).invoke(bluetoothDevice, new Object[0])).booleanValue();
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                zBooleanValue = false;
            }
            if (zBooleanValue) {
                LogUtil.m335d("CONNECTED DEVICE", bluetoothDevice.getName() + ":" + bluetoothDevice.getAddress());
                if (CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID1) || CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID2)) {
                    this.mList.add(new LocalDeviceBean((Boolean) false, bluetoothDevice));
                    this.mBinding.llBtmNotify.setVisibility(8);
                }
                this.adapter.setmDate(this.mList);
            }
        }
        this.mHandler.removeCallbacksAndMessages(null);
        Message message = new Message();
        message.what = 5000;
        this.mHandler.sendMessageDelayed(message, 10000L);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (!this.isScan || BleBtService.getInstance().getBluetoothAdapter().isEnabled()) {
            return;
        }
        openBleDialog();
    }

    private void openBleDialog() {
        DialogUtils.showDialogWithDefBtn(this, Integer.valueOf(C0874R.string.reminder), getResources().getString(C0874R.string.device_to_open_bt), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2103lambda$openBleDialog$6$comaivoxappactivityDeviceScanActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceScanActivity$$ExternalSyntheticLambda1
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2104lambda$openBleDialog$7$comaivoxappactivityDeviceScanActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, true, false);
    }

    /* JADX INFO: renamed from: lambda$openBleDialog$6$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2103lambda$openBleDialog$6$comaivoxappactivityDeviceScanActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$openBleDialog$7$com-aivox-app-activity-DeviceScanActivity, reason: not valid java name */
    /* synthetic */ void m2104lambda$openBleDialog$7$comaivoxappactivityDeviceScanActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        BaseAppUtils.jump2OpenBT(this);
    }

    private void changeScanStatus(boolean z) {
        if (z) {
            if (this.isConnecting || !this.mList.isEmpty()) {
                return;
            }
            this.mBinding.llConnectFail.setVisibility(0);
            this.mBinding.rcvDevices.setVisibility(8);
            this.mBinding.ivLoading.clearAnimation();
            this.mBinding.ivLoading.setVisibility(8);
            this.mBinding.tvMsg.setText(getString(C0874R.string.connectable_device_not_found));
            return;
        }
        this.isScan = false;
        this.mBinding.llConnectFail.setVisibility(8);
        this.mBinding.rcvDevices.setVisibility(0);
        this.mBinding.ivLoading.startAnimation(this.animation);
        this.mBinding.ivLoading.setVisibility(0);
        this.mBinding.tvMsg.setText(getString(C0874R.string.device_searching));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        CommonServiceUtils.getInstance().stopService(this);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        if (eventBean.getFrom() == 300 && !isDestroyed()) {
            Bundle bundle = new Bundle();
            bundle.putString("deviceName", eventBean.getS1());
            bundle.putBoolean("isConnect", true);
            finish();
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        CommonServiceUtils.getInstance().removeListening();
        BleServiceUtils.getInstance().stopScan();
        BleServiceUtils.getInstance().cleanListening();
        super.onDestroy();
    }
}
