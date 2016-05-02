package pt.egrupo.app.views;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.widget.Info;

public class EscoteiroProfileActivity extends EgrupoActivity implements View.OnClickListener{

    Escoteiro e;

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
    @Bind(R.id.fam) FloatingActionMenu fam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoteiro_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras() != null) {
            e = getIntent().getExtras().getParcelable("escoteiro");
        }

        setTitle(e.getNome());

        ButterKnife.bind(this);

        String url = App.getBigAvatarUrl(e.getId());
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.default_pic)
                .into(ivCover);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar desafio", Snackbar.LENGTH_LONG).show();
            }
        });*/

        findViewById(R.id.menu_etapa_1).setOnClickListener(this);
        findViewById(R.id.menu_etapa_2).setOnClickListener(this);
        findViewById(R.id.menu_etapa_3).setOnClickListener(this);

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        setData();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.menu_etapa_1:
                Snackbar.make(view, "Adicionar desafio 1", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.menu_etapa_2:
                Snackbar.make(view, "Adicionar desafio 2", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.menu_etapa_3:
                Snackbar.make(view, "Adicionar desafio 3", Snackbar.LENGTH_LONG).show();
                break;
        }
        fam.toggle(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
    }
}
