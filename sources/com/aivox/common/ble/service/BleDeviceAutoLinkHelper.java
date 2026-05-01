package com.aivox.common.ble.service;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.ble.listener.BLeBtConnectListener;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceBean;
import com.aivox.common.model.EventBean;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceAutoLinkHelper {
    private List<BluetoothDevice> deviceList;
    private WeakReference<Context> mContextWeakReference;
    private IConnectStatusChangListener mListener;

    public interface IConnectStatusChangListener {
        void onFailed(String str);

        void onSuccess();
    }

    private BleDeviceAutoLinkHelper() {
        this.deviceList = new ArrayList();
    }

    private static final class MInstanceHolder {
        static final BleDeviceAutoLinkHelper mInstance = new BleDeviceAutoLinkHelper();

        private MInstanceHolder() {
        }
    }

    public static BleDeviceAutoLinkHelper getInstance() {
        return MInstanceHolder.mInstance;
    }

    public void stopScan() {
        BleServiceUtils.getInstance().scanLeDevice(false);
    }

    public void destroy() {
        stopScan();
        WeakReference<Context> weakReference = this.mContextWeakReference;
        if (weakReference != null) {
            weakReference.clear();
            this.mContextWeakReference = null;
        }
        this.deviceList.clear();
        BleServiceUtils.getInstance().cleanListening();
    }

    public void setConnectStatusChangeListener(IConnectStatusChangListener iConnectStatusChangListener) {
        this.mListener = iConnectStatusChangListener;
    }

    public void startAutoLinkBle(Context context) {
        this.mContextWeakReference = new WeakReference<>(context);
        BleServiceUtils.getInstance().openBle(this.mContextWeakReference.get());
        BleServiceUtils.getInstance().setListening(new BLeBtConnectListener() { // from class: com.aivox.common.ble.service.BleDeviceAutoLinkHelper.1
            @Override // com.aivox.common.ble.listener.BLeBtConnectListener
            public void openBle() {
                LogUtil.m335d("BleDeviceAutoLinkHelper", "openBle name:");
            }

            @Override // com.aivox.common.ble.listener.BLeBtConnectListener
            public void refreshDeviceList(BluetoothDevice bluetoothDevice, int i, MyEnum.BLE_DEVICE_MODEL ble_device_model) {
                if (BaseStringUtil.isEmpty(bluetoothDevice.getName())) {
                    return;
                }
                LogUtil.m335d("BleDeviceAutoLinkHelper(" + hashCode() + ")", "refreshDeviceList name:" + bluetoothDevice.getName() + " -> " + bluetoothDevice.getAddress());
                if (ble_device_model == MyEnum.BLE_DEVICE_MODEL.DIGI_GIFT_BOX) {
                    return;
                }
                if (!bluetoothDevice.getAddress().equals(SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, ""))) {
                    for (int i2 = 0; i2 < BleDeviceAutoLinkHelper.this.deviceList.size(); i2++) {
                        if (((BluetoothDevice) BleDeviceAutoLinkHelper.this.deviceList.get(i2)).getAddress().equals(bluetoothDevice.getAddress())) {
                            return;
                        }
                    }
                    if (!BleDeviceAutoLinkHelper.this.deviceList.contains(bluetoothDevice)) {
                        BleDeviceAutoLinkHelper.this.deviceList.add(bluetoothDevice);
                    }
                    EventBean eventBean = new EventBean(Constant.EVENT.BLE_SHOW_CONNECT_DIALOG);
                    eventBean.setS1(ble_device_model.name);
                    eventBean.setS2(bluetoothDevice.getAddress());
                    EventBus.getDefault().post(eventBean);
                    return;
                }
                BleServiceUtils.getInstance().connectDevice(new DeviceBean(bluetoothDevice.getName(), bluetoothDevice.getAddress()));
            }

            @Override // com.aivox.common.ble.listener.BLeBtConnectListener
            public void connectSuccess() {
                LogUtil.m334d("BleDeviceAutoLinkHelper:connectSuccess");
                DataHandle.getIns().setHasConnectedBle(true);
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException unused) {
                }
                CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CONNECTED));
                BleServiceUtils.getInstance().scanLeDevice(false);
                if (BleDeviceAutoLinkHelper.this.mListener != null) {
                    BleDeviceAutoLinkHelper.this.mListener.onSuccess();
                }
                BleDeviceAutoLinkHelper.this.destroy();
            }

            @Override // com.aivox.common.ble.listener.BLeBtConnectListener
            public void connectFailed(String str) {
                LogUtil.m334d("BleDeviceAutoLinkHelper:connectFailed");
                if (BleDeviceAutoLinkHelper.this.mListener != null) {
                    BleDeviceAutoLinkHelper.this.mListener.onFailed(str);
                }
                BleDeviceAutoLinkHelper.this.destroy();
            }
        });
        CommonServiceUtils.getInstance().startBleService(this.mContextWeakReference.get());
        BleServiceUtils.getInstance().scanLeDevice(true);
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.common.ble.service.BleDeviceAutoLinkHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BleServiceUtils.getInstance().scanLeDevice(false);
            }
        }, 20000L);
    }
}
