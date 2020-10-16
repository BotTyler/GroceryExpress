package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddList extends AppCompatActivity {

    Button addListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        addListButton = (Button)findViewById(R.id.addListButton);

        Toast toast = Toast.makeText(this,"Creates a new list", Toast.LENGTH_SHORT);

        addListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast.show();
                finish();
            }
        });




    }
}