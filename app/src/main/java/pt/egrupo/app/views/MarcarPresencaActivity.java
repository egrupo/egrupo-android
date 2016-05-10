package pt.egrupo.app.views;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.TabsAdapter;
import pt.egrupo.app.models.Divisao;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.models.Presenca;
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

    ArrayList<Presenca> preloadedPresencas;
    public ArrayList<Integer> listPresentes;
    public ArrayList<Integer> listFaltas;

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
            preloadedPresencas = getIntent().getExtras().getParcelableArrayList("presencas");
        }

        listPresentes = new ArrayList<>();
        listFaltas = new ArrayList<>();

        for(int i = 0 ; i < preloadedPresencas.size() ; i++){
            if(preloadedPresencas.get(i).getTipo() == Presenca.PRESENTE){
                listPresentes.add(preloadedPresencas.get(i).getUser_id());
            } else {
                listFaltas.add(preloadedPresencas.get(i).getUser_id());
            }
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
                        loading.setVisibility(View.GONE);
                        init();
                    }
                });
            }
        }).start();

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

    }

    public ArrayList<Escoteiro> getEscoteirosList(int divisao){
        return mLists.get(divisao);
    }
}
