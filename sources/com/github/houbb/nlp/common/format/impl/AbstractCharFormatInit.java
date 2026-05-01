package com.github.houbb.nlp.common.format.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.support.pipeline.impl.DefaultPipeline;
import com.github.houbb.nlp.common.format.ICharFormat;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public abstract class AbstractCharFormatInit implements ICharFormat {
    protected abstract void init(Pipeline<ICharFormat> pipeline);

    @Override // com.github.houbb.nlp.common.format.ICharFormat
    public char format(char c) {
        DefaultPipeline defaultPipeline = new DefaultPipeline();
        init(defaultPipeline);
        Iterator<ICharFormat> it = defaultPipeline.list().iterator();
        while (it.hasNext()) {
            c = it.next().format(c);
        }
        return c;
    }
}
