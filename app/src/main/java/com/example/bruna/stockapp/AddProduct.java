package com.example.bruna.stockapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AddProduct extends AppCompatActivity {

    Spinner SpinFirstType, SpinSecondType;
    EditText ProdNameET, ProdDescET, ProdPriceET;
    Button BtnSave;
    List<String> arrayListFirstType = new ArrayList<>();
    List<String> arrayListSecondType = new ArrayList<>();
    String FirstType, SecondType;

    Boolean qrFromImage;

    private static int RESULT_LOAD_IMAGE = 1;

    Uri QRurl, Imageurl;
    Bitmap bitmap;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        //Tratamento do Dialog de progresso
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);

        ProdNameET = findViewById(R.id.ProdName);
        ProdDescET = findViewById(R.id.ProdDesc);
        ProdPriceET = findViewById(R.id.ProdPrice);
        BtnSave = findViewById(R.id.BtnSave);
        SpinFirstType = findViewById(R.id.SpinFirstType);
        SpinSecondType = findViewById(R.id.SpinSecondType);

        SecondType = "";
        getFirstType();

        SpinFirstType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                FirstType = (String) SpinFirstType.getItemAtPosition(position);
                getSecondType(FirstType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        SpinSecondType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SecondType  = (String) SpinSecondType.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }

    private void getFirstType() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(!dataSnapshot1.getKey().equals("Utilizadores")){
                        arrayListFirstType.add(dataSnapshot1.getKey());
                    }
                    addToSpinner(arrayListFirstType, SpinFirstType);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });

    }

    private void getSecondType(String sFirstType) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(sFirstType);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayListSecondType = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    arrayListSecondType.add(dataSnapshot1.getKey());
                    SpinSecondType.setVisibility(View.VISIBLE);
                    addToSpinner(arrayListSecondType, SpinSecondType);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });
    }

    public void createDialogQR(View view) {

        String name = String.valueOf(ProdNameET.getText());
        String desc = String.valueOf(ProdDescET.getText());
        String price = String.valueOf(ProdPriceET.getText());

        if (!name.equals("")) {
            if (!desc.equals("")) {
                if (!price.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Código Qr");
                    builder.setMessage("Quer criar código QR?");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            progress.show();
                            addQRCode();
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createDialogImage(false);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Inserir Preço",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Inserir Descrição",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Inserir Nome",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void addQRCode() {

        String name = String.valueOf(ProdNameET.getText());
        String desc = String.valueOf(ProdDescET.getText());
        String price = String.valueOf(ProdPriceET.getText());

        String inputValue = name + "&sep&" + desc + "&sep&" + price;


        if (!inputValue.equals("")) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = null;
            if (manager != null) {
                display = manager.getDefaultDisplay();
            }
            Point point = new Point();
            if (display != null) {
                display.getSize(point);
            }
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);

            try {
                bitmap = qrgEncoder.encodeAsBitmap();

            } catch (WriterException ignored) {

            }
        }

        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child("QRCodes").child(String.valueOf(ProdNameET.getText()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = riversRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                QRurl = taskSnapshot.getDownloadUrl();
                createDialogImage(true);

            }
        });
    }

    public void createDialogImage(final Boolean QR) {

        progress.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Imagem");
        builder.setMessage("Pretende Adicionar Uma Imagem?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                progress.show();
                addImage(QR);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveProduct(QR, false);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void addImage(final Boolean qr) {

        qrFromImage = qr;

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bm = null;

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bm != null) {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            final byte[] dataa = baos.toByteArray();

            final StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference[] sRef = new StorageReference[1];

            DatabaseReference myRef;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            if (SecondType.equals("")) {
                myRef = database.getReference().child(FirstType);
            } else {
                myRef = database.getReference().child(FirstType).child(SecondType);
            }

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long count = dataSnapshot.getChildrenCount();

                    if (SecondType.equals("")) {
                        sRef[0] = mStorageRef.child(FirstType).child(FirstType.substring(0, 3) + String.valueOf(count));
                    } else {
                        sRef[0] = mStorageRef.child(FirstType).child(SecondType).child(FirstType.substring(0, 3) + SecondType.substring(0, 3) + String.valueOf(count));
                    }

                    UploadTask uploadTask = sRef[0].putBytes(dataa);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Imageurl = taskSnapshot.getDownloadUrl();
                            //saveProduct(true);
                            saveProduct(qrFromImage, true);
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

        }
    }

    public void saveProduct(Boolean qrCode, boolean image) {

        final Product product = new Product();

        if (qrCode) {
            product.setQRCode(String.valueOf(QRurl));
        }

        if (image) {
            product.setImgURL(String.valueOf(Imageurl));
        }

        product.setNome(String.valueOf(ProdNameET.getText()));
        product.setDesc(String.valueOf(ProdDescET.getText()));
        product.setPreco(String.valueOf(ProdPriceET.getText()));

        final DatabaseReference myRef;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (SecondType.equals("")) {
            myRef = database.getReference().child(FirstType);
        } else {
            myRef = database.getReference().child(FirstType).child(SecondType);
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count = dataSnapshot.getChildrenCount();
                String s;
                if (SecondType.equals("")) {
                    s = FirstType.substring(0, 3);
                } else {
                    s = FirstType.substring(0, 3) + SecondType.substring(0, 3);
                }

                myRef.child(s + String.valueOf(count+1)).setValue(product);
                Toast.makeText(getApplicationContext(), "FEITO",
                        Toast.LENGTH_LONG).show();
                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addToSpinner(List<String> arrayList, Spinner spin) {

        String types[] = arrayList.toArray(new String[0]);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerArrayAdapter);

    }
}
