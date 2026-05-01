package com.tencent.qcloud.core.auth;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.QCloudHttpRequest;
import java.net.URL;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class COSXmlSigner implements QCloudSigner {
    static final String COS_SESSION_TOKEN = "x-cos-security-token";

    @Override // com.tencent.qcloud.core.auth.QCloudSigner
    public void sign(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials) throws QCloudClientException {
        if (qCloudCredentials == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("Credentials is null."));
        }
        COSXmlSignSourceProvider cOSXmlSignSourceProvider = (COSXmlSignSourceProvider) qCloudHttpRequest.getSignProvider();
        if (cOSXmlSignSourceProvider == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("No sign provider for cos xml signer."));
        }
        StringBuilder sb = new StringBuilder("q-sign-algorithm=sha1&q-ak=");
        QCloudLifecycleCredentials qCloudLifecycleCredentials = (QCloudLifecycleCredentials) qCloudCredentials;
        String keyTime = qCloudHttpRequest.getKeyTime();
        if (keyTime == null) {
            keyTime = qCloudLifecycleCredentials.getKeyTime();
        }
        cOSXmlSignSourceProvider.setSignTime(keyTime);
        sb.append(qCloudCredentials.getSecretId()).append("&q-sign-time=").append(keyTime).append("&q-key-time=").append(qCloudLifecycleCredentials.getKeyTime()).append("&q-header-list=").append(cOSXmlSignSourceProvider.getRealHeaderList().toLowerCase(Locale.ROOT)).append("&q-url-param-list=").append(cOSXmlSignSourceProvider.getRealParameterList().toLowerCase(Locale.ROOT)).append("&q-signature=").append(signature(cOSXmlSignSourceProvider.source(qCloudHttpRequest), qCloudLifecycleCredentials.getSignKey()));
        String string = sb.toString();
        if (qCloudHttpRequest.isSignInUrl()) {
            addAuthInPara(qCloudHttpRequest, qCloudCredentials, string);
        } else {
            addAuthInHeader(qCloudHttpRequest, qCloudCredentials, string);
        }
        cOSXmlSignSourceProvider.onSignRequestSuccess(qCloudHttpRequest, qCloudCredentials, string);
    }

    private void addAuthInPara(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials, String str) {
        String strConcat;
        URL url = qCloudHttpRequest.url();
        if (qCloudCredentials instanceof SessionQCloudCredentials) {
            str = str.concat("&token").concat(PunctuationConst.EQUAL).concat(((SessionQCloudCredentials) qCloudCredentials).getToken());
        }
        String query = url.getQuery();
        String string = url.toString();
        int iIndexOf = string.indexOf(63);
        if (iIndexOf < 0) {
            strConcat = string.concat(PunctuationConst.QUESTION_MARK).concat(str);
        } else {
            int length = iIndexOf + query.length() + 1;
            strConcat = string.substring(0, length).concat(PunctuationConst.AND).concat(str).concat(string.substring(length));
        }
        qCloudHttpRequest.setUrl(strConcat);
    }

    protected String getSessionTokenKey() {
        return "x-cos-security-token";
    }

    private void addAuthInHeader(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials, String str) {
        qCloudHttpRequest.removeHeader("Authorization");
        qCloudHttpRequest.addHeader("Authorization", str);
        if (qCloudCredentials instanceof SessionQCloudCredentials) {
            String sessionTokenKey = getSessionTokenKey();
            qCloudHttpRequest.removeHeader(sessionTokenKey);
            qCloudHttpRequest.addHeader(sessionTokenKey, ((SessionQCloudCredentials) qCloudCredentials).getToken());
        }
    }

    private String signature(String str, String str2) {
        byte[] bArrHmacSha1 = Utils.hmacSha1(str, str2);
        if (bArrHmacSha1 == null) {
            return "";
        }
        return new String(Utils.encodeHex(bArrHmacSha1));
    }
}
