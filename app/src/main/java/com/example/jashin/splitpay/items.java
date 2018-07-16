package com.example.jashin.splitpay;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import static java.lang.System.currentTimeMillis;

public class items extends AppCompatActivity {

    private String textDetected = "";
    private String photoPath = "";
    private ProgressBar pB;
    private imgProc iP;

    public class timer extends AsyncTask {

        protected void doInBackground() {
            
        }
        protected void onPostExecute() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        System.out.println("gets to new activity");
        photoPath = intent.getStringExtra("photoPath");
        processImage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pB = (ProgressBar) findViewById(R.id.progBar);
        pB.setVisibility(View.VISIBLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        //        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        waitForResults();
        System.out.println("done with results");
        pB.setVisibility(View.GONE);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void processImage() {
        iP = new imgProc(photoPath);
        Thread t = new Thread(iP);
        t.start();
    }

    public void waitForResults() {
        long startTime = System.currentTimeMillis();
        while (textDetected.isEmpty()) {
            System.out.println("text detected: " + textDetected);
            textDetected = iP.getTextDetected();
            /*
            long timeElapsed = 0;
            while (timeElapsed < 1000) {
                timeElapsed = System.currentTimeMillis() - startTime;
            }
            startTime = System.currentTimeMillis();
            */
        }
    }

    public void cancel(View v) {
        finish();
    }
}
