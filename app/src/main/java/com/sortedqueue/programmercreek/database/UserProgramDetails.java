package com.sortedqueue.programmercreek.database;

/**
 * Created by binay on 05/12/16.
 */

public class UserProgramDetails {
    String programLanguage;
    String programId;

    public UserProgramDetails() {
    }

    public UserProgramDetails(String programLanguage, String programId) {
        this.programLanguage = programLanguage;
        this.programId = programId;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "UserProgramDetails{" +
                "programLanguage='" + programLanguage + '\'' +
                ", programId='" + programId + '\'' +
                '}';
    }
}
