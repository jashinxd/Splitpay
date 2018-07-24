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

public class ItemView {

    private View view;
    private ManualEnter ME;
    private int topConstraint;
    private int bottomConstraint;
    private int itemID;

    public ItemView(View view, ManualEnter ME, int topConstraint, int bottomConstraint) {
        this.view = view;
        this.ME = ME;
        this.topConstraint = topConstraint;
        this.bottomConstraint = bottomConstraint;
        this.itemID = View.generateViewId();
    }

    public int getItemID() {
        return this.itemID;
    }

    public void drawItem() {
        EditText newItem = new EditText(ME);
        newItem.setId(itemID);

        Resources r = ME.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());

        newItem.setWidth(px);
        newItem.setInputType(8194);

        ConstraintLayout mainLayout = ME.findViewById(R.id.layout1);
        mainLayout.addView(newItem);
        //ME.setContentView(mainLayout);

        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(itemID, ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(itemID, ConstraintSet.WRAP_CONTENT);
        set.connect(itemID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(itemID, ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
        set.connect(itemID, ConstraintSet.TOP,
                topConstraint, ConstraintSet.BOTTOM, 0);
        //set.connect(itemID, ConstraintSet.BOTTOM,
        //        bottomConstraint, ConstraintSet.TOP, 50);
        set.setHorizontalBias(itemID, (float) 0.337);
        //set.setVerticalBias(itemID, (float) 0.063);
        set.applyTo(mainLayout);

        set.clone(mainLayout);
        set.connect(bottomConstraint, ConstraintSet.TOP,
                itemID, ConstraintSet.BOTTOM, 100);
        set.applyTo(mainLayout);
    }
}
