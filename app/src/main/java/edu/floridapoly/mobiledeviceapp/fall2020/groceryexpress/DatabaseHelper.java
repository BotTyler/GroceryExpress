package edu.floridapoly.mobiledeviceapp.fall2020.groceryexpress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "list.db";
    public static final String TABLE_NAME1 = "list", TABLE_NAME2 = "Items";
    public static final String CREATE_TABLE1 =
            "CREATE TABLE " + TABLE_NAME1 +" ("+TABLE_NAME1+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_NAME1+"_Name Text);";
    public static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 +
                    "("+TABLE_NAME2+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_NAME2+"_Name Text, "+TABLE_NAME2+"_Address Text, "+TABLE_NAME1+"_id INTEGER NOT NULL, FOREIGN KEY("+TABLE_NAME1+"_id) REFERENCES " +TABLE_NAME1+"(List_id))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db); // Create tables again
    }

}
