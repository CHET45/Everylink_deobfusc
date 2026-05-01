package com.aivox.jieliota.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.aivox.base.util.BaseAppUtils;
import com.hjq.permissions.Permission;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class AppUtil {
    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private static int clickCount;
    private static long lastClickTime;
    private static int theClickCount;
    private static long theLastClickTime;

    public static int changeConnectStatus(int i) {
        if (i != 1) {
            return i != 2 ? 0 : 1;
        }
        return 3;
    }

    public static boolean isHasLocationPermission(Context context) {
        return isHasPermission(context, Permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean isHasStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return isHasPermission(context, "android.permission.READ_EXTERNAL_STORAGE");
        }
        return isHasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") && isHasPermission(context, "android.permission.READ_EXTERNAL_STORAGE");
    }

    public static boolean checkHasConnectPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 31) {
            return isHasPermission(context, "android.permission.BLUETOOTH_CONNECT");
        }
        return true;
    }

    public static boolean checkHasScanPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 31) {
            return isHasPermission(context, "android.permission.BLUETOOTH_SCAN");
        }
        return true;
    }

    public static boolean isHasPermission(Context context, String str) {
        return context != null && ActivityCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(2000L);
    }

    public static boolean isFastDoubleClick(long j) {
        long time = new Date().getTime();
        boolean z = time - lastClickTime <= j;
        lastClickTime = time;
        return z;
    }

    public static int isFastContinuousClick() {
        return isFastContinuousClick(2000L);
    }

    public static int isFastContinuousClick(long j) {
        Log.d("ZHM", "isFastContinuousClick: " + clickCount);
        long time = new Date().getTime();
        if (time - lastClickTime <= j) {
            clickCount++;
        } else {
            clickCount = 1;
        }
        lastClickTime = time;
        return clickCount;
    }

    public static boolean isFastContinuousClick(long j, int i) {
        long time = new Date().getTime();
        if (time - theLastClickTime <= j) {
            theClickCount++;
        } else {
            theClickCount = 1;
        }
        theLastClickTime = time;
        boolean z = theClickCount == i;
        if (z) {
            theLastClickTime = 0L;
            theClickCount = 0;
        }
        return z;
    }

    public static boolean enableBluetooth(Context context) {
        BluetoothAdapter defaultAdapter;
        if (!checkHasConnectPermission(context) || (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            return false;
        }
        boolean zIsEnabled = defaultAdapter.isEnabled();
        return !zIsEnabled ? defaultAdapter.enable() : zIsEnabled;
    }

    public static boolean refreshBleDeviceCache(Context context, BluetoothGatt bluetoothGatt) {
        if (bluetoothGatt != null && checkHasConnectPermission(context)) {
            try {
                return bluetoothGatt.getClass().getMethod("refresh", new Class[0]).invoke(bluetoothGatt, new Object[0]) == Boolean.TRUE;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deviceHasProfile(Context context, BluetoothDevice bluetoothDevice, UUID uuid) {
        ParcelUuid[] uuids;
        if (BluetoothUtil.isBluetoothEnable() && bluetoothDevice != null && uuid != null && !TextUtils.isEmpty(uuid.toString()) && checkHasConnectPermission(context) && (uuids = bluetoothDevice.getUuids()) != null && uuids.length != 0) {
            for (ParcelUuid parcelUuid : uuids) {
                if (uuid.toString().toLowerCase(Locale.getDefault()).equalsIgnoreCase(parcelUuid.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getDeviceName(Context context, BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !checkHasConnectPermission(context)) {
            return "N/A";
        }
        String name = bluetoothDevice.getName();
        return TextUtils.isEmpty(name) ? "N/A" : name;
    }

    public static int getDeviceType(Context context, BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !checkHasConnectPermission(context)) {
            return 0;
        }
        return bluetoothDevice.getType();
    }

    public static String printBtDeviceInfo(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(BaseAppUtils.getContext(), bluetoothDevice);
    }

    public static void printBleGattServices(Context context, BluetoothDevice bluetoothDevice, BluetoothGatt bluetoothGatt, int i) {
        if (bluetoothDevice == null || bluetoothGatt == null || !checkHasConnectPermission(context) || !JL_Log.isIsLog()) {
            return;
        }
        JL_Log.m844d("ble", String.format(Locale.getDefault(), "[[============================Bluetooth[%s], Discovery Services status[%d]=================================]]\n", BluetoothUtil.printBtDeviceInfo(context, bluetoothDevice), Integer.valueOf(i)));
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services != null) {
            JL_Log.m844d("ble", "[[======Service Size:" + services.size() + "======================\n");
            for (BluetoothGattService bluetoothGattService : services) {
                if (bluetoothGattService != null) {
                    JL_Log.m844d("ble", "[[======Service:" + bluetoothGattService.getUuid() + "======================\n");
                    List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
                    if (characteristics != null) {
                        JL_Log.m844d("ble", "[[[[=============characteristics Size:" + characteristics.size() + "======================\n");
                        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : characteristics) {
                            if (bluetoothGattCharacteristic != null) {
                                JL_Log.m844d("ble", "[[[[=============characteristic:" + bluetoothGattCharacteristic.getUuid() + ",write type : " + bluetoothGattCharacteristic.getWriteType() + "======================\n");
                                List<BluetoothGattDescriptor> descriptors = bluetoothGattCharacteristic.getDescriptors();
                                if (descriptors != null) {
                                    JL_Log.m844d("ble", "[[[[[[=============descriptors Size:" + descriptors.size() + "======================\n");
                                    for (BluetoothGattDescriptor bluetoothGattDescriptor : descriptors) {
                                        if (bluetoothGattDescriptor != null) {
                                            JL_Log.m844d("ble", "[[[[[[=============descriptor:" + bluetoothGattDescriptor.getUuid() + ",permission:" + bluetoothGattDescriptor.getPermissions() + "\nvalue : " + CHexConver.byte2HexStr(bluetoothGattDescriptor.getValue()) + "======================\n");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        JL_Log.m844d("ble", "[[============================Bluetooth[" + BluetoothUtil.printBtDeviceInfo(context, bluetoothDevice) + "] Services show End=================================]]\n");
    }

    public static String createFilePath(Context context, String... strArr) {
        File externalFilesDir;
        if (context == null || strArr == null || strArr.length == 0 || (externalFilesDir = context.getExternalFilesDir(null)) == null || !externalFilesDir.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder(externalFilesDir.getPath());
        int i = 0;
        if (sb.toString().endsWith("/")) {
            sb = new StringBuilder(sb.substring(0, sb.lastIndexOf("/")));
        }
        int length = strArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            sb.append("/").append(strArr[i]);
            File file = new File(sb.toString());
            if ((!file.exists() || file.isFile()) && !file.mkdir()) {
                JL_Log.m852w("jieli", "create dir failed. filePath = " + ((Object) sb));
                break;
            }
            i++;
        }
        return sb.toString();
    }

    public static String createDownloadFolderFilePath(Context context, String... strArr) {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + OtaConstant.DIR_ROOT + File.separator + OtaConstant.DIR_UPGRADE;
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            JL_Log.m852w("jieli", "create dir failed. filePath = " + str);
        }
        return str.toString();
    }

    public static String getFileNameByPath(String str) {
        return (!TextUtils.isEmpty(str) && str.contains("/")) ? str.substring(str.lastIndexOf("/") + 1) : str;
    }
}
