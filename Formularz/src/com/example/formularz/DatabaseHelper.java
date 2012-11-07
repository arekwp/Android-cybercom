package com.example.formularz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
 
    // Contacts table name
    private static final String TABLE_FORMS = "forms";
 
    // Contacts Table Columns names
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
	                + KEY_FBSINCE + " TEXT" + ")";
	        db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
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
	    values.put(KEY_FBSINCE, contact.getSurname());
	    values.put(KEY_ISONFB, contact.isDoHaveFbAcc());
	 
	    // Inserting Row
	    db.insert(TABLE_FORMS, null, values);
	    db.close(); // Closing database connection
	}
	
}
