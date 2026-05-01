package com.aivox.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.aivox.base.C0874R;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WifiConnector {
    private static final String TAG = "WifiConnector";
    private ConnectivityManager.NetworkCallback activeNetworkCallback;
    private WifiConnectionListener connectionListener;
    private final ConnectivityManager connectivityManager;
    private final Context context;
    public String currentTargetSsid;
    private boolean isWaitingForWifiEnabled = false;
    private String lastAttemptedPassword;
    private String lastAttemptedSsid;
    private final WifiManager wifiManager;
    private WifiStateChangeReceiver wifiStateChangeReceiver;

    public interface WifiConnectionListener {
        void onPermissionsRequired(List<String> list);

        void onWifiConnected(String str, Network network);

        void onWifiConnecting(String str);

        void onWifiConnectionFailed(String str, String str2);

        void onWifiDisconnected(String str, String str2);

        void onWifiEnabledRequired();
    }

    public WifiConnector(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.wifiManager = (WifiManager) applicationContext.getSystemService("wifi");
        this.connectivityManager = (ConnectivityManager) applicationContext.getSystemService("connectivity");
    }

    public void setWifiConnectionListener(WifiConnectionListener wifiConnectionListener) {
        this.connectionListener = wifiConnectionListener;
    }

    public void connectToWifi(String str, String str2) {
        disconnectAndCleanup();
        if (!this.wifiManager.isWifiEnabled()) {
            Log.w(TAG, "Wi-Fi is not enabled. Saving connection attempt.");
            this.isWaitingForWifiEnabled = true;
            this.lastAttemptedSsid = str;
            this.lastAttemptedPassword = str2;
            WifiConnectionListener wifiConnectionListener = this.connectionListener;
            if (wifiConnectionListener != null) {
                wifiConnectionListener.onWifiEnabledRequired();
                return;
            }
            return;
        }
        if (ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            Log.e(TAG, "ACCESS_FINE_LOCATION permission not granted.");
            WifiConnectionListener wifiConnectionListener2 = this.connectionListener;
            if (wifiConnectionListener2 != null) {
                wifiConnectionListener2.onPermissionsRequired(Collections.singletonList("android.permission.ACCESS_FINE_LOCATION"));
                return;
            }
            return;
        }
        this.isWaitingForWifiEnabled = false;
        this.currentTargetSsid = str;
        WifiConnectionListener wifiConnectionListener3 = this.connectionListener;
        if (wifiConnectionListener3 != null) {
            wifiConnectionListener3.onWifiConnecting(str);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            connectToWifiQAndAbove(str, str2);
        } else {
            connectToWifiPreQ(str, str2);
        }
    }

    public void retryConnectionAfterEnablingWifi() {
        String str;
        if (this.isWaitingForWifiEnabled && this.wifiManager.isWifiEnabled()) {
            Log.d(TAG, "Wi-Fi is now enabled. Retrying connection to: " + this.lastAttemptedSsid);
            this.isWaitingForWifiEnabled = false;
            String str2 = this.lastAttemptedSsid;
            if (str2 == null || (str = this.lastAttemptedPassword) == null) {
                return;
            }
            connectToWifi(str2, str);
        }
    }

    private void connectToWifiQAndAbove(String str, String str2) {
        Log.d(TAG, "Attempting to connect (Q+) to SSID: " + str);
        NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(1).removeCapability(12).setNetworkSpecifier(new WifiNetworkSpecifier.Builder().setSsid(str).setWpa2Passphrase(str2).build()).build();
        ConnectivityManager.NetworkCallback networkCallbackCreateNetworkCallback = createNetworkCallback(str);
        this.activeNetworkCallback = networkCallbackCreateNetworkCallback;
        this.connectivityManager.requestNetwork(networkRequestBuild, networkCallbackCreateNetworkCallback);
    }

    private void connectToWifiPreQ(String str, String str2) {
        Log.d(TAG, "Attempting to connect (Pre-Q) to SSID: " + str);
        List<WifiConfiguration> configuredNetworks = this.wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            Iterator<WifiConfiguration> it = configuredNetworks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WifiConfiguration next = it.next();
                if (next.SSID != null && next.SSID.equals(PunctuationConst.DOUBLE_QUOTES + str + PunctuationConst.DOUBLE_QUOTES)) {
                    this.wifiManager.removeNetwork(next.networkId);
                    break;
                }
            }
        }
        int iAddNetwork = this.wifiManager.addNetwork(createWifiConfiguration(str, str2));
        if (iAddNetwork != -1) {
            this.wifiManager.disconnect();
            boolean zEnableNetwork = this.wifiManager.enableNetwork(iAddNetwork, true);
            this.wifiManager.reconnect();
            if (zEnableNetwork) {
                Log.d(TAG, "enableNetwork successful for " + str + ". Waiting for broadcast state.");
                registerPreQWifiStateReceiver(str);
                return;
            } else {
                handleConnectionFailure(str, "enableNetwork failed");
                return;
            }
        }
        handleConnectionFailure(str, "Failed to add network configuration");
    }

    public void cleanup() {
        Log.d(TAG, "Cleaning up all WifiConnector resources.");
        disconnectAndCleanup();
        this.connectionListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectAndCleanup() {
        Log.d(TAG, "Disconnecting and cleaning up previous state...");
        ConnectivityManager.NetworkCallback networkCallback = this.activeNetworkCallback;
        if (networkCallback != null) {
            try {
                this.connectivityManager.unregisterNetworkCallback(networkCallback);
                Log.d(TAG, "Unregistered active NetworkCallback.");
            } catch (Exception e) {
                Log.w(TAG, "Error unregistering NetworkCallback: " + e.getMessage());
            }
            this.activeNetworkCallback = null;
        }
        WifiStateChangeReceiver wifiStateChangeReceiver = this.wifiStateChangeReceiver;
        if (wifiStateChangeReceiver != null) {
            try {
                this.context.unregisterReceiver(wifiStateChangeReceiver);
                Log.d(TAG, "Unregistered WifiStateChangeReceiver.");
            } catch (Exception e2) {
                Log.w(TAG, "Error unregistering WifiStateChangeReceiver: " + e2.getMessage());
            }
            this.wifiStateChangeReceiver = null;
        }
        this.currentTargetSsid = null;
        if (!this.isWaitingForWifiEnabled) {
            this.lastAttemptedSsid = null;
            this.lastAttemptedPassword = null;
        }
        this.connectivityManager.bindProcessToNetwork(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConnectionFailure(String str, String str2) {
        Log.e(TAG, "Connection to " + str + " failed: " + str2);
        WifiConnectionListener wifiConnectionListener = this.connectionListener;
        if (wifiConnectionListener != null) {
            wifiConnectionListener.onWifiConnectionFailed(str, str2);
        }
        disconnectAndCleanup();
    }

    private void promptToEnableWifi() {
        Intent intent;
        Toast.makeText(this.context, C0874R.string.enable_wifi_hint, 1).show();
        if (Build.VERSION.SDK_INT >= 29) {
            intent = new Intent("android.settings.panel.action.WIFI");
        } else {
            intent = new Intent("android.settings.WIFI_SETTINGS");
        }
        intent.addFlags(268435456);
        this.context.startActivity(intent);
    }

    private ConnectivityManager.NetworkCallback createNetworkCallback(final String str) {
        return new ConnectivityManager.NetworkCallback() { // from class: com.aivox.common.util.WifiConnector.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.i(WifiConnector.TAG, "Network available for " + str);
                if (ContextCompat.checkSelfPermission(WifiConnector.this.context, "android.permission.CHANGE_NETWORK_STATE") == 0) {
                    Log.d(WifiConnector.TAG, "Reporting network connectivity as validated (false) to system.");
                    WifiConnector.this.connectivityManager.reportNetworkConnectivity(network, true);
                } else {
                    Log.w(WifiConnector.TAG, "CHANGE_NETWORK_STATE permission not granted. Cannot report network connectivity.");
                }
                WifiConnector.this.connectivityManager.bindProcessToNetwork(network);
                if (WifiConnector.this.connectionListener != null) {
                    WifiConnector.this.connectionListener.onWifiConnected(str, network);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                super.onUnavailable();
                WifiConnector.this.handleConnectionFailure(str, "Network unavailable");
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                Log.w(WifiConnector.TAG, "Lost connection to " + str);
                if (WifiConnector.this.connectionListener != null) {
                    WifiConnector.this.connectionListener.onWifiDisconnected(str, "Connection lost");
                }
                WifiConnector.this.disconnectAndCleanup();
            }
        };
    }

    private WifiConfiguration createWifiConfiguration(String str, String str2) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = PunctuationConst.DOUBLE_QUOTES + str + PunctuationConst.DOUBLE_QUOTES;
        wifiConfiguration.preSharedKey = PunctuationConst.DOUBLE_QUOTES + str2 + PunctuationConst.DOUBLE_QUOTES;
        return wifiConfiguration;
    }

    private void registerPreQWifiStateReceiver(String str) {
        this.wifiStateChangeReceiver = new WifiStateChangeReceiver(str, this.connectionListener, new Runnable() { // from class: com.aivox.common.util.WifiConnector$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.disconnectAndCleanup();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        this.context.registerReceiver(this.wifiStateChangeReceiver, intentFilter);
        Log.d(TAG, "Registered WifiStateChangeReceiver for SSID: " + str);
    }

    private static class WifiStateChangeReceiver extends BroadcastReceiver {
        private final Runnable cleanupCallback;
        private final WifiConnectionListener listener;
        private final String targetSsidPlain;
        private final String targetSsidWithQuotes;

        WifiStateChangeReceiver(String str, WifiConnectionListener wifiConnectionListener, Runnable runnable) {
            this.targetSsidPlain = str;
            this.targetSsidWithQuotes = PunctuationConst.DOUBLE_QUOTES + str + PunctuationConst.DOUBLE_QUOTES;
            this.listener = wifiConnectionListener;
            this.cleanupCallback = runnable;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo == null || !networkInfo.isConnected()) {
                    return;
                }
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (this.targetSsidWithQuotes.equals(connectionInfo != null ? connectionInfo.getSSID() : "")) {
                    Log.i(WifiConnector.TAG, "Pre-Q: Connected to target SSID: " + this.targetSsidPlain);
                    WifiConnectionListener wifiConnectionListener = this.listener;
                    if (wifiConnectionListener != null) {
                        wifiConnectionListener.onWifiConnected(this.targetSsidPlain, null);
                    }
                    this.cleanupCallback.run();
                    return;
                }
                return;
            }
            if ("android.net.wifi.supplicant.STATE_CHANGE".equals(action) && intent.hasExtra("supplicantError") && intent.getIntExtra("supplicantError", 0) == 1) {
                Log.e(WifiConnector.TAG, "Pre-Q: Authentication error for " + this.targetSsidPlain);
                WifiConnectionListener wifiConnectionListener2 = this.listener;
                if (wifiConnectionListener2 != null) {
                    wifiConnectionListener2.onWifiConnectionFailed(this.targetSsidPlain, "Authentication error");
                }
                this.cleanupCallback.run();
            }
        }
    }
}
