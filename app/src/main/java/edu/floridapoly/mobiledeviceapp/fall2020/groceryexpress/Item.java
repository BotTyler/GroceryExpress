package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

public class Item {
    private String name, location;
    private double price;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String fString ="";
        fString += String.format("%-50s",this.name);
        fString += price != 0.0f ? String.format("%-15s", "$"+price) : String.format("%-15s","");
        fString += location != null? String.format("%-50s", location) : String.format("%-50s","");
        return fString;
    }

    public Item(String name){
        this.name = name;
    }
    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }
    public Item(String name, String location){
        this.name = name;
        this.location = location;
    }
    public Item(String name, double price, String location){
        this.name = name;
        this.price = price;
        this.location = location;
    }
}
