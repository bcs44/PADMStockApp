package com.example.bruna.stockapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.bruna.stockapp.fragmentsProducts.Fragment_Calcado;
import com.example.bruna.stockapp.fragmentsProducts.Fragment_Ortopedia;

public class Products extends AppCompatActivity {

    private SectionsPageAdapter msectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        msectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
      /*  adapter.addFragment(new Fragment_almofadas(), "Almofadas");
        adapter.addFragment(new Fragment_andarilhos(), "Andarilhos");
        adapter.addFragment(new Fragment_Bengalas(), "Bengalas");
        adapter.addFragment(new Fragment_Cadeiras(), "Cadeiras de Rodas");
        adapter.addFragment(new Fragment_Calcanheiras(), "Calcanheiras");*/
        adapter.addFragment(new Fragment_Calcado(), "Calçado");
        adapter.addFragment(new Fragment_Ortopedia(), "Ortopedia");
        /*adapter.addFragment(new Fragment_calcadoo(), "Calçado");
        adapter.addFragment(new Fragment_Camas(), "Camas");
        adapter.addFragment(new Fragment_Ortopedia(), "Ortopedia");
        adapter.addFragment(new Fragment_Canadianas(), "Canadianas");
        adapter.addFragment(new Fragment_sapatos(), "Sapatos");
        adapter.addFragment(new Fragment_Colchoes(), "Colchões");
        adapter.addFragment(new Fragment_fraldas(), "Fraldas");
        adapter.addFragment(new Fragment_Gruas(), "Gruas");
        adapter.addFragment(new Fragment_Meias(), "");
        adapter.addFragment(new Fragment_Palmilhas(), "");
        adapter.addFragment(new Fragment_Poltronas(), "");
        adapter.addFragment(new Fragment_Rampas(), "Rampas");
        adapter.addFragment(new Fragment_Seguranca(), "Segurança");
        adapter.addFragment(new Fragment_Tripes(), "Tripés");
        adapter.addFragment(new Fragment_Tabuas(), "Tábuas");*/

        viewPager.setAdapter(adapter);
    }



}