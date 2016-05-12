package pt.egrupo.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.TabsAdapter;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Divisao;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.models.Presenca;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.LogTimer;
import pt.egrupo.app.views.frags.PresencaFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruie on 10/05/16.
 */
public class MarcarPresencaActivity extends EgrupoActivity {

    public static final int PAGE_ALCATEIA = 0;
    public static final int PAGE_TES = 1;
    public static final int PAGE_TEX = 2;
    public static final int PAGE_CLA = 3;
    public static final int PAGE_CHEFIA = 4;

    HashMap<Integer,ArrayList<Escoteiro>> mLists;

    int currentPosition = 0;

    private TabsAdapter mAdapter;
    @Bind(R.id.container)ViewPager mViewPager;
    @Bind(R.id.loading)ProgressBar loading;
    @Bind(R.id.bMarcarPresencas)RelativeLayout bMarcar;

    ArrayList<Presenca> preloadedPresencas;
    public ArrayList<Integer> listPresentes;
    public ArrayList<Integer> listFaltas;
    public ArrayList<Integer> idsMarcados;
    public ArrayList<Integer> listRemover;

    Atividade a;

    boolean loadingLists = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_presenca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Marcar Presenças");

        if(getIntent().getExtras() != null){
            a = getIntent().getExtras().getParcelable("atividade");
            preloadedPresencas = getIntent().getExtras().getParcelableArrayList("presencas");
        }

        listPresentes = new ArrayList<>();
        listFaltas = new ArrayList<>();
        idsMarcados = new ArrayList<>();

        for(int i = 0 ; i < preloadedPresencas.size() ; i++){
            if(preloadedPresencas.get(i).getTipo() == Presenca.PRESENTE){
                listPresentes.add(preloadedPresencas.get(i).getUser_id());
            } else {
                listFaltas.add(preloadedPresencas.get(i).getUser_id());
            }
            idsMarcados.add(preloadedPresencas.get(i).getUser_id());
        }

        ButterKnife.bind(this);

        loading.setVisibility(View.VISIBLE);
        app.api.getAllEscoteiros().enqueue(new Callback<List<Escoteiro>>() {
            @Override
            public void onResponse(Call<List<Escoteiro>> call, Response<List<Escoteiro>> response) {
                createLists(response.body());
            }

            @Override
            public void onFailure(Call<List<Escoteiro>> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar_layout);
    }

    public void createLists(final List<Escoteiro> escoteiros){

        new Thread(new Runnable() {
            @Override
            public void run() {

                LogTimer.getInstance(MarcarPresencaActivity.this).startTimer("parse_escoteiros");
                mLists = new HashMap<>();

                for(int j = Divisao.ALCATEIA ; j <= Divisao.CHEFIA ; j++ ){
                    mLists.put(j,new ArrayList<Escoteiro>());
                }

                for(int i = 0 ; i < escoteiros.size() ; i++ ){
                    mLists.get(escoteiros.get(i).getDivisao()).add(escoteiros.get(i));
                }

                LogTimer.getInstance(MarcarPresencaActivity.this).stopTimer("parse_escoteiros");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingLists = false;
                        loading.setVisibility(View.GONE);
                        bMarcar.setVisibility(View.VISIBLE);
                        init();
                    }
                });
            }
        }).start();

    }

    @OnClick(R.id.bMarcarPresencas)
    public void bMarcarPresencas(View v){
        if(loadingLists)
            return;

        listRemover = new ArrayList<>();
        for(int i = 0 ; i < idsMarcados.size() ; i++ ){

            if(listPresentes.contains(idsMarcados.get(i)) || listFaltas.contains(idsMarcados.get(i))){

            } else {
                listRemover.add(idsMarcados.get(i));
            }

        }

        HashMap<String,String> mParams = new HashMap<>();
        mParams.put("presencas",TextUtils.join(",",listPresentes));
        mParams.put("faltas",TextUtils.join(",",listFaltas));
        mParams.put("remover",TextUtils.join(",",listRemover));

        loading.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        bMarcar.setVisibility(View.GONE);

        app.api.postPresencas(a.getId(),mParams).enqueue(new Callback<List<Presenca>>() {
            @Override
            public void onResponse(Call<List<Presenca>> call, Response<List<Presenca>> response) {
                setResult(RESULT_OK);
                Intent i = new Intent();
                ArrayList<Presenca> mResPresencas = new ArrayList<>();
                mResPresencas.addAll(response.body());
                i.putParcelableArrayListExtra("presencas", mResPresencas);
                setResult(RESULT_OK, i);
                MarcarPresencaActivity.this.finish();
            }

            @Override
            public void onFailure(Call<List<Presenca>> call, Throwable t) {
                mViewPager.setVisibility(View.VISIBLE);
                bMarcar.setVisibility(View.VISIBLE);
                Toast.makeText(MarcarPresencaActivity.this,"Ocorreu um erro. Tenta mais tarde.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init(){
        mAdapter = new TabsAdapter(getSupportFragmentManager());
        for(int i = Divisao.ALCATEIA ; i <= Divisao.CHEFIA ; i++){
            PresencaFragment f = new PresencaFragment();
            Bundle b = new Bundle();
            b.putInt("divisao",i);
            f.setArguments(b);
            mAdapter.addFragment(f,Divisao.getLabel(i));
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                mViewPager.setCurrentItem(currentPosition,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(a.getDivisao() != Divisao.GRUPO){
            mViewPager.setCurrentItem(a.getDivisao()-1);
        }

    }

    public ArrayList<Escoteiro> getEscoteirosList(int divisao){
        return mLists.get(divisao);
    }
}
