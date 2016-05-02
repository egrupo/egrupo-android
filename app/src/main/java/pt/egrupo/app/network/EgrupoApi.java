package pt.egrupo.app.network;

import java.util.List;
import java.util.Map;

import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;

import pt.egrupo.app.models.Token;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by ruie on 02/05/16.
 */
public interface EgrupoApi {

    @GET("divisao/{divisao}/escoteiros")
    Call<List<Escoteiro>> getEscoteiros(@Path("divisao")int divisao);

    @GET("divisao/{divisao}/atividades")
    Call<List<Atividade>> getAtividades(@Path("divisao")int divisao);

    @POST("/oauth/access_token")
    Call<Token> refreshToken(@QueryMap Map<String,String> params);

}
