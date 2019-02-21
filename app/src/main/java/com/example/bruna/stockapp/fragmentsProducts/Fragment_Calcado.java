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

import com.example.bruna.stockapp.R;
import com.example.bruna.stockapp.SectionsPageAdapter;
import com.example.bruna.stockapp.fragmentsCalcado.Fragment_CalcadoChinelosOrtopedicos;
import com.example.bruna.stockapp.fragmentsCalcado.Fragment_CalcadoConforto;
import com.example.bruna.stockapp.fragmentsCalcado.Fragment_CalcadoCrianca;
import com.example.bruna.stockapp.fragmentsCalcado.Fragment_CalcadoMalhas;
import com.example.bruna.stockapp.fragmentsCalcado.Fragment_CalcadoProfissional;

import java.util.ArrayList;


public class Fragment_Calcado extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_calcado, container, false);

        msectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.containerrr);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new Fragment_CalcadoProfissional(), "Profissional");
        adapter.addFragment(new Fragment_CalcadoConforto(), "Conforto/Ortopedico");
        adapter.addFragment(new Fragment_CalcadoMalhas(), "Malhas");
        adapter.addFragment(new Fragment_CalcadoCrianca(), "Criança");
        adapter.addFragment(new Fragment_CalcadoChinelosOrtopedicos(), "Chinelos Ortopédicos");
        viewPager.setAdapter(adapter);
    }

}
