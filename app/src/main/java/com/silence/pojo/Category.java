package com.silence.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silence on 2016/1/30 0030.
 */
public class Category implements Parcelable {
    private int mId;
    private String mTitle;
    private String mDescription;

    public Category() {
    }

    public Category(int id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
    }

    protected Category(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
