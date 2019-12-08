package com.murat.murat.ydskelimatik;

import android.content.Intent;
import android.os.Handler;
 import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i =  new Intent(SplashScreen.this,MainPage.class);
                startActivity(i);
            }
        }, 1000);


    }
}
