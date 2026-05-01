package com.hjq.permissions;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
final class AndroidManifestInfo {
    ApplicationInfo applicationInfo;
    String packageName;
    UsesSdkInfo usesSdkInfo;
    final List<PermissionInfo> permissionInfoList = new ArrayList();
    final List<ActivityInfo> activityInfoList = new ArrayList();
    final List<ServiceInfo> serviceInfoList = new ArrayList();

    AndroidManifestInfo() {
    }

    static final class UsesSdkInfo {
        int minSdkVersion;

        UsesSdkInfo() {
        }
    }

    static final class PermissionInfo {
        private static final int REQUESTED_PERMISSION_NEVER_FOR_LOCATION;
        int maxSdkVersion;
        String name;
        int usesPermissionFlags;

        PermissionInfo() {
        }

        static {
            if (AndroidVersion.isAndroid12()) {
                REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 65536;
            } else {
                REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 65536;
            }
        }

        boolean neverForLocation() {
            return (this.usesPermissionFlags & REQUESTED_PERMISSION_NEVER_FOR_LOCATION) != 0;
        }
    }

    static final class ApplicationInfo {
        String name;
        boolean requestLegacyExternalStorage;

        ApplicationInfo() {
        }
    }

    static final class ActivityInfo {
        String name;
        boolean supportsPictureInPicture;

        ActivityInfo() {
        }
    }

    static final class ServiceInfo {
        String name;
        String permission;

        ServiceInfo() {
        }
    }
}
