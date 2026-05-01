package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetLiveVideoAuditResponse {
    public GetLiveVideoAuditResponseJobsDetail jobsDetail;
    public String requestId;

    public static class AudioSectionInfoLibResults {
        public List<String> keywords;
        public String libName;
        public int libType;
    }

    public static class GetLiveVideoAuditResponseAudioSection {
        public GetLiveVideoAuditResponseAudioSectionInfo adsInfo;
        public int duration;
        public String label;
        public int offsetTime;
        public GetLiveVideoAuditResponseAudioSectionInfo pornInfo;
        public int result;
        public String text;
        public String url;
    }

    public static class GetLiveVideoAuditResponseAudioSectionInfo {
        public String category;
        public String hitFlag;
        public List<String> keywords;
        public List<AudioSectionInfoLibResults> libResults;
        public String score;
    }

    public static class GetLiveVideoAuditResponseInfo {
        public int count;
        public int hitFlag;
    }

    public static class GetLiveVideoAuditResponseJobsDetail {
        public GetLiveVideoAuditResponseInfo adsInfo;
        public List<GetLiveVideoAuditResponseAudioSection> audioSection;
        public String code;
        public String creationTime;
        public String dataId;
        public String jobId;
        public String label;
        public AuditListInfo listInfo;
        public GetLiveVideoAuditResponseInfo meaninglessInfo;
        public String message;
        public GetLiveVideoAuditResponseInfo pornInfo;
        public int result;
        public List<GetLiveVideoAuditResponseSnapshot> snapshot;
        public String snapshotCount;
        public String state;
        public String type;
        public GetLiveVideoAuditResponseUserInfo userInfo;
    }

    public static class GetLiveVideoAuditResponseSnapshot {
        public GetLiveVideoAuditResponseSnapshotInfo adsInfo;
        public String label;
        public GetLiveVideoAuditResponseSnapshotInfo meaninglessInfo;
        public GetLiveVideoAuditResponseSnapshotInfo pornInfo;
        public int result;
        public int snapshotTime;
        public String text;
        public String url;
    }

    public static class GetLiveVideoAuditResponseSnapshotInfo {
        public String category;
        public int hitFlag;
        public String label;
        public List<SnapshotInfoLibResults> libResults;
        public List<SnapshotInfoObjectResults> objectResults;
        public List<SnapshotInfoOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }

    public static class GetLiveVideoAuditResponseUserInfo {
        public String appId;
        public String deviceId;
        public String gender;

        /* JADX INFO: renamed from: iP */
        public String f1819iP;
        public String level;
        public String nickname;
        public String receiveTokenId;
        public String role;
        public String room;
        public String tokenId;
        public String type;
    }

    public static class SnapshotInfoLibResults {
        public String imageId;
        public int score;
    }

    public static class SnapshotInfoObjectResults {
        public SnapshotInfoOcrResultsLocation location;
        public String name;
    }

    public static class SnapshotInfoOcrResults {
        public List<String> keywords;
        public SnapshotInfoOcrResultsLocation location;
        public String text;
    }

    public static class SnapshotInfoOcrResultsLocation {
        public float height;
        public float rotate;
        public float width;

        /* JADX INFO: renamed from: x */
        public float f1820x;

        /* JADX INFO: renamed from: y */
        public float f1821y;
    }
}
