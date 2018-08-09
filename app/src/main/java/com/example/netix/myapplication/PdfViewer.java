package com.example.netix.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;


import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class PdfViewer extends AppCompatActivity {
    String pdfName;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfName = getIntent().getStringExtra(EXTRA_MESSAGE);
        pdfName = pdfName.replace(' ', '_');
        pdfName = pdfName.replace('i', 'ı');
        pdfName = pdfName.replace('İ', 'ı');
        pdfName = pdfName.toUpperCase();
        Toast.makeText(getApplicationContext(),pdfName, Toast.LENGTH_LONG).show();
        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(pdfName+".pdf").load();
        }
}
