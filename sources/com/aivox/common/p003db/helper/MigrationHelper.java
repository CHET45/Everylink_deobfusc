package com.aivox.common.p003db.helper;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.microsoft.azure.storage.core.C2391SR;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public final class MigrationHelper {
    public static boolean DEBUG = false;
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";
    private static String TAG = "MigrationHelper";
    private static WeakReference<ReCreateAllTableListener> weakListener;

    public interface ReCreateAllTableListener {
        void onCreateAllTables(Database database, boolean z);

        void onDropAllTables(Database database, boolean z);
    }

    @SafeVarargs
    public static void migrate(SQLiteDatabase sQLiteDatabase, Class<? extends AbstractDao<?, ?>>... clsArr) {
        printLog("【The Old Database Version】" + sQLiteDatabase.getVersion());
        migrate(new StandardDatabase(sQLiteDatabase), clsArr);
    }

    @SafeVarargs
    public static void migrate(SQLiteDatabase sQLiteDatabase, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... clsArr) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(sQLiteDatabase, clsArr);
    }

    @SafeVarargs
    public static void migrate(Database database, ReCreateAllTableListener reCreateAllTableListener, Class<? extends AbstractDao<?, ?>>... clsArr) {
        weakListener = new WeakReference<>(reCreateAllTableListener);
        migrate(database, clsArr);
    }

    @SafeVarargs
    public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        printLog("【Generate temp table】start");
        generateTempTables(database, clsArr);
        printLog("【Generate temp table】complete");
        WeakReference<ReCreateAllTableListener> weakReference = weakListener;
        ReCreateAllTableListener reCreateAllTableListener = weakReference != null ? weakReference.get() : null;
        if (reCreateAllTableListener != null) {
            reCreateAllTableListener.onDropAllTables(database, true);
            printLog("【Drop all table by listener】");
            reCreateAllTableListener.onCreateAllTables(database, false);
            printLog("【Create all table by listener】");
        } else {
            dropAllTables(database, true, clsArr);
            createAllTables(database, false, clsArr);
        }
        printLog("【Restore data】start");
        restoreData(database, clsArr);
        printLog("【Restore data】complete");
    }

    @SafeVarargs
    private static void generateTempTables(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        for (Class<? extends AbstractDao<?, ?>> cls : clsArr) {
            DaoConfig daoConfig = new DaoConfig(database, cls);
            String str = daoConfig.tablename;
            if (!isTableExists(database, false, str)) {
                printLog("【New Table】" + str);
            } else {
                String strConcat = null;
                try {
                    strConcat = daoConfig.tablename.concat("_TEMP");
                    StringBuilder sb = new StringBuilder();
                    sb.append("DROP TABLE IF EXISTS ").append(strConcat).append(";");
                    database.execSQL(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CREATE TEMPORARY TABLE ").append(strConcat);
                    sb2.append(" AS SELECT * FROM `").append(str).append("`;");
                    database.execSQL(sb2.toString());
                    printLog("【Table】" + str + "\n ---Columns-->" + getColumnsStr(daoConfig));
                    printLog("【Generate temp table】" + strConcat);
                } catch (SQLException e) {
                    Log.e(TAG, "【Failed to generate temp table】" + strConcat, e);
                }
            }
        }
    }

    private static boolean isTableExists(Database database, boolean z, String str) {
        int i;
        if (database == null || TextUtils.isEmpty(str)) {
            return false;
        }
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = database.rawQuery("SELECT COUNT(*) FROM `" + (z ? SQLITE_TEMP_MASTER : SQLITE_MASTER) + "` WHERE type = ? AND name = ?", new String[]{C2391SR.TABLE, str});
            } catch (Exception e) {
                e.printStackTrace();
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                i = 0;
            }
            if (cursorRawQuery != null && cursorRawQuery.moveToFirst()) {
                i = cursorRawQuery.getInt(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return i > 0;
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
    }

    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            sb.append(daoConfig.allColumns[i]);
            sb.append(PunctuationConst.COMMA);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @SafeVarargs
    private static void dropAllTables(Database database, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        reflectMethod(database, "dropTable", z, clsArr);
        printLog("【Drop all table by reflect】");
    }

    @SafeVarargs
    private static void createAllTables(Database database, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        reflectMethod(database, "createTable", z, clsArr);
        printLog("【Create all table by reflect】");
    }

    @SafeVarargs
    private static void reflectMethod(Database database, String str, boolean z, Class<? extends AbstractDao<?, ?>>... clsArr) {
        if (clsArr.length < 1) {
            return;
        }
        try {
            for (Class<? extends AbstractDao<?, ?>> cls : clsArr) {
                cls.getDeclaredMethod(str, Database.class, Boolean.TYPE).invoke(null, database, Boolean.valueOf(z));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }

    @SafeVarargs
    private static void restoreData(Database database, Class<? extends AbstractDao<?, ?>>... clsArr) {
        String str;
        for (Class<? extends AbstractDao<?, ?>> cls : clsArr) {
            DaoConfig daoConfig = new DaoConfig(database, cls);
            String str2 = daoConfig.tablename;
            String strConcat = daoConfig.tablename.concat("_TEMP");
            if (isTableExists(database, true, strConcat)) {
                try {
                    List<TableInfo> tableInfo = TableInfo.getTableInfo(database, str2);
                    List<TableInfo> tableInfo2 = TableInfo.getTableInfo(database, strConcat);
                    ArrayList arrayList = new ArrayList(tableInfo.size());
                    ArrayList arrayList2 = new ArrayList(tableInfo.size());
                    for (TableInfo tableInfo3 : tableInfo2) {
                        if (tableInfo.contains(tableInfo3)) {
                            String str3 = '`' + tableInfo3.name + '`';
                            arrayList2.add(str3);
                            arrayList.add(str3);
                        }
                    }
                    for (TableInfo tableInfo4 : tableInfo) {
                        if (tableInfo4.notnull && !tableInfo2.contains(tableInfo4)) {
                            String str4 = '`' + tableInfo4.name + '`';
                            arrayList2.add(str4);
                            if (tableInfo4.dfltValue != null) {
                                str = PunctuationConst.SINGLE_QUOTES + tableInfo4.dfltValue + "' AS ";
                            } else {
                                str = "'' AS ";
                            }
                            arrayList.add(str + str4);
                        }
                    }
                    if (arrayList2.size() != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("REPLACE INTO `").append(str2).append("` (");
                        sb.append(TextUtils.join(PunctuationConst.COMMA, arrayList2));
                        sb.append(") SELECT ");
                        sb.append(TextUtils.join(PunctuationConst.COMMA, arrayList));
                        sb.append(" FROM ").append(strConcat).append(";");
                        database.execSQL(sb.toString());
                        printLog("【Restore data】 to " + str2);
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("DROP TABLE ").append(strConcat);
                    database.execSQL(sb2.toString());
                    printLog("【Drop temp table】" + strConcat);
                } catch (SQLException e) {
                    Log.e(TAG, "【Failed to restore data from temp table 】" + strConcat, e);
                }
            }
        }
    }

    private static List<String> getColumns(Database database, String str) throws Throwable {
        Cursor cursor = null;
        listAsList = null;
        List<String> listAsList = null;
        cursor = null;
        try {
            try {
                Cursor cursorRawQuery = database.rawQuery("SELECT * FROM " + str + " limit 0", null);
                if (cursorRawQuery != null) {
                    try {
                        if (cursorRawQuery.getColumnCount() > 0) {
                            listAsList = Arrays.asList(cursorRawQuery.getColumnNames());
                        }
                    } catch (Exception e) {
                        e = e;
                        cursor = cursorRawQuery;
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return new ArrayList();
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorRawQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        new ArrayList();
                        throw th;
                    }
                }
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return listAsList == null ? new ArrayList() : listAsList;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void printLog(String str) {
        if (DEBUG) {
            Log.d(TAG, str);
        }
    }

    private static class TableInfo {
        int cid;
        String dfltValue;
        String name;
        boolean notnull;

        /* JADX INFO: renamed from: pk */
        boolean f214pk;
        String type;

        private TableInfo() {
        }

        public boolean equals(Object obj) {
            return this == obj || (obj != null && getClass() == obj.getClass() && this.name.equals(((TableInfo) obj).name));
        }

        public String toString() {
            return "TableInfo{cid=" + this.cid + ", name='" + this.name + "', type='" + this.type + "', notnull=" + this.notnull + ", dfltValue='" + this.dfltValue + "', pk=" + this.f214pk + '}';
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static List<TableInfo> getTableInfo(Database database, String str) {
            String str2 = "PRAGMA table_info(`" + str + "`)";
            MigrationHelper.printLog(str2);
            Cursor cursorRawQuery = database.rawQuery(str2, null);
            if (cursorRawQuery == null) {
                return new ArrayList();
            }
            ArrayList arrayList = new ArrayList();
            while (cursorRawQuery.moveToNext()) {
                TableInfo tableInfo = new TableInfo();
                boolean z = false;
                tableInfo.cid = cursorRawQuery.getInt(0);
                tableInfo.name = cursorRawQuery.getString(1);
                tableInfo.type = cursorRawQuery.getString(2);
                tableInfo.notnull = cursorRawQuery.getInt(3) == 1;
                tableInfo.dfltValue = cursorRawQuery.getString(4);
                if (cursorRawQuery.getInt(5) == 1) {
                    z = true;
                }
                tableInfo.f214pk = z;
                arrayList.add(tableInfo);
            }
            cursorRawQuery.close();
            return arrayList;
        }
    }
}
