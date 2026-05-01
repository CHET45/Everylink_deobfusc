package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.p008io.CharTypes;
import com.fasterxml.jackson.core.p008io.ContentReference;
import com.github.houbb.heaven.constant.PunctuationConst;
import org.slf4j.Logger;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JsonStreamContext {
    public static final int TYPE_ARRAY = 1;
    public static final int TYPE_OBJECT = 2;
    public static final int TYPE_ROOT = 0;
    protected int _index;
    protected int _nestingDepth;
    protected int _type;

    public abstract String getCurrentName();

    public Object getCurrentValue() {
        return null;
    }

    public abstract JsonStreamContext getParent();

    public void setCurrentValue(Object obj) {
    }

    protected JsonStreamContext() {
    }

    protected JsonStreamContext(JsonStreamContext jsonStreamContext) {
        this._type = jsonStreamContext._type;
        this._index = jsonStreamContext._index;
    }

    protected JsonStreamContext(int i, int i2) {
        this._type = i;
        this._index = i2;
    }

    public final boolean inArray() {
        return this._type == 1;
    }

    public final boolean inRoot() {
        return this._type == 0;
    }

    public final boolean inObject() {
        return this._type == 2;
    }

    public final int getNestingDepth() {
        return this._nestingDepth;
    }

    @Deprecated
    public final String getTypeDesc() {
        int i = this._type;
        if (i == 0) {
            return Logger.ROOT_LOGGER_NAME;
        }
        if (i == 1) {
            return "ARRAY";
        }
        if (i == 2) {
            return "OBJECT";
        }
        return PunctuationConst.QUESTION_MARK;
    }

    public String typeDesc() {
        int i = this._type;
        if (i == 0) {
            return "root";
        }
        if (i == 1) {
            return "Array";
        }
        if (i == 2) {
            return "Object";
        }
        return PunctuationConst.QUESTION_MARK;
    }

    public final int getEntryCount() {
        return this._index + 1;
    }

    public final int getCurrentIndex() {
        int i = this._index;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public boolean hasCurrentIndex() {
        return this._index >= 0;
    }

    public boolean hasPathSegment() {
        int i = this._type;
        if (i == 2) {
            return hasCurrentName();
        }
        if (i == 1) {
            return hasCurrentIndex();
        }
        return false;
    }

    public boolean hasCurrentName() {
        return getCurrentName() != null;
    }

    public JsonPointer pathAsPointer() {
        return JsonPointer.forPath(this, false);
    }

    public JsonPointer pathAsPointer(boolean z) {
        return JsonPointer.forPath(this, z);
    }

    public JsonLocation startLocation(ContentReference contentReference) {
        return JsonLocation.f451NA;
    }

    @Deprecated
    public JsonLocation getStartLocation(Object obj) {
        return JsonLocation.f451NA;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        int i = this._type;
        if (i == 0) {
            sb.append("/");
        } else if (i == 1) {
            sb.append('[');
            sb.append(getCurrentIndex());
            sb.append(']');
        } else {
            sb.append('{');
            String currentName = getCurrentName();
            if (currentName != null) {
                sb.append('\"');
                CharTypes.appendQuoted(sb, currentName);
                sb.append('\"');
            } else {
                sb.append('?');
            }
            sb.append('}');
        }
        return sb.toString();
    }
}
