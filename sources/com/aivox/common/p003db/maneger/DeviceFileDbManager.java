package com.aivox.common.p003db.maneger;

import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.DeviceFileEntityDao;
import com.aivox.common.p003db.entity.DeviceFileEntity;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFileDbManager {
    private static DeviceFileDbManager dbManager;
    private final DeviceFileEntityDao entityDao;

    public static DeviceFileDbManager getInstance(DaoSession daoSession) {
        if (dbManager == null) {
            synchronized (DeviceFileDbManager.class) {
                if (dbManager == null) {
                    dbManager = new DeviceFileDbManager(daoSession);
                }
            }
        }
        return dbManager;
    }

    private DeviceFileDbManager(DaoSession daoSession) {
        this.entityDao = daoSession.getDeviceFileEntityDao();
    }

    public List<DeviceFileEntity> queryFileList(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        return this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
    }

    public void insertOrReplace(DeviceFileEntity deviceFileEntity) {
        this.entityDao.insertOrReplace(deviceFileEntity);
    }

    public void updateSyncStatus(boolean z, WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        DeviceFileEntity deviceFileEntity;
        List<DeviceFileEntity> list = this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).list();
        if (list.size() <= 0 || (deviceFileEntity = list.get(0)) == null) {
            return;
        }
        deviceFileEntity.setSynced(z);
        this.entityDao.update(deviceFileEntity);
    }

    public void deleteWhere(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        this.entityDao.queryBuilder().where(whereCondition, whereConditionArr).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
