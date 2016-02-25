package pt.egrupo.app.views.frags;

import android.view.View;

import pt.egrupo.app.GenericRecyclerviewFragment;

/**
 * Created by rsantos on 25/02/16.
 */
public class HomeFragment extends GenericRecyclerviewFragment {

    @Override
    public void initView(View v) {

    }

    @Override
    public void fetchData(int current_page) {

        setContent();
    }

    @Override
    public void setContent() {

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
}
