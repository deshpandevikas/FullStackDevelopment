package com.example.vikasdeshpande.hw1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements RegisterAsync.RegisterCallBack, LoginAsync.LoginCallBack{


    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;
    ProgressDialog pb;
    String uname, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Messageme(Sign Up)");

        final EditText etfname = (EditText) findViewById(R.id.etrfname);
        final EditText etlname = (EditText) findViewById(R.id.etrlname);
        final EditText etuname = (EditText) findViewById(R.id.etrusename);
        final EditText etpass = (EditText) findViewById(R.id.etrpassword);
        final EditText etcpass = (EditText) findViewById(R.id.etrcpassword);

        pb = new ProgressDialog(this);

        mPrefs =  getSharedPreferences("hw01",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        token=mPrefs.getString("token","null");


        Button btnsignup = (Button) findViewById(R.id.btnrsignup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etfname.getText().toString().trim().length()>0)
                {
                    if(etlname.getText().toString().trim().length()>0)
                    {
                        if(etuname.getText().toString().trim().length()>0)
                        {
                            if(etpass.getText().toString().trim().length()>0)
                            {
                                if(etcpass.getText().toString().trim().length()>0)
                                {

                                    if(etpass.getText().toString().equals(etcpass.getText().toString()))
                                    {

                                        String fname = etfname.getText().toString().trim();
                                        String lname = etlname.getText().toString().trim();
                                         uname = etuname.getText().toString().trim();
                                         pass = etpass.getText().toString().trim();


                                        new RegisterAsync(SignUp.this).execute(MainActivity.ip + "register", fname, lname, uname, pass );




                                    }else {
                                        Toast.makeText(getApplicationContext(),"Passwords didnt match",Toast.LENGTH_LONG).show();
                                        etpass.setText("");
                                        etcpass.setText("");
                                    }


                                }else {etcpass.setError("Confirm password is required");}


                            }else {etpass.setError("Passord is required");}


                        }else {etuname.setError("User name is required");}

                    }else {etlname.setError("Last name is required");}

                }else {etfname.setError( "First name is required!" );}



            }
        });



    }

    @Override
    public void callbackR(String body) {
        pb.dismiss();
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if(!jb.getBoolean("success")){

                Toast.makeText(getApplicationContext(), jb.getString("message"),Toast.LENGTH_LONG).show();

            }
            else {


                Toast.makeText(getApplicationContext(), "Registration Successful",Toast.LENGTH_LONG).show();

                new LoginAsync(SignUp.this).execute(MainActivity.ip + "login", uname, pass );

            }


        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(SignUp.this, MainActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }


    @Override
    public void callbackL(String body) {
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
                Intent i = new Intent(SignUp.this, InboxActivity.class);
                finish();
                startActivity(i);
            }


        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}