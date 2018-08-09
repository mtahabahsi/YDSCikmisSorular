package com.example.netix.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Result extends AppCompatActivity {
    LottieAnimationView animationView;
    MediaPlayer win;
    MediaPlayer lose;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        View view = this.getWindow().getDecorView();
        animationView = findViewById(R.id.animationView);

        lose = MediaPlayer.create(this, R.raw.lose);
        win = MediaPlayer.create(this, R.raw.win);

        TextView text = findViewById(R.id.text);
        Bundle bundle = getIntent().getExtras();
        boolean sonuc = bundle.getBoolean("sonuc");
        int dogruSayisi = bundle.getInt("dogru");

        if (sonuc){
            view.setBackgroundColor(Color.GREEN);
            text.setText("Congratulations!!! \n Bütün soruları doğru cevapladınız!!! ");
            win.start();
            animationView.setAnimation("win.json");
            animationView.playAnimation();

        }
        else{
            view.setBackgroundColor(Color.RED);
            text.setText("Hepsini bilemedin \n" + dogruSayisi + " soruyu doğru yanıtladın.");
            lose.start();
            animationView.setAnimation("lose.json");
            animationView.playAnimation();
        }

        Thread splashThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    Intent intent = new Intent(Result.this,AnaSayfa.class);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();
    }

}

