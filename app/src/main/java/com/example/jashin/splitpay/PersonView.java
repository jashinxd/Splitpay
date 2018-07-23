package com.example.jashin.splitpay;

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class PersonView {

    private ArrayList<Integer> itemIDs;
    private View view;
    private ManualEnter ME;
    private int topConstraint;
    private int bottomConstraint;
    private int personID;

    public PersonView(View view, ManualEnter ME, int topConstraint, int bottomConstraint) {
        this.itemIDs = new ArrayList<>(0);
        this.view = view;
        this.ME = ME;
        this.topConstraint = topConstraint;
        this.bottomConstraint = bottomConstraint;
        personID = View.generateViewId();
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

    public void addItem() {
        ItemView newItem;
        if (itemIDs.isEmpty()) {
            System.out.println("empty items");
            newItem = new ItemView(view, ME, personID, bottomConstraint);
        } else {
            int prevItemID = itemIDs.get(itemIDs.size() - 1);
            newItem = new ItemView(view, ME, prevItemID, bottomConstraint);
        }
        int newItemID = newItem.getItemID();
        itemIDs.add(newItemID);
        newItem.drawItem();
        EditText newItemView = ME.findViewById(newItemID);
        newItemView.setHint("Item " + itemIDs.size());

    }

    public ArrayList<Integer> getItemIDs() {
        return itemIDs;
    }

    public int getPersonID() {
        return personID;
    }
}
