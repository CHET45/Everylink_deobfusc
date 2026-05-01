package com.github.houbb.heaven.util.p010io.big;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p010io.FileUtil;
import java.nio.file.LinkOption;

/* JADX INFO: loaded from: classes3.dex */
public class BigFileStringHandlerSegment implements IBigFileStringHandler {
    private final int limitSize;

    public BigFileStringHandlerSegment(int i) {
        this.limitSize = i;
    }

    @Override // com.github.houbb.heaven.util.p010io.big.IBigFileStringHandler
    public void handle(BigFileStringHandlerContext bigFileStringHandlerContext) {
        int index = bigFileStringHandlerContext.getIndex();
        String line = bigFileStringHandlerContext.getLine();
        String str = bigFileStringHandlerContext.getFilePath() + PunctuationConst.UNDERLINE + StringUtil.leftPadding((index / this.limitSize) + "", 3, '0');
        if (FileUtil.notExists(str, new LinkOption[0])) {
            FileUtil.createFile(str);
        }
        FileUtil.append(str, line);
    }
}
