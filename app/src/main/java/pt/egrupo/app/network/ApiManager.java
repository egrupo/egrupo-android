package pt.egrupo.app.network;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pt.egrupo.app.App;
import pt.egrupo.app.R;
import pt.egrupo.app.utils.ELog;
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
            .authenticator(new TokenAuthenticator(app))
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

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
