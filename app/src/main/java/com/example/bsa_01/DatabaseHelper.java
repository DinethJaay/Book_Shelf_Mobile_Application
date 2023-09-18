package com.example.bsa_01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //create db name variable
    private static final String db_name = "BookShop";
    //create db version variable
    private static final int db_version = 1;


    public DatabaseHelper(Context context) {
        super(context,db_name,null,db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create sql statement
        String sql = "CREATE TABLE user(Email TEXT PRIMARY KEY,"+
                "Name TEXT,"+
                "Password TEXT,"+
                "userRole TEXT )";

        //execute the sql statement
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "user");
        onCreate(db);
    }

    public Boolean insertData(String Email,String Name,String Password,String userRole)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email",Email);
        contentValues.put("Name",Name);
        contentValues.put("Password",Password);
        contentValues.put("userRole",userRole);

        long result = sqLiteDatabase.insert("user",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean checkEmail(String Email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user Where Email = ? ",new String[]{Email});
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String checkUserRole(String Email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select userRole from user Where Email = ? ",new String[]{Email});
        if (cursor.moveToFirst())
        {
            int userRoleColumnIndex = cursor.getColumnIndex("userRole");
            // Retrieve the user role from the cursor
            String userRole = cursor.getString(userRoleColumnIndex);
            cursor.close();
            db.close();
            return userRole;
        }
        else
        {
            cursor.close();
            db.close();
            return null; // Or any other appropriate value indicating the user doesn't exist
        }

    }


    public boolean CheckCredentials(String Email, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user Where Email = ? and Password = ?",new String[]{Email,Password});
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
