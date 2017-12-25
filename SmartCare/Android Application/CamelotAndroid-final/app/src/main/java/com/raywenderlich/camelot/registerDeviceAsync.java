package com.raywenderlich.camelot;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Vikas Deshpande on 11/14/2017.
 */

public class registerDeviceAsync extends AsyncTask<String,Void, String> {

    RegisterDevCallBack mainActivity;

    registerDeviceAsync(StudyList mainActivity){
        this.mainActivity = mainActivity;

    }

    registerDeviceAsync(ProfileDetails mainActivity){
        this.mainActivity = mainActivity;

    }

    registerDeviceAsync(QuesAndSurvey mainActivity){
        this.mainActivity = mainActivity;

    }




    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("token", strings[1])
                .add("mydevid", strings[2])
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
        this.mainActivity.callbackRegisterDev(s);
        super.onPostExecute(s);


    }


    static public interface RegisterDevCallBack{

        public void callbackRegisterDev(String body);

    }
}

