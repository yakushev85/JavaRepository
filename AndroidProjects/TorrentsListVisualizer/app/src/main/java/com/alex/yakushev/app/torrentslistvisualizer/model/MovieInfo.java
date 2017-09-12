package com.alex.yakushev.app.torrentslistvisualizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class MovieInfo implements Parcelable {
    private int id;
    private String url;
    private String title;
    @SerializedName("title_english")
    private String titleEnglish;
    @SerializedName("title_long")
    private String titleLong;
    private int year;
    private float rating;
    private int runtime;
    private List<String> genres;
    private String summary;
    @SerializedName("background_image")
    private String backgroundImage;
    @SerializedName("background_image_orginal")
    private String backgroundImageOriginal;
    @SerializedName("small_cover_image")
    private String smallCoverImage;
    @SerializedName("medium_cover_image")
    private String mediumCoverImage;
    @SerializedName("large_cover_image")
    private String largeCoverImage;

    public MovieInfo() {}

    protected MovieInfo(Parcel in) {
        id = in.readInt();
        url = in.readString();
        title = in.readString();
        titleEnglish = in.readString();
        titleLong = in.readString();
        year = in.readInt();
        rating = in.readFloat();
        runtime = in.readInt();
        summary = in.readString();
        backgroundImage = in.readString();
        backgroundImageOriginal = in.readString();
        smallCoverImage = in.readString();
        mediumCoverImage = in.readString();
        largeCoverImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(titleEnglish);
        dest.writeString(titleLong);
        dest.writeInt(year);
        dest.writeFloat(rating);
        dest.writeInt(runtime);
        dest.writeString(summary);
        dest.writeString(backgroundImage);
        dest.writeString(backgroundImageOriginal);
        dest.writeString(smallCoverImage);
        dest.writeString(mediumCoverImage);
        dest.writeString(largeCoverImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public int getYear() {
        return year;
    }

    public float getRating() {
        return rating;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<String> getGenres() {
        return (genres == null)?new ArrayList<>():genres;
    }

    public String getSummary() {
        return summary;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return backgroundImageOriginal;
    }

    public String getSmallCoverImage() {
        return smallCoverImage;
    }

    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    public String getLargeCoverImage() {
        return largeCoverImage;
    }
}
