package com.example.administrator.surprisegiftserver.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 24/5/2017.
 */

public class User implements Serializable,Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("sUserName")
    public String userName;
    @SerializedName("sPassWord")
    public String passWord;
    @SerializedName("sEmail")
    public String email;
    @SerializedName("token")
    public String token;
    public User(){}
    protected User(Parcel in) {
        id = in.readInt();
        userName = in.readString();
        passWord = in.readString();
        email = in.readString();
        token=in.readString();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userName);
        dest.writeString(passWord);
        dest.writeString(email);
        dest.writeString(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
