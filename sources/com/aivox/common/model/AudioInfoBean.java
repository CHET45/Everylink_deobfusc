package com.aivox.common.model;

import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.FileUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioInfoBean implements Serializable {
    private AudioInfo audioInfo;
    private boolean existAiSummary;
    private int fileState;
    private int fileTime;

    /* JADX INFO: renamed from: id */
    private int f222id;
    private boolean isCheck;
    private int isSeparate;
    private boolean isTheLast;
    private int isTop;
    private int isTrans;
    private String keywords;
    private String language;
    private String localPath;
    private int markCount;
    private int progress;
    private String remark;
    private String site;
    private int source;
    private String startTime;
    private int state;
    private List<TagGroupList> tagGroupList;
    private String title;
    private int titleStatus;
    private String uuid;

    public String toString() {
        return "AudioInfoBean{id=" + this.f222id + ", user_id='" + this.uuid + "', title='" + this.title + "', remark='" + this.remark + "', site='" + this.site + "', local_path='" + this.localPath + "', startTime='" + this.startTime + "', isTrans=" + this.isTrans + ", fileState=" + this.fileState + ", isTop=" + this.isTop + ", audioInfo=" + this.audioInfo + ", fileTime='" + this.fileTime + "', isCheck=" + this.isCheck + ", keywords='" + this.keywords + "', language='" + this.language + "', state=" + this.state + ", progress=" + this.progress + '}';
    }

    public AudioInfoBean() {
        this.localPath = "";
    }

    public AudioInfoBean(File file, String str) {
        this.localPath = "";
        this.title = file.getName().substring(0, file.getName().lastIndexOf("."));
        this.startTime = DateUtil.local2UTC(file.lastModified(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        this.fileTime = (int) FileUtils.getAudioFileVoiceTime(file.getPath());
        this.localPath = file.getPath();
        this.uuid = str;
        this.isTrans = new File(FileUtils.getTranscribeFilePath(file.getPath())).exists() ? 1 : 0;
    }

    public AudioInfoBean(String str, long j, int i, String str2, String str3) {
        this.localPath = "";
        this.title = str;
        this.startTime = DateUtil.local2UTC(j, DateUtil.YYYY_MM_DD_HH_MM_SS);
        this.fileTime = i;
        this.localPath = str2;
        this.uuid = str3;
    }

    public AudioInfo getAudioInfo() {
        return this.audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }

    public int getIsTop() {
        return this.isTop;
    }

    public void setIsTop(int i) {
        this.isTop = i;
    }

    public int getFileState() {
        return this.fileState;
    }

    public void setFileState(int i) {
        this.fileState = i;
    }

    public void setState(int i) {
        this.state = i;
    }

    public int getState() {
        return this.state;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getIsTrans() {
        return this.isTrans;
    }

    public void setIsTrans(int i) {
        this.isTrans = i;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean z) {
        this.isCheck = z;
    }

    public int getId() {
        return this.f222id;
    }

    public void setId(int i) {
        this.f222id = i;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String str) {
        this.remark = str;
    }

    public String getSite() {
        String str = this.site;
        return str == null ? "" : str;
    }

    public void setSite(String str) {
        this.site = str;
    }

    public String getAudioUrl() {
        return getAudioInfo() == null ? "" : getAudioInfo().getAudioUrl();
    }

    public int getFileTime() {
        int i = this.fileTime;
        if (i != 0) {
            return i;
        }
        AudioInfo audioInfo = this.audioInfo;
        if (audioInfo == null) {
            return 0;
        }
        return audioInfo.getAudioTimeDuration().intValue();
    }

    public void setFileTime(int i) {
        this.fileTime = i;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getLocalPath() {
        if (BaseStringUtil.isNotEmpty(this.localPath)) {
            return this.localPath;
        }
        AudioInfo audioInfo = this.audioInfo;
        if (audioInfo == null) {
            return this.localPath;
        }
        return audioInfo.getLocalPath();
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public boolean isLocalAudio() {
        AudioInfo audioInfo = this.audioInfo;
        if (audioInfo == null) {
            return false;
        }
        return BaseStringUtil.isEmpty(audioInfo.getAudioUrl());
    }

    public List<TagGroupList> getTagGroupList() {
        List<TagGroupList> list = this.tagGroupList;
        return list == null ? new ArrayList() : list;
    }

    public void setTagGroupList(List<TagGroupList> list) {
        this.tagGroupList = list;
    }

    public int getMarkCount() {
        return this.markCount;
    }

    public void setMarkCount(int i) {
        this.markCount = i;
    }

    public int getIsSeparate() {
        return this.isSeparate;
    }

    public void setIsSeparate(int i) {
        this.isSeparate = i;
    }

    public boolean isExistAiSummary() {
        return this.existAiSummary;
    }

    public void setExistAiSummary(boolean z) {
        this.existAiSummary = z;
    }

    public boolean isTheLast() {
        return this.isTheLast;
    }

    public void setTheLast(boolean z) {
        this.isTheLast = z;
    }

    public int getTitleStatus() {
        return this.titleStatus;
    }

    public void setTitleStatus(int i) {
        this.titleStatus = i;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int i) {
        this.source = i;
    }

    public static class TagGroupList implements Serializable {
        private Integer colour;
        private String colourText;
        private String createdAt;

        /* JADX INFO: renamed from: id */
        private Integer f223id;
        private String name;
        private Integer sort;
        private Integer type;
        private String updatedAt;
        private String uuid;

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public Integer getColour() {
            return this.colour;
        }

        public void setColour(Integer num) {
            this.colour = num;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public Integer getId() {
            return this.f223id;
        }

        public void setId(Integer num) {
            this.f223id = num;
        }

        public Integer getSort() {
            return this.sort;
        }

        public void setSort(Integer num) {
            this.sort = num;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer num) {
            this.type = num;
        }

        public String getUuid() {
            return this.uuid;
        }

        public void setUuid(String str) {
            this.uuid = str;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public void setUpdatedAt(String str) {
            this.updatedAt = str;
        }

        public String getColourText() {
            return this.colourText;
        }

        public void setColourText(String str) {
            this.colourText = str;
        }
    }
}
