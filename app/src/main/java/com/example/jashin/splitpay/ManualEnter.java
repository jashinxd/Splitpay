package com.example.jashin.splitpay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ManualEnter extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
    }

    public void enterManually() {
        EditText person1 = (EditText) findViewById(R.id.person1);
        EditText person1item1 = (EditText) findViewById(R.id.person1item1);
        String person1String = person1.getText().toString();
        String person1item1String = person1item1.getText().toString();
        System.out.println(person1String);
        System.out.println(person1item1String);
    }

    public void addPerson(View view) {
        EditText newPerson = new EditText(context);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout1);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainLayout.addView(newPerson, params);
    }

    public void addItem(View view) {
        EditText newItem = new EditText(context);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.layout1);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainLayout.addView(newItem, params);
    }
}
