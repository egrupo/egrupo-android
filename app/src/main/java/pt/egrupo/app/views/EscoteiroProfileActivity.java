package pt.egrupo.app.views;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.widget.Info;

public class EscoteiroProfileActivity extends EgrupoActivity {

    Escoteiro e;


    @Bind(R.id.ivCover)ImageView ivCover;
    @Bind(R.id.infoIdAssociativo)Info infoIdAssociativo;
    @Bind(R.id.infoNomeCompleto)Info infoNomeCompleto;
    @Bind(R.id.infoTotem)Info infoTotem;
    @Bind(R.id.infoCargo)Info infoCargo;
    @Bind(R.id.infoPatrulha)Info infoPatrulha;

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

        Glide.with(this)
                .load(R.drawable.default_pic)
                .into(ivCover);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar desafio", Snackbar.LENGTH_LONG).show();
            }
        });

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        setData();
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
    }
}
