package com.ezrimo.mdamanage;

import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;
/*
* by implementing Parcelable were able to pass objects between activities more easily
* */
public class User implements Parcelable {
    String fullName, email;
    long isAdmin;

    public User(){}


    public User(String fullName, String userEmail, long isAdmin) {
        this.fullName = fullName;
        this.email = userEmail;
        this.isAdmin = isAdmin;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        email = in.readString();
        isAdmin = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    public long getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(long isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(email);
        parcel.writeLong(isAdmin);
    }
}
