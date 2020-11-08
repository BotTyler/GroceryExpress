package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public static final String gListNameID = "GROCERY_LIST_NAME";
    public static final String gListID = "GROCERY_LIST_ID";
    private Button addList;
    private static ListView groceryListView;
    public static MyDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groceryListView = (ListView)findViewById(R.id.GroceryListView);


        myDB = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "listdb").allowMainThreadQueries().build();
        List<ListEntity> listEntities = myDB.listDao().getAllList();

        ArrayAdapter<ListEntity> lists = new ArrayAdapter<ListEntity>(this, android.R.layout.simple_list_item_1, listEntities);
 /* examples of using the database


       List<ListEntity> rList = myDB.listDao().getAllList();

        Log.d("DatabaseEntry","List table");
        for(ListEntity a:rList){
            Log.d("DatabaseEntry", a.toString());
        }

        for(ListEntity a:rList)
            myDB.listDao().delete(a);

        Log.d("DatabaseEntry","delete list table");
        for(ListEntity a:rList){
            Log.d("DatabaseEntry", a.toString());
        }

        List<ItemEntity> itemList = myDB.itemDao().getAllItems();

        Log.d("DatabaseEntry","item table");
        for(ItemEntity a:itemList){
            Log.d("DatabaseEntry", a.toString());
        }

        for(ItemEntity a:itemList)
            myDB.itemDao().delete(a);

        Log.d("DatabaseEntry","delete item table");
        for(ItemEntity a:itemList){
            Log.d("DatabaseEntry", a.toString());
        }*/




        groceryListView.setAdapter(lists);
        boolean isHoldingList = false;

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // open list
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra(gListID, ((ListEntity)adapterView.getItemAtPosition(i)).getId());
                intent.putExtra(gListNameID, ((ListEntity)adapterView.getItemAtPosition(i)).getListName());

                startActivity(intent);
            }
        };

        AdapterView.OnItemLongClickListener listHoldListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("DB", "holding  " + ((ListEntity)adapterView.getItemAtPosition(i)).getListName());
                return false;
            }
        };
        groceryListView.setOnItemClickListener(itemClickListener);
        groceryListView.setOnItemLongClickListener(listHoldListener);

        addList = (Button)findViewById(R.id.addListButton);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add list to the current lists
                Intent intent = new Intent(MainActivity.this, AddList.class);
                startActivityForResult(intent, 1);
            }
        });


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        //dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            List<ListEntity> listEntities = myDB.listDao().getAllList();

            ArrayAdapter<ListEntity> lists = new ArrayAdapter<ListEntity>(this, android.R.layout.simple_list_item_1, listEntities);
            groceryListView.setAdapter(lists);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}