package com.luck.picture.lib.magical;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class ViewParams implements Parcelable {
    public static final Parcelable.Creator<ViewParams> CREATOR = new Parcelable.Creator<ViewParams>() { // from class: com.luck.picture.lib.magical.ViewParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewParams createFromParcel(Parcel parcel) {
            return new ViewParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewParams[] newArray(int i) {
            return new ViewParams[i];
        }
    };
    public int height;
    public int left;

    /* JADX INFO: renamed from: top, reason: collision with root package name */
    public int f2360top;
    public int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int i) {
        this.left = i;
    }

    public int getTop() {
        return this.f2360top;
    }

    public void setTop(int i) {
        this.f2360top = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.f2360top);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
    }

    public ViewParams() {
    }

    protected ViewParams(Parcel parcel) {
        this.left = parcel.readInt();
        this.f2360top = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
    }
}
