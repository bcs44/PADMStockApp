package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProduct extends AppCompatActivity {


    private ImageView imageView;
    private TextView tvname;
    private TextView tvpreco;
    private TextView bigdesc;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Intent myIntent = getIntent();
        String nome = myIntent.getStringExtra("nome");
        String preco= myIntent.getStringExtra("preco");
        String imgUrl = myIntent.getStringExtra("imgURL");
        String bigDesc = myIntent.getStringExtra("BigDesc");
        tvname = (TextView) findViewById(R.id.tvName);
        bigdesc = (TextView) findViewById(R.id.tvbigdesc);
        tvpreco = (TextView) findViewById(R.id.tvPrice);
        image = (ImageView) findViewById(R.id.ivImage);
        bigdesc.setText(bigDesc);
        tvname.setText(nome);
        tvpreco.setText(preco);

        Glide.with(getApplicationContext()).load(imgUrl).into(image);
    }
}