package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String gListNameID = "GROCERY_LIST_NAME";
    private Button addList;
    private static List[] lists = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView groceryListView = (ListView)findViewById(R.id.GroceryListView);
        ArrayAdapter<List> lists = new ArrayAdapter<List>(this, android.R.layout.simple_list_item_1, this.lists);

        groceryListView.setAdapter(lists);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra(gListNameID, (String)adapterView.getItemAtPosition(i));

                startActivity(intent);
            }
        };
        groceryListView.setOnItemClickListener(itemClickListener);

        addList = (Button)findViewById(R.id.addListButton);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddList.class);

                startActivity(intent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,12);
    }
}