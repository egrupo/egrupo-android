package pt.egrupo.app.network;

import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import pt.egrupo.app.App;
import pt.egrupo.app.utils.ELog;

/**
 * Created by rsantos on 25/02/16.
 */
public class SimpleTask extends AsyncTask<String, String, String> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    App app;
    int code;
    String result;
    SimpleTaskHelper sth;

    String endpoint;
    int type;
    HashMap<String, String> mParams;

    public static final int TYPE_GET = 1;
    public static final int TYPE_POST = 2;

    public SimpleTask(App app,SimpleTaskHelper simpleTaskHelper,String endpoint,int type,HashMap<String, String> params){
        this.app = app;
        this.sth = simpleTaskHelper;
        this.endpoint = endpoint;
        this.type = type;
        this.mParams = params;
    }

    public SimpleTask(App app,SimpleTaskHelper simpleTaskHelper,String endpoint,int type){
        this.app = app;
        this.sth = simpleTaskHelper;
        this.endpoint = endpoint;
        this.type = type;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(sth != null)
            sth.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        if(sth != null)
            sth.backgroundPreExecute();

        code = 0;
        result = "";

        RequestBody requestBody = null;

        //General task here
        if(type == TYPE_GET){

            if(App.isLogged()){

                if(App.getAccessToken() == null)
                    return "";

                endpoint += "?access_token="+App.getAccessToken();
            }

            //Add parameters
            if(mParams != null){
                for(Map.Entry<String, String> entry : mParams.entrySet()){
                    try {
                        endpoint += "&"+entry.getKey()+"="+ URLEncoder.encode(entry.getValue(), "utf-8");
                    } catch(UnsupportedEncodingException e){

                    }
                }
            }

            ELog.d("Oi", "GET Endpoint is: " + endpoint);
        } else if(type == TYPE_POST) {

            FormEncodingBuilder builder = new FormEncodingBuilder();

            builder.add("android_version", ""+1);

            if(App.isLogged()) {

                if(App.getAccessToken() == null)
                    return "";

                builder.add("access_token", App.getAccessToken());
            }

            if(mParams != null){
                for(Map.Entry<String, String> entry : mParams.entrySet()){
                    builder.add(entry.getKey(), entry.getValue());
                }
            }

            requestBody = builder.build();
        }

        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);

        Request.Builder builder = new Request.Builder();

        builder.url(endpoint);
        builder.addHeader("Connection", "close");

        if(type == TYPE_POST)
            builder.post(requestBody);

        Request request = builder.build();

        Response response;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
            code = response.code();
        } catch (IOException e) {

        }

        if(sth != null)
            sth.backgroundPostExecute(code, result);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(sth != null)
            sth.mainPostExecute(code, result);
    }

    public interface SimpleTaskHelper {
        void onPreExecute();
        void backgroundPreExecute();
        void backgroundPostExecute(int code,String result);
        void mainPostExecute(int code,String result);
    }

}
