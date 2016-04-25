package com.example.angelina_wu.sqldatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.BaseColumns._ID;
import static com.example.angelina_wu.sqldatabase.DBHelper.TABLE_NAME;
import static com.example.angelina_wu.sqldatabase.DBHelper.USER_NAME;

public class MainActivity extends AppCompatActivity {
    private DBHelper helper = null;
    TextView result = null;
    int version = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);
        helper.close();
        result = (TextView) findViewById(R.id.showData);
    }

    public void add(View view) {
        EditText userName = (EditText) findViewById(R.id.enterUserName);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName.getText().toString());
        db.insert(TABLE_NAME, null, values);
    }

    public void show(View view) {

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = { _ID , USER_NAME  };
        Cursor c = db.query( TABLE_NAME, projection, null, null, null, null, null );

        StringBuilder resultData = new StringBuilder("RESULT: \n");
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            resultData.append(id).append(": ");
            resultData.append(name).append(", ");
            resultData.append("\n");
        }
        result.setText(resultData);
    }
    public void upgrade(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        version = version + 1 ;
        helper.onUpgrade( db, helper.getVersion(), version);
    }
}
