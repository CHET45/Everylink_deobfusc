package com.tencent.qcloud.core.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes4.dex */
public final class SignerFactory {
    private static final String COS_XML_SIGNER = "CosXmlSigner";
    private static final Map<String, Class<? extends QCloudSigner>> SIGNERS;
    private static final Map<String, QCloudSigner> SIGNER_INSTANCES;

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(5);
        SIGNERS = concurrentHashMap;
        SIGNER_INSTANCES = new ConcurrentHashMap(5);
        concurrentHashMap.put(COS_XML_SIGNER, COSXmlSigner.class);
    }

    private SignerFactory() {
    }

    public static void registerSigner(String str, Class<? extends QCloudSigner> cls) {
        if (str == null) {
            throw new IllegalArgumentException("signerType cannot be null");
        }
        if (cls == null) {
            throw new IllegalArgumentException("signerClass cannot be null");
        }
        SIGNERS.put(str, cls);
    }

    public static <T extends QCloudSigner> void registerSigner(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("signerType cannot be null");
        }
        if (t == null) {
            throw new IllegalArgumentException("signer instance cannot be null");
        }
        SIGNER_INSTANCES.put(str, t);
    }

    public static QCloudSigner getSigner(String str) {
        return lookupAndCreateSigner(str);
    }

    private static QCloudSigner lookupAndCreateSigner(String str) {
        Map<String, QCloudSigner> map = SIGNER_INSTANCES;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        return createSigner(str);
    }

    private static QCloudSigner createSigner(String str) {
        Class<? extends QCloudSigner> cls = SIGNERS.get(str);
        if (cls == null) {
            return null;
        }
        try {
            QCloudSigner qCloudSignerNewInstance = cls.newInstance();
            SIGNER_INSTANCES.put(str, qCloudSignerNewInstance);
            return qCloudSignerNewInstance;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot create an instance of " + cls.getName(), e);
        } catch (InstantiationException e2) {
            throw new IllegalStateException("Cannot create an instance of " + cls.getName(), e2);
        }
    }
}
