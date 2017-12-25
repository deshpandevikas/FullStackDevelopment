package com.raywenderlich.camelot;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class SurveyQuestionsParser
{

    static public class parserr{
        static ArrayList<SurveyQs> parseJsonFunction(JSONObject input)
        {
            ArrayList<SurveyQs> personList = new ArrayList<SurveyQs>();


            try
            {
                JSONArray questionsArray = input.getJSONArray("questions");
                for(int i=0;i<questionsArray.length();i++)
                {
                    SurveyQs question = new SurveyQs();
                    JSONObject tempQuestion =  new JSONObject();
                    tempQuestion = questionsArray.getJSONObject(i);

                    String qid = tempQuestion.getString("qid");
                    question.setQid(qid);

                    String datatype = tempQuestion.getString("dataType");
                    question.setDatatype(datatype);

                    if(datatype.equals("text"))
                    {
                        String questionName = tempQuestion.getString("question");
                        question.setQuestionName(questionName);
                    }
                    else if(datatype.equals("mcq"))
                    {
                        String questionName = tempQuestion.getString("question");
                        question.setQuestionName(questionName);
                        JSONArray optionsArrayTemp = new JSONArray();
                        optionsArrayTemp =  tempQuestion.getJSONArray("options");

                        HashMap<Integer,String> tempOptions = new HashMap<Integer,String>();

                        for(int j=0;j<optionsArrayTemp.length();j++)
                        {
                            JSONObject tempeachoption = new JSONObject();
                            tempeachoption = optionsArrayTemp.getJSONObject(j);
                            tempOptions.put(Integer.parseInt(tempeachoption.getString("optionid")),tempeachoption.getString("optionname"));
                        }
                        question.setOptions(tempOptions);
                    }

                    personList.add(question);
                }

            }
            catch (Exception e)
            {
                Log.d("debug1",e.getMessage());
            }

            return personList;
        }

    }
}
