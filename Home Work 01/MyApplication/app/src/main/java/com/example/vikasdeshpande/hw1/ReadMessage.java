package com.example.vikasdeshpande.hw1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.Serializable;

public class ReadMessage extends AppCompatActivity implements AsyncDelete.ReadCallBack{

    TextView msgFrom;
    TextView regionFrom;
    TextView messageBody;
    int id;
    ProgressDialog pd;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;
    String msgfromu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        setTitle("Read Message");
        pd = new ProgressDialog(this);

        mPrefs =  getSharedPreferences("hw01",MODE_PRIVATE);

        token=mPrefs.getString("token","null");

        String msgfromf = getIntent().getExtras().getString("fromFname");
         msgfromu = getIntent().getExtras().getString("fromUname");
        String mb =  getIntent().getExtras().getString("msg");
        String rf= getIntent().getExtras().getString("region");
        id = getIntent().getExtras().getInt("id");

        msgFrom = (TextView) findViewById(R.id.tvMsgFrom);
        regionFrom = (TextView) findViewById(R.id.tvRegionName);
        messageBody = (TextView) findViewById(R.id.tvMessageBody);

        msgFrom.setText(msgfromf);
        regionFrom.setText(rf);
        messageBody.setText(mb);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.readmessageactivity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_delete:
                Toast.makeText(this, "E-mail is discarded", Toast.LENGTH_SHORT)
                        .show();
                pd.show();
                new AsyncDelete(ReadMessage.this).execute(MainActivity.ip+"deletemsg", token, String.valueOf(id));
                break;
            case R.id.action_reply:
                Toast.makeText(this, "Reply", Toast.LENGTH_SHORT)
                        .show();
                Intent composeReply = new Intent(ReadMessage.this,ComposeNewMessage.class);

                prefsEditor = mPrefs.edit();
                prefsEditor.putString("sendtoFname", msgFrom.getText().toString());
                prefsEditor.putString("sendtoUname",msgfromu);
                prefsEditor.putString("sendregion", regionFrom.getText().toString());
                prefsEditor.commit();

                finish();
                startActivity(composeReply);
                break;
            default: break;
        }
        return true;
    }

    @Override
    public void callbackDelete(String delete) {

        pd.dismiss();
        try {
            JSONObject jb = new JSONObject(delete);
            String tk = jb.getString("token");
            boolean flag = jb.getBoolean("success");

            prefsEditor = mPrefs.edit();
            prefsEditor.putString("token", tk);
            prefsEditor.commit();

            if(flag){
                Intent i = new Intent(this, InboxActivity.class);
                finish();
                startActivity(i);

            }
            else{
                Toast.makeText(getApplicationContext(),"Error Deleting: ", Toast.LENGTH_LONG).show();
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(ReadMessage.this, InboxActivity.class);
        finish();
        startActivity(i);
        super.onBackPressed();
    }
}
