package pt.egrupo.app.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.widget.Info;

/**
 * Created by ruie on 04/05/16.
 */
public class AtividadeActivity extends EgrupoActivity {

    @Bind(R.id.infoAno)Info infoAno;

    App app;
    Atividade a;

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
    }

    public void setData(){
        if(a.getAno() != null && !a.getAno().equals("")){
            infoAno.setValue(a.getAno());
        } else infoAno.setVisibility(View.GONE);
    }
}
