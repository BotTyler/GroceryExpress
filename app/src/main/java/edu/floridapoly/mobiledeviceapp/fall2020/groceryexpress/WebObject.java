package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

public class WebObject {
    private String name;
    private double price;
    private String altPrice;
    private String location;
    private String url;
    private boolean isSelected = false;



    public WebObject(){
        // default
    }
    public WebObject(String name, double price, String altPrice, String location){
        this.name = name;
        this.price = price;
        this.location = location;
        this.altPrice = altPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAltPrice() {
        return altPrice;
    }

    public void setAltPrice(String altPrice) {
        this.altPrice = altPrice;
    }

    @Override
    public String toString() {
        return "WebObject{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
