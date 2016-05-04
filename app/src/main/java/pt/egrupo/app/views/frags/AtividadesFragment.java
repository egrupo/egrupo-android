package pt.egrupo.app.views.frags;

import android.content.Intent;
import android.support.v7.widget.CardView;
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
import java.util.List;

import pt.egrupo.app.App;
import pt.egrupo.app.GenericRecyclerviewFragment;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.network.HTTPStatus;
import pt.egrupo.app.network.SimpleTask;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.endless.EndlessRecyclerViewAdapter;
import pt.egrupo.app.views.AtividadeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rsantos on 25/02/16.
 */
public class AtividadesFragment extends GenericRecyclerviewFragment<Atividade> {

    @Override
    public void initView(View v) {

    }

    @Override
    public void fetchData(int current_page) {

        app.api.getAtividades(App.getDivisao()).enqueue(new Callback<List<Atividade>>() {
            @Override
            public void onResponse(Call<List<Atividade>> call, Response<List<Atividade>> response) {
                mItems = new ArrayList<>();
                mItems.addAll(response.body());
                onFetchDataSuccess();
                setContent();
            }

            @Override
            public void onFailure(Call<List<Atividade>> call, Throwable t) {
                onFetchDataFailure();
            }
        });
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

            vh.cardContainer.setTag(a);

        }

        public class AtividadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvLocal;
            TextView tvData;
            TextView tvTrimestre;
            CardView cardContainer;

            public AtividadesViewHolder(View v) {
                super(v);

                tvName = (TextView)v.findViewById(R.id.tvName);
                tvLocal = (TextView)v.findViewById(R.id.tvLocal);
                tvData = (TextView)v.findViewById(R.id.tvData);
                tvTrimestre = (TextView)v.findViewById(R.id.tvTrimestre);
                cardContainer = (CardView)v.findViewById(R.id.cardAtividades);
                cardContainer.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AtividadeActivity.class);
                i.putExtra("atividade",(Atividade)view.getTag());
                startActivity(i);
            }
        }

    }
}
