package com.lzy.okgo.cache;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
class CacheDao<T> extends DataBaseDao<CacheEntity<T>> {
    public CacheDao() {
        super(new CacheHelper());
    }

    public CacheEntity<T> get(String str) {
        List<T> list = get("key=?", new String[]{str});
        if (list.size() > 0) {
            return (CacheEntity) list.get(0);
        }
        return null;
    }

    public boolean remove(String str) {
        return delete("key=?", new String[]{str}) > 0;
    }

    @Override // com.lzy.okgo.cache.DataBaseDao
    public CacheEntity<T> parseCursorToBean(Cursor cursor) {
        return CacheEntity.parseCursorToBean(cursor);
    }

    @Override // com.lzy.okgo.cache.DataBaseDao
    public ContentValues getContentValues(CacheEntity<T> cacheEntity) {
        return CacheEntity.getContentValues(cacheEntity);
    }

    @Override // com.lzy.okgo.cache.DataBaseDao
    protected String getTableName() {
        return CacheHelper.TABLE_NAME;
    }
}
