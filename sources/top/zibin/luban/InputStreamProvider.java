package top.zibin.luban;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes5.dex */
public interface InputStreamProvider {
    void close();

    int getIndex();

    String getPath();

    InputStream open() throws IOException;
}
