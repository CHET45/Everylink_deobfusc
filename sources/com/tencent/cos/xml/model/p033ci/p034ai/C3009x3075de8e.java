package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSoundHoundResult$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3009x3075de8e extends IXmlAdapter<PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult> {
    private HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult>> childElementBinders;

    public C3009x3075de8e() {
        HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SongList", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSoundHoundResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult postSoundHoundResponseSoundHoundResult, String str) throws XmlPullParserException, IOException {
                if (postSoundHoundResponseSoundHoundResult.songList == null) {
                    postSoundHoundResponseSoundHoundResult.songList = new ArrayList();
                }
                postSoundHoundResponseSoundHoundResult.songList.add((PostSoundHoundResponse.PostSoundHoundResponseSongList) QCloudXml.fromXml(xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseSongList.class, "SongList"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult postSoundHoundResponseSoundHoundResult = new PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSoundHoundResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSoundHoundResponseSoundHoundResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SoundHoundResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSoundHoundResponseSoundHoundResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSoundHoundResponseSoundHoundResult;
    }
}
