package com.tencent.qcloud.network.sonar.ping;

import com.tencent.qcloud.network.sonar.Sonar;
import com.tencent.qcloud.network.sonar.SonarCallback;

/* JADX INFO: loaded from: classes4.dex */
public class PingSonar implements Sonar<PingResult> {
    public SonarCallback.Step<String> stepCallback;
    private final int count = 10;
    private final int size = 56;
    private final int interval = 200;

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public void stop() {
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:31|166|32|(4:33|(3:35|(3:169|37|172)(1:171)|170)(1:168)|134|135)|(4:38|(3:40|(3:174|42|177)(1:176)|175)(1:173)|134|135)|43|160|44|(1:46)|134|135) */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0102, code lost:
    
        r2 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0103, code lost:
    
        r4 = r12.stepCallback;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0105, code lost:
    
        if (r4 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0112, code lost:
    
        if (com.tencent.qcloud.network.sonar.utils.SonarLog.openLog != false) goto L54;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0107 A[PHI: r2 r4
  0x0107: PHI (r2v17 'e' java.lang.Exception) = 
  (r2v11 'e' java.lang.Exception)
  (r2v13 'e' java.lang.Exception)
  (r2v15 'e' java.lang.Exception)
  (r2v21 'e' java.lang.Exception)
 binds: [B:87:0x015e, B:108:0x018b, B:129:0x01b9, B:50:0x0105] A[DONT_GENERATE, DONT_INLINE]
  0x0107: PHI (r4v14 com.tencent.qcloud.network.sonar.SonarCallback$Step<java.lang.String>) = 
  (r4v6 com.tencent.qcloud.network.sonar.SonarCallback$Step<java.lang.String>)
  (r4v9 com.tencent.qcloud.network.sonar.SonarCallback$Step<java.lang.String>)
  (r4v12 com.tencent.qcloud.network.sonar.SonarCallback$Step<java.lang.String>)
  (r4v23 com.tencent.qcloud.network.sonar.SonarCallback$Step<java.lang.String>)
 binds: [B:87:0x015e, B:108:0x018b, B:129:0x01b9, B:50:0x0105] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0114 A[PHI: r2
  0x0114: PHI (r2v16 'e' java.lang.Exception) = 
  (r2v11 'e' java.lang.Exception)
  (r2v13 'e' java.lang.Exception)
  (r2v15 'e' java.lang.Exception)
  (r2v21 'e' java.lang.Exception)
 binds: [B:90:0x0163, B:111:0x0191, B:132:0x01bf, B:53:0x0112] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r2v20, types: [java.lang.Process] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v5 */
    @Override // com.tencent.qcloud.network.sonar.Sonar
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.tencent.qcloud.network.sonar.SonarResult<com.tencent.qcloud.network.sonar.ping.PingResult> start(com.tencent.qcloud.network.sonar.SonarRequest r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.network.sonar.ping.PingSonar.start(com.tencent.qcloud.network.sonar.SonarRequest):com.tencent.qcloud.network.sonar.SonarResult");
    }
}
