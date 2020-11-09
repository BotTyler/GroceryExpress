package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress.MainActivity.gListID;
import static edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress.MainActivity.gListNameID;

public class ListActivity extends AppCompatActivity {

    private Button addItem;
    public static final String ITEM_ID = "ITEM_ID";
    private SwipeMenuListView itemsView;
    private int listId;
    public TextView totalPrice;
    public Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        myDialog = new Dialog(this);

        Intent intent = getIntent();
        listId = intent.getIntExtra(gListID, -1);
        String listName = intent.getStringExtra(gListNameID);
        if(listId == -1 || listName == null){
            finish();
        }

        totalPrice = findViewById(R.id.TotalListPrice);
        TextView listNameTextView = findViewById(R.id.ListTitle);
        itemsView = (SwipeMenuListView)findViewById(R.id.ItemListView);
        listNameTextView.setText(listName);

        // creating menu for the swiping motions
        SwipeMenuCreator creator = new SwipeMenuCreator(){

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem location = new SwipeMenuItem(getApplicationContext());
                location.setBackground(new ColorDrawable(Color.rgb(59,131,247)));
                location.setIcon(R.drawable.ic_location);
                location.setWidth(170);
                menu.addMenuItem(location);


                SwipeMenuItem rename = new SwipeMenuItem(getApplicationContext());
                rename.setBackground(new ColorDrawable(Color.rgb(22,191,0)));
                rename.setIcon(R.drawable.ic_rename);
                rename.setWidth(170);
                menu.addMenuItem(rename);

                /*SwipeMenuItem moreInfo = new SwipeMenuItem(getApplicationContext());
                moreInfo.setBackground(new ColorDrawable(Color.rgb(195, 199, 196)));
                moreInfo.setIcon(R.drawable.ic_info);
                moreInfo.setWidth(170);
                menu.addMenuItem(moreInfo);*/

            }
        };
        itemsView.setMenuCreator(creator);
        itemsView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        // delete button was pressed Prob should prompt for confirmation
                        ItemEntity item = (ItemEntity)itemsView.getItemAtPosition(position);
                        MainActivity.myDB.itemDao().delete(item);
                     /*   Snackbar.make(findViewById(R.id.MainActivityLayout), "Deleted list '" +item.getName() + "'", Snackbar.LENGTH_LONG).setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // do nothing yet maybe add undo button
                            }
                        }).setActionTextColor(getResources().getColor((R.color.white))).show();*/
                        updateItems();
                        break;
                    case 1:
                        // i dont know what to do here but it here we can delete this part if we want to
                      //  Log.d("DB", "location Clicked position="+position);
                        break;
                    case 2:
                       // Log.d("DB", "rename");
                        ShowPopup((ItemEntity)itemsView.getItemAtPosition(position));

                        break;
                }



                return false;
            }
        });

        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, NearbyStores.class);


                startActivity(intent);
            }
        });

        updateItems();

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

                updateItems();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateItems(){
        List<ItemEntity> itemEntities = MainActivity.myDB.itemDao().getAllItemsFromListID(listId);
        double price = MainActivity.myDB.itemDao().sumOfPrice(listId);
       // ArrayAdapter<ItemEntity> items = new ArrayAdapter<ItemEntity>(this, android.R.layout.simple_list_item_1, itemEntities);
        MyAdapter items = new MyAdapter(this, itemEntities);
        totalPrice.setText("$"+price);
        itemsView.setAdapter(items);
    }
    public void ShowPopup(ItemEntity itemEntity) {
        TextView txtclose;
        Button btnRename;
        myDialog.setContentView(R.layout.rename_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        btnRename = (Button) myDialog.findViewById(R.id.btnRename);
        EditText et = myDialog.findViewById(R.id.renameEditText);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemEntity.setName(et.getText().toString());
                MainActivity.myDB.itemDao().update(itemEntity);
                updateItems();
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}