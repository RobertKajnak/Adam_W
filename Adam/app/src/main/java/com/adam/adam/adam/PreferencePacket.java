package com.adam.adam.adam;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesiris on 20/12/2017.
 */

public class PreferencePacket {
    public String token;
    //type: 0 for tourist 1 for guide
    public int usertype;
    String date;
    int goodDays;
    public ArrayList<String> likes;

    public PreferencePacket()
    {
        this.likes = new ArrayList<String>();
    }

    public PreferencePacket(String token, int usertype, String date,int goodDays, ArrayList<String> likes) {
        this.token = token;
        this.usertype = usertype;
        this.date = date;
        this.goodDays = goodDays;
        this.likes = likes;
    }

    public void addDay(int day){
        this.goodDays |= day;
    }
    public void removeDay(int day){
        if ( (this.goodDays & day) == 0)
        this.goodDays ^= day;
    }

    public String getJSON(){
        JSONObject prefJSON =new  JSONObject();
        try {
            prefJSON.put("token",token);
            prefJSON.put("usertype",usertype);
            prefJSON.put("date",date);
            prefJSON.put("goodDays",goodDays);
            JSONArray jsonArr = new JSONArray();
            for (String like : likes){
                jsonArr.put(like);
            }
            prefJSON.put("likes",jsonArr);
        } catch (JSONException e) {
            Log.e("JSON","Failed toparse",e);
        }

        return prefJSON.toString();
    }
}
