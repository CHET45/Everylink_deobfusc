package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DatasetSimpleQuery {
    public Aggregations aggregations;
    public String datasetName;
    public int maxResults;
    public String nextToken;
    public String order;
    public Query query;
    public String sort;
    public String withFields;

    public static class Aggregations {
        public String field;
        public String operation;
    }

    public static class Query {
        public String field;
        public String operation;
        public List<SubQueries> subQueries;
        public String value;
    }

    public static class SubQueries {
        public String field;
        public String operation;
        public String value;
    }
}
