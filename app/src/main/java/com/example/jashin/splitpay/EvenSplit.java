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
    private String errorMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_split);
    }

    public void splitEvenly() {
        EditText totalText = (EditText) findViewById(R.id.totalPrice);
        EditText numberSplitText = (EditText) findViewById(R.id.splitNumber);
        String totalGetText = totalText.getText().toString();
        String splitGetText = numberSplitText.getText().toString();
        if (totalGetText.isEmpty() && splitGetText.isEmpty()) {
            errorMsg = "You did not enter the total and the number of people.";
        } else if (totalGetText.isEmpty()) {
            errorMsg = "You did not enter the total.";
        } else if (splitGetText.isEmpty()) {
            errorMsg = "You did not enter the number of people.";
        } else {
            totalPrice = Float.parseFloat(totalText.getText().toString());
            numberToSplit = Integer.parseInt(numberSplitText.getText().toString());
            if ((totalPrice == (float) 0.0) && numberToSplit == 0) {
                errorMsg = "You have entered 0.00 as the total and 0 as the number of people";
            } else if (totalPrice == (float) 0.0) {
                errorMsg = "You have entered 0.00 as the total.";
            } else if (numberToSplit == 0) {
                errorMsg = "You have entered 0 as the number of people.";
            }
        }
    }

    public void calculate(View view) {
        splitEvenly();
        System.out.println("totalPrice: " + totalPrice);
        System.out.println("numberToSplit: " + numberToSplit);
        System.out.println("finalSplitValue: " + finalSplitValue);
        TextView finalSplit = (TextView) findViewById(R.id.finalSplit);
        if (errorMsg.isEmpty()) {
            finalSplitValue = totalPrice / numberToSplit;
            finalSplit.setText(finalSplitValue + "");
        } else {
            finalSplit.setText(errorMsg);
            errorMsg = "";
        }
    }
}
