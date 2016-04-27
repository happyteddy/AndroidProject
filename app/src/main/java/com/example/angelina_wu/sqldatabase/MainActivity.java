package com.example.angelina_wu.sqldatabase;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
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

    public void add(View view) { //  Insert
        EditText userName = (EditText) findViewById(R.id.editUserName);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName.getText().toString());
        db.insert(TABLE_NAME, null, values);
        userName.setText(""); // clear editText
    }



    public void show(View view) { //  Query

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
    public void showSortByName(View view) { //  Query

        SQLiteDatabase db = mHelper.getReadableDatabase();
        String[] projection = { _ID , USER_NAME  };
        Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, USER_NAME );
        TextView sortData = (TextView)findViewById(R.id.showData_Name) ;
        StringBuilder resultData = new StringBuilder("RESULT: \n");
        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            resultData.append(id).append(": ");
            resultData.append(name).append(", ");
            resultData.append("\n");
        }
        sortData.setText(resultData);
    }

    public void delete(View view){ // Delete
        EditText deleteUserName = (EditText) findViewById(R.id.editUserName);
        String deleteName = deleteUserName.getText().toString();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String where = USER_NAME + "='" + deleteName +"'"; //  name = ' deleteName'
        db.delete(TABLE_NAME, where , null);
        deleteUserName.setText(""); // clear editText
    }

    public void update(View view) {  //  Update
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final ContentValues args = new ContentValues();

        final EditText editUserName = (EditText) findViewById(R.id.editUserName);
        String oldName = editUserName.getText().toString();

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View v = inflater.inflate(R.layout.alert_dialog, null);

        final String where = USER_NAME + "='" + oldName +"'"; //  name = ' deleteName'

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enter new name : ")
                    .setView(v)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) (v.findViewById(R.id.updateUserName));
                            String newName = editText.getText().toString();
                            args.put(USER_NAME, newName);
                            db.update(TABLE_NAME, args, where, null);
                        }
                    }).show();
        editUserName.setText(""); // clear editText
    }
    public void search(View view) {  //  Search
        final SQLiteDatabase db = mHelper.getReadableDatabase();
        EditText editUserName = (EditText) findViewById(R.id.editUserName);
        String searchName = editUserName.getText().toString();

        final String where = USER_NAME + "='" + searchName +"'"; //  name = ' deleteName'

        Cursor mCursor = db.query(TABLE_NAME, new String[] { _ID, USER_NAME }, where, null, null, null, null, null);

        StringBuilder resultData = new StringBuilder("RESULT: \n");
        while(mCursor.moveToNext()){
            int id = mCursor.getInt(0);
            String name = mCursor.getString(1);
            resultData.append(id).append(": ");
            resultData.append(name).append(", ");
            resultData.append("\n");
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Search")
                .setMessage(resultData)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

        editUserName.setText(""); // clear editText
    }


    public void upgrade(View view) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        mVersion = mVersion + 1 ;
        mHelper.onUpgrade(db, mHelper.getVersion(), mVersion);
        //add line1
        //add line2
    }
}
