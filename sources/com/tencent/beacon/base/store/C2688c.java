package com.tencent.beacon.base.store;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.c */
/* JADX INFO: compiled from: DBOpenHelper.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2688c extends SQLiteOpenHelper {

    /* JADX INFO: renamed from: a */
    private String f1377a;

    public C2688c(String str) {
        super(C2630c.m1059c().m1067b(), "beacon_db_" + str, (SQLiteDatabase.CursorFactory) null, 1);
        this.f1377a = "";
        C2695c.m1461a("[DB]", 0, "DBOpenHelper construc.", new Object[0]);
        this.f1377a = "beacon_db_" + str;
    }

    /* JADX INFO: renamed from: a */
    private boolean m1397a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorQuery;
        try {
            ArrayList<String> arrayList = new ArrayList();
            String[] strArr = new String[1];
            strArr[0] = "name";
            cursorQuery = sQLiteDatabase.query("sqlite_master", strArr, "type = 'table'", null, null, null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        arrayList.add(cursorQuery.getString(0));
                    } catch (Throwable th) {
                        th = th;
                        try {
                            C2624j.m1031e().m1024a("608", "[db] drop all tables error! ", th);
                            C2695c.m1465a(th);
                            if (cursorQuery != null && !cursorQuery.isClosed()) {
                                cursorQuery.close();
                            }
                            return false;
                        } finally {
                            if (cursorQuery != null && !cursorQuery.isClosed()) {
                                cursorQuery.close();
                            }
                        }
                    }
                }
            }
            if (arrayList.size() > 0) {
                for (String str : arrayList) {
                    if (!str.equals("sqlite_sequence") && !str.equals("android_metadata")) {
                        sQLiteDatabase.execSQL("drop table if exists " + str);
                        C2695c.m1461a("[DB]", 1, "[db] drop %s", str);
                    }
                }
            }
            return true;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
    }

    /* JADX INFO: renamed from: b */
    private void m1398b(SQLiteDatabase sQLiteDatabase) {
        if (m1397a(sQLiteDatabase)) {
            C2695c.m1461a("[DB]", 0, "[db] drop all success recreate!", new Object[0]);
            onCreate(sQLiteDatabase);
            return;
        }
        C2695c.m1476e("[db] drop all fail try deleted file,may next time will success!", new Object[0]);
        File databasePath = C2630c.m1059c().m1067b().getDatabasePath(this.f1377a);
        if (databasePath == null || !databasePath.canWrite()) {
            return;
        }
        databasePath.delete();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        sQLiteDatabase.setPageSize(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
        sQLiteDatabase.enableWriteAheadLogging();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        C2695c.m1461a("[DB]", 0, "DBOpenHelper onCreate.", new Object[0]);
        try {
            Iterator<String> it = C2687b.f1376a.iterator();
            while (it.hasNext()) {
                sQLiteDatabase.execSQL(it.next());
            }
        } catch (SQLException e) {
            C2624j.m1031e().m1024a("601", "error msg: " + e.getMessage(), e);
            C2695c.m1468b("[DB] crate db table error!", new Object[0]);
            C2695c.m1465a(e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        C2695c.m1461a("[DB]", 0, "[db] Downgrade a db  [%s] from v %d to  v%d , deleted all tables!", this.f1377a, Integer.valueOf(i), Integer.valueOf(i2));
        m1398b(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        C2695c.m1461a("[DB]", 0, "[db] Upgrade a db  [%s] from v %d to v %d , deleted all tables!", this.f1377a, Integer.valueOf(i), Integer.valueOf(i2));
        m1398b(sQLiteDatabase);
    }
}
