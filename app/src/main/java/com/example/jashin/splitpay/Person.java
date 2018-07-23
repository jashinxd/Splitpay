package com.example.jashin.splitpay;

import java.util.ArrayList;

public class Person {

    private int id;
    private String name;
    private ArrayList<Item> items;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        this.items = new ArrayList<Item>(0);
    }

    public void addPersonItem(float price) {
        Item newItem = new Item(price);
        items.add(newItem);
    }

    public class Item {

        private float price;

        public Item(float price) {
            this.price = price;
        }

        public float getPrice() { return price; }
    }

}
