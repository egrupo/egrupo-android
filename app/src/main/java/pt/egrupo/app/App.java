package pt.egrupo.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.models.User;
import pt.egrupo.app.network.ApiManager;
import pt.egrupo.app.network.EgrupoApi;
import pt.egrupo.app.utils.ELog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rsantos on 25/02/16.
 */
public class App extends Application {

    static SharedPreferences mPrefs;

    public EgrupoApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mPrefs = getSharedPreferences("app_prefs", Activity.MODE_PRIVATE);

        initApi();
    }

    public void initApi(){
        if(isLogged()){
            api = ApiManager.getInstance(this).retro.create(EgrupoApi.class);
        }
    }

    public static long getAccessTokenExpireTime(){
        return mPrefs.getLong("at_expires_at",0);
    }

    public static void saveAccessTokenExpireTime(long expires_at){
        long expireTime = System.currentTimeMillis()+(expires_at*1000);
        mPrefs.edit().putLong("at_expires_at",expireTime).commit();
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
        return "http://api."+mPrefs.getString("slug","")+".egrupo.pt/v1.0/";
    }

    public void baseEscoteiros(){
        api.getAllEscoteiros().enqueue(new Callback<List<Escoteiro>>() {
            @Override
            public void onResponse(Call<List<Escoteiro>> call, Response<List<Escoteiro>> response) {
                //save to database
            }

            @Override
            public void onFailure(Call<List<Escoteiro>> call, Throwable t) {
                //error on rebasing. need to fallback to something
            }
        });
    }
}
