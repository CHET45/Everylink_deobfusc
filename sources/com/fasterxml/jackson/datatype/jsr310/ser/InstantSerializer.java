package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
public class InstantSerializer extends InstantSerializerBase<Instant> {
    public static final InstantSerializer INSTANCE = new InstantSerializer();
    private static final long serialVersionUID = 1;

    protected InstantSerializer() {
        super(Instant.class, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((Instant) obj).toEpochMilli();
            }
        }, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((Instant) obj).getEpochSecond();
            }
        }, new ToIntFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Instant) obj).getNano();
            }
        }, (DateTimeFormatter) null);
    }

    @Deprecated
    protected InstantSerializer(InstantSerializer instantSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(instantSerializer, bool, instantSerializer._useNanoseconds, dateTimeFormatter);
    }

    protected InstantSerializer(InstantSerializer instantSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(instantSerializer, bool, instantSerializer._useNanoseconds, dateTimeFormatter, shape);
    }

    protected InstantSerializer(InstantSerializer instantSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(instantSerializer, bool, bool2, dateTimeFormatter);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase, com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<Instant> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new InstantSerializer(this, bool, dateTimeFormatter, shape);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new InstantSerializer(this, this._useTimestamp, bool2, this._formatter);
    }
}
