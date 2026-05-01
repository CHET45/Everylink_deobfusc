package com.lzy.okgo.cache;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.utils.OkLogger;

/* JADX INFO: loaded from: classes4.dex */
class CacheHelper extends SQLiteOpenHelper {
    public static final String DATA = "data";
    public static final String DB_CACHE_NAME = "okgo_cache.db";
    public static final int DB_CACHE_VERSION = 3;
    public static final String HEAD = "head";

    /* JADX INFO: renamed from: ID */
    public static final String f890ID = "_id";
    public static final String KEY = "key";
    public static final String LOCAL_EXPIRE = "localExpire";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE cache_table(_id INTEGER PRIMARY KEY AUTOINCREMENT, key VARCHAR, localExpire INTEGER, head BLOB, data BLOB)";
    private static final String SQL_CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX cache_unique_index ON cache_table(\"key\")";
    private static final String SQL_DELETE_TABLE = "DROP TABLE cache_table";
    private static final String SQL_DELETE_UNIQUE_INDEX = "DROP INDEX cache_unique_index";
    public static final String TABLE_NAME = "cache_table";

    public CacheHelper() {
        super(OkGo.getContext(), DB_CACHE_NAME, (SQLiteDatabase.CursorFactory) null, 3);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.beginTransaction();
        try {
            try {
                sQLiteDatabase.execSQL(SQL_CREATE_TABLE);
                sQLiteDatabase.execSQL(SQL_CREATE_UNIQUE_INDEX);
                sQLiteDatabase.setTransactionSuccessful();
            } catch (Exception e) {
                OkLogger.m864e(e);
            }
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 != i) {
            sQLiteDatabase.beginTransaction();
            try {
                try {
                    sQLiteDatabase.execSQL(SQL_DELETE_UNIQUE_INDEX);
                    sQLiteDatabase.execSQL(SQL_DELETE_TABLE);
                    sQLiteDatabase.execSQL(SQL_CREATE_TABLE);
                    sQLiteDatabase.execSQL(SQL_CREATE_UNIQUE_INDEX);
                    sQLiteDatabase.setTransactionSuccessful();
                } catch (Exception e) {
                    OkLogger.m864e(e);
                }
            } finally {
                sQLiteDatabase.endTransaction();
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(sQLiteDatabase, i, i2);
    }
}
