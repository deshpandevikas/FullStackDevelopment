package com.raywenderlich.camelot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDetails extends AppCompatActivity implements profileDetailsAsync.ProfileDetailsCallBack, registerDeviceAsync.RegisterDevCallBack, unregisterDeviceAsync.UnRegisterDevCallBack{

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;

    TextView fname, lname, email, age;
    ToggleButton notifications;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);


        ////

        drawerLayout = (DrawerLayout) findViewById(R.id.dlayout5);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();



             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
              getSupportActionBar().setHomeButtonEnabled(true);



        navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case(R.id.coach):
                        Intent in = new Intent(getApplicationContext(),StudyList.class);
                        finish();
                        startActivity(in);
                        break;
                    //Toast.makeText(NavigationBarActivity.this,"Coach Selected",Toast.LENGTH_LONG).show();
                    case(R.id.myProfile):
                        //Toast.makeText(NavigationBarActivity.this,"My Profile Selected",Toast.LENGTH_LONG).show();
                        Intent inp = new Intent(getApplicationContext(),ProfileDetails.class);
                        startActivity(inp);
                        break;
                    case(R.id.logout):
                        Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                        new unregisterDeviceAsync(ProfileDetails.this).execute(LoginActivity.ip + "deleteidforgcm", token, StudyList.gcmtoken );
                        prefsEditor = mPrefs.edit();
                        prefsEditor.remove("token");
                        prefsEditor.remove("notificationToggle");
                        prefsEditor.commit();

                        Intent i = new Intent(ProfileDetails.this,LoginActivity.class);
                        finish();
                        startActivity(i);
                        break;
                }

                return true;
            }
        });



        ////

        mPrefs =  getSharedPreferences("hw04",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        token=mPrefs.getString("token","null");


        fname = (TextView) findViewById(R.id.fname);
        lname = (TextView) findViewById(R.id.lname);
        email = (TextView) findViewById(R.id.email);
        age = (TextView) findViewById(R.id.age);

        notifications = (ToggleButton) findViewById(R.id.toggleButton);

        if(!token.equals("null")) {
            new profileDetailsAsync(this).execute(LoginActivity.ip + "getuserprofile", token );
        }


        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked)
                {
                    prefsEditor.putInt("notificationToggle", 1);
                    prefsEditor.commit();

                    Log.d("gcmid",StudyList.gcmtoken);
                    new registerDeviceAsync(ProfileDetails.this).execute(LoginActivity.ip + "addidforgcm", token, StudyList.gcmtoken );
                }
                else
                {
                    prefsEditor.putInt("notificationToggle", 0);
                    prefsEditor.commit();

                    new unregisterDeviceAsync(ProfileDetails.this).execute(LoginActivity.ip + "deleteidforgcm", token, StudyList.gcmtoken );
                }

            }
        });
    }

    @Override
    public void callbackpd(String body) {
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if(!jb.getBoolean("success")){



                Toast.makeText(getApplicationContext(), jb.getString("message"),Toast.LENGTH_LONG).show();

            }
            else {


                fname.setText(jb.getString("fname"));
                lname.setText(jb.getString("lname"));
                email.setText(jb.getString("uemail"));
                age.setText(jb.getString("age"));

                int notificationToggle = mPrefs.getInt("notificationToggle",-1);

                if(notificationToggle==0)
                {
                    notifications.setChecked(false);
                }
                else
                {
                    notifications.setChecked(true);
                }


            }






        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(),""+jb.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void callbackRegisterDev(String body) {
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if (!jb.getBoolean("success")) {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {

        }
    }

    @Override
    public void callbackUnRegisterDev(String body) {
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if (!jb.getBoolean("success")) {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
}

