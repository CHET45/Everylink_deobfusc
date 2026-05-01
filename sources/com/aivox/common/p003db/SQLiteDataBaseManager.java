package com.aivox.common.p003db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.UserInfo;

/* JADX INFO: loaded from: classes.dex */
public class SQLiteDataBaseManager {

    /* JADX INFO: renamed from: db */
    private SQLiteDatabase f208db;
    private final SQLiteDataBaseHelper helper;

    public SQLiteDataBaseManager(Context context) {
        SQLiteDataBaseHelper ins = SQLiteDataBaseHelper.getIns(context);
        this.helper = ins;
        this.f208db = ins.getWritableDatabase();
    }

    public void insertUserInfo(UserInfo userInfo) {
        SQLiteDatabase sQLiteDatabase;
        LogUtil.m338i("=insertUserInfo=");
        synchronized (this.helper) {
            if (!this.f208db.isOpen()) {
                this.f208db = this.helper.getWritableDatabase();
            }
            this.f208db.beginTransaction();
            try {
                try {
                    this.f208db.delete(SQLiteDataBaseHelper.TABLE_USER, "1=1", null);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SPUtil.USER_ID, userInfo.getUuid());
                    contentValues.put("img", userInfo.getAvatar());
                    contentValues.put(SPUtil.NICKNAME, userInfo.getNickname());
                    contentValues.put("birthday", userInfo.getBirthday());
                    contentValues.put("gender", Integer.valueOf(userInfo.getGender()));
                    contentValues.put(Constant.KEY_PHONE, userInfo.getPhone());
                    contentValues.put("email", userInfo.getEmail());
                    contentValues.put("vip", Integer.valueOf(userInfo.isVip() ? 1 : 0));
                    contentValues.put("vipExpire", userInfo.getVipExpire());
                    DataHandle.getIns().setUserInfo(userInfo);
                    this.f208db.insert(SQLiteDataBaseHelper.TABLE_USER, null, contentValues);
                    this.f208db.setTransactionSuccessful();
                    LogUtil.m338i("=insertUserInfo=success=");
                    this.f208db.endTransaction();
                    sQLiteDatabase = this.f208db;
                } catch (Throwable th) {
                    this.f208db.endTransaction();
                    this.f208db.close();
                    throw th;
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
                LogUtil.m338i("e2:" + e.getLocalizedMessage());
                this.f208db.endTransaction();
                sQLiteDatabase = this.f208db;
            }
            sQLiteDatabase.close();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.aivox.common.model.UserInfo getUserInfo() {
        /*
            Method dump skipped, instruction units count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.p003db.SQLiteDataBaseManager.getUserInfo():com.aivox.common.model.UserInfo");
    }

    public void updateUserInfo(String str, String str2, String str3, String str4, int i) {
        SQLiteDatabase sQLiteDatabase;
        synchronized (this.helper) {
            if (!this.f208db.isOpen()) {
                this.f208db = this.helper.getWritableDatabase();
            }
            this.f208db.beginTransaction();
            try {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SPUtil.USER_ID, str);
                    contentValues.put("img", str2);
                    contentValues.put(SPUtil.NICKNAME, str3);
                    contentValues.put("birthday", str4);
                    contentValues.put("gender", Integer.valueOf(i));
                    this.f208db.update(SQLiteDataBaseHelper.TABLE_USER, contentValues, "user_id = ? ", new String[]{str});
                    this.f208db.setTransactionSuccessful();
                    this.f208db.endTransaction();
                    sQLiteDatabase = this.f208db;
                } catch (Throwable th) {
                    this.f208db.endTransaction();
                    this.f208db.close();
                    throw th;
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
                this.f208db.endTransaction();
                sQLiteDatabase = this.f208db;
            }
            sQLiteDatabase.close();
        }
    }
}
