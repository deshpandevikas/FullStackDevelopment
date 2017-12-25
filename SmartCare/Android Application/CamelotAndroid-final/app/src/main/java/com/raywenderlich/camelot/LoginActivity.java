package com.raywenderlich.camelot;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener,LoginAsync.LoginCallBack {


    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;
    ProgressDialog pb;


    //static String ip = "http://34.235.151.74:8080/";

    static String ip = "http://18.216.218.221:1337/";
    EditText etusername,etpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pb = new ProgressDialog(this);

        mPrefs =  getSharedPreferences("hw04",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        token=mPrefs.getString("token","null");

        if(!token.equals("null"))
        {
            Intent i = new Intent(getApplicationContext(),StudyList.class);
            finish();
            startActivity(i);
        }

        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        //Button btnnewuser = (Button) findViewById(R.id.btnnewuser);

        etusername = (EditText) findViewById(R.id.etusename);
        etpassword = (EditText) findViewById(R.id.etpassword);

        btnlogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        pb.show();
        String uname = etusername.getText().toString().toLowerCase();
        String pwd = etpassword.getText().toString();
        if (!(uname.trim().length() == 0 ||pwd.trim().length() == 0)){

            new LoginAsync(this).execute(ip + "userlogin", uname, pwd );
        }
        else{
            Toast.makeText(getApplicationContext(),"Enter uname and password",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void callbackL(String body)
    {

        pb.dismiss();
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if(!jb.getBoolean("success")){



                Toast.makeText(getApplicationContext(), jb.getString("message"),Toast.LENGTH_LONG).show();

            }
            else {


                prefsEditor.putString("token", jb.getString("token"));
                prefsEditor.commit();

                Intent i = new Intent(LoginActivity.this, StudyList.class);
                finish();
                startActivity(i);

            }






        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(),""+jb.toString(),Toast.LENGTH_LONG).show();

    }



}

