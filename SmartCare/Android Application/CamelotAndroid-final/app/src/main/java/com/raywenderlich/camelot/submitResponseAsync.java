package com.raywenderlich.camelot;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Vikas Deshpande on 11/16/2017.
 */

public class submitResponseAsync extends AsyncTask<String,Void, String> {

    submitResponseCallBack mainActivity;

    submitResponseAsync(QuesAndSurvey mainActivity){
        this.mainActivity = mainActivity;

    }


    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("token", strings[1])
                .add("qid", strings[2])
                .add("timeid", strings[3])
                .add("optionid", strings[4])
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
        this.mainActivity.callbackSubmitResponse(s);
        super.onPostExecute(s);


    }


    static public interface submitResponseCallBack{

        public void callbackSubmitResponse(String body);

    }
}

