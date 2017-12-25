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

public class getQuestionsFromStudiesAsync extends AsyncTask<String,Void, String> {

    getQuestionsFromStudiesAsyncCallBack mainActivity;

    getQuestionsFromStudiesAsync(QuesAndSurvey mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("token", strings[1])
                .add("sid", strings[2])
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

            Log.d("getting questions error",e.getMessage());
        }
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        this.mainActivity.callbackFromStudies(s);
        super.onPostExecute(s);


    }


    static public interface getQuestionsFromStudiesAsyncCallBack{

        public void callbackFromStudies(String body);

    }
}
