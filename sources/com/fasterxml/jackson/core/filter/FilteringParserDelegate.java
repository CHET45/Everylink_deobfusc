package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes3.dex */
public class FilteringParserDelegate extends JsonParserDelegate {
    protected boolean _allowMultipleMatches;
    protected JsonToken _currToken;
    protected TokenFilterContext _exposedContext;
    protected TokenFilterContext _headContext;
    protected TokenFilter.Inclusion _inclusion;
    protected TokenFilter _itemFilter;
    protected JsonToken _lastClearedToken;
    protected int _matchCount;
    protected TokenFilter rootFilter;

    @Deprecated
    public FilteringParserDelegate(JsonParser jsonParser, TokenFilter tokenFilter, boolean z, boolean z2) {
        this(jsonParser, tokenFilter, z ? TokenFilter.Inclusion.INCLUDE_ALL_AND_PATH : TokenFilter.Inclusion.ONLY_INCLUDE_ALL, z2);
    }

    public FilteringParserDelegate(JsonParser jsonParser, TokenFilter tokenFilter, TokenFilter.Inclusion inclusion, boolean z) {
        super(jsonParser);
        this.rootFilter = tokenFilter;
        this._itemFilter = tokenFilter;
        this._headContext = TokenFilterContext.createRootContext(tokenFilter);
        this._inclusion = inclusion;
        this._allowMultipleMatches = z;
    }

    public TokenFilter getFilter() {
        return this.rootFilter;
    }

    public int getMatchCount() {
        return this._matchCount;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonToken currentToken() {
        return this._currToken;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    @Deprecated
    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public final int currentTokenId() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == null) {
            return 0;
        }
        return jsonToken.m508id();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    @Deprecated
    public final int getCurrentTokenId() {
        return currentTokenId();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean hasTokenId(int i) {
        JsonToken jsonToken = this._currToken;
        return jsonToken == null ? i == 0 : jsonToken.m508id() == i;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public final boolean hasToken(JsonToken jsonToken) {
        return this._currToken == jsonToken;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean isExpectedStartArrayToken() {
        return this._currToken == JsonToken.START_ARRAY;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean isExpectedStartObjectToken() {
        return this._currToken == JsonToken.START_OBJECT;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonLocation currentLocation() {
        return this.delegate.currentLocation();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    @Deprecated
    public JsonLocation getCurrentLocation() {
        return this.delegate.getCurrentLocation();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonLocation currentTokenLocation() {
        return this.delegate.currentTokenLocation();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    @Deprecated
    public JsonLocation getTokenLocation() {
        return this.delegate.getTokenLocation();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonStreamContext getParsingContext() {
        return _filterContext();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    @Deprecated
    public String getCurrentName() throws IOException {
        JsonStreamContext jsonStreamContext_filterContext = _filterContext();
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            JsonStreamContext parent = jsonStreamContext_filterContext.getParent();
            if (parent == null) {
                return null;
            }
            return parent.getCurrentName();
        }
        return jsonStreamContext_filterContext.getCurrentName();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public String currentName() throws IOException {
        JsonStreamContext jsonStreamContext_filterContext = _filterContext();
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            JsonStreamContext parent = jsonStreamContext_filterContext.getParent();
            if (parent == null) {
                return null;
            }
            return parent.getCurrentName();
        }
        return jsonStreamContext_filterContext.getCurrentName();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public void clearCurrentToken() {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != null) {
            this._lastClearedToken = jsonToken;
            this._currToken = null;
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public void overrideCurrentName(String str) {
        throw new UnsupportedOperationException("Can not currently override name during filtering read");
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x01a7  */
    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fasterxml.jackson.core.JsonToken nextToken() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.filter.FilteringParserDelegate.nextToken():com.fasterxml.jackson.core.JsonToken");
    }

    protected final JsonToken _nextToken2() throws IOException {
        TokenFilter tokenFilterCheckValue;
        JsonToken jsonToken_nextTokenWithBuffering;
        JsonToken jsonToken_nextTokenWithBuffering2;
        JsonToken jsonToken_nextTokenWithBuffering3;
        while (true) {
            JsonToken jsonTokenNextToken = this.delegate.nextToken();
            if (jsonTokenNextToken == null) {
                this._currToken = jsonTokenNextToken;
                return jsonTokenNextToken;
            }
            int iM508id = jsonTokenNextToken.m508id();
            if (iM508id == 1) {
                TokenFilter tokenFilter = this._itemFilter;
                if (tokenFilter == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(tokenFilter, true);
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
                if (tokenFilter == null) {
                    this.delegate.skipChildren();
                } else {
                    TokenFilter tokenFilterCheckValue2 = this._headContext.checkValue(tokenFilter);
                    if (tokenFilterCheckValue2 == null) {
                        this.delegate.skipChildren();
                    } else {
                        if (tokenFilterCheckValue2 != TokenFilter.INCLUDE_ALL) {
                            tokenFilterCheckValue2 = tokenFilterCheckValue2.filterStartObject();
                        }
                        this._itemFilter = tokenFilterCheckValue2;
                        if (tokenFilterCheckValue2 == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildObjectContext(tokenFilterCheckValue2, true);
                            this._currToken = jsonTokenNextToken;
                            return jsonTokenNextToken;
                        }
                        if (tokenFilterCheckValue2 != null && this._inclusion == TokenFilter.Inclusion.INCLUDE_NON_NULL) {
                            this._headContext = this._headContext.createChildObjectContext(tokenFilterCheckValue2, true);
                            this._currToken = jsonTokenNextToken;
                            return jsonTokenNextToken;
                        }
                        this._headContext = this._headContext.createChildObjectContext(tokenFilterCheckValue2, false);
                        if (this._inclusion == TokenFilter.Inclusion.INCLUDE_ALL_AND_PATH && (jsonToken_nextTokenWithBuffering3 = _nextTokenWithBuffering(this._headContext)) != null) {
                            this._currToken = jsonToken_nextTokenWithBuffering3;
                            return jsonToken_nextTokenWithBuffering3;
                        }
                    }
                }
            } else if (iM508id == 2) {
                boolean zIsStartHandled = this._headContext.isStartHandled();
                TokenFilter filter = this._headContext.getFilter();
                if (filter != null && filter != TokenFilter.INCLUDE_ALL) {
                    boolean zIncludeEmptyArray = filter.includeEmptyArray(this._headContext.hasCurrentName());
                    filter.filterFinishObject();
                    if (zIncludeEmptyArray) {
                        return _nextBuffered(this._headContext);
                    }
                }
                TokenFilterContext parent = this._headContext.getParent();
                this._headContext = parent;
                this._itemFilter = parent.getFilter();
                if (zIsStartHandled) {
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
            } else if (iM508id == 3) {
                TokenFilter tokenFilter2 = this._itemFilter;
                if (tokenFilter2 == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildArrayContext(tokenFilter2, true);
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
                if (tokenFilter2 == null) {
                    this.delegate.skipChildren();
                } else {
                    TokenFilter tokenFilterCheckValue3 = this._headContext.checkValue(tokenFilter2);
                    if (tokenFilterCheckValue3 == null) {
                        this.delegate.skipChildren();
                    } else {
                        if (tokenFilterCheckValue3 != TokenFilter.INCLUDE_ALL) {
                            tokenFilterCheckValue3 = tokenFilterCheckValue3.filterStartArray();
                        }
                        this._itemFilter = tokenFilterCheckValue3;
                        if (tokenFilterCheckValue3 == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue3, true);
                            this._currToken = jsonTokenNextToken;
                            return jsonTokenNextToken;
                        }
                        if (tokenFilterCheckValue3 != null && this._inclusion == TokenFilter.Inclusion.INCLUDE_NON_NULL) {
                            this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue3, true);
                            this._currToken = jsonTokenNextToken;
                            return jsonTokenNextToken;
                        }
                        this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue3, false);
                        if (this._inclusion == TokenFilter.Inclusion.INCLUDE_ALL_AND_PATH && (jsonToken_nextTokenWithBuffering2 = _nextTokenWithBuffering(this._headContext)) != null) {
                            this._currToken = jsonToken_nextTokenWithBuffering2;
                            return jsonToken_nextTokenWithBuffering2;
                        }
                    }
                }
            } else if (iM508id == 4) {
                boolean zIsStartHandled2 = this._headContext.isStartHandled();
                TokenFilter filter2 = this._headContext.getFilter();
                if (filter2 != null && filter2 != TokenFilter.INCLUDE_ALL) {
                    boolean zIncludeEmptyArray2 = filter2.includeEmptyArray(this._headContext.hasCurrentIndex());
                    filter2.filterFinishArray();
                    if (zIncludeEmptyArray2) {
                        return _nextBuffered(this._headContext);
                    }
                }
                TokenFilterContext parent2 = this._headContext.getParent();
                this._headContext = parent2;
                this._itemFilter = parent2.getFilter();
                if (zIsStartHandled2) {
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
            } else if (iM508id == 5) {
                String strCurrentName = this.delegate.currentName();
                TokenFilter fieldName = this._headContext.setFieldName(strCurrentName);
                if (fieldName == TokenFilter.INCLUDE_ALL) {
                    this._itemFilter = fieldName;
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
                if (fieldName == null) {
                    this.delegate.nextToken();
                    this.delegate.skipChildren();
                } else {
                    TokenFilter tokenFilterIncludeProperty = fieldName.includeProperty(strCurrentName);
                    if (tokenFilterIncludeProperty == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                    } else {
                        this._itemFilter = tokenFilterIncludeProperty;
                        if (tokenFilterIncludeProperty == TokenFilter.INCLUDE_ALL) {
                            if (_verifyAllowedMatches()) {
                                if (this._inclusion == TokenFilter.Inclusion.INCLUDE_ALL_AND_PATH) {
                                    this._currToken = jsonTokenNextToken;
                                    return jsonTokenNextToken;
                                }
                            } else {
                                this.delegate.nextToken();
                                this.delegate.skipChildren();
                            }
                        } else if (this._inclusion != TokenFilter.Inclusion.ONLY_INCLUDE_ALL && (jsonToken_nextTokenWithBuffering = _nextTokenWithBuffering(this._headContext)) != null) {
                            this._currToken = jsonToken_nextTokenWithBuffering;
                            return jsonToken_nextTokenWithBuffering;
                        }
                    }
                }
            } else {
                TokenFilter tokenFilter3 = this._itemFilter;
                if (tokenFilter3 == TokenFilter.INCLUDE_ALL) {
                    this._currToken = jsonTokenNextToken;
                    return jsonTokenNextToken;
                }
                if (tokenFilter3 != null && ((tokenFilterCheckValue = this._headContext.checkValue(tokenFilter3)) == TokenFilter.INCLUDE_ALL || (tokenFilterCheckValue != null && tokenFilterCheckValue.includeValue(this.delegate)))) {
                    if (_verifyAllowedMatches()) {
                        this._currToken = jsonTokenNextToken;
                        return jsonTokenNextToken;
                    }
                }
            }
        }
    }

    protected final JsonToken _nextTokenWithBuffering(TokenFilterContext tokenFilterContext) throws IOException {
        TokenFilter tokenFilterCheckValue;
        while (true) {
            JsonToken jsonTokenNextToken = this.delegate.nextToken();
            if (jsonTokenNextToken == null) {
                return jsonTokenNextToken;
            }
            int iM508id = jsonTokenNextToken.m508id();
            boolean z = false;
            if (iM508id != 1) {
                if (iM508id == 2) {
                    TokenFilter filter = this._headContext.getFilter();
                    if (filter != null && filter != TokenFilter.INCLUDE_ALL) {
                        boolean zIncludeEmptyObject = filter.includeEmptyObject(this._headContext.hasCurrentName());
                        filter.filterFinishObject();
                        if (zIncludeEmptyObject) {
                            TokenFilterContext tokenFilterContext2 = this._headContext;
                            tokenFilterContext2._currentName = tokenFilterContext2._parent != null ? this._headContext._parent._currentName : null;
                            this._headContext._needToHandleName = false;
                            return _nextBuffered(tokenFilterContext);
                        }
                    }
                    TokenFilterContext tokenFilterContext3 = this._headContext;
                    boolean z2 = tokenFilterContext3 == tokenFilterContext;
                    if (z2 && tokenFilterContext3.isStartHandled()) {
                        z = true;
                    }
                    TokenFilterContext parent = this._headContext.getParent();
                    this._headContext = parent;
                    this._itemFilter = parent.getFilter();
                    if (z) {
                        return jsonTokenNextToken;
                    }
                    if (z2) {
                        return null;
                    }
                } else if (iM508id == 3) {
                    TokenFilter tokenFilterCheckValue2 = this._headContext.checkValue(this._itemFilter);
                    if (tokenFilterCheckValue2 == null) {
                        this.delegate.skipChildren();
                    } else {
                        if (tokenFilterCheckValue2 != TokenFilter.INCLUDE_ALL) {
                            tokenFilterCheckValue2 = tokenFilterCheckValue2.filterStartArray();
                        }
                        this._itemFilter = tokenFilterCheckValue2;
                        if (tokenFilterCheckValue2 == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue2, true);
                            return _nextBuffered(tokenFilterContext);
                        }
                        if (tokenFilterCheckValue2 != null && this._inclusion == TokenFilter.Inclusion.INCLUDE_NON_NULL) {
                            this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue2, true);
                            return _nextBuffered(tokenFilterContext);
                        }
                        this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue2, false);
                    }
                } else if (iM508id == 4) {
                    TokenFilter filter2 = this._headContext.getFilter();
                    if (filter2 != null && filter2 != TokenFilter.INCLUDE_ALL) {
                        boolean zIncludeEmptyArray = filter2.includeEmptyArray(this._headContext.hasCurrentIndex());
                        filter2.filterFinishArray();
                        if (zIncludeEmptyArray) {
                            return _nextBuffered(tokenFilterContext);
                        }
                    }
                    TokenFilterContext tokenFilterContext4 = this._headContext;
                    boolean z3 = tokenFilterContext4 == tokenFilterContext;
                    if (z3 && tokenFilterContext4.isStartHandled()) {
                        z = true;
                    }
                    TokenFilterContext parent2 = this._headContext.getParent();
                    this._headContext = parent2;
                    this._itemFilter = parent2.getFilter();
                    if (z) {
                        return jsonTokenNextToken;
                    }
                    if (z3) {
                        return null;
                    }
                } else if (iM508id == 5) {
                    String strCurrentName = this.delegate.currentName();
                    TokenFilter fieldName = this._headContext.setFieldName(strCurrentName);
                    if (fieldName == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = fieldName;
                        return _nextBuffered(tokenFilterContext);
                    }
                    if (fieldName == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                    } else {
                        TokenFilter tokenFilterIncludeProperty = fieldName.includeProperty(strCurrentName);
                        if (tokenFilterIncludeProperty == null) {
                            this.delegate.nextToken();
                            this.delegate.skipChildren();
                        } else {
                            this._itemFilter = tokenFilterIncludeProperty;
                            if (tokenFilterIncludeProperty != TokenFilter.INCLUDE_ALL) {
                                continue;
                            } else {
                                if (_verifyAllowedMatches()) {
                                    return _nextBuffered(tokenFilterContext);
                                }
                                this._itemFilter = this._headContext.setFieldName(strCurrentName);
                            }
                        }
                    }
                } else {
                    TokenFilter tokenFilter = this._itemFilter;
                    if (tokenFilter == TokenFilter.INCLUDE_ALL) {
                        return _nextBuffered(tokenFilterContext);
                    }
                    if (tokenFilter != null && ((tokenFilterCheckValue = this._headContext.checkValue(tokenFilter)) == TokenFilter.INCLUDE_ALL || (tokenFilterCheckValue != null && tokenFilterCheckValue.includeValue(this.delegate)))) {
                        if (_verifyAllowedMatches()) {
                            return _nextBuffered(tokenFilterContext);
                        }
                    }
                }
            } else {
                TokenFilter tokenFilter2 = this._itemFilter;
                if (tokenFilter2 == TokenFilter.INCLUDE_ALL) {
                    this._headContext = this._headContext.createChildObjectContext(tokenFilter2, true);
                    return jsonTokenNextToken;
                }
                if (tokenFilter2 == null) {
                    this.delegate.skipChildren();
                } else {
                    TokenFilter tokenFilterCheckValue3 = this._headContext.checkValue(tokenFilter2);
                    if (tokenFilterCheckValue3 == null) {
                        this.delegate.skipChildren();
                    } else {
                        if (tokenFilterCheckValue3 != TokenFilter.INCLUDE_ALL) {
                            tokenFilterCheckValue3 = tokenFilterCheckValue3.filterStartObject();
                        }
                        this._itemFilter = tokenFilterCheckValue3;
                        if (tokenFilterCheckValue3 == TokenFilter.INCLUDE_ALL) {
                            this._headContext = this._headContext.createChildObjectContext(tokenFilterCheckValue3, true);
                            return _nextBuffered(tokenFilterContext);
                        }
                        if (tokenFilterCheckValue3 != null && this._inclusion == TokenFilter.Inclusion.INCLUDE_NON_NULL) {
                            this._headContext = this._headContext.createChildArrayContext(tokenFilterCheckValue3, true);
                            return _nextBuffered(tokenFilterContext);
                        }
                        this._headContext = this._headContext.createChildObjectContext(tokenFilterCheckValue3, false);
                    }
                }
            }
        }
    }

    private JsonToken _nextBuffered(TokenFilterContext tokenFilterContext) throws IOException {
        this._exposedContext = tokenFilterContext;
        JsonToken jsonTokenNextTokenToRead = tokenFilterContext.nextTokenToRead();
        if (jsonTokenNextTokenToRead != null) {
            return jsonTokenNextTokenToRead;
        }
        while (tokenFilterContext != this._headContext) {
            tokenFilterContext = this._exposedContext.findChildOf(tokenFilterContext);
            this._exposedContext = tokenFilterContext;
            if (tokenFilterContext == null) {
                throw _constructError("Unexpected problem: chain of filtered context broken");
            }
            JsonToken jsonTokenNextTokenToRead2 = tokenFilterContext.nextTokenToRead();
            if (jsonTokenNextTokenToRead2 != null) {
                return jsonTokenNextTokenToRead2;
            }
        }
        throw _constructError("Internal error: failed to locate expected buffered tokens");
    }

    private final boolean _verifyAllowedMatches() throws IOException {
        int i = this._matchCount;
        if (i != 0 && !this._allowMultipleMatches) {
            return false;
        }
        this._matchCount = i + 1;
        return true;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextValue() throws IOException {
        JsonToken jsonTokenNextToken = nextToken();
        return jsonTokenNextToken == JsonToken.FIELD_NAME ? nextToken() : jsonTokenNextToken;
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonParser skipChildren() throws IOException {
        if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
            return this;
        }
        int i = 1;
        while (true) {
            JsonToken jsonTokenNextToken = nextToken();
            if (jsonTokenNextToken == null) {
                return this;
            }
            if (jsonTokenNextToken.isStructStart()) {
                i++;
            } else if (jsonTokenNextToken.isStructEnd() && i - 1 == 0) {
                return this;
            }
        }
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public String getText() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return currentName();
        }
        return this.delegate.getText();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean hasTextCharacters() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return false;
        }
        return this.delegate.hasTextCharacters();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public char[] getTextCharacters() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return currentName().toCharArray();
        }
        return this.delegate.getTextCharacters();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int getTextLength() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return currentName().length();
        }
        return this.delegate.getTextLength();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int getTextOffset() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return 0;
        }
        return this.delegate.getTextOffset();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public BigInteger getBigIntegerValue() throws IOException {
        return this.delegate.getBigIntegerValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean getBooleanValue() throws IOException {
        return this.delegate.getBooleanValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public byte getByteValue() throws IOException {
        return this.delegate.getByteValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public short getShortValue() throws IOException {
        return this.delegate.getShortValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public BigDecimal getDecimalValue() throws IOException {
        return this.delegate.getDecimalValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public double getDoubleValue() throws IOException {
        return this.delegate.getDoubleValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public float getFloatValue() throws IOException {
        return this.delegate.getFloatValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int getIntValue() throws IOException {
        return this.delegate.getIntValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public long getLongValue() throws IOException {
        return this.delegate.getLongValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public JsonParser.NumberType getNumberType() throws IOException {
        return this.delegate.getNumberType();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public Number getNumberValue() throws IOException {
        return this.delegate.getNumberValue();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt() throws IOException {
        return this.delegate.getValueAsInt();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt(int i) throws IOException {
        return this.delegate.getValueAsInt(i);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public long getValueAsLong() throws IOException {
        return this.delegate.getValueAsLong();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public long getValueAsLong(long j) throws IOException {
        return this.delegate.getValueAsLong(j);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public double getValueAsDouble() throws IOException {
        return this.delegate.getValueAsDouble();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public double getValueAsDouble(double d) throws IOException {
        return this.delegate.getValueAsDouble(d);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean getValueAsBoolean() throws IOException {
        return this.delegate.getValueAsBoolean();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public boolean getValueAsBoolean(boolean z) throws IOException {
        return this.delegate.getValueAsBoolean(z);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return currentName();
        }
        return this.delegate.getValueAsString();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public String getValueAsString(String str) throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            return currentName();
        }
        return this.delegate.getValueAsString(str);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public Object getEmbeddedObject() throws IOException {
        return this.delegate.getEmbeddedObject();
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        return this.delegate.getBinaryValue(base64Variant);
    }

    @Override // com.fasterxml.jackson.core.util.JsonParserDelegate, com.fasterxml.jackson.core.JsonParser
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
        return this.delegate.readBinaryValue(base64Variant, outputStream);
    }

    protected JsonStreamContext _filterContext() {
        TokenFilterContext tokenFilterContext = this._exposedContext;
        return tokenFilterContext != null ? tokenFilterContext : this._headContext;
    }
}
