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
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView id, sifre ;
    Button giris ,kayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        id = findViewById(R.id.id);
        sifre = findViewById(R.id.sifre);
        giris = findViewById(R.id.bGiris);
        kayit = findViewById(R.id.bKayit);

        mAuth = FirebaseAuth.getInstance();





        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, Kayit.class);
                startActivity(intent);
            }
        });
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = id.getText().toString();
                final String pass = sifre.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Parola girilmemiş ise kullanıcıyı uyarıyoruz.
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(LoginScreen.this,AnaSayfa.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "E-Mail ya da şifre yanlış.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    public void onClick(View v) {
        String mail = id.getText().toString().trim();

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getApplication(), "Lütfen email adresinizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginScreen.this, "Yeni parola için gerekli bağlantı adresinize gönderildi!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginScreen.this, "Mail gönderme hatası!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        startActivity(new Intent(LoginScreen.this,AnaSayfa.class));
    }



}
