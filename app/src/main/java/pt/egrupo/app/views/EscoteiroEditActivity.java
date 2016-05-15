package pt.egrupo.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscoteiroEditActivity extends EgrupoActivity {

    Escoteiro e;

    @Bind(R.id.etIdAssociativo)EditText etIdAssociativo;
    @Bind(R.id.etNomeCompleto)EditText etNomeCompleto;
    @Bind(R.id.etTotem)EditText etTotem;
    @Bind(R.id.etCargo)EditText etCargo;
    @Bind(R.id.etPatrulha)EditText etPatrulha;
    @Bind(R.id.etBi)EditText etBi;
    @Bind(R.id.etEmail)EditText etEmail;
    @Bind(R.id.etNivelEscotista)EditText etNivelEscotista;
    @Bind(R.id.etMorada)EditText etMorada;
    @Bind(R.id.etNomeEE1)EditText etNomeEE1;
    @Bind(R.id.etNomeEE2)EditText etNomeEE2;
    @Bind(R.id.etTelemEE1)EditText etTelemEE1;
    @Bind(R.id.etTelemEE2)EditText etTelemEE2;
    @Bind(R.id.etDescricao)EditText etDescricao;
    @Bind(R.id.etNotas)EditText etNotas;
    @Bind(R.id.etTelemovel) EditText etTelemovel;

    @Bind(R.id.ivCover) ImageView ivCover;
    @Bind(R.id.fabSave)FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escoteiro_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        app = (App)getApplication();

        if(getIntent().getExtras() != null) {
            e = getIntent().getExtras().getParcelable(EXTRA_ESCOTEIRO);
        }

        setTitle(e.getNome());

        ButterKnife.bind(this);

        Glide.with(this)
                .load(e.getBigAvatarUrl())
                .placeholder(R.drawable.default_pic)
                .into(ivCover);

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEscoteiro();
            }
        });

        setData();
    }
    public void setData(){
        if(e.getId_associativo() > 0) etIdAssociativo.setText(""+e.getId_associativo());

        if(!"".equals(e.getNome_completo())) etNomeCompleto.setText(e.getNome_completo());

        if(!"".equals(e.getTotem())) etTotem.setText(e.getTotem());

        if(!"".equals(e.getCargo())) etCargo.setText(e.getCargo());

        if(!"".equals(e.getPatrulha())) etPatrulha.setText(e.getPatrulha());

        if(!"".equals(e.getBi())) etBi.setText(e.getBi());

        if(!"".equals(e.getEmail())) etEmail.setText(e.getEmail());

        if(!"".equals(e.getNivel_escotista())) etNivelEscotista.setText(e.getNivel_escotista());

        if(!"".equals(e.getMorada())) etMorada.setText(e.getMorada());

        if(!"".equals(e.getNome_ee_1())) etNomeEE1.setText(e.getNome_ee_1());

        if(!"".equals(e.getNome_ee_2())) etNomeEE2.setText(e.getNome_ee_2());

        if(!"".equals(e.getTelem_ee_1())) etTelemEE1.setText(e.getTelem_ee_1());

        if(!"".equals(e.getTelem_ee_2())) etTelemEE2.setText(e.getTelem_ee_2());

        if(!"".equals(e.getDescricao())) etDescricao.setText(e.getDescricao());

        if(!"".equals(e.getNotas())) etNotas.setText(e.getNotas());

        if(!"".equals(e.getTelemovel())) etTelemovel.setText(e.getTelemovel());
    }

    public void updateEscoteiro(){

        HashMap<String,String> mParams = new HashMap<>();

        if(etIdAssociativo.getText().toString().equals("")){
            Toast.makeText(this,"Este escoteiro tem de ter um Número Associativo!",Toast.LENGTH_SHORT).show();
            return;
        } else {
            mParams.put("id_associativo",etIdAssociativo.getText().toString());
        }

        mParams.put("nome_completo",etNomeCompleto.getText().toString());
        mParams.put("totem",etTotem.getText().toString());
        mParams.put("cargo",etCargo.getText().toString());
        mParams.put("patrulha",etPatrulha.getText().toString());
        mParams.put("bi",etBi.getText().toString());
        mParams.put("email",etEmail.getText().toString());
        mParams.put("nivel_escotista",etNivelEscotista.getText().toString());
        mParams.put("morada",etMorada.getText().toString());
        mParams.put("nome_ee_1",etNomeEE1.getText().toString());
        mParams.put("nome_ee_2",etNomeEE2.getText().toString());
        mParams.put("telem_ee_1",etTelemEE1.getText().toString());
        mParams.put("telem_ee_2",etTelemEE2.getText().toString());
        mParams.put("descricao",etDescricao.getText().toString());
        mParams.put("notas",etNotas.getText().toString());
        mParams.put("telemovel",etTelemovel.getText().toString());

        app.api.updateEscoteiro(e.getId(),mParams).enqueue(new Callback<Escoteiro>() {
            @Override
            public void onResponse(Call<Escoteiro> call, Response<Escoteiro> response) {
                Intent i = new Intent();
                e = response.body();
                i.putExtra(EXTRA_ESCOTEIRO, e);
                setResult(RESULT_OK, i);
                EscoteiroEditActivity.this.finish();
            }

            @Override
            public void onFailure(Call<Escoteiro> call, Throwable t) {

            }
        });

    }

}
