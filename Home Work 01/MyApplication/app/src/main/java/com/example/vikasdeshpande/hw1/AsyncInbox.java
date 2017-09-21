package com.example.vikasdeshpande.hw1;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sainishanthdilly on 9/17/17.
 */

public class AsyncInbox extends AsyncTask<String,Void,String> {

    InboxCallBack inboxCallBack;

    AsyncInbox(InboxCallBack activity){

        inboxCallBack = activity;
    }


    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("token", strings[1])
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

        this.inboxCallBack.callbackInbox(s);
        super.onPostExecute(s);
    }

    static public interface InboxCallBack{

        public void callbackInbox(String body);

    }
}
