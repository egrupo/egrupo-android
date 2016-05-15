package pt.egrupo.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.views.dialogs.DatePickerFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruie on 04/05/16.
 */
public class AtividadeEditActivity extends EgrupoActivity {

    App app;
    Atividade a;

    @Bind(R.id.llEditInfoContainer)LinearLayout llEditInfoContainer;
    @Bind(R.id.fabSave)FloatingActionButton fabSave;

    @Bind(R.id.etLocal)EditText etLocal;
    @Bind(R.id.etDuracao)EditText etDuracao;
    @Bind(R.id.etData) EditText etData;
    @Bind(R.id.etNoitesCampo)EditText etNoitesCampo;
    @Bind(R.id.etInformacoes)EditText etInformacoes;
    @Bind(R.id.etDescricao)EditText etDescricao;
    @Bind(R.id.etPrograma)EditText etPrograma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_edit);

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

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAtividade();
            }
        });

        etData.setInputType(InputType.TYPE_NULL);
        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        etData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                    showDateDialog();
            }
        });

        setData();
    }

    public void setData(){
        if(!"".equals(a.getDescricao()))
            etDescricao.setText(a.getDescricao());

        if(!"".equals(a.getLocal()))
            etLocal.setText(a.getLocal());

        if(!"".equals(a.getDuracao()))
            etDuracao.setText(a.getDuracao());

        if(!"".equals(a.getPerformed_at()))
            etData.setText(a.getPerformed_at());

        if(a.getNoites_campo() > 0)
            etNoitesCampo.setText(""+a.getNoites_campo());

        if(!"".equals(a.getInformacoes()))
            etInformacoes.setText(a.getInformacoes());

        if(!"".equals(a.getPrograma()))
            etPrograma.setText(a.getPrograma());
    }

    public void showDateDialog(){
        DatePickerFragment f = new DatePickerFragment();
        f.setParams(new DatePickerFragment.DateCallback() {
            @Override
            public void onDateSet(String date) {
                etData.setText(date);
            }
        });
        f.show(getSupportFragmentManager(),"date_dialog");
    }

    public void updateAtividade(){

        HashMap<String,String> mParams = new HashMap<>();

        //lets add the params
        if(!etLocal.getText().toString().equals(""))
            mParams.put("local",etLocal.getText().toString());

        if(!etDuracao.getText().toString().equals(""))
            mParams.put("duracao",etDuracao.getText().toString());

        if(!etNoitesCampo.getText().toString().equals(""))
            mParams.put("noites_campo",etNoitesCampo.getText().toString());

        if(!etData.getText().toString().equals(""))
            mParams.put("performed_at",etData.getText().toString());

        if(!etInformacoes.getText().toString().equals(""))
            mParams.put("infos",etInformacoes.getText().toString());

        if(!etDescricao.getText().toString().equals(""))
            mParams.put("descricao",etDescricao.getText().toString());

        if(!etPrograma.getText().toString().equals(""))
            mParams.put("programa",etPrograma.getText().toString());

        app.api.updateAtividade(a.getId(),mParams).enqueue(new Callback<Atividade>() {
            @Override
            public void onResponse(Call<Atividade> call, Response<Atividade> response) {
                Intent i = new Intent();
                a = response.body();
                i.putExtra("atividade", a);
                setResult(RESULT_OK, i);
                AtividadeEditActivity.this.finish();
            }

            @Override
            public void onFailure(Call<Atividade> call, Throwable t) {

            }
        });

    }

}
