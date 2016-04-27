package com.example.angelina_wu.sqldatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "information";
    public static final String USER_NAME = "name";
    public static final String USER_SCORE = "score";
    private static final int VERSION = 1;
    private final static String DATABASE_NAME = "info.db";

    //constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  USER_NAME + " CHAR," + USER_SCORE + " INT );" ;
        db.execSQL(INIT_TABLE);
    }

    public int getVersion() {
        return VERSION;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion){

            case 2 :
                db.execSQL("ALTER TABLE information ADD COLUMN sex CHAR  ");
                break;
            case 3 :
                db.execSQL("ALTER TABLE information ADD COLUMN student BOOLEAN DEFAULT flase");
                break;
            default:
                break;
        }
    }
}
