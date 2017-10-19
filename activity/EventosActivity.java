package com.example.gatanhoto.bdevento_projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gatanhoto.bdevento_projeto.R;
import com.example.gatanhoto.bdevento_projeto.adapter.TabsAdapter;

public class EventosActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //configura uma Intent explícita
                Intent intent = new Intent(EventosActivity.this, EventoActivity.class);
                //indica para a outra Activity qual o fragmento deve abrir
                intent.putExtra("qualFragmentAbrir", "EventoNovoFragment");
                //chama outra Activity
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d(TAG, "oncreate");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ViewPager
        //mapeia a ViewPager
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        //cria um adaptador para a ViewPager
        TabsAdapter adapter = new TabsAdapter(this, getSupportFragmentManager());
        //associa o adaptador a ViewPager
        viewPager.setAdapter(adapter);
        //define a quantidades de páginas
        viewPager.setOffscreenPageLimit(3);


        // Tabs
        //mapeia o container das tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //associa a ViewPager ao container
        tabLayout.setupWithViewPager(viewPager);
        //define que é rolável
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //insere o handler na lista de ouvintes do componente
        tabLayout.setOnTabSelectedListener(this);
        Log.d(TAG, "onresume");

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       // int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.nav_fragment_list_eventos:
                //não faz nada por enquanto
                break;
            case R.id.nav_fragment_sobre:
                Toast.makeText(EventosActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(EventosActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
