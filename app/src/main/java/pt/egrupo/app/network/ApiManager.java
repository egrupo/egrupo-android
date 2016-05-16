package pt.egrupo.app.network;

import android.support.v7.app.AppCompatActivity;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import pt.egrupo.app.App;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Token;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.Globals;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ruie on 02/05/16.
 */
public class ApiManager {

    private static ApiManager mApiManager;
    App app;

    private ApiManager(App app){
        this.app = app;
    }

    /* Construtores y */
    public static ApiManager getInstance(AppCompatActivity act){
        if(mApiManager == null)
            mApiManager = new ApiManager((App)act.getApplication());

        return mApiManager;
    }

    public static ApiManager getInstance(App app){
        if(mApiManager == null)
            mApiManager = new ApiManager(app);

        return mApiManager;
    }

    private OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    //workaround
                    if(!chain.request().url().toString().contains("oauth/access_token")) {
                        Request request = chain.request();
                        if (request.method().equals("GET")) {

                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("access_token", App.getAccessToken())
                                    .build();

                            request = request.newBuilder().url(url).build();
                        } else if (request.method().equals("POST")) {

                            FormBody.Builder builder = new FormBody.Builder();
                            builder.add("access_token", App.getAccessToken());

                            request = request.newBuilder().post(builder.build()).build();
                        }

                        return chain.proceed(request);
                    }

                    return chain.proceed(chain.request());
                }
            })
            .authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("refresh_token",App.getRefreshToken());
                    params.put("client_id", Globals.CLIENT_ID);
                    params.put("client_secret",Globals.CLIENT_SECRET);
                    params.put("grant_type","refresh_token");

                    Token token = app.api.refreshToken(params).execute().body();
                    App.saveAccessToken(token.getAccess_token());
                    App.saveRefreshToken(token.getRefres_token());

                    // Add new header to rejected request and retry it
                    if(response.request().method().equals("GET")){
                        HttpUrl url = response.request().url().newBuilder()
                                .addQueryParameter("access_token", App.getAccessToken())
                                .build();

                        response.request().newBuilder().url(url).build();
                    } else if(response.request().method().equals("POST")) {
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("access_token", App.getAccessToken());
                        response.request().newBuilder().post(builder.build()).build();
                    }

                    return response.request();
                }
            })
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public Retrofit retro = new Retrofit.Builder()
            .baseUrl(App.getBasePoint())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build();

    public Retrofit getRetro(){
        return retro;
    }

}
