package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class FunctionRightsBean implements Serializable {
    private Integer aiChatLimit;
    private Integer aiSummaryLimit;
    private Integer bilingualCountLimit;
    private Boolean imageInsert;
    private Integer imageInsertLimit;

    public Boolean getImageInsert() {
        if (this.imageInsert == null) {
            this.imageInsert = false;
        }
        return this.imageInsert;
    }

    public void setImageInsert(Boolean bool) {
        this.imageInsert = bool;
    }

    public Integer getAiSummaryLimit() {
        if (this.aiSummaryLimit == null) {
            this.aiSummaryLimit = 0;
        }
        return this.aiSummaryLimit;
    }

    public void setAiSummaryLimit(Integer num) {
        this.aiSummaryLimit = num;
    }

    public Integer getImageInsertLimit() {
        if (this.imageInsertLimit == null) {
            this.imageInsertLimit = 0;
        }
        return this.imageInsertLimit;
    }

    public void setImageInsertLimit(Integer num) {
        this.imageInsertLimit = num;
    }

    public Integer getBilingualCountLimit() {
        if (this.bilingualCountLimit == null) {
            this.bilingualCountLimit = 0;
        }
        return this.bilingualCountLimit;
    }

    public void setBilingualCountLimit(Integer num) {
        this.bilingualCountLimit = num;
    }

    public Integer getAiChatLimit() {
        if (this.aiChatLimit == null) {
            this.aiChatLimit = 0;
        }
        return this.aiChatLimit;
    }

    public void setAiChatLimit(Integer num) {
        this.aiChatLimit = num;
    }
}
