package pt.egrupo.app.views.frags;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

import pt.egrupo.app.App;
import pt.egrupo.app.GenericRecyclerviewFragment;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.network.HTTPStatus;
import pt.egrupo.app.network.SimpleTask;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.endless.EndlessRecyclerViewAdapter;

/**
 * Created by rsantos on 25/02/16.
 */
public class AtividadesFragment extends GenericRecyclerviewFragment<Atividade> {

    @Override
    public void initView(View v) {

    }

    @Override
    public void fetchData(int current_page) {

        new SimpleTask((App) mActivity.getApplication(), new SimpleTask.SimpleTaskHelper() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void backgroundPreExecute() {

            }

            @Override
            public void backgroundPostExecute(int code, String result) {
                ELog.d("AtividadesFrag", "Result(" + code + "): " + result);

                if(code == HTTPStatus.OK){
                    try {
                        JSONArray jarray = new JSONArray(result);

                        Gson gson =  new Gson();
                        mItems = new ArrayList<>();
                        for(int i = 0 ; i < jarray.length() ; i++ ){
                            mItems.add(0,gson.fromJson(jarray.getJSONObject(i).toString(),Atividade.class));
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void mainPostExecute(int code, String result) {
                if(code == HTTPStatus.OK){
                    onFetchDataSuccess();
                    setContent();
                } else {
                    onFetchDataFailure();
                }
            }
        },App.getBasePoint()+"/divisao/"+App.getDivisao()+"/atividades",SimpleTask.TYPE_GET).execute();
    }

    @Override
    public void setContent() {
        AtividadesAdapter adapter = new AtividadesAdapter(mItems);
        lv.setAdapter(adapter);
    }

    @Override
    public String getEmptyMessage() {
        return null;
    }

    @Override
    public String getEmptyTitle() {
        return null;
    }

    @Override
    public int getEmptyIcon() {
        return 0;
    }

    public class AtividadesAdapter extends EndlessRecyclerViewAdapter<AtividadesAdapter.AtividadesViewHolder,Atividade> {

        public AtividadesAdapter(ArrayList<Atividade> items) {
            super(mActivity,items);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_home_atividade, viewGroup, false);

            AtividadesViewHolder vh = new AtividadesViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolderImpl(AtividadesViewHolder vh, int position) {
            Atividade a = mItems.get(position);
            vh.tvName.setText(a.getNome());
            vh.tvLocal.setText(a.getLocal());
            vh.tvTrimestre.setText(a.getTrimestre()+"º trimestre");
            vh.tvData.setText(a.getPerformed_at());

        }

        public class AtividadesViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tvLocal;
            TextView tvData;
            TextView tvTrimestre;

            public AtividadesViewHolder(View v) {
                super(v);

                tvName = (TextView)v.findViewById(R.id.tvName);
                tvLocal = (TextView)v.findViewById(R.id.tvLocal);
                tvData = (TextView)v.findViewById(R.id.tvData);
                tvTrimestre = (TextView)v.findViewById(R.id.tvTrimestre);
            }

        }

    }
}
