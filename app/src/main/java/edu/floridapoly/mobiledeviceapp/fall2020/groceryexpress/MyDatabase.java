package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.room.*;

@Database(entities = {ItemEntity.class,ListEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase{

    public abstract ItemDao itemDao();
    public abstract ListDao listDao();
}
