package com.example.jashin.splitpay;

import android.app.Person;
import android.content.ClipData;
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
        people = new ArrayList<>(0);
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
        PersonView newPerson;
        if (people.isEmpty()) {
            newPerson = new PersonView(view, this, R.id.person1item1, R.id.addPerson);
        } else {
            PersonView prevPerson = people.get(people.size() - 1);
            ArrayList<Integer> prevPersonItems = prevPerson.getItemIDs();
            int prevPersonLastId = prevPersonItems.get(prevPersonItems.size() - 1);
            newPerson = new PersonView(view, this, prevPersonLastId, R.id.addPerson);
        }
        people.add(newPerson);
        newPerson.drawPerson();
        EditText newPersonView = findViewById(newPerson.getPersonID());
        newPersonView.setHint("Name");
    }
}
