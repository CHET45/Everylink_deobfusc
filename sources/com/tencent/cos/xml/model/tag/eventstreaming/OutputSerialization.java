package com.tencent.cos.xml.model.tag.eventstreaming;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class OutputSerialization implements Serializable, Cloneable {
    private CSVOutput csv;
    private JSONOutput json;

    public OutputSerialization(CSVOutput cSVOutput) {
        this.csv = cSVOutput;
    }

    public OutputSerialization(JSONOutput jSONOutput) {
        this.json = jSONOutput;
    }

    public CSVOutput getCsv() {
        return this.csv;
    }

    public void setCsv(CSVOutput cSVOutput) {
        this.csv = cSVOutput;
    }

    public OutputSerialization withCsv(CSVOutput cSVOutput) {
        setCsv(cSVOutput);
        return this;
    }

    public JSONOutput getJson() {
        return this.json;
    }

    public void setJson(JSONOutput jSONOutput) {
        this.json = jSONOutput;
    }

    public OutputSerialization withJson(JSONOutput jSONOutput) {
        setJson(jSONOutput);
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof OutputSerialization)) {
            return false;
        }
        OutputSerialization outputSerialization = (OutputSerialization) obj;
        if ((outputSerialization.getCsv() == null) ^ (getCsv() == null)) {
            return false;
        }
        if (outputSerialization.getCsv() != null && !outputSerialization.getCsv().equals(getCsv())) {
            return false;
        }
        if ((outputSerialization.getJson() == null) ^ (getJson() == null)) {
            return false;
        }
        return outputSerialization.getJson() == null || outputSerialization.getJson().equals(getJson());
    }

    public int hashCode() {
        return (((getCsv() == null ? 0 : getCsv().hashCode()) + 31) * 31) + (getJson() != null ? getJson().hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCsv() != null) {
            sb.append("CSV: ").append(getCsv());
        }
        if (getJson() != null) {
            sb.append("JSON: ").append(getJson());
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public OutputSerialization m2698clone() {
        try {
            return (OutputSerialization) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() even though we're Cloneable!", e);
        }
    }
}
