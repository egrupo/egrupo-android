package pt.egrupo.app.network;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import pt.egrupo.app.App;
import pt.egrupo.app.models.Token;

public class TokenAuthenticator implements Authenticator {

    App app;

    public TokenAuthenticator(App app){
        this.app = app;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        // Refresh your access_token using a synchronous api request

        HashMap<String,String> params = new HashMap<>();
        params.put("grant_type","refresh_token");
        params.put("refresh_token",App.getRefreshToken());
        params.put("client_id","abc");
        params.put("client_secret","abc");

        Token token = app.api.refreshToken(params).execute().body();
        App.saveAccessToken(token.getAccess_token());
        App.saveRefreshToken(token.getRefres_token());

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("access_token", App.getAccessToken())
                .build();
    }

}