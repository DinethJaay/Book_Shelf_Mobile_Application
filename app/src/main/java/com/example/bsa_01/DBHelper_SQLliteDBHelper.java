package com.example.bsa_01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_SQLliteDBHelper extends SQLiteOpenHelper {
    //create db name variable
    private static final String db_name = "Sarasavi_BookShop_01";
    //create db version variable
    private static final int db_version = 1;
    @Override
    public void onCreate(SQLiteDatabase db) {
        UserRegistration(db);
        AdminRegistration(db);
        CourierRegistration(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserUpgrade(db);
        AdminUpgrade(db);
        CourierUpgrade(db);
    }
    public DBHelper_SQLliteDBHelper(Context context) {
        super(context,db_name,null,db_version);
    }
    private void UserUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + "UserRegistration");
        onCreate(db);
    }
    private void AdminUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + "AdminRegistration");
        onCreate(db);
    }
    private void CourierUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + "CourierRegistration");
        onCreate(db);
    }
    private void UserRegistration(SQLiteDatabase db) {
        String sql = "CREATE TABLE UserRegistration(Email TEXT PRIMARY KEY,"+
                "Name TEXT,"+
                "Password TEXT,"+
                "userRole TEXT)";

        //execute the sql statement
        db.execSQL(sql);
    }


    private void AdminRegistration(SQLiteDatabase db) {
        String createAdminSQL = "CREATE TABLE AdminRegistration (" +
                "EmployeeID TEXT PRIMARY KEY, " +
                "EmployeeRole TEXT, " +
                "city TEXT, " +
                "Email TEXT );";

        db.execSQL(createAdminSQL);
    }

    private void CourierRegistration(SQLiteDatabase db) {
        String createAdminSQL = "CREATE TABLE CourierRegistration (" +
                "EmployeeID TEXT PRIMARY KEY, " +
                "EmployeeRole TEXT, " +
                "city TEXT, " +
                "Email TEXT );";

        db.execSQL(createAdminSQL);
    }
    public Boolean insertUserData(String Email,String Name,String Password,String userRole)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email",Email);
        contentValues.put("Name",Name);
        contentValues.put("Password",Password);
        contentValues.put("userRole",userRole);

        long result = sqLiteDatabase.insert("UserRegistration",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean checkUserEmail(String Email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserRegistration Where Email = ? ",new String[]{Email});
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
        Cursor cursor = db.rawQuery("Select userRole from UserRegistration Where Email = ? ",new String[]{Email});
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


    public boolean CheckUserCredentials(String Email, String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from UserRegistration Where Email = ? and Password = ?",new String[]{Email,Password});
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

    public Boolean insertAdminData(String Email,String employeeID, String employeeRole, String city) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("EmployeeID",employeeID);
        contentValues.put("EmployeeRole",employeeRole);
        contentValues.put("city",city);
        contentValues.put("Email",Email);

        long result = sqLiteDatabase.insert("AdminRegistration",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean insertCourierData(String email, String employeeID, String employeeRole, String city) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EmployeeID",employeeID);
        contentValues.put("EmployeeRole",employeeRole);
        contentValues.put("city",city);
        contentValues.put("Email",email);

        long result = sqLiteDatabase.insert("CourierRegistration",null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
