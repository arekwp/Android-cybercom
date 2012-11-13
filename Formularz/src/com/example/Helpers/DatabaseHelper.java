package com.example.Helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
 
    // FormDatas table name
    private static final String TABLE_FORMS = "forms";
    
    private static final String DATABASE_NAME = "forms";
    
    private static final int DATABASE_VERSION = 1;
 
    // FormDatas Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_DESC = "description";
    private static final String KEY_BLOG = "blog";
    private static final String KEY_LANGS = "languages";
    private static final String KEY_COLOURS = "colours";
    private static final String KEY_BIRTH = "birth";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ISONFB = "onfb";
    private static final String KEY_FBSINCE = "fbsince";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {		
		 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FORMS + "("
	                + KEY_ID + " INTEGER PRIMARY KEY," 
	                + KEY_NAME + " TEXT,"
	                + KEY_SURNAME + " TEXT,"
	                + KEY_DESC + " TEXT,"
	                + KEY_BLOG + " TEXT,"
	                + KEY_LANGS + " TEXT,"
	                + KEY_COLOURS + " TEXT,"
	                + KEY_BIRTH + " TEXT,"
	                + KEY_PHONE + " TEXT,"
	                + KEY_GENDER + " TEXT,"
	                + KEY_ISONFB + " INTEGER,"
	                + KEY_FBSINCE + " INTEGER" + ")";
	        db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMS);

				// Create tables again
				onCreate(db);
		
	}

	public void addForm(FormData contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, contact.getName());
	    values.put(KEY_SURNAME, contact.getSurname());
	    values.put(KEY_DESC, contact.getDescription());
	    values.put(KEY_BLOG, contact.getBlog());
	    values.put(KEY_LANGS, contact.getLanguages());
	    values.put(KEY_COLOURS, contact.getColours());
	    values.put(KEY_BIRTH, contact.getBirthDate());
	    values.put(KEY_PHONE, contact.getPhone());
	    values.put(KEY_GENDER, contact.getGender());
	    values.put(KEY_FBSINCE, contact.getDoHaveFbSince());
	    values.put(KEY_ISONFB, contact.getDoHaveFbAcc());
	 
	    // Inserting Row
	    db.insert(TABLE_FORMS, null, values);
	    //db.close(); // Closing database connection
	}
	
	/*
	 * 				0+ KEY_ID + " INTEGER PRIMARY KEY," 
	                1+ KEY_NAME + " TEXT,"
	                2+ KEY_SURNAME + " TEXT,"
	                3+ KEY_DESC + " TEXT,"
	                4+ KEY_BLOG + " TEXT,"
	                5+ KEY_LANGS + " TEXT,"
	                6+ KEY_COLOURS + " TEXT,"
	                7+ KEY_BIRTH + " TEXT,"
	                8+ KEY_PHONE + " TEXT,"
	                9+ KEY_GENDER + " TEXT,"
	                10+ KEY_ISONFB + " INTEGER,"
	                11+ KEY_FBSINCE + " INTEGER" + ")";
	 * 
	 */
	public List<FormData> getAllFormData() {
	    List<FormData> formList = new ArrayList<FormData>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_FORMS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	            FormData form = new FormData();
	            Log.d("ilosc kolumn: ", String.valueOf(cursor.getColumnCount()));
	            Log.d("kolumny: ", cursor.getColumnNames()[0] + ": " + Integer.parseInt(cursor.getString(0)));
	            Log.d("kolumny: ", cursor.getColumnNames()[1] + ": " + cursor.getString(1));
	            Log.d("kolumny: ", cursor.getColumnNames()[2] + ": " + cursor.getString(2));
	            Log.d("kolumny: ", cursor.getColumnNames()[3] + ": " + cursor.getString(3));
	            Log.d("kolumny: ", cursor.getColumnNames()[4] + ": " + cursor.getString(4));
	            Log.d("kolumny: ", cursor.getColumnNames()[5] + ": " + cursor.getString(5));
	            Log.d("kolumny: ", cursor.getColumnNames()[6] + ": " + cursor.getString(6));
	            Log.d("kolumny: ", cursor.getColumnNames()[7] + ": " + cursor.getString(7));
	            Log.d("kolumny: ", cursor.getColumnNames()[8] + ": " + cursor.getString(8));
	            Log.d("kolumny: ", cursor.getColumnNames()[9] + ": " + cursor.getString(9));
	            Log.d("kolumny: ", cursor.getColumnNames()[10] + ": " + Integer.parseInt(cursor.getString(10)));
	            Log.d("kolumny: ", cursor.getColumnNames()[11] + ": " + Integer.parseInt(cursor.getString(11)));
	            
	            form.setId(Integer.parseInt(cursor.getString(0)));
	            form.setName(cursor.getString(1));
	            form.setSurname(cursor.getString(2));
	            form.setDescription(cursor.getString(3));
	            form.setBlog(cursor.getString(4));
	            form.setLanguages(cursor.getString(5));
	            form.setColours(cursor.getString(6));
	            form.setBirthDate(cursor.getString(7));
	            form.setPhone(cursor.getString(8));
	            form.setGender(cursor.getString(9));
	            form.setDoHaveFbAcc(Integer.parseInt(cursor.getString(10)));
	            form.setDoHaveFbSince(Integer.parseInt(cursor.getString(11)));
	            // Adding contact to list
	            formList.add(form);
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    db.close();

	    return formList;
	}
	
	public int getCount()
	{
		
		String countQuery = "SELECT  * FROM " + TABLE_FORMS;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();
		db.close();

		// return count
		return count;
	}
	
	public void dropDb()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMS);
		
		onCreate(db);
		
		db.close();
	}
}
