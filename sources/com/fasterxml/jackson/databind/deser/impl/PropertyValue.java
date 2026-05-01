package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PropertyValue {
    public final PropertyValue next;
    public final Object value;

    public abstract void assign(Object obj) throws IOException;

    protected PropertyValue(PropertyValue propertyValue, Object obj) {
        this.next = propertyValue;
        this.value = obj;
    }

    public void setValue(Object obj) throws IOException {
        throw new UnsupportedOperationException("Should not be called by this type " + getClass().getName());
    }

    static final class Regular extends PropertyValue {
        final SettableBeanProperty _property;

        public Regular(PropertyValue propertyValue, Object obj, SettableBeanProperty settableBeanProperty) {
            super(propertyValue, obj);
            this._property = settableBeanProperty;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException {
            this._property.set(obj, this.value);
        }
    }

    static final class Any extends PropertyValue {
        final SettableAnyProperty _property;
        final String _propertyName;

        public Any(PropertyValue propertyValue, Object obj, SettableAnyProperty settableAnyProperty, String str) {
            super(propertyValue, obj);
            this._property = settableAnyProperty;
            this._propertyName = str;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException {
            this._property.set(obj, this._propertyName, this.value);
        }
    }

    static final class Map extends PropertyValue {
        final Object _key;

        public Map(PropertyValue propertyValue, Object obj, Object obj2) {
            super(propertyValue, obj);
            this._key = obj2;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException {
            ((java.util.Map) obj).put(this._key, this.value);
        }
    }

    static final class AnyParameter extends PropertyValue {
        final SettableAnyProperty _property;
        final String _propertyName;

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void assign(Object obj) throws IOException {
        }

        public AnyParameter(PropertyValue propertyValue, Object obj, SettableAnyProperty settableAnyProperty, String str) {
            super(propertyValue, obj);
            this._property = settableAnyProperty;
            this._propertyName = str;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.PropertyValue
        public void setValue(Object obj) throws IOException {
            this._property.set(obj, this._propertyName, this.value);
        }
    }
}
