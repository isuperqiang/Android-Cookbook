package com.silence.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silence on 2016/1/30 0030.
 */
public class Cook implements Parcelable {
    private int mId;
    private int mTypeId;
    private String mName;
    private String mMaterial;
    private String mMethod;
    private boolean mIsFavorite;
    private String path;

    public Cook() {
    }

    public Cook(int id, int typeId, String name, String material, String method, boolean isFavorite, String path) {
        mId = id;
        mTypeId = typeId;
        mName = name;
        mMaterial = material;
        mMethod = method;
        mIsFavorite = isFavorite;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getTypeId() {
        return mTypeId;
    }

    public void setTypeId(int typeId) {
        this.mTypeId = typeId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getMaterial() {
        return mMaterial;
    }

    public void setMaterial(String material) {
        this.mMaterial = material;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String method) {
        this.mMethod = method;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.mIsFavorite = isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mTypeId);
        dest.writeString(this.mName);
        dest.writeString(this.mMaterial);
        dest.writeString(this.mMethod);
        dest.writeByte(mIsFavorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.path);
    }

    protected Cook(Parcel in) {
        this.mId = in.readInt();
        this.mTypeId = in.readInt();
        this.mName = in.readString();
        this.mMaterial = in.readString();
        this.mMethod = in.readString();
        this.mIsFavorite = in.readByte() != 0;
        this.path = in.readString();
    }

    public static final Parcelable.Creator<Cook> CREATOR = new Parcelable.Creator<Cook>() {
        public Cook createFromParcel(Parcel source) {
            return new Cook(source);
        }

        public Cook[] newArray(int size) {
            return new Cook[size];
        }
    };
}
