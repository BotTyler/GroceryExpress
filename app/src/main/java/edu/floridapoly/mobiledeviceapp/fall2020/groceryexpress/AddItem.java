package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AddItem extends AppCompatActivity {

    Button addItemButton;
    TextView htmlText;
    Button htmlBtn;

    public class websiteHandler extends AsyncTask<Void, Void, Void>{
        String words;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                String url = "https://shopping.google.com/search?q=eggs";
                Document d = Jsoup.connect(url).get();
                words = d.html();
            }catch (Exception e){
                Log.d("expresserror", e.getMessage());
                Log.d("expresserror", "Error has occurred");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            htmlText.setText(words);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        addItemButton = (Button)findViewById(R.id.addItemButton);
        EditText itemName = findViewById(R.id.editTextTextPersonName);
        EditText itemPrice = findViewById(R.id.editTextItemPrice);
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
                Log.d("DB", itemPrice.getText().toString());

                double price = itemPrice.getText().toString().equals("") ? 0.0:  Double.parseDouble(itemPrice.getText().toString());
                MainActivity.myDB.itemDao().insertItem(new ItemEntity(listID, itemName.getText().toString(), price));
                setResult(RESULT_OK);
                finish();
            }
        });





        htmlText = findViewById(R.id.htmlplaceholder);
        htmlBtn = findViewById(R.id.testHTMLBtn);

        htmlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new websiteHandler().execute();
            }
        });
    }
}