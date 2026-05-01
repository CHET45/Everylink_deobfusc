package com.aivox.common.p003db.maneger;

import com.aivox.base.util.BaseStringUtil;
import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.entity.LocalFileEntity;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class LocalFileDbManager {
    private static LocalFileDbManager dbManager;
    private final LocalFileEntityDao entityDao;

    public static LocalFileDbManager getInstance(DaoSession daoSession) {
        if (dbManager == null) {
            synchronized (LocalFileDbManager.class) {
                if (dbManager == null) {
                    dbManager = new LocalFileDbManager(daoSession);
                }
            }
        }
        return dbManager;
    }

    private LocalFileDbManager(DaoSession daoSession) {
        this.entityDao = daoSession.getLocalFileEntityDao();
    }

    public List<LocalFileEntity> queryLocalList(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        return this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
    }

    public List<LocalFileEntity> queryLocalListByPage(int i, int i2, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        return this.entityDao.queryBuilder().orderDesc(LocalFileEntityDao.Properties.f207Id).where(whereCondition, whereConditionArr).offset(i * i2).limit(i2).list();
    }

    public void insertOrReplace(LocalFileEntity localFileEntity) {
        this.entityDao.insertOrReplace(localFileEntity);
    }

    public void updateTransStatus(int i, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setTransStatus(i);
        this.entityDao.update(localFileEntity);
    }

    public void updateAudioUrl(String str, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setSyncStatus(!BaseStringUtil.isEmpty(str) ? 1 : 0);
        localFileEntity.setVideoUrl(str);
        this.entityDao.update(localFileEntity);
    }

    public void updateLocalPath(String str, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setLocalPath(str);
        this.entityDao.update(localFileEntity);
    }

    public void updateTitle(String str, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setTitle(str);
        this.entityDao.update(localFileEntity);
    }

    public void updateAutoSyncStatus(boolean z, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setAutoSync(z);
        this.entityDao.update(localFileEntity);
    }

    public void updateAllStatus(int i, int i2, boolean z, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        LocalFileEntity localFileEntity;
        List<LocalFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (localFileEntity = list.get(0)) == null) {
            return;
        }
        localFileEntity.setSyncStatus(i);
        localFileEntity.setTransStatus(i2);
        localFileEntity.setAutoSync(z);
        this.entityDao.update(localFileEntity);
    }

    public void deleteWhere(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
