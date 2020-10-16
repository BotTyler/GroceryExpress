package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NearbyStores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_stores);
        ListView store = findViewById(R.id.StoreListView);
        ListView price = findViewById(R.id.PriceListView);
        ListView location = findViewById(R.id.LocationListView);
        ListView priceDiff = findViewById(R.id.PriceDiffListView);

        store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });
        price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });
        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });
        priceDiff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });

    }


}