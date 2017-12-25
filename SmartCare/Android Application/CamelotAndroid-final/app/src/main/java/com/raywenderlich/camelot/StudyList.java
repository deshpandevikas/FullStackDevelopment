package com.raywenderlich.camelot;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudyList extends AppCompatActivity implements ListOfStudiesRecyclerAdapter.ItemClickCallback,GetStudiesListAsync.DataCallBack, registerDeviceAsync.RegisterDevCallBack, unregisterDeviceAsync.UnRegisterDevCallBack
{
    private RecyclerView recViewSaved;
    private ListOfStudiesRecyclerAdapter adapterSaved;

    ListViewStudyList listViewStudyList;
    //ListView listView;

    private QuestionsAdapter newAdapterSaved;

    String token;
    ArrayList<StudiesAttributes> tempList = new ArrayList<StudiesAttributes>();
    ArrayList<Questions> questionstempList = new ArrayList<Questions>();
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static String gcmtoken;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_list);
        setTitle("Studies");


        //new DrawerBuilder().withActivity(this).build();


        ////

        drawerLayout = (DrawerLayout) findViewById(R.id.dlayout1);

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
                        new unregisterDeviceAsync(StudyList.this).execute(LoginActivity.ip + "deleteidforgcm", token, StudyList.gcmtoken );
                        prefsEditor = mPrefs.edit();
                        prefsEditor.remove("token");
                        prefsEditor.remove("notificationToggle");
                        prefsEditor.commit();

                        Intent i = new Intent(StudyList.this,LoginActivity.class);
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

        recViewSaved = (RecyclerView)findViewById(R.id.saved_recyclerStudy);

        //listView = (ListView) findViewById(R.id.lvlist);
        recViewSaved.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        token=mPrefs.getString("token","null");

        if(!token.equals("null")) {
            new GetStudiesListAsync(this).execute(LoginActivity.ip + "getStudiesForMobile", token );
        }



        //////
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Registration success
                    gcmtoken = intent.getStringExtra("token");


                    int notificationToggle = mPrefs.getInt("notificationToggle",-1);
                    if(notificationToggle==-1)
                    {
                        prefsEditor.putInt("notificationToggle", 1);
                        prefsEditor.commit();

                        new registerDeviceAsync(StudyList.this).execute(LoginActivity.ip + "addidforgcm", token, gcmtoken );

                        //Toast.makeText(getApplicationContext(), "GCM token:" + gcmtoken, Toast.LENGTH_LONG).show();

                    }


                } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    //Registration error
                    Toast.makeText(getApplicationContext(), "GCM registration error!!!", Toast.LENGTH_LONG).show();
                } else {
                    //Tobe define
                }
            }
        };

        //Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode) {
            //Check type of error
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                //So notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }
        } else {
            //Start service
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }
        /////



        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ii = new Intent(getApplicationContext(),QuesAndSurvey.class);
                ii.putExtra("token",token);
                ii.putExtra("position",i+"");
                startActivity(ii);
            }
        });*/
    }

    @Override
    public void OnReadClick(int p)
    {
        Intent i = new Intent(getApplicationContext(),QuesAndSurvey.class);
        i.putExtra("token",token);
        i.putExtra("position",tempList.get(p).getSid()+"");
        i.putExtra("nameOfStudy",tempList.get(p).getStudyName());
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    ////
    @Override
    public void callbackUnRegisterDev(String body) {

        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if (!jb.getBoolean("success")) {

                //Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                // Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {

        }
    }

    @Override
    public void callbackRegisterDev(String body) {

        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if (!jb.getBoolean("success")) {

                //Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                //Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            }
        }catch (Exception e) {

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
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
    ////

    @Override
    public void callBackD(String body)
    {
        try
        {
            JSONObject jsonData1 = new JSONObject(body);
            if(jsonData1.length()>0)
            {
                tempList = new StudiesJsonParser.parserr().parseJsonFunction(jsonData1);
                adapterSaved = new ListOfStudiesRecyclerAdapter(tempList, StudyList.this);
                //listViewStudyList = new ListViewStudyList(this,R.layout.custom_saved,tempList);
                //listView.setAdapter(listViewStudyList);
                //listViewStudyList.setNotifyOnChange(true);
                StudyList.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recViewSaved.setAdapter(adapterSaved);
                        adapterSaved.notifyDataSetChanged();
                    }
                });
                Log.d("data", jsonData1.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error","exception occured");
        }
    }

//    @Override
//    public void callbackFromStudies(String body)
//    {
//        try
//        {
//            JSONObject jsonData1 = new JSONObject(body);
//            if(jsonData1.length()>0)
//            {
//                questionstempList = new QuestionsJsonParser.parserr().parseJsonFunction(jsonData1);
//                newAdapterSaved = new QuestionsAdapter(questionstempList, StudyList.this);
//                StudyList.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //recViewSaved.setAdapter(adapterSaved);
//                        adapterSaved.notifyDataSetChanged();
//                    }
//                });
//                Log.d("data", jsonData1.toString());
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Log.d("error","exception occured");
//        }
//    }
}
