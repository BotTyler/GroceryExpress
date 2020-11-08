package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Button addItem;
    public static final String ITEM_ID = "ITEM_ID";
    private ListView itemsView;
    private int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();


        listId = intent.getIntExtra(MainActivity.gListID, -1);
        String listName = intent.getStringExtra(MainActivity.gListNameID);
        if(listId == -1 || listName == null){
            finish();
        }


        TextView listNameTextView = findViewById(R.id.ListTitle);
        itemsView = findViewById(R.id.ItemListView);

        listNameTextView.setText(listName);

        List<ItemEntity> itemsEntities = MainActivity.myDB.itemDao().getAllItemsFromListID(listId);

        ArrayAdapter<ItemEntity> lists = new ArrayAdapter<ItemEntity>(this, android.R.layout.simple_list_item_1, itemsEntities);
        itemsView.setAdapter(lists);
        // title needs  to be changed as well as check the id of the item change it in the other to make sure

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
                intent.putExtra(ITEM_ID, listId);
                startActivityForResult(intent, 1);
            }
        });

    }

    public void BackOnItemClick(View view) {

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            List<ItemEntity> itemEntities = MainActivity.myDB.itemDao().getAllItemsFromListID(listId);

            ArrayAdapter<ItemEntity> items = new ArrayAdapter<ItemEntity>(this, android.R.layout.simple_list_item_1, itemEntities);
            itemsView.setAdapter(items);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}