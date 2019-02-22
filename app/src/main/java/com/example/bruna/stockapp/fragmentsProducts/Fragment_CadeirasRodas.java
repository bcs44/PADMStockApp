package com.example.bruna.stockapp.fragmentsProducts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruna.stockapp.fragmentsCadeiras.Fragment_Eletricas;
import com.example.bruna.stockapp.fragmentsCadeiras.Fragment_Pediátricas;
import com.example.bruna.stockapp.R;
import com.example.bruna.stockapp.SectionsPageAdapter;

public class Fragment_CadeirasRodas extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadeirasrodas, container, false);

        ViewPager mViewPager = view.findViewById(R.id.containercad);
        setupViewPager(mViewPager);

        TabLayout tabLayout = view.findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new Fragment_Eletricas(), "Eletricas");
        adapter.addFragment(new Fragment_Pediátricas(), "Pediátricas");
        viewPager.setAdapter(adapter);
    }

}