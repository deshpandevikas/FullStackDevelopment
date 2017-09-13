package com.example.vikasdeshpande.inclass02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class MainActivity extends AppCompatActivity {

    private static final Map<String, String> PLACES_BY_BEACONS;



    ArrayList<Shopping> appslist = new ArrayList<Shopping>();
    private RecyclerView recViewSaved;
    private RecyclerAdapter adapterSaved;
    String beaconKey;
    String current = "";

    static {
        Map<String, String> placesByBeacons = new HashMap<>();
        placesByBeacons.put("15212:31506", "grocery");
        placesByBeacons.put("48071:25324", "lifestyle");
        placesByBeacons.put("26535:44799", "produce");

        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);


    }

    private String placesNearBeacon(Beacon beacon) {

        beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        Log.d("major",beaconKey+" is major");

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
        setContentView(R.layout.activity_main);
        recViewSaved = (RecyclerView)findViewById(R.id.saved_recycler);
        recViewSaved.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        final String url = "http://34.203.225.11:8080/shop";

         client = new OkHttpClient();

        formBody = new FormBody.Builder().build();
        Request request =new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                try {
                    JSONArray jasonData = new JSONArray(response.body().string());
                    if(jasonData.length()>0) {


                        appslist = new JsonParser.ShoppingJSONParser().parseJsonFunction(jasonData);

                        adapterSaved = new RecyclerAdapter(appslist, MainActivity.this);


                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recViewSaved.setAdapter(adapterSaved);
                                adapterSaved.notifyDataSetChanged();


                            }
                        });


                        Log.d("sd", jasonData.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        ///////////////////



        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {

            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {

                if (!beacons.isEmpty()) {
                    Beacon nearestBeacon = null;
                    for (Beacon b : beacons) {

                        Log.d("checkmajor",b.getMajor() + ":" + b.getMinor());

                        if (PLACES_BY_BEACONS.containsKey(b.getMajor() + ":" + b.getMinor())) {
                            nearestBeacon = b;
                            Log.d("ni", "ndemo2");
                            break;
                        }
                    }

                    if (nearestBeacon == null) {
                        nearestBeacon = beacons.get(0);
                    }



                    String places = placesNearBeacon(nearestBeacon);
                    // TODO: update the UI here
                    if(!places.equals(current)){

                        current = places;

                    if (places.trim().length() == 0) {
                        formBody = new FormBody.Builder()

                                .build();
                    } else {
                        formBody = new FormBody.Builder()
                                .add("region", places)
                                .build();
                    }


                    final Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            Log.d("f", response.toString());

                            Log.d("sdd", response.body().contentType().toString());

                            Log.d("sdd", response.body().toString());

                            try {
                                JSONArray jasonData = new JSONArray(response.body().string());
                                if (jasonData.length() > 0) {


                                    appslist = new JsonParser.ShoppingJSONParser().parseJsonFunction(jasonData);


                                    adapterSaved.setListData(appslist);


                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recViewSaved.setAdapter(adapterSaved);
                                            adapterSaved.notifyDataSetChanged();


                                        }
                                    });


                                    Log.d("sd", jasonData.toString());

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });


                }

                    Log.d("Airport", "Nearest places: " + places);
                }

            }
        });

        region = new BeaconRegion("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);


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
