package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSongList$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3008x77bd892f extends IXmlAdapter<PostSoundHoundResponse.PostSoundHoundResponseSongList> {
    private HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList>> childElementBinders;

    public C3008x77bd892f() {
        HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Inlier", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSongList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseSongList postSoundHoundResponseSongList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseSongList.inlier = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SingerName", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSongList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseSongList postSoundHoundResponseSongList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseSongList.singerName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SongName", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseSongList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseSongList postSoundHoundResponseSongList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseSongList.songName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSoundHoundResponse.PostSoundHoundResponseSongList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSoundHoundResponse.PostSoundHoundResponseSongList postSoundHoundResponseSongList = new PostSoundHoundResponse.PostSoundHoundResponseSongList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseSongList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSoundHoundResponseSongList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SongList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSoundHoundResponseSongList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSoundHoundResponseSongList;
    }
}
