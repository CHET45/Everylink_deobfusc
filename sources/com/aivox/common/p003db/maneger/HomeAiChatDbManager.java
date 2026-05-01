package com.aivox.common.p003db.maneger;

import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.HomeAiChatEntityDao;
import com.aivox.common.p003db.entity.HomeAiChatEntity;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HomeAiChatDbManager {
    private static HomeAiChatDbManager dbManager;
    private final HomeAiChatEntityDao entityDao;

    public static HomeAiChatDbManager getInstance(DaoSession daoSession) {
        if (dbManager == null) {
            synchronized (HomeAiChatDbManager.class) {
                if (dbManager == null) {
                    dbManager = new HomeAiChatDbManager(daoSession);
                }
            }
        }
        return dbManager;
    }

    private HomeAiChatDbManager(DaoSession daoSession) {
        this.entityDao = daoSession.getHomeAiChatEntityDao();
    }

    public List<HomeAiChatEntity> queryAiChatList(String str, int i, int i2) {
        return this.entityDao.queryBuilder().orderAsc(HomeAiChatEntityDao.Properties.f206Id).where(HomeAiChatEntityDao.Properties.Uid.m1944eq(str), HomeAiChatEntityDao.Properties.Uid.m1944eq(str)).offset((i - 1) * i2).limit(i2).list();
    }

    public synchronized void insert(HomeAiChatEntity homeAiChatEntity) {
        this.entityDao.insertOrReplaceInTx(homeAiChatEntity);
    }

    public synchronized void delete(Long l) {
        this.entityDao.deleteByKeyInTx(l);
    }

    public void clear() {
        this.entityDao.deleteAll();
    }
}
