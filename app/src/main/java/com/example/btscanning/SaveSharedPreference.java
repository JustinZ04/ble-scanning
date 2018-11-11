package com.example.btscanning;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_NAME = "";
    static final String PREF_PROF_NAME = "";
    static final String PREF_PROF_NID = "";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setPrefProfName(Context ctx, String profName)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PROF_NAME, profName);
        editor.commit();
    }

    public static void setPrefProfNID(Context ctx, String profNID)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PROF_NID, profNID);
        editor.commit();
    }

    public static String getPrefProfName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PROF_NAME, "");
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPrefProfNid(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_PROF_NID, "");
    }
}
