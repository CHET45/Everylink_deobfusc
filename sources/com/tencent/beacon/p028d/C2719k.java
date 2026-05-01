package com.tencent.beacon.p028d;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.lzy.okgo.cache.CacheHelper;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.module.StrategyModule;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.Locale;

/* JADX INFO: renamed from: com.tencent.beacon.d.k */
/* JADX INFO: compiled from: StrategyUtils.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2719k {

    /* JADX INFO: renamed from: a */
    private static String f1505a;

    /* JADX INFO: renamed from: com.tencent.beacon.d.k$a */
    /* JADX INFO: compiled from: StrategyUtils.java */
    public static class a extends SQLiteOpenHelper {
        public a(Context context, String str) {
            super(context, TextUtils.isEmpty(str) ? "beacon_db" : "beacon_db_" + str, (SQLiteDatabase.CursorFactory) null, 30);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(String.format(Locale.US, "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s int unique , %s int , %s blob)", "t_strategy", CacheHelper.f890ID, "_key", "_ut", "_datas"));
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            C2695c.m1464a("[db] Upgrade a db  [%s] from v %d to v %d , deleted all tables!", "beacon_db", Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    /* JADX INFO: renamed from: a */
    public static String m1611a() {
        if (!TextUtils.isEmpty(f1505a)) {
            return f1505a;
        }
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        f1505a = sharedPreferencesC2686aM1391a.getString("initsdkdate", "");
        if (!C2694b.m1458e().equals(f1505a)) {
            SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("initsdkdate", C2694b.m1458e());
            }
        }
        return f1505a;
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1612b() {
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        int i = C2694b.m1458e().equals(m1611a()) ? sharedPreferencesC2686aM1391a.getInt("QUERY_TIMES_KEY", 0) : 0;
        if (i > C2709a.m1508a().m1513c()) {
            C2695c.m1474d("[strategy] sdk init max times", new Object[0]);
            return true;
        }
        int i2 = i + 1;
        SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
        if (!C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
            return false;
        }
        aVarEdit.putInt("QUERY_TIMES_KEY", i2);
        return false;
    }

    /* JADX INFO: renamed from: c */
    public static boolean m1613c() {
        C2710b c2710bM1784b = ((StrategyModule) C2630c.m1059c().m1060a(ModuleName.STRATEGY)).m1784b();
        if (c2710bM1784b.m1521B()) {
            SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = ((jCurrentTimeMillis / 60000) + 480) % 1440;
            if (j >= 0 && j <= 30 && jCurrentTimeMillis - sharedPreferencesC2686aM1391a.getLong("last_success_strategy_query_time", 0L) <= 90000000) {
                return true;
            }
            if (C2694b.m1458e().equals(m1611a())) {
                return sharedPreferencesC2686aM1391a.getInt("today_success_strategy_query_times", 0) >= c2710bM1784b.m1560n();
            }
            SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putInt("today_success_strategy_query_times", 0);
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: d */
    public static void m1614d() {
        C2710b c2710bM1784b = ((StrategyModule) C2630c.m1059c().m1060a(ModuleName.STRATEGY)).m1784b();
        if (c2710bM1784b == null || !c2710bM1784b.m1521B()) {
            return;
        }
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        int i = sharedPreferencesC2686aM1391a.getInt("today_success_strategy_query_times", 0) + 1;
        SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
        if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
            aVarEdit.putInt("today_success_strategy_query_times", i).putLong("last_success_strategy_query_time", System.currentTimeMillis());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v8, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX INFO: renamed from: a */
    public static synchronized C2718j m1609a(Context context, int i) {
        Exception e;
        SQLiteDatabase sQLiteDatabase;
        C2718j c2718j;
        Cursor cursor = null;
        c2718jM1610a = null;
        C2718j c2718jM1610a = null;
        cursor = null;
        Cursor cursor2 = null;
        cursor = null;
        if (context == 0) {
            C2695c.m1476e("[db] context is null", new Object[0]);
            return null;
        }
        try {
            try {
                context = new a(context, C2630c.m1059c().m1072e()).getWritableDatabase();
                try {
                } catch (Exception e2) {
                    e = e2;
                    c2718j = null;
                    sQLiteDatabase = context;
                }
            } catch (Exception e3) {
                e = e3;
                sQLiteDatabase = 0;
                c2718j = null;
            } catch (Throwable th) {
                th = th;
                context = 0;
            }
            if (context == 0) {
                C2695c.m1476e("[db] getWritableDatabase fail!", new Object[0]);
                if (context != 0 && context.isOpen()) {
                    context.close();
                }
                return null;
            }
            Cursor cursorQuery = context.query("t_strategy", null, String.format(Locale.US, " %s = %d ", "_key", Integer.valueOf(i)), null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext() && (c2718jM1610a = m1610a(cursorQuery)) != null) {
                        C2695c.m1464a("[db] read strategy key: %d", Integer.valueOf(c2718jM1610a.f1503b));
                    }
                } catch (Exception e4) {
                    e = e4;
                    cursor2 = cursorQuery;
                    c2718j = null;
                    sQLiteDatabase = context;
                    C2624j.m1031e().m1023a("605", "[db] TB: t_strategy query fail!");
                    C2695c.m1465a(e);
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.close();
                    }
                    if (sQLiteDatabase != 0 && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                    c2718jM1610a = c2718j;
                } catch (Throwable th2) {
                    cursor = cursorQuery;
                    th = th2;
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    if (context != 0 && context.isOpen()) {
                        context.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null && !cursorQuery.isClosed()) {
                cursorQuery.close();
            }
            if (context != 0 && context.isOpen()) {
                context.close();
            }
            return c2718jM1610a;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX INFO: renamed from: a */
    private static C2718j m1610a(Cursor cursor) {
        if (cursor == null || cursor.isBeforeFirst() || cursor.isAfterLast()) {
            return null;
        }
        C2695c.m1464a("[db] parse bean.", new Object[0]);
        C2718j c2718j = new C2718j();
        c2718j.f1502a = cursor.getLong(cursor.getColumnIndex(CacheHelper.f890ID));
        c2718j.f1503b = cursor.getInt(cursor.getColumnIndex("_key"));
        c2718j.f1504c = cursor.getBlob(cursor.getColumnIndex("_datas"));
        return c2718j;
    }
}
