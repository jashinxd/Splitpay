package com.example.jashin.splitpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class items extends AppCompatActivity {

    private String textDetected = "";
    private ProgressBar pB;
    private imgProc iP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        iP = (imgProc)intent.getSerializableExtra("imgProc");
        pB = (ProgressBar) findViewById(R.id.progBar);
        pB.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        waitForResults();
        pB.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void waitForResults() {
        while (textDetected.equals("")) {
            textDetected = iP.getTextDetected();
            try {
                Thread.sleep(3000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void cancel(View v) {
        finish();
    }
}
