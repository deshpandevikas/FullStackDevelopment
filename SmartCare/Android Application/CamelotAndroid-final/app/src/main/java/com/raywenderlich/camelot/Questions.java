package com.raywenderlich.camelot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Vikas Deshpande on 11/16/2017.
 */

public class Questions {

    public int id;
    public int timeId;
    public String question;
    public String response;
    public HashMap<Integer,String> options;
    public Date dtime;

    public String surveyId;
    public String surveyName;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    String datatype;

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }


    public Questions()
    {

    }

    public Questions(int id, int timeId, String question, String response, HashMap<Integer, String> options, Date dtime, String datatype,String surveyId,String surveyName) {

        this.options = new HashMap<Integer, String>();

        this.id = id;
        this.timeId = timeId;
        this.question = question;
        this.response = response;
        this.options = options;
        this.dtime = dtime;
        this.datatype = datatype;
        this.surveyId = surveyId;
        this.surveyName = surveyName;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public HashMap<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<Integer, String> options) {
        this.options = options;
    }
}
