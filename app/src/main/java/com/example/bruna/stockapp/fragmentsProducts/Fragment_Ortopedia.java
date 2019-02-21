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

import com.example.bruna.stockapp.fragmentsOrtopedia.Fragment_OrtMembrosSuperiores;
import com.example.bruna.stockapp.fragmentsOrtopedia.Fragment_OrtTroncoCabeca;
import com.example.bruna.stockapp.R;
import com.example.bruna.stockapp.SectionsPageAdapter;

public class Fragment_Ortopedia extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_ortopedia, container, false);

        msectionsPageAdapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.containerr);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new Fragment_OrtTroncoCabeca(), "Tronco e Cabeça");
        adapter.addFragment(new Fragment_OrtMembrosSuperiores(), "MembrosSuperiores");
      //  adapter.addFragment(new Fragment_CalcadoMalhas(), "Malhas");
       // adapter.addFragment(new Fragment_CalcadoCrianca(), "Criança");
       // adapter.addFragment(new Fragment_CalcadoChinelosOrtopedicos(), "Chinelos Ortopédicos");
        viewPager.setAdapter(adapter);
    }

}

