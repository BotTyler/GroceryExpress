package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {
public static final Item[] list = {
        new Item("Graphics Card"),new Item("Soccer Ball", 6.54),new Item("Football", "Walmart"),new Item("Notebooks", 555.23, "Costco"),new Item("Pens/Pencils")
};
    private Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        ListView listView = findViewById(R.id.ItemListView);

       /* View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 3));
        v.setBackgroundColor(Color.rgb(51, 51, 51));

        TextView ListTitle = (TextView)findViewById(R.id.ListTitle);
        //ArrayAdapter<Item> itemList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);


        TableRow tr = new TableRow(this);

        TableLayout itemListView = (TableLayout)findViewById(R.id.ItemLayout);

        ListTitle.setText((String)getIntent().getExtras().get(MainActivity.gListNameID));

        TextView nTitle = new TextView(this);

        nTitle.setText("Name");
        nTitle.setGravity(Gravity.LEFT);
        nTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
        nTitle.setPadding(0,0,0,40);
        tr.addView(nTitle);

        TextView pTitle = new TextView(this);
        pTitle.setText("Price");
        pTitle.setGravity(Gravity.CENTER);
        pTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
        pTitle.setPadding(0,0,0,40);

        tr.addView(pTitle);

        TextView lTitle = new TextView(this);
        lTitle.setText("Location");
        lTitle.setGravity(Gravity.RIGHT);
        lTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
        lTitle.setPadding(0,0,0,40);
        tr.addView(lTitle);

        itemListView.addView(tr);
        itemListView.addView(v);


        for(Item temp : list){

            View listLine = new View(this);
            v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
            v.setBackgroundColor(Color.rgb(51, 51, 51));

            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

            TextView name = new TextView(this);

            name.setText(temp.getName());
            name.setGravity(Gravity.LEFT);
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
            name.setPadding(0,0,0,20);

            tr.addView(name);

            TextView price = new TextView(this);
            String p = temp.getPrice() == 0 ? "":"$"+temp.getPrice();
            price.setText(p);
            price.setGravity(Gravity.CENTER);
            price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
            price.setPadding(0,0,0,20);

            tr.addView(price);

            TextView location = new TextView(this);
            String l = temp.getLocation() == null? "":temp.getLocation();
            location.setText(l);
            location.setGravity(Gravity.RIGHT);
            location.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1f));
            location.setPadding(0,0,0,20);
            tr.setClickable(true);
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      TableRow test = (TableRow)view;
                      TextView name = (TextView)test.getChildAt(0);
                      TextView price = (TextView)test.getChildAt(1);
                      TextView location = (TextView)test.getChildAt(2);

                    Log.d("PRINTNOT","Name="+name.getText().toString()+" Price="+price.getText().toString()+" Location="+location.getText().toString());
                    Intent intent = new Intent(ListActivity.this, NearbyStores.class);
                    Toast toast = Toast.makeText(getApplicationContext(),"Starting NearbyStores Activity", Toast.LENGTH_LONG);
                    toast.show();
                    startActivity(intent);
                }
            });
            tr.addView(location);


            itemListView.addView(tr);
            itemListView.addView(listLine);



        }

        */

        addItem = (Button)findViewById(R.id.addItemButton);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, AddItem.class);
                Toast toast = Toast.makeText(getApplicationContext(),"Starting AddItem Activity", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
            }
        });

    }

    public void BackOnItemClick(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),"Returning to MainActivity", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }
}