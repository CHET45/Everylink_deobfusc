package com.aivox.common.model;

import com.aivox.common.model.Transcribe;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: ConferenceSummary.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u0000 42\u00020\u00012\u00020\u0002:\u00014B\u0005¢\u0006\u0002\u0010\u0003J\b\u00102\u001a\u00020\u0005H\u0016J\b\u00103\u001a\u00020!H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0007\"\u0004\b\u0018\u0010\tR\"\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0007\"\u0004\b.\u0010\tR\u001c\u0010/\u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010#\"\u0004\b1\u0010%¨\u00065"}, m1901d2 = {"Lcom/aivox/common/model/ConferenceSummary;", "Lcom/chad/library/adapter/base/entity/MultiItemEntity;", "Ljava/io/Serializable;", "()V", "audioId", "", "getAudioId", "()I", "setAudioId", "(I)V", "audioTransId", "getAudioTransId", "setAudioTransId", "beginAt", "getBeginAt", "setBeginAt", "curSelect", "getCurSelect", "setCurSelect", "endAt", "getEndAt", "setEndAt", "id", "getId", "setId", "imageList", "", "Lcom/aivox/common/model/Transcribe$TagImgBean;", "getImageList", "()Ljava/util/List;", "setImageList", "(Ljava/util/List;)V", "onebest", "", "getOnebest", "()Ljava/lang/String;", "setOnebest", "(Ljava/lang/String;)V", "pick", "", "getPick", "()Z", "setPick", "(Z)V", "showTypeId", "getShowTypeId", "setShowTypeId", "translate", "getTranslate", "setTranslate", "getItemType", "toString", "Companion", "common_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ConferenceSummary implements MultiItemEntity, Serializable {
    public static final int TYPE_1 = 2;
    public static final int TYPE_2 = 3;
    public static final int TYPE_3 = 4;
    public static final int TYPE_4 = 5;
    public static final int TYPE_ITEM = 0;
    private int audioId;
    private int audioTransId;
    private int beginAt;
    private int curSelect = -1;
    private int endAt;
    private int id;
    private List<? extends Transcribe.TagImgBean> imageList;
    private String onebest;
    private boolean pick;
    private int showTypeId;
    private String translate;

    public final int getShowTypeId() {
        return this.showTypeId;
    }

    public final void setShowTypeId(int i) {
        this.showTypeId = i;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final int getAudioId() {
        return this.audioId;
    }

    public final void setAudioId(int i) {
        this.audioId = i;
    }

    public final int getAudioTransId() {
        return this.audioTransId;
    }

    public final void setAudioTransId(int i) {
        this.audioTransId = i;
    }

    public final String getOnebest() {
        return this.onebest;
    }

    public final void setOnebest(String str) {
        this.onebest = str;
    }

    public final int getBeginAt() {
        return this.beginAt;
    }

    public final void setBeginAt(int i) {
        this.beginAt = i;
    }

    public final int getEndAt() {
        return this.endAt;
    }

    public final void setEndAt(int i) {
        this.endAt = i;
    }

    public final String getTranslate() {
        return this.translate;
    }

    public final void setTranslate(String str) {
        this.translate = str;
    }

    public final List<Transcribe.TagImgBean> getImageList() {
        return this.imageList;
    }

    public final void setImageList(List<? extends Transcribe.TagImgBean> list) {
        this.imageList = list;
    }

    public final int getCurSelect() {
        return this.curSelect;
    }

    public final void setCurSelect(int i) {
        this.curSelect = i;
    }

    public final boolean getPick() {
        return this.pick;
    }

    public final void setPick(boolean z) {
        this.pick = z;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    /* JADX INFO: renamed from: getItemType, reason: from getter */
    public int getShowTypeId() {
        return this.showTypeId;
    }

    public String toString() {
        return "ConferenceSummary(showTypeId=" + this.showTypeId + ", id=" + this.id + ", videosId=" + this.audioId + ", videoTransId=" + this.audioTransId + ", content=" + this.onebest + ", beginAt=" + this.beginAt + ", endAt=" + this.endAt + ')';
    }
}
