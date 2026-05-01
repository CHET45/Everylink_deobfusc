package com.aivox.common.p003db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aivox.base.common.Constant;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.lzy.okgo.cache.CacheHelper;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class LocalFileEntityDao extends AbstractDao<LocalFileEntity, Long> {
    public static final String TABLENAME = "LOCAL_FILE_ENTITY";

    public static class Properties {

        /* JADX INFO: renamed from: Id */
        public static final Property f207Id = new Property(0, Long.class, "id", true, CacheHelper.f890ID);
        public static final Property Vid = new Property(1, Integer.TYPE, "vid", false, "VID");
        public static final Property Uid = new Property(2, String.class, "uid", false, "UID");
        public static final Property Title = new Property(3, String.class, Constant.KEY_TITLE, false, "TITLE");
        public static final Property LocalPath = new Property(4, String.class, "localPath", false, "LOCAL_PATH");
        public static final Property CreateTime = new Property(5, String.class, "createTime", false, "CREATE_TIME");
        public static final Property EditTime = new Property(6, String.class, "editTime", false, "EDIT_TIME");
        public static final Property Site = new Property(7, String.class, "site", false, "SITE");
        public static final Property Participant = new Property(8, String.class, "participant", false, "PARTICIPANT");
        public static final Property VideoUrl = new Property(9, String.class, "videoUrl", false, "VIDEO_URL");
        public static final Property Duration = new Property(10, Integer.TYPE, TypedValues.TransitionType.S_DURATION, false, "DURATION");
        public static final Property TransStatus = new Property(11, Integer.TYPE, "transStatus", false, "TRANS_STATUS");
        public static final Property SyncStatus = new Property(12, Integer.TYPE, "syncStatus", false, "SYNC_STATUS");
        public static final Property TopStatus = new Property(13, Integer.TYPE, "topStatus", false, "TOP_STATUS");
        public static final Property FileSize = new Property(14, Long.TYPE, "fileSize", false, "FILE_SIZE");
        public static final Property FromOldVersion = new Property(15, Boolean.TYPE, "fromOldVersion", false, "FROM_OLD_VERSION");
        public static final Property AutoSync = new Property(16, Boolean.TYPE, "autoSync", false, "AUTO_SYNC");
        public static final Property IsBreak = new Property(17, Boolean.TYPE, "isBreak", false, "IS_BREAK");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public LocalFileEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public LocalFileEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"LOCAL_FILE_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"VID\" INTEGER NOT NULL UNIQUE ,\"UID\" TEXT,\"TITLE\" TEXT,\"LOCAL_PATH\" TEXT,\"CREATE_TIME\" TEXT,\"EDIT_TIME\" TEXT,\"SITE\" TEXT,\"PARTICIPANT\" TEXT,\"VIDEO_URL\" TEXT,\"DURATION\" INTEGER NOT NULL ,\"TRANS_STATUS\" INTEGER NOT NULL ,\"SYNC_STATUS\" INTEGER NOT NULL ,\"TOP_STATUS\" INTEGER NOT NULL ,\"FILE_SIZE\" INTEGER NOT NULL ,\"FROM_OLD_VERSION\" INTEGER NOT NULL ,\"AUTO_SYNC\" INTEGER NOT NULL ,\"IS_BREAK\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) {
        database.execSQL("DROP TABLE " + (z ? "IF EXISTS " : "") + "\"LOCAL_FILE_ENTITY\"");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, LocalFileEntity localFileEntity) {
        databaseStatement.clearBindings();
        Long id = localFileEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        databaseStatement.bindLong(2, localFileEntity.getVid());
        String uid = localFileEntity.getUid();
        if (uid != null) {
            databaseStatement.bindString(3, uid);
        }
        String title = localFileEntity.getTitle();
        if (title != null) {
            databaseStatement.bindString(4, title);
        }
        String localPath = localFileEntity.getLocalPath();
        if (localPath != null) {
            databaseStatement.bindString(5, localPath);
        }
        String createTime = localFileEntity.getCreateTime();
        if (createTime != null) {
            databaseStatement.bindString(6, createTime);
        }
        String editTime = localFileEntity.getEditTime();
        if (editTime != null) {
            databaseStatement.bindString(7, editTime);
        }
        String site = localFileEntity.getSite();
        if (site != null) {
            databaseStatement.bindString(8, site);
        }
        String participant = localFileEntity.getParticipant();
        if (participant != null) {
            databaseStatement.bindString(9, participant);
        }
        String videoUrl = localFileEntity.getVideoUrl();
        if (videoUrl != null) {
            databaseStatement.bindString(10, videoUrl);
        }
        databaseStatement.bindLong(11, localFileEntity.getDuration());
        databaseStatement.bindLong(12, localFileEntity.getTransStatus());
        databaseStatement.bindLong(13, localFileEntity.getSyncStatus());
        databaseStatement.bindLong(14, localFileEntity.getTopStatus());
        databaseStatement.bindLong(15, localFileEntity.getFileSize());
        databaseStatement.bindLong(16, localFileEntity.getFromOldVersion() ? 1L : 0L);
        databaseStatement.bindLong(17, localFileEntity.getAutoSync() ? 1L : 0L);
        databaseStatement.bindLong(18, localFileEntity.getIsBreak() ? 1L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, LocalFileEntity localFileEntity) {
        sQLiteStatement.clearBindings();
        Long id = localFileEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        sQLiteStatement.bindLong(2, localFileEntity.getVid());
        String uid = localFileEntity.getUid();
        if (uid != null) {
            sQLiteStatement.bindString(3, uid);
        }
        String title = localFileEntity.getTitle();
        if (title != null) {
            sQLiteStatement.bindString(4, title);
        }
        String localPath = localFileEntity.getLocalPath();
        if (localPath != null) {
            sQLiteStatement.bindString(5, localPath);
        }
        String createTime = localFileEntity.getCreateTime();
        if (createTime != null) {
            sQLiteStatement.bindString(6, createTime);
        }
        String editTime = localFileEntity.getEditTime();
        if (editTime != null) {
            sQLiteStatement.bindString(7, editTime);
        }
        String site = localFileEntity.getSite();
        if (site != null) {
            sQLiteStatement.bindString(8, site);
        }
        String participant = localFileEntity.getParticipant();
        if (participant != null) {
            sQLiteStatement.bindString(9, participant);
        }
        String videoUrl = localFileEntity.getVideoUrl();
        if (videoUrl != null) {
            sQLiteStatement.bindString(10, videoUrl);
        }
        sQLiteStatement.bindLong(11, localFileEntity.getDuration());
        sQLiteStatement.bindLong(12, localFileEntity.getTransStatus());
        sQLiteStatement.bindLong(13, localFileEntity.getSyncStatus());
        sQLiteStatement.bindLong(14, localFileEntity.getTopStatus());
        sQLiteStatement.bindLong(15, localFileEntity.getFileSize());
        sQLiteStatement.bindLong(16, localFileEntity.getFromOldVersion() ? 1L : 0L);
        sQLiteStatement.bindLong(17, localFileEntity.getAutoSync() ? 1L : 0L);
        sQLiteStatement.bindLong(18, localFileEntity.getIsBreak() ? 1L : 0L);
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
    public LocalFileEntity readEntity(Cursor cursor, int i) {
        int i2 = i + 2;
        int i3 = i + 3;
        int i4 = i + 4;
        int i5 = i + 5;
        int i6 = i + 6;
        int i7 = i + 7;
        int i8 = i + 8;
        int i9 = i + 9;
        return new LocalFileEntity(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)), cursor.getInt(i + 1), cursor.isNull(i2) ? null : cursor.getString(i2), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : cursor.getString(i4), cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : cursor.getString(i6), cursor.isNull(i7) ? null : cursor.getString(i7), cursor.isNull(i8) ? null : cursor.getString(i8), cursor.isNull(i9) ? null : cursor.getString(i9), cursor.getInt(i + 10), cursor.getInt(i + 11), cursor.getInt(i + 12), cursor.getInt(i + 13), cursor.getLong(i + 14), cursor.getShort(i + 15) != 0, cursor.getShort(i + 16) != 0, cursor.getShort(i + 17) != 0);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, LocalFileEntity localFileEntity, int i) {
        localFileEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        localFileEntity.setVid(cursor.getInt(i + 1));
        int i2 = i + 2;
        localFileEntity.setUid(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 3;
        localFileEntity.setTitle(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 4;
        localFileEntity.setLocalPath(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 5;
        localFileEntity.setCreateTime(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 6;
        localFileEntity.setEditTime(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 7;
        localFileEntity.setSite(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = i + 8;
        localFileEntity.setParticipant(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = i + 9;
        localFileEntity.setVideoUrl(cursor.isNull(i9) ? null : cursor.getString(i9));
        localFileEntity.setDuration(cursor.getInt(i + 10));
        localFileEntity.setTransStatus(cursor.getInt(i + 11));
        localFileEntity.setSyncStatus(cursor.getInt(i + 12));
        localFileEntity.setTopStatus(cursor.getInt(i + 13));
        localFileEntity.setFileSize(cursor.getLong(i + 14));
        localFileEntity.setFromOldVersion(cursor.getShort(i + 15) != 0);
        localFileEntity.setAutoSync(cursor.getShort(i + 16) != 0);
        localFileEntity.setIsBreak(cursor.getShort(i + 17) != 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(LocalFileEntity localFileEntity, long j) {
        localFileEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(LocalFileEntity localFileEntity) {
        if (localFileEntity != null) {
            return localFileEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(LocalFileEntity localFileEntity) {
        return localFileEntity.getId() != null;
    }
}
