package com.example.vikasdeshpande.hw1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InboxActivity extends AppCompatActivity implements InboxRecyclerAdapter.ItemClickCallback, AsyncInbox.InboxCallBack
{
    private RecyclerView recViewSaved;
    String places;
    private InboxRecyclerAdapter adapterSaved;
    ArrayList<InboxAttributes> appslist = new ArrayList<InboxAttributes>();
    ProgressDialog pb;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;

    private static final Map<String, String> PLACES_BY_BEACONS;
    private static final Map<String, Integer> flicker;
    String beaconKey;
    String current = "";

    int flag=0;


    static {
        Map<String, String> placesByBeacons = new HashMap<>();

        flicker = new HashMap<String, Integer>();

        placesByBeacons.put("1564:1564", "Woodward 333F");
        placesByBeacons.put("15212:31506", "Woodward 332");
        placesByBeacons.put("26535:44799", "Books Stand");

        flicker.put("Woodward 333F",0);
        flicker.put("Woodward 332",0);
        flicker.put("Books Stand",0);
        flicker.put("",0);


        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);

    }

    private String placesNearBeacon(Beacon beacon) {

        beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        Log.d("major",beaconKey);

        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return "";
    }

    private BeaconManager beaconManager;
    private BeaconRegion region;

    RequestBody formBody;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Inbox");

        pb = new ProgressDialog(this);
        recViewSaved = (RecyclerView) findViewById(R.id.saved_recycler);
        recViewSaved.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Fetch messages and populate in appslist
        pb.show();

        mPrefs =  getSharedPreferences("hw01",MODE_PRIVATE);




        token=mPrefs.getString("token","null");

        new AsyncInbox(this).execute(MainActivity.ip +"inbox", token);




        ///////////////////

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {

            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {

                Beacon nearestBeacon = null;
                if(!beacons.isEmpty()) {

                    for (Beacon b : beacons) {

                        Log.d("checkall", b.getMajor() + ":" + b.getMinor());

                        if (PLACES_BY_BEACONS.containsKey(b.getMajor() + ":" + b.getMinor())) {
                            nearestBeacon = b;
                            Log.d("ni", "ndemo2");
                            break;
                        }
                    }

                    if (nearestBeacon == null && !beacons.isEmpty()) {
                        nearestBeacon = beacons.get(0);
                    }


                    places = placesNearBeacon(nearestBeacon);
                }
                else {

                    places = "";
                }

                /////////
                for (String s: flicker.keySet()) {
                    if(s.equals(places))
                        flicker.put(s,flicker.get(s)+1);
                    else
                        flicker.put(s,0);

                }


                if(!(flicker.get(places)>10)) {
                    Log.d("major",flicker.get(places)+"");
                    places = current;
                    Log.d("major",flicker.get(places)+"");
                }
                ///////


                // TODO: update the UI here
                if(!places.equals("") && flicker.get(places)>10){

                    current = places;
                    flicker.put(places,0);

                    if (places.trim().length() == 0) {
                        formBody = new FormBody.Builder()

                                .build();
                    } else {
                        formBody = new FormBody.Builder()
                                .add("region", places)
                                .add("token",token)
                                .build();
                    }

                    String url = MainActivity.ip+"unlockmsg";

                    final Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                            try {

                                JSONObject jasonData = new JSONObject(response.body().string());
                                if (jasonData.getBoolean("success")) {

                                    prefsEditor = mPrefs.edit();
                                    prefsEditor.putString("token", jasonData.getString("token"));
                                    prefsEditor.commit();


                                    new AsyncInbox(InboxActivity.this).execute(MainActivity.ip +"inbox", token);

                                    InboxActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(getApplicationContext(),"Messages of " + places + "region are unlocked now",Toast.LENGTH_LONG).show();

                                        }
                                    });


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                }

            }
        });

        region = new BeaconRegion("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inboxactivity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_composeMessage:
                Toast.makeText(this, "Compose new message selected", Toast.LENGTH_SHORT)
                        .show();
                Intent composeNewEmail = new Intent(InboxActivity.this,ComposeNewMessage.class);
                finish();
                startActivity(composeNewEmail);
                break;
            case R.id.action_refresh:
                Toast.makeText(this, "Inbox Refreshed", Toast.LENGTH_SHORT)
                        .show();
                new AsyncInbox(this).execute(MainActivity.ip +"inbox", token);
                break;
            case R.id.logout:
                Toast.makeText(this, "Logged Out successfully", Toast.LENGTH_SHORT).show();
                prefsEditor = mPrefs.edit();
                prefsEditor.remove("token");
                prefsEditor.commit();

                Intent i = new Intent(InboxActivity.this,MainActivity.class);
                finish();
                startActivity(i);


                break;
            default: break;
        }
        return true;
    }

    @Override
    public void OnReadClick(int p) {
        InboxAttributes inboxAttributes = new InboxAttributes();
        inboxAttributes = appslist.get(p);
        String msgFromUname = inboxAttributes.getSenderUName();
        String msgFromFname = inboxAttributes.getSenderFullName();

        final Intent readMessageActivity = new Intent(this,ReadMessage.class);

        readMessageActivity.putExtra("fromUname",msgFromUname);
        readMessageActivity.putExtra("fromFname",msgFromFname);
        readMessageActivity.putExtra("msg",inboxAttributes.getFullMessage());
        readMessageActivity.putExtra("region",inboxAttributes.getRegion());
        readMessageActivity.putExtra("id",inboxAttributes.getID());



        String url = MainActivity.ip + "markread";

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("id",inboxAttributes.getID()+"")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String x =  e.getMessage();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                try {
                    JSONObject jasonData = new JSONObject(response.body().string());

                    if (jasonData.getBoolean("success")) {


                        prefsEditor = mPrefs.edit();
                        prefsEditor.putString("token", jasonData.getString("token"));
                        prefsEditor.commit();
                        finish();
                        startActivity(readMessageActivity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void callbackInbox(String body) {
        pb.dismiss();
        ArrayList<InboxAttributes> appslist1 = new ArrayList<InboxAttributes>();

        try {
            JSONObject jb = new JSONObject(body);
            String tk = jb.getString("token");



            prefsEditor = mPrefs.edit();
            prefsEditor.putString("token", tk);
            prefsEditor.commit();


            JSONArray arr = jb.getJSONArray("data");

            for(int i=0; i< arr.length(); i++){

                String sendername = arr.getJSONObject(i).getString("sender");
                String senderfullname = arr.getJSONObject(i).getString("fname")+" "+arr.getJSONObject(i).getString("lname");
                String fullMessage  = arr.getJSONObject(i).getString("message");

                String msgSummary = fullMessage.substring(0,Math.min(fullMessage.length(),45));



                boolean send = arr.getJSONObject(i).getInt("locked") == 0 ? false : true;

                boolean read = arr.getJSONObject(i).getInt("isread") == 0 ? false : true;
                int ID = arr.getJSONObject(i).getInt("ID");




                if(fullMessage == null){
                    fullMessage = "";
                }
                String value1 = arr.getJSONObject(i).getString("dtime");
                String region = arr.getJSONObject(i).getString("region");



                DateFormat requiredDateFormat = new SimpleDateFormat("yy/MM/dd");
                DateFormat requiredTimeFormat = new SimpleDateFormat("HH:mm a");


                try {
                    SimpleDateFormat df1= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date result1 = df1.parse(value1);

                    String date1 =requiredDateFormat.format(result1);
                    String time1   =requiredTimeFormat.format(result1);

                    InboxAttributes inboxAttributes = new InboxAttributes();
                    inboxAttributes.setDateSent(date1);
                    inboxAttributes.setFullMessage(fullMessage);
                    inboxAttributes.setMsgSummary(msgSummary);
                    inboxAttributes.setSenderUName(sendername);
                    inboxAttributes.setSenderFullName(senderfullname);
                    inboxAttributes.setTimeSent(time1);
                    inboxAttributes.setLocked(send);
                    inboxAttributes.setRead(read);
                    inboxAttributes.setID(ID);
                    inboxAttributes.setRegion(region);

                    appslist1.add(inboxAttributes);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(flag==0) {
            adapterSaved = new InboxRecyclerAdapter(appslist, InboxActivity.this);
            adapterSaved.setListData(appslist1);
            flag=1;
        }
        else
            adapterSaved.setListData(appslist1);

        recViewSaved.setAdapter(adapterSaved);
        adapterSaved.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }
}
