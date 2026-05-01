package com.tencent.cos.xml.crypto;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.common.Range;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.p035s3.Base64;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.core.util.QCloudUtils;
import com.tencentcloudapi.kms.v20190118.models.GenerateDataKeyRequest;
import com.tencentcloudapi.kms.v20190118.models.GenerateDataKeyResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CryptoModuleBase implements CryptoModule {
    protected static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final boolean IS_MULTI_PART = true;
    protected final ContentCryptoScheme contentCryptoScheme;
    protected final CosXmlSimpleService cos;
    protected final COSCryptoScheme cryptoScheme;
    protected final EncryptionMaterialsProvider kekMaterialsProvider;
    protected final QCLOUDKMS kms;
    protected final Map<String, MultipartUploadCryptoContext> multipartUploadContexts = Collections.synchronizedMap(new HashMap());

    abstract CipherLite cipherLiteForNextPart(MultipartUploadCryptoContext multipartUploadCryptoContext);

    protected abstract long ciphertextLength(long j);

    abstract long computeLastPartSize(UploadPartRequest uploadPartRequest);

    abstract MultipartUploadCryptoContext newUploadContext(InitMultipartUploadRequest initMultipartUploadRequest, ContentCryptoMaterial contentCryptoMaterial);

    protected CryptoModuleBase(QCLOUDKMS qcloudkms, CosXmlSimpleService cosXmlSimpleService, QCloudCredentialProvider qCloudCredentialProvider, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this.kekMaterialsProvider = encryptionMaterialsProvider;
        this.cos = cosXmlSimpleService;
        COSCryptoScheme cOSCryptoSchemeFrom = COSCryptoScheme.from();
        this.cryptoScheme = cOSCryptoSchemeFrom;
        this.contentCryptoScheme = cOSCryptoSchemeFrom.getContentCryptoScheme();
        this.kms = qcloudkms;
    }

    protected CryptoModuleBase(CosXmlSimpleService cosXmlSimpleService, QCloudCredentialProvider qCloudCredentialProvider, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this.kekMaterialsProvider = encryptionMaterialsProvider;
        this.cos = cosXmlSimpleService;
        COSCryptoScheme cOSCryptoSchemeFrom = COSCryptoScheme.from();
        this.cryptoScheme = cOSCryptoSchemeFrom;
        this.contentCryptoScheme = cOSCryptoSchemeFrom.getContentCryptoScheme();
        this.kms = null;
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public PutObjectResult putObjectSecurely(PutObjectRequest putObjectRequest) throws CosXmlServiceException, CosXmlClientException {
        ContentCryptoMaterial contentCryptoMaterialCreateContentCryptoMaterial = createContentCryptoMaterial(putObjectRequest);
        PutObjectRequest putObjectRequestWrapWithCipher = wrapWithCipher(putObjectRequest, contentCryptoMaterialCreateContentCryptoMaterial);
        putObjectRequest.setMetadata(updateMetadataWithContentCryptoMaterial(putObjectRequest.getMetadata(), contentCryptoMaterialCreateContentCryptoMaterial));
        return this.cos.putObject(putObjectRequestWrapWithCipher);
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public InitMultipartUploadResult initMultipartUploadSecurely(InitMultipartUploadRequest initMultipartUploadRequest) throws CosXmlServiceException, CosXmlClientException {
        cipherInitMultipartUploadRequest(initMultipartUploadRequest);
        ContentCryptoMaterial contentCryptoMaterialCreateContentCryptoMaterial = createContentCryptoMaterial(initMultipartUploadRequest);
        ObjectMetadata metadata = initMultipartUploadRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        initMultipartUploadRequest.setMetadata(updateMetadataWithContentCryptoMaterial(metadata, contentCryptoMaterialCreateContentCryptoMaterial));
        InitMultipartUploadResult initMultipartUploadResultInitMultipartUpload = this.cos.initMultipartUpload(initMultipartUploadRequest);
        this.multipartUploadContexts.put(initMultipartUploadResultInitMultipartUpload.initMultipartUpload.uploadId, newUploadContext(initMultipartUploadRequest, contentCryptoMaterialCreateContentCryptoMaterial));
        return initMultipartUploadResultInitMultipartUpload;
    }

    public boolean hasMultipartUploadContext(String str) {
        return this.multipartUploadContexts.containsKey(str);
    }

    public MultipartUploadCryptoContext getCryptoContext(String str) {
        return this.multipartUploadContexts.get(str);
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public UploadPartResult uploadPartSecurely(UploadPartRequest uploadPartRequest) throws CosXmlServiceException, CosXmlClientException {
        int blockSizeInBytes = this.contentCryptoScheme.getBlockSizeInBytes();
        boolean zIsLastPart = uploadPartRequest.isLastPart();
        String uploadId = uploadPartRequest.getUploadId();
        boolean z = 0 == uploadPartRequest.getFileLength() % ((long) blockSizeInBytes);
        if (!zIsLastPart && !z) {
            throw CosXmlClientException.internalException("Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size (" + blockSizeInBytes + ") with the exception of the last part.");
        }
        MultipartUploadCryptoContext multipartUploadCryptoContext = this.multipartUploadContexts.get(uploadId);
        if (multipartUploadCryptoContext == null) {
            throw CosXmlClientException.internalException("No client-side information available on upload ID " + uploadId);
        }
        multipartUploadCryptoContext.beginPartUpload(uploadPartRequest.getPartNumber());
        try {
            uploadPartRequest.setInputStream(newMultipartCOSCipherInputStream(uploadPartRequest, cipherLiteForNextPart(multipartUploadCryptoContext)));
            uploadPartRequest.setSrcPath(null);
            uploadPartRequest.setFileOffset(0L);
            if (zIsLastPart) {
                long jComputeLastPartSize = computeLastPartSize(uploadPartRequest);
                if (jComputeLastPartSize > -1) {
                    uploadPartRequest.setFileContentLength(jComputeLastPartSize);
                }
                if (multipartUploadCryptoContext.hasFinalPartBeenSeen()) {
                    throw CosXmlClientException.internalException("This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part.");
                }
            }
            UploadPartResult uploadPartResultUploadPart = this.cos.uploadPart(uploadPartRequest);
            if (zIsLastPart) {
                multipartUploadCryptoContext.setHasFinalPartBeenSeen(true);
            }
            return uploadPartResultUploadPart;
        } finally {
            multipartUploadCryptoContext.endPartUpload();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b4  */
    @Override // com.tencent.cos.xml.crypto.CryptoModule
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void uploadPartAsyncSecurely(com.tencent.cos.xml.model.object.UploadPartRequest r12, com.tencent.cos.xml.listener.CosXmlResultListener r13) throws java.lang.Throwable {
        /*
            r11 = this;
            java.lang.String r0 = "No client-side information available on upload ID "
            java.lang.String r1 = "Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size ("
            boolean r2 = r12.isLastPart()
            r3 = 1
            r4 = 0
            com.tencent.cos.xml.crypto.ContentCryptoScheme r5 = r11.contentCryptoScheme     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            int r5 = r5.getBlockSizeInBytes()     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            java.lang.String r6 = r12.getUploadId()     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            long r7 = r12.getFileLength()     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            long r9 = (long) r5     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            long r7 = r7 % r9
            r9 = 0
            int r7 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r7 != 0) goto L22
            r7 = r3
            goto L23
        L22:
            r7 = 0
        L23:
            if (r2 != 0) goto L40
            if (r7 == 0) goto L28
            goto L40
        L28:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            java.lang.String r1 = ") with the exception of the last part."
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            com.tencent.cos.xml.exception.CosXmlClientException r0 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r0)     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            throw r0     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
        L40:
            java.util.Map<java.lang.String, com.tencent.cos.xml.crypto.MultipartUploadCryptoContext> r1 = r11.multipartUploadContexts     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            com.tencent.cos.xml.crypto.MultipartUploadCryptoContext r1 = (com.tencent.cos.xml.crypto.MultipartUploadCryptoContext) r1     // Catch: java.lang.Throwable -> L9e com.tencent.cos.xml.exception.CosXmlClientException -> La0
            if (r1 == 0) goto L8c
            int r0 = r12.getPartNumber()     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r1.beginPartUpload(r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            com.tencent.cos.xml.crypto.CipherLite r0 = r11.cipherLiteForNextPart(r1)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            com.tencent.cos.xml.crypto.CipherLiteInputStream r0 = r11.newMultipartCOSCipherInputStream(r12, r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r12.setInputStream(r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r12.setSrcPath(r4)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r12.setFileOffset(r9)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            if (r2 == 0) goto L7f
            long r5 = r11.computeLastPartSize(r12)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r7 = -1
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 <= 0) goto L71
            r12.setFileContentLength(r5)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
        L71:
            boolean r0 = r1.hasFinalPartBeenSeen()     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            if (r0 != 0) goto L78
            goto L7f
        L78:
            java.lang.String r0 = "This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part."
            com.tencent.cos.xml.exception.CosXmlClientException r0 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            throw r0     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
        L7f:
            com.tencent.cos.xml.CosXmlSimpleService r0 = r11.cos     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r0.uploadPartAsync(r12, r13)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            if (r1 == 0) goto La8
        L86:
            r1.endPartUpload()
            goto La8
        L8a:
            r0 = move-exception
            goto La2
        L8c:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            r5.<init>(r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            java.lang.StringBuilder r0 = r5.append(r6)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            java.lang.String r0 = r0.toString()     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            com.tencent.cos.xml.exception.CosXmlClientException r0 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r0)     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
            throw r0     // Catch: com.tencent.cos.xml.exception.CosXmlClientException -> L8a java.lang.Throwable -> Lb0
        L9e:
            r12 = move-exception
            goto Lb2
        La0:
            r0 = move-exception
            r1 = r4
        La2:
            r13.onFail(r12, r0, r4)     // Catch: java.lang.Throwable -> Lb0
            if (r1 == 0) goto La8
            goto L86
        La8:
            if (r2 == 0) goto Laf
            if (r1 == 0) goto Laf
            r1.setHasFinalPartBeenSeen(r3)
        Laf:
            return
        Lb0:
            r12 = move-exception
            r4 = r1
        Lb2:
            if (r4 == 0) goto Lb7
            r4.endPartUpload()
        Lb7:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.crypto.CryptoModuleBase.uploadPartAsyncSecurely(com.tencent.cos.xml.model.object.UploadPartRequest, com.tencent.cos.xml.listener.CosXmlResultListener):void");
    }

    public final CipherLiteInputStream newMultipartCOSCipherInputStream(UploadPartRequest uploadPartRequest, CipherLite cipherLite) throws CosXmlClientException {
        InputStream inputStreamOpenInputStream;
        String srcPath = uploadPartRequest.getSrcPath();
        Uri uri = uploadPartRequest.getUri();
        try {
            if (!TextUtils.isEmpty(srcPath)) {
                inputStreamOpenInputStream = new ResettableInputStream(srcPath);
            } else {
                inputStreamOpenInputStream = (uri == null || ContextHolder.getAppContext() == null) ? null : ContextHolder.getAppContext().getContentResolver().openInputStream(uri);
            }
            InputSubstream inputSubstream = new InputSubstream(inputStreamOpenInputStream, uploadPartRequest.getFileOffset(), uploadPartRequest.getFileContentLength(), uploadPartRequest.isLastPart());
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(inputSubstream, cipherLite, 2048, true, uploadPartRequest.isLastPart());
            }
            return new RenewableCipherLiteInputStream(inputSubstream, cipherLite, 2048, true, uploadPartRequest.isLastPart());
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public CompleteMultiUploadResult completeMultipartUploadSecurely(CompleteMultiUploadRequest completeMultiUploadRequest) throws CosXmlServiceException, CosXmlClientException {
        String uploadId = completeMultiUploadRequest.getUploadId();
        MultipartUploadCryptoContext multipartUploadCryptoContext = this.multipartUploadContexts.get(uploadId);
        if (multipartUploadCryptoContext != null && !multipartUploadCryptoContext.hasFinalPartBeenSeen()) {
            throw CosXmlClientException.internalException("Unable to complete an encrypted multipart upload without being told which part was the last.  Without knowing which part was the last, the encrypted data in COS is incomplete and corrupt.");
        }
        CompleteMultiUploadResult completeMultiUploadResultCompleteMultiUpload = this.cos.completeMultiUpload(completeMultiUploadRequest);
        this.multipartUploadContexts.remove(uploadId);
        return completeMultiUploadResultCompleteMultiUpload;
    }

    protected final ObjectMetadata updateMetadataWithContentCryptoMaterial(ObjectMetadata objectMetadata, ContentCryptoMaterial contentCryptoMaterial) throws CosXmlClientException {
        if (objectMetadata == null) {
            objectMetadata = new ObjectMetadata();
        }
        try {
            return contentCryptoMaterial.toObjectMetadata(objectMetadata);
        } catch (JSONException e) {
            throw CosXmlClientException.internalException(e.getMessage());
        }
    }

    protected final ContentCryptoMaterial createContentCryptoMaterial(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        return newContentCryptoMaterial(this.kekMaterialsProvider, null, cosXmlRequest);
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider encryptionMaterialsProvider, Map<String, String> map, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        EncryptionMaterials encryptionMaterials = encryptionMaterialsProvider.getEncryptionMaterials(map);
        if (encryptionMaterials == null) {
            return null;
        }
        return buildContentCryptoMaterial(encryptionMaterials, provider, cosXmlRequest);
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider encryptionMaterialsProvider, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        EncryptionMaterials encryptionMaterials = encryptionMaterialsProvider.getEncryptionMaterials();
        if (encryptionMaterials == null) {
            throw CosXmlClientException.internalException("No material available from the encryption material provider");
        }
        return buildContentCryptoMaterial(encryptionMaterials, provider, cosXmlRequest);
    }

    private ContentCryptoMaterial buildContentCryptoMaterial(EncryptionMaterials encryptionMaterials, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        byte[] bArr = new byte[this.contentCryptoScheme.getIVLengthInBytes()];
        this.cryptoScheme.getSecureRandom().nextBytes(bArr);
        if (encryptionMaterials.isKMSEnabled()) {
            Map<String, String> mapMergeMaterialDescriptions = ContentCryptoMaterial.mergeMaterialDescriptions(encryptionMaterials, cosXmlRequest);
            GenerateDataKeyRequest generateDataKeyRequest = new GenerateDataKeyRequest();
            try {
                generateDataKeyRequest.setEncryptionContext(JSONUtils.toJsonString(mapMergeMaterialDescriptions));
                generateDataKeyRequest.setKeyId(encryptionMaterials.getCustomerMasterKeyId());
                generateDataKeyRequest.setKeySpec(this.contentCryptoScheme.getKeySpec());
                GenerateDataKeyResponse generateDataKeyResponseGenerateDataKey = this.kms.generateDataKey(generateDataKeyRequest);
                return ContentCryptoMaterial.wrap(new SecretKeySpec(Base64.decode(generateDataKeyResponseGenerateDataKey.getPlaintext()), this.contentCryptoScheme.getKeyGeneratorAlgorithm()), bArr, this.contentCryptoScheme, provider, new KMSSecuredCEK(generateDataKeyResponseGenerateDataKey.getCiphertextBlob().getBytes(), mapMergeMaterialDescriptions));
            } catch (JSONException unused) {
                throw CosXmlClientException.internalException("generate datakey request set encryption context got json processing exception");
            }
        }
        return ContentCryptoMaterial.create(generateCEK(encryptionMaterials, provider), bArr, encryptionMaterials, this.cryptoScheme, provider, this.kms, cosXmlRequest);
    }

    protected final SecretKey generateCEK(EncryptionMaterials encryptionMaterials, Provider provider) throws CosXmlClientException {
        KeyGenerator keyGenerator;
        boolean zEquals;
        String keyGeneratorAlgorithm = this.contentCryptoScheme.getKeyGeneratorAlgorithm();
        try {
            if (provider == null) {
                keyGenerator = KeyGenerator.getInstance(keyGeneratorAlgorithm);
            } else {
                keyGenerator = KeyGenerator.getInstance(keyGeneratorAlgorithm, provider);
            }
            keyGenerator.init(this.contentCryptoScheme.getKeyLengthInBits(), this.cryptoScheme.getSecureRandom());
            KeyPair keyPair = encryptionMaterials.getKeyPair();
            if (keyPair == null || this.cryptoScheme.getKeyWrapScheme().getKeyWrapAlgorithm(keyPair.getPublic()) != null) {
                zEquals = false;
            } else {
                Provider provider2 = keyGenerator.getProvider();
                zEquals = "BC".equals(provider2 == null ? null : provider2.getName());
            }
            SecretKey secretKeyGenerateKey = keyGenerator.generateKey();
            if (zEquals && secretKeyGenerateKey.getEncoded()[0] == 0) {
                for (int i = 0; i < 10; i++) {
                    SecretKey secretKeyGenerateKey2 = keyGenerator.generateKey();
                    if (secretKeyGenerateKey2.getEncoded()[0] != 0) {
                        return secretKeyGenerateKey2;
                    }
                }
                throw CosXmlClientException.internalException("Failed to generate secret key");
            }
            return secretKeyGenerateKey;
        } catch (NoSuchAlgorithmException e) {
            throw CosXmlClientException.internalException("Unable to generate envelope symmetric key:" + e.getMessage());
        }
    }

    private InputStream openInputStream(PutObjectRequest putObjectRequest) throws IOException {
        String srcPath = putObjectRequest.getSrcPath();
        Uri uri = putObjectRequest.getUri();
        if (srcPath != null) {
            return new FileInputStream(srcPath);
        }
        if (uri == null || ContextHolder.getAppContext() == null) {
            return null;
        }
        return ContextHolder.getAppContext().getContentResolver().openInputStream(uri);
    }

    protected final PutObjectRequest wrapWithCipher(PutObjectRequest putObjectRequest, ContentCryptoMaterial contentCryptoMaterial) throws CosXmlClientException {
        ObjectMetadata metadata = putObjectRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        String contentMD5 = metadata.getContentMD5();
        if (TextUtils.isEmpty(contentMD5)) {
            try {
                InputStream inputStreamOpenInputStream = openInputStream(putObjectRequest);
                if (inputStreamOpenInputStream != null) {
                    contentMD5 = DigestUtils.getCOSMd5(inputStreamOpenInputStream, 0L, -1L);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contentMD5 != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, contentMD5);
        }
        metadata.setContentMD5(null);
        long jPlaintextLength = plaintextLength(putObjectRequest, metadata);
        if (jPlaintextLength >= 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(jPlaintextLength));
            metadata.setContentLength(ciphertextLength(jPlaintextLength));
        }
        putObjectRequest.setMetadata(metadata);
        putObjectRequest.setInputStream(newCOSCipherLiteInputStream(putObjectRequest, contentCryptoMaterial, jPlaintextLength));
        putObjectRequest.setSrcPath(null);
        return putObjectRequest;
    }

    protected final InitMultipartUploadRequest cipherInitMultipartUploadRequest(InitMultipartUploadRequest initMultipartUploadRequest) {
        ObjectMetadata metadata = initMultipartUploadRequest.getMetadata();
        if (metadata == null) {
            return initMultipartUploadRequest;
        }
        if (metadata.getContentMD5() != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, metadata.getContentMD5());
        }
        metadata.setContentMD5(null);
        if (metadata.getContentLength() != 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(metadata.getContentLength()));
            metadata.setContentLength(0L);
        }
        initMultipartUploadRequest.setMetadata(metadata);
        return initMultipartUploadRequest;
    }

    private CipherLiteInputStream newCOSCipherLiteInputStream(PutObjectRequest putObjectRequest, ContentCryptoMaterial contentCryptoMaterial, long j) throws CosXmlClientException {
        try {
            InputStream inputStreamOpenInputStream = openInputStream(putObjectRequest);
            if (j > -1) {
                inputStreamOpenInputStream = new LengthCheckInputStream(inputStreamOpenInputStream, j, false);
            }
            CipherLite cipherLite = contentCryptoMaterial.getCipherLite();
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(inputStreamOpenInputStream, cipherLite, 2048);
            }
            return new RenewableCipherLiteInputStream(inputStreamOpenInputStream, cipherLite, 2048);
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    public CipherLiteInputStream newCOSCipherLiteInputStream(PutObjectRequest putObjectRequest, CipherLite cipherLite) throws CosXmlClientException {
        try {
            InputStream inputStreamOpenInputStream = openInputStream(putObjectRequest);
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(inputStreamOpenInputStream, cipherLite, 2048);
            }
            return new RenewableCipherLiteInputStream(inputStreamOpenInputStream, cipherLite, 2048);
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    protected final long plaintextLength(PutObjectRequest putObjectRequest, ObjectMetadata objectMetadata) {
        Context appContext;
        String srcPath = putObjectRequest.getSrcPath();
        Uri uri = putObjectRequest.getUri();
        if (!TextUtils.isEmpty(srcPath)) {
            return new File(srcPath).length();
        }
        if (uri == null || (appContext = ContextHolder.getAppContext()) == null) {
            return -1L;
        }
        return QCloudUtils.getUriContentLength(uri, appContext.getContentResolver());
    }

    public final COSCryptoScheme getCOSCryptoScheme() {
        return this.cryptoScheme;
    }

    static Range getAdjustedCryptoRange(Range range) {
        if (range == null) {
            return null;
        }
        return new Range(getCipherBlockLowerBound(range.getStart()), range.getEnd() != -1 ? getCipherBlockUpperBound(range.getEnd()) : -1L);
    }

    static long[] getAdjustedCryptoRange(long[] jArr) {
        if (jArr == null) {
            return null;
        }
        long j = jArr[0];
        if (j > jArr[1]) {
            return null;
        }
        return new long[]{getCipherBlockLowerBound(j), getCipherBlockUpperBound(jArr[1])};
    }

    private static long getCipherBlockLowerBound(long j) {
        long j2 = (j - (j % 16)) - 16;
        if (j2 < 0) {
            return 0L;
        }
        return j2;
    }

    private static long getCipherBlockUpperBound(long j) {
        long j2 = j + (16 - (j % 16)) + 16;
        if (j2 < 0) {
            return Long.MAX_VALUE;
        }
        return j2;
    }
}
