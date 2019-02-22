package com.example.bruna.stockapp.fragmentsProducts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

public class Fragment_Calcado extends Fragment {

    public Fragment_Calcado() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcado, container, false);

        ViewPager mViewPager = view.findViewById(R.id.containerrr);
        setupViewPager(mViewPager);

        TabLayout tabLayout = view.findViewById(R.id.tableLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    public void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new Fragment_CalcadoProfissional(), "Profissional");
        adapter.addFragment(new Fragment_CalcadoConforto(), "Conforto/Ortopedico");
        adapter.addFragment(new Fragment_CalcadoMalhas(), "Malhas");
        adapter.addFragment(new Fragment_CalcadoCrianca(), "Criança");
        adapter.addFragment(new Fragment_CalcadoChinelosOrtopedicos(), "Chinelos Ortopédicos");
        viewPager.setAdapter(adapter);
    }

}
