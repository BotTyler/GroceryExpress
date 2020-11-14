package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.graphics.Region;

import androidx.room.*;

import java.util.List;

@Entity(tableName = "list")
public class ListEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "List_id")
    private int id;

    @ColumnInfo(name="List_fav")
    private int favorite;

    @ColumnInfo(name = "list_Name")
    private String listName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public ListEntity(){}

    public ListEntity(String name){
        this.listName = name;
    }

    @Override
    public String toString() {
        return listName;
    }
}
@Dao
interface ListDao {

    @Query("SELECT * FROM list ORDER BY List_fav DESC")
    List<ListEntity> getAllList();

    @Query("DELETE FROM list") // careful this will delete everything in a table
    void DeleteAll();

    @Insert
    void insertAll(ListEntity... list);

    @Insert
    void insertList(ListEntity list);

    @Delete
    void delete(ListEntity... listEntities);

    @Delete
    void delete(ListEntity listEntity);

    @Update
    void update(ListEntity listEntity);


}