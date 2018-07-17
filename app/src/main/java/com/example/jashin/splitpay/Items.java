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

public class Items extends AppCompatActivity {

    private String photoPath = "";
    private String textProcessed;

    private class Timer extends AsyncTask<String, Void, String> {

        private String textDetected = "";
        private ProgressBar pB;
        private imgProc iP;

        @Override
        protected String doInBackground(String... photoPath) {
            processImage(photoPath[0]);
            waitForResults();
            return textDetected;
        }

        @Override
        protected void onPreExecute() {
            pB = (ProgressBar) findViewById(R.id.progBar);
            pB.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(String result) {
            pB.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        public void processImage(String photoPath) {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        System.out.println("gets to new activity");
        photoPath = intent.getStringExtra("photoPath");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer t = new Timer();
        String[] strArray = new String[1];
        strArray[0] = photoPath;
        t.execute(strArray);
        try {
            textProcessed = t.get();
        } catch (Exception e) {
            System.out.println("processing error");
        }
        System.out.println("done with results");
    }

    public void cancel(View v) {
        finish();
    }
}
