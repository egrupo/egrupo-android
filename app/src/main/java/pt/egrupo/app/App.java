package pt.egrupo.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import pt.egrupo.app.models.User;

/**
 * Created by rsantos on 25/02/16.
 */
public class App extends Application {

    static SharedPreferences mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        mPrefs = getSharedPreferences("app_prefs", Activity.MODE_PRIVATE);
    }

    public static String getAccessToken(){
        return mPrefs.getString("access_token","");
    }

    public static void saveAccessToken(String accessToken){
        mPrefs.edit().putString("access_token",accessToken).commit();
    }

    public static String getRefreshToken(){
        return mPrefs.getString("refresh_token", "");
    }

    public static void saveRefreshToken(String refresh_token){
        mPrefs.edit().putString("refresh_token",refresh_token).commit();
    }

    public static void saveDivisao(int divisao){
        mPrefs.edit().putInt("divisao", divisao).commit();
    }

    public static int getDivisao(){
        return mPrefs.getInt("divisao",5);
    }

    public static String getOrganizationSlug(){
        return mPrefs.getString("slug", "");
    }

    public static void logout(){
        mPrefs.edit().clear().commit();
    }

    public static boolean isLogged(){
        return getAccessToken() != null && !getAccessToken().equals("");
    }

    public static void saveBaseEndpoint(String organization_slug){
        mPrefs.edit().putString("slug",organization_slug).commit();
    }

    public static void saveUser(User user){
        mPrefs.edit()
                .putString("user::name",user.getName())
                .putInt("user::id_associativo", user.getId_associativo())
                .putString("user::email", user.getEmail())
                .commit();
    }

    /* Utility methods to return logged user stuff */
    public static String getUsername(){
        return mPrefs.getString("user::name","");
    }

    public static String getEmail(){
        return mPrefs.getString("user::email","");
    }

    public static String getBasePoint(){
        return "http://api."+mPrefs.getString("slug","")+".egrupo.pt/v1.0";
    }
}
