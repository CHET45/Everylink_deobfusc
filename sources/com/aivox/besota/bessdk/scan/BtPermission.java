package com.aivox.besota.bessdk.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;
import com.azure.core.util.polling.implementation.PollingConstants;
import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.hjq.permissions.Permission;

/* JADX INFO: loaded from: classes.dex */
public class BtPermission extends Activity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public Context mContext;

    public BtPermission(Context context) {
        this.mContext = context;
    }

    public boolean checkConditions() {
        return initPermission() && initLocation();
    }

    private boolean initPermission() {
        if (ActivityCompat.checkSelfPermission(this.mContext, Permission.ACCESS_COARSE_LOCATION) == 0) {
            return true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.mContext, Permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this.mContext).setMessage("Location permission is needed after Android 6.0. Apply the location permission firstly?").setNegativeButton(XmlConsts.XML_SA_NO, (DialogInterface.OnClickListener) null).setPositiveButton(XmlConsts.XML_SA_YES, new DialogInterface.OnClickListener() { // from class: com.aivox.besota.bessdk.scan.BtPermission.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    ActivityCompat.requestPermissions((Activity) BtPermission.this.mContext, new String[]{Permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_BACKGROUND_LOCATION}, 1);
        }
        return false;
    }

    private boolean initLocation() {
        if (((LocationManager) this.mContext.getSystemService(PollingConstants.LOCATION_LOWER_CASE)).isProviderEnabled("gps")) {
            return true;
        }
        new AlertDialog.Builder(this.mContext).setMessage("Location service is needed after Android 6.0. Turn to the setting view to enable this service?").setNegativeButton(XmlConsts.XML_SA_NO, (DialogInterface.OnClickListener) null).setPositiveButton(XmlConsts.XML_SA_YES, new DialogInterface.OnClickListener() { // from class: com.aivox.besota.bessdk.scan.BtPermission.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                BtPermission.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        }).create().show();
        return false;
    }
}
