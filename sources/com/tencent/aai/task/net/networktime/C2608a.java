package com.tencent.aai.task.net.networktime;

import com.tencent.aai.log.AAILogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: renamed from: com.tencent.aai.task.net.networktime.a */
/* JADX INFO: loaded from: classes4.dex */
public class C2608a {

    /* JADX INFO: renamed from: a */
    public final String f1005a = "a";

    /* JADX INFO: renamed from: b */
    public InterfaceC2609b f1006b;

    /* JADX INFO: renamed from: com.tencent.aai.task.net.networktime.a$a */
    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            C2608a.this.m983a();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m983a() {
        long j;
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://asr.cloud.tencent.com/server_time").openConnection();
                httpURLConnection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        sb.append(line);
                    }
                }
                String string = sb.toString();
                httpURLConnection.disconnect();
                if (string.contains(".")) {
                    string = string.replace(".", "");
                }
                try {
                    j = Long.parseLong(string) / 1000;
                } catch (NumberFormatException unused) {
                    j = 0;
                }
                AAILogger.debug(this.f1005a, "run: timeTamp=" + j + ",nowTime=" + System.currentTimeMillis());
                InterfaceC2609b interfaceC2609b = this.f1006b;
                if (interfaceC2609b != null) {
                    interfaceC2609b.mo876a(j);
                }
            } catch (IOException e) {
                InterfaceC2609b interfaceC2609b2 = this.f1006b;
                if (interfaceC2609b2 != null) {
                    interfaceC2609b2.mo876a(0L);
                }
                AAILogger.warn(this.f1005a, "Failed to synchronize server time" + e.toString());
                e.printStackTrace();
            }
        } catch (MalformedURLException e2) {
            InterfaceC2609b interfaceC2609b3 = this.f1006b;
            if (interfaceC2609b3 != null) {
                interfaceC2609b3.mo876a(0L);
            }
            AAILogger.warn(this.f1005a, "Failed to synchronize server time" + e2.toString());
            e2.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m984a(InterfaceC2609b interfaceC2609b) {
        this.f1006b = interfaceC2609b;
    }

    /* JADX INFO: renamed from: b */
    public void m985b() {
        new Thread(new a()).start();
    }
}
