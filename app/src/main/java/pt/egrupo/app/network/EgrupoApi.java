package pt.egrupo.app.network;

import java.util.List;
import java.util.Map;

import okhttp3.Response;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;

import pt.egrupo.app.models.Presenca;
import pt.egrupo.app.models.Progresso;
import pt.egrupo.app.models.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by ruie on 02/05/16.
 */
public interface EgrupoApi {

    @POST("/oauth/access_token")
    Call<Token> refreshToken(@QueryMap Map<String,String> params);

    @GET("divisao/{divisao}/escoteiros")
    Call<List<Escoteiro>> getEscoteiros(@Path("divisao")int divisao);

    @GET("divisao/{divisao}/atividades")
    Call<List<Atividade>> getAtividades(@Path("divisao")int divisao);

    @GET("atividade/{id}/presencas")
    Call<List<Presenca>> getPresencas(@Path("id")int id);

    @POST("atividade/{id}/presencas")
    Call<List<Presenca>> postPresencas(@Path("id")int id,@QueryMap Map<String,String> params);

    @GET("escoteiro/{id}/progresso")
    Call<List<Progresso>> getProgresso(@Path("id")int id);

    @POST("escoteiro/{id}/progresso")
    Call<List<Progresso>> postProgresso(@Path("id")int id,@QueryMap Map<String,String> params);

    @GET("escoteiros")
    Call<List<Escoteiro>> getAllEscoteiros();

}
