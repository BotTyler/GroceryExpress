package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NearbyStores extends AppCompatActivity {
    private TextView ItemTitle, ItemWebTitle, ItemLocation, ItemPrice;
    private ListView webObjectListView;
    private EditText minPriceText;
    private Button locateItemBtn, changeLocationBtn;
    private int preSelectedIndex = -1;
    private List<WebObject> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_stores);
        int ItemID = getIntent().getIntExtra("ITEM_ID", -1);
        if(ItemID == -1){
            Toast.makeText(this, "CANNOT FIND ITEM", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }

        ItemEntity item = MainActivity.myDB.itemDao().getItem(ItemID);

        ItemTitle = findViewById(R.id.LocationItemTitle);
        ItemWebTitle = findViewById(R.id.LocationItemWebTitle);
        //currentItemListView = findViewById(R.id.LocationCurrentItemList);
        ItemLocation = findViewById(R.id.LocationCurrentLocation);
        ItemPrice = findViewById(R.id.LocationItemPrice);
        webObjectListView = findViewById(R.id.LocationWebObjectListView);
        minPriceText = findViewById(R.id.LocationMinPrice);
        locateItemBtn = findViewById(R.id.LocationLocateItemBtn);
        changeLocationBtn = findViewById(R.id.LocationChangeBtn);

        ItemTitle.setText(item.getName());
        ItemWebTitle.setText("(" + item.getWebName()+")");
        ItemLocation.setText(item.getLocation());
        ItemPrice.setText("$" +item.getPrice());
        changeLocationBtn.setVisibility(View.INVISIBLE);

        locateItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    preSelectedIndex = -1;
                    double minPrice = minPriceText.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(minPriceText.getText().toString());
                    Product htmlLink = new Product(NearbyStores.this,item.getName(), webObjectListView, minPrice);
                    htmlLink.execute(20, 1);
                changeLocationBtn.setVisibility(View.VISIBLE);


            }
        });


        changeLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ArrayList<WebObject> adapter = new ArrayList<WebObject>();

                    if (preSelectedIndex == -1) {
                        Toast.makeText(NearbyStores.this, "PLEASE SELECT A LOCATION FROM THE LIST ABOVE", Toast.LENGTH_SHORT).show();
                    } else {

                       item.setWebName(list.get(preSelectedIndex).getName());
                       item.setAltPrice(list.get(preSelectedIndex).getAltPrice());
                       item.setPrice(list.get(preSelectedIndex).getPrice());
                       item.setUrl(list.get(preSelectedIndex).getUrl());
                       item.setLocation(list.get(preSelectedIndex).getLocation());
                       MainActivity.myDB.itemDao().update(item);
                        //setResult(RESULT_OK);
                        setResult(RESULT_OK);
                        finish();
                    }

                    for (WebObject a : adapter)
                        Log.d("output2", a.toString());
                    //

                    //finish();

            }
        });

        webObjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WebObjectListAdapter adapter = ((WebObjectListAdapter)adapterView.getAdapter());
                list = adapter.getItems();

                if(i == preSelectedIndex){
                    list.get(i).setSelected(!list.get(i).isSelected());
                    preSelectedIndex = list.get(i).isSelected() == true ? i : -1;
                }else if(preSelectedIndex > -1 && preSelectedIndex != i){
                    list.get(preSelectedIndex).setSelected(false);
                    list.get(i).setSelected(true);
                    preSelectedIndex = i;
                }else if(preSelectedIndex == -1 && i > -1){
                    list.get(i).setSelected(true);
                    preSelectedIndex = i;
                }
               /* list.get(i).setSelected(true);

                if (preSelectedIndex > -1){
                    list.get(preSelectedIndex).setSelected(false);

                }
                preSelectedIndex = i;
                */

                adapter.updateRecords(list);
                Log.d("Select", preSelectedIndex + "");
            }
        });


    }


}