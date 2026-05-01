package com.tencent.cos.xml.model.tag.eventstreaming;

/* JADX INFO: loaded from: classes4.dex */
public class SelectRequest {
    private String expression;
    private String expressionType;
    private InputSerialization inputSerialization;
    private OutputSerialization outputSerialization;
    private RequestProgress requestProgress;

    public SelectRequest(String str, String str2, RequestProgress requestProgress, InputSerialization inputSerialization, OutputSerialization outputSerialization) {
        this.expressionType = str;
        this.expression = str2;
        this.requestProgress = requestProgress;
        this.inputSerialization = inputSerialization;
        this.outputSerialization = outputSerialization;
    }

    public String getExpressionType() {
        return this.expressionType;
    }

    public String getExpression() {
        return this.expression;
    }

    public RequestProgress getRequestProgress() {
        return this.requestProgress;
    }

    public InputSerialization getInputSerialization() {
        return this.inputSerialization;
    }

    public OutputSerialization getOutputSerialization() {
        return this.outputSerialization;
    }
}
