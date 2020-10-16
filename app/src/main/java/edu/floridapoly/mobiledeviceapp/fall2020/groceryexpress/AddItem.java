package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemButton = (Button)findViewById(R.id.addItemButton);

        Toast toast = Toast.makeText(this,"Adds item to list", Toast.LENGTH_SHORT);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast.show();
                finish();
            }
        });
    }
}