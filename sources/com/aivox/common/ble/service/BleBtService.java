package com.aivox.common.ble.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.view.PointerIconCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.ble.BleAudioDataUpManager;
import com.aivox.common.ble.BleDataManager;
import com.aivox.common.ble.BleUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceBean;
import com.aivox.common.model.EventBean;
import com.microsoft.azure.storage.table.TableConstants;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class BleBtService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ACTION_DATA_AVAILABLE = "com.bluetooth.ACTION_DATA_AVAILABLE";
    public static final String ACTION_GATT_CONNECTED = "com.bluetooth.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "com.bluetooth.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "com.bluetooth.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String DEVICE_DOES_NOT_SUPPORT_UART = "com.bluetooth.DEVICE_DOES_NOT_SUPPORT_UART";
    public static final String EXTRA_DATA = "com.bluetooth.EXTRA_DATA";
    public static final int HANDLER_BLE_RECONNECT = 1002;
    private static final int HEART_BEAT_RATE = 3000;
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;
    private static final String TAG = "BleBtService";
    private static BleBtService service;
    boolean awaitingPong;
    private String deviceAddress;
    private String deviceName;
    private GlassFileReceiver glassFileReceiver;
    boolean isLargeMtu;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    NotificationManager manager;
    Notification notification;
    NotificationCompat.Builder notificationBuilder;
    PendingIntent pendingIntent;
    int receivePongCount;
    int sentPingCount;
    public static UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static UUID RX_SERVICE_UUID = UUID.fromString("01000100-0000-1000-8000-009078563412");
    public static UUID RX_CHAR_UUID = UUID.fromString("03000300-0000-1000-8000-009278563412");
    public static UUID TX_CHAR_UUID = UUID.fromString("02000200-0000-1000-8000-009178563412");
    private DeviceBean mDeviceBean = null;
    private int mConnectionState = 0;
    private boolean isGlass = false;
    private boolean isSupportFeature = false;
    private String channelId = TAG;
    private BluetoothGattCallback mGattCallback = new C09661();
    int failedPing = 0;
    Handler mHandler = new Handler((Looper) Objects.requireNonNull(Looper.myLooper())) { // from class: com.aivox.common.ble.service.BleBtService.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 3000 && DataHandle.getIns().isHasConnectedBle(false)) {
                if (BleBtService.this.awaitingPong) {
                    BleBtService.this.failedPing++;
                } else {
                    BleBtService.this.failedPing = 0;
                }
                BleBtService.this.sentPingCount++;
                BleBtService.this.awaitingPong = true;
                if (BleBtService.this.failedPing != 0) {
                    LogUtil.m336e("ble sent ping but didn't receive pong " + BleBtService.this.failedPing + " times after " + (BleBtService.this.sentPingCount - 1) + " successful ping/pongs)");
                    LogUtil.m337e(BleBtService.TAG, "disconnecting due to failed ping/pong");
                }
                if (BleBtService.this.failedPing != 3) {
                    if (!BleBtService.this.isGlass) {
                        BleBtService.getInstance().writeRXCharacteristic(Constant.CmdUpHeartbeat.getBytes());
                    } else {
                        BleBtService.this.sendGlassCmdWithResponse(GlassesCmd.HEART_BEAT);
                    }
                    BleBtService.this.mHandler.sendEmptyMessageDelayed(3000, 3000L);
                    return;
                }
                BleServiceUtils.getInstance().disconnect();
                EventBus.getDefault().post(new EventBean(301));
            }
        }
    };

    public interface GlassFileReceiver {
        void onComplete();

        void onError(Exception exc);

        void onProgress(long j, long j2, byte[] bArr);

        void onStart(long j, long j2, long j3);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void setGlassFileReceiver(GlassFileReceiver glassFileReceiver) {
        this.glassFileReceiver = glassFileReceiver;
    }

    public boolean isGlass() {
        return this.isGlass;
    }

    public synchronized void setGlass(boolean z) {
        if (this.isGlass == z) {
            return;
        }
        this.isGlass = z;
        LogUtil.m334d("setGlass:" + z);
        if (z) {
            RX_SERVICE_UUID = UUID.fromString("01000100-0000-2000-8000-009078563412");
            RX_CHAR_UUID = UUID.fromString("03000300-0000-2000-8000-009278563412");
            TX_CHAR_UUID = UUID.fromString("02000200-0000-2000-8000-009178563412");
        } else {
            RX_SERVICE_UUID = UUID.fromString("01000100-0000-1000-8000-009078563412");
            RX_CHAR_UUID = UUID.fromString("03000300-0000-1000-8000-009278563412");
            TX_CHAR_UUID = UUID.fromString("02000200-0000-1000-8000-009178563412");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestMtu(BluetoothGatt bluetoothGatt, int i) {
        LogUtil.m338i("requestMtu  BLE 读写20字节限制问题");
        if (bluetoothGatt != null) {
            return bluetoothGatt.requestMtu(i);
        }
        return false;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        if (this.mBluetoothAdapter == null) {
            initialize();
        }
        return this.mBluetoothAdapter;
    }

    public void setmConnectionState(int i) {
        this.mConnectionState = i;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public DeviceBean getmDeviceBean() {
        return this.mDeviceBean;
    }

    public void setServiceForeground() {
        NotificationChannel notificationChannel = new NotificationChannel(this.channelId, getString(C0874R.string.app_name), 3);
        Intent intent = new Intent(this, (Class<?>) BleBtService.class);
        intent.putExtra(TableConstants.ErrorConstants.ERROR_CODE, 1235);
        this.pendingIntent = PendingIntent.getService(this, PointerIconCompat.TYPE_HELP, intent, 67108864);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        this.manager = notificationManager;
        notificationManager.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, this.channelId);
        this.notificationBuilder = builder;
        Notification notificationBuild = builder.setOngoing(true).setSmallIcon(C0874R.mipmap.icon_logo).setContentTitle(getString(C0874R.string.notification_bluetooth_connected)).setPriority(4).setCategory(NotificationCompat.CATEGORY_SERVICE).setChannelId(this.channelId).setContentIntent(this.pendingIntent).build();
        this.notification = notificationBuild;
        startForeground(1, notificationBuild);
        this.manager.notify(1, this.notification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update(boolean z) {
        if (this.manager != null && BaseAppUtils.isServiceWork(this, BleBtService.class.getName())) {
            if (this.notificationBuilder != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, this.channelId);
                this.notificationBuilder = builder;
                this.notification = builder.setChannelId(this.channelId).setSmallIcon(C0874R.mipmap.icon_logo).setContentIntent(this.pendingIntent).setContentTitle(getResources().getString(z ? C0874R.string.notification_bluetooth_connected : C0874R.string.notification_bluetooth_disconnected)).build();
            }
            startForeground(1, this.notification);
            this.manager.notify(1, this.notification);
        }
    }

    public String getConnectedDeviceName() {
        return (String) SPUtil.get(SPUtil.LAST_BLE_DEVICE_NAME, this.deviceName);
    }

    public String getRealConnectedDeviceName() {
        return (String) SPUtil.get(SPUtil.LAST_BLUETOOTH_DEVICE_NAME, this.deviceName);
    }

    public String getConnectedDeviceAddress() {
        String str = this.deviceAddress;
        return str != null ? str : "";
    }

    /* JADX INFO: renamed from: com.aivox.common.ble.service.BleBtService$1 */
    class C09661 extends BluetoothGattCallback {
        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        }

        C09661() {
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            super.onMtuChanged(bluetoothGatt, i, i2);
            LogUtil.m339i(BleBtService.TAG, "--onMtuChanged-- mut:" + i + "\tstatus:" + i2);
            if (i2 == 0 && i == 500) {
                LogUtil.m339i(BleBtService.TAG, "请求500字节长度数据包成功");
                BleBtService.this.isLargeMtu = true;
            }
            BleBtService.this.setServiceForeground();
            BleBtService.this.mConnectionState = 2;
            BleBtService.this.deviceName = bluetoothGatt.getDevice().getName();
            BleBtService.this.deviceAddress = bluetoothGatt.getDevice().getAddress();
            SPUtil.put(SPUtil.CONNECTED_DEVICE_NAME, bluetoothGatt.getDevice().getName());
            SPUtil.put(SPUtil.CONNECTED_DEVICE_ADDRESS, bluetoothGatt.getDevice().getAddress());
            BleBtService.this.broadcastUpdate(BleBtService.ACTION_GATT_CONNECTED);
            LogUtil.m339i(BleBtService.TAG, "Connected to GATT server.");
            LogUtil.m339i(BleBtService.TAG, "Attempting to start service discovery:" + BleBtService.this.deviceName + "-->" + BleBtService.this.mBluetoothGatt.discoverServices());
            BleBtService.this.mHandler.removeMessages(3000);
            BleBtService.this.mHandler.sendEmptyMessageDelayed(3000, 3000L);
            BleBtService.this.requestConnectionPriority(1);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(final BluetoothGatt bluetoothGatt, int i, int i2) {
            LogUtil.m339i(BleBtService.TAG, "=onConnectionStateChange=" + i2);
            if (i2 == 2) {
                BleBtService.this.requestMtu(bluetoothGatt, 500);
                bluetoothGatt.requestConnectionPriority(1);
                if (BleBtService.this.isGlass) {
                    if (bluetoothGatt.getDevice().getBondState() == 10) {
                        bluetoothGatt.getDevice().createBond();
                    }
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (defaultAdapter != null) {
                        if (!defaultAdapter.isEnabled()) {
                            defaultAdapter.enable();
                        }
                        defaultAdapter.getProfileProxy(BleBtService.this.getApplicationContext(), new BluetoothProfile.ServiceListener() { // from class: com.aivox.common.ble.service.BleBtService.1.1
                            @Override // android.bluetooth.BluetoothProfile.ServiceListener
                            public void onServiceDisconnected(int i3) {
                            }

                            @Override // android.bluetooth.BluetoothProfile.ServiceListener
                            public void onServiceConnected(int i3, BluetoothProfile bluetoothProfile) {
                                BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
                                if (bluetoothA2dp != null) {
                                    try {
                                        bluetoothA2dp.getClass().getMethod("connect", BluetoothDevice.class).invoke(bluetoothA2dp, bluetoothGatt.getDevice());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, 2);
                        return;
                    }
                    return;
                }
                return;
            }
            if (i2 == 0) {
                BleBtService.this.mConnectionState = 0;
                BleBtService.this.mBluetoothGatt.close();
                BleBtService.this.mBluetoothGatt.disconnect();
                if (BleBtService.this.mBluetoothGatt != null) {
                    BleBtService.this.mBluetoothGatt.disconnect();
                    BleBtService.this.mBluetoothGatt.close();
                    BleBtService.this.mBluetoothGatt = null;
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException unused) {
                    }
                }
                LogUtil.m339i(BleBtService.TAG, "Disconnected from GATT server." + i2);
                DataHandle.getIns().setHasConnectedBle(false);
                EventBus.getDefault().post(new EventBean(301));
                BleBtService.this.broadcastUpdate(BleBtService.ACTION_GATT_DISCONNECTED);
                BleBtService.this.update(false);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            LogUtil.m339i(BleBtService.TAG, "onServicesDiscovered = " + i);
            if (i == 0) {
                LogUtil.m339i(BleBtService.TAG, "onServicesDiscovered:GATT_SUCCESS mBluetoothGatt = " + BleBtService.this.mBluetoothGatt);
                BleBtService.this.broadcastUpdate(BleBtService.ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                LogUtil.m339i(BleBtService.TAG, "onServicesDiscovered received: " + i);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            LogUtil.m339i(BleBtService.TAG, "=onCharacteristicRead=");
            if (i == 0) {
                LogUtil.m338i("=onCharacteristicRead received:=".concat(new String(bluetoothGattCharacteristic.getValue())));
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            String str = new String(value, StandardCharsets.UTF_8);
            if (str.startsWith(Constant.CmdDownHead)) {
                BleBtService.this.handleReceivedData(str);
                return;
            }
            if (BleUtil.validatePacket(value)) {
                int length = value.length - 5;
                final byte[] bArr = new byte[length];
                System.arraycopy(value, 3, bArr, 0, length);
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.common.ble.service.BleBtService$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m344x8adc73e4(bArr);
                    }
                });
                return;
            }
            if (DataHandle.getIns().isLength401()) {
                int length2 = value.length - 1;
                byte[] bArr2 = new byte[length2];
                System.arraycopy(value, 1, bArr2, 0, length2);
                BleDataManager.getInstance().writeData(bArr2);
                BleAudioDataUpManager.getInstance().resetIntervalIfNeeded(value[0]);
                return;
            }
            BleDataManager.getInstance().writeData(value);
        }

        /* JADX INFO: renamed from: lambda$onCharacteristicChanged$0$com-aivox-common-ble-service-BleBtService$1 */
        /* synthetic */ void m344x8adc73e4(byte[] bArr) {
            try {
                BleBtService.this.parserGlassesData(bArr);
            } catch (Exception e) {
                LogUtil.m337e(BleBtService.TAG, e.getLocalizedMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceivedData(String str) {
        if (str.startsWith(Constant.CmdDownBatteryLevel)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(302, str.trim()));
            return;
        }
        if (str.startsWith(Constant.CmdDownHeartbeat)) {
            this.receivePongCount++;
            this.awaitingPong = false;
            return;
        }
        if (str.startsWith(Constant.CmdDownEndCall)) {
            EventBus.getDefault().post(new EventBean(304));
            EventBus.getDefault().post(new EventBean(315));
            LogUtil.m335d(TAG, "ble_receive:" + str);
            return;
        }
        if (str.startsWith(Constant.CmdDownEarphoneVersion)) {
            EventBus.getDefault().post(new EventBean(305, str.trim()));
            parseVersion(str.trim());
            LogUtil.m335d(TAG, "ble_receive:" + str);
            return;
        }
        if (str.startsWith(Constant.CmdDownLeftStart)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(309));
            return;
        }
        if (str.startsWith(Constant.CmdDownLeftEnd)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(310));
            return;
        }
        if (str.startsWith(Constant.CmdDownRightStart)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(311));
            return;
        }
        if (str.startsWith(Constant.CmdDownRightEnd)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(312));
            return;
        }
        if (str.startsWith(Constant.CmdDownRequestMode)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(303, str));
            return;
        }
        if (str.startsWith(Constant.CmdDownActiveEnd)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(313));
            return;
        }
        if (str.startsWith(Constant.CmdDownCallingE) || str.startsWith(Constant.CmdDownCallingF) || str.startsWith(Constant.CmdDownCalling) || str.startsWith(Constant.CmdDownStartCallE)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(314));
            return;
        }
        if (str.startsWith(Constant.CmdDownNoCall)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(315));
        } else if (str.startsWith(Constant.CmdDownOpenWiFi)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_OPEN_WIFI, str.trim()));
        } else if (str.startsWith(Constant.CmdDownCloseWiFi)) {
            LogUtil.m335d(TAG, "ble_receive:" + str);
            EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CLOSE_WIFI, str.trim()));
        } else {
            LogUtil.m335d(TAG, "ble_receive:" + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void parserGlassesData(byte[] bArr) {
        byte[] bArr2;
        int i = (bArr[1] << 8) | (bArr[0] & UByte.MAX_VALUE);
        if (i == GlassesCmd.GET_DEVICE_INFO.getCmd()) {
            if (bArr.length > 10) {
                int i2 = (bArr[7] << 8) | (bArr[6] & UByte.MAX_VALUE);
                int i3 = (bArr[8] & UByte.MAX_VALUE) | (bArr[9] << 8);
                byte b = bArr[10];
                LogUtil.m335d(TAG, "客户ID:" + i2 + " 产品ID:" + i3 + " 颜色:" + ((int) b));
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_GET_DEVICE_INFO, i2, i3, b));
            }
        } else if (i == GlassesCmd.GET_PRODUCT_MODEL.getCmd()) {
            if (bArr.length > 6) {
                int length = bArr.length - 6;
                byte[] bArr3 = new byte[length];
                System.arraycopy(bArr, 6, bArr3, 0, length);
                String str = new String(bArr3, StandardCharsets.US_ASCII);
                LogUtil.m335d(TAG, "产品型号:".concat(str));
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_GET_PRODUCT_MODEL, str));
            }
        } else if (i == GlassesCmd.GET_VERSION.getCmd()) {
            if (bArr.length > 6) {
                int length2 = bArr.length - 6;
                byte[] bArr4 = new byte[length2];
                System.arraycopy(bArr, 6, bArr4, 0, length2);
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_BT_VERSION, ((int) bArr4[0]) + "." + ((int) bArr4[1]) + "." + ((int) bArr4[2])));
            }
        } else if (i == GlassesCmd.GET_HARDWARE_INFO.getCmd()) {
            if (bArr.length > 6) {
                int length3 = bArr.length - 6;
                byte[] bArr5 = new byte[length3];
                System.arraycopy(bArr, 6, bArr5, 0, length3);
                String str2 = new String(bArr5, StandardCharsets.US_ASCII);
                LogUtil.m335d(TAG, "硬件信息:".concat(str2));
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_HARDWARE_INFO, str2));
            }
        } else if (i == GlassesCmd.GET_SUPPORTED_FEATURES.getCmd()) {
            if (bArr.length > 6) {
                this.isSupportFeature = true;
                String str3 = "降噪:" + (bArr[6] == 1);
                if (bArr.length > 7) {
                    str3 = str3 + " 佩戴检测开关:" + (bArr[7] == 1);
                    if (bArr.length > 8) {
                        str3 = str3 + " 游戏模式:" + (bArr[8] == 1);
                        if (bArr.length > 9) {
                            str3 = str3 + " EQ 音效:" + (bArr[9] == 1);
                            if (bArr.length > 10) {
                                str3 = str3 + " 按键设置:" + (bArr[10] == 1);
                                if (bArr.length > 11) {
                                    str3 = str3 + " 设备查找:" + (bArr[11] == 1);
                                    if (bArr.length > 12) {
                                        str3 = str3 + " AI 对话:" + (bArr[12] == 1);
                                        if (bArr.length > 13) {
                                            str3 = str3 + " WIFI 眼镜功能:" + (bArr[13] == 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                LogUtil.m335d(TAG, str3);
            }
        } else if (i == GlassesCmd.GET_DEVICE_NAME.getCmd()) {
            if (bArr.length > 6) {
                int length4 = bArr.length - 6;
                byte[] bArr6 = new byte[length4];
                System.arraycopy(bArr, 6, bArr6, 0, length4);
                LogUtil.m335d(TAG, "设备名称：".concat(new String(bArr6, StandardCharsets.US_ASCII)));
            }
        } else if (i == GlassesCmd.HEART_BEAT.getCmd()) {
            this.receivePongCount++;
            this.awaitingPong = false;
        } else if (i == GlassesCmd.GET_BATTERY.getCmd() || i == GlassesCmd.REPORT_BATTERY.getCmd()) {
            if (bArr.length > 6) {
                if (this.isSupportFeature) {
                    this.isSupportFeature = false;
                    return;
                }
                byte b2 = bArr[6];
                if (b2 == 1 && bArr.length > 7) {
                    byte b3 = bArr[7];
                    boolean z = (b3 & 128) != 0;
                    int i4 = b3 & ByteCompanionObject.MAX_VALUE;
                    LogUtil.m335d(TAG, "主电量：" + i4 + "%，是否在充电：" + z);
                    EventBus.getDefault().post(new EventBean(302, "cmdd05-" + i4));
                    SPUtil.put(SPUtil.GLASS_BATTERY, Integer.valueOf(i4));
                    SPUtil.put(SPUtil.GLASS_IS_CHARGING, Boolean.valueOf(z));
                } else if (b2 == 2 && bArr.length > 8) {
                    EventBus.getDefault().post(new EventBean(302, "cmdd05-" + (bArr[7] & ByteCompanionObject.MAX_VALUE) + "-" + (bArr[8] & ByteCompanionObject.MAX_VALUE)));
                } else if (b2 == 3 && bArr.length > 9) {
                    EventBus.getDefault().post(new EventBean(302, "cmdd05-" + (bArr[7] & ByteCompanionObject.MAX_VALUE) + "-" + (bArr[8] & ByteCompanionObject.MAX_VALUE) + "-" + (bArr[9] & ByteCompanionObject.MAX_VALUE)));
                }
            }
        } else if (i == GlassesCmd.GET_NOISE_CONTROL_STATUS.getCmd() || i == GlassesCmd.REPORT_NOISE_STATUS.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "当前降噪模式：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.GET_WEARING_STATUS.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "当前佩戴检测状态：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.GET_GAME_MODE.getCmd() || i == GlassesCmd.REPORT_GAME_MODE.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "游戏模式：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.GET_EQ.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "当前EQ音效：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.GET_BUTTON_CONFIG.getCmd()) {
            if (bArr.length > 6) {
                for (int i5 = 6; i5 < bArr.length; i5++) {
                    byte b4 = bArr[i5];
                    LogUtil.m335d(TAG, "按键:" + ((b4 & UByte.MAX_VALUE) << 8) + " 功能:" + (b4 & UByte.MAX_VALUE));
                }
            }
        } else if (i == GlassesCmd.DEVICE_AI_MODE.getCmd()) {
            if (bArr.length > 6) {
                byte b5 = bArr[6];
                LogUtil.m335d(TAG, "AI 对话模式开关：" + ((int) b5));
                EventBean eventBean = new EventBean(403, GlassesCmd.DEVICE_AI_MODE);
                eventBean.setA(b5);
                EventBus.getDefault().post(eventBean);
            }
        } else if (i == GlassesCmd.AI_MODE_EVENT.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "AI 实时语音对话事件触发：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.AI_MODE_VOICE_EVENT_TRIGGER.getCmd()) {
            if (bArr.length > 6) {
                byte b6 = bArr[6];
                LogUtil.m335d(TAG, "AI 对话模式开关：" + ((int) b6));
                EventBean eventBean2 = new EventBean(403, GlassesCmd.AI_MODE_VOICE_EVENT_TRIGGER);
                eventBean2.setA(b6);
                EventBus.getDefault().post(eventBean2);
            }
        } else if (i == GlassesCmd.REPORT_WIFI_STATUS.getCmd()) {
            if (bArr.length > 10) {
                byte b7 = bArr[6];
                String str4 = Byte.toUnsignedInt(bArr[7]) + "." + Byte.toUnsignedInt(bArr[8]) + "." + Byte.toUnsignedInt(bArr[9]) + "." + Byte.toUnsignedInt(bArr[10]);
                LogUtil.m335d(TAG, "WIFI 连接状态：" + ((int) b7) + " IP地址：" + str4);
                if (b7 == 1) {
                    EventBean eventBean3 = new EventBean(403, GlassesCmd.REPORT_WIFI_STATUS);
                    eventBean3.setS1(str4);
                    EventBus.getDefault().post(eventBean3);
                }
            }
        } else if (i == GlassesCmd.FILE_COUNT_UPDATE.getCmd() || i == GlassesCmd.GET_FILE_COUNT.getCmd()) {
            if (bArr.length > 6) {
                int i6 = (bArr[7] << 8) | (bArr[6] & UByte.MAX_VALUE);
                if (bArr.length > 8) {
                    int length5 = bArr.length - 8;
                    byte[] bArr7 = new byte[length5];
                    System.arraycopy(bArr, 8, bArr7, 0, length5);
                    LogUtil.m335d(TAG, "未导入文件个数：" + i6 + "，导入文件 API：" + new String(bArr7, StandardCharsets.US_ASCII));
                } else {
                    LogUtil.m335d(TAG, "未导入文件个数：" + i6);
                }
                EventBean eventBean4 = new EventBean(403, GlassesCmd.FILE_COUNT_UPDATE);
                eventBean4.setA(i6);
                EventBus.getDefault().post(eventBean4);
            }
        } else if (i == GlassesCmd.IMAGE_CAPTURE_FINISH.getCmd()) {
            if (bArr.length > 6) {
                byte b8 = bArr[6];
                if (b8 == 1) {
                    int length6 = bArr.length - 7;
                    byte[] bArr8 = new byte[length6];
                    System.arraycopy(bArr, 7, bArr8, 0, length6);
                    String str5 = new String(bArr8, StandardCharsets.US_ASCII);
                    LogUtil.m335d(TAG, "图像识别拍照完成：".concat(str5));
                    EventBean eventBean5 = new EventBean(403, GlassesCmd.IMAGE_CAPTURE_FINISH);
                    eventBean5.setA(b8);
                    eventBean5.setS1(str5);
                    EventBus.getDefault().post(eventBean5);
                } else if (b8 == 2) {
                    LogUtil.m335d(TAG, "图像识别拍照完成：" + ((int) bArr[7]));
                    EventBean eventBean6 = new EventBean(403, GlassesCmd.IMAGE_CAPTURE_FINISH);
                    eventBean6.setA(b8);
                    eventBean6.setB(bArr[7]);
                    EventBus.getDefault().post(eventBean6);
                }
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.IMAGE_CAPTURE_FINISH));
        } else if (i == GlassesCmd.VIDEO_API.getCmd()) {
            if (bArr.length > 6) {
                int length7 = bArr.length - 6;
                byte[] bArr9 = new byte[length7];
                System.arraycopy(bArr, 6, bArr9, 0, length7);
                String str6 = new String(bArr9, StandardCharsets.US_ASCII);
                LogUtil.m335d(TAG, "实时视频地址：".concat(str6));
                EventBean eventBean7 = new EventBean(403, GlassesCmd.VIDEO_API);
                eventBean7.setS1(str6);
                EventBus.getDefault().post(eventBean7);
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.VIDEO_API));
        } else if (i == GlassesCmd.DISK_CAPACITY.getCmd()) {
            if (bArr.length > 6) {
                int length8 = bArr.length - 6;
                byte[] bArr10 = new byte[length8];
                System.arraycopy(bArr, 6, bArr10, 0, length8);
                String str7 = new String(bArr10, StandardCharsets.US_ASCII);
                LogUtil.m335d(TAG, "磁盘容量 HTTP 请求 API：".concat(str7));
                EventBean eventBean8 = new EventBean(403, GlassesCmd.DISK_CAPACITY);
                eventBean8.setS1(str7);
                EventBus.getDefault().post(eventBean8);
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.DISK_CAPACITY));
        } else if (i == GlassesCmd.REPORT_AP_SSID.getCmd()) {
            if (bArr.length > 6) {
                int length9 = bArr.length - 6;
                byte[] bArr11 = new byte[length9];
                System.arraycopy(bArr, 6, bArr11, 0, length9);
                String str8 = new String(bArr11, StandardCharsets.US_ASCII);
                if (!TextUtils.isEmpty(str8)) {
                    LogUtil.m335d(TAG, "AP SSID：".concat(str8));
                    EventBean eventBean9 = new EventBean(403, GlassesCmd.REPORT_AP_SSID);
                    eventBean9.setS1(str8);
                    EventBus.getDefault().post(eventBean9);
                }
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.REPORT_AP_SSID));
        } else if (i == GlassesCmd.REPORT_AP_PASSWORD.getCmd()) {
            if (bArr.length > 6) {
                int length10 = bArr.length - 6;
                byte[] bArr12 = new byte[length10];
                System.arraycopy(bArr, 6, bArr12, 0, length10);
                String str9 = new String(bArr12, StandardCharsets.US_ASCII);
                if (!TextUtils.isEmpty(str9)) {
                    LogUtil.m335d(TAG, "AP 密码：".concat(str9));
                    EventBean eventBean10 = new EventBean(403, GlassesCmd.REPORT_AP_PASSWORD);
                    eventBean10.setS1(str9);
                    EventBus.getDefault().post(eventBean10);
                }
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.REPORT_AP_PASSWORD));
        } else if (i == GlassesCmd.REPORT_WIFI_API.getCmd()) {
            if (bArr.length > 6) {
                int length11 = bArr.length - 6;
                byte[] bArr13 = new byte[length11];
                System.arraycopy(bArr, 6, bArr13, 0, length11);
                String str10 = new String(bArr13, StandardCharsets.US_ASCII);
                LogUtil.m335d(TAG, "wifi 操作 API：".concat(str10));
                EventBean eventBean11 = new EventBean(403, GlassesCmd.REPORT_WIFI_API);
                eventBean11.setS1(str10);
                EventBus.getDefault().post(eventBean11);
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.REPORT_WIFI_API));
        } else if (i == GlassesCmd.GET_BINDING_CODE.getCmd()) {
            if (bArr.length > 6) {
                int length12 = bArr.length - 6;
                byte[] bArr14 = new byte[length12];
                System.arraycopy(bArr, 6, bArr14, 0, length12);
                LogUtil.m335d(TAG, "获取绑定码：".concat(new String(bArr14, StandardCharsets.US_ASCII)));
            }
        } else if (i == GlassesCmd.CLEAR_RESULT_UPLOAD.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "清除结果上报：" + ((int) bArr[6]));
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.CLEAR_RESULT_UPLOAD));
        } else if (i == GlassesCmd.GET_ANTI_SHAKE.getCmd()) {
            if (bArr.length > 6) {
                byte b9 = bArr[6];
                LogUtil.m335d(TAG, "获取防抖设置：" + ((int) bArr[6]));
                EventBean eventBean12 = new EventBean(403, GlassesCmd.GET_ANTI_SHAKE);
                eventBean12.setA(b9);
                EventBus.getDefault().post(eventBean12);
            }
        } else if (i == GlassesCmd.GET_CALL_STATUS.getCmd()) {
            if (bArr.length > 6) {
                LogUtil.m335d(TAG, "获取通话状态：" + ((int) bArr[6]));
            }
        } else if (i == GlassesCmd.BLE_AUDIO_DATA.getCmd()) {
            if (bArr.length > 6) {
                int length13 = bArr.length - 7;
                byte[] bArr15 = new byte[length13];
                System.arraycopy(bArr, 7, bArr15, 0, length13);
                BleDataManager.getInstance().writeData(bArr15);
            }
        } else if (i == GlassesCmd.GET_OTA_INFO.getCmd()) {
            if (bArr.length > 6) {
                byte b10 = bArr[6];
                byte b11 = bArr[7];
                LogUtil.m335d(TAG, String.format("获取 OTA 升级信息: fileCount=%d protocol=%d protocol2=%d", Integer.valueOf(b10), Integer.valueOf(b11), Byte.valueOf(bArr[8])));
                EventBean eventBean13 = new EventBean(403, GlassesCmd.GET_OTA_INFO);
                eventBean13.setA(b10);
                eventBean13.setB(b11);
                EventBus.getDefault().post(eventBean13);
            }
        } else if (i == GlassesCmd.REPORT_HARDWARE_VERSION.getCmd()) {
            if (bArr.length > 6) {
                byte b12 = bArr[6];
                byte b13 = bArr[7];
                String str11 = ((int) bArr[8]) + "." + ((int) bArr[9]) + "." + ((int) bArr[10]);
                LogUtil.m335d(TAG, String.format("获取 OTA 版本: fileCount=%d protocol=%d version=" + str11, Integer.valueOf(b12), Integer.valueOf(b13)));
                EventBean eventBean14 = new EventBean(403, GlassesCmd.REPORT_HARDWARE_VERSION);
                eventBean14.setA(b12);
                eventBean14.setB(b13);
                eventBean14.setS1(str11);
                EventBus.getDefault().post(eventBean14);
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_WIFI_VERSION, str11));
            }
        } else if (i == GlassesCmd.COMPLETE_OTA.getCmd()) {
            LogUtil.m335d(TAG, "OTA 升级完成");
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.COMPLETE_OTA));
        } else if (i == GlassesCmd.START_GET_FILE.getCmd()) {
            if (bArr.length > 6) {
                long j = (((long) bArr[6]) & 255) | ((((long) bArr[7]) & 255) << 8) | ((((long) bArr[8]) & 255) << 16) | ((((long) bArr[9]) & 255) << 24);
                long j2 = (((long) bArr[10]) & 255) | ((((long) bArr[11]) & 255) << 8);
                long j3 = (((long) bArr[12]) & 255) | ((255 & ((long) bArr[13])) << 8);
                LogUtil.m335d(TAG, String.format("开始获取文件: fileSize(%d) filePackageSize(%d) allPackageCount(%d)", Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3)));
                GlassFileReceiver glassFileReceiver = this.glassFileReceiver;
                if (glassFileReceiver != null) {
                    glassFileReceiver.onStart(j, j2, j3);
                }
            } else {
                GlassFileReceiver glassFileReceiver2 = this.glassFileReceiver;
                if (glassFileReceiver2 != null) {
                    glassFileReceiver2.onError(new Exception("0x0C01 数据包长度不足6字节"));
                }
            }
        } else if (i == GlassesCmd.FILE_DATA_UPLOAD.getCmd()) {
            if (bArr.length > 6) {
                long j4 = (((long) bArr[6]) & 255) | ((((long) bArr[7]) & 255) << 8);
                long j5 = (((long) bArr[8]) & 255) | ((255 & ((long) bArr[9])) << 8);
                if (bArr.length - 10 > 0) {
                    try {
                        int length14 = bArr.length - 10;
                        byte[] bArr16 = new byte[length14];
                        System.arraycopy(bArr, 10, bArr16, 0, length14);
                        bArr2 = bArr16;
                    } catch (Exception e) {
                        e.printStackTrace();
                        bArr2 = new byte[0];
                    }
                } else {
                    bArr2 = new byte[0];
                }
                GlassFileReceiver glassFileReceiver3 = this.glassFileReceiver;
                if (glassFileReceiver3 != null) {
                    glassFileReceiver3.onProgress(j4, j5, bArr2);
                }
            } else {
                GlassFileReceiver glassFileReceiver4 = this.glassFileReceiver;
                if (glassFileReceiver4 != null) {
                    glassFileReceiver4.onError(new Exception("0x0C02 数据包长度不足6字节"));
                }
            }
        } else if (i == GlassesCmd.FILE_UPLOAD_END.getCmd()) {
            LogUtil.m335d(TAG, "上传文件结束");
            GlassFileReceiver glassFileReceiver5 = this.glassFileReceiver;
            if (glassFileReceiver5 != null) {
                glassFileReceiver5.onComplete();
            }
            EventBus.getDefault().post(new EventBean(404, GlassesCmd.FILE_UPLOAD_END));
        } else if (i == GlassesCmd.LOCAL_VIDEO_RECORD_STATUS_REPORT.getCmd()) {
            if (bArr.length > 6) {
                byte b14 = bArr[6];
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_VIDEO_RECORDING_STATUS, b14));
                LogUtil.m335d(TAG, "录像状态：" + ((int) b14));
            }
        } else {
            LogUtil.m335d(TAG, String.format("receive command(%04X) data:", Integer.valueOf(i)) + BaseStringUtil.getHexStringByteArray(bArr));
        }
    }

    public synchronized void sendGlassCmdWithResponse(GlassesCmd glassesCmd) {
        if (getInstance().isGlass) {
            writeRXCharacteristic(BleUtil.getCmdBytes(glassesCmd, true, new Object[0]), false);
        }
    }

    public synchronized void sendGlassCmd(GlassesCmd glassesCmd) {
        sendGlassCmd(glassesCmd, null);
    }

    public synchronized void sendGlassCmd(GlassesCmd glassesCmd, Object... objArr) {
        byte[] cmdBytes;
        if (getInstance().isGlass) {
            if (objArr[0] == null) {
                cmdBytes = BleUtil.getCmdBytes(glassesCmd, false, new Object[0]);
            } else {
                cmdBytes = BleUtil.getCmdBytes(glassesCmd, false, objArr);
            }
            if (BaseGlobalConfig.isDebug()) {
                StringBuilder sb = new StringBuilder();
                for (byte b : cmdBytes) {
                    sb.append(String.format("%02X", Byte.valueOf(b))).append(" ");
                }
                LogUtil.m335d(TAG, "start send data:" + ("[" + sb.toString().trim() + "]"));
            }
            writeRXCharacteristic(cmdBytes);
        }
    }

    private void parseVersion(String str) {
        if (str.startsWith(Constant.CmdDownEarphoneVersion)) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length >= 3) {
                DataHandle.getIns().setHeadsetVersion(strArrSplit[1]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastUpdate(String str) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(str));
    }

    public boolean initialize() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        if (defaultAdapter != null) {
            return true;
        }
        LogUtil.m337e(TAG, "Unable to obtain a BluetoothAdapter.");
        return false;
    }

    public void openBle(Context context) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
    }

    public void setRecording(boolean z) {
        this.mHandler.removeMessages(3000);
        if (z) {
            return;
        }
        this.mHandler.sendEmptyMessageDelayed(3000, 3000L);
    }

    public boolean connect(DeviceBean deviceBean) {
        if (this.mBluetoothAdapter == null || deviceBean == null) {
            LogUtil.m335d(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        DeviceBean deviceBean2 = this.mDeviceBean;
        if (deviceBean2 != null && deviceBean2.getAddress().equals(deviceBean.getAddress()) && this.mBluetoothGatt != null) {
            LogUtil.m335d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (!this.mBluetoothGatt.connect()) {
                return false;
            }
            this.mConnectionState = 1;
            return true;
        }
        BluetoothDevice remoteDevice = this.mBluetoothAdapter.getRemoteDevice(deviceBean.getAddress());
        if (remoteDevice == null) {
            LogUtil.m335d(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        LogUtil.m335d(TAG, "connectGatt." + Build.VERSION.SDK_INT);
        this.mBluetoothGatt = remoteDevice.connectGatt(this, false, this.mGattCallback, 2);
        LogUtil.m335d(TAG, "Trying to create a new connection.");
        this.mDeviceBean = deviceBean;
        this.mConnectionState = 1;
        return true;
    }

    public void disconnect() {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            LogUtil.m335d(TAG, "BluetoothAdapter not initialized");
            return;
        }
        update(false);
        this.mBluetoothGatt.disconnect();
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
        }
        DataHandle.getIns().setHasConnectedBle(false);
        this.mHandler.removeMessages(3000);
        LogUtil.m335d(TAG, "disconnect ");
    }

    public void close() {
        if (this.mBluetoothGatt == null) {
            return;
        }
        LogUtil.m335d(TAG, "mBluetoothGatt closed");
        this.mDeviceBean = null;
        this.mBluetoothGatt.close();
        this.mBluetoothGatt = null;
    }

    public void readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt;
        if (this.mBluetoothAdapter == null || (bluetoothGatt = this.mBluetoothGatt) == null) {
            LogUtil.m335d(TAG, "BluetoothAdapter not initialized");
        } else {
            bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
        }
    }

    public synchronized void enableTXNotification() {
        LogUtil.m339i(TAG, "=enableTXNotification=");
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            List<BluetoothGattService> services = bluetoothGatt.getServices();
            for (int i = 0; i < services.size(); i++) {
                LogUtil.m335d(TAG, services.get(i).getUuid().toString());
            }
            BluetoothGattService service2 = this.mBluetoothGatt.getService(RX_SERVICE_UUID);
            if (service2 == null) {
                showMessage("Rx service not found! read");
                broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
                return;
            }
            BluetoothGattCharacteristic characteristic = service2.getCharacteristic(TX_CHAR_UUID);
            if (characteristic == null) {
                showMessage("Tx characteristic not found!");
                broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
                return;
            }
            this.mBluetoothGatt.setCharacteristicNotification(characteristic, true);
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(CCCD);
            if (descriptor == null) {
                return;
            }
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            this.mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public void writeRXCharacteristic(byte[] bArr) {
        writeRXCharacteristic(bArr, true);
    }

    public void writeRXCharacteristic(byte[] bArr, boolean z) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            BluetoothGattService service2 = bluetoothGatt.getService(RX_SERVICE_UUID);
            if (service2 == null) {
                showMessage("Rx service not found!");
                broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
                return;
            }
            BluetoothGattCharacteristic characteristic = service2.getCharacteristic(RX_CHAR_UUID);
            if (characteristic == null) {
                showMessage("Rx characteristic not found!");
                broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
                return;
            }
            characteristic.setValue(bArr);
            characteristic.setWriteType(1);
            boolean zWriteCharacteristic = this.mBluetoothGatt.writeCharacteristic(characteristic);
            if (DataUtil.bytes2Hex(bArr).equals("636d64753030") || DataUtil.bytes2Hex(bArr).startsWith("a5060007000") || zWriteCharacteristic || !z) {
                return;
            }
            LogUtil.m337e(TAG, "writeCharacteristic fail:" + DataUtil.bytes2Hex(bArr));
            if (!getInstance().isGlass()) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.device_connect_fail));
            }
            DialogUtils.hideLoadingDialog();
        }
    }

    private void showMessage(String str) {
        LogUtil.m337e(TAG, str);
    }

    public static BleBtService getInstance() {
        if (service == null) {
            service = new BleBtService();
        }
        return service;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        service = this;
        initialize();
    }

    private boolean bleClean() {
        try {
            Method method = this.mBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (method != null) {
                return ((Boolean) method.invoke(this.mBluetoothGatt, new Object[0])).booleanValue();
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public void requestConnectionPriority(int i) {
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            LogUtil.m335d(TAG, "requestConnectionPriority : " + bluetoothGatt.requestConnectionPriority(i));
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        disconnect();
        this.mGattCallback = null;
        super.onDestroy();
    }
}
