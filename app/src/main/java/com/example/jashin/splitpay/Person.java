package com.example.jashin.splitpay;

import java.util.ArrayList;

public class Person {

    private int id;
    private String name;
    private float personalSubTotal;
    private float personalTotal;
    private float proportion;
    private ArrayList<Item> items;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        personalSubTotal = personalTotal = proportion = 0;
        this.items = new ArrayList<>(0);
    }

    public void addPersonItem(float price) {
        Item newItem = new Item(price);
        items.add(newItem);
    }

    public ArrayList<Item> getItems() { return items; }

    public float getPersonalSubTotal() { return personalSubTotal; }

    public float getPersonalTotal() { return personalTotal; }

    public float getProportion() { return proportion; }

    public void setPersonalSubTotal(float subTotal) {
        this.personalSubTotal = subTotal;
    }

    public void setPersonalTotal(float total) {
        this.personalTotal = total;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public void calculateSubTotal() {
        for (int i = 0; i < items.size(); i++) {
            personalSubTotal += items.get(i).getPrice();
        }
    }

    public class Item {

        private float price;

        public Item(float price) {
            this.price = price;
        }

        public float getPrice() { return price; }
    }

}
