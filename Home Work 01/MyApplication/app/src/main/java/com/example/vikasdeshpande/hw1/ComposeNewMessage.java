package com.example.vikasdeshpande.hw1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComposeNewMessage extends AppCompatActivity {

    Button btnsendMessage;
    ImageView listOfUsers, location;
    TextView sendMessageTo, setLocation;
    EditText msgbody;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;
    String sendmessageuname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_new_message);
        setTitle("Compose Message");

        //final String ip = "http://34.228.153.214:8080/";

        mPrefs = getSharedPreferences("hw01",MODE_PRIVATE);

        token = mPrefs.getString("token","null");

        String sendtoFname = mPrefs.getString("sendtoFname",null);
        String sendtoUname = mPrefs.getString("sendtoUname",null);
        String sendlocation = mPrefs.getString("sendregion",null);

        btnsendMessage = (Button) findViewById(R.id.btnSendMessage);
        listOfUsers = (ImageView) findViewById(R.id.ivListOfUsers);
        location = (ImageView) findViewById(R.id.ivLocation);
        sendMessageTo = (TextView) findViewById(R.id.tvSendMessageTo);
        setLocation = (TextView) findViewById(R.id.tvLocation);
        msgbody = (EditText) findViewById(R.id.etMessageBody);


        final ArrayList<String> users = new ArrayList<String>();
        final HashMap<String,String> userunamefname = new HashMap<String, String>();
        final ArrayList<String> regions = new ArrayList<String>();

        regions.add("Books Stand");
        regions.add("Woodward 332");
        regions.add("Woodward 333F");


        if(sendtoFname==null && sendtoUname==null && sendlocation==null) {

            final String url = MainActivity.ip + "getusers";

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder().add("token", token)
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
                            JSONArray usersarray = jasonData.getJSONArray("data");

                            prefsEditor = mPrefs.edit();
                            prefsEditor.putString("token", jasonData.getString("token"));
                            prefsEditor.commit();

                            for (int i = 0; i < usersarray.length(); i++) {
                                JSONObject temp = usersarray.getJSONObject(i);
                                userunamefname.put(temp.getString("fname")+" "+temp.getString("lname"),temp.getString("uname"));
                                users.add(temp.getString("fname")+" "+temp.getString("lname"));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
        else {

            listOfUsers.setClickable(false);
            location.setClickable(false);

            listOfUsers.setVisibility(View.GONE);
            location.setVisibility(View.GONE);

            sendMessageTo.setText(sendtoFname);
            setLocation.setText(sendlocation);

            sendmessageuname=sendtoUname;

            prefsEditor = mPrefs.edit();
            prefsEditor.remove("sendtoFname");
            prefsEditor.remove("sendtoUname");
            prefsEditor.remove("sendregion");
            prefsEditor.commit();

        }
        ////

        listOfUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComposeNewMessage.this);

                builder.setTitle("Users");

                final CharSequence[] cs=users.toArray(new CharSequence[users.size()]);

                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendMessageTo.setText(cs[which]);

                        sendmessageuname = userunamefname.get(cs[which].toString());
                    }
                });

                final AlertDialog alert = builder.create();
                alert.show();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ComposeNewMessage.this);

                builder.setTitle("Regions");

                final CharSequence[] cs=regions.toArray(new CharSequence[regions.size()]);

                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setLocation.setText(cs[which]);
                    }
                });

                final AlertDialog alert = builder.create();
                alert.show();

            }
        });

        btnsendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(setLocation.getText().toString().trim().length()>0 && sendMessageTo.getText().toString().trim().length()>0)
                {
                    if(msgbody.getText().toString().trim().length()>0)
                    {

                        final String url = MainActivity.ip+"createmsg";

                        OkHttpClient client = new OkHttpClient();

                        RequestBody formBody = new FormBody.Builder()
                                .add("token",token)
                                .add("receiver",sendmessageuname)
                                .add("message",msgbody.getText().toString().trim())
                                .add("region",setLocation.getText().toString().trim())
                                .build();

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
                                    final JSONObject jasonData = new JSONObject(response.body().string());

                                    prefsEditor = mPrefs.edit();
                                    prefsEditor.putString("token",jasonData.getString("token"));
                                    prefsEditor.commit();

                                    if(jasonData.getBoolean("success"))
                                    {
                                        ComposeNewMessage.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Toast.makeText(getApplicationContext(),"Message sent successfully",Toast.LENGTH_LONG).show();

                                                Intent i = new Intent(ComposeNewMessage.this, InboxActivity.class);
                                                finish();
                                                startActivity(i);


                                            }
                                        });
                                    }
                                    else{

                                        final String mssg = jasonData.getString("message");

                                        ComposeNewMessage.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Toast.makeText(getApplicationContext(),mssg,Toast.LENGTH_LONG).show();

                                                Intent i = new Intent(ComposeNewMessage.this, InboxActivity.class);
                                                finish();
                                                startActivity(i);

                                            }
                                        });


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });



                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Enter some message to send",Toast.LENGTH_LONG).show();
                    }

                }
                else {

                    Toast.makeText(getApplicationContext(),"Fill all the required fields",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ComposeNewMessage.this, InboxActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}