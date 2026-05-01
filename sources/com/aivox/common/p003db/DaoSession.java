package com.aivox.common.p003db;

import com.aivox.common.p003db.entity.ConversationEntity;
import com.aivox.common.p003db.entity.DeviceFileEntity;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.aivox.common.p003db.entity.HomeAiChatEntity;
import com.aivox.common.p003db.entity.LocalFileEntity;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class DaoSession extends AbstractDaoSession {
    private final ConversationEntityDao conversationEntityDao;
    private final DaoConfig conversationEntityDaoConfig;
    private final DeviceFileEntityDao deviceFileEntityDao;
    private final DaoConfig deviceFileEntityDaoConfig;
    private final GlassImageEntityDao glassImageEntityDao;
    private final DaoConfig glassImageEntityDaoConfig;
    private final HomeAiChatEntityDao homeAiChatEntityDao;
    private final DaoConfig homeAiChatEntityDaoConfig;
    private final LocalFileEntityDao localFileEntityDao;
    private final DaoConfig localFileEntityDaoConfig;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);
        DaoConfig daoConfigClone = map.get(ConversationEntityDao.class).clone();
        this.conversationEntityDaoConfig = daoConfigClone;
        daoConfigClone.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone2 = map.get(DeviceFileEntityDao.class).clone();
        this.deviceFileEntityDaoConfig = daoConfigClone2;
        daoConfigClone2.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone3 = map.get(GlassImageEntityDao.class).clone();
        this.glassImageEntityDaoConfig = daoConfigClone3;
        daoConfigClone3.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone4 = map.get(HomeAiChatEntityDao.class).clone();
        this.homeAiChatEntityDaoConfig = daoConfigClone4;
        daoConfigClone4.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone5 = map.get(LocalFileEntityDao.class).clone();
        this.localFileEntityDaoConfig = daoConfigClone5;
        daoConfigClone5.initIdentityScope(identityScopeType);
        ConversationEntityDao conversationEntityDao = new ConversationEntityDao(daoConfigClone, this);
        this.conversationEntityDao = conversationEntityDao;
        DeviceFileEntityDao deviceFileEntityDao = new DeviceFileEntityDao(daoConfigClone2, this);
        this.deviceFileEntityDao = deviceFileEntityDao;
        GlassImageEntityDao glassImageEntityDao = new GlassImageEntityDao(daoConfigClone3, this);
        this.glassImageEntityDao = glassImageEntityDao;
        HomeAiChatEntityDao homeAiChatEntityDao = new HomeAiChatEntityDao(daoConfigClone4, this);
        this.homeAiChatEntityDao = homeAiChatEntityDao;
        LocalFileEntityDao localFileEntityDao = new LocalFileEntityDao(daoConfigClone5, this);
        this.localFileEntityDao = localFileEntityDao;
        registerDao(ConversationEntity.class, conversationEntityDao);
        registerDao(DeviceFileEntity.class, deviceFileEntityDao);
        registerDao(GlassImageEntity.class, glassImageEntityDao);
        registerDao(HomeAiChatEntity.class, homeAiChatEntityDao);
        registerDao(LocalFileEntity.class, localFileEntityDao);
    }

    public void clear() {
        this.conversationEntityDaoConfig.clearIdentityScope();
        this.deviceFileEntityDaoConfig.clearIdentityScope();
        this.glassImageEntityDaoConfig.clearIdentityScope();
        this.homeAiChatEntityDaoConfig.clearIdentityScope();
        this.localFileEntityDaoConfig.clearIdentityScope();
    }

    public ConversationEntityDao getConversationEntityDao() {
        return this.conversationEntityDao;
    }

    public DeviceFileEntityDao getDeviceFileEntityDao() {
        return this.deviceFileEntityDao;
    }

    public GlassImageEntityDao getGlassImageEntityDao() {
        return this.glassImageEntityDao;
    }

    public HomeAiChatEntityDao getHomeAiChatEntityDao() {
        return this.homeAiChatEntityDao;
    }

    public LocalFileEntityDao getLocalFileEntityDao() {
        return this.localFileEntityDao;
    }
}
