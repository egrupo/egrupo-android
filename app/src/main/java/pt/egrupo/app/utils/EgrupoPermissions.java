package pt.egrupo.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import pt.egrupo.app.App;

/**
 * Created by rsantos on 25/02/16.
 */
public class EgrupoPermissions {

    private static final String PREFERENCES_NAME = "egrupo_permissions";

    Context context;
    SharedPreferences mPrefs;
    private static EgrupoPermissions mEgrupoPreferences;

    private EgrupoPermissions(Context ctx){
        this.context = ctx;
        mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public static EgrupoPermissions getInstance(Context ctx){
        if(mEgrupoPreferences == null)
            mEgrupoPreferences = new EgrupoPermissions(ctx);

        return mEgrupoPreferences;
    }

    public boolean hasPermissions(String permission){
        return mPrefs.getBoolean(permission,false);
    }

    public void putPermission(String permission,boolean val){
        mPrefs.edit().putBoolean(permission,val).commit();
    }

}
