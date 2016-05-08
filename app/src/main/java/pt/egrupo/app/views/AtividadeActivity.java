package pt.egrupo.app.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Presenca;
import pt.egrupo.app.widget.Info;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruie on 04/05/16.
 */
public class AtividadeActivity extends EgrupoActivity {

    @Bind(R.id.infoLocal)Info infoLocal;
    @Bind(R.id.infoData)Info infoData;
    @Bind(R.id.infoTrimestre)Info infoTrimestre;
    @Bind(R.id.infoDuracao)Info infoDuracao;
    @Bind(R.id.infoNoites)Info infoNoites;
    @Bind(R.id.infoInformaces)Info infoInformacoes;
    @Bind(R.id.infoDescricao)Info infoDescricao;
    @Bind(R.id.rlPresencas)RelativeLayout rlPresencas;
    @Bind(R.id.vLoading)ProgressBar vLoading;

    App app;
    Atividade a;
    ArrayList<Presenca> presencas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        app = (App)getApplication();

        if(getIntent().getExtras() != null){
            a = getIntent().getExtras().getParcelable("atividade");
        }

        setTitle(a.getNome());

        ButterKnife.bind(this);

        setData();
        fetchPresencas();
    }

    public void setData(){
        if(a.getLocal() != null && !a.getLocal().equals("")){
            infoLocal.setValue(a.getLocal());
        } else infoLocal.setVisibility(View.GONE);

        if(a.getPerformed_at() != null && !a.getPerformed_at().equals("")){
            infoData.setValue(a.getPerformed_at());
        } else infoData.setVisibility(View.GONE);

        infoTrimestre.setValue("" + a.getTrimestre());

        if(a.getDuracao() != null && !a.getDuracao().equals("")){
            infoDuracao.setValue(a.getDuracao());
        } else infoDuracao.setVisibility(View.GONE);

        if(a.getNoites() > 0){
            infoNoites.setValue(""+a.getNoites());
        } else infoNoites.setVisibility(View.GONE);

        if(a.getInformacoes() != null && !a.getInformacoes().equals("")){
            infoInformacoes.setValue(a.getInformacoes());
        } else infoInformacoes.setVisibility(View.GONE);

        if(a.getDescricao() != null && !a.getDescricao().equals("")){
            infoDescricao.setValue(a.getDescricao());
        } else infoDescricao.setVisibility(View.GONE);
    }

    public void renderPresencas(){

    }

    public void fetchPresencas(){
        app.api.getPresencas(a.getId()).enqueue(new Callback<List<Presenca>>() {
            @Override
            public void onResponse(Call<List<Presenca>> call, Response<List<Presenca>> response) {
                presencas = new ArrayList<>();
                presencas.addAll(response.body());

                if(presencas.size() == 0) {
                    rlPresencas.setVisibility(View.GONE);
                    return;
                }

                vLoading.setVisibility(View.GONE);
                renderPresencas();
            }

            @Override
            public void onFailure(Call<List<Presenca>> call, Throwable t) {
                vLoading.setVisibility(View.GONE);
            }
        });
    }
}
