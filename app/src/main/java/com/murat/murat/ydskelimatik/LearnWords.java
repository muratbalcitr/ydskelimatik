package com.murat.murat.ydskelimatik;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class LearnWords extends AppCompatActivity {


  Button Buttongelenkelimeler, Buttongelenanlamlari, btnTekrar, btnOgrendim, btnTest;
  ArrayList<WordModel> dataList = new ArrayList<>();


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


    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        getAllKeyValue();
      }
    }, 0000);

    Buttongelenkelimeler = (Button) findViewById(R.id.tvKelime);
    Buttongelenanlamlari = (Button) findViewById(R.id.tvAnlamlari);
    btnTekrar = (Button) findViewById(R.id.btnTekrar);
    btnOgrendim = (Button) findViewById(R.id.btnOgrendim);
    btnTest = (Button) findViewById(R.id.btnTest);

    btnOgrendim.setEnabled(false);
    btnTekrar.setEnabled(false);
    btnTest.setEnabled(false);
  }

  public void getAllKeyValue() {
    final AlertDialog alert;
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setIcon(R.drawable.splashscreen);
    alertDialog.setTitle("Loading");
    alertDialog.setMessage("Lutfen bekleyiniz...");
    alertDialog.setCancelable(false);
    alert = alertDialog.create();
    alert.show();
        /*
        proje başladığında veritabanından nesneleri çekmek için kullanılan yapı
        */

    dbRef = FirebaseDatabase.getInstance().getReference().child("genelkelimeler");
    dbRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
          WordModel data = new WordModel();
          data.key = dsp.getKey().toString();
          data.value = dsp.getValue().toString();
          dataList.add(data);
        }
        alert.hide();
        btnOgrendim.setEnabled(true);
        btnTekrar.setEnabled(true);
        btnTest.setEnabled(true);
        kelimebelirleBaba();
        Buttongelenkelimeler.setText(dataList.get(45).key);
        Buttongelenanlamlari.setText(dataList.get(45).value);

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
    Intent i = new Intent(LearnWords.this, WordTest.class);
    startActivity(i);
  }
}
