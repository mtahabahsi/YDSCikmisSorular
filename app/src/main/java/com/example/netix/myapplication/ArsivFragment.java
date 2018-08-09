package com.example.netix.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class ArsivFragment extends Fragment {
    ListView exams;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arsiv,container,false);
        exams = view.findViewById(R.id.exam_list_view);
        final String [] sinav={
                "2017 YDS Sonbahar İngilizce",
                "2017 YDS İlkbahar İngilizce",
                "2016 YDS Sonbahar İngilizce",
                "2016 YDS İlkbahar İngilizce",
                "2015 YDS Sonbahar İngilizce",
                "2015 YDS İlkbahar İngilizce",
                "2014 YDS Sonbahar İngilizce",
                "2014 YDS İlkbahar İngilizce",
                "2013 YDS Sonbahar İngilizce",
                "2013 YDS İlkbahar İngilizce"



        };
        ArrayAdapter<String> ListViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,sinav);

        exams.setAdapter(ListViewAdapter);
        exams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pdfName = sinav[position];
                Intent intent = new Intent(getActivity(), PdfViewer.class);
                intent.putExtra(EXTRA_MESSAGE, pdfName);
                startActivity(intent);
            }
        });


        return view;
    }

}
