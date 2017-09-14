package com.alex.yakushev.app.torrentslistvisualizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class DataMovie implements Parcelable {
    public static final Creator<DataMovie> CREATOR = new Creator<DataMovie>() {
        @Override
        public DataMovie createFromParcel(Parcel in) {
            return new DataMovie(in);
        }

        @Override
        public DataMovie[] newArray(int size) {
            return new DataMovie[size];
        }
    };

    @SerializedName("movie_count")
    private int movieCount;
    @SerializedName("limit")
    private int limit;
    @SerializedName("page_number")
    private int pageNumber;
    @SerializedName("movies")
    private List<MovieInfo> movies;

    public DataMovie() {}

    protected DataMovie(Parcel in) {
        movieCount = in.readInt();
        limit = in.readInt();
        pageNumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieCount);
        parcel.writeInt(limit);
        parcel.writeInt(pageNumber);
    }

    public int getMovieCount() {
        return movieCount;
    }

    public int getLimit() {
        return limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public List<MovieInfo> getMovies() {
        return (movies==null)?Collections.emptyList():movies;
    }
}
