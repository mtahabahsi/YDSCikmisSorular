package com.example.netix.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Splashscreen extends AppCompatActivity {
    DatabaseReference kelimelerReference = FirebaseDatabase.getInstance().getReference().child("kelimeler");
    final String[][] kelimeler = new String [644][2];
    GunlukKelimeFragment nesne = new GunlukKelimeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread splashThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Intent intent = new Intent(Splashscreen.this,AnaSayfa.class);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }
    public void onStart(){
        super.onStart();
       /* kelimelerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int j = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    kelimeler[j][0] = ds.getKey();
                    kelimeler[j][1]= String.valueOf(ds.getValue());
                    j++;
                }
                Log.d("ingilizce",kelimeler[0][0]);
                nesne.kelimeAl(kelimeler);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("hata", String.valueOf(databaseError));
            }
        });*/


    }
}
