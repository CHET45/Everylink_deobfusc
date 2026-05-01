package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.bucket.PutBucketIntelligentTieringRequest;
import com.tencent.cos.xml.model.tag.AccessControlPolicy;
import com.tencent.cos.xml.model.tag.BucketLoggingStatus;
import com.tencent.cos.xml.model.tag.CORSConfiguration;
import com.tencent.cos.xml.model.tag.CreateBucketConfiguration;
import com.tencent.cos.xml.model.tag.Delete;
import com.tencent.cos.xml.model.tag.DomainConfiguration;
import com.tencent.cos.xml.model.tag.InventoryConfiguration;
import com.tencent.cos.xml.model.tag.LifecycleConfiguration;
import com.tencent.cos.xml.model.tag.ReplicationConfiguration;
import com.tencent.cos.xml.model.tag.RestoreConfigure;
import com.tencent.cos.xml.model.tag.Tagging;
import com.tencent.cos.xml.model.tag.VersioningConfiguration;
import com.tencent.cos.xml.model.tag.WebsiteConfiguration;
import com.tencent.cos.xml.model.tag.eventstreaming.CSVInput;
import com.tencent.cos.xml.model.tag.eventstreaming.CSVOutput;
import com.tencent.cos.xml.model.tag.eventstreaming.JSONInput;
import com.tencent.cos.xml.model.tag.eventstreaming.JSONOutput;
import com.tencent.cos.xml.model.tag.eventstreaming.SelectRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class XmlBuilder extends XmlSlimBuilder {
    public static String buildPutBucketAccelerateXML(boolean z) throws XmlPullParserException, IOException {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "AccelerateConfiguration");
        addElement(xmlSerializerNewSerializer, "Status", z ? "Enabled" : PutBucketIntelligentTieringRequest.STATUS_SUSPEND);
        xmlSerializerNewSerializer.endTag("", "AccelerateConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildAccessControlPolicyXML(AccessControlPolicy accessControlPolicy) throws XmlPullParserException, IOException {
        if (accessControlPolicy == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "AccessControlPolicy");
        xmlSerializerNewSerializer.startTag("", "Owner");
        addElement(xmlSerializerNewSerializer, "ID", accessControlPolicy.owner.f1832id);
        xmlSerializerNewSerializer.endTag("", "Owner");
        xmlSerializerNewSerializer.startTag("", "AccessControlList");
        for (AccessControlPolicy.Grant grant : accessControlPolicy.accessControlList.grants) {
            xmlSerializerNewSerializer.startTag("", "Grant");
            if (!TextUtils.isEmpty(grant.grantee.uri)) {
                xmlSerializerNewSerializer.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                xmlSerializerNewSerializer.startTag("", "Grantee");
                xmlSerializerNewSerializer.attribute("", "xsi:type", "CanonicalUser");
                addElement(xmlSerializerNewSerializer, "URI", grant.grantee.uri);
                xmlSerializerNewSerializer.endTag("", "Grantee");
            } else if (!TextUtils.isEmpty(grant.grantee.f1831id)) {
                xmlSerializerNewSerializer.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                xmlSerializerNewSerializer.startTag("", "Grantee");
                xmlSerializerNewSerializer.attribute("", "xsi:type", "Group");
                addElement(xmlSerializerNewSerializer, "ID", grant.grantee.f1831id);
                xmlSerializerNewSerializer.endTag("", "Grantee");
            }
            addElement(xmlSerializerNewSerializer, Constants.PERMISSION, grant.permission);
            xmlSerializerNewSerializer.endTag("", "Grant");
        }
        xmlSerializerNewSerializer.endTag("", "AccessControlList");
        xmlSerializerNewSerializer.endTag("", "AccessControlPolicy");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildCORSConfigurationXML(CORSConfiguration cORSConfiguration) throws XmlPullParserException, IOException {
        if (cORSConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "CORSConfiguration");
        if (cORSConfiguration.corsRules != null) {
            for (CORSConfiguration.CORSRule cORSRule : cORSConfiguration.corsRules) {
                if (cORSRule != null) {
                    xmlSerializerNewSerializer.startTag("", "CORSRule");
                    addElement(xmlSerializerNewSerializer, "ID", cORSRule.f1833id);
                    addElement(xmlSerializerNewSerializer, "AllowedOrigin", cORSRule.allowedOrigin);
                    if (cORSRule.allowedMethod != null) {
                        Iterator<String> it = cORSRule.allowedMethod.iterator();
                        while (it.hasNext()) {
                            addElement(xmlSerializerNewSerializer, "AllowedMethod", it.next());
                        }
                    }
                    if (cORSRule.allowedHeader != null) {
                        Iterator<String> it2 = cORSRule.allowedHeader.iterator();
                        while (it2.hasNext()) {
                            addElement(xmlSerializerNewSerializer, "AllowedHeader", it2.next());
                        }
                    }
                    if (cORSRule.exposeHeader != null) {
                        Iterator<String> it3 = cORSRule.exposeHeader.iterator();
                        while (it3.hasNext()) {
                            addElement(xmlSerializerNewSerializer, "ExposeHeader", it3.next());
                        }
                    }
                    addElement(xmlSerializerNewSerializer, "MaxAgeSeconds", String.valueOf(cORSRule.maxAgeSeconds));
                    xmlSerializerNewSerializer.endTag("", "CORSRule");
                }
            }
        }
        xmlSerializerNewSerializer.endTag("", "CORSConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildLifecycleConfigurationXML(LifecycleConfiguration lifecycleConfiguration) throws XmlPullParserException, IOException {
        if (lifecycleConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "LifecycleConfiguration");
        if (lifecycleConfiguration.rules != null) {
            for (LifecycleConfiguration.Rule rule : lifecycleConfiguration.rules) {
                if (rule != null) {
                    xmlSerializerNewSerializer.startTag("", "Rule");
                    addElement(xmlSerializerNewSerializer, "ID", rule.f1835id);
                    if (rule.filter != null) {
                        xmlSerializerNewSerializer.startTag("", "Filter");
                        addElement(xmlSerializerNewSerializer, Constants.PREFIX_ELEMENT, rule.filter.prefix);
                        xmlSerializerNewSerializer.endTag("", "Filter");
                    }
                    addElement(xmlSerializerNewSerializer, "Status", rule.status);
                    if (rule.transition != null) {
                        xmlSerializerNewSerializer.startTag("", "Transition");
                        addElement(xmlSerializerNewSerializer, Constants.AnalyticsConstants.DAYS_ELEMENT, String.valueOf(rule.transition.days));
                        addElement(xmlSerializerNewSerializer, "StorageClass", rule.transition.storageClass);
                        addElement(xmlSerializerNewSerializer, "Date", rule.transition.date);
                        xmlSerializerNewSerializer.endTag("", "Transition");
                    }
                    if (rule.expiration != null) {
                        xmlSerializerNewSerializer.startTag("", "Expiration");
                        addElement(xmlSerializerNewSerializer, Constants.AnalyticsConstants.DAYS_ELEMENT, String.valueOf(rule.expiration.days));
                        addElement(xmlSerializerNewSerializer, "ExpiredObjectDeleteMarker", rule.expiration.expiredObjectDeleteMarker);
                        addElement(xmlSerializerNewSerializer, "Date", rule.expiration.date);
                        xmlSerializerNewSerializer.endTag("", "Expiration");
                    }
                    if (rule.noncurrentVersionTransition != null) {
                        xmlSerializerNewSerializer.startTag("", "NoncurrentVersionTransition");
                        addElement(xmlSerializerNewSerializer, "NoncurrentDays", String.valueOf(rule.noncurrentVersionTransition.noncurrentDays));
                        addElement(xmlSerializerNewSerializer, "StorageClass", rule.noncurrentVersionTransition.storageClass);
                        xmlSerializerNewSerializer.endTag("", "NoncurrentVersionTransition");
                    }
                    if (rule.noncurrentVersionExpiration != null) {
                        xmlSerializerNewSerializer.startTag("", "NoncurrentVersionExpiration");
                        addElement(xmlSerializerNewSerializer, "NoncurrentDays", String.valueOf(rule.noncurrentVersionExpiration.noncurrentDays));
                        xmlSerializerNewSerializer.endTag("", "NoncurrentVersionExpiration");
                    }
                    if (rule.abortIncompleteMultiUpload != null) {
                        xmlSerializerNewSerializer.startTag("", "AbortIncompleteMultipartUpload");
                        addElement(xmlSerializerNewSerializer, "DaysAfterInitiation", String.valueOf(rule.abortIncompleteMultiUpload.daysAfterInitiation));
                        xmlSerializerNewSerializer.endTag("", "AbortIncompleteMultipartUpload");
                    }
                    xmlSerializerNewSerializer.endTag("", "Rule");
                }
            }
        }
        xmlSerializerNewSerializer.endTag("", "LifecycleConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildReplicationConfiguration(ReplicationConfiguration replicationConfiguration) throws XmlPullParserException, IOException {
        if (replicationConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "ReplicationConfiguration");
        addElement(xmlSerializerNewSerializer, "Role", replicationConfiguration.role);
        if (replicationConfiguration.rules != null) {
            for (ReplicationConfiguration.Rule rule : replicationConfiguration.rules) {
                if (rule != null) {
                    xmlSerializerNewSerializer.startTag("", "Rule");
                    addElement(xmlSerializerNewSerializer, "Status", rule.status);
                    addElement(xmlSerializerNewSerializer, "ID", rule.f1843id);
                    addElement(xmlSerializerNewSerializer, Constants.PREFIX_ELEMENT, rule.prefix);
                    if (rule.destination != null) {
                        xmlSerializerNewSerializer.startTag("", "Destination");
                        addElement(xmlSerializerNewSerializer, "Bucket", rule.destination.bucket);
                        addElement(xmlSerializerNewSerializer, "StorageClass", rule.destination.storageClass);
                        xmlSerializerNewSerializer.endTag("", "Destination");
                    }
                    xmlSerializerNewSerializer.endTag("", "Rule");
                }
            }
        }
        xmlSerializerNewSerializer.endTag("", "ReplicationConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildVersioningConfiguration(VersioningConfiguration versioningConfiguration) throws XmlPullParserException, IOException {
        if (versioningConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "VersioningConfiguration");
        addElement(xmlSerializerNewSerializer, "Status", versioningConfiguration.status);
        xmlSerializerNewSerializer.endTag("", "VersioningConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildCreateBucketConfiguration(CreateBucketConfiguration createBucketConfiguration) throws XmlPullParserException, IOException {
        if (createBucketConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "CreateBucketConfiguration");
        addElement(xmlSerializerNewSerializer, "BucketAZConfig", createBucketConfiguration.bucketAzConfig);
        xmlSerializerNewSerializer.endTag("", "CreateBucketConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildDelete(Delete delete) throws XmlPullParserException, IOException {
        if (delete == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", Constants.AnalyticsConstants.DELETE_ELEMENT);
        addElement(xmlSerializerNewSerializer, "Quiet", String.valueOf(delete.quiet));
        if (delete.deleteObjects != null) {
            for (Delete.DeleteObject deleteObject : delete.deleteObjects) {
                if (deleteObject != null) {
                    xmlSerializerNewSerializer.startTag("", "Object");
                    addElement(xmlSerializerNewSerializer, "Key", deleteObject.key);
                    addElement(xmlSerializerNewSerializer, "VersionId", deleteObject.versionId);
                    xmlSerializerNewSerializer.endTag("", "Object");
                }
            }
        }
        xmlSerializerNewSerializer.endTag("", Constants.AnalyticsConstants.DELETE_ELEMENT);
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildRestore(RestoreConfigure restoreConfigure) throws XmlPullParserException, IOException {
        if (restoreConfigure == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "RestoreRequest");
        addElement(xmlSerializerNewSerializer, Constants.AnalyticsConstants.DAYS_ELEMENT, String.valueOf(restoreConfigure.days));
        if (restoreConfigure.casJobParameters != null) {
            xmlSerializerNewSerializer.startTag("", "CASJobParameters");
            addElement(xmlSerializerNewSerializer, "Tier", restoreConfigure.casJobParameters.tier);
            xmlSerializerNewSerializer.endTag("", "CASJobParameters");
        }
        xmlSerializerNewSerializer.endTag("", "RestoreRequest");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildBucketLogging(BucketLoggingStatus bucketLoggingStatus) throws XmlPullParserException, IOException {
        if (bucketLoggingStatus == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "BucketLoggingStatus");
        if (bucketLoggingStatus.loggingEnabled != null) {
            xmlSerializerNewSerializer.startTag("", "LoggingEnabled");
            addElement(xmlSerializerNewSerializer, "TargetBucket", bucketLoggingStatus.loggingEnabled.targetBucket);
            addElement(xmlSerializerNewSerializer, "TargetPrefix", bucketLoggingStatus.loggingEnabled.targetPrefix);
            xmlSerializerNewSerializer.endTag("", "LoggingEnabled");
        }
        xmlSerializerNewSerializer.endTag("", "BucketLoggingStatus");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildTagging(Tagging tagging) throws XmlPullParserException, IOException {
        if (tagging == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "Tagging");
        xmlSerializerNewSerializer.startTag("", "TagSet");
        if (!tagging.tagSet.tag.isEmpty()) {
            for (Tagging.Tag tag : tagging.tagSet.tag) {
                xmlSerializerNewSerializer.startTag("", "Tag");
                addElement(xmlSerializerNewSerializer, "Key", tag.key);
                addElement(xmlSerializerNewSerializer, "Value", tag.value);
                xmlSerializerNewSerializer.endTag("", "Tag");
            }
        }
        xmlSerializerNewSerializer.endTag("", "TagSet");
        xmlSerializerNewSerializer.endTag("", "Tagging");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildWebsiteConfiguration(WebsiteConfiguration websiteConfiguration) throws XmlPullParserException, IOException {
        if (websiteConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "WebsiteConfiguration");
        if (websiteConfiguration.indexDocument != null) {
            xmlSerializerNewSerializer.startTag("", "IndexDocument");
            if (websiteConfiguration.indexDocument.suffix != null) {
                addElement(xmlSerializerNewSerializer, "Suffix", websiteConfiguration.indexDocument.suffix);
            }
            xmlSerializerNewSerializer.endTag("", "IndexDocument");
        }
        if (websiteConfiguration.errorDocument != null) {
            xmlSerializerNewSerializer.startTag("", "ErrorDocument");
            if (websiteConfiguration.errorDocument.key != null) {
                addElement(xmlSerializerNewSerializer, "Key", websiteConfiguration.errorDocument.key);
            }
            xmlSerializerNewSerializer.endTag("", "ErrorDocument");
        }
        if (websiteConfiguration.redirectAllRequestTo != null) {
            xmlSerializerNewSerializer.startTag("", "RedirectAllRequestTo");
            if (websiteConfiguration.redirectAllRequestTo.protocol != null) {
                addElement(xmlSerializerNewSerializer, "Protocol", websiteConfiguration.redirectAllRequestTo.protocol);
            }
            xmlSerializerNewSerializer.endTag("", "RedirectAllRequestTo");
        }
        if (websiteConfiguration.routingRules != null && websiteConfiguration.routingRules.size() > 0) {
            xmlSerializerNewSerializer.startTag("", "RoutingRules");
            for (WebsiteConfiguration.RoutingRule routingRule : websiteConfiguration.routingRules) {
                xmlSerializerNewSerializer.startTag("", "RoutingRule");
                if (routingRule.contidion != null) {
                    xmlSerializerNewSerializer.startTag("", "Condition");
                    if (routingRule.contidion.httpErrorCodeReturnedEquals != -1) {
                        addElement(xmlSerializerNewSerializer, "HttpErrorCodeReturnedEquals", String.valueOf(routingRule.contidion.httpErrorCodeReturnedEquals));
                    }
                    if (routingRule.contidion.keyPrefixEquals != null) {
                        addElement(xmlSerializerNewSerializer, "KeyPrefixEquals", routingRule.contidion.keyPrefixEquals);
                    }
                    xmlSerializerNewSerializer.endTag("", "Condition");
                }
                if (routingRule.redirect != null) {
                    xmlSerializerNewSerializer.startTag("", "Redirect");
                    if (routingRule.redirect.protocol != null) {
                        addElement(xmlSerializerNewSerializer, "Protocol", routingRule.redirect.protocol);
                    }
                    if (routingRule.redirect.replaceKeyPrefixWith != null) {
                        addElement(xmlSerializerNewSerializer, "ReplaceKeyPrefixWith", routingRule.redirect.replaceKeyPrefixWith);
                    }
                    if (routingRule.redirect.replaceKeyWith != null) {
                        addElement(xmlSerializerNewSerializer, "ReplaceKeyWith", routingRule.redirect.replaceKeyWith);
                    }
                    xmlSerializerNewSerializer.endTag("", "Redirect");
                }
                xmlSerializerNewSerializer.endTag("", "RoutingRule");
            }
            xmlSerializerNewSerializer.endTag("", "RoutingRules");
        }
        xmlSerializerNewSerializer.endTag("", "WebsiteConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildInventoryConfiguration(InventoryConfiguration inventoryConfiguration) throws XmlPullParserException, IOException {
        if (inventoryConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "InventoryConfiguration");
        if (inventoryConfiguration.f1834id != null) {
            addElement(xmlSerializerNewSerializer, Constants.f893ID, inventoryConfiguration.f1834id);
        }
        addElement(xmlSerializerNewSerializer, "IsEnabled", inventoryConfiguration.isEnabled ? Constants.TRUE : Constants.FALSE);
        if (inventoryConfiguration.destination != null) {
            xmlSerializerNewSerializer.startTag("", "Destination");
            if (inventoryConfiguration.destination.cosBucketDestination != null) {
                xmlSerializerNewSerializer.startTag("", "COSBucketDestination");
                if (inventoryConfiguration.destination.cosBucketDestination.format != null) {
                    addElement(xmlSerializerNewSerializer, "Format", inventoryConfiguration.destination.cosBucketDestination.format);
                }
                if (inventoryConfiguration.destination.cosBucketDestination.accountId != null) {
                    addElement(xmlSerializerNewSerializer, "AccountId", inventoryConfiguration.destination.cosBucketDestination.accountId);
                }
                if (inventoryConfiguration.destination.cosBucketDestination.bucket != null) {
                    addElement(xmlSerializerNewSerializer, "Bucket", inventoryConfiguration.destination.cosBucketDestination.bucket);
                }
                if (inventoryConfiguration.destination.cosBucketDestination.prefix != null) {
                    addElement(xmlSerializerNewSerializer, Constants.PREFIX_ELEMENT, inventoryConfiguration.destination.cosBucketDestination.prefix);
                }
                if (inventoryConfiguration.destination.cosBucketDestination.encryption != null) {
                    xmlSerializerNewSerializer.startTag("", "Encryption");
                    addElement(xmlSerializerNewSerializer, "SSE-COS", inventoryConfiguration.destination.cosBucketDestination.encryption.sSECOS);
                    xmlSerializerNewSerializer.endTag("", "Encryption");
                }
                xmlSerializerNewSerializer.endTag("", "COSBucketDestination");
            }
            xmlSerializerNewSerializer.endTag("", "Destination");
        }
        if (inventoryConfiguration.schedule != null && inventoryConfiguration.schedule.frequency != null) {
            xmlSerializerNewSerializer.startTag("", "Schedule");
            addElement(xmlSerializerNewSerializer, "Frequency", inventoryConfiguration.schedule.frequency);
            xmlSerializerNewSerializer.endTag("", "Schedule");
        }
        if (inventoryConfiguration.filter != null && inventoryConfiguration.filter.prefix != null) {
            xmlSerializerNewSerializer.startTag("", "Filter");
            addElement(xmlSerializerNewSerializer, Constants.PREFIX_ELEMENT, inventoryConfiguration.filter.prefix);
            xmlSerializerNewSerializer.endTag("", "Filter");
        }
        if (inventoryConfiguration.includedObjectVersions != null) {
            addElement(xmlSerializerNewSerializer, "IncludedObjectVersions", inventoryConfiguration.includedObjectVersions);
        }
        if (inventoryConfiguration.optionalFields != null && inventoryConfiguration.optionalFields.fields != null) {
            xmlSerializerNewSerializer.startTag("", "OptionalFields");
            Iterator<String> it = inventoryConfiguration.optionalFields.fields.iterator();
            while (it.hasNext()) {
                addElement(xmlSerializerNewSerializer, "Field", it.next());
            }
            xmlSerializerNewSerializer.endTag("", "OptionalFields");
        }
        xmlSerializerNewSerializer.endTag("", "InventoryConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildDomainConfiguration(DomainConfiguration domainConfiguration) throws XmlPullParserException, IOException {
        if (domainConfiguration == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "DomainConfiguration");
        if (domainConfiguration.domainRules != null && domainConfiguration.domainRules.size() > 0) {
            for (DomainConfiguration.DomainRule domainRule : domainConfiguration.domainRules) {
                xmlSerializerNewSerializer.startTag("", "DomainRule");
                addElement(xmlSerializerNewSerializer, "Status", domainRule.status);
                addElement(xmlSerializerNewSerializer, Constants.NAME_ELEMENT, domainRule.name);
                addElement(xmlSerializerNewSerializer, "Type", domainRule.type);
                addElement(xmlSerializerNewSerializer, "ForcedReplacement", domainRule.forcedReplacement);
                xmlSerializerNewSerializer.endTag("", "DomainRule");
            }
        }
        xmlSerializerNewSerializer.endTag("", "DomainConfiguration");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static String buildSelectRequest(SelectRequest selectRequest) throws XmlPullParserException, IOException {
        if (selectRequest == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XmlPullParserFactory.newInstance().newSerializer();
        xmlSerializerNewSerializer.setOutput(stringWriter);
        xmlSerializerNewSerializer.startDocument("UTF-8", null);
        xmlSerializerNewSerializer.startTag("", "SelectRequest");
        addElement(xmlSerializerNewSerializer, "Expression", selectRequest.getExpression());
        addElement(xmlSerializerNewSerializer, "ExpressionType", selectRequest.getExpressionType());
        xmlSerializerNewSerializer.startTag("", "InputSerialization");
        addElement(xmlSerializerNewSerializer, "CompressionType", selectRequest.getInputSerialization().getCompressionType());
        if (selectRequest.getInputSerialization().getCsv() != null) {
            CSVInput csv = selectRequest.getInputSerialization().getCsv();
            xmlSerializerNewSerializer.startTag("", "CSV");
            addElement(xmlSerializerNewSerializer, "FileHeaderInfo", csv.getFileHeaderInfo());
            addElement(xmlSerializerNewSerializer, "RecordDelimiter", csv.getRecordDelimiterAsString());
            addElement(xmlSerializerNewSerializer, "FieldDelimiter", csv.getFieldDelimiterAsString());
            addElement(xmlSerializerNewSerializer, "QuoteCharacter", csv.getQuoteCharacterAsString());
            addElement(xmlSerializerNewSerializer, "QuoteEscapeCharacter", csv.getQuoteEscapeCharacterAsString());
            addElement(xmlSerializerNewSerializer, "Comments", csv.getCommentsAsString());
            addElement(xmlSerializerNewSerializer, "AllowQuotedRecordDelimiter", csv.getAllowQuotedRecordDelimiter().booleanValue() ? "TRUE" : "FALSE");
            xmlSerializerNewSerializer.endTag("", "CSV");
        } else if (selectRequest.getInputSerialization().getJson() != null) {
            JSONInput json = selectRequest.getInputSerialization().getJson();
            xmlSerializerNewSerializer.startTag("", "JSON");
            addElement(xmlSerializerNewSerializer, "Type", json.getType());
            xmlSerializerNewSerializer.endTag("", "JSON");
        }
        xmlSerializerNewSerializer.endTag("", "InputSerialization");
        xmlSerializerNewSerializer.startTag("", "OutputSerialization");
        if (selectRequest.getOutputSerialization().getCsv() != null) {
            CSVOutput csv2 = selectRequest.getOutputSerialization().getCsv();
            xmlSerializerNewSerializer.startTag("", "CSV");
            addElement(xmlSerializerNewSerializer, "QuoteFields", csv2.getQuoteFields());
            addElement(xmlSerializerNewSerializer, "RecordDelimiter", csv2.getRecordDelimiterAsString());
            addElement(xmlSerializerNewSerializer, "FieldDelimiter", csv2.getFieldDelimiterAsString());
            addElement(xmlSerializerNewSerializer, "QuoteCharacter", csv2.getQuoteCharacterAsString());
            addElement(xmlSerializerNewSerializer, "QuoteEscapeCharacter", csv2.getQuoteEscapeCharacterAsString());
            xmlSerializerNewSerializer.endTag("", "CSV");
        } else if (selectRequest.getOutputSerialization().getJson() != null) {
            JSONOutput json2 = selectRequest.getOutputSerialization().getJson();
            xmlSerializerNewSerializer.startTag("", "JSON");
            addElement(xmlSerializerNewSerializer, "RecordDelimiter", json2.getRecordDelimiterAsString());
            xmlSerializerNewSerializer.endTag("", "JSON");
        }
        xmlSerializerNewSerializer.endTag("", "OutputSerialization");
        xmlSerializerNewSerializer.startTag("", "RequestProgress");
        addElement(xmlSerializerNewSerializer, "Enabled", String.valueOf(selectRequest.getRequestProgress().getEnabled()));
        xmlSerializerNewSerializer.endTag("", "RequestProgress");
        xmlSerializerNewSerializer.endTag("", "SelectRequest");
        xmlSerializerNewSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    private static void addElement(XmlSerializer xmlSerializer, String str, String str2) throws IOException {
        if (str2 != null) {
            xmlSerializer.startTag("", str);
            xmlSerializer.text(str2);
            xmlSerializer.endTag("", str);
        }
    }

    private static String removeXMLHeader(String str) {
        return (str == null || !str.startsWith("<?xml")) ? str : str.substring(str.indexOf("?>") + 2);
    }
}
