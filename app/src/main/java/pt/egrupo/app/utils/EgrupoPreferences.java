package pt.egrupo.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import pt.egrupo.app.App;

/**
 * Created by rsantos on 25/02/16.
 */
public class EgrupoPreferences {

    private static final String PREFERENCES_NAME = "egrupo_preferences";

    Context context;
    SharedPreferences mPrefs;
    private static EgrupoPreferences mEgrupoPreferences;

    private EgrupoPreferences(Context ctx){
        this.context = ctx;
        mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public static EgrupoPreferences getInstance(Context ctx){
        if(mEgrupoPreferences == null)
            mEgrupoPreferences = new EgrupoPreferences(ctx);

        return mEgrupoPreferences;
    }

    public void putPreference(String key,boolean value){
        mPrefs.edit().putBoolean(key, value).commit();
    }

    public void putPreference(String key,String value){
        mPrefs.edit().putString(key, value).commit();
    }

    public void putPreference(String key,long value){
        mPrefs.edit().putLong(key, value).commit();
    }

    public String getStringPreference(String key){
        return mPrefs.getString(key,"0");
    }

    public boolean getPreference(String key){
        return mPrefs.getBoolean(key, false);
    }

    public long getLongPreference(String key){
        return mPrefs.getLong(key, 0);
    }

    public static void clearPreferences(App app){
        getInstance(app).mPrefs.edit().clear().commit();
    }



}
