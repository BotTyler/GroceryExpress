package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.widget.ArrayAdapter;

public class List {

    private Item[] items = {};

    private String ListName;


    public List(String listName){
            this.ListName = listName;
    }

    public Item[] getItems(){
        return items;
    }

}
