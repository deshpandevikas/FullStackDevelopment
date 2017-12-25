package com.raywenderlich.camelot;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class SurveyQs implements Parcelable
{
    String qid;

    String datatype;

    String questionName;

    public HashMap<Integer,String> options;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public HashMap<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<Integer, String> options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(qid);
        parcel.writeString(datatype);
        parcel.writeString(questionName);
        Bundle b = new Bundle();
        //b.pu
        //parcel.write(options);

    }
}
