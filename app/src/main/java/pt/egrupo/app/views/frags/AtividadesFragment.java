package pt.egrupo.app.views.frags;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import pt.egrupo.app.App;
import pt.egrupo.app.GenericRecyclerviewFragment;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.models.Divisao;
import pt.egrupo.app.utils.endless.EndlessRecyclerViewAdapter;
import pt.egrupo.app.views.AtividadeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rsantos on 25/02/16.
 */
public class AtividadesFragment extends GenericRecyclerviewFragment<Atividade> {

    private StickyHeaderDecoration decor;

    @Override
    public void initView(View v) {
        final DividerDecoration divider = new DividerDecoration.Builder(this.getActivity())
                .setColorResource(R.color.transparent)
                .build();

        lv.setHasFixedSize(true);
        lv.addItemDecoration(divider);
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

    public void refreshAtividades(ArrayList<Atividade> atividades){
        mItems = new ArrayList<>();
        mItems.addAll(atividades);
        setContent();
    }

    @Override
    public void setContent() {

        AtividadesAdapter adapter;
        if(lv.getAdapter() == null){
            adapter = new AtividadesAdapter(mItems);
            decor = new StickyHeaderDecoration(adapter);
            lv.setAdapter(adapter);
            lv.addItemDecoration(decor,1);
            return;
        }

        adapter = (AtividadesAdapter)lv.getAdapter();
        adapter.setItems(mItems);
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

    public class AtividadesAdapter extends EndlessRecyclerViewAdapter<AtividadesAdapter.AtividadesViewHolder,Atividade> implements
            StickyHeaderAdapter<AtividadesAdapter.HeaderHolder> {

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

            if(a.getLocal().equals("")){
                vh.tvLocal.setVisibility(View.GONE);
            } else {
                vh.tvLocal.setVisibility(View.VISIBLE);
                vh.tvLocal.setText(a.getLocal());
            }

            vh.tvTrimestre.setText(a.getTrimestre()+"º trimestre");
            vh.tvData.setText(a.getPerformed_at());

//            String color = mActivity.getRandomColor();
//            if(mActivity.useBlackText(color)){
//                vh.tvName.setTextColor(getResources().getColor(R.color.black));
//                vh.tvLocal.setTextColor(getResources().getColor(R.color.black));
//            } else {
//                vh.tvName.setTextColor(getResources().getColor(R.color.white));
//                vh.tvLocal.setTextColor(getResources().getColor(R.color.white));
//            }
//            vh.rlTitleAtividadesContainer.setBackgroundColor(Color.parseColor(color));

            vh.cardContainer.setTag(a);
        }

        public void setItems(ArrayList<Atividade> items){
            this.mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public long getHeaderId(int position) {
            return mItems.get(position).getTrimestre()-1;
        }

        @Override
        public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.row_header, parent, false);
            view.setBackgroundResource(Divisao.getColor(App.getDivisao()));
            return new HeaderHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(HeaderHolder vh, int position) {
            int trimestre = mItems.get(position).getTrimestre();

            vh.tvHeader.setText(trimestre+"ª Trimestre");
        }

        public class AtividadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvLocal;
            TextView tvData;
            TextView tvTrimestre;
            CardView cardContainer;
            RelativeLayout rlTitleAtividadesContainer;


            public AtividadesViewHolder(View v) {
                super(v);

                tvName = (TextView)v.findViewById(R.id.tvName);
                tvLocal = (TextView)v.findViewById(R.id.tvLocal);
                tvData = (TextView)v.findViewById(R.id.tvData);
                tvTrimestre = (TextView)v.findViewById(R.id.tvTrimestre);
                cardContainer = (CardView)v.findViewById(R.id.cardAtividades);
                rlTitleAtividadesContainer = (RelativeLayout)v.findViewById(R.id.rlTitleAtividadesContainer);
                cardContainer.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AtividadeActivity.class);
                i.putExtra("atividade",(Atividade)view.getTag());
                startActivity(i);
            }
        }

        class HeaderHolder extends RecyclerView.ViewHolder {
            public TextView tvHeader;

            public HeaderHolder(View v) {
                super(v);
                tvHeader = (TextView) v.findViewById(R.id.tvHeader);
            }
        }


    }


}
