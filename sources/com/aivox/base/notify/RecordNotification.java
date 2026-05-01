package com.aivox.base.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.aivox.base.C0874R;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;

/* JADX INFO: loaded from: classes.dex */
public class RecordNotification {
    private static String CHANNEL_ID = "channel_recording";
    public static String name = "recording";
    Intent intent;
    Bundle mBundle;
    private NotificationManager manager;
    private Notification notification;
    private Notification.Builder notificationBuilder;
    private NotificationCompat.Builder notificationCompatBuilder;
    PendingIntent pendingIntent;

    public RecordNotification(Context context, Class cls, Bundle bundle) {
        LogUtil.m338i("==RecordNotification==");
        this.manager = (NotificationManager) context.getSystemService("notification");
        Intent intent = new Intent(context, (Class<?>) cls);
        this.intent = intent;
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.intent.addFlags(268435456);
        this.pendingIntent = PendingIntent.getActivity(context, 11, this.intent, 67108864);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, 3);
        notificationChannel.setDescription("");
        notificationChannel.canBypassDnd();
        notificationChannel.setBypassDnd(true);
        notificationChannel.canShowBadge();
        notificationChannel.setShowBadge(true);
        notificationChannel.setSound(null, null);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
        this.manager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(context);
        this.notificationBuilder = builder;
        Notification notificationBuild = builder.setChannelId(CHANNEL_ID).setSmallIcon(C0874R.mipmap.icon_logo).setContentTitle(context.getResources().getString(C0874R.string.notification_recording) + "00:00:00").setContentIntent(this.pendingIntent).setAutoCancel(true).build();
        this.notification = notificationBuild;
        this.manager.notify(0, notificationBuild);
    }

    public void update(String str, Context context) {
        int i;
        if (((Integer) SPUtil.get(SPUtil.RECORD_STATE, 0)).intValue() == 2) {
            i = C0874R.string.notification_record_pause;
        } else {
            i = C0874R.string.notification_recording;
        }
        if (this.notificationBuilder != null) {
            Notification.Builder builder = new Notification.Builder(context);
            this.notificationBuilder = builder;
            this.notification = builder.setChannelId(CHANNEL_ID).setSmallIcon(C0874R.mipmap.icon_logo).setContentIntent(this.pendingIntent).setContentTitle(context.getResources().getString(i) + str).build();
        }
        this.manager.notify(0, this.notification);
    }

    public void cancel() {
        LogUtil.m338i("==RecordNotification  cancel==" + (this.manager != null));
        NotificationManager notificationManager = this.manager;
        if (notificationManager != null) {
            notificationManager.cancel(0);
        }
    }
}
