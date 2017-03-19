package com.codepath.aaneja.nytsearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by aaneja on 15/03/17.
 */
public class SearchParams implements Parcelable {
    public int Page = 0;
    public boolean AppendToResults = false;

    public String SearchTerm = "";
    public Date BeginDate;
    public String SortOrder; //oldest, newest, <not-applied>
    public List<String> NewsDeskValues;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Page);
        dest.writeByte(this.AppendToResults ? (byte) 1 : (byte) 0);
        dest.writeString(this.SearchTerm);
        dest.writeLong(this.BeginDate != null ? this.BeginDate.getTime() : -1);
        dest.writeString(this.SortOrder);
        dest.writeStringList(this.NewsDeskValues);
    }

    public SearchParams() {
    }

    protected SearchParams(Parcel in) {
        this.Page = in.readInt();
        this.AppendToResults = in.readByte() != 0;
        this.SearchTerm = in.readString();
        long tmpBeginDate = in.readLong();
        this.BeginDate = tmpBeginDate == -1 ? null : new Date(tmpBeginDate);
        this.SortOrder = in.readString();
        this.NewsDeskValues = in.createStringArrayList();
    }

    public static final Parcelable.Creator<SearchParams> CREATOR = new Parcelable.Creator<SearchParams>() {
        @Override
        public SearchParams createFromParcel(Parcel source) {
            return new SearchParams(source);
        }

        @Override
        public SearchParams[] newArray(int size) {
            return new SearchParams[size];
        }
    };
}
