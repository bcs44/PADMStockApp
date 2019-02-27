package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    List<String> arrayListFirstType = new ArrayList<>();
    List<String> arrayListSecondType = new ArrayList<>();
    List<Product> productsList = new ArrayList<>();
    String From, SearchString, SearchStringSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchString = getIntent().getStringExtra("SearchString");
        From = getIntent().getStringExtra("from");

        if (From.equals("Camera")) {
            SearchStringSecond = getIntent().getStringExtra("SearchStringSecond");
        }

        search2(SearchString);

    }

    private void search2(String searchString) {

        final String search = searchString;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    arrayListFirstType.add(dataSnapshot1.getKey());
                }
                doTheRestSecondType(search);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });
    }

    private void doTheRestSecondType(final String search) {

        for (int i = 0; i < arrayListFirstType.size(); i++) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(arrayListFirstType.get(i));
            final int finalI = i;

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrayListSecondType = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        arrayListSecondType.add(dataSnapshot1.getKey());
                        if (!arrayListFirstType.get(finalI).equals("Utilizadores")) {
                            if (!arrayListFirstType.get(finalI).equals("Chat")) {
                                doTheRest(arrayListFirstType.get(finalI), search);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("Value", "Failed to read value.", error.toException());
                }
            });
        }
    }

    private void doTheRest(String firstType, String sSearch) {
        String search;
        if (From.equals("Voice")) {
            search = sSearch.substring(0, 1).toUpperCase() + sSearch.substring(1, sSearch.length());
        } else {
            search = sSearch;
        }

        for (int i = 0; i < arrayListSecondType.size(); i++) {
            DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(firstType).child(arrayListSecondType.get(i));
            Query queryNome = mFirebaseDatabaseReference.orderByChild("nome").startAt(search).endAt(search + "\uf8ff");

            queryNome.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Product productsTable = dataSnapshot1.getValue(Product.class);
                            productsList.add(productsTable);
                        }
                        createProduct(productsList);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void createProduct(List<Product> productsList) {

        ListView mListView = findViewById(R.id.listViewProd);
        ArrayList<Product> productsListReady = new ArrayList<>();

        for (int i = 0; i < productsList.size(); i++) {
            Product products = new Product(productsList.get(i).getImg(), productsList.get(i).getNome(), productsList.get(i).getPreco(), productsList.get(i).getImgURL(), productsList.get(i).getDesc(), productsList.get(i).getQtd(), productsList.get(i).getQRCode());
            productsListReady.add(products);
        }


        if (From.equals("Camera")) {
            SearchWithDesc(productsList);
        } else {
            final ProductsListAdapter adapter = new ProductsListAdapter(getApplicationContext(), R.layout.list_item, productsListReady);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String nome = adapter.getItem(position).getNome();
                    Toast.makeText(getApplicationContext(), nome, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DetailProduct.class);
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

    private void SearchWithDesc(List<Product> productsList) {

        List<Product> productsListReady = new ArrayList<>();

        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getDesc().equals(SearchStringSecond)) {
                Product products = new Product(productsList.get(i).getImg(), productsList.get(i).getNome(), productsList.get(i).getPreco(), productsList.get(i).getImgURL(), productsList.get(i).getDesc(), productsList.get(i).getQtd(), productsList.get(i).getQRCode());
                productsListReady.add(products);
                From = "Voice";
                createProduct(productsListReady);
                return;
            }

        }
    }
}
