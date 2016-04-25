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
    private DBHelper mHelper = null;
    TextView mShowDataTextView = null;
    int mVersion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DBHelper(this);
        mHelper.close();
        mShowDataTextView = (TextView) findViewById(R.id.showData);
        //add line
    }

    public void add(View view) {
        EditText userName = (EditText) findViewById(R.id.enterUserName);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName.getText().toString());
        db.insert(TABLE_NAME, null, values);
    }

    public void show(View view) {

        SQLiteDatabase db = mHelper.getReadableDatabase();
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
        mShowDataTextView.setText(resultData);
    }
    public void upgrade(View view) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        mVersion = mVersion + 1 ;
        mHelper.onUpgrade(db, mHelper.getVersion(), mVersion);
    }
}
