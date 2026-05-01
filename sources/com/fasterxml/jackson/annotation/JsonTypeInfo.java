package com.fasterxml.jackson.annotation;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes3.dex */
@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonTypeInfo {

    /* JADX INFO: renamed from: com.fasterxml.jackson.annotation.JsonTypeInfo$As */
    public enum EnumC1421As {
        PROPERTY,
        WRAPPER_OBJECT,
        WRAPPER_ARRAY,
        EXTERNAL_PROPERTY,
        EXISTING_PROPERTY
    }

    @Deprecated
    public static abstract class None {
    }

    Class<?> defaultImpl() default JsonTypeInfo.class;

    EnumC1421As include() default EnumC1421As.PROPERTY;

    String property() default "";

    OptBoolean requireTypeIdForSubtypes() default OptBoolean.DEFAULT;

    EnumC1422Id use();

    boolean visible() default false;

    /* JADX INFO: renamed from: com.fasterxml.jackson.annotation.JsonTypeInfo$Id */
    public enum EnumC1422Id {
        NONE(null),
        CLASS("@class"),
        MINIMAL_CLASS("@c"),
        NAME("@type"),
        SIMPLE_NAME("@type"),
        DEDUCTION(null),
        CUSTOM(null);

        private final String _defaultPropertyName;

        EnumC1422Id(String str) {
            this._defaultPropertyName = str;
        }

        public String getDefaultPropertyName() {
            return this._defaultPropertyName;
        }
    }

    public static class Value implements JacksonAnnotationValue<JsonTypeInfo>, Serializable {
        protected static final Value EMPTY = new Value(EnumC1422Id.NONE, EnumC1421As.PROPERTY, null, null, false, null);
        private static final long serialVersionUID = 1;
        protected final Class<?> _defaultImpl;
        protected final EnumC1422Id _idType;
        protected final boolean _idVisible;
        protected final EnumC1421As _inclusionType;
        protected final String _propertyName;
        protected final Boolean _requireTypeIdForSubtypes;

        protected Value(EnumC1422Id enumC1422Id, EnumC1421As enumC1421As, String str, Class<?> cls, boolean z, Boolean bool) {
            this._defaultImpl = cls;
            this._idType = enumC1422Id;
            this._inclusionType = enumC1421As;
            this._propertyName = str;
            this._idVisible = z;
            this._requireTypeIdForSubtypes = bool;
        }

        public static Value construct(EnumC1422Id enumC1422Id, EnumC1421As enumC1421As, String str, Class<?> cls, boolean z, Boolean bool) {
            if (str == null || str.isEmpty()) {
                if (enumC1422Id != null) {
                    str = enumC1422Id.getDefaultPropertyName();
                } else {
                    str = "";
                }
            }
            String str2 = str;
            if (cls == null || cls.isAnnotation()) {
                cls = null;
            }
            return new Value(enumC1422Id, enumC1421As, str2, cls, z, bool);
        }

        public static Value from(JsonTypeInfo jsonTypeInfo) {
            if (jsonTypeInfo == null) {
                return null;
            }
            return construct(jsonTypeInfo.use(), jsonTypeInfo.include(), jsonTypeInfo.property(), jsonTypeInfo.defaultImpl(), jsonTypeInfo.visible(), jsonTypeInfo.requireTypeIdForSubtypes().asBoolean());
        }

        public Value withDefaultImpl(Class<?> cls) {
            return cls == this._defaultImpl ? this : new Value(this._idType, this._inclusionType, this._propertyName, cls, this._idVisible, this._requireTypeIdForSubtypes);
        }

        public Value withIdType(EnumC1422Id enumC1422Id) {
            return enumC1422Id == this._idType ? this : new Value(enumC1422Id, this._inclusionType, this._propertyName, this._defaultImpl, this._idVisible, this._requireTypeIdForSubtypes);
        }

        public Value withInclusionType(EnumC1421As enumC1421As) {
            return enumC1421As == this._inclusionType ? this : new Value(this._idType, enumC1421As, this._propertyName, this._defaultImpl, this._idVisible, this._requireTypeIdForSubtypes);
        }

        public Value withPropertyName(String str) {
            return str == this._propertyName ? this : new Value(this._idType, this._inclusionType, str, this._defaultImpl, this._idVisible, this._requireTypeIdForSubtypes);
        }

        public Value withIdVisible(boolean z) {
            return z == this._idVisible ? this : new Value(this._idType, this._inclusionType, this._propertyName, this._defaultImpl, z, this._requireTypeIdForSubtypes);
        }

        public Value withRequireTypeIdForSubtypes(Boolean bool) {
            return this._requireTypeIdForSubtypes == bool ? this : new Value(this._idType, this._inclusionType, this._propertyName, this._defaultImpl, this._idVisible, bool);
        }

        @Override // com.fasterxml.jackson.annotation.JacksonAnnotationValue
        public Class<JsonTypeInfo> valueFor() {
            return JsonTypeInfo.class;
        }

        public Class<?> getDefaultImpl() {
            return this._defaultImpl;
        }

        public EnumC1422Id getIdType() {
            return this._idType;
        }

        public EnumC1421As getInclusionType() {
            return this._inclusionType;
        }

        public String getPropertyName() {
            return this._propertyName;
        }

        public boolean getIdVisible() {
            return this._idVisible;
        }

        public Boolean getRequireTypeIdForSubtypes() {
            return this._requireTypeIdForSubtypes;
        }

        public static boolean isEnabled(Value value) {
            EnumC1422Id enumC1422Id;
            return (value == null || (enumC1422Id = value._idType) == null || enumC1422Id == EnumC1422Id.NONE) ? false : true;
        }

        public String toString() {
            EnumC1422Id enumC1422Id = this._idType;
            EnumC1421As enumC1421As = this._inclusionType;
            String str = this._propertyName;
            Class<?> cls = this._defaultImpl;
            return String.format("JsonTypeInfo.Value(idType=%s,includeAs=%s,propertyName=%s,defaultImpl=%s,idVisible=%s,requireTypeIdForSubtypes=%s)", enumC1422Id, enumC1421As, str, cls == null ? "NULL" : cls.getName(), Boolean.valueOf(this._idVisible), this._requireTypeIdForSubtypes);
        }

        public int hashCode() {
            EnumC1422Id enumC1422Id = this._idType;
            int iHashCode = ((enumC1422Id != null ? enumC1422Id.hashCode() : 0) + 31) * 31;
            EnumC1421As enumC1421As = this._inclusionType;
            int iHashCode2 = (iHashCode + (enumC1421As != null ? enumC1421As.hashCode() : 0)) * 31;
            String str = this._propertyName;
            int iHashCode3 = (iHashCode2 + (str != null ? str.hashCode() : 0)) * 31;
            Class<?> cls = this._defaultImpl;
            return ((((iHashCode3 + (cls != null ? cls.hashCode() : 0)) * 31) + (this._requireTypeIdForSubtypes.booleanValue() ? 11 : -17)) * 31) + (this._idVisible ? 11 : -17);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            return obj.getClass() == getClass() && _equals(this, (Value) obj);
        }

        private static boolean _equals(Value value, Value value2) {
            return value._idType == value2._idType && value._inclusionType == value2._inclusionType && value._defaultImpl == value2._defaultImpl && value._idVisible == value2._idVisible && _equal(value._propertyName, value2._propertyName) && _equal(value._requireTypeIdForSubtypes, value2._requireTypeIdForSubtypes);
        }

        private static <T> boolean _equal(T t, T t2) {
            if (t == null) {
                return t2 == null;
            }
            if (t2 == null) {
                return false;
            }
            return t.equals(t2);
        }
    }
}
