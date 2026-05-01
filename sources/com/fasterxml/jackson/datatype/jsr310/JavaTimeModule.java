package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleKeyDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JavaTimeDeserializerModifier;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.DurationKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.PeriodKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.YearKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.YearMonthKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZoneIdKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZoneOffsetKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.JavaTimeSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.MonthDaySerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZoneIdSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaTimeModule extends SimpleModule {
    private static final long serialVersionUID = 1;
    private JacksonFeatureSet<JavaTimeFeature> _features;

    public JavaTimeModule() {
        super(PackageVersion.VERSION);
        this._features = JacksonFeatureSet.fromDefaults(JavaTimeFeature.values());
    }

    public JavaTimeModule enable(JavaTimeFeature javaTimeFeature) {
        this._features = this._features.with(javaTimeFeature);
        return this;
    }

    public JavaTimeModule disable(JavaTimeFeature javaTimeFeature) {
        this._features = this._features.without(javaTimeFeature);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.module.SimpleModule, com.fasterxml.jackson.databind.Module
    public void setupModule(Module.SetupContext setupContext) {
        super.setupModule(setupContext);
        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        simpleDeserializers.addDeserializer(Instant.class, InstantDeserializer.INSTANT.withFeatures(this._features));
        simpleDeserializers.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME.withFeatures(this._features));
        simpleDeserializers.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME.withFeatures(this._features));
        simpleDeserializers.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        simpleDeserializers.addDeserializer(Year.class, YearDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        simpleDeserializers.addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        simpleDeserializers.addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
        setupContext.addDeserializers(simpleDeserializers);
        boolean zIsEnabled = this._features.isEnabled(JavaTimeFeature.ONE_BASED_MONTHS);
        setupContext.addBeanDeserializerModifier(new JavaTimeDeserializerModifier(zIsEnabled));
        setupContext.addBeanSerializerModifier(new JavaTimeSerializerModifier(zIsEnabled));
        if (this._deserializers != null) {
            setupContext.addDeserializers(this._deserializers);
        }
        SimpleSerializers simpleSerializers = new SimpleSerializers();
        simpleSerializers.addSerializer(Duration.class, DurationSerializer.INSTANCE);
        simpleSerializers.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        simpleSerializers.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        simpleSerializers.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        simpleSerializers.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        simpleSerializers.addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        simpleSerializers.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        simpleSerializers.addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        simpleSerializers.addSerializer(Period.class, new ToStringSerializer(Period.class));
        simpleSerializers.addSerializer(Year.class, YearSerializer.INSTANCE);
        simpleSerializers.addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);
        simpleSerializers.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        simpleSerializers.addSerializer(ZoneId.class, new ZoneIdSerializer());
        simpleSerializers.addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));
        setupContext.addSerializers(simpleSerializers);
        if (this._serializers != null) {
            setupContext.addSerializers(this._serializers);
        }
        SimpleSerializers simpleSerializers2 = new SimpleSerializers();
        simpleSerializers2.addSerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);
        setupContext.addKeySerializers(simpleSerializers2);
        if (this._keySerializers != null) {
            setupContext.addKeySerializers(this._keySerializers);
        }
        SimpleKeyDeserializers simpleKeyDeserializers = new SimpleKeyDeserializers();
        simpleKeyDeserializers.addDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(YearMonth.class, YearMonthKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        simpleKeyDeserializers.addDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
        setupContext.addKeyDeserializers(simpleKeyDeserializers);
        if (this._keyDeserializers != null) {
            setupContext.addKeyDeserializers(this._keyDeserializers);
        }
        setupContext.addValueInstantiators(new ValueInstantiators.Base() { // from class: com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.1
            @Override // com.fasterxml.jackson.databind.deser.ValueInstantiators.Base, com.fasterxml.jackson.databind.deser.ValueInstantiators
            public ValueInstantiator findValueInstantiator(DeserializationConfig deserializationConfig, BeanDescription beanDescription, ValueInstantiator valueInstantiator) {
                AnnotatedClass annotatedClassResolve;
                AnnotatedMethod annotatedMethod_findFactory;
                Class<?> rawClass = beanDescription.getType().getRawClass();
                if (ZoneId.class.isAssignableFrom(rawClass) && (valueInstantiator instanceof StdValueInstantiator)) {
                    StdValueInstantiator stdValueInstantiator = (StdValueInstantiator) valueInstantiator;
                    if (rawClass == ZoneId.class) {
                        annotatedClassResolve = beanDescription.getClassInfo();
                    } else {
                        annotatedClassResolve = AnnotatedClassResolver.resolve(deserializationConfig, deserializationConfig.constructType(ZoneId.class), deserializationConfig);
                    }
                    if (!stdValueInstantiator.canCreateFromString() && (annotatedMethod_findFactory = JavaTimeModule.this._findFactory(annotatedClassResolve, "of", String.class)) != null) {
                        stdValueInstantiator.configureFromStringCreator(annotatedMethod_findFactory);
                    }
                }
                return valueInstantiator;
            }
        });
    }

    protected AnnotatedMethod _findFactory(AnnotatedClass annotatedClass, String str, Class<?>... clsArr) {
        int length = clsArr.length;
        for (AnnotatedMethod annotatedMethod : annotatedClass.getFactoryMethods()) {
            if (str.equals(annotatedMethod.getName()) && annotatedMethod.getParameterCount() == length) {
                for (int i = 0; i < length; i++) {
                    annotatedMethod.getParameter(i).getRawType().isAssignableFrom(clsArr[i]);
                }
                return annotatedMethod;
            }
        }
        return null;
    }
}
