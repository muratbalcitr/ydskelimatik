package com.murat.murat.ydskelimatik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
 import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPage extends AppCompatActivity {
    FirebaseUser fbuser = null;
    FirebaseAuth frAuth = null;
    Button btnstart, btnencokcikanlar, btnkendinidene;
    Animation animation;
    TextView tvscore;
    String dogrusayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris_ekrani);


        btnstart =  findViewById(R.id.btnKelimeOgren);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.buttonanimasyon);
        btnstart.setAnimation(animation);
        btnencokcikanlar =  findViewById(R.id.btnOgrenilenler);
        btnencokcikanlar.setAnimation(animation);
        btnkendinidene =  findViewById(R.id.btnDeneme);
        btnkendinidene.setAnimation(animation);
    }

    public void start(View view) {
        Intent i = new Intent(getApplicationContext(), LearnWords.class);
        startActivity(i);
    }

    public void encokcikanlar(View view) {
        Intent i = new Intent(getApplicationContext(), LearnedWord.class);
        startActivity(i);
    }

    public void kendinidene(View view) {
        Intent i = new Intent(getApplicationContext(), WordTest.class);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set title
                alertDialogBuilder.setTitle("EXIT ?");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainPage.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
