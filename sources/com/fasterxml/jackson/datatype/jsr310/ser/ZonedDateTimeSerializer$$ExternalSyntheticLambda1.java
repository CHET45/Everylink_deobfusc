package com.fasterxml.jackson.datatype.jsr310.ser;

import java.time.ZonedDateTime;
import java.util.function.ToLongFunction;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class ZonedDateTimeSerializer$$ExternalSyntheticLambda1 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((ZonedDateTime) obj).toEpochSecond();
    }
}
