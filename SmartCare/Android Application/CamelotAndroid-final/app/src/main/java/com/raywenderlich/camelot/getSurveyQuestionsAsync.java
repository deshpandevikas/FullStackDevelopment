package com.raywenderlich.camelot;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class getSurveyQuestionsAsync extends AsyncTask<String,Void, String> {
    GetQuestionsFromSurveys studyList;

    getSurveyQuestionsAsync(QuesAndSurvey quesAndSurvey){
        this.studyList = quesAndSurvey;
    }

    static public interface GetQuestionsFromSurveys
    {
        public void getQuesFromSurveys(String body);
    }

    @Override
    protected void onPostExecute(String s) {
        this.studyList.getQuesFromSurveys(s);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("token", strings[1])
                .add("surveyid",strings[2])
                .build();

        Request request =new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        Response res = null;
        try {
            res = client.newCall(request).execute();
            return res.body().string();
        }
        catch (Exception e){

            Log.d("excep ques from surveys",e.getMessage());

        }
        return null;
    }
}

