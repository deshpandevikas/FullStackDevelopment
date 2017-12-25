package com.raywenderlich.camelot;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class StudiesAttributes
{
    String studyName;
    String studyDesc;
    int sid;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getStudyDesc() {
        return studyDesc;
    }

    public void setStudyDesc(String studyDesc) {
        this.studyDesc = studyDesc;
    }
}
