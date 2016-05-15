package pt.egrupo.app.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import pt.egrupo.app.App;
import pt.egrupo.app.EgrupoActivity;
import pt.egrupo.app.R;
import pt.egrupo.app.TabsAdapter;
import pt.egrupo.app.models.Atividade;
import pt.egrupo.app.views.dialogs.CriarAtividadeDialog;
import pt.egrupo.app.views.frags.AtividadesFragment;
import pt.egrupo.app.views.frags.EscoteirosFragment;
import pt.egrupo.app.views.frags.HomeFragment;

public class HomeActivity extends EgrupoActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TabsAdapter mAdapter;
    private ViewPager mViewPager;

    public static final int PAGE_HOME = 0;
    public static final int PAGE_ESCOTEIROS = 1;
    public static final int PAGE_ATIVIDADES = 2;
    int currentPosition = 0;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(App.getOrganizationSlug());

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentPosition){
                    case PAGE_ESCOTEIROS:
                        Snackbar.make(view, "Criar Escoteiro", Snackbar.LENGTH_SHORT).show();
                        break;
                    case PAGE_ATIVIDADES:
                        CriarAtividadeDialog dialog = new CriarAtividadeDialog();
                        dialog.show(getSupportFragmentManager(), "dialog_criar_atividade");
                        break;
                }

            }
        });
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerNavigationHeader(navigationView.getHeaderView(0));
        navigationView.setNavigationItemSelectedListener(this);

        //pager
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mAdapter = new TabsAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new HomeFragment(),"Início");
        mAdapter.addFragment(new EscoteirosFragment(),"Escoteiros");
        mAdapter.addFragment(new AtividadesFragment(),"Atividades");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                mViewPager.setCurrentItem(currentPosition,true);
                switch(tab.getPosition()){
                    case PAGE_HOME:
                        fab.hide();
                        break;
                    case PAGE_ESCOTEIROS:
                        fab.hide();
//                        fab.show();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_add_escoteiro, getTheme()));
//                        } else {
//                            fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_add_escoteiro));
//                        }
                        break;
                    case PAGE_ATIVIDADES:
                        fab.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_add_atividade, getTheme()));
                        } else {
                            fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_add_atividade));
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCoord = (CoordinatorLayout)findViewById(R.id.coordinator);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar_layout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_logout:
                App.logout();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                this.finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void drawerNavigationHeader(View headerView){
        ((TextView)headerView.findViewById(R.id.tvName)).setText(App.getUsername());
        ((TextView)headerView.findViewById(R.id.tvEmail)).setText(App.getEmail());
    }

}
