package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemButton = (Button)findViewById(R.id.addItemButton);
        EditText itemName = findViewById(R.id.editTextTextPersonName);
        Intent intent = getIntent();

        int listID = intent.getIntExtra(ListActivity.ITEM_ID, -1);
        if(listID == -1){
            // list is not established
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "ERROR: Cannot Find Current List", Toast.LENGTH_SHORT).show();
            finish();
        }

        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.myDB.itemDao().insertItem(new ItemEntity(listID, itemName.getText().toString()));
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}