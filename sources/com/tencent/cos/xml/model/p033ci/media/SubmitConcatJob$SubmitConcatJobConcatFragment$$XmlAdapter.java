package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter extends IXmlAdapter<SubmitConcatJob.SubmitConcatJobConcatFragment> {
    private HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>> childElementBinders;

    public SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatFragment.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FragmentIndex", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatFragment.fragmentIndex = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatFragment.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatFragment$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatFragment.endTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitConcatJob.SubmitConcatJobConcatFragment fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment = new SubmitConcatJob.SubmitConcatJobConcatFragment();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatFragment> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitConcatJobConcatFragment, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ConcatFragment" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitConcatJobConcatFragment;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitConcatJobConcatFragment;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob.SubmitConcatJobConcatFragment submitConcatJobConcatFragment, String str) throws XmlPullParserException, IOException {
        if (submitConcatJobConcatFragment == null) {
            return;
        }
        if (str == null) {
            str = "ConcatFragment";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJobConcatFragment.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(submitConcatJobConcatFragment.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (submitConcatJobConcatFragment.fragmentIndex != null) {
            xmlSerializer.startTag("", "FragmentIndex");
            xmlSerializer.text(String.valueOf(submitConcatJobConcatFragment.fragmentIndex));
            xmlSerializer.endTag("", "FragmentIndex");
        }
        if (submitConcatJobConcatFragment.startTime != null) {
            xmlSerializer.startTag("", "StartTime");
            xmlSerializer.text(String.valueOf(submitConcatJobConcatFragment.startTime));
            xmlSerializer.endTag("", "StartTime");
        }
        if (submitConcatJobConcatFragment.endTime != null) {
            xmlSerializer.startTag("", "EndTime");
            xmlSerializer.text(String.valueOf(submitConcatJobConcatFragment.endTime));
            xmlSerializer.endTag("", "EndTime");
        }
        xmlSerializer.endTag("", str);
    }
}
