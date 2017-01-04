package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import co.uk.rushorm.core.RushObject;

/**
 * Created by Alok on 04/01/17.
 */

public class ProgramTable extends RushObject implements Parcelable {

    private int index;
    private int line_No;
    private String program_Language;
    private String program_Line;
    private String program_Line_Description;

    public ProgramTable() {
    }

    public ProgramTable(int index, int line_No, String program_Language, String program_Line, String program_Line_Description) {
        this.index = index;
        this.line_No = line_No;
        this.program_Language = program_Language;
        this.program_Line = program_Line;
        this.program_Line_Description = program_Line_Description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLine_No() {
        return line_No;
    }

    public void setLine_No(int line_No) {
        this.line_No = line_No;
    }

    public String getProgram_Language() {
        return program_Language;
    }

    public void setProgram_Language(String program_Language) {
        this.program_Language = program_Language;
    }

    public String getProgram_Line() {
        return program_Line;
    }

    public void setProgram_Line(String program_Line) {
        this.program_Line = program_Line;
    }

    public String getProgram_Line_Description() {
        return program_Line_Description;
    }

    public void setProgram_Line_Description(String program_Line_Description) {
        this.program_Line_Description = program_Line_Description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramTable that = (ProgramTable) o;

        if (index != that.index) return false;
        if (line_No != that.line_No) return false;
        if (program_Language != null ? !program_Language.equals(that.program_Language) : that.program_Language != null)
            return false;
        if (program_Line != null ? !program_Line.equals(that.program_Line) : that.program_Line != null)
            return false;
        return program_Line_Description != null ? program_Line_Description.equals(that.program_Line_Description) : that.program_Line_Description == null;

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + line_No;
        result = 31 * result + (program_Language != null ? program_Language.hashCode() : 0);
        result = 31 * result + (program_Line != null ? program_Line.hashCode() : 0);
        result = 31 * result + (program_Line_Description != null ? program_Line_Description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProgramTable{" +
                "index=" + index +
                ", line_No=" + line_No +
                ", program_Language='" + program_Language + '\'' +
                ", program_Line='" + program_Line + '\'' +
                ", program_Line_Description='" + program_Line_Description + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeInt(this.line_No);
        dest.writeString(this.program_Language);
        dest.writeString(this.program_Line);
        dest.writeString(this.program_Line_Description);
    }

    protected ProgramTable(Parcel in) {
        this.index = in.readInt();
        this.line_No = in.readInt();
        this.program_Language = in.readString();
        this.program_Line = in.readString();
        this.program_Line_Description = in.readString();
    }

    public static final Parcelable.Creator<ProgramTable> CREATOR = new Parcelable.Creator<ProgramTable>() {
        @Override
        public ProgramTable createFromParcel(Parcel source) {
            return new ProgramTable(source);
        }

        @Override
        public ProgramTable[] newArray(int size) {
            return new ProgramTable[size];
        }
    };
}
