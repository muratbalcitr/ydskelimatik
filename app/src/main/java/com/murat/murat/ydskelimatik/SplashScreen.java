package com.murat.murat.ydskelimatik;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i =  new Intent(splashscreen.this,giris_ekrani.class);
                startActivity(i);
            }
        }, 3000);


    }
}
