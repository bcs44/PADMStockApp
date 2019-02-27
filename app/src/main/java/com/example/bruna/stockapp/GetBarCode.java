package com.example.bruna.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetBarCode extends AppCompatActivity {
    Button bScan;
    TextView tvCode, tvFormat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bar_code);

        bScan = findViewById(R.id.bScan);
        tvCode = findViewById(R.id.tvCode);
        tvFormat = findViewById(R.id.tvFormat);

    }

    public void bScanClick(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE",
                "QR_CODE_MODE");

        startActivityForResult(intent, 1000);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                // Handle successful scan
                tvCode.setText(data.getStringExtra("SCAN_RESULT"));
                tvFormat.setText(data.getStringExtra("SCAN_RESULT_FORMAT"));
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                tvCode.setText("");
            }
        }
    }
}
