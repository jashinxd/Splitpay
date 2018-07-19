package com.example.jashin.splitpay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EvenSplit extends AppCompatActivity {

    private float totalPrice;
    private int numberToSplit;
    private float finalSplitValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_split);
    }

    public void splitEvenly() {
        EditText totalText = (EditText) findViewById(R.id.totalPrice);
        totalPrice = Float.parseFloat(totalText.getText().toString());
        EditText numberSplitText = (EditText) findViewById(R.id.splitNumber);
        numberToSplit = Integer.parseInt(numberSplitText.getText().toString());
    }

    public void calculate(View view) {
        splitEvenly();
        finalSplitValue = totalPrice / numberToSplit;
        System.out.println("totalPrice: " + totalPrice);
        System.out.println("numberToSplit: " + numberToSplit);
        System.out.println("finalSplitValue: " + finalSplitValue);
        TextView finalSplit = (TextView) findViewById(R.id.finalSplit);
        finalSplit.setText(finalSplitValue + "");
    }
}
