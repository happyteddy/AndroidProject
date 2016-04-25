package com.example.angelina_wu.sqldatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import static android.provider.BaseColumns._ID;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "information";
    public static final String USER_NAME = "name";
    private static final int VERSION = 1;
    private final static String DATABASE_NAME = "info.db";

    //constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context, String name) {
        this(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  USER_NAME + " CHAR);" ;
        db.execSQL(INIT_TABLE);
    }

    public int getVersion() {
        return VERSION;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);*/

        if (newVersion == 2 ) {
            db.execSQL("ALTER TABLE information ADD COLUMN sex CHAR  ");
        }
        else if (newVersion == 3 ) {
            db.execSQL("ALTER TABLE information ADD COLUMN student BOOLEAN DEFAULT flase");
        }
        else{

        }
    }
}
