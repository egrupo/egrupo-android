package pt.egrupo.app.utils.endless;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by rsantos on 04/09/15.
 */
public abstract class EndlessRecyclerViewAdapter<ViewHolderEntity,ModelEntity> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_NORMAL = 0;
    public static final int VIEW_FOOTER = 1;

    Animation anim;

    Context mContext;

    public ArrayList<ModelEntity> mItems;

    public abstract void onBindViewHolderImpl(ViewHolderEntity vh,int position);
    public abstract RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup);

    public EndlessRecyclerViewAdapter(Context context,ArrayList<ModelEntity> mItems) {
        this.mItems = mItems;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;

        if(viewType == VIEW_FOOTER) {
            //TODO add loading footer view

            FooterViewHolder vh = null;// = new FooterViewHolder(v);
            return vh;
        }

        return onCreateViewHolderImpl(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int i) {

        if(getItemViewType(i) == VIEW_FOOTER) {

        } else {
            onBindViewHolderImpl((ViewHolderEntity) vh, i);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(mItems.get(position) == null) {
            return VIEW_FOOTER;
        }

        return VIEW_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View v){
            super(v);
        }

    }

}
