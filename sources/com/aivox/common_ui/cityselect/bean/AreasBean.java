package com.aivox.common_ui.cityselect.bean;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AreasBean {
    public List<ChildrenBeanX> children;

    /* JADX INFO: renamed from: id */
    public int f270id;
    public int is_hot;
    public String name;
    public int parent_id;

    public static class ChildrenBeanX {
        public List<ChildrenBean> children;

        /* JADX INFO: renamed from: id */
        public int f271id;
        public int is_hot;
        public String name;
        public int parent_id;

        public static class ChildrenBean {

            /* JADX INFO: renamed from: id */
            public int f272id;
            public int is_hot;
            public String name;
            public int parent_id;
        }
    }
}
