package com.murat.murat.ydskelimatik;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
 import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultTest extends AppCompatActivity {


    TextView tvDogrusayisi, tvYanlisSayisi, tvBasarimOrani;
    ProgressBar pbarDogru, pbarYanlis, pbarBasarim;

    public int myProgressDogru = 0, myProgressYanlis = 0, myProgressBasarim = 0;
    public int dogrusayisi, yanlissayisi, gorulenkelime;
    public int basarim = 0, gorulenPuan = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_test);

        tvDogrusayisi = (TextView) findViewById(R.id.tvDogruSayisi);
        tvYanlisSayisi = (TextView) findViewById(R.id.tvYanlisSayisi);
        tvBasarimOrani = (TextView) findViewById(R.id.txtBasarimOrani);

        pbarDogru = (ProgressBar) findViewById(R.id.pbarDogruYanit);
        pbarYanlis = (ProgressBar) findViewById(R.id.pbarYanlisYanit);
        pbarBasarim = (ProgressBar) findViewById(R.id.pbarBasarimOrani);


        dogrusayisi = 0;
        yanlissayisi = 0;
        gorulenkelime = 0;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                dogrusayisi = 0;
                yanlissayisi = 0;
                gorulenkelime = 0;
            } else {
                dogrusayisi = extras.getInt("dogruSayisi");
                yanlissayisi = extras.getInt("yanlisSayisi");
                gorulenkelime = extras.getInt("gorulenkelime");
            }
        } else {
            dogrusayisi = (Integer) savedInstanceState.getSerializable("dogruSayisi");
            yanlissayisi = (Integer) savedInstanceState.getSerializable("yanlisSayisi");
            gorulenkelime = (Integer) savedInstanceState.getSerializable("gorulenkelime");
        }

        if (gorulenkelime < 20) {

            pbarDogru.setMax(gorulenkelime);
            pbarYanlis.setMax(gorulenkelime);

            gorulenPuan = 100 / gorulenkelime;
            basarim = dogrusayisi*gorulenPuan;

        } else {
            basarim = dogrusayisi*5;

            Toast.makeText(getApplicationContext(), "deneme" + basarim, Toast.LENGTH_LONG).show();
        }

        int xx = 5;
        tvYanlisSayisi.setText(String.valueOf(yanlissayisi));
        tvDogrusayisi.setText(String.valueOf(dogrusayisi));
        tvBasarimOrani.setText(" % " + basarim + " başarılısın");


        new Thread(myThreadDogru).start();
        new Thread(myThreadYanlis).start();
        new Thread(myThreadBasarim).start();
    }

    private Runnable myThreadDogru = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (myProgressDogru <= dogrusayisi) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(50);
                } catch (Throwable t) {
                }
            }
        }

        Handler myHandle = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                myProgressDogru++;
                pbarDogru.setProgress(myProgressDogru);
            }
        };
    };
    private Runnable myThreadYanlis = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (myProgressYanlis <= yanlissayisi) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(50);
                } catch (Throwable t) {
                }
            }
        }

        Handler myHandle = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                myProgressYanlis++;
                pbarYanlis.setProgress(myProgressYanlis);
            }
        };
    };
    private Runnable myThreadBasarim = new Runnable() {
//Integer.parseInt(String.valueOf(tvBasarimOrani.getText().toString().split("% ")[1]))

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (myProgressBasarim <= basarim) {
                try {
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(50);
                } catch (Throwable t) {
                }
            }
        }

        Handler myHandle = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                myProgressBasarim++;
                pbarBasarim.setProgress(myProgressBasarim);

            }
        };
    };
}
