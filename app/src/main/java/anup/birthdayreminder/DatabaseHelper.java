package anup.birthdayreminder;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Reminder.db";
    public static final String TABLE_NAME = "Reminder";
    public static final String ID_COL= "ID";
    public static final String REMINDER_TEXT_COL = "REMINDER_NOTE";
    public static final String DATETIME_COL = "DATETIME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);



    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME+
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, REMINDER_NOTE TEXT, DATETIME INTEGER, PHONE_NUMBER TEXT)");

    }
;
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String reminder, String datetime, String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMINDER_TEXT_COL, reminder);
        contentValues.put(DATETIME_COL, datetime);
        contentValues.put(PHONE_NUMBER, phonenumber);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result ==  -1) {
            return false;
        }

        return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        if(cursor == null) {
            return null;
        }
        return cursor;

    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?" ,new String[]{String.valueOf(id)});

    }


}