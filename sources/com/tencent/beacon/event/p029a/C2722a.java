package com.tencent.beacon.event.p029a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.lzy.okgo.cache.CacheHelper;
import com.tencent.beacon.base.store.C2688c;
import com.tencent.beacon.base.store.InterfaceC2689d;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.p031c.C2730b;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p028d.C2710b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.event.a.a */
/* JADX INFO: compiled from: EventDAO.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2722a implements InterfaceC2689d<EventBean> {

    /* JADX INFO: renamed from: a */
    private static volatile C2722a f1512a;

    /* JADX INFO: renamed from: b */
    private final SQLiteStatement f1513b;

    /* JADX INFO: renamed from: c */
    private final SQLiteStatement f1514c;

    /* JADX INFO: renamed from: d */
    private final Object f1515d = new Object();

    /* JADX INFO: renamed from: e */
    private final Object f1516e = new Object();

    /* JADX INFO: renamed from: f */
    private C2730b f1517f = C2730b.m1629a();

    /* JADX INFO: renamed from: g */
    private SQLiteDatabase f1518g;

    /* JADX INFO: renamed from: h */
    private SQLiteDatabase f1519h;

    /* JADX INFO: renamed from: i */
    private long f1520i;

    /* JADX INFO: renamed from: j */
    private long f1521j;

    private C2722a() {
        C2688c c2688c = new C2688c(C2629b.m1049c(C2630c.m1059c().m1067b()));
        this.f1518g = c2688c.getWritableDatabase();
        SQLiteDatabase readableDatabase = c2688c.getReadableDatabase();
        this.f1519h = readableDatabase;
        this.f1513b = readableDatabase.compileStatement("INSERT INTO t_r_e (_appKey,_time,_length,_data )VALUES(?,?,?,?)");
        this.f1514c = this.f1519h.compileStatement("INSERT INTO t_n_e (_appKey,_time,_length,_data )VALUES(?,?,?,?)");
        this.f1520i = m1618a("t_r_e");
        long jM1618a = m1618a("t_n_e");
        this.f1521j = jM1618a;
        if (this.f1520i == 0 && jM1618a == 0) {
            return;
        }
        String str = " realtime: " + this.f1520i + ", normal: " + this.f1521j;
        C2695c.m1463a("[EventDAO]", str, new Object[0]);
        C2624j.m1031e().m1023a("607", "[EventDAO]" + str);
    }

    /* JADX INFO: renamed from: a */
    public static C2722a m1615a() {
        if (f1512a == null) {
            synchronized (C2722a.class) {
                if (f1512a == null) {
                    f1512a = new C2722a();
                }
            }
        }
        return f1512a;
    }

    /* JADX INFO: renamed from: b */
    public Map<String, Integer> m1623b(String str) {
        HashMap map = new HashMap();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.f1519h.rawQuery(str.equals("t_r_e") ? "SELECT ? , count(?) FROM t_r_e GROUP BY ?" : str.equals("t_n_e") ? "SELECT ? , count(?) FROM t_n_e GROUP BY ?" : "", new String[]{"_appKey", "_appKey", "_appKey"});
                if (cursorRawQuery.moveToFirst()) {
                    do {
                        map.put(cursorRawQuery.getString(0), Integer.valueOf(cursorRawQuery.getInt(1)));
                    } while (cursorRawQuery.moveToNext());
                }
                if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
            } catch (Exception e) {
                C2624j.m1031e().m1024a("605", "type: " + str + " query err: " + e.getMessage(), e);
                C2695c.m1465a(e);
                if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
            }
            return map;
        } catch (Throwable th) {
            if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1621a(EventBean eventBean) {
        boolean z = false;
        if (eventBean == null) {
            return false;
        }
        C2723b c2723bMo1161a = this.f1517f.m1630b().mo1161a(eventBean);
        boolean zM1640a = C2732d.m1640a(eventBean.getEventType());
        try {
            try {
                this.f1518g.beginTransactionNonExclusive();
                this.f1513b.clearBindings();
                if (zM1640a) {
                    this.f1513b.bindString(1, c2723bMo1161a.f1525d);
                    this.f1513b.bindLong(2, c2723bMo1161a.f1523b);
                    this.f1513b.bindLong(3, c2723bMo1161a.f1524c);
                    this.f1513b.bindBlob(4, c2723bMo1161a.f1526e);
                } else {
                    this.f1514c.bindString(1, c2723bMo1161a.f1525d);
                    this.f1514c.bindLong(2, c2723bMo1161a.f1523b);
                    this.f1514c.bindLong(3, c2723bMo1161a.f1524c);
                    this.f1514c.bindBlob(4, c2723bMo1161a.f1526e);
                }
                if (zM1640a) {
                    if (this.f1513b.executeInsert() >= 0) {
                        z = true;
                    }
                } else if (this.f1514c.executeInsert() >= 0) {
                    z = true;
                }
            } catch (Exception e) {
                C2624j.m1031e().m1024a("603", "type: " + zM1640a + " insert err: " + e.getMessage(), e);
                C2695c.m1465a(e);
                try {
                    this.f1518g.setTransactionSuccessful();
                    this.f1518g.endTransaction();
                } catch (Exception e2) {
                    C2624j.m1031e().m1024a("603", "type: " + zM1640a + " insert err: " + e2.getMessage(), e2);
                    C2695c.m1465a(e2);
                }
            }
            if (z) {
                m1617a(zM1640a, true, 1L);
            } else {
                C2624j.m1031e().m1023a("603", "type: " + zM1640a + " insert result: false");
            }
            return z;
        } finally {
            try {
                this.f1518g.setTransactionSuccessful();
                this.f1518g.endTransaction();
            } catch (Exception e3) {
                C2624j.m1031e().m1024a("603", "type: " + zM1640a + " insert err: " + e3.getMessage(), e3);
                C2695c.m1465a(e3);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public List<EventBean> m1619a(String str, String str2, int i) {
        List<EventBean> listM1616a;
        String str3;
        try {
            if (str.equals("t_r_e")) {
                str3 = " SELECT * FROM t_r_e WHERE ? NOT IN (?) ORDER BY ? DESC LIMIT ?";
            } else {
                str3 = str.equals("t_n_e") ? " SELECT * FROM t_n_e WHERE ? NOT IN (?) ORDER BY ? DESC LIMIT ?" : "";
            }
            SQLiteDatabase sQLiteDatabase = this.f1519h;
            String[] strArr = new String[4];
            strArr[0] = CacheHelper.f890ID;
            strArr[1] = str2;
            strArr[2] = "_time";
            strArr[3] = i + "";
            listM1616a = m1616a(str, sQLiteDatabase.rawQuery(str3, strArr));
        } catch (Exception e) {
            C2695c.m1465a(e);
            C2624j.m1031e().m1024a("605", "type: " + str + " query err: " + e.getMessage(), e);
            listM1616a = null;
        }
        C2695c.m1463a("[EventDAO]", "query tableName: %s, args: %s", str, str2);
        return listM1616a;
    }

    /* JADX INFO: renamed from: a */
    public long m1618a(String str) {
        String str2;
        Cursor cursorRawQuery = null;
        try {
            try {
                if (str.equals("t_r_e")) {
                    str2 = " SELECT count(?) FROM t_r_e";
                } else {
                    str2 = str.equals("t_n_e") ? " SELECT count(?) FROM t_n_e" : "";
                }
                String[] strArr = new String[1];
                strArr[0] = CacheHelper.f890ID;
                cursorRawQuery = this.f1519h.rawQuery(str2, strArr);
                cursorRawQuery.moveToFirst();
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery == null || cursorRawQuery.isClosed()) {
                    return j;
                }
                cursorRawQuery.close();
                return j;
            } catch (Exception e) {
                C2624j.m1031e().m1024a("605", "type: " + str + " query err: " + e.getMessage(), e);
                C2695c.m1465a(e);
                if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
                return -1L;
            }
        } catch (Throwable th) {
            if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1622a(String str, String str2) {
        try {
            int iDelete = this.f1518g.delete(str, "_id IN (" + str2 + ")", null);
            m1617a(str.equals("t_r_e"), false, iDelete);
            return iDelete >= 0;
        } catch (Exception e) {
            C2624j.m1031e().m1024a("606", "type: " + str + " delete err: " + e.getMessage() + " target: " + str2, e);
            C2695c.m1465a(e);
            return false;
        }
    }

    /* JADX INFO: renamed from: a */
    private List<EventBean> m1616a(String str, Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (cursor.moveToNext()) {
            C2723b c2723b = new C2723b();
            c2723b.f1522a = cursor.getLong(0);
            c2723b.f1525d = cursor.getString(1);
            c2723b.f1523b = cursor.getInt(2);
            c2723b.f1524c = cursor.getLong(3);
            c2723b.f1526e = cursor.getBlob(4);
            EventBean eventBeanMo1161a = this.f1517f.m1631c().mo1161a(c2723b);
            if (eventBeanMo1161a == null) {
                m1622a(str, String.valueOf(c2723b.f1522a));
                C2695c.m1468b("transformEventBean error, eventBean is null! delete this event.", new Object[0]);
            } else {
                arrayList.add(eventBeanMo1161a);
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: a */
    private void m1617a(boolean z, boolean z2, long j) {
        if (z) {
            synchronized (this.f1515d) {
                if (z2) {
                    this.f1520i += j;
                } else {
                    this.f1520i -= j;
                }
                C2695c.m1463a("[EventDAO]", "current db realtime:%s", Long.valueOf(this.f1520i));
            }
            return;
        }
        synchronized (this.f1516e) {
            if (z2) {
                this.f1521j += j;
            } else {
                this.f1521j -= j;
            }
            C2695c.m1463a("[EventDAO]", "current db normal:%s", Long.valueOf(this.f1521j));
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1620a(int i) {
        boolean z;
        if (i == 1) {
            synchronized (this.f1515d) {
                z = this.f1520i >= ((long) C2710b.m1518d().m1547f());
            }
            return z;
        }
        synchronized (this.f1516e) {
            z = this.f1521j >= ((long) C2710b.m1518d().m1547f());
        }
        return z;
    }
}
