package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.*;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public static final String gListNameID = "GROCERY_LIST_NAME";
    public static final String gListID = "GROCERY_LIST_ID";
    private Button addList;
    private static SwipeMenuListView groceryListView;
    public static MyDatabase myDB;
    public Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "listdb").allowMainThreadQueries().build();
        groceryListView = findViewById(R.id.GroceryListView);
        myDialog = new Dialog(this);



        // creating menu for the swiping motions
        SwipeMenuCreator creator = new SwipeMenuCreator(){

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);


                SwipeMenuItem rename = new SwipeMenuItem(getApplicationContext());
                rename.setBackground(new ColorDrawable(Color.rgb(22,191,0)));
                rename.setIcon(R.drawable.ic_rename);
                rename.setWidth(170);

                menu.addMenuItem(rename);

            }
        };
        groceryListView.setMenuCreator(creator);


        updateList();
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
        groceryListView.setOnItemClickListener(itemClickListener);

        groceryListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        // delete button was pressed Prob should prompt for confirmation
                        ListEntity list = (ListEntity)groceryListView.getItemAtPosition(position);
                        myDB.itemDao().deleteAll(list.getId());
                        myDB.listDao().delete(list);
                        updateList();
                        break;
                    case 1:
                        // rename button

                        ShowPopup((ListEntity)groceryListView.getItemAtPosition(position));
                        /*PopupMenu popup = new PopupMenu(MainActivity.this,getViewByPosition(position, groceryListView));
                        popup.setOnMenuItemClickListener(MainActivity.this::onMenuItemClick);
                        popup.inflate(R.menu.main_activity_menu_options);
                        popup.show();
                        Log.d("DB", "More Info Clicked position="+position);*/
                        break;
                }



                return false;
            }
        });



        addList = (Button)findViewById(R.id.addListButton);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add list to the current lists
                Intent intent = new Intent(MainActivity.this, AddList.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            updateList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateList(){
        List<ListEntity> listEntities = myDB.listDao().getAllList();
        ArrayAdapter<ListEntity> lists = new ArrayAdapter<ListEntity>(this, android.R.layout.simple_list_item_1, listEntities);
        groceryListView.setAdapter(lists);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.renameItem:
                Log.d("DB", "this works");
                break;
        }
        return false;
    }

    public void ShowPopup(ListEntity listEntity) {
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

                listEntity.setListName(et.getText().toString());
                myDB.listDao().update(listEntity);
                updateList();
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}