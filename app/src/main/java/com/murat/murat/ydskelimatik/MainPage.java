package com.murat.murat.ydskelimatik;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class giris_ekrani extends AppCompatActivity {
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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                dogrusayisi = "";
            } else {
                dogrusayisi = extras.getString("dogrusayimiz");
            }
        } else {
            dogrusayisi = (String) savedInstanceState.getSerializable("dogrusayimiz");
        }

        tvscore = (TextView) findViewById(R.id.tvPuan);
        tvscore.setText(dogrusayisi);

        btnstart = (Button) findViewById(R.id.btnKelimeOgren);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.buttonanimasyon);
        btnstart.setAnimation(animation);
        btnencokcikanlar = (Button) findViewById(R.id.btnOgrenilenler);
        btnencokcikanlar.setAnimation(animation);
        btnkendinidene = (Button) findViewById(R.id.btnDeneme);
        btnkendinidene.setAnimation(animation);
    }

    public void start(View view) {
        Intent i = new Intent(giris_ekrani.this, learnWords.class);
        startActivity(i);
    }

    public void encokcikanlar(View view) {
        Intent i = new Intent(giris_ekrani.this, ogrenilenler.class);
        startActivity(i);
    }

    public void kendinidene(View view) {
        Intent i = new Intent(giris_ekrani.this, kendiniDene.class);
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
                                giris_ekrani.this.finish();
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
