package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProduct extends AppCompatActivity {
    String qrURL;
    String nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent myIntent = getIntent();
        nome = myIntent.getStringExtra("nome");
        String preco = myIntent.getStringExtra("preco");
        String imgUrl = myIntent.getStringExtra("imgURL");
        String bigDesc = myIntent.getStringExtra("BigDesc");
        String Qtd = myIntent.getStringExtra("quantidade");
        qrURL = myIntent.getStringExtra("qrURL");

        TextView tvname = findViewById(R.id.tvName);
        TextView bigdesc = findViewById(R.id.tvbigdesc);
        TextView tvpreco = findViewById(R.id.tvPrice);
        TextView tvpqtd = findViewById(R.id.tvQtd);
        ImageView image = findViewById(R.id.ivImage);
        bigdesc.setText(bigDesc);
        tvname.setText(nome);
        tvpreco.setText(preco);
        tvpqtd.setText(Qtd);

        Glide.with(getApplicationContext()).load(imgUrl).into(image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_qr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_qr) {
            Intent intent = new Intent(DetailProduct.this, VerQR.class);
            intent.putExtra("qrURL", qrURL);
            intent.putExtra("nome", nome);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}