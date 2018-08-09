package com.example.netix.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

import java.util.Random;

public class Quiz extends AppCompatActivity {
    public TextView soru;
    RadioGroup cevaplar;
    RadioButton a,b,c,d;
    Button button;
    int soruSayisi = 2;
    Random random=new Random();
    int index=0;
    boolean sonuc=true;
    int dogruSayisi = 0;
    MediaPlayer dogruSes;
    MediaPlayer yanlisSes;
    int dogruCevapId = 0;
    LottieAnimationView correct,incorrect;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        correct.setVisibility(View.INVISIBLE);
        incorrect.setVisibility(View.INVISIBLE);



        dogruSes = MediaPlayer.create(this,R.raw.dogru);
        yanlisSes = MediaPlayer.create(this,R.raw.yanlis);
        Bundle bundle = getIntent().getExtras();
        final String ingilizce[] = bundle.getStringArray("ingilizce");
        final String turkce[] = bundle.getStringArray("turkce");
        soru = findViewById(R.id.soru);
        cevaplar = findViewById(R.id.cevaplar);
        a = findViewById(R.id.one);
        b = findViewById(R.id.two);
        c = findViewById(R.id.three);
        d = findViewById(R.id.four);
        a.setId(Integer.parseInt("1"));
        b.setId(Integer.parseInt("2"));
        c.setId(Integer.parseInt("3"));
        d.setId(Integer.parseInt("4"));
        button = findViewById(R.id.button);
        button.setText(soruSayisi+". soruya geç →");
        soruHazirla(ingilizce,turkce);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.INVISIBLE);
                switch (dogruCevapId){
                    case 1: a.setBackgroundColor(Color.GREEN); break;
                    case 2: b.setBackgroundColor(Color.GREEN); break;
                    case 3: c.setBackgroundColor(Color.GREEN); break;
                    case 4: d.setBackgroundColor(Color.GREEN); break;
                }
                if(dogruCevapId == cevaplar.getCheckedRadioButtonId()){
                    dogruSayisi++;
                    dogruSes.start();

                    countDownTimer = new CountDownTimer(1100,1000) {
                        @Override
                        public void onTick(long l) {
                            correct.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            correct.setVisibility(View.INVISIBLE);
                            soruCagir(ingilizce,turkce);
                        }
                    }.start();

                }
                else{
                    yanlisSes.start();
                    Toast.makeText(getApplicationContext(),"YANLIŞ! Doğru Cevap=" + turkce[index-1],Toast.LENGTH_LONG).show();
                    switch (cevaplar.getCheckedRadioButtonId()){
                        case 1: a.setBackgroundColor(Color.RED); break;
                        case 2: b.setBackgroundColor(Color.RED); break;
                        case 3: c.setBackgroundColor(Color.RED); break;
                        case 4: d.setBackgroundColor(Color.RED); break;
                    }
                    countDownTimer = new CountDownTimer(1100,1000) {
                        @Override
                        public void onTick(long l) {
                            incorrect.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            incorrect.setVisibility(View.INVISIBLE);
                            soruCagir(ingilizce,turkce);
                        }
                    }.start();


                }

            }
        });


    }


    public void soruHazirla(String ingilizce[],String turkce[]){
        int cevap1,cevap2,cevap3,cevap4;
        boolean temp=false;

        soru.setText(String.format("Hangisi %s kelimesinin türkçesidir?", ingilizce[index]));


        cevap1 = random.nextInt(8);
        a.setText("A-)" + turkce[cevap1]);

        if (cevap1==index){
            temp = true;
            dogruCevapId = a.getId();
        }


        do {
            cevap2 = random.nextInt(8);
        }while(cevap2==cevap1);
        b.setText("B-)" + turkce[cevap2]);

        if (cevap2==index){
            temp = true;
            dogruCevapId = b.getId();
        }



        do {
            cevap3 = random.nextInt(8);
        }while(cevap3==cevap1||cevap3==cevap2);
        c.setText("C-)" + turkce[cevap3]);

        if (cevap3==index){
            temp = true;
            dogruCevapId = c.getId();
        }


        if (temp){
            do {
                cevap4 = random.nextInt(8);
            }while(cevap4==cevap1||cevap4==cevap2||cevap4==cevap3);
            d.setText("D-)" + turkce[cevap4]);
        }
        else{
            cevap4=index;
            d.setText("D-)" + turkce[cevap4]);
            dogruCevapId = d.getId();
        }
        index ++;
    }
    public void soruCagir(String ingilizce[],String turkce[]){
        button.setVisibility(View.VISIBLE);
        cevaplar.clearCheck();
        a.setBackgroundColor(Color.TRANSPARENT);
        b.setBackgroundColor(Color.TRANSPARENT);
        c.setBackgroundColor(Color.TRANSPARENT);
        d.setBackgroundColor(Color.TRANSPARENT);
        if(soruSayisi<9){
            soruSayisi++;
            soruHazirla(ingilizce,turkce);


            if (soruSayisi<=8)
                button.setText(soruSayisi+". soruya geç →");
            else
                button.setText("TESTİ BİTİR");
        }
        else if(dogruSayisi==8){

            Intent i = new Intent(Quiz.this,Result.class);
            i.putExtra("sonuc",sonuc);
            i.putExtra("dogru",dogruSayisi);
            startActivity(i);
            finish();
        }
        else {
            sonuc=false;
            Intent i = new Intent(Quiz.this,Result.class);
            i.putExtra("sonuc",sonuc);
            i.putExtra("dogru",dogruSayisi);
            startActivity(i);
            finish();
        }
    }
}
