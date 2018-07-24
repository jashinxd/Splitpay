package com.example.jashin.splitpay;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ManualEnter extends AppCompatActivity {

    public Context context;
    public ArrayList<PersonView> personViews;
    public ArrayList<Person> people;
    private float taxPercent;
    private float tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        personViews = new ArrayList<>(0);
        addPerson(findViewById(R.id.addPerson));
        people = new ArrayList<>(0);
        //System.out.println(personViews.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        enterManually();
    }

    public void enterManually() {
//        EditText person1 = (EditText) findViewById(R.id.person1);
//        EditText person1item1 = (EditText) findViewById(R.id.person1item1);
//        String person1String = person1.getText().toString();
//        String person1item1String = person1item1.getText().toString();
//        System.out.println(person1String);
//        System.out.println(person1item1String);
    }

    public void addPerson(View view) {
        PersonView newPerson;
        if (personViews.isEmpty()) {
            newPerson = new PersonView(view, this, ConstraintSet.PARENT_ID, R.id.removePerson);
            newPerson.drawNewPerson();
        } else {
            PersonView prevPerson = personViews.get(personViews.size() - 1);
            ArrayList<Integer> prevPersonItems = prevPerson.getItemIDs();
            int prevPersonLastId = prevPersonItems.get(prevPersonItems.size() - 1);
            newPerson = new PersonView(view, this, prevPersonLastId, R.id.removePerson);
            newPerson.drawNewPerson();
            prevPerson.setBottomConstraint(newPerson.getPersonID());
        }
        personViews.add(newPerson);
        EditText newPersonView = findViewById(newPerson.getPersonID());
        newPersonView.setHint("Name");
    }

    public void removePerson(View view) {
        if (personViews.size() > 1) {
            System.out.println("getssssssss");
            PersonView personToRemove = personViews.remove(personViews.size() - 1);
            personToRemove.removeAllItems();
            System.out.println("removes all items");
            ViewGroup parentView = (ViewGroup) findViewById(personToRemove.getPersonID()).getParent();
            parentView.removeView(findViewById(personToRemove.getPersonID()));
            parentView.removeView(findViewById(personToRemove.getAddItemID()));
            parentView.removeView(findViewById(personToRemove.getRemoveItemID()));
            System.out.println("removes everything");

            PersonView prevPerson = personViews.get(personViews.size() - 1);
            prevPerson.setBottomConstraint(R.id.addPerson);
            prevPerson.drawPerson();
        }
    }

    public void onOtherClick(View view) {
        if (((RadioButton) view).isChecked()) {
            EditText otherEditText = findViewById(R.id.otherInput);
            otherEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            otherEditText.setEnabled(true);
        }
    }

    public void setTip() {
        TextView errorTextView = (TextView) findViewById(R.id.errorText);
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
                    errorTextView.setText("You did not enter a tip");
                    System.out.println("Error msg set");
                } else {
                    tipPercent = (float) Integer.parseInt(tipPercentText) * (float) 0.01;
                }
                break;
            case -1:
                errorTextView.setText("You provided an invalid name/number.");
                System.out.println("Error msg set");
                break;
        }
    }

    public void submitManual(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        people.clear();
        boolean invalid_input = false;
        for (int i = 0; i < personViews.size(); i++) {
            System.out.println("adding person");
            if (invalid_input) {
                break;
            }
            PersonView currentPersonView = personViews.get(i);
            EditText personField = (EditText) findViewById(currentPersonView.getPersonID());
            System.out.println("currentpersonID: " + currentPersonView.getPersonID());
            String personName = personField.getText().toString();
            if (personName.isEmpty()) {
                invalid_input = true;
                break;
            }
            System.out.println("new person: " + personName);
            Person newPerson = new Person(currentPersonView.getPersonID(), personName);
            ArrayList<Integer> itemIDs = currentPersonView.getItemIDs();
            for (int j = 0; j < itemIDs.size(); j++) {
                System.out.println("adding item");
                System.out.println("itemID: " + itemIDs.get(j));
                EditText itemField = (EditText) findViewById(itemIDs.get(j));
                if (itemField.getText().toString().isEmpty()) {
                    invalid_input = true;
                    break;
                }
                float itemPrice = Float.parseFloat(itemField.getText().toString());
                newPerson.addPersonItem(itemPrice);
            }
            people.add(newPerson);
        }
        TextView errorTextView = (TextView) findViewById(R.id.errorText);
        System.out.println("error set");
        EditText taxView = (EditText) findViewById(R.id.taxInput);
        if (taxView.getText().toString().isEmpty()) {
            invalid_input = true;
            errorTextView.setText("Please input a tax percentage.");
        }
        else {
            taxPercent = Float.parseFloat(taxView.getText().toString());
            System.out.println("tax is : " + taxPercent);
            taxPercent /= 100;
            System.out.println("got tax");
        }
        setTip();
        System.out.println("got tip");
        if (invalid_input && errorTextView.getText().toString().isEmpty()) {
            errorTextView.setText("You provided an invalid name/price.");
            System.out.println("bad input");
        }
        else if (!invalid_input && !errorTextView.getText().toString().isEmpty()){
            errorTextView.setText("");
            System.out.println("going to calculate");
            calculate();
        }
    }

    public void calculate() {
        System.out.println("calculating");
        float subTotal = 0;
        for (int i = 0; i < people.size(); i++) {
            System.out.println("getting subtotal");
            Person currPerson = people.get(i);
            for (int j = 0; j < currPerson.getItems().size(); j++) {
                System.out.println("adding subtotal");
                subTotal += currPerson.getItems().get(j).getPrice();
            }
            currPerson.calculateSubTotal();
            System.out.println("currPerson subtotal: " + currPerson.getPersonalSubTotal());
        }
        for (int i = 0; i < people.size(); i++) {
            System.out.println("getting proportions");
            Person currPerson = people.get(i);
            currPerson.setProportion(currPerson.getPersonalSubTotal() / subTotal);
            System.out.println("currPerson proportion: " + currPerson.getProportion());
        }
        float finalTotal = ((subTotal * (1 + taxPercent)) * (1 + tipPercent));
        System.out.println("tip: " + tipPercent + " tax: " + taxPercent + " finalTotal: " + finalTotal);
        for (int i = 0; i < people.size(); i++) {
            System.out.println("getting final totals");
            Person currPerson = people.get(i);
            currPerson.setPersonalTotal(currPerson.getProportion() * finalTotal);
            System.out.println("currPerson total: " + currPerson.getPersonalTotal());
        }
        System.out.println("finished calc");
    }
}
