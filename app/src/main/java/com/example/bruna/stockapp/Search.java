package com.example.bruna.stockapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    Button btnSearch;
    EditText editText;
    List<String> arrayListFirstType = new ArrayList<>();
    List<String> arrayListSecondType = new ArrayList<>();
    List<Product> productsList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        btnSearch = findViewById(R.id.button2);
        editText = findViewById(R.id.edittext);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search2();
            }
        });

    }

    private void search() {

        String search = "bengala";


        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Calçado").child("Conforto");
        Query query = mFirebaseDatabaseReference.orderByChild("nome").startAt(search).endAt(search + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        System.out.println("ola");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    private void search2() {

        final String search = "Sapato";


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

        for(int i = 0; i< arrayListFirstType.size(); i++){

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(arrayListFirstType.get(i));

            final int finalI = i;
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrayListSecondType = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        arrayListSecondType.add(dataSnapshot1.getKey());
                    }
                    doTheRest(arrayListFirstType.get(finalI), search);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("Value", "Failed to read value.", error.toException());
                }
            });


        }


    }

    private void doTheRest(String firstType, String search) {
        for(int i = 0; i< arrayListSecondType.size(); i++){
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
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

}
