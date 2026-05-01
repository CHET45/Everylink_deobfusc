package com.aivox.common.p003db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.aivox.common.p003db.entity.ConversationEntity;
import com.lzy.okgo.cache.CacheHelper;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class ConversationEntityDao extends AbstractDao<ConversationEntity, Long> {
    public static final String TABLENAME = "CONVERSATION_ENTITY";

    public static class Properties {

        /* JADX INFO: renamed from: Id */
        public static final Property f203Id = new Property(0, Long.class, "id", true, CacheHelper.f890ID);
        public static final Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public static final Property Translation = new Property(2, String.class, "translation", false, "TRANSLATION");
        public static final Property Uid = new Property(3, String.class, "uid", false, "UID");
        public static final Property MySide = new Property(4, Boolean.TYPE, "mySide", false, "MY_SIDE");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public ConversationEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public ConversationEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"CONVERSATION_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"CONTENT\" TEXT,\"TRANSLATION\" TEXT,\"UID\" TEXT,\"MY_SIDE\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) {
        database.execSQL("DROP TABLE " + (z ? "IF EXISTS " : "") + "\"CONVERSATION_ENTITY\"");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, ConversationEntity conversationEntity) {
        databaseStatement.clearBindings();
        Long id = conversationEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String content = conversationEntity.getContent();
        if (content != null) {
            databaseStatement.bindString(2, content);
        }
        String translation = conversationEntity.getTranslation();
        if (translation != null) {
            databaseStatement.bindString(3, translation);
        }
        String uid = conversationEntity.getUid();
        if (uid != null) {
            databaseStatement.bindString(4, uid);
        }
        databaseStatement.bindLong(5, conversationEntity.getMySide() ? 1L : 0L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, ConversationEntity conversationEntity) {
        sQLiteStatement.clearBindings();
        Long id = conversationEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String content = conversationEntity.getContent();
        if (content != null) {
            sQLiteStatement.bindString(2, content);
        }
        String translation = conversationEntity.getTranslation();
        if (translation != null) {
            sQLiteStatement.bindString(3, translation);
        }
        String uid = conversationEntity.getUid();
        if (uid != null) {
            sQLiteStatement.bindString(4, uid);
        }
        sQLiteStatement.bindLong(5, conversationEntity.getMySide() ? 1L : 0L);
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
    public ConversationEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        int i2 = i + 1;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 2;
        String string2 = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = i + 3;
        return new ConversationEntity(lValueOf, string, string2, cursor.isNull(i4) ? null : cursor.getString(i4), cursor.getShort(i + 4) != 0);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, ConversationEntity conversationEntity, int i) {
        conversationEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        int i2 = i + 1;
        conversationEntity.setContent(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 2;
        conversationEntity.setTranslation(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 3;
        conversationEntity.setUid(cursor.isNull(i4) ? null : cursor.getString(i4));
        conversationEntity.setMySide(cursor.getShort(i + 4) != 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(ConversationEntity conversationEntity, long j) {
        conversationEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(ConversationEntity conversationEntity) {
        if (conversationEntity != null) {
            return conversationEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(ConversationEntity conversationEntity) {
        return conversationEntity.getId() != null;
    }
}
