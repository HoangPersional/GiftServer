package com.example.administrator.surprisegiftserver.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 23/5/2017.
 */

public class Client implements Parcelable {
    @SerializedName("id")
    public int Id;
    @SerializedName("iIdUserName")
    public int iIdUserName;
    @SerializedName("sName")
    public String name;
    @SerializedName("sToken")
    public String sToken;
    @SerializedName("dCreate")
    public String dCreate;
    @SerializedName("sDescription")
    public String sDescription;
    public Client()
    {

    }
    protected Client(Parcel in) {
        Id = in.readInt();
        iIdUserName = in.readInt();
        name = in.readString();
        sToken = in.readString();
        dCreate = in.readString();
        sDescription = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(iIdUserName);
        dest.writeString(name);
        dest.writeString(sToken);
        dest.writeString(dCreate);
        dest.writeString(sDescription);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getiIdUserName() {
        return iIdUserName;
    }

    public void setiIdUserName(int iIdUserName) {
        this.iIdUserName = iIdUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public String getdCreate() {
        return dCreate;
    }

    public void setdCreate(String dCreate) {
        this.dCreate = dCreate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
}
