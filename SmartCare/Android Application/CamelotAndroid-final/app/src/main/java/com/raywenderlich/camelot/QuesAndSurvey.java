package com.raywenderlich.camelot;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.IntegerAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.ui.ViewTaskActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import co.touchlab.squeaky.stmt.query.In;

public class QuesAndSurvey extends Activity implements
        getQuestionsFromStudiesAsync.getQuestionsFromStudiesAsyncCallBack,
        QuestionsAdapter.ItemClickCallback,
        getSurveyQuestionsAsync.GetQuestionsFromSurveys,
        surveyResponseAsync.SetSurveyCallBack,
        submitResponseAsync.submitResponseCallBack,
        QuestionsAsync.QuestionsCallBack,
        registerDeviceAsync.RegisterDevCallBack,
        unregisterDeviceAsync.UnRegisterDevCallBack
{
    ArrayList<Questions> questionstempList = new ArrayList<Questions>();
    private static final int REQUEST_SURVEY  = 1;
    ArrayList<SurveyQs> questionFromSurveysList = new ArrayList<SurveyQs>();
    private QuestionsAdapter newAdapterSaved;
    private RecyclerView recViewSaved1;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    String token;
    static String responseSurvery;
    String surveyId;

    String pos1="";
    String nameOfStudy = "";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_and_survey);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                new getQuestionsFromStudiesAsync(QuesAndSurvey.this).execute(LoginActivity.ip+"getQuestionsForMobile",token,pos1);

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mPrefs =  getSharedPreferences("hw04",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        token=mPrefs.getString("token","null");

        //String token="";



        recViewSaved1 = (RecyclerView) findViewById(R.id.saved_recycler1);
        recViewSaved1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        ////

        drawerLayout = (DrawerLayout) findViewById(R.id.dlayout2);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //      getSupportActionBar().setHomeButtonEnabled(true);



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
                        new unregisterDeviceAsync(QuesAndSurvey.this).execute(LoginActivity.ip + "deleteidforgcm", token, StudyList.gcmtoken );
                        prefsEditor = mPrefs.edit();
                        prefsEditor.remove("token");
                        prefsEditor.remove("notificationToggle");
                        prefsEditor.commit();

                        Intent i = new Intent(QuesAndSurvey.this,LoginActivity.class);
                        finish();
                        startActivity(i);
                        break;
                }

                return true;
            }
        });



        ////


        TextView tvnameOfStudy = (TextView) findViewById(R.id.tvTextNameStudy);
        if(getIntent().getStringExtra("token")!=null)
        {
            token = getIntent().getStringExtra("token");
            pos1 = getIntent().getStringExtra("position");
            nameOfStudy = getIntent().getStringExtra("nameOfStudy");
            new getQuestionsFromStudiesAsync(this).execute(LoginActivity.ip+"getQuestionsForMobile",token,pos1);

            tvnameOfStudy.setText("Questions of Study: "+nameOfStudy);


        }
        Log.d("check","ola " + pos1 + token);

    }

    @Override
    public void callbackFromStudies(String body) {

        try
        {
            JSONObject jsonData1 = new JSONObject(body);
            if(jsonData1.length()>0)
            {
                questionstempList = new QuestionsJsonParser.parserr().parseJsonFunction(jsonData1);

                if(questionstempList.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"You have no questions for this study. Redirecting to all studies page",Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(QuesAndSurvey.this,StudyList.class);
                    finish();
                    startActivity(ii);
                }

                newAdapterSaved = new QuestionsAdapter(questionstempList, QuesAndSurvey.this);
                //newAdapterSaved.setListData(questionstempList);

                QuesAndSurvey.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recViewSaved1.setAdapter(newAdapterSaved);
                        newAdapterSaved.notifyDataSetChanged();
                    }
                });
                Log.d("data", jsonData1.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error","exxxception occured");
        }
        Collections.sort(questionstempList,new Comparator<Questions>() {
            @Override
            public int compare(Questions t1, Questions t2) {
                if(t1.getResponse().equals("yes") && t2.getResponse().equals("no"))
                {
                    return 1;
                }
                else if(t1.getResponse().equals("no") && t2.getResponse().equals("yes"))
                {
                    return -1;
                }
                else{
                    return t1.getDtime().compareTo(t2.getDtime()) * -1;
                }
            }
        });

        //recViewSaved1.setAdapter(newAdapterSaved);
        //
        //newAdapterSaved.notifyDataSetChanged();
    }

    @Override
    public void OnReadClick(int qid, int itemid, int optionid)
    {
        new submitResponseAsync(this).execute(LoginActivity.ip +"submitresponse", token,qid+"",itemid+"",optionid+"");
    }

    @Override
    public void callbackSubmitResponse(String body) {
        JSONObject jb = null;
        try {
            jb = new JSONObject(body);

            if (!jb.getBoolean("success")) {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(), jb.getString("message"), Toast.LENGTH_LONG).show();

                new getQuestionsFromStudiesAsync(this).execute(LoginActivity.ip+"getQuestionsForMobile",token,pos1);


            }
        }catch (Exception e) {

        }

    }

    @Override
    public void callbackQuestions(String body) {

    }

    @Override
    public void onSurveyClick(int pos) {
        surveyId = questionstempList.get(pos).getSurveyId();
//        Intent o = new Intent(QuesAndSurvey.this, DisplaySurveyQuestions.class);
//
//        o.putExtra("surveyId", surveyId);
//        finish();
//        startActivity(o);

        new getSurveyQuestionsAsync(this).execute(LoginActivity.ip+"getSurveyQuestions",token,surveyId+"");
    }

    @Override
    public void getQuesFromSurveys(String bodyy) {
        if(bodyy != null) {
            Log.d("body",bodyy);
            System.out.println(bodyy);
        }

        try
        {
            JSONObject jsonData1 = new JSONObject(bodyy);
            if(jsonData1.length()>0)
            {
                questionFromSurveysList = new SurveyQuestionsParser.parserr().parseJsonFunction(jsonData1);

                QuesAndSurvey.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //recViewSaved1.setAdapter(newAdapterSaved);
                        //newAdapterSaved.notifyDataSetChanged();
                        buildSurvey();
                    }
                });
                Log.d("data", jsonData1.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error","exxxception occured");
        }
    }

    private void buildSurvey()
    {
        List<Step> steps = new ArrayList<>();
        
        InstructionStep i1 = new InstructionStep("i1",
                "Welcome to Survey",
                "Welcome! We need to collect just a little health information from you before we begin. Select the correct answer.");
        steps.add(i1);

        AnswerFormat format = new TextAnswerFormat();

        for(int i=0;i<questionFromSurveysList.size();i++)
        {
            String tempDataTypeValue = questionFromSurveysList.get(i).getDatatype();
            if(tempDataTypeValue.equals("text"))
            {
                String tempQuestionText = questionFromSurveysList.get(i).getQuestionName();
                String uniqueQuestionId = questionFromSurveysList.get(i).getQid();
                QuestionStep questionStep = new QuestionStep(uniqueQuestionId,tempQuestionText,format);
                questionStep.setOptional(false);
                steps.add(questionStep);
            }
            else if(tempDataTypeValue.equals("mcq"))
            {
                String uniqueQuestionId = questionFromSurveysList.get(i).getQid();
                String tempQuestionText = questionFromSurveysList.get(i).getQuestionName();
                HashMap<Integer,String> tempMap = new HashMap<Integer, String>();
                tempMap = questionFromSurveysList.get(i).getOptions();
                AnswerFormat mcqs = null;
                Choice<String>[] arrayChoices = new Choice[tempMap.size()];



                int size = tempMap.size();

                int tempii=0;

                TreeMap<Integer,String> tMap = new TreeMap<Integer, String>(tempMap);

                for(Integer ii: tMap.keySet())
                {
                    arrayChoices[tempii] = new Choice(tMap.get(ii),ii);
                    tempii++;
                }

               /* for(int ii=0;ii<size;ii++)
                {



                    //arrayChoices[ii] = new Choice(ii+"",tempMap.get(ii+1));
                }*/

                mcqs = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
                        .SingleChoice,arrayChoices);




                QuestionStep questionStep = new QuestionStep(uniqueQuestionId,tempQuestionText,mcqs);
                questionStep.setOptional(false);
                steps.add(questionStep);
            }
        }

        CustomOrderedTask task = new CustomOrderedTask("survey_task", steps);

        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_SURVEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_SURVEY && resultCode == RESULT_OK)
        {
            processSurveyResult((TaskResult)data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));

            //new surveyResponseAsync(this).execute(LoginActivity.ip + "addSurveyAnswers", responseSurvery );

            new getQuestionsFromStudiesAsync(this).execute(LoginActivity.ip+"getQuestionsForMobile",token,pos1);
        }
    }

    private void processSurveyResult(TaskResult result)
    {
        JSONArray jsonArray = new JSONArray();
        try
        {
            for(String id:result.getResults().keySet())
            {
                JSONObject obj = new JSONObject();

                StepResult stepResult = result.getStepResult(id);

                String tempRes=null;
                if(stepResult!=null)
                {
                    if(stepResult.getAnswerFormat().getQuestionType().toString().equals("SingleChoice"))
                    {
                        
                        obj.put("questionType","mcq");


                        for(int k=0;k<questionFromSurveysList.size();k++)
                        {
                            if(questionFromSurveysList.get(k).getQid().equals(id))
                            {
                                HashMap<Integer,String> tempOptionsMap = questionFromSurveysList.get(k).getOptions();

                                for(Integer choiceId : tempOptionsMap.keySet())
                                {
                                    if((choiceId+"").equals(stepResult.getResult().toString()))
                                    {
                                        obj.put("choiceId",choiceId);
                                    }
                                }
                            }
                        }


                    }
                    else
                    {
                        obj.put("questionType","text");
                        tempRes = stepResult.getResult().toString();
                        obj.put("responseText",tempRes);
                    }


                    obj.put("qid",id);


                    jsonArray.put(obj);
                }



            }
            JSONObject obj1 = new JSONObject();
            obj1.put("token",token);
            obj1.put("surveyid",surveyId);
            obj1.put("resultArray",jsonArray);

            responseSurvery = obj1.toString();

            Log.d("responseSurvey",responseSurvery);

            new surveyResponseAsync(this).execute(LoginActivity.ip + "addSurveyAnswers", responseSurvery );

        }
        catch(Exception e)
        {
            Log.d("result","no response from surveys");
        }
    }

    @Override
    public void callbackSurverResponse(String body)
    {
        if(body != null) {
            Log.d("body",body);
            System.out.println(body);
        }

        try{
            JSONObject object = new JSONObject(body);
            Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();

            new getQuestionsFromStudiesAsync(this).execute(LoginActivity.ip+"getQuestionsForMobile",token,pos1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("error","exxxception occured");
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



}
