package com.aivox.common_ui.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.github.houbb.heaven.constant.PunctuationConst;

/* JADX INFO: loaded from: classes.dex */
public class UpdateNotification {
    public static String CALENDAR_ID = "channel_template";
    public static String name = "template";
    int logoIds;
    private NotificationManager manager;
    private Intent messageintent = null;
    private PendingIntent messagependingintent = null;
    private Notification notification;
    private Notification.Builder notificationBuilder;
    private NotificationCompat.Builder notificationCompatBuilder;

    public UpdateNotification(Context context) {
        Log.i("tag", "==MyNotification==");
        this.manager = (NotificationManager) context.getSystemService("notification");
        NotificationChannel notificationChannel = new NotificationChannel(CALENDAR_ID, name, 3);
        notificationChannel.setDescription("");
        notificationChannel.canBypassDnd();
        notificationChannel.setBypassDnd(true);
        notificationChannel.canShowBadge();
        notificationChannel.setShowBadge(true);
        notificationChannel.setSound(null, null);
        notificationChannel.enableLights(false);
        notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
        notificationChannel.enableVibration(false);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        this.manager.createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(context);
        this.notificationBuilder = builder;
        Notification notificationBuild = builder.setChannelId(CALENDAR_ID).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), this.logoIds)).setSmallIcon(this.logoIds).setContentTitle("正在下载，当前进度:0%").setProgress(100, 0, false).setNumber(5).build();
        this.notification = notificationBuild;
        this.manager.notify(0, notificationBuild);
    }

    public void update(int i, Context context, int i2) {
        if (this.notificationBuilder != null) {
            Notification.Builder builder = new Notification.Builder(context);
            this.notificationBuilder = builder;
            this.notification = builder.setChannelId(CALENDAR_ID).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), i2)).setSmallIcon(i2).setContentTitle("正在下载，当前进度:" + i + PunctuationConst.PERCENT).setProgress(100, i, false).setNumber(5).build();
        }
        this.manager.notify(0, this.notification);
    }

    public void cancel() {
        NotificationManager notificationManager = this.manager;
        if (notificationManager != null) {
            notificationManager.cancel(0);
        }
    }
}
