package com.example.jashin.splitpay;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static java.lang.System.currentTimeMillis;

public class Items extends AppCompatActivity {

    private String photoPath = "";
    private String textProcessed;
    private Timer t;

    private class Timer extends AsyncTask<String, Void, String> {

        private String textDetected = "";
        private TextView processingText;
        private ProgressBar pB;
        private Button cancelButton;
        private imgProc iP;

        @Override
        protected String doInBackground(String... photoPath) {
            processImage(photoPath[0]);
            waitForResults();
            return textDetected;
        }

        @Override
        protected void onPreExecute() {
            processingText = (TextView) findViewById(R.id.processingText);
            processingText.setVisibility(View.VISIBLE);
            pB = (ProgressBar) findViewById(R.id.progBar);
            pB.setVisibility(View.VISIBLE);
            cancelButton = (Button) findViewById(R.id.cancel);
            cancelButton.setClickable(true);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected void onPostExecute(String result) {
            processingText.setVisibility(View.GONE);
            pB.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
//          getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        public void processImage(String photoPath) {
            iP = new imgProc(photoPath);
            Thread t = new Thread(iP);
            t.start();
        }

        public void waitForResults() {
            while (textDetected.isEmpty()) {
                textDetected = iP.getTextDetected();
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("could not sleep");
                }
                System.out.println("text detected: " + textDetected);
                /*
                System.out.println("text detected: " + textDetected);
                long timeElapsed = 0;
                while (timeElapsed < 1000) {
                    timeElapsed = System.currentTimeMillis() - startTime;
                }
                textDetected = iP.getTextDetected();
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
        t = new Timer();
        String[] strArray = new String[1];
        strArray[0] = photoPath;
        t.execute(strArray);
//        try {
//            textProcessed = t.get();
//        } catch (Exception e) {
//            System.out.println("processing error");
//        }
        System.out.println("done with results");
    }

    public void cancel(View v) {
        finish();
        t.cancel(true);
    }
}
