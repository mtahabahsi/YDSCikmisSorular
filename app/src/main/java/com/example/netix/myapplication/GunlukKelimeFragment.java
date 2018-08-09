package com.example.netix.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class GunlukKelimeFragment extends Fragment {
    DatabaseReference kelimelerReference;
    ListView listView;
    HashMap<String, String> kelimeTurkceIngilizce;
    List<HashMap<String, String>> listItems;
    String [] ingilizce;
    String [] turkce;
    String[][] kelimeler;
    Random random=new Random();
    SimpleAdapter adapter;
    TextView textView;
    Button test;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gunluk_kelime, container, false);
        listView = view.findViewById(R.id.kelimeler_list_view);
        textView = view.findViewById(R.id.textView2);
        test = view.findViewById(R.id.test);
        kelimelerReference = FirebaseDatabase.getInstance().getReference().child("kelimeler");
        kelimeTurkceIngilizce = new HashMap<>();
        listItems = new ArrayList<>();
        ingilizce = new String [8];
        turkce = new String [8];
        test.setVisibility(View.INVISIBLE);
        if(internetKontrol()){

        }
        else{
            textView.setText("Bu kısma girebilmek için internetiniz olmalı.İnternet bağlatınız bulunmuyor :(((.");
        }
        kelimeler = new String [644][2];





        adapter = new SimpleAdapter(getActivity(),listItems,R.layout.list_item,
                new String[]{"First Line","Second Line"},
                new int[]{R.id.text1, R.id.text2});


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Quiz.class);
                i.putExtra("ingilizce", ingilizce);
                i.putExtra("turkce",turkce);
                startActivity(i);
                listItems.clear();
                listView.setAdapter(adapter);


            }
        });

        return view;
    }

    public void kelimeAl(){
        int index;
            kelimeTurkceIngilizce.clear();
            for (int i = 0; i < 8; i++) {
                index = random.nextInt(644);
                kelimeTurkceIngilizce.put(kelimeler[index][0], kelimeler[index][1]);
                ingilizce[i] = kelimeler[index][0];
                turkce[i] = kelimeler[index][1];
            }
            for (Object o : kelimeTurkceIngilizce.entrySet()) {
                HashMap<String, String> resultMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) o;
                resultMap.put("First Line", pair.getKey().toString());
                resultMap.put("Second Line", pair.getValue().toString());
                listItems.add(resultMap);
            }
            listView.setAdapter(adapter);
            test.setVisibility(View.VISIBLE);
        }


    public void onStart(){
        super.onStart();

        kelimelerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView.setText("Ezberlemen için 8 kelime");
                int j = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    kelimeler[j][0] = ds.getKey();
                    kelimeler[j][1]= String.valueOf(ds.getValue());
                    j++;
                }
                kelimeAl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("hata", String.valueOf(databaseError));
            }
        });
    }
    protected boolean internetKontrol() { //interneti kontrol eden method
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
