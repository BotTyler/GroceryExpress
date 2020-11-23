package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.EGL14;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddList extends AppCompatActivity {

    Button addListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        addListButton = (Button)findViewById(R.id.addListButton);
        EditText name = findViewById(R.id.AddListNameEditText);


        addListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()){
                    Toast.makeText(AddList.this, "PLEASE ENTER A VALID LIST NAME", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.myDB.listDao().insertList(new ListEntity(name.getText().toString()));
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });




    }
}