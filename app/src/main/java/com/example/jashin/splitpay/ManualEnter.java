package com.example.jashin.splitpay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

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

    public void submitManual(View view) {
        System.out.println("people.size: " + people.size());
        for (int i = 0; i < people.size(); i++) {
            PersonView currentPersonView = people.get(i);
            EditText personField = (EditText) findViewById(currentPersonView.getPersonID());
            System.out.println("currentpersonID: " + currentPersonView.getPersonID());
            String personName = personField.getText().toString();
            System.out.println("new person: " + personName);
            Person newPerson = new Person(currentPersonView.getPersonID(), personName);
            ArrayList<Integer> itemIDs = currentPersonView.getItemIDs();
            for (int j = 0; j < itemIDs.size(); j++) {
                System.out.println("itemID: " + itemIDs.get(j));
                EditText itemField = (EditText) findViewById(itemIDs.get(j));
                float itemPrice = Float.parseFloat(itemField.getText().toString());
                newPerson.addPersonItem(itemPrice);
                System.out.println("new item price: " + itemPrice);
            }
        }
        System.out.println("successful submission");
    }
}
