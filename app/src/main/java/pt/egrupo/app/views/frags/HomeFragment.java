package pt.egrupo.app.views.frags;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.R;

import pt.egrupo.app.models.Atividade;

import pt.egrupo.app.models.Lembrete;
import pt.egrupo.app.network.HTTPStatus;
import pt.egrupo.app.network.SimpleTask;
import pt.egrupo.app.utils.ELog;

import pt.egrupo.app.views.AtividadeActivity;
import pt.egrupo.app.views.HomeActivity;

/**
 * Created by rsantos on 25/02/16.
 */
public class HomeFragment extends Fragment {

    HomeActivity act;

    @Bind(R.id.tvAvisosAtividades)TextView tvAvisosAtividades;
    @Bind(R.id.cardAtividades)CardView cardAtividades;
    @Bind(R.id.cardLembretes)CardView cardLembretes;
    @Bind(R.id.rlLembretesContainer)LinearLayout rlLembretesContainer;

    //prox atividade
    @Bind(R.id.llProximaAtividade) LinearLayout llProximaAtividade;
    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.tvLocal) TextView tvLocal;
    @Bind(R.id.tvTrimestre) TextView tvTrimestre;
    @Bind(R.id.tvData) TextView tvData;

    int nAvisosAtividades = 0;
    ArrayList<Lembrete> lembretes;
    Atividade proxima_atividade;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        act = (HomeActivity)getActivity();

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this,v);

        fetchHome();

        return v;
    }

    public void fetchHome(){
        new SimpleTask((App) act.getApplication(), new SimpleTask.SimpleTaskHelper() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void backgroundPreExecute() {

            }

            @Override
            public void backgroundPostExecute(int code, String result) {
                ELog.d("HomeFrag", "Result(" + code + "): " + result);

                if(code == HTTPStatus.OK){
                    try {
                        JSONObject json = new JSONObject(result);

                        nAvisosAtividades = json.getInt("n_avisos_atividades");

                        lembretes = new ArrayList<>();
                        JSONArray jarray = json.getJSONArray("lembretes");
                        Gson gson = new Gson();
                        for(int i = 0 ; i < jarray.length() ; i++ ){
                            lembretes.add(0,gson.fromJson(jarray.get(i).toString(),Lembrete.class));
                        }

                        proxima_atividade = new Gson().fromJson(json.getJSONObject("proxima_atividade").toString(),Atividade.class);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                } else {

                }

            }

            @Override
            public void mainPostExecute(int code, String result) {

                if(code == HTTPStatus.OK){
                    if(nAvisosAtividades > 0){
                        cardAtividades.setVisibility(View.VISIBLE);
                        tvAvisosAtividades.setText(getResources().getQuantityString(R.plurals.home_warning_atividades, nAvisosAtividades, nAvisosAtividades));
                    } else {
                        cardAtividades.setVisibility(View.GONE);
                    }

                    if(lembretes.size() > 0){
                        cardLembretes.setVisibility(View.VISIBLE);
                        TextView lembrete;
                        rlLembretesContainer.removeAllViews();
                        for(int i = 0 ; i < lembretes.size() ; i++ ){
                            //add textviws to card
                            lembrete = (TextView)act.getLayoutInflater().inflate(R.layout.text_lembrete,null);
                            lembrete.setText(lembretes.get(i).getLabel());
                            rlLembretesContainer.addView(lembrete);
                        }
                    } else {
                        cardLembretes.setVisibility(View.GONE);
                    }

                    if(proxima_atividade == null){
                        llProximaAtividade.setVisibility(View.GONE);
                    } else {
                        llProximaAtividade.setVisibility(View.VISIBLE);
                        tvTrimestre.setText(proxima_atividade.getTrimestre() + "º trimestre");
                        tvData.setText(proxima_atividade.getPerformed_at());

                        tvName.setText(proxima_atividade.getNome());

                        if("".equals(proxima_atividade.getLocal())){
                            tvLocal.setVisibility(View.GONE);
                        } else {
                            tvLocal.setVisibility(View.VISIBLE);
                            tvLocal.setText(proxima_atividade.getLocal());
                        }

                        llProximaAtividade.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), AtividadeActivity.class);
                                i.putExtra("atividade",proxima_atividade);
                                startActivity(i);
                            }
                        });
                    }


                } else {

                }

            }
        },App.getBasePoint()+"divisao/"+App.getDivisao()+"/avisos",SimpleTask.TYPE_GET).execute();
    }


}
