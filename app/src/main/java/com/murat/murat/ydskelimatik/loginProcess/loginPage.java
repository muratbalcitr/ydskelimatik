package com.murat.murat.ydskelimatik.loginProcess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.appevents.AppEventsLogger;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.murat.murat.ydskelimatik.R;
import com.murat.murat.ydskelimatik.giris_ekrani;

public class loginPage extends AppCompatActivity {
    FirebaseAuth frAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    EditText email, password;
    ProgressBar pbar;
    View view;
    CallbackManager callbackManager;
    LoginButton loginButton;
    FirebaseAuthInvalidCredentialsException frInvalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login_page);
        //  getActionBar().hide();
        email = (EditText) findViewById(R.id.LoginEmailEditText);
        password = (EditText) findViewById(R.id.LoginPasswordEditText);
        pbar = (ProgressBar) findViewById(R.id.pbar);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        frAuth = FirebaseAuth.getInstance();
        loginButton = (LoginButton) findViewById(R.id.loginButtonFace);


          authStateListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("ÇIKTI", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("ÇIKTI", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

    }

    private void facebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.loginButtonFace);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("başarılı", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Intent i = new Intent(loginPage.this, giris_ekrani.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Log.d("giriş iptal", "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "facebooka bağlanırken bir hata oluştu", Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("ÇIKTI", "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        frAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("ÇIKTI", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("ÇIKTI", "signInWithCredential", task.getException());
                            Toast.makeText(loginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    public void register(View view) {
        Intent i = new Intent(loginPage.this, register.class);
        startActivity(i);
    }

    public void login(View view) {
        //getting email and password from edit texts
        String Lemail = email.getText().toString().trim();
        String Lpassword = password.getText().toString().trim();
        try {
            //checking if email and passwords are empty
            if (TextUtils.isEmpty(Lemail)) {
                Toast.makeText(loginPage.this, "mail adresini giriniz", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(Lpassword)) {
                Toast.makeText(loginPage.this, "parolanızı giriniz", Toast.LENGTH_LONG).show();
                return;
            }

            if (!Lemail.matches("") && !Lpassword.matches("")) {
                pbar.setVisibility(View.VISIBLE);

                frAuth.signInWithEmailAndPassword(Lemail, Lpassword).addOnCompleteListener(loginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pbar.setVisibility(View.GONE);

                        FirebaseUser fbUser = task.getResult().getUser();

                        if (task.isSuccessful()) {
                           /**/
                            if (fbUser.isEmailVerified()) {
                                Intent i = new Intent(loginPage.this, giris_ekrani.class);
                                startActivity(i);
                                finish();///sonradan eklendi

                            } else {
                                Toast.makeText(loginPage.this, "doğrulanmamış e-mail lütfen doğrulayın", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Başarısız", Toast.LENGTH_LONG).show();

                        }
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetpassword(View view) {
        Intent i = new Intent(loginPage.this, resetPassword.class);
        startActivity(i);

    }
    @Override
    public void onStart() {
        super.onStart();
        frAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            frAuth.removeAuthStateListener(authStateListener);
        }
    }
}
