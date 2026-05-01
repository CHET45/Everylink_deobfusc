package com.lzy.okgo.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lzy.okgo.utils.OkLogger;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DataBaseDao<T> {
    private SQLiteOpenHelper helper;

    public abstract ContentValues getContentValues(T t);

    protected abstract String getTableName();

    public abstract T parseCursorToBean(Cursor cursor);

    public DataBaseDao(SQLiteOpenHelper sQLiteOpenHelper) {
        this.helper = sQLiteOpenHelper;
    }

    protected final SQLiteDatabase openReader() {
        return this.helper.getReadableDatabase();
    }

    protected final SQLiteDatabase openWriter() {
        return this.helper.getWritableDatabase();
    }

    protected final void closeDatabase(SQLiteDatabase sQLiteDatabase, Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            return;
        }
        sQLiteDatabase.close();
    }

    public int count() {
        return countColumn(CacheHelper.f890ID);
    }

    public int countColumn(String str) {
        String str2 = "SELECT COUNT(?) FROM " + getTableName();
        SQLiteDatabase sQLiteDatabaseOpenReader = openReader();
        Cursor cursorRawQuery = null;
        try {
            sQLiteDatabaseOpenReader.beginTransaction();
            cursorRawQuery = sQLiteDatabaseOpenReader.rawQuery(str2, new String[]{str});
            int i = cursorRawQuery.moveToNext() ? cursorRawQuery.getInt(0) : 0;
            sQLiteDatabaseOpenReader.setTransactionSuccessful();
            return i;
        } catch (Exception e) {
            OkLogger.m864e(e);
            return 0;
        } finally {
            sQLiteDatabaseOpenReader.endTransaction();
            closeDatabase(sQLiteDatabaseOpenReader, cursorRawQuery);
        }
    }

    public int deleteAll() {
        return delete(null, null);
    }

    public int delete(String str, String[] strArr) {
        SQLiteDatabase sQLiteDatabaseOpenWriter = openWriter();
        try {
            try {
                sQLiteDatabaseOpenWriter.beginTransaction();
                int iDelete = sQLiteDatabaseOpenWriter.delete(getTableName(), str, strArr);
                sQLiteDatabaseOpenWriter.setTransactionSuccessful();
                return iDelete;
            } catch (Exception e) {
                OkLogger.m864e(e);
                sQLiteDatabaseOpenWriter.endTransaction();
                closeDatabase(sQLiteDatabaseOpenWriter, null);
                return 0;
            }
        } finally {
            sQLiteDatabaseOpenWriter.endTransaction();
            closeDatabase(sQLiteDatabaseOpenWriter, null);
        }
    }

    public List<T> getAll() {
        return get(null, null);
    }

    public List<T> get(String str, String[] strArr) {
        return get(null, str, strArr, null, null, null, null);
    }

    public List<T> get(String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5) {
        SQLiteDatabase sQLiteDatabaseOpenReader = openReader();
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                sQLiteDatabaseOpenReader.beginTransaction();
                cursorQuery = sQLiteDatabaseOpenReader.query(getTableName(), strArr, str, strArr2, str2, str3, str4, str5);
                while (!cursorQuery.isClosed() && cursorQuery.moveToNext()) {
                    arrayList.add(parseCursorToBean(cursorQuery));
                }
                sQLiteDatabaseOpenReader.setTransactionSuccessful();
            } catch (Exception e) {
                OkLogger.m864e(e);
            }
            return arrayList;
        } finally {
            sQLiteDatabaseOpenReader.endTransaction();
            closeDatabase(sQLiteDatabaseOpenReader, cursorQuery);
        }
    }

    public long replace(T t) {
        SQLiteDatabase sQLiteDatabaseOpenWriter = openWriter();
        try {
            try {
                sQLiteDatabaseOpenWriter.beginTransaction();
                long jReplace = sQLiteDatabaseOpenWriter.replace(getTableName(), null, getContentValues(t));
                sQLiteDatabaseOpenWriter.setTransactionSuccessful();
                return jReplace;
            } catch (Exception e) {
                OkLogger.m864e(e);
                sQLiteDatabaseOpenWriter.endTransaction();
                closeDatabase(sQLiteDatabaseOpenWriter, null);
                return 0L;
            }
        } finally {
            sQLiteDatabaseOpenWriter.endTransaction();
            closeDatabase(sQLiteDatabaseOpenWriter, null);
        }
    }

    public long create(T t) {
        SQLiteDatabase sQLiteDatabaseOpenWriter = openWriter();
        try {
            try {
                sQLiteDatabaseOpenWriter.beginTransaction();
                long jInsert = sQLiteDatabaseOpenWriter.insert(getTableName(), null, getContentValues(t));
                sQLiteDatabaseOpenWriter.setTransactionSuccessful();
                return jInsert;
            } catch (Exception e) {
                OkLogger.m864e(e);
                sQLiteDatabaseOpenWriter.endTransaction();
                closeDatabase(sQLiteDatabaseOpenWriter, null);
                return 0L;
            }
        } finally {
            sQLiteDatabaseOpenWriter.endTransaction();
            closeDatabase(sQLiteDatabaseOpenWriter, null);
        }
    }

    public int update(T t, String str, String[] strArr) {
        SQLiteDatabase sQLiteDatabaseOpenWriter = openWriter();
        try {
            try {
                sQLiteDatabaseOpenWriter.beginTransaction();
                int iUpdate = sQLiteDatabaseOpenWriter.update(getTableName(), getContentValues(t), str, strArr);
                sQLiteDatabaseOpenWriter.setTransactionSuccessful();
                return iUpdate;
            } catch (Exception e) {
                OkLogger.m864e(e);
                sQLiteDatabaseOpenWriter.endTransaction();
                closeDatabase(sQLiteDatabaseOpenWriter, null);
                return 0;
            }
        } finally {
            sQLiteDatabaseOpenWriter.endTransaction();
            closeDatabase(sQLiteDatabaseOpenWriter, null);
        }
    }
}
