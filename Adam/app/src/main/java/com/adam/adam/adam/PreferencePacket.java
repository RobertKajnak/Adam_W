package com.adam.adam.adam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesiris on 20/12/2017.
 */

public class PreferencePacket {
    public String token;
    //type: 0 for guide 1 for tourist
    public int usertype;
    String date;
    int goodDays;
    public ArrayList<String> likes;

    public PreferencePacket()
    {
        this.likes = new ArrayList<String>();
    }

    public PreferencePacket(String token, int usertype, String date, ArrayList<String> likes) {
        this.token = token;
        this.usertype = usertype;
        this.date = date;
        this.likes = likes;
    }

    public void addDay(int day){
        this.goodDays |= day;
    }
    public void removeDay(int day){
        if ( (this.goodDays & day) == 0)
        this.goodDays ^= day;
    }
}
