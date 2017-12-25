package com.raywenderlich.camelot;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Vikas Deshpande on 12/11/2017.
 */

public class surveyResponseAsync extends AsyncTask<String,Void, String> {

    SetSurveyCallBack mainActivity;

    surveyResponseAsync(QuesAndSurvey mainActivity){
        this.mainActivity = mainActivity;

    }

    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        //MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        //RequestBody formBody = RequestBody.create(JSON,strings[1]);

        RequestBody formBody = new FormBody.Builder()
                .add("surveyResponse", strings[1])
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

            Log.d("Exception Occured",e.getMessage());

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        this.mainActivity.callbackSurverResponse(s);
        super.onPostExecute(s);


    }


    static public interface SetSurveyCallBack{

        public void callbackSurverResponse(String body);

    }
}
