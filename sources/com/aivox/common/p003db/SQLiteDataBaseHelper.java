package com.aivox.common.p003db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LNT_DB";
    private static final int DATABASE_VERSION = 9;
    public static final String TABLE_LOCALFILEPATH = "localfilepath_table";
    public static final String TABLE_LOCALFILEPATH_ = "localfilepath_table_";
    public static final String TABLE_TOP = "top_table";
    public static final String TABLE_TOP_ = "top_table_";
    public static final String TABLE_USER = "user_table";
    public static final String TABLE_USER_ = "user_table_";
    private static SQLiteDataBaseHelper ins;

    public SQLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 9);
    }

    public static synchronized SQLiteDataBaseHelper getIns(Context context) {
        if (ins == null) {
            ins = new SQLiteDataBaseHelper(context);
        }
        return ins;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        LogUtil.m338i("====db oncreate====");
        try {
            sQLiteDatabase.execSQL("create table IF NOT EXISTS user_table (_id integer primary key autoincrement, user_id varchar(10),nickname varchar(30),img varchar(100),birthday varchar(20),gender varchar(2),usedstorage varchar(20),totalstorage varchar(20),point varchar(10),phone varchar(20),email varchar(50),intCode varchar(10),isAutomaticUpload varchar(2),isDeleteAfterUpload varchar(2),invitationCode varchar(20),vip varchar(2),vipExpire varchar(20))");
            sQLiteDatabase.execSQL("create table IF NOT EXISTS top_table (_id integer primary key autoincrement, title varchar(200),addtime varchar(20),filetime varchar(50),local_path varchar(200),uid varchar(10))");
            sQLiteDatabase.execSQL("create table IF NOT EXISTS localfilepath_table (_id integer primary key autoincrement, local_path varchar(200),addtime varchar(20),uid varchar(10),vid varchar(20))");
        } catch (SQLException e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("=sql-onCreate-e=" + e.getMessage());
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            LogUtil.m338i("==onUpgrade==" + i + "--" + i2);
            for (int i3 = i; i3 < i2; i3++) {
                switch (i3) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                        updateTableUser(sQLiteDatabase);
                        break;
                    case 4:
                        updateTableLocalFilePath(sQLiteDatabase);
                        break;
                    case 6:
                        updateTableUser6(sQLiteDatabase);
                        break;
                    case 7:
                        updateTableUser7(sQLiteDatabase);
                        break;
                    case 8:
                        updateTableUser8(sQLiteDatabase);
                        break;
                }
            }
        } catch (SQLException e) {
            BaseAppUtils.printErrorMsg(e, "aql-onUpgrade:from:" + i + "to" + i2);
            LogUtil.m338i("=sql-onUpgrade-e=" + e.getMessage());
        }
    }

    private void updateTableUser(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("alter table user_table rename to user_table_");
        onCreate(sQLiteDatabase);
        sQLiteDatabase.execSQL("insert into user_table select _id,user_id,nickname,img,birthday,gender,usedstorage,totalstorage,point,phone,email,intCode,'' from user_table_");
        sQLiteDatabase.execSQL(" drop table user_table_");
    }

    private void updateTableUser6(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("alter table user_table rename to user_table_");
        onCreate(sQLiteDatabase);
        sQLiteDatabase.execSQL("insert into user_table select _id,user_id,nickname,img,birthday,gender,usedstorage,totalstorage,point,phone,email,intCode,isAutomaticUpload,'' from user_table_");
        sQLiteDatabase.execSQL(" drop table user_table_");
    }

    private void updateTableUser7(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("alter table user_table rename to user_table_");
        onCreate(sQLiteDatabase);
        sQLiteDatabase.execSQL("insert into user_table select _id,user_id,nickname,img,birthday,gender,usedstorage,totalstorage,point,phone,email,intCode,isAutomaticUpload,isDeleteAfterUpload,'' from user_table_");
        sQLiteDatabase.execSQL(" drop table user_table_");
    }

    private void updateTableUser8(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("alter table user_table rename to user_table_");
        onCreate(sQLiteDatabase);
        sQLiteDatabase.execSQL("insert into user_table select _id,user_id,nickname,img,birthday,gender,usedstorage,totalstorage,point,phone,email,intCode,isAutomaticUpload,isDeleteAfterUpload,invitationCode,'','' from user_table_");
        sQLiteDatabase.execSQL(" drop table user_table_");
    }

    private void updateTableLocalFilePath(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("alter table localfilepath_table rename to localfilepath_table_");
        onCreate(sQLiteDatabase);
        sQLiteDatabase.execSQL("insert into localfilepath_table select _id,local_path,addtime,uid,'' from localfilepath_table_");
        sQLiteDatabase.execSQL(" drop table localfilepath_table_");
    }

    public boolean isTableExists(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select count(*) as c from sqlite_master where type ='table' and name = '" + str + "' ;", null);
        boolean z = false;
        if (cursorRawQuery.moveToNext() && cursorRawQuery.getInt(0) > 0) {
            z = true;
        }
        LogUtil.m338i("表" + str + "是否存在:" + z);
        return z;
    }
}
