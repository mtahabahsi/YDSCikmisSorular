package com.example.netix.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Kayit extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        Button kayit_guncelle = findViewById(R.id.kayit);
        final TextView mail_kayit = findViewById(R.id.mail_kayit);
        final TextView sifre_kayit = findViewById(R.id.sifre_kayit);
        final TextView sifreT_kayit = findViewById(R.id.sifreT_kayit);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();

        kayit_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail_kayit.getText().toString();
                String sifre = sifre_kayit.getText().toString();
                String sifreT = sifreT_kayit.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sifre)) {
                    Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
                }
                if (sifre.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Parola en az 8 haneli olmalıdır", Toast.LENGTH_SHORT).show();
                }
                if (!sifre.equals(sifreT)) {
                    Toast.makeText(getApplicationContext(), "Girdiğiniz şifreler aynı değil", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(Kayit.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Kayit.this, "Kayıt Başarısız",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(Kayit.this, "Kayıt Başarılı",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Kayit.this, LoginScreen.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
