package com.example.Helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/* TODO add method:
 * update -> form
 * 
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

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
	    int version)
    {
	super(context, name, factory, version);
    }

    public DatabaseHelper(Context context)
    {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FORMS + "("
	        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
	        + KEY_SURNAME + " TEXT," + KEY_DESC + " TEXT," + KEY_BLOG
	        + " TEXT," + KEY_LANGS + " TEXT," + KEY_COLOURS + " TEXT,"
	        + KEY_BIRTH + " TEXT," + KEY_PHONE + " TEXT," + KEY_GENDER
	        + " TEXT," + KEY_ISONFB + " INTEGER," + KEY_FBSINCE
	        + " INTEGER" + ")";
	db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMS);

	// Create tables again
	onCreate(db);

    }

    public void addForm(FormData form)
    {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = packToValues(form);

	// Inserting Row
	db.insert(TABLE_FORMS, null, values);
	// db.close(); // Closing database connection
    }

    /*
     * 0+ KEY_ID + " INTEGER PRIMARY KEY," 1+ KEY_NAME + " TEXT," 2+ KEY_SURNAME
     * + " TEXT," 3+ KEY_DESC + " TEXT," 4+ KEY_BLOG + " TEXT," 5+ KEY_LANGS +
     * " TEXT," 6+ KEY_COLOURS + " TEXT," 7+ KEY_BIRTH + " TEXT," 8+ KEY_PHONE +
     * " TEXT," 9+ KEY_GENDER + " TEXT," 10+ KEY_ISONFB + " INTEGER," 11+
     * KEY_FBSINCE + " INTEGER" + ")";
     */
    public List<FormData> getAllFormData()
    {
	List<FormData> formList = new ArrayList<FormData>();
	// Select All Query
	String selectQuery = "SELECT  * FROM " + TABLE_FORMS;

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQuery, null);

	if (cursor.moveToFirst())
	{
	    do
	    {
		FormData form = packToForm(cursor);
		
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

    public FormData getFormById(int id)
    {
	FormData form = new FormData();
	// Select All Query

	String selectQuery = "SELECT  * FROM " + TABLE_FORMS + " WHERE id = "
	        + id;

	SQLiteDatabase db = this.getWritableDatabase();
	Cursor cursor = db.rawQuery(selectQuery, null);

	if (cursor.moveToFirst())
	{
	    form = packToForm(cursor);
	}
	cursor.close();
	db.close();

	return form;
    }

    private FormData packToForm(Cursor cursor)
    {
	FormData form = new FormData();
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

	return form;
    }

    public int update(FormData fd)
    {
	SQLiteDatabase db = getWritableDatabase();

	ContentValues cv = packToValues(fd);

	return db.update(TABLE_FORMS, cv, KEY_ID + " = ?", new String[]
	{ String.valueOf(fd.getId()) });
    }

    private ContentValues packToValues(FormData form)
    {
	ContentValues values = new ContentValues();
	values.put(KEY_NAME, form.getName());
	values.put(KEY_SURNAME, form.getSurname());
	values.put(KEY_DESC, form.getDescription());
	values.put(KEY_BLOG, form.getBlog());
	values.put(KEY_LANGS, form.getLanguages());
	values.put(KEY_COLOURS, form.getColours());
	values.put(KEY_BIRTH, form.getBirthDate());
	values.put(KEY_PHONE, form.getPhone());
	values.put(KEY_GENDER, form.getGender());
	values.put(KEY_FBSINCE, form.getDoHaveFbSince());
	values.put(KEY_ISONFB, form.getDoHaveFbAcc());
	return values;
    }
}
