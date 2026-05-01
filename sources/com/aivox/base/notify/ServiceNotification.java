package com.aivox.base.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class ServiceNotification extends Notification {
    private static String CHANNEL_ID = "app_keeplive_service";
    public static String name = "running";
    private NotificationManager manager;
    private Notification notification;
    private Notification.Builder notificationBuilder;
    private NotificationCompat.Builder notificationCompatBuilder;
    PendingIntent pendingIntent;

    public ServiceNotification(Context context, Class cls) {
        LogUtil.m338i("==MyNotification==");
        this.manager = (NotificationManager) context.getSystemService("notification");
        this.pendingIntent = PendingIntent.getActivity(context, 1001, new Intent(context, (Class<?>) cls), 67108864);
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
        Notification notificationBuild = builder.setChannelId(CHANNEL_ID).setLargeIcon(BitmapFactory.decodeResource(BaseAppUtils.getContext().getResources(), C0874R.mipmap.icon_logo)).setSmallIcon(C0874R.mipmap.icon_logo).setContentIntent(this.pendingIntent).setNumber(1).build();
        this.notification = notificationBuild;
        this.manager.notify(0, notificationBuild);
    }

    public void cancel() {
        NotificationManager notificationManager = this.manager;
        if (notificationManager != null) {
            notificationManager.cancel(0);
        }
    }
}
