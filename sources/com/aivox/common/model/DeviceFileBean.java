package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFileBean implements Serializable {
    private List<FileList> fileList;
    private Integer total;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer num) {
        this.total = num;
    }

    public List<FileList> getFileList() {
        return this.fileList;
    }

    public void setFileList(List<FileList> list) {
        this.fileList = list;
    }

    public static class FileList implements Serializable {
        private String check;

        /* JADX INFO: renamed from: id */
        private Integer f234id;
        private boolean isLast;
        private String name;
        private Long size;

        public Long getSize() {
            return this.size;
        }

        public void setSize(Long l) {
            this.size = l;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public Integer getId() {
            return this.f234id;
        }

        public void setId(Integer num) {
            this.f234id = num;
        }

        public String getCheck() {
            return this.check;
        }

        public void setCheck(String str) {
            this.check = str;
        }

        public boolean isLast() {
            return this.isLast;
        }

        public void setLast(boolean z) {
            this.isLast = z;
        }
    }
}
