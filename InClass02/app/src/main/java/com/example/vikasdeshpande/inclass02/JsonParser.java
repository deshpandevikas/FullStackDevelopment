package com.example.vikasdeshpande.inclass02;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Vikas Deshpande on 9/12/2017.
 */

public class JsonParser
{
    static public class ShoppingJSONParser{

        static ArrayList<Shopping> parseJsonFunction (JSONArray input)
        {
            ArrayList<Shopping> shoppingArrayList = new ArrayList<Shopping>();

            try
            {
                JSONArray jsonArray= input;

                for(int i=0;i<jsonArray.length();i++)
                {
                    Shopping shopping = new Shopping();
                    JSONObject eachobj = jsonArray.getJSONObject(i);
                    shopping.setDiscount(eachobj.getDouble("discount"));
                    shopping.setName(eachobj.getString("name"));
                    shopping.setPhoto_name(eachobj.getString("photo"));
                    shopping.setPrice(eachobj.getDouble("price"));
                    shopping.setRegion(eachobj.getString("region"));

                    shoppingArrayList.add(shopping);
                }

            }
            catch (Exception e)
            {
                Log.d("debug1",e.getMessage());
            }

            return shoppingArrayList;

        }
    }
}
