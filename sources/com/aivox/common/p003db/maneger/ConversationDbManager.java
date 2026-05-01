package com.aivox.common.p003db.maneger;

import com.aivox.common.p003db.ConversationEntityDao;
import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.entity.ConversationEntity;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ConversationDbManager {
    private static ConversationDbManager dbManager;
    private final ConversationEntityDao entityDao;

    public static ConversationDbManager getInstance(DaoSession daoSession) {
        if (dbManager == null) {
            synchronized (ConversationDbManager.class) {
                if (dbManager == null) {
                    dbManager = new ConversationDbManager(daoSession);
                }
            }
        }
        return dbManager;
    }

    private ConversationDbManager(DaoSession daoSession) {
        this.entityDao = daoSession.getConversationEntityDao();
    }

    public List<ConversationEntity> queryLocalList(String str) {
        return this.entityDao.queryBuilder().orderDesc(ConversationEntityDao.Properties.f203Id).where(ConversationEntityDao.Properties.Uid.m1944eq(str), ConversationEntityDao.Properties.Uid.m1944eq(str)).offset(0).limit(100).list();
    }

    public void insertOrReplace(ConversationEntity conversationEntity) {
        this.entityDao.insertOrReplace(conversationEntity);
    }
}
