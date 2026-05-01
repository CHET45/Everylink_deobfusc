package com.fasterxml.jackson.core.json.async;

import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.FastDoubleMath;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.p008io.CharTypes;
import com.fasterxml.jackson.core.p008io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.util.InternalJacksonUtil;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.microsoft.azure.storage.Constants;
import java.io.IOException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public abstract class NonBlockingUtf8JsonParserBase extends NonBlockingJsonParserBase {
    protected int _origBufferLen;
    private static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
    private static final int FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
    private static final int FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
    private static final int FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
    private static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
    private static final int FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
    private static final int FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
    private static final int[] _icUTF8 = CharTypes.getInputCodeUtf8();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();

    protected abstract byte getByteFromBuffer(int i);

    protected abstract byte getNextSignedByteFromBuffer();

    protected abstract int getNextUnsignedByteFromBuffer();

    protected NonBlockingUtf8JsonParserBase(IOContext iOContext, int i, ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
        super(iOContext, i, byteQuadsCanonicalizer);
    }

    public final boolean needMoreInput() {
        return this._inputPtr >= this._inputEnd && !this._endOfInput;
    }

    public void endOfInput() {
        this._endOfInput = true;
    }

    @Override // com.fasterxml.jackson.core.base.ParserBase
    protected char _decodeEscaped() throws IOException {
        VersionUtil.throwInternal();
        return ' ';
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            if (this._closed) {
                return null;
            }
            if (this._endOfInput) {
                if (this._currToken == JsonToken.NOT_AVAILABLE) {
                    return _finishTokenWithEOF();
                }
                return _eofAsNextToken();
            }
            return JsonToken.NOT_AVAILABLE;
        }
        if (this._currToken == JsonToken.NOT_AVAILABLE) {
            return _finishToken();
        }
        this._numTypesValid = 0;
        this._tokenInputTotal = this._currInputProcessed + ((long) this._inputPtr);
        this._binaryValue = null;
        int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
        switch (this._majorState) {
            case 0:
                return _startDocument(nextUnsignedByteFromBuffer);
            case 1:
                return _startValue(nextUnsignedByteFromBuffer);
            case 2:
                return _startFieldName(nextUnsignedByteFromBuffer);
            case 3:
                return _startFieldNameAfterComma(nextUnsignedByteFromBuffer);
            case 4:
                return _startValueExpectColon(nextUnsignedByteFromBuffer);
            case 5:
                return _startValue(nextUnsignedByteFromBuffer);
            case 6:
                return _startValueExpectComma(nextUnsignedByteFromBuffer);
            default:
                VersionUtil.throwInternal();
                return null;
        }
    }

    protected final JsonToken _finishToken() throws IOException {
        int i = this._minorState;
        if (i == 1) {
            return _finishBOM(this._pending32);
        }
        if (i == 4) {
            return _startFieldName(getNextUnsignedByteFromBuffer());
        }
        if (i == 5) {
            return _startFieldNameAfterComma(getNextUnsignedByteFromBuffer());
        }
        switch (i) {
            case 7:
                return _parseEscapedName(this._quadLength, this._pending32, this._pendingBytes);
            case 8:
                return _finishFieldWithEscape();
            case 9:
                return _finishAposName(this._quadLength, this._pending32, this._pendingBytes);
            case 10:
                return _finishUnquotedName(this._quadLength, this._pending32, this._pendingBytes);
            default:
                switch (i) {
                    case 12:
                        return _startValue(getNextUnsignedByteFromBuffer());
                    case 13:
                        return _startValueExpectComma(getNextUnsignedByteFromBuffer());
                    case 14:
                        return _startValueExpectColon(getNextUnsignedByteFromBuffer());
                    case 15:
                        return _startValueAfterComma(getNextUnsignedByteFromBuffer());
                    case 16:
                        return _finishKeywordToken("null", this._pending32, JsonToken.VALUE_NULL);
                    case 17:
                        return _finishKeywordToken(Constants.TRUE, this._pending32, JsonToken.VALUE_TRUE);
                    case 18:
                        return _finishKeywordToken(Constants.FALSE, this._pending32, JsonToken.VALUE_FALSE);
                    case 19:
                        return _finishNonStdToken(this._nonStdTokenType, this._pending32);
                    default:
                        switch (i) {
                            case 22:
                                return _finishNumberPlus(getNextUnsignedByteFromBuffer());
                            case 23:
                                return _finishNumberMinus(getNextUnsignedByteFromBuffer());
                            case 24:
                                return _finishNumberLeadingZeroes();
                            case 25:
                                return _finishNumberLeadingNegZeroes();
                            case 26:
                                return _finishNumberIntegralPart(this._textBuffer.getBufferWithoutReset(), this._textBuffer.getCurrentSegmentSize());
                            default:
                                switch (i) {
                                    case 30:
                                        return _finishFloatFraction();
                                    case 31:
                                        return _finishFloatExponent(true, getNextUnsignedByteFromBuffer());
                                    case 32:
                                        return _finishFloatExponent(false, getNextUnsignedByteFromBuffer());
                                    default:
                                        switch (i) {
                                            case 40:
                                                return _finishRegularString();
                                            case 41:
                                                int i_decodeSplitEscaped = _decodeSplitEscaped(this._quoted32, this._quotedDigits);
                                                if (i_decodeSplitEscaped < 0) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                this._textBuffer.append((char) i_decodeSplitEscaped);
                                                if (this._minorStateAfterSplit == 45) {
                                                    return _finishAposString();
                                                }
                                                return _finishRegularString();
                                            case 42:
                                                this._textBuffer.append((char) _decodeUTF8_2(this._pending32, getNextSignedByteFromBuffer()));
                                                if (this._minorStateAfterSplit == 45) {
                                                    return _finishAposString();
                                                }
                                                return _finishRegularString();
                                            case 43:
                                                if (!_decodeSplitUTF8_3(this._pending32, this._pendingBytes, getNextSignedByteFromBuffer())) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                if (this._minorStateAfterSplit == 45) {
                                                    return _finishAposString();
                                                }
                                                return _finishRegularString();
                                            case 44:
                                                if (!_decodeSplitUTF8_4(this._pending32, this._pendingBytes, getNextSignedByteFromBuffer())) {
                                                    return JsonToken.NOT_AVAILABLE;
                                                }
                                                if (this._minorStateAfterSplit == 45) {
                                                    return _finishAposString();
                                                }
                                                return _finishRegularString();
                                            case 45:
                                                return _finishAposString();
                                            default:
                                                switch (i) {
                                                    case 50:
                                                        return _finishErrorToken();
                                                    case 51:
                                                        return _startSlashComment(this._pending32);
                                                    case 52:
                                                        return _finishCComment(this._pending32, true);
                                                    case 53:
                                                        return _finishCComment(this._pending32, false);
                                                    case 54:
                                                        return _finishCppComment(this._pending32);
                                                    case 55:
                                                        return _finishHashComment(this._pending32);
                                                    default:
                                                        VersionUtil.throwInternal();
                                                        return null;
                                                }
                                        }
                                }
                        }
                }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected final JsonToken _finishTokenWithEOF() throws IOException {
        JsonToken jsonToken = this._currToken;
        int i = this._minorState;
        if (i == 3) {
            return _eofAsNextToken();
        }
        if (i == 12) {
            return _eofAsNextToken();
        }
        if (i != 50) {
            switch (i) {
                case 16:
                    return _finishKeywordTokenWithEOF("null", this._pending32, JsonToken.VALUE_NULL);
                case 17:
                    return _finishKeywordTokenWithEOF(Constants.TRUE, this._pending32, JsonToken.VALUE_TRUE);
                case 18:
                    return _finishKeywordTokenWithEOF(Constants.FALSE, this._pending32, JsonToken.VALUE_FALSE);
                case 19:
                    return _finishNonStdTokenWithEOF(this._nonStdTokenType, this._pending32);
                default:
                    switch (i) {
                        case 24:
                        case 25:
                            return _valueCompleteInt(0, "0");
                        case 26:
                            int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                            if (this._numberNegative) {
                                currentSegmentSize--;
                            }
                            this._intLength = currentSegmentSize;
                            return _valueComplete(JsonToken.VALUE_NUMBER_INT);
                        default:
                            switch (i) {
                                case 30:
                                    this._expLength = 0;
                                    return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
                                case 31:
                                    _reportInvalidEOF(": was expecting fraction after exponent marker", JsonToken.VALUE_NUMBER_FLOAT);
                                    _reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
                                    return _eofAsNextToken();
                                case 32:
                                    return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
                                default:
                                    switch (i) {
                                        case 52:
                                        case 53:
                                            _reportInvalidEOF(": was expecting closing '*/' for comment", JsonToken.NOT_AVAILABLE);
                                            break;
                                        case 54:
                                        case 55:
                                            break;
                                        default:
                                            _reportInvalidEOF(": was expecting rest of token (internal state: " + this._minorState + ")", this._currToken);
                                            return jsonToken;
                                    }
                                    return _eofAsNextToken();
                            }
                    }
            }
        }
        return _finishErrorTokenWithEOF();
    }

    private final JsonToken _startDocument(int i) throws IOException {
        int nextUnsignedByteFromBuffer = i & 255;
        if (nextUnsignedByteFromBuffer == 239 && this._minorState != 1) {
            return _finishBOM(1);
        }
        while (nextUnsignedByteFromBuffer <= 32) {
            if (nextUnsignedByteFromBuffer != 32) {
                if (nextUnsignedByteFromBuffer == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer != 9) {
                    _throwInvalidSpace(nextUnsignedByteFromBuffer);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 3;
                if (this._closed) {
                    return null;
                }
                if (this._endOfInput) {
                    return _eofAsNextToken();
                }
                return JsonToken.NOT_AVAILABLE;
            }
            nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
        }
        return _startValue(nextUnsignedByteFromBuffer);
    }

    private final JsonToken _finishBOM(int i) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        this._currInputProcessed -= 3;
                        return _startDocument(nextUnsignedByteFromBuffer);
                    }
                } else if (nextUnsignedByteFromBuffer != 191) {
                    _reportError("Unexpected byte 0x%02x following 0xEF 0xBB; should get 0xBF as third byte of UTF-8 BOM", Integer.valueOf(nextUnsignedByteFromBuffer));
                }
            } else if (nextUnsignedByteFromBuffer != 187) {
                _reportError("Unexpected byte 0x%02x following 0xEF; should get 0xBB as second byte UTF-8 BOM", Integer.valueOf(nextUnsignedByteFromBuffer));
            }
            i++;
        }
        this._pending32 = i;
        this._minorState = 1;
        return _updateTokenToNA();
    }

    private final JsonToken _startFieldName(int i) throws IOException {
        String str_fastParseName;
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 4;
            return this._currToken;
        }
        _updateTokenLocation();
        if (i != 34) {
            if (i == 125) {
                return _closeObjectScope();
            }
            return _handleOddName(i);
        }
        if (this._inputPtr + 13 <= this._inputEnd && (str_fastParseName = _fastParseName()) != null) {
            return _fieldComplete(str_fastParseName);
        }
        return _parseEscapedName(0, 0, 0);
    }

    private final JsonToken _startFieldNameAfterComma(int i) throws IOException {
        String str_fastParseName;
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 5;
            return this._currToken;
        }
        if (i != 44) {
            if (i == 125) {
                return _closeObjectScope();
            }
            if (i == 35) {
                return _finishHashComment(5);
            }
            if (i == 47) {
                return _startSlashComment(5);
            }
            _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        int i2 = this._inputPtr;
        if (i2 >= this._inputEnd) {
            this._minorState = 4;
            return _updateTokenToNA();
        }
        int byteFromBuffer = getByteFromBuffer(i2);
        this._inputPtr = i2 + 1;
        if (byteFromBuffer <= 32 && (byteFromBuffer = _skipWS(byteFromBuffer)) <= 0) {
            this._minorState = 4;
            return this._currToken;
        }
        _updateTokenLocation();
        if (byteFromBuffer != 34) {
            if (byteFromBuffer == 125 && (this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                return _closeObjectScope();
            }
            return _handleOddName(byteFromBuffer);
        }
        if (this._inputPtr + 13 <= this._inputEnd && (str_fastParseName = _fastParseName()) != null) {
            return _fieldComplete(str_fastParseName);
        }
        return _parseEscapedName(0, 0, 0);
    }

    private final JsonToken _startValue(int i) throws IOException {
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 12;
            return this._currToken;
        }
        _updateTokenLocation();
        this._parsingContext.expectComma();
        if (i == 34) {
            return _startString();
        }
        if (i == 35) {
            return _finishHashComment(12);
        }
        if (i == 43) {
            return _startPositiveNumber();
        }
        if (i == 91) {
            return _startArrayScope();
        }
        if (i == 93) {
            return _closeArrayScope();
        }
        if (i == 102) {
            return _startFalseToken();
        }
        if (i == 110) {
            return _startNullToken();
        }
        if (i == 116) {
            return _startTrueToken();
        }
        if (i == 123) {
            return _startObjectScope();
        }
        if (i != 125) {
            switch (i) {
                case 45:
                    return _startNegativeNumber();
                case 46:
                    if (isEnabled(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS.mappedFeature())) {
                        return _startFloatThatStartsWithPeriod();
                    }
                    break;
                case 47:
                    return _startSlashComment(12);
                case 48:
                    return _startNumberLeadingZero();
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case Opcodes.FSTORE /* 56 */:
                case Opcodes.DSTORE /* 57 */:
                    return _startPositiveNumber(i);
            }
            return _startUnexpectedValue(false, i);
        }
        return _closeObjectScope();
    }

    private final JsonToken _startValueExpectComma(int i) throws IOException {
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 13;
            return this._currToken;
        }
        if (i != 44) {
            if (i == 93) {
                return _closeArrayScope();
            }
            if (i == 125) {
                return _closeObjectScope();
            }
            if (i == 47) {
                return _startSlashComment(13);
            }
            if (i == 35) {
                return _finishHashComment(13);
            }
            _reportUnexpectedChar(i, "was expecting comma to separate " + this._parsingContext.typeDesc() + " entries");
        }
        this._parsingContext.expectComma();
        int i2 = this._inputPtr;
        if (i2 >= this._inputEnd) {
            this._minorState = 15;
            return _updateTokenToNA();
        }
        int byteFromBuffer = getByteFromBuffer(i2);
        this._inputPtr = i2 + 1;
        if (byteFromBuffer <= 32 && (byteFromBuffer = _skipWS(byteFromBuffer)) <= 0) {
            this._minorState = 15;
            return this._currToken;
        }
        _updateTokenLocation();
        if (byteFromBuffer == 34) {
            return _startString();
        }
        if (byteFromBuffer == 35) {
            return _finishHashComment(15);
        }
        if (byteFromBuffer == 43) {
            return _startPositiveNumber();
        }
        if (byteFromBuffer == 45) {
            return _startNegativeNumber();
        }
        if (byteFromBuffer == 91) {
            return _startArrayScope();
        }
        if (byteFromBuffer != 93) {
            if (byteFromBuffer == 102) {
                return _startFalseToken();
            }
            if (byteFromBuffer == 110) {
                return _startNullToken();
            }
            if (byteFromBuffer == 116) {
                return _startTrueToken();
            }
            if (byteFromBuffer == 123) {
                return _startObjectScope();
            }
            if (byteFromBuffer != 125) {
                switch (byteFromBuffer) {
                    case 47:
                        return _startSlashComment(15);
                    case 48:
                        return _startNumberLeadingZero();
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case Opcodes.FSTORE /* 56 */:
                    case Opcodes.DSTORE /* 57 */:
                        return _startPositiveNumber(byteFromBuffer);
                }
            }
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                return _closeObjectScope();
            }
        } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
            return _closeArrayScope();
        }
        return _startUnexpectedValue(true, byteFromBuffer);
    }

    private final JsonToken _startValueExpectColon(int i) throws IOException {
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 14;
            return this._currToken;
        }
        if (i != 58) {
            if (i == 47) {
                return _startSlashComment(14);
            }
            if (i == 35) {
                return _finishHashComment(14);
            }
            _reportUnexpectedChar(i, "was expecting a colon to separate field name and value");
        }
        int i2 = this._inputPtr;
        if (i2 >= this._inputEnd) {
            this._minorState = 12;
            return _updateTokenToNA();
        }
        int byteFromBuffer = getByteFromBuffer(i2);
        this._inputPtr = i2 + 1;
        if (byteFromBuffer <= 32 && (byteFromBuffer = _skipWS(byteFromBuffer)) <= 0) {
            this._minorState = 12;
            return this._currToken;
        }
        _updateTokenLocation();
        if (byteFromBuffer == 34) {
            return _startString();
        }
        if (byteFromBuffer == 35) {
            return _finishHashComment(12);
        }
        if (byteFromBuffer == 43) {
            return _startPositiveNumber();
        }
        if (byteFromBuffer == 45) {
            return _startNegativeNumber();
        }
        if (byteFromBuffer == 91) {
            return _startArrayScope();
        }
        if (byteFromBuffer == 102) {
            return _startFalseToken();
        }
        if (byteFromBuffer == 110) {
            return _startNullToken();
        }
        if (byteFromBuffer == 116) {
            return _startTrueToken();
        }
        if (byteFromBuffer != 123) {
            switch (byteFromBuffer) {
                case 47:
                    return _startSlashComment(12);
                case 48:
                    return _startNumberLeadingZero();
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case Opcodes.FSTORE /* 56 */:
                case Opcodes.DSTORE /* 57 */:
                    return _startPositiveNumber(byteFromBuffer);
                default:
                    return _startUnexpectedValue(false, byteFromBuffer);
            }
        }
        return _startObjectScope();
    }

    private final JsonToken _startValueAfterComma(int i) throws IOException {
        if (i <= 32 && (i = _skipWS(i)) <= 0) {
            this._minorState = 15;
            return this._currToken;
        }
        _updateTokenLocation();
        if (i == 34) {
            return _startString();
        }
        if (i == 35) {
            return _finishHashComment(15);
        }
        if (i == 43) {
            return _startPositiveNumber();
        }
        if (i == 45) {
            return _startNegativeNumber();
        }
        if (i == 91) {
            return _startArrayScope();
        }
        if (i != 93) {
            if (i == 102) {
                return _startFalseToken();
            }
            if (i == 110) {
                return _startNullToken();
            }
            if (i == 116) {
                return _startTrueToken();
            }
            if (i == 123) {
                return _startObjectScope();
            }
            if (i != 125) {
                switch (i) {
                    case 47:
                        return _startSlashComment(15);
                    case 48:
                        return _startNumberLeadingZero();
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case Opcodes.FSTORE /* 56 */:
                    case Opcodes.DSTORE /* 57 */:
                        return _startPositiveNumber(i);
                }
            }
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
                return _closeObjectScope();
            }
        } else if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0) {
            return _closeArrayScope();
        }
        return _startUnexpectedValue(true, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _startUnexpectedValue(boolean r3, int r4) throws java.io.IOException {
        /*
            r2 = this;
            r3 = 39
            if (r4 == r3) goto L53
            r3 = 73
            r0 = 1
            if (r4 == r3) goto L4e
            r3 = 78
            if (r4 == r3) goto L48
            r3 = 93
            if (r4 == r3) goto L24
            r3 = 125(0x7d, float:1.75E-43)
            if (r4 == r3) goto L5f
            r3 = 43
            if (r4 == r3) goto L1e
            r3 = 44
            if (r4 == r3) goto L2d
            goto L5f
        L1e:
            r3 = 2
            com.fasterxml.jackson.core.JsonToken r3 = r2._finishNonStdToken(r3, r0)
            return r3
        L24:
            com.fasterxml.jackson.core.json.JsonReadContext r3 = r2._parsingContext
            boolean r3 = r3.inArray()
            if (r3 != 0) goto L2d
            goto L5f
        L2d:
            com.fasterxml.jackson.core.json.JsonReadContext r3 = r2._parsingContext
            boolean r3 = r3.inRoot()
            if (r3 != 0) goto L5f
            int r3 = r2._features
            int r1 = com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase.FEAT_MASK_ALLOW_MISSING
            r3 = r3 & r1
            if (r3 == 0) goto L5f
            int r3 = r2._inputPtr
            int r3 = r3 - r0
            r2._inputPtr = r3
            com.fasterxml.jackson.core.JsonToken r3 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            com.fasterxml.jackson.core.JsonToken r3 = r2._valueComplete(r3)
            return r3
        L48:
            r3 = 0
            com.fasterxml.jackson.core.JsonToken r3 = r2._finishNonStdToken(r3, r0)
            return r3
        L4e:
            com.fasterxml.jackson.core.JsonToken r3 = r2._finishNonStdToken(r0, r0)
            return r3
        L53:
            int r3 = r2._features
            int r0 = com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase.FEAT_MASK_ALLOW_SINGLE_QUOTES
            r3 = r3 & r0
            if (r3 == 0) goto L5f
            com.fasterxml.jackson.core.JsonToken r3 = r2._startAposString()
            return r3
        L5f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r0 = "expected a valid value "
            r3.<init>(r0)
            java.lang.String r0 = r2._validJsonValueList()
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            r2._reportUnexpectedChar(r4, r3)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase._startUnexpectedValue(boolean, int):com.fasterxml.jackson.core.JsonToken");
    }

    private final int _skipWS(int i) throws IOException {
        do {
            if (i != 32) {
                if (i == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (i != 9) {
                    _throwInvalidSpace(i);
                }
            }
            if (this._inputPtr >= this._inputEnd) {
                _updateTokenToNA();
                return 0;
            }
            i = getNextUnsignedByteFromBuffer();
        } while (i <= 32);
        return i;
    }

    private final JsonToken _startSlashComment(int i) throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_JAVA_COMMENTS) == 0) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd) {
            this._pending32 = i;
            this._minorState = 51;
            return _updateTokenToNA();
        }
        byte nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
        if (nextSignedByteFromBuffer == 42) {
            return _finishCComment(i, false);
        }
        if (nextSignedByteFromBuffer == 47) {
            return _finishCppComment(i);
        }
        _reportUnexpectedChar(nextSignedByteFromBuffer & UByte.MAX_VALUE, "was expecting either '*' or '/' for a comment");
        return null;
    }

    private final JsonToken _finishHashComment(int i) throws IOException {
        if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) == 0) {
            _reportUnexpectedChar(35, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_YAML_COMMENTS' not enabled for parser)");
        }
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer < 32) {
                if (nextUnsignedByteFromBuffer == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer != 9) {
                    _throwInvalidSpace(nextUnsignedByteFromBuffer);
                }
                return _startAfterComment(i);
            }
        }
        this._minorState = 55;
        this._pending32 = i;
        return _updateTokenToNA();
    }

    private final JsonToken _finishCppComment(int i) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer < 32) {
                if (nextUnsignedByteFromBuffer == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer != 9) {
                    _throwInvalidSpace(nextUnsignedByteFromBuffer);
                }
                return _startAfterComment(i);
            }
        }
        this._minorState = 54;
        this._pending32 = i;
        return _updateTokenToNA();
    }

    private final JsonToken _finishCComment(int i, boolean z) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer < 32) {
                if (nextUnsignedByteFromBuffer == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer == 13) {
                    this._currInputRowAlt++;
                    this._currInputRowStart = this._inputPtr;
                } else if (nextUnsignedByteFromBuffer != 9) {
                    _throwInvalidSpace(nextUnsignedByteFromBuffer);
                }
            } else if (nextUnsignedByteFromBuffer == 42) {
                z = true;
            } else if (nextUnsignedByteFromBuffer == 47 && z) {
                return _startAfterComment(i);
            }
            z = false;
        }
        this._minorState = z ? 52 : 53;
        this._pending32 = i;
        return _updateTokenToNA();
    }

    private final JsonToken _startAfterComment(int i) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = i;
            return _updateTokenToNA();
        }
        int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
        if (i == 4) {
            return _startFieldName(nextUnsignedByteFromBuffer);
        }
        if (i == 5) {
            return _startFieldNameAfterComma(nextUnsignedByteFromBuffer);
        }
        switch (i) {
            case 12:
                return _startValue(nextUnsignedByteFromBuffer);
            case 13:
                return _startValueExpectComma(nextUnsignedByteFromBuffer);
            case 14:
                return _startValueExpectColon(nextUnsignedByteFromBuffer);
            case 15:
                return _startValueAfterComma(nextUnsignedByteFromBuffer);
            default:
                VersionUtil.throwInternal();
                return null;
        }
    }

    protected JsonToken _startFalseToken() throws IOException {
        int byteFromBuffer;
        int i = this._inputPtr;
        if (i + 4 < this._inputEnd) {
            int i2 = i + 1;
            if (getByteFromBuffer(i) == 97) {
                int i3 = i + 2;
                if (getByteFromBuffer(i2) == 108) {
                    int i4 = i + 3;
                    if (getByteFromBuffer(i3) == 115) {
                        int i5 = i + 4;
                        if (getByteFromBuffer(i4) == 101 && ((byteFromBuffer = getByteFromBuffer(i5) & UByte.MAX_VALUE) < 48 || (byteFromBuffer | 32) == 125)) {
                            this._inputPtr = i5;
                            return _valueComplete(JsonToken.VALUE_FALSE);
                        }
                    }
                }
            }
        }
        this._minorState = 18;
        return _finishKeywordToken(Constants.FALSE, 1, JsonToken.VALUE_FALSE);
    }

    protected JsonToken _startTrueToken() throws IOException {
        int byteFromBuffer;
        int i = this._inputPtr;
        if (i + 3 < this._inputEnd) {
            int i2 = i + 1;
            if (getByteFromBuffer(i) == 114) {
                int i3 = i + 2;
                if (getByteFromBuffer(i2) == 117) {
                    int i4 = i + 3;
                    if (getByteFromBuffer(i3) == 101 && ((byteFromBuffer = getByteFromBuffer(i4) & UByte.MAX_VALUE) < 48 || (byteFromBuffer | 32) == 125)) {
                        this._inputPtr = i4;
                        return _valueComplete(JsonToken.VALUE_TRUE);
                    }
                }
            }
        }
        this._minorState = 17;
        return _finishKeywordToken(Constants.TRUE, 1, JsonToken.VALUE_TRUE);
    }

    protected JsonToken _startNullToken() throws IOException {
        int byteFromBuffer;
        int i = this._inputPtr;
        if (i + 3 < this._inputEnd) {
            int i2 = i + 1;
            if (getByteFromBuffer(i) == 117) {
                int i3 = i + 2;
                if (getByteFromBuffer(i2) == 108) {
                    int i4 = i + 3;
                    if (getByteFromBuffer(i3) == 108 && ((byteFromBuffer = getByteFromBuffer(i4) & UByte.MAX_VALUE) < 48 || (byteFromBuffer | 32) == 125)) {
                        this._inputPtr = i4;
                        return _valueComplete(JsonToken.VALUE_NULL);
                    }
                }
            }
        }
        this._minorState = 16;
        return _finishKeywordToken("null", 1, JsonToken.VALUE_NULL);
    }

    protected JsonToken _finishKeywordToken(String str, int i, JsonToken jsonToken) throws IOException {
        int length = str.length();
        while (this._inputPtr < this._inputEnd) {
            char byteFromBuffer = getByteFromBuffer(this._inputPtr);
            if (i == length) {
                if (byteFromBuffer < '0' || (byteFromBuffer | ' ') == 125) {
                    return _valueComplete(jsonToken);
                }
            } else if (byteFromBuffer == str.charAt(i)) {
                i++;
                this._inputPtr++;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(str, 0, i);
            return _finishErrorToken();
        }
        this._pending32 = i;
        return _updateTokenToNA();
    }

    protected JsonToken _finishKeywordTokenWithEOF(String str, int i, JsonToken jsonToken) throws IOException {
        if (i == str.length()) {
            return _updateToken(jsonToken);
        }
        this._textBuffer.resetWithCopy(str, 0, i);
        return _finishErrorTokenWithEOF();
    }

    protected JsonToken _finishNonStdToken(int i, int i2) throws IOException {
        String str_nonStdToken = _nonStdToken(i);
        int length = str_nonStdToken.length();
        while (this._inputPtr < this._inputEnd) {
            char byteFromBuffer = getByteFromBuffer(this._inputPtr);
            if (i2 == length) {
                if (byteFromBuffer < '0' || (byteFromBuffer | ' ') == 125) {
                    return _valueNonStdNumberComplete(i);
                }
            } else if (byteFromBuffer == str_nonStdToken.charAt(i2)) {
                i2++;
                this._inputPtr++;
            }
            this._minorState = 50;
            this._textBuffer.resetWithCopy(str_nonStdToken, 0, i2);
            return _finishErrorToken();
        }
        this._nonStdTokenType = i;
        this._pending32 = i2;
        this._minorState = 19;
        return _updateTokenToNA();
    }

    protected JsonToken _finishNonStdTokenWithEOF(int i, int i2) throws IOException {
        String str_nonStdToken = _nonStdToken(i);
        if (i2 == str_nonStdToken.length()) {
            return _valueNonStdNumberComplete(i);
        }
        this._textBuffer.resetWithCopy(str_nonStdToken, 0, i2);
        return _finishErrorTokenWithEOF();
    }

    protected JsonToken _finishErrorToken() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            char nextSignedByteFromBuffer = (char) getNextSignedByteFromBuffer();
            if (Character.isJavaIdentifierPart(nextSignedByteFromBuffer)) {
                this._textBuffer.append(nextSignedByteFromBuffer);
                if (this._textBuffer.size() < this._ioContext.errorReportConfiguration().getMaxErrorTokenLength()) {
                }
            }
            return _reportErrorToken(this._textBuffer.contentsAsString());
        }
        return _updateTokenToNA();
    }

    protected JsonToken _finishErrorTokenWithEOF() throws IOException {
        return _reportErrorToken(this._textBuffer.contentsAsString());
    }

    protected JsonToken _reportErrorToken(String str) throws IOException {
        _reportError("Unrecognized token '%s': was expecting %s", this._textBuffer.contentsAsString(), _validJsonTokenList());
        return JsonToken.NOT_AVAILABLE;
    }

    protected JsonToken _startFloatThatStartsWithPeriod() throws IOException {
        this._numberNegative = false;
        this._intLength = 0;
        return _startFloat(this._textBuffer.emptyAndGetCurrentSegment(), 0, 46);
    }

    protected JsonToken _startPositiveNumber(int i) throws IOException {
        this._numberNegative = false;
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        cArrEmptyAndGetCurrentSegment[0] = (char) i;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(1);
            return _updateTokenToNA();
        }
        int byteFromBuffer = getByteFromBuffer(this._inputPtr) & UByte.MAX_VALUE;
        int i2 = 1;
        while (true) {
            if (byteFromBuffer < 48) {
                if (byteFromBuffer == 46) {
                    this._intLength = i2;
                    this._inputPtr++;
                    return _startFloat(cArrEmptyAndGetCurrentSegment, i2, byteFromBuffer);
                }
            } else if (byteFromBuffer <= 57) {
                if (i2 >= cArrEmptyAndGetCurrentSegment.length) {
                    cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                }
                int i3 = i2 + 1;
                cArrEmptyAndGetCurrentSegment[i2] = (char) byteFromBuffer;
                int i4 = this._inputPtr + 1;
                this._inputPtr = i4;
                if (i4 >= this._inputEnd) {
                    this._minorState = 26;
                    this._textBuffer.setCurrentLength(i3);
                    return _updateTokenToNA();
                }
                byteFromBuffer = getByteFromBuffer(this._inputPtr) & UByte.MAX_VALUE;
                i2 = i3;
            } else if ((byteFromBuffer | 32) == 101) {
                this._intLength = i2;
                this._inputPtr++;
                return _startFloat(cArrEmptyAndGetCurrentSegment, i2, byteFromBuffer);
            }
        }
        this._intLength = i2;
        this._textBuffer.setCurrentLength(i2);
        return _valueComplete(JsonToken.VALUE_NUMBER_INT);
    }

    protected JsonToken _startNegativeNumber() throws IOException {
        this._numberNegative = true;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 23;
            return _updateTokenToNA();
        }
        int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
        int i = 2;
        if (nextUnsignedByteFromBuffer <= 48) {
            if (nextUnsignedByteFromBuffer == 48) {
                return _finishNumberLeadingNegZeroes();
            }
            _reportUnexpectedNumberChar(nextUnsignedByteFromBuffer, "expected digit (0-9) to follow minus sign, for valid numeric value");
        } else if (nextUnsignedByteFromBuffer > 57) {
            if (nextUnsignedByteFromBuffer == 73) {
                return _finishNonStdToken(3, 2);
            }
            _reportUnexpectedNumberChar(nextUnsignedByteFromBuffer, "expected digit (0-9) to follow minus sign, for valid numeric value");
        }
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        cArrEmptyAndGetCurrentSegment[0] = '-';
        cArrEmptyAndGetCurrentSegment[1] = (char) nextUnsignedByteFromBuffer;
        if (this._inputPtr >= this._inputEnd) {
            this._minorState = 26;
            this._textBuffer.setCurrentLength(2);
            this._intLength = 1;
            return _updateTokenToNA();
        }
        int byteFromBuffer = getByteFromBuffer(this._inputPtr);
        while (true) {
            if (byteFromBuffer < 48) {
                if (byteFromBuffer == 46) {
                    this._intLength = i - 1;
                    this._inputPtr++;
                    return _startFloat(cArrEmptyAndGetCurrentSegment, i, byteFromBuffer);
                }
            } else if (byteFromBuffer <= 57) {
                if (i >= cArrEmptyAndGetCurrentSegment.length) {
                    cArrEmptyAndGetCurrentSegment = this._textBuffer.expandCurrentSegment();
                }
                int i2 = i + 1;
                cArrEmptyAndGetCurrentSegment[i] = (char) byteFromBuffer;
                int i3 = this._inputPtr + 1;
                this._inputPtr = i3;
                if (i3 >= this._inputEnd) {
                    this._minorState = 26;
                    this._textBuffer.setCurrentLength(i2);
                    return _updateTokenToNA();
                }
                byteFromBuffer = getByteFromBuffer(this._inputPtr) & UByte.MAX_VALUE;
                i = i2;
            } else if ((byteFromBuffer | 32) == 101) {
                this._intLength = i - 1;
                this._inputPtr++;
                return _startFloat(cArrEmptyAndGetCurrentSegment, i, byteFromBuffer);
            }
        }
        this._intLength = i - 1;
        this._textBuffer.setCurrentLength(i);
        return _valueComplete(JsonToken.VALUE_NUMBER_INT);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ac, code lost:
    
        r9._intLength = r7 - 1;
        r9._textBuffer.setCurrentLength(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bb, code lost:
    
        return _valueComplete(com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _startPositiveNumber() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase._startPositiveNumber():com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _startNumberLeadingZero() throws IOException {
        int i = this._inputPtr;
        if (i >= this._inputEnd) {
            this._minorState = 24;
            return _updateTokenToNA();
        }
        int i2 = i + 1;
        int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
        if (byteFromBuffer < 48) {
            if (byteFromBuffer == 46) {
                this._inputPtr = i2;
                this._intLength = 1;
                char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                cArrEmptyAndGetCurrentSegment[0] = '0';
                return _startFloat(cArrEmptyAndGetCurrentSegment, 1, byteFromBuffer);
            }
        } else {
            if (byteFromBuffer <= 57) {
                return _finishNumberLeadingZeroes();
            }
            int i3 = byteFromBuffer | 32;
            if (i3 == 101) {
                this._inputPtr = i2;
                this._intLength = 1;
                char[] cArrEmptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                cArrEmptyAndGetCurrentSegment2[0] = '0';
                return _startFloat(cArrEmptyAndGetCurrentSegment2, 1, byteFromBuffer);
            }
            if (i3 != 125) {
                _reportUnexpectedNumberChar(byteFromBuffer, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
            }
        }
        return _valueCompleteInt(0, "0");
    }

    protected JsonToken _finishNumberMinus(int i) throws IOException {
        return _finishNumberPlusMinus(i, true);
    }

    protected JsonToken _finishNumberPlus(int i) throws IOException {
        return _finishNumberPlusMinus(i, false);
    }

    protected JsonToken _finishNumberPlusMinus(int i, boolean z) throws IOException {
        if (i <= 48) {
            if (i == 48) {
                if (z) {
                    return _finishNumberLeadingNegZeroes();
                }
                if (!isEnabled(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature())) {
                    _reportUnexpectedNumberChar(43, "JSON spec does not allow numbers to have plus signs: enable `JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS` to allow");
                }
                return _finishNumberLeadingPosZeroes();
            }
            if (i == 46 && isEnabled(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS.mappedFeature())) {
                if (z) {
                    this._inputPtr--;
                    return _finishNumberLeadingNegZeroes();
                }
                if (!isEnabled(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature())) {
                    _reportUnexpectedNumberChar(43, "JSON spec does not allow numbers to have plus signs: enable `JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS` to allow");
                }
                this._inputPtr--;
                return _finishNumberLeadingPosZeroes();
            }
            _reportUnexpectedNumberChar(i, z ? "expected digit (0-9) to follow minus sign, for valid numeric value" : "expected digit (0-9) for valid numeric value");
        } else if (i > 57) {
            if (i == 73) {
                return _finishNonStdToken(z ? 3 : 2, 2);
            }
            _reportUnexpectedNumberChar(i, z ? "expected digit (0-9) to follow minus sign, for valid numeric value" : "expected digit (0-9) for valid numeric value");
        }
        if (!z && !isEnabled(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature())) {
            _reportUnexpectedNumberChar(43, "JSON spec does not allow numbers to have plus signs: enable `JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS` to allow");
        }
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        cArrEmptyAndGetCurrentSegment[0] = z ? '-' : '+';
        cArrEmptyAndGetCurrentSegment[1] = (char) i;
        this._intLength = 1;
        return _finishNumberIntegralPart(cArrEmptyAndGetCurrentSegment, 2);
    }

    protected JsonToken _finishNumberLeadingZeroes() throws IOException {
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer < 48) {
                if (nextUnsignedByteFromBuffer == 46) {
                    char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment[0] = '0';
                    this._intLength = 1;
                    return _startFloat(cArrEmptyAndGetCurrentSegment, 1, nextUnsignedByteFromBuffer);
                }
            } else if (nextUnsignedByteFromBuffer > 57) {
                int i = nextUnsignedByteFromBuffer | 32;
                if (i == 101) {
                    char[] cArrEmptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment2[0] = '0';
                    this._intLength = 1;
                    return _startFloat(cArrEmptyAndGetCurrentSegment2, 1, nextUnsignedByteFromBuffer);
                }
                if (i != 125) {
                    _reportUnexpectedNumberChar(nextUnsignedByteFromBuffer, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                }
            } else {
                if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
                    reportInvalidNumber("Leading zeroes not allowed");
                }
                if (nextUnsignedByteFromBuffer != 48) {
                    char[] cArrEmptyAndGetCurrentSegment3 = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment3[0] = (char) nextUnsignedByteFromBuffer;
                    this._intLength = 1;
                    return _finishNumberIntegralPart(cArrEmptyAndGetCurrentSegment3, 1);
                }
            }
            this._inputPtr--;
            return _valueCompleteInt(0, "0");
        }
        this._minorState = 24;
        return _updateTokenToNA();
    }

    protected JsonToken _finishNumberLeadingNegZeroes() throws IOException {
        return _finishNumberLeadingPosNegZeroes(true);
    }

    protected JsonToken _finishNumberLeadingPosZeroes() throws IOException {
        return _finishNumberLeadingPosNegZeroes(false);
    }

    protected JsonToken _finishNumberLeadingPosNegZeroes(boolean z) throws IOException {
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer < 48) {
                if (nextUnsignedByteFromBuffer == 46) {
                    char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment[0] = z ? '-' : '+';
                    cArrEmptyAndGetCurrentSegment[1] = '0';
                    this._intLength = 1;
                    return _startFloat(cArrEmptyAndGetCurrentSegment, 2, nextUnsignedByteFromBuffer);
                }
            } else if (nextUnsignedByteFromBuffer > 57) {
                int i = nextUnsignedByteFromBuffer | 32;
                if (i == 101) {
                    char[] cArrEmptyAndGetCurrentSegment2 = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment2[0] = z ? '-' : '+';
                    cArrEmptyAndGetCurrentSegment2[1] = '0';
                    this._intLength = 1;
                    return _startFloat(cArrEmptyAndGetCurrentSegment2, 2, nextUnsignedByteFromBuffer);
                }
                if (i != 125) {
                    _reportUnexpectedNumberChar(nextUnsignedByteFromBuffer, "expected digit (0-9), decimal point (.) or exponent indicator (e/E) to follow '0'");
                }
            } else {
                if ((this._features & FEAT_MASK_LEADING_ZEROS) == 0) {
                    reportInvalidNumber("Leading zeroes not allowed");
                }
                if (nextUnsignedByteFromBuffer != 48) {
                    char[] cArrEmptyAndGetCurrentSegment3 = this._textBuffer.emptyAndGetCurrentSegment();
                    cArrEmptyAndGetCurrentSegment3[0] = z ? '-' : '+';
                    cArrEmptyAndGetCurrentSegment3[1] = (char) nextUnsignedByteFromBuffer;
                    this._intLength = 1;
                    return _finishNumberIntegralPart(cArrEmptyAndGetCurrentSegment3, 2);
                }
            }
            this._inputPtr--;
            return _valueCompleteInt(0, "0");
        }
        this._minorState = z ? 25 : 24;
        return _updateTokenToNA();
    }

    protected JsonToken _finishNumberIntegralPart(char[] cArr, int i) throws IOException {
        int i2 = this._numberNegative ? -1 : 0;
        while (this._inputPtr < this._inputEnd) {
            int byteFromBuffer = getByteFromBuffer(this._inputPtr) & UByte.MAX_VALUE;
            if (byteFromBuffer < 48) {
                if (byteFromBuffer == 46) {
                    this._intLength = i2 + i;
                    this._inputPtr++;
                    return _startFloat(cArr, i, byteFromBuffer);
                }
            } else if (byteFromBuffer <= 57) {
                this._inputPtr++;
                if (i >= cArr.length) {
                    cArr = this._textBuffer.expandCurrentSegment();
                }
                cArr[i] = (char) byteFromBuffer;
                i++;
            } else if ((byteFromBuffer | 32) == 101) {
                this._intLength = i2 + i;
                this._inputPtr++;
                return _startFloat(cArr, i, byteFromBuffer);
            }
            this._intLength = i2 + i;
            this._textBuffer.setCurrentLength(i);
            return _valueComplete(JsonToken.VALUE_NUMBER_INT);
        }
        this._minorState = 26;
        this._textBuffer.setCurrentLength(i);
        return _updateTokenToNA();
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00f5  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x00ec -> B:40:0x009c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.core.JsonToken _startFloat(char[] r7, int r8, int r9) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase._startFloat(char[], int, int):com.fasterxml.jackson.core.JsonToken");
    }

    protected JsonToken _finishFloatFraction() throws IOException {
        int i = this._fractLength;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        byte nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
        boolean z = true;
        while (z) {
            if (nextSignedByteFromBuffer >= 48 && nextSignedByteFromBuffer <= 57) {
                i++;
                if (currentSegmentSize >= bufferWithoutReset.length) {
                    bufferWithoutReset = this._textBuffer.expandCurrentSegment();
                }
                int i2 = currentSegmentSize + 1;
                bufferWithoutReset[currentSegmentSize] = (char) nextSignedByteFromBuffer;
                if (this._inputPtr >= this._inputEnd) {
                    this._textBuffer.setCurrentLength(i2);
                    this._fractLength = i;
                    return JsonToken.NOT_AVAILABLE;
                }
                nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
                currentSegmentSize = i2;
            } else if ((nextSignedByteFromBuffer | 34) == 102) {
                _reportUnexpectedNumberChar(nextSignedByteFromBuffer, "JSON does not support parsing numbers that have 'f' or 'd' suffixes");
            } else if (nextSignedByteFromBuffer == 46) {
                _reportUnexpectedNumberChar(nextSignedByteFromBuffer, "Cannot parse number with more than one decimal point");
            } else {
                z = false;
            }
        }
        if (i == 0 && !isEnabled(JsonReadFeature.ALLOW_TRAILING_DECIMAL_POINT_FOR_NUMBERS.mappedFeature())) {
            _reportUnexpectedNumberChar(nextSignedByteFromBuffer, "Decimal point not followed by a digit");
        }
        this._fractLength = i;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        if ((nextSignedByteFromBuffer | 32) == 101) {
            this._textBuffer.append((char) nextSignedByteFromBuffer);
            this._expLength = 0;
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 31;
                return JsonToken.NOT_AVAILABLE;
            }
            this._minorState = 32;
            return _finishFloatExponent(true, getNextUnsignedByteFromBuffer());
        }
        this._inputPtr--;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        this._expLength = 0;
        return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    protected JsonToken _finishFloatExponent(boolean z, int i) throws IOException {
        if (z) {
            this._minorState = 32;
            if (i == 45 || i == 43) {
                this._textBuffer.append((char) i);
                if (this._inputPtr >= this._inputEnd) {
                    this._minorState = 32;
                    this._expLength = 0;
                    return JsonToken.NOT_AVAILABLE;
                }
                i = getNextSignedByteFromBuffer();
            }
        }
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int i2 = this._expLength;
        while (i >= 48 && i <= 57) {
            i2++;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.expandCurrentSegment();
            }
            int i3 = currentSegmentSize + 1;
            bufferWithoutReset[currentSegmentSize] = (char) i;
            if (this._inputPtr >= this._inputEnd) {
                this._textBuffer.setCurrentLength(i3);
                this._expLength = i2;
                return JsonToken.NOT_AVAILABLE;
            }
            i = getNextSignedByteFromBuffer();
            currentSegmentSize = i3;
        }
        int i4 = i & 255;
        if (i2 == 0) {
            _reportUnexpectedNumberChar(i4, "Exponent indicator not followed by a digit");
        }
        this._inputPtr--;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        this._expLength = i2;
        return _valueComplete(JsonToken.VALUE_NUMBER_FLOAT);
    }

    private final String _fastParseName() throws IOException {
        int[] iArr = _icLatin1;
        int i = this._inputPtr;
        int i2 = i + 1;
        int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer] != 0) {
            if (byteFromBuffer != 34) {
                return null;
            }
            this._inputPtr = i2;
            return "";
        }
        int i3 = i + 2;
        int byteFromBuffer2 = getByteFromBuffer(i2) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer2] != 0) {
            if (byteFromBuffer2 != 34) {
                return null;
            }
            this._inputPtr = i3;
            return _findName(byteFromBuffer, 1);
        }
        int i4 = byteFromBuffer2 | (byteFromBuffer << 8);
        int i5 = i + 3;
        int byteFromBuffer3 = getByteFromBuffer(i3) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer3] != 0) {
            if (byteFromBuffer3 != 34) {
                return null;
            }
            this._inputPtr = i5;
            return _findName(i4, 2);
        }
        int i6 = (i4 << 8) | byteFromBuffer3;
        int i7 = i + 4;
        int byteFromBuffer4 = getByteFromBuffer(i5) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer4] != 0) {
            if (byteFromBuffer4 != 34) {
                return null;
            }
            this._inputPtr = i7;
            return _findName(i6, 3);
        }
        int i8 = (i6 << 8) | byteFromBuffer4;
        int i9 = i + 5;
        int byteFromBuffer5 = getByteFromBuffer(i7) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer5] == 0) {
            this._quad1 = i8;
            return _parseMediumName(i9, byteFromBuffer5);
        }
        if (byteFromBuffer5 != 34) {
            return null;
        }
        this._inputPtr = i9;
        return _findName(i8, 4);
    }

    private final String _parseMediumName(int i, int i2) throws IOException {
        int[] iArr = _icLatin1;
        int i3 = i + 1;
        int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer] != 0) {
            if (byteFromBuffer != 34) {
                return null;
            }
            this._inputPtr = i3;
            return _findName(this._quad1, i2, 1);
        }
        int i4 = (i2 << 8) | byteFromBuffer;
        int i5 = i + 2;
        int byteFromBuffer2 = getByteFromBuffer(i3) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer2] != 0) {
            if (byteFromBuffer2 != 34) {
                return null;
            }
            this._inputPtr = i5;
            return _findName(this._quad1, i4, 2);
        }
        int i6 = (i4 << 8) | byteFromBuffer2;
        int i7 = i + 3;
        int byteFromBuffer3 = getByteFromBuffer(i5) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer3] != 0) {
            if (byteFromBuffer3 != 34) {
                return null;
            }
            this._inputPtr = i7;
            return _findName(this._quad1, i6, 3);
        }
        int i8 = (i6 << 8) | byteFromBuffer3;
        int i9 = i + 4;
        int byteFromBuffer4 = getByteFromBuffer(i7) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer4] == 0) {
            return _parseMediumName2(i9, byteFromBuffer4, i8);
        }
        if (byteFromBuffer4 != 34) {
            return null;
        }
        this._inputPtr = i9;
        return _findName(this._quad1, i8, 4);
    }

    private final String _parseMediumName2(int i, int i2, int i3) throws IOException {
        int[] iArr = _icLatin1;
        int i4 = i + 1;
        int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer] != 0) {
            if (byteFromBuffer != 34) {
                return null;
            }
            this._inputPtr = i4;
            return _findName(this._quad1, i3, i2, 1);
        }
        int i5 = (i2 << 8) | byteFromBuffer;
        int i6 = i + 2;
        int byteFromBuffer2 = getByteFromBuffer(i4) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer2] != 0) {
            if (byteFromBuffer2 != 34) {
                return null;
            }
            this._inputPtr = i6;
            return _findName(this._quad1, i3, i5, 2);
        }
        int i7 = (i5 << 8) | byteFromBuffer2;
        int i8 = i + 3;
        int byteFromBuffer3 = getByteFromBuffer(i6) & UByte.MAX_VALUE;
        if (iArr[byteFromBuffer3] != 0) {
            if (byteFromBuffer3 != 34) {
                return null;
            }
            this._inputPtr = i8;
            return _findName(this._quad1, i3, i7, 3);
        }
        int i9 = (i7 << 8) | byteFromBuffer3;
        int i10 = i + 4;
        if ((getByteFromBuffer(i8) & UByte.MAX_VALUE) != 34) {
            return null;
        }
        this._inputPtr = i10;
        return _findName(this._quad1, i3, i9, 4);
    }

    private final JsonToken _parseEscapedName(int i, int i2, int i3) throws IOException {
        int i4;
        int[] iArr_growNameDecodeBuffer = this._quadBuffer;
        int[] iArr = _icLatin1;
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (iArr[nextUnsignedByteFromBuffer] == 0) {
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | nextUnsignedByteFromBuffer;
                } else {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        int[] iArr_growNameDecodeBuffer2 = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer2;
                        iArr_growNameDecodeBuffer = iArr_growNameDecodeBuffer2;
                    }
                    i4 = i + 1;
                    iArr_growNameDecodeBuffer[i] = i2;
                    i = i4;
                    i2 = nextUnsignedByteFromBuffer;
                    i3 = 1;
                }
            } else if (nextUnsignedByteFromBuffer != 34) {
                if (nextUnsignedByteFromBuffer != 92) {
                    _throwUnquotedSpace(nextUnsignedByteFromBuffer, "name");
                } else {
                    nextUnsignedByteFromBuffer = _decodeCharEscape();
                    if (nextUnsignedByteFromBuffer < 0) {
                        this._minorState = 8;
                        this._minorStateAfterSplit = 7;
                        this._quadLength = i;
                        this._pending32 = i2;
                        this._pendingBytes = i3;
                        return _updateTokenToNA();
                    }
                }
                if (i >= iArr_growNameDecodeBuffer.length) {
                    iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                    this._quadBuffer = iArr_growNameDecodeBuffer;
                }
                if (nextUnsignedByteFromBuffer > 127) {
                    int i5 = 0;
                    if (i3 >= 4) {
                        iArr_growNameDecodeBuffer[i] = i2;
                        i++;
                        i2 = 0;
                        i3 = 0;
                    }
                    if (nextUnsignedByteFromBuffer < 2048) {
                        i2 = (i2 << 8) | (nextUnsignedByteFromBuffer >> 6) | Opcodes.CHECKCAST;
                        i3++;
                    } else {
                        int i6 = (i2 << 8) | (nextUnsignedByteFromBuffer >> 12) | 224;
                        int i7 = i3 + 1;
                        if (i7 >= 4) {
                            iArr_growNameDecodeBuffer[i] = i6;
                            i++;
                            i7 = 0;
                        } else {
                            i5 = i6;
                        }
                        i2 = (i5 << 8) | ((nextUnsignedByteFromBuffer >> 6) & 63) | 128;
                        i3 = i7 + 1;
                    }
                    nextUnsignedByteFromBuffer = (nextUnsignedByteFromBuffer & 63) | 128;
                }
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | nextUnsignedByteFromBuffer;
                } else {
                    i4 = i + 1;
                    iArr_growNameDecodeBuffer[i] = i2;
                    i = i4;
                    i2 = nextUnsignedByteFromBuffer;
                    i3 = 1;
                }
            } else {
                if (i3 > 0) {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer;
                    }
                    iArr_growNameDecodeBuffer[i] = _padLastQuad(i2, i3);
                    i++;
                } else if (i == 0) {
                    return _fieldComplete("");
                }
                String strFindName = this._symbols.findName(iArr_growNameDecodeBuffer, i);
                if (strFindName == null) {
                    strFindName = _addName(iArr_growNameDecodeBuffer, i, i3);
                }
                return _fieldComplete(strFindName);
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 7;
        return _updateTokenToNA();
    }

    private JsonToken _handleOddName(int i) throws IOException {
        if (i != 35) {
            if (i != 39) {
                if (i == 47) {
                    return _startSlashComment(4);
                }
                if (i == 93) {
                    return _closeArrayScope();
                }
            } else if ((this._features & FEAT_MASK_ALLOW_SINGLE_QUOTES) != 0) {
                return _finishAposName(0, 0, 0);
            }
        } else if ((this._features & FEAT_MASK_ALLOW_YAML_COMMENTS) != 0) {
            return _finishHashComment(4);
        }
        if ((this._features & FEAT_MASK_ALLOW_UNQUOTED_NAMES) == 0) {
            _reportUnexpectedChar((char) i, "was expecting double-quote to start field name");
        }
        if (CharTypes.getInputCodeUtf8JsNames()[i] != 0) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        return _finishUnquotedName(0, i, 1);
    }

    private JsonToken _finishUnquotedName(int i, int i2, int i3) throws IOException {
        int[] iArr_growNameDecodeBuffer = this._quadBuffer;
        int[] inputCodeUtf8JsNames = CharTypes.getInputCodeUtf8JsNames();
        while (this._inputPtr < this._inputEnd) {
            int byteFromBuffer = getByteFromBuffer(this._inputPtr) & UByte.MAX_VALUE;
            if (inputCodeUtf8JsNames[byteFromBuffer] == 0) {
                this._inputPtr++;
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | byteFromBuffer;
                } else {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer;
                    }
                    iArr_growNameDecodeBuffer[i] = i2;
                    i++;
                    i2 = byteFromBuffer;
                    i3 = 1;
                }
            } else {
                if (i3 > 0) {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer;
                    }
                    iArr_growNameDecodeBuffer[i] = i2;
                    i++;
                }
                String strFindName = this._symbols.findName(iArr_growNameDecodeBuffer, i);
                if (strFindName == null) {
                    strFindName = _addName(iArr_growNameDecodeBuffer, i, i3);
                }
                return _fieldComplete(strFindName);
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 10;
        return _updateTokenToNA();
    }

    private JsonToken _finishAposName(int i, int i2, int i3) throws IOException {
        int[] iArr_growNameDecodeBuffer = this._quadBuffer;
        int[] iArr = _icLatin1;
        while (this._inputPtr < this._inputEnd) {
            int nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
            if (nextUnsignedByteFromBuffer != 39) {
                if (nextUnsignedByteFromBuffer != 34 && iArr[nextUnsignedByteFromBuffer] != 0) {
                    if (nextUnsignedByteFromBuffer != 92) {
                        _throwUnquotedSpace(nextUnsignedByteFromBuffer, "name");
                    } else {
                        nextUnsignedByteFromBuffer = _decodeCharEscape();
                        if (nextUnsignedByteFromBuffer < 0) {
                            this._minorState = 8;
                            this._minorStateAfterSplit = 9;
                            this._quadLength = i;
                            this._pending32 = i2;
                            this._pendingBytes = i3;
                            return _updateTokenToNA();
                        }
                    }
                    if (nextUnsignedByteFromBuffer > 127) {
                        int i4 = 0;
                        if (i3 >= 4) {
                            if (i >= iArr_growNameDecodeBuffer.length) {
                                iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                                this._quadBuffer = iArr_growNameDecodeBuffer;
                            }
                            iArr_growNameDecodeBuffer[i] = i2;
                            i++;
                            i2 = 0;
                            i3 = 0;
                        }
                        if (nextUnsignedByteFromBuffer < 2048) {
                            i2 = (i2 << 8) | (nextUnsignedByteFromBuffer >> 6) | Opcodes.CHECKCAST;
                            i3++;
                        } else {
                            int i5 = (i2 << 8) | (nextUnsignedByteFromBuffer >> 12) | 224;
                            int i6 = i3 + 1;
                            if (i6 >= 4) {
                                if (i >= iArr_growNameDecodeBuffer.length) {
                                    int[] iArr_growNameDecodeBuffer2 = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                                    this._quadBuffer = iArr_growNameDecodeBuffer2;
                                    iArr_growNameDecodeBuffer = iArr_growNameDecodeBuffer2;
                                }
                                iArr_growNameDecodeBuffer[i] = i5;
                                i++;
                                i6 = 0;
                            } else {
                                i4 = i5;
                            }
                            i2 = (i4 << 8) | ((nextUnsignedByteFromBuffer >> 6) & 63) | 128;
                            i3 = i6 + 1;
                        }
                        nextUnsignedByteFromBuffer = (nextUnsignedByteFromBuffer & 63) | 128;
                    }
                }
                if (i3 < 4) {
                    i3++;
                    i2 = (i2 << 8) | nextUnsignedByteFromBuffer;
                } else {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer;
                    }
                    iArr_growNameDecodeBuffer[i] = i2;
                    i++;
                    i2 = nextUnsignedByteFromBuffer;
                    i3 = 1;
                }
            } else {
                if (i3 > 0) {
                    if (i >= iArr_growNameDecodeBuffer.length) {
                        iArr_growNameDecodeBuffer = _growNameDecodeBuffer(iArr_growNameDecodeBuffer, iArr_growNameDecodeBuffer.length);
                        this._quadBuffer = iArr_growNameDecodeBuffer;
                    }
                    iArr_growNameDecodeBuffer[i] = _padLastQuad(i2, i3);
                    i++;
                } else if (i == 0) {
                    return _fieldComplete("");
                }
                String strFindName = this._symbols.findName(iArr_growNameDecodeBuffer, i);
                if (strFindName == null) {
                    strFindName = _addName(iArr_growNameDecodeBuffer, i, i3);
                }
                return _fieldComplete(strFindName);
            }
        }
        this._quadLength = i;
        this._pending32 = i2;
        this._pendingBytes = i3;
        this._minorState = 9;
        return _updateTokenToNA();
    }

    protected final JsonToken _finishFieldWithEscape() throws IOException {
        int i;
        int i2;
        int i_decodeSplitEscaped = _decodeSplitEscaped(this._quoted32, this._quotedDigits);
        if (i_decodeSplitEscaped < 0) {
            this._minorState = 8;
            return JsonToken.NOT_AVAILABLE;
        }
        if (this._quadLength >= this._quadBuffer.length) {
            this._quadBuffer = _growNameDecodeBuffer(this._quadBuffer, 32);
        }
        int i3 = this._pending32;
        int i4 = this._pendingBytes;
        int i5 = 1;
        if (i_decodeSplitEscaped > 127) {
            int i6 = 0;
            if (i4 >= 4) {
                int[] iArr = this._quadBuffer;
                int i7 = this._quadLength;
                this._quadLength = i7 + 1;
                iArr[i7] = i3;
                i3 = 0;
                i4 = 0;
            }
            if (i_decodeSplitEscaped < 2048) {
                i = i3 << 8;
                i2 = (i_decodeSplitEscaped >> 6) | Opcodes.CHECKCAST;
            } else {
                int i8 = (i3 << 8) | (i_decodeSplitEscaped >> 12) | 224;
                i4++;
                if (i4 >= 4) {
                    int[] iArr2 = this._quadBuffer;
                    int i9 = this._quadLength;
                    this._quadLength = i9 + 1;
                    iArr2[i9] = i8;
                    i4 = 0;
                } else {
                    i6 = i8;
                }
                i = i6 << 8;
                i2 = ((i_decodeSplitEscaped >> 6) & 63) | 128;
            }
            i3 = i | i2;
            i4++;
            i_decodeSplitEscaped = (i_decodeSplitEscaped & 63) | 128;
        }
        if (i4 < 4) {
            i5 = 1 + i4;
            i_decodeSplitEscaped |= i3 << 8;
        } else {
            int[] iArr3 = this._quadBuffer;
            int i10 = this._quadLength;
            this._quadLength = i10 + 1;
            iArr3[i10] = i3;
        }
        if (this._minorStateAfterSplit == 9) {
            return _finishAposName(this._quadLength, i_decodeSplitEscaped, i5);
        }
        return _parseEscapedName(this._quadLength, i_decodeSplitEscaped, i5);
    }

    private int _decodeSplitEscaped(int i, int i2) throws IOException {
        if (this._inputPtr >= this._inputEnd) {
            this._quoted32 = i;
            this._quotedDigits = i2;
            return -1;
        }
        byte nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
        if (i2 == -1) {
            if (nextSignedByteFromBuffer == 34 || nextSignedByteFromBuffer == 47 || nextSignedByteFromBuffer == 92) {
                return nextSignedByteFromBuffer;
            }
            if (nextSignedByteFromBuffer == 98) {
                return 8;
            }
            if (nextSignedByteFromBuffer == 102) {
                return 12;
            }
            if (nextSignedByteFromBuffer == 110) {
                return 10;
            }
            if (nextSignedByteFromBuffer == 114) {
                return 13;
            }
            if (nextSignedByteFromBuffer == 116) {
                return 9;
            }
            if (nextSignedByteFromBuffer != 117) {
                return _handleUnrecognizedCharacterEscape((char) nextSignedByteFromBuffer);
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = 0;
                this._quoted32 = 0;
                return -1;
            }
            nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
            i2 = 0;
        }
        int nextUnsignedByteFromBuffer = nextSignedByteFromBuffer & UByte.MAX_VALUE;
        while (true) {
            int iCharToHex = CharTypes.charToHex(nextUnsignedByteFromBuffer);
            if (iCharToHex < 0) {
                _reportUnexpectedChar(nextUnsignedByteFromBuffer & 255, "expected a hex-digit for character escape sequence");
            }
            i = (i << 4) | iCharToHex;
            i2++;
            if (i2 == 4) {
                return i;
            }
            if (this._inputPtr >= this._inputEnd) {
                this._quotedDigits = i2;
                this._quoted32 = i;
                return -1;
            }
            nextUnsignedByteFromBuffer = getNextUnsignedByteFromBuffer();
        }
    }

    protected JsonToken _startString() throws IOException {
        int i = this._inputPtr;
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = _icUTF8;
        int iMin = Math.min(this._inputEnd, cArrEmptyAndGetCurrentSegment.length + i);
        int i2 = 0;
        while (true) {
            if (i >= iMin) {
                break;
            }
            int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
            if (iArr[byteFromBuffer] == 0) {
                i++;
                cArrEmptyAndGetCurrentSegment[i2] = (char) byteFromBuffer;
                i2++;
            } else if (byteFromBuffer == 34) {
                this._inputPtr = i + 1;
                this._textBuffer.setCurrentLength(i2);
                return _valueComplete(JsonToken.VALUE_STRING);
            }
        }
        this._textBuffer.setCurrentLength(i2);
        this._inputPtr = i;
        return _finishRegularString();
    }

    private final JsonToken _finishRegularString() throws IOException {
        int[] iArr = _icUTF8;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int i = this._inputPtr;
        int i2 = this._inputEnd - 5;
        while (i < this._inputEnd) {
            int i3 = 0;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int iMin = Math.min(this._inputEnd, InternalJacksonUtil.addOverflowSafe(i, bufferWithoutReset.length - currentSegmentSize));
            while (true) {
                if (i < iMin) {
                    int i4 = i + 1;
                    int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
                    int i5 = iArr[byteFromBuffer];
                    if (i5 == 0) {
                        bufferWithoutReset[currentSegmentSize] = (char) byteFromBuffer;
                        currentSegmentSize++;
                        i = i4;
                    } else {
                        if (byteFromBuffer == 34) {
                            this._inputPtr = i4;
                            this._textBuffer.setCurrentLength(currentSegmentSize);
                            return _valueComplete(JsonToken.VALUE_STRING);
                        }
                        if (i4 >= i2) {
                            this._inputPtr = i4;
                            this._textBuffer.setCurrentLength(currentSegmentSize);
                            if (!_decodeSplitMultiByte(byteFromBuffer, iArr[byteFromBuffer], i4 < this._inputEnd)) {
                                this._minorStateAfterSplit = 40;
                                return _updateTokenToNA();
                            }
                            bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
                            currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                            i = this._inputPtr;
                        } else {
                            if (i5 == 1) {
                                this._inputPtr = i4;
                                byteFromBuffer = _decodeFastCharEscape();
                                i = this._inputPtr;
                            } else if (i5 == 2) {
                                i += 2;
                                byteFromBuffer = _decodeUTF8_2(byteFromBuffer, getByteFromBuffer(i4));
                            } else if (i5 == 3) {
                                int i6 = i + 2;
                                i += 3;
                                byteFromBuffer = _decodeUTF8_3(byteFromBuffer, getByteFromBuffer(i4), getByteFromBuffer(i6));
                            } else if (i5 != 4) {
                                if (byteFromBuffer < 32) {
                                    _throwUnquotedSpace(byteFromBuffer, "string value");
                                } else {
                                    _reportInvalidChar(byteFromBuffer);
                                }
                                i = i4;
                            } else {
                                byte byteFromBuffer2 = getByteFromBuffer(i4);
                                int i7 = i + 3;
                                byte byteFromBuffer3 = getByteFromBuffer(i + 2);
                                i += 4;
                                int i_decodeUTF8_4 = _decodeUTF8_4(byteFromBuffer, byteFromBuffer2, byteFromBuffer3, getByteFromBuffer(i7));
                                int i8 = currentSegmentSize + 1;
                                bufferWithoutReset[currentSegmentSize] = (char) ((i_decodeUTF8_4 >> 10) | 55296);
                                if (i8 >= bufferWithoutReset.length) {
                                    bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                                    currentSegmentSize = 0;
                                } else {
                                    currentSegmentSize = i8;
                                }
                                byteFromBuffer = (i_decodeUTF8_4 & FastDoubleMath.DOUBLE_EXPONENT_BIAS) | 56320;
                            }
                            if (currentSegmentSize >= bufferWithoutReset.length) {
                                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                            } else {
                                i3 = currentSegmentSize;
                            }
                            currentSegmentSize = i3 + 1;
                            bufferWithoutReset[i3] = (char) byteFromBuffer;
                        }
                    }
                }
            }
        }
        this._inputPtr = i;
        this._minorState = 40;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        return _updateTokenToNA();
    }

    protected JsonToken _startAposString() throws IOException {
        int i = this._inputPtr;
        char[] cArrEmptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int[] iArr = _icUTF8;
        int iMin = Math.min(this._inputEnd, cArrEmptyAndGetCurrentSegment.length + i);
        int i2 = 0;
        while (i < iMin) {
            int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
            if (byteFromBuffer == 39) {
                this._inputPtr = i + 1;
                this._textBuffer.setCurrentLength(i2);
                return _valueComplete(JsonToken.VALUE_STRING);
            }
            if (iArr[byteFromBuffer] != 0) {
                break;
            }
            i++;
            cArrEmptyAndGetCurrentSegment[i2] = (char) byteFromBuffer;
            i2++;
        }
        this._textBuffer.setCurrentLength(i2);
        this._inputPtr = i;
        return _finishAposString();
    }

    private final JsonToken _finishAposString() throws IOException {
        int[] iArr = _icUTF8;
        char[] bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int i = this._inputPtr;
        int i2 = this._inputEnd - 5;
        while (i < this._inputEnd) {
            int i3 = 0;
            if (currentSegmentSize >= bufferWithoutReset.length) {
                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int iMin = Math.min(this._inputEnd, InternalJacksonUtil.addOverflowSafe(i, bufferWithoutReset.length - currentSegmentSize));
            while (true) {
                if (i < iMin) {
                    int i4 = i + 1;
                    int byteFromBuffer = getByteFromBuffer(i) & UByte.MAX_VALUE;
                    int i5 = iArr[byteFromBuffer];
                    if (i5 == 0 || byteFromBuffer == 34) {
                        if (byteFromBuffer == 39) {
                            this._inputPtr = i4;
                            this._textBuffer.setCurrentLength(currentSegmentSize);
                            return _valueComplete(JsonToken.VALUE_STRING);
                        }
                        bufferWithoutReset[currentSegmentSize] = (char) byteFromBuffer;
                        currentSegmentSize++;
                        i = i4;
                    } else if (i4 >= i2) {
                        this._inputPtr = i4;
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        if (!_decodeSplitMultiByte(byteFromBuffer, iArr[byteFromBuffer], i4 < this._inputEnd)) {
                            this._minorStateAfterSplit = 45;
                            return _updateTokenToNA();
                        }
                        bufferWithoutReset = this._textBuffer.getBufferWithoutReset();
                        currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
                        i = this._inputPtr;
                    } else {
                        if (i5 == 1) {
                            this._inputPtr = i4;
                            byteFromBuffer = _decodeFastCharEscape();
                            i = this._inputPtr;
                        } else if (i5 == 2) {
                            i += 2;
                            byteFromBuffer = _decodeUTF8_2(byteFromBuffer, getByteFromBuffer(i4));
                        } else if (i5 == 3) {
                            int i6 = i + 2;
                            i += 3;
                            byteFromBuffer = _decodeUTF8_3(byteFromBuffer, getByteFromBuffer(i4), getByteFromBuffer(i6));
                        } else if (i5 != 4) {
                            if (byteFromBuffer < 32) {
                                _throwUnquotedSpace(byteFromBuffer, "string value");
                            } else {
                                _reportInvalidChar(byteFromBuffer);
                            }
                            i = i4;
                        } else {
                            byte byteFromBuffer2 = getByteFromBuffer(i4);
                            int i7 = i + 3;
                            byte byteFromBuffer3 = getByteFromBuffer(i + 2);
                            i += 4;
                            int i_decodeUTF8_4 = _decodeUTF8_4(byteFromBuffer, byteFromBuffer2, byteFromBuffer3, getByteFromBuffer(i7));
                            int i8 = currentSegmentSize + 1;
                            bufferWithoutReset[currentSegmentSize] = (char) ((i_decodeUTF8_4 >> 10) | 55296);
                            if (i8 >= bufferWithoutReset.length) {
                                bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                                currentSegmentSize = 0;
                            } else {
                                currentSegmentSize = i8;
                            }
                            byteFromBuffer = (i_decodeUTF8_4 & FastDoubleMath.DOUBLE_EXPONENT_BIAS) | 56320;
                        }
                        if (currentSegmentSize >= bufferWithoutReset.length) {
                            bufferWithoutReset = this._textBuffer.finishCurrentSegment();
                        } else {
                            i3 = currentSegmentSize;
                        }
                        currentSegmentSize = i3 + 1;
                        bufferWithoutReset[i3] = (char) byteFromBuffer;
                    }
                }
            }
        }
        this._inputPtr = i;
        this._minorState = 45;
        this._textBuffer.setCurrentLength(currentSegmentSize);
        return _updateTokenToNA();
    }

    private final boolean _decodeSplitMultiByte(int i, int i2, boolean z) throws IOException {
        if (i2 == 1) {
            int i_decodeSplitEscaped = _decodeSplitEscaped(0, -1);
            if (i_decodeSplitEscaped < 0) {
                this._minorState = 41;
                return false;
            }
            this._textBuffer.append((char) i_decodeSplitEscaped);
            return true;
        }
        if (i2 == 2) {
            if (z) {
                this._textBuffer.append((char) _decodeUTF8_2(i, getNextSignedByteFromBuffer()));
                return true;
            }
            this._minorState = 42;
            this._pending32 = i;
            return false;
        }
        if (i2 == 3) {
            int i3 = i & 15;
            if (z) {
                return _decodeSplitUTF8_3(i3, 1, getNextSignedByteFromBuffer());
            }
            this._minorState = 43;
            this._pending32 = i3;
            this._pendingBytes = 1;
            return false;
        }
        if (i2 != 4) {
            if (i < 32) {
                _throwUnquotedSpace(i, "string value");
            } else {
                _reportInvalidChar(i);
            }
            this._textBuffer.append((char) i);
            return true;
        }
        int i4 = i & 7;
        if (z) {
            return _decodeSplitUTF8_4(i4, 1, getNextSignedByteFromBuffer());
        }
        this._pending32 = i4;
        this._pendingBytes = 1;
        this._minorState = 44;
        return false;
    }

    private final boolean _decodeSplitUTF8_3(int i, int i2, int i3) throws IOException {
        if (i2 == 1) {
            if ((i3 & Opcodes.CHECKCAST) != 128) {
                _reportInvalidOther(i3 & 255, this._inputPtr);
            }
            i = (i << 6) | (i3 & 63);
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 43;
                this._pending32 = i;
                this._pendingBytes = 2;
                return false;
            }
            i3 = getNextSignedByteFromBuffer();
        }
        if ((i3 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        this._textBuffer.append((char) ((i << 6) | (i3 & 63)));
        return true;
    }

    private final boolean _decodeSplitUTF8_4(int i, int i2, int i3) throws IOException {
        if (i2 == 1) {
            if ((i3 & Opcodes.CHECKCAST) != 128) {
                _reportInvalidOther(i3 & 255, this._inputPtr);
            }
            i = (i << 6) | (i3 & 63);
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = i;
                this._pendingBytes = 2;
                return false;
            }
            i3 = getNextSignedByteFromBuffer();
            i2 = 2;
        }
        if (i2 == 2) {
            if ((i3 & Opcodes.CHECKCAST) != 128) {
                _reportInvalidOther(i3 & 255, this._inputPtr);
            }
            i = (i << 6) | (i3 & 63);
            if (this._inputPtr >= this._inputEnd) {
                this._minorState = 44;
                this._pending32 = i;
                this._pendingBytes = 3;
                return false;
            }
            i3 = getNextSignedByteFromBuffer();
        }
        if ((i3 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        int i4 = ((i << 6) | (i3 & 63)) - 65536;
        this._textBuffer.append((char) ((i4 >> 10) | 55296));
        this._textBuffer.append((char) ((i4 & FastDoubleMath.DOUBLE_EXPONENT_BIAS) | 56320));
        return true;
    }

    private final int _decodeCharEscape() throws IOException {
        if (this._inputEnd - this._inputPtr < 5) {
            return _decodeSplitEscaped(0, -1);
        }
        return _decodeFastCharEscape();
    }

    private final int _decodeFastCharEscape() throws IOException {
        int iCharToHex;
        byte nextSignedByteFromBuffer = getNextSignedByteFromBuffer();
        if (nextSignedByteFromBuffer == 34 || nextSignedByteFromBuffer == 47 || nextSignedByteFromBuffer == 92) {
            return (char) nextSignedByteFromBuffer;
        }
        if (nextSignedByteFromBuffer == 98) {
            return 8;
        }
        if (nextSignedByteFromBuffer == 102) {
            return 12;
        }
        if (nextSignedByteFromBuffer == 110) {
            return 10;
        }
        if (nextSignedByteFromBuffer == 114) {
            return 13;
        }
        if (nextSignedByteFromBuffer == 116) {
            return 9;
        }
        if (nextSignedByteFromBuffer != 117) {
            return _handleUnrecognizedCharacterEscape((char) nextSignedByteFromBuffer);
        }
        byte nextSignedByteFromBuffer2 = getNextSignedByteFromBuffer();
        int iCharToHex2 = CharTypes.charToHex(nextSignedByteFromBuffer2);
        if (iCharToHex2 >= 0 && (iCharToHex = CharTypes.charToHex((nextSignedByteFromBuffer2 = getNextSignedByteFromBuffer()))) >= 0) {
            int i = (iCharToHex2 << 4) | iCharToHex;
            byte nextSignedByteFromBuffer3 = getNextSignedByteFromBuffer();
            int iCharToHex3 = CharTypes.charToHex(nextSignedByteFromBuffer3);
            if (iCharToHex3 >= 0) {
                int i2 = (i << 4) | iCharToHex3;
                nextSignedByteFromBuffer3 = getNextSignedByteFromBuffer();
                int iCharToHex4 = CharTypes.charToHex(nextSignedByteFromBuffer3);
                if (iCharToHex4 >= 0) {
                    return (i2 << 4) | iCharToHex4;
                }
            }
            nextSignedByteFromBuffer2 = nextSignedByteFromBuffer3;
        }
        _reportUnexpectedChar(nextSignedByteFromBuffer2 & UByte.MAX_VALUE, "expected a hex-digit for character escape sequence");
        return -1;
    }

    private final int _decodeUTF8_2(int i, int i2) throws IOException {
        if ((i2 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        return ((i & 31) << 6) | (i2 & 63);
    }

    private final int _decodeUTF8_3(int i, int i2, int i3) throws IOException {
        int i4 = i & 15;
        if ((i2 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        int i5 = (i4 << 6) | (i2 & 63);
        if ((i3 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        return (i5 << 6) | (i3 & 63);
    }

    private final int _decodeUTF8_4(int i, int i2, int i3, int i4) throws IOException {
        if ((i2 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i2 & 255, this._inputPtr);
        }
        int i5 = ((i & 7) << 6) | (i2 & 63);
        if ((i3 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i3 & 255, this._inputPtr);
        }
        int i6 = (i5 << 6) | (i3 & 63);
        if ((i4 & Opcodes.CHECKCAST) != 128) {
            _reportInvalidOther(i4 & 255, this._inputPtr);
        }
        return ((i6 << 6) | (i4 & 63)) - 65536;
    }
}
