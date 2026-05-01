package reactor.core.publisher;

import com.github.houbb.heaven.constant.PunctuationConst;
import reactor.util.Logger;

/* JADX INFO: loaded from: classes5.dex */
class StateLogger {
    final Logger logger;

    StateLogger(Logger logger) {
        this.logger = logger;
    }

    void log(String str, String str2, long j, long j2) {
        log(str, str2, j, j2, false);
    }

    void log(String str, String str2, long j, long j2, boolean z) {
        if (z) {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", str, str2, str2, Long.valueOf(Thread.currentThread().getId()), formatState(j, 64), formatState(j2, 64)), new RuntimeException());
        } else {
            this.logger.trace(String.format("[%s][%s][%s][\n\t%s\n\t%s]", str, str2, Long.valueOf(Thread.currentThread().getId()), formatState(j, 64), formatState(j2, 64)));
        }
    }

    void log(String str, String str2, int i, int i2) {
        log(str, str2, i, i2, false);
    }

    void log(String str, String str2, int i, int i2, boolean z) {
        if (z) {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", str, str2, str2, Long.valueOf(Thread.currentThread().getId()), formatState(i, 32), formatState(i2, 32)), new RuntimeException());
        } else {
            this.logger.trace(String.format("[%s][%s][%s][%s-%s]", str, str2, Long.valueOf(Thread.currentThread().getId()), formatState(i, 32), formatState(i2, 32)));
        }
    }

    static String formatState(long j, int i) {
        String binaryString = Long.toBinaryString(j);
        StringBuilder sb = new StringBuilder();
        int length = i - binaryString.length();
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 != 0 && i2 % 4 == 0) {
                sb.append(PunctuationConst.UNDERLINE);
            }
            if (i2 < length) {
                sb.append("0");
            } else {
                sb.append(binaryString.charAt(i2 - length));
            }
        }
        sb.insert(0, "0b");
        return sb.toString();
    }
}
