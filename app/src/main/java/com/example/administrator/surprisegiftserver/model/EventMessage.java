package com.example.administrator.surprisegiftserver.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 23/5/2017.
 */

public class EventMessage implements Parcelable {
    private String message;
    private String image;
    private String mp3;
    private String video;
    private int clientId;

    public EventMessage() {
    }

    protected EventMessage(Parcel in) {
        message = in.readString();
        image = in.readString();
        mp3 = in.readString();
        video = in.readString();
        clientId = in.readInt();
    }

    public static final Creator<EventMessage> CREATOR = new Creator<EventMessage>() {
        @Override
        public EventMessage createFromParcel(Parcel in) {
            return new EventMessage(in);
        }

        @Override
        public EventMessage[] newArray(int size) {
            return new EventMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(image);
        dest.writeString(mp3);
        dest.writeString(video);
        dest.writeInt(clientId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
