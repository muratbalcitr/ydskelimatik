package com.murat.murat.ydskelimatik.loginProcess;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.murat.murat.ydskelimatik.R;

public class register extends AppCompatActivity {

    EditText username, mail, password, passwordrepeat;
    Button cancel, register;
    private FirebaseAuth firebaseAuth;
    String Rusername,Remail,Rpassword,RPasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.etUsername);
        mail = (EditText) findViewById(R.id.etMail);
        password = (EditText) findViewById(R.id.etPassword);
        passwordrepeat = (EditText) findViewById(R.id.etPasswordRepeat);

    }

    public void cancel(View view) {
        Intent i = new Intent(register.this, loginPage.class);
        startActivity(i);
    }


    public void register(View view) {

          Rusername = username.getText().toString().trim();
          Remail = mail.getText().toString().trim();
          Rpassword = password.getText().toString().trim();
          RPasswordRepeat = passwordrepeat.getText().toString().trim();
        if (Rusername.matches("") && Rusername.length() < 5) {
            Toast.makeText(register.this, "Kullanıcı adını boş olamaz ve en az 5 harfli olacak  ", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Remail)) {
            Toast.makeText(register.this, "e-mail'i boş bırakmayınız!", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Rpassword)) {
            Toast.makeText(register.this, "parola giriniz ", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(RPasswordRepeat)) {
            Toast.makeText(register.this, "parola giriniz", Toast.LENGTH_SHORT).show();
        }

        if (!(Rpassword.equals(RPasswordRepeat))) {
            Toast.makeText(register.this, "parolalar uyuşmuyor", Toast.LENGTH_SHORT).show();
        }
        if (Rpassword.length() < 6) {
            Toast.makeText(register.this, "parola en az 6 karakter olmalı", Toast.LENGTH_SHORT).show();
        }
        if (Rpassword.length() >= 6 && Rpassword.equals(RPasswordRepeat) && Rusername.length() >= 5 && !Remail.matches("")) {

            firebaseAuth.createUserWithEmailAndPassword(Remail, Rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = task.getResult().getUser();
                        String userId = fbUser.getUid();
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("musteriler");//eğer üyeler tablosu yoka kendi oluşturuyor.

                        dbref.child(userId).child("username").setValue(Rusername);
                        dbref.child(userId).child("email").setValue(Remail);

                        fbUser.sendEmailVerification().addOnCompleteListener(register.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(register.this, "kayıt başarılı mail adresini kontrol ediniz", Toast.LENGTH_LONG).show();
                                           /* Intent i = new Intent(register.this, LoginActivity.class);
                                            startActivity(i);*/
                            }
                        });
                    }
                }
            });
        }
        else{
            Toast.makeText(register.this, "kayıt yapılamadı", Toast.LENGTH_LONG).show();

        }
    }
}
