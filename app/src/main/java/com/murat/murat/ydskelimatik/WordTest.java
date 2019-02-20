package com.murat.murat.ydskelimatik;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.murat.murat.ydskelimatik.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.murat.murat.ydskelimatik.R.*;
import static com.murat.murat.ydskelimatik.R.color.renk2;

public class WordTest extends AppCompatActivity {
    Button cevapA, cevapB, cevapC, cevapD;
    TextView gelenPopupSoru;
    ArrayList<kelimeogrenStructer> dataTest = new ArrayList<>();
    ProgressBar pbar;
    ArrayList<HashMap<String, String>> keyValue = null;
    int indis = 0;
    Random rnd = new Random();
    Database dataWord;
    public Button btn;
    ProgressBar kendinidenetime;
    Button[] btnDizi = null;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_kendini_dene);
        gelenPopupSoru = (TextView) findViewById(id.btnSoru);
        cevapA = (Button) findViewById(id.btnCevapA);
        cevapB = (Button) findViewById(id.btnCevapB);
        cevapC = (Button) findViewById(id.btnCevapC);
        cevapD = (Button) findViewById(id.btnCevapD);
        kelimeler();
        btnDizi = new Button[]{cevapA, cevapB, cevapC, cevapD};
    }

//pbHeaderProgress.getIndeterminateDrawable().setColorFilter(Color.RED, Mode.MULTIPLY);

    private void kelimeler() {
        dataWord = new Database(this);
        keyValue = dataWord.kelimeler();//kelime listesini alıyoruz
        if (keyValue.size() == 0) {//kelime listesi boşsa
            Toast.makeText(getApplicationContext(), "Henüz kelime Eklenmemiş.", Toast.LENGTH_LONG).show();
        } else {
            //kelime = new String[keyValue.size()];

            for (int i = 0; i < keyValue.size(); i++) {
                //String dd = keyValue.get(i).get(keyValue);
                kelimeogrenStructer kk = new kelimeogrenStructer();
                kk.key = keyValue.get(i).get("datakelime").toString();
                kk.value = keyValue.get(i).get("dataanlami").toString();
                kk.cevapA = keyValue.get(i).get("cevapBir").toString();
                kk.cevapB = keyValue.get(i).get("cevapIki").toString();
                kk.cevapC = keyValue.get(i).get("cevapUc").toString();
                dataTest.add(kk);
            }
            convert();
        }
    }


    int dogru = 0, yanlis = 0, son = 0;

    public void btncevap(View view) {
        btn = ((Button) view);
        String anlami = keyValue.get(indis).get("dataanlami").toString();


        if (btn.getText().equals(anlami)) {
            butonKapa();
            dogruYanit();
            son++;
        } else if (!btn.getText().equals(anlami)) {
            butonKapa();
            yanlisYanit();
            son++;
        }
        if (son >= 20 || son >= dataTest.size()) {
            sonucumuz();
        }
    }

    private void sonucumuz() {
        Intent i = new Intent(kendiniDene.this, sonucdegerlendirme.class);
        i.putExtra("dogruSayisi", dogru);
        i.putExtra("yanlisSayisi", yanlis);
        i.putExtra("gorulenkelime", son);
        startActivity(i);
        finish();
        son = 0;
    }

    private void dogruYanit() {
        dogru++;
        btn.setBackgroundResource(drawable.dogruyanit);
        indis = rnd.nextInt(dataTest.size());
        btn.setTextColor(Color.WHITE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                convert();

            }
        }, 1000);

    }

    private void yanlisYanit() {
        String anlami = keyValue.get(indis).get("dataanlami").toString();
        yanlis++;
        indis = rnd.nextInt(dataTest.size());
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundResource(drawable.yanlisyanit);
        for (int i = 0; i < btnDizi.length; i++) {
            if (btnDizi[i].getText().equals(anlami)) {
                btnDizi[i].setBackgroundResource(drawable.dogruyanit);
                btn.setTextColor(Color.WHITE);
                break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                convert();
            }
        }, 1000);
    }

    Animation animation;


    public void convert() {

        butonAc();
        String[] cevaplar = {dataTest.get(indis).value.toString(), dataTest.get(indis).cevapA.toString(), dataTest.get(indis).cevapB.toString(), dataTest.get(indis).cevapC.toString()};
        List<String> siklar = new ArrayList<>();
        siklar.add(cevaplar[0]);
        siklar.add(cevaplar[1]);
        siklar.add(cevaplar[2]);
        siklar.add(cevaplar[3]);

        Collections.shuffle(siklar);

        gelenPopupSoru.setText(dataTest.get(indis).key);
        cevapA.setText(siklar.get(0));
        cevapB.setText(siklar.get(1));
        cevapC.setText(siklar.get(2));
        cevapD.setText(siklar.get(3));

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                anim.buttonanimasyon);

        cevapA.startAnimation(animation);
        cevapB.startAnimation(animation);
        cevapC.startAnimation(animation);
        cevapD.startAnimation(animation);

        cevapA.setBackgroundResource(drawable.yuvarlak);
        cevapB.setBackgroundResource(drawable.yuvarlak);
        cevapC.setBackgroundResource(drawable.yuvarlak);
        cevapD.setBackgroundResource(drawable.yuvarlak);

        cevapA.setTextColor(Color.BLACK);
        cevapB.setTextColor(Color.BLACK);
        cevapC.setTextColor(Color.BLACK);
        cevapD.setTextColor(Color.BLACK);

    }

    private void butonAc() {
        cevapA.setClickable(true);
        cevapB.setClickable(true);
        cevapC.setClickable(true);
        cevapD.setClickable(true);
    }

    private void butonKapa() {
        cevapA.setClickable(false);
        cevapB.setClickable(false);
        cevapC.setClickable(false);
        cevapD.setClickable(false);
    }


}
