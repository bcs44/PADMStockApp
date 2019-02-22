package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView boxTv;
    TextView moneyTv;
    List<String> arrayListFirstType = new ArrayList<>();
    List<String> arrayListSecondType = new ArrayList<>();

    int stockUni;
    int stockMoney;


    TextView tvNome;
    TextView tvEmail;
    ImageView imageUser;
    String nome;
    String email;
    String imgURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent myIntent = getIntent();
        nome = myIntent.getStringExtra("nome");
        email = myIntent.getStringExtra("email");
        imgURL = myIntent.getStringExtra("imgURL");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        tvNome = headerView.findViewById(R.id.tvNome);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        imageUser  = headerView.findViewById(R.id.imageView);
        tvNome.setText(nome);
        tvEmail.setText(email);

        Glide.with(getApplicationContext()).load(imgURL).into(imageUser);

        changeStockDataMain();

    }

    private void changeStockDataMain() {

        boxTv = findViewById(R.id.textViewBox);
        moneyTv = findViewById(R.id.textViewMoney);

        //boxTv.setText("Aqui fica o valor em stock de tudo");
       // moneyTv.setText("Aqui fica o valor em stock em euros");

        stockUni = 0;
        stockMoney = 0;

        stockFirst();

        Log.d("w", String.valueOf(stockMoney));

    }

    private void stockFirst() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    arrayListFirstType.add(dataSnapshot1.getKey());
                }
                doTheRestSecondType();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });
    }

    private void doTheRestSecondType() {

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
                    }
                    if(!arrayListFirstType.get(finalI).equals("Utilizadores")){
                        doTheRest(arrayListFirstType.get(finalI));
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("Value", "Failed to read value.", error.toException());
                }
            });
        }

    }

    private void doTheRest(String firstType) {


        for (int i = 0; i < arrayListSecondType.size(); i++) {
            DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(firstType).child(arrayListSecondType.get(i));

            mFirebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Product> productsList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Product productsTable = dataSnapshot1.getValue(Product.class);
                        productsList.add(productsTable);
                    }
                    createValuesStockAndPrice(productsList);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("Value", "Failed to read value.", error.toException());
                }
            });

        }
    }

    private void createValuesStockAndPrice(List<Product> productsList) {

        String preco;

        for (int i = 0; i < productsList.size(); i++) {
            preco = productsList.get(i).getPreco().split(",")[0];
            stockUni = stockUni + Integer.valueOf(productsList.get(i).getQtd());
            stockMoney = stockMoney + Integer.valueOf(preco);
        }

        boxTv.setText("Existem " + stockUni + " unidades em stock");
        moneyTv.setText("O Stock contém " + stockMoney + "€");

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_products) {
            Intent intent = new Intent(MainActivity.this, Products.class);
            startActivity(intent);
        } else if (id == R.id.nav_talk) {
            Intent intent = new Intent(MainActivity.this, VoiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, AddProduct.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
