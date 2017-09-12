package com.alex.yakushev.app.torrentslistvisualizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class GeneralMoviesData implements Parcelable {
    public static final Creator<GeneralMoviesData> CREATOR = new Creator<GeneralMoviesData>() {
        @Override
        public GeneralMoviesData createFromParcel(Parcel in) {
            return new GeneralMoviesData(in);
        }

        @Override
        public GeneralMoviesData[] newArray(int size) {
            return new GeneralMoviesData[size];
        }
    };

    private String status;
    @SerializedName("status_message")
    private String statusMessage;
    private DataMovie data;

    public GeneralMoviesData() {}

    protected GeneralMoviesData(Parcel in) {
        status = in.readString();
        statusMessage = in.readString();
        data = in.readParcelable(DataMovie.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(statusMessage);
        parcel.writeParcelable(data, i);
    }

    public String getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public DataMovie getData() {
        return data;
    }
}
