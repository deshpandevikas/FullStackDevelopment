//package com.raywenderlich.camelot;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//
//import org.json.JSONObject;
//import org.researchstack.backbone.answerformat.AnswerFormat;
//import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
//import org.researchstack.backbone.answerformat.IntegerAnswerFormat;
//import org.researchstack.backbone.model.Choice;
//import org.researchstack.backbone.step.InstructionStep;
//import org.researchstack.backbone.step.QuestionStep;
//import org.researchstack.backbone.step.Step;
//import org.researchstack.backbone.ui.ViewTaskActivity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class DisplaySurveyQuestions extends AppCompatActivity implements getSurveyQuestionsAsync.GetQuestionsFromSurveys{
//    String surveyId;
//    private static final int REQUEST_SURVEY  = 1;
//    SharedPreferences mPrefs;
//    SharedPreferences.Editor prefsEditor;
//    String token;
//    ArrayList<SurveyQs> questionFromSurveysList = new ArrayList<SurveyQs>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        setContentView(R.layout.activity_display_survey_questions);
//
//
//        if(getIntent().getExtras().getString("surveyId")!=null)
//        {
//            surveyId = getIntent().getExtras().getString("surveyId");
//        }
//
//        mPrefs =  getSharedPreferences("hw04",MODE_PRIVATE);
//        prefsEditor = mPrefs.edit();
//        token=mPrefs.getString("token","null");
//
//        new getSurveyQuestionsAsync(this).execute(StudyList.ip+"getSurveyQuestions",token,surveyId);
//    }
//
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        DisplaySurveyQuestions.this.finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }
//
//    @Override
//    public void getQuesFromSurveys(String bodyy) {
//        if(bodyy != null) {
//            Log.d("body",bodyy);
//            System.out.println(bodyy);
//        }
//
//        try
//        {
//            JSONObject jsonData1 = new JSONObject(bodyy);
//            if(jsonData1.length()>0)
//            {
//                questionFromSurveysList = new SurveyQuestionsParser.parserr().parseJsonFunction(jsonData1);
//
//                DisplaySurveyQuestions.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //recViewSaved1.setAdapter(newAdapterSaved);
//                        //newAdapterSaved.notifyDataSetChanged();
//                        buildSurvey();
//                    }
//                });
//                Log.d("data", jsonData1.toString());
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Log.d("error","exxxception occured");
//        }
//    }
//
//    private void buildSurvey()
//    {
//        List<Step> steps = new ArrayList<>();
//
//
//        InstructionStep i1 = new InstructionStep("i1",
//                "Welcome to Survey",
//                "Welcome! We need to collect just a little health information from you before we begin. Select the correct answer.");
//        steps.add(i1);
//
//        AnswerFormat format = new IntegerAnswerFormat(0,10000000);
//
//        for(int i=0;i<questionFromSurveysList.size();i++)
//        {
//            String tempDataTypeValue = questionFromSurveysList.get(i).getDatatype();
//            if(tempDataTypeValue.equals("text"))
//            {
//                String tempQuestionText = questionFromSurveysList.get(i).getQuestionName();
//                String uniqueQuestionId = questionFromSurveysList.get(i).getQid();
//                QuestionStep questionStep = new QuestionStep(uniqueQuestionId,tempQuestionText,format);
//                questionStep.setOptional(false);
//                steps.add(questionStep);
//            }
//            else if(tempDataTypeValue.equals("mcq"))
//            {
//                String uniqueQuestionId = questionFromSurveysList.get(i).getQid();
//                String tempQuestionText = questionFromSurveysList.get(i).getQuestionName();
//                HashMap<Integer,String> tempMap = questionFromSurveysList.get(i).getOptions();
//                AnswerFormat mcqs = null;
//                for(int ii:tempMap.keySet())
//                {
//                    mcqs = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
//                            .SingleChoice,new Choice<>(ii+"", tempMap.get(ii)+""));
//                }
//
//                QuestionStep questionStep = new QuestionStep(uniqueQuestionId,tempQuestionText,mcqs);
//                questionStep.setOptional(false);
//                steps.add(questionStep);
//            }
//        }
//
//        CustomOrderedTask task = new CustomOrderedTask("survey_task", steps);
//
//        Intent intent = ViewTaskActivity.newIntent(this, task);
//        startActivityForResult(intent, REQUEST_SURVEY);
//    }
//
//
//}
