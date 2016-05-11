package pt.egrupo.app;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;

/**
 * Created by ruie on 11/05/16.
 */
public abstract class BaseDecorationFragment<Entity> extends GenericRecyclerviewFragment<Entity> {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setAdapterAndDecor(lv);
    }

    protected abstract void setAdapterAndDecor(RecyclerView list);

}
