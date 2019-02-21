package com.example.bruna.stockapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.bruna.stockapp.fragmentsProducts.Fragment_CadeirasRodas;
import com.example.bruna.stockapp.fragmentsProducts.Fragment_Calcado;
import com.example.bruna.stockapp.fragmentsProducts.Fragment_Fisioterapia;
import com.example.bruna.stockapp.fragmentsProducts.Fragment_Mobilidade;
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

        adapter.addFragment(new Fragment_Calcado(), "Calçado");
        adapter.addFragment(new Fragment_Ortopedia(), "Ortopedia");
        adapter.addFragment(new Fragment_CadeirasRodas(), "Cadeiras de Rodas");
        adapter.addFragment(new Fragment_Fisioterapia(), "Fisioterapia");
        adapter.addFragment(new Fragment_Mobilidade(), "Mobilidade");

        viewPager.setAdapter(adapter);
    }



}