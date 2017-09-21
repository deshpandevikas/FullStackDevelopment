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

public class RegisterAsync extends AsyncTask<String,Void, String> {

    RegisterCallBack signupActivity;

    RegisterAsync(SignUp signupActivity){
        this.signupActivity = signupActivity;

    }



    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        String url = strings[0];

        RequestBody formBody = new FormBody.Builder()
                .add("fname", strings[1])
                .add("lname", strings[2])
                .add("uname", strings[3])
                .add("pwd", strings[4])
                .build();

        Request request =new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response res = null;
        try {
            res = client.newCall(request).execute();
            return  res.body().string();


        }
        catch (Exception e){

            Log.d("Exception Occured",e.getMessage());

        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        this.signupActivity.callbackR(s);
        super.onPostExecute(s);


    }


    static public interface RegisterCallBack{

        public void callbackR(String body);

    }
}