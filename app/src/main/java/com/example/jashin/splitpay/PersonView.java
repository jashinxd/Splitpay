package com.example.jashin.splitpay;

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PersonView {

    private ArrayList<Integer> itemIDs;
    private View view;
    private ManualEnter ME;
    private int topConstraint;
    private int bottomConstraint;
    private int personID;
    private int addItemID;
    private int removeItemID;
    private ConstraintLayout mainLayout;

    public PersonView(View view, ManualEnter ME, int topConstraint, int bottomConstraint) {
        this.itemIDs = new ArrayList<>(0);
        this.view = view;
        this.ME = ME;
        this.topConstraint = topConstraint;
        this.bottomConstraint = bottomConstraint;
        this.mainLayout = ME.findViewById(R.id.layout1);
        personID = View.generateViewId();
        addItemID = View.generateViewId();
        removeItemID = View.generateViewId();
    }

    public void drawNewPerson() {

        EditText newPerson = new EditText(ME);
        newPerson.setId(personID);

        Resources r = ME.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());

        newPerson.setWidth(px);
        newPerson.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

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

    public void drawPerson() {
        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);
        set.connect(bottomConstraint, ConstraintSet.TOP,
                itemIDs.get(itemIDs.size() - 1), ConstraintSet.BOTTOM);
        set.applyTo(mainLayout);
    }

    public void drawAddRemoveItemButton() {

        int lastItemID = itemIDs.get(itemIDs.size() - 1);
        if (itemIDs.size() == 1) {
            Button addItemButton = new Button(ME);
            addItemButton.setId(addItemID);
            Resources r = ME.getResources();
            addItemButton.setText("+");
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                    drawAddRemoveItemButton();
                }
            });

            Button removeItemButton = new Button(ME);
            removeItemButton.setId(removeItemID);
            removeItemButton.setText("-");
            removeItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem();
                    drawAddRemoveItemButton();
                }
            });

            mainLayout.addView(addItemButton);
            mainLayout.addView(removeItemButton);
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

        set.constrainHeight(removeItemID, 125);
        set.constrainWidth(removeItemID, 125);
        set.connect(removeItemID, ConstraintSet.LEFT,
                addItemID, ConstraintSet.RIGHT, 0);
        set.connect(removeItemID, ConstraintSet.TOP,
                lastItemID, ConstraintSet.TOP, 0);
        set.connect(removeItemID, ConstraintSet.BOTTOM,
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

            //ConstraintSet set = new ConstraintSet();
            //set.connect(bottomConstraint, ConstraintSet.TOP,
            //        newItem.getItemID(), ConstraintSet.BOTTOM);
        }
        int newItemID = newItem.getItemID();
        itemIDs.add(newItemID);
        newItem.drawItem();
        EditText newItemView = ME.findViewById(newItemID);
        newItemView.setHint("Item " + itemIDs.size());
        drawAddRemoveItemButton();
    }

    public void removeItem() {
        if (itemIDs.size() > 1) {
            int itemToRemove = itemIDs.remove(itemIDs.size() - 1);
            ViewGroup parentView = (ViewGroup) ME.findViewById(itemToRemove).getParent();
            parentView.removeView(ME.findViewById(itemToRemove));
            drawAddRemoveItemButton();

            ConstraintSet set = new ConstraintSet();
            set.clone(mainLayout);
            set.connect(bottomConstraint, ConstraintSet.TOP,
                    itemIDs.get(itemIDs.size() - 1), ConstraintSet.BOTTOM);
            set.applyTo(mainLayout);
        }
    }

    public void removeAllItems() {
        while (itemIDs.size() > 0) {
            removeItem();
        }
    }

    public ArrayList<Integer> getItemIDs() {
        return itemIDs;
    }

    public int getPersonID() {
        return personID;
    }

    public int getAddItemID() {
        return addItemID;
    }

    public int getRemoveItemID() {
        return removeItemID;
    }

    public void setBottomConstraint(int id) {
        bottomConstraint = id;
    }

    public void draw() {

    }
}
