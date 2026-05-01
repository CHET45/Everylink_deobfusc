package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.type.LogicalType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CoercionConfigs implements Serializable {
    private static final int TARGET_TYPE_COUNT = LogicalType.values().length;
    private static final long serialVersionUID = 1;
    protected CoercionAction _defaultAction;
    protected final MutableCoercionConfig _defaultCoercions;
    protected Map<Class<?>, MutableCoercionConfig> _perClassCoercions;
    protected MutableCoercionConfig[] _perTypeCoercions;

    public CoercionConfigs() {
        this(CoercionAction.TryConvert, new MutableCoercionConfig(), null, null);
    }

    protected CoercionConfigs(CoercionAction coercionAction, MutableCoercionConfig mutableCoercionConfig, MutableCoercionConfig[] mutableCoercionConfigArr, Map<Class<?>, MutableCoercionConfig> map) {
        this._defaultCoercions = mutableCoercionConfig;
        this._defaultAction = coercionAction;
        this._perTypeCoercions = mutableCoercionConfigArr;
        this._perClassCoercions = map;
    }

    public CoercionConfigs copy() {
        MutableCoercionConfig[] mutableCoercionConfigArr;
        MutableCoercionConfig[] mutableCoercionConfigArr2 = this._perTypeCoercions;
        HashMap map = null;
        if (mutableCoercionConfigArr2 == null) {
            mutableCoercionConfigArr = null;
        } else {
            int length = mutableCoercionConfigArr2.length;
            mutableCoercionConfigArr = new MutableCoercionConfig[length];
            for (int i = 0; i < length; i++) {
                mutableCoercionConfigArr[i] = _copy(this._perTypeCoercions[i]);
            }
        }
        if (this._perClassCoercions != null) {
            map = new HashMap();
            for (Map.Entry<Class<?>, MutableCoercionConfig> entry : this._perClassCoercions.entrySet()) {
                map.put(entry.getKey(), entry.getValue().copy());
            }
        }
        return new CoercionConfigs(this._defaultAction, this._defaultCoercions.copy(), mutableCoercionConfigArr, map);
    }

    private static MutableCoercionConfig _copy(MutableCoercionConfig mutableCoercionConfig) {
        if (mutableCoercionConfig == null) {
            return null;
        }
        return mutableCoercionConfig.copy();
    }

    public MutableCoercionConfig defaultCoercions() {
        return this._defaultCoercions;
    }

    public MutableCoercionConfig findOrCreateCoercion(LogicalType logicalType) {
        if (this._perTypeCoercions == null) {
            this._perTypeCoercions = new MutableCoercionConfig[TARGET_TYPE_COUNT];
        }
        MutableCoercionConfig mutableCoercionConfig = this._perTypeCoercions[logicalType.ordinal()];
        if (mutableCoercionConfig != null) {
            return mutableCoercionConfig;
        }
        MutableCoercionConfig[] mutableCoercionConfigArr = this._perTypeCoercions;
        int iOrdinal = logicalType.ordinal();
        MutableCoercionConfig mutableCoercionConfig2 = new MutableCoercionConfig();
        mutableCoercionConfigArr[iOrdinal] = mutableCoercionConfig2;
        return mutableCoercionConfig2;
    }

    public MutableCoercionConfig findOrCreateCoercion(Class<?> cls) {
        if (this._perClassCoercions == null) {
            this._perClassCoercions = new HashMap();
        }
        MutableCoercionConfig mutableCoercionConfig = this._perClassCoercions.get(cls);
        if (mutableCoercionConfig != null) {
            return mutableCoercionConfig;
        }
        MutableCoercionConfig mutableCoercionConfig2 = new MutableCoercionConfig();
        this._perClassCoercions.put(cls, mutableCoercionConfig2);
        return mutableCoercionConfig2;
    }

    public CoercionAction findCoercion(DeserializationConfig deserializationConfig, LogicalType logicalType, Class<?> cls, CoercionInputShape coercionInputShape) {
        MutableCoercionConfig mutableCoercionConfig;
        CoercionAction coercionActionFindAction;
        MutableCoercionConfig mutableCoercionConfig2;
        CoercionAction coercionActionFindAction2;
        Map<Class<?>, MutableCoercionConfig> map = this._perClassCoercions;
        if (map != null && cls != null && (mutableCoercionConfig2 = map.get(cls)) != null && (coercionActionFindAction2 = mutableCoercionConfig2.findAction(coercionInputShape)) != null) {
            return coercionActionFindAction2;
        }
        MutableCoercionConfig[] mutableCoercionConfigArr = this._perTypeCoercions;
        if (mutableCoercionConfigArr != null && logicalType != null && (mutableCoercionConfig = mutableCoercionConfigArr[logicalType.ordinal()]) != null && (coercionActionFindAction = mutableCoercionConfig.findAction(coercionInputShape)) != null) {
            return coercionActionFindAction;
        }
        CoercionAction coercionActionFindAction3 = this._defaultCoercions.findAction(coercionInputShape);
        if (coercionActionFindAction3 != null) {
            return coercionActionFindAction3;
        }
        int i = C14291.$SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionInputShape[coercionInputShape.ordinal()];
        if (i == 1) {
            return deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT) ? CoercionAction.AsNull : CoercionAction.Fail;
        }
        if (i == 2) {
            if (logicalType == LogicalType.Integer) {
                return deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT) ? CoercionAction.TryConvert : CoercionAction.Fail;
            }
        } else if (i == 3 && logicalType == LogicalType.Enum && deserializationConfig.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
            return CoercionAction.Fail;
        }
        boolean z_isScalarType = _isScalarType(logicalType);
        if (z_isScalarType && !deserializationConfig.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS) && (logicalType != LogicalType.Float || coercionInputShape != CoercionInputShape.Integer)) {
            return CoercionAction.Fail;
        }
        if (coercionInputShape == CoercionInputShape.EmptyString) {
            if (logicalType == LogicalType.OtherScalar) {
                return CoercionAction.TryConvert;
            }
            if (z_isScalarType || deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
                return CoercionAction.AsNull;
            }
            return CoercionAction.Fail;
        }
        return this._defaultAction;
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.cfg.CoercionConfigs$1 */
    static /* synthetic */ class C14291 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionInputShape;

        static {
            int[] iArr = new int[CoercionInputShape.values().length];
            $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionInputShape = iArr;
            try {
                iArr[CoercionInputShape.EmptyArray.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionInputShape[CoercionInputShape.Float.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionInputShape[CoercionInputShape.Integer.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public CoercionAction findCoercionFromBlankString(DeserializationConfig deserializationConfig, LogicalType logicalType, Class<?> cls, CoercionAction coercionAction) {
        Boolean acceptBlankAsEmpty;
        CoercionAction coercionActionFindAction;
        MutableCoercionConfig mutableCoercionConfig;
        MutableCoercionConfig mutableCoercionConfig2;
        Map<Class<?>, MutableCoercionConfig> map = this._perClassCoercions;
        if (map == null || cls == null || (mutableCoercionConfig2 = map.get(cls)) == null) {
            acceptBlankAsEmpty = null;
            coercionActionFindAction = null;
        } else {
            acceptBlankAsEmpty = mutableCoercionConfig2.getAcceptBlankAsEmpty();
            coercionActionFindAction = mutableCoercionConfig2.findAction(CoercionInputShape.EmptyString);
        }
        MutableCoercionConfig[] mutableCoercionConfigArr = this._perTypeCoercions;
        if (mutableCoercionConfigArr != null && logicalType != null && (mutableCoercionConfig = mutableCoercionConfigArr[logicalType.ordinal()]) != null) {
            if (acceptBlankAsEmpty == null) {
                acceptBlankAsEmpty = mutableCoercionConfig.getAcceptBlankAsEmpty();
            }
            if (coercionActionFindAction == null) {
                coercionActionFindAction = mutableCoercionConfig.findAction(CoercionInputShape.EmptyString);
            }
        }
        if (acceptBlankAsEmpty == null) {
            acceptBlankAsEmpty = this._defaultCoercions.getAcceptBlankAsEmpty();
        }
        if (coercionActionFindAction == null) {
            coercionActionFindAction = this._defaultCoercions.findAction(CoercionInputShape.EmptyString);
        }
        if (Boolean.FALSE.equals(acceptBlankAsEmpty)) {
            return coercionAction;
        }
        if (coercionActionFindAction != null) {
            return coercionActionFindAction;
        }
        if (_isScalarType(logicalType)) {
            return CoercionAction.AsNull;
        }
        return deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT) ? CoercionAction.AsNull : coercionAction;
    }

    protected boolean _isScalarType(LogicalType logicalType) {
        return logicalType == LogicalType.Float || logicalType == LogicalType.Integer || logicalType == LogicalType.Boolean || logicalType == LogicalType.DateTime;
    }
}
