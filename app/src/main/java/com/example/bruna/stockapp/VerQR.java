package com.example.bruna.stockapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class VerQR extends AppCompatActivity {

    ImageView imageView;
    Button button;
    String nome;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verqr);

        Intent myIntent = getIntent();
        String qrURL = myIntent.getStringExtra("qrURL");
        nome = myIntent.getStringExtra("nome");


        imageView = findViewById(R.id.imageView2);
        button = findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isWriteStoragePermissionGranted();

            }
        });

        Glide.with(getApplicationContext()).load(qrURL).into(imageView);


    }

    public void isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted2");
                isReadStoragePermissionGranted();
            } else {

                Log.v("TAG", "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        } else {
            Log.v("TAG", "Permission is granted2");
        }
    }


    public void isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted1");
                downloadFile();
            } else {

                Log.v("TAG", "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        } else {
            Log.v("TAG", "Permission is granted1");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d("TAG", "External storage2");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
                    isReadStoragePermissionGranted();
                }
                break;

            case 3:
                Log.d("TAG", "External storage1");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //resume tasks needing this permission
                    downloadFile();

                }

                break;
        }
    }

    private void downloadFile() {

        imageView.buildDrawingCache();
        Bitmap bmp = imageView.getDrawingCache();


        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nome + ".png");

        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            scanFile(getApplicationContext(), Uri.fromFile(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void scanFile(Context context, Uri imageUri) {

        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open folder"));

    }

}