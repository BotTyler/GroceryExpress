package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String gListNameID = "GROCERY_LIST_NAME";
    private Button addList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView groceryListView = (ListView)findViewById(R.id.GroceryListView);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra(gListNameID, (String)adapterView.getItemAtPosition(i));
                Toast toast = Toast.makeText(getApplicationContext(),"Opening " + (String)adapterView.getItemAtPosition(i) + " List", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
            }
        };
        groceryListView.setOnItemClickListener(itemClickListener);

        addList = (Button)findViewById(R.id.addListButton);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddList.class);
                Toast toast = Toast.makeText(getApplicationContext(),"Starting AddList Activity", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
            }
        });
    }
}