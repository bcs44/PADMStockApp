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

    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    List<String> arrayListFirstType = new ArrayList<>();
    List<String> arrayListSecondType = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        btnSearch = findViewById(R.id.button2);
        editText = findViewById(R.id.edittext);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    private void search() {

        String search = "Imobilizador";
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ortopedia").child("MembrosSuperiores");
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




}
