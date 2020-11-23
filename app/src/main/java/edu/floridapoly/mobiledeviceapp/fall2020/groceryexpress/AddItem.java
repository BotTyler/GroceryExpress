package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddItem extends AppCompatActivity {

    private Button addItemButton;
    private Button findLocationForItems;
    private int listID;
    private ListView listView;
    private int preSelectedIndex = -1;
    private List<WebObject> list;
    private EditText itemName, itemMinPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemButton = (Button)findViewById(R.id.addItemButton);
        findLocationForItems = (Button)findViewById(R.id.LocateItemButton);
        itemName = findViewById(R.id.editTextTextPersonName);
        listView = findViewById(R.id.addItemToListListView);
        itemMinPrice = findViewById(R.id.AddItemMinPrice);
        preSelectedIndex = -1;


        Intent intent = getIntent();

        this.listID = intent.getIntExtra(ListActivity.ITEM_ID, -1);
        if(listID == -1){
            // list is not established
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "ERROR: Cannot Find Current List", Toast.LENGTH_SHORT).show();
            finish();
        }

        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (itemName.getText().toString().isEmpty()) {
                    Toast.makeText(AddItem.this, "PLEASE ENTER A VALID ITEM NAME", Toast.LENGTH_SHORT).show();

                } else {

                    ArrayList<WebObject> adapter = new ArrayList<WebObject>();

                    if (preSelectedIndex == -1) {
                        double minPrice = itemMinPrice.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(itemMinPrice.getText().toString());
                        Product htmlLink = new Product(AddItem.this, new ItemEntity(listID, itemName.getText().toString()), minPrice);
                        htmlLink.execute(1, 1);
                    } else {

                        ItemEntity item = new ItemEntity(listID, itemName.getText().toString(), list.get(preSelectedIndex).getPrice(), list.get(preSelectedIndex).getLocation(), list.get(preSelectedIndex).getUrl());
                        item.setWebName(list.get(preSelectedIndex).getName());
                        item.setAltPrice(list.get(preSelectedIndex).getAltPrice());
                        MainActivity.myDB.itemDao().insertItem(item);
                        setResult(RESULT_OK);

                        finish();
                    }

                    for (WebObject a : adapter)
                        Log.d("output2", a.toString());
                    //
                    setResult(RESULT_OK);
                    //finish();
                }
            }
        });


        findLocationForItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!itemName.getText().toString().isEmpty()){


                preSelectedIndex = -1;
                double minPrice = itemMinPrice.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(itemMinPrice.getText().toString());
                Product htmlLink = new Product(AddItem.this,itemName.getText().toString(), listView, minPrice);
                htmlLink.execute(20, 1);
                //Log.d("Outp", htmlLink.getWebList().toString());

                //String temp = htmlLink.getHTML("https://shopping.google.com/search?q=eggs");
                //htmlText.setText(temp);
                /*new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String word = "";
                        try {
                            Document doc = Jsoup.connect("https://shopping.google.com/search?q=eggs").timeout(6000).get();
                            word = doc.html();
                        }catch (Exception e){}
                        String finalWord = word;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                htmlText.setText(finalWord);
                            }
                        });
                    }
                });
                */
                }else{
                    Toast.makeText(AddItem.this, "PLEASE ENTER A VALID ITEM NAME", Toast.LENGTH_SHORT).show();

                }

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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