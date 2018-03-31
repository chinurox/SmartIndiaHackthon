package com.example.gargc.smartindiahackthon.Activity.Investor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gargc.smartindiahackthon.Model.DownloadFile;
import com.example.gargc.smartindiahackthon.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewPichDeck extends AppCompatActivity {

    public static  int SPLASH_SCREEN_TIMEOUT=4000;

    ProgressDialog progressDialog;


    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pich_deck);
        progressDialog=new ProgressDialog(this);

        progressDialog.setMessage("Downloading..");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                pdfView=(PDFView) findViewById(R.id.pitchdeckview);
                pdfView.fromAsset("pitchdeck.pdf").load();


            }
        },SPLASH_SCREEN_TIMEOUT);

        try {
            new DownloadFile().execute("http://192.168.101.13:3000/assets/pitch_deck.pdf", "pitchdeck.pdf");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pdfView=(PDFView) findViewById(R.id.pitchdeckview);
//
//                new RetrievePdfStream().execute("https://svfair.ru/wp-content/uploads/Karina-Sotnik.pdf");
//
//
//            }
//        },SPLASH_SCREEN_TIMEOUT);


    }




    class RetrievePdfStream extends AsyncTask<String,Void,InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream=null;
            try
            {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();

                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            progressDialog.dismiss();
            pdfView.fromStream(inputStream).load();
        }
    }
}
