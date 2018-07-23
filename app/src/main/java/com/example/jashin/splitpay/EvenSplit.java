package com.example.jashin.splitpay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class EvenSplit extends AppCompatActivity {

    private float totalPrice;
    private int numberToSplit;
    private float finalSplitValue;
    private float tipPercent;
    private String errorMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_split);
    }

    public void onOtherClick(View view) {
        if (((RadioButton) view).isChecked()) {
            EditText otherEditText = findViewById(R.id.otherInput);
            otherEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            otherEditText.setEnabled(true);
        }
    }

    public void setTip() {
        RadioGroup tipGroup = findViewById(R.id.tipGroup);
        int checkedID = tipGroup.getCheckedRadioButtonId();
        switch (checkedID) {
            case R.id.percent0:
                tipPercent = (float) 0;
                break;
            case R.id.percent15:
                tipPercent = (float) 0.15;
                break;
            case R.id.percent18:
                tipPercent = (float) 0.18;
                break;
            case R.id.percent20:
                tipPercent = (float) 0.20;
                break;
            case R.id.other:
                EditText otherInput = (EditText) findViewById(R.id.otherInput);
                String tipPercentText = otherInput.getText().toString();
                if (tipPercentText.isEmpty()) {
                    errorMsg = "You did not enter a tip";
                } else {
                    tipPercent = (float) Integer.parseInt(tipPercentText) * (float) 0.01;
                }
                break;
            case -1:
               errorMsg = "You did not select a tip";
               break;
        }
    }


    public void splitEvenly() {
        EditText totalText = (EditText) findViewById(R.id.totalPrice);
        EditText numberSplitText = (EditText) findViewById(R.id.splitNumber);
        String totalGetText = totalText.getText().toString();
        String splitGetText = numberSplitText.getText().toString();
        if (totalGetText.isEmpty() && splitGetText.isEmpty()) {
            errorMsg = "You did not enter the total and the number of personViews.";
        } else if (totalGetText.isEmpty()) {
            errorMsg = "You did not enter the total.";
        } else if (splitGetText.isEmpty()) {
            errorMsg = "You did not enter the number of personViews.";
        } else {
            totalPrice = Float.parseFloat(totalText.getText().toString());
            numberToSplit = Integer.parseInt(numberSplitText.getText().toString());
            if ((totalPrice == (float) 0.0) && numberToSplit == 0) {
                errorMsg = "You have entered 0.00 as the total and 0 as the number of personViews";
            } else if (totalPrice == (float) 0.0) {
                errorMsg = "You have entered 0.00 as the total.";
            } else if (numberToSplit == 0) {
                errorMsg = "You have entered 0 as the number of personViews.";
            }
        }
    }

    public void calculate(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        splitEvenly();
        setTip();
        System.out.println("totalPrice: " + totalPrice);
        System.out.println("numberToSplit: " + numberToSplit);
        System.out.println("finalSplitValue: " + finalSplitValue);
        System.out.println("tipPercent: " + tipPercent);
        TextView finalSplit = (TextView) findViewById(R.id.finalSplit);
        if (errorMsg.isEmpty()) {
            finalSplitValue = (totalPrice * (1 + tipPercent)) / numberToSplit;
            finalSplit.setText(finalSplitValue + "");
        } else {
            finalSplit.setText(errorMsg);
            errorMsg = "";
        }
    }
}
