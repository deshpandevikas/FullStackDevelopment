package com.raywenderlich.camelot;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class StudiesJsonParser
{
    static public class parserr{
        static ArrayList<StudiesAttributes> parseJsonFunction(JSONObject input)
        {
            Log.d("jsonn",input.toString());

            ArrayList<StudiesAttributes> personList = new ArrayList<StudiesAttributes>();

            try
            {
                JSONArray jsonArray= input.getJSONArray("Studies");

                for(int i=0;i<jsonArray.length();i++)
                {
                    StudiesAttributes person = new StudiesAttributes();
                    JSONObject eachobj = jsonArray.getJSONObject(i);
                    person.setStudyName(eachobj.getString("sname"));
                    person.setSid(eachobj.getInt("sid"));
                    person.setStudyDesc(eachobj.getString("sdescription"));
                    personList.add(person);
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
