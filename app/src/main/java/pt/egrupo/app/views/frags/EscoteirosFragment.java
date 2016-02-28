package pt.egrupo.app.views.frags;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import pt.egrupo.app.App;
import pt.egrupo.app.GenericRecyclerviewFragment;
import pt.egrupo.app.R;
import pt.egrupo.app.models.Escoteiro;
import pt.egrupo.app.network.HTTPStatus;
import pt.egrupo.app.network.SimpleTask;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.RoundedCornersTransformation;
import pt.egrupo.app.utils.Utils;
import pt.egrupo.app.utils.endless.EndlessRecyclerOnScrollListener;
import pt.egrupo.app.utils.endless.EndlessRecyclerViewAdapter;
import pt.egrupo.app.views.EscoteiroProfileActivity;

/**
 * Created by rsantos on 25/02/16.
 */
public class EscoteirosFragment extends GenericRecyclerviewFragment<Escoteiro> {

    @Override
    public void initView(View v) {

        mTransform = new RoundedCornersTransformation(
                Glide.get(getActivity()).getBitmapPool(),
                Utils.convertDpToPixel(4,mActivity),
                0);
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
                ELog.d("ElementosFrag", "Result(" + code + "): " + result);

                if(code == HTTPStatus.OK){
                    try {
                        JSONArray jarray = new JSONArray(result);

                        Gson gson =  new Gson();
                        mItems = new ArrayList<Escoteiro>();
                        for(int i = 0 ; i < jarray.length() ; i++ ){
                            mItems.add(0,gson.fromJson(jarray.getJSONObject(i).toString(),Escoteiro.class));
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
        },App.getBasePoint()+"/divisao/"+App.getDivisao()+"/escoteiros",SimpleTask.TYPE_GET).execute();

    }

    @Override
    public void setContent() {
        ElementosAdapter adapter = new ElementosAdapter(mItems);
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

    public class ElementosAdapter extends EndlessRecyclerViewAdapter<ElementosAdapter.ElementosViewHolder,Escoteiro> {

        public ElementosAdapter(ArrayList<Escoteiro> items) {
            super(mActivity,items);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_home_escoteiro, viewGroup, false);

            ElementosViewHolder vh = new ElementosViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolderImpl(ElementosViewHolder vh, int position) {
            Escoteiro e = mItems.get(position);

            vh.tvName.setText(e.getNome());
            vh.tvIdAssociativo.setText(e.getId_associativo()+"");

            Glide.with(mActivity)
                    .load(R.drawable.default_pic)
                    .bitmapTransform(mTransform)
                    .placeholder(R.drawable.default_pic)
                    .into(vh.ivAvatar);

            vh.rlContainer.setTag(e);

        }

        public class ElementosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView ivAvatar;
            TextView tvName;
            TextView tvIdAssociativo;
            RelativeLayout rlContainer;

            public ElementosViewHolder(View v) {
                super(v);

                ivAvatar = (ImageView)v.findViewById(R.id.ivAvatar);
                tvName = (TextView)v.findViewById(R.id.tvName);
                tvIdAssociativo = (TextView)v.findViewById(R.id.tvIdAssociativo);
                rlContainer = (RelativeLayout)v.findViewById(R.id.rlContainer);

                rlContainer.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Escoteiro e = (Escoteiro)view.getTag();
                Intent i = new Intent(mActivity, EscoteiroProfileActivity.class);
                i.putExtra("escoteiro", e);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptionsCompat options = ActivityOptionsCompat.
//                            makeSceneTransitionAnimation(this, imageView, getString(R.string.activity_image_trans));
//                    startActivity(i, options.toBundle());
//                } else {
                    startActivity(i);
//                }
            }
        }

    }

    RoundedCornersTransformation mTransform;
}
