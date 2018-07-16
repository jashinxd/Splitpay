package com.example.jashin.splitpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class items extends AppCompatActivity {

    private String textDetected = "";
    ProgressBar pB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        pB = (ProgressBar) findViewById(R.id.progBar);
        imgProc iP = (imgProc)intent.getSerializableExtra("imgProc");
        textDetected = iP.getTextDetected();
    }

    public void waitForResults() {

    }

    public void cancel(View v) {
        finish();
    }
}
