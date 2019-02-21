package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProduct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_detail);
        Intent myIntent = getIntent();
        String nome = myIntent.getStringExtra("nome");
        String preco= myIntent.getStringExtra("preco");
        String imgUrl = myIntent.getStringExtra("imgURL");
        String bigDesc = myIntent.getStringExtra("BigDesc");
        TextView tvname = findViewById(R.id.tvName);
        TextView bigdesc = findViewById(R.id.tvbigdesc);
        TextView tvpreco = findViewById(R.id.tvPrice);
        ImageView image = findViewById(R.id.ivImage);
        bigdesc.setText(bigDesc);
        tvname.setText(nome);
        tvpreco.setText(preco);

        Glide.with(getApplicationContext()).load(imgUrl).into(image);

    }
}