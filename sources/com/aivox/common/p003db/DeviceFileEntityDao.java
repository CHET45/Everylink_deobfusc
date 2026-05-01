package com.aivox.common.p003db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aivox.common.p003db.entity.DeviceFileEntity;
import com.lzy.okgo.cache.CacheHelper;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFileEntityDao extends AbstractDao<DeviceFileEntity, Long> {
    public static final String TABLENAME = "DEVICE_FILE_ENTITY";

    public static class Properties {

        /* JADX INFO: renamed from: Id */
        public static final Property f204Id = new Property(0, Long.class, "id", true, CacheHelper.f890ID);
        public static final Property FileId = new Property(1, Long.TYPE, "fileId", false, "FILE_ID");
        public static final Property FileSize = new Property(2, Long.TYPE, "fileSize", false, "FILE_SIZE");
        public static final Property CreateTime = new Property(3, Integer.TYPE, "createTime", false, "CREATE_TIME");
        public static final Property Duration = new Property(4, Integer.TYPE, TypedValues.TransitionType.S_DURATION, false, "DURATION");
        public static final Property Synced = new Property(5, Boolean.TYPE, "synced", false, "SYNCED");
        public static final Property Name = new Property(6, String.class, "name", false, "NAME");
        public static final Property Uid = new Property(7, String.class, "uid", false, "UID");
        public static final Property LocalPath = new Property(8, String.class, "localPath", false, "LOCAL_PATH");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public DeviceFileEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public DeviceFileEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"DEVICE_FILE_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"FILE_ID\" INTEGER NOT NULL UNIQUE ,\"FILE_SIZE\" INTEGER NOT NULL ,\"CREATE_TIME\" INTEGER NOT NULL ,\"DURATION\" INTEGER NOT NULL ,\"SYNCED\" INTEGER NOT NULL ,\"NAME\" TEXT,\"UID\" TEXT,\"LOCAL_PATH\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        database.execSQL("DROP TABLE " + (z ? "IF EXISTS " : "") + "\"DEVICE_FILE_ENTITY\"");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, DeviceFileEntity deviceFileEntity) {
        databaseStatement.clearBindings();
        Long id = deviceFileEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        databaseStatement.bindLong(2, deviceFileEntity.getFileId());
        databaseStatement.bindLong(3, deviceFileEntity.getFileSize());
        databaseStatement.bindLong(4, deviceFileEntity.getCreateTime());
        databaseStatement.bindLong(5, deviceFileEntity.getDuration());
        databaseStatement.bindLong(6, deviceFileEntity.getSynced() ? 1L : 0L);
        String name = deviceFileEntity.getName();
        if (name != null) {
            databaseStatement.bindString(7, name);
        }
        String uid = deviceFileEntity.getUid();
        if (uid != null) {
            databaseStatement.bindString(8, uid);
        }
        String localPath = deviceFileEntity.getLocalPath();
        if (localPath != null) {
            databaseStatement.bindString(9, localPath);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, DeviceFileEntity deviceFileEntity) {
        sQLiteStatement.clearBindings();
        Long id = deviceFileEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        sQLiteStatement.bindLong(2, deviceFileEntity.getFileId());
        sQLiteStatement.bindLong(3, deviceFileEntity.getFileSize());
        sQLiteStatement.bindLong(4, deviceFileEntity.getCreateTime());
        sQLiteStatement.bindLong(5, deviceFileEntity.getDuration());
        sQLiteStatement.bindLong(6, deviceFileEntity.getSynced() ? 1L : 0L);
        String name = deviceFileEntity.getName();
        if (name != null) {
            sQLiteStatement.bindString(7, name);
        }
        String uid = deviceFileEntity.getUid();
        if (uid != null) {
            sQLiteStatement.bindString(8, uid);
        }
        String localPath = deviceFileEntity.getLocalPath();
        if (localPath != null) {
            sQLiteStatement.bindString(9, localPath);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public DeviceFileEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        long j = cursor.getLong(i + 1);
        long j2 = cursor.getLong(i + 2);
        int i2 = cursor.getInt(i + 3);
        int i3 = cursor.getInt(i + 4);
        boolean z = cursor.getShort(i + 5) != 0;
        int i4 = i + 6;
        String string = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 7;
        int i6 = i + 8;
        return new DeviceFileEntity(lValueOf, j, j2, i2, i3, z, string, cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : cursor.getString(i6));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, DeviceFileEntity deviceFileEntity, int i) {
        deviceFileEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        deviceFileEntity.setFileId(cursor.getLong(i + 1));
        deviceFileEntity.setFileSize(cursor.getLong(i + 2));
        deviceFileEntity.setCreateTime(cursor.getInt(i + 3));
        deviceFileEntity.setDuration(cursor.getInt(i + 4));
        deviceFileEntity.setSynced(cursor.getShort(i + 5) != 0);
        int i2 = i + 6;
        deviceFileEntity.setName(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 7;
        deviceFileEntity.setUid(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 8;
        deviceFileEntity.setLocalPath(cursor.isNull(i4) ? null : cursor.getString(i4));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(DeviceFileEntity deviceFileEntity, long j) {
        deviceFileEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(DeviceFileEntity deviceFileEntity) {
        if (deviceFileEntity != null) {
            return deviceFileEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(DeviceFileEntity deviceFileEntity) {
        return deviceFileEntity.getId() != null;
    }
}
