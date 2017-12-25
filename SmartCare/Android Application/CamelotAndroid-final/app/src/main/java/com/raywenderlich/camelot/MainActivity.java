

package com.raywenderlich.camelot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.IntegerAnswerFormat;

import org.researchstack.backbone.model.Choice;

import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;

import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;

import org.researchstack.backbone.ui.ViewTaskActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;




public class MainActivity extends AppCompatActivity implements responseAsync.SetSurveyCallBack, checkSurveyAsync.checkSurvey{
  private static final int REQUEST_SURVEY  = 1;

  private static HashSet<String> medicationSet;
  private static HashSet<String> dietSet;
  private static HashSet<String> physicalSet;
  private static HashSet<String> smokingSet;
  private static HashSet<String> weightSet;
  private static HashSet<String> alcoholSet;
  private static HashSet<String> generalSet;

  static JSONObject medicationJSON;
  static JSONObject dietJSON;
  static JSONObject physicalJSON;
  static JSONObject smokingJSON;
  static JSONObject weightJSON;
  static JSONObject alcoholJSON;
  static JSONObject generalJSON;

  SharedPreferences mPrefs;
  SharedPreferences.Editor prefsEditor;
  String token;

  static String responseSurvery;

  Button surveyButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    //super.onCreate(savedInstanceState, R.layout.activity_main);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //super.onCreateDrawer();

//    drawerLayout = (DrawerLayout) findViewById(R.id.dlayout1);

    initializeGlobals();
    mPrefs =  getSharedPreferences("hw04",MODE_PRIVATE);
    prefsEditor = mPrefs.edit();
    token=mPrefs.getString("token","null");
    surveyButton = (Button)findViewById(R.id.surveyButton);

    surveyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displaySurvey();
      }
    });

    new checkSurveyAsync(this).execute(LoginActivity.ip + "checksurvey", token );
}

  public static void initializeGlobals()
  {
    medicationSet = new HashSet<String>();
    dietSet = new HashSet<String>();
    physicalSet = new HashSet<String>();
    smokingSet = new HashSet<String>();
    weightSet = new HashSet<String>();
    alcoholSet = new HashSet<String>();
    generalSet = new HashSet<String>();

    medicationJSON = new JSONObject();
    dietJSON = new JSONObject();
    physicalJSON = new JSONObject();
    smokingJSON = new JSONObject();
    weightJSON = new JSONObject();
    alcoholJSON = new JSONObject();
    generalJSON = new JSONObject();

  }

  private void displaySurvey()
  {
    List<Step> steps = new ArrayList<>();


    InstructionStep i1 = new InstructionStep("i1",
            "Welcome to Survey",
            "Welcome! We need to collect just a little health information from you before we begin. Select the correct answer.");
    steps.add(i1);



    AnswerFormat questionFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
        .SingleChoice,
        new Choice<>("Yes", 0),
        new Choice<>("No", 1));

    QuestionStep questionStepHighBP = new QuestionStep("q1", "Has your doctor told you that you have high blood pressure?", questionFormat);
    questionStepHighBP.setPlaceholder("Quest");
    questionStepHighBP.setOptional(false);
    steps.add(questionStepHighBP);
    generalSet.add("q1");

    AnswerFormat questionStepMedicationHighBPFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("Yes", 0),
            new Choice<>("No", 1));

    QuestionStep questionStepMedicationHighBP = new QuestionStep("q2", "Has your doctor prescribed medication or pills to treat your high blood pressure?", questionStepMedicationHighBPFormat);
    questionStepMedicationHighBP.setPlaceholder("Quest");
    questionStepMedicationHighBP.setOptional(false);
    steps.add(questionStepMedicationHighBP);
    generalSet.add("q2");

    ///////////////////////////////////////////////////////////////////////////////////////////



    InstructionStep i2 = new InstructionStep("i2",
            "Medication Usage",
            "The following questions ask about your health behavior activities during the past 7 days. For each\n" +
                    "question, select the number of days that you performed that activity in the past 7 days");
    steps.add(i2);



    //Medication Usage

    AnswerFormat bloodPressurePills = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("0", 0),
            new Choice<>("1", 1),
            new Choice<>("2", 2),
            new Choice<>("3", 3),
            new Choice<>("4", 4),
            new Choice<>("5", 5),
            new Choice<>("6", 6),
            new Choice<>("7", 7),
            new Choice<>("I havent been prescribed blood pressure pills.", 8));


    QuestionStep bloodPressurePillsQuest = new QuestionStep("q3", "Take your blood pressure pills?", bloodPressurePills);
    bloodPressurePillsQuest.setPlaceholder("Quest");
    bloodPressurePillsQuest.setOptional(false);
    steps.add(bloodPressurePillsQuest);
    medicationSet.add("q3");

    QuestionStep takeYourBlooodPressureQuest = new QuestionStep("q4", "Take your blood pressure pills at the same time everyday?", bloodPressurePills);
    takeYourBlooodPressureQuest.setPlaceholder("Quest");
    takeYourBlooodPressureQuest.setOptional(false);
    steps.add(takeYourBlooodPressureQuest);
    medicationSet.add("q4");

    QuestionStep recommendedNumberOfPillsQuest = new QuestionStep("q5", "Take the recommended number of blood pressure pills?", bloodPressurePills);
    recommendedNumberOfPillsQuest.setPlaceholder("Quest");
    recommendedNumberOfPillsQuest.setOptional(false);
    steps.add(recommendedNumberOfPillsQuest);
    medicationSet.add("q5");


//////////////////////////////////////////


    InstructionStep i3 = new InstructionStep("i3",
            "Diet",
            "The following questions ask about your health behavior activities during the past 7 days. For each\n" +
                    "question, select the number of days that you performed that activity in the past 7 days");
    steps.add(i3);

    //Diet Section

    AnswerFormat nutsOrPenutButter = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("0", 0),
            new Choice<>("1", 1),
            new Choice<>("2", 2),
            new Choice<>("3", 3),
            new Choice<>("4", 4),
            new Choice<>("5", 5),
            new Choice<>("6", 6),
            new Choice<>("7", 7),
            new Choice<>("I'm allergic to nuts.", 8));


    QuestionStep nutsOrPenutButterQuest = new QuestionStep("q6", "Eat nuts or peanut butter?", nutsOrPenutButter);
    nutsOrPenutButterQuest.setPlaceholder("Quest");
    nutsOrPenutButterQuest.setOptional(false);
    steps.add(nutsOrPenutButterQuest);
    dietSet.add("q6");



    AnswerFormat beansPeasOrLentils = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("0", 0),
            new Choice<>("1", 1),
            new Choice<>("2", 2),
            new Choice<>("3", 3),
            new Choice<>("4", 4),
            new Choice<>("5", 5),
            new Choice<>("6", 6),
            new Choice<>("7", 7));

    QuestionStep beansPeasOrLentilsQuest = new QuestionStep("q7", "Eat beans, peas, or lentils?", beansPeasOrLentils);
    beansPeasOrLentilsQuest.setPlaceholder("Quest");
    beansPeasOrLentilsQuest.setOptional(false);
    steps.add(beansPeasOrLentilsQuest);
    dietSet.add("q7");


    QuestionStep eggsFormatQuestion = new QuestionStep("q8", "Eat eggs?", beansPeasOrLentils);
    eggsFormatQuestion.setPlaceholder("Quest");
    eggsFormatQuestion.setOptional(false);
    steps.add(eggsFormatQuestion);
    dietSet.add("q8");

    QuestionStep picklesOlivesQuest = new QuestionStep("q9", "Eat pickles, olives, or other vegetables in brine?", beansPeasOrLentils);
    picklesOlivesQuest.setPlaceholder("Quest");
    picklesOlivesQuest.setOptional(false);
    steps.add(picklesOlivesQuest);
    dietSet.add("q9");

    QuestionStep fiveVeggiesQuest = new QuestionStep("q10", "Eat five or more servings of fruits and vegetables?", beansPeasOrLentils);
    fiveVeggiesQuest.setPlaceholder("Quest");
    fiveVeggiesQuest.setOptional(false);
    steps.add(fiveVeggiesQuest);
    dietSet.add("q10");

    QuestionStep moreThanOnFruitQuest = new QuestionStep("q11", "Eat more than one serving of fruit (fresh frozen, canned or fruit juice)?", beansPeasOrLentils);
    moreThanOnFruitQuest.setPlaceholder("Quest");
    moreThanOnFruitQuest.setOptional(false);
    steps.add(moreThanOnFruitQuest);
    dietSet.add("q11");

    QuestionStep moreThanOneVeggieQuest = new QuestionStep("q12", "Eat more than one serving of vegetables?", beansPeasOrLentils);
    moreThanOneVeggieQuest.setPlaceholder("Quest");
    moreThanOneVeggieQuest.setOptional(false);
    steps.add(moreThanOneVeggieQuest);
    dietSet.add("q12");

    QuestionStep milkkQuest = new QuestionStep("q13", "Drink milk (in a glass, with cereal, or in coffee, tea or cocoa)?", beansPeasOrLentils);
    milkkQuest.setPlaceholder("Quest");
    milkkQuest.setOptional(false);
    steps.add(milkkQuest);
    dietSet.add("q13");

    QuestionStep broccoliSpinachQuest = new QuestionStep("q14", "Eat broccoli, collard greens, spinach, potatoes, squash or sweet potatoes?", beansPeasOrLentils);
    broccoliSpinachQuest.setPlaceholder("Quest");
    broccoliSpinachQuest.setOptional(false);
    steps.add(broccoliSpinachQuest);
    dietSet.add("q14");

    QuestionStep applesBananasQuest = new QuestionStep("q15", "Eat apples, bananas, oranges, melon or raisins?", beansPeasOrLentils);
    applesBananasQuest.setPlaceholder("Quest");
    applesBananasQuest.setOptional(false);
    steps.add(applesBananasQuest);
    dietSet.add("q15");

    QuestionStep grainsBreadsQuest = new QuestionStep("q16", "Eat whole grain breads, cereals, grits, oatmeal or brown rice?", beansPeasOrLentils);
    grainsBreadsQuest.setPlaceholder("Quest");
    grainsBreadsQuest.setOptional(false);
    steps.add(grainsBreadsQuest);
    dietSet.add("q16");

    ///////////////////////////////////////////

    InstructionStep i4 = new InstructionStep("i4",
            "Physical Activity",
            "The following questions ask about your health behavior activities during the past 7 days. For each\n" +
                    "question, select the number of days that you performed that activity in the past 7 days");
    steps.add(i4);


    //Physical Actiity

    QuestionStep ThirtyMinsActivityQuest = new QuestionStep("q17", "Do at least 30 minutes total of physical activity?", beansPeasOrLentils);
    ThirtyMinsActivityQuest.setPlaceholder("Quest");
    ThirtyMinsActivityQuest.setOptional(false);
    steps.add(ThirtyMinsActivityQuest);
    physicalSet.add("q17");


    QuestionStep specificExerciseActivityQuest = new QuestionStep("q18", "Do a specific exercise activity (such as swimming, walking, or biking) other than what you do around the house or as part of your work?", beansPeasOrLentils);
    specificExerciseActivityQuest.setPlaceholder("Quest");
    specificExerciseActivityQuest.setOptional(false);
    steps.add(specificExerciseActivityQuest);
    physicalSet.add("q18");

    QuestionStep weightActivityQuest = new QuestionStep("q19", "Engage in weight lifting or strength training (other than what you do around\n the house or as part of your work)?", beansPeasOrLentils);
    weightActivityQuest.setPlaceholder("Quest");
    weightActivityQuest.setOptional(false);
    steps.add(weightActivityQuest);
    physicalSet.add("q19");

    QuestionStep heavyLiftingQuest = new QuestionStep("q20", "Do any repeated heavy lifting or pushing/pulling of heavy items either for your job or around the house or garden?", beansPeasOrLentils);
    heavyLiftingQuest.setPlaceholder("Quest");
    heavyLiftingQuest.setOptional(false);
    steps.add(heavyLiftingQuest);
    physicalSet.add("q20");


    ////////////////////////////////////////////////////////////////////////

    InstructionStep i5 = new InstructionStep("i5",
            "Smoking",
            "The following questions ask about your health behavior activities during the past 7 days. For each\n" +
                    "question, select the number of days that you performed that activity in the past 7 days");
    steps.add(i5);
    //Smoking

    QuestionStep smokeCigarQuest = new QuestionStep("q21", "Smoke a cigarette or cigar, even just one puff?", beansPeasOrLentils);
    smokeCigarQuest.setPlaceholder("Quest");
    smokeCigarQuest.setOptional(false);
    steps.add(smokeCigarQuest);
    smokingSet.add("q21");

    QuestionStep stayInARoomQuest = new QuestionStep("q22", "Stay in a room or ride in an enclosed vehicle while someone was smoking?", beansPeasOrLentils);
    stayInARoomQuest.setPlaceholder("Quest");
    stayInARoomQuest.setOptional(false);
    steps.add(stayInARoomQuest);
    smokingSet.add("q22");

    ///////////////////////////////////////////////////////////////////////////////////

    InstructionStep i6 = new InstructionStep("i6",
            "Weight Management",
            "The following questions ask about your efforts to manage your weight during the last 30 days. If\n" +
                    "you were sick during the past month, please think back to the previous month that you were not\n" +
                    "sick. Select the one answer that best describes what you do to lose weight or maintain your weight.i.e., In order to lose weight or maintain my\n" +
                    "weight…");
    steps.add(i6);
    //Weight Management

    AnswerFormat carefulOfWhatIEat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("Strongly Disagree", 1),
            new Choice<>("Disagree", 2),
            new Choice<>("Not Sure", 3),
            new Choice<>("Agree", 4),
            new Choice<>("Strongly Agree", 5));

    QuestionStep carefulOfWhatIEatQuest = new QuestionStep("q23", "I am careful about what I eat.", carefulOfWhatIEat);
    carefulOfWhatIEatQuest.setPlaceholder("Quest");
    carefulOfWhatIEatQuest.setOptional(false);
    steps.add(carefulOfWhatIEatQuest);
    weightSet.add("q23");

    QuestionStep readFullLablesQuest = new QuestionStep("q24", "I read food labels when I grocery shop.", carefulOfWhatIEat);
    readFullLablesQuest.setPlaceholder("Quest");
    readFullLablesQuest.setOptional(false);
    steps.add(readFullLablesQuest);
    weightSet.add("q24");

    QuestionStep exerciseQuest = new QuestionStep("q25", "I exercise in order to lose or maintain weight.", carefulOfWhatIEat);
    exerciseQuest.setPlaceholder("Quest");
    exerciseQuest.setOptional(false);
    steps.add(exerciseQuest);
    weightSet.add("q25");

    QuestionStep cutSugarQuest = new QuestionStep("q26", "I have cut out drinking sugary sodas and sweet tea.", carefulOfWhatIEat);
    cutSugarQuest.setPlaceholder("Quest");
    cutSugarQuest.setOptional(false);
    steps.add(cutSugarQuest);
    weightSet.add("q26");

    QuestionStep eatSmallerPortionsQuest = new QuestionStep("q27", "I eat smaller portions or eat fewer portions.", carefulOfWhatIEat);
    eatSmallerPortionsQuest.setPlaceholder("Quest");
    eatSmallerPortionsQuest.setOptional(false);
    steps.add(eatSmallerPortionsQuest);
    weightSet.add("q27");

    QuestionStep unhealthyFoodsQuest = new QuestionStep("q28", "I have stopped buying or bringing unhealthy foods into my home.", carefulOfWhatIEat);
    unhealthyFoodsQuest.setPlaceholder("Quest");
    unhealthyFoodsQuest.setOptional(false);
    steps.add(unhealthyFoodsQuest);
    weightSet.add("q28");


    QuestionStep cutFoodsWhichArentGoodQuest = new QuestionStep("q29", "I have cut out or limit some foods that I like but that are not good for me.", carefulOfWhatIEat);
    cutFoodsWhichArentGoodQuest.setPlaceholder("Quest");
    cutFoodsWhichArentGoodQuest.setOptional(false);
    steps.add(cutFoodsWhichArentGoodQuest);
    weightSet.add("q29");

    QuestionStep eatLessAtRestaurantsQuest = new QuestionStep("q30", "I eat at restaurants or fast food places less often.", carefulOfWhatIEat);
    eatLessAtRestaurantsQuest.setPlaceholder("Quest");
    eatLessAtRestaurantsQuest.setOptional(false);
    steps.add(eatLessAtRestaurantsQuest);
    weightSet.add("q30");

    QuestionStep substituteHealthyFoodsQuest = new QuestionStep("q31", "I substitute healthier foods for things that I used to eat.", carefulOfWhatIEat);
    substituteHealthyFoodsQuest.setPlaceholder("Quest");
    substituteHealthyFoodsQuest.setOptional(false);
    steps.add(substituteHealthyFoodsQuest);
    weightSet.add("q31");

    QuestionStep modifyRecipesQuest = new QuestionStep("q32", "I have modified my recipes when I cook.", carefulOfWhatIEat);
    modifyRecipesQuest.setPlaceholder("Quest");
    modifyRecipesQuest.setOptional(false);
    steps.add(modifyRecipesQuest);
    weightSet.add("q32");

/////////////

    InstructionStep i7 = new InstructionStep("i7",
            "Alcohol Consumption",
            "The next three questions are about alcohol consumption. A drink of alcohol is\n" +
                    "defined as:\n" +
                    "One, 12 oz. can or bottle of beer;\n" +
                    "One, 4 ounce glass of wine;\n" +
                    "One, 12 oz. can or bottle of wine cooler;\n" +
                    "One mixed drink or cocktail;\n" +
                    "Or 1 shot of hard liquor.");
    steps.add(i7);



    //alcohol

    QuestionStep howManyDaysAlcoholQuest = new QuestionStep("q33", "On average, how many days per week do you drink alcohol?", beansPeasOrLentils);
    howManyDaysAlcoholQuest.setPlaceholder("Quest");
    howManyDaysAlcoholQuest.setOptional(false);
    steps.add(howManyDaysAlcoholQuest);
    alcoholSet.add("q33");

    AnswerFormat format = new IntegerAnswerFormat(0,10000000);

    QuestionStep amountOfAlcoholQuest = new QuestionStep("q34", "On a typical day that you drink alcohol, how many drinks do you have?", format);
    amountOfAlcoholQuest.setPlaceholder("How many drinks per day?");
    amountOfAlcoholQuest.setOptional(false);
    steps.add(amountOfAlcoholQuest);
    alcoholSet.add("q34");


    QuestionStep amountOfAlcoholPerMonthQuest = new QuestionStep("q35", "What is the largest number of drinks that you’ve had on any given day within the last month?", format);
    amountOfAlcoholPerMonthQuest.setPlaceholder("How many drinks in last month?");
    amountOfAlcoholPerMonthQuest.setOptional(false);
    steps.add(amountOfAlcoholPerMonthQuest);
    alcoholSet.add("q35");


    AnswerFormat genderSelection = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle
            .SingleChoice,
            new Choice<>("Male", 1),
            new Choice<>("Female", 2));

    QuestionStep genderQuest = new QuestionStep("q36", "Gender", genderSelection);
    genderQuest.setPlaceholder("Quest");
    genderQuest.setOptional(false);
    steps.add(genderQuest);



    ///////////////////////////////////////////////////////////////////////
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

      new responseAsync(this).execute(LoginActivity.ip + "setsurvey", responseSurvery );
    }
  }


  private void processSurveyResult(TaskResult result)
  {

    JSONObject HScale = new JSONObject();
    int medicationHScale = 0;
    int dietHSScale = 0;
    int physicalHSScale = 0;
    int smokingHScale = 0;
    int weightHScale = 0;
    int alcoholHscale = 1;
    int gender = 0;

    try {


      for (String id : result.getResults().keySet()) {
        StepResult stepResult = result.getStepResult(id);

        String tempRes=null;
        if(stepResult!=null)
          tempRes = stepResult.getResult().toString();


        if (dietSet.contains(id)) {
          dietJSON.put(id, tempRes);

          if(Integer.parseInt(tempRes)!=8)
            dietHSScale = dietHSScale + Integer.parseInt(tempRes);
        }
        else if (medicationSet.contains(id))
        {
          medicationJSON.put(id, tempRes);

          if(Integer.parseInt(tempRes)!=8)
            medicationHScale = medicationHScale + Integer.parseInt(tempRes);
        }
        else if(smokingSet.contains(id))
        {
          smokingJSON.put(id, tempRes);

          smokingHScale = smokingHScale + Integer.parseInt(tempRes);
        }
        else if(alcoholSet.contains(id))
        {
          alcoholJSON.put(id, tempRes);

          alcoholHscale = alcoholHscale *  Integer.parseInt(tempRes);
        }
        else if(physicalSet.contains(id))
        {
          physicalJSON.put(id, tempRes);

          physicalHSScale = physicalHSScale + Integer.parseInt(tempRes);
        }
        else if(weightSet.contains(id))
        {
          weightJSON.put(id, tempRes);

          weightHScale = weightHScale + Integer.parseInt(tempRes);

        }
        else if(generalSet.contains(id))
        {
          generalJSON.put(id, tempRes);

          if(id.equals("q1") && tempRes.equals("1"))
          {
            generalJSON.put("q2","-1");
            medicationJSON.put("q3","-1");
            medicationJSON.put("q4","-1");
            medicationJSON.put("q5","-1");

            medicationHScale = -3;
          }
          else if(id.equals("q2") && tempRes.equals("1"))
          {
            medicationJSON.put("q3","-1");
            medicationJSON.put("q4","-1");
            medicationJSON.put("q5","-1");

            medicationHScale = -3;
          }

        }
        else if(id.equals("q36"))
        {
          gender = Integer.parseInt(tempRes);
        }

      }


      if(medicationHScale==21)
      {
        HScale.put("medication","Adherence");
      }
      else {
        HScale.put("medication","Non Adherence");
      }


      if(dietHSScale<=32)
      {
        HScale.put("diet","Low Diet Quality");
      }
      else if(dietHSScale<=51)
      {
        HScale.put("diet","Medium Diet Quality");
      }
      else
      {
        HScale.put("diet","Adherence");
      }

      if(physicalHSScale>=8)
      {
        HScale.put("physical","Adherence");
      }
      else
      {
        HScale.put("physical","Non Adherence");
      }

      if(smokingHScale==0)
      {
        HScale.put("smoking","Adherence");
      }
      else
      {
        HScale.put("smoking","Non Adherence");
      }

      if(weightHScale>=40)
      {
        HScale.put("weight","Adherence");
      }
      else
      {
        HScale.put("weight","Non Adherence");
      }

      if(gender==1)
      {
        if(alcoholHscale<=14)
        {
          HScale.put("alcohol","Adherence");
        }
        else
        {
          HScale.put("alcohol","Non Adherence");
        }
      }
      else
      {
        if(alcoholHscale<=7)
        {
          HScale.put("alcohol","Adherence");
        }
        else
        {
          HScale.put("alcohol","Non Adherence");
        }
      }

      JSONObject response = new JSONObject();
      response.put("general",generalJSON);
      response.put("smoking",smokingJSON);
      response.put("alcohol",alcoholJSON);
      response.put("weight",weightJSON);
      response.put("physical",physicalJSON);
      response.put("medication",medicationJSON);
      response.put("diet",dietJSON);
      response.put("HScale",HScale);


      Log.d("finalrespons",response.toString());

      response.put("token",token);

      responseSurvery = response.toString();

    }
    catch (Exception e)
    {

    }
  }

  @Override
  public void callbackS(String body)
  {

    JSONObject jb = null;
    try {
      jb = new JSONObject(body);

      if(!jb.getBoolean("success")){



        Toast.makeText(getApplicationContext(), jb.getString("message"),Toast.LENGTH_LONG).show();

      }
      else {

        Toast.makeText(getApplicationContext(),"Succeessfully submitted the survey",Toast.LENGTH_LONG).show();
        surveyButton.setEnabled(false);

        /*Intent i = new Intent(MainActivity.this, MainActivity.class);
        finish();
        startActivity(i);*/

      }


    } catch (JSONException e) {
      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }

    //Toast.makeText(getApplicationContext(),""+jb.toString(),Toast.LENGTH_LONG).show();

  }


  @Override
  public void callbackSurvey(String body)
  {

    JSONObject jb = null;
    try {
      jb = new JSONObject(body);

      if(!jb.getBoolean("success")){



        Toast.makeText(getApplicationContext(), jb.getString("message"),Toast.LENGTH_LONG).show();

      }
      else {

        Toast.makeText(getApplicationContext(),jb.getString("message"),Toast.LENGTH_LONG).show();

        surveyButton.setEnabled(false);

      }


    } catch (JSONException e) {
      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }

    //Toast.makeText(getApplicationContext(),""+jb.toString(),Toast.LENGTH_LONG).show();

  }

}
