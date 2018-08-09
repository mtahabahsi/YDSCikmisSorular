package com.example.netix.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnaSayfa extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView textView;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();



       BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    startActivity(new Intent(AnaSayfa.this, LoginScreen.class));
                }
            }
        };
        mAuth = FirebaseAuth.getInstance();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_ev:
                   selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_arsiv:
                    selectedFragment = new ArsivFragment();
                    break;
                case R.id.navigation_gunluk_kelime:
                    selectedFragment = new GunlukKelimeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.three_dots_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.hakkinda:
                startActivity(new Intent(AnaSayfa.this, Hakkinda.class));
                break;
            case R.id.cikis:
                mAuth.signOut();
                startActivity(new Intent(AnaSayfa.this, LoginScreen.class));
                break;
            case R.id.hesap_sil:
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AnaSayfa.this, "Hesabın silindi.Yeni bir hesap oluştur!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AnaSayfa.this, Kayit.class));
                                        finish();

                                    } else {
                                        Toast.makeText(AnaSayfa.this, "Hesap silinemedi!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

        }
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
            startActivity(new Intent(AnaSayfa.this,LoginScreen.class));


        db = FirebaseDatabase.getInstance();

        DatabaseReference ezberlenenKelimeler = db.getReference("ezberlenenler");
        ezberlenenKelimeler.child("0");

    }




}
