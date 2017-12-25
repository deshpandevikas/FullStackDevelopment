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

public class GetStudiesListAsync extends AsyncTask<String,Void, String> {
    DataCallBack studyList;

    GetStudiesListAsync(StudyList studyList){
        this.studyList = studyList;
    }

    static public interface DataCallBack
    {
        public void callBackD(String body);
    }

    @Override
    protected void onPostExecute(String s) {
        this.studyList.callBackD(s);
        super.onPostExecute(s);
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

            Log.d("not getting studies",e.getMessage());

        }
        return null;
    }
}
