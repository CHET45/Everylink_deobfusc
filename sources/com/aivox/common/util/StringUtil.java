package com.aivox.common.util;

import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StringUtil {
    public static boolean isOversea() {
        return false;
    }

    public static String getHttpUrl() {
        return BaseGlobalConfig.getApiHost();
    }

    public static String getWebsocketUrl() {
        return BaseGlobalConfig.getWsHost();
    }

    public static void updateFileTime(LocalFileDbManager localFileDbManager, String str, List<AudioInfoBean> list) {
        Iterator<AudioInfoBean> it = list.iterator();
        while (it.hasNext()) {
            updateFileTime(localFileDbManager, str, it.next());
        }
    }

    public static void updateFileTime(LocalFileDbManager localFileDbManager, String str, AudioInfoBean audioInfoBean) {
        List<LocalFileEntity> listQueryLocalList;
        if (audioInfoBean.getFileTime() != 0 || (listQueryLocalList = localFileDbManager.queryLocalList(LocalFileEntityDao.Properties.Uid.m1944eq(str), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean.getId())))) == null || listQueryLocalList.size() <= 0) {
            return;
        }
        audioInfoBean.setFileTime((int) FileUtils.getAudioFileVoiceTime(listQueryLocalList.get(0).getLocalPath()));
    }

    public static void updateFileLocalPath(LocalFileDbManager localFileDbManager, String str, List<AudioInfoBean> list) {
        Iterator<AudioInfoBean> it = list.iterator();
        while (it.hasNext()) {
            updateItemFileLocalPath(localFileDbManager, str, it.next());
        }
    }

    public static void updateItemFileLocalPath(LocalFileDbManager localFileDbManager, String str, AudioInfoBean audioInfoBean) {
        List<LocalFileEntity> listQueryLocalList;
        if (audioInfoBean.getAudioInfo() == null || !BaseStringUtil.isEmpty(audioInfoBean.getAudioInfo().getLocalPath()) || (listQueryLocalList = localFileDbManager.queryLocalList(LocalFileEntityDao.Properties.Uid.m1944eq(str), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean.getId())))) == null || listQueryLocalList.size() <= 0) {
            return;
        }
        audioInfoBean.getAudioInfo().setLocalPath(listQueryLocalList.get(0).getLocalPath());
    }
}
