package com.aivox.common.p003db.maneger;

import com.aivox.common.p003db.DaoSession;
import com.aivox.common.p003db.GlassImageEntityDao;
import com.aivox.common.p003db.entity.GlassImageEntity;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class GlassImageDbManager {
    private static GlassImageDbManager dbManager;
    private final GlassImageEntityDao entityDao;

    public static GlassImageDbManager getInstance(DaoSession daoSession) {
        if (dbManager == null) {
            synchronized (GlassImageDbManager.class) {
                if (dbManager == null) {
                    dbManager = new GlassImageDbManager(daoSession);
                }
            }
        }
        return dbManager;
    }

    private GlassImageDbManager(DaoSession daoSession) {
        this.entityDao = daoSession.getGlassImageEntityDao();
    }

    public GlassImageEntity query(int i) {
        return this.entityDao.load(Long.valueOf(i));
    }

    public List<GlassImageEntity> queryGallery(String str) {
        return this.entityDao.queryBuilder().orderAsc(GlassImageEntityDao.Properties.ImageName).where(GlassImageEntityDao.Properties.Uid.m1944eq(str), GlassImageEntityDao.Properties.Uid.m1944eq(str)).list();
    }

    public List<GlassImageEntity> queryGallery(String str, int i) {
        QueryBuilder<GlassImageEntity> queryBuilder = this.entityDao.queryBuilder();
        if (i == 1) {
            queryBuilder.where(GlassImageEntityDao.Properties.ImagePath.like("%.jpg%"), new WhereCondition[0]);
        } else if (i == 2) {
            queryBuilder.where(GlassImageEntityDao.Properties.ImagePath.like("%.mp4%"), new WhereCondition[0]);
        } else if (i == 3) {
            queryBuilder.where(GlassImageEntityDao.Properties.IsFavorite.m1944eq(true), new WhereCondition[0]);
        }
        return queryBuilder.orderAsc(GlassImageEntityDao.Properties.ImageName).where(GlassImageEntityDao.Properties.Uid.m1944eq(str), GlassImageEntityDao.Properties.Uid.m1944eq(str)).list();
    }

    public boolean queryIsFavorite(int i) {
        GlassImageEntity glassImageEntityLoad = this.entityDao.load(Long.valueOf(i));
        if (glassImageEntityLoad != null) {
            return glassImageEntityLoad.getIsFavorite();
        }
        return false;
    }

    public synchronized void insert(GlassImageEntity glassImageEntity) {
        this.entityDao.insertOrReplace(glassImageEntity);
    }

    public synchronized void updateFavorite(int i, boolean z) {
        GlassImageEntity glassImageEntityLoad = this.entityDao.load(Long.valueOf(i));
        if (glassImageEntityLoad != null && glassImageEntityLoad.getIsFavorite() != z) {
            glassImageEntityLoad.setIsFavorite(z);
            this.entityDao.updateInTx(glassImageEntityLoad);
        }
    }

    public synchronized void delete(Long l) {
        this.entityDao.deleteByKey(l);
    }
}
