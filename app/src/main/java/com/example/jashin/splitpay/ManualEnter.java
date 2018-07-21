package com.example.jashin.splitpay;

import android.app.Person;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class ManualEnter extends AppCompatActivity {

    public Context context;
    public ArrayList<PersonView> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
    }

    @Override
    protected void onResume() {
        super.onResume();
        enterManually();
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
        EditText newPerson = new EditText(this);
        int newID = View.generateViewId();
        newPerson.setId(newID);

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());

        newPerson.setWidth(px);
        newPerson.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        ConstraintLayout mainLayout = findViewById(R.id.layout1);
        mainLayout.addView(newPerson);
        setContentView(mainLayout);

        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(newPerson.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(newPerson.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(newPerson.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(newPerson.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        set.connect(newPerson.getId(), ConstraintSet.TOP,
                R.id.person1item1, ConstraintSet.BOTTOM, 0);
        set.connect(newPerson.getId(), ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        set.setHorizontalBias(newPerson.getId(), (float) 0.052);
        //set.setVerticalBias(newPerson.getId(), (float) 0.063);
        set.applyTo(mainLayout);
    }

    public void addItem(View view) {
        EditText newItem = new EditText(this);
        int newID = View.generateViewId();
        newItem.setId(newID);

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());

        newItem.setWidth(px);
        newItem.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        ConstraintLayout mainLayout = findViewById(R.id.layout1);
        mainLayout.addView(newItem);
        setContentView(mainLayout);

        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(newItem.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(newItem.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(newItem.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(newItem.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        set.connect(newItem.getId(), ConstraintSet.TOP,
                R.id.person1item1, ConstraintSet.BOTTOM, 0);
        set.connect(newItem.getId(), ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        set.setHorizontalBias(newItem.getId(), (float) 0.337);
        //set.setVerticalBias(newItem.getId(), (float) 0.063);
        set.applyTo(mainLayout);
    }
}
