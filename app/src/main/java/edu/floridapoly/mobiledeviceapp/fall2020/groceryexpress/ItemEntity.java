
package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.annotation.NonNull;
import androidx.room.*;


import java.util.List;

@Entity(tableName = "Items")
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int Item_id;

    @ColumnInfo(name = "List_id")
    @ForeignKey(entity = ListEntity.class, parentColumns = "List_id", childColumns = "List_id")
    @NonNull
    private int List_id;

    @ColumnInfo(name = "Item_name")
    private String name;

    @ColumnInfo(name = "Item_Web_Name")
    private String webName;

    @ColumnInfo(name = "Item_location")
    private String location;

    @ColumnInfo(name = "Item_price")
    private double price;

    @ColumnInfo(name = "Item_Alt_Price")
    private String altPrice;

    @ColumnInfo(name = "Item_URL")
    private String url;



    public int getItem_id() {
        return Item_id;
    }
    public void setItem_id(int item_id) {
        Item_id = item_id;
    }
    public int getList_id() {
        return List_id;
    }
    public void setList_id(int list_id) {
        List_id = list_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getWebName() {
        return webName;
    }
    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getAltPrice() {
        return altPrice;
    }

    public void setAltPrice(String altPrice) {
        this.altPrice = altPrice;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ItemEntity(){
        // do not use this constructor you need to supply a listid
    }
    public ItemEntity(int list_id, String name){
        this.name = name;
        this.List_id = list_id;
    }
    public ItemEntity(int list_id, String name, double price){
        this.name = name;
        this.price = price;
        this.List_id = list_id;

    }
    public ItemEntity(int list_id, String name, String location){
        this.name = name;
        this.location = location;
        this.List_id = list_id;

    }
    public ItemEntity(int list_id, String name, double price, String location){
        this.name = name;
        this.price = price;
        this.location = location;
        this.List_id = list_id;

    }
    public ItemEntity(int list_id, String name, double price, String location, String url){
        this.name = name;
        this.price = price;
        this.location = location;
        this.List_id = list_id;
        this.url = url;
    }
}

@Dao
interface ItemDao{
    @Query("SELECT * FROM Items where List_id = :ListId ORDER BY Item_name ASC")
    List<ItemEntity> getAllItemsFromListID(int ListId);

    @Query("SELECT * FROM Items")
    List<ItemEntity> getAllItems();

    @Insert
    void insertAll(ItemEntity... items);
    
    @Insert
    void insertItem(ItemEntity item);

    @Delete
    void delete(ItemEntity... itemEntity);

    @Delete
    void delete(ItemEntity itemEntity);

    @Query("DELETE FROM Items WHERE List_id=:ListId") // careful this will delete everything in the list
    void deleteAll(int ListId);

    @Query("Delete FROM items")
    void DeleteAll();

    @Query("SELECT SUM(Item_price) FROM Items WHERE List_id=:ListId")
    double sumOfPrice(int ListId);

    @Update
    void update(ItemEntity itemEntity);
}

