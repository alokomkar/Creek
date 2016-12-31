package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok Omkar on 2016-12-31.
 */

public class WikiModel extends RushObject implements Parcelable {

    private String wikiHeader;
    private String wikiId;
    private ArrayList<ProgramWiki> programWikis = new ArrayList<>();

    public WikiModel(String wikiId, String wikiHeader, ArrayList<ProgramWiki> programWikis) {
        this.wikiHeader = wikiHeader;
        this.programWikis = programWikis;
        this.wikiId = wikiId;
    }

    public String getWikiId() {
        return wikiId;
    }

    public void setWikiId(String wikiId) {
        this.wikiId = wikiId;
    }

    public String getWikiHeader() {
        return wikiHeader;
    }

    public void setWikiHeader(String wikiHeader) {
        this.wikiHeader = wikiHeader;
    }

    public ArrayList<ProgramWiki> getProgramWikis() {
        return programWikis;
    }

    public void setProgramWikis(ArrayList<ProgramWiki> programWikis) {
        this.programWikis = programWikis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wikiHeader);
        dest.writeString(this.wikiId);
        dest.writeList(this.programWikis);
    }

    protected WikiModel(Parcel in) {
        this.wikiHeader = in.readString();
        this.wikiId = in.readString();
        this.programWikis = new ArrayList<ProgramWiki>();
        in.readList(this.programWikis, ProgramWiki.class.getClassLoader());
    }

    public static final Parcelable.Creator<WikiModel> CREATOR = new Parcelable.Creator<WikiModel>() {
        @Override
        public WikiModel createFromParcel(Parcel source) {
            return new WikiModel(source);
        }

        @Override
        public WikiModel[] newArray(int size) {
            return new WikiModel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WikiModel wikiModel = (WikiModel) o;

        if (wikiHeader != null ? !wikiHeader.equals(wikiModel.wikiHeader) : wikiModel.wikiHeader != null)
            return false;
        if (wikiId != null ? !wikiId.equals(wikiModel.wikiId) : wikiModel.wikiId != null)
            return false;
        return programWikis != null ? programWikis.equals(wikiModel.programWikis) : wikiModel.programWikis == null;

    }

    @Override
    public int hashCode() {
        int result = wikiHeader != null ? wikiHeader.hashCode() : 0;
        result = 31 * result + (wikiId != null ? wikiId.hashCode() : 0);
        result = 31 * result + (programWikis != null ? programWikis.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WikiModel{" +
                "wikiHeader='" + wikiHeader + '\'' +
                ", wikiId='" + wikiId + '\'' +
                ", programWikis=" + programWikis +
                '}';
    }
}
