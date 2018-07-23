package com.example.jashin.splitpay;

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PersonView {

    private ArrayList<Integer> itemIDs;
    private ArrayList<Integer> addButtonIDs;
    private View view;
    private ManualEnter ME;
    private int topConstraint;
    private int bottomConstraint;
    private int personID;
    private int addItemID;

    public PersonView(View view, ManualEnter ME, int topConstraint, int bottomConstraint) {
        this.itemIDs = new ArrayList<>(0);
        this.addButtonIDs = new ArrayList<>(0);
        this.view = view;
        this.ME = ME;
        this.topConstraint = topConstraint;
        this.bottomConstraint = bottomConstraint;
        personID = View.generateViewId();
        addItemID = View.generateViewId();
    }

    public void drawPerson() {

        EditText newPerson = new EditText(ME);
        newPerson.setId(personID);

        Resources r = ME.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());

        newPerson.setWidth(px);
        newPerson.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

        ConstraintLayout mainLayout = ME.findViewById(R.id.layout1);
        mainLayout.addView(newPerson);
        //ME.setContentView(mainLayout);

        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(personID, ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(personID, ConstraintSet.WRAP_CONTENT);
        set.connect(personID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(personID, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        if (topConstraint == ConstraintSet.PARENT_ID) {
            set.connect(personID, ConstraintSet.TOP,
                    topConstraint, ConstraintSet.TOP, 50);
        } else {
            set.connect(personID, ConstraintSet.TOP,
                    topConstraint, ConstraintSet.BOTTOM, 0);
        }
        //set.connect(personID, ConstraintSet.BOTTOM,
        //        R.id.addPerson, ConstraintSet.TOP, 0);
        set.setHorizontalBias(personID, (float) 0.052);
        //set.setVerticalBias(personID, (float) 0.063);
        set.applyTo(mainLayout);
        addItem();
        set.connect(personID, ConstraintSet.BOTTOM,
                itemIDs.get(0), ConstraintSet.TOP, 0);
        set.applyTo(mainLayout);
    }

    public void drawAddItemButton() {
        ConstraintLayout mainLayout = ME.findViewById(R.id.layout1);

        int lastItemID = itemIDs.get(itemIDs.size() - 1);
        if (itemIDs.size() == 1) {
            Button addItemButton = new Button(ME);
            addItemButton.setId(addItemID);
            addButtonIDs.add(addItemID);

            Resources r = ME.getResources();
            addItemButton.setText("+");
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                    drawAddItemButton();
                }
            });

            mainLayout.addView(addItemButton);
            //ME.setContentView(mainLayout);
        }

        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(addItemID, 125);
        set.constrainWidth(addItemID, 125);
        set.connect(addItemID, ConstraintSet.LEFT,
                lastItemID, ConstraintSet.RIGHT, 0);
        set.connect(addItemID, ConstraintSet.TOP,
                lastItemID, ConstraintSet.TOP, 0);
        set.connect(addItemID, ConstraintSet.BOTTOM,
                lastItemID, ConstraintSet.BOTTOM, 0);
        set.applyTo(mainLayout);
    }


    public void addItem() {
        ItemView newItem;
        if (itemIDs.isEmpty()) {
            System.out.println("empty items");
            newItem = new ItemView(view, ME, personID, bottomConstraint);
        } else {
            int prevItemID = itemIDs.get(itemIDs.size() - 1);
            newItem = new ItemView(view, ME, prevItemID, bottomConstraint);

            ConstraintSet set = new ConstraintSet();
            set.connect(bottomConstraint, ConstraintSet.TOP,
                    newItem.getItemID(), ConstraintSet.BOTTOM);
        }
        int newItemID = newItem.getItemID();
        itemIDs.add(newItemID);
        newItem.drawItem();
        EditText newItemView = ME.findViewById(newItemID);
        newItemView.setHint("Item " + itemIDs.size());
        drawAddItemButton();
    }

    public ArrayList<Integer> getItemIDs() {
        return itemIDs;
    }

    public int getPersonID() {
        return personID;
    }

    public void setBottomConstraint(int id) {
        bottomConstraint = id;
    }
}
