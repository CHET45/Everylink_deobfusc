package com.aivox.common.p003db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.lzy.okgo.cache.CacheHelper;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class GlassImageEntityDao extends AbstractDao<GlassImageEntity, Long> {
    public static final String TABLENAME = "GLASS_IMAGE_ENTITY";

    public static class Properties {

        /* JADX INFO: renamed from: Id */
        public static final Property f205Id = new Property(0, Long.class, "id", true, CacheHelper.f890ID);
        public static final Property Uid = new Property(1, String.class, "uid", false, "UID");
        public static final Property ImagePath = new Property(2, String.class, "imagePath", false, "IMAGE_PATH");
        public static final Property ImageName = new Property(3, String.class, "imageName", false, "IMAGE_NAME");
        public static final Property CreateTime = new Property(4, String.class, "createTime", false, "CREATE_TIME");
        public static final Property Duration = new Property(5, Long.TYPE, TypedValues.TransitionType.S_DURATION, false, "DURATION");
        public static final Property IsFavorite = new Property(6, Boolean.TYPE, "isFavorite", false, "IS_FAVORITE");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public GlassImageEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public GlassImageEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"GLASS_IMAGE_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"UID\" TEXT,\"IMAGE_PATH\" TEXT,\"IMAGE_NAME\" TEXT UNIQUE ,\"CREATE_TIME\" TEXT,\"DURATION\" INTEGER NOT NULL ,\"IS_FAVORITE\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) {
        database.execSQL("DROP TABLE " + (z ? "IF EXISTS " : "") + "\"GLASS_IMAGE_ENTITY\"");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, GlassImageEntity glassImageEntity) {
        databaseStatement.clearBindings();
        Long id = glassImageEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String uid = glassImageEntity.getUid();
        if (uid != null) {
            databaseStatement.bindString(2, uid);
        }
        String imagePath = glassImageEntity.getImagePath();
        if (imagePath != null) {
            databaseStatement.bindString(3, imagePath);
        }
        String imageName = glassImageEntity.getImageName();
        if (imageName != null) {
            databaseStatement.bindString(4, imageName);
        }
        String createTime = glassImageEntity.getCreateTime();
        if (createTime != null) {
            databaseStatement.bindString(5, createTime);
        }
        databaseStatement.bindLong(6, glassImageEntity.getDuration());
        databaseStatement.bindLong(7, glassImageEntity.getIsFavorite() ? 1L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, GlassImageEntity glassImageEntity) {
        sQLiteStatement.clearBindings();
        Long id = glassImageEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String uid = glassImageEntity.getUid();
        if (uid != null) {
            sQLiteStatement.bindString(2, uid);
        }
        String imagePath = glassImageEntity.getImagePath();
        if (imagePath != null) {
            sQLiteStatement.bindString(3, imagePath);
        }
        String imageName = glassImageEntity.getImageName();
        if (imageName != null) {
            sQLiteStatement.bindString(4, imageName);
        }
        String createTime = glassImageEntity.getCreateTime();
        if (createTime != null) {
            sQLiteStatement.bindString(5, createTime);
        }
        sQLiteStatement.bindLong(6, glassImageEntity.getDuration());
        sQLiteStatement.bindLong(7, glassImageEntity.getIsFavorite() ? 1L : 0L);
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
    public GlassImageEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        int i2 = i + 1;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 2;
        String string2 = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 3;
        String string3 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = i + 4;
        return new GlassImageEntity(lValueOf, string, string2, string3, cursor.isNull(i5) ? null : cursor.getString(i5), cursor.getLong(i + 5), cursor.getShort(i + 6) != 0);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, GlassImageEntity glassImageEntity, int i) {
        glassImageEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        int i2 = i + 1;
        glassImageEntity.setUid(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 2;
        glassImageEntity.setImagePath(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 3;
        glassImageEntity.setImageName(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 4;
        glassImageEntity.setCreateTime(cursor.isNull(i5) ? null : cursor.getString(i5));
        glassImageEntity.setDuration(cursor.getLong(i + 5));
        glassImageEntity.setIsFavorite(cursor.getShort(i + 6) != 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(GlassImageEntity glassImageEntity, long j) {
        glassImageEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(GlassImageEntity glassImageEntity) {
        if (glassImageEntity != null) {
            return glassImageEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(GlassImageEntity glassImageEntity) {
        return glassImageEntity.getId() != null;
    }
}
