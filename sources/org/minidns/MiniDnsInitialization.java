package org.minidns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class MiniDnsInitialization {
    private static final Logger LOGGER;
    static final String VERSION;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Class, java.lang.Class<org.minidns.MiniDnsInitialization>] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v8 */
    static {
        ?? r1 = MiniDnsInitialization.class;
        LOGGER = Logger.getLogger(r1.getName());
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(r1.getClassLoader().getResourceAsStream("org.minidns/version"), StandardCharsets.UTF_8));
                    try {
                        String line = bufferedReader2.readLine();
                        bufferedReader2.close();
                        r1 = line;
                    } catch (Exception e) {
                        e = e;
                        bufferedReader = bufferedReader2;
                        LOGGER.log(Level.SEVERE, "Could not determine MiniDNS version", (Throwable) e);
                        r1 = "unkown";
                        if (bufferedReader != null) {
                            bufferedReader.close();
                            r1 = r1;
                        }
                        VERSION = r1;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                LOGGER.log(Level.WARNING, "IOException closing stream", (Throwable) e2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (IOException e4) {
            LOGGER.log(Level.WARNING, "IOException closing stream", (Throwable) e4);
        }
        VERSION = r1;
    }
}
