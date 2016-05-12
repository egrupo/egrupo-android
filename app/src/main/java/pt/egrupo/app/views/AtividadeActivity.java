package pt.egrupo.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Presenca;
import pt.egrupo.app.utils.RoundedCornersTransformation;
import pt.egrupo.app.utils.Utils;
import pt.egrupo.app.widget.Info;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruie on 04/05/16.
 */
public class AtividadeActivity extends EgrupoActivity {

    public static final int CODE_MARCAR_PRESENCA = 1337;

    @Bind(R.id.infoLocal)Info infoLocal;
    @Bind(R.id.infoData)Info infoData;
    @Bind(R.id.infoTrimestre)Info infoTrimestre;
    @Bind(R.id.infoDuracao)Info infoDuracao;
    @Bind(R.id.infoNoites)Info infoNoites;
    @Bind(R.id.infoInformaces)Info infoInformacoes;
    @Bind(R.id.infoDescricao)Info infoDescricao;
    @Bind(R.id.infoPrograma)Info infoPrograma;
    @Bind(R.id.rlPresencas)RelativeLayout rlPresencas;
    @Bind(R.id.llPresencaContainer)LinearLayout llPresencaContainer;
    @Bind(R.id.loading)ProgressBar loading;

    @Bind(R.id.fab)FloatingActionButton fab;

    App app;
    Atividade a;
    ArrayList<Presenca> presencas;

    RoundedCornersTransformation mTransform;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtividadeActivity.this,MarcarPresencaActivity.class);
                i.putExtra("atividade",a);
                i.putParcelableArrayListExtra("presencas",presencas);
                startActivityForResult(i, CODE_MARCAR_PRESENCA);
            }
        });

        mTransform = new RoundedCornersTransformation(
                Glide.get(this).getBitmapPool(),
                Utils.convertDpToPixel(4, this),
                0);

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

        if(a.getPrograma() != null && !a.getPrograma().equals("")){
            infoPrograma.setValue(a.getPrograma());
        } else infoPrograma.setVisibility(View.GONE);

    }

    public void renderPresencas(){
        llPresencaContainer.removeAllViews();
        for(int i = 0 ; i < presencas.size() ; i++){
            View v = getLayoutInflater().inflate(R.layout.row_presenca,null);

            ((TextView)v.findViewById(R.id.tvName)).setText(presencas.get(i).getEscoteiro().getNome());
            ((TextView)v.findViewById(R.id.tvTipo)).setText(presencas.get(i).getLabel());
            ((TextView)v.findViewById(R.id.tvTipo)).setTextColor(getResources().getColor(presencas.get(i).getColor()));
            Glide.with(this)
                    .load(presencas.get(i).getEscoteiro().getAvatarUrl())
                    .bitmapTransform(mTransform)
                    .into((ImageView) v.findViewById(R.id.ivAvatar));

            llPresencaContainer.addView(v);
        }

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
                loading.setVisibility(View.GONE);
                renderPresencas();
            }

            @Override
            public void onFailure(Call<List<Presenca>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == CODE_MARCAR_PRESENCA){
                presencas = data.getParcelableArrayListExtra("presencas");
                renderPresencas();
            }
        }
    }
}
