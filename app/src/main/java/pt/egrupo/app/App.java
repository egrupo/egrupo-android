package pt.egrupo.app;

import android.app.Application;

/**
 * Created by rsantos on 25/02/16.
 */
public class App extends Application {

    public static final String BASE_ENDPOINT = "api.{}.egrupo.pt";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static String getAccessToken(){
        return "";
    }

    public static boolean isLogged(){
        return getAccessToken() != null && !getAccessToken().equals("");
    }
}
