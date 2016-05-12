package pt.egrupo.app.views.frags;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pt.egrupo.app.GenericRecyclerviewFragment;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.utils.RoundedCornersTransformation;
import pt.egrupo.app.utils.Utils;
import pt.egrupo.app.utils.endless.EndlessRecyclerViewAdapter;
import pt.egrupo.app.views.MarcarPresencaActivity;

/**
 * Created by ruie on 10/05/16.
 */
public class PresencaFragment extends GenericRecyclerviewFragment<Escoteiro> {

    MarcarPresencaActivity act;
    int divisao;

    RoundedCornersTransformation mTransform;

    @Override
    public void initView(View v) {
        act = (MarcarPresencaActivity)getActivity();

        if(getArguments() != null){
            divisao = getArguments().getInt("divisao");
        }

        mTransform = new RoundedCornersTransformation(
                Glide.get(getActivity()).getBitmapPool(),
                Utils.convertDpToPixel(4, mActivity),
                0);
    }

    @Override
    public void fetchData(int current_page) {
        mItems = act.getEscoteirosList(divisao);
        onFetchDataSuccess();
        setContent();
    }

    @Override
    public void setContent() {

        MarcarPresencaAdapter adapter;

        if(lv.getAdapter() == null) {
            adapter = new MarcarPresencaAdapter(mItems);
            lv.setAdapter(adapter);
            return;
        }

        adapter = (MarcarPresencaAdapter)lv.getAdapter();
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

    public class MarcarPresencaAdapter extends EndlessRecyclerViewAdapter<MarcarPresencaAdapter.MarcarPresencaViewHolder,Escoteiro> {

        public MarcarPresencaAdapter(ArrayList<Escoteiro> items) {
            super(mActivity,items);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_marcar_presenca, viewGroup, false);

            MarcarPresencaViewHolder vh = new MarcarPresencaViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolderImpl(MarcarPresencaViewHolder vh, int position) {
            Escoteiro e = mItems.get(position);

            vh.tvName.setText(e.getNome());
            vh.tvIdAssociativo.setText(e.getId_associativo() + "");

            Glide.with(mActivity)
                    .load(e.getAvatarUrl())
                    .bitmapTransform(mTransform)
                    .placeholder(R.drawable.default_pic)
                    .into(vh.ivAvatar);

            vh.cbPresente.setOnCheckedChangeListener(null);
            vh.cbFalta.setOnCheckedChangeListener(null);

            if(act.listPresentes.contains(e.getId())) {
                vh.cbPresente.setChecked(true);
                vh.cbFalta.setChecked(false);
            } else if(act.listFaltas.contains(e.getId())){
                vh.cbPresente.setChecked(false);
                vh.cbFalta.setChecked(true);
            } else {
                vh.cbPresente.setChecked(false);
                vh.cbFalta.setChecked(false);
            }

            vh.cbPresente.setOnCheckedChangeListener(vh);
            vh.cbFalta.setOnCheckedChangeListener(vh);

            vh.cbPresente.setTag(e);
            vh.cbFalta.setTag(e);
        }

        public void setItems(ArrayList<Escoteiro> items){
            this.mItems = items;
            notifyDataSetChanged();
        }

        public class MarcarPresencaViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{

            ImageView ivAvatar;
            TextView tvName;
            TextView tvIdAssociativo;
            RelativeLayout rlContainer;
            CheckBox cbPresente;
            CheckBox cbFalta;

            public MarcarPresencaViewHolder(View v) {
                super(v);

                ivAvatar = (ImageView)v.findViewById(R.id.ivAvatar);
                tvName = (TextView)v.findViewById(R.id.tvName);
                tvIdAssociativo = (TextView)v.findViewById(R.id.tvIdAssociativo);
                rlContainer = (RelativeLayout)v.findViewById(R.id.rlContainer);
                cbPresente = (CheckBox)v.findViewById(R.id.cbPresente);
                cbFalta = (CheckBox)v.findViewById(R.id.cbFalta);
            }


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Escoteiro e = (Escoteiro)compoundButton.getTag();

                ViewGroup parent = (LinearLayout)compoundButton.getParent().getParent();

                CheckBox presente = (CheckBox)parent.findViewById(R.id.cbPresente);
                CheckBox falta = (CheckBox)parent.findViewById(R.id.cbFalta);

                presente.setOnCheckedChangeListener(null);
                falta.setOnCheckedChangeListener(null);

                switch (compoundButton.getId()){
                    case R.id.cbPresente:

                        if(b){
                            act.listPresentes.add(e.getId());

                            if(falta.isChecked()){
                                falta.setChecked(false);
                                act.listFaltas.remove((Integer)e.getId());
                            }
                        } else {
                            act.listPresentes.remove((Integer)e.getId());
                        }

                        break;
                    case R.id.cbFalta:

                        if(b) {
                            act.listFaltas.add(e.getId());

                            if (presente.isChecked()) {
                                presente.setChecked(false);
                                act.listPresentes.remove((Integer)e.getId());
                            }
                        } else {
                            act.listFaltas.remove((Integer)e.getId());
                        }

                        break;
                }

                presente.setOnCheckedChangeListener(this);
                falta.setOnCheckedChangeListener(this);
            }
        }

    }
}
