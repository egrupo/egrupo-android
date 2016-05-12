package pt.egrupo.app.views;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.models.Progresso;
import pt.egrupo.app.models.ProvaEtapa;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.views.dialogs.AssinarProvaDialog;
import pt.egrupo.app.widget.Info;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscoteiroProfileActivity extends EgrupoActivity implements View.OnClickListener{

    public Escoteiro e;
    public ArrayList<Progresso> p;
    ArrayList<Progresso> mItems;
    public App app;

    boolean fetchingProgresso = true;

    @Bind(R.id.ivCover)ImageView ivCover;
    @Bind(R.id.infoIdAssociativo)Info infoIdAssociativo;
    @Bind(R.id.infoNomeCompleto)Info infoNomeCompleto;
    @Bind(R.id.infoTotem)Info infoTotem;
    @Bind(R.id.infoCargo)Info infoCargo;
    @Bind(R.id.infoPatrulha)Info infoPatrulha;
    @Bind(R.id.infoBi) Info infoBi;
    @Bind(R.id.infoEmail) Info infoEmail;
    @Bind(R.id.infoTelemovel) Info infoTelemovel;
    @Bind(R.id.infoNivelEscotista) Info infoNivelEscotista;
    @Bind(R.id.infoMorada) Info infoMorada;
    @Bind(R.id.infoNomeEE1) Info infoNomeEE1;
    @Bind(R.id.infoNomeEE2) Info infoNomeEE2;
    @Bind(R.id.infoTelemEE1) Info infoTelemEE1;
    @Bind(R.id.infoTelemEE2) Info infoTelemEE2;
    @Bind(R.id.infoDescricao) Info infoDescricao;
    @Bind(R.id.infoNotas) Info infoNotas;
    @Bind(R.id.fam) FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoteiro_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        app = (App)getApplication();

        if(getIntent().getExtras() != null) {
            e = getIntent().getExtras().getParcelable("escoteiro");
        }

        setTitle(e.getNome());

        ButterKnife.bind(this);

        Glide.with(this)
                .load(e.getBigAvatarUrl())
                .placeholder(R.drawable.default_pic)
                .into(ivCover);

        findViewById(R.id.menu_etapa_1).setOnClickListener(this);
        findViewById(R.id.menu_etapa_2).setOnClickListener(this);
        findViewById(R.id.menu_etapa_3).setOnClickListener(this);

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        setData();

        app.api.getProgresso(e.getId()).enqueue(new Callback<List<Progresso>>() {
            @Override
            public void onResponse(Call<List<Progresso>> call, Response<List<Progresso>> response) {
                fetchingProgresso = false;
                p = new ArrayList<>();
                p.addAll(response.body());
                e.setProgresso(p);
                createArrayListItems();
            }

            @Override
            public void onFailure(Call<List<Progresso>> call, Throwable t) {
                fetchingProgresso = false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!fetchingProgresso) {

            int etapa = 1;

            switch (view.getId()) {
                case R.id.menu_etapa_1:
                    break;
                case R.id.menu_etapa_2:
                    etapa = 2;
                    break;
                case R.id.menu_etapa_3:
                    etapa = 3;
                    break;
            }

            Bundle args = new Bundle();
            args.putInt("etapa", etapa);

            AssinarProvaDialog dialog = new AssinarProvaDialog();
            dialog.setArguments(args);

            dialog.show(getSupportFragmentManager(), "dialog_assinar_prova");
            fam.toggle(true);
        } else {
            //still fetching.
        }
    }

    public ArrayList<ProvaEtapa> getAdapterItems(int etapa){
        ArrayList<ProvaEtapa> items = new ArrayList<>();
        items.addAll(mItems.get(--etapa).getProvas());
        ELog.d("ProfileAc","Returning: "+items.size()+" items");
        return items;
    }

    public void createArrayListItems(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mItems = new ArrayList<>();
                for(int k = 0 ; k < p.size() ; k++){
                    Collections.sort(p.get(k).getProvas(), new Comparator<ProvaEtapa>() {
                        @Override
                        public int compare(ProvaEtapa provaEtapa, ProvaEtapa t1) {
                            return provaEtapa.getProva() - t1.getProva();
                        }
                    });
                    mItems.add(new Progresso(p.get(k)));
                }

                for (int i = 0 ; i < mItems.size() ; i++){
                    for(int j = 0 ; j < mItems.get(i).getTotal() ; j++){

                        if(!mItems.get(i).temProva(j+1)){
                            ProvaEtapa temp = new ProvaEtapa(j+1,mItems.get(i).getEtapa(),mItems.get(i).getDivisao());

                            mItems.get(i).getProvas().add(j,temp);
                        }

                    }
                }

            }
        }).start();
    }

    public void setData(){
        infoIdAssociativo.setValue("" + e.getId_associativo());

        if(e.getNome_completo() != null && !e.getNome_completo().equals("")) {
            infoNomeCompleto.setValue("" + e.getNome_completo());
        } else infoNomeCompleto.setVisibility(View.GONE);

        if(e.getTotem()!= null && !e.getTotem().equals("")) {
            infoTotem.setValue("" + e.getTotem());
        } else infoTotem.setVisibility(View.GONE);

        if(e.getCargo() != null && !e.getCargo().equals("")) {
            infoCargo.setValue("" + e.getCargo());
        } else infoCargo.setVisibility(View.GONE);

        if(e.getPatrulha() != null && !e.getPatrulha().equals("")) {
            infoPatrulha.setValue("" + e.getPatrulha());
        } else infoPatrulha.setVisibility(View.GONE);

        if(e.getBi() != null && !e.getBi().equals("")) {
            infoBi.setValue("" + e.getBi());
        } else infoBi.setVisibility(View.GONE);

        if(e.getTelemovel() != null && !e.getTelemovel().equals("")) {
            infoTelemovel.setValue("" + e.getTelemovel());
        } else infoTelemovel.setVisibility(View.GONE);

        if(e.getEmail() != null && !e.getEmail().equals("")) {
            infoEmail.setValue("" + e.getEmail());
        } else infoEmail.setVisibility(View.GONE);

        if(e.getNivel_escotista() != null && !e.getNivel_escotista().equals("")) {
            infoNivelEscotista.setValue("" + e.getNivel_escotista());
        } else infoNivelEscotista.setVisibility(View.GONE);

        if(e.getMorada() != null && !e.getMorada().equals("")) {
            infoMorada.setValue("" + e.getMorada());
        } else infoMorada.setVisibility(View.GONE);

        if(e.getNome_ee_1() != null && !e.getNome_ee_1().equals("")) {
            infoNomeEE1.setValue("" + e.getNome_ee_1());
        } else infoNomeEE1.setVisibility(View.GONE);

        if(e.getTelem_ee_1() != null && !e.getTelem_ee_1().equals("")) {
            infoTelemEE1.setValue("" + e.getTelem_ee_1());
        } else infoTelemEE1.setVisibility(View.GONE);

        if(e.getNome_ee_2() != null && !e.getNome_ee_2().equals("")) {
            infoNomeEE2.setValue("" + e.getNome_ee_2());
        } else infoNomeEE2.setVisibility(View.GONE);

        if(e.getTelem_ee_2() != null && !e.getTelem_ee_2().equals("")) {
            infoTelemEE2.setValue("" + e.getTelem_ee_2());
        } else infoTelemEE2.setVisibility(View.GONE);

        if(e.getDescricao() != null && !e.getDescricao().equals("")) {
            infoDescricao.setValue("" + e.getDescricao());
        } else infoDescricao.setVisibility(View.GONE);

        if(e.getNotas() != null && !e.getNotas().equals("")) {
            infoNotas.setValue("" + e.getNotas());
        } else infoNotas.setVisibility(View.GONE);

    }

}
