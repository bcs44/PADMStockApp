package com.example.bruna.stockapp.fragmentMobilidade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bruna.stockapp.DetailProduct;
import com.example.bruna.stockapp.Product;
import com.example.bruna.stockapp.ProductsListAdapter;
import com.example.bruna.stockapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Bengalas extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_productslist, container, false);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Mobilidade").child("Bengalas");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Product productsTable = dataSnapshot1.getValue(Product.class);

                    productsList.add(productsTable);
                }
                createProduct(productsList, view);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });
        return view;
    }

    public void createProduct(List<Product> productsList, View view) {
        Log.d("cena2", "aqui");
        ListView mListView = view.findViewById(R.id.listViewCalcado);
        ArrayList<Product> productsListReady = new ArrayList<>();
        for (int i = 0; i < productsList.size(); i++) {
            Product products = new Product(productsList.get(i).getImg(), productsList.get(i).getNome(), productsList.get(i).getPreco(), productsList.get(i).getImgURL(), productsList.get(i).getDesc(), productsList.get(i).getQtd(), productsList.get(i).getQRCode());
            productsListReady.add(products);
        }
        final ProductsListAdapter adapter = new ProductsListAdapter(getActivity(), R.layout.list_item, productsListReady);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DetailProduct.class);
                intent.putExtra("nome", adapter.getItem(position).getNome());
                intent.putExtra("preco", adapter.getItem(position).getPreco());
                intent.putExtra("imgURL", adapter.getItem(position).getImgURL());
                intent.putExtra("BigDesc", adapter.getItem(position).getDesc());
                intent.putExtra("quantidade", adapter.getItem(position).getQtd());
                intent.putExtra("qrURL", adapter.getItem(position).getQRCode());


                startActivity(intent);
            }


        });
    }


}


