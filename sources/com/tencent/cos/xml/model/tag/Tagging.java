package com.tencent.cos.xml.model.tag;

import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Tagging {
    public TagSet tagSet = new TagSet();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Tagging) {
            return this.tagSet.equals(((Tagging) obj).tagSet);
        }
        return false;
    }

    public static class TagSet {
        public List<Tag> tag = new LinkedList();

        public void addTag(Tag tag) {
            this.tag.add(tag);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof TagSet) {
                TagSet tagSet = (TagSet) obj;
                int size = this.tag.size();
                if (size == tagSet.tag.size()) {
                    int i = 0;
                    while (true) {
                        int i2 = size - 1;
                        if (size == 0) {
                            return true;
                        }
                        if (!this.tag.get(i).equals(tagSet.tag.get(i))) {
                            return false;
                        }
                        i++;
                        size = i2;
                    }
                }
            }
            return false;
        }
    }

    public static class Tag {
        public String key;
        public String value;

        public Tag(String str, String str2) {
            this.key = str;
            this.value = str2;
        }

        public Tag() {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Tag)) {
                return false;
            }
            Tag tag = (Tag) obj;
            return this.key.equals(tag.key) && this.value.equals(tag.value);
        }
    }
}
