package com.example.bruna.stockapp.fragmentsProducts;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruna.stockapp.fragmentMobilidade.Fragment_Andarilhos;
import com.example.bruna.stockapp.fragmentMobilidade.Fragment_Bengalas;
import com.example.bruna.stockapp.fragmentMobilidade.Fragment_Canadianas;
import com.example.bruna.stockapp.R;
import com.example.bruna.stockapp.SectionsPageAdapter;

public class Fragment_Mobilidade extends Fragment {
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private SectionsPageAdapter msectionsPageAdapter;
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobilidade, container, false);

        msectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.containermob);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

       adapter.addFragment(new Fragment_Andarilhos(), "Andarilhos");
       adapter.addFragment(new Fragment_Bengalas(), "Bengalas");
        adapter.addFragment(new Fragment_Canadianas(), "Canadianas");
        //  adapter.addFragment(new Fragment_CalcadoMalhas(), "Malhas");
        // adapter.addFragment(new Fragment_CalcadoCrianca(), "Criança");
        // adapter.addFragment(new Fragment_CalcadoChinelosOrtopedicos(), "Chinelos Ortopédicos");
        viewPager.setAdapter(adapter);
    }

}
