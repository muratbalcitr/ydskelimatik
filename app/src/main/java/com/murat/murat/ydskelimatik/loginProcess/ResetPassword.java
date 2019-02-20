package com.murat.murat.ydskelimatik.loginProcess;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.murat.murat.ydskelimatik.R;

public class ResetPassword extends AppCompatActivity {

    EditText mail ;
    FirebaseAuth frAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mail=(EditText)findViewById(R.id.etSendMail);
        frAuth = FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.pbar);
    }

    public void reset_Parola(View view) {
        String Rmail =mail.getText().toString().trim();
        frAuth.sendPasswordResetEmail(Rmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(resetPassword.this, "lütfen  mailinizi kontrol ediniz!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(resetPassword.this, "mail gönderilemedi!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
