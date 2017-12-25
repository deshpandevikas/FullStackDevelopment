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
 * Created by Vikas Deshpande on 11/8/2017.
 */

public class responseAsync extends AsyncTask<String,Void, String> {

    SetSurveyCallBack mainActivity;

    responseAsync(MainActivity mainActivity){
        this.mainActivity = mainActivity;

    }




    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody formBody = RequestBody.create(JSON,strings[1]);


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
        this.mainActivity.callbackS(s);
        super.onPostExecute(s);


    }


    static public interface SetSurveyCallBack{

        public void callbackS(String body);

    }
}
