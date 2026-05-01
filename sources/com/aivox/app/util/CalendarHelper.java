package com.aivox.app.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import com.aivox.base.common.Constant;
import com.azure.core.implementation.logging.LoggingKeys;
import com.lzy.okgo.cache.CacheHelper;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class CalendarHelper {
    private static final String TAG = "CalendarHelper";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void addEvent(final Context context, final String str, final String str2, final Long l, final Long l2) {
        this.executor.execute(new Runnable() { // from class: com.aivox.app.util.CalendarHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2377lambda$addEvent$0$comaivoxapputilCalendarHelper(context, l, l2, str, str2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addEvent$0$com-aivox-app-util-CalendarHelper, reason: not valid java name */
    /* synthetic */ void m2377lambda$addEvent$0$comaivoxapputilCalendarHelper(Context context, Long l, Long l2, String str, String str2) {
        long primaryCalendarId = getPrimaryCalendarId(context);
        if (primaryCalendarId == -1) {
            Log.e(TAG, "未找到有效的日历账户");
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("dtstart", l);
        contentValues.put("dtend", l2);
        contentValues.put(Constant.KEY_TITLE, str);
        contentValues.put("description", str2);
        contentValues.put("calendar_id", Long.valueOf(primaryCalendarId));
        contentValues.put("eventTimezone", TimeZone.getDefault().getID());
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uriInsert = contentResolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);
            if (uriInsert != null) {
                long j = Long.parseLong((String) Objects.requireNonNull(uriInsert.getLastPathSegment()));
                Log.d("Calendar", "事件创建成功，ID: " + j);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("event_id", Long.valueOf(j));
                contentValues2.put(LoggingKeys.HTTP_METHOD_KEY, (Integer) 1);
                contentValues2.put("minutes", (Integer) 60);
                Log.d("Calendar", "提醒添加结果: " + contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, contentValues2));
            }
        } catch (SecurityException e) {
            Log.e(TAG, "权限不足，请检查是否申请了 WRITE_CALENDAR 权限", e);
        } catch (Exception e2) {
            Log.e(TAG, "添加事件失败", e2);
        }
    }

    private long getPrimaryCalendarId(Context context) {
        try {
            Cursor cursorQuery = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, new String[]{CacheHelper.f890ID, "isPrimary"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        int columnIndex = cursorQuery.getColumnIndex(CacheHelper.f890ID);
                        int columnIndex2 = cursorQuery.getColumnIndex("isPrimary");
                        if (columnIndex == -1) {
                            Log.e(TAG, "无法获取日历ID列");
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return -1L;
                        }
                        do {
                            if (columnIndex2 != -1 && cursorQuery.getInt(columnIndex2) == 1) {
                                long j = cursorQuery.getLong(columnIndex);
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                return j;
                            }
                        } while (cursorQuery.moveToNext());
                        cursorQuery.moveToFirst();
                        long j2 = cursorQuery.getLong(columnIndex);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return j2;
                    }
                } catch (Throwable th) {
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (SecurityException unused) {
            Log.e(TAG, "读取日历权限被拒绝");
        } catch (Exception e) {
            Log.e(TAG, "查询日历账户时发生错误", e);
        }
        return -1L;
    }
}
