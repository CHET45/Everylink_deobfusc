package com.aivox.common.p003db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import androidx.core.app.NotificationCompat;
import com.aivox.common.p003db.entity.HomeAiChatEntity;
import com.lzy.okgo.cache.CacheHelper;
import com.microsoft.azure.storage.table.TableConstants;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class HomeAiChatEntityDao extends AbstractDao<HomeAiChatEntity, Long> {
    public static final String TABLENAME = "HOME_AI_CHAT_ENTITY";

    public static class Properties {

        /* JADX INFO: renamed from: Id */
        public static final Property f206Id = new Property(0, Long.class, "id", true, CacheHelper.f890ID);
        public static final Property Uid = new Property(1, String.class, "uid", false, "UID");
        public static final Property AudioId = new Property(2, Long.class, "audioId", false, "AUDIO_ID");
        public static final Property TokenCount = new Property(3, Long.class, "tokenCount", false, "TOKEN_COUNT");
        public static final Property Type = new Property(4, Integer.class, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, false, "TYPE");
        public static final Property Status = new Property(5, Integer.class, NotificationCompat.CATEGORY_STATUS, false, "STATUS");
        public static final Property Question = new Property(6, String.class, "question", false, "QUESTION");
        public static final Property Answer = new Property(7, String.class, "answer", false, "ANSWER");
        public static final Property CreatedAt = new Property(8, String.class, "createdAt", false, "CREATED_AT");
        public static final Property ImageUrl = new Property(9, String.class, "imageUrl", false, "IMAGE_URL");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public HomeAiChatEntityDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public HomeAiChatEntityDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    public static void createTable(Database database, boolean z) {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"HOME_AI_CHAT_ENTITY\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"UID\" TEXT,\"AUDIO_ID\" INTEGER,\"TOKEN_COUNT\" INTEGER,\"TYPE\" INTEGER,\"STATUS\" INTEGER,\"QUESTION\" TEXT,\"ANSWER\" TEXT,\"CREATED_AT\" TEXT,\"IMAGE_URL\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) {
        database.execSQL("DROP TABLE " + (z ? "IF EXISTS " : "") + "\"HOME_AI_CHAT_ENTITY\"");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, HomeAiChatEntity homeAiChatEntity) {
        databaseStatement.clearBindings();
        Long id = homeAiChatEntity.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String uid = homeAiChatEntity.getUid();
        if (uid != null) {
            databaseStatement.bindString(2, uid);
        }
        Long audioId = homeAiChatEntity.getAudioId();
        if (audioId != null) {
            databaseStatement.bindLong(3, audioId.longValue());
        }
        Long tokenCount = homeAiChatEntity.getTokenCount();
        if (tokenCount != null) {
            databaseStatement.bindLong(4, tokenCount.longValue());
        }
        if (homeAiChatEntity.getType() != null) {
            databaseStatement.bindLong(5, r0.intValue());
        }
        if (homeAiChatEntity.getStatus() != null) {
            databaseStatement.bindLong(6, r0.intValue());
        }
        String question = homeAiChatEntity.getQuestion();
        if (question != null) {
            databaseStatement.bindString(7, question);
        }
        String answer = homeAiChatEntity.getAnswer();
        if (answer != null) {
            databaseStatement.bindString(8, answer);
        }
        String createdAt = homeAiChatEntity.getCreatedAt();
        if (createdAt != null) {
            databaseStatement.bindString(9, createdAt);
        }
        String imageUrl = homeAiChatEntity.getImageUrl();
        if (imageUrl != null) {
            databaseStatement.bindString(10, imageUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, HomeAiChatEntity homeAiChatEntity) {
        sQLiteStatement.clearBindings();
        Long id = homeAiChatEntity.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String uid = homeAiChatEntity.getUid();
        if (uid != null) {
            sQLiteStatement.bindString(2, uid);
        }
        Long audioId = homeAiChatEntity.getAudioId();
        if (audioId != null) {
            sQLiteStatement.bindLong(3, audioId.longValue());
        }
        Long tokenCount = homeAiChatEntity.getTokenCount();
        if (tokenCount != null) {
            sQLiteStatement.bindLong(4, tokenCount.longValue());
        }
        if (homeAiChatEntity.getType() != null) {
            sQLiteStatement.bindLong(5, r0.intValue());
        }
        if (homeAiChatEntity.getStatus() != null) {
            sQLiteStatement.bindLong(6, r0.intValue());
        }
        String question = homeAiChatEntity.getQuestion();
        if (question != null) {
            sQLiteStatement.bindString(7, question);
        }
        String answer = homeAiChatEntity.getAnswer();
        if (answer != null) {
            sQLiteStatement.bindString(8, answer);
        }
        String createdAt = homeAiChatEntity.getCreatedAt();
        if (createdAt != null) {
            sQLiteStatement.bindString(9, createdAt);
        }
        String imageUrl = homeAiChatEntity.getImageUrl();
        if (imageUrl != null) {
            sQLiteStatement.bindString(10, imageUrl);
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
    public HomeAiChatEntity readEntity(Cursor cursor, int i) {
        Long lValueOf = cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i));
        int i2 = i + 1;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 2;
        Long lValueOf2 = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 3;
        Long lValueOf3 = cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4));
        int i5 = i + 4;
        Integer numValueOf = cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5));
        int i6 = i + 5;
        Integer numValueOf2 = cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6));
        int i7 = i + 6;
        String string2 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 7;
        String string3 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 8;
        int i10 = i + 9;
        return new HomeAiChatEntity(lValueOf, string, lValueOf2, lValueOf3, numValueOf, numValueOf2, string2, string3, cursor.isNull(i9) ? null : cursor.getString(i9), cursor.isNull(i10) ? null : cursor.getString(i10));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, HomeAiChatEntity homeAiChatEntity, int i) {
        homeAiChatEntity.setId(cursor.isNull(i) ? null : Long.valueOf(cursor.getLong(i)));
        int i2 = i + 1;
        homeAiChatEntity.setUid(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 2;
        homeAiChatEntity.setAudioId(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = i + 3;
        homeAiChatEntity.setTokenCount(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
        int i5 = i + 4;
        homeAiChatEntity.setType(cursor.isNull(i5) ? null : Integer.valueOf(cursor.getInt(i5)));
        int i6 = i + 5;
        homeAiChatEntity.setStatus(cursor.isNull(i6) ? null : Integer.valueOf(cursor.getInt(i6)));
        int i7 = i + 6;
        homeAiChatEntity.setQuestion(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = i + 7;
        homeAiChatEntity.setAnswer(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = i + 8;
        homeAiChatEntity.setCreatedAt(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = i + 9;
        homeAiChatEntity.setImageUrl(cursor.isNull(i10) ? null : cursor.getString(i10));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(HomeAiChatEntity homeAiChatEntity, long j) {
        homeAiChatEntity.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(HomeAiChatEntity homeAiChatEntity) {
        if (homeAiChatEntity != null) {
            return homeAiChatEntity.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(HomeAiChatEntity homeAiChatEntity) {
        return homeAiChatEntity.getId() != null;
    }
}
