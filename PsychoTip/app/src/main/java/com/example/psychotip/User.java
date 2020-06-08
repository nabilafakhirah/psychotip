package com.example.psychotip;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private String username;
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String gender;
    private String address;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = "Default Name";
        this.birthday = "01/01/00";
        this.gender = "M";
        this.address = "Province";
    }

    public User(Parcel in) {
        this.username = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.birthday = in.readString();
        this.gender = in.readString();
        this.address = in.readString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeString(this.birthday);
        dest.writeString(this.gender);
        dest.writeString(this.address);
    }
}
