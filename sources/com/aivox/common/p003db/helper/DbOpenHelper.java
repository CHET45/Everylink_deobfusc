package com.aivox.common.p003db.helper;

import android.content.Context;
import com.aivox.common.p003db.ConversationEntityDao;
import com.aivox.common.p003db.DaoMaster;
import com.aivox.common.p003db.GlassImageEntityDao;
import com.aivox.common.p003db.HomeAiChatEntityDao;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.helper.MigrationHelper;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/* JADX INFO: loaded from: classes.dex */
public class DbOpenHelper extends DaoMaster.OpenHelper {
    public DbOpenHelper(Context context, String str) {
        super(context, str);
    }

    @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
    public void onUpgrade(Database database, int i, int i2) {
        MigrationHelper.migrate(database, new MigrationHelper.ReCreateAllTableListener() { // from class: com.aivox.common.db.helper.DbOpenHelper.1
            @Override // com.aivox.common.db.helper.MigrationHelper.ReCreateAllTableListener
            public void onCreateAllTables(Database database2, boolean z) {
                DaoMaster.createAllTables(database2, z);
            }

            @Override // com.aivox.common.db.helper.MigrationHelper.ReCreateAllTableListener
            public void onDropAllTables(Database database2, boolean z) {
                DaoMaster.dropAllTables(database2, z);
            }
        }, (Class<? extends AbstractDao<?, ?>>[]) new Class[]{LocalFileEntityDao.class, ConversationEntityDao.class, HomeAiChatEntityDao.class, GlassImageEntityDao.class});
    }
}
