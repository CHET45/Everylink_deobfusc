package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.SelectObjectContentListener;
import com.tencent.cos.xml.model.object.SelectObjectContentResult;
import com.tencent.cos.xml.model.tag.CosError;
import com.tencent.cos.xml.model.tag.eventstreaming.Message;
import com.tencent.cos.xml.model.tag.eventstreaming.MessageDecoder;
import com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEvent;
import com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SelectObjectContentConverter<T> extends ResponseBodyConverter<T> {
    private SelectObjectContentListener contentListener;
    private String localPath;
    private MessageDecoder messageDecoder = new MessageDecoder();
    private SelectObjectContentResult selectObjectContentResult;

    public SelectObjectContentConverter(SelectObjectContentResult selectObjectContentResult, String str) {
        this.selectObjectContentResult = selectObjectContentResult;
        this.localPath = str;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse<T> httpResponse) throws QCloudServiceException, QCloudClientException {
        parseCOSXMLError(httpResponse);
        this.selectObjectContentResult.parseResponseBody(httpResponse);
        InputStream inputStreamByteStream = httpResponse.byteStream();
        byte[] bArr = new byte[256];
        FileOutputStream fileOutputStreamNewFileOutputStream = newFileOutputStream(this.localPath);
        while (true) {
            try {
                int i = inputStreamByteStream.read(bArr);
                if (i > 0) {
                    List<Message> listFeed = this.messageDecoder.feed(bArr, 0, i);
                    if (fileOutputStreamNewFileOutputStream != null) {
                        fileOutputStreamNewFileOutputStream.write(bArr, 0, i);
                    }
                    Iterator<Message> it = listFeed.iterator();
                    while (it.hasNext()) {
                        SelectObjectContentEvent selectObjectContentEventUnmarshalMessage = SelectObjectContentEventUnmarshaller.unmarshalMessage(it.next());
                        SelectObjectContentListener selectObjectContentListener = this.contentListener;
                        if (selectObjectContentListener != null) {
                            selectObjectContentListener.onProcess(selectObjectContentEventUnmarshalMessage);
                        }
                    }
                } else {
                    closeFileOutputStream(fileOutputStreamNewFileOutputStream);
                    return (T) this.selectObjectContentResult;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new QCloudClientException(e);
            }
        }
    }

    public void setContentListener(SelectObjectContentListener selectObjectContentListener) {
        this.contentListener = selectObjectContentListener;
    }

    private FileOutputStream newFileOutputStream(String str) throws QCloudClientException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        try {
            if (file.createNewFile()) {
                return new FileOutputStream(file);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new QCloudClientException(e);
        }
    }

    private void closeFileOutputStream(FileOutputStream fileOutputStream) throws QCloudClientException {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new QCloudClientException(e);
            }
        }
    }

    private void parseCOSXMLError(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        int iCode = httpResponse.code();
        if (iCode < 200 || iCode >= 300) {
            CosXmlServiceException cosXmlServiceException = new CosXmlServiceException(httpResponse.message(), httpResponse.host());
            cosXmlServiceException.setStatusCode(iCode);
            cosXmlServiceException.setRequestId(httpResponse.header(Headers.REQUEST_ID));
            InputStream inputStreamByteStream = httpResponse.byteStream();
            if (inputStreamByteStream != null) {
                CosError cosError = new CosError();
                try {
                    BaseXmlSlimParser.parseError(inputStreamByteStream, cosError);
                    if (cosError.code != null) {
                        cosXmlServiceException.setErrorCode(cosError.code);
                    }
                    if (cosError.message != null) {
                        cosXmlServiceException.setErrorMessage(cosError.message);
                    }
                    if (cosError.requestId != null) {
                        cosXmlServiceException.setRequestId(cosError.requestId);
                    }
                    if (cosError.resource != null) {
                        cosXmlServiceException.setServiceName(cosError.resource);
                        throw cosXmlServiceException;
                    }
                    throw cosXmlServiceException;
                } catch (IOException e) {
                    throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
                } catch (XmlPullParserException e2) {
                    throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
                }
            }
            throw cosXmlServiceException;
        }
    }
}
