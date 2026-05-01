package com.tencent.cos.xml.transfer;

import android.util.Xml;
import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.tag.AccelerateConfiguration;
import com.tencent.cos.xml.model.tag.AccessControlPolicy;
import com.tencent.cos.xml.model.tag.BucketLoggingStatus;
import com.tencent.cos.xml.model.tag.CORSConfiguration;
import com.tencent.cos.xml.model.tag.DeleteResult;
import com.tencent.cos.xml.model.tag.DomainConfiguration;
import com.tencent.cos.xml.model.tag.InventoryConfiguration;
import com.tencent.cos.xml.model.tag.LifecycleConfiguration;
import com.tencent.cos.xml.model.tag.ListBucket;
import com.tencent.cos.xml.model.tag.ListBucketVersions;
import com.tencent.cos.xml.model.tag.ListInventoryConfiguration;
import com.tencent.cos.xml.model.tag.ListVersionResult;
import com.tencent.cos.xml.model.tag.LocationConstraint;
import com.tencent.cos.xml.model.tag.ReplicationConfiguration;
import com.tencent.cos.xml.model.tag.Tagging;
import com.tencent.cos.xml.model.tag.VersioningConfiguration;
import com.tencent.cos.xml.model.tag.WebsiteConfiguration;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class XmlParser extends XmlSlimParser {
    public static void parseGetBucketObjectVersionsResult(InputStream inputStream, ListVersionResult listVersionResult) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        LinkedList linkedList3 = new LinkedList();
        listVersionResult.commonPrefixes = linkedList;
        listVersionResult.deleteMarkers = linkedList3;
        listVersionResult.versions = linkedList2;
        ListVersionResult.Owner owner = null;
        ListVersionResult.DeleteMarker deleteMarker = null;
        ListVersionResult.Version version = null;
        ListVersionResult.CommonPrefixes commonPrefixes = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase(Constants.NAME_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.name = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-Type")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.encodingType = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("KeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.keyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("VersionIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.versionIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxKeys")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.maxKeys = Integer.parseInt(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase(Constants.DELIMITER_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.delimiter = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextKeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.nextKeyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("NextVersionIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listVersionResult.nextVersionIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (commonPrefixes == null) {
                        listVersionResult.prefix = xmlPullParserNewPullParser.getText();
                    } else {
                        commonPrefixes.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.key = xmlPullParserNewPullParser.getText();
                    } else if (deleteMarker != null) {
                        deleteMarker.key = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("VersionId")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.versionID = xmlPullParserNewPullParser.getText();
                    } else if (deleteMarker != null) {
                        deleteMarker.versionId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("IsLatest")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.isLatest = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                    } else if (deleteMarker != null) {
                        deleteMarker.isLatest = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("LastModified")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.lastModified = xmlPullParserNewPullParser.getText();
                    } else if (deleteMarker != null) {
                        deleteMarker.lastModified = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ETag")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.etag = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(BlobConstants.SIZE_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.size = Long.parseLong(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.storageClass = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListVersionResult.Owner();
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.f1842id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DisplayName")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.displayName = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DeleteMarker")) {
                    deleteMarker = new ListVersionResult.DeleteMarker();
                } else if (name.equalsIgnoreCase(Constants.AnalyticsConstants.VERSION_ELEMENT)) {
                    version = new ListVersionResult.Version();
                } else if (name.equalsIgnoreCase("CommonPrefixes")) {
                    commonPrefixes = new ListVersionResult.CommonPrefixes();
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Owner")) {
                    if (owner != null) {
                        if (deleteMarker != null) {
                            deleteMarker.owner = owner;
                        } else if (version != null) {
                            version.owner = owner;
                        }
                        owner = null;
                    }
                } else if (name2.equalsIgnoreCase("DeleteMarker")) {
                    if (deleteMarker != null) {
                        linkedList3.add(deleteMarker);
                        deleteMarker = null;
                    }
                } else if (name2.equalsIgnoreCase(Constants.AnalyticsConstants.VERSION_ELEMENT)) {
                    if (version != null) {
                        linkedList2.add(version);
                        version = null;
                    }
                } else if (name2.equalsIgnoreCase("CommonPrefixes") && commonPrefixes != null) {
                    linkedList.add(commonPrefixes);
                    commonPrefixes = null;
                }
            }
        }
    }

    public static void parseListBucketResult(InputStream inputStream, ListBucket listBucket) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        listBucket.contentsList = new ArrayList();
        listBucket.commonPrefixesList = new ArrayList();
        ListBucket.Contents contents = null;
        ListBucket.CommonPrefixes commonPrefixes = null;
        ListBucket.Owner owner = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase(Constants.NAME_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listBucket.name = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-Type")) {
                    xmlPullParserNewPullParser.next();
                    listBucket.encodingType = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase(Constants.MARKER_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listBucket.marker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxKeys")) {
                    xmlPullParserNewPullParser.next();
                    listBucket.maxKeys = Integer.parseInt(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase(Constants.DELIMITER_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listBucket.delimiter = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextMarker")) {
                    xmlPullParserNewPullParser.next();
                    listBucket.nextMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listBucket.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (commonPrefixes == null) {
                        listBucket.prefix = xmlPullParserNewPullParser.getText();
                    } else {
                        commonPrefixes.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Contents")) {
                    contents = new ListBucket.Contents();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    if (contents != null) {
                        contents.key = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("LastModified")) {
                    xmlPullParserNewPullParser.next();
                    if (contents != null) {
                        contents.lastModified = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ETag")) {
                    xmlPullParserNewPullParser.next();
                    if (contents != null) {
                        contents.eTag = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(BlobConstants.SIZE_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (contents != null) {
                        contents.size = Long.parseLong(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    if (contents != null) {
                        contents.storageClass = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListBucket.Owner();
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.f1837id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("CommonPrefixes")) {
                    commonPrefixes = new ListBucket.CommonPrefixes();
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Contents")) {
                    listBucket.contentsList.add(contents);
                    contents = null;
                } else if (name2.equalsIgnoreCase("Owner")) {
                    if (contents != null) {
                        contents.owner = owner;
                    }
                    owner = null;
                } else if (name2.equalsIgnoreCase("CommonPrefixes")) {
                    listBucket.commonPrefixesList.add(commonPrefixes);
                    commonPrefixes = null;
                }
            }
        }
    }

    public static void parseAccessControlPolicy(InputStream inputStream, AccessControlPolicy accessControlPolicy) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        accessControlPolicy.accessControlList = new AccessControlPolicy.AccessControlList();
        accessControlPolicy.accessControlList.grants = new ArrayList();
        AccessControlPolicy.Owner owner = null;
        AccessControlPolicy.Grant grant = null;
        AccessControlPolicy.Grantee grantee = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Owner")) {
                    owner = new AccessControlPolicy.Owner();
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.f1832id = xmlPullParserNewPullParser.getText();
                    } else if (grantee != null) {
                        grantee.f1831id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DisplayName")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.displayName = xmlPullParserNewPullParser.getText();
                    } else if (grantee != null) {
                        grantee.displayName = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Grant")) {
                    grant = new AccessControlPolicy.Grant();
                } else if (name.equalsIgnoreCase("Grantee")) {
                    grantee = new AccessControlPolicy.Grantee();
                } else if (name.equalsIgnoreCase("URI")) {
                    xmlPullParserNewPullParser.next();
                    if (grantee != null) {
                        grantee.uri = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.PERMISSION)) {
                    xmlPullParserNewPullParser.next();
                    if (grant != null) {
                        grant.permission = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Owner")) {
                    accessControlPolicy.owner = owner;
                    owner = null;
                } else if (name2.equalsIgnoreCase("Grant")) {
                    accessControlPolicy.accessControlList.grants.add(grant);
                    grant = null;
                } else if (name2.equalsIgnoreCase("Grantee")) {
                    if (grant != null) {
                        grant.grantee = grantee;
                    }
                    grantee = null;
                }
            }
        }
    }

    public static void parseCORSConfiguration(InputStream inputStream, CORSConfiguration cORSConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        cORSConfiguration.corsRules = new ArrayList();
        CORSConfiguration.CORSRule cORSRule = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("CORSRule")) {
                    cORSRule = new CORSConfiguration.CORSRule();
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null) {
                        cORSRule.f1833id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("AllowedOrigin")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null) {
                        cORSRule.allowedOrigin = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("AllowedMethod")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null && cORSRule.allowedMethod == null) {
                        cORSRule.allowedMethod = new ArrayList();
                    }
                    if (cORSRule != null) {
                        cORSRule.allowedMethod.add(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("AllowedHeader")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null && cORSRule.allowedHeader == null) {
                        cORSRule.allowedHeader = new ArrayList();
                    }
                    if (cORSRule != null) {
                        cORSRule.allowedHeader.add(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("ExposeHeader")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null && cORSRule.exposeHeader == null) {
                        cORSRule.exposeHeader = new ArrayList();
                    }
                    if (cORSRule != null) {
                        cORSRule.exposeHeader.add(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("MaxAgeSeconds")) {
                    xmlPullParserNewPullParser.next();
                    if (cORSRule != null) {
                        cORSRule.maxAgeSeconds = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    }
                }
            } else if (eventType == 3 && xmlPullParserNewPullParser.getName().equalsIgnoreCase("CORSRule")) {
                cORSConfiguration.corsRules.add(cORSRule);
                cORSRule = null;
            }
        }
    }

    public static void parseReplicationConfiguration(InputStream inputStream, ReplicationConfiguration replicationConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        replicationConfiguration.rules = new ArrayList();
        ReplicationConfiguration.Rule rule = null;
        ReplicationConfiguration.Destination destination = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Role")) {
                    xmlPullParserNewPullParser.next();
                    replicationConfiguration.role = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Rule")) {
                    rule = new ReplicationConfiguration.Rule();
                } else if (name.equalsIgnoreCase("Status")) {
                    xmlPullParserNewPullParser.next();
                    if (rule != null) {
                        rule.status = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (rule != null) {
                        rule.f1843id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (rule != null) {
                        rule.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Destination")) {
                    destination = new ReplicationConfiguration.Destination();
                } else if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    if (destination != null) {
                        destination.bucket = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    if (destination != null) {
                        destination.storageClass = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Rule")) {
                    replicationConfiguration.rules.add(rule);
                    rule = null;
                } else if (name2.equalsIgnoreCase("Destination")) {
                    if (rule != null) {
                        rule.destination = destination;
                    }
                    destination = null;
                }
            }
        }
    }

    public static void parseVersioningConfiguration(InputStream inputStream, VersioningConfiguration versioningConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2 && xmlPullParserNewPullParser.getName().equalsIgnoreCase("Status")) {
                xmlPullParserNewPullParser.next();
                versioningConfiguration.status = xmlPullParserNewPullParser.getText();
            }
        }
    }

    public static void parseTagging(InputStream inputStream, Tagging tagging) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        Tagging.TagSet tagSet = null;
        Tagging.Tag tag = null;
        String text = null;
        String text2 = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if ("TagSet".equalsIgnoreCase(name)) {
                    tagSet = new Tagging.TagSet();
                } else if ("Tag".equalsIgnoreCase(name)) {
                    tag = new Tagging.Tag();
                } else if ("Key".equalsIgnoreCase(name)) {
                    xmlPullParserNewPullParser.next();
                    text = xmlPullParserNewPullParser.getText();
                } else if ("Value".equalsIgnoreCase(name)) {
                    xmlPullParserNewPullParser.next();
                    text2 = xmlPullParserNewPullParser.getText();
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if ("TagSet".equalsIgnoreCase(name2)) {
                    tagging.tagSet = tagSet;
                    tagSet = null;
                } else if ("Tag".equalsIgnoreCase(name2)) {
                    if (tagSet != null) {
                        tagSet.addTag(tag);
                    }
                } else if ("Key".equalsIgnoreCase(name2)) {
                    if (tag != null) {
                        tag.key = text;
                    }
                } else if ("Value".equalsIgnoreCase(name2) && tag != null) {
                    tag.value = text2;
                }
            }
        }
    }

    public static void parseAccelerateConfiguration(InputStream inputStream, AccelerateConfiguration accelerateConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if ("Status".equalsIgnoreCase(name)) {
                    xmlPullParserNewPullParser.next();
                    accelerateConfiguration.status = xmlPullParserNewPullParser.getText();
                } else if ("Type".equalsIgnoreCase(name)) {
                    xmlPullParserNewPullParser.next();
                    accelerateConfiguration.type = xmlPullParserNewPullParser.getText();
                }
            }
        }
    }

    public static void parseLifecycleConfiguration(InputStream inputStream, LifecycleConfiguration lifecycleConfiguration) throws XmlPullParserException, IOException {
        LifecycleConfiguration.AbortIncompleteMultiUpload abortIncompleteMultiUpload;
        LifecycleConfiguration lifecycleConfiguration2 = lifecycleConfiguration;
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        int eventType = xmlPullParserNewPullParser.getEventType();
        lifecycleConfiguration2.rules = new ArrayList();
        LifecycleConfiguration.Rule rule = null;
        LifecycleConfiguration.Filter filter = null;
        LifecycleConfiguration.Transition transition = null;
        LifecycleConfiguration.NoncurrentVersionExpiration noncurrentVersionExpiration = null;
        LifecycleConfiguration.NoncurrentVersionTransition noncurrentVersionTransition = null;
        LifecycleConfiguration.Expiration expiration = null;
        LifecycleConfiguration.AbortIncompleteMultiUpload abortIncompleteMultiUpload2 = null;
        while (eventType != 1) {
            LifecycleConfiguration.AbortIncompleteMultiUpload abortIncompleteMultiUpload3 = abortIncompleteMultiUpload2;
            if (eventType != 2) {
                if (eventType == 3) {
                    String name = xmlPullParserNewPullParser.getName();
                    if (name.equalsIgnoreCase("Rule")) {
                        lifecycleConfiguration2.rules.add(rule);
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        rule = null;
                    } else if (name.equalsIgnoreCase("Filter")) {
                        if (rule != null) {
                            rule.filter = filter;
                        }
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        filter = null;
                    } else if (name.equalsIgnoreCase("Transition")) {
                        if (rule != null) {
                            rule.transition = transition;
                        }
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        transition = null;
                    } else if (name.equalsIgnoreCase("NoncurrentVersionExpiration")) {
                        if (rule != null) {
                            rule.noncurrentVersionExpiration = noncurrentVersionExpiration;
                        }
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        noncurrentVersionExpiration = null;
                    } else if (name.equalsIgnoreCase("NoncurrentVersionTransition")) {
                        if (rule != null) {
                            rule.noncurrentVersionTransition = noncurrentVersionTransition;
                        }
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        noncurrentVersionTransition = null;
                    } else if (name.equalsIgnoreCase("Expiration")) {
                        if (rule != null) {
                            rule.expiration = expiration;
                        }
                        abortIncompleteMultiUpload2 = abortIncompleteMultiUpload3;
                        expiration = null;
                    } else if (name.equalsIgnoreCase("AbortIncompleteMultipartUpload")) {
                        if (rule != null) {
                            rule.abortIncompleteMultiUpload = abortIncompleteMultiUpload3;
                        }
                        abortIncompleteMultiUpload2 = null;
                    }
                }
                abortIncompleteMultiUpload = abortIncompleteMultiUpload3;
                abortIncompleteMultiUpload2 = abortIncompleteMultiUpload;
            } else {
                abortIncompleteMultiUpload = abortIncompleteMultiUpload3;
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Rule")) {
                    rule = new LifecycleConfiguration.Rule();
                } else if (name2.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (rule != null) {
                        rule.f1835id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name2.equalsIgnoreCase("Filter")) {
                    filter = new LifecycleConfiguration.Filter();
                } else if (name2.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (filter != null) {
                        filter.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name2.equalsIgnoreCase("Status")) {
                    xmlPullParserNewPullParser.next();
                    if (rule != null) {
                        rule.status = xmlPullParserNewPullParser.getText();
                    }
                } else if (name2.equalsIgnoreCase("Transition")) {
                    transition = new LifecycleConfiguration.Transition();
                } else if (name2.equalsIgnoreCase("Expiration")) {
                    expiration = new LifecycleConfiguration.Expiration();
                } else if (name2.equalsIgnoreCase(Constants.AnalyticsConstants.DAYS_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (transition != null) {
                        transition.days = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    } else if (rule != null && rule.expiration != null && expiration != null) {
                        expiration.days = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    }
                } else if (name2.equalsIgnoreCase("Date")) {
                    xmlPullParserNewPullParser.next();
                    if (transition != null) {
                        transition.date = xmlPullParserNewPullParser.getText();
                    } else if (expiration != null) {
                        expiration.date = xmlPullParserNewPullParser.getText();
                    }
                } else if (name2.equalsIgnoreCase("ExpiredObjectDeleteMarker")) {
                    xmlPullParserNewPullParser.next();
                    if (expiration != null) {
                        expiration.expiredObjectDeleteMarker = xmlPullParserNewPullParser.getText();
                    }
                } else if (name2.equalsIgnoreCase("AbortIncompleteMultipartUpload")) {
                    abortIncompleteMultiUpload2 = new LifecycleConfiguration.AbortIncompleteMultiUpload();
                } else if (name2.equalsIgnoreCase("DaysAfterInitiation")) {
                    xmlPullParserNewPullParser.next();
                    if (abortIncompleteMultiUpload != null) {
                        abortIncompleteMultiUpload.daysAfterInitiation = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    }
                } else if (name2.equalsIgnoreCase("NoncurrentVersionExpiration")) {
                    noncurrentVersionExpiration = new LifecycleConfiguration.NoncurrentVersionExpiration();
                } else if (name2.equalsIgnoreCase("NoncurrentVersionTransition")) {
                    noncurrentVersionTransition = new LifecycleConfiguration.NoncurrentVersionTransition();
                } else if (name2.equalsIgnoreCase("NoncurrentDays")) {
                    xmlPullParserNewPullParser.next();
                    if (noncurrentVersionExpiration != null) {
                        noncurrentVersionExpiration.noncurrentDays = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    } else if (noncurrentVersionTransition != null) {
                        noncurrentVersionTransition.noncurrentDays = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    }
                } else if (name2.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    if (transition != null) {
                        transition.storageClass = xmlPullParserNewPullParser.getText();
                    } else if (noncurrentVersionTransition != null) {
                        noncurrentVersionTransition.storageClass = xmlPullParserNewPullParser.getText();
                    }
                }
                abortIncompleteMultiUpload2 = abortIncompleteMultiUpload;
            }
            eventType = xmlPullParserNewPullParser.next();
            lifecycleConfiguration2 = lifecycleConfiguration;
        }
    }

    public static void parseLocationConstraint(InputStream inputStream, LocationConstraint locationConstraint) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2 && xmlPullParserNewPullParser.getName().equalsIgnoreCase("LocationConstraint")) {
                xmlPullParserNewPullParser.next();
                locationConstraint.location = xmlPullParserNewPullParser.getText();
            }
        }
    }

    public static void parseDeleteResult(InputStream inputStream, DeleteResult deleteResult) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        deleteResult.errorList = new ArrayList();
        deleteResult.deletedList = new ArrayList();
        DeleteResult.Deleted deleted = null;
        DeleteResult.Error error = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Deleted")) {
                    deleted = new DeleteResult.Deleted();
                } else if (name.equalsIgnoreCase(Constants.ERROR_ROOT_ELEMENT)) {
                    error = new DeleteResult.Error();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    if (deleted != null) {
                        deleted.key = xmlPullParserNewPullParser.getText();
                    } else if (error != null) {
                        error.key = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("VersionId")) {
                    xmlPullParserNewPullParser.next();
                    if (deleted != null) {
                        deleted.versionId = xmlPullParserNewPullParser.getText();
                    } else if (error != null) {
                        error.versionId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DeleteMarker")) {
                    xmlPullParserNewPullParser.next();
                    if (deleted != null) {
                        deleted.deleteMarker = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("DeleteMarkerVersionId")) {
                    xmlPullParserNewPullParser.next();
                    if (deleted != null) {
                        deleted.deleteMarkerVersionId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.ERROR_MESSAGE)) {
                    xmlPullParserNewPullParser.next();
                    if (error != null) {
                        error.message = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Deleted")) {
                    deleteResult.deletedList.add(deleted);
                    deleted = null;
                } else if (name2.equalsIgnoreCase(Constants.ERROR_ROOT_ELEMENT)) {
                    deleteResult.errorList.add(error);
                    error = null;
                }
            }
        }
    }

    public static void parseListBucketVersions(InputStream inputStream, ListBucketVersions listBucketVersions) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        listBucketVersions.objectVersionList = new ArrayList();
        ListBucketVersions.ObjectVersion version = null;
        ListBucketVersions.Owner owner = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase(Constants.NAME_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.name = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.prefix = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("KeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.keyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("VersionIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.versionIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxKeys")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.maxKeys = Long.parseLong(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("NextKeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.nextKeyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextVersionIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listBucketVersions.nextVersionIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("DeleteMarker")) {
                    version = new ListBucketVersions.DeleteMarker();
                } else if (name.equalsIgnoreCase(Constants.AnalyticsConstants.VERSION_ELEMENT)) {
                    version = new ListBucketVersions.Version();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.key = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("VersionId")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.versionId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("IsLatest")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.isLatest = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("LastModified")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null) {
                        version.lastModified = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListBucketVersions.Owner();
                } else if (name.equalsIgnoreCase("UID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.uid = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ETag")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null && (version instanceof ListBucketVersions.Version)) {
                        ((ListBucketVersions.Version) version).eTag = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(BlobConstants.SIZE_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (version != null && (version instanceof ListBucketVersions.Version)) {
                        ((ListBucketVersions.Version) version).size = Long.parseLong(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    if (version != null && (version instanceof ListBucketVersions.Version)) {
                        ((ListBucketVersions.Version) version).storageClass = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Owner")) {
                    if (version != null) {
                        version.owner = owner;
                    }
                    owner = null;
                } else if (name2.equalsIgnoreCase("DeleteMarker") || name2.equalsIgnoreCase(Constants.AnalyticsConstants.VERSION_ELEMENT)) {
                    listBucketVersions.objectVersionList.add(version);
                    version = null;
                }
            }
        }
    }

    public static void parseWebsiteConfig(InputStream inputStream, WebsiteConfiguration websiteConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        websiteConfiguration.routingRules = new ArrayList();
        WebsiteConfiguration.IndexDocument indexDocument = null;
        WebsiteConfiguration.ErrorDocument errorDocument = null;
        WebsiteConfiguration.RedirectAllRequestTo redirectAllRequestTo = null;
        WebsiteConfiguration.RoutingRule routingRule = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("IndexDocument")) {
                    indexDocument = new WebsiteConfiguration.IndexDocument();
                } else if (name.equalsIgnoreCase("Suffix")) {
                    xmlPullParserNewPullParser.next();
                    if (indexDocument != null) {
                        indexDocument.suffix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ErrorDocument")) {
                    errorDocument = new WebsiteConfiguration.ErrorDocument();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    if (errorDocument != null) {
                        errorDocument.key = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("RedirectAllRequestsTo")) {
                    redirectAllRequestTo = new WebsiteConfiguration.RedirectAllRequestTo();
                } else if (name.equalsIgnoreCase("Protocol")) {
                    xmlPullParserNewPullParser.next();
                    if (redirectAllRequestTo != null) {
                        redirectAllRequestTo.protocol = xmlPullParserNewPullParser.getText();
                    } else if (routingRule != null) {
                        routingRule.redirect.protocol = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("RoutingRule")) {
                    routingRule = new WebsiteConfiguration.RoutingRule();
                } else if (name.equalsIgnoreCase("Condition")) {
                    if (routingRule != null) {
                        routingRule.contidion = new WebsiteConfiguration.Contidion();
                    }
                } else if (name.equalsIgnoreCase("HttpErrorCodeReturnedEquals")) {
                    xmlPullParserNewPullParser.next();
                    if (routingRule != null) {
                        routingRule.contidion.httpErrorCodeReturnedEquals = Integer.parseInt(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("KeyPrefixEquals")) {
                    xmlPullParserNewPullParser.next();
                    if (routingRule != null) {
                        routingRule.contidion.keyPrefixEquals = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Redirect")) {
                    if (routingRule != null) {
                        routingRule.redirect = new WebsiteConfiguration.Redirect();
                    }
                } else if (name.equalsIgnoreCase("ReplaceKeyPrefixWith")) {
                    xmlPullParserNewPullParser.next();
                    if (routingRule != null) {
                        routingRule.redirect.replaceKeyPrefixWith = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ReplaceKeyWith")) {
                    xmlPullParserNewPullParser.next();
                    if (routingRule != null) {
                        routingRule.redirect.replaceKeyWith = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("IndexDocument")) {
                    websiteConfiguration.indexDocument = indexDocument;
                    indexDocument = null;
                } else if (name2.equalsIgnoreCase("ErrorDocument")) {
                    websiteConfiguration.errorDocument = errorDocument;
                    errorDocument = null;
                } else if (name2.equalsIgnoreCase("RedirectAllRequestsTo")) {
                    websiteConfiguration.redirectAllRequestTo = redirectAllRequestTo;
                    redirectAllRequestTo = null;
                } else if (name2.equalsIgnoreCase("RoutingRule")) {
                    websiteConfiguration.routingRules.add(routingRule);
                    routingRule = null;
                }
            }
        }
    }

    public static void parseListInventoryConfiguration(InputStream inputStream, ListInventoryConfiguration listInventoryConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        listInventoryConfiguration.inventoryConfigurations = new HashSet(20);
        InventoryConfiguration inventoryConfiguration = null;
        InventoryConfiguration.OptionalFields optionalFields = null;
        InventoryConfiguration.COSBucketDestination cOSBucketDestination = null;
        InventoryConfiguration.Filter filter = null;
        InventoryConfiguration.Schedule schedule = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listInventoryConfiguration.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("ContinuationToken")) {
                    xmlPullParserNewPullParser.next();
                    listInventoryConfiguration.continuationToken = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextContinuationToken")) {
                    xmlPullParserNewPullParser.next();
                    listInventoryConfiguration.nextContinuationToken = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("InventoryConfiguration")) {
                    inventoryConfiguration = new InventoryConfiguration();
                } else if (name.equalsIgnoreCase(Constants.f893ID)) {
                    xmlPullParserNewPullParser.next();
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.f1834id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("IsEnabled")) {
                    xmlPullParserNewPullParser.next();
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.isEnabled = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                    }
                } else if (name.equalsIgnoreCase("COSBucketDestination")) {
                    cOSBucketDestination = new InventoryConfiguration.COSBucketDestination();
                } else if (name.equalsIgnoreCase("Format")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.format = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("AccountId")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.accountId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.bucket = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.prefix = xmlPullParserNewPullParser.getText();
                    } else if (filter != null) {
                        filter.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Encryption")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.encryption = new InventoryConfiguration.Encryption();
                    }
                } else if (name.equalsIgnoreCase("SSE-COS")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.encryption.sSECOS = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Schedule")) {
                    schedule = new InventoryConfiguration.Schedule();
                } else if (name.equalsIgnoreCase("Frequency")) {
                    xmlPullParserNewPullParser.next();
                    if (schedule != null) {
                        schedule.frequency = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Filter")) {
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.filter = new InventoryConfiguration.Filter();
                    }
                } else if (name.equalsIgnoreCase("IncludedObjectVersions")) {
                    xmlPullParserNewPullParser.next();
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.includedObjectVersions = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("OptionalFields")) {
                    optionalFields = new InventoryConfiguration.OptionalFields();
                    optionalFields.fields = new HashSet(6);
                } else if (name.equalsIgnoreCase("Field")) {
                    xmlPullParserNewPullParser.next();
                    if (optionalFields != null) {
                        optionalFields.fields.add(xmlPullParserNewPullParser.getText());
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("COSBucketDestination")) {
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.destination = new InventoryConfiguration.Destination();
                    }
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.destination.cosBucketDestination = cOSBucketDestination;
                    }
                    cOSBucketDestination = null;
                } else if (name2.equalsIgnoreCase("OptionalFields")) {
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.optionalFields = optionalFields;
                    }
                    optionalFields = null;
                } else if (name2.equalsIgnoreCase("Filter")) {
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.filter = filter;
                    }
                    filter = null;
                } else if (name2.equalsIgnoreCase("Schedule")) {
                    if (inventoryConfiguration != null) {
                        inventoryConfiguration.schedule = schedule;
                    }
                    schedule = null;
                } else if (name2.equalsIgnoreCase("InventoryConfiguration")) {
                    listInventoryConfiguration.inventoryConfigurations.add(inventoryConfiguration);
                    inventoryConfiguration = null;
                }
            }
        }
    }

    public static void parseInventoryConfiguration(InputStream inputStream, InventoryConfiguration inventoryConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        InventoryConfiguration.COSBucketDestination cOSBucketDestination = null;
        InventoryConfiguration.OptionalFields optionalFields = null;
        InventoryConfiguration.Filter filter = null;
        InventoryConfiguration.Schedule schedule = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase(Constants.f893ID)) {
                    xmlPullParserNewPullParser.next();
                    inventoryConfiguration.f1834id = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("IsEnabled")) {
                    xmlPullParserNewPullParser.next();
                    inventoryConfiguration.isEnabled = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("COSBucketDestination")) {
                    cOSBucketDestination = new InventoryConfiguration.COSBucketDestination();
                } else if (name.equalsIgnoreCase("Format")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.format = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("AccountId")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.accountId = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.bucket = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.prefix = xmlPullParserNewPullParser.getText();
                    } else if (filter != null) {
                        filter.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Encryption")) {
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.encryption = new InventoryConfiguration.Encryption();
                    }
                } else if (name.equalsIgnoreCase("SSE-COS")) {
                    xmlPullParserNewPullParser.next();
                    if (cOSBucketDestination != null) {
                        cOSBucketDestination.encryption.sSECOS = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Schedule")) {
                    schedule = new InventoryConfiguration.Schedule();
                } else if (name.equalsIgnoreCase("Frequency")) {
                    xmlPullParserNewPullParser.next();
                    if (schedule != null) {
                        schedule.frequency = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Filter")) {
                    filter = new InventoryConfiguration.Filter();
                } else if (name.equalsIgnoreCase("IncludedObjectVersions")) {
                    xmlPullParserNewPullParser.next();
                    inventoryConfiguration.includedObjectVersions = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("OptionalFields")) {
                    optionalFields = new InventoryConfiguration.OptionalFields();
                    optionalFields.fields = new HashSet(6);
                } else if (name.equalsIgnoreCase("Field")) {
                    xmlPullParserNewPullParser.next();
                    if (optionalFields != null) {
                        optionalFields.fields.add(xmlPullParserNewPullParser.getText());
                    }
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("COSBucketDestination")) {
                    inventoryConfiguration.destination = new InventoryConfiguration.Destination();
                    inventoryConfiguration.destination.cosBucketDestination = cOSBucketDestination;
                    cOSBucketDestination = null;
                } else if (name2.equalsIgnoreCase("OptionalFields")) {
                    inventoryConfiguration.optionalFields = optionalFields;
                    optionalFields = null;
                } else if (name2.equalsIgnoreCase("Filter")) {
                    inventoryConfiguration.filter = filter;
                    filter = null;
                } else if (name2.equalsIgnoreCase("Schedule")) {
                    inventoryConfiguration.schedule = schedule;
                    schedule = null;
                }
            }
        }
    }

    public static void parseBucketLoggingStatus(InputStream inputStream, BucketLoggingStatus bucketLoggingStatus) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        BucketLoggingStatus.LoggingEnabled loggingEnabled = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("LoggingEnabled")) {
                    loggingEnabled = new BucketLoggingStatus.LoggingEnabled();
                } else if (name.equalsIgnoreCase("TargetBucket")) {
                    xmlPullParserNewPullParser.next();
                    if (loggingEnabled != null) {
                        loggingEnabled.targetBucket = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("TargetPrefix")) {
                    xmlPullParserNewPullParser.next();
                    if (loggingEnabled != null) {
                        loggingEnabled.targetPrefix = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3 && xmlPullParserNewPullParser.getName().equalsIgnoreCase("LoggingEnabled")) {
                bucketLoggingStatus.loggingEnabled = loggingEnabled;
                loggingEnabled = null;
            }
        }
    }

    public static void parseDomainConfiguration(InputStream inputStream, DomainConfiguration domainConfiguration) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        domainConfiguration.domainRules = new ArrayList();
        DomainConfiguration.DomainRule domainRule = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("DomainRule")) {
                    domainRule = new DomainConfiguration.DomainRule();
                } else if (name.equalsIgnoreCase("Status")) {
                    xmlPullParserNewPullParser.next();
                    if (domainRule != null) {
                        domainRule.status = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.NAME_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (domainRule != null) {
                        domainRule.name = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Type")) {
                    xmlPullParserNewPullParser.next();
                    if (domainRule != null) {
                        domainRule.type = xmlPullParserNewPullParser.getText();
                    }
                }
            } else if (eventType == 3 && xmlPullParserNewPullParser.getName().equalsIgnoreCase("DomainRule")) {
                domainConfiguration.domainRules.add(domainRule);
                domainRule = null;
            }
        }
    }
}
