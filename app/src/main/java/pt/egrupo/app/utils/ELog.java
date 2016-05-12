package pt.egrupo.app.utils;

import android.util.Log;

/**
 * Created by rsantos on 25/02/16.
 */
public class ELog {

    public static void d(Object obj,String msg){
        Log.d(obj.getClass().getCanonicalName(), msg);
    }

    public static void d(String str,String msg){
        Log.d(str,msg);
    }

    public static void e(Object obj,String msg){
        Log.e(obj.getClass().getCanonicalName(), msg);
    }

    public static void e(String str,String msg){
        Log.e(str, msg);
    }

    public static void v(Object obj,String msg){
        Log.v(obj.getClass().getCanonicalName(), msg);
    }

    public static void v(String str,String msg){
        Log.v(str, msg);
    }

    public static void drawDivider(){
        Log.d("-----","--------------------------------------------------");
    }

}
