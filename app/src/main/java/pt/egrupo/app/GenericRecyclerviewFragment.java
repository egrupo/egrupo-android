package pt.egrupo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.utils.endless.EndlessRecyclerOnScrollListener;
import pt.egrupo.app.utils.endless.EndlessScroll;

/**
 * Created by rsantos on 01/09/15.
 */
public abstract class GenericRecyclerviewFragment<Entity> extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    public static final int INITIAL_PAGE = 1;

    public ArrayList<Entity> mItems;
    public EgrupoActivity mActivity;
    public App app;

    @Bind(R.id.lv)public RecyclerView lv;
    @Bind(R.id.swipe)SwipeRefreshLayout mSwipe;

    public EndlessRecyclerOnScrollListener mRecyclerOnScrollListener;

    View vError,vLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (EgrupoActivity)getActivity();
        app = (App)mActivity.getApplication();

        View v = inflater.inflate(R.layout.fragment_generic_recyclerview, container, false);

        ButterKnife.bind(this,v);

        //Add On Scroll AND Set Layout Manager
        if(this instanceof EndlessScroll) {
            mRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(setLayoutManager()) {
                @Override
                public void onLoadMore(int current_page) {
                    if(GenericRecyclerviewFragment.this instanceof EndlessScroll) {
                        ((EndlessScroll)GenericRecyclerviewFragment.this).addLoadingView();
                    }
                    fetchData(current_page);
                }
            };
            lv.addOnScrollListener(mRecyclerOnScrollListener);
        } else {
            //still need to set the LayoutManager
            setLayoutManager();
        }

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(GenericRecyclerviewFragment.this instanceof EndlessScroll) {
                    mRecyclerOnScrollListener.reset();
                }

                fetchData(INITIAL_PAGE);
            }
        });

        //TODO need to add thse views
//        vLoading = v.findViewById(R.id.vLoading);
//        vError = v.findViewById(R.id.vError);

        initView(v);

        showLoadingView();
        fetchData(INITIAL_PAGE);

        return v;
    }

    public LinearLayoutManager setLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(layoutManager);
        return layoutManager;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            mSwipe.setEnabled(true);
        } else {
            mSwipe.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.appBarLayout.removeOnOffsetChangedListener(this);
    }

    public void onFetchDataSuccess(){
        if(mActivity != null && isAdded()) {
            onFetchData();

            hideErrorView();

            if (mItems != null && mItems.size() == 0) {
                showErrorView(getEmptyIcon(), getEmptyTitle(), getEmptyMessage());
            }
        }
    }

    public void onFetchDataFailure(){
        if(mActivity != null && isAdded()) {
            onFetchData();

            if(mItems != null && mItems.size() > 0){
//                Snackbar.make(mActivity.mCoord, getString(R.string.snackbar_loadmore_error_text), Snackbar.LENGTH_LONG)
//                        .setAction(getString(R.string.snackbar_loadmore_error_action), new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (GenericRecyclerviewFragment.this instanceof EndlessScroll) {
//                                    mRecyclerOnScrollListener.onLoadMore(mRecyclerOnScrollListener.getCurrentPage());
//                                } else {
//                                    fetchData(INITIAL_PAGE);
//                                }
//                            }
//                        }).show();
//                return;
            }

        }
    }

    public void onFetchData(){
        mSwipe.setRefreshing(false);

        if(this instanceof EndlessScroll) {
            mRecyclerOnScrollListener.setLoadingView(false);
        }

        hideLoadingView();
    }

    public abstract void initView(View v);
    public abstract void fetchData(int current_page);
    public abstract void setContent();
    public abstract String getEmptyMessage();
    public abstract String getEmptyTitle();
    public abstract int getEmptyIcon();

    public void showLoadingView(){
        //TODO show loading view
    }

    public void hideLoadingView(){
        //TODO hide laoding view
    }

    private void showErrorView(int resourceId, String errorTitle, String errorMessage) {
        //TODO add error message
    }

    public void hideErrorView(){
        //TODO hide error message
    }


}
