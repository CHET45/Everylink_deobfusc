package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GoogleLocationResult {
    String formatted_address;
    List<String> types = new ArrayList();
    List<AddressComponents> address_components = new ArrayList();

    public String getFormatted_address() {
        return this.formatted_address;
    }

    public void setFormatted_address(String str) {
        this.formatted_address = str;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> list) {
        this.types = list;
    }

    public List<AddressComponents> getAddress_components() {
        return this.address_components;
    }

    public void setAddress_components(List<AddressComponents> list) {
        this.address_components = list;
    }

    public class AddressComponents {
        String long_name;
        String short_name;
        List<String> types = new ArrayList();

        public AddressComponents() {
        }

        public String getLong_name() {
            return this.long_name;
        }

        public void setLong_name(String str) {
            this.long_name = str;
        }

        public String getShort_name() {
            return this.short_name;
        }

        public void setShort_name(String str) {
            this.short_name = str;
        }

        public List<String> getTypes() {
            return this.types;
        }

        public void setTypes(List<String> list) {
            this.types = list;
        }
    }
}
