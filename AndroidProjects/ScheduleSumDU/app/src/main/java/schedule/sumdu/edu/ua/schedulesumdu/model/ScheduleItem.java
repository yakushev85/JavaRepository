package schedule.sumdu.edu.ua.schedulesumdu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Oleksandr on 21-Sep-17.
 */

public class ScheduleItem implements Parcelable {
    @SerializedName("DATE_REG")
    private String dateLesson;
    @SerializedName("NAME_WDAY")
    private String nameWeekDay;
    @SerializedName("NAME_PAIR")
    private String namePair;
    @SerializedName("TIME_PAIR")
    private String timePair;
    @SerializedName("NAME_FIO")
    private String nameTeacher;
    @SerializedName("NAME_AUD")
    private String nameAuditorium;
    @SerializedName("NAME_GROUP")
    private String nameGroup;
    @SerializedName("ABBR_DISC")
    private String abbrDescription;
    @SerializedName("NAME_STUD")
    private String nameStud;
    @SerializedName("REASON")
    private String reason;
    @SerializedName("PUB_DATE")
    private String publicDate;
    @SerializedName("KOD_STUD")
    private String kodStud;
    @SerializedName("KOD_FIO")
    private String kodTeacher;
    @SerializedName("KOD_AUD")
    private String kodAuditorium;
    @SerializedName("KOD_DISC")
    private String kodDescription;
    @SerializedName("INFO")
    private String info;

    public ScheduleItem() {}

    protected ScheduleItem(Parcel in) {
        dateLesson = in.readString();
        nameWeekDay = in.readString();
        namePair = in.readString();
        timePair = in.readString();
        nameTeacher = in.readString();
        nameAuditorium = in.readString();
        nameGroup = in.readString();
        abbrDescription = in.readString();
        nameStud = in.readString();
        reason = in.readString();
        publicDate = in.readString();
        kodStud = in.readString();
        kodTeacher = in.readString();
        kodAuditorium = in.readString();
        kodDescription = in.readString();
        info = in.readString();
    }

    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel in) {
            return new ScheduleItem(in);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };

    public String getDateLesson() {
        return dateLesson;
    }

    public String getNameWeekDay() {
        return nameWeekDay;
    }

    public String getNamePair() {
        return namePair;
    }

    public String getTimePair() {
        return timePair;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public String getNameAuditorium() {
        return nameAuditorium;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public String getAbbrDescription() {
        return abbrDescription;
    }

    public String getNameStud() {
        return nameStud;
    }

    public String getReason() {
        return reason;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public String getKodStud() {
        return kodStud;
    }

    public String getKodTeacher() {
        return kodTeacher;
    }

    public String getKodAuditorium() {
        return kodAuditorium;
    }

    public String getKodDescription() {
        return kodDescription;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateLesson);
        parcel.writeString(nameWeekDay);
        parcel.writeString(namePair);
        parcel.writeString(timePair);
        parcel.writeString(nameTeacher);
        parcel.writeString(nameAuditorium);
        parcel.writeString(nameGroup);
        parcel.writeString(abbrDescription);
        parcel.writeString(nameStud);
        parcel.writeString(reason);
        parcel.writeString(publicDate);
        parcel.writeString(kodStud);
        parcel.writeString(kodTeacher);
        parcel.writeString(kodAuditorium);
        parcel.writeString(kodDescription);
        parcel.writeString(info);
    }

    @Override
    public String toString() {
        return dateLesson + "\n" +
                nameWeekDay + "\n" +
                namePair + "\n" +
                timePair + "\n" +
                nameTeacher + "\n" +
                nameAuditorium + "\n" +
                nameGroup + "\n" +
                abbrDescription + "\n" +
                nameStud + "\n" +
                reason;
    }
}
