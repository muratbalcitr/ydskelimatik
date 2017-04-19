package com.murat.murat.ydskelimatik;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.murat.murat.ydskelimatik.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class learnWords extends AppCompatActivity {


    Button Buttongelenkelimeler, Buttongelenanlamlari;
    ImageButton ses;
    ArrayList<dataStructure> dataList = new ArrayList<>();
    ProgressBar pbar;


    SQLiteDatabase db;
    int indis = 0;
    Random rnd = new Random();
    DatabaseReference dbRef;
    Database dbs = null;
    RelativeLayout rltv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learnwords);

        dbs = new Database(this);

        db = openOrCreateDatabase("ogrenilenkelimeler",
                MODE_PRIVATE,
                null);

        pbar = (ProgressBar) findViewById(R.id.pbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllKeyValue();
            }
        }, 0000);

        Buttongelenkelimeler = (Button) findViewById(R.id.tvKelime);
        Buttongelenanlamlari = (Button) findViewById(R.id.tvAnlamlari);
        ses = (ImageButton) findViewById(R.id.btnSes);


    }

    public void getAllKeyValue() {
        /*
        proje başladığında veritabanından nesneleri çekmek için kullanılan yapı
        */

        pbar.setVisibility(View.VISIBLE);
        dbRef = FirebaseDatabase.getInstance().getReference().child("genelkelimeler ");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    dataStructure data = new dataStructure();
                    data.key = dsp.getKey().toString();
                    data.value = dsp.getValue().toString();
                    dataList.add(data);
                }
                kelimebelirleBaba();
                Buttongelenkelimeler.setText(dataList.get(45).key);
                Buttongelenanlamlari.setText(dataList.get(45).value);

                pbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void kelimebelirleBaba() {
        indis = rnd.nextInt(dataList.size());
        Buttongelenkelimeler.setText(dataList.get(indis).key);//random belirlendikten sonra ilk kelimeleri atıyor
        Buttongelenanlamlari.setText(dataList.get(indis).value);
    }


    public void randomrenk() {
        int i = 0;
        rltv = (RelativeLayout) findViewById(R.id.relativelayoutstart);
        List<Integer> dizi = new ArrayList<>();

        dizi.add(R.color.renk1);
        dizi.add(R.color.renk2);
        dizi.add(R.color.renk3);
        dizi.add(R.color.renk4);
        dizi.add(R.color.renk5);
        dizi.add(R.color.renk6);
        dizi.add(R.color.renk7);
        dizi.add(R.color.renk8);


        Collections.shuffle(dizi);

        rltv.setBackgroundColor(rnd.nextInt(dizi.get(0)));
    }

    public void Ilearned(View view) {

        indis = rnd.nextInt(dataList.size() - 1);

        String keyKelime = dataList.get(indis).key;
        String valueAnlam = dataList.get(indis).value;


        Buttongelenkelimeler.setText(keyKelime.toLowerCase());
        Buttongelenanlamlari.setText(valueAnlam.toLowerCase());

        randomrenk();

        String cevap1 = dataList.get(rnd.nextInt(dataList.size() - 1)).value;
        String cevap2 = dataList.get(rnd.nextInt(dataList.size() - 1)).value;
        String cevap3 = dataList.get(rnd.nextInt(dataList.size() - 1)).value;

        dbs.kelime_ekle(keyKelime, valueAnlam, cevap1, cevap2, cevap3);
        Toast.makeText(getApplicationContext(), "eklendi", Toast.LENGTH_SHORT).show();
    }

    public void terkrar_sor(View view) {
        randomrenk();
        indis = rnd.nextInt(dataList.size());
        Buttongelenkelimeler.setText(dataList.get(indis).key.toLowerCase());
        Buttongelenanlamlari.setText(dataList.get(indis).value.toLowerCase());
    }

    public void testebasla(View view) {
        Intent i = new Intent(learnWords.this, kendiniDene.class);
        startActivity(i);
    }
}