package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class ListInventoryConfiguration {
    public String continuationToken;
    public Set<InventoryConfiguration> inventoryConfigurations;
    public boolean isTruncated = false;
    public String nextContinuationToken;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ListInventoryConfigurationResult\nIsTruncated:");
        sb.append(this.isTruncated).append("\n");
        if (this.continuationToken != null) {
            sb.append("ContinuationToken:").append(this.continuationToken).append("\n");
        }
        if (this.nextContinuationToken != null) {
            sb.append("NextContinuationToken:").append(this.nextContinuationToken).append("\n");
        }
        Set<InventoryConfiguration> set = this.inventoryConfigurations;
        if (set != null) {
            for (InventoryConfiguration inventoryConfiguration : set) {
                if (inventoryConfiguration != null) {
                    sb.append(inventoryConfiguration.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }
}
