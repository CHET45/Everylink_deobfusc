package com.aivox.besota.bessdk;

import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BesSdkConstants {
    public static final String BES_BLE_INTERVAL = "BES_BLE_INTERVAL";
    public static final String BES_CONNECT_CHARACTERISTIC = "BES_CONNECT_CHARACTERISTIC";
    public static final String BES_CONNECT_DESCRIPTOR = "BES_CONNECT_DESCRIPTOR";
    public static final int BES_CONNECT_ERROR = 444;
    public static final String BES_CONNECT_SERVICE = "CONNECT_SERVICE";
    public static final int BES_CONNECT_SUCCESS = 666;
    public static final int BES_NOTIFY_ERROR = 445;
    public static final int BES_NOTIFY_SUCCESS = 667;
    public static final int BES_NOTI_MSG_TIMEOUT_TOTA_START = 1025;
    public static final String BES_SAVE_LOG_KEY = "BES_SAVE_LOG";
    public static final String BES_SAVE_LOG_NAME = "BES_SAVE_LOG_NAME";
    public static final String BES_SAVE_LOG_OTA = "BESOTA.txt";
    public static final boolean BES_SAVE_LOG_VALUE = true;
    public static final String BES_SPP_INTERVAL = "BES_SPP_INTERVAL";
    public static final int BES_TOTA_CONFIRM = 769;
    public static final String BES_TOTA_ENCRYPTION_KEY = "BES_TOTA_ENCRYPTION_KEY";
    public static final boolean BES_TOTA_ENCRYPTION_VALUE = true;
    public static final int BES_TOTA_ERROR = 771;
    public static final int BES_TOTA_START = 768;
    public static final int BES_TOTA_SUCCESS = 770;
    public static final String BES_TOTA_USE_TOTAV2 = "BES_TOTA_USE_TOTAV2";
    public static final boolean BES_TOTA_USE_TOTAV2_VALUE = true;
    public static final String BES_USE_PHY_2M = "BES_USE_PHY_2M";
    public static final boolean BES_USE_PHY_2MVALUE = true;
    public static final String BES_default_INTERVAL = "BES_default_INTERVAL";
    public static final boolean BES_default_INTERVAL_VALUE = true;
    public static final String CUSTOMER_CHARACTERISTIC_RX_UUID = "CUSTOMER_CHARACTERISTIC_RX_UUID";
    public static final String CUSTOMER_CHARACTERISTIC_TX_UUID = "CUSTOMER_CHARACTERISTIC_TX_UUID";
    public static final String CUSTOMER_DESCRIPTOR_UUID = "CUSTOMER_DESCRIPTOR_UUID";
    public static final String CUSTOMER_SERVICE_UUID = "CUSTOMER_SERVICE_UUID";
    public static final int DEFAULT_MTU = 512;
    public static final int MSG_PARAMETER_ERROR = 1029;
    public static final int MSG_TIME_OUT = 1028;
    public static final String TOTA_LOG = "TOTA_LOG_INFO";
    public static final int TOTA_LOG_INFO = 772;
    public static final UUID BES_OTA_SERVICE_OTA_UUID_OLD = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final UUID BES_OTA_SERVICE_OTA_UUID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    public static final UUID BES_OTA_CHARACTERISTIC_OTA_UUID = UUID.fromString("77777777-7777-7777-7777-777777777777");
    public static final UUID BES_OTA_DESCRIPTOR_OTA_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID BES_TOTA_SERVICE_OTA_UUID = UUID.fromString("86868686-8686-8686-8686-868686868686");
    public static final UUID BES_TOTA_CHARACTERISTI_OTA_UUID = UUID.fromString("97979797-9797-9797-9797-979797979797");
    public static final UUID BES_TOTA_DESCRIPTOR_OTA_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID BES_GATT_SERVICE_UUID = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID BES_GATT_DESCRIPTOR_OTA_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID BES_SPP_CONNECT = UUID.fromString("5a3aa4d1-41b9-4da3-9820-ed330885b29f");
    public static final UUID THROUGHPUT_SERVICE_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f820000");
    public static final UUID THROUGHPUT_CHARACTERISTIC_TX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f820002");
    public static final UUID THROUGHPUT_CHARACTERISTIC_RX_UUID = UUID.fromString("65786365-6c70-6f69-6e74-2e636f820001");
    public static final UUID BES_DATA_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    public enum BesConnectState {
        BES_CONFIG_ERROR,
        BES_NO_CONNECT,
        BES_CONNECT_NOTOTA,
        BES_CONNECT_TOTA,
        BES_CONNECT
    }
}
