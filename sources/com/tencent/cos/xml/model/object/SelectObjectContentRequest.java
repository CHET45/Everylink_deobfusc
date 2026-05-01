package com.tencent.cos.xml.model.object;

import androidx.exifinterface.media.ExifInterface;
import com.tencent.cos.xml.listener.SelectObjectContentListener;
import com.tencent.cos.xml.model.tag.eventstreaming.InputSerialization;
import com.tencent.cos.xml.model.tag.eventstreaming.OutputSerialization;
import com.tencent.cos.xml.model.tag.eventstreaming.RequestProgress;
import com.tencent.cos.xml.model.tag.eventstreaming.SelectRequest;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SelectObjectContentRequest extends ObjectRequest {
    public static final String EXPRESSION_TYPE_SQL = "SQL";
    private String expression;
    private String expressionType;
    private InputSerialization inputSerialization;
    private OutputSerialization outputSerialization;
    private RequestProgress requestProgress;
    private SelectObjectContentListener selectObjectContentProgressListener;
    private String selectResponseFilePath;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public SelectObjectContentRequest(String str, String str2, String str3, String str4, RequestProgress requestProgress, InputSerialization inputSerialization, OutputSerialization outputSerialization) {
        super(str, str2);
        this.expression = str4;
        this.expressionType = str3;
        this.requestProgress = requestProgress;
        this.inputSerialization = inputSerialization;
        this.outputSerialization = outputSerialization;
    }

    public SelectObjectContentRequest(String str, String str2, String str3, boolean z, InputSerialization inputSerialization, OutputSerialization outputSerialization) {
        this(str, str2, EXPRESSION_TYPE_SQL, str3, new RequestProgress(Boolean.valueOf(z)), inputSerialization, outputSerialization);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("select", null);
        this.queryParameters.put("select-type", ExifInterface.GPS_MEASUREMENT_2D);
        return this.queryParameters;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "POST";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildSelectRequest(new SelectRequest(this.expressionType, this.expression, this.requestProgress, this.inputSerialization, this.outputSerialization)));
    }

    public void setSelectObjectContentProgressListener(SelectObjectContentListener selectObjectContentListener) {
        this.selectObjectContentProgressListener = selectObjectContentListener;
    }

    public SelectObjectContentListener getSelectObjectContentProgressListener() {
        return this.selectObjectContentProgressListener;
    }

    public void setSelectResponseFilePath(String str) {
        this.selectResponseFilePath = str;
    }

    public String getSelectResponseFilePath() {
        return this.selectResponseFilePath;
    }
}
