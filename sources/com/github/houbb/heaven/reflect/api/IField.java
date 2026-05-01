package com.github.houbb.heaven.reflect.api;

import com.github.houbb.heaven.util.util.Optional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface IField extends IMember, Comparable<IField> {
    Annotation annotation(Class cls);

    Optional<Annotation> annotationOpt(Class cls);

    List<Annotation> annotations();

    Field field();

    IField value(Object obj);

    Object value();
}
