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

public class QuestionsJsonParser
{
    static public class parserr{
        static ArrayList<Questions> parseJsonFunction(JSONObject input)
        {
            ArrayList<Questions> personList = new ArrayList<Questions>();

            try
            {
                JSONArray questionsArray = input.getJSONArray("questions");
                for(int i=0;i<questionsArray.length();i++)
                {
                    Questions question = new Questions();
                    JSONObject tempQuestion = questionsArray.getJSONObject(i);

                    String datatype = tempQuestion.getString("dataType");
                    question.setDatatype(datatype);
                    String tempResponse = tempQuestion.getString("response");
                    question.setResponse(tempResponse);

                    String tempdtime = tempQuestion.getString("dtime");
                    SimpleDateFormat df1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date resultdtime = df1.parse(tempdtime);
                    question.setDtime(resultdtime);


                    if(datatype.equals("question"))
                    {
                        int tempId = Integer.parseInt(tempQuestion.getString("qid"));
                        question.setId(tempId);

                        int temptimeid = Integer.parseInt(tempQuestion.getString("timeId"));
                        question.setTimeId(temptimeid);

                        String tempQuestionName = tempQuestion.getString("question");
                        question.setQuestion(tempQuestionName);

                        HashMap<Integer,String> tempOptions = new HashMap<Integer,String>();

                        if(tempResponse.equals("no"))
                        {
                            JSONArray optionsArrayTemp = tempQuestion.getJSONArray("options");

                            for(int j=0;j<optionsArrayTemp.length();j++)
                            {
                                JSONObject tempeachoption = optionsArrayTemp.getJSONObject(j);
                                tempOptions.put(Integer.parseInt(tempeachoption.getString("optionid")),tempeachoption.getString("optionname"));
                            }
                            question.setOptions(tempOptions);
                        }
                        else if(tempResponse.equals("yes"))
                        {
                            tempOptions.put(Integer.parseInt(tempQuestion.getString("optionId")),tempQuestion.getString("optionName"));
                            question.setOptions(tempOptions);
                        }

                    }
                    else if(datatype.equals("survey"))
                    {
                        String surveyId = tempQuestion.getString("surveyid");
                        question.setSurveyId(surveyId);

                        String surveyName = tempQuestion.getString("surveyname");
                        question.setSurveyName(surveyName);
                    }
//                    Questions q = new Questions(tempId, temptimeid, tempQuestionName,tempResponse, tempOptions,resultdtime,datatype,surveyId,surveyName);
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






